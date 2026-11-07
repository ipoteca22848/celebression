package ru.method.celebression.punchy.client.render;

import java.util.HashMap;
import net.minecraft.class_1306;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_746;
import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.state.MovementStateMachine;

public class PendulumPhysics {
    private static final PendulumPhysics INSTANCE = new PendulumPhysics();
    public static float PENDULUM_LIMIT_DEG = 55.0f;
    public static float PENDULUM_FORCE = 0.75f;
    public static float PENDULUM_SPEED = 16.0f;
    public static float PENDULUM_CAMERA_SENS = 0.3f;
    public static float PENDULUM_GRAVITY = 25.0f;
    public static float PENDULUM_MOTION_SENS = 0.2f;
    private float lastYaw = Float.NaN;
    private float lastPitch = Float.NaN;
    private long lastUpdateTime = System.nanoTime();
    private float frameDt;
    private float frameYawDelta;
    private float framePitchDelta;
    private float framePitch;
    private float frameWalkSpeed;
    private float frameWalkPos;
    private float frameVelY;
    private boolean frameOnGround;
    private boolean frameShaderActive;
    private boolean frameValid;
    private long frameId;
    private Boolean lastLoggedShaderState;
    private final HashMap<StateKey, State> states = new HashMap();
    private final HashMap<StateKey, Matrix4f> lastApplied = new HashMap();

    public static PendulumPhysics get() {
        return INSTANCE;
    }

    public void updateFrame(class_746 class_7462, float f) {
        if (class_7462 == null) {
            this.reset();
        } else {
            long l = System.nanoTime();
            float f2 = (float)(l - this.lastUpdateTime) / 1.0E9f;
            this.lastUpdateTime = l;
            if (!(f2 <= 0.0f)) {
                if (f2 > 0.033333335f) {
                    f2 = 0.033333335f;
                }
                float f3 = class_7462.method_5705(f);
                float f4 = class_7462.method_5695(f);
                if (Float.isNaN(this.lastYaw)) {
                    this.lastYaw = f3;
                }
                if (Float.isNaN(this.lastPitch)) {
                    this.lastPitch = f4;
                }
                float f5 = class_3532.method_15393((float)(f3 - this.lastYaw));
                float f6 = f4 - this.lastPitch;
                this.lastYaw = f3;
                this.lastPitch = f4;
                this.frameWalkSpeed = class_7462.field_42108.method_48566();
                this.frameWalkPos = class_7462.field_42108.method_48569();
                this.frameVelY = (float)class_7462.method_18798().field_1351;
                this.frameOnGround = class_7462.method_24828();
                this.frameShaderActive = PendulumPhysics.detectShaderStateStable();
                if (this.lastLoggedShaderState == null || this.lastLoggedShaderState != this.frameShaderActive) {
                    this.lastLoggedShaderState = this.frameShaderActive;
                }
                this.frameDt = f2;
                this.frameYawDelta = f5;
                this.framePitchDelta = f6;
                this.framePitch = f4;
                this.frameValid = true;
                ++this.frameId;
            }
        }
    }

