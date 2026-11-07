package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.Random;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import ru.method.celebression.modules.impl.render.cosmetic.PetGroundSampler;

public final class PetWanderController {
    private static final double TICK_DT = 0.05;
    private class_243 pos = null;
    private class_243 prevPos = null;
    private float bodyYaw = 0.0f;
    private float prevBodyYaw = 0.0f;
    private float headYaw = 0.0f;
    private float prevHeadYaw = 0.0f;
    private float speedBps = 0.0f;
    private float limbPosDelta = 0.0f;
    private class_243 wanderTarget = null;
    private int wanderCooldownTicks = 0;
    private boolean following = false;
    private int lastAge = -1;
    private boolean jumping = false;
    private double jumpVy = 0.0;
    private double jumpTargetY = 0.0;
    private final Random rnd = new Random();

    public boolean hasPos() {
        return this.pos != null;
    }

    public class_243 pos() {
        return this.pos;
    }

    public float bodyYaw() {
        return this.bodyYaw;
    }

    public float headYaw() {
        return this.headYaw;
    }

    public class_243 posLerp(float f) {
        if (this.pos == null) {
            return null;
        }
        if (this.prevPos == null) {
            return this.pos;
        }
        return new class_243(PetWanderController.lerp(this.prevPos.field_1352, this.pos.field_1352, f), PetWanderController.lerp(this.prevPos.field_1351, this.pos.field_1351, f), PetWanderController.lerp(this.prevPos.field_1350, this.pos.field_1350, f));
    }

    public float bodyYawLerp(float f) {
        return PetWanderController.lerpAngle(this.prevBodyYaw, this.bodyYaw, f);
    }

    public float headYawLerp(float f) {
        return PetWanderController.lerpAngle(this.prevHeadYaw, this.headYaw, f);
    }

    public float limbPosDelta() {
        return this.limbPosDelta;
    }

    public void reset() {
        this.pos = null;
        this.prevPos = null;
        this.wanderTarget = null;
        this.wanderCooldownTicks = 0;
        this.speedBps = 0.0f;
        this.limbPosDelta = 0.0f;
        this.following = false;
        this.lastAge = -1;
        this.jumping = false;
        this.jumpVy = 0.0;
    }

    public int frame(class_1937 class_19372, double d, double d2, double d3, float f, double d4, int n) {
        int n2;
        if (class_19372 == null) {
            return 0;
        }
        if (this.pos == null) {
            this.seed(class_19372, d, d2, d3);
            this.lastAge = n;
            return 0;
        }
        if (n == this.lastAge) {
            return 0;
        }
        int n3 = n2 = this.lastAge < 0 ? 1 : n - this.lastAge;
        if (n2 < 1) {
            this.lastAge = n;
            return 0;
        }
        n2 = Math.min(2, n2);
        this.lastAge = n;
        for (int i = 0; i < n2; ++i) {
            this.tickStep(class_19372, d, d2, d3, f, d4);
        }
        return n2;
    }

    private void seed(class_1937 class_19372, double d, double d2, double d3) {
        double d4 = PetGroundSampler.sampleGroundY(class_19372, d, d3, d2);
        this.prevPos = this.pos = new class_243(d, Double.isNaN(d4) ? d2 : d4, d3);
        this.prevHeadYaw = 0.0f;
        this.prevBodyYaw = 0.0f;
        this.headYaw = 0.0f;
        this.bodyYaw = 0.0f;
        this.speedBps = 0.0f;
        this.limbPosDelta = 0.0f;
        this.jumping = false;
    }

