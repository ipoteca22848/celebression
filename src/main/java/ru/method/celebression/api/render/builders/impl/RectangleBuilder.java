package ru.method.celebression.api.render.builders.impl;

import ru.method.celebression.api.render.builders.AbstractBuilder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltRectangle;
import ru.method.celebression.api.render.builders.states.QuadColorState;
import ru.method.celebression.api.render.builders.states.QuadRadiusState;
import ru.method.celebression.api.render.builders.states.SizeState;

public final class RectangleBuilder
extends AbstractBuilder<BuiltRectangle> {
    private SizeState size;
    private QuadRadiusState radius;
    private QuadColorState color;
    private float smoothness;

    public RectangleBuilder size(SizeState sizeState) {
        this.size = sizeState;
        return this;
    }

    public RectangleBuilder radius(QuadRadiusState quadRadiusState) {
        this.radius = quadRadiusState;
        return this;
    }

    public RectangleBuilder color(QuadColorState quadColorState) {
        this.color = quadColorState;
        return this;
    }

    public RectangleBuilder smoothness(float f) {
        this.smoothness = f;
        return this;
    }

    @Override
    protected BuiltRectangle _build() {
        return new BuiltRectangle(this.size, this.radius, this.color, this.smoothness);
    }

    @Override
    public void reset() {
        this.size = SizeState.NONE;
        this.radius = QuadRadiusState.NO_ROUND;
        this.color = QuadColorState.TRANSPARENT;
        this.smoothness = 1.0f;
    }
}

