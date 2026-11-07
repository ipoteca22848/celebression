package ru.method.celebression.integration.ui.screens.mainmenu.render.layer;

import ru.method.celebression.integration.ui.screens.mainmenu.infra.MinecraftClientProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum PrimitiveRenderLayer
{

    OVERLAY
    (
            false,
            true,
            false
    ),

    MENU_BACKDROP
    (
            true,
            false,
            true
    ),

    MENU
    (
            true,
            true,
            true
    ),

    GUI
    (
            true,
            true,
            false
    );

    private static final float FIXED_MENU_SCALE = 2.0f;
    private static final PrimitiveRenderLayer[] COMPOSITED_LAYERS = {MENU_BACKDROP, MENU};

    boolean offscreen;
    boolean fontPresentationEnabled;
    boolean transientStateful;

    public static PrimitiveRenderLayer[] compositedLayers()
    {
        return COMPOSITED_LAYERS.clone();
    }

    public void clearFrameState()
    {
        if (!transientStateful) return;

        LayerBurnMask.clear(this);
        LayerBlurState.clear(this);
        LayerPresentationTransform.clear(this);
    }

    public int screenWidth()
    {
        return switch (this)
        {
            case OVERLAY, GUI -> MinecraftClientProvider.getMinecraft().getWindow().getFramebufferWidth();
            case MENU_BACKDROP, MENU -> (int) Math.ceil(
                    MinecraftClientProvider.getMinecraft().getWindow().getWidth() / FIXED_MENU_SCALE
            );
        };
    }

    public int screenHeight()
    {
        return switch (this)
        {
            case OVERLAY, GUI -> MinecraftClientProvider.getMinecraft().getWindow().getFramebufferHeight();
            case MENU_BACKDROP, MENU -> (int) Math.ceil(
                    MinecraftClientProvider.getMinecraft().getWindow().getHeight() / FIXED_MENU_SCALE
            );
        };
    }
}