    private void tickStep(class_1937 class_19372, double d, double d2, double d3, float f, double d4) {
        double d5;
        double d6;
        boolean bl;
        double d7;
        double d8;
        double d9;
        double d10;
        double d11;
        class_243 class_2432;
        double d12;
        double d13;
        this.prevPos = this.pos;
        this.prevBodyYaw = this.bodyYaw;
        this.prevHeadYaw = this.headYaw;
        double d14 = d - this.pos.field_1352;
        double d15 = d3 - this.pos.field_1350;
        double d16 = Math.sqrt(d14 * d14 + d15 * d15);
        if (d16 > 16.0) {
            double d17 = PetGroundSampler.sampleGroundY(class_19372, d, d3, d2);
            this.prevPos = this.pos = new class_243(d + (this.rnd.nextDouble() - 0.5), Double.isNaN(d17) ? d2 : d17, d3 + (this.rnd.nextDouble() - 0.5));
            this.wanderTarget = null;
            this.following = false;
            this.speedBps = 0.0f;
            this.jumping = false;
            this.limbPosDelta = 0.0f;
            return;
        }
        double d18 = 1.3 * (double)class_3532.method_15363((float)f, (float)0.4f, (float)3.0f);
        double d19 = Math.max(2.4, d4 + 1.0);
        double d20 = Math.max(1.1, d4 - 0.2);
        if (d16 > d19) {
            this.following = true;
        } else if (this.following && d16 < d20) {
            this.following = false;
        }
        if (this.following) {
            d13 = d16 > 1.0E-4 ? 1.0 / d16 : 0.0;
            d12 = d20 * 0.85;
            class_2432 = new class_243(d - d14 * d13 * d12, d2, d3 - d15 * d13 * d12);
            this.wanderTarget = null;
            d11 = Math.max(0.0, d16 - d20);
            d10 = Math.min(d18 * (2.4 + class_3532.method_15350((double)(d11 * 1.2), (double)0.0, (double)3.6)), 7.0);
        } else {
            if (this.wanderTarget == null) {
                if (this.wanderCooldownTicks > 0) {
                    --this.wanderCooldownTicks;
                } else {
                    this.pickWander(class_19372, d, d2, d3, d4);
                }
            } else if (PetWanderController.horizDist(this.pos, this.wanderTarget) < 0.4) {
                this.wanderTarget = null;
                this.wanderCooldownTicks = 40 + this.rnd.nextInt(70);
            }
            class_2432 = this.wanderTarget;
            d10 = d18;
        }
        d13 = 0.0;
        d12 = 0.0;
        d11 = 0.0;
        if (class_2432 != null && (d9 = Math.sqrt((d8 = class_2432.field_1352 - this.pos.field_1352) * d8 + (d7 = class_2432.field_1350 - this.pos.field_1350) * d7)) > 0.35) {
            d13 = d8 / d9;
            d12 = d7 / d9;
            d11 = d10;
        }
        float f2 = d11 > (double)this.speedBps ? 0.25f : 0.2f;
        this.speedBps = PetWanderController.approach(this.speedBps, (float)d11, 0.05f, f2);
        double d21 = PetWanderController.groundOr(class_19372, this.pos.field_1352, this.pos.field_1350, d2);
        double d22 = Double.NaN;
        boolean bl2 = bl = (d13 != 0.0 || d12 != 0.0) && this.speedBps > 0.05f;
        if (bl) {
            d6 = (double)this.speedBps * 0.05;
            d5 = this.pos.field_1352 + d13 * d6;
            double d23 = this.pos.field_1350 + d12 * d6;
            double d24 = PetGroundSampler.sampleGroundY(class_19372, d5, d23, d21);
            d22 = PetGroundSampler.sampleGroundY(class_19372, this.pos.field_1352 + d13 * 0.6, this.pos.field_1350 + d12 * 0.6, d21);
            if (!Double.isNaN(d24) && d24 - d21 <= 1.05) {
                this.pos = new class_243(d5, this.pos.field_1351, d23);
                this.bodyYaw = PetWanderController.lerpAngle(this.bodyYaw, PetWanderController.dirToYaw(d13, d12), 1.0f - (float)Math.exp(-0.2777777777777778));
            } else {
                if (!this.following) {
                    this.wanderTarget = null;
                    this.wanderCooldownTicks = 24;
                }
                this.speedBps = PetWanderController.approach(this.speedBps, 0.0f, 0.05f, 0.12f);
            }
        }
        d21 = PetGroundSampler.sampleGroundY(class_19372, this.pos.field_1352, this.pos.field_1350, d2);
        if (!this.jumping && bl && !Double.isNaN(d22) && !Double.isNaN(d21) && (d6 = d22 - d21) >= 0.55 && d6 <= 1.2 && Math.abs(this.pos.field_1351 - d21) < 0.25) {
            this.jumping = true;
            this.jumpVy = 0.5;
            this.jumpTargetY = d22;
        }
        if (this.jumping) {
            this.jumpVy -= 0.08;
            d6 = this.pos.field_1351 + this.jumpVy;
            if (this.jumpVy < 0.0 && d6 <= this.jumpTargetY) {
                d6 = this.jumpTargetY;
                this.jumping = false;
                this.jumpVy = 0.0;
            }
            this.pos = new class_243(this.pos.field_1352, d6, this.pos.field_1350);
        } else if (!Double.isNaN(d21)) {
            d6 = d21 > this.pos.field_1351 ? 0.08 : 0.14;
            d5 = this.pos.field_1351 + (d21 - this.pos.field_1351) * (1.0 - Math.exp(-0.05 / d6));
            if (d5 < d21 - 0.08) {
                d5 = d21;
            }
            this.pos = new class_243(this.pos.field_1352, d5, this.pos.field_1350);
        }
        float f3 = PetWanderController.dirToYaw(d - this.pos.field_1352, d3 - this.pos.field_1350);
        float f4 = class_3532.method_15363((float)class_3532.method_15393((float)(f3 - this.bodyYaw)), (float)-55.0f, (float)55.0f);
        this.headYaw = PetWanderController.lerpAngle(this.headYaw, this.bodyYaw + f4, 1.0f - (float)Math.exp(-0.3125));
        if (d13 == 0.0 && d12 == 0.0) {
            float f5 = class_3532.method_15363((float)class_3532.method_15393((float)(f3 - this.bodyYaw)), (float)-35.0f, (float)35.0f);
            this.bodyYaw = PetWanderController.lerpAngle(this.bodyYaw, this.bodyYaw + f5, 1.0f - (float)Math.exp(-0.08333333333333334));
        }
        this.limbPosDelta = (float)PetWanderController.horizDist(this.prevPos, this.pos);
    }

