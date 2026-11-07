package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_636;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.punchy.client.state.UseActionTracker;
import ru.method.celebression.punchy.client.state.UseItemStateMachine;

@Mixin(value={class_636.class})
public class MultiPlayerGameModeMixin {
    @Inject(method={"interactItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"}, at={@At(value="HEAD")})
    private void punchy$onUseItemHead(class_1657 class_16572, class_1268 class_12682, CallbackInfoReturnable<class_1269> callbackInfoReturnable) {
        class_1799 class_17992 = class_16572 == null ? class_1799.field_8037 : class_16572.method_5998(class_12682);
        UseItemStateMachine.capturePendingThrowableSnapshot(class_12682, class_17992);
    }

    @Inject(method={"interactBlock(Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;"}, at={@At(value="HEAD")})
    private void punchy$onUseItemOnHead(class_746 class_7462, class_1268 class_12682, class_3965 class_39652, CallbackInfoReturnable<class_1269> callbackInfoReturnable) {
        class_1799 class_17992 = class_7462 != null && class_12682 != null ? class_7462.method_5998(class_12682) : class_1799.field_8037;
        UseItemStateMachine.capturePendingBoneMealUseOnSnapshot(class_12682, class_17992);
        UseActionTracker.recordFallbackUse(class_12682, UseActionTracker.TriggerType.USE_ITEM_ON, class_39652);
    }

    @Inject(method={"interactBlock(Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;"}, at={@At(value="RETURN")})
    private void punchy$onUseItemOn(class_746 class_7462, class_1268 class_12682, class_3965 class_39652, CallbackInfoReturnable<class_1269> callbackInfoReturnable) {
        class_1269 class_12692 = (class_1269)callbackInfoReturnable.getReturnValue();
        boolean bl = this.shouldTrackUse(class_12692);
        if (bl) {
            UseActionTracker.recordUse(class_12682, UseActionTracker.TriggerType.USE_ITEM_ON, class_39652);
        } else if (!UseItemStateMachine.hasPendingBoneMealUseOnSnapshot(class_12682) && !this.isBoneMealUse((class_1657)class_7462, class_12682)) {
            UseActionTracker.discardFallbackForHand(class_12682);
        } else {
            UseActionTracker.recordUse(class_12682, UseActionTracker.TriggerType.USE_ITEM_ON, class_39652);
        }
    }

    @Inject(method={"interactItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"}, at={@At(value="RETURN")})
    private void punchy$onUseItem(class_1657 class_16572, class_1268 class_12682, CallbackInfoReturnable<class_1269> callbackInfoReturnable) {
        class_1269 class_12692 = (class_1269)callbackInfoReturnable.getReturnValue();
        boolean bl = this.shouldTrackUse(class_12692);
        if (bl) {
            UseActionTracker.recordUse(class_12682, UseActionTracker.TriggerType.USE_ITEM);
        } else {
            UseActionTracker.discardFallbackForHand(class_12682);
        }
    }

    @Inject(method={"interactEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"}, at={@At(value="RETURN")})
    private void punchy$onInteract(class_1657 class_16572, class_1297 class_12972, class_1268 class_12682, CallbackInfoReturnable<class_1269> callbackInfoReturnable) {
        class_1269 class_12692 = (class_1269)callbackInfoReturnable.getReturnValue();
        boolean bl = this.shouldTrackUse(class_12692);
        if (bl) {
            UseActionTracker.recordUse(class_12682, UseActionTracker.TriggerType.INTERACT);
        }
    }

    @Inject(method={"interactEntityAtLocation(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/hit/EntityHitResult;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"}, at={@At(value="RETURN")})
    private void punchy$onInteractAt(class_1657 class_16572, class_1297 class_12972, class_3966 class_39662, class_1268 class_12682, CallbackInfoReturnable<class_1269> callbackInfoReturnable) {
        class_1269 class_12692 = (class_1269)callbackInfoReturnable.getReturnValue();
        boolean bl = this.shouldTrackUse(class_12692);
        if (bl) {
            UseActionTracker.recordUse(class_12682, UseActionTracker.TriggerType.INTERACT_AT);
        }
    }

    private boolean shouldTrackUse(class_1269 class_12692) {
        return class_12692 != null && class_12692.method_23665();
    }

    private boolean isBoneMealUse(class_1657 class_16572, class_1268 class_12682) {
        if (class_16572 != null && class_12682 != null) {
            class_1799 class_17992 = class_16572.method_5998(class_12682);
            return class_17992 != null && class_17992.method_31574(class_1802.field_8324);
        }
        return false;
    }
}

