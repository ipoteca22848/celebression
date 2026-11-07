package ru.method.celebression.punchy.mixin.client;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_10255;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1835;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import net.minecraft.class_746;
import net.minecraft.class_759;
import net.minecraft.class_7833;
import net.minecraft.class_804;
import net.minecraft.class_811;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.render.HandRenderBobContext;
import ru.method.celebression.punchy.client.render.PunchyArmRenderer;
import ru.method.celebression.punchy.client.render.ToolKindResolver;
import ru.method.celebression.punchy.client.render.UseAnimationSuppressor;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.MovementStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.mixin.client.accessor.ItemInHandRendererInvoker;

@Mixin(value={class_759.class})
public class ItemInHandRendererMixin {
    private static final ThreadLocal<class_804> PUNCHY_PREV_BASELINE = new ThreadLocal();
    private static final ThreadLocal<Boolean> PUNCHY_BASELINE_ACTIVE = ThreadLocal.withInitial(() -> false);

    @Inject(method={"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"}, at={@At(value="HEAD")})
    private void punchy$renderFirstPersonModel(float f, class_4587 class_45872, class_4597.class_4598 class_45982, class_746 class_7462, int n, CallbackInfo callbackInfo) {
        HandRenderBobContext.begin();
        if (ItemInHandRendererMixin.punchy$shouldSuppressVanillaHandBob(class_7462)) {
            ItemInHandRendererMixin.punchy$applyInverseVanillaBob(class_45872);
        }
        PunchyArmRenderer.renderFirstPerson((class_759)(Object)this, class_7462, f, class_45872, class_45982, n);
    }

    @Inject(method={"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"}, at={@At(value="RETURN")}, require=0)
    private void punchy$endHandRenderContext(float f, class_4587 class_45872, class_4597.class_4598 class_45982, class_746 class_7462, int n, CallbackInfo callbackInfo) {
        HandRenderBobContext.end();
    }

    @Inject(method={"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void punchy$cancelVanillaArms(class_742 class_7422, float f, float f2, class_1268 class_12682, float f3, class_1799 class_17992, float f4, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102.field_1690.method_31044().method_31034()) {
            if (class_3102.field_1724 != class_7422) {
                return;
            }
            if (!PunchyConfig.isModEnabled()) {
                return;
            }
            if (HandEquipStateMachine.shouldAllowVanillaHandOut(class_12682)) {
                if (ItemInHandRendererMixin.punchy$isActiveUseHand(class_7422, class_12682)) {
                    UseAnimationSuppressor.clear(class_12682);
                    UseAnimationSuppressor.clearMeshStart(class_12682);
                    UseAnimationSuppressor.force(false);
                }
                return;
            }
            if (PunchyConfig.isHandBlacklisted((class_1657)class_7422, class_12682)) {
                if (PunchyAnimationManager.shouldKeepBlacklistedOppositeHandForDualHanded(class_3102, class_12682)) {
                    callbackInfo.cancel();
                    return;
                }
                if (ItemInHandRendererMixin.punchy$isActiveUseHand(class_7422, class_12682)) {
                    UseAnimationSuppressor.clear(class_12682);
                    UseAnimationSuppressor.clearMeshStart(class_12682);
                    UseAnimationSuppressor.force(false);
                }
                return;
            }
            callbackInfo.cancel();
        }
    }

    private static boolean punchy$isActiveUseHand(class_742 class_7422, class_1268 class_12682) {
        return class_7422 != null && class_12682 != null && class_7422.method_6115() && class_7422.method_6058() == class_12682;
    }

