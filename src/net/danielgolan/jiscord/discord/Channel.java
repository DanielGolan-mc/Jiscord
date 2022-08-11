package net.danielgolan.jiscord.discord;

import net.danielgolan.jiscord.util.Snowflake;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Date;

public sealed interface Channel extends DiscordObject {
    int getType();
    int getFlags();

    /**
     * A channel that isn't a DirectChannel, but a part of a guild
     */
    sealed interface Integrated extends Channel, DiscordObject.Named, Parented permits Positioned, Thread {
        Snowflake getGuildID();
        default Guild getGuild() throws IOException, InterruptedException {
            return Guild.get(getGuildID());
        }
    }

    /**
     * All channels with a position:
     * every guild channel that isn't a thread (August 2022)
     */
    sealed interface Positioned extends Integrated permits Category, ForumChannel, TextChannel, Threaded, VoiceChannel {
        int getPosition();

        @Nullable Object[] getPermissionOverwrites(); //TODO: Implement PermissionOverwrite
    }

    /**
     * Supports audio chat
     */
    sealed interface UnObjectified extends Channel permits VideoChat {
        int getBitrate();

        @Nullable String getRTCRegion();
    }

    /**
     * Supports video chat
     */
    sealed interface VideoChat extends UnObjectified permits DirectChannel, VoiceChannel {
        default int getVideoQualityMode() {
            return 1;
        }
    }

    sealed interface LimitedUsers extends Channel permits DirectChannel, VoiceChannel {
        int getUserLimit();
    }

    /**
     * Channels that use objects (like messages or threads), not audio or video.
     */
    sealed interface Objectified extends Channel permits Chat, Threaded {
        @Nullable Snowflake getLastMessageID(); //TODO: Implement Messages

        /**
         * Slow-mode
         */
        int getUserRatelimit();
    }

    /**
     * Supports messages
     */
    sealed interface Chat extends Objectified permits DirectChannel, TextChannel, Thread, VoiceChannel {
        int getMessageCount();
        int getTotalMessageCount();
    }

    /**
     * Supports Topics
     */
    non-sealed interface Subjected extends Channel {
        @Nullable String getTopic();
    }

    /**
     * Supports {@linkplain Thread threads}
     */
    non-sealed interface Threaded extends Objectified, Positioned {


    }
    /**
     * Has an owner
     */
    sealed interface Owned extends Channel permits DirectChannel.Group, Thread {

        Snowflake getOwnerID();
        @Nullable Snowflake getApplicationID();
    }
    sealed interface Parented extends Channel permits Integrated {

        @Nullable Snowflake getParentID();
    }
    non-sealed interface SupportsAgeBasedLimit extends Channel {

        boolean isNSFW();
    }
    non-sealed interface SupportsPins extends Channel {

        @Nullable Date getLastPinTime();
    }

    //region Actual Channel Types
    non-sealed interface Category extends Channel.Positioned {

    }
    /**
     * A DM or a DM Group
     */
    sealed interface DirectChannel extends LimitedUsers, VideoChat, Chat permits DirectChannel.Group {

        User[] getRecipients();
        non-sealed interface Group extends DirectChannel, DiscordObject.Named, DiscordObject.Iconable, Owned {


        }
    }
    non-sealed interface VoiceChannel extends LimitedUsers, VideoChat, Chat, Positioned {


    }
    sealed interface TextChannel extends Chat, Positioned, Threaded, Subjected, SupportsPins permits NewsChannel {

    }
    non-sealed interface NewsChannel extends TextChannel { }
    non-sealed interface NewsThread extends Thread { }
    /**
     * @deprecated still in development Discord-side, don't use
     */
    @Deprecated
    non-sealed interface ForumChannel extends Threaded, SupportsPins, Subjected, Positioned {

    }
    sealed interface Thread extends Owned, Integrated, Chat permits NewsThread, PrivateThread {

        @Override //removing @Nullable
        Snowflake getParentID();

        int getMemberCount();

        Object getThreadMetadata(); //TODO: Implement Thread MetaData

        /**
         * in minutes
         */
        int getDefaultAutoArchiveDuration(); //TODO: create enum for Thread Auto Archive Duration

    }
    non-sealed interface PrivateThread extends Thread {

        @Override //removing @Nullable
        Snowflake getParentID();

        int getMemberCount();

        Object getThreadMetadata(); //TODO: Implement Thread MetaData

        /**
         * in minutes
         */
        int getDefaultAutoArchiveDuration(); //TODO: create enum for Thread Auto Archive Duration

    }
    //endregion
}
