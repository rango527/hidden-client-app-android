/* Copyright Airship and Contributors */

package com.urbanairship.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.urbanairship.Logger;
import com.urbanairship.R;
import com.urbanairship.UAirship;
import com.urbanairship.actions.Action;
import com.urbanairship.actions.ActionArguments;
import com.urbanairship.actions.ActionCompletionCallback;
import com.urbanairship.actions.ActionResult;
import com.urbanairship.actions.ActionRunRequestFactory;
import com.urbanairship.actions.ActionValue;
import com.urbanairship.js.Whitelist;
import com.urbanairship.json.JsonException;
import com.urbanairship.json.JsonValue;
import com.urbanairship.richpush.RichPushMessage;
import com.urbanairship.util.UriUtils;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.WeakHashMap;

/**
 * <p>
 * A web view client that intercepts Airship URLs and enables triggering
 * actions from javascript.
 * <p>
 * <p>
 * <p>
 * The UAWebViewClient will intercept links with the 'uairship' scheme and with
 * the commands (supplied as the host) 'run-actions' or 'run-basic-actions'.
 * <p>
 * <p>
 * <p>
 * The run-actions command runs a set of actions listed in the URL's query
 * options, by providing key=value pairs, where each pair's key is the name of
 * an action and the value is a JSON encoded string representing the value of
 * the action's {@link com.urbanairship.actions.ActionArguments}. The JSON
 * encoded string is decoded and converted to a List<Object> if the argument is
 * a JSONArray or a Map<String, Object> if the argument is a JSONObject.
 * <p>
 * <p>
 * <p>
 * Example: uairship://run-actions?&add_tags_action=%5B%22one%22%2C%22two%22%5D
 * will run the "add_tags_action" with value "["one", "two"]".
 * <p>
 * <p>
 * <p>
 * The run-basic-actions command is similar to run-actions, but the argument value
 * is treated as a string literal.
 * <p>
 * <p>
 * <p>
 * Example: uairship://run-basic-actions?add_tags_action=one&remove_tags_action=two will run
 * the "add_tags_action" with the value "one", and perform the "remove_tags_action" action with
 * value "two".
 * <p>
 * <p>
 * <p>
 * When extending this class, any overridden methods should call through to the
 * super class' implementations.
 * <p>
 */
public class UAWebViewClient extends WebViewClient {

    /**
     * Airship's scheme. The web view client will override any
     * URLs that have this scheme by default.
     */
    @NonNull
    public static final String UA_ACTION_SCHEME = "uairship";

    /**
     * Run basic actions command.
     */
    @NonNull
    public static final String RUN_BASIC_ACTIONS_COMMAND = "run-basic-actions";

    /**
     * Run actions command.
     */
    @NonNull
    public static final String RUN_ACTIONS_COMMAND = "run-actions";

    /**
     * Run actions command with a callback.
     */
    private static final String RUN_ACTIONS_COMMAND_CALLBACK = "run-action-cb";

    /**
     * Close command to handle close method in the Javascript Interface.
     */
    @NonNull
    public static final String CLOSE_COMMAND = "close";

    private final Map<String, Credentials> authRequestCredentials = new HashMap<>();
    private ActionCompletionCallback actionCompletionCallback;
    private final ActionRunRequestFactory actionRunRequestFactory;

    private static SimpleDateFormat dateFormatter;
    private static String nativeBridge;
    private boolean faviconEnabled = false;

    private final Map<WebView, InjectJsBridgeTask> injectJsBridgeTaskMap = new WeakHashMap<>();

    /**
     * Default constructor.
     */
    public UAWebViewClient() {
        this(new ActionRunRequestFactory());
    }

    /**
     * Constructs a UAWebViewClient with the specified ActionRunRequestFactory.
     *
     * @param actionRunRequestFactory The action run request factory.
     */
    protected UAWebViewClient(@NonNull ActionRunRequestFactory actionRunRequestFactory) {
        this.actionRunRequestFactory = actionRunRequestFactory;
    }

    /**
     * Sets the action completion callback to be invoked whenever an {@link com.urbanairship.actions.Action}
     * is finished running from the web view.
     *
     * @param actionCompletionCallback The completion callback.
     */
    public void setActionCompletionCallback(@Nullable ActionCompletionCallback actionCompletionCallback) {
        synchronized (this) {
            this.actionCompletionCallback = actionCompletionCallback;
        }
    }

