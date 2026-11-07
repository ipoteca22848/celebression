package ru.method.celebression.modules.impl.render.cosmetic;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
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
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewTransformState;
import ru.method.celebression.manager.UserSyncManager;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffects;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEnums;

public final class Nimb {
    private final CosmeticEffects p;
    private final TorusCache torusCache = new TorusCache();
    private final Quaternionf qTmp = new Quaternionf();

    public Nimb(CosmeticEffects cosmeticEffects) {
        this.p = cosmeticEffects;
    }

    public void deactivate() {
        this.torusCache.reset();
    }

    public void onWorldRender(WorldRenderStages.MatrixStackStage matrixStackStage) {
        block6: {
            block5: {
                if (CosmeticEffects.aiG.field_1687 == null) break block5;
                if (CosmeticEffects.aiG.field_1724 != null) break block6;
            }
            return;
        }
        if (!this.p.haloOn()) {
            return;
        }
        class_4587 class_45872 = matrixStackStage.l();
        float f = matrixStackStage.k().method_60637(true);
        class_4184 class_41842 = CosmeticEffects.aiG.field_1773.method_19418();
        class_243 class_2432 = class_41842.method_19326();
        boolean bl = CosmeticEffects.aiG.field_1690.method_31044().method_31034();
        boolean bl2 = CosmeticEffects.bool(this.p.haloOthers);
        boolean bl3 = CosmeticEffects.bool(this.p.haloFirstPerson);
        double d = Math.max(8.0, Math.min(192.0, (double)((Float)this.p.haloDistance.jT()).floatValue()));
        double d2 = d * d;
        UserSyncManager userSyncManager = Celebression.getInstance() == null ? null : Celebression.getInstance().getUserSyncManager();
        for (class_1657 class_16572 : CosmeticEffects.aiG.field_1687.method_18456()) {
            double d3;
            boolean bl4;
            if (class_16572 == null || class_16572.method_5767()) continue;
            boolean bl5 = bl4 = class_16572 == CosmeticEffects.aiG.field_1724;
            if (!bl4 && !bl2 || bl4 && bl && !bl3 || !bl4 && userSyncManager != null && class_16572.method_7334() != null && userSyncManager.g(class_16572.method_7334().getId(), class_16572.method_7334().getName()) != null) continue;
            double d4 = class_3532.method_16436((double)f, (double)class_16572.field_6014, (double)class_16572.method_23317());
            double d5 = class_3532.method_16436((double)f, (double)class_16572.field_6036, (double)class_16572.method_23318());
            double d6 = class_3532.method_16436((double)f, (double)class_16572.field_5969, (double)class_16572.method_23321());
            double d7 = class_2432.field_1352 - d4;
            double d8 = d5 + (double)class_16572.method_17682() + 0.12 + (double)((Float)this.p.haloYOffset.jT()).floatValue();
            double d9 = class_2432.field_1351 - d8;
            if (d7 * d7 + d9 * d9 + (d3 = class_2432.field_1350 - d6) * d3 > d2) continue;
            float f2 = class_3532.method_17821((float)f, (float)class_16572.field_6220, (float)class_16572.field_6283);
            double d10 = Math.toRadians(f2);
            this.renderHalo(class_45872, class_41842, class_16572, d4, d5, d6, d10, ((Color)this.p.haloColor.jT()).getRGB(), ((Float)this.p.haloRadius.jT()).floatValue(), ((Float)this.p.haloThickness.jT()).floatValue(), ((Float)this.p.haloYOffset.jT()).floatValue(), CosmeticEffects.bool(this.p.haloAnimate));
        }
    }

