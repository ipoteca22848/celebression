package ru.method.celebression.util;

import ru.method.celebression.animation.Animation;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.class_4587;
import ru.method.celebression.animation.InterpolationUtils;
import ru.method.celebression.render.LinkGuiBrandRenderer;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class TextLayoutUtils {
    public static final float WR = 24.0f;
    public static final float WS = 8.6f;
    public static final float WT = 7.8f;

    public static float al(float f) {
        return f + (24.0f - TextLayoutUtils.am(8.6f)) / 2.0f;
    }

    public static float am(float f) {
        return UiRenderUtils.a(Fonts.MEDIUM, f);
    }

    public static void a(class_4587 class_45872, String string, float f, float f2, float f3) {
        Color color = ThemeTransition.d(220);
        float f4 = TextLayoutUtils.al(f2);
        String string2 = TextLayoutUtils.d(string, f3, 8.6f);
        UiRenderUtils.a(class_45872, Fonts.MEDIUM, string2, f, f4, color, 8.6f);
    }

    public static void a(class_4587 class_45872, String string, float f, float f2, float f3, float f4, float f5) {
        LinkGuiBrandRenderer.h(class_45872, f, f2, f3, f4, 5.0f, f5);
        float f6 = Fonts.MEDIUM.getWidth(string, 7.8f);
        float f7 = TextLayoutUtils.am(7.8f);
        UiRenderUtils.a(class_45872, Fonts.MEDIUM, string, f + (f3 - f6) / 2.0f, f2 + (f4 - f7) / 2.0f, ThemeTransition.d((int)(200.0f + 55.0f * f5)), 7.8f);
    }

    public static void i(class_4587 class_45872, float f, float f2, float f3) {
        LinkGuiBrandRenderer.i(class_45872, f, f2, 30.0f, 15.0f, f3);
    }

    public static String d(String string, float f, float f2) {
        if (Fonts.MEDIUM.getWidth(string, f2) <= f) {
            return string;
        }
        while (string.length() > 3 && Fonts.MEDIUM.getWidth(string + "...", f2) > f) {
            string = string.substring(0, string.length() - 1);
        }
        return string + "...";
    }

    public static Color an(float f) {
        Color color = ThemeTransition.accent();
        return InterpolationUtils.e(color, f);
    }

    @Generated
    private TextLayoutUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

