package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_276;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_310;
import net.minecraft.class_5944;
import net.minecraft.class_6367;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import ru.method.celebression.api.render.providers.ResourceProvider;
import ru.method.celebression.utils.debug.RenderProfiler;

public final class LiquidGlassCaptureBlur {
    private static final class_10156 CAPTURE_BLUR_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("captureblur"), class_290.field_1585, class_10149.field_53930);
    private static volatile class_6367 pingPong;

    private LiquidGlassCaptureBlur() {
    }

        public static void apply(class_6367 class_63672, float f) {
        RenderSystem.assertOnRenderThread();
        if (class_63672 == null || f < 0.5f) {
            return;
        }
        int n = class_63672.field_1482;
        int n2 = class_63672.field_1481;
        if (n <= 0 || n2 <= 0) {
            return;
        }
        int[] nArray = new int[4];
        GL11.glGetIntegerv((int)2978, (int[])nArray);
        class_276 class_2762 = class_310.method_1551().method_1522();
        boolean bl = GL11.glIsEnabled((int)3042);
        boolean bl2 = GL11.glIsEnabled((int)2929);
        try {
            class_6367 class_63673 = LiquidGlassCaptureBlur.ensurePingPong(n, n2);
            int n3 = Math.max(2, Math.min(4, Math.round(f / 2.5f)));
            RenderProfiler.incrementCounter("glass.blurPasses", n3 * 2);
            for (int i = 0; i < n3; ++i) {
                LiquidGlassCaptureBlur.blurPass((class_276)class_63672, (class_276)class_63673, n, n2, 1.0f, 0.0f);
                LiquidGlassCaptureBlur.blurPass((class_276)class_63673, (class_276)class_63672, n, n2, 0.0f, 1.0f);
            }
        }
        finally {
            if (class_2762 != null) {
                class_2762.method_1235(false);
            }
            GL11.glViewport((int)nArray[0], (int)nArray[1], (int)nArray[2], (int)nArray[3]);
            if (bl2) {
                RenderSystem.enableDepthTest();
            }
            if (bl) {
                RenderSystem.enableBlend();
            }
        }
    }

    public static void release() {
        class_6367 class_63672 = pingPong;
        if (class_63672 != null) {
            class_63672.method_1238();
            pingPong = null;
        }
    }

    private static void blurPass(class_276 class_2762, class_276 class_2763, int n, int n2, float f, float f2) {
        class_2763.method_1235(false);
        RenderSystem.viewport((int)0, (int)0, (int)n, (int)n2);
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.setShaderTexture((int)0, (int)class_2762.method_30277());
        class_5944 class_59442 = RenderSystem.setShader((class_10156)CAPTURE_BLUR_SHADER_KEY);
        class_59442.method_34582("TexelSize").method_1255(1.0f / (float)n, 1.0f / (float)n2);
        class_59442.method_34582("Direction").method_1255(f, f2);
        Matrix4f matrix4f = new Matrix4f().ortho(0.0f, (float)n, (float)n2, 0.0f, -1.0f, 1.0f);
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1585);
        class_2872.method_22918(matrix4f, 0.0f, 0.0f, 0.0f).method_22913(0.0f, 0.0f);
        class_2872.method_22918(matrix4f, 0.0f, (float)n2, 0.0f).method_22913(0.0f, 1.0f);
        class_2872.method_22918(matrix4f, (float)n, (float)n2, 0.0f).method_22913(1.0f, 1.0f);
        class_2872.method_22918(matrix4f, (float)n, 0.0f, 0.0f).method_22913(1.0f, 0.0f);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.setShaderTexture((int)0, (int)0);
    }

    private static class_6367 ensurePingPong(int n, int n2) {
        class_6367 class_63672 = pingPong;
        if (class_63672 == null || class_63672.field_1482 != n || class_63672.field_1481 != n2) {
            if (class_63672 != null) {
                class_63672.method_1238();
            }
            class_63672 = new class_6367(n, n2, false);
            LiquidGlassCaptureBlur.configureTextureFilter(class_63672);
            pingPong = class_63672;
        }
        return class_63672;
    }

    private static void configureTextureFilter(class_6367 class_63672) {
        class_63672.method_58226(9729);
        RenderSystem.bindTexture((int)class_63672.method_30277());
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        RenderSystem.bindTexture((int)0);
    }
}

