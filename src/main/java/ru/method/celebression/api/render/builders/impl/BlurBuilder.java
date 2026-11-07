package ru.method.celebression.api.render.builders.impl;

import ru.method.celebression.api.render.builders.AbstractBuilder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltBlur;
import ru.method.celebression.api.render.builders.states.QuadColorState;
import ru.method.celebression.api.render.builders.states.QuadRadiusState;
import ru.method.celebression.api.render.builders.states.SizeState;

public final class BlurBuilder
extends AbstractBuilder<BuiltBlur> {
    private SizeState size;
    private QuadRadiusState radius;
    private QuadColorState color;
    private float smoothness;
    private float blurRadius;

    public BlurBuilder size(SizeState sizeState) {
        this.size = sizeState;
        return this;
    }

    public BlurBuilder radius(QuadRadiusState quadRadiusState) {
        this.radius = quadRadiusState;
        return this;
    }

    public BlurBuilder color(QuadColorState quadColorState) {
        this.color = quadColorState;
        return this;
    }

    public BlurBuilder smoothness(float f) {
        this.smoothness = f;
        return this;
    }

    public BlurBuilder blurRadius(float f) {
        this.blurRadius = f;
        return this;
    }

    @Override
    protected BuiltBlur _build() {
        return new BuiltBlur(this.size, this.radius, this.color, this.smoothness, this.blurRadius);
    }

    @Override
    public void reset() {
        this.size = SizeState.NONE;
        this.radius = QuadRadiusState.NO_ROUND;
        this.color = QuadColorState.WHITE;
        this.smoothness = 1.0f;
        this.blurRadius = 0.0f;
    }
}

