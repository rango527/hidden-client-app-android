/* Copyright Airship and Contributors */

package com.urbanairship.iam;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.Size;
import androidx.annotation.StringDef;

import com.urbanairship.Logger;
import com.urbanairship.iam.banner.BannerDisplayContent;
import com.urbanairship.iam.custom.CustomDisplayContent;
import com.urbanairship.iam.fullscreen.FullScreenDisplayContent;
import com.urbanairship.iam.html.HtmlDisplayContent;
import com.urbanairship.iam.modal.ModalDisplayContent;
import com.urbanairship.json.JsonException;
import com.urbanairship.json.JsonMap;
import com.urbanairship.json.JsonSerializable;
import com.urbanairship.json.JsonValue;
import com.urbanairship.util.Checks;
import com.urbanairship.util.UAStringUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines an in-app message.
 */
public class InAppMessage implements Parcelable, JsonSerializable {

    /**
     * Max message ID length.
     */
    public static final int MAX_ID_LENGTH = 100;

    // JSON keys
    static final String MESSAGE_ID_KEY = "message_id";

    private static final String DISPLAY_TYPE_KEY = "display_type";
    private static final String DISPLAY_CONTENT_KEY = "display";
    private static final String EXTRA_KEY = "extra";
    private static final String AUDIENCE_KEY = "audience";
    private static final String ACTIONS_KEY = "actions";
    private static final String SOURCE_KEY = "source";
    private static final String CAMPAIGNS_KEY = "campaigns";
    private static final String DISPLAY_BEHAVIOR_KEY = "display_behavior";
    private static final String REPORTING_ENABLED_KEY = "reporting_enabled";
    private static final String RENDERED_LOCALE_KEY = "rendered_locale";
    private static final String RENDERED_LOCALE_LANGUAGE_KEY = "language";
    private static final String RENDERED_LOCALE_COUNTRY_KEY = "country";

    @StringDef({ SOURCE_LEGACY_PUSH, SOURCE_REMOTE_DATA, SOURCE_APP_DEFINED })
    @Retention(RetentionPolicy.SOURCE)
    @interface Source {}

    /**
     * In-app message was generated from a push in the legacy in-app message manager.
     */
    static final String SOURCE_LEGACY_PUSH = "legacy-push";

    /**
     * In-app message from the remote-data service.
     */
    static final String SOURCE_REMOTE_DATA = "remote-data";

    /**
     * In-app message created programmatically by the application.
     */
    static final String SOURCE_APP_DEFINED = "app-defined";

    @StringDef({ TYPE_BANNER, TYPE_CUSTOM, TYPE_FULLSCREEN, TYPE_MODAL, TYPE_HTML })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayType {}

    /**
     * Banner in-app message.
     */
    @NonNull
    public static final String TYPE_BANNER = "banner";

    /**
     * Custom in-app message.
     */
    @NonNull
    public static final String TYPE_CUSTOM = "custom";

    /**
     * Fullscreen in-app message.
     */
    @NonNull
    public static final String TYPE_FULLSCREEN = "fullscreen";

    /**
     * Modal in-app message.
     */
    @NonNull
    public static final String TYPE_MODAL = "modal";

    /**
     * HTML in-app message.
     */
    @NonNull
    public static final String TYPE_HTML = "html";

    @StringDef({ DISPLAY_BEHAVIOR_DEFAULT, DISPLAY_BEHAVIOR_IMMEDIATE })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayBehavior {}

    /**
     * The in-app message default display behavior. Usually displayed using the default coordinator
     * that allows defining display interval.
     */
    @NonNull
    public static final String DISPLAY_BEHAVIOR_DEFAULT = "default";

    /**
     * The in-app message should be displayed ASAP.
     */
    @NonNull
    public static final String DISPLAY_BEHAVIOR_IMMEDIATE = "immediate";

    @DisplayType
    private final String type;
    private final JsonMap extras;
    private final String id;
    private final JsonSerializable content;
    private final Audience audience;
    private final Map<String, JsonValue> actions;
    private final JsonValue campaigns;

    @DisplayBehavior
    private final String displayBehavior;
    private final boolean isReportingEnabled;

    @Source
    private final String source;

    private final Map<String, JsonValue> renderedLocale;

    /**
     * Default constructor.
     *
     * @param builder An InAppMessage builder instance.
     */
    private InAppMessage(@NonNull Builder builder) {
        this.type = builder.type;
        this.content = builder.content;
        this.id = builder.id;
        this.extras = builder.extras == null ? JsonMap.EMPTY_MAP : builder.extras;
        this.audience = builder.audience;
        this.actions = builder.actions;
        this.source = builder.source;
        this.campaigns = builder.campaigns;
        this.displayBehavior = builder.displayBehavior;
        this.isReportingEnabled = builder.isReportingEnabled;
        this.renderedLocale = builder.renderedLocale;
    }

