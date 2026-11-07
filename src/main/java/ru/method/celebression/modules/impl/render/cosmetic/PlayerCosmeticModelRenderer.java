package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.animation.Animation;

import java.util.UUID;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_591;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.BoneTransformSet;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticAnimationApplicator;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticDefinition;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModel;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelCache;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewContext;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsService;
import ru.method.celebression.modules.impl.render.cosmetic.ModelAttachmentTransform;
import ru.method.celebression.animation.MotionBlendState;

public final class PlayerCosmeticModelRenderer {
    private static final MotionBlendState em = new MotionBlendState();
    private static long en;
    private static final long eo;
    private static final float ep = 0.5f;

    private PlayerCosmeticModelRenderer() {
    }

    public static void a(class_4587 class_45872, class_4597 class_45972, int n, class_591 class_5912, CosmeticDefinition cosmeticDefinition, CosmeticSlot cosmeticSlot, float f, boolean bl, UUID uUID, String string) {
        PlayerCosmeticModelRenderer.a(class_45872, class_45972, n, class_5912, cosmeticDefinition, cosmeticSlot, f, bl, 0.0f, 0.0f, uUID, string);
    }

        public static void a(class_4587 class_45872, class_4597 class_45972, int n, class_591 class_5912, CosmeticDefinition cosmeticDefinition, CosmeticSlot cosmeticSlot, float f, boolean bl, float f2, float f3, UUID uUID, String string) {
        if (cosmeticDefinition == null || !cosmeticDefinition.X()) {
            return;
        }
        CosmeticModel cosmeticModel = CosmeticModelCache.b(cosmeticDefinition.aE());
        if (cosmeticModel == null || cosmeticModel.isEmpty()) {
            return;
        }
        float f4 = PlayerCosmeticModelRenderer.aY();
        float f5 = PlayerCosmeticModelRenderer.aZ();
        float f6 = PlayerCosmeticModelRenderer.b(f, f4, bl);
        BoneTransformSet boneTransformSet = new BoneTransformSet();
        CosmeticAnimationApplicator.a(boneTransformSet, cosmeticModel, f5, f6);
        int n2 = PlayerCosmeticModelRenderer.a(cosmeticSlot, uUID, string);
        class_45872.method_22903();
        try {
            ModelAttachmentTransform.a(class_45872, class_5912, cosmeticModel, f2, f3);
            CosmeticModelRenderer.a(cosmeticModel, class_45872, class_45972, n, n2, boneTransformSet);
        }
        finally {
            class_45872.method_22909();
        }
    }

    private static float aY() {
        CosmeticPreviewContext.PreviewState previewState = CosmeticPreviewContext.aO();
        if (previewState != null) {
            return previewState.aT();
        }
        long l = System.nanoTime();
        if (en == 0L) {
            en = l;
            return 0.016666668f;
        }
        float f = (float)(l - en) / 1.0E9f;
        en = l;
        return class_3532.method_15363((float)f, (float)0.004166667f, (float)0.1f);
    }

    private static float b(float f, float f2, boolean bl) {
        CosmeticPreviewContext.PreviewState previewState = CosmeticPreviewContext.aO();
        if (previewState != null) {
            return previewState.bv();
        }
        em.a(bl, f, f2, false);
        return em.bv();
    }

    private static float aZ() {
        CosmeticPreviewContext.PreviewState previewState = CosmeticPreviewContext.aO();
        if (previewState != null) {
            return previewState.aS();
        }
        return (float)(System.nanoTime() - eo) / 1.0E9f * 0.5f;
    }

    private static int a(CosmeticSlot cosmeticSlot, UUID uUID, String string) {
        if (cosmeticSlot != CosmeticSlot.dt && cosmeticSlot != CosmeticSlot.du && cosmeticSlot != CosmeticSlot.ds) {
            return -1;
        }
        CosmeticPreviewContext.PreviewState previewState = CosmeticPreviewContext.aO();
        if (previewState != null) {
            return previewState.aQ().g(cosmeticSlot);
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return -1;
        }
        CosmeticsService cosmeticsService = celebression.getCosmeticsService();
        if (cosmeticsService == null) {
            return -1;
        }
        if (uUID == null && (string == null || string.isBlank())) {
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 == null || class_3102.field_1724 == null) {
                return -1;
            }
            return cosmeticsService.a(cosmeticSlot, class_3102.field_1724.method_5667(), class_3102.field_1724.method_7334().getName());
        }
        return cosmeticsService.a(cosmeticSlot, uUID, string);
    }

    static {
        eo = System.nanoTime();
    }
}

