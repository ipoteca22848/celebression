package ru.method.celebression.api.render.builders.impl;

import ru.method.celebression.api.render.builders.AbstractBuilder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltBorder;
import ru.method.celebression.api.render.builders.states.QuadColorState;
import ru.method.celebression.api.render.builders.states.QuadRadiusState;
import ru.method.celebression.api.render.builders.states.SizeState;

public final class BorderBuilder
extends AbstractBuilder<BuiltBorder> {
    private SizeState size;
    private QuadRadiusState radius;
    private QuadColorState color;
    private float thickness;
    private float internalSmoothness;
    private float externalSmoothness;

    public BorderBuilder size(SizeState sizeState) {
        this.size = sizeState;
        return this;
    }

    public BorderBuilder radius(QuadRadiusState quadRadiusState) {
        this.radius = quadRadiusState;
        return this;
    }

    public BorderBuilder color(QuadColorState quadColorState) {
        this.color = quadColorState;
        return this;
    }

    public BorderBuilder thickness(float f) {
        this.thickness = f;
        return this;
    }

    public BorderBuilder smoothness(float f, float f2) {
        this.internalSmoothness = f;
        this.externalSmoothness = f2;
        return this;
    }

    @Override
    protected BuiltBorder _build() {
        return new BuiltBorder(this.size, this.radius, this.color, this.thickness, this.internalSmoothness, this.externalSmoothness);
    }

    @Override
    public void reset() {
        this.size = SizeState.NONE;
        this.radius = QuadRadiusState.NO_ROUND;
        this.color = QuadColorState.TRANSPARENT;
        this.thickness = 0.0f;
        this.internalSmoothness = 1.0f;
        this.externalSmoothness = 1.0f;
    }
}

