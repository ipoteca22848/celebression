package ru.method.celebression.utils.client.discord.callbacks;

import com.sun.jna.Callback;

public interface DisconnectedCallback
extends Callback {
    public void apply(int var1, String var2);
}

