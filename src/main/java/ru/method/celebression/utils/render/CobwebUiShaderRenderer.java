package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_284;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_5944;
import net.minecraft.class_9801;
import ru.method.celebression.api.render.providers.ResourceProvider;

public final class CobwebUiShaderRenderer {
    private static final class_10156 COBWEB_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("block_overlay_cobweb"), class_290.field_1575, class_10149.field_53930);

    private CobwebUiShaderRenderer() {
    }

    public static void render(class_4587 class_45872, float f, float f2, float f3, float f4, Color color, float f5) {
        if (f3 <= 0.0f || f4 <= 0.0f || f5 <= 0.0f) {
            return;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask((boolean)false);
        class_5944 class_59442 = RenderSystem.setShader((class_10156)COBWEB_SHADER_KEY);
        class_310 class_3102 = class_310.method_1551();
        int n = Math.max(1, class_3102.method_22683().method_4489());
        int n2 = Math.max(1, class_3102.method_22683().method_4506());
        float f6 = (float)(System.currentTimeMillis() % 12000L) / 1000.0f;
        float f7 = Math.max(0.0f, Math.min(1.0f, f5));
        CobwebUiShaderRenderer.set(class_59442, "time", f6);
        CobwebUiShaderRenderer.set(class_59442, "screenSize", n, n2);
        CobwebUiShaderRenderer.set(class_59442, "baseColor", (float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, f7);
        CobwebUiShaderRenderer.set(class_59442, "alpha", f7);
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        class_4587.class_4665 class_46652 = class_45872.method_23760();
        class_2872.method_56824(class_46652, f, f2 + f4, 0.0f).method_22913(0.0f, 1.0f).method_22915(1.0f, 1.0f, 1.0f, f7);
        class_2872.method_56824(class_46652, f + f3, f2 + f4, 0.0f).method_22913(1.0f, 1.0f).method_22915(1.0f, 1.0f, 1.0f, f7);
        class_2872.method_56824(class_46652, f + f3, f2, 0.0f).method_22913(1.0f, 0.0f).method_22915(1.0f, 1.0f, 1.0f, f7);
        class_2872.method_56824(class_46652, f, f2, 0.0f).method_22913(0.0f, 0.0f).method_22915(1.0f, 1.0f, 1.0f, f7);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.depthMask((boolean)true);
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

    private static void set(class_5944 class_59442, String string, float f, float f2, float f3, float f4) {
        class_284 class_2842 = class_59442.method_34582(string);
        if (class_2842 != null) {
            class_2842.method_35657(f, f2, f3, f4);
        }
    }
}

