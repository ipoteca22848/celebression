package ru.method.celebression.utils.client.discord.callbacks;

import com.sun.jna.Callback;
import ru.method.celebression.utils.client.discord.utils.DiscordUser;

public interface JoinRequestCallback
extends Callback {
    public void apply(DiscordUser var1);
}

