package ru.method.celebression.modules.impl.render.cosmetic;

public final class CosmeticPreviewTransformState {
    public static boolean active;
    public static float pivotY;
    public static float eg;
    public static float eh;
    public static boolean ei;
    public static float ej;
    public static float ek;
    public static float el;

    private CosmeticPreviewTransformState() {
    }

    public static void a(float f, float f2, float f3) {
        active = true;
        pivotY = f;
        eg = f2;
        eh = f3;
    }

    public static void clear() {
        active = false;
        pivotY = 0.0f;
        eg = 0.0f;
        eh = 0.0f;
    }

    public static void d(float f, float f2) {
        CosmeticPreviewTransformState.b(f, f2, 0.0f);
    }

    public static void b(float f, float f2, float f3) {
        ei = true;
        ej = f;
        ek = f2;
        el = f3;
    }

    public static void aX() {
        ei = false;
        ej = 0.0f;
        ek = 0.0f;
        el = 0.0f;
    }
}

