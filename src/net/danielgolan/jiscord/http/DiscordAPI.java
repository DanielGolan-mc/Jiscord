package net.danielgolan.jiscord.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.HashMap;

public final class DiscordAPI {
    public final static short API_VERSION = 10;
    public final static HttpClient CLIENT;
    public final static ObjectMapper OBJECT_MAPPER;
    public final static Gson GSON;

    private DiscordAPI() {}

    public enum Endpoint {
        GET_GUILD("/guilds/%s", RequestType.GET);
        public final String address;
        public final RequestType type;

        Endpoint(@NotNull String address, RequestType type) {

            if (!address.startsWith("/")) throw new IllegalArgumentException("Address must start with '/'");

            this.address = address;
            this.type = type;
        }

        @Contract(pure = true)
        public @NotNull URI makeURI(Object... args) throws URISyntaxException {
            return makeURI(null, args);
        }

        /**
         * @param input arguments, formatted {@code "...?key=value"}
         * @param args {@link java.util.Formatter} arguments
         * @return URI
         * @throws URISyntaxException when URI syntax is wrong
         */
        @Contract(pure = true)
        public @NotNull URI makeURI(HashMap<String, Object> input, Object... args) throws URISyntaxException {
            return new URI(("https://discord.com/api/v" + API_VERSION + this.address).formatted(args));
        }
    }
    public enum RequestType {
            GET, POST
        }

        static {
            CLIENT = HttpClient.newHttpClient();
            OBJECT_MAPPER = new ObjectMapper();
            GSON = new GsonBuilder()
                    .serializeNulls()
                    .setPrettyPrinting()
                    .create();
        }
    }
