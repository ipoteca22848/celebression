package ru.method.celebression.ui;

import net.minecraft.class_3532;

public final class LinkGuiLayoutState {
    public static final float TI = 1280.0f;
    public static final float TJ = 720.0f;
    public static final float TK = 12.0f;
    public static final float TL = 720.0f;
    public static final float TM = 480.0f;
    public static final float TN = 360.0f;
    public static final float TO = 48.0f;
    public static final float TP = 12.0f;
    public static final float TQ = 220.0f;
    public static final float TR = 240.0f;
    public static final float TS = 32.0f;
    public static final float TT = 100.0f;
    public static final float TU = 52.0f;
    public static final float TV = 84.0f;
    public static final float TW = 188.0f;
    public static final float TX = 240.0f;
    public static final float TY = 570.0f;
    public static final float TZ = 86.0f;
    private static final float Ua = 0.55f;
    private static final float Ub = 12.0f;

    private LinkGuiLayoutState() {
    }

    public static LayoutMetrics mP() {
        float f = 280.0f;
        float f2 = 100.0f;
        float f3 = 460.0f;
        float f4 = f2 + 480.0f + 12.0f;
        float f5 = 32.0f;
        float f6 = f2;
        float f7 = 56.0f;
        float f8 = f2;
        float f9 = 1008.0f;
        float f10 = f2 + -45.0f;
        return new LayoutMetrics(f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
    }

    public static float p(float f, float f2) {
        if (f <= 0.0f || f2 <= 0.0f) {
            return 1.0f;
        }
        float f3 = (f - 24.0f) / 1280.0f;
        float f4 = (f2 - 24.0f) / 720.0f;
        float f5 = Math.min(f3, f4);
        if (f5 >= 1.0f) {
            return 1.0f;
        }
        return class_3532.method_15363((float)f5, (float)0.55f, (float)1.0f);
    }

    public static final class LayoutMetrics {
        private final float Uc;
        private final float Ud;
        private final float Ue;
        private final float Uf;
        private final float Ug;
        private final float Uh;
        private final float Ui;
        private final float Uj;
        private final float Uk;
        private final float Ul;

        public LayoutMetrics(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            this.Uc = f;
            this.Ud = f2;
            this.Ue = f3;
            this.Uf = f4;
            this.Ug = f5;
            this.Uh = f6;
            this.Ui = f7;
            this.Uj = f8;
            this.Uk = f9;
            this.Ul = f10;
        }

        @Override
        public final String toString() {
            return "LayoutMetrics[" + "mainX=" + this.Uc + ", " + "mainY=" + this.Ud + ", " + "dockX=" + this.Ue + ", " + "dockY=" + this.Uf + ", " + "interfaceX=" + this.Ug + ", " + "interfaceY=" + this.Uh + ", " + "profileX=" + this.Ui + ", " + "profileY=" + this.Uj + ", " + "themeX=" + this.Uk + ", " + "themeY=" + this.Ul + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.Uc, this.Ud, this.Ue, this.Uf, this.Ug, this.Uh, this.Ui, this.Uj, this.Uk, this.Ul);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof LayoutMetrics other)) return false;
            return java.util.Objects.deepEquals(this.Uc, other.Uc)
                && java.util.Objects.deepEquals(this.Ud, other.Ud)
                && java.util.Objects.deepEquals(this.Ue, other.Ue)
                && java.util.Objects.deepEquals(this.Uf, other.Uf)
                && java.util.Objects.deepEquals(this.Ug, other.Ug)
                && java.util.Objects.deepEquals(this.Uh, other.Uh)
                && java.util.Objects.deepEquals(this.Ui, other.Ui)
                && java.util.Objects.deepEquals(this.Uj, other.Uj)
                && java.util.Objects.deepEquals(this.Uk, other.Uk)
                && java.util.Objects.deepEquals(this.Ul, other.Ul);
        }

        public float mQ() {
            return this.Uc;
        }

        public float mR() {
            return this.Ud;
        }

        public float mS() {
            return this.Ue;
        }

        public float mT() {
            return this.Uf;
        }

        public float mU() {
            return this.Ug;
        }

        public float mV() {
            return this.Uh;
        }

        public float mW() {
            return this.Ui;
        }

        public float mX() {
            return this.Uj;
        }

        public float mY() {
            return this.Uk;
        }

        public float mZ() {
            return this.Ul;
        }
    }
}

