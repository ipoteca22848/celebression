package ru.method.celebression.render;


public final class Rect {
    private final float QD;
    private final float QE;
    private final float QF;
    private final float QG;

    public Rect(float f, float f2, float f3, float f4) {
        this.QD = f;
        this.QE = f2;
        this.QF = f3;
        this.QG = f4;
    }

    public boolean m(float f, float f2) {
        return f >= this.QD && f <= this.QD + this.QF && f2 >= this.QE && f2 <= this.QE + this.QG;
    }

    @Override
    public final String toString() {
        return "Rect[" + "x=" + this.QD + ", " + "y=" + this.QE + ", " + "w=" + this.QF + ", " + "h=" + this.QG + "]";
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(this.QD, this.QE, this.QF, this.QG);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Rect other)) return false;
        return java.util.Objects.deepEquals(this.QD, other.QD)
            && java.util.Objects.deepEquals(this.QE, other.QE)
            && java.util.Objects.deepEquals(this.QF, other.QF)
            && java.util.Objects.deepEquals(this.QG, other.QG);
    }

    public float x() {
        return this.QD;
    }

    public float y() {
        return this.QE;
    }

    public float lB() {
        return this.QF;
    }

    public float lC() {
        return this.QG;
    }
}

