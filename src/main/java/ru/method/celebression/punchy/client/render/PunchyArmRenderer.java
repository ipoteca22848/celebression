package ru.method.celebression.punchy.client.render;

import ru.method.celebression.animation.Animation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.minecraft.class_1011;
import net.minecraft.class_10442;
import net.minecraft.class_10444;
import net.minecraft.class_1058;
import net.minecraft.class_1059;
import net.minecraft.class_1068;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1806;
import net.minecraft.class_1835;
import net.minecraft.class_1921;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5321;
import net.minecraft.class_5538;
import net.minecraft.class_5602;
import net.minecraft.class_591;
import net.minecraft.class_630;
import net.minecraft.class_6862;
import net.minecraft.class_742;
import net.minecraft.class_746;
import net.minecraft.class_759;
import net.minecraft.class_7833;
import net.minecraft.class_7924;
import net.minecraft.class_804;
import net.minecraft.class_811;
import net.minecraft.class_897;
import net.minecraft.class_922;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import ru.method.celebression.punchy.client.PunchyClient;
import ru.method.celebression.punchy.client.access.TransformablePart;
import ru.method.celebression.punchy.client.animation.Easing;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemAnimationManager;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemSystem;
import ru.method.celebression.punchy.client.debug.PunchyDebug;
import ru.method.celebression.punchy.client.model.SimpleBedrockGeo;
import ru.method.celebression.punchy.client.modelparts.ModelPartsParticleSystem;
import ru.method.celebression.punchy.client.modelparts.ModelPartsSystem;
import ru.method.celebression.punchy.client.render.BoatInHandRenderer;
import ru.method.celebression.punchy.client.render.CameraAnimationState;
import ru.method.celebression.punchy.client.render.ChestInHandRenderer;
import ru.method.celebression.punchy.client.render.InHandBlockEntityRenderer;
import ru.method.celebression.punchy.client.render.MacePhysics;
import ru.method.celebression.punchy.client.render.MinecartInHandRenderer;
import ru.method.celebression.punchy.client.render.PendulumPhysics;
import ru.method.celebression.punchy.client.render.ResourcePackItemTransformResolver;
import ru.method.celebression.punchy.client.render.ShieldImpactPhysics;
import ru.method.celebression.punchy.client.render.SlimeInHandPhysics;
import ru.method.celebression.punchy.client.render.ToolKindResolver;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.render.UseAnimationSuppressor;
import ru.method.celebression.punchy.client.renderer.layer.ArmMeshTuning;
import ru.method.celebression.punchy.client.renderer.layer.ArmToolTuning;
import ru.method.celebression.punchy.client.renderer.layer.PlayerArmModelLayer;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.client.state.ClimbStateMachine;
import ru.method.celebression.punchy.client.state.CrawlStateMachine;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.MovementStateMachine;
import ru.method.celebression.punchy.client.state.MudStateMachine;
import ru.method.celebression.punchy.client.state.SweatStateMachine;
import ru.method.celebression.punchy.compat.EmfFirstPersonHandCompat;
import ru.method.celebression.punchy.compat.FirstPersonModelCompat;
import ru.method.celebression.punchy.compat.SkinLayersCompat;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.mixin.client.accessor.ItemInHandRendererInvoker;
import ru.method.celebression.punchy.mixin.client.accessor.ItemStackRenderStateAccessor;
import ru.method.celebression.punchy.mixin.client.accessor.ItemStackRenderStateLayerAccessor;

public class PunchyArmRenderer {
    private static class_591 fpModelDefault;
    private static class_591 fpModelSlim;
    private static final class_1799 DEBUG_PIVOT_STACK;
    private static final class_1799 DEBUG_GRIP_STACK;
    private static final Vector3f DEBUG_PIVOT_NUDGE;
    private static final float PIVOT_BASE_OFFSET_Y = -0.14f;
    private static final float PIVOT_BASE_OFFSET_Z = 0.11f;
    private static final float PIVOT_ITEM_COMP_Y = 0.14f;
    private static final float ITEM_BASE_OFFSET_Y = -0.17f;
    private static final float ITEM_BASE_OFFSET_Z = -0.02f;
    private static final float PIVOT_BASE_OFFSET_X_RIGHT = -0.04f;
    private static final float PIVOT_BASE_OFFSET_X_LEFT = 0.04f;
    private static final float ITEM_BASE_OFFSET_X_RIGHT = -0.04f;
    private static final float ITEM_BASE_OFFSET_X_LEFT = 0.04f;
    private static final class_2960 FREEZE_ARM_STAGE_1;
    private static final class_2960 FREEZE_ARM_STAGE_2;
    private static final class_2960 FREEZE_ARM_STAGE_3;
    private static final class_2960 MUD_ARM_STAGE_1;
    private static final class_2960 MUD_ARM_STAGE_2;
    private static final class_2960 MUD_ARM_STAGE_3;
    private static final class_2960 SWEAT_ARM_STAGE_1;
    private static final class_2960 SWEAT_ARM_STAGE_2;
    private static final class_2960 SWEAT_ARM_STAGE_3;
    private static final class_2960 SWEAT_ARM_STAGE_4;
    private static final class_2960 BURN_ARM_HAND_OVERLAY;
    private static final class_2960 FIRE_ATLAS_ID;
    private static final class_2960 FIRE_ATLAS_TEXTURE;
    private static final class_2960 FIRE_SPRITE_ID;
    private static final class_2960 BURN_ARM_HAND_OVERLAY_LEGACY;
    private static final int FREEZE_TIP_RIGHT_U0 = 40;
    private static final int FREEZE_TIP_RIGHT_V0 = 28;
    private static final int FREEZE_TIP_LEFT_U0 = 32;
    private static final int FREEZE_TIP_LEFT_V0 = 60;
    private static final int FREEZE_TIP_W = 16;
    private static final int FREEZE_TIP_H = 4;
    private static final int SWEAT_TIP_RIGHT_U0 = 40;
    private static final int SWEAT_TIP_LEFT_U0 = 40;
    private static final int SWEAT_TIP_V0 = 28;
    private static final int SWEAT_TIP_W = 16;
    private static final int SWEAT_TIP_H = 16;
    private static final float FREEZE_VOXEL_THICKNESS = 0.45f;
    private static final float SWEAT_VOXEL_THICKNESS = 0.09f;
    private static final float SWEAT_PIXEL_SCALE_X = 0.65f;
    private static final float SWEAT_PIXEL_SCALE_Y = 0.15f;
    private static final float BURN_FADE_IN_TICKS = 30.0f;
    private static final float BURN_FADE_OUT_TICKS = 60.0f;
    private static final Map<String, FreezeVoxelMask> FREEZE_MASK_CACHE;
    private static final float FREEZE_SHAKE_TRANSLATION = 7.65E-4f;
    private static final float FREEZE_SHAKE_ROTATION_DEG = 0.1275f;
    private static final Map<class_2960, Integer> SKIN_HEIGHT_CACHE;
    private static final float OFFHAND_LOWER_Y = 0.0f;
    private static final float OFFHAND_LOWER_Z = 0.0f;
    private static final float OFFHAND_LOWER_X = 0.0f;
    private static final float OFFHAND_LOWER_ROT_X = 90.0f;
    private static final int OFFHAND_HIDE_AFTER_LOWER_FRAMES = 6;
    private static int offhandLowerExitDelayFrames;
    private static int offhandHideAfterLowerFrames;
    private static boolean lastOffhandEmptyForLower;
    private static int offhandAwaitHandInFrames;
    private static boolean lastOffhandHasItemForLower;
    private static int offhandAwaitHandOutFrames;
    private static boolean lastDualHandedForHide;
    private static boolean hideOffhandAfterDualExit;
    private static int hideOffhandAfterDualFrames;
    private static boolean keepEmptyOffhandVisibleForFlight;
    private static boolean flightOffhandLandingHideRequested;
    private static final float ELYTRA_OFFHAND_SHOW_SPEED_MIN = 0.55f;
    private static final EnumMap<class_1306, Matrix4f> ARM_WORLD_CACHE;
    private static final EnumMap<class_1306, Float> DRY_OUT_ARM_TUNING_BLEND;
    private static final EnumMap<class_1306, Float> ARM_ROTATION_RESET_PROGRESS;
    private static final EnumMap<class_1306, BurnFadeState> BURN_FADE_STATE;
    private static long lastArmCacheTick;
    private static final ThreadLocal<Boolean> FORCE_VANILLA_DISPLAY;
    private static final ThreadLocal<Boolean> NEUTRALIZE_USE_TRANSFORMS;
    private static volatile boolean FIRSTPERSON_HIDE;
    private static final float CRAWL_PITCH_START = 0.0f;
    private static final float CRAWL_PITCH_END = 0.0f;
    private static final float CLIMB_PITCH_START = 0.0f;
    private static final float CLIMB_PITCH_END = 0.0f;
    private static final float ARM_ROTATION_RESET_ENTER_STEP_MIN = 0.1f;
    private static final float ARM_ROTATION_RESET_ENTER_STEP_MAX = 0.16f;
    private static final float ARM_ROTATION_RESET_EXIT_STEP_MIN = 0.08f;
    private static final float ARM_ROTATION_RESET_EXIT_STEP_MAX = 0.13f;
    private static final float ARM_ROTATION_RESET_BACK_MIX = 0.45f;
    private static final class_6862<class_1792> SPEAR_TAG;
    private static final Map<class_1792, EnumMap<class_811, class_804>> VANILLA_THIRD_PERSON_CACHE;
    private static final Map<class_1792, EnumMap<class_811, class_804>> IDLE_USE_TRANSFORM_CACHE;
    private static final ThreadLocal<class_804> BASELINE_USE_TRANSFORM;

    public static boolean shouldForceVanillaDisplay() {
        return FORCE_VANILLA_DISPLAY.get();
    }

    private static void setForceVanillaDisplay(boolean bl) {
        FORCE_VANILLA_DISPLAY.set(bl);
    }

    public static boolean shouldNeutralizeUseTransforms() {
        return NEUTRALIZE_USE_TRANSFORMS.get();
    }

    private static void setNeutralizeUseTransforms(boolean bl) {
        NEUTRALIZE_USE_TRANSFORMS.set(bl);
    }

    private static float updateWaterArmTuningBlend(class_1306 class_13062, float f) {
        float f2 = DRY_OUT_ARM_TUNING_BLEND.getOrDefault(class_13062, Float.valueOf(1.0f)).floatValue();
        float f3 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        float f4 = 1.0f;
        float f5 = 0.35f + 0.25f * f3;
        boolean bl = false;
        if (PunchyAnimationManager.SWIM_HANDLER.isPlaying()) {
            AnimationClip animationClip = PunchyAnimationManager.SWIM_HANDLER.getCurrentClip();
            boolean bl2 = bl = animationClip == PunchyAnimationManager.SWIMING_CLIP;
            if (bl && class_13062 != null && animationClip != null) {
                boolean bl3;
                boolean bl4 = bl3 = ItemAnimationManager.isSwimSuppressed(class_13062) || BedrockItemAnimationManager.isSwimSuppressed(class_13062);
                if (bl3) {
                    bl = false;
                }
            }
        }
        if (bl) {
            f4 = 0.0f;
            f5 = 0.5f + 0.3f * f3;
        }
        if (Math.abs((f2 = class_3532.method_16439((float)class_3532.method_15363((float)f5, (float)0.0f, (float)1.0f), (float)f2, (float)f4)) - f4) < 0.001f) {
            f2 = f4;
        }
        DRY_OUT_ARM_TUNING_BLEND.put(class_13062, Float.valueOf(f2));
        return f2;
    }

    private static float updateArmRotationResetBlend(class_1306 class_13062, float f) {
        float f2;
        float f3 = ARM_ROTATION_RESET_PROGRESS.getOrDefault(class_13062, Float.valueOf(0.0f)).floatValue();
        float f4 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        if (PunchyArmRenderer.shouldZeroArmRotationForActiveAnimation()) {
            f2 = class_3532.method_16439((float)f4, (float)0.1f, (float)0.16f);
            f3 = class_3532.method_15363((float)(f3 + f2), (float)0.0f, (float)1.0f);
        } else {
            f2 = class_3532.method_16439((float)f4, (float)0.08f, (float)0.13f);
            f3 = class_3532.method_15363((float)(f3 - f2), (float)0.0f, (float)1.0f);
        }
        ARM_ROTATION_RESET_PROGRESS.put(class_13062, Float.valueOf(f3));
        f2 = f3 * f3 * (3.0f - 2.0f * f3);
        float f5 = class_3532.method_15363((float)Easing.easeOutBack(f3), (float)0.0f, (float)1.0f);
        float f6 = class_3532.method_16439((float)0.45f, (float)f2, (float)f5);
        return 1.0f - f6;
    }

    private static boolean shouldZeroArmRotationForActiveAnimation() {
        return PunchyArmRenderer.shouldZeroArmRotationForClip(PunchyAnimationManager.POSE_HANDLER.getCurrentClip(), PunchyAnimationManager.POSE_HANDLER.isPlaying()) || PunchyArmRenderer.shouldZeroArmRotationForClip(PunchyAnimationManager.SWIM_HANDLER.getCurrentClip(), PunchyAnimationManager.SWIM_HANDLER.isPlaying());
    }

    private static float resolveGlobalHandFactorForFlightPose() {
        MovementStateMachine movementStateMachine = MovementStateMachine.get();
        if (movementStateMachine != null && movementStateMachine.isFlightPoseActive()) {
            float f = class_3532.method_15363((float)(movementStateMachine.getFlightPoseBlend() * 1.12f), (float)0.0f, (float)1.0f);
            float f2 = class_3532.method_15363((float)Easing.easeOutBack(f), (float)0.0f, (float)1.0f);
            return 1.0f - f2;
        }
        return 1.0f;
    }

    private static float resolveFlightPoseTuningFactor(float f, class_1306 class_13062) {
        float f2 = PunchyAnimationManager.POSE_HANDLER.getArmTuningFactor(f, class_13062);
        float f3 = PunchyArmRenderer.resolveGlobalHandFactorForFlightPose();
        if (f3 < 0.999f) {
            f2 *= f3;
        }
        return f2;
    }

    private static boolean isActualFlightActive(class_746 class_7462) {
        if (class_7462 == null) {
            return false;
        }
        if (!PunchyConfig.enableElytraFlightAnimation()) {
            return false;
        }
        if (!class_7462.method_24828() && !class_7462.method_5681() && !class_7462.method_5799() && class_7462.method_6128()) {
            class_243 class_2432 = class_7462.method_18798();
            float f = class_2432 == null ? 0.0f : (float)class_2432.method_1033();
            return f >= 0.55f;
        }
        return false;
    }

    private static boolean hasEquippedElytra(class_746 class_7462) {
        if (class_7462 == null) {
            return false;
        }
        class_1799 class_17992 = class_7462.method_6118(class_1304.field_6174);
        return class_17992 != null && class_17992.method_31574(class_1802.field_8833);
    }

    private static void resetFlightOffhandState() {
        keepEmptyOffhandVisibleForFlight = false;
        flightOffhandLandingHideRequested = false;
    }

