package ru.method.celebression.utils.render;

public final class LiquidPanelPresets {
    public static final float CLICKGUI_PANEL_WIDTH = 120.0f;
    public static final float CLICKGUI_PANEL_HEADER = 20.0f;
    public static final float CLICKGUI_PANEL_OPEN = 250.0f;
    public static final float CLICKGUI_SEARCH_WIDTH = 150.0f;
    public static final float CLICKGUI_SEARCH_HEIGHT = 18.0f;
    public static final float CLICKGUI_SETTINGS_WIDTH = 120.0f;
    public static final float CLICKGUI_SETTINGS_HEIGHT = 170.0f;
    public static final float CLICKGUI_CHIP_WIDTH = 42.0f;
    public static final float CLICKGUI_CHIP_HEIGHT = 12.0f;
    public static final float CLICKGUI_COLOR_PICKER_WIDTH = 152.0f;
    public static final float CLICKGUI_COLOR_PICKER_HEIGHT = 195.0f;
    public static final float HUD_TILE = 18.0f;
    public static final float TARGET_HUD_WIDTH = 116.0f;
    public static final float TARGET_HUD_HEIGHT = 31.0f;
    private static final float[] WIDTH_PRESETS = new float[]{10.0f, 18.0f, 20.0f, 42.0f, 90.0f, 98.0f, 100.0f, 110.0f, 116.0f, 120.0f, 130.0f, 140.0f, 150.0f, 152.0f, 160.0f, 170.0f, 180.0f, 200.0f, 220.0f, 250.0f};
    private static final float[] HEIGHT_PRESETS = new float[]{6.0f, 8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 22.0f, 24.0f, 27.0f, 31.0f, 32.0f, 36.0f, 40.0f, 50.0f, 60.0f, 70.0f, 80.0f, 100.0f, 120.0f, 150.0f, 170.0f, 195.0f, 200.0f, 250.0f};

    private LiquidPanelPresets() {
    }

    public static float snapWidth(float f) {
        return LiquidPanelPresets.snapPreset(f, WIDTH_PRESETS);
    }

    public static float snapHeight(float f) {
        return LiquidPanelPresets.snapPreset(f, HEIGHT_PRESETS);
    }

    private static float snapPreset(float f, float[] fArray) {
        if (f <= 0.0f) {
            return f;
        }
        float f2 = fArray[0];
        float f3 = Math.abs(f - f2);
        for (float f4 : fArray) {
            float f5 = Math.abs(f - f4);
            if (!(f5 < f3)) continue;
            f2 = f4;
            f3 = f5;
        }
        return f2;
    }
}

