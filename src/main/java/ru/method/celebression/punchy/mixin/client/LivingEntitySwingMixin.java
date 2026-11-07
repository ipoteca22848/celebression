package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_239;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.state.AttackActionTracker;
import ru.method.celebression.punchy.client.state.SpearStateMachine;
import ru.method.celebression.punchy.client.state.UseActionTracker;

@Mixin(value={class_1309.class})
public class LivingEntitySwingMixin {
    private static final long USE_SWING_GUARD_NANOS = 250000000L;

    @Inject(method={"swingHand(Lnet/minecraft/util/Hand;Z)V"}, at={@At(value="HEAD")})
    private void punchy$onSwing(class_1268 class_12682, boolean bl, CallbackInfo callbackInfo) {
        class_310 class_3102 = class_310.method_1551();
        if (!(class_3102 == null || class_3102.field_1724 == null || (Object)this != class_3102.field_1724 || UseActionTracker.peekTrigger() != null || UseActionTracker.wasRecentUse(250000000L) || class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434() && !class_3102.field_1690.field_1886.method_1434() || class_3102.field_1765 != null && class_3102.field_1765.method_17783() == class_239.class_240.field_1332 && LivingEntitySwingMixin.shouldIgnoreBlockSwing(class_3102.field_1724.method_5998(class_12682)))) {
            AttackActionTracker.recordAttack(class_12682);
        }
    }

    private static boolean shouldIgnoreBlockSwing(class_1799 class_17992) {
        return !SpearStateMachine.isSpearStack(class_17992);
    }
}