    public void renderRemoteHalo(class_4587 class_45872, class_4184 class_41842, class_1657 class_16572, float f, UserSyncManager.SyncResult syncResult) {
        if (class_16572 == null || syncResult == null) {
            return;
        }
        double d = class_3532.method_16436((double)f, (double)class_16572.field_6014, (double)class_16572.method_23317());
        double d2 = class_3532.method_16436((double)f, (double)class_16572.field_6036, (double)class_16572.method_23318());
        double d3 = class_3532.method_16436((double)f, (double)class_16572.field_5969, (double)class_16572.method_23321());
        float f2 = class_3532.method_17821((float)f, (float)class_16572.field_6220, (float)class_16572.field_6283);
        this.renderHalo(class_45872, class_41842, class_16572, d, d2, d3, Math.toRadians(f2), syncResult.ow, syncResult.ox, syncResult.oy, syncResult.oz, syncResult.oA);
    }

    private void renderHalo(class_4587 class_45872, class_4184 class_41842, class_1657 class_16572, double d, double d2, double d3, double d4, int n, float f, float f2, float f3, boolean bl) {
        int n2;
        int n3;
        int n4;
        int n5;
        double d5;
        double d6 = d2 + (double)class_16572.method_17682() + 0.12 + (double)f3;
        float f4 = (float)(System.currentTimeMillis() % 1000000L) / 1000.0f;
        float f5 = class_3532.method_15363((float)(f2 * 0.8f), (float)0.0f, (float)0.12f);
        if (bl) {
            d6 += Math.sin(f4 * 2.8f) * (double)f5;
        }
        if (class_16572 == CosmeticEffects.aiG.field_1724) {
            if (!CosmeticEffects.aiG.field_1690.method_31044().method_31034() && CosmeticPreviewTransformState.ei) {
                double d7 = d2 + (double)CosmeticPreviewTransformState.ej;
                double d8 = d6 - d7;
                double d9 = CosmeticPreviewTransformState.ek;
                d5 = Math.sin(d9);
                double d10 = Math.cos(d9);
                double d11 = -Math.sin(d4);
                double d12 = Math.cos(d4);
                d += d11 * d8 * d5;
                d3 += d12 * d8 * d5;
                d6 = d7 + d8 * d10;
            }
        }
        float f6 = f;
        float f7 = Math.max(0.01f, f2 * 0.5f);
        class_243 class_2432 = class_41842.method_19326().method_1023(d, d6, d3);
        double d13 = class_2432.method_1033();
        class_2432 = d13 > 1.0E-6 ? class_2432.method_1021(1.0 / d13) : new class_243(0.0, 0.0, 1.0);
        class_243 class_2433 = new class_243(0.35, 1.0, 0.25);
        d5 = class_2433.method_1033();
        class_2433 = d5 > 1.0E-6 ? class_2433.method_1021(1.0 / d5) : new class_243(0.0, 1.0, 0.0);
        if (this.p.haloQuality.jT() == CosmeticEnums.HaloQuality.LOW) {
            n5 = 64;
            n4 = 18;
            n3 = 1;
        } else if (this.p.haloQuality.jT() == CosmeticEnums.HaloQuality.HIGH) {
            n5 = 128;
            n4 = 30;
            n3 = 3;
        } else if (this.p.haloQuality.jT() == CosmeticEnums.HaloQuality.ULTRA) {
            n5 = 160;
            n4 = 36;
            n3 = 3;
        } else {
            n5 = 96;
            n4 = 24;
            n3 = 2;
        }
        if (d13 >= 32.0) {
            n5 = 32;
            n4 = 10;
            n3 = 0;
        } else if (d13 >= 12.0) {
            n5 = Math.max(24, n5 * 55 / 100);
            n4 = Math.max(10, n4 * 55 / 100);
            n3 = Math.min(n3, 1);
        }
        this.torusCache.ensure(n5, n4);
        this.computeNodeLights(class_2432, class_2433);
        class_45872.method_22903();
        class_45872.method_22904(d, d6, d3);
        this.qTmp.identity().rotateY((float)d4);
        class_45872.method_22907(this.qTmp);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc((int)515);
        RenderSystem.disableCull();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask((boolean)false);
        this.drawHaloTorusFast(class_45872, f6, f7, n);
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE);
        if (n3 >= 1) {
            n2 = this.withAlphaMul(n, 0.34f);
            this.drawHaloTorusFast(class_45872, f6, f7 * 1.55f, n2);
        }
        if (n3 >= 2) {
            n2 = this.withAlphaMul(n, 0.16f);
            this.drawHaloTorusFast(class_45872, f6, f7 * 2.35f, n2);
        }
        if (n3 >= 3) {
            n2 = this.withAlphaMul(n, 0.08f);
            this.drawHaloTorusFast(class_45872, f6, f7 * 3.25f, n2);
        }
        RenderSystem.depthMask((boolean)true);
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_45872.method_22909();
    }

    private void computeNodeLights(class_243 class_2432, class_243 class_2433) {
        int n = this.torusCache.majorSeg;
        int n2 = this.torusCache.minorSeg;
        float[] fArray = this.torusCache.majorCos;
        float[] fArray2 = this.torusCache.majorSin;
        float[] fArray3 = this.torusCache.minorCos;
        float[] fArray4 = this.torusCache.minorSin;
        float[] fArray5 = this.torusCache.nodeLight;
        float f = (float)class_2432.field_1352;
        float f2 = (float)class_2432.field_1351;
        float f3 = (float)class_2432.field_1350;
        float f4 = (float)class_2433.field_1352;
        float f5 = (float)class_2433.field_1351;
        float f6 = (float)class_2433.field_1350;
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            float f7 = fArray[i];
            float f8 = fArray2[i];
            int n4 = 0;
            while (n4 < n2) {
                float f9;
                float f10 = fArray3[n4];
                float f11 = f10 * f7;
                float f12 = fArray4[n4];
                float f13 = f10 * f8;
                float f14 = f11 * f + f12 * f2 + f13 * f3;
                if (f14 < 0.0f) {
                    f14 = 0.0f;
                }
                if (f14 > 1.0f) {
                    f14 = 1.0f;
                }
                float f15 = f11 * f4 + f12 * f5 + f13 * f6;
                if ((f15 = (f15 + 1.0f) * 0.5f) < 0.0f) {
                    f15 = 0.0f;
                }
                if (f15 > 1.0f) {
                    f15 = 1.0f;
                }
                float f16 = 1.0f - f14;
                float f17 = f14 * f14;
                float f18 = f17 * f17;
                float f19 = f18 * f18;
                float f20 = f19 * f17;
                fArray5[n3] = (f9 = 0.22f + 0.68f * f15 + (f16 *= f16) * 0.3f + f20 * 0.2f) > 1.0f ? 1.0f : f9;
                ++n4;
                ++n3;
            }
        }
    }

    private void drawHaloTorusFast(class_4587 class_45872, float f, float f2, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6 = n >>> 24 & 0xFF;
        int n7 = n >>> 16 & 0xFF;
        int n8 = n >>> 8 & 0xFF;
        int n9 = n & 0xFF;
        int n10 = this.torusCache.majorSeg;
        int n11 = this.torusCache.minorSeg;
        float[] fArray = this.torusCache.majorCos;
        float[] fArray2 = this.torusCache.majorSin;
        float[] fArray3 = this.torusCache.minorCos;
        float[] fArray4 = this.torusCache.minorSin;
        float[] fArray5 = this.torusCache.nodeLight;
        float[] fArray6 = this.torusCache.nodeX;
        float[] fArray7 = this.torusCache.nodeY;
        float[] fArray8 = this.torusCache.nodeZ;
        int[] nArray = this.torusCache.nodeColor;
        int n12 = 0;
        int n13 = (n6 & 0xFF) << 24;
        for (int i = 0; i < n10; ++i) {
            float f3 = fArray[i];
            float f4 = fArray2[i];
            n5 = 0;
            while (n5 < n11) {
                float f5 = fArray3[n5];
                float f6 = f + f2 * f5;
                fArray6[n12] = f6 * f3;
                fArray7[n12] = f2 * fArray4[n5];
                fArray8[n12] = f6 * f4;
                float f7 = fArray5[n12];
                n4 = this.clamp255((int)((float)n7 * f7));
                n3 = this.clamp255((int)((float)n8 * f7));
                n2 = this.clamp255((int)((float)n9 * f7));
                nArray[n12] = n13 | n4 << 16 | n3 << 8 | n2;
                ++n5;
                ++n12;
            }
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        for (int i = 0; i < n10; ++i) {
            n5 = i + 1;
            if (n5 == n10) {
                n5 = 0;
            }
            int n14 = i * n11;
            int n15 = n5 * n11;
            for (int j = 0; j < n11; ++j) {
                n4 = j + 1;
                if (n4 == n11) {
                    n4 = 0;
                }
                n3 = n14 + j;
                n2 = n15 + j;
                int n16 = n15 + n4;
                int n17 = n14 + n4;
                float f8 = fArray6[n3];
                float f9 = fArray7[n3];
                float f10 = fArray8[n3];
                float f11 = fArray6[n16];
                float f12 = fArray7[n16];
                float f13 = fArray8[n16];
                int n18 = nArray[n3];
                int n19 = nArray[n16];
                class_2872.method_22918(matrix4f, f8, f9, f10).method_39415(n18);
                class_2872.method_22918(matrix4f, fArray6[n2], fArray7[n2], fArray8[n2]).method_39415(nArray[n2]);
                class_2872.method_22918(matrix4f, f11, f12, f13).method_39415(n19);
                class_2872.method_22918(matrix4f, f8, f9, f10).method_39415(n18);
                class_2872.method_22918(matrix4f, f11, f12, f13).method_39415(n19);
                class_2872.method_22918(matrix4f, fArray6[n17], fArray7[n17], fArray8[n17]).method_39415(nArray[n17]);
            }
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
    }

    private int clamp255(int n) {
        if (n < 0) {
            return 0;
        }
        if (n > 255) {
            return 255;
        }
        return n;
    }

    private int withAlphaMul(int n, float f) {
        int n2 = n >>> 24 & 0xFF;
        int n3 = class_3532.method_15340((int)((int)((float)n2 * f)), (int)0, (int)255);
        return n3 << 24 | n & 0xFFFFFF;
    }

    static final class TorusCache {
        int majorSeg;
        int minorSeg;
        float[] majorCos;
        float[] majorSin;
        float[] minorCos;
        float[] minorSin;
        float[] nodeLight;
        float[] nodeX;
        float[] nodeY;
        float[] nodeZ;
        int[] nodeColor;

        TorusCache() {
        }

        void reset() {
            this.majorSeg = 0;
            this.minorSeg = 0;
            this.majorCos = null;
            this.majorSin = null;
            this.minorCos = null;
            this.minorSin = null;
            this.nodeLight = null;
            this.nodeX = null;
            this.nodeY = null;
            this.nodeZ = null;
            this.nodeColor = null;
        }

        void ensure(int n, int n2) {
            double d;
            int n3;
            if (n == this.majorSeg && n2 == this.minorSeg && this.majorCos != null && this.minorCos != null) {
                return;
            }
            this.majorSeg = Math.max(12, n);
            this.minorSeg = Math.max(8, n2);
            this.majorCos = new float[this.majorSeg];
            this.majorSin = new float[this.majorSeg];
            this.minorCos = new float[this.minorSeg];
            this.minorSin = new float[this.minorSeg];
            int n4 = this.majorSeg * this.minorSeg;
            this.nodeLight = new float[n4];
            this.nodeX = new float[n4];
            this.nodeY = new float[n4];
            this.nodeZ = new float[n4];
            this.nodeColor = new int[n4];
            for (n3 = 0; n3 < this.majorSeg; ++n3) {
                d = (double)n3 / (double)this.majorSeg * (Math.PI * 2);
                this.majorCos[n3] = (float)Math.cos(d);
                this.majorSin[n3] = (float)Math.sin(d);
            }
            for (n3 = 0; n3 < this.minorSeg; ++n3) {
                d = (double)n3 / (double)this.minorSeg * (Math.PI * 2);
                this.minorCos[n3] = (float)Math.cos(d);
                this.minorSin[n3] = (float)Math.sin(d);
            }
        }
    }
}