    private static boolean punchy$shouldSuppressVanillaHandBob(class_746 class_7462) {
        if (!PunchyConfig.isModEnabled()) {
            return false;
        }
        if (class_7462 == null || !PunchyConfig.isHandBlacklisted((class_1657)class_7462, class_1268.field_5808) && !PunchyConfig.isHandBlacklisted((class_1657)class_7462, class_1268.field_5810)) {
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 != null && ItemInHandRendererMixin.punchy$isBoatVehicle((class_1309)class_3102.field_1724)) {
                return false;
            }
            if (PunchyConfig.enableCustomWalk()) {
                return true;
            }
            return !PunchyConfig.enableSprintArmSwing() ? false : MovementStateMachine.get().getSprintBlendAlpha() > 0.001f;
        }
        return false;
    }

    private static void punchy$applyInverseVanillaBob(class_4587 class_45872) {
        if (class_45872 != null && HandRenderBobContext.hasBobSample()) {
            float f = HandRenderBobContext.getLastWalkDist();
            float f2 = HandRenderBobContext.getLastBob();
            float f3 = f * (float)Math.PI;
            float f4 = class_3532.method_15374((float)f3);
            float f5 = class_3532.method_15362((float)f3);
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(-Math.abs(class_3532.method_15362((float)(f3 - 0.2f)) * f2) * 5.0f));
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(-(f4 * f2 * 3.0f)));
            class_45872.method_46416(-(f4 * f2 * 0.5f), Math.abs(f5 * f2), 0.0f);
        }
    }

    @Redirect(method={"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/item/HeldItemRenderer;renderArmHoldingItem(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IFFLnet/minecraft/util/Arm;)V"))
    private void punchy$renderCustomArm(class_759 class_7592, class_4587 class_45872, class_4597 class_45972, int n, float f, float f2, class_1306 class_13062, class_742 class_7422, float f3, float f4, class_1268 class_12682, float f5, class_1799 class_17992, float f6, class_4587 class_45873, class_4597 class_45973, int n2) {
        if (!PunchyConfig.isModEnabled() || HandEquipStateMachine.shouldAllowVanillaHandOut(class_12682) || PunchyConfig.isHandBlacklisted((class_1657)class_7422, class_12682)) {
            ((ItemInHandRendererInvoker)class_7592).punchy$renderPlayerArm(class_45872, class_45972, n, f, f2, class_13062);
        }
    }

    private static boolean punchy$isBoatVehicle(class_1309 class_13092) {
        return class_13092 != null && class_13092.method_5854() instanceof class_10255;
    }

    @Inject(method={"renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="HEAD")})
    private void punchy$lockUseDisplayBaseline(class_1309 class_13092, class_1799 class_17992, class_811 class_8112, boolean bl, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        class_804 class_8042;
        class_1799 class_17993;
        if (ItemInHandRendererMixin.shouldSkipRenderItemHooksForInteractiveStuffCompat()) {
            ItemInHandRendererMixin.clearBaselineHookState();
        } else if (class_13092 != null && class_17992 != null && !class_17992.method_7960() && class_13092.method_6115() && !PunchyConfig.isItemBlacklisted(class_17992) && (class_17993 = class_13092.method_6030()) != null && !class_17993.method_7960() && class_1799.method_31577((class_1799)class_17992, (class_1799)class_17993) && (class_8042 = PunchyArmRenderer.resolveBaselineUseTransform(class_17992, class_8112, class_13092)) != null) {
            PUNCHY_PREV_BASELINE.set(PunchyArmRenderer.getBaselineUseTransform());
            PUNCHY_BASELINE_ACTIVE.set(true);
            PunchyArmRenderer.setBaselineUseTransform(class_8042);
        }
    }

    @Inject(method={"renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="RETURN")})
    private void punchy$restoreUseDisplayBaseline(class_1309 class_13092, class_1799 class_17992, class_811 class_8112, boolean bl, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        if (ItemInHandRendererMixin.shouldSkipRenderItemHooksForInteractiveStuffCompat()) {
            ItemInHandRendererMixin.clearBaselineHookState();
        } else if (PUNCHY_BASELINE_ACTIVE.get().booleanValue()) {
            PunchyArmRenderer.setBaselineUseTransform(PUNCHY_PREV_BASELINE.get());
            PUNCHY_PREV_BASELINE.remove();
            PUNCHY_BASELINE_ACTIVE.set(false);
        }
    }

    @ModifyVariable(method={"renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at=@At(value="HEAD"), argsOnly=true)
    private class_811 punchy$forceVanillaDisplay(class_811 class_8112, class_1309 class_13092, class_1799 class_17992) {
        if (!PunchyArmRenderer.shouldForceVanillaDisplay()) {
            return class_8112;
        }
        if (!ItemInHandRendererMixin.shouldForceVanillaDisplay(class_8112, class_13092, class_17992)) {
            return class_8112;
        }
        if (class_8112 == class_811.field_4320) {
            return class_811.field_4322;
        }
        return class_8112 == class_811.field_4323 ? class_811.field_4321 : class_8112;
    }

    private static boolean shouldForceVanillaDisplay(class_811 class_8112, class_1309 class_13092, class_1799 class_17992) {
        if (class_8112 != class_811.field_4320 && class_8112 != class_811.field_4323) {
            return false;
        }
        if (class_13092 != null && class_17992 != null && !class_17992.method_7960()) {
            class_1799 activeStack = class_13092.method_6115() ? class_13092.method_6030() : null;
            if (activeStack != null && !activeStack.method_7960() && class_1799.method_31577(class_17992, activeStack)) {
                return false;
            }
            if (class_17992.method_7909() instanceof class_1835) {
                return false;
            }
            if (PunchyConfig.isItemBlacklisted(class_17992)) {
                return false;
            }
            VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
            if (toolKind != VanillaFirstPersonItemLayer.ToolKind.INGREDIENT && toolKind != VanillaFirstPersonItemLayer.ToolKind.FOOD) {
                class_310 class_3102 = class_310.method_1551();
                if (class_3102 == null) {
                    return false;
                }
                class_811 class_8114 = class_8112 == class_811.field_4320 ? class_811.field_4322 : class_811.field_4321;
                class_804 class_8042 = PunchyArmRenderer.resolveBaselineUseTransform(class_17992, class_8112, class_13092);
                class_804 class_8043 = PunchyArmRenderer.resolveBaselineUseTransform(class_17992, class_8114, class_13092);
                if (class_8042 != null && class_8043 != null) {
                    Vector3f vector3f = ItemInHandRendererMixin.computeUpVector(class_8042);
                    Vector3f vector3f2 = ItemInHandRendererMixin.computeUpVector(class_8043);
                    if (vector3f != null && vector3f2 != null) {
                        float f = vector3f.dot((Vector3fc)vector3f2);
                        return f < 0.6f;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private static Vector3f computeUpVector(class_804 class_8042) {
        if (class_8042 != null && class_8042.field_4287 != null) {
            Vector3f vector3f = new Vector3f((Vector3fc)class_8042.field_4287);
            Matrix3f matrix3f = new Matrix3f().rotateX((float)Math.toRadians(vector3f.x)).rotateY((float)Math.toRadians(vector3f.y)).rotateZ((float)Math.toRadians(vector3f.z));
            Vector3f vector3f2 = new Vector3f(0.0f, 1.0f, 0.0f);
            matrix3f.transform(vector3f2);
            if (vector3f2.lengthSquared() > 0.0f) {
                vector3f2.normalize();
            }
            return vector3f2;
        }
        return null;
    }

    private static boolean shouldSkipRenderItemHooksForInteractiveStuffCompat() {
        if (!PunchyConfig.isInteractiveStuffCompatEnabled()) {
            return false;
        }
        class_310 class_3102 = class_310.method_1551();
        return class_3102 != null && class_3102.field_1690 != null && class_3102.field_1690.method_31044().method_31034();
    }

    private static void clearBaselineHookState() {
        PunchyArmRenderer.setBaselineUseTransform(PUNCHY_PREV_BASELINE.get());
        PUNCHY_PREV_BASELINE.remove();
        PUNCHY_BASELINE_ACTIVE.set(false);
    }
}
