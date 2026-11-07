package ru.method.celebression.punchy.client.input;

import java.util.Locale;
import net.minecraft.class_1657;
import net.minecraft.class_2561;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.debug.PunchyDebug;
import ru.method.celebression.punchy.client.debug.PunchyTuningIO;
import ru.method.celebression.punchy.platform.Services;

public class PunchyKeybinds {
    private static final String CATEGORY = "key.categories.punchy.tuning";
    private static class_304 axisXKey;
    private static class_304 axisYKey;
    private static class_304 axisZKey;
    private static class_304 toggleModeKey;
    private static class_304 targetArmRightKey;
    private static class_304 targetArmLeftKey;
    private static class_304 targetItemRightKey;
    private static class_304 targetItemLeftKey;
    private static class_304 increaseKey;
    private static class_304 decreaseKey;
    private static class_304 resetKey;
    private static class_304 printKey;
    private static class_304 saveKey;
    private static class_304 togglePivotKey;
    private static Mode mode;
    private static Axis axis;
    private static Target target;

    public static void register() {
        axisXKey = new class_304("key.punchy.tuning.axis_x", 324, "key.categories.punchy.tuning");
        axisYKey = new class_304("key.punchy.tuning.axis_y", 325, "key.categories.punchy.tuning");
        axisZKey = new class_304("key.punchy.tuning.axis_z", 326, "key.categories.punchy.tuning");
        toggleModeKey = new class_304("key.punchy.tuning.toggle_mode", 261, "key.categories.punchy.tuning");
        targetArmRightKey = new class_304("key.punchy.tuning.target_arm_right", 327, "key.categories.punchy.tuning");
        targetArmLeftKey = new class_304("key.punchy.tuning.target_arm_left", 328, "key.categories.punchy.tuning");
        targetItemRightKey = new class_304("key.punchy.tuning.target_item_right", 329, "key.categories.punchy.tuning");
        targetItemLeftKey = new class_304("key.punchy.tuning.target_item_left", 320, "key.categories.punchy.tuning");
        increaseKey = new class_304("key.punchy.tuning.increase", 265, "key.categories.punchy.tuning");
        decreaseKey = new class_304("key.punchy.tuning.decrease", 264, "key.categories.punchy.tuning");
        resetKey = new class_304("key.punchy.tuning.reset", 82, "key.categories.punchy.tuning");
        printKey = new class_304("key.punchy.tuning.print", 335, "key.categories.punchy.tuning");
        saveKey = new class_304("key.punchy.tuning.save_session", 295, "key.categories.punchy.tuning");
        togglePivotKey = new class_304("key.punchy.tuning.toggle_pivot", 296, "key.categories.punchy.tuning");
        Services.CLIENT.registerKeyMapping(axisXKey);
        Services.CLIENT.registerKeyMapping(axisYKey);
        Services.CLIENT.registerKeyMapping(axisZKey);
        Services.CLIENT.registerKeyMapping(toggleModeKey);
        Services.CLIENT.registerKeyMapping(targetArmRightKey);
        Services.CLIENT.registerKeyMapping(targetArmLeftKey);
        Services.CLIENT.registerKeyMapping(targetItemRightKey);
        Services.CLIENT.registerKeyMapping(targetItemLeftKey);
        Services.CLIENT.registerKeyMapping(increaseKey);
        Services.CLIENT.registerKeyMapping(decreaseKey);
        Services.CLIENT.registerKeyMapping(resetKey);
        Services.CLIENT.registerKeyMapping(printKey);
        Services.CLIENT.registerKeyMapping(saveKey);
        Services.CLIENT.registerKeyMapping(togglePivotKey);
        Services.CLIENT.registerClientTickEnd(PunchyKeybinds::handleTick);
    }

