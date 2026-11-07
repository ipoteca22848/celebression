package ru.method.celebression.punchy.client.tuning;

import net.minecraft.class_304;

public final class TuningEditorKeybinds {
    private static final String CATEGORY = "key.categories.punchy.tuning";
    private static class_304 openConfigKey;
    private static boolean registered;

    private TuningEditorKeybinds() {
    }

    public static void register() {
        registered = true;
    }

    static {
        registered = false;
    }
}

