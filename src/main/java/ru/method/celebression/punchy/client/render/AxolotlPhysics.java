package ru.method.celebression.punchy.client.render;

import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_746;

public class AxolotlPhysics {
    private static final float ROLL_STIFFNESS = 0.18f;
    private static final float ROLL_DAMPING = 0.75f;
    private static final float ROLL_MAX_VELOCITY = 0.18f;
    private static final float ROLL_YAW_SCALE = 0.7f;
    private static final float LIFT_STIFFNESS = 0.12f;
    private static final float LIFT_DAMPING = 0.8f;
    private static final float LIFT_MAX_VELOCITY = 0.12f;
    private static final float LIFT_FALL_SCALE = 0.6f;
    private static final float LIFT_FALL_MAX = 0.28f;
    private static final float LIFT_MIN_FALL_SPEED = 0.05f;
    private static final float LANDING_SLOW_TIME = 0.2f;
    private static final float LANDING_RETURN_MULT = 0.5f;
    private static final float MAX_DT = 0.1f;
    private final SpringAxis rollSpring = new SpringAxis(0.18f, 0.75f, 0.18f);
    private final SpringAxis liftSpring = new SpringAxis(0.12f, 0.8f, 0.12f);
    private float roll;
    private float lift;
    private float lastYaw = Float.NaN;
    private long lastUpdateTime = System.nanoTime();
    private boolean prevOnGround = true;
    private float landingSlowTimer;

    public void update(class_746 class_7462) {
        long l = System.nanoTime();
        float f = (float)(l - this.lastUpdateTime) / 1.0E9f;
        this.lastUpdateTime = l;
        if (!(f <= 0.0f)) {
            float f2;
            float f3;
            float f4;
            if (f > 0.1f) {
                f = 0.1f;
            }
            float f5 = 0.0f;
            float f6 = 0.0f;
            if (class_7462 != null) {
                f4 = AxolotlPhysics.resolveCameraYaw(class_7462);
                if (Float.isNaN(this.lastYaw)) {
                    this.lastYaw = f4;
                }
                f3 = class_3532.method_15393((float)(f4 - this.lastYaw));
                this.lastYaw = f4;
                f5 = (float)Math.toRadians(-f3) * 0.7f;
                f2 = (float)class_7462.method_18798().field_1351;
                if (f2 < -0.05f) {
                    f6 = class_3532.method_15363((float)(-f2 * 0.6f), (float)0.0f, (float)0.28f);
                }
                boolean bl = class_7462.method_24828();
                if (!this.prevOnGround && bl) {
                    this.landingSlowTimer = 0.2f;
                }
                this.prevOnGround = bl;
            } else {
                this.lastYaw = Float.NaN;
                this.prevOnGround = true;
                this.landingSlowTimer = 0.0f;
            }
            this.roll = this.rollSpring.update(f5);
            if (this.landingSlowTimer > 0.0f) {
                this.landingSlowTimer = Math.max(0.0f, this.landingSlowTimer - f);
            }
            f4 = 0.12f;
            f3 = 0.8f;
            f2 = 0.12f;
            if (this.landingSlowTimer > 0.0f && f6 == 0.0f) {
                f4 *= 0.5f;
                f2 *= 0.5f;
            }
            this.lift = this.liftSpring.update(f6, f4, f3, f2);
        }
    }

    public float getRoll() {
        return this.roll;
    }

    public float getLift() {
        return this.lift;
    }

    private static float resolveCameraYaw(class_746 class_7462) {
        if (class_7462 == null) {
            return 0.0f;
        }
        class_243 class_2432 = class_7462.method_5828(1.0f);
        return class_2432.method_1027() < 1.0E-6 ? class_7462.method_36454() : (float)Math.toDegrees(Math.atan2(-class_2432.field_1352, class_2432.field_1350));
    }

    static final class SpringAxis {
        private final float stiffness;
        private final float damping;
        private final float maxVelocity;
        private float value;
        private float velocity;

        SpringAxis(float f, float f2, float f3) {
            this.stiffness = f;
            this.damping = f2;
            this.maxVelocity = f3;
        }

        float update(float f) {
            return this.update(f, this.stiffness, this.damping, this.maxVelocity);
        }

        float update(float f, float f2, float f3, float f4) {
            float f5 = (f - this.value) * f2;
            this.velocity += f5;
            this.velocity = class_3532.method_15363((float)this.velocity, (float)(-f4), (float)f4);
            this.velocity *= f3;
            this.value += this.velocity;
            float f6 = f - this.value;
            if (Math.signum(this.velocity) != Math.signum(f6)) {
                this.velocity *= 0.25f;
            }
            if (Math.abs(f6) < 0.001f && Math.abs(this.velocity) < 0.001f) {
                this.value = f;
                this.velocity = 0.0f;
            }
            return this.value;
        }
    }
}

