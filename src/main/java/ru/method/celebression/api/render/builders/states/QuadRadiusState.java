package ru.method.celebression.api.render.builders.states;

public record QuadRadiusState(float radius1, float radius2, float radius3, float radius4) {
    public static final QuadRadiusState NO_ROUND = new QuadRadiusState(0.0f, 0.0f, 0.0f, 0.0f);

    public QuadRadiusState(double d, double d2, double d3, double d4) {
        this((float)d, (float)d2, (float)d3, (float)d4);
    }

    public QuadRadiusState(double d) {
        this(d, d, d, d);
    }

    public QuadRadiusState(float f) {
        this(f, f, f, f);
    }
}

