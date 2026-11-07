package ru.method.celebression.utils.render;

import java.awt.Color;

public record ColorRGBA(int red, int green, int blue, int alpha) {
    public ColorRGBA(Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static ColorRGBA of(int n, int n2, int n3, int n4) {
        return new ColorRGBA(n, n2, n3, n4);
    }

    public static ColorRGBA of(int n, int n2, int n3) {
        return new ColorRGBA(n, n2, n3, 255);
    }

    public ColorRGBA withAlpha(int n) {
        return new ColorRGBA(this.red, this.green, this.blue, n);
    }

    public int getR() {
        return this.red;
    }

    public int getG() {
        return this.green;
    }

    public int getB() {
        return this.blue;
    }

    public int getA() {
        return this.alpha;
    }

    public Color toAwtColor() {
        return new Color(this.clamp(this.red), this.clamp(this.green), this.clamp(this.blue), this.clamp(this.alpha));
    }

    private int clamp(int n) {
        if (n < 0) {
            return 0;
        }
        if (n > 255) {
            return 255;
        }
        return n;
    }
}

