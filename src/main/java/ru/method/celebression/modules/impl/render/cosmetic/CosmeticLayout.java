package ru.method.celebression.modules.impl.render.cosmetic;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColorUtils;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.utils.render.Render2D;

public final class CosmeticLayout {
    public static void b(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = f3 * 0.42f;
        float f5 = f3 * 0.11f;
        for (int i = 0; i < 3; ++i) {
            float f6 = f2 - f3 * 0.18f + (float)i * f5 * 1.15f;
            Render2D.drawRoundedRect(class_45872, f - f4 / 2.0f, f6, f4, f5, f5 / 2.0f, color);
        }
    }

    public static void c(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = f3 * 0.54f;
        float f5 = f3 * 0.32f;
        UiRenderUtils.c(class_45872, f - f4 / 2.0f, f2 - f5 / 2.0f, f4, f5, f5 / 2.0f, 1.4f, color);
        Render2D.drawCircle(class_45872, f, f2, f3 * 0.1f, color);
    }

    public static void d(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = f3 * 0.11f;
        Render2D.drawCircle(class_45872, f, f2 + f4 * 0.35f, f3 * 0.17f, color);
        Render2D.drawCircle(class_45872, f - f4 * 1.05f, f2 - f4 * 0.55f, f3 * 0.1f, color);
        Render2D.drawCircle(class_45872, f - f4 * 0.35f, f2 - f4 * 0.95f, f3 * 0.1f, color);
        Render2D.drawCircle(class_45872, f + f4 * 0.35f, f2 - f4 * 0.95f, f3 * 0.1f, color);
        Render2D.drawCircle(class_45872, f + f4 * 1.05f, f2 - f4 * 0.55f, f3 * 0.1f, color);
    }

    public static void e(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = f3 * 0.22f;
        Render2D.drawRoundedRect(class_45872, f - f4 / 2.0f, f2 - f3 * 0.34f, f4, f3 * 0.68f, f4 / 2.0f, color);
        Render2D.drawRoundedRect(class_45872, f - f3 * 0.34f, f2 - f4 / 2.0f, f3 * 0.68f, f4, f4 / 2.0f, color);
        Render2D.drawCircle(class_45872, f, f2, f3 * 0.11f, color);
    }

