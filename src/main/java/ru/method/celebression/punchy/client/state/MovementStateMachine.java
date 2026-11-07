package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.LinkedHashSet;
import java.util.Locale;
import net.minecraft.class_10255;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1747;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1799;
import net.minecraft.class_1835;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3486;
import net.minecraft.class_3532;
import net.minecraft.class_5819;
import net.minecraft.class_746;
import org.joml.Matrix4f;
import ru.method.celebression.punchy.client.PunchyClient;
import ru.method.celebression.punchy.client.animation.Easing;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.render.ToolKindResolver;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.client.state.SpearStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class MovementStateMachine {
    private static final MovementStateMachine INSTANCE;
    public static boolean APPLY_TO_ARMS;
    public static float WALK_SCALE;
    private static final boolean LOG_UNDERWATER_SINK_POSE = true;
    private static final float FORWARD_OFFSET = 1.6f;
    private static final float STRAFE_OFFSET = 1.5f;
    private static final float SMOOTH = 0.3f;
    public static float JUMP_IMPULSE;
    public static float LAND_IMPULSE;
    public static float JUMP_STIFFNESS;
    public static float JUMP_DAMPING;
    public static float JUMP_OFFSET_SCALE;
    public static float JUMP_ROT_SCALE;
    public static float CAMERA_TILT_SMOOTH;
    public static float CAMERA_YAW_TILT_Y_SCALE;
    public static float CAMERA_YAW_TILT_Z_SCALE;
    public static float CAMERA_YAW_ARM_TILT_Y_SCALE;
    public static float CAMERA_YAW_ARM_TILT_MAX;
    public static float CAMERA_YAW_ARM_TILT_STIFFNESS;
    public static float CAMERA_YAW_ARM_TILT_DAMPING;
    private static final float CAMERA_LOOK_REFERENCE_DT = 0.016666668f;
    private static final float CAMERA_LOOK_SPRING_STEP_DT = 0.008333334f;
    private static final float CAMERA_LOOK_MIN_DT = 1.0E-4f;
    public static float CAMERA_PITCH_OFFSET_SCALE;
    public static float CAMERA_PITCH_TILT_X_SCALE;
    public static float SPEAR_CHARGE_IMPACT_SCALE;
    public static float IMPACT_STIFFNESS;
    public static float IMPACT_DAMPING;
    public static float SHIELD_BLOCK_IMPACT_SCALE;
    public static float SHIELD_IMPACT_STIFFNESS;
    public static float SHIELD_IMPACT_DAMPING;
    public static float ACTION_ANIM_IMPULSE_SCALE;
    public static float ACTION_ANIM_STIFFNESS;
    public static float ACTION_ANIM_DAMPING;
    public static float IDLE_INTENSITY;
    public static float IDLE_SPEED;
    private static final float IDLE_BALANCE_INTENSITY_SCALE = 3.3f;
    private static final float IDLE_BALANCE_SPEED_BONUS = 0.0f;
    private static final float IDLE_BALANCE_DECAY = 1.0f;
    private static final float IDLE_BALANCE_RAMP = 0.55f;
    private static final float IDLE_BALANCE_JITTER_SCALE = 0.0078f;
    private static final float IDLE_BALANCE_JITTER_RAMP = 0.35f;
    private static final float IDLE_BALANCE_JITTER_MIN = 0.35f;
    private static final float IDLE_BALANCE_JITTER_MAX = 0.8f;
    private static final float SPRINT_BLEND_IN_TIME = 0.07f;
    private static final float SPRINT_BLEND_OUT_TIME = 0.35f;
    private static final float SPRINT_SPEED_SCALE = 1.6f;
    private static final float SPRINT_PHASE_SPEED = 6.63f;
    private static final float SPRINT_PHASE_SPEED_MAX = 9.69f;
    private static final float SPRINT_PITCH_AMP = 0.135f;
    private static final float SPRINT_PITCH_AMP_2 = 0.06f;
    private static final float SPRINT_YAW_AMP = 0.0375f;
    private static final float SPRINT_YAW_AMP_2 = 0.015f;
    private static final float SPRINT_ROLL_AMP = 0.0525f;
    private static final float SPRINT_ROLL_AMP_2 = 0.0225f;
    private static final float SPRINT_FWD_AMP = 3.6f;
    private static final float SPRINT_FWD_AMP_2 = 1.56f;
    private static final float SPRINT_FWD_PITCH_UP = 0.28f;
    private static final float SPRINT_INWARD_YAW = 0.045f;
    private static final float SPRINT_INWARD_ROLL = 0.03f;
    private static final float SPRINT_INWARD_YAW_STATIC;
    private static final float SPRINT_UP_AMP = 0.12f;
    private static final float SPRINT_TILT_BOOST = 1.28f;
    private static final float WALK_BLEND_IN_TIME = 0.35f;
    private static final float WALK_BLEND_OUT_TIME = 0.6f;
    private static final float WALK_PHASE_SPEED = 5.31f;
    private static final float WALK_PHASE_SPEED_MAX = 8.13f;
    private static final float WALK_PITCH_AMP = 0.085f;
    private static final float WALK_PITCH_AMP_2 = 0.035f;
    private static final float WALK_YAW_AMP = 0.03f;
    private static final float WALK_YAW_AMP_2 = 0.012f;
    private static final float WALK_ROLL_AMP = 0.038f;
    private static final float WALK_ROLL_AMP_2 = 0.016f;
    private static final float WALK_SIDE_AMP = 1.85f;
    private static final float WALK_SIDE_UP_AMP = 0.11f;
    private static final float WALK_UP_AMP = 0.06f;
    private static final float WALK_INWARD_YAW = 0.022f;
    private static final float WALK_INWARD_ROLL = 0.018f;
    private static final float WALK_TILT_BOOST = 1.22f;
    private static final float FALL_ORBIT_BLEND_IN_TIME = 0.16f;
    private static final float FALL_ORBIT_BLEND_OUT_TIME = 0.2f;
    private static final float FALL_ORBIT_PHASE_SPEED = -5.4f;
    private static final float FALL_ORBIT_PHASE_SPEED_MAX = -8.8f;
    private static final float FALL_ORBIT_FWD_AMP = 1.17f;
    private static final float FALL_ORBIT_UP_AMP = 0.68f;
    private static final float FALL_ORBIT_PITCH_AMP = 0.14f;
    private static final float FALL_ORBIT_ROLL_AMP = 0.1f;
    private static final float FLIGHT_POSE_BLEND_IN_TIME = 0.52f;
    private static final float FLIGHT_POSE_BLEND_OUT_TIME = 0.24f;
    private static final float ELYTRA_POSE_SPEED_MIN = 0.55f;
    private static final float ELYTRA_POSE_SPEED_MAX = 1.75f;
    private static final float CREATIVE_POSE_SPEED_MIN = 0.16f;
    private static final float CREATIVE_POSE_SPEED_MAX = 0.85f;
    private static final float FLIGHT_POSE_BACK_HIGH_SCALE = 1.12f;
    private static final float FLIGHT_POSE_INWARD_DEGREES = 35.0f;
    private static final float FLIGHT_POSE_SIDE = 0.0f;
    private static final float FLIGHT_POSE_FORWARD = 5.8f;
    private static final float FLIGHT_POSE_UP = -0.55f;
    private static final float FLIGHT_POSE_LIFT = 0.0f;
    private static final float FLIGHT_POSE_SWAY_SPEED = 1.15f;
    private static final float FLIGHT_POSE_SWAY_SIDE_AMP = 0.12f;
    private static final float FLIGHT_POSE_SWAY_UP_AMP = 0.012f;
    private static final float UNDERWATER_SINK_POSE_BLEND_OUT_TIME = 0.26f;
    private static final float UNDERWATER_SINK_POSE_BLEND_IN_TIME = 2.25f;
    private static final float UNDERWATER_SINK_POSE_DESCEND_MIN = 0.0f;
    private static final float UNDERWATER_SINK_POSE_DESCEND_MAX = 0.08f;
    private static final float UNDERWATER_SINK_POSE_FORWARD = 4.6400003f;
    private static final float UNDERWATER_SINK_POSE_UP = 3.168f;
    private static final float UNDERWATER_SINK_POSE_SIDE = 0.6f;
    private static final float UNDERWATER_SINK_POSE_INWARD_YAW;
    private static final float UNDERWATER_SINK_POSE_PITCH = -0.09f;
    private static final float UNDERWATER_SINK_POSE_ROLL = 0.03f;
    private static final String SYNC_WALK_ARMS_EFFECT;
    private static final float SYNC_WALK_MOTION_SCALE = 0.7f;
    private static final float SYNC_ROOT_MOTION_SCALE = 0.75f;
    private static final float SYNC_WALK_ROTATION_SCALE = 0.38f;
    private static final float SYNC_WALK_SIDE_SCALE = 1.35f;
    private static final float SYNC_WALK_UP_SCALE = 1.12f;
    private float offsetX;
    private float offsetZ;
    private float jumpValue;
    private float jumpVelocity;
    private float impactValue;
    private float impactVelocity;
    private class_1306 impactArm;
    private float shieldImpactValue;
    private float shieldImpactVelocity;
    private class_1306 shieldImpactArm;
    private final float[] actionAnimValue = new float[2];
    private final float[] actionAnimVelocity = new float[2];
    private final float[] actionAnimKick = new float[2];
    private boolean prevOnGround = true;
    private int airborneTicks;
    private float airborneMaxFallDistance;
    private double airborneMinVy = 0.0;
    private boolean airborneJumped;
    private boolean animGrounded;
    private long lastUpdateTime = System.nanoTime();
    private float lastCameraYaw = Float.NaN;
    private float lastCameraPitch = Float.NaN;
    private float cameraTiltY;
    private float cameraTiltZ;
    private float cameraArmTiltY;
    private float cameraArmTiltYVelocity;
    private float cameraPitchOffset;
    private float cameraPitchTiltX;
    private float cameraForwardX;
    private float cameraForwardY;
    private float cameraForwardZ;
    private float cameraRightX;
    private float cameraRightY;
    private float cameraRightZ;
    private float cameraUpX;
    private float cameraUpY;
    private float cameraUpZ;
    private float idleTime;
    private float idleWeight;
    private float idleBoostLeft;
    private float idleBoostRight;
    private float idleBoostLeftTarget;
    private float idleBoostRightTarget;
    private float idleJitterLeft;
    private float idleJitterRight;
    private float idleJitterLeftTarget;
    private float idleJitterRightTarget;
    private float idleJitterLeftTimer;
    private float idleJitterRightTimer;
    private final class_5819 idleJitterRandom = class_5819.method_43047();
    private final SneakSpringState sneakSpring = new SneakSpringState();
    private boolean suppressWalkSway;
    private float sprintBlend;
    private float sprintPhase;
    private float sprintPhaseVelocity;
    private float sprintTarget;
    private final float[] sprintRotX = new float[2];
    private final float[] sprintRotY = new float[2];
    private final float[] sprintRotZ = new float[2];
    private final float[] sprintOffFwd = new float[2];
    private final float[] sprintOffUp = new float[2];
    private final float[] sprintLateralGate = new float[]{1.0f, 1.0f};
    private float sprintSpeedFactor;
    private float sprintDt;
    private float walkBlend;
    private float walkPhase;
    private float walkPhaseVelocity;
    private final float[] walkRotX = new float[2];
    private final float[] walkRotY = new float[2];
    private final float[] walkRotZ = new float[2];
    private final float[] walkOffSide = new float[2];
    private final float[] walkOffUp = new float[2];
    private float walkSpeedFactor;
    private boolean isGrounded;
    private float fallOrbitBlend;
    private float fallOrbitPhase;
    private float fallOrbitPhaseVelocity;
    private float fallOrbitSpeedFactor;
    private float flightPoseBlend;
    private float underwaterSinkPoseBlend;
    private float lastLoggedUnderwaterSinkTarget = Float.NaN;
    private float lastLoggedUnderwaterSinkBlend = Float.NaN;
    private String lastLoggedUnderwaterSinkReason;
    private static final double MICRO_AIR_VEL_EPS = 0.1;
    private static final double VEL_IMPULSE_THRESHOLD = 0.1;
    private static final int MICRO_AIR_TICKS_MAX = 1;
    private static final float LAND_FALLDIST_THRESHOLD = 0.05f;
    private static final float AIR_FREEZE_FALLDIST_THRESHOLD = 3.0f;
    private static final float JUMP_LAND_TRANSITION_COOLDOWN_S = 0.08f;
    private float jumpLandTransitionCooldown;

    public static MovementStateMachine get() {
        return INSTANCE;
    }

    public void triggerSpearImpact(class_1306 class_13062) {
        this.impactArm = class_13062;
        this.impactVelocity += SPEAR_CHARGE_IMPACT_SCALE * 40.0f;
    }

    public void triggerShieldBlockImpact(class_1306 class_13062) {
        this.shieldImpactArm = class_13062;
        this.shieldImpactVelocity += SHIELD_BLOCK_IMPACT_SCALE * 20.0f;
    }

    public void triggerActionAnimation(class_1306 class_13062) {
        if (class_13062 != null) {
            int n = class_13062 == class_1306.field_6182 ? 0 : 1;
            this.actionAnimVelocity[n] = this.actionAnimVelocity[n] + ACTION_ANIM_IMPULSE_SCALE * 20.0f;
            int n2 = n;
            this.actionAnimKick[n2] = this.actionAnimKick[n2] + 1.0f;
        }
    }

    public void tick(class_310 class_3102) {
    }

    public void updateFrame(class_746 class_7462, float f) {
        long l = System.nanoTime();
        float f2 = (float)(l - this.lastUpdateTime) / 1.0E9f;
        this.lastUpdateTime = l;
        if (!(f2 <= 0.0f)) {
            float f3;
            if (f2 > 0.033333335f) {
                f2 = 0.033333335f;
            }
            float f4 = 1.0f - (float)Math.pow(0.7f, f2 * 20.0f);
            float f5 = 1.0f - (float)Math.pow(1.0f - CAMERA_TILT_SMOOTH, f2 * 20.0f);
            this.sprintDt = f2;
            float f6 = 0.0f;
            float f7 = 0.0f;
            if (class_7462 != null) {
                f3 = class_3532.method_15363((float)class_7462.field_6250, (float)-1.0f, (float)1.0f);
                float f8 = class_3532.method_15363((float)class_7462.field_6212, (float)-1.0f, (float)1.0f);
                f6 = f8 * 1.5f;
                f7 = -f3 * 1.6f;
                this.suppressWalkSway = class_7462.method_5854() instanceof class_10255;
            } else {
                this.suppressWalkSway = false;
            }
            this.offsetX = class_3532.method_16439((float)f4, (float)this.offsetX, (float)(f6 *= WALK_SCALE));
            this.offsetZ = class_3532.method_16439((float)f4, (float)this.offsetZ, (float)(f7 *= WALK_SCALE));
            if (class_7462 != null) {
                float f9;
                float f10;
                float f11;
                float f12;
                double d;
                if (this.jumpLandTransitionCooldown > 0.0f) {
                    this.jumpLandTransitionCooldown = Math.max(0.0f, this.jumpLandTransitionCooldown - f2);
                }
                f3 = class_3532.method_15363((float)class_7462.method_36455(), (float)-89.5f, (float)89.5f);
                class_243 class_2432 = class_243.method_1030((float)f3, (float)class_7462.method_36454());
                this.cameraForwardX = (float)class_2432.field_1352;
                this.cameraForwardY = (float)class_2432.field_1351;
                this.cameraForwardZ = (float)class_2432.field_1350;
                class_243 class_2433 = new class_243(0.0, 1.0, 0.0);
                class_243 class_2434 = class_2432.method_1036(class_2433);
                class_2434 = class_2434.method_1027() < 1.0E-6 ? new class_243(1.0, 0.0, 0.0) : class_2434.method_1029();
                class_243 class_2435 = class_2434.method_1036(class_2432);
                class_2435 = class_2435.method_1027() < 1.0E-6 ? class_2433 : class_2435.method_1029();
                this.cameraRightX = (float)class_2434.field_1352;
                this.cameraRightY = (float)class_2434.field_1351;
                this.cameraRightZ = (float)class_2434.field_1350;
                this.cameraUpX = (float)class_2435.field_1352;
                this.cameraUpY = (float)class_2435.field_1351;
                this.cameraUpZ = (float)class_2435.field_1350;
                boolean bl = MovementStateMachine.isStandingOnSlime(class_7462);
                boolean bl2 = class_7462.method_24828();
                if (!bl2 && bl && Math.abs(d = class_7462.method_18798().field_1351) <= 0.1 && (double)class_7462.field_6017 <= 0.0) {
                    bl2 = true;
                }
                this.isGrounded = bl2;
                if (this.prevOnGround && !bl2) {
                    this.airborneTicks = 0;
                    this.airborneMaxFallDistance = 0.0f;
                    this.airborneMinVy = class_7462.method_18798().field_1351;
                    boolean bl3 = class_310.method_1551().field_1690.field_1903.method_1434();
                    this.airborneJumped = bl3 || class_7462.method_18798().field_1351 > 0.1;
                    float jumpImpulse = bl3 ? JUMP_IMPULSE : JUMP_IMPULSE * 0.5f;
                    if ((!bl || bl3) && this.jumpLandTransitionCooldown <= 0.0f && (bl3 || class_7462.method_18798().field_1351 > 0.1)) {
                        this.jumpVelocity += jumpImpulse;
                        this.jumpLandTransitionCooldown = 0.08f;
                    }
                } else if (!this.prevOnGround && bl2) {
                    boolean bl4;
                    boolean bl5 = bl4 = this.airborneTicks > 1 || this.airborneMaxFallDistance > 0.05f || this.airborneMinVy < -0.1;
                    if (bl4 && this.jumpLandTransitionCooldown <= 0.0f) {
                        float landImpulse = LAND_IMPULSE;
                        if (bl) {
                            f12 = class_3532.method_15363((float)(this.airborneMaxFallDistance / 1.0f), (float)0.0f, (float)1.0f);
                            landImpulse *= 0.35f + 0.65f * f12;
                        }
                        this.jumpVelocity += landImpulse;
                        this.jumpLandTransitionCooldown = 0.08f;
                    }
                    this.airborneTicks = 0;
                    this.airborneMaxFallDistance = 0.0f;
                    this.airborneMinVy = 0.0;
                    this.airborneJumped = false;
                }
                this.prevOnGround = bl2;
                if (!bl2) {
                    ++this.airborneTicks;
                    this.airborneMaxFallDistance = Math.max(this.airborneMaxFallDistance, class_7462.field_6017);
                    this.airborneMinVy = Math.min(this.airborneMinVy, class_7462.method_18798().field_1351);
                }
                boolean bl6 = !this.isGrounded && !this.airborneJumped && this.airborneMaxFallDistance < 3.0f;
                this.animGrounded = this.isGrounded || bl6;
                boolean bl7 = this.isSubmergedWithoutGround(class_7462);
                f12 = 0.0f;
                if (!(!PunchyConfig.enableFallArmAnimation() || bl2 || bl7 || class_7462.method_6128() || this.suppressWalkSway)) {
                    f11 = (float)Math.max(0.0, -class_7462.method_18798().field_1351);
                    f12 = class_3532.method_15363((float)((f11 - 0.04f) / 0.85f), (float)0.0f, (float)1.0f);
                    if ((double)class_7462.field_6017 > (double)0.15f) {
                        f12 = Math.max(f12, (float)class_3532.method_15350((double)((double)class_7462.field_6017 / 3.0), (double)0.0, (double)1.0));
                    }
                }
                f11 = f12 > this.fallOrbitBlend ? 0.16f : 0.2f;
                float f14 = f11 <= 0.0f ? 1.0f : f2 / f11;
                this.fallOrbitBlend = class_3532.method_15363((float)(this.fallOrbitBlend + (f12 - this.fallOrbitBlend) * f14), (float)0.0f, (float)1.0f);
                this.fallOrbitSpeedFactor = class_3532.method_16439((float)f14, (float)this.fallOrbitSpeedFactor, (float)f12);
                if (this.fallOrbitBlend > 0.001f) {
                    f10 = class_3532.method_16439((float)this.fallOrbitSpeedFactor, (float)-5.4f, (float)-8.8f);
                    f9 = 1.0f - (float)Math.pow(0.74f, f2 * 20.0f);
                    this.fallOrbitPhaseVelocity = class_3532.method_16439((float)f9, (float)this.fallOrbitPhaseVelocity, (float)f10);
                    this.fallOrbitPhase += this.fallOrbitPhaseVelocity * f2;
                } else {
                    this.fallOrbitPhaseVelocity = 0.0f;
                }
                f10 = this.resolveFlightPoseTargetBlend(class_7462);
                f9 = f10 > this.flightPoseBlend ? 0.52f : 0.24f;
                float f15 = f9 <= 0.0f ? 1.0f : f2 / f9;
                this.flightPoseBlend = class_3532.method_15363((float)(this.flightPoseBlend + (f10 - this.flightPoseBlend) * f15), (float)0.0f, (float)1.0f);
                float f16 = this.resolveUnderwaterSinkPoseTargetBlend(class_7462);
                float f17 = f16 > this.underwaterSinkPoseBlend ? 2.25f : 0.26f;
                float f18 = f17 <= 0.0f ? 1.0f : f2 / f17;
                this.underwaterSinkPoseBlend = MovementStateMachine.approach(this.underwaterSinkPoseBlend, f16, f18);
                this.debugUnderwaterSinkPoseBlend(class_7462, f16);
                float f19 = class_7462.method_36454();
                if (Float.isNaN(this.lastCameraYaw)) {
                    this.lastCameraYaw = f19;
                }
                float f20 = MovementStateMachine.normalizeLookDelta(class_3532.method_15393((float)(f19 - this.lastCameraYaw)), f2);
                this.lastCameraYaw = f19;
                float f21 = -f20 * CAMERA_YAW_TILT_Y_SCALE;
                this.cameraTiltY = class_3532.method_16439((float)f5, (float)this.cameraTiltY, (float)f21);
                float f22 = f20 * CAMERA_YAW_TILT_Z_SCALE;
                this.cameraTiltZ = class_3532.method_16439((float)f5, (float)this.cameraTiltZ, (float)f22);
                float f23 = class_3532.method_15363((float)(-f20 * CAMERA_YAW_ARM_TILT_Y_SCALE), (float)(-CAMERA_YAW_ARM_TILT_MAX), (float)CAMERA_YAW_ARM_TILT_MAX);
                this.stepCameraArmTiltSpring(f23, f2);
                float f24 = class_7462.method_36455();
                if (Float.isNaN(this.lastCameraPitch)) {
                    this.lastCameraPitch = f24;
                }
                float f25 = MovementStateMachine.normalizeLookDelta(f24 - this.lastCameraPitch, f2);
                this.lastCameraPitch = f24;
                float f26 = -f25 * CAMERA_PITCH_TILT_X_SCALE;
                this.cameraPitchTiltX = class_3532.method_16439((float)f5, (float)this.cameraPitchTiltX, (float)f26);
                float f27 = f24 * CAMERA_PITCH_OFFSET_SCALE;
                this.cameraPitchOffset = class_3532.method_16439((float)f5, (float)this.cameraPitchOffset, (float)f27);
                float f28 = class_3532.method_15355((float)(f7 * f7 + f6 * f6));
                float f29 = 1.0f - class_3532.method_15363((float)(f28 / 0.2f), (float)0.0f, (float)1.0f);
                this.idleWeight = class_3532.method_16439((float)f5, (float)this.idleWeight, (float)f29);
                boolean bl8 = class_7462.method_18276() || class_7462.method_5715();
                this.sneakSpring.setSneaking(bl8);
                if (this.animGrounded && !bl7) {
                    float f30;
                    float f31;
                    float f32;
                    float f33;
                    boolean bl9 = this.shouldApplySprintSwing(class_7462);
                    float f34 = class_7462.field_42108.method_48566();
                    this.sprintTarget = f33 = bl9 ? 1.0f : 0.0f;
                    float f35 = f33 > this.sprintBlend ? 0.07f : 0.35f;
                    float f36 = f32 = f35 <= 0.0f ? 1.0f : f2 / f35;
                    if (f33 < this.sprintBlend) {
                        f32 *= f32;
                    }
                    this.sprintBlend = class_3532.method_15363((float)(this.sprintBlend + (f33 - this.sprintBlend) * f32), (float)0.0f, (float)1.0f);
                    float f37 = bl9 ? class_3532.method_15363((float)(f34 * 1.6f), (float)0.0f, (float)1.0f) : 0.0f;
                    float f38 = f35 <= 0.0f ? 1.0f : f2 / f35;
                    this.sprintSpeedFactor = class_3532.method_16439((float)f38, (float)this.sprintSpeedFactor, (float)f37);
                    float f39 = class_3532.method_16439((float)this.sprintSpeedFactor, (float)6.63f, (float)9.69f);
                    float f40 = PunchyConfig.getSprintArmSwingSpeed();
                    float f41 = 1.0f + f40;
                    if (f41 < 0.0f) {
                        f41 = 0.0f;
                    }
                    float f42 = 1.0f - (float)Math.pow(0.75, f2 * 20.0f);
                    this.sprintPhaseVelocity = class_3532.method_16439((float)f42, (float)this.sprintPhaseVelocity, (float)(f39 *= f41));
                    this.sprintPhase += this.sprintPhaseVelocity * f2;
                    float f43 = class_3532.method_15355((float)(class_7462.field_6250 * class_7462.field_6250 + class_7462.field_6212 * class_7462.field_6212));
                    float f44 = class_3532.method_15363((float)Math.max(f43, f31 = class_7462.field_42108.method_48566()), (float)0.0f, (float)1.0f);
                    boolean bl10 = this.shouldApplyWalkSwing(class_7462, f44);
                    float f45 = bl10 ? 1.0f : 0.0f;
                    float f46 = f45 > this.walkBlend ? 0.35f : 0.6f;
                    float f47 = f30 = f46 <= 0.0f ? 1.0f : f2 / f46;
                    if (f45 < this.walkBlend) {
                        f30 *= f30;
                    }
                    this.walkBlend = class_3532.method_15363((float)(this.walkBlend + (f45 - this.walkBlend) * f30), (float)0.0f, (float)1.0f);
                    float f48 = f46 <= 0.0f ? 1.0f : f2 / f46;
                    float f49 = bl10 ? f44 : 0.0f;
                    this.walkSpeedFactor = class_3532.method_16439((float)f48, (float)this.walkSpeedFactor, (float)f49);
                    float f50 = class_3532.method_16439((float)this.walkSpeedFactor, (float)5.31f, (float)8.13f);
                    float f51 = 1.0f - (float)Math.pow(0.75, f2 * 20.0f);
                    this.walkPhaseVelocity = class_3532.method_16439((float)f51, (float)this.walkPhaseVelocity, (float)f50);
                    this.walkPhase += this.walkPhaseVelocity * f2;
                    if (this.walkBlend <= 0.001f && !bl10) {
                        this.walkBlend = 0.0f;
                        this.walkPhaseVelocity = 0.0f;
                        this.walkRotX[0] = 0.0f;
                        this.walkRotX[1] = 0.0f;
                        this.walkRotY[0] = 0.0f;
                        this.walkRotY[1] = 0.0f;
                        this.walkRotZ[0] = 0.0f;
                        this.walkRotZ[1] = 0.0f;
                        this.walkOffSide[0] = 0.0f;
                        this.walkOffSide[1] = 0.0f;
                        this.walkOffUp[0] = 0.0f;
                        this.walkOffUp[1] = 0.0f;
                    }
                } else if (bl7) {
                    float f52 = class_3532.method_15363((float)(f2 / 0.22f), (float)0.0f, (float)1.0f);
                    this.sprintTarget = 0.0f;
                    this.sprintBlend = class_3532.method_16439((float)f52, (float)this.sprintBlend, (float)0.0f);
                    this.sprintSpeedFactor = class_3532.method_16439((float)f52, (float)this.sprintSpeedFactor, (float)0.0f);
                    this.walkBlend = class_3532.method_16439((float)f52, (float)this.walkBlend, (float)0.0f);
                    this.walkSpeedFactor = class_3532.method_16439((float)f52, (float)this.walkSpeedFactor, (float)0.0f);
                    if (this.sprintBlend <= 0.001f) {
                        this.sprintBlend = 0.0f;
                        this.sprintPhaseVelocity = 0.0f;
                    }
                    if (this.walkBlend <= 0.001f) {
                        this.walkBlend = 0.0f;
                        this.walkPhaseVelocity = 0.0f;
                    }
                }
            } else {
                this.underwaterSinkPoseBlend = 0.0f;
            }
            this.idleTime += f2;
            if (this.idleBoostLeftTarget > 0.0f) {
                this.idleBoostLeftTarget = Math.max(0.0f, this.idleBoostLeftTarget - f2 * 1.0f);
            }
            if (this.idleBoostRightTarget > 0.0f) {
                this.idleBoostRightTarget = Math.max(0.0f, this.idleBoostRightTarget - f2 * 1.0f);
            }
            f3 = 1.0f - (float)Math.pow(0.45f, f2 * 20.0f);
            this.idleBoostLeft = class_3532.method_16439((float)f3, (float)this.idleBoostLeft, (float)this.idleBoostLeftTarget);
            this.idleBoostRight = class_3532.method_16439((float)f3, (float)this.idleBoostRight, (float)this.idleBoostRightTarget);
            this.updateIdleJitter(f2);
            float f53 = -JUMP_STIFFNESS * this.jumpValue - JUMP_DAMPING * this.jumpVelocity;
            this.jumpVelocity += f53 * f2;
            this.jumpValue += this.jumpVelocity * f2;
            if (Math.abs(this.jumpValue) < 1.0E-4f && Math.abs(this.jumpVelocity) < 1.0E-4f) {
                this.jumpValue = 0.0f;
                this.jumpVelocity = 0.0f;
            }
            float f54 = -IMPACT_STIFFNESS * this.impactValue - IMPACT_DAMPING * this.impactVelocity;
            this.impactVelocity += f54 * f2;
            this.impactValue += this.impactVelocity * f2;
            if (Math.abs(this.impactValue) < 1.0E-4f && Math.abs(this.impactVelocity) < 1.0E-4f) {
                this.impactValue = 0.0f;
                this.impactVelocity = 0.0f;
                this.impactArm = null;
            }
            float f55 = -SHIELD_IMPACT_STIFFNESS * this.shieldImpactValue - SHIELD_IMPACT_DAMPING * this.shieldImpactVelocity;
            this.shieldImpactVelocity += f55 * f2;
            this.shieldImpactValue += this.shieldImpactVelocity * f2;
            if (Math.abs(this.shieldImpactValue) < 1.0E-4f && Math.abs(this.shieldImpactVelocity) < 1.0E-4f) {
                this.shieldImpactValue = 0.0f;
                this.shieldImpactVelocity = 0.0f;
                this.shieldImpactArm = null;
            }
            for (int i = 0; i < 2; ++i) {
                float f56 = -ACTION_ANIM_STIFFNESS * this.actionAnimValue[i] - ACTION_ANIM_DAMPING * this.actionAnimVelocity[i];
                this.actionAnimVelocity[i] = this.actionAnimVelocity[i] + f56 * f2;
                this.actionAnimValue[i] = this.actionAnimValue[i] + this.actionAnimVelocity[i] * f2;
                if (!(Math.abs(this.actionAnimValue[i]) < 1.0E-4f) || !(Math.abs(this.actionAnimVelocity[i]) < 1.0E-4f)) continue;
                this.actionAnimValue[i] = 0.0f;
                this.actionAnimVelocity[i] = 0.0f;
            }
            this.sneakSpring.tick(f2);
        }
    }

    public boolean isSprintSwingActive() {
        return this.sprintTarget > 0.0f || this.sprintBlend > 0.001f;
    }

    public boolean isWalkSwingActive() {
        return this.walkBlend > 0.001f;
    }

    public float getSprintBlendAlpha() {
        return MovementStateMachine.smoothStep(this.sprintBlend) * MovementStateMachine.smoothStep(this.sprintSpeedFactor);
    }

    public void applyToMatrix(Matrix4f matrix4f, float f, class_1306 class_13062) {
        if (matrix4f != null && !this.suppressWalkSway) {
            float f2;
            SwingTransform swingTransform;
            SwingTransform swingTransform2;
            SwingTransform swingTransform3;
            boolean bl = this.isWalkArmSyncActive();
            float f3 = this.getWalkAndSprintForceScale();
            float f4 = this.getRemainingProceduralForceScale();
            float f5 = (bl ? 0.75f : 1.0f) * f3;
            boolean bl2 = false;
            if (!(bl2 || this.offsetX == 0.0f && this.offsetZ == 0.0f)) {
                if (bl) {
                    matrix4f.translateLocal(this.cameraRightX * (this.offsetX * f5) + this.cameraForwardX * (this.offsetZ * f5), this.cameraRightY * (this.offsetX * f5) + this.cameraForwardY * (this.offsetZ * f5), this.cameraRightZ * (this.offsetX * f5) + this.cameraForwardZ * (this.offsetZ * f5));
                } else {
                    matrix4f.translate(this.offsetX * f5, this.offsetZ * f5, 0.0f);
                }
            }
            if ((swingTransform3 = SwingTransform.blend(swingTransform2 = this.computeWalkSwing(class_13062), swingTransform = this.computeSprintSwing(class_13062), f2 = this.resolveSprintAlpha(class_13062, f))) != null && class_13062 != null) {
                int n = class_13062 == class_1306.field_6182 ? 0 : 1;
                float f6 = this.sprintDt > 0.0f ? this.sprintDt : 0.05f;
                float f7 = 1.0f - (float)Math.pow(0.72f, f6 * 20.0f);
                float f8 = MovementStateMachine.isSprintLateralTiltEnabled(class_13062) ? 1.0f : 0.0f;
                this.sprintLateralGate[n] = class_3532.method_16439((float)f7, (float)this.sprintLateralGate[n], (float)f8);
                float f9 = class_3532.method_16439((float)f2, (float)1.0f, (float)this.sprintLateralGate[n]);
                swingTransform3 = new SwingTransform(swingTransform3.rotX, swingTransform3.rotY * f9, swingTransform3.rotZ * f9, swingTransform3.offX * f9, swingTransform3.offY, swingTransform3.offZ);
            }
            if (swingTransform3 != null) {
                float f10 = (bl ? 0.7f : 1.0f) * f3;
                swingTransform3 = swingTransform3.scaled(f10);
            }
            if (swingTransform3 != null && swingTransform3.hasMotion()) {
                if (bl) {
                    this.applySwingRootSpace(matrix4f, swingTransform3);
                } else {
                    matrix4f.rotateX(swingTransform3.rotX);
                    matrix4f.rotateY(swingTransform3.rotY);
                    matrix4f.rotateZ(swingTransform3.rotZ);
                    matrix4f.translate(swingTransform3.offX, swingTransform3.offY, swingTransform3.offZ);
                }
            }
            this.applyFallOrbitMotion(matrix4f, class_13062, bl, f4);
            this.applyFlightPoseMotion(matrix4f, class_13062);
            this.applyUnderwaterSinkPoseMotion(matrix4f, class_13062);
        }
    }

    private void applyFallOrbitMotion(Matrix4f matrix4f, class_1306 class_13062, boolean bl, float f) {
        if (matrix4f != null && class_13062 != null && !(this.fallOrbitBlend <= 0.001f)) {
            boolean bl2;
            boolean bl3 = bl2 = class_13062 != class_1306.field_6182;
            float f2 = bl ? 1.0f : (!bl2 ? 1.0f : -1.0f);
            float f4 = this.fallOrbitPhase + (bl ? 0.0f : (f2 < 0.0f ? (float)Math.PI : 0.0f));
            float f5 = MovementStateMachine.smoothStep(this.fallOrbitBlend) * f;
            float f6 = class_3532.method_15374((float)f4) * 1.17f * f5;
            float f7 = class_3532.method_15362((float)f4) * 0.68f * f5;
            float f8 = class_3532.method_15374((float)(f4 + 0.35f)) * 0.14f * f5;
            float f9 = class_3532.method_15362((float)(f4 + 0.12f)) * 0.1f * f5 * f2;
            if (bl) {
                matrix4f.rotate(f8 *= 0.65f, this.cameraRightX, this.cameraRightY, this.cameraRightZ);
                matrix4f.rotate(f9 *= 0.65f, this.cameraForwardX, this.cameraForwardY, this.cameraForwardZ);
                matrix4f.translateLocal(this.cameraForwardX * f6 + this.cameraUpX * (f7 *= 0.75f), this.cameraForwardY * (f6 *= 0.75f) + this.cameraUpY * f7, this.cameraForwardZ * f6 + this.cameraUpZ * f7);
            } else {
                matrix4f.rotateX(f8);
                matrix4f.rotateZ(f9);
                matrix4f.translate(0.0f, f6, f7);
            }
        }
    }

    private void applyFlightPoseMotion(Matrix4f matrix4f, class_1306 class_13062) {
        if (matrix4f != null && class_13062 != null && !(this.flightPoseBlend <= 0.001f)) {
            float f = class_3532.method_15363((float)(this.flightPoseBlend * 1.12f), (float)0.0f, (float)1.0f);
            float f2 = class_3532.method_15363((float)Easing.easeOutBack(f), (float)0.0f, (float)1.0f);
            class_746 class_7462 = class_310.method_1551().field_1724;
            class_243 class_2432 = class_7462 == null ? null : class_7462.method_18798();
            float f3 = class_2432 == null ? 0.0f : (float)class_2432.method_1033();
            float f4 = class_7462 != null && class_7462.method_6128() ? class_3532.method_15363((float)((f3 - 0.55f) / 1.2f), (float)0.0f, (float)1.0f) : class_3532.method_15363((float)((f3 - 0.16f) / 0.69000006f), (float)0.0f, (float)1.0f);
            float f5 = MovementStateMachine.smoothStep(f4);
            float f6 = f2 * f5 * f5 * f5;
            float f7 = class_7462 == null ? 0.0f : (float)class_7462.field_6012 * 1.15f;
            float f8 = class_13062 == class_1306.field_6182 ? -1.0f : 1.0f;
            float f9 = (float)Math.toRadians(35.0) * f2;
            if (class_13062 == class_1306.field_6182) {
                matrix4f.rotateY(f9);
            } else {
                matrix4f.rotateY(-f9);
            }
            float f10 = (0.0f + class_3532.method_15374((float)f7) * 0.12f * f8) * f6;
            float f11 = 5.8f * f2;
            float f12 = -0.55f * f2 + class_3532.method_15374((float)(f7 * 0.25f + 0.6f)) * 0.012f * f6;
            float f13 = 0.0f * f2;
            matrix4f.translate(f10, f11 + f13, f12);
        }
    }

    private void applyUnderwaterSinkPoseMotion(Matrix4f matrix4f, class_1306 class_13062) {
        if (matrix4f != null && class_13062 != null && !(this.underwaterSinkPoseBlend <= 0.001f)) {
            float f = this.underwaterSinkPoseBlend;
            float f2 = class_13062 == class_1306.field_6182 ? -1.0f : 1.0f;
            float f3 = UNDERWATER_SINK_POSE_INWARD_YAW * f;
            if (class_13062 == class_1306.field_6182) {
                matrix4f.rotateY(f3);
            } else {
                matrix4f.rotateY(-f3);
            }
            matrix4f.rotateX(-0.09f * f);
            matrix4f.rotateZ(0.03f * f * f2);
            matrix4f.translate(-0.6f * f * f2, 4.6400003f * f, -3.168f * f);
            this.debugUnderwaterSinkPoseApply(class_13062, f);
        }
    }

    private float resolveFlightPoseTargetBlend(class_746 class_7462) {
        if (class_7462 == null || this.suppressWalkSway) {
            return 0.0f;
        }
        if (!(class_7462.method_24828() || class_7462.method_5681() || class_7462.method_5799())) {
            if (PunchyAnimationManager.SWIM_HANDLER.isPlaying() && PunchyAnimationManager.SWIM_HANDLER.getCurrentClip() == PunchyAnimationManager.SWIMING_CLIP) {
                return 0.0f;
            }
            if (!PunchyConfig.enableElytraFlightAnimation()) {
                return 0.0f;
            }
            class_243 class_2432 = class_7462.method_18798();
            float f = class_2432 == null ? 0.0f : (float)class_2432.method_1033();
            return class_7462.method_6128() ? class_3532.method_15363((float)((f - 0.55f) / 1.2f), (float)0.0f, (float)1.0f) : 0.0f;
        }
        return 0.0f;
    }

    private float resolveUnderwaterSinkPoseTargetBlend(class_746 class_7462) {
        class_310 class_3102 = class_310.method_1551();
        if (class_7462 != null && class_3102 != null && class_3102.field_1690 != null && !this.suppressWalkSway) {
            boolean bl;
            boolean bl2;
            boolean bl3 = bl2 = class_7462.method_5869() || class_7462.method_5777(class_3486.field_15517);
            if (!bl2) {
                this.debugUnderwaterSinkPoseState("not_submerged", class_7462, 0.0f, false, false, 0.0f);
                return 0.0f;
            }
            if (class_7462.method_5681() || class_7462.method_31549().field_7479 || class_7462.method_6128()) {
                this.debugUnderwaterSinkPoseState("swimming_or_flying", class_7462, 0.0f, bl2, false, 0.0f);
                return 0.0f;
            }
            if (class_3102.field_1690.field_1903.method_1434()) {
                this.debugUnderwaterSinkPoseState("jump_pressed", class_7462, 0.0f, bl2, false, 0.0f);
                return 0.0f;
            }
            if (this.hasSolidSupportAtFeet(class_7462)) {
                this.debugUnderwaterSinkPoseState("solid_support", class_7462, 0.0f, bl2, false, 0.0f);
                return 0.0f;
            }
            boolean bl4 = bl = Math.abs(class_7462.field_6250) > 0.05f || Math.abs(class_7462.field_6212) > 0.05f || class_3102.field_1690.field_1894.method_1434() || class_3102.field_1690.field_1881.method_1434() || class_3102.field_1690.field_1913.method_1434() || class_3102.field_1690.field_1849.method_1434();
            if (bl) {
                this.debugUnderwaterSinkPoseState("wasd_active", class_7462, 0.0f, bl2, false, 0.0f);
                return 0.0f;
            }
            class_243 class_2432 = class_7462.method_18798();
            float f = class_2432 == null ? 0.0f : (float)Math.max(0.0, -class_2432.field_1351);
            float f2 = class_3532.method_15363((float)((f - 0.0f) / 0.08f), (float)0.0f, (float)1.0f);
            float f3 = Math.max(1.0f, f2);
            this.debugUnderwaterSinkPoseState("active_idle_sink", class_7462, f3, bl2, false, f);
            return f3;
        }
        this.debugUnderwaterSinkPoseState("invalid_context", class_7462, 0.0f, false, false, 0.0f);
        return 0.0f;
    }

    private boolean hasSolidSupportAtFeet(class_746 class_7462) {
        if (class_7462 != null && class_7462.method_37908() != null) {
            class_238 class_2383 = class_7462.method_5829();
            double d = class_2383.field_1322 - 0.05;
            double d2 = Math.min(0.2, class_2383.method_17939() * 0.25);
            double d3 = Math.min(0.2, class_2383.method_17941() * 0.25);
            double[] dArray = new double[]{class_2383.field_1323 + d2, class_2383.field_1320 - d2, (class_2383.field_1323 + class_2383.field_1320) * 0.5};
            double[] dArray2 = new double[]{class_2383.field_1321 + d3, class_2383.field_1324 - d3, (class_2383.field_1321 + class_2383.field_1324) * 0.5};
            for (double d4 : dArray) {
                for (double d5 : dArray2) {
                    class_2338 class_23382 = class_2338.method_49637((double)d4, (double)d, (double)d5);
                    if (!class_7462.method_37908().method_8320(class_23382).method_51366()) continue;
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean isFlightPoseActive() {
        return this.flightPoseBlend > 0.001f;
    }

    public float getFlightPoseBlend() {
        return this.flightPoseBlend;
    }

    public boolean isUnderwaterSinkPoseActive() {
        return this.underwaterSinkPoseBlend > 0.001f;
    }

    public float getUnderwaterSinkPoseBlend() {
        return this.underwaterSinkPoseBlend;
    }

    private void debugUnderwaterSinkPoseState(String string, class_746 class_7462, float f, boolean bl, boolean bl2, float f2) {
        if (!MovementStateMachine.safeEquals(this.lastLoggedUnderwaterSinkReason, string) || !(Math.abs(this.lastLoggedUnderwaterSinkTarget - f) < 0.01f)) {
            this.lastLoggedUnderwaterSinkReason = string;
            this.lastLoggedUnderwaterSinkTarget = f;
        }
    }

    private void debugUnderwaterSinkPoseBlend(class_746 class_7462, float f) {
        if (!(Math.abs(this.lastLoggedUnderwaterSinkBlend - this.underwaterSinkPoseBlend) < 0.02f) || !(Math.abs(this.lastLoggedUnderwaterSinkTarget - f) < 0.01f)) {
            this.lastLoggedUnderwaterSinkBlend = this.underwaterSinkPoseBlend;
        }
    }

    private void debugUnderwaterSinkPoseApply(class_1306 class_13062, float f) {
    }

    private static boolean safeEquals(String string, String string2) {
        return string == null ? string2 == null : string.equals(string2);
    }

    private static float approach(float f, float f2, float f3) {
        float f4 = f2 - f;
        return Math.abs(f4) <= f3 ? f2 : f + Math.copySign(f3, f4);
    }

    private static String clipName(AnimationClip animationClip) {
        return animationClip == null ? "null" : animationClip.getName();
    }

    private void applySwingRootSpace(Matrix4f matrix4f, SwingTransform swingTransform) {
        if (matrix4f != null && swingTransform != null) {
            if (swingTransform.rotX != 0.0f) {
                matrix4f.rotate(swingTransform.rotX, this.cameraRightX, this.cameraRightY, this.cameraRightZ);
            }
            if (swingTransform.rotY != 0.0f) {
                matrix4f.rotate(swingTransform.rotY, this.cameraUpX, this.cameraUpY, this.cameraUpZ);
            }
            if (swingTransform.rotZ != 0.0f) {
                matrix4f.rotate(swingTransform.rotZ, this.cameraForwardX, this.cameraForwardY, this.cameraForwardZ);
            }
            if (swingTransform.offX != 0.0f || swingTransform.offY != 0.0f || swingTransform.offZ != 0.0f) {
                matrix4f.translateLocal(this.cameraRightX * swingTransform.offX + this.cameraForwardX * swingTransform.offY + this.cameraUpX * swingTransform.offZ, this.cameraRightY * swingTransform.offX + this.cameraForwardY * swingTransform.offY + this.cameraUpY * swingTransform.offZ, this.cameraRightZ * swingTransform.offX + this.cameraForwardZ * swingTransform.offY + this.cameraUpZ * swingTransform.offZ);
            }
        }
    }

    public void applyRootSpaceMotion(Matrix4f matrix4f, float f, class_1306 class_13062) {
        if (matrix4f != null && !this.suppressWalkSway) {
            float f2;
            float f3;
            float f4;
            boolean bl = this.isWalkArmSyncActive();
            float f5 = this.getRemainingProceduralForceScale();
            float f6 = (bl ? 0.75f : 1.0f) * f5;
            if (this.jumpValue != 0.0f) {
                f4 = this.jumpValue * JUMP_OFFSET_SCALE * f6;
                matrix4f.translateLocal(this.cameraUpX * f4, this.cameraUpY * f4, this.cameraUpZ * f4);
                matrix4f.rotateX(this.jumpValue * JUMP_ROT_SCALE * f6);
            }
            if ((f4 = this.sneakSpring.offset()) != 0.0f) {
                f3 = f4 * JUMP_OFFSET_SCALE * f6;
                matrix4f.translateLocal(this.cameraUpX * f3, this.cameraUpY * f3, this.cameraUpZ * f3);
                matrix4f.rotateX(f4 * JUMP_ROT_SCALE * f6);
            }
            if (this.impactValue != 0.0f && (bl || class_13062 == this.impactArm)) {
                f3 = -this.impactValue * 2.5f * f6;
                f2 = this.impactValue * 0.5f * f6;
                matrix4f.translateLocal(this.cameraForwardX * f3 + this.cameraUpX * f2, this.cameraForwardY * f3 + this.cameraUpY * f2, this.cameraForwardZ * f3 + this.cameraUpZ * f2);
                matrix4f.rotateX(-this.impactValue * 0.6f * f6);
            }
            if (!this.suppressWalkSway && this.idleWeight > 0.0f && IDLE_INTENSITY != 0.0f) {
                float f7;
                f3 = bl ? (this.idleBoostLeft + this.idleBoostRight) * 0.5f : this.getIdleBoost(class_13062);
                f2 = IDLE_SPEED * (1.0f + f3 * 0.0f);
                float f8 = this.idleTime * Math.max(0.01f, f2);
                float f9 = IDLE_INTENSITY * this.idleWeight * (1.0f + f3 * 3.3f) * f6;
                float f10 = class_3532.method_15374((float)(f8 * 1.35f)) * 0.015f * f9;
                float f11 = class_3532.method_15374((float)(f8 * 0.9f + 1.2f)) * 0.02f * f9;
                float f12 = class_3532.method_15374((float)(f8 * 0.75f + 0.5f)) * 0.012f * f9;
                matrix4f.translateLocal(this.cameraForwardX * f11 + this.cameraUpX * f10, this.cameraForwardY * f11 + this.cameraUpY * f10, this.cameraForwardZ * f11 + this.cameraUpZ * f10);
                matrix4f.rotateX(f12);
                float f13 = f7 = bl ? (this.idleJitterLeft + this.idleJitterRight) * 0.5f : this.getIdleJitter(class_13062);
                if (f7 != 0.0f && f3 > 0.0f) {
                    float f14 = f7 * 0.0078f * f3 * f5;
                    matrix4f.translateLocal(this.cameraRightX * f14, this.cameraRightY * f14, this.cameraRightZ * f14);
                }
            }
        }
    }

    public void applyCameraLookToRoot(Matrix4f matrix4f) {
        if (matrix4f != null && !this.suppressWalkSway) {
            float f = this.getRemainingProceduralForceScale();
            if (this.cameraTiltY != 0.0f) {
                matrix4f.rotate(this.cameraTiltY * f, this.cameraUpX, this.cameraUpY, this.cameraUpZ);
            }
            if (this.cameraTiltZ != 0.0f) {
                matrix4f.rotate(this.cameraTiltZ * f, this.cameraForwardX, this.cameraForwardY, this.cameraForwardZ);
            }
            if (this.cameraPitchTiltX != 0.0f) {
                matrix4f.rotate(this.cameraPitchTiltX * f, this.cameraRightX, this.cameraRightY, this.cameraRightZ);
            }
            if (this.cameraPitchOffset != 0.0f) {
                matrix4f.translate(this.cameraUpX * this.cameraPitchOffset * f, this.cameraUpY * this.cameraPitchOffset * f, this.cameraUpZ * this.cameraPitchOffset * f);
            }
        }
    }

    public void applyCameraYawLagToArm(Matrix4f matrix4f) {
        if (matrix4f != null && !PunchyConfig.disableArmPhysics() && this.cameraArmTiltY != 0.0f) {
            float f = this.getRemainingProceduralForceScale();
            matrix4f.rotateY(this.cameraArmTiltY * f);
        }
    }

    private static float normalizeLookDelta(float f, float f2) {
        return f * (0.016666668f / Math.max(f2, 1.0E-4f));
    }

    private void stepCameraArmTiltSpring(float f, float f2) {
        float f3;
        for (float f4 = Math.max(f2, 0.0f); f4 > 0.0f; f4 -= f3) {
            f3 = Math.min(f4, 0.008333334f);
            float f5 = CAMERA_YAW_ARM_TILT_STIFFNESS * (f - this.cameraArmTiltY) - CAMERA_YAW_ARM_TILT_DAMPING * this.cameraArmTiltYVelocity;
            this.cameraArmTiltYVelocity += f5 * f3;
            this.cameraArmTiltY += this.cameraArmTiltYVelocity * f3;
            if (this.cameraArmTiltY > CAMERA_YAW_ARM_TILT_MAX) {
                this.cameraArmTiltY = CAMERA_YAW_ARM_TILT_MAX;
                if (!(this.cameraArmTiltYVelocity > 0.0f)) continue;
                this.cameraArmTiltYVelocity = 0.0f;
                continue;
            }
            if (!(this.cameraArmTiltY < -CAMERA_YAW_ARM_TILT_MAX)) continue;
            this.cameraArmTiltY = -CAMERA_YAW_ARM_TILT_MAX;
            if (!(this.cameraArmTiltYVelocity < 0.0f)) continue;
            this.cameraArmTiltYVelocity = 0.0f;
        }
    }

    public float getCameraRightX() {
        return this.cameraRightX;
    }

    public float getCameraRightY() {
        return this.cameraRightY;
    }

    public float getCameraRightZ() {
        return this.cameraRightZ;
    }

    public void applyShieldImpactToRoot(Matrix4f matrix4f, float f, class_1306 class_13062) {
        if (matrix4f != null) {
            boolean bl = this.isWalkArmSyncActive();
            float f2 = this.getRemainingProceduralForceScale();
            if (this.shieldImpactValue != 0.0f && this.shieldImpactArm != null && class_13062 != null) {
                float f3 = (bl ? 0.85f : (class_13062 == this.shieldImpactArm ? 1.0f : 0.6f)) * f2;
                float f4 = this.shieldImpactValue * 5.0f * f3;
                matrix4f.translateLocal(this.cameraForwardX * f4, this.cameraForwardY * f4, this.cameraForwardZ * f4);
                matrix4f.rotateX(-this.shieldImpactValue * 0.5f * f3);
            }
        }
    }

    public float getOffsetX() {
        return this.offsetX;
    }

    public float getOffsetZ() {
        return this.offsetZ;
    }

    public float getJumpValue() {
        return this.jumpValue;
    }

    public float getImpactValue() {
        return this.impactValue;
    }

    public float getShieldImpactValue() {
        return this.shieldImpactValue;
    }

    public float getActionAnimValue(class_1306 class_13062) {
        return class_13062 == null ? 0.0f : this.actionAnimValue[class_13062 == class_1306.field_6182 ? 0 : 1];
    }

    public float consumeActionAnimKick(class_1306 class_13062) {
        if (class_13062 == null) {
            return 0.0f;
        }
        int n = class_13062 == class_1306.field_6182 ? 0 : 1;
        float f = this.actionAnimKick[n];
        this.actionAnimKick[n] = 0.0f;
        return f;
    }

    public float getIdleTime() {
        return this.idleTime;
    }

    public float getIdleWeight() {
        return this.idleWeight;
    }

    public void triggerIdleBalance(class_1306 class_13062) {
        if (class_13062 != null) {
            if (class_13062 == class_1306.field_6182) {
                this.idleBoostLeftTarget = 1.0f;
                this.idleJitterLeftTimer = 0.0f;
            } else {
                this.idleBoostRightTarget = 1.0f;
                this.idleJitterRightTimer = 0.0f;
            }
        }
    }

    private float getIdleBoost(class_1306 class_13062) {
        if (class_13062 == null) {
            return 0.0f;
        }
        return class_13062 == class_1306.field_6182 ? this.idleBoostLeft : this.idleBoostRight;
    }

    private float getIdleJitter(class_1306 class_13062) {
        if (class_13062 == null) {
            return 0.0f;
        }
        return class_13062 == class_1306.field_6182 ? this.idleJitterLeft : this.idleJitterRight;
    }

    private void updateIdleJitter(float f) {
        this.idleJitterLeftTimer -= f;
        if (this.idleJitterLeftTimer <= 0.0f) {
            this.idleJitterLeftTarget = this.idleJitterRandom.method_43057() * 2.0f - 1.0f;
            this.idleJitterLeftTimer = class_3532.method_16439((float)this.idleJitterRandom.method_43057(), (float)0.35f, (float)0.8f);
        }
        this.idleJitterRightTimer -= f;
        if (this.idleJitterRightTimer <= 0.0f) {
            this.idleJitterRightTarget = this.idleJitterRandom.method_43057() * 2.0f - 1.0f;
            this.idleJitterRightTimer = class_3532.method_16439((float)this.idleJitterRandom.method_43057(), (float)0.35f, (float)0.8f);
        }
        float f2 = 1.0f - (float)Math.pow(0.65f, f * 20.0f);
        this.idleJitterLeft = class_3532.method_16439((float)f2, (float)this.idleJitterLeft, (float)this.idleJitterLeftTarget);
        this.idleJitterRight = class_3532.method_16439((float)f2, (float)this.idleJitterRight, (float)this.idleJitterRightTarget);
    }

    private static boolean isStandingOnSlime(class_746 class_7462) {
        return class_7462 != null && class_7462.method_37908() != null ? class_7462.method_37908().method_8320(class_7462.method_24515().method_10074()).method_27852(class_2246.field_10030) : false;
    }

    private boolean shouldApplySprintSwing(class_746 class_7462) {
        if (class_7462 == null) {
            return false;
        }
        if (!PunchyConfig.enableSprintArmSwing() || PunchyConfig.getSprintArmSwingIntensity() <= 0.0f) {
            return false;
        }
        if (this.suppressWalkSway) {
            return false;
        }
        if (this.isSubmergedWithoutGround(class_7462)) {
            return false;
        }
        if (!class_7462.method_5624()) {
            return false;
        }
        if (class_7462.method_18276() || class_7462.method_5681() || class_7462.method_6128()) {
            return false;
        }
        if (class_7462.method_6115() && !this.isSprintCompatibleUseItem(class_7462)) {
            return false;
        }
        return PunchyAnimationManager.SWIM_HANDLER.isPlaying() ? false : class_7462.field_6250 > 0.1f || class_7462.field_42108.method_48566() > 0.05f;
    }

    private float resolveSprintAlpha(class_1306 class_13062, float f) {
        float f2 = MovementStateMachine.smoothStep(this.sprintBlend) * MovementStateMachine.smoothStep(this.sprintSpeedFactor);
        if (f2 <= 1.0E-4f || class_13062 == null) {
            return f2;
        }
        if (PunchyAnimationManager.POSE_HANDLER.isCurrentDualHandedActive()) {
            return f2;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (this.isSprintBlendAllowedDuringPrimaryPose(animationClip)) {
            return f2;
        }
        class_1306 class_13063 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
        if (!PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
            return class_13063 != null && class_13063 == class_13062 && PunchyAnimationManager.POSE_HANDLER.isBlendOutActive() ? f2 * PunchyAnimationManager.POSE_HANDLER.getBlendOutProgress(f) : f2;
        }
        if (class_13063 == null || class_13063 != class_13062) {
            return f2;
        }
        return PunchyAnimationManager.POSE_HANDLER.isRebaseReturnToOriginActive() ? f2 * PunchyAnimationManager.POSE_HANDLER.getRebaseReturnToOriginProgress(f) : 0.0f;
    }

    private boolean isSprintBlendAllowedDuringPrimaryPose(AnimationClip animationClip) {
        return this.isSprintBlendAllowedDuringEquip(animationClip) || this.isSprintBlendAllowedDuringAttack(animationClip) || this.isSprintBlendAllowedDuringMining(animationClip) || this.isSprintBlendAllowedDuringUseItem(animationClip);
    }

    private boolean isSprintBlendAllowedDuringEquip(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        String string = animationClip.getName();
        return string != null && !string.isBlank() ? "hand_in".equals(string) || "hand_out".equals(string) || string.startsWith("hand_in_") || string.startsWith("hand_out_") : false;
    }

    private boolean isSprintBlendAllowedDuringAttack(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        if (!(PunchyAnimationManager.isClipNamed(animationClip, "attack") || PunchyAnimationManager.isClipNamed(animationClip, "attack_mace") || PunchyAnimationManager.isClipNamed(animationClip, "attack_trident") || PunchyAnimationManager.isClipNamed(animationClip, "attack_spear") || PunchyAnimationManager.isClipNamed(animationClip, "punch"))) {
            String string = animationClip.getName();
            if (string != null && !string.isBlank()) {
                String string2 = string.trim().toLowerCase(Locale.ROOT);
                return string2.contains("attack") || string2.startsWith("punch");
            }
            return false;
        }
        return true;
    }

    private boolean isSprintBlendAllowedDuringMining(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        if (!(PunchyAnimationManager.isClipNamed(animationClip, "mining") || PunchyAnimationManager.isClipNamed(animationClip, "mining_foward") || PunchyAnimationManager.isClipNamed(animationClip, "mining_forward") || PunchyAnimationManager.isClipNamed(animationClip, "mining_sideways") || PunchyAnimationManager.isClipNamed(animationClip, "mining_digging"))) {
            String string = animationClip.getName();
            if (string != null && !string.isBlank()) {
                String string2 = string.trim().toLowerCase(Locale.ROOT);
                return string2.equals("mining") || string2.startsWith("mining_") || string2.contains("mining_v2");
            }
            return false;
        }
        return true;
    }

    private boolean isSprintBlendAllowedDuringUseItem(AnimationClip animationClip) {
        if (animationClip == null) {
            return false;
        }
        if (!(PunchyAnimationManager.isClipNamed(animationClip, "interact") || PunchyAnimationManager.isClipNamed(animationClip, "use_eat") || PunchyAnimationManager.isClipNamed(animationClip, "use_drink") || PunchyAnimationManager.isClipNamed(animationClip, "use_item") || PunchyAnimationManager.isClipNamed(animationClip, "useitem") || PunchyAnimationManager.isClipNamed(animationClip, "mining_use_foward") || PunchyAnimationManager.isClipNamed(animationClip, "mining_use_forward") || PunchyAnimationManager.isClipNamed(animationClip, "mining_use_digging"))) {
            String string = animationClip.getName();
            if (string != null && !string.isBlank()) {
                String string2 = string.trim().toLowerCase(Locale.ROOT);
                if (!(string2.equals("interact") || string2.equals("interaction") || string2.equals("use_item") || string2.equals("useitem") || string2.equals("use_eat") || string2.equals("use_drink") || string2.startsWith("mining_use_"))) {
                    class_310 class_3102 = class_310.method_1551();
                    return class_3102 != null && class_3102.field_1724 != null && this.isSprintCompatibleUseItem(class_3102.field_1724);
                }
                return true;
            }
            return false;
        }
        return true;
    }

    private boolean shouldApplyWalkSwing(class_746 class_7462, float f) {
        if (class_7462 == null) {
            return false;
        }
        if (!PunchyConfig.enableCustomWalk()) {
            return false;
        }
        if (this.suppressWalkSway) {
            return false;
        }
        if (this.isSubmergedWithoutGround(class_7462)) {
            return false;
        }
        if (class_7462.method_18276() || class_7462.method_5681() || class_7462.method_6128()) {
            return false;
        }
        if (class_7462.method_6115() && !this.isSprintCompatibleUseItem(class_7462)) {
            return false;
        }
        if (class_7462.method_5799() || class_7462.method_5869() || class_7462.method_5777(class_3486.field_15517)) {
            class_310 class_3102 = class_310.method_1551();
            if (!PunchyClient.getWalkOnWaterStateMachine().isTriggerActiveOrInGap(class_3102)) {
                return false;
            }
        }
        return f > 0.02f;
    }

    private boolean isSubmergedWithoutGround(class_746 class_7462) {
        return class_7462 == null ? false : class_7462.method_5869() && !this.isGrounded;
    }

    private SwingTransform computeWalkSwing(class_1306 class_13062) {
        float f;
        float f2;
        float f3;
        int n;
        if (class_13062 == null) {
            return SwingTransform.empty();
        }
        boolean bl = this.isWalkArmSyncActive();
        float f4 = MovementStateMachine.smoothStep(this.walkBlend);
        float f5 = this.animGrounded ? this.walkSpeedFactor : 1.0f;
        float f6 = f4 * f5;
        int n2 = class_13062 == class_1306.field_6182 ? 0 : 1;
        int n3 = n = bl ? 0 : n2;
        f3 = bl ? 1.0f : (n2 == 0 ? 1.0f : -1.0f);
        float f7 = f3;
        float f8 = this.walkPhase + (bl ? 0.0f : (f3 < 0.0f ? (float)Math.PI : 0.0f));
        float f9 = 0.0f;
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = 0.0f;
        float f13 = 0.0f;
        if (f6 > 1.0E-4f) {
            f2 = class_3532.method_15374((float)f8);
            f = class_3532.method_15374((float)(f8 * 2.0f + 0.35f));
            float f14 = class_3532.method_15374((float)(f8 + 0.4f));
            float f15 = Math.abs(f2);
            f9 = (f2 * 0.085f + f * 0.035f * 0.55f) * f6;
            f10 = (class_3532.method_15374((float)(f8 + 1.1f)) * 0.03f * 0.75f + class_3532.method_15374((float)(f8 * 2.0f + 0.2f)) * 0.012f * 0.5f) * f6 * f3 + 0.022f * f6 * f3;
            f11 = (class_3532.method_15362((float)(f8 + 0.35f)) * 0.038f * 0.8f + class_3532.method_15374((float)(f8 * 1.4f + 0.3f)) * 0.016f * 0.45f) * f6 * f3 + 0.018f * f6 * f3;
            f12 = f14 * 1.85f * 0.7f * f6 * f3;
            float f16 = f2 * f2 * 0.11f * 0.35f * f6;
            f13 = (-f15 * 0.06f * 0.55f + class_3532.method_15362((float)(f8 * 2.0f + 0.2f)) * 0.06f * 0.25f) * f6 + f16;
            f9 *= 1.22f;
            f10 *= 1.22f;
            f11 *= 1.22f;
            if (bl) {
                f9 *= 0.38f;
                f10 *= 0.38f;
                f11 *= 0.38f;
                f12 *= 1.35f;
                f13 *= 1.12f;
            }
        }
        if (this.animGrounded) {
            f2 = this.sprintDt > 0.0f ? this.sprintDt : 0.05f;
            f = 1.0f - (float)Math.pow(0.6f, f2 * 20.0f);
            this.walkRotX[n] = class_3532.method_16439((float)f, (float)this.walkRotX[n], (float)f9);
            this.walkRotY[n] = class_3532.method_16439((float)f, (float)this.walkRotY[n], (float)f10);
            this.walkRotZ[n] = class_3532.method_16439((float)f, (float)this.walkRotZ[n], (float)f11);
            this.walkOffSide[n] = class_3532.method_16439((float)f, (float)this.walkOffSide[n], (float)f12);
            this.walkOffUp[n] = class_3532.method_16439((float)f, (float)this.walkOffUp[n], (float)f13);
            if (bl) {
                this.walkRotX[1] = this.walkRotX[0];
                this.walkRotY[1] = this.walkRotY[0];
                this.walkRotZ[1] = this.walkRotZ[0];
                this.walkOffSide[1] = this.walkOffSide[0];
                this.walkOffUp[1] = this.walkOffUp[0];
            }
        }
        return new SwingTransform(this.walkRotX[n], this.walkRotY[n], this.walkRotZ[n], this.walkOffSide[n], 0.0f, this.walkOffUp[n]);
    }

    private boolean isWalkArmSyncActive() {
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying() && animationClip.hasEffectAny("sync_walk_arms")) {
            return true;
        }
        PunchyTuningConfig.CustomFlags customFlags = PunchyAnimationManager.POSE_HANDLER.getOverlapCustomFlags();
        if (customFlags != null && Boolean.TRUE.equals(customFlags.syncProceduralArmAnimations)) {
            return true;
        }
        if (this.isDualHandedSpearChargeActive(animationClip)) {
            return true;
        }
        AnimationClip animationClip2 = PunchyAnimationManager.SWIM_HANDLER.getCurrentClip();
        return animationClip2 != null && PunchyAnimationManager.SWIM_HANDLER.isPlaying() && animationClip2.hasEffectAny("sync_walk_arms");
    }

    public float getWalkAndSprintForceScale() {
        PunchyTuningConfig.CustomFlags customFlags = PunchyAnimationManager.POSE_HANDLER.getOverlapCustomFlags();
        return customFlags != null && customFlags.walkAndSprintForce != null ? (float)customFlags.walkAndSprintForce.intValue() / 100.0f : 1.0f;
    }

    public float getRemainingProceduralForceScale() {
        PunchyTuningConfig.CustomFlags customFlags = PunchyAnimationManager.POSE_HANDLER.getOverlapCustomFlags();
        return customFlags != null && customFlags.remainingProceduralAnimationsForce != null ? (float)customFlags.remainingProceduralAnimationsForce.intValue() / 100.0f : 1.0f;
    }

    private boolean isSpearChargeUseActive(class_746 class_7462) {
        return class_7462 != null && class_7462.method_6115() ? SpearStateMachine.isSpearStack(class_7462.method_6030()) : false;
    }

    private boolean isSprintCompatibleUseItem(class_746 class_7462) {
        if (class_7462 == null || !class_7462.method_6115()) {
            return false;
        }
        if (this.isSpearChargeUseActive(class_7462)) {
            return true;
        }
        class_1799 class_17992 = class_7462.method_6030();
        return class_17992 != null && !class_17992.method_7960() ? !(class_17992.method_7909() instanceof class_1753 || class_17992.method_7909() instanceof class_1764 || class_17992.method_7909() instanceof class_1835) : false;
    }

    private boolean isDualHandedSpearChargeActive(AnimationClip animationClip) {
        if (animationClip == null || !PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
            return false;
        }
        if (!PunchyAnimationManager.POSE_HANDLER.isCurrentDualHandedActive()) {
            return false;
        }
        String string = animationClip.getName();
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            return string2.startsWith("use_spear_pose_");
        }
        return false;
    }

    private SwingTransform computeSprintSwing(class_1306 class_13062) {
        float f;
        float f2;
        float f3;
        int n;
        if (class_13062 == null) {
            return SwingTransform.empty();
        }
        boolean bl = MovementStateMachine.isSprintLateralTiltEnabled(class_13062);
        boolean bl2 = this.isWalkArmSyncActive();
        float f4 = MovementStateMachine.smoothStep(this.sprintBlend);
        float f5 = PunchyConfig.getSprintArmSwingIntensity() * 0.75f;
        float f6 = this.animGrounded ? this.sprintSpeedFactor : 1.0f;
        float f7 = f4 * f6 * f5 * (bl2 ? 0.7f : 1.0f);
        int n2 = class_13062 == class_1306.field_6182 ? 0 : 1;
        int n3 = n = bl2 ? 0 : n2;
        f3 = bl2 ? 1.0f : (n2 == 0 ? 1.0f : -1.0f);
        float f8 = f3;
        float f9 = this.sprintPhase + (bl2 ? 0.0f : (f3 < 0.0f ? (float)Math.PI : 0.0f));
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = 0.0f;
        float f13 = 0.0f;
        float f14 = 0.0f;
        if (f7 > 1.0E-4f) {
            f2 = class_3532.method_15374((float)f9);
            f = class_3532.method_15374((float)(f9 * 2.0f + 0.2f));
            float f15 = Math.abs(f2);
            f10 = (f2 * 0.135f * 1.2f + f * 0.06f * 0.9f - f15 * 0.035f) * f7;
            f11 = (class_3532.method_15374((float)(f9 + 1.1f)) * 0.0375f * 0.65f + class_3532.method_15374((float)(f9 * 2.2f + 0.22f)) * 0.015f * 0.45f) * f7 * f3 + 0.045f * f7 * f3;
            f11 += f3 * SPRINT_INWARD_YAW_STATIC * f4 * f5;
            if (bl) {
                f12 = (class_3532.method_15362((float)(f9 + 0.35f)) * 0.0525f * 0.85f + class_3532.method_15374((float)(f9 * 1.6f + 0.35f)) * 0.0225f * 0.55f) * f7 * f3 + 0.03f * f7 * f3;
            }
            f13 = (Math.max(0.0f, f2) * 3.6f * 0.9f + f * 1.56f * 0.45f) * f7;
            float f16 = class_3532.method_15363((float)f13, (float)0.0f, (float)1.0f) * 0.28f * f7;
            f10 -= f16;
            f14 = (-f15 * 0.12f * 0.7f + class_3532.method_15362((float)(f9 * 2.0f + 0.3f)) * 0.12f * 0.25f) * f7;
            f10 *= 1.28f;
            f11 *= 1.28f;
            f12 *= 1.28f;
        }
        if (this.animGrounded) {
            f2 = this.sprintDt > 0.0f ? this.sprintDt : 0.05f;
            f = 1.0f - (float)Math.pow(0.52f, f2 * 20.0f);
            this.sprintRotX[n] = class_3532.method_16439((float)f, (float)this.sprintRotX[n], (float)f10);
            this.sprintRotY[n] = class_3532.method_16439((float)f, (float)this.sprintRotY[n], (float)f11);
            this.sprintRotZ[n] = class_3532.method_16439((float)f, (float)this.sprintRotZ[n], (float)f12);
            this.sprintOffFwd[n] = class_3532.method_16439((float)f, (float)this.sprintOffFwd[n], (float)f13);
            this.sprintOffUp[n] = class_3532.method_16439((float)f, (float)this.sprintOffUp[n], (float)f14);
            if (bl2) {
                this.sprintRotX[1] = this.sprintRotX[0];
                this.sprintRotY[1] = this.sprintRotY[0];
                this.sprintRotZ[1] = this.sprintRotZ[0];
                this.sprintOffFwd[1] = this.sprintOffFwd[0];
                this.sprintOffUp[1] = this.sprintOffUp[0];
            }
        }
        return new SwingTransform(this.sprintRotX[n], this.sprintRotY[n], this.sprintRotZ[n], 0.0f, this.sprintOffFwd[n], this.sprintOffUp[n]);
    }

    private static boolean isSprintLateralTiltEnabled(class_1306 class_13062) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null && class_13062 != null) {
            class_746 class_7462 = class_3102.field_1724;
            class_1799 class_17992 = MovementStateMachine.getStackForArm(class_7462, class_13062);
            boolean bl = class_13062 == class_1306.field_6182;
            class_1799 class_17993 = PunchyAnimationManager.resolveRenderStack((class_1657)class_7462, class_13062, class_17992);
            return MovementStateMachine.isSprintLateralTiltDisabledForStack(class_17993, bl) ? false : !MovementStateMachine.isSprintLateralTiltDisabledForStack(class_17992, bl);
        }
        return true;
    }

    private static boolean isSprintLateralTiltDisabledForStack(class_1799 class_17992, boolean bl) {
        if (class_17992 != null && !class_17992.method_7960()) {
            LinkedHashSet<VanillaFirstPersonItemLayer.ToolKind> linkedHashSet = new LinkedHashSet<VanillaFirstPersonItemLayer.ToolKind>();
            VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
            if (toolKind != null) {
                linkedHashSet.add(toolKind);
            }
            if (class_17992.method_7909() instanceof class_1747) {
                linkedHashSet.add(VanillaFirstPersonItemLayer.ToolKind.FULL_BLOCK);
                linkedHashSet.add(VanillaFirstPersonItemLayer.ToolKind.BLOCK);
                linkedHashSet.add(VanillaFirstPersonItemLayer.ToolKind.BUILDING_BLOCKS);
                linkedHashSet.add(VanillaFirstPersonItemLayer.ToolKind.COLORED_BLOCKS);
                linkedHashSet.add(VanillaFirstPersonItemLayer.ToolKind.NATURAL_BLOCKS);
                linkedHashSet.add(VanillaFirstPersonItemLayer.ToolKind.FUNCTIONAL_BLOCKS);
            }
            linkedHashSet.add(VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
            for (VanillaFirstPersonItemLayer.ToolKind toolKind2 : linkedHashSet) {
                ToolTuning.Profile profile = ToolTuning.resolveProfile(class_17992, toolKind2, bl);
                if (profile == null || !Boolean.TRUE.equals(profile.disableSprintLateralTilt)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static class_1799 getStackForArm(class_746 class_7462, class_1306 class_13062) {
        if (class_7462 != null && class_13062 != null) {
            boolean bl = class_7462.method_6068() == class_1306.field_6183;
            boolean bl2 = class_13062 == class_1306.field_6183 == bl;
            return bl2 ? class_7462.method_6047() : class_7462.method_6079();
        }
        return class_1799.field_8037;
    }

    private static float smoothStep(float f) {
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        return f2 * f2 * (3.0f - 2.0f * f2);
    }

    private static float wrapRadians(float f) {
        return (float)Math.atan2(Math.sin(f), Math.cos(f));
    }

    static {
        SYNC_WALK_ARMS_EFFECT = "sync_walk_arms";
        INSTANCE = new MovementStateMachine();
        APPLY_TO_ARMS = true;
        WALK_SCALE = 1.0f;
        JUMP_IMPULSE = -2.8f;
        LAND_IMPULSE = 2.4f;
        JUMP_STIFFNESS = 75.0f;
        JUMP_DAMPING = 16.0f;
        JUMP_OFFSET_SCALE = 0.85f;
        JUMP_ROT_SCALE = 1.65f;
        CAMERA_TILT_SMOOTH = 0.45f;
        CAMERA_YAW_TILT_Y_SCALE = 0.018f;
        CAMERA_YAW_TILT_Z_SCALE = 0.005f;
        CAMERA_YAW_ARM_TILT_Y_SCALE = 0.03264f;
        CAMERA_YAW_ARM_TILT_MAX = (float)Math.toRadians(48.0);
        CAMERA_YAW_ARM_TILT_STIFFNESS = 64.0f;
        CAMERA_YAW_ARM_TILT_DAMPING = 15.0f;
        CAMERA_PITCH_OFFSET_SCALE = 0.01105f;
        CAMERA_PITCH_TILT_X_SCALE = 0.027625f;
        SPEAR_CHARGE_IMPACT_SCALE = 0.5f;
        IMPACT_STIFFNESS = 60.0f;
        IMPACT_DAMPING = 15.5f;
        SHIELD_BLOCK_IMPACT_SCALE = 0.5f;
        SHIELD_IMPACT_STIFFNESS = 60.0f;
        SHIELD_IMPACT_DAMPING = 15.5f;
        ACTION_ANIM_IMPULSE_SCALE = 0.5f;
        ACTION_ANIM_STIFFNESS = 60.0f;
        ACTION_ANIM_DAMPING = 15.5f;
        IDLE_INTENSITY = 2.6f;
        IDLE_SPEED = 2.0f;
        SPRINT_INWARD_YAW_STATIC = (float)Math.toRadians(25.0);
        UNDERWATER_SINK_POSE_INWARD_YAW = (float)Math.toRadians(20.0);
    }

    static final class SneakSpringState {
        private static final float TARGET_DEPTH = -0.12f;
        private static final float DOWN_STIFFNESS = 22.0f;
        private static final float UP_STIFFNESS = 22.0f;
        private static final float DAMPING = 14.0f;
        private static final float DOWN_IMPULSE = -0.4f;
        private static final float UP_IMPULSE = 1.0f;
        private float value;
        private float velocity;
        private boolean sneaking;

        SneakSpringState() {
        }

        void setSneaking(boolean bl) {
            if (this.sneaking != bl) {
                this.sneaking = bl;
                this.velocity += bl ? -0.4f : 1.0f;
            }
        }

        void tick(float f) {
            float f2 = this.sneaking ? -0.12f : 0.0f;
            float f3 = this.sneaking ? 22.0f : 22.0f;
            float f4 = f3 * (f2 - this.value) - 14.0f * this.velocity;
            this.velocity += f4 * f;
            this.value += this.velocity * f;
            if (!this.sneaking && Math.abs(this.value) < 1.0E-4f && Math.abs(this.velocity) < 1.0E-4f) {
                this.value = 0.0f;
                this.velocity = 0.0f;
            }
        }

        float offset() {
            return this.value;
        }
    }

    static final class SwingTransform {
        final float rotX;
        final float rotY;
        final float rotZ;
        final float offX;
        final float offY;
        final float offZ;

        SwingTransform(float f, float f2, float f3, float f4, float f5, float f6) {
            this.rotX = f;
            this.rotY = f2;
            this.rotZ = f3;
            this.offX = f4;
            this.offY = f5;
            this.offZ = f6;
        }

        static SwingTransform empty() {
            return new SwingTransform(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }

        boolean hasMotion() {
            return Math.abs(this.rotX) > 1.0E-5f || Math.abs(this.rotY) > 1.0E-5f || Math.abs(this.rotZ) > 1.0E-5f || Math.abs(this.offX) > 1.0E-5f || Math.abs(this.offY) > 1.0E-5f || Math.abs(this.offZ) > 1.0E-5f;
        }

        SwingTransform scaled(float f) {
            float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)2.0f);
            return new SwingTransform(this.rotX * f2, this.rotY * f2, this.rotZ * f2, this.offX * f2, this.offY * f2, this.offZ * f2);
        }

        static SwingTransform blend(SwingTransform swingTransform, SwingTransform swingTransform2, float f) {
            if (swingTransform == null && swingTransform2 == null) {
                return null;
            }
            if (swingTransform == null) {
                return swingTransform2;
            }
            if (swingTransform2 == null) {
                return swingTransform;
            }
            float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
            float f3 = 1.0f - f2;
            return new SwingTransform(swingTransform.rotX * f3 + swingTransform2.rotX * f2, swingTransform.rotY * f3 + swingTransform2.rotY * f2, swingTransform.rotZ * f3 + swingTransform2.rotZ * f2, swingTransform.offX * f3 + swingTransform2.offX * f2, swingTransform.offY * f3 + swingTransform2.offY * f2, swingTransform.offZ * f3 + swingTransform2.offZ * f2);
        }
    }
}

