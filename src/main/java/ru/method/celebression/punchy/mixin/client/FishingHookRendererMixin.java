package ru.method.celebression.punchy.mixin.client;

import ru.method.celebression.animation.Animation;

import java.util.HashMap;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1787;
import net.minecraft.class_1799;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_630;
import net.minecraft.class_898;
import net.minecraft.class_906;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.method.celebression.punchy.client.access.TransformablePart;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.model.SimpleBedrockGeo;
import ru.method.celebression.punchy.client.render.CameraAnimationState;
import ru.method.celebression.punchy.client.render.FishingLineProxyTuning;
import ru.method.celebression.punchy.client.render.PunchyArmRenderer;
import ru.method.celebression.punchy.client.state.MovementStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;

@Mixin(value={class_906.class})
public abstract class FishingHookRendererMixin {
    @Inject(method={"getHandPos(Lnet/minecraft/entity/player/PlayerEntity;FF)Lnet/minecraft/util/math/Vec3d;"}, at={@At(value="HEAD")}, cancellable=true)
    private void punchy$useAnimatedGrip(class_1657 class_16572, float f, float f2, CallbackInfoReturnable<class_243> callbackInfoReturnable) {
        class_310 client = class_310.method_1551();
        if (client == null || client.field_1724 != class_16572) {
            return;
        }
        class_898 renderDispatcher = client.method_1561();
        if (renderDispatcher == null || renderDispatcher.field_4692 == null || !renderDispatcher.field_4692.method_31044().method_31034()) {
            return;
        }
        if (SimpleBedrockGeo.loadedParts.isEmpty() || !SimpleBedrockGeo.loadedParts.containsKey("camera_animation") && !SimpleBedrockGeo.loadedParts.containsKey("camera")) {
            SimpleBedrockGeo.load(class_2960.method_60655("punchy", "geo/player.geo.json"));
        }

        class_630 camera = SimpleBedrockGeo.loadedParts.get("camera");
        class_630 cameraAnimation = SimpleBedrockGeo.loadedParts.get("camera_animation");
        if (cameraAnimation == null) {
            cameraAnimation = camera;
        }
        class_630 body = SimpleBedrockGeo.loadedParts.get("body");
        class_630 rightArm = SimpleBedrockGeo.loadedParts.get("right_arm");
        class_630 leftArm = SimpleBedrockGeo.loadedParts.get("left_arm");
        class_630 rightItem = SimpleBedrockGeo.loadedParts.get("item_right");
        class_630 leftItem = SimpleBedrockGeo.loadedParts.get("item_left");
        class_630 rightGrip = SimpleBedrockGeo.loadedParts.get("itemgrip_right");
        class_630 leftGrip = SimpleBedrockGeo.loadedParts.get("itemgrip_left");
        if (cameraAnimation == null || camera == null || body == null) {
            return;
        }

        resetPose(cameraAnimation);
        resetPose(camera);
        resetPose(body);
        resetPose(rightArm);
        resetPose(leftArm);
        resetPose(rightItem);
        resetPose(leftItem);
        resetPose(rightGrip);
        resetPose(leftGrip);

        HashMap<String, class_630> animatedParts = new HashMap<>();
        animatedParts.put("camera_animation", cameraAnimation);
        animatedParts.put("camera", camera);
        animatedParts.put("body", body);
        putIfPresent(animatedParts, "right_arm", rightArm);
        putIfPresent(animatedParts, "left_arm", leftArm);
        putIfPresent(animatedParts, "item_right", rightItem);
        putIfPresent(animatedParts, "item_left", leftItem);
        putIfPresent(animatedParts, "itemgrip_right", rightGrip);
        putIfPresent(animatedParts, "itemgrip_left", leftGrip);
        PunchyAnimationManager.POSE_HANDLER.applyTransformations(animatedParts, f2);
        PunchyAnimationManager.SWIM_HANDLER.applyTransformations(animatedParts, f2, false);
        CameraAnimationState.update(camera);

        class_1306 holdingArm = resolveHoldingArm(class_16572);
        class_630 arm = holdingArm == class_1306.field_6182 ? leftArm : rightArm;
        class_630 item = holdingArm == class_1306.field_6182 ? leftItem : rightItem;
        class_630 grip = holdingArm == class_1306.field_6182 ? leftGrip : rightGrip;
        if (arm == null || item == null || grip == null) {
            return;
        }

        Matrix4f cameraTransform = resolveJointTransform(cameraAnimation, camera);
        Matrix4f bodyTransform = resolveJointTransform(cameraAnimation, camera, body);
        Matrix4f armTransform = getLocalMatrix(arm);
        Matrix4f itemTransform = getLocalMatrix(item);
        Matrix4f gripTransform = getLocalMatrix(grip);
        Matrix4f gripWorldTransform = null;
        Matrix4f cachedArmWorld = PunchyArmRenderer.getCachedArmWorld(client.field_1724, holdingArm);
        if (cachedArmWorld != null && itemTransform != null && gripTransform != null) {
            gripWorldTransform = new Matrix4f(cachedArmWorld).mul(itemTransform).mul(gripTransform);
        } else if (bodyTransform != null && armTransform != null && itemTransform != null && gripTransform != null) {
            gripWorldTransform = new Matrix4f(bodyTransform);
            MovementStateMachine movement = MovementStateMachine.get();
            if (!PunchyConfig.disableArmPhysics()) {
                movement.applyCameraLookToRoot(gripWorldTransform);
                movement.applyRootSpaceMotion(gripWorldTransform, f2, holdingArm);
                movement.applyShieldImpactToRoot(gripWorldTransform, f2, holdingArm);
            }
            gripWorldTransform.mul(armTransform);
            if (MovementStateMachine.APPLY_TO_ARMS) {
                movement.applyToMatrix(gripWorldTransform, f2, holdingArm);
            }
            movement.applyCameraYawLagToArm(gripWorldTransform);
            gripWorldTransform.mul(itemTransform).mul(gripTransform);
        }
        if (cameraTransform == null || gripWorldTransform == null) {
            return;
        }

        Matrix4f cameraAnchor = new Matrix4f().scale(0.0625f).scale(1.0f, -1.0f, -1.0f).mul(cameraTransform).translate(0.0f, 0.0f, -5.0f);
        Matrix4f gripAnchor = new Matrix4f().scale(0.0625f).scale(1.0f, -1.0f, -1.0f).mul(gripWorldTransform);
        float fieldOfView = ((Integer)renderDispatcher.field_4692.method_41808().method_41753()).intValue();
        float perspectiveBlend = 2.0408163E-4f * fieldOfView * fieldOfView;
        Matrix4f lineAnchor = cameraAnchor.lerp(gripAnchor, perspectiveBlend);
        FishingLineProxyTuning.Profile profile = FishingLineProxyTuning.get(FishingLineProxyTuning.Kind.ROD, holdingArm == class_1306.field_6182);
        if (profile != null) {
            lineAnchor.mul(new Matrix4f().scale(profile.scale).rotateX((float)Math.toRadians(profile.rotX)).rotateY((float)Math.toRadians(profile.rotY)).rotateZ((float)Math.toRadians(profile.rotZ)).translate(profile.offX, profile.offY, profile.offZ));
        }

        float pitch = class_16572.method_36455() * ((float)Math.PI / 180.0f);
        float yaw = class_16572.method_36454() * ((float)(-Math.PI) / 180.0f);
        Quaternionf viewRotation = new Quaternionf().rotateY(yaw).rotateX(pitch);
        class_243 cameraPosition = renderDispatcher.field_4686.method_19326();
        Matrix4f worldTransform = new Matrix4f()
            .translate((float)cameraPosition.field_1352, (float)cameraPosition.field_1351, (float)cameraPosition.field_1350)
            .rotate(viewRotation)
            .translate(lineAnchor.getTranslation(new Vector3f()))
            .rotate(lineAnchor.getNormalizedRotation(new Quaternionf()));
        Vector3f handPosition = worldTransform.getTranslation(new Vector3f());
        callbackInfoReturnable.setReturnValue(new class_243(handPosition.x, handPosition.y, handPosition.z));
    }

