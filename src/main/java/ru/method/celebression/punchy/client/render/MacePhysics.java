package ru.method.celebression.punchy.client.render;

import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1306;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_4587;
import net.minecraft.class_746;
import net.minecraft.class_7833;
import net.minecraft.class_9362;
import org.joml.Matrix4f;

public class MacePhysics {
    private static final MacePhysics INSTANCE = new MacePhysics();
    private static final float FALL_THRESHOLD = -0.15f;
    private static final float TARGET_PITCH_UP = -50.0f;
    private static final float BLEND_SPEED = 0.25f;
    private static final double MIN_FALL_DIST = 5.0;
    private float pitch;
    private float prevPitch;
    private float targetPitch;
    private double fallStartY = Double.NaN;

    public static MacePhysics get() {
        return INSTANCE;
    }

    public void tick(class_746 class_7462) {
        this.prevPitch = this.pitch;
        if (class_7462 == null) {
            this.pitch = 0.0f;
            this.targetPitch = 0.0f;
            this.fallStartY = Double.NaN;
        } else {
            boolean bl;
            boolean bl2 = bl = class_7462.method_18798().field_1351 < (double)-0.15f && !class_7462.method_24828() && !class_7462.method_5740();
            if (bl) {
                if (Double.isNaN(this.fallStartY)) {
                    this.fallStartY = class_7462.method_23318();
                }
            } else {
                this.fallStartY = Double.NaN;
            }
            double d = Double.isNaN(this.fallStartY) ? 0.0 : this.fallStartY - class_7462.method_23318();
            double d2 = this.getDistanceToGround(class_7462);
            double d3 = d + d2;
            boolean bl3 = this.isHoldingMace(class_7462);
            this.targetPitch = bl3 && bl && d3 > 5.0 ? -50.0f : 0.0f;
            this.pitch = class_3532.method_16439((float)0.25f, (float)this.pitch, (float)this.targetPitch);
        }
    }

    private double getDistanceToGround(class_746 class_7462) {
        class_243 class_2432 = class_7462.method_19538();
        class_243 class_2433 = class_2432.method_1031(0.0, -20.0, 0.0);
        class_3965 class_39652 = class_7462.method_37908().method_17742(new class_3959(class_2432, class_2433, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)class_7462));
        return class_39652.method_17783() == class_239.class_240.field_1333 ? 20.0 : class_2432.field_1351 - class_39652.method_17784().field_1351;
    }

    public void applyTo(class_4587 class_45872, class_746 class_7462, class_1306 class_13062, float f) {
        float f2 = class_3532.method_16439((float)f, (float)this.prevPitch, (float)this.pitch);
        if (class_7462 != null && !(Math.abs(f2) < 0.01f) && this.isArmHoldingMace(class_7462, class_13062)) {
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(f2));
        }
    }

    public void applyTo(Matrix4f matrix4f, class_746 class_7462, class_1306 class_13062, float f) {
        float f2 = class_3532.method_16439((float)f, (float)this.prevPitch, (float)this.pitch);
        if (class_7462 != null && !(Math.abs(f2) < 0.01f) && this.isArmHoldingMace(class_7462, class_13062)) {
            matrix4f.rotateX((float)Math.toRadians(f2));
        }
    }

    private boolean isHoldingMace(class_746 class_7462) {
        return class_7462.method_6047().method_7909() instanceof class_9362 || class_7462.method_6079().method_7909() instanceof class_9362;
    }

    private boolean isArmHoldingMace(class_746 class_7462, class_1306 class_13062) {
        boolean bl = class_7462.method_6068() == class_1306.field_6183;
        class_1268 class_12682 = class_13062 == class_1306.field_6183 == bl ? class_1268.field_5808 : class_1268.field_5810;
        return class_7462.method_5998(class_12682).method_7909() instanceof class_9362;
    }
}