    private static boolean shouldZeroArmRotationForClip(AnimationClip animationClip, boolean bl) {
        return bl && animationClip != null ? animationClip == PunchyAnimationManager.FALLING_IN_WATER_CLIP || animationClip == PunchyAnimationManager.PUT_OUT_FIRE_CLIP || PunchyAnimationManager.isClipNamed(animationClip, "crawl") || PunchyAnimationManager.isClipNamed(animationClip, "climb") : false;
    }

    public static class_804 getCachedVanillaThirdPersonTransform(class_1799 class_17992, class_811 class_8112) {
        if (class_17992 != null && !class_17992.method_7960() && class_8112 != null) {
            class_1792 class_17922 = class_17992.method_7909();
            if (class_17922 == null) {
                return null;
            }
            EnumMap<class_811, class_804> enumMap = VANILLA_THIRD_PERSON_CACHE.get(class_17922);
            if (enumMap == null) {
                return null;
            }
            class_804 class_8042 = enumMap.get(class_8112);
            return class_8042 == null ? null : PunchyArmRenderer.cloneTransform(class_8042);
        }
        return null;
    }

    public static void cacheVanillaThirdPersonTransform(class_1799 class_17992, class_811 class_8112, class_804 class_8042) {
        class_1792 class_17923;
        if (class_17992 != null && !class_17992.method_7960() && class_8112 != null && class_8042 != null && (class_17923 = class_17992.method_7909()) != null) {
            EnumMap enumMap = VANILLA_THIRD_PERSON_CACHE.computeIfAbsent(class_17923, class_17922 -> new EnumMap(class_811.class));
            enumMap.put(class_8112, PunchyArmRenderer.cloneTransform(class_8042));
        }
    }

    private static class_804 cloneTransform(class_804 class_8042) {
        return class_8042;
    }

    public static class_804 getBaselineUseTransform() {
        return BASELINE_USE_TRANSFORM.get();
    }

    public static void setBaselineUseTransform(class_804 class_8042) {
        BASELINE_USE_TRANSFORM.set(class_8042);
    }

    public static void cacheIdleUseTransform(class_1799 class_17992, class_811 class_8112, class_804 class_8042) {
        class_1792 class_17923;
        if (class_17992 != null && !class_17992.method_7960() && class_8112 != null && class_8042 != null && (class_17923 = class_17992.method_7909()) != null) {
            EnumMap enumMap = IDLE_USE_TRANSFORM_CACHE.computeIfAbsent(class_17923, class_17922 -> new EnumMap(class_811.class));
            enumMap.put(class_8112, PunchyArmRenderer.cloneTransform(class_8042));
        }
    }

    public static class_804 getCachedIdleTransform(class_1799 class_17992, class_811 class_8112) {
        if (class_17992 != null && !class_17992.method_7960() && class_8112 != null) {
            class_1792 class_17922 = class_17992.method_7909();
            if (class_17922 == null) {
                return null;
            }
            EnumMap<class_811, class_804> enumMap = IDLE_USE_TRANSFORM_CACHE.get(class_17922);
            if (enumMap == null) {
                return null;
            }
            class_804 class_8042 = enumMap.get(class_8112);
            return class_8042 == null ? null : PunchyArmRenderer.cloneTransform(class_8042);
        }
        return null;
    }

