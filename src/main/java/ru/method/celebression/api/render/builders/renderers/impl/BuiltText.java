package ru.method.celebression.api.render.builders.renderers.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_286;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_4588;
import net.minecraft.class_5944;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import ru.method.celebression.api.render.builders.renderers.IRenderer;
import ru.method.celebression.api.render.msdf.MsdfFont;
import ru.method.celebression.api.render.providers.ColorProvider;
import ru.method.celebression.api.render.providers.ResourceProvider;

public record BuiltText(MsdfFont font, String text, float size, float thickness, int color, float smoothness, float spacing, int outlineColor, float outlineThickness, int glowColor, float glowRadius, int gradientColorStart, int gradientColorEnd, float gradientAngle) implements IRenderer
{
    private static final class_10156 MSDF_FONT_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("msdf_font"), class_290.field_1575, class_10149.field_53930);

    @Override
    public void render(Matrix4f matrix4f, float f, float f2, float f3) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        this.font.ensureLinearFiltering();
        RenderSystem.setShaderTexture((int)0, (int)this.font.getTextureId());
        boolean bl = this.outlineThickness > 0.0f;
        boolean bl2 = this.glowRadius > 0.0f;
        boolean bl3 = this.gradientColorStart != 0 || this.gradientColorEnd != 0;
        class_5944 class_59442 = RenderSystem.setShader((class_10156)MSDF_FONT_SHADER_KEY);
        class_59442.method_34582("Range").method_1251(this.font.getAtlas().range());
        class_59442.method_34582("Thickness").method_1251(this.thickness);
        class_59442.method_34582("Smoothness").method_1251(this.smoothness);
        class_59442.method_34582("Outline").method_35649(bl ? 1 : 0);
        if (bl) {
            class_59442.method_34582("OutlineThickness").method_1251(this.outlineThickness);
            float[] normalizedColor = ColorProvider.normalize(this.outlineColor);
            class_59442.method_34582("OutlineColor").method_35657(normalizedColor[0], normalizedColor[1], normalizedColor[2], normalizedColor[3]);
        }
        class_59442.method_34582("Glow").method_35649(bl2 ? 1 : 0);
        if (bl2) {
            float[] normalizedColor = ColorProvider.normalize(this.glowColor);
            class_59442.method_34582("GlowColor").method_35657(normalizedColor[0], normalizedColor[1], normalizedColor[2], normalizedColor[3]);
            class_59442.method_34582("GlowRadius").method_1251(this.glowRadius);
        } else {
            class_59442.method_34582("GlowColor").method_35657(0.0f, 0.0f, 0.0f, 0.0f);
            class_59442.method_34582("GlowRadius").method_1251(0.0f);
        }
        class_59442.method_34582("Gradient").method_35649(bl3 ? 1 : 0);
        if (bl3) {
            float[] normalizedColor = ColorProvider.normalize(this.gradientColorStart);
            float[] fArray = ColorProvider.normalize(this.gradientColorEnd);
            class_59442.method_34582("GradientColorStart").method_35657(normalizedColor[0], normalizedColor[1], normalizedColor[2], normalizedColor[3]);
            class_59442.method_34582("GradientColorEnd").method_35657(fArray[0], fArray[1], fArray[2], fArray[3]);
            float f4 = this.font.getMetrics().baselineHeight() * this.size;
            float f5 = this.font.getWidth(this.text, this.size);
            float f6 = (float)Math.cos(this.gradientAngle);
            float f7 = (float)Math.sin(this.gradientAngle);
            float f8 = f2 + f4;
            float f9 = f * f7 + f2 * f6;
            float f10 = (f + f5) * f7 + f2 * f6;
            float f11 = f * f7 + f8 * f6;
            float f12 = (f + f5) * f7 + f8 * f6;
            float f13 = Math.min(Math.min(f9, f10), Math.min(f11, f12));
            float f14 = Math.max(Math.max(f9, f10), Math.max(f11, f12));
            if (Math.abs(f14 - f13) < 1.0E-5f) {
                f13 = 0.0f;
                f14 = 1.0f;
            }
            class_59442.method_34582("GradientBounds").method_1255(f13, f14);
            class_59442.method_34582("GradientAngle").method_1251(this.gradientAngle);
        } else {
            class_59442.method_34582("GradientColorStart").method_35657(0.0f, 0.0f, 0.0f, 0.0f);
            class_59442.method_34582("GradientColorEnd").method_35657(0.0f, 0.0f, 0.0f, 0.0f);
            class_59442.method_34582("GradientBounds").method_1255(0.0f, 0.0f);
            class_59442.method_34582("GradientAngle").method_1251(0.0f);
        }
        var bufferBuilder = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        this.font.applyGlyphs(matrix4f, bufferBuilder, this.text, this.size, (this.thickness + this.outlineThickness * 0.5f) * 0.5f * this.size, this.spacing, f, f2 + this.font.getMetrics().baselineHeight() * this.size, f3, this.color);
        class_286.method_43433(bufferBuilder.method_60800());
        RenderSystem.setShaderTexture((int)0, (int)0);
        RenderSystem.bindTexture((int)0);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }
}
