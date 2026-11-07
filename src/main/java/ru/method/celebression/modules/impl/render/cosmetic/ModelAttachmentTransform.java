package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_4587;
import net.minecraft.class_591;
import net.minecraft.class_630;
import net.minecraft.class_7833;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModel;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelAnchor;

public final class ModelAttachmentTransform {
    private static final float SCALE = 0.0625f;
    private static final float aU = 57.295776f;

    private ModelAttachmentTransform() {
    }

    public static void a(class_4587 class_45872, class_591 class_5912, CosmeticModel cosmeticModel) {
        ModelAttachmentTransform.a(class_45872, class_5912, cosmeticModel, 0.0f, 0.0f);
    }

    public static void a(class_4587 class_45872, class_591 class_5912, CosmeticModel cosmeticModel, float f) {
        ModelAttachmentTransform.a(class_45872, class_5912, cosmeticModel, f, 0.0f);
    }

    public static void a(class_4587 class_45872, class_591 class_5912, CosmeticModel cosmeticModel, float f, float f2) {
        float f3 = ModelAttachmentTransform.a(cosmeticModel.be(), class_5912, class_45872, f2);
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(180.0f));
        float f4 = cosmeticModel.be() == CosmeticModelAnchor.cY ? f : 0.0f;
        ModelAttachmentTransform.a(cosmeticModel, f3 + f4, class_45872);
    }

    private static float a(CosmeticModelAnchor cosmeticModelAnchor, class_591 class_5912, class_4587 class_45872, float f) {
        if (class_5912 == null) {
            return 0.0f;
        }
        return switch (cosmeticModelAnchor) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case CosmeticModelAnchor.cY -> {
                ModelAttachmentTransform.a(class_5912.field_3398, class_45872, f);
                yield 0.5f;
            }
            case CosmeticModelAnchor.cZ -> {
                ModelAttachmentTransform.a(class_5912.field_3391, class_45872, 0.0f);
                yield -0.3f;
            }
        };
    }

    private static void a(class_630 class_6302, class_4587 class_45872, float f) {
        if (class_6302 == null) {
            return;
        }
        class_45872.method_46416(class_6302.field_3657 * 0.0625f, class_6302.field_3656 * 0.0625f, class_6302.field_3655 * 0.0625f);
        ModelAttachmentTransform.c(class_45872, (class_6302.field_3654 + f) * 57.295776f, class_6302.field_3675 * 57.295776f, class_6302.field_3674 * 57.295776f);
    }

    private static void a(CosmeticModel cosmeticModel, float f, class_4587 class_45872) {
        class_45872.method_46416(cosmeticModel.bf(), cosmeticModel.bg() + f, cosmeticModel.bh());
        if (cosmeticModel.bi() != 0.0f) {
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(cosmeticModel.bi()));
        }
        if (cosmeticModel.bj() != 0.0f) {
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(cosmeticModel.bj()));
        }
        if (cosmeticModel.bk() != 0.0f) {
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(cosmeticModel.bk()));
        }
        float f2 = cosmeticModel.scale();
        class_45872.method_22905(f2, f2, f2);
    }

    private static void c(class_4587 class_45872, float f, float f2, float f3) {
        if (f3 != 0.0f) {
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(f3));
        }
        if (f2 != 0.0f) {
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(f2));
        }
        if (f != 0.0f) {
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(f));
        }
    }
}

