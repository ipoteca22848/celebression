package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.punchy.compat.SwordBlockingMechanicsCompat;

@Pseudo
@Mixin(targets={"fuzs/swordblockingmechanics/handler/SwordBlockingHandler"}, remap=false)
public class SwordBlockingMechanicsHandlerMixin {
    @Inject(method={"onUseItem"}, at={@At(value="RETURN")}, require=0)
    private static void punchy$onSwordBlockingUseItem(class_1657 class_16572, class_1937 class_19372, class_1268 class_12682, CallbackInfoReturnable<Object> callbackInfoReturnable) {
        if (class_16572 != null && class_16572.method_6115() && class_16572.method_6058() == class_12682) {
            SwordBlockingMechanicsCompat.recordBlockingStart(class_16572, class_12682);
        }
    }

    @Inject(method={"onUseItemStop"}, at={@At(value="HEAD")}, require=0)
    private static void punchy$onSwordBlockingUseItemStop(class_1309 class_13092, class_1799 class_17992, int n, CallbackInfoReturnable<Object> callbackInfoReturnable) {
        if (class_13092 instanceof class_1657) {
            class_1657 class_16572 = (class_1657)class_13092;
            SwordBlockingMechanicsCompat.recordBlockingStop(class_16572, class_16572.method_6058());
        }
    }
}

