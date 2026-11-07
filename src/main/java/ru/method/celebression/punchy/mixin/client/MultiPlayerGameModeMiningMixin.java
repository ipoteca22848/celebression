package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.punchy.client.state.MiningActionTracker;

@Mixin(value={class_636.class})
public class MultiPlayerGameModeMiningMixin {
    @Unique
    private boolean punchy$startTargetWasTrackable;
    @Unique
    private boolean punchy$continueTargetWasTrackable;

    @Inject(method={"attackBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"}, at={@At(value="HEAD")})
    private void punchy$captureStartDestroy(class_2338 class_23382, class_2350 class_23502, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        this.punchy$startTargetWasTrackable = this.punchy$isTrackableMiningTarget(class_23382);
    }

    @Inject(method={"attackBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"}, at={@At(value="RETURN")})
    private void punchy$onStartDestroy(class_2338 class_23382, class_2350 class_23502, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (this.punchy$startTargetWasTrackable) {
            class_243 class_2432 = this.punchy$resolveHitLocation(class_23382, class_23502);
            if (Boolean.TRUE.equals(callbackInfoReturnable.getReturnValue())) {
                MiningActionTracker.recordStart(class_23382, class_23502, class_2432);
            } else {
                MiningActionTracker.recordAttempt(class_23382, class_23502, class_2432);
            }
        }
    }

    @Inject(method={"updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"}, at={@At(value="HEAD")})
    private void punchy$captureContinueDestroy(class_2338 class_23382, class_2350 class_23502, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        this.punchy$continueTargetWasTrackable = this.punchy$isTrackableMiningTarget(class_23382);
    }

    @Inject(method={"updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"}, at={@At(value="RETURN")})
    private void punchy$onContinueDestroy(class_2338 class_23382, class_2350 class_23502, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Boolean.TRUE.equals(callbackInfoReturnable.getReturnValue()) && this.punchy$continueTargetWasTrackable) {
            MiningActionTracker.recordContinue(class_23382, class_23502, this.punchy$resolveHitLocation(class_23382, class_23502));
        }
    }

    @Inject(method={"cancelBlockBreaking()V"}, at={@At(value="HEAD")})
    private void punchy$onStopDestroy(CallbackInfo callbackInfo) {
        MiningActionTracker.recordStop();
    }

    @Unique
    private boolean punchy$isTrackableMiningTarget(class_2338 class_23382) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1687 != null && class_23382 != null) {
            class_2680 class_26802 = class_3102.field_1687.method_8320(class_23382);
            return class_26802 != null && !class_26802.method_26215();
        }
        return false;
    }

    @Unique
    private class_243 punchy$resolveHitLocation(class_2338 class_23382, class_2350 class_23502) {
        block4: {
            class_3965 class_39652;
            block6: {
                block5: {
                    class_310 class_3102 = class_310.method_1551();
                    if (class_3102 == null || class_3102.field_1765 == null || class_23382 == null) break block4;
                    class_239 class_2392 = class_3102.field_1765;
                    if (!(class_2392 instanceof class_3965)) break block5;
                    class_39652 = (class_3965)class_2392;
                    if (class_2392.method_17783() == class_239.class_240.field_1332) break block6;
                }
                return null;
            }
            if (!class_23382.equals((Object)class_39652.method_17777())) {
                return null;
            }
            return class_23502 != null && class_39652.method_17780() != class_23502 ? null : class_39652.method_17784();
        }
        return null;
    }
}

