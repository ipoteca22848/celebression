package ru.method.celebression.api.render.builders.renderers.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_5944;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import ru.method.celebression.api.render.builders.renderers.IRenderer;
import ru.method.celebression.api.render.builders.states.QuadColorState;
import ru.method.celebression.api.render.builders.states.QuadRadiusState;
import ru.method.celebression.api.render.builders.states.SizeState;
import ru.method.celebression.api.render.providers.ResourceProvider;

public record BuiltTexture(SizeState size, QuadRadiusState radius, QuadColorState color, float smoothness, float u, float v, float texWidth, float texHeight, int textureId) implements IRenderer
{
    private static final class_10156 TEXTURE_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("texture"), class_290.field_1575, class_10149.field_53930);

    @Override
    public void render(Matrix4f matrix4f, float f, float f2, float f3) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShaderTexture((int)0, (int)this.textureId);
        float f4 = this.size.width();
        float f5 = this.size.height();
        class_5944 class_59442 = RenderSystem.setShader((class_10156)TEXTURE_SHADER_KEY);
        class_59442.method_34582("Size").method_1255(f4, f5);
        class_59442.method_34582("Radius").method_35657(this.radius.radius1(), this.radius.radius2(), this.radius.radius3(), this.radius.radius4());
        class_59442.method_34582("Smoothness").method_1251(this.smoothness);
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        class_2872.method_22918(matrix4f, f, f2, f3).method_22913(this.u, this.v).method_39415(this.color.color1());
        class_2872.method_22918(matrix4f, f, f2 + f5, f3).method_22913(this.u, this.v + this.texHeight).method_39415(this.color.color2());
        class_2872.method_22918(matrix4f, f + f4, f2 + f5, f3).method_22913(this.u + this.texWidth, this.v + this.texHeight).method_39415(this.color.color3());
        class_2872.method_22918(matrix4f, f + f4, f2, f3).method_22913(this.u + this.texWidth, this.v).method_39415(this.color.color4());
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.setShaderTexture((int)0, (int)0);
        RenderSystem.bindTexture((int)0);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }
}

