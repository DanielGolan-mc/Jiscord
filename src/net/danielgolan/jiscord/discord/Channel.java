package net.danielgolan.jiscord.discord;

import net.danielgolan.jiscord.util.Snowflake;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public non-sealed interface Channel extends DiscordObject {
    int getType();
    @Nullable Snowflake getGuildID();
    default @Nullable Guild getGuild() throws IOException, InterruptedException {
        return Guild.get(getGuildID());
    }
}
