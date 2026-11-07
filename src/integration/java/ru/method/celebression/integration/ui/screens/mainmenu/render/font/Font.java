package ru.method.celebression.integration.ui.screens.mainmenu.render.font;

import ru.method.celebression.integration.ui.manager.fontManager.FontUtils;
import ru.method.celebression.integration.ui.manager.fontManager.RenderFonts;
import net.minecraft.client.util.math.MatrixStack;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.TextValue;
import ru.method.celebression.integration.ui.screens.mainmenu.render.MenuDrawBridge;
import ru.method.celebression.integration.ui.screens.mainmenu.render.layer.PrimitiveRenderLayer;

public record Font(String name) {
    private static final float RENDER_FONT_SCALE = 0.5f;

    public void draw(String text, float x, float y, float size, int color) {
        draw(defaultLayer(), text, x, y, size, color);
    }

    public void draw(PrimitiveRenderLayer layer, String text, float x, float y, float size, int color) {
        if (text == null || text.isEmpty() || MenuDrawBridge.context() == null) {
            return;
        }
        RenderFonts font = font(size);
        if (font == null) {
            return;
        }
        MatrixStack matrices = MenuDrawBridge.matrices();
        font.drawLeftAligned(
                matrices,
                text,
                MenuDrawBridge.toScreenX(x),
                MenuDrawBridge.toScreenY(y),
                color
        );
    }

    public void drawCentered(String text, float centerX, float y, float size, int color) {
        draw(text, centerX - getWidth(text, size) * 0.5f, y, size, color);
    }

    public float getWidth(String text, float size) {
        RenderFonts font = font(size);
        if (font == null || text == null) {
            return 0.0f;
        }
        float scaleX = MenuDrawBridge.scaleX();
        if (scaleX <= 0.0f) {
            return font.getWidth(text);
        }
        return font.getWidth(text) / scaleX;
    }

    public float getHeight(float size) {
        RenderFonts font = font(size);
        if (font == null) {
            return size;
        }
        float scaleY = MenuDrawBridge.scaleY();
        if (scaleY <= 0.0f) {
            return font.getHeight();
        }
        return font.getHeight() / scaleY;
    }

    public void drawColored(TextValue coloredText, float x, float y, float size) {
        if (coloredText == null) {
            return;
        }
        float cursorX = x;
        for (int i = 0; i < coloredText.length(); i++) {
            String ch = String.valueOf(coloredText.getText().charAt(i));
            draw(ch, cursorX, y, size, coloredText.colorAt(i));
            cursorX += getWidth(ch, size);
        }
    }

    public FontAtlas atlas() {
        return FontAtlas.INSTANCE;
    }

    private static PrimitiveRenderLayer defaultLayer() {
        return PrimitiveRenderLayer.OVERLAY;
    }

    private RenderFonts font(float size) {
        float scaleY = Math.max(0.001f, MenuDrawBridge.scaleY());
        int index = Math.max(1, Math.min(255, Math.round(size * scaleY / RENDER_FONT_SCALE)));
        return FontUtils.sf_medium[index];
    }
}