    private static void handleTick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && PunchyDebug.DEBUG_POSITION_ENABLED && class_3102.field_1755 == null) {
            boolean bl;
            class_746 class_7462 = class_3102.field_1724;
            while (axisXKey.method_1436()) {
                axis = Axis.X;
                PunchyKeybinds.toast((class_1657)class_7462, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.axis", (Object[])new Object[]{PunchyKeybinds.axisLabel(axis)}));
            }
            while (axisYKey.method_1436()) {
                axis = Axis.Y;
                PunchyKeybinds.toast((class_1657)class_7462, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.axis", (Object[])new Object[]{PunchyKeybinds.axisLabel(axis)}));
            }
            while (axisZKey.method_1436()) {
                axis = Axis.Z;
                PunchyKeybinds.toast((class_1657)class_7462, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.axis", (Object[])new Object[]{PunchyKeybinds.axisLabel(axis)}));
            }
            while (toggleModeKey.method_1436()) {
                mode = switch (mode.ordinal()) {
                    default -> throw new IllegalStateException("Unexpected enum value");
                    case 0 -> Mode.ROTATE;
                    case 1 -> Mode.SCALE;
                    case 2 -> Mode.PIVOT;
                    case 3 -> Mode.TRANSLATE;
                };
                PunchyKeybinds.toast((class_1657)class_7462, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.mode", (Object[])new Object[]{PunchyKeybinds.modeLabel(mode)}));
            }
            while (targetArmRightKey.method_1436()) {
                target = Target.ARM_RIGHT;
                PunchyKeybinds.toast((class_1657)class_7462, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.target", (Object[])new Object[]{PunchyKeybinds.targetLabel(target)}));
            }
            while (targetArmLeftKey.method_1436()) {
                target = Target.ARM_LEFT;
                PunchyKeybinds.toast((class_1657)class_7462, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.target", (Object[])new Object[]{PunchyKeybinds.targetLabel(target)}));
            }
            while (targetItemRightKey.method_1436()) {
                target = Target.ITEM_RIGHT;
                PunchyKeybinds.toast((class_1657)class_7462, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.target", (Object[])new Object[]{PunchyKeybinds.targetLabel(target)}));
            }
            while (targetItemLeftKey.method_1436()) {
                target = Target.ITEM_LEFT;
                PunchyKeybinds.toast((class_1657)class_7462, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.target", (Object[])new Object[]{PunchyKeybinds.targetLabel(target)}));
            }
            while (resetKey.method_1436()) {
                PunchyKeybinds.resetTarget();
                PunchyKeybinds.toast((class_1657)class_7462, PunchyKeybinds.formatOffset());
            }
            boolean bl2 = increaseKey.method_1434() && !decreaseKey.method_1434();
            boolean bl3 = bl = decreaseKey.method_1434() && !increaseKey.method_1434();
            if (bl2 || bl) {
                float f = PunchyKeybinds.computeStep(class_3102, mode);
                if (bl) {
                    f *= -1.0f;
                }
                PunchyKeybinds.applyDelta(f, mode);
                PunchyKeybinds.toast((class_1657)class_7462, PunchyKeybinds.formatOffset());
            }
            while (printKey.method_1436()) {
                class_7462.method_7353(PunchyKeybinds.formatOffset(), false);
            }
            while (saveKey.method_1436()) {
                PunchyTuningIO.saveSession();
                class_7462.method_7353((class_2561)class_2561.method_43469((String)"punchy.tuning.toast.saved_session", (Object[])new Object[]{PunchyTuningIO.getSessionPath()}), false);
            }
            while (togglePivotKey.method_1436()) {
                PunchyDebug.SHOW_GRIP_PIVOT = !PunchyDebug.SHOW_GRIP_PIVOT;
                PunchyKeybinds.toast((class_1657)class_7462, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.grip_pivot", (Object[])new Object[]{PunchyDebug.SHOW_GRIP_PIVOT ? class_2561.method_43471((String)"punchy.toggle.on") : class_2561.method_43471((String)"punchy.toggle.off")}));
            }
        }
    }

    private static float computeStep(class_310 class_3102, Mode mode) {
        float f;
        float f2 = f = mode == Mode.ROTATE ? 1.0f : 0.05f;
        if (mode == Mode.SCALE || mode == Mode.PIVOT) {
            f = 0.05f;
        }
        if (class_3102.field_1690 != null) {
            if (class_3102.field_1690.field_1867.method_1434()) {
                f *= 5.0f;
            }
            if (class_3102.field_1690.field_1832.method_1434()) {
                f *= 0.2f;
            }
        }
        return f;
    }

    private static void applyDelta(float f, Mode mode) {
        if (mode == Mode.SCALE) {
            PunchyKeybinds.addScale(f);
        } else if (mode == Mode.PIVOT) {
            PunchyKeybinds.addPivot(f);
        } else {
            switch (axis.ordinal()) {
                case 0: {
                    if (mode == Mode.ROTATE) {
                        PunchyKeybinds.addRotX(f);
                        break;
                    }
                    PunchyKeybinds.addPosX(f);
                    break;
                }
                case 1: {
                    if (mode == Mode.ROTATE) {
                        PunchyKeybinds.addRotY(f);
                        break;
                    }
                    PunchyKeybinds.addPosY(f);
                    break;
                }
                case 2: {
                    if (mode == Mode.ROTATE) {
                        PunchyKeybinds.addRotZ(f);
                        break;
                    }
                    PunchyKeybinds.addPosZ(f);
                }
            }
        }
    }

    private static class_2561 formatOffset() {
        float f = PunchyKeybinds.getPosX();
        float f2 = PunchyKeybinds.getPosY();
        float f3 = PunchyKeybinds.getPosZ();
        float f4 = PunchyKeybinds.getRotX();
        float f5 = PunchyKeybinds.getRotY();
        float f6 = PunchyKeybinds.getRotZ();
        float f7 = PunchyKeybinds.getScale();
        float f8 = PunchyKeybinds.getPivotX();
        float f9 = PunchyKeybinds.getPivotY();
        float f10 = PunchyKeybinds.getPivotZ();
        String string = String.format(Locale.ROOT, "%.3f", Float.valueOf(f));
        String string2 = String.format(Locale.ROOT, "%.3f", Float.valueOf(f2));
        String string3 = String.format(Locale.ROOT, "%.3f", Float.valueOf(f3));
        String string4 = String.format(Locale.ROOT, "%.1f", Float.valueOf(f4));
        String string5 = String.format(Locale.ROOT, "%.1f", Float.valueOf(f5));
        String string6 = String.format(Locale.ROOT, "%.1f", Float.valueOf(f6));
        String string7 = String.format(Locale.ROOT, "%.3f", Float.valueOf(f7));
        String string8 = String.format(Locale.ROOT, "%.3f", Float.valueOf(f8));
        String string9 = String.format(Locale.ROOT, "%.3f", Float.valueOf(f9));
        String string10 = String.format(Locale.ROOT, "%.3f", Float.valueOf(f10));
        return class_2561.method_43469((String)"punchy.tuning.offset_status", (Object[])new Object[]{PunchyKeybinds.targetLabel(target), PunchyKeybinds.modeLabel(mode), PunchyKeybinds.axisLabel(axis), string, string2, string3, string4, string5, string6, string7, string8, string9, string10});
    }

    private static void resetTarget() {
        switch (target.ordinal()) {
            case 0: {
                PunchyDebug.ARM_R_POS_X = 0.0f;
                PunchyDebug.ARM_R_POS_Y = 0.0f;
                PunchyDebug.ARM_R_POS_Z = 0.0f;
                PunchyDebug.ARM_R_ROT_X = 0.0f;
                PunchyDebug.ARM_R_ROT_Y = 0.0f;
                PunchyDebug.ARM_R_ROT_Z = 0.0f;
                PunchyDebug.ARM_R_SCALE_X = 1.0f;
                PunchyDebug.ARM_R_SCALE_Y = 1.0f;
                PunchyDebug.ARM_R_SCALE_Z = 1.0f;
                break;
            }
            case 1: {
                PunchyDebug.ARM_L_POS_X = 0.0f;
                PunchyDebug.ARM_L_POS_Y = 0.0f;
                PunchyDebug.ARM_L_POS_Z = 0.0f;
                PunchyDebug.ARM_L_ROT_X = 0.0f;
                PunchyDebug.ARM_L_ROT_Y = 0.0f;
                PunchyDebug.ARM_L_ROT_Z = 0.0f;
                PunchyDebug.ARM_L_SCALE_X = 1.0f;
                PunchyDebug.ARM_L_SCALE_Y = 1.0f;
                PunchyDebug.ARM_L_SCALE_Z = 1.0f;
                break;
            }
            case 2: {
                PunchyDebug.ITEM_R_POS_X = 0.0f;
                PunchyDebug.ITEM_R_POS_Y = 0.0f;
                PunchyDebug.ITEM_R_POS_Z = 0.0f;
                PunchyDebug.ITEM_R_ROT_X = 0.0f;
                PunchyDebug.ITEM_R_ROT_Y = 0.0f;
                PunchyDebug.ITEM_R_ROT_Z = 0.0f;
                PunchyDebug.ITEM_R_SCALE_X = 1.0f;
                PunchyDebug.ITEM_R_SCALE_Y = 1.0f;
                PunchyDebug.ITEM_R_SCALE_Z = 1.0f;
                PunchyDebug.ITEM_R_PIVOT_X = 0.0f;
                PunchyDebug.ITEM_R_PIVOT_Y = 0.0f;
                PunchyDebug.ITEM_R_PIVOT_Z = 0.0f;
                break;
            }
            case 3: {
                PunchyDebug.ITEM_L_POS_X = 0.0f;
                PunchyDebug.ITEM_L_POS_Y = 0.0f;
                PunchyDebug.ITEM_L_POS_Z = 0.0f;
                PunchyDebug.ITEM_L_ROT_X = 0.0f;
                PunchyDebug.ITEM_L_ROT_Y = 0.0f;
                PunchyDebug.ITEM_L_ROT_Z = 0.0f;
                PunchyDebug.ITEM_L_SCALE_X = 1.0f;
                PunchyDebug.ITEM_L_SCALE_Y = 1.0f;
                PunchyDebug.ITEM_L_SCALE_Z = 1.0f;
                PunchyDebug.ITEM_L_PIVOT_X = 0.0f;
                PunchyDebug.ITEM_L_PIVOT_Y = 0.0f;
                PunchyDebug.ITEM_L_PIVOT_Z = 0.0f;
            }
        }
    }

    private static float getPosX() {
        return switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> PunchyDebug.ARM_R_POS_X;
            case 1 -> PunchyDebug.ARM_L_POS_X;
            case 2 -> PunchyDebug.ITEM_R_POS_X;
            case 3 -> PunchyDebug.ITEM_L_POS_X;
        };
    }

    private static float getPosY() {
        return switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> PunchyDebug.ARM_R_POS_Y;
            case 1 -> PunchyDebug.ARM_L_POS_Y;
            case 2 -> PunchyDebug.ITEM_R_POS_Y;
            case 3 -> PunchyDebug.ITEM_L_POS_Y;
        };
    }

    private static float getPosZ() {
        return switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> PunchyDebug.ARM_R_POS_Z;
            case 1 -> PunchyDebug.ARM_L_POS_Z;
            case 2 -> PunchyDebug.ITEM_R_POS_Z;
            case 3 -> PunchyDebug.ITEM_L_POS_Z;
        };
    }

    private static float getRotX() {
        return switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> PunchyDebug.ARM_R_ROT_X;
            case 1 -> PunchyDebug.ARM_L_ROT_X;
            case 2 -> PunchyDebug.ITEM_R_ROT_X;
            case 3 -> PunchyDebug.ITEM_L_ROT_X;
        };
    }

    private static float getRotY() {
        return switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> PunchyDebug.ARM_R_ROT_Y;
            case 1 -> PunchyDebug.ARM_L_ROT_Y;
            case 2 -> PunchyDebug.ITEM_R_ROT_Y;
            case 3 -> PunchyDebug.ITEM_L_ROT_Y;
        };
    }

    private static float getRotZ() {
        return switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> PunchyDebug.ARM_R_ROT_Z;
            case 1 -> PunchyDebug.ARM_L_ROT_Z;
            case 2 -> PunchyDebug.ITEM_R_ROT_Z;
            case 3 -> PunchyDebug.ITEM_L_ROT_Z;
        };
    }

    private static float getScale() {
        return switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> PunchyDebug.ARM_R_SCALE_X;
            case 1 -> PunchyDebug.ARM_L_SCALE_X;
            case 2 -> PunchyDebug.ITEM_R_SCALE_X;
            case 3 -> PunchyDebug.ITEM_L_SCALE_X;
        };
    }

    private static float getPivotX() {
        return switch (target.ordinal()) {
            case 2 -> PunchyDebug.ITEM_R_PIVOT_X;
            case 3 -> PunchyDebug.ITEM_L_PIVOT_X;
            default -> 0.0f;
        };
    }

    private static float getPivotY() {
        return switch (target.ordinal()) {
            case 2 -> PunchyDebug.ITEM_R_PIVOT_Y;
            case 3 -> PunchyDebug.ITEM_L_PIVOT_Y;
            default -> 0.0f;
        };
    }

    private static float getPivotZ() {
        return switch (target.ordinal()) {
            case 2 -> PunchyDebug.ITEM_R_PIVOT_Z;
            case 3 -> PunchyDebug.ITEM_L_PIVOT_Z;
            default -> 0.0f;
        };
    }

    private static void addPosX(float f) {
        switch (target.ordinal()) {
            case 0: {
                PunchyDebug.ARM_R_POS_X += f;
                break;
            }
            case 1: {
                PunchyDebug.ARM_L_POS_X += f;
                break;
            }
            case 2: {
                PunchyDebug.ITEM_R_POS_X += f;
                break;
            }
            case 3: {
                PunchyDebug.ITEM_L_POS_X += f;
            }
        }
    }

    private static void addPosY(float f) {
        switch (target.ordinal()) {
            case 0: {
                PunchyDebug.ARM_R_POS_Y += f;
                break;
            }
            case 1: {
                PunchyDebug.ARM_L_POS_Y += f;
                break;
            }
            case 2: {
                PunchyDebug.ITEM_R_POS_Y += f;
                break;
            }
            case 3: {
                PunchyDebug.ITEM_L_POS_Y += f;
            }
        }
    }

    private static void addPosZ(float f) {
        switch (target.ordinal()) {
            case 0: {
                PunchyDebug.ARM_R_POS_Z += f;
                break;
            }
            case 1: {
                PunchyDebug.ARM_L_POS_Z += f;
                break;
            }
            case 2: {
                PunchyDebug.ITEM_R_POS_Z += f;
                break;
            }
            case 3: {
                PunchyDebug.ITEM_L_POS_Z += f;
            }
        }
    }

    private static void addRotX(float f) {
        switch (target.ordinal()) {
            case 0: {
                PunchyDebug.ARM_R_ROT_X += f;
                break;
            }
            case 1: {
                PunchyDebug.ARM_L_ROT_X += f;
                break;
            }
            case 2: {
                PunchyDebug.ITEM_R_ROT_X += f;
                break;
            }
            case 3: {
                PunchyDebug.ITEM_L_ROT_X += f;
            }
        }
    }

    private static void addRotY(float f) {
        switch (target.ordinal()) {
            case 0: {
                PunchyDebug.ARM_R_ROT_Y += f;
                break;
            }
            case 1: {
                PunchyDebug.ARM_L_ROT_Y += f;
                break;
            }
            case 2: {
                PunchyDebug.ITEM_R_ROT_Y += f;
                break;
            }
            case 3: {
                PunchyDebug.ITEM_L_ROT_Y += f;
            }
        }
    }

    private static void addRotZ(float f) {
        switch (target.ordinal()) {
            case 0: {
                PunchyDebug.ARM_R_ROT_Z += f;
                break;
            }
            case 1: {
                PunchyDebug.ARM_L_ROT_Z += f;
                break;
            }
            case 2: {
                PunchyDebug.ITEM_R_ROT_Z += f;
                break;
            }
            case 3: {
                PunchyDebug.ITEM_L_ROT_Z += f;
            }
        }
    }

    private static void addScale(float f) {
        switch (target.ordinal()) {
            case 0: {
                float f2;
                PunchyDebug.ARM_R_SCALE_X = f2 = PunchyKeybinds.clampScale(PunchyDebug.ARM_R_SCALE_X + f);
                PunchyDebug.ARM_R_SCALE_Y = f2;
                PunchyDebug.ARM_R_SCALE_Z = f2;
                break;
            }
            case 1: {
                float f3;
                PunchyDebug.ARM_L_SCALE_X = f3 = PunchyKeybinds.clampScale(PunchyDebug.ARM_L_SCALE_X + f);
                PunchyDebug.ARM_L_SCALE_Y = f3;
                PunchyDebug.ARM_L_SCALE_Z = f3;
                break;
            }
            case 2: {
                float f4;
                PunchyDebug.ITEM_R_SCALE_X = f4 = PunchyKeybinds.clampScale(PunchyDebug.ITEM_R_SCALE_X + f);
                PunchyDebug.ITEM_R_SCALE_Y = f4;
                PunchyDebug.ITEM_R_SCALE_Z = f4;
                break;
            }
            case 3: {
                float f5;
                PunchyDebug.ITEM_L_SCALE_X = f5 = PunchyKeybinds.clampScale(PunchyDebug.ITEM_L_SCALE_X + f);
                PunchyDebug.ITEM_L_SCALE_Y = f5;
                PunchyDebug.ITEM_L_SCALE_Z = f5;
            }
        }
    }

    private static void addPivot(float f) {
        switch (axis.ordinal()) {
            case 0: {
                PunchyKeybinds.addPivotX(f);
                break;
            }
            case 1: {
                PunchyKeybinds.addPivotY(f);
                break;
            }
            case 2: {
                PunchyKeybinds.addPivotZ(f);
            }
        }
    }

    private static void addPivotX(float f) {
        switch (target.ordinal()) {
            case 2: {
                PunchyDebug.ITEM_R_PIVOT_X += f;
                break;
            }
            case 3: {
                PunchyDebug.ITEM_L_PIVOT_X += f;
            }
        }
    }

    private static void addPivotY(float f) {
        switch (target.ordinal()) {
            case 2: {
                PunchyDebug.ITEM_R_PIVOT_Y += f;
                break;
            }
            case 3: {
                PunchyDebug.ITEM_L_PIVOT_Y += f;
            }
        }
    }

    private static void addPivotZ(float f) {
        switch (target.ordinal()) {
            case 2: {
                PunchyDebug.ITEM_R_PIVOT_Z += f;
                break;
            }
            case 3: {
                PunchyDebug.ITEM_L_PIVOT_Z += f;
            }
        }
    }

    private static float clampScale(float f) {
        return Math.max(0.01f, f);
    }

    private static void toast(class_1657 class_16572, class_2561 class_25612) {
        class_16572.method_7353(class_25612, true);
    }

    private static class_2561 axisLabel(Axis axis) {
        return switch (axis.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> class_2561.method_43471((String)"punchy.tuning.axis.x");
            case 1 -> class_2561.method_43471((String)"punchy.tuning.axis.y");
            case 2 -> class_2561.method_43471((String)"punchy.tuning.axis.z");
        };
    }

    private static class_2561 modeLabel(Mode mode) {
        return switch (mode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> class_2561.method_43471((String)"punchy.tuning.mode.translate");
            case 1 -> class_2561.method_43471((String)"punchy.tuning.mode.rotate");
            case 2 -> class_2561.method_43471((String)"punchy.tuning.mode.scale");
            case 3 -> class_2561.method_43471((String)"punchy.tuning.mode.pivot");
        };
    }

    private static class_2561 targetLabel(Target target) {
        return switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> class_2561.method_43471((String)"punchy.tuning.target.arm_right");
            case 1 -> class_2561.method_43471((String)"punchy.tuning.target.arm_left");
            case 2 -> class_2561.method_43471((String)"punchy.tuning.target.item_right");
            case 3 -> class_2561.method_43471((String)"punchy.tuning.target.item_left");
        };
    }

    static {
        mode = Mode.TRANSLATE;
        axis = Axis.X;
        target = Target.ARM_RIGHT;
    }

    static enum Axis {
        X,
        Y,
        Z;

    }

    static enum Mode {
        TRANSLATE,
        ROTATE,
        SCALE,
        PIVOT;

    }

    static enum Target {
        ARM_RIGHT,
        ARM_LEFT,
        ITEM_RIGHT,
        ITEM_LEFT;

    }
}

