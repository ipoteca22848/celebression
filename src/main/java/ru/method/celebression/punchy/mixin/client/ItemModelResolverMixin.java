package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_10442;
import net.minecraft.class_10444;
import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_310;
import net.minecraft.class_804;
import net.minecraft.class_811;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.render.CreatureBucketAnimator;
import ru.method.celebression.punchy.client.render.PunchyArmRenderer;
import ru.method.celebression.punchy.client.render.UseAnimationSuppressor;
import ru.method.celebression.punchy.compat.BetterCombatCompat;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.mixin.client.accessor.ItemStackRenderStateAccessor;
import ru.method.celebression.punchy.mixin.client.accessor.ItemStackRenderStateLayerAccessor;

@Mixin(value={class_10442.class})
public class ItemModelResolverMixin {
    private static final ThreadLocal<Boolean> PUNCHY_REENTRANT = ThreadLocal.withInitial(() -> false);

        @Inject(method={"updateForLivingEntity(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/entity/LivingEntity;)V"}, at={@At(value="RETURN")})
    private void punchy$neutralizeUseTransforms(class_10444 class_104442, class_1799 class_17992, class_811 class_8112, boolean bl, class_1309 class_13092, CallbackInfo callbackInfo) {
        class_1799 class_17993;
        if (!(PUNCHY_REENTRANT.get().booleanValue() || class_17992 == null || class_17992.method_7960() || PunchyConfig.isItemBlacklisted(class_17992) || class_8112 != class_811.field_4322 && class_8112 != class_811.field_4321 && class_8112 != class_811.field_4320 && class_8112 != class_811.field_4323 || class_13092 == null || class_17992 == null || class_17992.method_7960() || !class_13092.method_6115() || (class_17993 = class_13092.method_6030()) == null || class_17993.method_7960() || !class_1799.method_31577((class_1799)class_17992, (class_1799)class_17993))) {
            class_10444 class_104443 = new class_10444();
            boolean bl2 = UseAnimationSuppressor.isForced();
            PUNCHY_REENTRANT.set(true);
            UseAnimationSuppressor.force(true);
            try {
                ((class_10442)(Object)this).method_65597(class_104443, class_17992, class_8112, class_8112 == class_811.field_4321 || class_8112 == class_811.field_4323, class_13092);
            }
            finally {
                UseAnimationSuppressor.force(bl2);
                PUNCHY_REENTRANT.set(false);
            }
            ItemStackRenderStateAccessor itemStackRenderStateAccessor = (ItemStackRenderStateAccessor)class_104442;
            ItemStackRenderStateAccessor itemStackRenderStateAccessor2 = (ItemStackRenderStateAccessor)class_104443;
            class_10444.class_10446[] class_10446Array = itemStackRenderStateAccessor.punchy$getLayers();
            class_10444.class_10446[] class_10446Array2 = itemStackRenderStateAccessor2.punchy$getLayers();
            int n = Math.min(itemStackRenderStateAccessor.punchy$getActiveLayerCount(), itemStackRenderStateAccessor2.punchy$getActiveLayerCount());
            if (class_10446Array != null && class_10446Array2 != null && n > 0) {
                for (int i = 0; i < n && i < class_10446Array.length && i < class_10446Array2.length; ++i) {
                    class_10444.class_10446 class_104462 = class_10446Array[i];
                    class_10444.class_10446 class_104463 = class_10446Array2[i];
                    if (class_104462 == null || class_104463 == null) continue;
                    class_804 class_8042 = ((ItemStackRenderStateLayerAccessor)class_104462).punchy$getTransform();
                    class_804 class_8043 = ((ItemStackRenderStateLayerAccessor)class_104463).punchy$getTransform();
                    if (class_8042 == null || class_8043 == null) continue;
                    Vector3f vector3f = class_8043.field_4287;
                    Vector3f vector3f2 = class_8043.field_4286;
                    Vector3f vector3f3 = class_8042.field_4285;
                    if (vector3f != null && vector3f2 != null && vector3f3 == null) continue;
                }
                if (class_8112 == class_811.field_4322 || class_8112 == class_811.field_4321 || class_8112 == class_811.field_4320 || class_8112 == class_811.field_4323) {
                    CreatureBucketAnimator.apply(class_17992, class_104442, ItemModelResolverMixin.resolveHand(class_8112));
                    ItemModelResolverMixin.applyVanillaWeaponDisplay(class_104442, class_17992, class_8112, class_13092);
                }
            }
        }
    }

