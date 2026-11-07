package ru.method.celebression.api.render.builders.impl;

import java.awt.Color;
import ru.method.celebression.api.render.builders.AbstractBuilder;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltText;
import ru.method.celebression.api.render.msdf.MsdfFont;

public final class TextBuilder
extends AbstractBuilder<BuiltText> {
    private MsdfFont font;
    private String text;
    private float size;
    private float thickness;
    private int color;
    private float smoothness;
    private float spacing;
    private int outlineColor;
    private float outlineThickness;
    private int glowColor;
    private float glowRadius;
    private int gradientColorStart;
    private int gradientColorEnd;
    private float gradientAngle;

    public TextBuilder font(MsdfFont msdfFont) {
        this.font = msdfFont;
        return this;
    }

    public TextBuilder text(String string) {
        this.text = string;
        return this;
    }

    public TextBuilder size(float f) {
        this.size = f;
        return this;
    }

    public TextBuilder thickness(float f) {
        this.thickness = f;
        return this;
    }

    public TextBuilder color(Color color) {
        return this.color(color.getRGB());
    }

    public TextBuilder color(int n) {
        this.color = n;
        return this;
    }

    public TextBuilder smoothness(float f) {
        this.smoothness = f;
        return this;
    }

    public TextBuilder spacing(float f) {
        this.spacing = f;
        return this;
    }

    public TextBuilder outline(Color color, float f) {
        return this.outline(color.getRGB(), f);
    }

    public TextBuilder outline(int n, float f) {
        this.outlineColor = n;
        this.outlineThickness = f;
        return this;
    }

    public TextBuilder glow(Color color, float f) {
        return this.glow(color.getRGB(), f);
    }

    public TextBuilder glow(int n, float f) {
        this.glowColor = n;
        this.glowRadius = f;
        return this;
    }

    public TextBuilder gradient(Color color, Color color2, float f) {
        return this.gradient(color.getRGB(), color2.getRGB(), f);
    }

    public TextBuilder gradient(int n, int n2, float f) {
        this.gradientColorStart = n;
        this.gradientColorEnd = n2;
        this.gradientAngle = f;
        return this;
    }

    @Override
    protected BuiltText _build() {
        return new BuiltText(this.font, this.text, this.size, this.thickness, this.color, this.smoothness, this.spacing, this.outlineColor, this.outlineThickness, this.glowColor, this.glowRadius, this.gradientColorStart, this.gradientColorEnd, this.gradientAngle);
    }

    @Override
    public void reset() {
        this.font = null;
        this.text = "";
        this.size = 0.0f;
        this.thickness = 0.05f;
        this.color = -1;
        this.smoothness = 0.5f;
        this.spacing = 0.0f;
        this.outlineColor = 0;
        this.outlineThickness = 0.0f;
        this.glowColor = 0;
        this.glowRadius = 0.0f;
        this.gradientColorStart = 0;
        this.gradientColorEnd = 0;
        this.gradientAngle = 0.0f;
    }
}