        public static class_804 resolveBaselineUseTransform(class_1799 class_17992, class_811 class_8112, class_1309 class_13092) {
        if (class_17992 != null && !class_17992.method_7960() && class_8112 != null) {
            class_804 class_8042 = ResourcePackItemTransformResolver.resolve(class_17992, class_8112);
            if (class_8042 != null) {
                return class_8042;
            }
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 == null) {
                return null;
            }
            class_10442 class_104422 = class_3102.method_65386();
            if (class_104422 != null && class_13092 != null) {
                class_10444 class_104442 = new class_10444();
                boolean bl = UseAnimationSuppressor.isForced();
                UseAnimationSuppressor.force(true);
                try {
                    class_104422.method_65597(class_104442, class_17992, class_8112, class_8112 == class_811.field_4321 || class_8112 == class_811.field_4323, class_13092);
                }
                finally {
                    UseAnimationSuppressor.force(bl);
                }
                ItemStackRenderStateAccessor itemStackRenderStateAccessor = (ItemStackRenderStateAccessor)class_104442;
                class_10444.class_10446[] class_10446Array = itemStackRenderStateAccessor.punchy$getLayers();
                int n = itemStackRenderStateAccessor.punchy$getActiveLayerCount();
                if (class_10446Array != null && n > 0) {
                    for (int i = 0; i < n && i < class_10446Array.length; ++i) {
                        class_804 class_8043;
                        class_10444.class_10446 class_104462 = class_10446Array[i];
                        if (class_104462 == null || (class_8043 = ((ItemStackRenderStateLayerAccessor)class_104462).punchy$getTransform()) == null) continue;
                        return class_8043;
                    }
                    return null;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public static Matrix4f getCachedArmWorld(class_746 class_7462, class_1306 class_13062) {
        if (class_7462 != null && class_13062 != null && class_7462.method_37908() != null) {
            if (lastArmCacheTick != class_7462.method_37908().method_8510()) {
                return null;
            }
            Matrix4f matrix4f = ARM_WORLD_CACHE.get(class_13062);
            return matrix4f == null ? null : new Matrix4f((Matrix4fc)matrix4f);
        }
        return null;
    }

    public static void renderFirstPerson(class_759 class_7592, class_746 class_7462, float f, class_4587 class_45872, class_4597 class_45972, int n) {
        try (EmfFirstPersonHandCompat.Scope scope = EmfFirstPersonHandCompat.enter(class_7462);){
            PunchyArmRenderer.renderFirstPersonInternal(class_7592, class_7462, f, class_45872, class_45972, n);
        }
    }

    private static void renderFirstPersonInternal(class_759 class_7592, class_746 class_7462, float f, class_4587 class_45872, class_4597 class_45972, int n) {
        if (class_7462 != null && class_7592 != null && PunchyConfig.isModEnabled()) {
            MovementStateMachine.get().updateFrame(class_7462, f);
            if (!PunchyConfig.disableNativeItemPhysics()) {
                PendulumPhysics.get().updateFrame(class_7462, f);
                SlimeInHandPhysics.get().updateFrame(class_7462);
            }
            class_310 class_3102 = class_310.method_1551();
            if (class_3102.field_1690.method_31044().method_31034() && !FIRSTPERSON_HIDE && !FirstPersonModelCompat.shouldHideHands()) {
                class_1306 class_13062;
                boolean bl;
                boolean bl2;
                boolean bl3;
                boolean bl4;
                boolean bl5;
                boolean bl6;
                boolean bl7;
                class_591 class_5912 = PunchyArmRenderer.getFirstPersonModel(PunchyArmRenderer.isSlim((class_742)class_7462));
                if (SimpleBedrockGeo.loadedParts.isEmpty() || !SimpleBedrockGeo.loadedParts.containsKey("camera_animation") && !SimpleBedrockGeo.loadedParts.containsKey("camera")) {
                    class_2960 class_29602 = class_2960.method_60655((String)"punchy", (String)"geo/player.geo.json");
                    SimpleBedrockGeo.load(class_29602);
                }
                class_45872.method_22903();
                class_45872.method_22907(class_7833.field_40718.rotationDegrees(180.0f));
                PunchyArmRenderer.applyCameraAnimationCompensation(class_45872);
                PunchyArmRenderer.resetPlayerModelPose(class_5912);
                class_5912.field_3391.field_3665 = false;
                class_5912.field_3398.field_3665 = false;
                class_5912.field_3394.field_3665 = false;
                class_5912.field_3397.field_3665 = false;
                class_5912.field_3392.field_3665 = false;
                class_5912.field_3483.field_3665 = false;
                class_5912.field_3482.field_3665 = false;
                class_5912.field_3479.field_3665 = false;
                boolean bl8 = class_7462.method_6068() == class_1306.field_6183;
                class_1306 class_13063 = bl8 ? class_1306.field_6182 : class_1306.field_6183;
                boolean bl9 = !class_7462.method_6079().method_7960();
                boolean bl10 = PunchyAnimationManager.isEquipOverrideActive(class_13063);
                boolean bl11 = PunchyAnimationManager.hasRenderableEquipOverride(class_13063);
                boolean bl12 = HandEquipStateMachine.isOffhandEquipTransitionActive();
                boolean bl13 = bl9 || bl10 || bl12;
                boolean bl14 = !bl13;
                boolean bl15 = MovementStateMachine.get().isFlightPoseActive();
                boolean bl16 = PunchyArmRenderer.isActualFlightActive(class_7462);
                boolean bl17 = PunchyArmRenderer.hasEquippedElytra(class_7462);
                if (!bl17 && !bl16) {
                    PunchyArmRenderer.resetFlightOffhandState();
                }
                if (!bl14) {
                    PunchyArmRenderer.resetFlightOffhandState();
                } else if (bl16) {
                    keepEmptyOffhandVisibleForFlight = true;
                    flightOffhandLandingHideRequested = false;
                } else if (keepEmptyOffhandVisibleForFlight && class_7462.method_24828() && !flightOffhandLandingHideRequested) {
                    HandEquipStateMachine.requestFlightOffhandLandingHide(class_7462);
                    keepEmptyOffhandVisibleForFlight = false;
                    flightOffhandLandingHideRequested = true;
                } else if (!keepEmptyOffhandVisibleForFlight) {
                    flightOffhandLandingHideRequested = false;
                }
                AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
                boolean bl18 = PunchyAnimationManager.POSE_HANDLER.isPlaying() && animationClip != null && (PunchyAnimationManager.isClipNamed(animationClip, "hand_out") || animationClip.getName() != null && animationClip.getName().startsWith("hand_out_"));
                boolean bl19 = "hand_in".equals(HandEquipStateMachine.getActiveType());
                boolean bl20 = HandEquipStateMachine.shouldTreatHandOutActive();
                boolean bl21 = HandEquipStateMachine.shouldKeepOffhandVisibleUntilHandOutEnds();
                boolean bl22 = HandEquipStateMachine.shouldHideIncomingOffhandUntilHandInStarts(class_7462);
                boolean bl23 = PunchyAnimationManager.POSE_HANDLER.isCurrentDualHandedActive() || ItemAnimationManager.isAnyDualHandedActive() || BedrockItemAnimationManager.isAnyDualHandedActive();
                boolean bl24 = bl23 || PunchyAnimationManager.isSwimDualHandedActive();
                boolean bl25 = PunchyAnimationManager.isOffhandHiddenIfEmptyActive(f);
                boolean bl26 = PunchyClient.shouldSuppressOffhandRender((class_1657)class_7462);
                if (bl24) {
                    bl26 = false;
                }
                boolean bl27 = flightOffhandLandingHideRequested && (HandEquipStateMachine.shouldKeepOffhandVisibleUntilHandOutEnds() || HandEquipStateMachine.isOffhandEquipTransitionActive());
                boolean bl28 = flightOffhandLandingHideRequested && !bl16 && class_7462.method_24828() && bl14;
                boolean bl29 = bl7 = (bl16 || keepEmptyOffhandVisibleForFlight && !class_7462.method_24828()) && !bl28;
                if (keepEmptyOffhandVisibleForFlight && !bl16 && class_7462.method_24828() && !bl27) {
                    PunchyArmRenderer.resetFlightOffhandState();
                }
                boolean bl30 = (bl13 || bl7 || bl27) && !bl26;
                boolean bl31 = (bl9 || bl11) && !bl26;
                boolean bl32 = bl6 = bl26 && bl14 && !bl11;
                if (bl28) {
                    bl30 = false;
                    bl31 = false;
                }
                boolean bl33 = class_7462.method_6115() && class_7462.method_6058() == PunchyArmRenderer.resolveHand(class_7462, class_13063);
                boolean bl34 = PunchyArmRenderer.isUsingSpyglass((class_742)class_7462);
                boolean bl35 = PunchyAnimationManager.POSE_HANDLER.isPlaying() || PunchyAnimationManager.SWIM_HANDLER.isPlaying();
                boolean bl36 = bl5 = bl14 && MovementStateMachine.get().isSprintSwingActive() && !bl24 && !bl33 && !bl20 && !bl19 && !bl7 && !bl27 && !bl28;
                if (lastDualHandedForHide && !bl24 && bl14) {
                    hideOffhandAfterDualExit = true;
                    hideOffhandAfterDualFrames = 3;
                }
                if (bl24) {
                    hideOffhandAfterDualExit = false;
                    hideOffhandAfterDualFrames = 0;
                } else if (hideOffhandAfterDualFrames > 0) {
                    --hideOffhandAfterDualFrames;
                } else {
                    hideOffhandAfterDualExit = false;
                }
                boolean bl37 = bl4 = !bl24 && (hideOffhandAfterDualExit || hideOffhandAfterDualFrames > 0);
                if (lastOffhandEmptyForLower && !bl14) {
                    offhandAwaitHandInFrames = 5;
                }
                lastOffhandEmptyForLower = bl14;
                if (lastOffhandHasItemForLower && bl14) {
                    offhandAwaitHandOutFrames = 5;
                }
                boolean bl38 = lastOffhandHasItemForLower = !bl14;
                if (bl35) {
                    offhandAwaitHandInFrames = 0;
                    offhandAwaitHandOutFrames = 0;
                } else if (offhandAwaitHandInFrames > 0) {
                    --offhandAwaitHandInFrames;
                } else if (offhandAwaitHandOutFrames > 0) {
                    --offhandAwaitHandOutFrames;
                }
                boolean bl39 = bl3 = bl14 && !bl24 && !bl33 && !bl35;
                if (!bl14) {
                    offhandLowerExitDelayFrames = 0;
                } else if (bl3) {
                    offhandLowerExitDelayFrames = 3;
                } else if (!bl35 && offhandLowerExitDelayFrames > 0) {
                    --offhandLowerExitDelayFrames;
                }
                boolean bl40 = !bl14 && offhandAwaitHandInFrames > 0 && !bl35;
                boolean bl41 = bl14 && offhandAwaitHandOutFrames > 0 && !bl35;
                boolean bl42 = bl14 && !bl24 && (bl18 || bl21);
                boolean bl43 = bl6 && !bl42;
                boolean bl44 = !bl28 && !bl7 && !bl27 && !bl5 && !bl20 && !bl19 && !bl24 && !bl35 && (bl3 || offhandLowerExitDelayFrames > 0 || bl40 || bl41);
                boolean bl45 = bl14 && (bl43 || bl44);
                boolean bl46 = bl2 = bl14 && !bl42 && (bl43 || bl40 || bl41 || bl4);
                if (!bl14) {
                    offhandHideAfterLowerFrames = 0;
                } else if (bl2) {
                    offhandHideAfterLowerFrames = 6;
                } else if (bl45) {
                    offhandHideAfterLowerFrames = Math.min(6, offhandHideAfterLowerFrames + 1);
                }
                boolean bl47 = bl = bl45 && (bl2 || offhandHideAfterLowerFrames >= 6);
                if (bl43 || bl44) {
                    bl30 = bl42 || !bl;
                    bl31 = false;
                }
                if (bl5) {
                    bl30 = true;
                    bl31 = false;
                }
                if (bl28) {
                    bl30 = false;
                    bl31 = false;
                }
                boolean bl48 = bl8 || class_13063 == class_1306.field_6183 && bl30;
                boolean bl49 = !bl8 || class_13063 == class_1306.field_6182 && bl30;
                boolean bl50 = bl8 || class_13063 == class_1306.field_6183 && bl31;
                boolean bl51 = !bl8 || class_13063 == class_1306.field_6182 && bl31;
                boolean bl52 = HandEquipStateMachine.shouldAllowVanillaHandOut(class_1268.field_5808);
                boolean bl53 = HandEquipStateMachine.shouldAllowVanillaHandOut(class_1268.field_5810);
                if (bl52) {
                    if (bl8) {
                        bl48 = false;
                        bl50 = false;
                    } else {
                        bl49 = false;
                        bl51 = false;
                    }
                }
                if (bl53) {
                    if (class_13063 == class_1306.field_6183) {
                        bl48 = false;
                        bl50 = false;
                    } else {
                        bl49 = false;
                        bl51 = false;
                    }
                }
                boolean bl54 = PunchyArmRenderer.isBlacklistedForArm(class_7462, class_1306.field_6183);
                boolean bl55 = PunchyArmRenderer.isBlacklistedForArm(class_7462, class_1306.field_6182);
                if (bl54) {
                    bl48 = false;
                    bl50 = false;
                }
                if (bl55) {
                    bl49 = false;
                    bl51 = false;
                }
                if (bl4) {
                    if (class_13063 == class_1306.field_6183) {
                        bl48 = bl8;
                        bl50 = bl8;
                    } else {
                        bl49 = !bl8;
                        bl51 = !bl8;
                    }
                }
                boolean bl56 = false;
                if (!(!bl14 || bl28 || bl7 || bl27 || bl24 || bl42)) {
                    if (class_13063 == class_1306.field_6183) {
                        bl48 = bl8;
                        bl50 = bl8;
                    } else {
                        bl49 = !bl8;
                        boolean bl57 = bl51 = !bl8;
                    }
                }
                if (!bl28 && !bl7 && !bl24 && bl22) {
                    if (class_13063 == class_1306.field_6183) {
                        bl48 = bl8;
                        bl50 = bl8;
                    } else {
                        bl49 = !bl8;
                        bl51 = !bl8;
                    }
                }
                lastDualHandedForHide = bl24;
                if (bl34) {
                    bl48 = false;
                    bl49 = false;
                    bl50 = false;
                    bl51 = false;
                }
                if (!bl26 && PunchyAnimationManager.SWIM_HANDLER.isPlaying() && (class_13062 = PunchyAnimationManager.SWIM_HANDLER.getActiveArm()) == class_13063) {
                    bl56 = true;
                    bl30 = true;
                    bl31 = true;
                    bl48 = bl8 || class_13063 == class_1306.field_6183 && bl30;
                    bl49 = !bl8 || class_13063 == class_1306.field_6182 && bl30;
                    bl50 = bl8 || class_13063 == class_1306.field_6183 && bl31;
                    boolean bl58 = bl51 = !bl8 || class_13063 == class_1306.field_6182 && bl31;
                }
                if (bl24) {
                    bl48 = true;
                    bl49 = true;
                    if (!bl26) {
                        bl50 = true;
                        bl51 = true;
                    }
                }
                if (bl25) {
                    if (class_13063 == class_1306.field_6183) {
                        bl48 = bl8;
                        bl50 = bl8;
                    } else {
                        bl49 = !bl8;
                        bl51 = !bl8;
                    }
                }
                boolean bl59 = PunchyAnimationManager.shouldKeepBlacklistedOppositeHandForDualHanded(class_3102, PunchyArmRenderer.resolveHand(class_7462, class_1306.field_6183));
                boolean bl60 = PunchyAnimationManager.shouldKeepBlacklistedOppositeHandForDualHanded(class_3102, PunchyArmRenderer.resolveHand(class_7462, class_1306.field_6182));
                boolean bl61 = PunchyAnimationManager.isItemForceShown(class_1306.field_6183);
                boolean bl62 = PunchyAnimationManager.isItemForceShown(class_1306.field_6182);
                if (bl54) {
                    bl48 = bl59;
                    boolean bl63 = bl50 = bl59 && bl61;
                }
                if (bl55) {
                    bl49 = bl60;
                    boolean bl64 = bl51 = bl60 && bl62;
                }
                if (bl28) {
                    if (class_13063 == class_1306.field_6183) {
                        bl48 = bl8;
                        bl50 = bl8;
                    } else {
                        bl49 = !bl8;
                        boolean bl65 = bl51 = !bl8;
                    }
                }
                if (bl14 && bl56) {
                    bl30 = true;
                    bl31 = true;
                    bl48 = bl8 || class_13063 == class_1306.field_6183 && bl30;
                    bl49 = !bl8 || class_13063 == class_1306.field_6182 && bl30;
                    bl50 = bl8 || class_13063 == class_1306.field_6183 && bl31;
                    bl51 = !bl8 || class_13063 == class_1306.field_6182 && bl31;
                }
                bl54 = PunchyArmRenderer.isBlacklistedForArm(class_7462, class_1306.field_6183);
                bl55 = PunchyArmRenderer.isBlacklistedForArm(class_7462, class_1306.field_6182);
                if (bl54) {
                    bl48 = false;
                    bl50 = false;
                }
                if (bl55) {
                    bl49 = false;
                    bl51 = false;
                }
                class_5912.field_3401.field_3665 = bl48;
                class_5912.field_27433.field_3665 = bl49;
                class_5912.field_3486.field_3665 = false;
                class_5912.field_3484.field_3665 = false;
                class_630 class_6302 = SimpleBedrockGeo.loadedParts.get("camera_animation");
                class_630 class_6303 = SimpleBedrockGeo.loadedParts.get("camera");
                if (class_6302 == null) {
                    class_6302 = class_6303;
                }
                class_630 class_6304 = SimpleBedrockGeo.loadedParts.get("body");
                class_630 class_6305 = SimpleBedrockGeo.loadedParts.get("right_arm");
                class_630 class_6306 = SimpleBedrockGeo.loadedParts.get("left_arm");
                class_630 class_6307 = SimpleBedrockGeo.loadedParts.get("item_right");
                class_630 class_6308 = SimpleBedrockGeo.loadedParts.get("item_left");
                class_630 class_6309 = SimpleBedrockGeo.loadedParts.get("itemgrip_right");
                class_630 class_63010 = SimpleBedrockGeo.loadedParts.get("itemgrip_left");
                if (class_6302 != null) {
                    class_6302.method_41923();
                }
                if (class_6303 != null) {
                    class_6303.method_41923();
                }
                if (class_6304 != null) {
                    class_6304.method_41923();
                }
                if (class_6305 != null) {
                    class_6305.method_41923();
                }
                if (class_6306 != null) {
                    class_6306.method_41923();
                }
                if (class_6307 != null) {
                    class_6307.method_41923();
                }
                if (class_6308 != null) {
                    class_6308.method_41923();
                }
                if (class_6309 != null) {
                    class_6309.method_41923();
                }
                if (class_63010 != null) {
                    class_63010.method_41923();
                }
                HashMap<String, class_630> hashMap = new HashMap<String, class_630>();
                if (class_6302 != null) {
                    hashMap.put("camera_animation", class_6302);
                }
                if (class_6303 != null) {
                    hashMap.put("camera", class_6303);
                }
                if (class_6304 != null) {
                    hashMap.put("body", class_6304);
                }
                if (class_6305 != null) {
                    hashMap.put("right_arm", class_6305);
                }
                if (class_6306 != null) {
                    hashMap.put("left_arm", class_6306);
                }
                if (class_6307 != null) {
                    hashMap.put("item_right", class_6307);
                }
                if (class_6308 != null) {
                    hashMap.put("item_left", class_6308);
                }
                if (class_6309 != null) {
                    hashMap.put("itemgrip_right", class_6309);
                }
                if (class_63010 != null) {
                    hashMap.put("itemgrip_left", class_63010);
                }
                PunchyAnimationManager.POSE_HANDLER.applyTransformations(hashMap, f);
                if (PunchyArmRenderer.shouldApplySwimTransforms()) {
                    PunchyAnimationManager.SWIM_HANDLER.applyTransformations(hashMap, f, false);
                }
                CameraAnimationState.update(class_6303);
                Matrix4f matrix4f = PunchyArmRenderer.getLocalMatrix(class_6303);
                Matrix4f matrix4f2 = PunchyArmRenderer.getLocalMatrix(class_6304);
                Matrix4f matrix4f3 = new Matrix4f();
                if (matrix4f != null) {
                    matrix4f3.set((Matrix4fc)matrix4f);
                }
                if (matrix4f2 != null) {
                    matrix4f3.mul((Matrix4fc)matrix4f2);
                }
                PunchyArmRenderer.applyCrawlCameraDecouple(matrix4f3, class_7462);
                PunchyArmRenderer.applyCameraLookOffset(matrix4f3);
                PunchyArmRenderer.applyGeoArmToPlayer(class_5912.field_3401, class_6305, matrix4f3, class_7462, class_1306.field_6183, f);
                PunchyArmRenderer.applyGeoArmToPlayer(class_5912.field_27433, class_6306, matrix4f3, class_7462, class_1306.field_6182, f);
                if ((bl43 || bl44) && !bl) {
                    PunchyArmRenderer.applyLoweredOffhandPose(class_7462, class_5912, class_13063);
                }
                PunchyArmRenderer.clearExplicitTransform(class_5912.field_3486);
                PunchyArmRenderer.clearExplicitTransform(class_5912.field_3484);
                class_5912.field_3486.field_3665 = bl48;
                class_5912.field_3484.field_3665 = bl49;
                if (PunchyDebug.DEBUG_POSITION_ENABLED) {
                    class_45872.method_46416(PunchyDebug.POS_X * 0.0625f, PunchyDebug.POS_Y * 0.0625f, PunchyDebug.POS_Z * 0.0625f);
                    class_45872.method_22907(class_7833.field_40714.rotationDegrees(PunchyDebug.ROT_X));
                    class_45872.method_22907(class_7833.field_40716.rotationDegrees(PunchyDebug.ROT_Y));
                    class_45872.method_22907(class_7833.field_40718.rotationDegrees(PunchyDebug.ROT_Z));
                }
                class_2960 class_29603 = PunchyArmRenderer.getPlayerTexture((class_742)class_7462);
                boolean bl66 = PunchyArmRenderer.isSlim((class_742)class_7462);
                SkinLayersCompat.applyFirstPersonSleeves((class_742)class_7462, class_5912, bl66, bl49, bl48);
                if (bl49) {
                    PunchyArmRenderer.renderArm(class_5912, (class_742)class_7462, class_1306.field_6182, class_45872, class_45972, n, class_29603, bl66, f);
                }
                if (bl48) {
                    PunchyArmRenderer.renderArm(class_5912, (class_742)class_7462, class_1306.field_6183, class_45872, class_45972, n, class_29603, bl66, f);
                }
                if (bl51) {
                    PunchyArmRenderer.renderItemInHand(class_7592, class_5912, class_7462, class_1306.field_6182, class_45872, class_45972, n, matrix4f3, class_6306, class_6308, class_63010, f);
                }
                if (bl50) {
                    PunchyArmRenderer.renderItemInHand(class_7592, class_5912, class_7462, class_1306.field_6183, class_45872, class_45972, n, matrix4f3, class_6305, class_6307, class_6309, f);
                }
                class_45872.method_22909();
            }
        }
    }

    public static void render(class_742 class_7422, float f, class_4587 class_45872, class_4597 class_45972, int n, class_1306 class_13062) {
    }

    public static void renderPlayerArm(class_742 class_7422, float f, class_4587 class_45872, class_4597 class_45972, int n, class_1306 class_13062) {
        class_310 class_3102 = class_310.method_1551();
        class_897 renderer = class_3102.method_1561().method_3953((class_1297)class_7422);
        if (renderer instanceof class_922 playerRenderer && playerRenderer.method_4038() instanceof class_591 playerModel) {
            class_45872.method_22903();
            if (PunchyDebug.DEBUG_POSITION_ENABLED) {
                class_45872.method_46416(PunchyDebug.POS_X * 0.0625f, PunchyDebug.POS_Y * 0.0625f, PunchyDebug.POS_Z * 0.0625f);
            }
            playerModel.field_3401.method_41923();
            playerModel.field_27433.method_41923();
            playerModel.field_3486.method_41923();
            playerModel.field_3484.method_41923();
            playerModel.field_3401.field_3665 = class_13062 == class_1306.field_6183;
            playerModel.field_27433.field_3665 = class_13062 == class_1306.field_6182;
            playerModel.field_3486.field_3665 = class_13062 == class_1306.field_6183;
            playerModel.field_3484.field_3665 = class_13062 == class_1306.field_6182;
            Map<String, class_630> armParts = new HashMap<>();
            armParts.put("right_arm", playerModel.field_3401);
            armParts.put("left_arm", playerModel.field_27433);
            PunchyAnimationManager.POSE_HANDLER.applyTransformations(armParts, f);
            if (PunchyArmRenderer.shouldApplySwimTransforms()) {
                PunchyAnimationManager.SWIM_HANDLER.applyTransformations(armParts, f, false);
            }
            PunchyArmRenderer.copyMatrixToSleeve(playerModel.field_3401, playerModel.field_3486);
            PunchyArmRenderer.copyMatrixToSleeve(playerModel.field_27433, playerModel.field_3484);
            boolean bl = PunchyArmRenderer.isSlim(class_7422);
            SkinLayersCompat.applyFirstPersonSleeves(class_7422, playerModel, bl, class_13062 == class_1306.field_6182, class_13062 == class_1306.field_6183);
            class_2960 class_29602 = PunchyArmRenderer.getPlayerTexture(class_7422);
            if (class_13062 == class_1306.field_6183) {
                class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23580((class_2960)class_29602));
                playerModel.field_3401.method_22698(class_45872, class_45882, n, class_4608.field_21444);
                playerModel.field_3486.method_22698(class_45872, class_45882, n, class_4608.field_21444);
            } else {
                class_4588 class_45883 = class_45972.getBuffer(class_1921.method_23580((class_2960)class_29602));
                playerModel.field_27433.method_22698(class_45872, class_45883, n, class_4608.field_21444);
                playerModel.field_3484.method_22698(class_45872, class_45883, n, class_4608.field_21444);
            }
            class_45872.method_22909();
        }
    }

    private static void copyMatrixToSleeve(class_630 class_6302, class_630 class_6303) {
        if ((Object)class_6302 instanceof TransformablePart source && (Object)class_6303 instanceof TransformablePart target) {
                Matrix4f explicitTransform = source.punchy$getExplicitTransform();
                if (explicitTransform == null) {
                    target.punchy$setExplicitTransform(null);
                } else {
                    target.punchy$setExplicitTransform(new Matrix4f((Matrix4fc)explicitTransform));
                }
        }
    }

    private static boolean shouldApplySwimTransforms() {
        if (!CrawlStateMachine.shouldSuppressSwimForSwap() && !ClimbStateMachine.shouldSuppressSwimForSwap()) {
            AnimationClip animationClip;
            if (PunchyAnimationManager.SWIM_HANDLER.isPlaying() && PunchyAnimationManager.SWIM_HANDLER.getCurrentClip() != null && (PunchyAnimationManager.isClipNamed(animationClip = PunchyAnimationManager.SWIM_HANDLER.getCurrentClip(), "swiming") || PunchyAnimationManager.isClipNamed(animationClip, "crawl") || PunchyAnimationManager.isClipNamed(animationClip, "climb"))) {
                return true;
            }
            animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
            if (animationClip == null || !PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
                return true;
            }
            if (PunchyArmRenderer.isDualHandedEquipPoseActive(animationClip)) {
                return false;
            }
            String string = animationClip.getName();
            if (string != null && !string.isBlank()) {
                String string2 = string.toLowerCase(Locale.ROOT);
                return !string2.startsWith("use_") && !string2.startsWith("set_fire") && !string2.startsWith("put_out_fire");
            }
            return true;
        }
        return false;
    }

    private static boolean isDualHandedEquipPoseActive(AnimationClip animationClip) {
        if (!PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
            return false;
        }
        String string = animationClip.getName();
        if (string != null && !string.isBlank()) {
            String string2 = string.toLowerCase(Locale.ROOT);
            if (!(string2.equals("hand_in") || string2.equals("hand_out") || string2.startsWith("hand_in_") || string2.startsWith("hand_out_"))) {
                return false;
            }
            return PunchyAnimationManager.POSE_HANDLER.getActiveArm() != null ? false : PunchyAnimationManager.isDualHandedActive(animationClip, PunchyAnimationManager.POSE_HANDLER.getCurrentTime());
        }
        return false;
    }

    private static void applyGeoArmToPlayer(class_630 class_6302, class_630 class_6303, Matrix4f matrix4f, class_746 class_7462, class_1306 class_13062, float f) {
        if (class_6302 != null && (Object)class_6302 instanceof TransformablePart transformablePart) {
            if (class_6303 == null) {
                transformablePart.punchy$setExplicitTransform(null);
            } else {
                Matrix4f localMatrix = PunchyArmRenderer.getLocalMatrix(class_6303);
                if (localMatrix == null) {
                    transformablePart.punchy$setExplicitTransform(null);
                } else if (PunchyArmRenderer.isBlacklistedForArm(class_7462, class_13062)) {
                    transformablePart.punchy$setExplicitTransform(null);
                } else {
                    Matrix4f matrix4f2 = new Matrix4f((Matrix4fc)matrix4f);
                    if (!PunchyConfig.disableArmPhysics()) {
                        MovementStateMachine.get().applyRootSpaceMotion(matrix4f2, f, class_13062);
                        MovementStateMachine.get().applyShieldImpactToRoot(matrix4f2, f, class_13062);
                    }
                    matrix4f2.mul((Matrix4fc)localMatrix);
                    PunchyArmRenderer.applyArmOffsets(matrix4f2, class_7462, class_13062, f);
                    MovementStateMachine.get().applyCameraYawLagToArm(matrix4f2);
                    if (MovementStateMachine.APPLY_TO_ARMS) {
                        MovementStateMachine.get().applyToMatrix(matrix4f2, f, class_13062);
                    }
                    PunchyArmRenderer.cacheArmWorld(class_7462, class_13062, matrix4f2);
                    transformablePart.punchy$setExplicitTransform(matrix4f2);
                }
            }
        }
    }

    private static void cacheArmWorld(class_746 class_7462, class_1306 class_13062, Matrix4f matrix4f) {
        if (class_7462 != null && class_13062 != null && matrix4f != null && class_7462.method_37908() != null) {
            long l = class_7462.method_37908().method_8510();
            if (l != lastArmCacheTick) {
                ARM_WORLD_CACHE.clear();
                lastArmCacheTick = l;
            }
            ARM_WORLD_CACHE.put(class_13062, new Matrix4f((Matrix4fc)matrix4f));
        }
    }

    private static void applyArmOffsets(Matrix4f matrix4f, class_746 class_7462, class_1306 class_13062, float f) {
        if (class_7462 != null && class_13062 != null) {
            MovementStateMachine movementStateMachine;
            float f2;
            ArmToolTuning.Profile profile;
            boolean bl = class_13062 == class_1306.field_6182;
            class_1268 class_12682 = PunchyArmRenderer.resolveHand(class_7462, class_13062);
            class_1799 class_17992 = class_7462.method_5998(class_12682);
            class_1799 class_17993 = PunchyAnimationManager.resolveRenderStack((class_1657)class_7462, class_13062, class_17992);
            PlayerArmModelLayer.ToolKind toolKind = ToolKindResolver.resolveArmKind(class_17993, PlayerArmModelLayer.ToolKind.AXE);
            boolean bl2 = !class_17993.method_7960() && ArmToolTuning.hasSpecificProfile(class_17993, bl);
            ArmToolTuning.Profile profile2 = ArmToolTuning.resolveProfile(class_17993, toolKind, bl);
            if (PunchyArmRenderer.shouldResetBurningEmptyMainHandTuning(class_7462, class_13062, class_17992)) {
                profile2 = PunchyArmRenderer.zeroArmRotations(profile2);
            }
            if (!PunchyConfig.disableNativeItemPhysics()) {
                MacePhysics.get().applyTo(matrix4f, class_7462, class_13062, f);
            }
            if ((profile = ArmToolTuning.getGlobal(bl)) == null) {
                profile = new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
            }
            if (profile2 == null) {
                profile2 = profile;
            }
            if ((f2 = PunchyArmRenderer.resolveGlobalHandFactorForFlightPose()) < 0.999f) {
                profile = new ArmToolTuning.Profile(profile.scale, profile.offX * f2, profile.offY * f2, profile.offZ * f2, profile.rotX * f2, profile.rotY * f2, profile.rotZ * f2);
            }
            if (f2 < 0.999f && toolKind == PlayerArmModelLayer.ToolKind.EMPTY_HAND && profile2 != null) {
                profile2 = new ArmToolTuning.Profile(profile2.scale, profile2.offX * f2, profile2.offY * f2, profile2.offZ * f2, profile2.rotX * f2, profile2.rotY * f2, profile2.rotZ * f2);
            }
            float f3 = PunchyArmRenderer.resolveFlightPoseTuningFactor(f, class_13062);
            if (toolKind == PlayerArmModelLayer.ToolKind.EMPTY_HAND && (movementStateMachine = MovementStateMachine.get()) != null) {
                f3 *= 1.0f - class_3532.method_15363((float)movementStateMachine.getSprintBlendAlpha(), (float)0.0f, (float)1.0f);
            }
            float f4 = class_3532.method_15363((float)(f3 *= PunchyArmRenderer.updateWaterArmTuningBlend(class_13062, f)), (float)0.0f, (float)1.0f);
            if (bl2) {
                f4 = 1.0f;
            }
            float f5 = class_3532.method_15363((float)PunchyArmRenderer.updateArmRotationResetBlend(class_13062, f), (float)0.0f, (float)1.0f);
            profile2 = PunchyArmRenderer.blendArmRotations(profile2, f5);
            float f6 = (profile.offX + (profile2.offX - profile.offX) * f4) * 16.0f;
            float f7 = (profile.offY + (profile2.offY - profile.offY) * f4) * 16.0f;
            float f8 = (profile.offZ + (profile2.offZ - profile.offZ) * f4) * 16.0f;
            float f9 = profile.rotX + (profile2.rotX - profile.rotX) * f4;
            float f10 = profile.rotY + (profile2.rotY - profile.rotY) * f4;
            float f11 = profile.rotZ + (profile2.rotZ - profile.rotZ) * f4;
            float f12 = profile.scale + (profile2.scale - profile.scale) * f4;
            matrix4f.translateLocal(f6, f7, f8);
            Matrix4f matrix4f2 = new Matrix4f().rotateX((float)Math.toRadians(f9)).rotateY((float)Math.toRadians(f10)).rotateZ((float)Math.toRadians(f11)).scale(f12, f12, f12);
            matrix4f.mul((Matrix4fc)matrix4f2);
        }
    }

    private static boolean shouldResetBurningEmptyMainHandTuning(class_746 class_7462, class_1306 class_13062, class_1799 class_17992) {
        return class_7462 != null && class_13062 != null && class_17992 != null && class_17992.method_7960() && class_7462.method_5809() ? PunchyArmRenderer.resolveHand(class_7462, class_13062) == class_1268.field_5808 : false;
    }

    private static ArmToolTuning.Profile zeroArmRotations(ArmToolTuning.Profile profile) {
        return profile == null ? null : new ArmToolTuning.Profile(profile.scale, profile.offX, profile.offY, profile.offZ, 0.0f, 0.0f, 0.0f);
    }

    private static ArmToolTuning.Profile blendArmRotations(ArmToolTuning.Profile profile, float f) {
        if (profile == null) {
            return null;
        }
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        return new ArmToolTuning.Profile(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX * f2, profile.rotY * f2, profile.rotZ * f2);
    }

    private static ToolTuning.Profile zeroItemRotations(ToolTuning.Profile profile) {
        if (profile == null) {
            return null;
        }
        ToolTuning.Profile profile2 = new ToolTuning.Profile(profile.scale, profile.offX, profile.offY, profile.offZ, 0.0f, 0.0f, 0.0f, profile.pivotX, profile.pivotY, profile.pivotZ, profile.physicsMode);
        profile2.disableSprintLateralTilt = profile.disableSprintLateralTilt;
        profile2.keepArmTuning = profile.keepArmTuning;
        profile2.keepTriggerArmTuning = profile.keepTriggerArmTuning;
        if (profile.physicsSettings != null) {
            profile2.physicsSettings = profile.physicsSettings.copy();
        }
        return profile2;
    }

    private static void applyCrawlCameraDecouple(Matrix4f matrix4f, class_746 class_7462) {
        AnimationClip animationClip;
        if (matrix4f != null && class_7462 != null && PunchyAnimationManager.SWIM_HANDLER.isPlaying() && (animationClip = PunchyAnimationManager.SWIM_HANDLER.getCurrentClip()) != null) {
            String string = animationClip.getName();
            boolean bl = "crawl".equals(string);
            boolean bl2 = "climb".equals(string);
            if (bl || bl2) {
                float f;
                float f2 = class_7462.method_36455();
                float f3 = Math.abs(f2);
                float f4 = bl ? 0.0f : 0.0f;
                float f5 = f = bl ? 0.0f : 0.0f;
                if (!(f3 <= f4)) {
                    float f6 = f <= f4 ? 1.0f : (f3 - f4) / (f - f4);
                    f6 = class_3532.method_15363((float)f6, (float)0.0f, (float)1.0f);
                    float f7 = 75.0f;
                    float f8 = Math.min(f3, f7) * Math.signum(f2);
                    float f9 = -f8 * f6;
                    MovementStateMachine movementStateMachine = MovementStateMachine.get();
                    if (movementStateMachine == null) {
                        matrix4f.rotateX((float)Math.toRadians(f9));
                    } else {
                        matrix4f.rotate((float)Math.toRadians(f9), 1.0f, 0.0f, 0.0f);
                        if (bl && f2 > 0.0f) {
                            float f10 = class_3532.method_15363((float)(f2 / 90.0f), (float)0.0f, (float)1.0f);
                            matrix4f.translate(0.0f, 20.0f * f10, 0.0f);
                        }
                    }
                }
            }
        }
    }

    private static void applyCameraLookOffset(Matrix4f matrix4f) {
        MovementStateMachine movementStateMachine;
        if (matrix4f != null && !PunchyConfig.disableArmPhysics() && (movementStateMachine = MovementStateMachine.get()) != null) {
            movementStateMachine.applyCameraLookToRoot(matrix4f);
        }
    }

    static Matrix4f getLocalMatrix(class_630 class_6302) {
        if (class_6302 == null) {
            return null;
        }
        if ((Object)class_6302 instanceof TransformablePart transformablePart) {
            Matrix4f explicitTransform = transformablePart.punchy$getExplicitTransform();
            if (explicitTransform != null) {
                return new Matrix4f((Matrix4fc)explicitTransform);
            }
        }
        return new Matrix4f().translate(class_6302.field_3657, class_6302.field_3656, class_6302.field_3655).rotateZ(class_6302.field_3674).rotateY(class_6302.field_3675).rotateX(class_6302.field_3654).scale(class_6302.field_37938, class_6302.field_37939, class_6302.field_37940);
    }

    private static Vector3f getAnimatedTranslation(class_630 class_6302) {
        if (class_6302 != null && (Object)class_6302 instanceof TransformablePart transformablePart) {
            Matrix4f explicitTransform = transformablePart.punchy$getExplicitTransform();
            if (explicitTransform != null) {
                Matrix4f matrix4f = new Matrix4f().translate(class_6302.field_3657, class_6302.field_3656, class_6302.field_3655).rotateZ(class_6302.field_3674).rotateY(class_6302.field_3675).rotateX(class_6302.field_3654).scale(class_6302.field_37938, class_6302.field_37939, class_6302.field_37940);
                Matrix4f matrix4f2 = new Matrix4f((Matrix4fc)matrix4f).invert();
                Matrix4f matrix4f3 = matrix4f2.mul((Matrix4fc)explicitTransform, new Matrix4f());
                return matrix4f3.getTranslation(new Vector3f());
            }
            return transformablePart.punchy$hasDirectPoseBase() ? new Vector3f(class_6302.field_3657 - transformablePart.punchy$getDirectPoseBaseX(), class_6302.field_3656 - transformablePart.punchy$getDirectPoseBaseY(), class_6302.field_3655 - transformablePart.punchy$getDirectPoseBaseZ()) : new Vector3f();
        }
        return new Vector3f();
    }

    private static Vector3f getAnimatedGripTranslation(class_630 class_6302) {
        if (class_6302 != null && (Object)class_6302 instanceof TransformablePart transformablePart) {
            Matrix4f explicitTransform = transformablePart.punchy$getExplicitTransform();
            if (explicitTransform == null && transformablePart.punchy$hasDirectPoseBase()) {
                Vector3f vector3f = new Vector3f(class_6302.field_3657 - transformablePart.punchy$getDirectPoseBaseX(), class_6302.field_3656 - transformablePart.punchy$getDirectPoseBaseY(), class_6302.field_3655 - transformablePart.punchy$getDirectPoseBaseZ());
                return new Vector3f(-vector3f.x, -vector3f.z, -vector3f.y);
            }
            return PunchyArmRenderer.getAnimatedTranslation(class_6302);
        }
        return new Vector3f();
    }

    private static void applyGripLocalTransform(class_4587 class_45872, class_630 class_6302, Vector3f vector3f) {
        if (class_6302 != null) {
            if ((Object)class_6302 instanceof TransformablePart transformablePart && transformablePart.punchy$getExplicitTransform() == null && transformablePart.punchy$hasDirectPoseBase()) {
                float f;
                float f2 = class_6302.field_3654 - transformablePart.punchy$getDirectPoseBaseXRot();
                float f3 = class_6302.field_3675 - transformablePart.punchy$getDirectPoseBaseYRot();
                float f4 = class_6302.field_3674 - transformablePart.punchy$getDirectPoseBaseZRot();
                float f5 = vector3f != null ? vector3f.x : 0.0f;
                float f6 = vector3f != null ? vector3f.y : 0.0f;
                float f7 = f = vector3f != null ? vector3f.z : 0.0f;
                if (f5 != 0.0f || f6 != 0.0f || f != 0.0f) {
                    class_45872.method_46416(f5, f6, f);
                }
                class_45872.method_22907(class_7833.field_40714.rotation(-f2));
                class_45872.method_22907(class_7833.field_40716.rotation(-f4));
                class_45872.method_22907(class_7833.field_40718.rotation(-f3));
                if (f5 != 0.0f || f6 != 0.0f || f != 0.0f) {
                    class_45872.method_46416(-f5, -f6, -f);
                }
            } else {
                Matrix4f localMatrix = PunchyArmRenderer.getLocalMatrix(class_6302);
                if (localMatrix != null) {
                    float f;
                    Quaternionf quaternionf = new Quaternionf();
                    localMatrix.getUnnormalizedRotation(quaternionf);
                    quaternionf.invert();
                    float f8 = vector3f != null ? vector3f.x : 0.0f;
                    float f9 = vector3f != null ? vector3f.y : 0.0f;
                    float f10 = f = vector3f != null ? vector3f.z : 0.0f;
                    if (f8 != 0.0f || f9 != 0.0f || f != 0.0f) {
                        class_45872.method_46416(f8, f9, f);
                    }
                    class_45872.method_22907(quaternionf);
                    if (f8 != 0.0f || f9 != 0.0f || f != 0.0f) {
                        class_45872.method_46416(-f8, -f9, -f);
                    }
                }
            }
        }
    }

    private static void applyGripTranslation(class_4587 class_45872, Vector3f vector3f) {
        if (vector3f != null) {
            class_45872.method_46416(vector3f.x, vector3f.y, vector3f.z);
        }
    }

    private static void applyItemPreTransform(class_4587 class_45872, Vector3f vector3f, ToolTuning.Profile profile, boolean bl) {
        if (profile != null) {
            float f;
            float f2 = profile.rotX;
            float f3 = profile.rotY;
            float f4 = profile.rotZ;
            float f5 = vector3f != null ? vector3f.x : 0.0f;
            float f6 = vector3f != null ? vector3f.y : 0.0f;
            float f7 = f = vector3f != null ? vector3f.z : 0.0f;
            if (f5 != 0.0f || f6 != 0.0f || f != 0.0f) {
                class_45872.method_46416(f5, f6, f);
            }
            if (bl) {
                class_45872.method_22907(class_7833.field_40718.rotationDegrees(f4));
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(f2));
                class_45872.method_22907(class_7833.field_40716.rotationDegrees(f3));
            } else {
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(f2));
                class_45872.method_22907(class_7833.field_40716.rotationDegrees(f3));
                class_45872.method_22907(class_7833.field_40718.rotationDegrees(f4));
            }
            if (f5 != 0.0f || f6 != 0.0f || f != 0.0f) {
                class_45872.method_46416(-f5, -f6, -f);
            }
        }
    }

