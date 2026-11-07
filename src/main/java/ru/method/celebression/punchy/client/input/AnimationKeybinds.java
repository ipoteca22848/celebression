package ru.method.celebression.punchy.client.input;

import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_3675;
import ru.method.celebression.punchy.client.state.InspectActionTracker;
import ru.method.celebression.punchy.platform.Services;

public final class AnimationKeybinds {
    private static final String CATEGORY = "key.categories.punchy.tuning";
    private static class_304 inspectKey;
    private static boolean registered;

    private AnimationKeybinds() {
    }

    public static void register() {
        if (!registered) {
            inspectKey = new class_304("key.punchy.inspect", class_3675.class_307.field_1668, 73, "key.categories.punchy.tuning");
            Services.CLIENT.registerKeyMapping(inspectKey);
            registered = true;
            Services.CLIENT.registerClientTickEnd(AnimationKeybinds::handleClientTick);
        }
    }

    private static void handleClientTick(class_310 class_3102) {
        if (registered && class_3102 != null && class_3102.field_1724 != null && class_3102.field_1755 == null) {
            while (inspectKey.method_1436()) {
                InspectActionTracker.record();
            }
        }
    }
}

