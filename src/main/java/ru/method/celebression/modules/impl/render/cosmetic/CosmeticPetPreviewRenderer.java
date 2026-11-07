package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1309;
import net.minecraft.class_332;
import net.minecraft.class_490;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewContext;
import ru.method.celebression.modules.impl.player.CosmeticsModule;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffects;

public final class CosmeticPetPreviewRenderer {
    private CosmeticPetPreviewRenderer() {
    }

    public static void a(class_332 class_3322, int n, int n2, int n3, int n4, int n5) {
        if (n3 <= 0 || n4 <= 0) {
            return;
        }
        CosmeticsModule cosmeticsModule = Celebression.getInstance().getModuleManager().getModule(CosmeticsModule.class);
        if (cosmeticsModule == null) {
            return;
        }
        if (!CosmeticPreviewContext.isActive() && !cosmeticsModule.isToggle()) {
            return;
        }
        CosmeticEffects cosmeticEffects = cosmeticsModule.hX();
        if (cosmeticEffects == null) {
            return;
        }
        class_1309 class_13092 = cosmeticEffects.getPreviewPetEntity();
        if (class_13092 == null) {
            return;
        }
        int n6 = Math.max(24, Math.round((float)n3 * 0.55f));
        int n7 = Math.max(24, Math.round((float)n4 * 0.55f));
        int n8 = n + Math.round((float)n3 * 0.7f);
        int n9 = n2 + Math.round((float)n4 * 0.62f);
        int n10 = n8 - n6 / 2;
        int n11 = n9 - n7 / 2;
        int n12 = n10 + n6;
        int n13 = n11 + n7;
        float f = Math.max(0.4f, class_13092.method_17682());
        int n14 = Math.max(10, Math.round((float)n5 * (1.7f / f) * 0.6f));
        float f2 = n8;
        float f3 = (float)n11 + (float)n7 * 0.45f;
        class_490.method_2486((class_332)class_3322, (int)n10, (int)n11, (int)n12, (int)n13, (int)n14, (float)0.0625f, (float)f2, (float)f3, (class_1309)class_13092);
    }
}

