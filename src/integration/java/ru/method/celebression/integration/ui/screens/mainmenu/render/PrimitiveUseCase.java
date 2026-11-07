package ru.method.celebression.integration.ui.screens.mainmenu.render;

import ru.method.celebression.integration.ui.util.render.RenderUtil;
import net.minecraft.client.util.math.MatrixStack;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.ColorValue;
import ru.method.celebression.integration.ui.screens.mainmenu.render.MenuDrawBridge;
import ru.method.celebression.integration.ui.screens.mainmenu.render.layer.PrimitiveRenderLayer;
import ru.method.celebression.integration.ui.screens.mainmenu.render.layer.RenderLayerContext;
import org.joml.Vector4f;
import ru.method.celebression.integration.ui.util.math.geometry.Bound;
import ru.method.celebression.integration.ui.util.math.geometry.Radii;

public final class PrimitiveUseCase {
    private static final float OUTLINE_THIN_FACTOR = 1.0f / 2.25f;

    private PrimitiveUseCase() {}

    public static void close() {}

    public static void present() {}

    public static void present(PrimitiveRenderLayer layer) {}

    public static void rectangle(Bound bound, Radii radii, ColorValue colorValue) {
        if (bound == null || colorValue == null || MenuDrawBridge.context() == null) {
            return;
        }
        float radius = radii == null ? 0.0f : MenuDrawBridge.toScreenRadius(radii.lt);
        drawRect(bound, radius, colorValue.toRGBA(), 1.0f);
    }

    public static void rectangleCorners(
            Bound bound,
            float topLeft,
            float bottomLeft,
            float bottomRight,
            float topRight,
            ColorValue colorValue,
            float smoothness
    ) {
        if (bound == null || colorValue == null || MenuDrawBridge.context() == null) {
            return;
        }
        MatrixStack matrices = MenuDrawBridge.matrices();
        Vector4f radii = new Vector4f(
                MenuDrawBridge.toScreenRadius(topLeft),
                MenuDrawBridge.toScreenRadius(bottomLeft),
                MenuDrawBridge.toScreenRadius(bottomRight),
                MenuDrawBridge.toScreenRadius(topRight)
        );
        RenderUtil.drawRoundedRect(
                matrices,
                MenuDrawBridge.toScreenX(bound.x),
                MenuDrawBridge.toScreenY(bound.y),
                MenuDrawBridge.toScreenW(bound.width),
                MenuDrawBridge.toScreenH(bound.height),
                radii,
                smoothness,
                colorValue.toRGBA()
        );
    }

    public static void rectangleHorizontal(Bound bound, Radii radii, ColorValue left, ColorValue right) {
        if (bound == null || left == null || right == null || MenuDrawBridge.context() == null) {
            return;
        }
        MatrixStack matrices = MenuDrawBridge.matrices();
        float x = MenuDrawBridge.toScreenX(bound.x);
        float y = MenuDrawBridge.toScreenY(bound.y);
        float w = MenuDrawBridge.toScreenW(bound.width);
        float h = MenuDrawBridge.toScreenH(bound.height);
        float radius = radii == null ? 0.0f : MenuDrawBridge.toScreenRadius(radii.lt);
        int c1 = left.toRGBA();
        int c2 = right.toRGBA();
        RenderUtil.rectRGB(matrices, x, y, w, h, radius, c1, c2, c2, c1);
    }

    public static void rectangleVertical(Bound bound, Radii radii, ColorValue top, ColorValue bottom) {
        if (bound == null || top == null || bottom == null || MenuDrawBridge.context() == null) {
            return;
        }
        MatrixStack matrices = MenuDrawBridge.matrices();
        float x = MenuDrawBridge.toScreenX(bound.x);
        float y = MenuDrawBridge.toScreenY(bound.y);
        float w = MenuDrawBridge.toScreenW(bound.width);
        float h = MenuDrawBridge.toScreenH(bound.height);
        float radius = radii == null ? 0.0f : MenuDrawBridge.toScreenRadius(radii.lt);
        int c1 = top.toRGBA();
        int c2 = bottom.toRGBA();
        RenderUtil.rectRGB(matrices, x, y, w, h, radius, c1, c1, c2, c2);
    }

    public static void strokeInside(Bound bound, Radii radii, float thickness, ColorValue colorValue) {
        if (bound == null || colorValue == null || MenuDrawBridge.context() == null) {
            return;
        }
        MatrixStack matrices = MenuDrawBridge.matrices();
        float x = MenuDrawBridge.toScreenX(bound.x);
        float y = MenuDrawBridge.toScreenY(bound.y);
        float w = MenuDrawBridge.toScreenW(bound.width);
        float h = MenuDrawBridge.toScreenH(bound.height);
        float radius = radii == null ? 0.0f : MenuDrawBridge.toScreenRadius(radii.lt);
        float border = Math.max(0.25f, thickness * Math.min(MenuDrawBridge.scaleX(), MenuDrawBridge.scaleY()) * OUTLINE_THIN_FACTOR);
        RenderUtil.drawRoundedBorder(matrices, x, y, w, h, radius, border, colorValue.toRGBA());
    }

    private static void drawRect(Bound bound, float radius, int color, float smoothness) {
        MatrixStack matrices = MenuDrawBridge.matrices();
        RenderUtil.drawRoundedRect(
                matrices,
                MenuDrawBridge.toScreenX(bound.x),
                MenuDrawBridge.toScreenY(bound.y),
                MenuDrawBridge.toScreenW(bound.width),
                MenuDrawBridge.toScreenH(bound.height),
                new Vector4f(radius, radius, radius, radius),
                smoothness,
                color
        );
    }
}
