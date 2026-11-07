package ru.method.celebression.integration.ui.screens.mainmenu;

import net.minecraft.util.math.MathHelper;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.GameWindowPort;
import ru.method.celebression.integration.ui.screens.mainmenu.render.font.Font;

public final class MenuUiMetrics
{
    private static final float REFERENCE_WIDTH = 1920.0f;
    private static final float REFERENCE_HEIGHT = 1080.0f;
    private static final float MIN_UI_SCALE = 1.0f;
    private static final float MAX_UI_SCALE = 1.15f;

    private MenuUiMetrics()
    {
    }

    public static float uiScale(GameWindowPort window)
    {
        if (window == null) {
            return 1.0f;
        }

        float framebufferWidth = Math.max(1.0f, window.getFramebufferWidth());
        float framebufferHeight = Math.max(1.0f, window.getFramebufferHeight());
        float resolutionScale = Math.max(1.0f, Math.min(framebufferWidth / REFERENCE_WIDTH, framebufferHeight / REFERENCE_HEIGHT));

        return MathHelper.clamp(resolutionScale, MIN_UI_SCALE, MAX_UI_SCALE);
    }

    static float fitFontSize(Font font, String text, float preferredSize, float minSize, float maxWidth)
    {
        if (font == null || text == null || text.isEmpty() || maxWidth <= 0.0f) {
            return preferredSize;
        }

        float width = font.getWidth(text, preferredSize);
        if (width <= maxWidth || width <= 0.0f) {
            return preferredSize;
        }

        return Math.max(minSize, preferredSize * (maxWidth / width));
    }

}
