package net.danielgolan.jiscord.discord;

import com.google.gson.annotations.SerializedName;
import net.danielgolan.jiscord.http.DiscordAPI;
import net.danielgolan.jiscord.util.Snowflake;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import static net.danielgolan.jiscord.http.DiscordAPI.GSON;

public non-sealed interface Guild extends GuildPreview {

    Snowflake getOwnerID(); //TODO: add user support

    @Nullable Snowflake getAFKChannelID(); //TODO: add channel support

    int getAFKTimeout();

    boolean isWidgetEnabled();

    @Nullable Snowflake getWidgetChannelID();

    int getVerificationLevel();

    int getDefaultMessageNotificationLevel();

    int getExplicitContentFilterLevel();

    Object[] getRoles(); //TODO: add role support

    //MF Authentication?
    int getMFALevel();

    @Nullable Snowflake getApplicationID();

    @Nullable Snowflake getSystemChannelID();

    int getSystemChannelFlags();

    @Nullable Snowflake getRulesChannelID();

    int getMaxMemberCount();

    @Nullable String getVanityURLCode();

    @Nullable String getBanner();

    int getPremiumTier();

    int getPremiumSubscriptionCount();

    String getPreferredLocale();

    @Nullable Snowflake getPublicUpdatesChannelID();

    @Nullable Object getWelcomeScreen(); //TODO: Add WelcomeScreen Support

    int getNSFWLevel();

    boolean isPremiumProgressBarEnabled();

    static Guild get(Snowflake id) throws IOException, InterruptedException {
        return get(id, false);
    }

    static Guild get(Snowflake id, boolean withCounts) throws IOException, InterruptedException {
        return GSON.fromJson(getRaw(id, withCounts),Guild.Model.class);
    }

    static String getRaw(Snowflake id, boolean withCounts) throws IOException, InterruptedException {
        HttpRequest request;

        // handle URISyntaxException
        try {
            HashMap<String, Object> input = new HashMap<>();
            input.put("with_counts", withCounts);

            request = HttpRequest.newBuilder().uri(DiscordAPI.Endpoint.GET_GUILD.makeURI(input, id)).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Jiscord uses an illegal URI address" + e);
        }

        HttpResponse<String> response = DiscordAPI.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    final class Model extends GuildPreview.Model implements Guild {

        @SerializedName("owner_id")
        private final Snowflake ownerID;
        @SerializedName("afk_channel_id")
        private final @Nullable Snowflake afkChannelID;
        @SerializedName("afk_timeout")
        private final int afkTimeout;
        @SerializedName("widget_enabled")
        private final boolean isWidgetEnabled;
        @SerializedName("widget_channel_id")
        private final @Nullable Snowflake widgetChannelID;
        @SerializedName("verification_level")
        private final int verificationLevel;
        @SerializedName("default_message_notifications")
        private final int defaultMessageNotificationLevel;
        @SerializedName("explicit_content_filter")
        private final int explicitContentFilterLevel;
        private final Object[] roles;
        @SerializedName("mfa_level")
        private final int mfaLevel;
        @SerializedName("application_id")
        private final @Nullable Snowflake applicationID;
        @SerializedName("system_channel_id")
        private final @Nullable Snowflake systemChannelID;
        @SerializedName("system_channel_flags")
        private final int systemChannelFlags;
        @SerializedName("rules_channel_id")
        private final @Nullable Snowflake rulesChannelID;
        @SerializedName("max_members")
        private final int maxMemberCount;
        @SerializedName("vanity_url_code")
        private final @Nullable String vanityURLCode;
        private final @Nullable String banner;
        @SerializedName("premium_tier")
        private final int premiumTier;
        @SerializedName("premium_subscription_count")
        private final int premiumSubscriptionCount;
        @SerializedName("preferred_locale")
        private final String preferredLocale;
        @SerializedName("public_updates_channel_id")
        private final @Nullable Snowflake publicUpdatesChannelID;
        @SerializedName("welcome_screen")
        private final @Nullable Object welcomeScreen;
        @SerializedName("nsfw_level")
        private final int nsfwLevel;
        @SerializedName("premium_progress_bar_enabled")
        private final boolean isPremiumProgressBarEnabled;

        private Model(Snowflake id, String name, @Nullable String icon, @Nullable String splash, @Nullable String discoverySplash, Snowflake ownerID, @Nullable Snowflake afkChannelID, int afkTimeout, boolean isWidgetEnabled, @Nullable Snowflake widgetChannelID, int verificationLevel, int defaultMessageNotificationLevel, int explicitContentFilterLevel, Object[] roles, Object[] emojis, String[] features, int mfaLevel, @Nullable Snowflake applicationID, @Nullable Snowflake systemChannelID, int systemChannelFlags, @Nullable Snowflake rulesChannelID, int maxMemberCount, @Nullable String vanityURLCode, @Nullable String description, @Nullable String banner, int premiumTier, int premiumSubscriptionCount, String preferredLocale, @Nullable Snowflake publicUpdatesChannelID, @Nullable Object welcomeScreen, int nsfwLevel, @Nullable Object[] stickers, boolean isPremiumProgressBarEnabled) {
            super(id, name, icon ,splash, discoverySplash, emojis, features, description, stickers);
            this.ownerID = ownerID;
            this.afkChannelID = afkChannelID;
            this.afkTimeout = afkTimeout;
            this.isWidgetEnabled = isWidgetEnabled;
            this.widgetChannelID = widgetChannelID;
            this.verificationLevel = verificationLevel;
            this.defaultMessageNotificationLevel = defaultMessageNotificationLevel;
            this.explicitContentFilterLevel = explicitContentFilterLevel;
            this.roles = roles;
            this.mfaLevel = mfaLevel;
            this.applicationID = applicationID;
            this.systemChannelID = systemChannelID;
            this.systemChannelFlags = systemChannelFlags;
            this.rulesChannelID = rulesChannelID;
            this.maxMemberCount = maxMemberCount;
            this.vanityURLCode = vanityURLCode;
            this.banner = banner;
            this.premiumTier = premiumTier;
            this.premiumSubscriptionCount = premiumSubscriptionCount;
            this.preferredLocale = preferredLocale;
            this.publicUpdatesChannelID = publicUpdatesChannelID;
            this.welcomeScreen = welcomeScreen;
            this.nsfwLevel = nsfwLevel;
            this.isPremiumProgressBarEnabled = isPremiumProgressBarEnabled;
        }


        @Override
        public Snowflake getOwnerID() {
            return ownerID;
        }

        @Override
        public Snowflake getAFKChannelID() {
            return afkChannelID;
        }

        @Override
        public int getAFKTimeout() {
            return afkTimeout;
        }

        @Override
        public boolean isWidgetEnabled() {
            return isWidgetEnabled;
        }

        @Nullable
        @Override
        public Snowflake getWidgetChannelID() {
            return widgetChannelID;
        }

        @Override
        public int getVerificationLevel() {
            return verificationLevel;
        }

        @Override
        public int getDefaultMessageNotificationLevel() {
            return defaultMessageNotificationLevel;
        }

        @Override
        public int getExplicitContentFilterLevel() {
            return explicitContentFilterLevel;
        }

        @Override
        public Object[] getRoles() {
            return roles;
        }

        @Override
        public int getMFALevel() {
            return mfaLevel;
        }

        @Nullable
        @Override
        public Snowflake getApplicationID() {
            return applicationID;
        }

        @Nullable
        @Override
        public Snowflake getSystemChannelID() {
            return systemChannelID;
        }

        @Override
        public int getSystemChannelFlags() {
            return systemChannelFlags;
        }

        @Nullable
        @Override
        public Snowflake getRulesChannelID() {
            return rulesChannelID;
        }

        @Override
        public int getMaxMemberCount() {
            return maxMemberCount;
        }

        @Nullable
        @Override
        public String getVanityURLCode() {
            return vanityURLCode;
        }

        @Nullable
        @Override
        public String getBanner() {
            return banner;
        }

        @Override
        public int getPremiumTier() {
            return premiumTier;
        }

        @Override
        public int getPremiumSubscriptionCount() {
            return premiumSubscriptionCount;
        }

        @Override
        public String getPreferredLocale() {
            return preferredLocale;
        }

        @Nullable
        @Override
        public Snowflake getPublicUpdatesChannelID() {
            return publicUpdatesChannelID;
        }

        @Nullable
        @Override
        public Object getWelcomeScreen() {
            return welcomeScreen;
        }

        @Override
        public int getNSFWLevel() {
            return nsfwLevel;
        }

        @Override
        public boolean isPremiumProgressBarEnabled() {
            return isPremiumProgressBarEnabled;
        }
    }

}