    private void pickWander(class_1937 class_19372, double d, double d2, double d3, double d4) {
        if (this.rnd.nextFloat() < 0.6f) {
            this.wanderTarget = null;
            this.wanderCooldownTicks = 50 + this.rnd.nextInt(80);
            return;
        }
        for (int i = 0; i < 12; ++i) {
            double d5;
            double d6;
            double d7;
            double d8 = this.rnd.nextDouble() * Math.PI * 2.0;
            double d9 = 0.9 + this.rnd.nextDouble() * Math.max(0.4, d4 * 0.9);
            double d10 = d + Math.cos(d8) * d9;
            double d11 = d10 - this.pos.field_1352;
            if (d11 * d11 + (d7 = (d6 = d3 + Math.sin(d8) * d9) - this.pos.field_1350) * d7 < 0.64 || Double.isNaN(d5 = PetGroundSampler.sampleGroundY(class_19372, d10, d6, d2)) || !(Math.abs(d5 - d2) <= 2.0)) continue;
            this.wanderTarget = new class_243(d10, d5, d6);
            this.wanderCooldownTicks = 60 + this.rnd.nextInt(80);
            return;
        }
        this.wanderTarget = null;
        this.wanderCooldownTicks = 30;
    }

    private static double groundOr(class_1937 class_19372, double d, double d2, double d3) {
        double d4 = PetGroundSampler.sampleGroundY(class_19372, d, d2, d3);
        return Double.isNaN(d4) ? d3 : d4;
    }

    private static double horizDist(class_243 class_2432, class_243 class_2433) {
        double d = class_2432.field_1352 - class_2433.field_1352;
        double d2 = class_2432.field_1350 - class_2433.field_1350;
        return Math.sqrt(d * d + d2 * d2);
    }

    private static double lerp(double d, double d2, float f) {
        return d + (d2 - d) * (double)f;
    }

    private static float approach(float f, float f2, float f3, float f4) {
        return f + (f2 - f) * (1.0f - (float)Math.exp(-f3 / Math.max(0.02f, f4)));
    }

    private static float dirToYaw(double d, double d2) {
        return (float)(class_3532.method_15349((double)d2, (double)d) * 57.29577951308232) - 90.0f;
    }

    private static float lerpAngle(float f, float f2, float f3) {
        float f4 = class_3532.method_15393((float)(f2 - f));
        return f + f4 * class_3532.method_15363((float)f3, (float)0.0f, (float)1.0f);
    }
}

