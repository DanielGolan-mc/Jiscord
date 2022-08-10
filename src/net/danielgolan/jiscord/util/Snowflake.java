package net.danielgolan.jiscord.util;

import org.jetbrains.annotations.Contract;

public record Snowflake(String value) {
    /**
     * @return snowflake creation time, in unix timestamp (ms)
     * @see Snowflake#discordEpochTimestamp()
     */
    public long timestamp() {
        return discordEpochTimestamp() + 1420070400000L;
    }

    /**
     * @return creation time of the snowflake, in milliseconds since Discord Epoch, the first second of 2015 or 1420070400000 (unix timestamp - ms)
     * @see Snowflake#timestamp()
     */
    public long discordEpochTimestamp() {
        return toLong() >> 22;
    }

    public long internalWorkerID() {
        return (toLong() & 0x3E0000) >> 17;
    }

    public long internalProcessID() {
        return (toLong() & 0x1F000) >> 12;
    }

    /**
     * @return For every ID that is generated on that {@linkplain #internalProcessID() process}, this number is incremented
     */
    public long increment() {
        return toLong() & 0xFFF;
    }

    /**
     * @return snowflake as {@link Long}
     */
    public long toLong() {
        return Long.parseLong(value);
    }

    @Contract(pure = true)
    @Override
    public String toString() {
        return value;
    }
}
