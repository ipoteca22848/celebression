package ru.method.celebression.integration.ui.screens.mainmenu.render;

import ru.method.celebression.integration.ui.util.render.RenderUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.ColorValue;
import ru.method.celebression.integration.ui.screens.mainmenu.render.MenuDrawBridge;
import ru.method.celebression.integration.ui.screens.mainmenu.render.layer.PrimitiveRenderLayer;
import ru.method.celebression.integration.ui.util.math.geometry.Bound;
import ru.method.celebression.integration.ui.util.math.geometry.Radii;

public final class TextureUseCase {
    private static final ColorValue WHITE = ColorValue.rgb(255, 255, 255);

    private TextureUseCase() {}

    public static void close() {}

    public static void present() {}

    public static void present(PrimitiveRenderLayer layer) {}

    public static void draw(Identifier texture, Bound bound) {
        draw(texture, bound, Radii.zero(), WHITE);
    }

    public static void draw(Identifier texture, Bound bound, Radii radii, ColorValue tint) {
        draw(texture, bound, TextureRegion.full(), radii, 1.0f, tint);
    }

    public static void draw(
            Identifier texture,
            Bound bound,
            TextureRegion region,
            Radii radii,
            float alpha,
            ColorValue tint
    ) {
        if (texture == null || bound == null || MenuDrawBridge.context() == null) {
            return;
        }
        ColorValue resolved = tint == null ? WHITE : tint;
        int color = applyAlpha(resolved.toRGBA(), alpha);
        MatrixStack matrices = MenuDrawBridge.matrices();
        float radius = radii == null ? 0.0f : MenuDrawBridge.toScreenRadius(radii.lt);
        RenderUtil.drawTexture(
                matrices,
                texture,
                MenuDrawBridge.toScreenX(bound.x),
                MenuDrawBridge.toScreenY(bound.y),
                MenuDrawBridge.toScreenW(bound.width),
                MenuDrawBridge.toScreenH(bound.height),
                radius,
                color
        );
    }

    private static int applyAlpha(int rgba, float alpha) {
        int a = rgba >>> 24 & 0xFF;
        int scaled = Math.round(a * Math.max(0.0f, Math.min(1.0f, alpha)));
        return (scaled << 24) | (rgba & 0x00FFFFFF);
    }
}
