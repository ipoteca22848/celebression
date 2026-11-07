package ru.method.celebression.modules.impl.render.cosmetic;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_7833;
import ru.method.celebression.modules.impl.render.cosmetic.BoneTransformSet;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModel;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;

public final class CosmeticModelPreviewRenderer {
    private static final int ee = 0xF000F0;
    private static final float ef = 32.0f;

    private CosmeticModelPreviewRenderer() {
    }

    public static void a(class_332 class_3322, int n, int n2, int n3, CosmeticModel cosmeticModel, CosmeticSlot cosmeticSlot, int n4) {
        if (cosmeticModel == null || cosmeticModel.isEmpty() || cosmeticModel.texture() == null || n3 <= 0) {
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return;
        }
        float f = (float)n + (float)n3 * 0.5f;
        float f2 = (float)n2 + (float)n3 * 0.5f + (cosmeticSlot == CosmeticSlot.dt ? (float)n3 * 0.24f : 0.0f);
        float f3 = Math.max(0.75f, (float)n3 / 46.0f);
        float f4 = 32.0f * f3;
        float f5 = cosmeticSlot == CosmeticSlot.dt ? 180.0f : 190.0f;
        float f6 = cosmeticSlot == CosmeticSlot.dt ? 6.0f : -4.0f;
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 100.0f);
        class_45872.method_22905(-(f4 + 10.0f), f4 + 10.0f, f4 + 10.0f);
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(180.0f));
        class_45872.method_22907(class_7833.field_40716.rotationDegrees(135.0f));
        class_45872.method_22907(class_7833.field_40716.rotationDegrees(-135.0f + f5));
        class_45872.method_22907(class_7833.field_40714.rotationDegrees(f6));
        class_45872.method_22905(-0.5f, 0.5f, 0.5f);
        float f7 = cosmeticModel.bm();
        if (cosmeticSlot == CosmeticSlot.dt) {
            f7 = Math.max(f7 * 1.85f, 0.7f);
        } else if (cosmeticSlot == CosmeticSlot.ds) {
            f7 = Math.max(f7, 1.15f);
        }
        class_45872.method_46416(-0.05f, cosmeticModel.bl(), 0.0f);
        class_45872.method_22905(f7, f7, f7);
        if (cosmeticModel.bi() != 0.0f) {
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(cosmeticModel.bi()));
        }
        if (cosmeticModel.bj() != 0.0f) {
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(cosmeticModel.bj()));
        }
        if (cosmeticModel.bk() != 0.0f) {
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(cosmeticModel.bk()));
        }
        class_45872.method_46416(cosmeticModel.bf() * 0.0625f, cosmeticModel.bg() * 0.0625f, cosmeticModel.bh() * 0.0625f);
        float f8 = cosmeticModel.scale();
        class_45872.method_22905(f8, f8, f8);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_4597.class_4598 class_45982 = class_3102.method_22940().method_23000();
        BoneTransformSet boneTransformSet = new BoneTransformSet();
        CosmeticModelRenderer.a(cosmeticModel, class_45872, (class_4597)class_45982, 0xF000F0, n4, boneTransformSet);
        class_45982.method_22993();
        class_45872.method_22909();
        RenderSystem.disableDepthTest();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

