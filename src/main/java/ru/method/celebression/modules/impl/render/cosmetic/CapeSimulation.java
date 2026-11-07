package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1657;
import net.minecraft.class_3532;

public final class CapeSimulation {
    public static final int ey = 16;
    private static final float ez = 25.0f;
    private static final int eA = 30;
    private static final float eB = 5.0f;
    private static final float eC = 6.0f;
    private static final float eD = 0.05f;
    private static final float eE = 1.0f;
    private static final float eF = 57.2958f;
    private final float[] eG = new float[16];
    private final float[] eH = new float[16];
    private final float[] eI = new float[16];
    private final float[] eJ = new float[16];
    private boolean eK;

    public boolean bD() {
        return this.eK;
    }

    public void b(class_1657 class_16572) {
        int n;
        if (this.eK) {
            return;
        }
        for (n = 0; n < 16; ++n) {
            this.eG[n] = 0.0f;
            this.eH[n] = -n;
            this.eI[n] = 0.0f;
            this.eJ[n] = -n;
        }
        this.eK = true;
        for (n = 0; n < 10; ++n) {
            this.c(class_16572);
        }
    }

    public float c(int n, float f) {
        return class_3532.method_16439((float)f, (float)this.eI[n], (float)this.eG[n]);
    }

    public float d(int n, float f) {
        return class_3532.method_16439((float)f, (float)this.eJ[n], (float)this.eH[n]);
    }

    public void c(class_1657 class_16572) {
        if (!this.eK) {
            return;
        }
        this.eI[0] = this.eG[0];
        this.eJ[0] = this.eH[0];
        double d = class_16572.field_7500 - class_16572.method_23317();
        double d2 = class_16572.field_7499 - class_16572.method_23321();
        float f = class_16572.method_43078();
        double d3 = class_3532.method_15374((float)(f * ((float)Math.PI / 180)));
        double d4 = -class_3532.method_15362((float)(f * ((float)Math.PI / 180)));
        double d5 = class_3532.method_15350((double)((double)this.eH[0] - class_16572.method_23318() * 6.0), (double)0.0, (double)1.0);
        this.eG[0] = this.eG[0] + (float)(d * d3 + d2 * d4 + d5);
        this.eH[0] = (float)(class_16572.method_23318() * 6.0 + (double)(class_16572.method_18276() ? -4.0f : 0.0f));
        this.bE();
    }

    private void bE() {
        float f;
        float f2;
        int n;
        int n2;
        float f3 = 1.25f;
        for (n2 = 1; n2 < 16; ++n2) {
            float f4 = this.eG[n2];
            float f5 = this.eH[n2];
            this.eH[n2] = f5 - f3;
            this.eI[n2] = f4;
            this.eJ[n2] = f5;
        }
        for (n2 = 1; n2 < 16; ++n2) {
            if (!(this.eG[n2] - this.eG[0] > 0.0f)) continue;
            this.eG[n2] = this.eG[0] - 0.1f;
        }
        for (n2 = 14; n2 >= 1; --n2) {
            double d;
            double d2 = CapeSimulation.a(this.eG[n2], this.eH[n2], this.eG[n2 - 1], this.eH[n2 - 1], this.eG[n2 + 1], this.eH[n2 + 1]) * (double)57.2958f;
            if (d2 > 360.0) {
                d2 -= 360.0;
            }
            if (d2 < -360.0) {
                d2 += 360.0;
            }
            if ((d = Math.abs(d2)) < 175.0) {
                this.a(n2, d2, 176.0);
                continue;
            }
            if (!(d > 185.0)) continue;
            this.a(n2, d2, 184.0);
        }
        float f6 = 0.5f;
        for (n = 0; n < 30; ++n) {
            for (int i = 14; i >= 0; --i) {
                int n3 = i;
                int n4 = i + 1;
                f2 = (this.eG[n3] + this.eG[n4]) / 2.0f;
                f = (this.eH[n3] + this.eH[n4]) / 2.0f;
                float f7 = this.eG[n3] - this.eG[n4];
                float f8 = this.eH[n3] - this.eH[n4];
                float f9 = class_3532.method_15355((float)(f7 * f7 + f8 * f8));
                if (f9 < 1.0E-4f) {
                    f7 = 0.0f;
                    f8 = 0.0f;
                } else {
                    f7 /= f9;
                    f8 /= f9;
                }
                if (n3 != 0) {
                    this.eG[n3] = f2 + f7 * f6;
                    this.eH[n3] = f + f8 * f6;
                }
                this.eG[n4] = f2 - f7 * f6;
                this.eH[n4] = f - f8 * f6;
            }
        }
        for (n = 0; n < 15; ++n) {
            int n5 = n;
            int n6 = n + 1;
            float f10 = this.eG[n5] - this.eG[n6];
            f2 = this.eH[n5] - this.eH[n6];
            f = class_3532.method_15355((float)(f10 * f10 + f2 * f2));
            if (f < 1.0E-4f) {
                f10 = 0.0f;
                f2 = 0.0f;
            } else {
                f10 /= f;
                f2 /= f;
            }
            this.eG[n6] = this.eG[n5] - f10 * 1.0f;
            this.eH[n6] = this.eH[n5] - f2 * 1.0f;
        }
    }

    private static double a(float f, float f2, float f3, float f4, float f5, float f6) {
        return Math.atan2(f6 - f2, f5 - f) - Math.atan2(f4 - f2, f3 - f);
    }

    private void a(int n, double d, double d2) {
        double d3 = d2 / (double)57.2958f;
        if (d < 0.0) {
            d3 = -d3;
        }
        float f = this.eG[n - 1] - this.eG[n];
        float f2 = this.eH[n - 1] - this.eH[n];
        double d4 = Math.cos(d3);
        double d5 = Math.sin(d3);
        this.eG[n + 1] = (float)((double)f * d4 - (double)f2 * d5 + (double)this.eG[n]);
        this.eH[n + 1] = (float)((double)f * d5 + (double)f2 * d4 + (double)this.eH[n]);
    }
}

