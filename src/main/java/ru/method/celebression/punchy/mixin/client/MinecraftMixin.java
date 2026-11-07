package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_239;
import net.minecraft.class_310;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.state.AttackActionTracker;
import ru.method.celebression.punchy.client.state.SpearStateMachine;
import ru.method.celebression.punchy.client.state.UseActionTracker;
import ru.method.celebression.punchy.client.state.UseItemStateMachine;

@Mixin(value={class_310.class})
public class MinecraftMixin {
    @Inject(method={"handleInputEvents()V"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;dropSelectedItem(Z)Z")})
    private void punchy$onDropKeyUse(CallbackInfo callbackInfo) {
        class_746 class_7462;
        class_1799 class_17992;
        class_310 class_3102 = (class_310)(Object)this;
        if (class_3102.field_1724 != null && (class_17992 = (class_7462 = class_3102.field_1724).method_6047()) != null && !class_17992.method_7960()) {
            UseItemStateMachine.capturePendingDropSnapshot(class_1268.field_5808, class_17992);
            UseActionTracker.recordUse(class_1268.field_5808, UseActionTracker.TriggerType.DROP_ITEM);
        }
    }

    @Inject(method={"doAttack()Z"}, at={@At(value="HEAD")})
    private void punchy$onStartAttackHead(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        class_310 class_3102 = (class_310)(Object)this;
        AttackActionTracker.recordCriticalCandidate(class_3102);
    }

    @Inject(method={"doAttack()Z"}, at={@At(value="RETURN")})
    private void punchy$onStartAttackReturn(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        class_310 class_3102 = (class_310)(Object)this;
        if (callbackInfoReturnable.getReturnValue() != null && ((Boolean)callbackInfoReturnable.getReturnValue()).booleanValue()) {
            if (class_3102.field_1724 != null && class_3102.field_1765 != null && class_3102.field_1765.method_17783() == class_239.class_240.field_1332 && MinecraftMixin.shouldSuppressBlockAttack(class_3102.field_1724.method_6047())) {
                AttackActionTracker.clearPendingCritical();
            } else {
                AttackActionTracker.recordAttack();
            }
        } else {
            AttackActionTracker.clearPendingCritical();
        }
    }

    private static boolean shouldSuppressBlockAttack(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        return SpearStateMachine.isSpearStack(class_17992) ? false : ToolTuning.hasCustomAnimationType(class_17992, "mining_v2");
    }
}
