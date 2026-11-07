package ru.method.celebression.utils.render;

import net.minecraft.class_310;
import ru.method.celebression.screen.clickgui.ClickGui;

public final class ClickGuiRenderContext {
    private static volatile boolean active;
    private static volatile boolean closing;
    private static volatile float fade;

    private ClickGuiRenderContext() {
    }

    public static boolean isActive() {
        return active;
    }

    public static boolean isClosing() {
        return closing;
    }

    public static float getFadeMultiplier() {
        return fade;
    }

    public static boolean isClickGuiOpen() {
        class_310 class_3102 = class_310.method_1551();
        return class_3102 != null && class_3102.field_1755 instanceof ClickGui;
    }

    public static void begin(float f, boolean bl) {
        active = true;
        closing = bl;
        fade = Math.max(0.0f, Math.min(1.0f, f));
    }

    public static void begin(float f) {
        ClickGuiRenderContext.begin(f, false);
    }

    public static void begin() {
        ClickGuiRenderContext.begin(1.0f, false);
    }

    public static void end() {
        active = false;
        closing = false;
        fade = 1.0f;
    }

    static {
        fade = 1.0f;
    }
}