    public static void f(class_4587 class_45872, float f, float f2, float f3, Color color) {
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(45.0f));
        float f4 = f3 * 0.2f;
        Render2D.drawRoundedRect(class_45872, -f4 / 2.0f, -f3 * 0.36f, f4, f3 * 0.72f, f4 / 2.0f, color);
        Render2D.drawRoundedRect(class_45872, -f3 * 0.36f, -f4 / 2.0f, f3 * 0.72f, f4, f4 / 2.0f, color);
        Render2D.drawCircle(class_45872, 0.0f, 0.0f, f3 * 0.1f, color);
        class_45872.method_22909();
    }

    public static void g(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = Math.max(1.4f, f3 * 0.12f);
        Render2D.drawRoundedRect(class_45872, f - f4 * 0.5f, f2 - f3 * 0.22f, f4, f3 * 0.44f, f4 * 0.5f, color);
        Render2D.drawRoundedRect(class_45872, f - f3 * 0.22f, f2 - f4 * 0.5f, f3 * 0.44f, f4, f4 * 0.5f, color);
    }

    public static void h(class_4587 class_45872, float f, float f2, float f3, Color color) {
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(-35.0f));
        float f4 = Math.max(1.6f, f3 * 0.11f);
        Render2D.drawRoundedRect(class_45872, -f3 * 0.06f, -f3 * 0.28f, f4, f3 * 0.52f, f4 * 0.5f, color);
        class_45872.method_22909();
        CosmeticLayout.e(class_45872, f + f3 * 0.14f, f2 - f3 * 0.22f, f3 * 0.38f, color);
    }

    public static void i(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = f3 * 0.07f;
        Render2D.drawCircle(class_45872, f - f3 * 0.12f, f2 - f3 * 0.18f, f4, color);
        Render2D.drawRoundedRect(class_45872, f - f3 * 0.06f, f2 - f3 * 0.06f, f3 * 0.22f, f3 * 0.3f, f4, color);
        Render2D.drawRoundedRect(class_45872, f - f3 * 0.18f, f2 + f3 * 0.1f, f3 * 0.12f, f3 * 0.22f, f4, color);
        Render2D.drawRoundedRect(class_45872, f + f3 * 0.02f, f2 + f3 * 0.06f, f3 * 0.12f, f3 * 0.26f, f4, color);
    }

    public static void j(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = f3 * 0.68f;
        float f5 = f3 * 0.78f;
        float f6 = f2 - f5 * 0.42f;
        Render2D.drawRoundedRect(class_45872, f - f4 * 0.5f, f6, f4, f5, f3 * 0.08f, color);
        Color color2 = CosmeticColorUtils.c(color, 0.35f);
        float f7 = f4 * 0.58f;
        float f8 = f5 * 0.82f;
        Render2D.drawRoundedRect(class_45872, f - f7 * 0.5f, f6 + f5 * 0.08f, f7, f8, f3 * 0.06f, color2);
    }

    public static void k(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = f3 * 0.62f;
        float f5 = f3 * 0.12f;
        float f6 = f3 * 0.4f;
        float f7 = f3 * 0.35f;
        Render2D.drawRoundedRect(class_45872, f - f4 * 0.5f, f2 + f3 * 0.12f, f4, f5, f5 * 0.5f, color);
        Render2D.drawRoundedRect(class_45872, f - f6 * 0.5f, f2 - f3 * 0.22f, f6, f7, f3 * 0.07f, color);
        Render2D.drawRoundedRect(class_45872, f - f6 * 0.5f, f2 + f3 * 0.04f, f6, f3 * 0.07f, f3 * 0.035f, CosmeticColorUtils.c(color, 0.5f));
    }

    public static void l(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = f3 * 0.34f;
        float f5 = f3 * 0.78f;
        Render2D.drawRoundedRect(class_45872, f - f4 * 0.15f, f2 - f5 * 0.42f, f4, f5, f4 * 0.45f, color);
        Color color2 = CosmeticColorUtils.c(color, 0.55f);
        Render2D.drawRoundedRect(class_45872, f + f4 * 0.05f, f2 - f5 * 0.36f, f4 * 0.82f, f5 * 0.88f, f4 * 0.4f, color2);
    }

    public static void m(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = Math.max(1.6f, f3 * 0.1f);
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(45.0f));
        Render2D.drawRoundedRect(class_45872, -f4 * 0.5f, -f3 * 0.27f, f4, f3 * 0.54f, f4 * 0.5f, color);
        class_45872.method_22909();
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(-45.0f));
        Render2D.drawRoundedRect(class_45872, -f4 * 0.5f, -f3 * 0.27f, f4, f3 * 0.54f, f4 * 0.5f, color);
        class_45872.method_22909();
    }

    public static void n(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = f3 * 0.24f;
        Render2D.drawRing(class_45872, f, f2, f4, Math.max(1.2f, f3 * 0.05f), color);
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(-45.0f));
        Render2D.drawRoundedRect(class_45872, -f4 * 0.95f, -f3 * 0.03f, f4 * 1.9f, f3 * 0.06f, f3 * 0.03f, color);
        class_45872.method_22909();
    }

    public static void o(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = Math.max(2.0f, f3 * 0.18f);
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(-4.0f));
        Render2D.drawRoundedRect(class_45872, -f3 * 0.32f, -f3 * 0.05f, f3 * 0.22f, f4, f4 * 0.5f, color);
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(48.0f));
        Render2D.drawRoundedRect(class_45872, -f4 * 0.5f, -f3 * 0.36f, f4, f3 * 0.4f, f4 * 0.5f, color);
        class_45872.method_22909();
    }

    public static Color accent() {
        return CosmeticColorUtils.Qm;
    }

    public static Color lJ() {
        return CosmeticColorUtils.Qs;
    }

    @Generated
    private CosmeticLayout() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

