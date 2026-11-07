package ru.method.celebression.integration.ui.screens.mainmenu.render.layer;

public final class RenderLayerContext
{
    private static final ThreadLocal<PrimitiveRenderLayer> ACTIVE = new ThreadLocal<>();

    private RenderLayerContext()
    {
    }

    public static PrimitiveRenderLayer activeOr(PrimitiveRenderLayer fallback)
    {
        PrimitiveRenderLayer layer = ACTIVE.get();
        return layer != null ? layer : fallback;
    }

    public static void run(PrimitiveRenderLayer layer, Runnable action)
    {
        ACTIVE.set(layer);
        try
        {
            action.run();
        }
        finally
        {
            ACTIVE.remove();
        }
    }
}
