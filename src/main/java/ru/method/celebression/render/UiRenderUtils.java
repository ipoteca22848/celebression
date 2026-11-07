package ru.method.celebression.render;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.class_4587;
import ru.method.celebression.render.RoundedOutlineRenderer;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Instance;

public final class UiRenderUtils {
    public static void c(class_4587 class_45872, float f, float f2, float f3, float f4, Color color) {
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, 0.0f, color);
    }

    public static void drawRoundedRect(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color) {
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, color);
    }

    public static void c(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        RoundedOutlineRenderer.b(class_45872, f, f2, f3, f4, f5, f6, color);
    }

    public static void d(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color, Color color2) {
        RoundedOutlineRenderer.a(class_45872, f, f2, f3, f4, f5, f6, color, color2);
    }

    public static void e(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color, Color color2) {
        RoundedOutlineRenderer.b(class_45872, f, f2, f3, f4, f5, f6, color, color2);
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, Color color2) {
        ColorRGBA colorRGBA = new ColorRGBA(color);
        ColorRGBA colorRGBA2 = new ColorRGBA(color2);
        Render2D.drawRect(class_45872, f, f2, f3, f4, f5, colorRGBA, colorRGBA2, colorRGBA2, colorRGBA);
    }

    public static void f(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color, Color color2) {
        RoundedOutlineRenderer.c(class_45872, f, f2, f3, f4, f5, f6, color, color2);
    }

    public static void b(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, Color color2) {
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, f5, color, color2);
    }

    public static void c(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, Color color2) {
        Render2D.drawRoundedHorizontalGradient(class_45872, f, f2, f3, f4, f5, color, color2);
    }

    public static void a(class_4587 class_45872, Font font, String string, float f, float f2, Color color, float f3) {
        Render2D.drawFont(class_45872, font.getFont(f3), string, f, f2, color);
    }

    public static void b(class_4587 class_45872, Font font, String string, float f, float f2, Color color, float f3) {
        Instance instance = font.getFont(f3);
        float f4 = instance.getWidth(string);
        Render2D.drawFont(class_45872, instance, string, f - f4 / 2.0f, f2, color);
    }

    public static float a(Font font, String string, float f) {
        return font.getWidth(string, f);
    }

    public static float a(Font font, float f) {
        return font.getHeight(f);
    }

    @Generated
    private UiRenderUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

