package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1074;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColorUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColors;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticDefinition;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffectType;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticIconRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLayout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticMathUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewPose;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticUiRenderer;
import ru.method.celebression.util.TextLayoutUtils;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class CosmeticDescriptionRenderer {
    public void a(class_332 class_3322, class_4587 class_45872, CosmeticMathUtils.Transform transform, float f, float f2, float f3, float f4, CosmeticDefinition cosmeticDefinition, CosmeticSlot cosmeticSlot, boolean bl, CosmeticPreviewPose cosmeticPreviewPose, float f5, float f6) {
        boolean bl2 = transform.c(f, f2, f3, f4, f5, f6);
        this.c(class_45872, f, f2, f3, f4, transform.NC, bl, bl2);
        float f7 = 12.0f;
        float f8 = f + (f3 - transform.ND) * 0.5f;
        float f9 = f2 + f7;
        CosmeticUiRenderer.a(class_45872, f8, f9, transform.ND, transform.NE, bl);
        if (cosmeticDefinition.W()) {
            CosmeticLayout.n(class_45872, f8 + transform.ND * 0.5f, f9 + transform.ND * 0.5f, transform.ND * 0.72f, CosmeticColorUtils.Qs);
        } else {
            int n = Math.round(transform.ND * 0.78f);
            cosmeticPreviewPose.a(class_3322, Math.round(f8 + (transform.ND - (float)n) * 0.5f), Math.round(f9 + (transform.ND - (float)n) * 0.5f), n, cosmeticDefinition, cosmeticSlot, 1.0f);
        }
        this.a(class_45872, f, f2, f3, f4, transform, f7, cosmeticDefinition.aC(), bl);
        CosmeticUiRenderer.b(class_45872, f, f2, f3, f4, bl, 1.0f);
    }

    public void a(class_332 class_3322, class_4587 class_45872, CosmeticMathUtils.Transform transform, float f, float f2, float f3, float f4, String string, String string2, CosmeticEffectType cosmeticEffectType, boolean bl, boolean bl2, float f5, float f6) {
        boolean bl3 = transform.c(f, f2, f3, f4, f5, f6);
        this.c(class_45872, f, f2, f3, f4, transform.NC, bl2, bl3);
        float f7 = 12.0f;
        float f8 = f + (f3 - transform.ND) * 0.5f;
        float f9 = f2 + f7;
        CosmeticUiRenderer.a(class_45872, f8, f9, transform.ND, transform.NE, bl2);
        int n = Math.round(transform.ND * 0.78f);
        CosmeticIconRenderer.a(class_3322, Math.round(f8 + (transform.ND - (float)n) * 0.5f), Math.round(f9 + (transform.ND - (float)n) * 0.5f), n, cosmeticEffectType, bl, bl2);
        this.a(class_45872, f, f2, f3, f4, transform, f7, string, bl2);
        CosmeticUiRenderer.b(class_45872, f, f2, f3, f4, bl2, 1.0f);
    }

    private void c(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, boolean bl, boolean bl2) {
        CosmeticUiRenderer.b(class_45872, f, f2, f3, f4, f5, bl, bl2);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, CosmeticMathUtils.Transform transform, float f5, String string, boolean bl) {
        float f6 = f2 + f5 + transform.ND + f5;
        float f7 = transform.ad(12.0f);
        float f8 = transform.ad(10.0f);
        float f9 = f3 - f5 * 2.0f - 44.0f;
        UiRenderUtils.a(class_45872, Fonts.ROUNDBOLD, class_1074.method_4662((String)string, (Object[])new Object[0]), f + f5, f6, bl ? CosmeticColorUtils.Qo : CosmeticColors.NT, f7);
        String string2 = string + ".desc";
        String string3 = class_1074.method_4662((String)string2, (Object[])new Object[0]);
        if (!string3.equals(string2)) {
            UiRenderUtils.a(class_45872, Fonts.MEDIUM, TextLayoutUtils.d(string3, f9, f8), f + f5, f6 + transform.ac(15.0f), CosmeticColorUtils.Qs, f8);
        }
    }
}

