package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_276;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_6367;
import net.minecraft.class_9801;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class FogBlurRenderer
implements MinecraftClientAccess {
    private static final float SQRT_2PI = 2.50662f;
    private static ShaderProgram gaussianShader;
    private static ShaderProgram depthShader;
    private static ShaderProgram tintShader;
    private static ShaderProgram passThroughShader;
    private static class_6367 copyFbo;
    private static class_6367 tintFbo;
    private static class_6367 blurPingFbo;
    private static class_6367 blurPongFbo;
    private static class_6367 depthMaskFbo;
    private static boolean initAttempted;
    private static boolean initialized;
    private static long tintInitTime;

    private FogBlurRenderer() {
    }

        public static void render(float f, float f2, boolean bl, boolean bl2, float f3, float f4, float f5, Color color) {
        RenderSystem.assertOnRenderThread();
        if (!FogBlurRenderer.ensureInitialized()) {
            return;
        }
        try {
            float f6 = Math.min(f, 14.0f);
            float f7 = FogBlurRenderer.resolveBlurResolutionScale(f6);
            FogBlurRenderer.ensureFramebuffers(f7);
            if (copyFbo == null || depthMaskFbo == null) {
                return;
            }
            class_276 class_2762 = aiG.method_1522();
            FogBlurRenderer.blitColorAndDepth(class_2762, (class_276)copyFbo);
            int n = copyFbo.method_30277();
            if (bl2) {
                n = FogBlurRenderer.runRainbowTintPass(n, f3, f4, f5);
            } else if (color != null && color.getAlpha() > 0) {
                n = FogBlurRenderer.runColorTintPass(n, color);
            }
            int n2 = FogBlurRenderer.runGaussianPass(n, f6, bl);
            int n3 = FogBlurRenderer.runDepthMaskPass(n2, f2);
            FogBlurRenderer.runCompositePass(n3);
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.warn("[Celebression] FogBlur pass failed, releasing resources.", throwable);
            FogBlurRenderer.release();
        }
        finally {
            aiG.method_1522().method_1235(false);
            RenderSystem.activeTexture((int)33984);
        }
    }

    public static void release() {
        RenderSystem.assertOnRenderThread();
        FogBlurRenderer.deleteShader(gaussianShader);
        FogBlurRenderer.deleteShader(depthShader);
        FogBlurRenderer.deleteShader(tintShader);
        FogBlurRenderer.deleteShader(passThroughShader);
        gaussianShader = null;
        depthShader = null;
        tintShader = null;
        passThroughShader = null;
        FogBlurRenderer.deleteFramebuffer((class_276)copyFbo);
        FogBlurRenderer.deleteFramebuffer((class_276)tintFbo);
        FogBlurRenderer.deleteFramebuffer((class_276)blurPingFbo);
        FogBlurRenderer.deleteFramebuffer((class_276)blurPongFbo);
        FogBlurRenderer.deleteFramebuffer((class_276)depthMaskFbo);
        copyFbo = null;
        tintFbo = null;
        blurPingFbo = null;
        blurPongFbo = null;
        depthMaskFbo = null;
        initAttempted = false;
        initialized = false;
    }

    private static boolean ensureInitialized() {
        if (initialized) {
            return true;
        }
        if (initAttempted) {
            return false;
        }
        initAttempted = true;
        try {
            gaussianShader = new ShaderProgram("blurs/gaussian");
            depthShader = new ShaderProgram("effects/depth");
            tintShader = new ShaderProgram("effects/tint");
            passThroughShader = new ShaderProgram("effects/passthrough");
            tintInitTime = System.nanoTime();
            initialized = true;
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.error("[Celebression] Failed to initialize FogBlur shaders.", throwable);
            FogBlurRenderer.release();
            return false;
        }
        return true;
    }

    private static void ensureFramebuffers() {
        FogBlurRenderer.ensureFramebuffers(1.0f);
    }

    private static void ensureFramebuffers(float f) {
        boolean bl;
        boolean bl2;
        int n = aiG.method_22683().method_4489();
        int n2 = aiG.method_22683().method_4506();
        if (n <= 0 || n2 <= 0) {
            return;
        }
        int n3 = Math.max(1, Math.round((float)n * f));
        int n4 = Math.max(1, Math.round((float)n2 * f));
        boolean bl3 = bl2 = copyFbo == null || FogBlurRenderer.copyFbo.field_1482 != n || FogBlurRenderer.copyFbo.field_1481 != n2 || tintFbo == null || FogBlurRenderer.tintFbo.field_1482 != n || FogBlurRenderer.tintFbo.field_1481 != n2 || depthMaskFbo == null || FogBlurRenderer.depthMaskFbo.field_1482 != n || FogBlurRenderer.depthMaskFbo.field_1481 != n2;
        if (bl2) {
            FogBlurRenderer.deleteFramebuffer((class_276)copyFbo);
            FogBlurRenderer.deleteFramebuffer((class_276)tintFbo);
            FogBlurRenderer.deleteFramebuffer((class_276)depthMaskFbo);
            copyFbo = new class_6367(n, n2, true);
            tintFbo = new class_6367(n, n2, false);
            depthMaskFbo = new class_6367(n, n2, false);
            FogBlurRenderer.configureLinearFilter((class_276)copyFbo);
            FogBlurRenderer.configureLinearFilter((class_276)tintFbo);
            FogBlurRenderer.configureLinearFilter((class_276)depthMaskFbo);
        }
        boolean bl4 = bl = blurPingFbo == null || FogBlurRenderer.blurPingFbo.field_1482 != n3 || FogBlurRenderer.blurPingFbo.field_1481 != n4 || blurPongFbo == null || FogBlurRenderer.blurPongFbo.field_1482 != n3 || FogBlurRenderer.blurPongFbo.field_1481 != n4;
        if (bl) {
            FogBlurRenderer.deleteFramebuffer((class_276)blurPingFbo);
            FogBlurRenderer.deleteFramebuffer((class_276)blurPongFbo);
            blurPingFbo = new class_6367(n3, n4, false);
            blurPongFbo = new class_6367(n3, n4, false);
            FogBlurRenderer.configureLinearFilter((class_276)blurPingFbo);
            FogBlurRenderer.configureLinearFilter((class_276)blurPongFbo);
        }
    }

    private static int runRainbowTintPass(int n, float f, float f2, float f3) {
        tintFbo.method_1235(true);
        tintShader.bind();
        tintShader.set1i("Tex0", 0);
        tintShader.setBool("RGBPuke", true);
        tintShader.setBool("UseCustomColor", false);
        tintShader.set2f("SV", f2, f3);
        tintShader.set1f("Opacity", f);
        tintShader.set1f("Time", (float)(System.nanoTime() - tintInitTime) / 1.0E9f);
        if (FogBlurRenderer.aiG.field_1724 != null) {
            tintShader.set1f("Yaw", FogBlurRenderer.aiG.field_1724.method_36454());
            tintShader.set1f("Pitch", FogBlurRenderer.aiG.field_1724.method_36455());
        } else {
            tintShader.set1f("Yaw", 0.0f);
            tintShader.set1f("Pitch", 0.0f);
        }
        RenderSystem.activeTexture((int)33984);
        RenderSystem.bindTexture((int)n);
        FogBlurRenderer.drawFullscreenQuad();
        tintShader.unbind();
        return tintFbo.method_30277();
    }

    private static int runColorTintPass(int n, Color color) {
        tintFbo.method_1235(true);
        tintShader.bind();
        tintShader.set1i("Tex0", 0);
        tintShader.setBool("RGBPuke", false);
        tintShader.setBool("UseCustomColor", true);
        tintShader.set3f("CustomColor", (float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f);
        tintShader.set1f("Opacity", (float)color.getAlpha() / 255.0f);
        RenderSystem.activeTexture((int)33984);
        RenderSystem.bindTexture((int)n);
        FogBlurRenderer.drawFullscreenQuad();
        tintShader.unbind();
        return tintFbo.method_30277();
    }

    private static int runGaussianPass(int n, float f, boolean bl) {
        float f2 = blurPingFbo == null || copyFbo == null || FogBlurRenderer.copyFbo.field_1482 <= 0 ? 1.0f : Math.min(1.0f, (float)FogBlurRenderer.blurPingFbo.field_1482 / (float)FogBlurRenderer.copyFbo.field_1482);
        float f3 = Math.max(1.0f, f * f2 + (1.0f - f2) * 4.0f);
        float[] fArray = FogBlurRenderer.calculateGaussian(f3);
        int n2 = Math.min(20, (int)Math.ceil(f3 * 2.2f));
        gaussianShader.bind();
        gaussianShader.set1i("Tex0", 0);
        gaussianShader.setBool("Alpha", false);
        gaussianShader.set3f("Gaussian", fArray[0], fArray[1], fArray[2]);
        gaussianShader.set1i("Support", n2);
        gaussianShader.setBool("LinearSampling", bl);
        blurPingFbo.method_1235(true);
        gaussianShader.set2f("Direction", 1.0f, 0.0f);
        gaussianShader.set2f("TexelSize", 1.0f / (float)FogBlurRenderer.blurPingFbo.field_1482, 1.0f / (float)FogBlurRenderer.blurPingFbo.field_1481);
        RenderSystem.activeTexture((int)33984);
        RenderSystem.bindTexture((int)n);
        FogBlurRenderer.drawFullscreenQuad();
        blurPongFbo.method_1235(true);
        gaussianShader.set2f("Direction", 0.0f, 1.0f);
        gaussianShader.set2f("TexelSize", 1.0f / (float)FogBlurRenderer.blurPongFbo.field_1482, 1.0f / (float)FogBlurRenderer.blurPongFbo.field_1481);
        RenderSystem.activeTexture((int)33984);
        RenderSystem.bindTexture((int)blurPingFbo.method_30277());
        FogBlurRenderer.drawFullscreenQuad();
        gaussianShader.unbind();
        return blurPongFbo.method_30277();
    }

    private static float resolveBlurResolutionScale(float f) {
        if (f >= 14.0f) {
            return 0.45f;
        }
        if (f >= 10.0f) {
            return 0.55f;
        }
        if (f >= 6.0f) {
            return 0.7f;
        }
        return 0.85f;
    }

    private static int runDepthMaskPass(int n, float f) {
        depthMaskFbo.method_1235(true);
        depthShader.bind();
        depthShader.set1i("Tex0", 0);
        depthShader.set1i("Tex1", 1);
        depthShader.set1f("Near", 0.05f);
        depthShader.set1f("Far", FogBlurRenderer.aiG.field_1773.method_32796());
        depthShader.set1f("MinThreshold", 0.1f * f / 100.0f);
        depthShader.set1f("MaxThreshold", 0.28f * f / 100.0f);
        RenderSystem.activeTexture((int)33984);
        RenderSystem.bindTexture((int)n);
        RenderSystem.activeTexture((int)33985);
        RenderSystem.bindTexture((int)copyFbo.method_30278());
        GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        FogBlurRenderer.drawFullscreenQuad();
        depthShader.unbind();
        RenderSystem.activeTexture((int)33984);
        return depthMaskFbo.method_30277();
    }

    private static void runCompositePass(int n) {
        class_276 class_2762 = aiG.method_1522();
        class_2762.method_1235(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.depthMask((boolean)false);
        passThroughShader.bind();
        passThroughShader.set1i("Tex0", 0);
        passThroughShader.setBool("Alpha", true);
        RenderSystem.activeTexture((int)33984);
        RenderSystem.bindTexture((int)n);
        FogBlurRenderer.drawFullscreenQuad();
        passThroughShader.unbind();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.depthMask((boolean)true);
        RenderSystem.enableDepthTest();
    }

    private static float[] calculateGaussian(float f) {
        float f2 = Math.max(0.2f, f);
        float f3 = (float)Math.exp(-0.5f / (f2 * f2));
        return new float[]{1.0f / (2.50662f * f2), f3, f3 * f3};
    }

    private static void drawFullscreenQuad() {
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1592);
        class_2872.method_22912(-1.0f, -1.0f, 0.0f);
        class_2872.method_22912(1.0f, -1.0f, 0.0f);
        class_2872.method_22912(1.0f, 1.0f, 0.0f);
        class_2872.method_22912(-1.0f, 1.0f, 0.0f);
        class_286.method_43437((class_9801)class_2872.method_60800());
    }

    private static void blitColorAndDepth(class_276 class_2762, class_276 class_2763) {
        if (class_2762 == null || class_2763 == null) {
            return;
        }
        if (class_2762.field_1476 <= 0 || class_2763.field_1476 <= 0) {
            class_2763.method_1235(true);
            class_2762.method_1237(class_2763.field_1482, class_2763.field_1481);
            class_2762.method_1235(false);
            return;
        }
        int n = GL11.glGetInteger((int)36010);
        int n2 = GL11.glGetInteger((int)36006);
        GL30.glBindFramebuffer((int)36008, (int)class_2762.field_1476);
        GL30.glBindFramebuffer((int)36009, (int)class_2763.field_1476);
        GL30.glBlitFramebuffer((int)0, (int)0, (int)class_2762.field_1482, (int)class_2762.field_1481, (int)0, (int)0, (int)class_2763.field_1482, (int)class_2763.field_1481, (int)16640, (int)9728);
        GL30.glBindFramebuffer((int)36008, (int)n);
        GL30.glBindFramebuffer((int)36009, (int)n2);
        class_2762.method_1235(false);
    }

    private static void configureLinearFilter(class_276 class_2762) {
        if (class_2762 == null) {
            return;
        }
        class_2762.method_58226(9729);
        RenderSystem.bindTexture((int)class_2762.method_30277());
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        RenderSystem.bindTexture((int)0);
    }

    private static void deleteFramebuffer(class_276 class_2762) {
        if (class_2762 != null) {
            class_2762.method_1238();
        }
    }

    private static void deleteShader(ShaderProgram shaderProgram) {
        if (shaderProgram != null) {
            shaderProgram.delete();
        }
    }

    static int compile(String string, int n) {
        int n2 = GL20.glCreateShader((int)n);
        GL20.glShaderSource((int)n2, (CharSequence)string);
        GL20.glCompileShader((int)n2);
        if (GL20.glGetShaderi((int)n2, (int)35713) == 0) {
            String string2 = GL20.glGetShaderInfoLog((int)n2);
            GL20.glDeleteShader((int)n2);
            throw new IllegalStateException("Failed to compile shader: " + string2);
        }
        return n2;
    }

    static String readShader(String string) {
        String resourcePath = string.startsWith("/") ? string : "/" + string;
        try (InputStream inputStream = FogBlurRenderer.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Shader file is missing: " + resourcePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (Exception exception) {
            throw new IllegalStateException("Unable to read shader: " + resourcePath, exception);
        }
    }

    static {
        tintInitTime = System.nanoTime();
    }

    static final class ShaderProgram {
        private final int id;
        private final Map<String, Integer> uniformCache = new HashMap<String, Integer>();

        ShaderProgram(String string) {
            String string2 = FogBlurRenderer.readShader("assets/celebression/shaders/impl/" + string + ".vsh");
            String string3 = FogBlurRenderer.readShader("assets/celebression/shaders/impl/" + string + ".fsh");
            int n = FogBlurRenderer.compile(string2, 35633);
            int n2 = FogBlurRenderer.compile(string3, 35632);
            int n3 = GL20.glCreateProgram();
            GL20.glAttachShader((int)n3, (int)n);
            GL20.glAttachShader((int)n3, (int)n2);
            GL20.glLinkProgram((int)n3);
            if (GL20.glGetProgrami((int)n3, (int)35714) == 0) {
                String string4 = GL20.glGetProgramInfoLog((int)n3);
                GL20.glDeleteProgram((int)n3);
                GL20.glDeleteShader((int)n);
                GL20.glDeleteShader((int)n2);
                throw new IllegalStateException("Failed to link shader: " + string + " -> " + string4);
            }
            GL20.glDetachShader((int)n3, (int)n);
            GL20.glDetachShader((int)n3, (int)n2);
            GL20.glDeleteShader((int)n);
            GL20.glDeleteShader((int)n2);
            this.id = n3;
        }

        void bind() {
            GL20.glUseProgram((int)this.id);
        }

        void unbind() {
            GL20.glUseProgram((int)0);
        }

        void set1i(String string, int n) {
            GL20.glUniform1i((int)this.uniform(string), (int)n);
        }

        void set1f(String string, float f) {
            GL20.glUniform1f((int)this.uniform(string), (float)f);
        }

        void set2f(String string, float f, float f2) {
            GL20.glUniform2f((int)this.uniform(string), (float)f, (float)f2);
        }

        void set3f(String string, float f, float f2, float f3) {
            GL20.glUniform3f((int)this.uniform(string), (float)f, (float)f2, (float)f3);
        }

        void setBool(String string, boolean bl) {
            this.set1i(string, bl ? 1 : 0);
        }

        void delete() {
            GL20.glDeleteProgram((int)this.id);
        }

        private int uniform(String string2) {
            return this.uniformCache.computeIfAbsent(string2, string -> GL20.glGetUniformLocation((int)this.id, (CharSequence)string));
        }
    }
}
