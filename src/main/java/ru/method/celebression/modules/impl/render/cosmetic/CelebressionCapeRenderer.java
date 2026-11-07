package ru.method.celebression.modules.impl.render.cosmetic;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.function.Supplier;
import net.minecraft.class_1011;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_10366;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_1060;
import net.minecraft.class_276;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_6367;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Matrix4fc;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import ru.method.celebression.Celebression;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;
import ru.method.celebression.utils.render.ClickGuiRenderContext;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class CelebressionCapeRenderer {
    public static final String cu = "celebression_signature";
    public static final class_2960 cv = class_2960.method_60655((String)"celebression", (String)"celebression_signature_cape");
    private static final class_2960 cw = class_2960.method_60655((String)"celebression", (String)"celebression_signature_cape_overlay");
    private static final class_2960 cx = class_2960.method_60655((String)"celebression", (String)"celebression_signature_cape_bv");
    private static final class_2960 cy = class_2960.method_60655((String)"celebression", (String)"celebression_signature_cape_icon");
    private static final int cz = 16;
    private static final int cA = 1024;
    private static final int cB = 512;
    private static final int cC = 16;
    private static final int cD = 16;
    private static final int cE = 160;
    private static final int cF = 256;
    private static final int cG = 192;
    private static final float cH = 400.0f;
    private static final float cI = 640.0f;
    private static final long cJ = 33333333L;
    private static class_6367 cK;
    private static class_6367 cL;
    private static class_6367 cM;
    private static class_6367 cN;
    private static class_6367 cO;
    private static boolean registered;
    private static boolean cP;
    private static boolean cQ;
    private static boolean cR;
    private static long cS;
    private static final long cT;
    private static volatile boolean cU;
    private static boolean cV;
    private static final float[][] cW;

    private CelebressionCapeRenderer() {
    }

    public static class_2960 texture() {
        cU = true;
        return cv;
    }

    public static void F() {
        cU = true;
    }

    public static class_2960 G() {
        cU = true;
        return cR ? cy : cv;
    }

    public static boolean H() {
        return cR;
    }

    public static void I() {
        boolean bl = cV;
        cV = cU;
        cU = false;
        try {
            CelebressionCapeRenderer.K();
            if (!bl) {
                return;
            }
            long l = System.nanoTime();
            if (cP && l - cS < 33333333L) {
                return;
            }
            cS = l;
            if (!cP) {
                if (CelebressionCapeRenderer.J()) {
                    return;
                }
                CelebressionCapeRenderer.M();
            }
            CelebressionCapeRenderer.N();
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.warn("[CelebressionCape] render failed: {}", (Object)throwable.toString());
        }
    }

    private static boolean J() {
        if (ClickGuiRenderContext.isActive() && ClickGuiRenderContext.isClosing()) {
            return true;
        }
        return LinkGuiRenderContext.getFadeMultiplier() < 1.0f;
    }

    private static void K() {
        class_1060 class_10602;
        if (cK == null) {
            cK = CelebressionCapeRenderer.L();
            cL = CelebressionCapeRenderer.L();
            cM = CelebressionCapeRenderer.L();
            cN = CelebressionCapeRenderer.L();
            cO = CelebressionCapeRenderer.L();
            CelebressionCapeRenderer.a(cK);
            CelebressionCapeRenderer.a(cL);
            class_276 framebuffer = class_310.method_1551().method_1522();
            framebuffer.method_1235(false);
            GL11.glViewport(0, 0, framebuffer.field_1480, framebuffer.field_1477);
        }
        if (!registered) {
            class_10602 = class_310.method_1551().method_1531();
            class_10602.method_4616(cv, (class_1044)new CapeTexturePart(() -> cK));
            class_10602.method_4616(cw, (class_1044)new CapeTexturePart(() -> cN));
            class_10602.method_4616(cx, (class_1044)new CapeTexturePart(() -> cO));
            registered = true;
            Celebression.LOGGER.info("[CelebressionCape] FBO-\u0442\u0435\u043a\u0441\u0442\u0443\u0440\u0430 \u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043e\u0432\u0430\u043d\u0430 ({}x{})", (Object)1024, (Object)512);
        }
    }

    private static void a(class_6367 class_63672) {
        GlStateManager._bindTexture((int)class_63672.method_30277());
        GL11.glTexParameteri((int)3553, (int)10241, (int)9987);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL30.glGenerateMipmap((int)3553);
    }

    private static class_6367 L() {
        class_6367 class_63672 = new class_6367(1024, 512, false);
        class_63672.method_1236(0.0f, 0.0f, 0.0f, 0.0f);
        class_63672.method_1235(false);
        GL11.glViewport((int)0, (int)0, (int)1024, (int)512);
        GL11.glClearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glClear((int)16384);
        return class_63672;
    }

    private static void M() {
        CelebressionCapeRenderer.a(cM, true, () -> {
            class_4587 class_45872 = new class_4587();
            Render2D.drawRoundedRect(class_45872, 0.0f, 0.0f, 352.0f, 272.0f, 0.0f, new Color(7, 11, 9, 255));
            CelebressionCapeRenderer.a(class_45872, 16.0f, 16.0f, 160.0f, 256.0f);
            CelebressionCapeRenderer.a(class_45872, 192.0f, 16.0f, 160.0f, 256.0f);
        });
        CelebressionCapeRenderer.a(cN, true, () -> {
            class_4587 class_45872 = new class_4587();
            CelebressionCapeRenderer.b(class_45872, 16.0f, 16.0f, 160.0f, 256.0f);
            CelebressionCapeRenderer.b(class_45872, 192.0f, 16.0f, 160.0f, 256.0f);
        });
        CelebressionCapeRenderer.a(cO, true, () -> {
            class_4587 class_45872 = new class_4587();
            CelebressionCapeRenderer.c(class_45872, 16.0f, 16.0f, 160.0f, 256.0f);
            CelebressionCapeRenderer.c(class_45872, 192.0f, 16.0f, 160.0f, 256.0f);
        });
        cP = true;
        Celebression.LOGGER.info("[CelebressionCape] \u0441\u0442\u0430\u0442\u0438\u0447\u043d\u044b\u0435 \u0441\u043b\u043e\u0438 \u0437\u0430\u043f\u0435\u0447\u0435\u043d\u044b ({}x{})", (Object)1024, (Object)512);
    }

    private static void N() {
        class_6367 class_63672 = cL;
        CelebressionCapeRenderer.a(class_63672, (float)(System.nanoTime() - cT) / 1.0E9f);
        GlStateManager._bindTexture((int)class_63672.method_30277());
        GL30.glGenerateMipmap((int)3553);
        cL = cK;
        cK = class_63672;
        if (!cQ) {
            cQ = true;
            Celebression.LOGGER.info("[CelebressionCape] \u0430\u043d\u0438\u043c\u0438\u0440\u043e\u0432\u0430\u043d\u043d\u044b\u0439 \u043f\u043b\u0430\u0449 \u0430\u043a\u0442\u0438\u0432\u0435\u043d ({}x{})", (Object)1024, (Object)512);
        }
        if (!cR) {
            try {
                CelebressionCapeRenderer.O();
            }
            catch (Throwable throwable) {
                cR = true;
                Celebression.LOGGER.warn("[CelebressionCape] \u0441\u043d\u0438\u043c\u043e\u043a \u0438\u043a\u043e\u043d\u043a\u0438 \u043d\u0435 \u0443\u0434\u0430\u043b\u0441\u044f: {}", (Object)throwable.toString());
            }
        }
    }

    private static void a(class_6367 class_63672, float f) {
        CelebressionCapeRenderer.a(class_63672, false, () -> {
            GL30.glBindFramebuffer((int)36008, (int)CelebressionCapeRenderer.cM.field_1476);
            GL30.glBlitFramebuffer((int)0, (int)0, (int)1024, (int)512, (int)0, (int)0, (int)1024, (int)512, (int)16384, (int)9728);
            GL30.glBindFramebuffer((int)36008, (int)class_63672.field_1476);
            class_4587 class_45872 = new class_4587();
            float f2 = 0.4f;
            float f3 = 0.55f + 0.45f * (0.5f - 0.5f * (float)Math.cos((double)(f / 4.5f * 2.0f) * Math.PI));
            CelebressionCapeRenderer.a(class_45872, 16.0f, 16.0f, 160.0f, 256.0f, f2, f);
            CelebressionCapeRenderer.a(class_45872, 192.0f, 16.0f, 160.0f, 256.0f, f2, f);
            CelebressionCapeRenderer.a(class_45872, 96.0f, 133.76001f, 200.0f * f2, CelebressionCapeRenderer.e(0.18f * f3));
            CelebressionCapeRenderer.a(class_45872, 272.0f, 133.76001f, 200.0f * f2, CelebressionCapeRenderer.e(0.18f * f3));
            CelebressionCapeRenderer.a(class_45872, 16.0f, f2);
            CelebressionCapeRenderer.a(class_45872, 192.0f, f2);
            CelebressionCapeRenderer.a(class_45872, cw, 1.0f, 0.0f);
            float f4 = CelebressionCapeRenderer.frac(f / 7.0f);
            float f5 = f4 < 0.1f ? 0.0f : (f4 < 0.32f ? (f4 - 0.1f) / 0.22f : (f4 < 0.7f ? 1.0f : Math.max(0.0f, 1.0f - (f4 - 0.7f) / 0.3f)));
            if (f5 > 0.004f) {
                CelebressionCapeRenderer.a(class_45872, cx, f5, (1.0f - f5) * 5.0f * f2);
            }
        });
    }

    private static void O() {
        CelebressionCapeRenderer.a(cL, 3.5f);
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)163840);
        GL30.glBindFramebuffer((int)36008, (int)CelebressionCapeRenderer.cL.field_1476);
        GL11.glReadPixels((int)16, (int)16, (int)160, (int)256, (int)6408, (int)5121, (ByteBuffer)byteBuffer);
        GL30.glBindFramebuffer((int)36008, (int)0);
        int n = 80;
        int n2 = 128;
        class_1011 class_10112 = new class_1011(class_1011.class_1012.field_4997, n, n2, false);
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n3 = 0;
                int n4 = 0;
                int n5 = 0;
                for (int k = 0; k < 2; ++k) {
                    for (int i2 = 0; i2 < 2; ++i2) {
                        int n6 = ((i * 2 + k) * 160 + (j * 2 + i2)) * 4;
                        n3 += byteBuffer.get(n6) & 0xFF;
                        n4 += byteBuffer.get(n6 + 1) & 0xFF;
                        n5 += byteBuffer.get(n6 + 2) & 0xFF;
                    }
                }
                class_10112.method_61941(j, i, 0xFF000000 | n3 / 4 << 16 | n4 / 4 << 8 | n5 / 4);
            }
        }
        class_1043 class_10432 = new class_1043(class_10112);
        class_10432.method_4527(true, false);
        class_10432.method_4524();
        class_310.method_1551().method_1531().method_4616(cy, (class_1044)class_10432);
        cR = true;
        Celebression.LOGGER.info("[CelebressionCape] \u0441\u0442\u0430\u0442\u0438\u0447\u043d\u0430\u044f \u0438\u043a\u043e\u043d\u043a\u0430 \u0441\u043d\u044f\u0442\u0430 ({}x{})", (Object)n, (Object)n2);
    }

        private static void a(class_6367 class_63672, boolean bl, Runnable runnable) {
        class_310 class_3102 = class_310.method_1551();
        class_276 class_2762 = class_3102.method_1522();
        Matrix4f matrix4f = new Matrix4f((Matrix4fc)RenderSystem.getProjectionMatrix());
        class_10366 class_103662 = RenderSystem.getProjectionType();
        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        boolean bl2 = false;
        try {
            RenderSystem.colorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
            class_63672.method_1235(false);
            GL11.glViewport((int)0, (int)0, (int)1024, (int)512);
            if (bl) {
                GL11.glClearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
                GL11.glClear((int)16384);
            }
            RenderSystem.setProjectionMatrix((Matrix4f)new Matrix4f().setOrtho(0.0f, 1024.0f, 0.0f, 512.0f, -1000.0f, 1000.0f), (class_10366)class_10366.field_54954);
            matrix4fStack.pushMatrix();
            bl2 = true;
            matrix4fStack.identity();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask((boolean)false);
            runnable.run();
        }
        finally {
            if (bl2) {
                matrix4fStack.popMatrix();
            }
            class_2762.method_1235(false);
            GL11.glViewport((int)0, (int)0, (int)class_2762.field_1480, (int)class_2762.field_1477);
            RenderSystem.setProjectionMatrix((Matrix4f)matrix4f, (class_10366)class_103662);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask((boolean)true);
        }
    }

    private static Color e(float f) {
        Color color = CustomMainMenuScreen.ACCENT;
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), CelebressionCapeRenderer.f(f));
    }

    private static int f(float f) {
        return Math.max(0, Math.min(255, Math.round(f * 255.0f)));
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, float f4) {
        float f5 = f3 / 400.0f;
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, 5.0f * f5, new Color(12, 20, 16), new Color(5, 8, 7));
        Render2D.drawHatch(class_45872, f, f2, f3, f4, 9.0f * f5, 1.0f * f5, new Color(255, 255, 255, 4));
        CelebressionCapeRenderer.a(class_45872, f + 0.5f * f3, f2 + 0.42f * f4, 150.0f * f5, CelebressionCapeRenderer.e(0.12f));
        CelebressionCapeRenderer.a(class_45872, f + 0.28f * f3, f2 + 0.24f * f4, 95.0f * f5, CelebressionCapeRenderer.e(0.08f));
        CelebressionCapeRenderer.a(class_45872, f + 0.72f * f3, f2 + 0.66f * f4, 105.0f * f5, CelebressionCapeRenderer.e(0.07f));
        CelebressionCapeRenderer.a(class_45872, f + 0.4f * f3, f2 + 0.84f * f4, 85.0f * f5, CelebressionCapeRenderer.e(0.05f));
    }

    private static void a(class_4587 class_45872, float f, float f2) {
        Color color = new Color(9, 14, 11, 255);
        float f3 = 8.0f * f2;
        Render2D.drawRoundedRect(class_45872, f, 16.0f, 160.0f, f3, 0.0f, color);
        Render2D.drawRoundedRect(class_45872, f, 272.0f - f3, 160.0f, f3, 0.0f, color);
        Render2D.drawRoundedRect(class_45872, f, 16.0f, f3, 256.0f, 0.0f, color);
        Render2D.drawRoundedRect(class_45872, f + 160.0f - f3, 16.0f, f3, 256.0f, 0.0f, color);
    }

    private static void b(class_4587 class_45872, float f, float f2, float f3, float f4) {
        float f5 = f3 / 400.0f;
        float f6 = f + 8.0f * f5;
        float f7 = f2 + 8.0f * f5;
        float f8 = f3 - 16.0f * f5;
        float f9 = f4 - 16.0f * f5;
        Render2D.drawBorder(class_45872, f6, f7, f8, f9, 4.0f * f5, 1.5f, 6.0f * f5, CelebressionCapeRenderer.e(0.42f));
        Render2D.drawBorder(class_45872, f6, f7, f8, f9, 4.0f * f5, 1.2f, 2.6f * f5, CelebressionCapeRenderer.e(0.22f));
        Render2D.drawBorder(class_45872, f6, f7, f8, f9, 4.0f * f5, 2.0f * f5, new ColorRGBA(new Color(183, 244, 216, 230)));
        Instance instance = Fonts.GOLOS_SEMI.getFont(40.0f * f5);
        float f10 = instance.getWidth("celebression");
        float f11 = instance.getWidth(".");
        float f12 = f + (f3 - (f10 + f11)) / 2.0f;
        float f13 = f2 + 0.46f * f4 - instance.getHeight() / 2.0f;
        Render2D.drawFontGlow(class_45872, instance, "celebression", f12, f13, new Color(238, 242, 239), CelebressionCapeRenderer.e(0.4f), 26.0f * f5);
        Render2D.drawFont(class_45872, instance, ".", f12 + f10, f13, CelebressionCapeRenderer.e(1.0f));
        Instance instance2 = Fonts.PLEXMONO.getFont(21.0f * f5);
        float f14 = instance2.getWidth("@Celebression");
        float f15 = f2 + f4 - 28.0f * f5 - instance2.getHeight();
        Render2D.drawFontGlow(class_45872, instance2, "@Celebression", f + (f3 - f14) / 2.0f, f15, CelebressionCapeRenderer.e(0.85f), CelebressionCapeRenderer.e(0.5f), 14.0f * f5);
    }

    private static void c(class_4587 class_45872, float f, float f2, float f3, float f4) {
        float f5 = f3 / 400.0f;
        Instance instance = Fonts.GOLOS_SEMI.getFont(40.0f * f5);
        float f6 = f2 + 0.46f * f4 - instance.getHeight() / 2.0f;
        Instance instance2 = Fonts.UNBOUNDED.getFont(11.0f * f5);
        float f7 = f6 + instance.getHeight() + 10.0f * f5;
        CelebressionCapeRenderer.a(class_45872, instance2, "Best Visual", f, f3, f7, CelebressionCapeRenderer.e(1.0f), 3.3000002f * f5, CelebressionCapeRenderer.e(0.7f), 14.0f * f5);
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, Color color) {
        int n = 7;
        int n2 = 32;
        float f4 = (float)color.getAlpha() / 255.0f;
        int n3 = Math.max(1, Math.round((1.0f - (float)Math.pow(1.0f - f4, 1.0 / (double)n)) * 255.0f));
        int n4 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n3).getRGB();
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        for (int i = n; i >= 1; --i) {
            float f5 = f3 * (float)i / (float)n;
            for (int j = 0; j < n2; ++j) {
                double d = Math.PI * 2 * (double)j / (double)n2;
                double d2 = Math.PI * 2 * (double)(j + 1) / (double)n2;
                class_2872.method_22918(matrix4f, f, f2, 0.0f).method_39415(n4);
                class_2872.method_22918(matrix4f, f + (float)(Math.cos(d) * (double)f5), f2 + (float)(Math.sin(d) * (double)f5), 0.0f).method_39415(n4);
                class_2872.method_22918(matrix4f, f + (float)(Math.cos(d2) * (double)f5), f2 + (float)(Math.sin(d2) * (double)f5), 0.0f).method_39415(n4);
            }
        }
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        int n;
        float f7 = f + 8.0f * f5;
        float f8 = f2 + 8.0f * f5;
        float f9 = f3 - 16.0f * f5;
        float f10 = f4 - 16.0f * f5;
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        Color color = CustomMainMenuScreen.ACCENT;
        int n2 = n = color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
        for (float[] fArray : cW) {
            float f11 = fArray[1] * f5;
            float f12 = fArray[2] * f5;
            float f13 = fArray[3];
            float f14 = fArray[4];
            float f15 = fArray[5];
            float f16 = Math.max(f7, Math.min(f7 + fArray[0] / 100.0f * f9, f7 + f9 - f11));
            float f17 = CelebressionCapeRenderer.frac((f6 - f14) / f13);
            float f18 = f17 < 0.06f ? f17 / 0.06f : (f17 < 0.88f ? 1.0f : (1.0f - f17) / 0.12f);
            float f19 = Math.max(0.0f, Math.min(1.0f, f15 * f18));
            if (f19 <= 0.01f) continue;
            float f20 = f8 - f12 + f17 * (f10 + f12);
            float f21 = f20 + f12 * 0.62f;
            float f22 = Math.max(f20, f8);
            float f23 = Math.min(f20 + f12, f8 + f10);
            if (f23 <= f22) continue;
            float f24 = Math.max(f22, Math.min(f21, f23));
            int n3 = n | CelebressionCapeRenderer.f(0.75f * f19) << 24;
            int n4 = 0xEAFFF3 | CelebressionCapeRenderer.f(f19) << 24;
            if (f24 > f22) {
                CelebressionCapeRenderer.a(class_2872, matrix4f, f16, f22, f16 + f11, f24, n2, n3);
            }
            if (!(f23 > f24)) continue;
            CelebressionCapeRenderer.a(class_2872, matrix4f, f16, f24, f16 + f11, f23, n3, n4);
        }
        class_9801 class_98012 = class_2872.method_60794();
        if (class_98012 != null) {
            RenderSystem.setShader((class_10156)class_10142.field_53876);
            class_286.method_43433((class_9801)class_98012);
        }
        RenderSystem.enableCull();
    }

    private static void a(class_287 class_2872, Matrix4f matrix4f, float f, float f2, float f3, float f4, int n, int n2) {
        class_2872.method_22918(matrix4f, f, f2, 0.0f).method_39415(n);
        class_2872.method_22918(matrix4f, f, f4, 0.0f).method_39415(n2);
        class_2872.method_22918(matrix4f, f3, f4, 0.0f).method_39415(n2);
        class_2872.method_22918(matrix4f, f3, f2, 0.0f).method_39415(n);
    }

    private static void a(class_4587 class_45872, class_2960 class_29602, float f, float f2) {
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        int n = new Color(255, 255, 255, CelebressionCapeRenderer.f(f)).getRGB();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShader((class_10156)class_10142.field_53880);
        RenderSystem.setShaderTexture((int)0, (class_2960)class_29602);
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        class_2872.method_22918(matrix4f, 0.0f, f2, 0.0f).method_22913(0.0f, 0.0f).method_39415(n);
        class_2872.method_22918(matrix4f, 0.0f, 512.0f + f2, 0.0f).method_22913(0.0f, 1.0f).method_39415(n);
        class_2872.method_22918(matrix4f, 1024.0f, 512.0f + f2, 0.0f).method_22913(1.0f, 1.0f).method_39415(n);
        class_2872.method_22918(matrix4f, 1024.0f, f2, 0.0f).method_22913(1.0f, 0.0f).method_39415(n);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
    }

    private static void a(class_4587 class_45872, Instance instance, String string, float f, float f2, float f3, Color color, float f4, Color color2, float f5) {
        float f6 = 0.0f;
        for (int i = 0; i < string.length(); ++i) {
            f6 += instance.getWidth(String.valueOf(string.charAt(i))) + f4;
        }
        float f7 = f + (f2 - (f6 -= f4)) / 2.0f;
        for (int i = 0; i < string.length(); ++i) {
            String string2 = String.valueOf(string.charAt(i));
            Render2D.drawFontGlow(class_45872, instance, string2, f7, f3, color, color2, f5);
            f7 += instance.getWidth(string2) + f4;
        }
    }

    private static float frac(float f) {
        return f - (float)Math.floor(f);
    }

    static {
        cT = System.nanoTime();
        cW = new float[][]{{6.0f, 2.0f, 64.0f, 1.6f, 0.0f, 0.55f}, {12.0f, 1.5f, 42.0f, 2.1f, 0.7f, 0.35f}, {19.0f, 2.0f, 78.0f, 1.3f, 0.3f, 0.8f}, {26.0f, 1.5f, 38.0f, 2.4f, 1.4f, 0.3f}, {33.0f, 2.0f, 58.0f, 1.7f, 0.9f, 0.6f}, {41.0f, 2.0f, 86.0f, 1.15f, 0.2f, 0.9f}, {48.0f, 1.5f, 44.0f, 2.2f, 1.8f, 0.4f}, {55.0f, 2.0f, 70.0f, 1.45f, 0.5f, 0.75f}, {62.0f, 1.5f, 36.0f, 2.5f, 1.1f, 0.3f}, {69.0f, 2.0f, 80.0f, 1.25f, 0.8f, 0.85f}, {76.0f, 2.0f, 54.0f, 1.8f, 0.1f, 0.55f}, {83.0f, 1.5f, 46.0f, 2.3f, 1.6f, 0.4f}, {90.0f, 2.0f, 72.0f, 1.5f, 0.6f, 0.7f}, {96.0f, 1.5f, 40.0f, 2.0f, 1.2f, 0.35f}, {3.0f, 1.5f, 48.0f, 1.9f, 1.0f, 0.45f}, {9.0f, 2.0f, 68.0f, 1.35f, 1.5f, 0.7f}, {16.0f, 1.5f, 40.0f, 2.2f, 0.2f, 0.35f}, {23.0f, 2.0f, 74.0f, 1.4f, 1.9f, 0.75f}, {29.0f, 1.5f, 44.0f, 2.0f, 0.5f, 0.4f}, {37.0f, 2.0f, 62.0f, 1.55f, 1.3f, 0.65f}, {45.0f, 2.0f, 82.0f, 1.2f, 0.9f, 0.85f}, {52.0f, 1.5f, 38.0f, 2.35f, 0.1f, 0.3f}, {59.0f, 2.0f, 66.0f, 1.5f, 1.7f, 0.7f}, {66.0f, 1.5f, 46.0f, 2.1f, 0.4f, 0.4f}, {73.0f, 2.0f, 76.0f, 1.3f, 1.05f, 0.8f}, {80.0f, 1.5f, 42.0f, 2.25f, 1.85f, 0.35f}, {87.0f, 2.0f, 60.0f, 1.6f, 0.75f, 0.6f}, {93.0f, 2.0f, 70.0f, 1.4f, 1.45f, 0.7f}};
    }

    static final class CapeTexturePart
    extends class_1044 {
        private final Supplier<class_276> cX;

        CapeTexturePart(Supplier<class_276> supplier) {
            this.cX = supplier;
        }

        public int method_4624() {
            return this.cX.get().method_30277();
        }
    }
}
