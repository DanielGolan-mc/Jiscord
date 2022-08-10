package net.danielgolan.jiscord;

import net.danielgolan.jiscord.discord.DiscordObject;
import net.danielgolan.jiscord.util.Snowflake;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Jiscord {
    //todo: figure out a way to also filter them by event. Maybe just using .merge? We'll see.
    /**
     * A cache of all snowflakes requested before.
     * Cleared every 4 hours by default.
     */
    public static final Map<Snowflake, DiscordObject> snowflakes;
    /**
     * The time between cache clears, in milliseconds.
     */
    private static int cacheClearFrequency = 14400000;
    private static final Thread cleaner;

    private final String token;

    public Jiscord(String token) {
        this.token = token;
    }

    static {
        snowflakes = Collections.synchronizedMap(new HashMap<>());
        cleaner = new Thread(() -> {
            snowflakes.clear();
            try {
                Thread.sleep(cacheClearFrequency);
            } catch (InterruptedException e) {
                System.err.println("Jiscord cache cleaner was interrupted; " + e.getMessage());
            }
        }, "jiscord_cleaner");
    }

    public static int getCacheClearFrequency() {
        return cacheClearFrequency;
    }

    public static void setCacheClearFrequency(int cacheClearFrequency) {
        Jiscord.cacheClearFrequency = cacheClearFrequency;
    }
}
