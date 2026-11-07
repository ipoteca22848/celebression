package ru.method.celebression.integration.ui.screens.mainmenu.render.layer;

public final class LayerPresentationTransform {
    public record Rect(float x, float y, float width, float height) {}

    private LayerPresentationTransform() {}

    public static void clear(PrimitiveRenderLayer layer) {}

    public static LayerPresentationTransform resolve(PrimitiveRenderLayer layer) {
        return new LayerPresentationTransform();
    }

    public Rect transformRect(float x, float y, float width, float height) {
        return new Rect(x, y, width, height);
    }
}
