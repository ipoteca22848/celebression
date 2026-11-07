package ru.method.celebression.animation;

import java.awt.Color;

public final class InterpolationUtils {
    private InterpolationUtils() {
    }

    public static Color e(Color color, float f) {
        if (color == null) {
            return new Color(0, 0, 0, 0);
        }
        int n = Math.round(Math.max(0.0f, Math.min(1.0f, f)) * 255.0f);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
    }

    public static Color k(int n, float f) {
        Color color = new Color(n, true);
        return InterpolationUtils.e(color, f);
    }

    public static Color lerp(Color color, Color color2, float f) {
        f = Math.max(0.0f, Math.min(1.0f, f));
        int n = Math.round((float)color.getRed() + (float)(color2.getRed() - color.getRed()) * f);
        int n2 = Math.round((float)color.getGreen() + (float)(color2.getGreen() - color.getGreen()) * f);
        int n3 = Math.round((float)color.getBlue() + (float)(color2.getBlue() - color.getBlue()) * f);
        int n4 = Math.round((float)color.getAlpha() + (float)(color2.getAlpha() - color.getAlpha()) * f);
        return new Color(n, n2, n3, n4);
    }

    public static Color aw(float f) {
        return InterpolationUtils.e(Color.WHITE, f);
    }
}

