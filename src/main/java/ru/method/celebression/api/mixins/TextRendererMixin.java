package ru.method.celebression.api.mixins;

import net.minecraft.class_327;
import net.minecraft.class_5481;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import ru.method.celebression.util.StreamerTextSanitizer;
import ru.method.celebression.utils.debug.TabProfiler;

@Mixin(value={class_327.class})
public abstract class TextRendererMixin {
    private static boolean celebression$shouldSanitizeSelfNametag() {
        return !TabProfiler.isActive() && !StreamerTextSanitizer.sy() && StreamerTextSanitizer.sB();
    }

    @ModifyVariable(method={"draw(Lnet/minecraft/text/OrderedText;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private class_5481 celebression$sanitizeSelfNametagOrdered(class_5481 class_54812) {
        return TextRendererMixin.celebression$shouldSanitizeSelfNametag() ? StreamerTextSanitizer.a(class_54812) : class_54812;
    }

    @ModifyVariable(method={"drawWithOutline(Lnet/minecraft/text/OrderedText;FFIILorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private class_5481 celebression$sanitizeSelfNametagOutline(class_5481 class_54812) {
        return TextRendererMixin.celebression$shouldSanitizeSelfNametag() ? StreamerTextSanitizer.a(class_54812) : class_54812;
    }

    @ModifyVariable(method={"draw(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"}, at=@At(value="HEAD"), argsOnly=true, require=0)
    private String celebression$sanitizeSelfNametagString(String string) {
        return TextRendererMixin.celebression$shouldSanitizeSelfNametag() ? StreamerTextSanitizer.dq(string) : string;
    }
}

