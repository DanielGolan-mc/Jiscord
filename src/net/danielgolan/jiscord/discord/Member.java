package net.danielgolan.jiscord.discord;

import org.jetbrains.annotations.Nullable;

import java.util.Date;

non-sealed public interface Member extends User {
    @Nullable String getNickname();

    @Nullable String getGuildAvatar();

    Object[] getRoles();

    Date getJoinTime();

    @Nullable Date getPremiumJoinTime();

    boolean isDeafened();

    boolean isMuted();

    boolean pending();

    @Nullable Date getCommunicationDisabilityExpirationTime();
}
