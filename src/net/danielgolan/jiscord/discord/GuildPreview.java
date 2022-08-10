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

public sealed interface GuildPreview extends DiscordObject permits Guild, GuildPreview.Model {

    String getName();

    @Nullable String getIcon(); //TODO: add images support

    @Nullable String getSplash();

    @Nullable String getDiscoverySplash();

    Object[] getEmojis(); //TODO: add Emoji support

    String[] getFeatures();

    @Nullable String getDescription();

    @Nullable Object[] getStickers(); //TODO: Add Sticker Support

    sealed class Model extends DiscordObject.Model implements GuildPreview permits Guild.Model {
        protected final String name;
        protected final @Nullable String icon;
        protected final @Nullable String splash;
        @SerializedName("discovery_splash")
        protected final @Nullable String discoverySplash;
        protected final Object[] emojis;
        protected final String[] features;
        protected final @Nullable String description;
        protected final @Nullable Object[] stickers;

        public Model(Snowflake id, String name, @Nullable String icon, @Nullable String splash, @Nullable String discoverySplash, Object[] emojis, String[] features, @Nullable String description, @Nullable Object[] stickers) {
            super(id);
            this.name = name;
            this.icon = icon;
            this.splash = splash;
            this.discoverySplash = discoverySplash;
            this.emojis = emojis;
            this.features = features;
            this.description = description;
            this.stickers = stickers;
        }

        @Override
        public String getName() {
            return name;
        }

        @Nullable
        @Override
        public String getIcon() {
            return icon;
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

    static Guild getGuild(Snowflake id) throws IOException, InterruptedException {
        return getGuild(id, false);
    }

    static Guild getGuild(Snowflake id, boolean withCounts) throws IOException, InterruptedException {
        return GSON.fromJson(getRawGuild(id, withCounts),Guild.Model.class);
    }

    static String getRawGuild(Snowflake id, boolean withCounts) throws IOException, InterruptedException {
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
}
