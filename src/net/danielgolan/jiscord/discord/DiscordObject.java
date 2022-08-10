package net.danielgolan.jiscord.discord;

import net.danielgolan.jiscord.util.Snowflake;

public sealed interface DiscordObject permits Channel, DiscordObject.Model, GuildPreview, User {
    Snowflake getID();

    sealed abstract class Model implements DiscordObject permits GuildPreview.Model {
        private final Snowflake id;

        protected Model(Snowflake id) {
            this.id = id;
        }

        public Snowflake getID() {
            return id;
        }
    }
}
