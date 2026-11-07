package ru.method.celebression.punchy.client.tuning;

public final class MixPackKeybinds {
    private static boolean registered = false;

    private MixPackKeybinds() {
    }

    public static void register() {
        if (!registered) {
            registered = true;
        }
    }
}

