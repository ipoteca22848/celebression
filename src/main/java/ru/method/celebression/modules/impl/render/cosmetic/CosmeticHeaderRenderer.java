package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.Locale;
import net.minecraft.class_1074;
import net.minecraft.class_4587;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColorUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColors;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticMathUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticRenderUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticUiRenderer;
import ru.method.celebression.render.Rect;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class CosmeticHeaderRenderer {
    public Rect a(class_4587 class_45872, CosmeticMathUtils.Transform transform, float f, float f2) {
        float f3 = 30.0f;
        float f4 = transform.Ng;
        float f5 = 14.0f + (transform.Nh - 14.0f - f3) * 0.5f;
        CosmeticRenderUtils.d(class_45872, f4, f5, f3);
        float f6 = f4 + f3 + 12.0f;
        float f7 = LinkGuiRenderContext.getFadeMultiplier();
        String string = class_1074.method_4662((String)"cosmetics.title", (Object[])new Object[0]).toUpperCase(Locale.ROOT);
        CosmeticUiRenderer.a(class_45872, f6, 16.0f, string, f7);
        float f8 = f7 * 0.72f;
        UiRenderUtils.a(class_45872, Fonts.MEDIUM, class_1074.method_4662((String)"cosmetics.subtitle", (Object[])new Object[0]), f6, 34.0f, CosmeticColorUtils.c(CosmeticColors.NR, f8), 11.0f);
        float f9 = transform.ll() - transform.Ng - transform.NF;
        float f10 = 12.0f + (transform.Nh - transform.NF) * 0.5f;
        boolean bl = transform.c(f9, f10, transform.NF, transform.NF, f, f2);
        CosmeticRenderUtils.a(class_45872, f9, f10, transform.NF, bl);
        return new Rect(f9, f10, transform.NF, transform.NF);
    }
}

