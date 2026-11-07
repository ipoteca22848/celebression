package ru.method.celebression.api.mixins;

import net.minecraft.class_10034;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_7833;
import net.minecraft.class_979;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewTransformState;

@Mixin(value={class_979.class})
public class ElytraFeatureRendererMixin {
    @Inject(method={"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V"}, at={@At(value="HEAD")})
    private void celebression$narutoLeanPush(class_4587 class_45872, class_4597 class_45972, int n, class_10034 class_100342, float f, float f2, CallbackInfo callbackInfo) {
        if (!CosmeticPreviewTransformState.active) {
            return;
        }
        class_45872.method_22903();
        class_45872.method_46416(0.0f, CosmeticPreviewTransformState.pivotY, 0.0f);
        class_45872.method_22907(class_7833.field_40714.rotation(CosmeticPreviewTransformState.eg));
        class_45872.method_46416(0.0f, -CosmeticPreviewTransformState.pivotY, 0.0f);
    }

    @Inject(method={"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V"}, at={@At(value="RETURN")})
    private void celebression$narutoLeanPop(class_4587 class_45872, class_4597 class_45972, int n, class_10034 class_100342, float f, float f2, CallbackInfo callbackInfo) {
        if (!CosmeticPreviewTransformState.active) {
            return;
        }
        class_45872.method_22909();
    }
}

