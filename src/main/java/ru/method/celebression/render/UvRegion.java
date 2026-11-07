package ru.method.celebression.render;


public final class UvRegion {
    private final float H;
    private final float I;
    private final float J;
    private final float K;
    public static final UvRegion L = new UvRegion(0.0f, 0.0f, 0.0f, 0.0f);

    public UvRegion(float f, float f2, float f3, float f4) {
        this.H = f;
        this.I = f2;
        this.J = f3;
        this.K = f4;
    }

    public boolean isEmpty() {
        return this.J == 0.0f && this.K == 0.0f;
    }

    @Override
    public final String toString() {
        return "UvRegion[" + "u=" + this.H + ", " + "v=" + this.I + ", " + "width=" + this.J + ", " + "height=" + this.K + "]";
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(this.H, this.I, this.J, this.K);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UvRegion other)) return false;
        return java.util.Objects.deepEquals(this.H, other.H)
            && java.util.Objects.deepEquals(this.I, other.I)
            && java.util.Objects.deepEquals(this.J, other.J)
            && java.util.Objects.deepEquals(this.K, other.K);
    }

    public float u() {
        return this.H;
    }

    public float v() {
        return this.I;
    }

    public float width() {
        return this.J;
    }

    public float height() {
        return this.K;
    }
}

