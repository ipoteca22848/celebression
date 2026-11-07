package ru.method.celebression.api.render.builders.renderers;

import org.joml.Matrix4f;

public interface IRenderer {
    public static final Matrix4f DEFAULT_MATRIX = new Matrix4f();

    default public void render(double d, double d2) {
        this.render((float)d, (float)d2);
    }

    default public void render(float f, float f2) {
        this.render(DEFAULT_MATRIX, f, f2);
    }

    default public void render(Matrix4f matrix4f, double d, double d2) {
        this.render(matrix4f, (float)d, (float)d2);
    }

    default public void render(Matrix4f matrix4f, float f, float f2) {
        this.render(matrix4f, f, f2, 0.0f);
    }

    default public void render(double d, double d2, double d3) {
        this.render((float)d, (float)d2, (float)d3);
    }

    default public void render(float f, float f2, float f3) {
        this.render(DEFAULT_MATRIX, f, f2, f3);
    }

    default public void render(Matrix4f matrix4f, double d, double d2, double d3) {
        this.render(matrix4f, (float)d, (float)d2, (float)d3);
    }

    public void render(Matrix4f var1, float var2, float var3, float var4);
}

