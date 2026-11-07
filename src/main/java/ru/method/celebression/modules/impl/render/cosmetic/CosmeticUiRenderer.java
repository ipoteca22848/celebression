package ru.method.celebression.modules.impl.render.cosmetic;

import java.awt.Color;
import java.util.Locale;
import lombok.Generated;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticCategory;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColorUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColors;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLayout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticMathUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticRenderUtils;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class CosmeticUiRenderer {
    public static void a(class_4587 class_45872, float f, float f2, String string, float f3) {
        float f4 = 17.0f;
        Color color = CosmeticColorUtils.c(CosmeticColors.NQ, f3);
        Color color2 = CosmeticColorUtils.c(new Color(196, 154, 255, 72), f3);
        UiRenderUtils.a(class_45872, Fonts.ROUNDBOLD, string, f, f2 + 1.0f, color2, f4);
        UiRenderUtils.a(class_45872, Fonts.ROUNDBOLD, string, f, f2, color, f4);
    }

    public static void a(class_4587 class_45872, CosmeticMathUtils.Transform transform, Style style, String string, float f, float f2, float f3) {
        float f4 = LinkGuiRenderContext.getFadeMultiplier();
        float f5 = f + f3 * 0.5f;
        float f6 = f2 + f3 * 0.5f;
        Color color = CosmeticColorUtils.c(CosmeticColors.NJ, f4);
        switch (style.ordinal()) {
            case 0: {
                CosmeticLayout.b(class_45872, f5, f6, f3 * 0.88f, color);
                break;
            }
            case 1: {
                CosmeticLayout.c(class_45872, f5, f6, f3 * 0.88f, color);
                break;
            }
            case 2: {
                CosmeticLayout.j(class_45872, f5, f6, f3 * 0.88f, color);
            }
        }
        String string2 = string.toUpperCase(Locale.ROOT);
        UiRenderUtils.a(class_45872, Fonts.ROUNDBOLD, string2, f + f3 + 8.0f, f2 + 1.0f, CosmeticColorUtils.c(CosmeticColors.NJ, f4), 13.0f);
    }

    public static void a(class_4587 class_45872, CosmeticMathUtils.Transform transform, float f, float f2, float f3, CosmeticCategory cosmeticCategory, boolean bl, boolean bl2) {
        float f4 = LinkGuiRenderContext.getFadeMultiplier();
        float f5 = transform.Nv;
        if (bl) {
            CosmeticRenderUtils.g(class_45872, f, f2, f3, f5);
        }
        float f6 = 20.0f;
        float f7 = f + transform.Ny + 14.0f;
        float f8 = f2 + f5 * 0.5f;
        Color color = bl ? Color.WHITE : CosmeticColorUtils.c(CosmeticColors.NT, f4 * (bl2 ? 1.0f : 0.75f));
        CosmeticUiRenderer.a(class_45872, cosmeticCategory, f7, f8, f6, color);
        float f9 = f + transform.Ny + 28.0f + 14.0f;
        Color color2 = bl ? Color.WHITE : CosmeticColorUtils.c(bl2 ? new Color(0xB8BCC8) : CosmeticColors.NT, f4 * (bl2 ? 1.0f : 0.75f));
        UiRenderUtils.a(class_45872, Fonts.MEDIUM, cosmeticCategory.label(), f9, f2 + (f5 - 12.0f) * 0.5f, color2, 12.0f);
        if (bl) {
            float f10 = 22.0f;
            float f11 = f + f3 - f10 - transform.Ny;
            float f12 = f2 + (f5 - f10) * 0.5f;
            float f13 = f11 + f10 * 0.5f;
            UiRenderUtils.drawRoundedRect(class_45872, f11, f12, f10, f10, f10 * 0.5f, CosmeticColorUtils.c(new Color(98, 70, 173, 140), f4));
            UiRenderUtils.c(class_45872, f11, f12, f10, f10, f10 * 0.5f, 1.0f, CosmeticColorUtils.c(new Color(255, 255, 255, 140), f4));
            Render2D.drawVerticalGradient(class_45872, f11 + 1.0f, f12 + 1.0f, f11 + f10 - 1.0f, f12 + 3.0f, new Color(0, 0, 0, Math.round(64.0f * f4)), new Color(0, 0, 0, 0));
            UiRenderUtils.b(class_45872, Fonts.ROUNDBOLD, "\u2713", f13, f12 + f10 * 0.22f, CosmeticColorUtils.c(Color.WHITE, f4), f10 * 0.52f);
        }
    }

    private static void a(class_4587 class_45872, CosmeticCategory cosmeticCategory, float f, float f2, float f3, Color color) {
        switch (cosmeticCategory.lF()) {
            case PA: {
                if (cosmeticCategory.G() == null) break;
                float f4 = f3 * 0.5f;
                Render2D.drawTexture(class_45872, f - f4, f2 - f4, f3, f3, 4.0f, cosmeticCategory.G(), color);
                break;
            }
            case PB: {
                CosmeticLayout.j(class_45872, f, f2, f3, color);
                break;
            }
            case PC: {
                CosmeticLayout.k(class_45872, f, f2, f3, color);
                break;
            }
            case PD: {
                CosmeticLayout.l(class_45872, f, f2, f3, color);
                break;
            }
            case PE: {
                CosmeticLayout.e(class_45872, f, f2, f3, color);
                break;
            }
            case PF: {
                CosmeticLayout.d(class_45872, f, f2, f3, color);
                break;
            }
            case PG: {
                CosmeticLayout.i(class_45872, f, f2, f3, color);
                break;
            }
            case PH: {
                CosmeticLayout.h(class_45872, f, f2, f3, color);
            }
        }
    }

    public static void b(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, boolean bl, boolean bl2) {
        CosmeticRenderUtils.a(class_45872, f, f2, f3, f4, f5, bl, bl2);
    }

    public static void b(class_4587 class_45872, float f, float f2, float f3, float f4, boolean bl, float f5) {
        float f6 = 34.0f * f5;
        float f7 = 18.0f * f5;
        float f8 = 14.0f * f5;
        CosmeticRenderUtils.b(class_45872, f + f3 - f6 - f8, f2 + f4 - f7 - f8, f6, f7, bl);
    }

    public static void a(class_4587 class_45872, CosmeticMathUtils.Transform transform, CosmeticCategory cosmeticCategory, float f, float f2, float f3) {
        float f4 = LinkGuiRenderContext.getFadeMultiplier();
        float f5 = f + f3 * 0.5f;
        float f6 = f2 + f3 * 0.5f;
        Color color = CosmeticColorUtils.c(CosmeticColors.NJ, f4);
        CosmeticUiRenderer.a(class_45872, cosmeticCategory, f5, f6, f3 * 0.88f, color);
        String string = cosmeticCategory.label().toUpperCase(Locale.ROOT);
        UiRenderUtils.a(class_45872, Fonts.ROUNDBOLD, string, f + f3 + 8.0f, f2 + 1.0f, CosmeticColorUtils.c(CosmeticColors.NJ, f4), 13.0f);
    }

    public static void h(class_4587 class_45872, float f, float f2, float f3, float f4) {
        CosmeticRenderUtils.a(class_45872, f, f2, f3, f4, false);
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, float f4, boolean bl) {
        CosmeticRenderUtils.a(class_45872, f, f2, f3, f4, bl);
    }

    public static void a(class_4587 class_45872, float f, float f2, float f3, float f4, class_2960 class_29602, String string, String string2, CosmeticMathUtils.Transform transform) {
        float f5 = LinkGuiRenderContext.getFadeMultiplier();
        CosmeticRenderUtils.f(class_45872, f, f2, f3, f4);
        float f6 = 22.0f;
        float f7 = f + 10.0f;
        float f8 = f2 + (f4 - f6) * 0.5f;
        if (class_29602 != null) {
            Render2D.drawTexture(class_45872, f7, f8, f6, f6, 5.0f, 0.125f, 0.125f, 0.125f, 0.125f, class_29602, Color.WHITE);
        }
        float f9 = 11.0f;
        float f10 = 9.0f;
        float f11 = f7 + f6 + 6.0f;
        float f12 = f2 + 7.0f;
        UiRenderUtils.a(class_45872, Fonts.MEDIUM, string, f11, f12, CosmeticColorUtils.c(CosmeticColorUtils.Qo, f5), f9);
        float f13 = 14.0f;
        float f14 = f11 + Fonts.MEDIUM.getWidth(string, f9) + 5.0f;
        float f15 = f12 + (f9 - f13) * 0.5f;
        CosmeticRenderUtils.e(class_45872, f14, f15, f13, f13, 3.0f);
        CosmeticLayout.f(class_45872, f14 + f13 * 0.5f, f15 + f13 * 0.5f, f13 * 0.55f, CosmeticColorUtils.c(CosmeticColors.NJ, f5));
        if (string2 != null && !string2.isBlank()) {
            UiRenderUtils.a(class_45872, Fonts.MEDIUM, string2, f11, f12 + f9 + 2.0f, CosmeticColorUtils.c(CosmeticColorUtils.Qs, f5 * 0.92f), f10);
        }
    }

    @Generated
    private CosmeticUiRenderer() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static enum Style {
        Qv,
        Qw,
        Qx;
}
}

