package ru.method.celebression.input;

import lombok.Generated;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.method.celebression.modules.impl.client.UI;

public final class UiInputUtils {
    public static final float Tf = 60.0f;
    private static final float Tg = 12.0f;
    private static final float Th = 0.55f;

    public static float h(float f, float f2, float f3, float f4) {
        return UiInputUtils.e(f, f2, f3, f4, 12.0f);
    }

    public static float e(float f, float f2, float f3, float f4, float f5) {
        float f6;
        if (f <= 0.0f || f2 <= 0.0f) {
            return 1.0f;
        }
        float f7 = Math.max(0.0f, f5);
        float f8 = (f - f7 * 2.0f) / f3;
        float f9 = Math.min(f8, f6 = (f2 - f7 * 2.0f) / f4);
        if (f9 >= 1.0f) {
            return 1.0f;
        }
        return class_3532.method_15363((float)f9, (float)0.55f, (float)1.0f);
    }

    public static float i(float f, float f2, float f3, float f4) {
        return UiInputUtils.f(f, f2, f3, f4, 12.0f);
    }

    public static float f(float f, float f2, float f3, float f4, float f5) {
        return Math.max(0.01f, UI.getInterfaceScale() * UiInputUtils.e(f, f2, f3, f4, f5));
    }

    public static float ai(float f) {
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        return (1.0f - f2) * 60.0f;
    }

    public static void a(class_4587 class_45872, int n, int n2, float f, float f2, float f3, float f4) {
        class_45872.method_46416((float)n / 2.0f, (float)n2 / 2.0f + f2, 0.0f);
        class_45872.method_22905(f, f, 1.0f);
        class_45872.method_46416(-f3, -f4, 0.0f);
    }

    public static float[] a(double d, double d2, int n, int n2, float f, float f2, float f3) {
        if (f <= 0.01f) {
            f = 0.01f;
        }
        return new float[]{(float)((double)f2 + (d - (double)((float)n / 2.0f)) / (double)f), (float)((double)f3 + (d2 - (double)((float)n2 / 2.0f)) / (double)f)};
    }

    public static float[] a(float f, float f2, float f3, float f4, int n, int n2, float f5, float f6, float f7, float f8) {
        float f9 = (float)n / 2.0f + (f - f7) * f5;
        float f10 = (float)n2 / 2.0f + f6 + (f2 - f8) * f5;
        return new float[]{f9, f10, f3 * f5, f4 * f5};
    }

    @Generated
    private UiInputUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

