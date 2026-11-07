package ru.method.celebression.utils.client.discord;

import ru.method.celebression.Celebression;
import ru.method.celebression.utils.client.discord.DiscordManager;

public final class DiscordRichPresenceUtil {
    private DiscordRichPresenceUtil() {
    }

    public static void discordrpc() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return;
        }
        DiscordManager discordManager = celebression.getDiscordManager();
        if (discordManager == null) {
            return;
        }
        discordManager.init();
        discordManager.forcePresenceReplace();
    }

    public static void shutdownDiscord() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return;
        }
        DiscordManager discordManager = celebression.getDiscordManager();
        if (discordManager == null) {
            return;
        }
        discordManager.stopRPC();
    }
}

