package ru.method.celebression.modules.impl.render.cosmetic;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewTransformState;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffects;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEnums;

public final class Spheres {
    private final CosmeticEffects p;
    private final Map<UUID, TrailState> sphereTrailMap = new HashMap<UUID, TrailState>();
    private int lastEditIdx = Integer.MIN_VALUE;
    private float allLR = 0.0f;
    private float allFB = 0.0f;
    private final float[] offLR = new float[]{0.0f, 0.0f, 0.0f};
    private final float[] offFB = new float[]{0.0f, 0.0f, 0.0f};
    private static final float[] PHASE = new float[]{0.0f, 2.0943952f, 4.1887903f};
    private static final float[] STATIC_OFFSETS_DEG = new float[]{180.0f, -90.0f, 360.0f};
    private final int[] baseCols = new int[3];
    private final SphereCache sphereCache = new SphereCache();

    public Spheres(CosmeticEffects cosmeticEffects) {
        this.p = cosmeticEffects;
    }

    public void deactivate() {
        this.sphereTrailMap.clear();
        this.lastEditIdx = Integer.MIN_VALUE;
        this.allLR = 0.0f;
        this.allFB = 0.0f;
        this.offLR[2] = 0.0f;
        this.offLR[1] = 0.0f;
        this.offLR[0] = 0.0f;
        this.offFB[2] = 0.0f;
        this.offFB[1] = 0.0f;
        this.offFB[0] = 0.0f;
        this.sphereCache.reset();
    }

    public void onWorldRender(WorldRenderStages.MatrixStackStage matrixStackStage) {
        block6: {
            block5: {
                if (CosmeticEffects.aiG.field_1687 == null) break block5;
                if (CosmeticEffects.aiG.field_1724 != null) break block6;
            }
            return;
        }
        if (!this.p.spheresOn()) {
            return;
        }
        this.syncOffsetEditor();
        class_4587 class_45872 = matrixStackStage.l();
        float f = matrixStackStage.k().method_60637(true);
        class_4184 class_41842 = CosmeticEffects.aiG.field_1773.method_19418();
        class_243 class_2432 = class_41842.method_19326();
        boolean bl = CosmeticEffects.aiG.field_1690.method_31044().method_31034();
        boolean bl2 = CosmeticEffects.bool(this.p.self);
        boolean bl3 = CosmeticEffects.bool(this.p.others);
        boolean bl4 = CosmeticEffects.bool(this.p.firstPerson);
        for (class_1657 class_16572 : CosmeticEffects.aiG.field_1687.method_18456()) {
            boolean bl5;
            if (class_16572 == null || class_16572.method_5767()) continue;
            boolean bl6 = bl5 = class_16572 == CosmeticEffects.aiG.field_1724;
            if (bl5 ? !bl2 || bl && !bl4 : !bl3) continue;
            this.renderSpheres(class_45872, class_41842, class_2432, class_16572, f);
        }
    }

