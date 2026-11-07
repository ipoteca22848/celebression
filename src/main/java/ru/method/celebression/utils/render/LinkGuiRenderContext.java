package ru.method.celebression.utils.render;

public final class LinkGuiRenderContext {
    private static volatile boolean active;
    private static volatile float fade;

    private LinkGuiRenderContext() {
    }

    public static boolean isActive() {
        return active;
    }

    public static void begin() {
        active = true;
    }

    public static void end() {
        active = false;
    }

    public static float getFadeMultiplier() {
        return fade;
    }

    public static void setFade(float f) {
        float f2 = Math.max(0.0f, Math.min(1.0f, f));
        fade = (float)Math.round(f2 * 64.0f) / 64.0f;
    }

    public static void resetFade() {
        fade = 1.0f;
    }

    static {
        fade = 1.0f;
    }
}

