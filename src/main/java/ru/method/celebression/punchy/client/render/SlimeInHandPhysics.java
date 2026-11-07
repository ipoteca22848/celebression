package ru.method.celebression.punchy.client.render;

import java.util.EnumMap;
import net.minecraft.class_1268;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_746;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public final class SlimeInHandPhysics {
    private static final SlimeInHandPhysics INSTANCE = new SlimeInHandPhysics();
    private static final float STIFFNESS = 192.375f;
    private static final float DAMPING = 32.90625f;
    private static final float JUMP_IMPULSE = -1.8f;
    private static final float LAND_IMPULSE = -9.6f;
    private static final float FALL_STRETCH_MAX = 0.6f;
    private static final float FALL_SPEED_MAX = 0.6f;
    private static final float FALL_DISTANCE_MAX = 6.0f;
    private static final float RISE_SQUASH_MAX = 0.45f;
    private static final float RISE_SPEED_MAX = 0.6f;
    private static final float MAX_DEFORM = 0.9f;
    private static final float XZ_COMP = 0.5f;
    private long lastUpdateTime = System.nanoTime();
    private float frameDt;
    private boolean frameOnGround;
    private boolean frameNoGravity;
    private float frameDeltaY;
    private float frameFallDistance;
    private boolean frameJumpPressed;
    private boolean frameValid;
    private long frameId;
    private final EnumMap<class_1268, State> states = new EnumMap(class_1268.class);

    public static SlimeInHandPhysics get() {
        return INSTANCE;
    }

    private SlimeInHandPhysics() {
    }

    public void updateFrame(class_746 class_7462) {
        if (class_7462 == null) {
            this.reset();
        } else {
            long l = System.nanoTime();
            float f = (float)(l - this.lastUpdateTime) / 1.0E9f;
            this.lastUpdateTime = l;
            if (!(f <= 0.0f)) {
                if (f > 0.1f) {
                    f = 0.1f;
                }
                this.frameDt = f;
                this.frameOnGround = class_7462.method_24828();
                this.frameNoGravity = class_7462.method_5740();
                this.frameDeltaY = (float)class_7462.method_18798().field_1351;
                this.frameFallDistance = class_7462.field_6017;
                this.frameJumpPressed = class_310.method_1551().field_1690.field_1903.method_1434();
                this.frameValid = true;
                ++this.frameId;
            }
        }
    }

    public void applySlimeBall(class_4587 class_45872, class_1268 class_12682, float f) {
        float f2;
        if (class_45872 != null && class_12682 != null && !(Math.abs(f2 = this.sampleDeform(class_12682, f)) < 1.0E-4f)) {
            float f3 = SlimeInHandPhysics.verticalScaleForDeform(f2);
            float f4 = SlimeInHandPhysics.horizontalScaleForDeform(f2);
            SlimeInHandPhysics.applyCameraAlignedScale(class_45872, f4, f3);
        }
    }

    public void applySlimeBlock(class_4587 class_45872, class_1268 class_12682, float f) {
        float f2;
        if (class_45872 != null && class_12682 != null && !(Math.abs(f2 = this.sampleDeform(class_12682, f)) < 1.0E-4f)) {
            float f3 = SlimeInHandPhysics.verticalScaleForDeform(f2);
            float f4 = SlimeInHandPhysics.horizontalScaleForDeform(f2);
            class_45872.method_46416(0.0f, -0.5f, 0.0f);
            SlimeInHandPhysics.applyCameraAlignedScale(class_45872, f4, f3);
            class_45872.method_46416(0.0f, 0.5f, 0.0f);
        }
    }

    public float sampleSlimeBlockDeform(class_1268 class_12682, float f) {
        return this.sampleDeform(class_12682, f);
    }

    public static float horizontalScaleForDeform(float f) {
        return 1.0f - f * 0.5f;
    }

    public static float verticalScaleForDeform(float f) {
        return 1.0f + f;
    }

    private float sampleDeform(class_1268 class_12683, float f) {
        if (class_12683 == null) {
            return 0.0f;
        }
        if (!this.frameValid) {
            return 0.0f;
        }
        State state = this.states.computeIfAbsent(class_12683, class_12682 -> new State());
        if (state.lastFrameId != this.frameId) {
            state.update(this.frameDt, this.frameOnGround, this.frameNoGravity, this.frameDeltaY, this.frameFallDistance, this.frameJumpPressed);
            state.lastFrameId = this.frameId;
        }
        return class_3532.method_16439((float)f, (float)state.prevValue, (float)state.value);
    }

    private void reset() {
        this.lastUpdateTime = System.nanoTime();
        this.frameValid = false;
        this.frameId = 0L;
        this.states.clear();
    }

    public void resetHand(class_1268 class_12682) {
        State state;
        if (class_12682 != null && (state = this.states.get(class_12682)) != null) {
            state.reset();
        }
    }

    private static void applyCameraAlignedScale(class_4587 class_45872, float f, float f2) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1773 != null && class_3102.field_1773.method_19418() != null) {
            Quaternionf quaternionf = class_3102.field_1773.method_19418().method_23767();
            if (quaternionf == null) {
                class_45872.method_22905(f, f2, f);
            } else {
                class_45872.method_22907(quaternionf);
                class_45872.method_22905(f, f2, f);
                class_45872.method_22907(new Quaternionf((Quaternionfc)quaternionf).invert());
            }
        } else {
            class_45872.method_22905(f, f2, f);
        }
    }

    static final class State {
        float value;
        float velocity;
        float prevValue;
        float lastFallSpeed;
        float lastFallDistance;
        boolean prevOnGround = true;
        long lastFrameId = -1L;

        State() {
        }

        void reset() {
            this.value = 0.0f;
            this.velocity = 0.0f;
            this.prevValue = 0.0f;
            this.lastFallSpeed = 0.0f;
            this.lastFallDistance = 0.0f;
            this.prevOnGround = true;
            this.lastFrameId = -1L;
        }

        void update(float f, boolean bl, boolean bl2, float f2, float f3, boolean bl3) {
            float f4;
            float f5;
            this.prevValue = this.value;
            if (this.prevOnGround && !bl) {
                f5 = bl3 ? -1.8f : -1.17f;
                this.velocity += f5;
            } else if (!this.prevOnGround && bl) {
                f5 = Math.max(class_3532.method_15363((float)(this.lastFallDistance / 6.0f), (float)0.0f, (float)1.0f), class_3532.method_15363((float)(this.lastFallSpeed / 0.6f), (float)0.0f, (float)1.0f));
                this.velocity += -9.6f * (0.6f + 0.7f * f5);
                this.lastFallSpeed = 0.0f;
                this.lastFallDistance = 0.0f;
            }
            this.prevOnGround = bl;
            f5 = 0.0f;
            if (!bl && !bl2) {
                if (f2 < -0.001f) {
                    f4 = class_3532.method_15363((float)(-f2), (float)0.0f, (float)0.6f);
                    float f6 = f4 / 0.6f;
                    float f7 = class_3532.method_15363((float)(f3 / 6.0f), (float)0.0f, (float)1.0f);
                    f5 = 0.6f * Math.max(f6, f7);
                    this.lastFallSpeed = Math.max(this.lastFallSpeed, f4);
                    this.lastFallDistance = Math.max(this.lastFallDistance, f3);
                } else if (f2 > 0.001f) {
                    f4 = class_3532.method_15363((float)f2, (float)0.0f, (float)0.6f);
                    float f8 = f4 / 0.6f;
                    f5 = -0.45f * f8;
                }
            }
            f4 = -192.375f * (this.value - f5) - 32.90625f * this.velocity;
            this.velocity += f4 * f;
            this.value += this.velocity * f;
            this.value = class_3532.method_15363((float)this.value, (float)-0.9f, (float)0.9f);
            if (Math.abs(this.value) < 1.0E-4f && Math.abs(this.velocity) < 1.0E-4f && f5 == 0.0f) {
                this.value = 0.0f;
                this.velocity = 0.0f;
            }
        }
    }
}

