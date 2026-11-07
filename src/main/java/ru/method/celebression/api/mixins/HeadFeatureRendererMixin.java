package ru.method.celebression.api.mixins;

import net.minecraft.class_10042;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_583;
import net.minecraft.class_7833;
import net.minecraft.class_976;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewTransformState;

@Mixin(value={class_976.class})
public abstract class HeadFeatureRendererMixin<S extends class_10042, M extends class_583<S>>
extends class_3887<S, M> {
    @Unique
    private boolean celebression$leanPushed;

    public HeadFeatureRendererMixin(class_3883<S, M> class_38832) {
        super(class_38832);
    }

    @Inject(method={"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;FF)V"}, at={@At(value="HEAD")})
    private void celebression$leanHead(class_4587 class_45872, class_4597 class_45972, int n, S s, float f, float f2, CallbackInfo callbackInfo) {
        this.celebression$leanPushed = false;
        if (CosmeticPreviewTransformState.active) {
            class_45872.method_22903();
            class_45872.method_46416(0.0f, CosmeticPreviewTransformState.pivotY, 0.0f);
            class_45872.method_22907(class_7833.field_40714.rotation(CosmeticPreviewTransformState.eg));
            class_45872.method_46416(0.0f, -CosmeticPreviewTransformState.pivotY, 0.0f);
            this.celebression$leanPushed = true;
        }
    }

    @Inject(method={"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;FF)V"}, at={@At(value="RETURN")})
    private void celebression$clearHead(class_4587 class_45872, class_4597 class_45972, int n, S s, float f, float f2, CallbackInfo callbackInfo) {
        if (this.celebression$leanPushed) {
            class_45872.method_22909();
            this.celebression$leanPushed = false;
        }
    }
}

