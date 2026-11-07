package ru.method.celebression.modules.impl.render.cosmetic;

public final class BakedModelVertex {
    public final float cc;
    public final float cd;
    public final float ce;
    public float u;
    public float v;

    public BakedModelVertex(float f, float f2, float f3) {
        this.cc = f;
        this.cd = f2;
        this.ce = f3;
    }

    public BakedModelVertex c(float f, float f2) {
        this.u = f;
        this.v = f2;
        return this;
    }
}