    /**
     * Gets the in-app message type.
     *
     * @return The in-app message type.
     */
    @DisplayType
    @NonNull
    public String getType() {
        return type;
    }

    /**
     * Returns the display content.
     * <p>
     * The return type depends on the in-app message type:
     * {@link #TYPE_BANNER}: a {@link com.urbanairship.iam.banner.BannerDisplayContent},
     * {@link #TYPE_CUSTOM}: a {@link com.urbanairship.iam.custom.CustomDisplayContent},
     * {@link #TYPE_FULLSCREEN}: a {@link com.urbanairship.iam.fullscreen.FullScreenDisplayContent},
     * {@link #TYPE_HTML}: a {@link com.urbanairship.iam.html.HtmlDisplayContent}
     *
     * @return The display content.
     */
    @Nullable
    public <T extends DisplayContent> T getDisplayContent() {
        if (content == null) {
            return null;
        }
        try {
            //noinspection unchecked
            return (T) content;
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Gets the message ID.
     *
     * @return The message ID.
     */
    @NonNull
    public String getId() {
        return id;
    }

    /**
     * Gets the extras.
     *
     * @return The extras.
     */
    @NonNull
    public JsonMap getExtras() {
        return extras;
    }

    /**
     * Gets the audience.
     *
     * @return The audience.
     */
    @Nullable
    public Audience getAudience() {
        return audience;
    }

    /**
     * Gets the actions.
     *
     * @return The actions.
     */
    @NonNull
    public Map<String, JsonValue> getActions() {
        return actions;
    }

    /**
     * The in-app message source.
     *
     * @return The in-app message source.
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    @Source
    @NonNull
    String getSource() {
        return source;
    }

    /**
     * The in-app message campaigns info.
     *
     * @return The in-app message campaigns info.
     * @hide
     */
    @Nullable
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    JsonValue getCampaigns() {
        return campaigns;
    }

    @Nullable
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    Map<String, JsonValue> getRenderedLocale() {
        return renderedLocale;
    }

    /**
     * Gets the display behavior.
     *
     * @return The in-app message display behavior.
     */
    @DisplayBehavior
    @NonNull
    public String getDisplayBehavior() {
        return displayBehavior;
    }

    /**
     * Checks if reporting is enabled for the in-app message.
     *
     * @return {@code true} if reporting is enabled, otherwise {@code false}.
     */
    public boolean isReportingEnabled() {
        return isReportingEnabled;
    }

    @NonNull
    @Override
    public JsonValue toJsonValue() {
        return JsonMap.newBuilder()
                      .put(MESSAGE_ID_KEY, id)
                      .putOpt(EXTRA_KEY, extras)
                      .putOpt(DISPLAY_CONTENT_KEY, content)
                      .putOpt(DISPLAY_TYPE_KEY, type)
                      .putOpt(AUDIENCE_KEY, audience)
                      .putOpt(ACTIONS_KEY, actions)
                      .putOpt(SOURCE_KEY, source)
                      .putOpt(CAMPAIGNS_KEY, campaigns)
                      .putOpt(DISPLAY_BEHAVIOR_KEY, displayBehavior)
                      .putOpt(REPORTING_ENABLED_KEY, isReportingEnabled)
                      .putOpt(RENDERED_LOCALE_KEY, renderedLocale)
                      .build().toJsonValue();
    }

    /**
     * Parses a json value.
     *
     * @param jsonValue The json value.
     * @param defaultSource The default source if its not set in the JSON.
     * @return The parsed InAppMessage.
     * @throws JsonException If the json is invalid.
     * @hide
     */
    @NonNull
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    static InAppMessage fromJson(@NonNull JsonValue jsonValue, @Nullable @Source String defaultSource) throws JsonException {
        String type = jsonValue.optMap().opt(DISPLAY_TYPE_KEY).optString();
        JsonValue content = jsonValue.optMap().opt(DISPLAY_CONTENT_KEY);

        String messageId = jsonValue.optMap().opt(MESSAGE_ID_KEY).getString();
        if (messageId == null || messageId.length() > MAX_ID_LENGTH) {
            throw new JsonException("Invalid message ID. Must be nonnull and less than or equal to " + MAX_ID_LENGTH + " characters.");
        }

        InAppMessage.Builder builder = InAppMessage.newBuilder()
                                                   .setId(messageId)
                                                   .setExtras(jsonValue.optMap().opt(EXTRA_KEY).optMap())
                                                   .setDisplayContent(type, content);

        // Source
        @Source String source = jsonValue.optMap().opt(SOURCE_KEY).getString();
        if (source != null) {
            builder.setSource(source);
        } else if (defaultSource != null) {
            builder.setSource(defaultSource);
        }

        // Actions
        if (jsonValue.optMap().containsKey(ACTIONS_KEY)) {
            JsonMap jsonMap = jsonValue.optMap().opt(ACTIONS_KEY).getMap();
            if (jsonMap == null) {
                throw new JsonException("Actions must be a JSON object: " + jsonValue.optMap().opt(ACTIONS_KEY));
            }

            builder.setActions(jsonMap.getMap());
        }

        // Audience
        if (jsonValue.optMap().containsKey(AUDIENCE_KEY)) {
            builder.setAudience(Audience.fromJson(jsonValue.optMap().opt(AUDIENCE_KEY)));
        }

        // Campaigns
        if (jsonValue.optMap().containsKey(CAMPAIGNS_KEY)) {
            builder.setCampaigns(jsonValue.optMap().opt(CAMPAIGNS_KEY));
        }

        // Behavior
        if (jsonValue.optMap().containsKey(DISPLAY_BEHAVIOR_KEY)) {
            switch (jsonValue.optMap().opt(DISPLAY_BEHAVIOR_KEY).optString()) {
                case DISPLAY_BEHAVIOR_DEFAULT:
                    builder.setDisplayBehavior(DISPLAY_BEHAVIOR_DEFAULT);
                    break;
                case DISPLAY_BEHAVIOR_IMMEDIATE:
                    builder.setDisplayBehavior(DISPLAY_BEHAVIOR_IMMEDIATE);
                    break;
                default:
                    throw new JsonException("Unexpected display behavior: " + jsonValue.optMap().get(DISPLAY_BEHAVIOR_IMMEDIATE));
            }
        }

        // Reporting
        if (jsonValue.optMap().containsKey(REPORTING_ENABLED_KEY)) {
            builder.setReportingEnabled(jsonValue.optMap().opt(REPORTING_ENABLED_KEY).getBoolean(true));
        }

        if (jsonValue.optMap().containsKey(RENDERED_LOCALE_KEY)) {
            JsonMap jsonMap = jsonValue.optMap().opt(RENDERED_LOCALE_KEY).getMap();

            if (jsonMap == null) {
                throw new JsonException("Rendered locale must be a JSON object: " + jsonValue.optMap().opt(RENDERED_LOCALE_KEY));
            }

            if (!jsonMap.containsKey(RENDERED_LOCALE_LANGUAGE_KEY) && !jsonMap.containsKey(RENDERED_LOCALE_COUNTRY_KEY)) {
                throw new JsonException("Rendered locale must contain one of " +
                        "\"" + RENDERED_LOCALE_LANGUAGE_KEY + "\" " +
                        "or \"" + RENDERED_LOCALE_COUNTRY_KEY + "\" fields :" + jsonMap);
            }

            JsonValue languageValue = jsonMap.opt(RENDERED_LOCALE_LANGUAGE_KEY);
            if (!languageValue.isNull() && !languageValue.isString()) {
                throw new JsonException("Language must be a string: " + languageValue);
            }

            JsonValue countryValue = jsonMap.opt(RENDERED_LOCALE_COUNTRY_KEY);
            if (!countryValue.isNull() && !countryValue.isString()) {
                throw new JsonException("Country must be a string: " + countryValue);
            }

            builder.setRenderedLocale(jsonMap.getMap());
        }

        try {
            return builder.build();
        } catch (IllegalArgumentException e) {
            throw new JsonException("Invalid InAppMessage json.", e);
        }
    }

    /**
     * Parses a json value.
     *
     * @param jsonValue The json value.
     * @return The parsed InAppMessage.
     * @throws JsonException If the json is invalid.
     */
    @NonNull
    static InAppMessage fromJson(@NonNull JsonValue jsonValue) throws JsonException {
        return fromJson(jsonValue, null);
    }

    /**
     * Creates a new builder.
     *
     * @return A new in-app message builder.
     */
    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Creates a new builder from an existing message.
     *
     * @param message The in-app message.
     * @return A new in-app message builder.
     */
    @NonNull
    public static Builder newBuilder(@NonNull InAppMessage message) {
        return new Builder(message);
    }

    /**
     * Creator for parcelable interface.
     *
     * @hide
     */
    @NonNull
    public static final Creator<InAppMessage> CREATOR = new Creator<InAppMessage>() {

        @Nullable
        @Override
        public InAppMessage createFromParcel(@NonNull Parcel in) {
            String payload = in.readString();

            try {
                return fromJson(JsonValue.parseString(payload));
            } catch (JsonException e) {
                Logger.error("InAppMessage - Invalid parcel: %s", e);
                return null;
            }
        }

        @NonNull
        @Override
        public InAppMessage[] newArray(int size) {
            return new InAppMessage[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(toJsonValue().toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return toJsonValue().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InAppMessage message = (InAppMessage) o;

        if (!displayBehavior.equals(message.displayBehavior)) {
            return false;
        }

        if (isReportingEnabled != message.isReportingEnabled) {
            return false;
        }

        if (!type.equals(message.type)) {
            return false;
        }

        if (!extras.equals(message.extras)) {
            return false;
        }

        if (!id.equals(message.id)) {
            return false;
        }

        if (!content.equals(message.content)) {
            return false;
        }

        if (audience != null ? !audience.equals(message.audience) : message.audience != null) {
            return false;
        }

        if (!actions.equals(message.actions)) {
            return false;
        }

        if (campaigns != null ? !campaigns.equals(message.campaigns) : message.campaigns != null) {
            return false;
        }

        if (renderedLocale != null ? !renderedLocale.equals(message.renderedLocale) : message.renderedLocale != null) {
            return false;
        }

        return source.equals(message.source);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + extras.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + (audience != null ? audience.hashCode() : 0);
        result = 31 * result + actions.hashCode();
        result = 31 * result + (renderedLocale != null ? renderedLocale.hashCode() : 0);
        result = 31 * result + (campaigns != null ? campaigns.hashCode() : 0);
        result = 31 * result + displayBehavior.hashCode();
        result = 31 * result + (isReportingEnabled ? 1 : 0);
        result = 31 * result + source.hashCode();
        return result;
    }

    /**
     * In-app message builder.
     */
    public static class Builder {

        @DisplayType
        private String type;
        private JsonMap extras;
        private String id;
        private JsonSerializable content;
        private Audience audience;
        private Map<String, JsonValue> actions = new HashMap<>();

        @Source
        private String source = SOURCE_APP_DEFINED;
        private JsonValue campaigns;

        @DisplayBehavior
        private String displayBehavior = DISPLAY_BEHAVIOR_DEFAULT;
        private boolean isReportingEnabled = true;

        private Map<String, JsonValue> renderedLocale;

        private Builder() {
        }

        public Builder(@NonNull InAppMessage message) {
            this.type = message.type;
            this.content = message.content;
            this.id = message.id;
            this.extras = message.extras;
            this.audience = message.audience;
            this.actions = message.actions;
            this.source = message.source;
            this.campaigns = message.campaigns;
            this.displayBehavior = message.displayBehavior;
            this.isReportingEnabled = message.isReportingEnabled;
            this.renderedLocale = message.renderedLocale;
        }

        /**
         * Sets the display content to the parsed type.
         *
         * @param type The type.
         * @param content The display content as a json value.
         * @return The builder object.
         */
        @NonNull
        private Builder setDisplayContent(@NonNull String type, @NonNull JsonValue content) throws JsonException {
            switch (type) {
                case TYPE_BANNER:
                    this.setDisplayContent(BannerDisplayContent.fromJson(content));
                    break;

                case TYPE_CUSTOM:
                    this.setDisplayContent(CustomDisplayContent.fromJson(content));
                    break;

                case TYPE_FULLSCREEN:
                    this.setDisplayContent(FullScreenDisplayContent.fromJson(content));
                    break;

                case TYPE_MODAL:
                    this.setDisplayContent(ModalDisplayContent.fromJson(content));
                    break;

                case TYPE_HTML:
                    this.setDisplayContent(HtmlDisplayContent.fromJson(content));
                    break;
            }

            return this;
        }

        /**
         * Sets the modal display content and type.
         *
         * @param displayContent The modal display content.
         * @return The builder.
         */
        @NonNull
        public Builder setDisplayContent(@NonNull ModalDisplayContent displayContent) {
            this.type = TYPE_MODAL;
            this.content = displayContent;
            return this;
        }

        /**
         * Sets the full screen display content and type.
         *
         * @param displayContent The full screen display content.
         * @return The builder.
         */
        @NonNull
        public Builder setDisplayContent(@NonNull FullScreenDisplayContent displayContent) {
            this.type = TYPE_FULLSCREEN;
            this.content = displayContent;
            return this;
        }

        /**
         * Sets the banner display content and type.
         *
         * @param displayContent The banner display content.
         * @return The builder.
         */
        @NonNull
        public Builder setDisplayContent(@NonNull BannerDisplayContent displayContent) {
            this.type = TYPE_BANNER;
            this.content = displayContent;
            return this;
        }

        /**
         * Sets the html display content and type.
         *
         * @param displayContent The html display content.
         * @return The builder.
         */
        @NonNull
        public Builder setDisplayContent(@NonNull HtmlDisplayContent displayContent) {
            this.type = TYPE_HTML;
            this.content = displayContent;
            return this;
        }

        /**
         * Sets the display behavior.
         *
         * @param displayBehavior The display behavior.
         * @return The builder.
         */
        @NonNull
        public Builder setDisplayBehavior(@NonNull @DisplayBehavior String displayBehavior) {
            this.displayBehavior = displayBehavior;
            return this;
        }

        /**
         * Sets the source of the in-app message.
         *
         * @param source The in-app message source.
         * @return The builder.
         * @hide
         */
        @NonNull
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        Builder setSource(@Nullable @Source String source) {
            this.source = source;
            return this;
        }

        /**
         * Sets the campaigns info for the in-app message.
         *
         * @param campaigns The in-app message campaigns info.
         * @return The builder.
         * @hide
         */
        @NonNull
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        Builder setCampaigns(@Nullable JsonValue campaigns) {
            this.campaigns = campaigns;
            return this;
        }

        /**
         * Sets the custom display content and type.
         *
         * @param displayContent The custom display content.
         * @return The builder.
         */
        @NonNull
        public Builder setDisplayContent(@NonNull CustomDisplayContent displayContent) {
            this.type = TYPE_CUSTOM;
            this.content = displayContent;
            return this;
        }

        /**
         * Sets the extras.
         *
         * @param extras An extra json map.
         * @return The builder.
         */
        @NonNull
        public Builder setExtras(@Nullable JsonMap extras) {
            this.extras = extras;
            return this;
        }

        /**
         * Sets the in-app message ID.
         *
         * @param id The message ID.
         * @return The builder.
         */
        @NonNull
        public Builder setId(@NonNull @Size(min = 1, max = MAX_ID_LENGTH) String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the audience.
         *
         * @param audience The audience.
         * @return The builder.
         */
        @NonNull
        public Builder setAudience(@Nullable Audience audience) {
            this.audience = audience;
            return this;
        }

        /**
         * Sets the actions to run when the in-app message is displayed.
         *
         * @param actions The action map.
         * @return The builder.
         */
        @NonNull
        public Builder setActions(@Nullable Map<String, JsonValue> actions) {
            this.actions.clear();

            if (actions != null) {
                this.actions.putAll(actions);
            }

            return this;
        }

        /**
         * Adds an action to run when the in-app message is displayed.
         *
         * @param actionName The action name.
         * @param actionValue The action value.
         * @return The builder.
         */
        @NonNull
        public Builder addAction(@NonNull String actionName, @NonNull JsonValue actionValue) {
            this.actions.put(actionName, actionValue);
            return this;
        }

        /**
         * Enables/disables generating display and resolution events for the in-app message.
         *
         * @param isReportingEnabled {@code true} to generate reporting events, otherwise {@code false}.
         * @return The builder.
         */
        public Builder setReportingEnabled(boolean isReportingEnabled) {
            this.isReportingEnabled = isReportingEnabled;
            return this;
        }

        /**
         * Sets the rendered locale info.
         *
         * @param renderedLocale The rendered locale dictionary
         * @return The builder.
         * @hide
         */
        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        public Builder setRenderedLocale(@Nullable Map<String, JsonValue> renderedLocale) {
            this.renderedLocale = renderedLocale;
            return this;
        }

        /**
         * Builds the in-app message.
         *
         * @return The built in-app message.
         * @throws IllegalArgumentException If the ID is missing, ID length is greater than the {@link #MAX_ID_LENGTH},
         * or if the content is missing.
         */
        @NonNull
        public InAppMessage build() {
            Checks.checkArgument(!UAStringUtil.isEmpty(id), "Missing ID.");
            Checks.checkArgument(id.length() <= MAX_ID_LENGTH, "Id exceeds max ID length: " + MAX_ID_LENGTH);
            Checks.checkNotNull(type, "Missing type.");
            Checks.checkNotNull(content, "Missing content.");
            return new InAppMessage(this);
        }

    }

}