    private void renderSpheres(class_4587 matrices, class_4184 camera, class_243 cameraPosition, class_1657 player, float tickDelta) {
        double playerX = class_3532.method_16436((double)tickDelta, (double)player.field_6014, (double)player.method_23317());
        double playerY = class_3532.method_16436((double)tickDelta, (double)player.field_6036, (double)player.method_23318());
        double playerZ = class_3532.method_16436((double)tickDelta, (double)player.field_5969, (double)player.method_23321());
        float bodyYaw = class_3532.method_17821((float)tickDelta, (float)player.field_6220, (float)player.field_6283);
        double yawRadians = Math.toRadians(bodyYaw);
        class_243 forward = new class_243(-Math.sin(yawRadians), 0.0, Math.cos(yawRadians));
        class_243 sideways = new class_243(forward.field_1350, 0.0, -forward.field_1352);
        boolean previewTransform = player == CosmeticEffects.aiG.field_1724
            && !CosmeticEffects.aiG.field_1690.method_31044().method_31034()
            && CosmeticPreviewTransformState.ei;
        double previewLean = previewTransform ? (double)CosmeticPreviewTransformState.ek : 0.0;
        double previewY = playerY + (previewTransform ? (double)CosmeticPreviewTransformState.ej : 0.0);
        double centerY = playerY + (double)player.method_17682() * 0.5 + (double)((Float)this.p.height.jT()).floatValue();
        double topY = playerY + (double)player.method_17682() + (double)((Float)this.p.height.jT()).floatValue();
        float radius = ((Float)this.p.radius.jT()).floatValue();
        float sphereSize = ((Float)this.p.size.jT()).floatValue();
        this.baseCols[0] = ((Color)this.p.exort.jT()).getRGB();
        this.baseCols[1] = ((Color)this.p.wex.jT()).getRGB();
        this.baseCols[2] = ((Color)this.p.quas.jT()).getRGB();
        float time = (float)(System.currentTimeMillis() % 1000000L) / 1000.0f;
        float verticalPulse = class_3532.method_15363((float)(sphereSize * 0.85f), (float)0.0f, (float)0.18f);
        boolean trailsEnabled = this.p.effect.jT() != CosmeticEnums.SphereEffect.NONE;
        boolean fireTrail = this.p.effect.jT() == CosmeticEnums.SphereEffect.FIRE_TRAIL;
        int trailLength = Math.max(6, Math.min(48, ((Float)this.p.trailLen.jT()).intValue()));
        float trailPower = ((Float)this.p.trailPower.jT()).floatValue();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc((int)515);
        RenderSystem.disableCull();
        for (int sphereIndex = 0; sphereIndex < 3; ++sphereIndex) {
            float phase = PHASE[sphereIndex];
            float breathing = 1.0f + (float)(Math.sin(time * 2.1f + phase) * (double)((Float)this.p.breathe.jT()).floatValue());
            float animatedRadius = radius * breathing;
            float visibility = 1.0f;
            double sphereX;
            double sphereY;
            double sphereZ;
            if (this.p.mode.jT() == CosmeticEnums.SphereMode.ORBIT) {
                float angle = time * ((Float)this.p.speed.jT()).floatValue() + phase + (float)yawRadians;
                sphereX = playerX + Math.cos(angle) * (double)animatedRadius;
                sphereZ = playerZ + Math.sin(angle) * (double)animatedRadius;
                sphereY = centerY + Math.sin(time * 2.8f + phase) * (double)verticalPulse;
            } else if (this.p.mode.jT() == CosmeticEnums.SphereMode.SPIRAL) {
                double spiralTop = topY + (double)((Float)this.p.spiralHeight.jT()).floatValue();
                double spiralBottom = playerY + (double)((Float)this.p.height.jT()).floatValue() - (double)((Float)this.p.spiralHeight.jT()).floatValue();
                double spiralSpan = Math.max(0.001, spiralTop - spiralBottom);
                float progress = this.frac(time * (0.18f * ((Float)this.p.speed.jT()).floatValue()) + (float)sphereIndex * 0.33333334f);
                float angle = time * (2.1f * ((Float)this.p.speed.jT()).floatValue()) + phase + (float)yawRadians;
                sphereX = playerX + Math.cos(angle) * (double)animatedRadius;
                sphereZ = playerZ + Math.sin(angle) * (double)animatedRadius;
                sphereY = spiralTop - (double)progress * spiralSpan;
                visibility = this.spiralFade((float)((sphereY - spiralBottom) / spiralSpan));
            } else if (this.p.mode.jT() == CosmeticEnums.SphereMode.SPIRAL_V2) {
                double spiralTop = topY + (double)((Float)this.p.spiralHeight.jT()).floatValue();
                double spiralBottom = playerY + (double)((Float)this.p.height.jT()).floatValue() - (double)((Float)this.p.spiralHeight.jT()).floatValue();
                double spiralSpan = Math.max(0.001, spiralTop - spiralBottom);
                float progress = this.frac(time * (0.22f * ((Float)this.p.speed.jT()).floatValue()) + (float)sphereIndex * 0.33333334f);
                float spiralRadius = animatedRadius * (0.8f + 0.2f * (float)Math.sin(time * 1.7f + phase));
                float angle = time * (2.65f * ((Float)this.p.speed.jT()).floatValue()) + phase * 1.15f + (float)yawRadians;
                sphereX = playerX + Math.cos(angle) * (double)spiralRadius;
                sphereZ = playerZ + Math.sin(angle) * (double)spiralRadius;
                double baseY = spiralTop - (double)progress * spiralSpan;
                sphereY = baseY + Math.sin(time * 1.35f + phase) * (spiralSpan * 0.04);
                visibility = this.spiralFade((float)((sphereY - spiralBottom) / spiralSpan));
            } else if (this.p.mode.jT() == CosmeticEnums.SphereMode.WAVES) {
                float angle = time * ((Float)this.p.speed.jT()).floatValue() + phase + (float)yawRadians;
                float wave = (float)Math.sin(angle * ((Float)this.p.waveFreq.jT()).floatValue() + time * 1.2f);
                sphereX = playerX + Math.cos(angle) * (double)animatedRadius;
                sphereZ = playerZ + Math.sin(angle) * (double)animatedRadius;
                sphereY = topY + (double)(wave * ((Float)this.p.waveAmp.jT()).floatValue()) + Math.sin(time * 2.8f + phase) * (double)verticalPulse;
            } else {
                double angle = Math.toRadians(bodyYaw + STATIC_OFFSETS_DEG[sphereIndex]);
                sphereX = playerX + Math.cos(angle) * (double)animatedRadius;
                sphereZ = playerZ + Math.sin(angle) * (double)animatedRadius;
                sphereY = centerY + Math.sin(time * 2.8f + phase) * (double)verticalPulse;
            }
            float lateralOffset = this.allLR + this.offLR[sphereIndex];
            float forwardOffset = this.allFB + this.offFB[sphereIndex];
            sphereX += sideways.field_1352 * (double)lateralOffset + forward.field_1352 * (double)forwardOffset;
            sphereZ += sideways.field_1350 * (double)lateralOffset + forward.field_1350 * (double)forwardOffset;
            if (previewTransform) {
                double[] transformed = Spheres.leanPoint(sphereX, sphereY, sphereZ, playerX, previewY, playerZ, forward, sideways, previewLean);
                sphereX = transformed[0];
                sphereY = transformed[1];
                sphereZ = transformed[2];
            }
            class_243 cameraDirection = cameraPosition.method_1023(sphereX, sphereY, sphereZ);
            double distance = cameraDirection.method_1033();
            cameraDirection = distance > 1.0E-6 ? cameraDirection.method_1021(1.0 / distance) : new class_243(0.0, 0.0, 1.0);
            int color = this.withAlphaMul(this.baseCols[sphereIndex], visibility);
            if (trailsEnabled) {
                this.pushSphereTrail(player.method_5667(), sphereIndex, new class_243(sphereX, sphereY, sphereZ), trailLength);
                this.renderSphereTrail(matrices, camera, player.method_5667(), sphereIndex, color, trailPower, fireTrail);
            }
            if (visibility > 0.0025f) {
                this.renderOne(matrices, camera, sphereX, sphereY, sphereZ, sphereSize, color, cameraDirection);
            }
        }
        if (this.p.mode.jT() == CosmeticEnums.SphereMode.SPIRAL_V2) {
            this.renderSpiralV2ExtraRight(matrices, camera, cameraPosition, playerX, playerY, playerZ, topY, yawRadians, forward, sideways, trailsEnabled, fireTrail, trailLength, trailPower, player.method_5667());
        }
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    private static double[] leanPoint(double d, double d2, double d3, double d4, double d5, double d6, class_243 class_2432, class_243 class_2433, double d7) {
        double d8 = d - d4;
        double d9 = d2 - d5;
        double d10 = d3 - d6;
        double d11 = d8 * class_2432.field_1352 + d10 * class_2432.field_1350;
        double d12 = d8 * class_2433.field_1352 + d10 * class_2433.field_1350;
        double d13 = Math.cos(d7);
        double d14 = Math.sin(d7);
        double d15 = d11 * d13 + d9 * d14;
        double d16 = d9 * d13 - d11 * d14;
        double d17 = d4 + d15 * class_2432.field_1352 + d12 * class_2433.field_1352;
        double d18 = d6 + d15 * class_2432.field_1350 + d12 * class_2433.field_1350;
        double d19 = d5 + d16;
        return new double[]{d17, d19, d18};
    }

    private void renderSpiralV2ExtraRight(class_4587 class_45872, class_4184 class_41842, class_243 class_2432, double d, double d2, double d3, double d4, double d5, class_243 class_2433, class_243 class_2434, boolean bl, boolean bl2, int n, float f, UUID uUID) {
        float f2 = (float)(System.currentTimeMillis() % 1000000L) / 1000.0f;
        double d6 = d4 + (double)((Float)this.p.spiralHeight.jT()).floatValue();
        double d7 = d2 + (double)((Float)this.p.height.jT()).floatValue() - (double)((Float)this.p.spiralHeight.jT()).floatValue();
        double d8 = Math.max(0.001, d6 - d7);
        float f3 = ((Float)this.p.radius.jT()).floatValue();
        float f4 = ((Float)this.p.size.jT()).floatValue();
        float[] fArray = PHASE;
        int[] nArray = this.baseCols;
        for (int i = 0; i < 3; ++i) {
            float f5 = fArray[i];
            float f6 = this.frac(f2 * (0.22f * ((Float)this.p.speed.jT()).floatValue()) + (float)i * 0.33333334f);
            float f7 = f3 * (0.8f + 0.2f * (float)Math.sin(f2 * 1.7f + f5));
            float f8 = f2 * (2.65f * ((Float)this.p.speed.jT()).floatValue()) + f5 * 1.15f + (float)d5 + (float)Math.PI;
            double d9 = d + Math.cos(f8) * (double)f7;
            double d10 = d3 + Math.sin(f8) * (double)f7;
            double d11 = d6 - (double)f6 * d8;
            double d12 = d11 + Math.sin(f2 * 1.35f + f5) * (d8 * 0.04);
            class_243 class_2435 = class_2432.method_1023(d9 += class_2434.field_1352 * (double)this.allLR + class_2433.field_1352 * (double)this.allFB, d12, d10 += class_2434.field_1350 * (double)this.allLR + class_2433.field_1350 * (double)this.allFB);
            double d13 = class_2435.method_1033();
            class_2435 = d13 > 1.0E-6 ? class_2435.method_1021(1.0 / d13) : new class_243(0.0, 0.0, 1.0);
            float f9 = this.spiralFade((float)((d12 - d7) / d8));
            int n2 = this.withAlphaMul(nArray[i], f9);
            int n3 = 3 + i;
            if (bl) {
                this.pushSphereTrail(uUID, n3, new class_243(d9, d12, d10), n);
                this.renderSphereTrail(class_45872, class_41842, uUID, n3, n2, f, bl2);
            }
            if (!(f9 > 0.0025f)) continue;
            this.renderOne(class_45872, class_41842, d9, d12, d10, f4, n2, class_2435);
        }
    }

    private float spiralFade(float f) {
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        float f3 = 0.14f;
        float f4 = class_3532.method_15363((float)(f2 / f3), (float)0.0f, (float)1.0f);
        float f5 = class_3532.method_15363((float)((1.0f - f2) / f3), (float)0.0f, (float)1.0f);
        float f6 = Math.min(f4, f5);
        return f6 * f6 * (3.0f - 2.0f * f6);
    }

    private int withAlphaMul(int n, float f) {
        int n2 = n >>> 24 & 0xFF;
        int n3 = class_3532.method_15340((int)((int)((float)n2 * f)), (int)0, (int)255);
        return n3 << 24 | n & 0xFFFFFF;
    }

    private void renderOne(class_4587 class_45872, class_4184 class_41842, double d, double d2, double d3, float f, int n, class_243 class_2432) {
        class_45872.method_22903();
        class_45872.method_22904(d, d2, d3);
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc((int)515);
        RenderSystem.depthMask((boolean)true);
        RenderSystem.defaultBlendFunc();
        this.drawSphere(class_45872, f, n, class_2432);
        RenderSystem.setShader((class_10156)class_10142.field_53880);
        RenderSystem.setShaderTexture((int)0, (class_2960)this.p.defaultTexture);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE);
        RenderSystem.depthMask((boolean)false);
        float f2 = f * 6.0f;
        int n2 = n >>> 24 & 0xFF;
        int n3 = Math.min(255, (int)((float)n2 * 0.62f));
        int n4 = n3 << 24 | n & 0xFFFFFF;
        this.drawBillboard(class_45872, class_41842, f2, n4);
        RenderSystem.depthMask((boolean)true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        class_45872.method_22909();
    }

    private void drawBillboard(class_4587 class_45872, class_4184 class_41842, float f, int n) {
        class_45872.method_22903();
        class_45872.method_22907(class_41842.method_23767());
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        float f2 = f * 0.5f;
        class_2872.method_22918(matrix4f, -f2, f2, 0.0f).method_22913(0.0f, 1.0f).method_39415(n);
        class_2872.method_22918(matrix4f, f2, f2, 0.0f).method_22913(1.0f, 1.0f).method_39415(n);
        class_2872.method_22918(matrix4f, f2, -f2, 0.0f).method_22913(1.0f, 0.0f).method_39415(n);
        class_2872.method_22918(matrix4f, -f2, -f2, 0.0f).method_22913(0.0f, 0.0f).method_39415(n);
        class_286.method_43433((class_9801)class_2872.method_60800());
        class_45872.method_22909();
    }

    private void drawSphere(class_4587 class_45872, float f, int n, class_243 class_2432) {
        int n2 = n >>> 24 & 0xFF;
        int n3 = n >>> 16 & 0xFF;
        int n4 = n >>> 8 & 0xFF;
        int n5 = n & 0xFF;
        SphereCache sphereCache = this.sphereCache;
        sphereCache.ensure();
        int n6 = sphereCache.rows;
        int n7 = sphereCache.cols;
        float[] fArray = sphereCache.ux;
        float[] fArray2 = sphereCache.uy;
        float[] fArray3 = sphereCache.uz;
        int[] nArray = sphereCache.nodeColor;
        int n8 = n6 * n7;
        for (int i = 0; i < n8; ++i) {
            nArray[i] = this.shade(fArray[i], fArray2[i], fArray3[i], class_2432, n3, n4, n5, n2);
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        for (int i = 0; i < n6 - 1; ++i) {
            int n9 = i * n7;
            int n10 = (i + 1) * n7;
            for (int j = 0; j < n7; ++j) {
                int n11 = j + 1;
                if (n11 == n7) {
                    n11 = 0;
                }
                int n12 = n9 + j;
                int n13 = n9 + n11;
                int n14 = n10 + j;
                int n15 = n10 + n11;
                float f2 = fArray[n12] * f;
                float f3 = fArray2[n12] * f;
                float f4 = fArray3[n12] * f;
                float f5 = fArray[n15] * f;
                float f6 = fArray2[n15] * f;
                float f7 = fArray3[n15] * f;
                int n16 = nArray[n12];
                int n17 = nArray[n15];
                class_2872.method_22918(matrix4f, f2, f3, f4).method_39415(n16);
                class_2872.method_22918(matrix4f, f5, f6, f7).method_39415(n17);
                class_2872.method_22918(matrix4f, fArray[n13] * f, fArray2[n13] * f, fArray3[n13] * f).method_39415(nArray[n13]);
                class_2872.method_22918(matrix4f, f2, f3, f4).method_39415(n16);
                class_2872.method_22918(matrix4f, fArray[n14] * f, fArray2[n14] * f, fArray3[n14] * f).method_39415(nArray[n14]);
                class_2872.method_22918(matrix4f, f5, f6, f7).method_39415(n17);
            }
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
    }

    private int shade(float f, float f2, float f3, class_243 class_2432, int n, int n2, int n3, int n4) {
        double d;
        double d2;
        double d3;
        double d4;
        double d5 = (double)f * class_2432.field_1352 + (double)f2 * class_2432.field_1351 + (double)f3 * class_2432.field_1350;
        double d6 = 0.4 + 0.6 * (d5 = class_3532.method_15350((double)d5, (double)0.0, (double)1.0));
        double d7 = d6 + (d4 = 0.22 * (d3 = (d2 = (d = 1.0 - d5) * d) * d2));
        if (d7 > 1.0) {
            d7 = 1.0;
        }
        int n5 = this.clamp255((int)((double)n * d7));
        int n6 = this.clamp255((int)((double)n2 * d7));
        int n7 = this.clamp255((int)((double)n3 * d7));
        return (n4 & 0xFF) << 24 | (n5 & 0xFF) << 16 | (n6 & 0xFF) << 8 | n7 & 0xFF;
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

    private float frac(float f) {
        return f - (float)Math.floor(f);
    }

    private void pushSphereTrail(UUID uUID2, int n, class_243 class_2432, int n2) {
        TrailState trailState = this.sphereTrailMap.computeIfAbsent(uUID2, uUID -> new TrailState(6));
        ArrayDeque<TrailPoint> arrayDeque = trailState.queues[class_3532.method_15340((int)n, (int)0, (int)(trailState.queues.length - 1))];
        arrayDeque.addFirst(new TrailPoint(class_2432, System.currentTimeMillis()));
        while (arrayDeque.size() > n2) {
            arrayDeque.removeLast();
        }
    }

    private void renderSphereTrail(class_4587 class_45872, class_4184 class_41842, UUID uUID, int n, int n2, float f, boolean bl) {
        TrailState trailState = this.sphereTrailMap.get(uUID);
        if (trailState == null) {
            return;
        }
        ArrayDeque<TrailPoint> arrayDeque = trailState.queues[class_3532.method_15340((int)n, (int)0, (int)(trailState.queues.length - 1))];
        if (arrayDeque.isEmpty()) {
            return;
        }
        float f2 = (float)(n2 >>> 24 & 0xFF) / 255.0f;
        float f3 = (float)(n2 >>> 16 & 0xFF) / 255.0f;
        float f4 = (float)(n2 >>> 8 & 0xFF) / 255.0f;
        float f5 = (float)(n2 & 0xFF) / 255.0f;
        RenderSystem.setShader((class_10156)class_10142.field_53880);
        RenderSystem.setShaderTexture((int)0, (class_2960)this.p.defaultTexture);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE);
        RenderSystem.depthMask((boolean)false);
        RenderSystem.disableCull();
        int n3 = arrayDeque.size();
        if (n3 == 0) {
            RenderSystem.depthMask((boolean)true);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            return;
        }
        Vector3f vector3f = new Vector3f(1.0f, 0.0f, 0.0f).rotate((Quaternionfc)class_41842.method_23767());
        Vector3f vector3f2 = new Vector3f(0.0f, 1.0f, 0.0f).rotate((Quaternionfc)class_41842.method_23767());
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        int n4 = 0;
        for (TrailPoint trailPoint : arrayDeque) {
            float f6;
            float f7 = n3 <= 1 ? 0.0f : (float)n4 / (float)(n3 - 1);
            float f8 = 1.0f - f7;
            f8 *= f8;
            float f9 = class_3532.method_15363((float)(f2 * (0.6f * f8) * f), (float)0.0f, (float)1.0f);
            float f10 = ((Float)this.p.size.jT()).floatValue() * 4.8f * (0.55f + 0.7f * f8);
            float f11 = f3;
            float f12 = f4;
            float f13 = f5;
            if (bl) {
                f6 = class_3532.method_15363((float)(0.22f + 0.78f * f8), (float)0.0f, (float)1.0f);
                f11 = f11 * (1.0f - f6) + 1.0f * f6;
                f12 = f12 * (1.0f - f6) + 0.78f * f6;
                f13 = f13 * (1.0f - f6) + 0.32f * f6;
                f9 = class_3532.method_15363((float)(f9 * 1.18f), (float)0.0f, (float)1.0f);
            }
            f6 = f10 * 0.5f;
            float f14 = (float)trailPoint.pos.field_1352;
            float f15 = (float)trailPoint.pos.field_1351;
            float f16 = (float)trailPoint.pos.field_1350;
            class_2872.method_22918(matrix4f, f14 + (-vector3f.x + vector3f2.x) * f6, f15 + (-vector3f.y + vector3f2.y) * f6, f16 + (-vector3f.z + vector3f2.z) * f6).method_22913(0.0f, 1.0f).method_22915(f11, f12, f13, f9);
            class_2872.method_22918(matrix4f, f14 + (vector3f.x + vector3f2.x) * f6, f15 + (vector3f.y + vector3f2.y) * f6, f16 + (vector3f.z + vector3f2.z) * f6).method_22913(1.0f, 1.0f).method_22915(f11, f12, f13, f9);
            class_2872.method_22918(matrix4f, f14 + (vector3f.x - vector3f2.x) * f6, f15 + (vector3f.y - vector3f2.y) * f6, f16 + (vector3f.z - vector3f2.z) * f6).method_22913(1.0f, 0.0f).method_22915(f11, f12, f13, f9);
            class_2872.method_22918(matrix4f, f14 + (-vector3f.x - vector3f2.x) * f6, f15 + (-vector3f.y - vector3f2.y) * f6, f16 + (-vector3f.z - vector3f2.z) * f6).method_22913(0.0f, 0.0f).method_22915(f11, f12, f13, f9);
            ++n4;
        }
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.depthMask((boolean)true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
    }

    private void syncOffsetEditor() {
        int n = this.getEditIndex();
        if (n != this.lastEditIdx) {
            this.lastEditIdx = n;
            if (n < 0) {
                this.p.editLR.a(Float.valueOf(this.allLR));
                this.p.editFB.a(Float.valueOf(this.allFB));
            } else {
                this.p.editLR.a(Float.valueOf(this.offLR[n]));
                this.p.editFB.a(Float.valueOf(this.offFB[n]));
            }
            return;
        }
        float f = ((Float)this.p.editLR.jT()).floatValue();
        float f2 = ((Float)this.p.editFB.jT()).floatValue();
        if (n < 0) {
            if (!this.eq(f, this.allLR) || !this.eq(f2, this.allFB)) {
                this.allLR = f;
                this.allFB = f2;
            }
        } else if (!this.eq(f, this.offLR[n]) || !this.eq(f2, this.offFB[n])) {
            this.offLR[n] = f;
            this.offFB[n] = f2;
        }
    }

    private boolean eq(float f, float f2) {
        return Math.abs(f - f2) <= 1.0E-4f;
    }

    private int getEditIndex() {
        if (this.p.sphereEditor.jT() == CosmeticEnums.SphereEditor.EXORT) {
            return 0;
        }
        if (this.p.sphereEditor.jT() == CosmeticEnums.SphereEditor.WEX) {
            return 1;
        }
        if (this.p.sphereEditor.jT() == CosmeticEnums.SphereEditor.QUAS) {
            return 2;
        }
        return -1;
    }

    static final class SphereCache {
        static final int STACKS = 18;
        static final int SLICES = 28;
        int rows;
        int cols;
        float[] ux;
        float[] uy;
        float[] uz;
        int[] nodeColor;

        SphereCache() {
        }

        void reset() {
            this.rows = 0;
            this.cols = 0;
            this.ux = null;
            this.uy = null;
            this.uz = null;
            this.nodeColor = null;
        }

        void ensure() {
            if (this.ux != null) {
                return;
            }
            this.rows = 19;
            this.cols = 28;
            int n = this.rows * this.cols;
            this.ux = new float[n];
            this.uy = new float[n];
            this.uz = new float[n];
            this.nodeColor = new int[n];
            int n2 = 0;
            for (int i = 0; i < this.rows; ++i) {
                double d = (double)i / 18.0 * Math.PI - 1.5707963267948966;
                float f = (float)Math.sin(d);
                float f2 = (float)Math.cos(d);
                int n3 = 0;
                while (n3 < this.cols) {
                    double d2 = (double)n3 / 28.0 * (Math.PI * 2);
                    this.ux[n2] = (float)(Math.cos(d2) * (double)f2);
                    this.uy[n2] = f;
                    this.uz[n2] = (float)(Math.sin(d2) * (double)f2);
                    ++n3;
                    ++n2;
                }
            }
        }
    }

    static final class TrailState {
        final ArrayDeque<TrailPoint>[] queues;

        TrailState(int n) {
            this.queues = new ArrayDeque[n];
            for (int i = 0; i < n; ++i) {
                this.queues[i] = new ArrayDeque();
            }
        }
    }

    static final class TrailPoint {
        final class_243 pos;
        final long time;

        TrailPoint(class_243 class_2432, long l) {
            this.pos = class_2432;
            this.time = l;
        }
    }
}
