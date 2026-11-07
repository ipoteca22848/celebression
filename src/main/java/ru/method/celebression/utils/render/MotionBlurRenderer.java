package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_276;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_310;
import net.minecraft.class_6367;
import net.minecraft.class_9801;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.modules.impl.render.MotionBlurModule;

public final class MotionBlurRenderer
implements MinecraftClientAccess {
    private static ShaderProgram shader;
    private static class_6367 accumFbo;
    private static class_6367 tempFbo;
    private static boolean accumPrimed;

    private MotionBlurRenderer() {
    }

    public static void render(float f) {
        if (!MotionBlurRenderer.isActive() || f <= 0.001f) {
            return;
        }
        RenderSystem.assertOnRenderThread();
        try {
            class_310 class_3102 = class_310.method_1551();
            class_276 class_2762 = class_3102.method_1522();
            MotionBlurRenderer.ensureFramebuffers();
            if (class_2762 == null || accumFbo == null || tempFbo == null) {
                return;
            }
            if (!accumPrimed) {
                MotionBlurRenderer.blitColor(class_2762, (class_276)accumFbo);
                accumPrimed = true;
            }
            tempFbo.method_1235(false);
            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask((boolean)false);
            RenderSystem.disableCull();
            shader.bind();
            MotionBlurRenderer.bindTexture(33984, class_2762.method_30277(), 9729);
            MotionBlurRenderer.bindTexture(33985, accumFbo.method_30277(), 9729);
            shader.set1i("CurrentTexture", 0);
            shader.set1i("PrevTexture", 1);
            shader.set1f("strength", Math.max(0.0f, Math.min(0.95f, f)));
            MotionBlurRenderer.drawFullscreenQuad();
            shader.unbind();
            RenderSystem.activeTexture((int)33985);
            RenderSystem.bindTexture((int)0);
            RenderSystem.activeTexture((int)33984);
            RenderSystem.bindTexture((int)0);
            MotionBlurRenderer.blitColor((class_276)tempFbo, class_2762);
            MotionBlurRenderer.blitColor((class_276)tempFbo, (class_276)accumFbo);
            RenderSystem.depthMask((boolean)true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.warn("[Celebression] Motion blur pass failed.", throwable);
            MotionBlurRenderer.release();
        }
        finally {
            if (aiG != null && aiG.method_1522() != null) {
                aiG.method_1522().method_1235(false);
            }
            RenderSystem.activeTexture((int)33984);
        }
    }

    public static void release() {
        if (shader != null) {
            shader.delete();
            shader = null;
        }
        MotionBlurRenderer.deleteFramebuffer((class_276)accumFbo);
        MotionBlurRenderer.deleteFramebuffer((class_276)tempFbo);
        accumFbo = null;
        tempFbo = null;
        accumPrimed = false;
    }

    public static boolean isActive() {
        if (aiG == null || MotionBlurRenderer.aiG.field_1724 == null || MotionBlurRenderer.aiG.field_1687 == null) {
            return false;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.isPanic() || celebression.getModuleManager() == null) {
            return false;
        }
        MotionBlurModule motionBlurModule = celebression.getModuleManager().getModule(MotionBlurModule.class);
        return motionBlurModule != null && motionBlurModule.isToggled();
    }

    private static void ensureFramebuffers() {
        int n = Math.max(1, aiG.method_22683().method_4489());
        int n2 = Math.max(1, aiG.method_22683().method_4506());
        if (accumFbo == null || MotionBlurRenderer.accumFbo.field_1482 != n || MotionBlurRenderer.accumFbo.field_1481 != n2) {
            MotionBlurRenderer.deleteFramebuffer((class_276)accumFbo);
            MotionBlurRenderer.deleteFramebuffer((class_276)tempFbo);
            accumFbo = new class_6367(n, n2, false);
            tempFbo = new class_6367(n, n2, false);
            accumFbo.method_58226(9729);
            tempFbo.method_58226(9729);
            accumPrimed = false;
        }
        if (shader == null) {
            shader = new ShaderProgram("effects/motionblur");
        }
    }

    private static void drawFullscreenQuad() {
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1592);
        class_2872.method_22912(-1.0f, -1.0f, 0.0f);
        class_2872.method_22912(1.0f, -1.0f, 0.0f);
        class_2872.method_22912(1.0f, 1.0f, 0.0f);
        class_2872.method_22912(-1.0f, 1.0f, 0.0f);
        class_286.method_43437((class_9801)class_2872.method_60800());
    }

    private static void bindTexture(int n, int n2, int n3) {
        RenderSystem.activeTexture((int)n);
        RenderSystem.bindTexture((int)n2);
        GL11.glTexParameteri((int)3553, (int)10241, (int)n3);
        GL11.glTexParameteri((int)3553, (int)10240, (int)n3);
    }

    private static void blitColor(class_276 class_2762, class_276 class_2763) {
        if (class_2762 == null || class_2763 == null) {
            return;
        }
        int n = GL11.glGetInteger((int)36010);
        int n2 = GL11.glGetInteger((int)36006);
        GL30.glBindFramebuffer((int)36008, (int)class_2762.field_1476);
        GL30.glBindFramebuffer((int)36009, (int)class_2763.field_1476);
        GL30.glBlitFramebuffer((int)0, (int)0, (int)class_2762.field_1482, (int)class_2762.field_1481, (int)0, (int)0, (int)class_2763.field_1482, (int)class_2763.field_1481, (int)16384, (int)9728);
        GL30.glBindFramebuffer((int)36008, (int)n);
        GL30.glBindFramebuffer((int)36009, (int)n2);
    }

    private static void deleteFramebuffer(class_276 class_2762) {
        if (class_2762 != null) {
            class_2762.method_1238();
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
        try (InputStream inputStream = MotionBlurRenderer.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Shader file is missing: " + resourcePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (Exception exception) {
            throw new IllegalStateException("Unable to read shader: " + resourcePath, exception);
        }
    }

    static final class ShaderProgram {
        private final int id;
        private final Map<String, Integer> uniformCache = new HashMap<String, Integer>();

        ShaderProgram(String string) {
            String string2 = MotionBlurRenderer.readShader("assets/celebression/shaders/impl/" + string + ".vsh");
            String string3 = MotionBlurRenderer.readShader("assets/celebression/shaders/impl/" + string + ".fsh");
            int n = MotionBlurRenderer.compile(string2, 35633);
            int n2 = MotionBlurRenderer.compile(string3, 35632);
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
            int n2 = this.uniform(string);
            if (n2 >= 0) {
                GL20.glUniform1i((int)n2, (int)n);
            }
        }

        void set1f(String string, float f) {
            int n = this.uniform(string);
            if (n >= 0) {
                GL20.glUniform1f((int)n, (float)f);
            }
        }

        void delete() {
            GL20.glDeleteProgram((int)this.id);
        }

        private int uniform(String string2) {
            return this.uniformCache.computeIfAbsent(string2, string -> GL20.glGetUniformLocation((int)this.id, (CharSequence)string));
        }
    }
}
