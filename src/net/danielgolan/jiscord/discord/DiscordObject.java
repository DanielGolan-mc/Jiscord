package net.danielgolan.jiscord.discord;

import net.danielgolan.jiscord.util.Snowflake;
import org.jetbrains.annotations.Nullable;

public sealed interface DiscordObject permits Channel, DiscordObject.Iconable, DiscordObject.Model, DiscordObject.Named, User {
    Snowflake getID();

    sealed abstract class Model implements DiscordObject permits Iconable.Model, Named.Model, NamedAndIconableModel {
        private final Snowflake id;

        protected Model(Snowflake id) {
            this.id = id;
        }

        public Snowflake getID() {
            return id;
        }
    }

    sealed interface Named extends DiscordObject permits Channel.DirectChannel.Group, Channel.Integrated, Named.Model, NamedAndIconableModel, GuildPreview {
        String getName();

        non-sealed abstract class Model extends DiscordObject.Model implements DiscordObject.Named {
            private final String name;
            protected Model(Snowflake id, String name) {
                super(id);
                this.name = name;
            }

            public String getIcon() {
                return name;
            }
        }
    }

    //TODO: Implement Icon Support
    sealed interface Iconable extends DiscordObject permits Channel.DirectChannel.Group, Iconable.Model, NamedAndIconableModel, GuildPreview {
        @Nullable String getIcon();

        non-sealed abstract class Model extends DiscordObject.Model implements DiscordObject.Iconable {
            private final @Nullable String icon;
            protected Model(Snowflake id, @Nullable String icon) {
                super(id);
                this.icon = icon;
            }

            @Override
            public @Nullable String getIcon() {
                return icon;
            }
        }
    }

    sealed abstract class NamedAndIconableModel extends DiscordObject.Model implements DiscordObject.Named, DiscordObject.Iconable permits GuildPreview.Model {
        private final String name;
        private final @Nullable String icon;

        protected NamedAndIconableModel(Snowflake id, String name, @Nullable String icon) {
            super(id);
            this.name = name;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public @Nullable String getIcon() {
            return icon;
        }
    }
}
