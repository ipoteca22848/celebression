package ru.method.celebression.modules.impl.render.cosmetic;

import lombok.Generated;
import ru.method.celebression.input.UiInputUtils;

public final class CosmeticMathUtils {
    public static final float MY = 1120.0f;
    public static final float MZ = 700.0f;
    public static final float Na = 560.0f;
    public static final float Nb = 350.0f;
    private static final float Nc = 16.0f;

    public static Transform p(int n, int n2) {
        n = Math.max(1, n);
        n2 = Math.max(1, n2);
        float f = UiInputUtils.f(n, n2, 1120.0f, 700.0f, 16.0f);
        return new Transform(n, n2, f);
    }

    @Generated
    private CosmeticMathUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final class Transform {
        public final int Nd;
        public final int Ne;
        public final float Nf;
        public final float Ng;
        public final float Nh;
        public final float Ni;
        public final float Nj;
        public final float Nk;
        public final float Nl;
        public final float Nm;
        public final float Nn;
        public final float No;
        public final float Np;
        public final float Nq;
        public final float Nr;
        public final float Ns;
        public final float Nt;
        public final float Nu;
        public final float Nv;
        public final float Nw;
        public final float Nx;
        public final float Ny;
        public final float Nz;
        public final float NA;
        public final float NB;
        public final float NC;
        public final float ND;
        public final float NE;
        public final float NF;
        public final float NG;

        public Transform(int n, int n2, float f) {
            this.Nd = n;
            this.Ne = n2;
            this.Nf = f;
            this.Ng = 34.0f;
            this.Nh = 76.0f;
            this.Nj = 255.0f;
            this.Nn = 357.0f;
            this.Np = 408.0f;
            this.Nq = 16.0f;
            this.Nr = 8.0f;
            this.Nt = 12.0f;
            this.Nu = 10.0f;
            this.Ni = this.Ng;
            this.Nk = this.Nh;
            this.Nl = 700.0f - this.Nh - 28.0f;
            this.Ns = this.Nl - this.Nr;
            this.Nm = this.Ni + this.Nj + this.Nq;
            this.No = this.Nm + this.Nn + this.Nq;
            this.Nv = 64.0f;
            this.Nw = 14.0f;
            this.Nx = 6.0f;
            this.Ny = 18.0f;
            this.Nz = 38.0f;
            this.NA = 14.0f;
            this.NB = 178.0f;
            this.NC = 8.0f;
            this.ND = 52.0f;
            this.NE = 8.0f;
            this.NF = 30.0f;
            this.NG = 24.0f;
        }

        public float lk() {
            return this.Nk + this.Nr;
        }

        public float ll() {
            return 1120.0f;
        }

        public float lm() {
            return 700.0f;
        }

        public float ln() {
            return Math.max(0.01f, this.Nf);
        }

        public float ac(float f) {
            return f;
        }

        public float ad(float f) {
            return f;
        }

        public boolean c(float f, float f2, float f3, float f4, float f5, float f6) {
            return f5 >= f && f5 <= f + f3 && f6 >= f2 && f6 <= f2 + f4;
        }
    }
}