    private static void putIfPresent(HashMap<String, class_630> parts, String name, class_630 part) {
        if (part != null) {
            parts.put(name, part);
        }
    }

    private static void resetPose(class_630 class_6302) {
        if (class_6302 != null) {
            class_6302.method_41923();
        }
    }

    private static Matrix4f resolveJointTransform(class_630 ... class_630Array) {
        if (class_630Array != null && class_630Array.length != 0) {
            Matrix4f matrix4f = new Matrix4f();
            for (class_630 class_6302 : class_630Array) {
                if (class_6302 == null) {
                    return null;
                }
                matrix4f.mul((Matrix4fc)FishingHookRendererMixin.getLocalMatrix(class_6302));
            }
            return matrix4f;
        }
        return null;
    }

    private static Matrix4f getLocalMatrix(class_630 class_6302) {
        if (class_6302 == null) {
            return null;
        }
        if ((Object)class_6302 instanceof TransformablePart transformablePart) {
            Matrix4f explicitTransform = transformablePart.punchy$getExplicitTransform();
            if (explicitTransform != null) {
                return new Matrix4f(explicitTransform);
            }
        }
        return new Matrix4f().translate(class_6302.field_3657, class_6302.field_3656, class_6302.field_3655).rotateZ(class_6302.field_3674).rotateY(class_6302.field_3675).rotateX(class_6302.field_3654).scale(class_6302.field_37938, class_6302.field_37939, class_6302.field_37940);
    }

    private static class_1306 resolveHoldingArm(class_1657 class_16572) {
        if (class_16572 == null) {
            return class_1306.field_6183;
        }
        class_1799 class_17992 = class_16572.method_6047();
        class_1799 class_17993 = class_16572.method_6079();
        boolean bl = FishingHookRendererMixin.isFishingRod(class_17992);
        boolean bl2 = FishingHookRendererMixin.isFishingRod(class_17993);
        if (bl && !bl2) {
            return class_16572.method_6068();
        }
        if (bl2 && !bl) {
            return class_16572.method_6068() == class_1306.field_6183 ? class_1306.field_6182 : class_1306.field_6183;
        }
        class_1268 class_12682 = class_16572.method_6058();
        if (class_12682 == class_1268.field_5810) {
            return class_16572.method_6068() == class_1306.field_6183 ? class_1306.field_6182 : class_1306.field_6183;
        }
        return class_16572.method_6068();
    }

    private static boolean isFishingRod(class_1799 class_17992) {
        return class_17992 != null && class_17992.method_7909() instanceof class_1787;
    }
}
