package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1309;
import net.minecraft.class_332;
import net.minecraft.class_490;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLoadout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewContext;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffects;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEnums;

public final class CosmeticPreviewSetup {
    private CosmeticPreviewSetup() {
    }

        public static void a(class_332 class_3322, CosmeticEffects cosmeticEffects, CosmeticEnums.PetSkin petSkin, int n, int n2, int n3) {
        if (cosmeticEffects == null || petSkin == null || n3 <= 0) {
            return;
        }
        class_1309 class_13092 = cosmeticEffects.getPreviewPetEntity(petSkin);
        if (class_13092 == null) {
            return;
        }
        int n4 = Math.max(2, Math.round((float)n3 * 0.08f));
        int n5 = n + n4;
        int n6 = n2 + n4;
        int n7 = n + n3 - n4;
        int n8 = n2 + n3 - n4;
        float f = Math.max(0.4f, class_13092.method_17682());
        int n9 = Math.max(12, Math.round((float)n3 * (1.6f / f)));
        float f2 = (float)n + (float)n3 * 0.5f;
        float f3 = (float)n2 + (float)n3 * 0.58f;
        CosmeticPreviewContext.a(new CosmeticPreviewContext.PreviewState(new CosmeticLoadout(), CosmeticSlot.du, false, 0.0f, 0.0f, 0.016666668f, true));
        try {
            class_490.method_2486((class_332)class_3322, (int)n5, (int)n6, (int)n7, (int)n8, (int)n9, (float)0.0625f, (float)f2, (float)(f3 - (float)n3 * 0.04f), (class_1309)class_13092);
        }
        finally {
            CosmeticPreviewContext.end();
        }
    }
}

