package ru.method.celebression.api.render.builders.impl;

import java.awt.Color;
import ru.method.celebression.api.render.builders.AbstractBuilder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltLiquidGlassNew;
import ru.method.celebression.api.render.builders.states.QuadColorState;
import ru.method.celebression.api.render.builders.states.QuadRadiusState;
import ru.method.celebression.api.render.builders.states.SizeState;
import ru.method.celebression.utils.render.LiquidGlassReflectionCache;

public final class LiquidGlassNewBuilder
extends AbstractBuilder<BuiltLiquidGlassNew> {
    private SizeState size;
    private QuadRadiusState radius;
    private QuadColorState color;
    private float smoothness;
    private float cornerSmoothness;
    private float globalAlpha;
    private float fresnelPower;
    private Color fresnelColor;
    private float fresnelAlpha;
    private float baseAlpha;
    private boolean fresnelInvert;
    private float fresnelMix;
    private float distortStrength;
    private float blurRadius;
    private LiquidGlassReflectionCache.ReflectionSource reflectionSource;

    public LiquidGlassNewBuilder size(SizeState sizeState) {
        this.size = sizeState;
        return this;
    }

    public LiquidGlassNewBuilder radius(QuadRadiusState quadRadiusState) {
        this.radius = quadRadiusState;
        return this;
    }

    public LiquidGlassNewBuilder color(QuadColorState quadColorState) {
        this.color = quadColorState;
        return this;
    }

    public LiquidGlassNewBuilder smoothness(float f) {
        this.smoothness = f;
        return this;
    }

    public LiquidGlassNewBuilder cornerSmoothness(float f) {
        this.cornerSmoothness = f;
        return this;
    }

    public LiquidGlassNewBuilder globalAlpha(float f) {
        this.globalAlpha = f;
        return this;
    }

    public LiquidGlassNewBuilder fresnelPower(float f) {
        this.fresnelPower = f;
        return this;
    }

    public LiquidGlassNewBuilder fresnelColor(Color color) {
        this.fresnelColor = color;
        return this;
    }

    public LiquidGlassNewBuilder fresnelAlpha(float f) {
        this.fresnelAlpha = f;
        return this;
    }

    public LiquidGlassNewBuilder baseAlpha(float f) {
        this.baseAlpha = f;
        return this;
    }

    public LiquidGlassNewBuilder fresnelInvert(boolean bl) {
        this.fresnelInvert = bl;
        return this;
    }

    public LiquidGlassNewBuilder fresnelMix(float f) {
        this.fresnelMix = f;
        return this;
    }

    public LiquidGlassNewBuilder distortStrength(float f) {
        this.distortStrength = f;
        return this;
    }

    public LiquidGlassNewBuilder blurRadius(float f) {
        this.blurRadius = f;
        return this;
    }

    public LiquidGlassNewBuilder reflectionSource(LiquidGlassReflectionCache.ReflectionSource reflectionSource) {
        this.reflectionSource = reflectionSource;
        return this;
    }

    @Override
    protected BuiltLiquidGlassNew _build() {
        return new BuiltLiquidGlassNew(this.size, this.radius, this.color, this.smoothness, this.cornerSmoothness, this.globalAlpha, this.fresnelPower, this.fresnelColor, this.fresnelAlpha, this.baseAlpha, this.fresnelInvert, this.fresnelMix, this.distortStrength, this.blurRadius, this.reflectionSource);
    }

    @Override
    public void reset() {
        this.size = SizeState.NONE;
        this.radius = QuadRadiusState.NO_ROUND;
        this.color = QuadColorState.WHITE;
        this.smoothness = 1.0f;
        this.cornerSmoothness = 2.0f;
        this.globalAlpha = 1.0f;
        this.fresnelPower = 2.0f;
        this.fresnelColor = Color.WHITE;
        this.fresnelAlpha = 0.8f;
        this.baseAlpha = 0.2f;
        this.fresnelInvert = false;
        this.fresnelMix = 1.0f;
        this.distortStrength = 0.03f;
        this.blurRadius = 3.2f;
        this.reflectionSource = LiquidGlassReflectionCache.ReflectionSource.GAMEPLAY;
    }
}