    private static void applyItemTranslation(class_4587 class_45872, ToolTuning.Profile profile, boolean bl) {
        if (profile != null) {
            float f = bl ? 0.04f : -0.04f;
            class_45872.method_46416(profile.offX + f, profile.offY + 0.14f + -0.17f, profile.offZ + -0.02f);
        }
    }

    private static Vector3f getGeoItemScale(class_630 class_6302, class_630 class_6303) {
        class_630 class_6304;
        class_630 class_6305 = class_6304 = class_6302 != null ? class_6302 : class_6303;
        if (class_6304 == null) {
            return new Vector3f(1.0f, 1.0f, 1.0f);
        }
        Matrix4f matrix4f = PunchyArmRenderer.getLocalMatrix(class_6304);
        return matrix4f == null ? new Vector3f(1.0f, 1.0f, 1.0f) : PunchyArmRenderer.getScaleFromMatrix(matrix4f);
    }

    private static void applyGeoItemScale(class_4587 class_45872, class_630 class_6302, class_630 class_6303) {
        Vector3f vector3f = PunchyArmRenderer.getGeoItemScale(class_6302, class_6303);
        class_45872.method_22905(vector3f.x, vector3f.y, vector3f.z);
    }

    private static Vector3f getScaleFromMatrix(Matrix4f matrix4f) {
        Vector3f vector3f = new Vector3f(matrix4f.m00(), matrix4f.m01(), matrix4f.m02());
        Vector3f vector3f2 = new Vector3f(matrix4f.m10(), matrix4f.m11(), matrix4f.m12());
        Vector3f vector3f3 = new Vector3f(matrix4f.m20(), matrix4f.m21(), matrix4f.m22());
        return new Vector3f(vector3f.length(), vector3f2.length(), vector3f3.length());
    }

