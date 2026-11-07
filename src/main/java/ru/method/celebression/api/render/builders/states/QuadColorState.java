package ru.method.celebression.api.render.builders.states;

import java.awt.Color;

public record QuadColorState(int color1, int color2, int color3, int color4) {
    public static final QuadColorState TRANSPARENT = new QuadColorState(0, 0, 0, 0);
    public static final QuadColorState WHITE = new QuadColorState(-1, -1, -1, -1);

    public QuadColorState(Color color, Color color2, Color color3, Color color4) {
        this(color.getRGB(), color2.getRGB(), color3.getRGB(), color4.getRGB());
    }

    public QuadColorState(Color color) {
        this(color, color, color, color);
    }

    public QuadColorState(int n) {
        this(n, n, n, n);
    }
}

