package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.render.UvRegion;

public final class BedrockCubeDefinition {
    private final float bo;
    private final float bp;
    private final float bq;
    private final float br;
    private final float bs;
    private final float bt;
    private final float bu;
    private final float bv;
    private final float bw;
    private final float bx;
    private final float by;
    private final float bz;
    private final boolean A;
    private final UvRegion B;
    private final UvRegion C;
    private final UvRegion D;
    private final UvRegion E;
    private final UvRegion F;
    private final UvRegion G;

    public BedrockCubeDefinition(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, boolean bl, UvRegion uvRegion, UvRegion uvRegion2, UvRegion uvRegion3, UvRegion uvRegion4, UvRegion uvRegion5, UvRegion uvRegion6) {
        this.bo = f;
        this.bp = f2;
        this.bq = f3;
        this.br = f4;
        this.bs = f5;
        this.bt = f6;
        this.bu = f7;
        this.bv = f8;
        this.bw = f9;
        this.bx = f10;
        this.by = f11;
        this.bz = f12;
        this.A = bl;
        this.B = uvRegion;
        this.C = uvRegion2;
        this.D = uvRegion3;
        this.E = uvRegion4;
        this.F = uvRegion5;
        this.G = uvRegion6;
    }

    @Override
    public final String toString() {
        return "BedrockCubeDefinition[" + "originX=" + this.bo + ", " + "originY=" + this.bp + ", " + "originZ=" + this.bq + ", " + "sizeX=" + this.br + ", " + "sizeY=" + this.bs + ", " + "sizeZ=" + this.bt + ", " + "pivotX=" + this.bu + ", " + "pivotY=" + this.bv + ", " + "pivotZ=" + this.bw + ", " + "rotationX=" + this.bx + ", " + "rotationY=" + this.by + ", " + "rotationZ=" + this.bz + ", " + "mirror=" + this.A + ", " + "north=" + this.B + ", " + "east=" + this.C + ", " + "south=" + this.D + ", " + "west=" + this.E + ", " + "up=" + this.F + ", " + "down=" + this.G + "]";
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(this.bo, this.bp, this.bq, this.br, this.bs, this.bt, this.bu, this.bv, this.bw, this.bx, this.by, this.bz, this.A, this.B, this.C, this.D, this.E, this.F, this.G);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof BedrockCubeDefinition other)) return false;
        return java.util.Objects.deepEquals(this.bo, other.bo)
            && java.util.Objects.deepEquals(this.bp, other.bp)
            && java.util.Objects.deepEquals(this.bq, other.bq)
            && java.util.Objects.deepEquals(this.br, other.br)
            && java.util.Objects.deepEquals(this.bs, other.bs)
            && java.util.Objects.deepEquals(this.bt, other.bt)
            && java.util.Objects.deepEquals(this.bu, other.bu)
            && java.util.Objects.deepEquals(this.bv, other.bv)
            && java.util.Objects.deepEquals(this.bw, other.bw)
            && java.util.Objects.deepEquals(this.bx, other.bx)
            && java.util.Objects.deepEquals(this.by, other.by)
            && java.util.Objects.deepEquals(this.bz, other.bz)
            && java.util.Objects.deepEquals(this.A, other.A)
            && java.util.Objects.deepEquals(this.B, other.B)
            && java.util.Objects.deepEquals(this.C, other.C)
            && java.util.Objects.deepEquals(this.D, other.D)
            && java.util.Objects.deepEquals(this.E, other.E)
            && java.util.Objects.deepEquals(this.F, other.F)
            && java.util.Objects.deepEquals(this.G, other.G);
    }

    public float ar() {
        return this.bo;
    }

    public float as() {
        return this.bp;
    }

    public float at() {
        return this.bq;
    }

    public float au() {
        return this.br;
    }

    public float av() {
        return this.bs;
    }

    public float aw() {
        return this.bt;
    }

    public float aj() {
        return this.bu;
    }

    public float ak() {
        return this.bv;
    }

    public float al() {
        return this.bw;
    }

    public float am() {
        return this.bx;
    }

    public float an() {
        return this.by;
    }

    public float ao() {
        return this.bz;
    }

    public boolean ax() {
        return this.A;
    }

    public UvRegion ay() {
        return this.B;
    }

    public UvRegion az() {
        return this.C;
    }

    public UvRegion ba() {
        return this.D;
    }

    public UvRegion bb() {
        return this.E;
    }

    public UvRegion bc() {
        return this.F;
    }

    public UvRegion bd() {
        return this.G;
    }
}