    private static float getFreezeProgress(class_742 class_7422, float f) {
        if (class_7422 == null) {
            return 0.0f;
        }
        int n = class_7422.method_32312();
        if (n <= 0) {
            return 0.0f;
        }
        int n2 = class_7422.method_32315();
        if (n2 <= 0) {
            return 0.0f;
        }
        float f2 = ((float)n + f) / (float)n2;
        return class_3532.method_15363((float)f2, (float)0.0f, (float)1.0f);
    }

    private static float smoothStep(float f, float f2, float f3) {
        float f4 = class_3532.method_15363((float)((f3 - f) / (f2 - f)), (float)0.0f, (float)1.0f);
        return f4 * f4 * (3.0f - 2.0f * f4);
    }

    private static void renderFreezeOverlay(class_630 class_6302, boolean bl, boolean bl2, class_4587 class_45872, class_4597 class_45972, int n, class_742 class_7422, float f) {
        if (PunchyConfig.enableFreezeArmOverlay()) {
            float f2;
            float f3;
            float f4 = PunchyArmRenderer.getFreezeProgress(class_7422, f);
            if (class_7422 != null) {
                f3 = 24.0f;
                if ((float)class_7422.method_32312() + f < f3) {
                    return;
                }
                int n2 = class_7422.method_32315();
                if (n2 > 0) {
                    f2 = ((float)class_7422.method_32312() + f - f3) / (float)n2;
                    f4 = class_3532.method_15363((float)f2, (float)0.0f, (float)1.0f);
                }
            }
            if (!(f4 <= 0.0f)) {
                float f5;
                if (f4 < 0.33f) {
                    f2 = 1.0f;
                    f3 = 0.0f;
                    f5 = 0.0f;
                } else if (f4 < 0.66f) {
                    f2 = 0.0f;
                    f3 = 1.0f;
                    f5 = 0.0f;
                } else {
                    f2 = 0.0f;
                    f3 = 0.0f;
                    f5 = 1.0f;
                }
                class_2960 class_29602 = f5 > 0.0f ? FREEZE_ARM_STAGE_3 : (f3 > 0.0f ? FREEZE_ARM_STAGE_2 : FREEZE_ARM_STAGE_1);
                PunchyArmRenderer.submitFreezeLayer3d(class_6302, class_45872, class_45972, n, class_29602, bl, bl2);
            }
        }
    }

    private static void renderMudOverlay(class_630 class_6302, boolean bl, boolean bl2, class_4587 class_45872, class_4597 class_45972, int n) {
        int n2;
        if (PunchyConfig.enableMud() && (n2 = MudStateMachine.getStageForArm(bl ? class_1306.field_6182 : class_1306.field_6183)) > 0) {
            class_2960 class_29602 = n2 == 1 ? MUD_ARM_STAGE_1 : (n2 == 2 ? MUD_ARM_STAGE_2 : MUD_ARM_STAGE_3);
            PunchyArmRenderer.submitFreezeLayer3d(class_6302, class_45872, class_45972, n, class_29602, bl, bl2);
        }
    }

    private static void renderSweatOverlay(class_630 class_6302, boolean bl, boolean bl2, class_4587 class_45872, class_4597 class_45972, int n) {
        int n2;
        if (PunchyConfig.enableSweat() && (n2 = SweatStateMachine.getStage()) > 0) {
            class_2960 class_29602 = switch (n2) {
                case 1 -> SWEAT_ARM_STAGE_1;
                case 2 -> SWEAT_ARM_STAGE_2;
                case 3 -> SWEAT_ARM_STAGE_3;
                default -> SWEAT_ARM_STAGE_4;
            };
            float f = SweatStateMachine.getFadeAlpha();
            if (!(f <= 0.0f)) {
                FreezeVoxelMask freezeVoxelMask = PunchyArmRenderer.getSweatMask(class_29602, bl);
                PunchyArmRenderer.submitFreezeLayer3dWithMask(class_6302, class_45872, class_45972, n, class_29602, freezeVoxelMask, bl, bl2, 0.09f, 0.65f, 0.15f, true, f);
            }
        }
    }

