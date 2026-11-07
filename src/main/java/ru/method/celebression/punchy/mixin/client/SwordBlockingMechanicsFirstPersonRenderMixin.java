package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import net.minecraft.class_759;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.punchy.compat.SwordBlockingMechanicsCompat;

@Pseudo
@Mixin(targets={"fuzs/swordblockingmechanics/client/handler/FirstPersonRenderingHandler"}, remap=false)
public class SwordBlockingMechanicsFirstPersonRenderMixin {
    @Inject(method={"onRenderBothHands(Lnet/minecraft/client/renderer/ItemInHandRenderer;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/world/entity/HumanoidArm;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IFFFFF)Lfuzs/puzzleslib/api/event/v1/core/EventResult;"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    private static void punchy$disableSwordBlockingFirstPersonRender(class_759 class_7592, class_1268 class_12682, class_742 class_7422, class_1306 class_13062, class_1799 class_17992, class_4587 class_45872, class_4597 class_45972, int n, float f, float f2, float f3, float f4, CallbackInfoReturnable<Object> callbackInfoReturnable) {
        Object object;
        if (SwordBlockingMechanicsCompat.isAvailable() && (object = SwordBlockingMechanicsCompat.getEventResultPass()) != null) {
            callbackInfoReturnable.setReturnValue(object);
            callbackInfoReturnable.cancel();
        }
    }
}

