package ru.method.celebression.api.render.builders.impl;

import net.minecraft.class_1044;
import ru.method.celebression.api.render.builders.AbstractBuilder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltTexture;
import ru.method.celebression.api.render.builders.states.QuadColorState;
import ru.method.celebression.api.render.builders.states.QuadRadiusState;
import ru.method.celebression.api.render.builders.states.SizeState;

public final class TextureBuilder
extends AbstractBuilder<BuiltTexture> {
    private SizeState size;
    private QuadRadiusState radius;
    private QuadColorState color;
    private float smoothness;
    private float u;
    private float v;
    private float texWidth;
    private float texHeight;
    private int textureId;

    public TextureBuilder size(SizeState sizeState) {
        this.size = sizeState;
        return this;
    }

    public TextureBuilder radius(QuadRadiusState quadRadiusState) {
        this.radius = quadRadiusState;
        return this;
    }

    public TextureBuilder color(QuadColorState quadColorState) {
        this.color = quadColorState;
        return this;
    }

    public TextureBuilder smoothness(float f) {
        this.smoothness = f;
        return this;
    }

    public TextureBuilder texture(float f, float f2, float f3, float f4, class_1044 class_10442) {
        return this.texture(f, f2, f3, f4, class_10442.method_4624());
    }

    public TextureBuilder texture(float f, float f2, float f3, float f4, int n) {
        this.u = f;
        this.v = f2;
        this.texWidth = f3;
        this.texHeight = f4;
        this.textureId = n;
        return this;
    }

    @Override
    protected BuiltTexture _build() {
        return new BuiltTexture(this.size, this.radius, this.color, this.smoothness, this.u, this.v, this.texWidth, this.texHeight, this.textureId);
    }

    @Override
    public void reset() {
        this.size = SizeState.NONE;
        this.radius = QuadRadiusState.NO_ROUND;
        this.color = QuadColorState.WHITE;
        this.smoothness = 1.0f;
        this.u = 0.0f;
        this.v = 0.0f;
        this.texWidth = 0.0f;
        this.texHeight = 0.0f;
        this.textureId = 0;
    }
}

