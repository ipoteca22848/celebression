package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_10042;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_922;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_922.class})
public abstract class LivingEntityRendererMixin {
    @Inject(method={"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V", shift=At.Shift.AFTER)})
    private void punchy$applyThirdPersonAnimation(class_10042 class_100422, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
    }
}

