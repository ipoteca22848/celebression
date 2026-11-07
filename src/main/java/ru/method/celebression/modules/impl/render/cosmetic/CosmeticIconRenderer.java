package ru.method.celebression.modules.impl.render.cosmetic;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColorUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColors;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticDefinition;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffectType;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLayout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModel;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelCache;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelPreviewRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;

public final class CosmeticIconRenderer {
    private static final int eb = 64;
    private static final int ec = 32;

    private CosmeticIconRenderer() {
    }

    public static void a(class_332 class_3322, int n, int n2, int n3, CosmeticDefinition cosmeticDefinition, CosmeticSlot cosmeticSlot, int n4) {
        Object object;
        if (cosmeticDefinition == null || cosmeticDefinition.W()) {
            return;
        }
        if (cosmeticSlot == CosmeticSlot.dr) {
            class_2960 class_29602 = CosmeticIconRenderer.b(cosmeticDefinition);
            if (class_29602 != null) {
                CosmeticIconRenderer.a(class_3322, n, n2, n3, class_29602, n4);
            }
            return;
        }
        if (cosmeticSlot == CosmeticSlot.du) {
            CosmeticIconRenderer.a(class_3322, n, n2, n3, cosmeticDefinition.aF());
            return;
        }
        if (cosmeticDefinition.X() && (object = CosmeticModelCache.b(cosmeticDefinition.aE())) != null && !((CosmeticModel)object).isEmpty()) {
            CosmeticModelPreviewRenderer.a(class_3322, n, n2, n3, (CosmeticModel)object, cosmeticSlot, n4);
            return;
        }
        object = CosmeticIconRenderer.b(cosmeticDefinition);
        if (object == null) {
            return;
        }
        CosmeticIconRenderer.a(class_3322, n, n2, n3, (class_2960)object, n4, 64, 64);
    }

    public static void a(class_332 class_3322, int n, int n2, int n3, CosmeticEffectType cosmeticEffectType, boolean bl, boolean bl2) {
        class_4587 class_45872 = class_3322.method_51448();
        if (bl || cosmeticEffectType == null) {
            CosmeticLayout.n(class_45872, (float)n + (float)n3 / 2.0f, (float)n2 + (float)n3 / 2.0f, (float)n3 * 0.76f, CosmeticColors.NU);
            return;
        }
        Color color = bl2 ? Color.WHITE : CosmeticColors.NT;
        CosmeticIconRenderer.a(class_3322, n, n2, n3, cosmeticEffectType, color);
    }

    @Deprecated
    public static void a(class_332 class_3322, int n, int n2, int n3, CosmeticEffectType cosmeticEffectType, boolean bl) {
        CosmeticIconRenderer.a(class_3322, n, n2, n3, cosmeticEffectType, bl, false);
    }

    public static void a(class_332 class_3322, int n, int n2, int n3, CosmeticEffectType cosmeticEffectType) {
        CosmeticIconRenderer.a(class_3322, n, n2, n3, cosmeticEffectType, ColorUtils.getPrimaryColor());
    }

    public static void a(class_332 class_3322, int n, int n2, int n3, CosmeticEffectType cosmeticEffectType, Color color) {
        if (cosmeticEffectType == null) {
            return;
        }
        class_4587 class_45872 = class_3322.method_51448();
        float f = (float)n + (float)n3 / 2.0f;
        float f2 = (float)n2 + (float)n3 / 2.0f;
        float f3 = (float)n3 * 0.3f;
        switch (cosmeticEffectType) {
            case dk: {
                Render2D.drawRing(class_45872, f, f2 - (float)n3 * 0.04f, f3, Math.max(1.8f, (float)n3 * 0.08f), color);
                break;
            }
            case dl: {
                Render2D.drawRing(class_45872, f, f2, f3, Math.max(1.2f, (float)n3 * 0.035f), CosmeticColorUtils.c(color, 0.55f));
                float f4 = (float)n3 * 0.09f;
                for (int i = 0; i < 3; ++i) {
                    double d = (double)i * 2.0943951023931953 - 1.5707963267948966;
                    Render2D.drawCircle(class_45872, f + (float)Math.cos(d) * f3, f2 + (float)Math.sin(d) * f3, f4, color);
                }
                break;
            }
            case dm: {
                for (int i = 0; i < 5; ++i) {
                    float f5 = (float)i / 4.0f;
                    float f6 = (float)n + (float)n3 * 0.24f + (float)n3 * 0.52f * f5;
                    float f7 = (float)n2 + (float)n3 * 0.3f + (float)n3 * 0.42f * f5;
                    Render2D.drawCircle(class_45872, f6, f7, Math.max(1.0f, (float)n3 * 0.1f * (1.0f - f5 * 0.55f)), ColorUtils.alpha(color, Math.round(255.0f * (1.0f - f5 * 0.5f))));
                }
                break;
            }
        }
    }

