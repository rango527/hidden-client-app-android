/* Copyright Airship and Contributors */

package com.urbanairship.wallet;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import android.text.TextUtils;

import com.urbanairship.AirshipExecutors;
import com.urbanairship.Logger;
import com.urbanairship.UAirship;
import com.urbanairship.http.Request;
import com.urbanairship.http.RequestFactory;
import com.urbanairship.http.Response;
import com.urbanairship.json.JsonException;
import com.urbanairship.json.JsonMap;
import com.urbanairship.json.JsonValue;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

/**
 * Defines a request to fetch a {@link Pass}.
 */
public class PassRequest {

    private static final Executor DEFAULT_REQUEST_EXECUTOR = AirshipExecutors.newSerialExecutor();

    private static final String DEPRECATED_PATH_FORMAT = "v1/pass/%s?api_key=%s";
    private static final String PATH_FORMAT = "v1/pass/%s";

    private static final String API_REVISION_HEADER_NAME = "Api-Revision";
    private static final String API_REVISION = "1.2";
    private static final String FIELDS_KEY = "fields";
    private static final String HEADERS_KEY = "headers";
    private static final String PUBLIC_URL_KEY = "publicURL";
    private static final String PUBLIC_URL_TYPE_KEY = "type";
    private static final String TAG_KEY = "tag";
    private static final String EXTERNAL_ID_KEY = "externalId";

    private final String userName;
    private final String apiKey;
    private final String templateId;
    private final Collection<Field> fields;
    private final Collection<Field> headers;
    private final String tag;
    private final String externalId;
    private final RequestFactory requestFactory;
    private final Executor requestExecutor;

    private CancelableCallback requestCallback;

    /**
     * Constructor available for testing.
     *
     * @param builder The pass request builder instance.
     * @param requestFactory An HTTP request factory instance.
     * @param requestExecutor A thread executor instance.
     */
    PassRequest(@NonNull Builder builder, @NonNull RequestFactory requestFactory, @NonNull Executor requestExecutor) {
        this.apiKey = builder.apiKey;
        this.userName = builder.userName;
        this.templateId = builder.templateId;
        this.fields = builder.fields;
        this.headers = builder.headers;
        this.tag = builder.tag;
        this.externalId = builder.externalId;
        this.requestFactory = requestFactory;
        this.requestExecutor = requestExecutor;
    }

    /**
     * Default constructor.
     *
     * @param builder The pass request builder instance.
     */
    PassRequest(@NonNull Builder builder) {
        this(builder, RequestFactory.DEFAULT_REQUEST_FACTORY, DEFAULT_REQUEST_EXECUTOR);
    }

    /**
     * Creates a new Builder instance.
     *
     * @return The new Builder instance.
     */
    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Executes the request to fetch the {@link Pass}. Must be called on the
     * UI thread.
     *
     * @param callback A callback for the result.
     */
    @SuppressLint("NewApi")
    public void execute(@NonNull Callback callback) {
        execute(callback, null);
    }

    /**
     * Executes the request to fetch the {@link Pass}. Must be called on the
     * UI thread.
     *
     * @param callback A callback for the result.
     * @param looper The looper used for executing the result callback. Defaults to that
     * of the calling thread if null.
     */
    @SuppressLint("NewApi")
    public void execute(@NonNull Callback callback, @Nullable Looper looper) {
        if (requestCallback != null) {
            throw new IllegalStateException("PassRequest can only be executed once.");
        }

        requestCallback = new CancelableCallback(callback, looper);
        Runnable requestRunnable = new Runnable() {
            @Override
            public void run() {
                Logger.info("Requesting pass %s", templateId);
                URL url;

                try {
                    url = getPassUrl();
                } catch (MalformedURLException e) {
                    Logger.error(e, "PassRequest - Invalid pass URL");
                    requestCallback.setResult(-1, null);
                    return;
                }

                JsonMap.Builder fieldsJson = JsonMap.newBuilder();
                for (Field field : fields) {
                    fieldsJson.putOpt(field.getName(), field.toJsonValue());
                }

                JsonMap headersJson = null;
                if (!headers.isEmpty()) {
                    JsonMap.Builder builder = JsonMap.newBuilder();
                    for (Field header : headers) {
                        builder.putOpt(header.getName(), header.toJsonValue());
                    }

                    headersJson = builder.build();
                }

                JsonMap body = JsonMap.newBuilder()
                                      .putOpt(HEADERS_KEY, headersJson)
                                      .put(FIELDS_KEY, fieldsJson.build())
                                      .putOpt(TAG_KEY, tag)
                                      .put(PUBLIC_URL_KEY, JsonMap.newBuilder().put(PUBLIC_URL_TYPE_KEY, "multiple").build())
                                      .putOpt(EXTERNAL_ID_KEY, externalId)
                                      .build();

                Request httpRequest = requestFactory.createRequest("POST", url)
                                                    .setHeader(API_REVISION_HEADER_NAME, API_REVISION)
                                                    .setRequestBody(body.toString(), "application/json");

                if (userName != null) {
                    httpRequest.setCredentials(userName, apiKey);
                }

                Logger.debug("PassRequest - Requesting pass %s with payload: %s", url, body);
                Response response = httpRequest.execute();

                if (response == null) {
                    Logger.error("PassRequest - Failed to get a response.");
                    requestCallback.setResult(-1, null);
                    return;
                }

                if (response.getStatus() == HttpURLConnection.HTTP_OK) {
                    JsonValue json;
                    try {
                        json = JsonValue.parseString(response.getResponseBody());
                    } catch (JsonException e) {
                        Logger.error("PassRequest - Failed to parse response body %s", response.getResponseBody());
                        return;
                    }

                    Logger.debug("PassRequest - Received pass response: %s for pass %s", json, url);
                    requestCallback.setResult(response.getStatus(), Pass.parsePass(json));
                } else {
                    Logger.debug("PassRequest - Pass %s request failed with status %s", templateId, response.getStatus());
                    requestCallback.setResult(response.getStatus(), null);
                }

                // Notify the result
                requestCallback.run();
            }
        };

        requestExecutor.execute(requestRunnable);
    }

