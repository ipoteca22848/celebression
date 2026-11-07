package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_284;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_5944;
import net.minecraft.class_9801;
import ru.method.celebression.api.render.providers.ResourceProvider;

public final class CapePreviewRenderer {
    private static final class_10156 KEY = new class_10156(ResourceProvider.getShaderIdentifier("cape_preview"), class_290.field_1575, class_10149.field_53930);
    private static final float FACE_U0 = 0.015625f;
    private static final float FACE_V0 = 0.03125f;
    private static final float FACE_U1 = 0.171875f;
    private static final float FACE_V1 = 0.53125f;

    private CapePreviewRenderer() {
    }

    public static void render(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, class_2960 class_29602, float f6, float f7) {
        CapePreviewRenderer.render(class_45872, f, f2, f3, f4, f5, class_29602, f6, f7, 0.015625f, 0.03125f, 0.171875f, 0.53125f);
    }

    public static void render(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, class_2960 class_29602, float f6, float f7, float f8, float f9, float f10, float f11) {
        if (f3 <= 0.0f || f4 <= 0.0f || f6 <= 0.0f || class_29602 == null) {
            return;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        class_5944 class_59442 = RenderSystem.setShader((class_10156)KEY);
        RenderSystem.setShaderTexture((int)0, (class_2960)class_29602);
        CapePreviewRenderer.set(class_59442, "Size", f3, f4);
        CapePreviewRenderer.set4(class_59442, "Radius", f5, f5, f5, f5);
        CapePreviewRenderer.set(class_59442, "Smoothness", 1.0f);
        CapePreviewRenderer.set4(class_59442, "UvRect", f8, f9, f10, f11);
        CapePreviewRenderer.set(class_59442, "Time", (float)(System.currentTimeMillis() % 100000L) / 1000.0f);
        CapePreviewRenderer.set(class_59442, "Amount", Math.max(0.0f, Math.min(1.0f, f7)));
        float f12 = Math.max(0.0f, Math.min(1.0f, f6));
        class_4587.class_4665 class_46652 = class_45872.method_23760();
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        class_2872.method_56824(class_46652, f, f2, 0.0f).method_22913(0.0f, 0.0f).method_22915(1.0f, 1.0f, 1.0f, f12);
        class_2872.method_56824(class_46652, f, f2 + f4, 0.0f).method_22913(0.0f, 1.0f).method_22915(1.0f, 1.0f, 1.0f, f12);
        class_2872.method_56824(class_46652, f + f3, f2 + f4, 0.0f).method_22913(1.0f, 1.0f).method_22915(1.0f, 1.0f, 1.0f, f12);
        class_2872.method_56824(class_46652, f + f3, f2, 0.0f).method_22913(1.0f, 0.0f).method_22915(1.0f, 1.0f, 1.0f, f12);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
    }

    private static void set(class_5944 class_59442, String string, float f) {
        class_284 class_2842 = class_59442.method_34582(string);
        if (class_2842 != null) {
            class_2842.method_1251(f);
        }
    }

    private static void set(class_5944 class_59442, String string, float f, float f2) {
        class_284 class_2842 = class_59442.method_34582(string);
        if (class_2842 != null) {
            class_2842.method_1255(f, f2);
        }
    }

    private static void set4(class_5944 class_59442, String string, float f, float f2, float f3, float f4) {
        class_284 class_2842 = class_59442.method_34582(string);
        if (class_2842 != null) {
            class_2842.method_35657(f, f2, f3, f4);
        }
    }
}