    private static void renderLavaHandOverlay(class_630 class_6302, class_4587 class_45872, class_4597 class_45972, int n, class_742 class_7422, class_1306 class_13062, float f) {
        class_1799 class_17992;
        float f2;
        if (class_7422 != null && class_13062 != null && PunchyConfig.enableBurn() && !((f2 = PunchyArmRenderer.updateBurnFade(class_7422, class_13062, class_17992 = PunchyArmRenderer.getStackForArm(class_7422, class_13062), f)) <= 0.001f)) {
            int n2 = class_3532.method_15340((int)((int)(f2 * 255.0f)), (int)0, (int)255);
            int n3 = n2 << 24 | 0xFFFFFF;
            class_2960 class_29602 = PunchyArmRenderer.resolveBurnOverlayTexture(class_7422);
            class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23580((class_2960)class_29602));
            class_6302.method_22699(class_45872, class_45882, n, class_4608.field_21444, n3);
        }
    }

    private static class_2960 resolveBurnOverlayTexture(class_742 class_7422) {
        if (class_7422 == null) {
            return BURN_ARM_HAND_OVERLAY;
        }
        class_2960 class_29602 = PunchyArmRenderer.getPlayerTexture(class_7422);
        if (class_29602 == null) {
            return BURN_ARM_HAND_OVERLAY;
        }
        Integer n = SKIN_HEIGHT_CACHE.get(class_29602);
        if (n == null) {
            n = PunchyArmRenderer.readTextureHeight(class_29602);
            SKIN_HEIGHT_CACHE.put(class_29602, n);
        }
        return n != null && n <= 32 ? BURN_ARM_HAND_OVERLAY_LEGACY : BURN_ARM_HAND_OVERLAY;
    }

    private static Integer readTextureHeight(class_2960 class_29602) {
        class_3300 class_33002 = class_310.method_1551().method_1478();
        Optional optional = class_33002.method_14486(class_29602);
        if (optional.isEmpty()) {
            return null;
        }
        try {
            Integer n;
            try (InputStream inputStream = ((class_3298)optional.get()).method_14482();){
                class_1011 class_10112 = class_1011.method_4309((InputStream)inputStream);
                n = class_10112.method_4323();
            }
            return n;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    private static void submitFreezeLayer3d(class_630 class_6302, class_4587 class_45872, class_4597 class_45972, int n, class_2960 class_29602, boolean bl, boolean bl2) {
        FreezeVoxelMask freezeVoxelMask = PunchyArmRenderer.getFreezeMask(class_29602, bl);
        PunchyArmRenderer.submitFreezeLayer3dWithMask(class_6302, class_45872, class_45972, n, class_29602, freezeVoxelMask, bl, bl2, 0.45f, 1.0f, 1.0f, false, 1.0f);
    }

    private static void submitFreezeLayer3dWithMask(class_630 class_6302, class_4587 class_45872, class_4597 class_45972, int n, class_2960 class_29602, FreezeVoxelMask freezeVoxelMask, boolean bl, boolean bl2, float f, float f2, float f3, boolean bl3, float f4) {
        PunchyArmRenderer.submitFreezeLayer3dWithMask(class_6302, class_45872, class_45972, n, class_29602, freezeVoxelMask, bl, bl2, f, f2, f3, bl3, f4, 6.0f);
    }

    private static void submitFreezeLayer3dWithMask(class_630 class_6302, class_4587 class_45872, class_4597 class_45972, int n, class_2960 class_29602, FreezeVoxelMask freezeVoxelMask, boolean bl, boolean bl2, float f, float f2, float f3, boolean bl3, float f4, float f5) {
        if (freezeVoxelMask != null && !freezeVoxelMask.pixels.isEmpty()) {
            float f6;
            float f7 = f6 = bl2 ? 3.0f : 4.0f;
            float f8 = bl ? -1.0f : (bl2 ? -2.0f : -3.0f);
            float f9 = f8 + f6;
            float f10 = f8;
            float f11 = f9;
            float f12 = -2.0f;
            float f13 = 2.0f;
            boolean bl4 = bl2;
            float f14 = f5;
            float f15 = 1.0f;
            class_45872.method_22903();
            class_6302.method_22703(class_45872);
            class_45872.method_22905(0.0625f, 0.0625f, 0.0625f);
            class_4588 class_45882 = class_45972.getBuffer(bl3 ? class_1921.method_23580((class_2960)class_29602) : class_1921.method_23578((class_2960)class_29602));
            class_4587.class_4665 class_46652 = class_45872.method_23760();
            for (FreezeVoxelMask.Pixel pixel : freezeVoxelMask.pixels) {
                float f16;
                float f17;
                float f18;
                float f19;
                int n2 = pixel.x / 4;
                int n3 = pixel.x % 4;
                if (bl4 && (n2 == 1 || n2 == 3) && (pixel.y == 0 || pixel.y == 3 || pixel.y == 1)) continue;
                float f20 = f14 + (float)pixel.y * 1.0f;
                float f21 = f20 + 1.0f;
                if (n2 == 0) {
                    f19 = -2.0f + (float)n3;
                    f18 = f19 + 1.0f;
                    f17 = f11;
                    f16 = f11 + f;
                    if (n3 == 0) {
                        f19 -= f;
                    }
                    if (n3 == 3) {
                        f18 += f;
                    }
                } else if (n2 == 1) {
                    f17 = f10 + (float)n3;
                    f16 = f17 + 1.0f;
                    f19 = -2.0f - f;
                    f18 = -2.0f;
                    if (n3 == 0) {
                        f17 -= f;
                    }
                    if (n3 == 3) {
                        f16 += f;
                    }
                } else if (n2 == 2) {
                    f19 = -2.0f + (float)n3;
                    f18 = f19 + 1.0f;
                    f17 = f10 - f;
                    f16 = f10;
                    if (n3 == 0) {
                        f19 -= f;
                    }
                    if (n3 == 3) {
                        f18 += f;
                    }
                } else {
                    f17 = f10 + (float)n3;
                    f16 = f17 + 1.0f;
                    f19 = 2.0f;
                    f18 = 2.0f + f;
                    if (n3 == 0) {
                        f17 -= f;
                    }
                    if (n3 == 3) {
                        f16 += f;
                    }
                }
                if (f2 < 1.0f || f3 < 1.0f) {
                    float f22 = (1.0f - f2) * 0.5f;
                    float f23 = (1.0f - f3) * 0.5f;
                    if (n2 != 0 && n2 != 2) {
                        f17 += f22;
                        f16 -= f22;
                        f20 += f23;
                        f21 -= f23;
                    } else {
                        f19 += f22;
                        f18 -= f22;
                        f20 += f23;
                        f21 -= f23;
                    }
                }
                PunchyArmRenderer.renderVoxel(class_46652, class_45882, f17, f16, f20, f21, f19, f18, pixel.u0, pixel.v0, pixel.u1, pixel.v1, n, f4);
            }
            class_45872.method_22909();
        }
    }

    private static void renderVoxel(class_4587.class_4665 class_46652, class_4588 class_45882, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, int n) {
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f4, f5, f7, f8, n, 0.0f, 1.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f4, f5, f9, f8, n, 0.0f, 1.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f4, f6, f9, f10, n, 0.0f, 1.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f4, f6, f7, f10, n, 0.0f, 1.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f3, f6, f7, f10, n, 0.0f, -1.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f3, f6, f9, f10, n, 0.0f, -1.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f3, f5, f9, f8, n, 0.0f, -1.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f3, f5, f7, f8, n, 0.0f, -1.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f3, f5, f7, f8, n, 0.0f, 0.0f, -1.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f3, f5, f9, f8, n, 0.0f, 0.0f, -1.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f4, f5, f9, f10, n, 0.0f, 0.0f, -1.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f4, f5, f7, f10, n, 0.0f, 0.0f, -1.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f4, f6, f7, f10, n, 0.0f, 0.0f, 1.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f4, f6, f9, f10, n, 0.0f, 0.0f, 1.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f3, f6, f9, f8, n, 0.0f, 0.0f, 1.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f3, f6, f7, f8, n, 0.0f, 0.0f, 1.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f3, f6, f7, f10, n, -1.0f, 0.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f4, f6, f9, f10, n, -1.0f, 0.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f4, f5, f9, f8, n, -1.0f, 0.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f, f3, f5, f7, f8, n, -1.0f, 0.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f3, f5, f7, f8, n, 1.0f, 0.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f4, f5, f9, f8, n, 1.0f, 0.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f4, f6, f9, f10, n, 1.0f, 0.0f, 0.0f);
        PunchyArmRenderer.addVertex(class_46652, class_45882, f2, f3, f6, f7, f10, n, 1.0f, 0.0f, 0.0f);
    }

    private static void renderVoxel(class_4587.class_4665 class_46652, class_4588 class_45882, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, int n, float f11) {
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f4, f5, f7, f8, n, 0.0f, 1.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f4, f5, f9, f8, n, 0.0f, 1.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f4, f6, f9, f10, n, 0.0f, 1.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f4, f6, f7, f10, n, 0.0f, 1.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f3, f6, f7, f10, n, 0.0f, -1.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f3, f6, f9, f10, n, 0.0f, -1.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f3, f5, f9, f8, n, 0.0f, -1.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f3, f5, f7, f8, n, 0.0f, -1.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f3, f5, f7, f8, n, 0.0f, 0.0f, -1.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f3, f5, f9, f8, n, 0.0f, 0.0f, -1.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f4, f5, f9, f10, n, 0.0f, 0.0f, -1.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f4, f5, f7, f10, n, 0.0f, 0.0f, -1.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f4, f6, f7, f10, n, 0.0f, 0.0f, 1.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f4, f6, f9, f10, n, 0.0f, 0.0f, 1.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f3, f6, f9, f8, n, 0.0f, 0.0f, 1.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f3, f6, f7, f8, n, 0.0f, 0.0f, 1.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f3, f6, f7, f10, n, -1.0f, 0.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f4, f6, f9, f10, n, -1.0f, 0.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f4, f5, f9, f8, n, -1.0f, 0.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f, f3, f5, f7, f8, n, -1.0f, 0.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f3, f5, f7, f8, n, 1.0f, 0.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f4, f5, f9, f8, n, 1.0f, 0.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f4, f6, f9, f10, n, 1.0f, 0.0f, 0.0f, f11);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f2, f3, f6, f7, f10, n, 1.0f, 0.0f, 0.0f, f11);
    }

    private static void addVertex(class_4587.class_4665 class_46652, class_4588 class_45882, float f, float f2, float f3, float f4, float f5, int n, float f6, float f7, float f8) {
        class_45882.method_22918(class_46652.method_23761(), f, f2, f3).method_1336(255, 255, 255, 255).method_22913(f4, f5).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, f6, f7, f8);
    }

    private static void addVertexAlpha(class_4587.class_4665 class_46652, class_4588 class_45882, float f, float f2, float f3, float f4, float f5, int n, float f6, float f7, float f8, float f9) {
        int n2 = class_3532.method_15340((int)((int)(f9 * 255.0f)), (int)0, (int)255);
        class_45882.method_22918(class_46652.method_23761(), f, f2, f3).method_1336(255, 255, 255, n2).method_22913(f4, f5).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, f6, f7, f8);
    }

    private static FreezeVoxelMask getFreezeMask(class_2960 class_29602, boolean bl) {
        return PunchyArmRenderer.getOverlayMask(class_29602, bl, "freeze", 40, 28, 32, 60, 16, 4);
    }

    private static FreezeVoxelMask getSweatMask(class_2960 class_29602, boolean bl) {
        return PunchyArmRenderer.getOverlayMask(class_29602, bl, "sweat", 40, 28, 40, 28, 16, 16);
    }

    private static FreezeVoxelMask getOverlayMask(class_2960 class_29602, boolean bl, String string, int n, int n2, int n3, int n4, int n5, int n6) {
        String string2 = string + ":" + String.valueOf(class_29602) + ":" + (bl ? "L" : "R");
        FreezeVoxelMask freezeVoxelMask = FREEZE_MASK_CACHE.get(string2);
        if (freezeVoxelMask != null) {
            return freezeVoxelMask;
        }
        class_3300 class_33002 = class_310.method_1551().method_1478();
        Optional optional = class_33002.method_14486(class_29602);
        if (optional.isEmpty()) {
            return null;
        }
        try {
            FreezeVoxelMask freezeVoxelMask2;
            try (InputStream inputStream = ((class_3298)optional.get()).method_14482();){
                class_1011 class_10112 = class_1011.method_4309((InputStream)inputStream);
                int n7 = class_10112.method_4307();
                int n8 = class_10112.method_4323();
                ArrayList<FreezeVoxelMask.Pixel> arrayList = new ArrayList<FreezeVoxelMask.Pixel>();
                int n9 = bl ? n3 : n;
                int n10 = bl ? n4 : n2;
                for (int i = 0; i < n6; ++i) {
                    for (int j = 0; j < n5; ++j) {
                        int n11 = class_10112.method_61940(n9 + j, n10 + i);
                        int n12 = n11 >> 24 & 0xFF;
                        if (n12 <= 0) continue;
                        float f = (float)(n9 + j) / (float)n7;
                        float f2 = (float)(n10 + i) / (float)n8;
                        float f3 = (float)(n9 + j + 1) / (float)n7;
                        float f4 = (float)(n10 + i + 1) / (float)n8;
                        arrayList.add(new FreezeVoxelMask.Pixel(j, i, f, f2, f3, f4));
                    }
                }
                FreezeVoxelMask freezeVoxelMask3 = new FreezeVoxelMask(arrayList);
                FREEZE_MASK_CACHE.put(string2, freezeVoxelMask3);
                freezeVoxelMask2 = freezeVoxelMask3;
            }
            return freezeVoxelMask2;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    private static boolean isHotItem(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        if (class_17992.method_31574(class_1802.field_8187)) {
            return true;
        }
        if (class_17992.method_31574(class_1802.field_8354)) {
            return true;
        }
        if (class_17992.method_31574(class_1802.field_8135)) {
            return true;
        }
        if (class_17992.method_31574(class_1802.field_8894)) {
            return true;
        }
        if (class_17992.method_31574(class_1802.field_8183)) {
            return true;
        }
        if (class_17992.method_31574(class_1802.field_8814)) {
            return true;
        }
        return class_17992.method_31574(class_1802.field_17346) ? true : class_17992.method_31574(class_1802.field_23842);
    }

    private static float updateBurnFade(class_742 class_7422, class_1306 class_13063, class_1799 class_17992, float f) {
        if (class_7422 != null && class_13063 != null && class_7422.method_37908() != null) {
            boolean bl = PunchyArmRenderer.isHotItem(class_17992);
            String string = HandEquipStateMachine.getActiveType();
            class_1306 class_13064 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
            boolean bl2 = class_13064 != null && class_13064 == class_13063;
            BurnFadeState burnFadeState = BURN_FADE_STATE.computeIfAbsent(class_13063, class_13062 -> new BurnFadeState());
            float f2 = (float)class_7422.method_37908().method_8510() + f;
            if (burnFadeState.lastTime < 0.0f) {
                burnFadeState.lastTime = f2;
                burnFadeState.value = 0.0f;
                return 0.0f;
            }
            if (bl2 && bl && "hand_in".equals(string)) {
                burnFadeState.value = 0.0f;
                burnFadeState.lastTime = f2;
                return 0.0f;
            }
            if (bl2 && !bl && "hand_out".equals(string)) {
                burnFadeState.lastTime = f2;
                return burnFadeState.value;
            }
            float f3 = f2 - burnFadeState.lastTime;
            burnFadeState.lastTime = f2;
            float f4 = bl ? 1.0f : 0.0f;
            float f5 = bl ? 30.0f : 60.0f;
            float f6 = f5 <= 0.0f ? 1.0f : class_3532.method_15363((float)(f3 / f5), (float)0.0f, (float)1.0f);
            burnFadeState.value = class_3532.method_15363((float)(burnFadeState.value + (f4 - burnFadeState.value) * f6), (float)0.0f, (float)1.0f);
            return burnFadeState.value;
        }
        return 0.0f;
    }

    private static void applyFreezeShake(class_4587 class_45872, class_742 class_7422, float f) {
        float f2;
        if (PunchyConfig.enableFreezeShake() && !((f2 = PunchyArmRenderer.getFreezeProgress(class_7422, f)) <= 0.0f)) {
            float f3 = (float)class_7422.field_6012 + f;
            float f4 = 7.65E-4f * f2;
            float f5 = 0.1275f * f2;
            float f6 = class_3532.method_15374((float)(f3 * 3.2f)) * f4;
            float f7 = class_3532.method_15374((float)(f3 * 4.6f + 1.7f)) * f4 * 0.6f;
            float f8 = class_3532.method_15374((float)(f3 * 2.4f + 2.1f)) * f4;
            class_45872.method_46416(f6, f7, f8);
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(class_3532.method_15374((float)(f3 * 5.1f)) * f5));
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(class_3532.method_15374((float)(f3 * 4.3f + 0.5f)) * f5));
        }
    }

    private static void renderArm(class_591 class_5912, class_742 class_7422, class_1306 class_13062, class_4587 class_45872, class_4597 class_45972, int n, class_2960 class_29602, boolean bl, float f) {
        class_630 class_6302 = class_13062 == class_1306.field_6182 ? class_5912.field_27433 : class_5912.field_3401;
        boolean bl2 = class_13062 == class_1306.field_6182;
        class_45872.method_22903();
        if (bl) {
            class_45872.method_46416((bl2 ? 1.0f : -1.0f) * 0.5f / 16.0f, 0.0f, 0.0f);
        }
        PunchyArmRenderer.applyArmMeshOffsets(class_45872, bl2);
        PunchyArmRenderer.applyFreezeShake(class_45872, class_7422, f);
        class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23580((class_2960)class_29602));
        class_6302.method_22698(class_45872, class_45882, n, class_4608.field_21444);
        PunchyArmRenderer.renderLavaHandOverlay(class_6302, class_45872, class_45972, n, class_7422, bl2 ? class_1306.field_6182 : class_1306.field_6183, f);
        PunchyArmRenderer.renderFreezeOverlay(class_6302, bl2, bl, class_45872, class_45972, n, class_7422, f);
        PunchyArmRenderer.renderMudOverlay(class_6302, bl2, bl, class_45872, class_45972, n);
        PunchyArmRenderer.renderSweatOverlay(class_6302, bl2, bl, class_45872, class_45972, n);
        if (class_7422.method_5809()) {
            class_45872.method_22903();
            class_6302.method_22703(class_45872);
            if (!PunchyConfig.disableEnhancedFireArmEffects()) {
                PunchyArmRenderer.renderFlameOnArm(class_45872, class_45972);
            }
            class_45872.method_22909();
        }
        class_45872.method_22909();
    }

    private static void clearExplicitTransform(class_630 class_6302) {
        if (class_6302 != null && (Object)class_6302 instanceof TransformablePart transformablePart) {
            transformablePart.punchy$setExplicitTransform(null);
        }
    }

        private static void renderItemInHand(class_759 class_7592, class_591 class_5912, class_746 class_7462, class_1306 class_13062, class_4587 class_45872, class_4597 class_45972, int n, Matrix4f matrix4f, class_630 class_6302, class_630 class_6303, class_630 class_6304, float f) {
        ToolTuning.Profile profile;
        boolean bl;
        boolean bl2 = bl = class_7462.method_6068() == class_1306.field_6183;
        class_1799 class_17992 = class_13062 == class_1306.field_6183 ? (bl ? class_7462.method_6047() : (class_7462.method_6115() && class_7462.method_6058() == class_1268.field_5810 ? class_7462.method_6079() : HandEquipStateMachine.getVisualOffhandStack(class_7462))) : (!bl ? class_7462.method_6047() : (class_7462.method_6115() && class_7462.method_6058() == class_1268.field_5810 ? class_7462.method_6079() : HandEquipStateMachine.getVisualOffhandStack(class_7462)));
        class_1799 class_17993 = PunchyAnimationManager.resolveRenderStack((class_1657)class_7462, class_13062, class_17992);
        boolean bl3 = class_13062 == class_1306.field_6182;
        class_1268 class_12682 = PunchyArmRenderer.resolveHand(class_7462, class_13062);
        boolean bl4 = class_17993.method_31574(class_1802.field_8828);
        boolean bl5 = class_17993.method_31574(class_1802.field_8777);
        boolean bl6 = PunchyArmRenderer.shouldForceBlockTuning(class_17993);
        VanillaFirstPersonItemLayer.ToolKind toolKind = bl6 ? VanillaFirstPersonItemLayer.ToolKind.BLOCK : ToolKindResolver.resolveItemKind(class_17993, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
        ToolTuning.Profile profile2 = profile = bl6 ? ToolTuning.get(VanillaFirstPersonItemLayer.ToolKind.BLOCK, bl3) : ToolTuning.resolveProfile(class_17993, toolKind, bl3);
        if (PunchyArmRenderer.shouldResetBurningEmptyMainHandTuning(class_7462, class_13062, class_17992)) {
            profile = PunchyArmRenderer.zeroItemRotations(profile);
        }
        class_45872.method_22903();
        class_5912.method_2803(class_13062, class_45872);
        PunchyArmRenderer.applyFreezeShake(class_45872, (class_742)class_7462, f);
        if (!PunchyConfig.disableNativeItemPhysics()) {
            ShieldImpactPhysics.get().applyTo(class_45872, class_13062, f);
        }
        class_45872.method_22907(class_7833.field_40714.rotationDegrees(-90.0f));
        class_45872.method_22907(class_7833.field_40716.rotationDegrees(180.0f));
        boolean bl7 = BedrockItemSystem.get().hasSlimeBonePhysics(class_17993);
        if (!(bl5 || bl4 || bl7 || PunchyConfig.disableNativeItemPhysics())) {
            SlimeInHandPhysics.get().resetHand(class_12682);
        }
        float f2 = bl3 ? -1.0f : 1.0f;
        class_45872.method_46416(f2 / 16.0f, 0.125f, -0.625f);
        if (!PunchyConfig.disableBoatFirstPersonAnimations()) {
            // empty if block
        }
        if (class_17993.method_7960()) {
            class_45872.method_22909();
        } else {
            class_811 class_8112;
            Vector3f vector3f;
            Vector3f vector3f2;
            Vector3f vector3f3 = vector3f2 = class_6304 != null ? PunchyArmRenderer.getGripPos(class_6304, class_6303, bl3) : null;
            if (class_6304 != null && vector3f2 != null) {
                vector3f = PunchyArmRenderer.getAnimatedGripTranslation(class_6304).mul(0.0625f);
                vector3f2 = new Vector3f((Vector3fc)vector3f2).add((Vector3fc)vector3f);
            }
            PunchyArmRenderer.applyGripTranslation(class_45872, vector3f2);
            vector3f = new Vector3f();
            Vector3f vector3f4 = new Vector3f();
            if (profile != null) {
                vector3f.set(profile.pivotX, profile.pivotY, profile.pivotZ);
                vector3f4 = PunchyArmRenderer.getTuningPivot(profile, bl3);
            }
            PunchyArmRenderer.applyGripLocalTransform(class_45872, class_6304, vector3f4);
            Vector3f vector3f5 = PunchyArmRenderer.getGeoItemScale(class_6304, class_6303);
            class_45872.method_22905(vector3f5.x, vector3f5.y, vector3f5.z);
            if (profile != null && profile.scale != 1.0f) {
                class_45872.method_22905(profile.scale, profile.scale, profile.scale);
            }
            if (vector3f.x != 0.0f || vector3f.y != 0.0f || vector3f.z != 0.0f) {
                class_45872.method_46416(vector3f.x, vector3f.y, vector3f.z);
            }
            Vector3f vector3f6 = new Vector3f((Vector3fc)vector3f4).sub((Vector3fc)vector3f);
            if (class_6304 != null && profile != null) {
                class_45872.method_22903();
                class_45872.method_46416(vector3f6.x, vector3f6.y, vector3f6.z);
                InHandBlockEntityRenderer.updateHeldPivot(class_7462, class_13062, class_45872);
                if (PunchyDebug.DEBUG_POSITION_ENABLED) {
                    class_45872.method_22905(vector3f5.x * 0.1f, vector3f5.y * 0.1f, vector3f5.z * 0.1f);
                    class_7592.method_3233((class_1309)class_7462, DEBUG_PIVOT_STACK, class_811.field_4319, false, class_45872, class_45972, n);
                }
                class_45872.method_22909();
            }
            boolean bl8 = ChestInHandRenderer.isChestStack(class_17993) || class_17993.method_31574(class_1802.field_8249) || class_17993.method_31574(class_1802.field_8105);
            boolean bl9 = profile != null && profile.physicsMode != null && profile.physicsMode != ToolTuning.PhysicsMode.NONE && (profile.physicsSettings == null || !Boolean.FALSE.equals(profile.physicsSettings.handSpace));
            boolean bl10 = PunchyAnimationManager.POSE_HANDLER.isPendulumDisabled(f, class_13062);
            Matrix4f matrix4f2 = new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
            Matrix4f matrix4f3 = null;
            if (bl9) {
                if (!PunchyConfig.disableNativeItemPhysics() && !bl10) {
                    PendulumPhysics.get().applyTo(class_45872, profile.physicsMode, vector3f6, profile.physicsSettings, class_13062, f);
                }
                PunchyArmRenderer.applyItemPreTransform(class_45872, vector3f6, profile, bl8);
            } else {
                PunchyArmRenderer.applyItemPreTransform(class_45872, vector3f6, profile, bl8);
                if (!PunchyConfig.disableNativeItemPhysics() && !bl10) {
                    PendulumPhysics.get().applyTo(class_45872, profile.physicsMode, vector3f6, profile.physicsSettings, class_13062, f);
                }
            }
            PunchyArmRenderer.applyItemTranslation(class_45872, profile, bl3);
            if (bl5) {
                if (!PunchyConfig.disableNativeItemPhysics()) {
                    SlimeInHandPhysics.get().applySlimeBall(class_45872, class_12682, f);
                    matrix4f3 = new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
                }
            } else if (bl4 && !PunchyConfig.disableNativeItemPhysics()) {
                SlimeInHandPhysics.get().applySlimeBlock(class_45872, class_12682, f);
                matrix4f3 = new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
            }
            if (matrix4f3 == null) {
                matrix4f3 = new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
            }
            class_811 class_8114 = class_8112 = class_13062 == class_1306.field_6183 ? class_811.field_4320 : class_811.field_4323;
            if (!PunchyConfig.disableBoatMinecartRaftModels()) {
                if (BoatInHandRenderer.isBoatStack(class_17993) && BoatInHandRenderer.tryRender(class_17993, class_12682, class_45872, class_45972, n)) {
                    class_45872.method_22909();
                    return;
                }
                if (MinecartInHandRenderer.isMinecartStack(class_17993) && MinecartInHandRenderer.tryRender(class_17993, class_12682, class_45872, class_45972, n)) {
                    class_45872.method_22909();
                    return;
                }
            }
            if (!PunchyConfig.disableChestModels() && ChestInHandRenderer.isChestStack(class_17993) && ChestInHandRenderer.tryRender(class_17993, class_12682, class_45872, class_45972, n)) {
                class_45872.method_22909();
            } else if (InHandBlockEntityRenderer.renderIfSupported(class_17993, class_7462, class_13062, class_45872, class_45972, n, f)) {
                class_45872.method_22909();
            } else {
                Matrix4f matrix4f4 = new Matrix4f((Matrix4fc)matrix4f2);
                class_4587 class_45873 = new class_4587();
                class_45873.method_23760().method_23761().set((Matrix4fc)matrix4f4);
                PunchyArmRenderer.applyItemPreTransform(class_45873, vector3f6, profile, bl8);
                PunchyArmRenderer.applyItemTranslation(class_45873, profile, bl3);
                matrix4f4 = new Matrix4f((Matrix4fc)class_45873.method_23760().method_23761());
                Matrix4f matrix4f5 = new Matrix4f((Matrix4fc)matrix4f3).invert().mul((Matrix4fc)matrix4f4);
                boolean bl11 = class_7462.method_6115() && class_7462.method_6058() == class_12682;
                boolean bl12 = false;
                BedrockItemSystem.setCurrentPhysicsDelta(class_13062, matrix4f5);
                try {
                    if (!bl12 && BedrockItemSystem.get().tryRenderItem(class_17993, class_7462, class_13062, class_45872, class_45972, n, f, true)) {
                        class_45872.method_22909();
                        return;
                    }
                }
                finally {
                    BedrockItemSystem.clearCurrentPhysicsDelta(class_13062);
                }
                if (!bl12 && ModelPartsSystem.get().tryRenderItem(class_17993, class_7462, class_13062, class_45872, class_45972, n, f, true)) {
                    class_45872.method_22909();
                } else {
                    boolean bl13 = PunchyArmRenderer.shouldNeutralizeUseTransforms();
                    if (PunchyArmRenderer.isFunctionalMap(class_7462, class_17993)) {
                        ((ItemInHandRendererInvoker)class_7592).punchy$renderMap(class_45872, class_45972, n, class_17993);
                        Matrix4f matrix4f6 = new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
                        ModelPartsParticleSystem.setRenderRoot(class_13062, matrix4f6);
                        ModelPartsParticleSystem.setExactInHandGhostRoot(class_13062, matrix4f6);
                        ModelPartsParticleSystem.emitDefinitionForItemRender(class_310.method_1551(), class_13062, matrix4f6, ToolTuning.getParticleEmitters(class_17993));
                        ModelPartsParticleSystem.renderForArm(class_310.method_1551(), class_13062, class_45972, class_45872, n);
                        class_45872.method_22909();
                    } else {
                        PunchyArmRenderer.setForceVanillaDisplay(!PunchyArmRenderer.isSpearStack(class_17993));
                        PunchyArmRenderer.setNeutralizeUseTransforms(bl11);
                        Matrix4f matrix4f7 = new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
                        try {
                            class_7592.method_3233((class_1309)class_7462, class_17993, class_8112, class_8112 == class_811.field_4321 || class_8112 == class_811.field_4323, class_45872, class_45972, n);
                        }
                        finally {
                            PunchyArmRenderer.setForceVanillaDisplay(false);
                            PunchyArmRenderer.setNeutralizeUseTransforms(bl13);
                        }
                        Matrix4f matrix4f8 = PunchyArmRenderer.resolveVanillaItemParticleRoot(class_7462, class_17993, class_8112, matrix4f7, bl3);
                        ModelPartsParticleSystem.setRenderRoot(class_13062, matrix4f8);
                        ModelPartsParticleSystem.setExactInHandGhostRoot(class_13062, matrix4f8);
                        ModelPartsParticleSystem.emitDefinitionForItemRender(class_310.method_1551(), class_13062, matrix4f8, ToolTuning.getParticleEmitters(class_17993));
                        ModelPartsParticleSystem.renderForArm(class_310.method_1551(), class_13062, class_45972, class_45872, n);
                        class_45872.method_22909();
                    }
                }
            }
        }
    }

    private static boolean isSpearStack(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        return class_17992.method_7909() instanceof class_1835 ? true : class_17992.method_31573(SPEAR_TAG);
    }

    private static Matrix4f resolveVanillaItemParticleRoot(class_746 class_7462, class_1799 class_17992, class_811 class_8112, Matrix4f matrix4f, boolean bl) {
        Matrix4f matrix4f2 = matrix4f == null ? new Matrix4f() : new Matrix4f((Matrix4fc)matrix4f);
        class_804 class_8042 = PunchyArmRenderer.resolveVanillaDisplayTransform(class_7462, class_17992, class_8112);
        if (class_8042 == null) {
            return matrix4f2;
        }
        class_4587 class_45872 = new class_4587();
        class_45872.method_23760().method_23761().set((Matrix4fc)matrix4f2);
        class_8042.method_23075(bl, class_45872);
        return new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
    }

        private static class_804 resolveVanillaDisplayTransform(class_746 class_7462, class_1799 class_17992, class_811 class_8112) {
        if (class_7462 != null && class_17992 != null && !class_17992.method_7960() && class_8112 != null) {
            class_10442 class_104422;
            class_10442 class_104423 = class_104422 = class_310.method_1551() == null ? null : class_310.method_1551().method_65386();
            if (class_104422 == null) {
                return null;
            }
            class_10444 class_104442 = new class_10444();
            boolean bl = UseAnimationSuppressor.isForced();
            UseAnimationSuppressor.force(true);
            try {
                boolean bl2 = class_8112 == class_811.field_4321 || class_8112 == class_811.field_4323;
                class_104422.method_65597(class_104442, class_17992, class_8112, bl2, (class_1309)class_7462);
            }
            finally {
                UseAnimationSuppressor.force(bl);
            }
            return PunchyArmRenderer.resolveItemTransform(class_104442);
        }
        return null;
    }

    private static class_804 resolveItemTransform(class_10444 class_104442) {
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

    private static boolean isFunctionalMap(class_746 class_7462, class_1799 class_17992) {
        return class_7462 != null && class_17992 != null && !class_17992.method_7960() ? class_1806.method_8001((class_1799)class_17992, (class_1937)class_7462.method_37908()) != null : false;
    }

    private static boolean isBlacklistedForArm(class_746 class_7462, class_1306 class_13062) {
        if (class_7462 != null && class_13062 != null) {
            boolean bl;
            boolean bl2 = bl = class_7462.method_6068() == class_1306.field_6183;
            class_1268 class_12682 = class_13062 == class_1306.field_6183 ? (bl ? class_1268.field_5808 : class_1268.field_5810) : (bl ? class_1268.field_5810 : class_1268.field_5808);
            return PunchyConfig.isHandBlacklisted((class_1657)class_7462, class_12682);
        }
        return false;
    }

    public static void setFirstPersonHidden(boolean bl) {
        FIRSTPERSON_HIDE = bl;
    }

    private static boolean isUsingSpyglass(class_742 class_7422) {
        class_1799 class_17992;
        if (class_7422 == null) {
            return false;
        }
        if (class_7422.method_6115() && (class_17992 = class_7422.method_6030()) != null && class_17992.method_7909() instanceof class_5538) {
            return true;
        }
        return class_7422.method_31550();
    }

    private static boolean shouldForceBlockTuning(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            if (!PunchyConfig.disableBoatMinecartRaftModels() || !BoatInHandRenderer.isBoatStack(class_17992) && !MinecartInHandRenderer.isMinecartStack(class_17992)) {
                if (PunchyConfig.disableChestModels() && ChestInHandRenderer.isChestStack(class_17992)) {
                    return true;
                }
                return !PunchyConfig.disablePistonModels() || !class_17992.method_31574(class_1802.field_8249) && !class_17992.method_31574(class_1802.field_8105) ? PunchyConfig.disableEnchantingTableModels() && class_17992.method_31574(class_1802.field_8657) : true;
            }
            return true;
        }
        return false;
    }

    private static void renderFlameOnArm(class_4587 class_45872, class_4597 class_45972) {
        if (class_45872 != null && class_45972 != null) {
            class_1058 class_10582 = class_310.method_1551().method_1554().method_24153(FIRE_ATLAS_ID).method_4608(FIRE_SPRITE_ID);
            class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23580((class_2960)FIRE_ATLAS_TEXTURE));
            float[] fArray = new float[]{-0.02f, -0.22f, -0.42f, -0.62f};
            float f = 0.06f;
            float f2 = 0.02f;
            float f3 = 0.56f;
            float f4 = -0.04f;
            float f5 = 0.004f;
            float[] fArray2 = new float[]{0.0f, 1.5707964f, (float)Math.PI, 4.712389f};
            class_45872.method_22903();
            class_45872.method_46416(f5, f3, f4);
            Quaternionf quaternionf = new Quaternionf().rotateX(-1.5707964f);
            for (float f6 : fArray) {
                for (float f7 : fArray2) {
                    float f8 = (float)Math.cos(f7) * f;
                    float f9 = (float)Math.sin(f7) * f;
                    Quaternionf quaternionf2 = new Quaternionf((Quaternionfc)quaternionf).rotateY(f7);
                    class_45872.method_22903();
                    class_45872.method_46416(f8, f6, f9);
                    class_45872.method_22907(quaternionf2);
                    PunchyArmRenderer.renderArmFlameQuad(class_45872, class_45882, class_10582, 0.24f, 0.32f, 0.95f);
                    class_45872.method_22909();
                }
                class_45872.method_22903();
                class_45872.method_46416(0.0f, f6, -f2);
                class_45872.method_22907(quaternionf);
                PunchyArmRenderer.renderArmFlameQuad(class_45872, class_45882, class_10582, 0.18f, 0.28f, 0.7f);
                class_45872.method_22909();
            }
            class_45872.method_22909();
        }
    }

    private static void renderArmFlameQuad(class_4587 class_45872, class_4588 class_45882, class_1058 class_10582, float f, float f2, float f3) {
        class_4587.class_4665 class_46652 = class_45872.method_23760();
        float f4 = f * 0.5f;
        float f5 = f2 * 0.5f;
        float f6 = -f2 * 0.5f;
        int n = 0xF000F0;
        float f7 = class_10582.method_4594();
        float f8 = class_10582.method_4577();
        float f9 = class_10582.method_4593();
        float f10 = class_10582.method_4575();
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, -f4, f5, 0.0f, f7, f9, n, 0.0f, 0.0f, 1.0f, f3);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f4, f5, 0.0f, f8, f9, n, 0.0f, 0.0f, 1.0f, f3);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, f4, f6, 0.0f, f8, f10, n, 0.0f, 0.0f, 1.0f, f3);
        PunchyArmRenderer.addVertexAlpha(class_46652, class_45882, -f4, f6, 0.0f, f7, f10, n, 0.0f, 0.0f, 1.0f, f3);
    }

    private static Vector3f getGripPos(class_630 class_6302, class_630 class_6303, boolean bl) {
        String string = bl ? "itemgrip_left" : "itemgrip_right";
        Vector3f vector3f = SimpleBedrockGeo.bedrockLocalPivots.get(string);
        return vector3f != null ? PunchyArmRenderer.convertPivot(vector3f).mul(0.0625f) : new Vector3f(-class_6302.field_3657, -class_6302.field_3656, class_6302.field_3655).mul(0.0625f);
    }

    private static Vector3f convertPivot(Vector3f vector3f) {
        return new Vector3f(-vector3f.x, vector3f.z, vector3f.y);
    }

    private static Vector3f getTuningPivot(ToolTuning.Profile profile, boolean bl) {
        if (profile == null) {
            return new Vector3f();
        }
        float f = bl ? 0.04f : -0.04f;
        return new Vector3f(profile.pivotX + f, profile.pivotY + -0.14f, profile.pivotZ + 0.11f);
    }

    private static void applyPivotMarkerTransform(class_4587 class_45872, ToolTuning.Profile profile, boolean bl) {
        if (profile != null) {
            class_45872.method_22905(profile.scale, profile.scale, profile.scale);
            Vector3f vector3f = PunchyArmRenderer.getTuningPivot(profile, bl);
            vector3f.add((Vector3fc)DEBUG_PIVOT_NUDGE);
            class_45872.method_46416(vector3f.x, vector3f.y, vector3f.z);
        }
    }

    private static class_1268 resolveHand(class_746 class_7462, class_1306 class_13062) {
        boolean bl;
        boolean bl2 = bl = class_7462.method_6068() == class_1306.field_6183;
        if (class_13062 == class_1306.field_6183) {
            return bl ? class_1268.field_5808 : class_1268.field_5810;
        }
        return bl ? class_1268.field_5810 : class_1268.field_5808;
    }

    private static class_1799 getStackForArm(class_742 class_7422, class_1306 class_13062) {
        if (class_7422 != null && class_13062 != null) {
            boolean bl;
            boolean bl2 = bl = class_7422.method_6068() == class_1306.field_6183;
            if (class_13062 == class_1306.field_6183) {
                return bl ? class_7422.method_6047() : class_7422.method_6079();
            }
            return bl ? class_7422.method_6079() : class_7422.method_6047();
        }
        return class_1799.field_8037;
    }

    private static void applyArmMeshOffsets(class_4587 class_45872, boolean bl) {
        ArmMeshTuning.Profile profile = ArmMeshTuning.getGlobal(bl);
        if (profile != null) {
            class_45872.method_46416(profile.offX, profile.offY, profile.offZ);
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(profile.rotX));
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(profile.rotY));
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(profile.rotZ));
            class_45872.method_22905(profile.scale, profile.scale, profile.scale);
        }
    }

    private static void renderPivotMarker(class_4597 class_45972, class_4587 class_45872) {
    }

    private static void renderFace(Matrix4f matrix4f, class_4588 class_45882, class_2350 class_23502, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        switch (class_23502) {
            case field_11033: {
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f2, f3, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f2, f3, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f2, f6, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f2, f6, f7, f8, f9, f10);
                break;
            }
            case field_11036: {
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f5, f3, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f5, f6, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f5, f6, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f5, f3, f7, f8, f9, f10);
                break;
            }
            case field_11043: {
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f2, f3, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f5, f3, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f5, f3, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f2, f3, f7, f8, f9, f10);
                break;
            }
            case field_11035: {
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f2, f6, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f2, f6, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f5, f6, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f5, f6, f7, f8, f9, f10);
                break;
            }
            case field_11039: {
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f2, f3, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f2, f6, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f5, f6, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f, f5, f3, f7, f8, f9, f10);
                break;
            }
            case field_11034: {
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f2, f3, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f5, f3, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f5, f6, f7, f8, f9, f10);
                PunchyArmRenderer.addVertex(class_45882, matrix4f, f4, f2, f6, f7, f8, f9, f10);
            }
        }
    }

    private static void addVertex(class_4588 class_45882, Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        class_45882.method_22918(matrix4f, f, f2, f3).method_22915(f4, f5, f6, f7);
    }

    private static void resetPlayerModelPose(class_591 class_5912) {
        if (class_5912 != null) {
            class_5912.field_3398.method_41923();
            class_5912.field_3394.method_41923();
            class_5912.field_3391.method_41923();
            class_5912.field_3401.method_41923();
            class_5912.field_27433.method_41923();
            class_5912.field_3392.method_41923();
            class_5912.field_3397.method_41923();
            class_5912.field_3483.method_41923();
            class_5912.field_3486.method_41923();
            class_5912.field_3484.method_41923();
            class_5912.field_3479.method_41923();
            class_5912.field_3482.method_41923();
        }
    }

    private static class_2960 getPlayerTexture(class_742 class_7422) {
        class_2960 class_29602 = class_1068.method_4649();
        try {
            if (class_7422.method_52814() != null && class_7422.method_52814().comp_1626() != null) {
                class_29602 = class_7422.method_52814().comp_1626();
            }
        }
        catch (Exception exception) {
        }
        return class_29602;
    }

    private static class_591 getFirstPersonModel(boolean bl) {
        if (bl) {
            if (fpModelSlim == null) {
                class_630 class_6302 = class_310.method_1551().method_31974().method_32072(class_5602.field_27581);
                fpModelSlim = new class_591(class_6302, true);
            }
            return fpModelSlim;
        }
        if (fpModelDefault == null) {
            class_630 class_6303 = class_310.method_1551().method_31974().method_32072(class_5602.field_27577);
            fpModelDefault = new class_591(class_6303, false);
        }
        return fpModelDefault;
    }

    private static boolean isSlim(class_742 class_7422) {
        Object object;
        Object object2;
        if (class_7422 == null) {
            return false;
        }
        try {
            object2 = class_7422.method_52814();
            if (object2 == null) {
                return false;
            }
            object = object2.getClass().getMethod("model", new Class[0]).invoke(object2, new Object[0]);
            if (PunchyArmRenderer.isSlimModelValue(object)) {
                return true;
            }
        }
        catch (Exception exception) {
        }
        try {
            object2 = class_7422.getClass().getMethod("getModelName", new Class[0]);
            object = ((Method)object2).invoke((Object)class_7422, new Object[0]);
            return PunchyArmRenderer.isSlimModelValue(object);
        }
        catch (Exception exception) {
            return false;
        }
    }

    private static boolean isSlimModelValue(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof CharSequence) {
            CharSequence charSequence = (CharSequence)object;
            return "slim".equalsIgnoreCase(charSequence.toString());
        }
        if (object instanceof Enum) {
            Enum enum_ = (Enum)object;
            return "SLIM".equalsIgnoreCase(enum_.name()) || "slim".equalsIgnoreCase(enum_.toString());
        }
        String string = String.valueOf(object);
        return "slim".equalsIgnoreCase(string) || string.endsWith(".SLIM");
    }

    private static void applyCameraAnimationCompensation(class_4587 class_45872) {
        if (class_45872 != null && CameraAnimationState.isActive()) {
            Vector3f vector3f = CameraAnimationState.offset();
            Quaternionf quaternionf = CameraAnimationState.rotation();
            if (Math.abs(quaternionf.w - 1.0f) > 1.0E-4f || quaternionf.x != 0.0f || quaternionf.y != 0.0f || quaternionf.z != 0.0f) {
                Quaternionf quaternionf2 = new Quaternionf((Quaternionfc)quaternionf).invert();
                Vector3f vector3f2 = quaternionf2.getEulerAnglesZYX(new Vector3f());
                vector3f2.x = -vector3f2.x;
                vector3f2.y = -vector3f2.y;
                quaternionf2.rotationZYX(vector3f2.z, vector3f2.y, vector3f2.x);
                class_45872.method_22907(quaternionf2);
            }
            if (vector3f.lengthSquared() > 0.0f) {
                class_45872.method_46416(vector3f.x, vector3f.y, -vector3f.z);
            }
        }
    }

    private static void applyLoweredOffhandPose(class_746 class_7462, class_591 class_5912, class_1306 class_13062) {
        if (class_7462 != null && class_5912 != null && class_13062 != null) {
            class_630 armPart = class_13062 == class_1306.field_6182 ? class_5912.field_27433 : class_5912.field_3401;
            if ((Object)armPart instanceof TransformablePart transformablePart) {
                Matrix4f explicitTransform = transformablePart.punchy$getExplicitTransform();
                if (explicitTransform == null) return;
                float f = class_13062 == class_1306.field_6182 ? -1.0f : 1.0f;
                Matrix4f matrix4f = new Matrix4f().translate(f * 0.0f, 0.0f, 0.0f).rotateX((float)Math.toRadians(90.0));
                explicitTransform.mul((Matrix4fc)matrix4f);
                transformablePart.punchy$setExplicitTransform(explicitTransform);
            }
        }
    }

    static {
        DEBUG_PIVOT_STACK = new class_1799((class_1935)class_1802.field_8197);
        DEBUG_GRIP_STACK = new class_1799((class_1935)class_1802.field_8737);
        DEBUG_PIVOT_NUDGE = new Vector3f(0.0f, 0.0f, 0.13f);
        FREEZE_ARM_STAGE_1 = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_freeze_tip_1.png");
        FREEZE_ARM_STAGE_2 = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_freeze_tip_2.png");
        FREEZE_ARM_STAGE_3 = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_freeze_tip_3.png");
        MUD_ARM_STAGE_1 = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_mud_tip_1.png");
        MUD_ARM_STAGE_2 = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_mud_tip_2.png");
        MUD_ARM_STAGE_3 = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_mud_tip_3.png");
        SWEAT_ARM_STAGE_1 = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_sweat_tip_1.png");
        SWEAT_ARM_STAGE_2 = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_sweat_tip_2.png");
        SWEAT_ARM_STAGE_3 = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_sweat_tip_3.png");
        SWEAT_ARM_STAGE_4 = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_sweat_tip_4.png");
        BURN_ARM_HAND_OVERLAY = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_burn_hand_overlay.png");
        FIRE_ATLAS_ID = class_1059.field_5275;
        FIRE_ATLAS_TEXTURE = class_1059.field_5275;
        FIRE_SPRITE_ID = class_2960.method_60655((String)"minecraft", (String)"block/fire_1");
        BURN_ARM_HAND_OVERLAY_LEGACY = class_2960.method_60655((String)"punchy", (String)"textures/misc/arm_burn_hand_overlay_legacy.png");
        FREEZE_MASK_CACHE = new HashMap<String, FreezeVoxelMask>();
        SKIN_HEIGHT_CACHE = new HashMap<class_2960, Integer>();
        offhandLowerExitDelayFrames = 0;
        offhandHideAfterLowerFrames = 0;
        lastOffhandEmptyForLower = true;
        offhandAwaitHandInFrames = 0;
        lastOffhandHasItemForLower = false;
        offhandAwaitHandOutFrames = 0;
        lastDualHandedForHide = false;
        hideOffhandAfterDualExit = false;
        hideOffhandAfterDualFrames = 0;
        keepEmptyOffhandVisibleForFlight = false;
        flightOffhandLandingHideRequested = false;
        ARM_WORLD_CACHE = new EnumMap(class_1306.class);
        DRY_OUT_ARM_TUNING_BLEND = new EnumMap(class_1306.class);
        ARM_ROTATION_RESET_PROGRESS = new EnumMap(class_1306.class);
        BURN_FADE_STATE = new EnumMap(class_1306.class);
        lastArmCacheTick = -1L;
        FORCE_VANILLA_DISPLAY = ThreadLocal.withInitial(() -> false);
        NEUTRALIZE_USE_TRANSFORMS = ThreadLocal.withInitial(() -> false);
        SPEAR_TAG = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"spears"));
        VANILLA_THIRD_PERSON_CACHE = new HashMap<class_1792, EnumMap<class_811, class_804>>();
        IDLE_USE_TRANSFORM_CACHE = new HashMap<class_1792, EnumMap<class_811, class_804>>();
        BASELINE_USE_TRANSFORM = new ThreadLocal();
    }

    static final class FreezeVoxelMask {
        final List<Pixel> pixels;

        FreezeVoxelMask(List<Pixel> list) {
            this.pixels = list;
        }

        @Override
        public final String toString() {
            return "FreezeVoxelMask[" + "pixels=" + this.pixels + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.pixels);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof FreezeVoxelMask other)) return false;
            return java.util.Objects.deepEquals(this.pixels, other.pixels);
        }

        public List<Pixel> pixels() {
            return this.pixels;
        }

        static final class Pixel {
            final int x;
            final int y;
            final float u0;
            final float v0;
            final float u1;
            final float v1;

            Pixel(int n, int n2, float f, float f2, float f3, float f4) {
                this.x = n;
                this.y = n2;
                this.u0 = f;
                this.v0 = f2;
                this.u1 = f3;
                this.v1 = f4;
            }

            @Override
            public final String toString() {
                return "Pixel[" + "x=" + this.x + ", " + "y=" + this.y + ", " + "u0=" + this.u0 + ", " + "v0=" + this.v0 + ", " + "u1=" + this.u1 + ", " + "v1=" + this.v1 + "]";
            }

            @Override
            public final int hashCode() {
                return java.util.Objects.hash(this.x, this.y, this.u0, this.v0, this.u1, this.v1);
            }

            @Override
            public final boolean equals(Object object) {
                if (this == object) return true;
                if (!(object instanceof Pixel other)) return false;
                return java.util.Objects.deepEquals(this.x, other.x)
                    && java.util.Objects.deepEquals(this.y, other.y)
                    && java.util.Objects.deepEquals(this.u0, other.u0)
                    && java.util.Objects.deepEquals(this.v0, other.v0)
                    && java.util.Objects.deepEquals(this.u1, other.u1)
                    && java.util.Objects.deepEquals(this.v1, other.v1);
            }

            public int x() {
                return this.x;
            }

            public int y() {
                return this.y;
            }

            public float u0() {
                return this.u0;
            }

            public float v0() {
                return this.v0;
            }

            public float u1() {
                return this.u1;
            }

            public float v1() {
                return this.v1;
            }
        }
    }

    static final class BurnFadeState {
        float value = 0.0f;
        float lastTime = -1.0f;

        BurnFadeState() {
        }
    }
}