    @Inject(method={"update(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V"}, at={@At(value="RETURN")})
    private void punchy$splitAxolotlLayers(class_10444 class_104442, class_1799 class_17992, class_811 class_8112, class_1937 class_19372, class_1309 class_13092, int n, CallbackInfo callbackInfo) {
        if (!(PUNCHY_REENTRANT.get().booleanValue() || class_17992 == null || class_17992.method_7960() || PunchyConfig.isItemBlacklisted(class_17992) || class_8112 != class_811.field_4322 && class_8112 != class_811.field_4321 && class_8112 != class_811.field_4320 && class_8112 != class_811.field_4323)) {
            CreatureBucketAnimator.apply(class_17992, class_104442, ItemModelResolverMixin.resolveHand(class_8112));
            ItemModelResolverMixin.applyVanillaWeaponDisplay(class_104442, class_17992, class_8112, null);
        }
    }

    private static void applyVanillaWeaponDisplay(class_10444 class_104442, class_1799 class_17992, class_811 class_8112, class_1309 class_13092) {
        if (class_104442 != null && class_17992 != null && !class_17992.method_7960() && !PunchyConfig.isItemBlacklisted(class_17992) && class_13092 != null && BetterCombatCompat.isAvailable() && PunchyConfig.isBetterCombatCompatEnabled() && BetterCombatCompat.isWeaponStack(class_17992) && (class_8112 == class_811.field_4320 || class_8112 == class_811.field_4323)) {
            class_804 class_8042 = PunchyArmRenderer.getCachedVanillaThirdPersonTransform(class_17992, class_8112);
            if (class_8042 == null && (class_8042 = ItemModelResolverMixin.resolveVanillaTransform(class_17992, class_8112, class_13092)) != null) {
                PunchyArmRenderer.cacheVanillaThirdPersonTransform(class_17992, class_8112, class_8042);
            }
            if (class_8042 != null) {
                ItemStackRenderStateAccessor itemStackRenderStateAccessor = (ItemStackRenderStateAccessor)class_104442;
                class_10444.class_10446[] class_10446Array = itemStackRenderStateAccessor.punchy$getLayers();
                int n = itemStackRenderStateAccessor.punchy$getActiveLayerCount();
                if (class_10446Array != null && n > 0) {
                    for (int i = 0; i < n && i < class_10446Array.length; ++i) {
                        class_10444.class_10446 class_104462 = class_10446Array[i];
                        if (class_104462 == null) continue;
                    }
                }
            }
        }
    }

        private static class_804 resolveVanillaTransform(class_1799 class_17992, class_811 class_8112, class_1309 class_13092) {
        if (class_17992 == null || class_17992.method_7960() || class_8112 == null) {
            return null;
        }
        if (class_13092 == null) {
            return null;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return null;
        }
        class_10442 class_104422 = class_3102.method_65386();
        if (class_104422 == null) {
            return null;
        }
        class_10444 class_104442 = new class_10444();
        boolean bl = UseAnimationSuppressor.isForced();
        PUNCHY_REENTRANT.set(true);
        UseAnimationSuppressor.force(true);
        try {
            class_104422.method_65597(class_104442, class_17992, class_8112, class_8112 == class_811.field_4321 || class_8112 == class_811.field_4323, class_13092);
        }
        finally {
            UseAnimationSuppressor.force(bl);
            PUNCHY_REENTRANT.set(false);
        }
        return ItemModelResolverMixin.resolveLayerTransform(class_104442);
    }

    private static class_804 resolveLayerTransform(class_10444 class_104442) {
        if (class_104442 == null) {
            return null;
        }
        ItemStackRenderStateAccessor itemStackRenderStateAccessor = (ItemStackRenderStateAccessor)class_104442;
        class_10444.class_10446[] class_10446Array = itemStackRenderStateAccessor.punchy$getLayers();
        int n = itemStackRenderStateAccessor.punchy$getActiveLayerCount();
        if (class_10446Array != null && n > 0) {
            for (int i = 0; i < n && i < class_10446Array.length; ++i) {
                class_804 class_8042;
                class_10444.class_10446 class_104462 = class_10446Array[i];
                if (class_104462 == null || (class_8042 = ((ItemStackRenderStateLayerAccessor)class_104462).punchy$getTransform()) == null) continue;
                return class_8042;
            }
            return null;
        }
        return null;
    }

    private static class_1268 resolveHand(class_811 class_8112) {
        return class_8112 != class_811.field_4321 && class_8112 != class_811.field_4323 ? class_1268.field_5808 : class_1268.field_5810;
    }
}