    /**
     * Called when UAirship.close() is triggered from the Airship Javascript interface.
     * <p>
     * The default behavior simulates a back key press.
     *
     * @param webView The web view.
     */
    public void onClose(@NonNull final WebView webView) {
        webView.getRootView().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        webView.getRootView().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
    }

    @CallSuper
    @Override
    public boolean shouldOverrideUrlLoading(@NonNull WebView webView, @Nullable String url) {
        return interceptUrl(webView, url);
    }

    /**
     * Sets favicon enabled flag.
     *
     * @hide
     *
     * @param enabled {@code true} to enable favicon, {@code false} to disable and intercept favicon request.
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public void setFaviconEnabled(boolean enabled) {
        faviconEnabled = enabled;
    }

    /**
     * Intercepts the favicon request and returns a blank favicon
     *
     * @param webView The web view.
     * @param url The url being loaded.
     * @return The blank favicon image embedded in a WebResourceResponse or null if the url does not contain a favicon.
     */
    @CallSuper
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        if (faviconEnabled) {
            return null;
        }

        if (url.toLowerCase().endsWith("/favicon.ico")) {
            return generateEmptyFaviconResponse(webView);
        }

        return null;
    }

    /**
     * Intercepts the favicon request and returns blank favicon
     *
     * @param webView The web view.
     * @param request The WebResourceRequest being loaded.
     * @return The blank favicon image embedded in a WebResourceResponse or null if the url does not contain a favicon.
     */
    @CallSuper
    @Override
    @SuppressLint("NewApi")
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request) {
        if (faviconEnabled) {
            return null;
        }

        if (!request.isForMainFrame()) {
            String path = request.getUrl().getPath();
            if (path != null && path.endsWith("/favicon.ico")) {
                return generateEmptyFaviconResponse(webView);
            }
        }

        return null;
    }

    @CallSuper
    @Override
    public void onLoadResource(@NonNull WebView webView, @Nullable String url) {

        /*
         * Sometimes shouldOverrideUrlLoading is not called when the uairship library is ready for whatever reasons,
         * but once shouldOverrideUrlLoading is called and returns true it will prevent onLoadResource from
         * being called with the url.
         */

        interceptUrl(webView, url);
    }

    /**
     * Intercepts a url for our JS bridge.
     *
     * @param webView The web view.
     * @param url The url being loaded.
     * @return <code>true</code> if the url was loaded, otherwise <code>false</code>.
     */
    protected boolean interceptUrl(@NonNull WebView webView, @Nullable String url) {
        if (url == null) {
            return false;
        }

        Uri uri = Uri.parse(url);
        if (uri.getHost() == null || !UA_ACTION_SCHEME.equals(uri.getScheme()) || !isWhiteListed(webView.getUrl())) {
            return false;
        }

        Logger.verbose("Intercepting: %s", url);
        switch (uri.getHost()) {
            case RUN_BASIC_ACTIONS_COMMAND:
                Logger.info("Running run basic actions command for URL: %s", url);
                runActions(webView, decodeActionArguments(uri, true));
                return true;

            case RUN_ACTIONS_COMMAND:
                Logger.info("Running run actions command for URL: %s", url);
                runActions(webView, decodeActionArguments(uri, false));
                return true;

            case RUN_ACTIONS_COMMAND_CALLBACK:
                Logger.info("Running run actions command with callback for URL: %s", url);

                List<String> paths = uri.getPathSegments();
                if (paths.size() == 3) {
                    Logger.info("Action: %s, Args: %s, Callback: %s", paths.get(0), paths.get(1), paths.get(2));
                    runAction(webView, paths.get(0), paths.get(1), paths.get(2));
                } else {
                    Logger.error("Unable to run action, invalid number of arguments.");
                }
                return true;

            case CLOSE_COMMAND:
                Logger.info("Running close command for URL: %s", url);
                onClose(webView);
                return true;

            default:
                Logger.warn("Unrecognized command: %s for URL: %s", uri.getHost(), url);

                return false;
        }
    }

    /**
     * Runs a set of actions for the web view.
     *
     * @param webView The web view.
     * @param arguments Map of action to action arguments to run.
     */
    private void runActions(@NonNull WebView webView, @Nullable Map<String, List<ActionValue>> arguments) {
        if (arguments == null) {
            return;
        }

        Bundle metadata = new Bundle();
        RichPushMessage message = getMessage(webView);
        if (message != null) {
            metadata.putString(ActionArguments.RICH_PUSH_ID_METADATA, message.getMessageId());
        }

        for (String actionName : arguments.keySet()) {
            List<ActionValue> args = arguments.get(actionName);
            if (args == null) {
                continue;
            }
            for (ActionValue arg : args) {
                actionRunRequestFactory.createActionRequest(actionName)
                                       .setValue(arg)
                                       .setMetadata(metadata)
                                       .setSituation(Action.SITUATION_WEB_VIEW_INVOCATION)
                                       .run(new ActionCompletionCallback() {
                                           @Override
                                           public void onFinish(@NonNull ActionArguments arguments, @NonNull ActionResult result) {
                                               synchronized (this) {
                                                   if (actionCompletionCallback != null) {
                                                       actionCompletionCallback.onFinish(arguments, result);
                                                   }
                                               }
                                           }
                                       });
            }
        }
    }

    private WebResourceResponse generateEmptyFaviconResponse(@NonNull WebView webView) {
        try {
            return new WebResourceResponse("image/png", null, new BufferedInputStream(webView.getContext().getResources().openRawResource(R.raw.ua_blank_favicon)));
        } catch (Exception e) {
            Logger.error(e, "Failed to read blank favicon with IOException.");
        }

        return null;
    }

    /**
     * Runs a single action by name, and performs callbacks into the JavaScript layer with results.
     *
     * @param webView The web view.
     * @param name The name of the action to run.
     * @param value A JSON-encoded string representing the action value.
     * @param callbackKey The key for the callback function in JavaScript.
     */
    private void runAction(@NonNull final WebView webView, @NonNull final String name, @Nullable final String value, @Nullable final String callbackKey) {
        // Parse the action value
        ActionValue actionValue;
        try {
            actionValue = new ActionValue(JsonValue.parseString(value));
        } catch (JsonException e) {
            Logger.error(e, "Unable to parse action argument value: %s", value);
            triggerCallback(webView, "Unable to decode arguments payload", new ActionValue(), callbackKey);
            return;
        }

        // Create metadata
        Bundle metadata = new Bundle();
        RichPushMessage message = getMessage(webView);
        if (message != null) {
            metadata.putString(ActionArguments.RICH_PUSH_ID_METADATA, message.getMessageId());
        }

        // Run the action
        actionRunRequestFactory.createActionRequest(name)
                               .setMetadata(metadata)
                               .setValue(actionValue)
                               .setSituation(Action.SITUATION_WEB_VIEW_INVOCATION)
                               .run(new ActionCompletionCallback() {
                                   @Override
                                   public void onFinish(@NonNull ActionArguments arguments, @NonNull ActionResult result) {

                                       String errorMessage = null;
                                       switch (result.getStatus()) {
                                           case ActionResult.STATUS_COMPLETED:
                                               break;
                                           case ActionResult.STATUS_ACTION_NOT_FOUND:
                                               errorMessage = String.format("Action %s not found", name);
                                               break;
                                           case ActionResult.STATUS_REJECTED_ARGUMENTS:
                                               errorMessage = String.format("Action %s rejected its arguments", name);
                                               break;
                                           case ActionResult.STATUS_EXECUTION_ERROR:
                                               if (result.getException() != null) {
                                                   errorMessage = result.getException().getMessage();
                                               } else {
                                                   errorMessage = String.format("Action %s failed with unspecified error", name);
                                               }
                                       }

                                       triggerCallback(webView, errorMessage, result.getValue(), callbackKey);

                                       synchronized (this) {
                                           if (actionCompletionCallback != null) {
                                               actionCompletionCallback.onFinish(arguments, result);
                                           }
                                       }
                                   }
                               });
    }

    /**
     * Helper method that calls the action callback.
     *
     * @param webView The web view.
     * @param error The error message or null if no error.
     * @param resultValue The actions value of the result.
     * @param callbackKey The key for the callback function in JavaScript.
     */
    @SuppressLint("NewAPI")
    private void triggerCallback(@NonNull final WebView webView, @Nullable String error, @NonNull ActionValue resultValue, @Nullable String callbackKey) {
        // Create the callback string
        String callbackString = String.format("'%s'", callbackKey);

        // Create the error string
        String errorString;
        if (error == null) {
            errorString = "null";
        } else {
            errorString = String.format(Locale.US, "new Error(%s)", JSONObject.quote(error));
        }

        // Create the result value
        String resultValueString = resultValue.toString();

        // Create the javascript call for UAirship.finishAction(error, value, callback)
        final String finishAction = String.format(Locale.US, "UAirship.finishAction(%s, %s, %s);",
                errorString, resultValueString, callbackString);

        if (Build.VERSION.SDK_INT >= 19) {
            webView.evaluateJavascript(finishAction, null);
        } else {
            webView.loadUrl("javascript:" + finishAction);
        }
    }

    /**
     * Decodes actions with basic URL or URL+json encoding
     *
     * @param uri The uri.
     * @param basicEncoding A boolean to select for basic encoding
     * @return A map of action values under action name strings or returns null if decoding error occurs.
     */
    private Map<String, List<ActionValue>> decodeActionArguments(@NonNull Uri uri, boolean basicEncoding) {
        Map<String, List<String>> options = UriUtils.getQueryParameters(uri);
        Map<String, List<ActionValue>> decodedActions = new HashMap<>();

        for (String actionName : options.keySet()) {
            List<ActionValue> decodedActionArguments = new ArrayList<>();

            if (options.get(actionName) == null) {
                Logger.warn("No arguments to decode for actionName: %s", actionName);
                return null;
            }

            List<String> args = options.get(actionName);
            if (args == null) {
                continue;
            }
            for (String arg : args) {
                try {
                    JsonValue jsonValue = basicEncoding ? JsonValue.wrap(arg) : JsonValue.parseString(arg);
                    decodedActionArguments.add(new ActionValue(jsonValue));
                } catch (JsonException e) {
                    Logger.warn(e, "Invalid json. Unable to create action argument "
                            + actionName + " with args: " + arg);
                    return null;
                }
            }

            decodedActions.put(actionName, decodedActionArguments);
        }

        if (decodedActions.isEmpty()) {
            Logger.warn("Error no action names are present in the actions key set");
            return null;
        }

        return decodedActions;
    }

    @CallSuper
    @Override
    public void onPageFinished(@Nullable final WebView view, @Nullable String url) {
        if (view == null) {
            return;
        }

        if (!isWhiteListed(url)) {
            Logger.debug("UAWebViewClient - %s is not a white listed URL. Airship Javascript interface will not be accessible.", url);
            return;
        }

        Logger.info("Loading Airship Javascript interface.");
        InjectJsBridgeTask task = new InjectJsBridgeTask(view.getContext(), view);
        injectJsBridgeTaskMap.put(view, task);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @CallSuper
    @Override
    public void onPageStarted(@NonNull WebView view, @Nullable String url, @Nullable Bitmap favicon) {
        InjectJsBridgeTask task = injectJsBridgeTaskMap.remove(view);
        if (task != null) {
            task.cancel(true);
        }
    }

    /**
     * Checks if the URL is white listed.
     *
     * @param url The URL being loaded.
     * @return <code>true</code> if the URL is white listed, otherwise <code>false</code>.
     */
    protected boolean isWhiteListed(@Nullable String url) {
        return UAirship.shared().getWhitelist().isWhitelisted(url, Whitelist.SCOPE_JAVASCRIPT_INTERFACE);
    }

    @CallSuper
    @Override
    public void onReceivedHttpAuthRequest(@NonNull WebView view, @NonNull HttpAuthHandler handler, @Nullable String host,
                                          @Nullable String realm) {
        Credentials credentials = authRequestCredentials.get(host);
        if (credentials != null) {
            handler.proceed(credentials.username, credentials.password);
        }
    }

    /**
     * Adds auth request credentials for a host.
     *
     * @param expectedAuthHost The expected host.
     * @param username The auth user.
     * @param password The auth password.
     */
    void addAuthRequestCredentials(@NonNull String expectedAuthHost, @Nullable String username, @Nullable String password) {
        authRequestCredentials.put(expectedAuthHost, new Credentials(username, password));
    }

    /**
     * Removes auth request credentials for a host.
     *
     * @param expectedAuthHost The expected host.
     */
    void removeAuthRequestCredentials(@NonNull String expectedAuthHost) {
        authRequestCredentials.remove(expectedAuthHost);
    }

    private String createGetter(@NonNull String functionName, @Nullable String value) {
        value = (value == null) ? "null" : JSONObject.quote(value);
        return String.format(Locale.US, "_UAirship.%s = function(){return %s;};", functionName, value);
    }

    private String createGetter(@NonNull String functionName, long value) {
        return String.format(Locale.US, "_UAirship.%s = function(){return %d;};", functionName, value);
    }

    /**
     * Helper method to get the RichPushMessage from the web view.
     *
     * @param webView The web view.
     * @return The rich push message or null if the web view is not an instance of UAWebView
     * or does not have an associated message.
     */
    @Nullable
    private RichPushMessage getMessage(@Nullable WebView webView) {
        if (webView instanceof UAWebView) {
            return ((UAWebView) webView).getCurrentMessage();
        }
        return null;
    }

    /**
     * Credentials model class.
     */
    private static class Credentials {

        final String username;
        final String password;

        Credentials(String username, String password) {
            this.username = username;
            this.password = password;
        }

    }

    /**
     * Async task to inject the Javascript bridge.
     */
    @SuppressLint("StaticFieldLeak")
    private class InjectJsBridgeTask extends AsyncTask<Void, Void, String> {

        @NonNull
        private final WeakReference<WebView> webViewWeakReference;
        private final Context context;

        private InjectJsBridgeTask(Context context, WebView webView) {
            this.context = context.getApplicationContext();
            this.webViewWeakReference = new WeakReference<>(webView);
        }

        @Nullable
        @Override
        protected String doInBackground(Void... params) {
            WebView webView = webViewWeakReference.get();
            if (webView == null) {
                return null;
            }

            RichPushMessage message = getMessage(webView);

            if (dateFormatter == null) {
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.US);
                dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            }

            /*
             * The native bridge will prototype _UAirship, so inject any additional
             * functionality under _UAirship and the final UAirship object will have
             * access to it.
             */
            StringBuilder sb = new StringBuilder().append("var _UAirship = {};");

            // Getters
            sb.append(createGetter("getDeviceModel", Build.MODEL))
              .append(createGetter("getMessageId", (message != null) ? message.getMessageId() : null))
              .append(createGetter("getMessageTitle", (message != null) ? message.getTitle() : null))
              .append(createGetter("getMessageSentDate", (message != null) ? dateFormatter.format(message.getSentDate()) : null))
              .append(createGetter("getMessageSentDateMS", (message != null) ? message.getSentDateMS() : -1))
              .append(createGetter("getUserId", UAirship.shared().getInbox().getUser().getId()))
              .append(createGetter("getChannelId", UAirship.shared().getChannel().getId()))
              .append(createGetter("getAppKey", UAirship.shared().getAirshipConfigOptions().appKey))
              .append(createGetter("getNamedUser", UAirship.shared().getNamedUser().getId()));

            if (TextUtils.isEmpty(nativeBridge)) {
                try {
                    nativeBridge = readNativeBridge();
                } catch (IOException e) {
                    Logger.error("Failed to read native bridge.");
                }
            }

            sb.append(nativeBridge);

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String jsBridge) {
            WebView webView = webViewWeakReference.get();
            if (webView == null) {
                return;
            }

            injectJsBridgeTaskMap.remove(webView);

            if (Build.VERSION.SDK_INT >= 19) {
                webView.evaluateJavascript(jsBridge, null);
            } else {
                webView.loadUrl("javascript:" + jsBridge);
            }
        }

        /**
         * Helper method to read the native bridge from resources.
         *
         * @return The native bridge.
         * @throws IOException if output steam read or write operations fail.
         */
        private String readNativeBridge() throws IOException {
            InputStream input = context.getResources().openRawResource(R.raw.ua_native_bridge);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                byte[] buffer = new byte[1024];
                int length;

                while ((length = input.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }

                return outputStream.toString();
            } finally {
                try {
                    input.close();
                    outputStream.close();
                } catch (Exception e) {
                    Logger.debug(e, "Failed to close streams");
                }
            }
        }

    }

}
