package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

public final class DepthRenderScope
implements AutoCloseable {
    private final boolean depthTestEnabled = GL11.glIsEnabled((int)2929);
    private final int previousDepthFunc = this.depthTestEnabled ? GL11.glGetInteger((int)2932) : 515;

    private DepthRenderScope() {
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc((int)515);
    }

    public static DepthRenderScope lequal() {
        return new DepthRenderScope();
    }

    @Override
    public void close() {
        if (this.depthTestEnabled) {
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc((int)this.previousDepthFunc);
        } else {
            RenderSystem.disableDepthTest();
        }
    }
}

