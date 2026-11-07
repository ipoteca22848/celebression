package ru.method.celebression.punchy.client.render;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1819;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_746;
import net.minecraft.class_7833;

public class ShieldImpactPhysics {
    private static final ShieldImpactPhysics INSTANCE = new ShieldImpactPhysics();
    private static final int IMPACT_TICKS = 5;
    private static final float IMPACT_ROT_X = -18.0f;
    private static final float IMPACT_ROT_Z = 4.0f;
    private static final float IMPACT_OFF_Z = -0.12f;
    private static final float IMPACT_OFF_Y = -0.02f;
    private long startTick = -1L;
    private class_1306 impactArm = class_1306.field_6183;
    private float jitterPhase = 0.0f;

    public static ShieldImpactPhysics get() {
        return INSTANCE;
    }

    public void trigger(class_746 class_7462) {
        class_1306 class_13062 = ShieldImpactPhysics.resolveImpactArm(class_7462);
        if (class_13062 != null) {
            this.trigger(class_13062);
        }
    }

    public void trigger(class_1306 class_13062) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102.field_1687 != null) {
            this.startTick = class_3102.field_1687.method_8510();
            this.impactArm = class_13062;
            this.jitterPhase = (float)(Math.random() * Math.PI * 2.0);
        }
    }

    public static class_1306 resolveImpactArm(class_746 class_7462) {
        if (class_7462 == null) {
            return null;
        }
        class_1306 class_13062 = class_7462.method_6068();
        if (class_7462.method_6079().method_7909() instanceof class_1819) {
            class_13062 = class_13062 == class_1306.field_6183 ? class_1306.field_6182 : class_1306.field_6183;
        } else if (class_7462.method_6047().method_7909() instanceof class_1819) {
            class_13062 = class_7462.method_6068();
        } else {
            boolean bl;
            if (!class_7462.method_6115() || !(class_7462.method_6030().method_7909() instanceof class_1819)) {
                return null;
            }
            class_1268 class_12682 = class_7462.method_6058();
            boolean bl2 = bl = class_7462.method_6068() == class_1306.field_6183;
            class_13062 = class_12682 == class_1268.field_5808 ? (bl ? class_1306.field_6183 : class_1306.field_6182) : (bl ? class_1306.field_6182 : class_1306.field_6183);
        }
        return class_13062;
    }

    public void applyTo(class_4587 class_45872, class_1306 class_13062, float f) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102.field_1687 != null && this.startTick >= 0L && class_13062 == this.impactArm) {
            long l = class_3102.field_1687.method_8510();
            long l2 = l - this.startTick;
            if (l2 < 0L) {
                l2 = 0L;
            }
            if (l2 < 5L) {
                float f2 = ((float)l2 + f) / 5.0f;
                if (f2 > 1.0f) {
                    f2 = 1.0f;
                }
                float f3 = f2 < 0.4f ? f2 / 0.4f : 1.0f - (f2 - 0.4f) / 0.6f;
                f3 = f3 * f3 * (3.0f - 2.0f * f3);
                float f4 = 1.0f + 0.05f * (float)Math.sin(this.jitterPhase + f2 * (float)Math.PI * 2.0f);
                float f5 = f3 * f4;
                float f6 = class_13062 == class_1306.field_6182 ? -1.0f : 1.0f;
                float f7 = -18.0f * f5;
                float f8 = 4.0f * f5 * f6;
                float f9 = -0.12f * f5;
                float f10 = -0.02f * f5;
                class_45872.method_46416(0.0f, f10, f9);
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(f7));
                class_45872.method_22907(class_7833.field_40718.rotationDegrees(f8));
            }
        }
    }
}

