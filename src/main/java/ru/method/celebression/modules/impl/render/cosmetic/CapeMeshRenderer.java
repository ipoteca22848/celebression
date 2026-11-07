package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import ru.method.celebression.modules.impl.render.cosmetic.CapeSimulation;

public final class CapeMeshRenderer {
    private static final int eL = 16;
    private static final float eM = 0.3125f;
    private static final float eN = 0.0625f;
    private static final float eO = 0.0625f;
    private static final float eP = 0.03125f;
    private static final float eQ = 0.125f;
    private static final float eR = 0.015625f;
    private static final float eS = 0.171875f;
    private static final float eT = 0.1875f;
    private static final float eU = 0.34375f;
    private static final float eV = 0.0f;
    private static final float eW = 0.015625f;
    private static final float eX = 0.171875f;
    private static final float eY = 0.1875f;
    private static final float eZ = 0.171875f;
    private static final float fa = 0.328125f;
    private static final float fb = 0.03125f;
    private static final float fc = 0.53125f;
    private static final float fd = 0.0f;
    private static final float fe = 0.03125f;
    private static final float ff = 3.0f;
    private static final long fg = 1080L;
    private static final Matrix4f[] fh = new Matrix4f[16];
    private static final Vector3f fi = new Vector3f();

    private CapeMeshRenderer() {
    }

