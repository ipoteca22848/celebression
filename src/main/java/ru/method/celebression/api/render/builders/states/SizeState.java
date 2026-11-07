package ru.method.celebression.api.render.builders.states;

public record SizeState(float width, float height) {
    public static final SizeState NONE = new SizeState(0.0f, 0.0f);

    public SizeState(double d, double d2) {
        this((float)d, (float)d2);
    }
}

