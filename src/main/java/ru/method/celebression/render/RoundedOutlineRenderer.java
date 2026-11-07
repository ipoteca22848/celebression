package ru.method.celebression.render;

import ru.method.celebression.animation.Animation;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import lombok.Generated;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_4587;
import net.minecraft.class_5944;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import ru.method.celebression.api.render.providers.ResourceProvider;
import ru.method.celebression.animation.InterpolationUtils;
import ru.method.celebression.utils.render.LinkGuiRenderContext;

public final class RoundedOutlineRenderer {
    private static final class_10156 aii = new class_10156(ResourceProvider.getShaderIdentifier("dw_rounded_outline"), class_290.field_1576, class_10149.field_53930);
    private static final float[] aij = new float[4];
    private static final float[] aik = new float[4];
    private static final float[] ail = new float[4];
    private static final float[] aim = new float[4];

    public static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color, Color color2) {
        RoundedOutlineRenderer.a(class_45872, f, f2, f3, f4, f5, f6, color, color, color2, color2);
    }

    public static void b(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color, Color color2) {
        RoundedOutlineRenderer.a(class_45872, f, f2, f3, f4, f5, f6, color, color2, color2, color);
    }

    public static void c(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color, Color color2) {
        Color color3 = InterpolationUtils.lerp(color, color2, 0.5f);
        RoundedOutlineRenderer.a(class_45872, f, f2, f3, f4, f5, f6, color, color3, color2, color3);
    }

    public static void b(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        RoundedOutlineRenderer.a(class_45872, f, f2, f3, f4, f5, f6, color, color, color, color);
    }

    private static Color f(Color color, float f) {
        if (color == null) {
            return null;
        }
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, Math.round((float)color.getAlpha() * f))));
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, Color color, Color color2, Color color3, Color color4) {
        if (f3 <= 0.0f || f4 <= 0.0f || f6 <= 0.0f) {
            return;
        }
        float f7 = LinkGuiRenderContext.getFadeMultiplier();
        if (f7 < 0.999f) {
            color = RoundedOutlineRenderer.f(color, f7);
            color2 = RoundedOutlineRenderer.f(color2, f7);
            color3 = RoundedOutlineRenderer.f(color3, f7);
            color4 = RoundedOutlineRenderer.f(color4, f7);
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_5944 class_59442 = RenderSystem.setShader((class_10156)aii);
        class_59442.method_34582("Size").method_1255(f3, f4);
        class_59442.method_34582("Round").method_35657(f5, f5, f5, f5);
        class_59442.method_34582("OutlineWidth").method_1251(f6);
        class_59442.method_34582("Color1").method_1253(RoundedOutlineRenderer.a(color, aij));
        class_59442.method_34582("Color2").method_1253(RoundedOutlineRenderer.a(color2, aik));
        class_59442.method_34582("Color3").method_1253(RoundedOutlineRenderer.a(color3, ail));
        class_59442.method_34582("Color4").method_1253(RoundedOutlineRenderer.a(color4, aim));
        class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        class_2872.method_22918(matrix4f, f, f2, 0.0f).method_39415(-1);
        class_2872.method_22918(matrix4f, f, f2 + f4, 0.0f).method_39415(-1);
        class_2872.method_22918(matrix4f, f + f3, f2 + f4, 0.0f).method_39415(-1);
        class_2872.method_22918(matrix4f, f + f3, f2, 0.0f).method_39415(-1);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static float[] a(Color color, float[] fArray) {
        fArray[0] = (float)color.getRed() / 255.0f;
        fArray[1] = (float)color.getGreen() / 255.0f;
        fArray[2] = (float)color.getBlue() / 255.0f;
        fArray[3] = (float)color.getAlpha() / 255.0f;
        return fArray;
    }

    @Generated
    private RoundedOutlineRenderer() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

