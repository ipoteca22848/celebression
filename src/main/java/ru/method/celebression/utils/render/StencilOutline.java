package ru.method.celebression.utils.render;

import java.awt.Color;
import net.minecraft.class_4587;
import ru.method.celebression.utils.render.Render2D;

public final class StencilOutline {
    public static final float OUTLINE_WIDTH = 0.68f;
    public static final float SMOOTH_MIN = 0.0f;
    public static final float SMOOTH_MAX = 0.34f;

    private StencilOutline() {
    }

    public static void drawRoundedOutline(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        if (class_45872 == null || color == null || color.getAlpha() <= 0 || f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        float f7 = f6 > 0.001f ? f6 : 0.68f;
        Render2D.drawDrainwalkOutline(class_45872, f, f2, f3, f4, f5, f7, color);
    }
}

