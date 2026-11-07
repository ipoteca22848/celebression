package ru.method.celebression.render;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Generated;
import org.lwjgl.opengl.GL11;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class OpenGlStateUtils
implements MinecraftClientAccess {
    public static void rd() {
        if (aiG == null || aiG.method_1522() == null) {
            return;
        }
        aiG.method_1522().method_1235(false);
        GL11.glClear((int)1024);
        GL11.glEnable((int)2960);
        GL11.glStencilFunc((int)519, (int)1, (int)255);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        RenderSystem.colorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
    }

    public static void bg(int n) {
        RenderSystem.colorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glStencilFunc((int)514, (int)n, (int)255);
        GL11.glStencilOp((int)7680, (int)7680, (int)7680);
    }

    public static void re() {
        GL11.glDisable((int)2960);
    }

    @Generated
    private OpenGlStateUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

