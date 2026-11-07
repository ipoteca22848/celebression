package ru.method.celebression.punchy.client.animation;

import ru.method.celebression.animation.Animation;

public final class Easing {
    private static final float C1 = 1.9f;
    private static final float C3 = 2.9f;

    private Easing() {
    }

    public static float easeInBack(float f) {
        return 2.9f * f * f * f - 1.9f * f * f;
    }

    public static float easeOutBack(float f) {
        float f2 = f - 1.0f;
        return 1.0f + 2.9f * f2 * f2 * f2 + 1.9f * f2 * f2;
    }

    public static float easeOutBackStrong(float f) {
        float f2 = 3.61f;
        float f3 = f2 + 1.0f;
        float f4 = f - 1.0f;
        return 1.0f + f3 * f4 * f4 * f4 + f2 * f4 * f4;
    }

    public static float smoothStep(float f) {
        float f2 = Math.max(0.0f, Math.min(1.0f, f));
        return f2 * f2 * (3.0f - 2.0f * f2);
    }
}

