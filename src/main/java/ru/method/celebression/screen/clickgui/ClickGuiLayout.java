package ru.method.celebression.screen.clickgui;

import net.minecraft.class_3532;

public final class ClickGuiLayout {
    public static final float PANEL_WIDTH = 120.0f;
    public static final float PANEL_GAP = 4.0f;
    public static final float PANELS_Y_SHIFT = 125.0f;
    public static final float PANEL_OPEN_HEIGHT = 250.0f;
    public static final float PANEL_HEADER_HEIGHT = 20.0f;
    public static final float SEARCH_WIDTH = 150.0f;
    public static final float SEARCH_HEIGHT = 18.0f;
    public static final float SEARCH_Y_SHIFT = 140.0f;
    public static final float DESCRIPTION_Y_SHIFT = 165.0f;
    public static final float EDGE_PADDING = 4.0f;
    private static final float MIN_FIT_SCALE = 0.72f;
    private static final float FIT_RELAXATION = 0.42f;

    private ClickGuiLayout() {
    }

    public static float designWidth(int n) {
        if (n <= 0) {
            return 120.0f;
        }
        return (float)n * 120.0f + (float)(n - 1) * 4.0f;
    }

    public static float halfHeightExtent() {
        float f = 131.0f;
        float f2 = 158.0f;
        return Math.max(f, f2);
    }

    public static float halfWidthExtent(int n) {
        return ClickGuiLayout.designWidth(n) / 2.0f;
    }

    public static float computeFitScale(float f, float f2, int n) {
        float f3;
        float f4;
        if (f <= 0.0f || f2 <= 0.0f) {
            return 1.0f;
        }
        float f5 = Math.max(1.0f, f / 2.0f - 4.0f);
        float f6 = Math.max(1.0f, f2 / 2.0f - 4.0f);
        float f7 = ClickGuiLayout.halfWidthExtent(n);
        float f8 = f5 / f7;
        float f9 = Math.min(1.0f, Math.min(f8, f4 = f6 / (f3 = ClickGuiLayout.halfHeightExtent())));
        if (f9 >= 1.0f) {
            return 1.0f;
        }
        float f10 = class_3532.method_16439((float)f9, (float)1.0f, (float)0.42f);
        return class_3532.method_15363((float)f10, (float)0.72f, (float)1.0f);
    }
}

