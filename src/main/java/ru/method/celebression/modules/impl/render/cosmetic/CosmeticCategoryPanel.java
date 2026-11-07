package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1074;
import net.minecraft.class_4587;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticCategory;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticMathUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticUiRenderer;

public final class CosmeticCategoryPanel {
    public void a(class_4587 class_45872, CosmeticMathUtils.Transform transform, CosmeticCategory[] cosmeticCategoryArray, CosmeticCategory cosmeticCategory, float f, float f2) {
        float f3 = transform.Ni;
        float f4 = transform.lk();
        float f5 = 16.0f;
        CosmeticUiRenderer.a(class_45872, transform, CosmeticUiRenderer.Style.Qv, class_1074.method_4662((String)"cosmetics.categories", (Object[])new Object[0]), f3 + transform.Nt, f4 + transform.Nt, f5);
        float f6 = f4 + transform.Nz;
        float f7 = f3 + transform.Nt;
        float f8 = transform.Nj - transform.Nt * 2.0f;
        for (CosmeticCategory cosmeticCategory2 : cosmeticCategoryArray) {
            boolean bl = cosmeticCategory2 == cosmeticCategory;
            boolean bl2 = transform.c(f7, f6, f8, transform.Nv, f, f2);
            CosmeticUiRenderer.a(class_45872, transform, f7, f6, f8, cosmeticCategory2, bl, bl2);
            f6 += transform.Nv + transform.Nw;
        }
    }

    public CosmeticCategory a(CosmeticMathUtils.Transform transform, CosmeticCategory[] cosmeticCategoryArray, float f, float f2) {
        float f3 = transform.lk() + transform.Nz;
        float f4 = transform.Ni + transform.Nt;
        float f5 = transform.Nj - transform.Nt * 2.0f;
        for (CosmeticCategory cosmeticCategory : cosmeticCategoryArray) {
            if (transform.c(f4, f3, f5, transform.Nv, f, f2)) {
                return cosmeticCategory;
            }
            f3 += transform.Nv + transform.Nw;
        }
        return null;
    }
}