    public void applyTo(class_4587 class_45872, ToolTuning.PhysicsMode physicsMode, Vector3f vector3f, ToolTuning.PhysicsSettings physicsSettings, class_1306 class_13062, float f) {
        Matrix4f matrix4f;
        if (class_45872 != null && physicsMode != null && physicsMode != ToolTuning.PhysicsMode.NONE && (matrix4f = this.computeAppliedMatrix(new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761()), physicsMode, vector3f, physicsSettings, class_13062, f, "default")) != null) {
            class_45872.method_23760().method_23761().mul((Matrix4fc)matrix4f);
            class_45872.method_23760().method_23762().set((Matrix3fc)new Matrix3f((Matrix4fc)class_45872.method_23760().method_23761()).invert().transpose());
            StateKey stateKey = new StateKey(class_13062, "default");
            this.lastApplied.put(stateKey, matrix4f);
            State state = this.states.get(stateKey);
            if (state != null) {
                state.lastAppliedFrameId = this.frameId;
            }
        }
    }

    public Matrix4f computeAppliedMatrix(Matrix4f matrix4f, ToolTuning.PhysicsMode physicsMode, Vector3f vector3f, ToolTuning.PhysicsSettings physicsSettings, class_1306 class_13062, float f) {
        return this.computeAppliedMatrix(matrix4f, physicsMode, vector3f, physicsSettings, class_13062, f, "default", 0.0f);
    }

    public Matrix4f computeAppliedMatrix(Matrix4f matrix4f, ToolTuning.PhysicsMode physicsMode, Vector3f vector3f, ToolTuning.PhysicsSettings physicsSettings, class_1306 class_13062, float f, String string) {
        return this.computeAppliedMatrix(matrix4f, physicsMode, vector3f, physicsSettings, class_13062, f, string, 0.0f);
    }

    public Matrix4f computeAppliedMatrix(Matrix4f matrix4f, ToolTuning.PhysicsMode physicsMode, Vector3f vector3f, ToolTuning.PhysicsSettings physicsSettings, class_1306 class_13062, float f, String string, float f2) {
        Matrix4f matrix4f2;
        if (matrix4f == null || physicsMode == null || physicsMode == ToolTuning.PhysicsMode.NONE) {
            return null;
        }
        if (!this.frameValid || class_13062 == null) {
            return null;
        }
        if (!PendulumPhysics.isFiniteMatrix(matrix4f)) {
            return new Matrix4f();
        }
        ResolvedSettings resolvedSettings = PendulumPhysics.resolveSettings(physicsSettings);
        StateKey stateKey2 = new StateKey(class_13062, string != null && !string.isBlank() ? string : "default");
        State state = this.states.computeIfAbsent(stateKey2, stateKey -> new State());
        if (state.lastFrameId != this.frameId) {
            state.update(this.frameDt, this.frameYawDelta, this.framePitchDelta, this.framePitch, f2, this.frameWalkSpeed, this.frameWalkPos, this.frameVelY, this.frameOnGround, resolvedSettings, class_13062);
            state.lastFrameId = this.frameId;
        }
        if (!state.isFinite()) {
            state.resetDynamic();
            state.lastFrameId = this.frameId;
        }
        float f3 = class_3532.method_16439((float)f, (float)state.prevAngleX, (float)state.angleX);
        float f4 = class_3532.method_16439((float)f, (float)state.prevAngleZ, (float)state.angleZ);
        if (physicsMode == ToolTuning.PhysicsMode.FORWARD_PENDULUM) {
            f4 = 0.0f;
        }
        if (this.frameShaderActive) {
            f4 = -f4;
        }
        if (resolvedSettings.invert) {
            f3 = -f3;
            f4 = -f4;
        }
        if (!(Float.isFinite(f3) && Float.isFinite(f4) && Float.isFinite(state.angleX) && Float.isFinite(state.angleZ) && Float.isFinite(state.velocityX) && Float.isFinite(state.velocityZ))) {
            state.resetDynamic();
            return new Matrix4f();
        }
        if (f3 == 0.0f && f4 == 0.0f) {
            return new Matrix4f();
        }
        float f5 = vector3f != null ? vector3f.x : 0.0f;
        float f6 = vector3f != null ? vector3f.y : 0.0f;
        float f7 = vector3f != null ? vector3f.z : 0.0f;
        Vector3f vector3f2 = new Vector3f(f5, f6, f7);
        matrix4f.transformPosition(vector3f2);
        Vector3f vector3f3 = matrix4f.getTranslation(new Vector3f());
        Quaternionf quaternionf = matrix4f.getUnnormalizedRotation(new Quaternionf());
        if (quaternionf.lengthSquared() < 1.0E-8f) {
            quaternionf.identity();
        } else {
            quaternionf.normalize();
        }
        Matrix4f matrix4f3 = new Matrix4f().translationRotate((Vector3fc)vector3f3, (Quaternionfc)quaternionf);
        Matrix4f matrix4f4 = new Matrix4f();
        if (vector3f2.x != 0.0f || vector3f2.y != 0.0f || vector3f2.z != 0.0f) {
            matrix4f4.translate((Vector3fc)vector3f2);
        }
        if (this.frameShaderActive) {
            if (f4 != 0.0f) {
                matrix4f4.rotateZ((float)Math.toRadians(f4));
            }
            if (f3 != 0.0f) {
                matrix4f4.rotateX((float)Math.toRadians(f3));
            }
        } else {
            float f8 = (float)Math.toRadians(this.lastYaw);
            float f9 = (float)Math.toRadians(this.framePitch);
            float f10 = class_3532.method_15362((float)f9);
            Vector3f vector3f4 = new Vector3f(-class_3532.method_15374((float)f8) * f10, -class_3532.method_15374((float)f9), class_3532.method_15362((float)f8) * f10);
            if (vector3f4.lengthSquared() < 1.0E-6f) {
                vector3f4.set(0.0f, 0.0f, 1.0f);
            } else {
                vector3f4.normalize();
            }
            Vector3f vector3f5 = new Vector3f(-class_3532.method_15362((float)f8), 0.0f, -class_3532.method_15374((float)f8));
            if (vector3f5.lengthSquared() < 1.0E-6f) {
                vector3f5.set(1.0f, 0.0f, 0.0f);
            } else {
                vector3f5.normalize();
            }
            if (f4 != 0.0f) {
                matrix4f4.rotate((float)Math.toRadians(f4), vector3f4.x, vector3f4.y, vector3f4.z);
            }
            if (f3 != 0.0f) {
                matrix4f4.rotate((float)Math.toRadians(f3), vector3f5.x, vector3f5.y, vector3f5.z);
            }
        }
        if (vector3f2.x != 0.0f || vector3f2.y != 0.0f || vector3f2.z != 0.0f) {
            matrix4f4.translate(-vector3f2.x, -vector3f2.y, -vector3f2.z);
        }
        return PendulumPhysics.isFiniteMatrix(matrix4f2 = new Matrix4f((Matrix4fc)matrix4f3).invert().mul((Matrix4fc)matrix4f4).mul((Matrix4fc)matrix4f3)) ? matrix4f2 : new Matrix4f();
    }

    private static boolean isFiniteMatrix(Matrix4f matrix4f) {
        return matrix4f != null && Float.isFinite(matrix4f.m00()) && Float.isFinite(matrix4f.m01()) && Float.isFinite(matrix4f.m02()) && Float.isFinite(matrix4f.m03()) && Float.isFinite(matrix4f.m10()) && Float.isFinite(matrix4f.m11()) && Float.isFinite(matrix4f.m12()) && Float.isFinite(matrix4f.m13()) && Float.isFinite(matrix4f.m20()) && Float.isFinite(matrix4f.m21()) && Float.isFinite(matrix4f.m22()) && Float.isFinite(matrix4f.m23()) && Float.isFinite(matrix4f.m30()) && Float.isFinite(matrix4f.m31()) && Float.isFinite(matrix4f.m32()) && Float.isFinite(matrix4f.m33());
    }

    private static boolean detectShaderStateStable() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return false;
        }
        Boolean bl = PendulumPhysics.queryIrisShaderPackInUse();
        if (bl != null) {
            return bl;
        }
        try {
            return class_310.method_29611();
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    private static Boolean queryIrisShaderPackInUse() {
        Object object;
        Object object2;
        Class<?> clazz;
        try {
            clazz = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
            object2 = clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            object = clazz.getMethod("isShaderPackInUse", new Class[0]).invoke(object2, new Object[0]);
            if (object instanceof Boolean) {
                Boolean bl = (Boolean)object;
                return bl;
            }
        }
        catch (Throwable throwable) {
        }
        try {
            clazz = Class.forName("net.coderbot.iris.api.v0.IrisApi");
            object2 = clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            object = clazz.getMethod("isShaderPackInUse", new Class[0]).invoke(object2, new Object[0]);
            if (object instanceof Boolean) {
                Boolean bl = (Boolean)object;
                return bl;
            }
        }
        catch (Throwable throwable) {
        }
        return null;
    }

    public Matrix4f getLastAppliedMatrix(class_1306 class_13062) {
        if (class_13062 == null) {
            return null;
        }
        StateKey stateKey = new StateKey(class_13062, "default");
        State state = this.states.get(stateKey);
        if (state != null && state.lastAppliedFrameId == this.frameId) {
            Matrix4f matrix4f = this.lastApplied.get(stateKey);
            return matrix4f == null ? null : new Matrix4f((Matrix4fc)matrix4f);
        }
        return null;
    }

    private static float clampAngle(float f, float f2) {
        if (f > f2) {
            return f2;
        }
        return f < -f2 ? -f2 : f;
    }

    private void reset() {
        this.lastYaw = Float.NaN;
        this.lastPitch = Float.NaN;
        this.frameValid = false;
        this.frameId = 0L;
        this.states.clear();
    }

    private static ResolvedSettings resolveSettings(ToolTuning.PhysicsSettings physicsSettings) {
        ResolvedSettings resolvedSettings = new ResolvedSettings();
        resolvedSettings.limitDeg = PENDULUM_LIMIT_DEG;
        resolvedSettings.limitDegUp = PENDULUM_LIMIT_DEG;
        resolvedSettings.limitDegDown = PENDULUM_LIMIT_DEG;
        resolvedSettings.force = PENDULUM_FORCE;
        resolvedSettings.speed = PENDULUM_SPEED;
        resolvedSettings.cameraSens = PENDULUM_CAMERA_SENS;
        resolvedSettings.gravity = PENDULUM_GRAVITY;
        resolvedSettings.motionSens = PENDULUM_MOTION_SENS;
        resolvedSettings.invert = false;
        boolean bl = false;
        boolean bl2 = false;
        if (physicsSettings != null) {
            if (physicsSettings.limitDeg != null) {
                resolvedSettings.limitDeg = physicsSettings.limitDeg.floatValue();
                bl2 = true;
            }
            if (physicsSettings.limitDegUp != null) {
                resolvedSettings.limitDegUp = physicsSettings.limitDegUp.floatValue();
                bl = true;
            }
            if (physicsSettings.limitDegDown != null) {
                resolvedSettings.limitDegDown = physicsSettings.limitDegDown.floatValue();
                bl = true;
            }
            if (physicsSettings.force != null) {
                resolvedSettings.force = physicsSettings.force.floatValue();
            }
            if (physicsSettings.speed != null) {
                resolvedSettings.speed = physicsSettings.speed.floatValue();
            }
            if (physicsSettings.cameraSens != null) {
                resolvedSettings.cameraSens = physicsSettings.cameraSens.floatValue();
            }
            if (physicsSettings.gravity != null) {
                resolvedSettings.gravity = physicsSettings.gravity.floatValue();
            }
            if (physicsSettings.motionSens != null) {
                resolvedSettings.motionSens = physicsSettings.motionSens.floatValue();
            }
            if (physicsSettings.invert != null) {
                resolvedSettings.invert = physicsSettings.invert;
            }
        }
        if (bl2 && !bl) {
            resolvedSettings.limitDegUp = resolvedSettings.limitDeg;
            resolvedSettings.limitDegDown = resolvedSettings.limitDeg;
        } else if (!bl && resolvedSettings.limitDeg > 0.0f) {
            resolvedSettings.limitDegUp = resolvedSettings.limitDeg;
            resolvedSettings.limitDegDown = resolvedSettings.limitDeg;
        }
        resolvedSettings.hasExplicitAngularLimits = bl2 || bl;
        return resolvedSettings;
    }

    record StateKey(class_1306 arm, String channel) {
    }

    static final class State {
        private static final float WALK_TO_YAW = 18.0f;
        private static final float WALK_TO_PITCH = 16.0f;
        private static final float WALK_BOB_TO_YAW = 12.0f;
        private static final float WALK_MOTION_REFERENCE_RATE = 60.0f;
        private static final float JUMP_TO_PITCH = 36.0f;
        private static final float IMPACT_TO_PITCH = 42.0f;
        private static final float SHIELD_TO_PITCH = 30.0f;
        private static final float ACTION_ANIM_TO_PITCH = 34.0f;
        private static final float ACTION_ANIM_KICK_TO_PITCH = 220.0f;
        private static final float ACTION_ANIM_KICK_ANGLE = 1.8f;
        private static final float VERTICAL_DV_TO_PITCH = 460.0f;
        private static final float VERTICAL_IMPULSE_MAX = 120.0f;
        private static final float TAKEOFF_IMPULSE = 180.0f;
        private static final float LANDING_IMPULSE = 240.0f;
        private static final float TAKEOFF_ANGLE_KICK = 4.5f;
        private static final float LANDING_ANGLE_KICK = 6.0f;
        private static final float VERTICAL_VEL_TO_TARGET = 28.0f;
        private static final float VERTICAL_TARGET_MAX = 18.0f;
        private static final float VERTICAL_REFERENCE_DT = 0.016666668f;
        private static final float GROUND_TRANSITION_COOLDOWN_S = 0.08f;
        float angleX;
        float angleZ;
        float prevAngleX;
        float prevAngleZ;
        float velocityX;
        float velocityZ;
        float lastOffsetX;
        float lastOffsetZ;
        float lastJumpValue;
        float lastImpactValue;
        float lastShieldImpactValue;
        float lastActionAnimValue;
        float lastVelY;
        boolean motionInitialized;
        boolean verticalInitialized;
        boolean prevOnGround = true;
        float groundTransitionCooldown;
        boolean hitLimitX;
        boolean hitLimitZ;
        long lastFrameId = -1L;
        long lastAppliedFrameId = -1L;

        State() {
        }

        boolean isFinite() {
            return Float.isFinite(this.angleX) && Float.isFinite(this.angleZ) && Float.isFinite(this.prevAngleX) && Float.isFinite(this.prevAngleZ) && Float.isFinite(this.velocityX) && Float.isFinite(this.velocityZ) && Float.isFinite(this.lastOffsetX) && Float.isFinite(this.lastOffsetZ) && Float.isFinite(this.lastJumpValue) && Float.isFinite(this.lastImpactValue) && Float.isFinite(this.lastShieldImpactValue) && Float.isFinite(this.lastActionAnimValue) && Float.isFinite(this.lastVelY) && Float.isFinite(this.groundTransitionCooldown);
        }

        void resetDynamic() {
            this.angleX = 0.0f;
            this.angleZ = 0.0f;
            this.prevAngleX = 0.0f;
            this.prevAngleZ = 0.0f;
            this.velocityX = 0.0f;
            this.velocityZ = 0.0f;
            this.lastOffsetX = 0.0f;
            this.lastOffsetZ = 0.0f;
            this.lastJumpValue = 0.0f;
            this.lastImpactValue = 0.0f;
            this.lastShieldImpactValue = 0.0f;
            this.lastActionAnimValue = 0.0f;
            this.lastVelY = 0.0f;
            this.motionInitialized = false;
            this.verticalInitialized = false;
            this.prevOnGround = true;
            this.groundTransitionCooldown = 0.0f;
            this.hitLimitX = false;
            this.hitLimitZ = false;
            this.lastAppliedFrameId = -1L;
        }

        void update(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean bl, ResolvedSettings resolvedSettings, class_1306 class_13062) {
            float f9;
            float f10;
            float f11;
            float f12;
            float f13;
            float f14;
            float f15;
            float f16;
            float f17;
            float f18;
            if (!Float.isFinite(f5)) {
                f5 = 0.0f;
            }
            float f19 = 0.1f;
            float f20 = 0.25f;
            float f21 = 2.2f;
            float f22 = 2.5E-4f;
            this.prevAngleX = this.angleX;
            this.prevAngleZ = this.angleZ;
            float f23 = MovementStateMachine.get().getWalkAndSprintForceScale();
            float f24 = MovementStateMachine.get().getRemainingProceduralForceScale();
            float f25 = -f2 * resolvedSettings.cameraSens * f24;
            float f26 = -f3 * resolvedSettings.cameraSens * f24;
            float f27 = resolvedSettings.force * 20.0f;
            this.velocityZ -= f25 * f27 * 5.0f;
            this.velocityX += f26 * f27;
            MovementStateMachine movementStateMachine = MovementStateMachine.get();
            if (movementStateMachine != null) {
                float f28;
                f18 = movementStateMachine.getOffsetX();
                f17 = movementStateMachine.getOffsetZ();
                f16 = movementStateMachine.getJumpValue();
                f15 = movementStateMachine.getImpactValue();
                f14 = movementStateMachine.getShieldImpactValue();
                f13 = movementStateMachine.getActionAnimValue(class_13062);
                f12 = movementStateMachine.consumeActionAnimKick(class_13062);
                if (!this.motionInitialized) {
                    this.lastOffsetX = f18;
                    this.lastOffsetZ = f17;
                    this.lastJumpValue = f16;
                    this.lastImpactValue = f15;
                    this.lastShieldImpactValue = f14;
                    this.lastActionAnimValue = f13;
                    this.motionInitialized = true;
                }
                f11 = f18 - this.lastOffsetX;
                f10 = f17 - this.lastOffsetZ;
                f9 = (f16 - this.lastJumpValue) * f24;
                float f29 = (f15 - this.lastImpactValue) * f24;
                float f30 = (f14 - this.lastShieldImpactValue) * f24;
                float f31 = (f13 - this.lastActionAnimValue) * f24;
                this.lastOffsetX = f18;
                this.lastOffsetZ = f17;
                this.lastJumpValue = f16;
                this.lastImpactValue = f15;
                this.lastShieldImpactValue = f14;
                this.lastActionAnimValue = f13;
                float f32 = f11 * 60.0f;
                float f33 = f10 * 60.0f;
                if (resolvedSettings.motionSens != 0.0f) {
                    f28 = f32 * 18.0f * f23;
                    float f34 = f33 * 16.0f * f23 + f9 * 36.0f + f29 * 42.0f + f30 * 30.0f;
                    if (f6 > 0.01f) {
                        float f35 = (float)Math.sin(f7 * 1.1f) * f6 * f23;
                        f28 += f35 * 12.0f;
                    }
                    this.velocityZ -= f28 * resolvedSettings.motionSens;
                    this.velocityX += f34 * resolvedSettings.motionSens;
                }
                this.velocityX += f31 * 34.0f;
                if (f12 != 0.0f) {
                    f28 = f12 * f24;
                    this.velocityX += f28 * 220.0f;
                    this.angleX += f28 * 1.8f;
                }
            }
            f18 = Math.max(0.55f, Math.abs(resolvedSettings.motionSens));
            if (this.groundTransitionCooldown > 0.0f) {
                this.groundTransitionCooldown = Math.max(0.0f, this.groundTransitionCooldown - f);
            }
            if (!this.verticalInitialized) {
                this.lastVelY = f8;
                this.prevOnGround = bl;
                this.verticalInitialized = true;
            } else {
                f17 = f8 - this.lastVelY;
                this.lastVelY = f8;
                f16 = class_3532.method_15363((float)(0.016666668f / Math.max(f, 1.0E-4f)), (float)0.35f, (float)1.25f);
                f15 = class_3532.method_15363((float)(-f17 * 460.0f * f16), (float)-120.0f, (float)120.0f) * f24;
                this.velocityX += f15;
                if (this.groundTransitionCooldown <= 0.0f && this.prevOnGround && !bl && f8 > 0.08f) {
                    this.angleX -= 4.5f * f24;
                    this.velocityX -= 180.0f * f24;
                    this.groundTransitionCooldown = 0.08f;
                } else if (this.groundTransitionCooldown <= 0.0f && !this.prevOnGround && bl) {
                    this.angleX += 6.0f * f24;
                    this.velocityX += 240.0f * f24;
                    this.groundTransitionCooldown = 0.08f;
                }
                this.prevOnGround = bl;
            }
            f17 = (f4 - f5) * f24;
            f16 = class_3532.method_15363((float)(-f8 * 28.0f), (float)-18.0f, (float)18.0f) * f24;
            f17 += f16 * f18;
            if (resolvedSettings.hasExplicitAngularLimits || resolvedSettings.limitDegUp > 0.0f || resolvedSettings.limitDegDown > 0.0f) {
                f15 = resolvedSettings.limitDegUp > 0.0f ? resolvedSettings.limitDegUp : resolvedSettings.limitDegDown;
                f14 = resolvedSettings.limitDegDown > 0.0f ? resolvedSettings.limitDegDown : resolvedSettings.limitDegUp;
                f17 = class_3532.method_15363((float)f17, (float)(-f14), (float)f15);
            }
            f15 = (f17 - this.angleX) * resolvedSettings.gravity;
            f14 = -this.angleZ * (resolvedSettings.gravity * 0.6f);
            f13 = 2.2f / Math.max(resolvedSettings.speed, 0.001f);
            this.velocityX += (f15 - this.velocityX * f13) * f;
            this.velocityZ += (f14 - this.velocityZ * f13) * f;
            f12 = (float)Math.exp(-f13 * f);
            this.velocityX *= f12;
            this.velocityZ *= f12;
            this.angleX += this.velocityX * f;
            this.angleZ += this.velocityZ * f;
            if (resolvedSettings.hasExplicitAngularLimits || resolvedSettings.limitDegUp > 0.0f || resolvedSettings.limitDegDown > 0.0f) {
                boolean bl2;
                f11 = resolvedSettings.limitDegUp > 0.0f ? resolvedSettings.limitDegUp : resolvedSettings.limitDegDown;
                f10 = resolvedSettings.limitDegDown > 0.0f ? resolvedSettings.limitDegDown : resolvedSettings.limitDegUp;
                f9 = Math.max(f11, f10);
                if (resolvedSettings.hasExplicitAngularLimits && f11 == 0.0f && f10 == 0.0f) {
                    this.angleX = 0.0f;
                    this.velocityX = 0.0f;
                }
                if (resolvedSettings.hasExplicitAngularLimits && f9 == 0.0f) {
                    this.angleZ = 0.0f;
                    this.velocityZ = 0.0f;
                }
                boolean bl3 = this.prevAngleX > -f10 && this.prevAngleX < f11;
                boolean bl4 = bl2 = Math.abs(this.prevAngleZ) < f9;
                if (this.angleX > f11) {
                    this.angleX = f11;
                    if (bl3 && this.velocityX > 0.0f) {
                        this.velocityX = -this.velocityX * 0.1f;
                        this.velocityX *= 0.25f;
                    }
                } else if (this.angleX < -f10) {
                    this.angleX = -f10;
                    if (bl3 && this.velocityX < 0.0f) {
                        this.velocityX = -this.velocityX * 0.1f;
                        this.velocityX *= 0.25f;
                    }
                }
                if (this.angleZ > f9) {
                    this.angleZ = f9;
                    if (bl2 && this.velocityZ > 0.0f) {
                        this.velocityZ = -this.velocityZ * 0.1f;
                        this.velocityZ *= 0.25f;
                    }
                } else if (this.angleZ < -f9) {
                    this.angleZ = -f9;
                    if (bl2 && this.velocityZ < 0.0f) {
                        this.velocityZ = -this.velocityZ * 0.1f;
                        this.velocityZ *= 0.25f;
                    }
                }
            }
            if (Math.abs(this.angleX) < 2.5E-4f && Math.abs(this.velocityX) < 2.5E-4f) {
                this.angleX = 0.0f;
                this.velocityX = 0.0f;
            }
            if (Math.abs(this.angleZ) < 2.5E-4f && Math.abs(this.velocityZ) < 2.5E-4f) {
                this.angleZ = 0.0f;
                this.velocityZ = 0.0f;
            }
        }
    }

    static final class ResolvedSettings {
        float limitDeg;
        float limitDegUp;
        float limitDegDown;
        float force;
        float speed;
        float cameraSens;
        float gravity;
        float motionSens;
        boolean invert;
        boolean hasExplicitAngularLimits;

        ResolvedSettings() {
        }
    }
}

