package ru.method.celebression.api.render.builders.impl;

import ru.method.celebression.api.render.builders.AbstractBuilder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltRoundedOutline;
import ru.method.celebression.api.render.builders.states.QuadColorState;
import ru.method.celebression.api.render.builders.states.QuadRadiusState;
import ru.method.celebression.api.render.builders.states.SizeState;

public final class RoundedOutlineBuilder
extends AbstractBuilder<BuiltRoundedOutline> {
    private SizeState size;
    private QuadRadiusState radius;
    private QuadColorState color;
    private float thickness;
    private float internalSmoothness;
    private float externalSmoothness;

    public RoundedOutlineBuilder size(SizeState sizeState) {
        this.size = sizeState;
        return this;
    }

    public RoundedOutlineBuilder radius(QuadRadiusState quadRadiusState) {
        this.radius = quadRadiusState;
        return this;
    }

    public RoundedOutlineBuilder color(QuadColorState quadColorState) {
        this.color = quadColorState;
        return this;
    }

    public RoundedOutlineBuilder thickness(float f) {
        this.thickness = f;
        return this;
    }

    public RoundedOutlineBuilder smoothness(float f, float f2) {
        this.internalSmoothness = f;
        this.externalSmoothness = f2;
        return this;
    }

    @Override
    protected BuiltRoundedOutline _build() {
        return new BuiltRoundedOutline(this.size, this.radius, this.color, this.thickness, this.internalSmoothness, this.externalSmoothness);
    }

    @Override
    public void reset() {
        this.size = SizeState.NONE;
        this.radius = QuadRadiusState.NO_ROUND;
        this.color = QuadColorState.TRANSPARENT;
        this.thickness = 0.0f;
        this.internalSmoothness = 0.0f;
        this.externalSmoothness = 0.34f;
    }
}

