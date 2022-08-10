package net.danielgolan.jiscord.discord;

import org.jetbrains.annotations.Nullable;

public sealed interface User extends DiscordObject permits Member {
    String getUsername();
    String getDiscriminator();

    default String getName() {
        return getUsername() + '#' + getDiscriminator();
    }

    @Nullable String getAvatar();

    boolean isBot();

    boolean isSystem();

    boolean isMFAEnabled();

    @Nullable String getBanner();

    int getAccentColor();

    String getLocale();

    boolean isVerified();

    String getEmail();

    int getFlags();

    int getPremiumType();

    int getPublicFlags();
}
