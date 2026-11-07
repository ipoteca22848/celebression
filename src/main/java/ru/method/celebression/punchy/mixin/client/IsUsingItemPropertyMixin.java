package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_10468;
import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1799;
import net.minecraft.class_1819;
import net.minecraft.class_1835;
import net.minecraft.class_310;
import net.minecraft.class_5498;
import net.minecraft.class_638;
import net.minecraft.class_811;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.punchy.client.render.UseAnimationSuppressor;
import ru.method.celebression.punchy.config.PunchyConfig;

@Mixin(value={class_10468.class})
public abstract class IsUsingItemPropertyMixin {
    @Inject(method={"getValue(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/entity/LivingEntity;ILnet/minecraft/item/ModelTransformationMode;)Z"}, at={@At(value="HEAD")}, cancellable=true)
    private void punchy$suppressUseProperty(class_1799 class_17992, class_638 class_6382, class_1309 class_13092, int n, class_811 class_8112, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (!IsUsingItemPropertyMixin.isUsingBlacklistedItem(class_13092) && !IsUsingItemPropertyMixin.shouldSkipBlacklist(class_17992, class_13092) && class_13092 instanceof class_1657) {
            class_1268 class_12682;
            class_1799 class_17993;
            class_1657 class_16572 = (class_1657)class_13092;
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 != null && class_3102.field_1690 != null && class_3102.field_1690.method_31044() == class_5498.field_26664 && class_16572 == class_3102.field_1724 && class_16572.method_6115() && (class_17993 = class_16572.method_6030()) != null && !class_17993.method_7960() && !class_17992.method_7960() && class_17993 == class_17992 && (class_12682 = IsUsingItemPropertyMixin.resolveHand(class_16572, class_17992)) != null && (UseAnimationSuppressor.isSuppressed(class_12682) || UseAnimationSuppressor.isForced()) && !IsUsingItemPropertyMixin.shouldPreserveUseProperties(class_17993)) {
                callbackInfoReturnable.setReturnValue(false);
            }
        }
    }

    private static boolean shouldSkipBlacklist(class_1799 class_17992, class_1309 class_13092) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        if (class_13092 instanceof class_1657) {
            class_1657 class_16572 = (class_1657)class_13092;
            if (!PunchyConfig.isItemBlacklisted(class_17992)) {
                return false;
            }
            class_310 class_3102 = class_310.method_1551();
            return class_3102 != null && class_3102.field_1690 != null && class_3102.field_1690.method_31044() == class_5498.field_26664 ? class_3102.field_1724 == class_16572 : false;
        }
        return false;
    }

    private static boolean isUsingBlacklistedItem(class_1309 class_13092) {
        if (class_13092 instanceof class_1657) {
            class_1657 class_16572 = (class_1657)class_13092;
            if (!class_16572.method_6115()) {
                return false;
            }
            class_1799 class_17992 = class_16572.method_6030();
            return PunchyConfig.isItemBlacklisted(class_17992);
        }
        return false;
    }

    private static class_1268 resolveHand(class_1657 class_16572, class_1799 class_17992) {
        if (class_17992 == null) {
            return null;
        }
        class_1799 class_17993 = class_16572.method_6047();
        class_1799 class_17994 = class_16572.method_6079();
        if (!class_17993.method_7960() && class_17993.method_7909() == class_17992.method_7909()) {
            return class_1268.field_5808;
        }
        return !class_17994.method_7960() && class_17994.method_7909() == class_17992.method_7909() ? class_1268.field_5810 : null;
    }

    private static boolean shouldPreserveUseProperties(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        return class_17992.method_7909() instanceof class_1819 || class_17992.method_7909() instanceof class_1835 || class_17992.method_7909() instanceof class_1753 || class_17992.method_7909() instanceof class_1764 ? false : !UseAnimationSuppressor.isForced();
    }
}

