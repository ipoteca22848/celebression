package ru.method.celebression.punchy.mixin.client;

import ru.method.celebression.animation.Animation;

import java.lang.reflect.Method;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_5498;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.render.UseAnimationSuppressor;
import ru.method.celebression.punchy.config.PunchyConfig;

@Pseudo
@Mixin(targets={"timmychips/modefiteitemdefinitions/property/resolver/rangeentry/UseDurationFloat"}, remap=false)
public class ModefiteUseDurationMixin {
    @Inject(method={"getValue"}, at={@At(value="HEAD")}, cancellable=true)
    private void punchy$gateUseDuration(class_1799 class_17992, class_1309 class_13092, @Coerce Object object, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (ModefiteUseDurationMixin.shouldGateUseDuration(class_17992, class_13092)) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(0.0f));
        } else {
            Float f = ModefiteUseDurationMixin.resolveMeshStartValue(class_17992, class_13092, object);
            if (f != null) {
                callbackInfoReturnable.setReturnValue(f);
            }
        }
    }

    private static boolean shouldGateUseDuration(class_1799 class_17992, class_1309 class_13092) {
        if (class_17992 != null && PunchyConfig.isItemBlacklisted(class_17992)) {
            return false;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1690 != null && class_3102.field_1690.method_31044() == class_5498.field_26664) {
            boolean bl;
            class_1657 class_16572;
            if (!(class_13092 instanceof class_1657) || class_3102.field_1724 != (class_16572 = (class_1657)class_13092)) {
                return false;
            }
            if (!class_16572.method_6115()) {
                return false;
            }
            class_1799 class_17993 = class_16572.method_6030();
            if (class_17992 == null || class_17992.method_7960() || class_17993 == null || class_17993.method_7960()) {
                return false;
            }
            if (!class_1799.method_31577((class_1799)class_17992, (class_1799)class_17993)) {
                return false;
            }
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            if (animationClip == null || !PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
                return false;
            }
            if (!PunchyAnimationManager.isClipNamed(animationClip, "use_bow")) {
                return false;
            }
            boolean bl2 = bl = animationClip.hasEffectAny("start_mesh_animation") || animationClip.hasEffectAny("loadstart") || animationClip.hasEffectAny("load_start");
            if (!bl) {
                return false;
            }
            float f = PunchyAnimationManager.POSE_HANDLER.getCurrentTime();
            boolean bl3 = animationClip.isEffectActive("start_mesh_animation", f) || animationClip.isEffectActive("loadstart", f) || animationClip.isEffectActive("load_start", f);
            return !bl3;
        }
        return false;
    }

    private static Float resolveMeshStartValue(class_1799 class_17992, class_1309 class_13092, Object object) {
        if (class_17992 != null && PunchyConfig.isItemBlacklisted(class_17992)) {
            return null;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1690 != null && class_3102.field_1690.method_31044() == class_5498.field_26664) {
            boolean bl;
            class_1657 class_16572;
            if (!(class_13092 instanceof class_1657) || class_3102.field_1724 != (class_16572 = (class_1657)class_13092)) {
                return null;
            }
            if (!class_16572.method_6115()) {
                return null;
            }
            class_1799 class_17993 = class_16572.method_6030();
            if (class_17992 == null || class_17992.method_7960() || class_17993 == null || class_17993.method_7960()) {
                return null;
            }
            if (!class_1799.method_31577((class_1799)class_17992, (class_1799)class_17993)) {
                return null;
            }
            AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            if (animationClip == null || !PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
                return null;
            }
            if (!PunchyAnimationManager.isClipNamed(animationClip, "use_bow")) {
                return null;
            }
            boolean bl2 = bl = animationClip.hasEffectAny("start_mesh_animation") || animationClip.hasEffectAny("loadstart") || animationClip.hasEffectAny("load_start");
            if (!bl) {
                return null;
            }
            int n = UseAnimationSuppressor.getMeshStartRemainingTicks();
            if (n < 0) {
                return null;
            }
            int n2 = class_16572.method_6014();
            int n3 = Math.max(0, n - n2);
            boolean bl3 = ModefiteUseDurationMixin.resolveUseRemaining(object);
            return Float.valueOf(bl3 ? n2 : n3);
        }
        return null;
    }

    private static boolean resolveUseRemaining(Object object) {
        if (object == null) {
            return false;
        }
        try {
            Method method = object.getClass().getMethod("useRemaining", new Class[0]);
            Object object2 = method.invoke(object, new Object[0]);
            return Boolean.TRUE.equals(object2);
        }
        catch (Exception exception) {
            return false;
        }
    }
}