    public static void a(class_4587 class_45872, class_4597 class_45972, int n, class_2960 class_29602, CapeSimulation capeSimulation, float f, boolean bl) {
        float f2 = (float)(System.currentTimeMillis() % 1080L) / 1080.0f;
        for (int i = 0; i < 16; ++i) {
            class_45872.method_22903();
            CapeMeshRenderer.a(class_45872, capeSimulation, f, bl, i, f2);
            fh[i].set((Matrix4fc)class_45872.method_23760().method_23761());
            class_45872.method_22909();
        }
        class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23572((class_2960)class_29602));
        for (int i = 0; i < 16; ++i) {
            Matrix4f matrix4f = fh[i];
            Matrix4f matrix4f2 = fh[i == 0 ? 0 : i - 1];
            float f3 = (float)i * 0.0625f;
            float f4 = (float)(i + 1) * 0.0625f;
            float f5 = 0.03125f + 0.5f * (float)i / 16.0f;
            float f6 = 0.03125f + 0.5f * (float)(i + 1) / 16.0f;
            CapeMeshRenderer.a(class_45882, n, matrix4f, matrix4f2, f3, f4, f5, f6);
            CapeMeshRenderer.b(class_45882, n, matrix4f, matrix4f2, f3, f4, f5, f6);
            CapeMeshRenderer.c(class_45882, n, matrix4f, matrix4f2, f3, f4, f5, f6);
            CapeMeshRenderer.d(class_45882, n, matrix4f, matrix4f2, f3, f4, f5, f6);
            if (i == 0) {
                CapeMeshRenderer.a(class_45882, n, matrix4f);
            }
            if (i != 15) continue;
            CapeMeshRenderer.a(class_45882, n, matrix4f, f4);
        }
    }

    private static void a(class_4587 class_45872, CapeSimulation capeSimulation, float f, boolean bl, int n, float f2) {
        class_45872.method_46416(0.0f, 0.0f, 0.125f);
        float f3 = capeSimulation.c(n, f) - capeSimulation.c(0, f);
        if (f3 > 0.0f) {
            f3 = 0.0f;
        }
        float f4 = capeSimulation.d(0, f) - (float)n - capeSimulation.d(n, f);
        float f5 = -((float)class_3532.method_15349((double)f4, (double)f3));
        if ((f5 = Math.max(f5, 0.0f)) != 0.0f) {
            f5 = (float)Math.PI - f5;
        }
        f5 *= 114.59155f;
        float f6 = 6.0f;
        if (bl) {
            f6 += 25.0f;
            class_45872.method_46416(0.0f, 0.15f, 0.0f);
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        matrix4f.rotateX((float)Math.toRadians(f6 += CapeMeshRenderer.e(n, f2)));
        matrix4f.rotateY((float)Math.PI);
        class_45872.method_46416(0.0f, f4 / 16.0f, f3 / 16.0f);
        class_45872.method_46416(0.0f, 0.03125f + (float)n * 0.0625f, -0.03125f);
        matrix4f.rotateX((float)Math.toRadians(-f5));
        class_45872.method_46416(0.0f, -0.03125f - (float)n * 0.0625f, 0.03125f);
    }

    private static float e(int n, float f) {
        float f2 = (float)(n + 1) / 16.0f;
        return class_3532.method_15374((float)((f2 - f) * ((float)Math.PI * 2))) * 3.0f;
    }

    private static void a(class_4588 class_45882, int n, Matrix4f matrix4f, Matrix4f matrix4f2, float f, float f2, float f3, float f4) {
        CapeMeshRenderer.a(matrix4f, 0.0f, 0.0f, -1.0f);
        CapeMeshRenderer.a(class_45882, n, matrix4f2, 0.3125f, f, -0.0625f, 0.171875f, f3);
        CapeMeshRenderer.a(class_45882, n, matrix4f2, -0.3125f, f, -0.0625f, 0.015625f, f3);
        CapeMeshRenderer.a(class_45882, n, matrix4f, -0.3125f, f2, -0.0625f, 0.015625f, f4);
        CapeMeshRenderer.a(class_45882, n, matrix4f, 0.3125f, f2, -0.0625f, 0.171875f, f4);
    }

    private static void b(class_4588 class_45882, int n, Matrix4f matrix4f, Matrix4f matrix4f2, float f, float f2, float f3, float f4) {
        CapeMeshRenderer.a(matrix4f, 0.0f, 0.0f, 1.0f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, 0.3125f, f2, 0.0f, 0.34375f, f4);
        CapeMeshRenderer.a(class_45882, n, matrix4f, -0.3125f, f2, 0.0f, 0.1875f, f4);
        CapeMeshRenderer.a(class_45882, n, matrix4f2, -0.3125f, f, 0.0f, 0.1875f, f3);
        CapeMeshRenderer.a(class_45882, n, matrix4f2, 0.3125f, f, 0.0f, 0.34375f, f3);
    }

    private static void c(class_4588 class_45882, int n, Matrix4f matrix4f, Matrix4f matrix4f2, float f, float f2, float f3, float f4) {
        CapeMeshRenderer.a(matrix4f, -1.0f, 0.0f, 0.0f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, -0.3125f, f2, 0.0f, 0.015625f, f4);
        CapeMeshRenderer.a(class_45882, n, matrix4f, -0.3125f, f2, -0.0625f, 0.0f, f4);
        CapeMeshRenderer.a(class_45882, n, matrix4f2, -0.3125f, f, -0.0625f, 0.0f, f3);
        CapeMeshRenderer.a(class_45882, n, matrix4f2, -0.3125f, f, 0.0f, 0.015625f, f3);
    }

    private static void d(class_4588 class_45882, int n, Matrix4f matrix4f, Matrix4f matrix4f2, float f, float f2, float f3, float f4) {
        CapeMeshRenderer.a(matrix4f, 1.0f, 0.0f, 0.0f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, 0.3125f, f2, -0.0625f, 0.171875f, f4);
        CapeMeshRenderer.a(class_45882, n, matrix4f, 0.3125f, f2, 0.0f, 0.1875f, f4);
        CapeMeshRenderer.a(class_45882, n, matrix4f2, 0.3125f, f, 0.0f, 0.1875f, f3);
        CapeMeshRenderer.a(class_45882, n, matrix4f2, 0.3125f, f, -0.0625f, 0.171875f, f3);
    }

    private static void a(class_4588 class_45882, int n, Matrix4f matrix4f) {
        CapeMeshRenderer.a(matrix4f, 0.0f, -1.0f, 0.0f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, 0.3125f, 0.0f, 0.0f, 0.171875f, 0.03125f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, -0.3125f, 0.0f, 0.0f, 0.015625f, 0.03125f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, -0.3125f, 0.0f, -0.0625f, 0.015625f, 0.03125f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, 0.3125f, 0.0f, -0.0625f, 0.171875f, 0.03125f);
    }

    private static void a(class_4588 class_45882, int n, Matrix4f matrix4f, float f) {
        CapeMeshRenderer.a(matrix4f, 0.0f, 1.0f, 0.0f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, 0.3125f, f, -0.0625f, 0.171875f, 0.53125f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, -0.3125f, f, -0.0625f, 0.015625f, 0.53125f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, -0.3125f, f, 0.0f, 0.015625f, 0.53125f);
        CapeMeshRenderer.a(class_45882, n, matrix4f, 0.3125f, f, 0.0f, 0.171875f, 0.53125f);
    }

    private static void a(Matrix4f matrix4f, float f, float f2, float f3) {
        fi.set(f, f2, f3);
        matrix4f.transformDirection(fi);
        fi.normalize();
    }

    private static void a(class_4588 class_45882, int n, Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5) {
        class_45882.method_22918(matrix4f, f, f2, f3).method_22915(1.0f, 1.0f, 1.0f, 1.0f).method_22913(f4, f5).method_22922(class_4608.field_21444).method_60803(n).method_22914(CapeMeshRenderer.fi.x, CapeMeshRenderer.fi.y, CapeMeshRenderer.fi.z);
    }

    static {
        for (int i = 0; i < 16; ++i) {
            CapeMeshRenderer.fh[i] = new Matrix4f();
        }
    }
}

