package ru.method.celebression.api.mixins;

import net.minecraft.class_10017;
import net.minecraft.class_1297;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_897;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewContext;
import ru.method.celebression.modules.impl.render.SelfNametagsModule;
import ru.method.celebression.util.StreamerTextSanitizer;

@Mixin(value={class_897.class})
public abstract class EntityRendererLabelMixin {
    @Inject(method={"updateRenderState"}, at={@At(value="TAIL")})
    private void celebression$stripPreviewLabel(class_1297 class_12972, class_10017 class_100172, float f, CallbackInfo callbackInfo) {
        if (CosmeticPreviewContext.aP() && class_100172 != null) {
            class_100172.field_53337 = null;
        }
    }

    @Inject(method={"hasLabel(Lnet/minecraft/entity/Entity;D)Z"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$suppressPreviewHasLabel(class_1297 class_12972, double d, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (CosmeticPreviewContext.aP()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method={"renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$skipCosmeticPreviewLabel(CallbackInfo callbackInfo) {
        if (CosmeticPreviewContext.aP()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"getDisplayName"}, at={@At(value="HEAD")}, cancellable=true)
    private void celebression$plainPreviewDisplayName(class_1297 class_12972, CallbackInfoReturnable<class_2561> callbackInfoReturnable) {
        if (CosmeticPreviewContext.aP()) {
            callbackInfoReturnable.setReturnValue(null);
        }
    }

    @ModifyVariable(method={"renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at=@At(value="HEAD"), argsOnly=true, ordinal=0, require=0)
    private class_2561 celebression$sanitizeSelfLabel(class_2561 class_25612, class_10017 class_100172, class_2561 class_25613, class_4587 class_45872, class_4597 class_45972, int n) {
        if (class_25612 == null) {
            return null;
        }
        Celebression celebression = Celebression.getInstance();
        class_310 class_3102 = class_310.method_1551();
        if (celebression == null || class_3102 == null || class_3102.field_1724 == null) {
            return class_25612;
        }
        SelfNametagsModule selfNametagsModule = celebression.getModuleManager().getModule(SelfNametagsModule.class);
        if (selfNametagsModule == null || !selfNametagsModule.isToggle()) {
            return class_25612;
        }
        String string = class_3102.field_1724.method_7334().getName();
        if (string == null || string.isBlank()) {
            return class_25612;
        }
        String string2 = class_25612.getString();
        if (string2 == null || !string2.contains(string)) {
            return class_25612;
        }
        return StreamerTextSanitizer.n(class_25612);
    }
}

