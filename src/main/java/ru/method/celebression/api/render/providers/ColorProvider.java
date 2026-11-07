package ru.method.celebression.api.render.providers;

import java.awt.Color;

public final class ColorProvider {
    public static int pack(int n, int n2, int n3, int n4) {
        return (n4 & 0xFF) << 24 | (n & 0xFF) << 16 | (n2 & 0xFF) << 8 | (n3 & 0xFF) << 0;
    }

    public static int[] unpack(int n) {
        return new int[]{n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF};
    }

    public static float[] normalize(Color color) {
        return new float[]{(float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f};
    }

    public static float[] normalize(int n) {
        int[] nArray = ColorProvider.unpack(n);
        return new float[]{(float)nArray[0] / 255.0f, (float)nArray[1] / 255.0f, (float)nArray[2] / 255.0f, (float)nArray[3] / 255.0f};
    }
}

