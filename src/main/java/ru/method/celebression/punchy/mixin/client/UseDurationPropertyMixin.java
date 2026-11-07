package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_10485;
import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1819;
import net.minecraft.class_1835;
import net.minecraft.class_310;
import net.minecraft.class_5498;
import net.minecraft.class_638;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.punchy.client.render.UseAnimationSuppressor;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.mixin.client.accessor.UseDurationAccessor;

@Mixin(value={class_10485.class})
public abstract class UseDurationPropertyMixin {
    @Inject(method={"getValue(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/entity/LivingEntity;I)F"}, at={@At(value="HEAD")}, cancellable=true)
    private void punchy$gateUseDuration(class_1799 class_17992, class_638 class_6382, class_1309 class_13092, int n, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (!UseDurationPropertyMixin.isUsingBlacklistedItem(class_13092) && !UseDurationPropertyMixin.shouldSkipBlacklist(class_17992, class_13092) && class_13092 instanceof class_1309 && class_13092 instanceof class_1657) {
            class_1657 class_16572 = (class_1657)class_13092;
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 != null && class_3102.field_1690 != null && class_3102.field_1690.method_31044() == class_5498.field_26664 && class_3102.field_1724 == class_16572) {
                if (!class_16572.method_6115()) {
                    UseAnimationSuppressor.clearReleaseStart();
                    UseAnimationSuppressor.clearMeshStart();
                } else {
                    class_1268 class_12682;
                    class_1799 class_17993 = class_16572.method_6030();
                    if (!class_17993.method_7960() && !class_17992.method_7960() && class_17993 == class_17992 && (class_12682 = UseDurationPropertyMixin.resolveHand(class_16572, class_17992)) != null) {
                        if ((UseAnimationSuppressor.isSuppressed(class_12682) || UseAnimationSuppressor.isForced()) && !UseDurationPropertyMixin.shouldPreserveUseProperties(class_17993)) {
                            callbackInfoReturnable.setReturnValue(Float.valueOf(0.0f));
                        } else {
                            int n2;
                            boolean bl = ((UseDurationAccessor)((Object)this)).punchy$isRemaining();
                            long l = UseAnimationSuppressor.getReleaseStartTick(class_12682);
                            int n3 = UseAnimationSuppressor.getReleaseStartRemainingTicks(class_12682);
                            if (l >= 0L && class_6382 != null && n3 >= 0) {
                                long l2 = Math.max(0L, class_6382.method_8510() - l);
                                n2 = Math.max(0, n3 - (int)l2);
                            } else {
                                n2 = class_16572.method_6014();
                            }
                            int n4 = class_17993.method_7935((class_1309)class_16572);
                            float f = n2;
                            float f2 = n4 - n2;
                            if (!bl && l >= 0L && n3 >= 0) {
                                if (class_17993.method_7909() instanceof class_1753) {
                                    f2 = Math.max(0, n3 - n2);
                                } else {
                                    class_1792 class_17922 = class_17993.method_7909();
                                    if (class_17922 instanceof class_1764) {
                                        class_1764 class_17642 = (class_1764)class_17922;
                                        if (!class_1764.method_7781((class_1799)class_17993)) {
                                            int n5 = class_1764.method_7775((class_1799)class_17993, (class_1309)class_16572);
                                            if (n5 > 0) {
                                                int n6 = Math.max(0, n4 - n5);
                                                int n7 = UseAnimationSuppressor.getMeshStartRemainingTicks(class_12682);
                                                if (n7 < 0) {
                                                    n7 = n3;
                                                }
                                                if (n7 >= n6 && n7 <= n4) {
                                                    int n8 = Math.max(1, n7 - n6);
                                                    int n9 = Math.max(0, n7 - n2);
                                                    float f3 = (float)n9 / (float)n8;
                                                    if (f3 < 0.0f) {
                                                        f3 = 0.0f;
                                                    }
                                                    if (f3 > 1.0f) {
                                                        f3 = 1.0f;
                                                    }
                                                    f2 = f3 * (float)n5;
                                                } else {
                                                    f2 = Math.max(0, n3 - n2);
                                                }
                                            } else {
                                                f2 = Math.max(0, n3 - n2);
                                            }
                                        }
                                    }
                                }
                            }
                            callbackInfoReturnable.setReturnValue(Float.valueOf(bl ? f : f2));
                        }
                    }
                }
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
        if (class_16572 != null && class_17992 != null) {
            class_1799 class_17993 = class_16572.method_6047();
            class_1799 class_17994 = class_16572.method_6079();
            if (class_1799.method_31577((class_1799)class_17993, (class_1799)class_17992)) {
                return class_1268.field_5808;
            }
            return class_1799.method_31577((class_1799)class_17994, (class_1799)class_17992) ? class_1268.field_5810 : null;
        }
        return null;
    }

    private static boolean shouldPreserveUseProperties(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        return class_17992.method_7909() instanceof class_1819 || class_17992.method_7909() instanceof class_1835 || class_17992.method_7909() instanceof class_1753 || class_17992.method_7909() instanceof class_1764 ? false : !UseAnimationSuppressor.isForced();
    }
}

