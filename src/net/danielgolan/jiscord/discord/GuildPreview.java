package net.danielgolan.jiscord.discord;

import com.google.gson.annotations.SerializedName;
import net.danielgolan.jiscord.http.DiscordAPI;
import net.danielgolan.jiscord.util.Snowflake;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static net.danielgolan.jiscord.http.DiscordAPI.GSON;

public sealed interface GuildPreview extends DiscordObject.Named, DiscordObject.Iconable permits Guild, GuildPreview.Model {
    @Nullable String getSplash();

    @Nullable String getDiscoverySplash();

    Object[] getEmojis(); //TODO: add Emoji support

    String[] getFeatures();

    @Nullable String getDescription();

    @Nullable Object[] getStickers(); //TODO: Add Sticker Support

    sealed class Model extends DiscordObject.NamedAndIconableModel implements GuildPreview permits Guild.Model {
        protected final @Nullable String splash;
        @SerializedName("discovery_splash")
        protected final @Nullable String discoverySplash;
        protected final Object[] emojis;
        protected final String[] features;
        protected final @Nullable String description;
        protected final @Nullable Object[] stickers;

        public Model(Snowflake id, String name, @Nullable String icon, @Nullable String splash, @Nullable String discoverySplash, Object[] emojis, String[] features, @Nullable String description, @Nullable Object[] stickers) {
            super(id, name, icon);
            this.splash = splash;
            this.discoverySplash = discoverySplash;
            this.emojis = emojis;
            this.features = features;
            this.description = description;
            this.stickers = stickers;
        }

        @Nullable
        @Override
        public String getSplash() {
            return splash;
        }

        @Nullable
        @Override
        public String getDiscoverySplash() {
            return discoverySplash;
        }

        @Override
        public Object[] getEmojis() {
            return emojis;
        }

        @Override
        public String[] getFeatures() {
            return features;
        }

        @Nullable
        @Override
        public String getDescription() {
            return description;
        }

        @Nullable
        @Override
        public Object[] getStickers() {
            return stickers;
        }
    }

    static GuildPreview get(Snowflake id) throws IOException, InterruptedException {
        return GSON.fromJson(getRaw(id),GuildPreview.Model.class);
    }

    static String getRaw(Snowflake id) throws IOException, InterruptedException {
        HttpRequest request;

        // handle URISyntaxException
        try {
            request = HttpRequest.newBuilder().uri(DiscordAPI.Endpoint.GET_GUILD_PREVIEW.makeURI(id)).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Jiscord uses an illegal URI address" + e);
        }

        HttpResponse<String> response = DiscordAPI.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