    private static class_2960 b(CosmeticDefinition cosmeticDefinition) {
        if (cosmeticDefinition.aD() != null) {
            return cosmeticDefinition.aD();
        }
        return cosmeticDefinition.texture();
    }

    public static void a(class_332 class_3322, int n, int n2, int n3, class_2960 class_29602, int n4, int n5, int n6) {
        float f = (float)n3 * 0.12f;
        int n7 = Math.max(1, Math.round((float)n3 - f * 2.0f));
        int n8 = Math.round((float)n + (float)(n3 - n7) * 0.5f);
        int n9 = Math.round((float)n2 + (float)(n3 - n7) * 0.5f);
        CosmeticIconRenderer.a(n4);
        class_3322.method_25290(class_1921::method_62277, class_29602, n8, n9, 0.0f, 0.0f, n7, n7, n5, n6);
        CosmeticIconRenderer.aW();
    }

    public static void a(class_332 class_3322, int n, int n2, int n3, class_2960 class_29602, int n4) {
        float f = (float)n + (float)n3 * 0.5f;
        float f2 = (float)n2 + (float)n3 * 0.56f;
        float f3 = (float)n3 * 0.78f;
        float f4 = (float)n3 * 0.34f;
        float f5 = (float)n3 * 0.72f;
        class_4587 class_45872 = class_3322.method_51448();
        CosmeticIconRenderer.a(n4);
        class_45872.method_22903();
        class_45872.method_46416(f, f2 - f3 * 0.42f, 0.0f);
        float f6 = f4 * 1.15f;
        float f7 = f3 * 0.14f;
        CosmeticIconRenderer.a(class_3322, -f6 * 0.5f, 0.0f, f6, f7, class_29602, 1.0f, 0.0f, 10.0f, 4.0f);
        class_45872.method_22909();
        CosmeticIconRenderer.a(class_3322, f - f5 * 0.5f, f2 - f3 * 0.28f, f5 * 0.5f, f3, class_29602, true);
        CosmeticIconRenderer.a(class_3322, f, f2 - f3 * 0.28f, f5 * 0.5f, f3, class_29602, false);
        CosmeticIconRenderer.aW();
    }

    private static void a(class_332 class_3322, float f, float f2, float f3, float f4, class_2960 class_29602, boolean bl) {
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        float f5 = bl ? -11.0f : 11.0f;
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(f5));
        float f6 = bl ? 1.0f : 11.0f;
        CosmeticIconRenderer.a(class_3322, 0.0f, 0.0f, f3, f4, class_29602, f6, 1.0f, 10.0f, 16.0f);
        class_45872.method_22909();
    }

    private static void a(class_332 class_3322, float f, float f2, float f3, float f4, class_2960 class_29602, float f5, float f6, float f7, float f8) {
        class_3322.method_25302(class_1921::method_62277, class_29602, Math.round(f), Math.round(f2), f5, f6, Math.round(f3), Math.round(f4), Math.round(f7), Math.round(f8), 64, 32);
    }

    public static int a(int n, float f) {
        int n2 = class_3532.method_15340((int)Math.round((float)(n >> 24 & 0xFF) * f), (int)0, (int)255);
        return n2 << 24 | n & 0xFFFFFF;
    }

    private static void a(int n) {
        Color color = new Color(n, true);
        RenderSystem.setShaderColor((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }

    private static void aW() {
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

