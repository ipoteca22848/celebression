package ru.method.celebression.api.render.builders.renderers.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_276;
import net.minecraft.class_284;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_310;
import net.minecraft.class_5944;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL11;
import ru.method.celebression.api.render.builders.Builder;
import ru.method.celebression.api.render.builders.renderers.IRenderer;
import ru.method.celebression.api.render.builders.renderers.impl.BuiltRectangle;
import ru.method.celebression.api.render.builders.states.QuadColorState;
import ru.method.celebression.api.render.builders.states.QuadRadiusState;
import ru.method.celebression.api.render.builders.states.SizeState;
import ru.method.celebression.api.render.providers.ResourceProvider;
import ru.method.celebression.utils.debug.ChatProfiler;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.LiquidGlassReflectionCache;
import ru.method.celebression.utils.render.RenderFrameTracker;

public record BuiltLiquidGlassNew(SizeState size, QuadRadiusState radius, QuadColorState color, float smoothness, float cornerSmoothness, float globalAlpha, float fresnelPower, Color fresnelColor, float fresnelAlpha, float baseAlpha, boolean fresnelInvert, float fresnelMix, float distortStrength, float blurRadius, LiquidGlassReflectionCache.ReflectionSource reflectionSource) implements IRenderer
{
    private static final class_10156 LIQUID_GLASS_NEW_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("liquidglassnew"), class_290.field_1575, class_10149.field_53930);
    private static final ThreadLocal<List<BatchEntry>> BATCH_QUEUE = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal<Boolean> BATCHING = ThreadLocal.withInitial(() -> Boolean.FALSE);
    private static long resolvedFrameId = -1L;
    private static class_276 resolvedGameplayFramebuffer;
    private static class_276 resolvedMainMenuFramebuffer;
    private static class_276 resolvedClickGuiFramebuffer;

    public static void beginBatch() {
        BATCHING.set(Boolean.TRUE);
        BATCH_QUEUE.get().clear();
    }

    public static void flushBatch() {
        BATCHING.set(Boolean.FALSE);
        List<BatchEntry> list = BATCH_QUEUE.get();
        if (list.isEmpty()) {
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        long l = ChatProfiler.startSection();
        LinkedHashMap<BuiltLiquidGlassNew, List> linkedHashMap = new LinkedHashMap<BuiltLiquidGlassNew, List>();
        for (BatchEntry batchEntry : list) {
            linkedHashMap.computeIfAbsent(batchEntry.style(), builtLiquidGlassNew -> new ArrayList()).add(batchEntry);
        }
        list.clear();
        boolean bl = GL11.glIsEnabled((int)3042);
        boolean bl2 = GL11.glIsEnabled((int)2884);
        boolean bl3 = GL11.glIsEnabled((int)2929);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            ((BuiltLiquidGlassNew)entry.getKey()).renderBatchGroup(class_3102, (List)entry.getValue());
        }
        RenderSystem.setShaderTexture((int)0, (int)0);
        if (bl3) {
            RenderSystem.enableDepthTest();
        } else {
            RenderSystem.disableDepthTest();
        }
        if (bl2) {
            RenderSystem.enableCull();
        } else {
            RenderSystem.disableCull();
        }
        RenderSystem.defaultBlendFunc();
        if (!bl) {
            RenderSystem.disableBlend();
        }
        ChatProfiler.endSection("liquidBatchFlush", l);
    }

    public static boolean isBatching() {
        return Boolean.TRUE.equals(BATCHING.get());
    }

    @Override
    public void render(Matrix4f matrix4f, float f, float f2, float f3) {
        this.render(matrix4f, f, f2, f3, f, f2, this.size.width(), this.size.height());
    }

    public void render(Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5) {
        this.render(matrix4f, f, f2, f3, f, f2, f4, f5);
    }

    public void render(Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        if (Boolean.TRUE.equals(BATCHING.get())) {
            BATCH_QUEUE.get().add(new BatchEntry(new Matrix4f((Matrix4fc)matrix4f), f, f2, f3, f4, f5, f6, f7, this));
            return;
        }
        this.renderImmediate(matrix4f, f, f2, f3, f4, f5, f6, f7);
    }

    private void renderBatchGroup(class_310 class_3102, List<BatchEntry> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        LiquidGlassReflectionCache.ReflectionSource reflectionSource = this.reflectionSource == null ? LiquidGlassReflectionCache.ReflectionSource.GAMEPLAY : this.reflectionSource;
        class_276 class_2762 = BuiltLiquidGlassNew.resolveFramebuffer(class_3102, reflectionSource);
        if (class_2762 == null || class_2762.field_1482 <= 0 || class_2762.field_1481 <= 0) {
            for (BatchEntry batchEntry : list) {
                this.renderFallbackGlass(batchEntry.matrix(), batchEntry.x(), batchEntry.y());
            }
            return;
        }
        RenderSystem.setShaderTexture((int)0, (int)class_2762.method_30277());
        if (!this.bindShaderUniforms(class_3102, class_2762)) {
            for (BatchEntry batchEntry : list) {
                this.renderFallbackGlass(batchEntry.matrix(), batchEntry.x(), batchEntry.y());
            }
            return;
        }
        float f = this.size.width();
        float f2 = this.size.height();
        int n = class_2762.field_1482;
        int n2 = class_2762.field_1481;
        float f3 = (float)n / (float)class_3102.method_22683().method_4486();
        float f4 = (float)n2 / (float)class_3102.method_22683().method_4502();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        for (BatchEntry batchEntry : list) {
            this.appendQuad(class_2872, batchEntry, f, f2, n, n2, f3, f4);
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
    }

    private void renderImmediate(Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        LiquidGlassReflectionCache.ReflectionSource reflectionSource;
        class_310 class_3102 = class_310.method_1551();
        class_276 class_2762 = BuiltLiquidGlassNew.resolveFramebuffer(class_3102, reflectionSource = this.reflectionSource == null ? LiquidGlassReflectionCache.ReflectionSource.GAMEPLAY : this.reflectionSource);
        if (class_2762 == null || class_2762.field_1482 <= 0 || class_2762.field_1481 <= 0) {
            this.renderFallbackGlass(matrix4f, f, f2);
            return;
        }
        long l = ChatProfiler.startSection();
        boolean bl = GL11.glIsEnabled((int)3042);
        boolean bl2 = GL11.glIsEnabled((int)2884);
        boolean bl3 = GL11.glIsEnabled((int)2929);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.setShaderTexture((int)0, (int)class_2762.method_30277());
        if (!this.bindShaderUniforms(class_3102, class_2762)) {
            BuiltLiquidGlassNew.restoreRenderState(bl, bl2, bl3);
            this.renderFallbackGlass(matrix4f, f, f2);
            return;
        }
        float f8 = this.size.width();
        float f9 = this.size.height();
        int n = class_2762.field_1482;
        int n2 = class_2762.field_1481;
        float f10 = (float)n / (float)class_3102.method_22683().method_4486();
        float f11 = (float)n2 / (float)class_3102.method_22683().method_4502();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        this.appendQuad(class_2872, new BatchEntry(matrix4f, f, f2, f3, f4, f5, f6, f7, this), f8, f9, n, n2, f10, f11);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.setShaderTexture((int)0, (int)0);
        BuiltLiquidGlassNew.restoreRenderState(bl, bl2, bl3);
        ChatProfiler.endSection("liquidShader", l);
    }

    private static void restoreRenderState(boolean bl, boolean bl2, boolean bl3) {
        RenderSystem.setShaderTexture((int)0, (int)0);
        if (bl3) {
            RenderSystem.enableDepthTest();
        } else {
            RenderSystem.disableDepthTest();
        }
        if (bl2) {
            RenderSystem.enableCull();
        } else {
            RenderSystem.disableCull();
        }
        RenderSystem.defaultBlendFunc();
        if (!bl) {
            RenderSystem.disableBlend();
        }
    }

    private boolean bindShaderUniforms(class_310 class_3102, class_276 class_2762) {
        float f = this.size.width();
        float f2 = this.size.height();
        class_5944 class_59442 = RenderSystem.setShader((class_10156)LIQUID_GLASS_NEW_SHADER_KEY);
        if (class_59442 == null) {
            return false;
        }
        class_284 class_2842 = class_59442.method_34582("Size");
        if (class_2842 == null) {
            return false;
        }
        class_2842.method_1255(f, f2);
        class_59442.method_34582("Radius").method_35657(this.radius.radius1(), this.radius.radius2(), this.radius.radius3(), this.radius.radius4());
        class_59442.method_34582("Smoothness").method_1251(this.smoothness);
        class_59442.method_34582("CornerSmoothness").method_1251(this.cornerSmoothness);
        float f3 = Math.max(0.0f, Math.min(1.0f, this.globalAlpha * this.extractMaxAlpha()));
        class_59442.method_34582("GlobalAlpha").method_1251(f3);
        class_59442.method_34582("FresnelPower").method_1251(this.fresnelPower);
        Color color = this.fresnelColor == null ? Color.WHITE : this.fresnelColor;
        class_59442.method_34582("FresnelColor").method_1249((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f);
        class_59442.method_34582("FresnelAlpha").method_1251(this.fresnelAlpha);
        class_59442.method_34582("BaseAlpha").method_1251(this.baseAlpha);
        class_59442.method_34582("FresnelInvert").method_35649(this.fresnelInvert ? 1 : 0);
        class_59442.method_34582("FresnelMix").method_1251(this.fresnelMix);
        class_59442.method_34582("DistortStrength").method_1251(this.distortStrength);
        int n = class_2762.field_1482;
        int n2 = class_2762.field_1481;
        float f4 = this.resolveCaptureScale(class_3102, n, n2);
        class_59442.method_34582("BlurRadius").method_1251(Math.max(0.0f, this.blurRadius) * f4);
        class_59442.method_34582("FramebufferSize").method_1255((float)n, (float)n2);
        return true;
    }

    private void appendQuad(class_287 class_2872, BatchEntry batchEntry, float f, float f2, int n, int n2, float f3, float f4) {
        float f5 = batchEntry.uvX() * f3;
        float f6 = batchEntry.uvY() * f4;
        float f7 = batchEntry.uvWidth() * f3;
        float f8 = batchEntry.uvHeight() * f4;
        f6 = (float)n2 - f6 - f8;
        float f9 = f5 / (float)n;
        float f10 = f6 / (float)n2;
        float f11 = (f5 + f7) / (float)n;
        float f12 = (f6 + f8) / (float)n2;
        Matrix4f matrix4f = batchEntry.matrix();
        float f13 = batchEntry.x();
        float f14 = batchEntry.y();
        float f15 = batchEntry.z();
        this.emitVertex(class_2872, matrix4f, f13, f14, f15, f9, f12, this.color.color1());
        this.emitVertex(class_2872, matrix4f, f13, f14 + f2, f15, f9, f10, this.color.color2());
        this.emitVertex(class_2872, matrix4f, f13 + f, f14 + f2, f15, f11, f10, this.color.color3());
        this.emitVertex(class_2872, matrix4f, f13 + f, f14, f15, f11, f12, this.color.color4());
    }

    private static class_276 resolveFramebuffer(class_310 class_3102, LiquidGlassReflectionCache.ReflectionSource reflectionSource) {
        long l = RenderFrameTracker.getFrameId();
        if (l == resolvedFrameId) {
            if (reflectionSource == LiquidGlassReflectionCache.ReflectionSource.MAIN_MENU && resolvedMainMenuFramebuffer != null) {
                return resolvedMainMenuFramebuffer;
            }
            if (reflectionSource == LiquidGlassReflectionCache.ReflectionSource.CLICK_GUI && resolvedClickGuiFramebuffer != null) {
                return resolvedClickGuiFramebuffer;
            }
            if (reflectionSource == LiquidGlassReflectionCache.ReflectionSource.GAMEPLAY && resolvedGameplayFramebuffer != null) {
                return resolvedGameplayFramebuffer;
            }
        } else {
            resolvedFrameId = l;
            resolvedGameplayFramebuffer = null;
            resolvedMainMenuFramebuffer = null;
            resolvedClickGuiFramebuffer = null;
        }
        if (reflectionSource == LiquidGlassReflectionCache.ReflectionSource.MAIN_MENU) {
            LiquidGlassReflectionCache.requestCapture(LiquidGlassReflectionCache.ReflectionSource.MAIN_MENU);
            LiquidGlassReflectionCache.captureIfRequested(LiquidGlassReflectionCache.ReflectionSource.MAIN_MENU);
            resolvedMainMenuFramebuffer = LiquidGlassReflectionCache.getFramebuffer(reflectionSource, class_3102.method_1522());
            return resolvedMainMenuFramebuffer;
        }
        if (reflectionSource == LiquidGlassReflectionCache.ReflectionSource.CLICK_GUI) {
            LiquidGlassReflectionCache.captureClickGuiBackdrop(true);
            resolvedClickGuiFramebuffer = LiquidGlassReflectionCache.getFramebuffer(reflectionSource, class_3102.method_1522());
            return resolvedClickGuiFramebuffer;
        }
        LiquidGlassReflectionCache.markGameplayReflectionUsed();
        if (!LiquidGlassReflectionCache.isCapturedThisFrame(LiquidGlassReflectionCache.ReflectionSource.GAMEPLAY) && !ColorUtils.blocksGameplayReflectionRecapture()) {
            LiquidGlassReflectionCache.ensureGameplayCapturedForFrame();
        }
        resolvedGameplayFramebuffer = LiquidGlassReflectionCache.getFramebuffer(reflectionSource, null);
        return resolvedGameplayFramebuffer;
    }

    private void renderFallbackGlass(Matrix4f matrix4f, float f, float f2) {
        float f3 = this.size.width();
        float f4 = this.size.height();
        if (f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        Color color = ColorUtils.getGlassColor(Math.max(140, (int)(this.baseAlpha * 0.85f)));
        BuiltRectangle builtRectangle = (BuiltRectangle)Builder.rectangle().size(new SizeState(f3, f4)).radius(this.radius).color(new QuadColorState(color)).build();
        builtRectangle.render(matrix4f, f, f2);
    }

    private float resolveCaptureScale(class_310 class_3102, int n, int n2) {
        int n3 = Math.max(1, class_3102.method_22683().method_4480());
        int n4 = Math.max(1, class_3102.method_22683().method_4507());
        float f = (float)n / (float)n3;
        float f2 = (float)n2 / (float)n4;
        return Math.max(0.001f, Math.min(f, f2));
    }

    private void emitVertex(class_287 class_2872, Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, int n) {
        int n2 = n >>> 24 & 0xFF;
        int n3 = n >>> 16 & 0xFF;
        int n4 = n >>> 8 & 0xFF;
        int n5 = n & 0xFF;
        class_2872.method_22918(matrix4f, f, f2, f3).method_22913(f4, f5).method_1336(n3, n4, n5, n2);
    }

    private float extractMaxAlpha() {
        int n = this.extractAlpha(this.color.color1());
        int n2 = this.extractAlpha(this.color.color2());
        int n3 = this.extractAlpha(this.color.color3());
        int n4 = this.extractAlpha(this.color.color4());
        int n5 = Math.max(Math.max(n, n2), Math.max(n3, n4));
        return (float)n5 / 255.0f;
    }

    private int extractAlpha(int n) {
        return n >>> 24 & 0xFF;
    }

    record BatchEntry(Matrix4f matrix, float x, float y, float z, float uvX, float uvY, float uvWidth, float uvHeight, BuiltLiquidGlassNew style) {
    }
}

