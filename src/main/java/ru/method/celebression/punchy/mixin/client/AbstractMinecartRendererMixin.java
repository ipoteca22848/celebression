package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_10045;
import net.minecraft.class_2246;
import net.minecraft.class_2680;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_925;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.render.MinecartInHandContext;

@Mixin(value={class_925.class})
public class AbstractMinecartRendererMixin {
    @Inject(method={"renderBlock(Lnet/minecraft/client/render/entity/state/MinecartEntityRenderState;Lnet/minecraft/block/BlockState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="HEAD")})
    private void punchy$offsetTnt(class_10045 class_100452, class_2680 class_26802, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        float f;
        if (MinecartInHandContext.isActive() && class_26802 != null && class_26802.method_26204() == class_2246.field_10375 && (f = MinecartInHandContext.getTntOffset()) != 0.0f) {
            class_45872.method_22903();
            class_45872.method_46416(0.0f, f, 0.0f);
        }
    }

    @Inject(method={"renderBlock(Lnet/minecraft/client/render/entity/state/MinecartEntityRenderState;Lnet/minecraft/block/BlockState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="RETURN")})
    private void punchy$offsetTntTail(class_10045 class_100452, class_2680 class_26802, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        float f;
        if (MinecartInHandContext.isActive() && class_26802 != null && class_26802.method_26204() == class_2246.field_10375 && (f = MinecartInHandContext.getTntOffset()) != 0.0f) {
            class_45872.method_22909();
        }
    }
}

