package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_10463;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_638;
import net.minecraft.class_811;
import net.minecraft.class_906;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.punchy.mixin.client.accessor.PlayerFishingAccessor;

@Mixin(value={class_10463.class})
public class FishingRodCastPropertyMixin {
    @Inject(method={"getValue(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/entity/LivingEntity;ILnet/minecraft/item/ModelTransformationMode;)Z"}, at={@At(value="HEAD")}, cancellable=true)
    private void punchy$fixCastWhenStackIsCopied(class_1799 class_17992, class_638 class_6382, class_1309 class_13092, int n, class_811 class_8112, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (class_13092 instanceof class_1657) {
            class_1799 class_17993;
            class_1306 class_13062;
            class_1657 class_16572 = (class_1657)class_13092;
            if (!(class_17992 == null || class_17992.method_7960() || class_8112 != class_811.field_4322 && class_8112 != class_811.field_4321 && class_8112 != class_811.field_4320 && class_8112 != class_811.field_4323 || ((PlayerFishingAccessor)class_16572).punchy$getFishing() == null || (class_13062 = class_906.method_65567((class_1657)class_16572)) == null || (class_17993 = class_16572.method_61420(class_13062)) == null || class_17993.method_7960() || !class_1799.method_31577((class_1799)class_17993, (class_1799)class_17992))) {
                callbackInfoReturnable.setReturnValue(true);
            }
        }
    }
}

