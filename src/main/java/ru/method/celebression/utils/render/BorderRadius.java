package ru.method.celebression.utils.render;

public record BorderRadius(float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
    public BorderRadius(double d) {
        this((float)d);
    }

    public BorderRadius(float f) {
        this(f, f, f, f);
    }

    public BorderRadius(double d, double d2, double d3, double d4) {
        this((float)d, (float)d2, (float)d3, (float)d4);
    }

    public float[] asArray() {
        return new float[]{this.topLeftRadius, this.topRightRadius, this.bottomRightRadius, this.bottomLeftRadius};
    }
}

