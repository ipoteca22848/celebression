package ru.method.celebression.modules.impl.render.cosmetic;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.api.event.WorldRenderEvent;

public final class CosmeticWorldEffectRenderer
implements MinecraftClientAccess {
    private static final CosmeticWorldEffectRenderer dO = new CosmeticWorldEffectRenderer();
    private static final int dP = 96;
    private static final int dQ = 20;
    private static final int dR = 4;
    private static final int dS = 24;
    private final EffectState dT = new EffectState();
    private final Quaternionf dU = new Quaternionf();
    private final Map<UUID, Deque<class_243>> dV = new HashMap<UUID, Deque<class_243>>();

    private CosmeticWorldEffectRenderer() {
    }

    public static CosmeticWorldEffectRenderer aV() {
        return dO;
    }

    @EventHandler
    public void onRenderWorld(WorldRenderEvent worldRenderEvent) {
    }

    private void a(class_4587 class_45872, class_243 class_2432, class_1657 class_16572, float f, int n) {
        class_243 class_2433 = class_16572.method_30950(f);
        double d = (double)class_16572.method_17682() + 0.3;
        float f2 = (float)(System.currentTimeMillis() % 1000000L) / 1000.0f;
        double d2 = Math.sin(f2 * 2.6f) * 0.03;
        float f3 = class_3532.method_16439((float)f, (float)class_16572.field_6220, (float)class_16572.field_6283);
        float f4 = 0.32f;
        float f5 = 0.045f;
        class_243 class_2434 = new class_243(class_2433.field_1352, class_2433.field_1351 + d + d2, class_2433.field_1350);
        class_243 class_2435 = class_2432.method_1020(class_2434);
        double d3 = class_2435.method_1033();
        class_2435 = d3 > 1.0E-6 ? class_2435.method_1021(1.0 / d3) : new class_243(0.0, 0.0, 1.0);
        class_243 class_2436 = new class_243(0.35, 1.0, 0.25).method_1029();
        this.dT.ensure(96, 20);
        class_45872.method_22903();
        class_45872.method_22904(class_2434.field_1352 - class_2432.field_1352, class_2434.field_1351 - class_2432.field_1351, class_2434.field_1350 - class_2432.field_1350);
        class_45872.method_22907(this.dU.identity().rotateY((float)Math.toRadians(-f3)));
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc((int)515);
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask((boolean)false);
        this.a(class_45872, f4, f5, n, class_2435, class_2436);
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE);
        this.a(class_45872, f4, f5 * 1.55f, CosmeticWorldEffectRenderer.withAlphaMul(n, 0.34f), class_2435, class_2436);
        this.a(class_45872, f4, f5 * 2.35f, CosmeticWorldEffectRenderer.withAlphaMul(n, 0.16f), class_2435, class_2436);
        RenderSystem.depthMask((boolean)true);
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_45872.method_22909();
    }

    private void a(class_4587 class_45872, float f, float f2, int n, class_243 class_2432, class_243 class_2433) {
        int n2 = n >>> 24 & 0xFF;
        int n3 = n >>> 16 & 0xFF;
        int n4 = n >>> 8 & 0xFF;
        int n5 = n & 0xFF;
        int n6 = this.dT.majorSeg;
        int n7 = this.dT.minorSeg;
        float[] fArray = this.dT.majorCos;
        float[] fArray2 = this.dT.majorSin;
        float[] fArray3 = this.dT.minorCos;
        float[] fArray4 = this.dT.minorSin;
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        for (int i = 0; i < n6; ++i) {
            int n8 = (i + 1) % n6;
            float f3 = fArray[i];
            float f4 = fArray2[i];
            float f5 = fArray[n8];
            float f6 = fArray2[n8];
            for (int j = 0; j < n7; ++j) {
                int n9 = (j + 1) % n7;
                float f7 = fArray3[j];
                float f8 = fArray4[j];
                float f9 = fArray3[n9];
                float f10 = fArray4[n9];
                float f11 = f + f2 * f7;
                float f12 = f + f2 * f9;
                float f13 = f11 * f3;
                float f14 = f2 * f8;
                float f15 = f11 * f4;
                float f16 = f11 * f5;
                float f17 = f2 * f8;
                float f18 = f11 * f6;
                float f19 = f12 * f5;
                float f20 = f2 * f10;
                float f21 = f12 * f6;
                float f22 = f12 * f3;
                float f23 = f2 * f10;
                float f24 = f12 * f4;
                int n10 = this.a(f7 * f3, f8, f7 * f4, class_2432, class_2433, n3, n4, n5, n2);
                int n11 = this.a(f7 * f5, f8, f7 * f6, class_2432, class_2433, n3, n4, n5, n2);
                int n12 = this.a(f9 * f5, f10, f9 * f6, class_2432, class_2433, n3, n4, n5, n2);
                int n13 = this.a(f9 * f3, f10, f9 * f4, class_2432, class_2433, n3, n4, n5, n2);
                class_2872.method_22918(matrix4f, f13, f14, f15).method_39415(n10);
                class_2872.method_22918(matrix4f, f16, f17, f18).method_39415(n11);
                class_2872.method_22918(matrix4f, f19, f20, f21).method_39415(n12);
                class_2872.method_22918(matrix4f, f13, f14, f15).method_39415(n10);
                class_2872.method_22918(matrix4f, f19, f20, f21).method_39415(n12);
                class_2872.method_22918(matrix4f, f22, f23, f24).method_39415(n13);
            }
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
    }

    private int a(float f, float f2, float f3, class_243 class_2432, class_243 class_2433, int n, int n2, int n3, int n4) {
        float f4 = CosmeticWorldEffectRenderer.clamp01((float)((double)f * class_2432.field_1352 + (double)f2 * class_2432.field_1351 + (double)f3 * class_2432.field_1350));
        float f5 = CosmeticWorldEffectRenderer.clamp01(((float)((double)f * class_2433.field_1352 + (double)f2 * class_2433.field_1351 + (double)f3 * class_2433.field_1350) + 1.0f) * 0.5f);
        float f6 = (1.0f - f4) * (1.0f - f4);
        float f7 = f4 * f4;
        float f8 = f7 * f7;
        float f9 = f8 * f8;
        float f10 = f9 * f7;
        float f11 = Math.min(1.0f, 0.22f + 0.68f * f5 + f6 * 0.3f + f10 * 0.2f);
        int n5 = CosmeticWorldEffectRenderer.clamp255((int)((float)n * f11));
        int n6 = CosmeticWorldEffectRenderer.clamp255((int)((float)n2 * f11));
        int n7 = CosmeticWorldEffectRenderer.clamp255((int)((float)n3 * f11));
        return n4 << 24 | n5 << 16 | n6 << 8 | n7;
    }

    private void a(class_4587 class_45872, class_4184 class_41842, class_243 class_2432, class_1657 class_16572, float f, int n) {
        class_243 class_2433 = class_16572.method_30950(f);
        double d = class_2433.field_1351 + (double)class_16572.method_17682() * 0.55;
        float f2 = (float)((double)(System.currentTimeMillis() % 100000L) / 1000.0 * 1.4);
        float f3 = 0.7f;
        float f4 = 0.1f;
        Quaternionf quaternionf = class_41842.method_23767();
        Vector3f vector3f = quaternionf.transform((Vector3fc)new Vector3f(1.0f, 0.0f, 0.0f), new Vector3f());
        Vector3f vector3f2 = quaternionf.transform((Vector3fc)new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f());
        int n2 = n >>> 24 & 0xFF;
        float f5 = (float)(n >>> 16 & 0xFF) / 255.0f;
        float f6 = (float)(n >>> 8 & 0xFF) / 255.0f;
        float f7 = (float)(n & 0xFF) / 255.0f;
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc((int)515);
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE);
        RenderSystem.depthMask((boolean)false);
        class_45872.method_22903();
        class_45872.method_22904(class_2433.field_1352 - class_2432.field_1352, d - class_2432.field_1351, class_2433.field_1350 - class_2432.field_1350);
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        for (int i = 0; i < 4; ++i) {
            double d2 = (double)f2 + (double)i * 1.5707963267948966;
            float f8 = (float)(Math.cos(d2) * (double)f3);
            float f9 = (float)(Math.sin(d2) * (double)f3);
            float f10 = (float)(Math.sin((double)f2 * 1.7 + (double)i) * 0.14);
            this.a(class_2872, matrix4f, f8, f10, f9, vector3f, vector3f2, f4 * 2.2f, f5, f6, f7, Math.round((float)n2 * 0.14f), 14);
            this.a(class_2872, matrix4f, f8, f10, f9, vector3f, vector3f2, f4 * 1.4f, f5, f6, f7, Math.round((float)n2 * 0.3f), 14);
            this.a(class_2872, matrix4f, f8, f10, f9, vector3f, vector3f2, f4, f5, f6, f7, n2, 14);
            this.a(class_2872, matrix4f, f8, f10, f9, vector3f, vector3f2, f4 * 0.45f, 1.0f, 1.0f, 1.0f, Math.round((float)n2 * 0.85f), 12);
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.depthMask((boolean)true);
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        class_45872.method_22909();
    }

    private void a(class_287 class_2872, Matrix4f matrix4f, float f, float f2, float f3, Vector3f vector3f, Vector3f vector3f2, float f4, float f5, float f6, float f7, int n, int n2) {
        float f8 = (float)Math.max(0, Math.min(255, n)) / 255.0f;
        for (int i = 0; i < n2; ++i) {
            double d = Math.PI * 2 * (double)i / (double)n2;
            double d2 = Math.PI * 2 * (double)(i + 1) / (double)n2;
            float f9 = (float)Math.cos(d);
            float f10 = (float)Math.sin(d);
            float f11 = (float)Math.cos(d2);
            float f12 = (float)Math.sin(d2);
            class_2872.method_22918(matrix4f, f, f2, f3).method_22915(f5, f6, f7, f8);
            class_2872.method_22918(matrix4f, f + (vector3f.x * f9 + vector3f2.x * f10) * f4, f2 + (vector3f.y * f9 + vector3f2.y * f10) * f4, f3 + (vector3f.z * f9 + vector3f2.z * f10) * f4).method_22915(f5, f6, f7, f8);
            class_2872.method_22918(matrix4f, f + (vector3f.x * f11 + vector3f2.x * f12) * f4, f2 + (vector3f.y * f11 + vector3f2.y * f12) * f4, f3 + (vector3f.z * f11 + vector3f2.z * f12) * f4).method_22915(f5, f6, f7, f8);
        }
    }

    private void a(class_4587 class_45872, class_4184 class_41842, class_243 class_2432, class_1657 class_16572, float f, int n, UUID uUID2) {
        if (uUID2 == null) {
            return;
        }
        class_243 class_2433 = class_16572.method_30950(f);
        class_243 class_2434 = new class_243(class_2433.field_1352, class_2433.field_1351 + (double)class_16572.method_17682() * 0.5, class_2433.field_1350);
        Deque<class_243> deque = this.dV.computeIfAbsent(uUID2, uUID -> new ArrayDeque<>());
        class_243 class_2435 = deque.peekFirst();
        if (class_2435 == null || class_2435.method_1025(class_2434) > 9.0E-4) {
            deque.addFirst(class_2434);
            while (deque.size() > 24) {
                deque.removeLast();
            }
        }
        if (deque.size() < 2) {
            return;
        }
        Quaternionf quaternionf = class_41842.method_23767();
        Vector3f vector3f = quaternionf.transform((Vector3fc)new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f());
        int n2 = n >>> 16 & 0xFF;
        int n3 = n >>> 8 & 0xFF;
        int n4 = n & 0xFF;
        int n5 = n >>> 24 & 0xFF;
        float f2 = (float)n2 / 255.0f;
        float f3 = (float)n3 / 255.0f;
        float f4 = (float)n4 / 255.0f;
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc((int)515);
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE);
        RenderSystem.depthMask((boolean)false);
        class_45872.method_22903();
        class_45872.method_22904(-class_2432.field_1352, -class_2432.field_1351, -class_2432.field_1350);
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27380, class_290.field_1576);
        int n6 = deque.size();
        int n7 = 0;
        for (class_243 class_2436 : deque) {
            float f5 = 1.0f - (float)n7 / (float)(n6 - 1);
            float f6 = 0.16f * f5;
            float f7 = (float)n5 / 255.0f * f5 * f5;
            float f8 = vector3f.x * f6;
            float f9 = vector3f.y * f6;
            float f10 = vector3f.z * f6;
            class_2872.method_22918(matrix4f, (float)class_2436.field_1352 - f8, (float)class_2436.field_1351 - f9, (float)class_2436.field_1350 - f10).method_22915(f2, f3, f4, f7);
            class_2872.method_22918(matrix4f, (float)class_2436.field_1352 + f8, (float)class_2436.field_1351 + f9, (float)class_2436.field_1350 + f10).method_22915(f2, f3, f4, f7);
            ++n7;
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.depthMask((boolean)true);
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        class_45872.method_22909();
    }

    private static int withAlphaMul(int n, float f) {
        int n2 = n >>> 24 & 0xFF;
        int n3 = class_3532.method_15340((int)((int)((float)n2 * f)), (int)0, (int)255);
        return n3 << 24 | n & 0xFFFFFF;
    }

    private static float clamp01(float f) {
        return f < 0.0f ? 0.0f : (f > 1.0f ? 1.0f : f);
    }

    private static int clamp255(int n) {
        return n < 0 ? 0 : (n > 255 ? 255 : n);
    }

    static final class EffectState {
        int majorSeg;
        int minorSeg;
        float[] majorCos;
        float[] majorSin;
        float[] minorCos;
        float[] minorSin;

        EffectState() {
        }

        void ensure(int n, int n2) {
            double d;
            int n3;
            if (n == this.majorSeg && n2 == this.minorSeg && this.majorCos != null) {
                return;
            }
            this.majorSeg = Math.max(12, n);
            this.minorSeg = Math.max(8, n2);
            this.majorCos = new float[this.majorSeg];
            this.majorSin = new float[this.majorSeg];
            this.minorCos = new float[this.minorSeg];
            this.minorSin = new float[this.minorSeg];
            for (n3 = 0; n3 < this.majorSeg; ++n3) {
                d = (double)n3 / (double)this.majorSeg * Math.PI * 2.0;
                this.majorCos[n3] = (float)Math.cos(d);
                this.majorSin[n3] = (float)Math.sin(d);
            }
            for (n3 = 0; n3 < this.minorSeg; ++n3) {
                d = (double)n3 / (double)this.minorSeg * Math.PI * 2.0;
                this.minorCos[n3] = (float)Math.cos(d);
                this.minorSin[n3] = (float)Math.sin(d);
            }
        }
    }
}
