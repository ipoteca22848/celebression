package ru.method.celebression.api.render.builders.renderers.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_10142;
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
import ru.method.celebression.api.render.builders.renderers.IRenderer;
import ru.method.celebression.api.render.builders.states.QuadColorState;
import ru.method.celebression.api.render.builders.states.QuadRadiusState;
import ru.method.celebression.api.render.builders.states.SizeState;
import ru.method.celebression.api.render.providers.ResourceProvider;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.LiquidGlassCaptureBlur;
import ru.method.celebression.utils.render.LiquidGlassReflectionCache;
import ru.method.celebression.utils.render.RenderFrameTracker;

public record BuiltBlur(SizeState size, QuadRadiusState radius, QuadColorState color, float smoothness, float blurRadius) implements IRenderer
{
    private static final class_10156 BLUR_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("blur"), class_290.field_1576, class_10149.field_53930);
    private static volatile class_6367 tempFbo;
    private static volatile long capturedFrameId;
    private static volatile int capturedDownsample;
    private static volatile boolean captureBlurred;
    private static final int MAX_ITERATIONS = 16;

    @Override
    public void render(Matrix4f matrix4f, float f, float f2, float f3) {
        float f4;
        int n;
        RenderSystem.assertOnRenderThread();
        class_276 class_2762 = class_310.method_1551().method_1522();
        int n2 = class_2762.field_1482;
        int n3 = class_2762.field_1481;
        if (n2 <= 0 || n3 <= 0) {
            return;
        }
        float f5 = Math.max(0.0f, Math.abs(this.blurRadius));
        int n4 = BuiltBlur.downsampleFactor(f5);
        int n5 = Math.max(1, n2 / n4);
        int n6 = Math.max(1, n3 / n4);
        class_6367 class_63672 = BuiltBlur.ensureTempFramebuffer(n5, n6);
        BuiltBlur.captureSceneIfNeeded(class_2762, class_63672, n4, f5 / (float)n4);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShaderTexture((int)0, (int)class_63672.method_30277());
        float f6 = this.size.width();
        float f7 = this.size.height();
        class_5944 class_59442 = RenderSystem.setShader((class_10156)BLUR_SHADER_KEY);
        if (class_59442 == null) {
            BuiltBlur.drawFallbackQuad(matrix4f, f, f2, f6, f7, f3, this.color.color1(), this.color.color2(), this.color.color3(), this.color.color4());
            RenderSystem.setShaderTexture((int)0, (int)0);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            return;
        }
        class_59442.method_34582("Size").method_1255(f6, f7);
        class_59442.method_34582("Radius").method_35657(this.radius.radius1(), this.radius.radius2(), this.radius.radius3(), this.radius.radius4());
        class_59442.method_34582("Smoothness").method_1251(this.smoothness);
        class_59442.method_34582("Resolution").method_1255((float)class_63672.field_1482, (float)class_63672.field_1481);
        if (f5 <= 0.001f) {
            n = 0;
            f4 = 0.0f;
        } else if (captureBlurred) {
            n = 0;
            f4 = 0.0f;
        } else {
            float f8 = f5 / (float)n4;
            int n7 = ColorUtils.isClickGuiOpen() ? 4 : (ColorUtils.isOverlayGuiOpen() ? 5 : (n4 == 1 ? 16 : (n4 == 2 ? 12 : 8)));
            n = Math.max(1, Math.min(n7, Math.round(f8)));
            f4 = f8 / (float)n;
        }
        class_59442.method_34582("BlurRadius").method_1251(f4);
        class_59442.method_34582("Iterations").method_35649(n);
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        class_2872.method_22918(matrix4f, f, f2, f3).method_39415(this.color.color1());
        class_2872.method_22918(matrix4f, f, f2 + f7, f3).method_39415(this.color.color2());
        class_2872.method_22918(matrix4f, f + f6, f2 + f7, f3).method_39415(this.color.color3());
        class_2872.method_22918(matrix4f, f + f6, f2, f3).method_39415(this.color.color4());
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.setShaderTexture((int)0, (int)0);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void drawFallbackQuad(Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, int n, int n2, int n3, int n4) {
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        class_2872.method_22918(matrix4f, f, f2, f5).method_39415(n);
        class_2872.method_22918(matrix4f, f, f2 + f4, f5).method_39415(n2);
        class_2872.method_22918(matrix4f, f + f3, f2 + f4, f5).method_39415(n3);
        class_2872.method_22918(matrix4f, f + f3, f2, f5).method_39415(n4);
        class_286.method_43433((class_9801)class_2872.method_60800());
    }

    public static void renderBackdrop(Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, int n) {
        RenderSystem.assertOnRenderThread();
        class_276 class_2762 = class_310.method_1551().method_1522();
        int n2 = class_2762.field_1482;
        int n3 = class_2762.field_1481;
        if (n2 <= 0 || n3 <= 0 || f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        float f6 = Math.max(0.0f, f5);
        int n4 = Math.max(2, Math.min(3, Math.round(f6 / 18.0f)));
        int n5 = Math.max(1, n2 / n4);
        int n6 = Math.max(1, n3 / n4);
        class_6367 class_63672 = BuiltBlur.ensureTempFramebuffer(n5, n6);
        capturedFrameId = -1L;
        LiquidGlassReflectionCache.blitColor(class_2762, (class_276)class_63672);
        capturedFrameId = RenderFrameTracker.getFrameId();
        capturedDownsample = n4;
        captureBlurred = false;
        boolean bl = GL11.glIsEnabled((int)3042);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShaderTexture((int)0, (int)class_63672.method_30277());
        class_5944 class_59442 = RenderSystem.setShader((class_10156)BLUR_SHADER_KEY);
        if (class_59442 == null) {
            BuiltBlur.drawFallbackQuad(matrix4f, f, f2, f3, f4, 0.0f, n, n, n, n);
            RenderSystem.setShaderTexture((int)0, (int)0);
            RenderSystem.enableCull();
            if (!bl) {
                RenderSystem.disableBlend();
            }
            return;
        }
        class_59442.method_34582("Size").method_1255(f3, f4);
        class_59442.method_34582("Radius").method_35657(0.0f, 0.0f, 0.0f, 0.0f);
        class_59442.method_34582("Smoothness").method_1251(0.001f);
        class_59442.method_34582("Resolution").method_1255((float)class_63672.field_1482, (float)class_63672.field_1481);
        float f7 = f6 / (float)n4;
        int n7 = Math.max(2, Math.min(6, Math.round(f7)));
        float f8 = f7 / (float)n7;
        class_59442.method_34582("BlurRadius").method_1251(f8);
        class_59442.method_34582("Iterations").method_35649(n7);
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        class_2872.method_22918(matrix4f, f, f2, 0.0f).method_39415(n);
        class_2872.method_22918(matrix4f, f, f2 + f4, 0.0f).method_39415(n);
        class_2872.method_22918(matrix4f, f + f3, f2 + f4, 0.0f).method_39415(n);
        class_2872.method_22918(matrix4f, f + f3, f2, 0.0f).method_39415(n);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.setShaderTexture((int)0, (int)0);
        RenderSystem.enableCull();
        if (!bl) {
            RenderSystem.disableBlend();
        }
    }

    private static void captureSceneIfNeeded(class_276 class_2762, class_6367 class_63672, int n, float f) {
        long l = RenderFrameTracker.getFrameId();
        class_6367 class_63673 = tempFbo;
        if (class_63673 != class_63672) {
            capturedFrameId = -1L;
            capturedDownsample = 1;
            captureBlurred = false;
        }
        if (capturedFrameId == l && capturedDownsample == n) {
            return;
        }
        LiquidGlassReflectionCache.blitColor(class_2762, (class_276)class_63672);
        boolean bl = captureBlurred = f >= 0.5f;
        if (captureBlurred) {
            LiquidGlassCaptureBlur.apply(class_63672, f);
        }
        capturedFrameId = l;
        capturedDownsample = n;
    }

    private static class_6367 ensureTempFramebuffer(int n, int n2) {
        class_6367 class_63672 = tempFbo;
        if (class_63672 == null || class_63672.field_1482 != n || class_63672.field_1481 != n2) {
            if (class_63672 != null) {
                class_63672.method_1238();
            }
            class_63672 = new class_6367(n, n2, false);
            BuiltBlur.configureTextureFilter(class_63672);
            tempFbo = class_63672;
            capturedFrameId = -1L;
            capturedDownsample = 1;
            captureBlurred = false;
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

    private static int downsampleFactor(float f) {
        if (ColorUtils.isClickGuiOpen()) {
            return 5;
        }
        if (ColorUtils.isOverlayGuiOpen()) {
            return 4;
        }
        if (f >= 36.0f) {
            return 3;
        }
        if (f >= 8.0f) {
            return 2;
        }
        return 1;
    }

    public static void releaseTempFramebuffer() {
        class_6367 class_63672 = tempFbo;
        if (class_63672 != null) {
            class_63672.method_1238();
            tempFbo = null;
            capturedFrameId = -1L;
            capturedDownsample = 1;
        }
    }

    static {
        capturedFrameId = -1L;
        capturedDownsample = 1;
    }
}

