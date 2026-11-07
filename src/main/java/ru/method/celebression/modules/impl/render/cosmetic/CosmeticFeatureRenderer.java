package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_10017;
import net.minecraft.class_10055;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_591;
import net.minecraft.class_7833;
import org.jetbrains.annotations.Nullable;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewContext;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewTransformState;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsService;
import ru.method.celebression.modules.impl.render.cosmetic.PlayerCosmeticModelRenderer;

@Environment(value=EnvType.CLIENT)
public class CosmeticFeatureRenderer
extends class_3887<class_10055, class_591> {
    public CosmeticFeatureRenderer(class_3883<class_10055, class_591> class_38832) {
        super(class_38832);
    }

    @Override
    public void method_4199(class_4587 class_45872, class_4597 class_45972, int n, class_10055 class_100552, float f, float f2) {
        String string;
        if (class_100552 == null || class_100552.field_53542 || class_100552.field_53461) {
            return;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return;
        }
        CosmeticsService cosmeticsService = celebression.getCosmeticsService();
        if (cosmeticsService == null || !cosmeticsService.isEnabled()) {
            return;
        }
        UUID uUID = this.b(class_100552);
        if (!cosmeticsService.d(uUID, string = class_100552.field_53529)) {
            return;
        }
        class_591 class_5912 = (class_591)this.method_17165();
        class_1657 class_16572 = this.a(class_100552);
        boolean bl = CosmeticFeatureRenderer.a(class_16572);
        boolean bl2 = CosmeticPreviewTransformState.active;
        if (cosmeticsService.c(CosmeticSlot.ds, uUID, string)) {
            float f3;
            float f4 = f3 = class_16572 != null && !class_16572.method_6118(class_1304.field_6169).method_7960() ? 0.17f : 0.0f;
            if (bl2) {
                class_45872.method_22903();
                CosmeticFeatureRenderer.a(class_45872);
            }
            PlayerCosmeticModelRenderer.a(class_45872, class_45972, n, class_5912, cosmeticsService.b(CosmeticSlot.ds, uUID, string), CosmeticSlot.ds, f2, bl, f3, 0.0f, uUID, string);
            if (bl2) {
                class_45872.method_22909();
            }
        }
        if (cosmeticsService.c(CosmeticSlot.dt, uUID, string)) {
            if (bl2) {
                class_45872.method_22903();
                CosmeticFeatureRenderer.a(class_45872);
            }
            PlayerCosmeticModelRenderer.a(class_45872, class_45972, n, class_5912, cosmeticsService.b(CosmeticSlot.dt, uUID, string), CosmeticSlot.dt, f2, bl, uUID, string);
            if (bl2) {
                class_45872.method_22909();
            }
        }
    }

    private static void a(class_4587 class_45872) {
        class_45872.method_46416(0.0f, CosmeticPreviewTransformState.pivotY, 0.0f);
        class_45872.method_22907(class_7833.field_40714.rotation(CosmeticPreviewTransformState.eg));
        class_45872.method_46416(0.0f, -CosmeticPreviewTransformState.pivotY, 0.0f);
    }

    private static boolean a(class_1657 class_16572) {
        if (class_16572 == null) {
            return false;
        }
        return Math.abs(class_16572.method_23317() - class_16572.field_6014) > 1.0E-4 || Math.abs(class_16572.method_23321() - class_16572.field_5969) > 1.0E-4;
    }

    @Nullable
    private class_1657 a(class_10055 class_100552) {
        Object object;
        class_310 class_3102 = class_310.method_1551();
        if (class_3102.field_1724 != null && class_100552.field_53529 != null && (object = class_3102.field_1724.method_7334().getName()) != null && ((String)object).equalsIgnoreCase(class_100552.field_53529)) {
            return class_3102.field_1724;
        }
        if (class_3102.field_1687 == null || class_100552.field_53529 == null) {
            return null;
        }
        for (class_1657 class_16572 : class_3102.field_1687.method_18456()) {
            if (class_16572.method_7334().getName() == null || !class_16572.method_7334().getName().equalsIgnoreCase(class_100552.field_53529)) continue;
            return class_16572;
        }
        return null;
    }

    @Nullable
    private UUID b(class_10055 class_100552) {
        class_310 class_3102 = class_310.method_1551();
        if (CosmeticPreviewContext.isActive() && class_3102.field_1724 != null) {
            return class_3102.field_1724.method_5667();
        }
        class_1657 player = this.a(class_100552);
        return player == null ? null : player.method_5667();
    }
}