    /**
     * Cancels the requests.
     */
    public void cancel() {
        if (requestCallback != null) {
            requestCallback.cancel();
        }
    }

    /**
     * Gets the pass request URL.
     *
     * @return The pass request URL.
     * @throws MalformedURLException if URL string is malformed.
     */
    @NonNull
    URL getPassUrl() throws MalformedURLException {
        Uri uri;
        if (userName == null) {
            uri = Uri.withAppendedPath(Uri.parse(UAirship.shared().getAirshipConfigOptions().walletUrl), String.format(Locale.US, DEPRECATED_PATH_FORMAT, templateId, apiKey));
        } else {
            uri = Uri.withAppendedPath(Uri.parse(UAirship.shared().getAirshipConfigOptions().walletUrl), String.format(Locale.US, PATH_FORMAT, templateId));
        }

        return new URL(uri.toString());
    }

    @NonNull
    @Override
    public String toString() {
        return "PassRequest{ templateId: " + templateId + ", fields: " + fields + ", tag: " + tag + ", externalId: " + externalId + ", headers: " + headers + " }";
    }

    /**
     * Builds the {@link PassRequest} object.
     */
    public static class Builder {

        private String apiKey;
        private String templateId;
        private final List<Field> fields = new ArrayList<>();
        private final List<Field> headers = new ArrayList<>();
        private String tag;
        private String externalId;
        private String userName;

        /**
         * Sets the request auth.
         *
         * @param userName The request user name.
         * @param token The request token.
         * @return Builder object.
         */
        @NonNull
        public Builder setAuth(@NonNull String userName, @NonNull String token) {
            this.apiKey = token;
            this.userName = userName;
            return this;
        }

        /**
         * Sets the Template ID.
         *
         * @param templateId The ID of the template.
         * @return Builder object.
         */
        @NonNull
        public Builder setTemplateId(@NonNull @Size(min = 1) String templateId) {
            this.templateId = templateId;
            return this;
        }

        /**
         * Adds field information for the pass.
         *
         * @param field The field instance.
         * @return Builder object.
         */
        @NonNull
        public Builder addField(@NonNull Field field) {
            fields.add(field);
            return this;
        }

        /**
         * Sets the expirationDate field.
         *
         * @param value The expiration date value.
         * @param label The expiration date label.
         * @return Builder object.
         */
        @NonNull
        public Builder setExpirationDate(@NonNull String value, @NonNull String label) {
            Field field = Field.newBuilder()
                               .setName("expirationDate")
                               .setValue(value)
                               .setLabel(label)
                               .build();

            headers.add(field);
            return this;
        }

        /**
         * Sets the barcode_value field.
         *
         * @param value The barcode_value value.
         * @param label The barcode_value label.
         * @return Builder object.
         */
        @NonNull
        public Builder setBarcodeValue(@NonNull String value, @NonNull String label) {
            Field field = Field.newBuilder()
                               .setName("barcode_value")
                               .setValue(value)
                               .setLabel(label)
                               .build();

            headers.add(field);
            return this;
        }

        /**
         * Sets the barcodeAltText field.
         *
         * @param value The barcodeAltText value.
         * @param label The barcodeAltText label.
         * @return Builder object.
         */
        @NonNull
        public Builder setBarcodeAltText(@NonNull String value, @NonNull String label) {
            Field field = Field.newBuilder()
                               .setName("barcodeAltText")
                               .setValue(value)
                               .setLabel(label)
                               .build();

            headers.add(field);
            return this;
        }

        /**
         * Sets the pass tag.
         *
         * @param tag The pass tag.
         * @return Builder object.
         */
        @NonNull
        public Builder setTag(@Nullable String tag) {
            this.tag = tag;
            return this;
        }

        /**
         * Sets the external ID.
         *
         * @param externalId The external ID.
         * @return Builder object.
         */
        @NonNull
        public Builder setExternalId(@Nullable String externalId) {
            this.externalId = externalId;
            return this;
        }

        /**
         * Builds the {@link PassRequest}.
         *
         * @return A @link PassRequest} instance.
         * @throws IllegalStateException if the apiKey or templateId is
         * null or empty.
         */
        @NonNull
        public PassRequest build() {
            if (TextUtils.isEmpty(apiKey) || TextUtils.isEmpty(templateId)) {
                throw new IllegalStateException("The apiKey or templateId is missing.");
            }

            return new PassRequest(this);
        }

    }

}
