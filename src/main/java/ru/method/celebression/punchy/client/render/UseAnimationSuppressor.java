package ru.method.celebression.punchy.client.render;

import net.minecraft.class_1268;

public final class UseAnimationSuppressor {
    private static boolean mainHandSuppressed;
    private static boolean offHandSuppressed;
    private static boolean forced;
    private static boolean mainHandPendingReset;
    private static boolean offHandPendingReset;
    private static long mainHandReleaseStartTick;
    private static long offHandReleaseStartTick;
    private static int mainHandReleaseStartRemainingTicks;
    private static int offHandReleaseStartRemainingTicks;
    private static int mainHandMeshStartRemainingTicks;
    private static int offHandMeshStartRemainingTicks;

    private UseAnimationSuppressor() {
    }

    public static void suppress() {
        UseAnimationSuppressor.suppress(class_1268.field_5808);
        UseAnimationSuppressor.suppress(class_1268.field_5810);
    }

    public static void suppress(class_1268 class_12682) {
        if (class_12682 == class_1268.field_5810) {
            offHandSuppressed = true;
            offHandPendingReset = true;
            offHandReleaseStartTick = -1L;
            offHandReleaseStartRemainingTicks = -1;
        } else {
            mainHandSuppressed = true;
            mainHandPendingReset = true;
            mainHandReleaseStartTick = -1L;
            mainHandReleaseStartRemainingTicks = -1;
        }
    }

    public static void release(long l, int n) {
        UseAnimationSuppressor.release(class_1268.field_5808, l, n);
        UseAnimationSuppressor.release(class_1268.field_5810, l, n);
    }

    public static void release(class_1268 class_12682, long l, int n) {
        if (class_12682 == class_1268.field_5810) {
            offHandSuppressed = false;
            if (offHandPendingReset) {
                offHandReleaseStartTick = l;
                offHandReleaseStartRemainingTicks = n;
                offHandPendingReset = false;
            }
        } else {
            mainHandSuppressed = false;
            if (mainHandPendingReset) {
                mainHandReleaseStartTick = l;
                mainHandReleaseStartRemainingTicks = n;
                mainHandPendingReset = false;
            }
        }
    }

    public static void clear() {
        UseAnimationSuppressor.clear(class_1268.field_5808);
        UseAnimationSuppressor.clear(class_1268.field_5810);
    }

    public static void clear(class_1268 class_12682) {
        if (class_12682 == class_1268.field_5810) {
            offHandSuppressed = false;
            offHandPendingReset = false;
            offHandReleaseStartTick = -1L;
            offHandReleaseStartRemainingTicks = -1;
            offHandMeshStartRemainingTicks = -1;
        } else {
            mainHandSuppressed = false;
            mainHandPendingReset = false;
            mainHandReleaseStartTick = -1L;
            mainHandReleaseStartRemainingTicks = -1;
            mainHandMeshStartRemainingTicks = -1;
        }
    }

    public static boolean isSuppressed() {
        return mainHandSuppressed || offHandSuppressed;
    }

    public static boolean isSuppressed(class_1268 class_12682) {
        return class_12682 == class_1268.field_5810 ? offHandSuppressed : mainHandSuppressed;
    }

    public static void force(boolean bl) {
        forced = bl;
    }

    public static boolean isForced() {
        return forced;
    }

    public static long getReleaseStartTick() {
        return mainHandReleaseStartTick >= 0L ? mainHandReleaseStartTick : offHandReleaseStartTick;
    }

    public static long getReleaseStartTick(class_1268 class_12682) {
        return class_12682 == class_1268.field_5810 ? offHandReleaseStartTick : mainHandReleaseStartTick;
    }

    public static int getReleaseStartRemainingTicks() {
        return mainHandReleaseStartRemainingTicks >= 0 ? mainHandReleaseStartRemainingTicks : offHandReleaseStartRemainingTicks;
    }

    public static int getReleaseStartRemainingTicks(class_1268 class_12682) {
        return class_12682 == class_1268.field_5810 ? offHandReleaseStartRemainingTicks : mainHandReleaseStartRemainingTicks;
    }

    public static void clearReleaseStart() {
        UseAnimationSuppressor.clearReleaseStart(class_1268.field_5808);
        UseAnimationSuppressor.clearReleaseStart(class_1268.field_5810);
    }

    public static void clearReleaseStart(class_1268 class_12682) {
        if (class_12682 == class_1268.field_5810) {
            offHandReleaseStartTick = -1L;
            offHandReleaseStartRemainingTicks = -1;
        } else {
            mainHandReleaseStartTick = -1L;
            mainHandReleaseStartRemainingTicks = -1;
        }
    }

    public static void recordMeshStart(int n) {
        UseAnimationSuppressor.recordMeshStart(class_1268.field_5808, n);
        UseAnimationSuppressor.recordMeshStart(class_1268.field_5810, n);
    }

    public static void recordMeshStart(class_1268 class_12682, int n) {
        if (n >= 0) {
            if (class_12682 == class_1268.field_5810) {
                if (offHandMeshStartRemainingTicks < 0) {
                    offHandMeshStartRemainingTicks = n;
                }
            } else if (mainHandMeshStartRemainingTicks < 0) {
                mainHandMeshStartRemainingTicks = n;
            }
        }
    }

    public static int getMeshStartRemainingTicks() {
        return mainHandMeshStartRemainingTicks >= 0 ? mainHandMeshStartRemainingTicks : offHandMeshStartRemainingTicks;
    }

    public static int getMeshStartRemainingTicks(class_1268 class_12682) {
        return class_12682 == class_1268.field_5810 ? offHandMeshStartRemainingTicks : mainHandMeshStartRemainingTicks;
    }

    public static void clearMeshStart() {
        UseAnimationSuppressor.clearMeshStart(class_1268.field_5808);
        UseAnimationSuppressor.clearMeshStart(class_1268.field_5810);
    }

    public static void clearMeshStart(class_1268 class_12682) {
        if (class_12682 == class_1268.field_5810) {
            offHandMeshStartRemainingTicks = -1;
        } else {
            mainHandMeshStartRemainingTicks = -1;
        }
    }

    static {
        mainHandReleaseStartTick = -1L;
        offHandReleaseStartTick = -1L;
        mainHandReleaseStartRemainingTicks = -1;
        offHandReleaseStartRemainingTicks = -1;
        mainHandMeshStartRemainingTicks = -1;
        offHandMeshStartRemainingTicks = -1;
    }
}

