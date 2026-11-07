package ru.method.celebression.punchy.client.state;

import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_310;

public final class MiningActionTracker {
    private static boolean miningActive;
    private static boolean triggered;
    private static boolean miningMud;
    private static class_2338 miningPos;
    private static class_2350 miningFace;
    private static class_243 miningHitLocation;
    private static long triggerEpoch;
    private static long lastRecordNano;
    private static class_2338 lastTrackablePos;
    private static class_2350 lastTrackableFace;
    private static class_243 lastTrackableHitLocation;
    private static class_2680 lastTrackableState;
    private static long lastTrackableNano;

    private MiningActionTracker() {
    }

    public static void recordStart() {
        MiningActionTracker.recordStart(null, null);
    }

    public static void recordContinue() {
        MiningActionTracker.recordContinue(null, null);
    }

    public static void recordStart(class_2338 class_23382) {
        MiningActionTracker.recordStart(class_23382, null);
    }

    public static void recordStart(class_2338 class_23382, class_2350 class_23502) {
        MiningActionTracker.recordStart(class_23382, class_23502, null);
    }

    public static void recordStart(class_2338 class_23382, class_2350 class_23502, class_243 class_2432) {
        miningActive = true;
        triggered = true;
        ++triggerEpoch;
        miningPos = class_23382;
        if (class_23502 != null) {
            miningFace = class_23502;
        }
        miningHitLocation = class_2432;
        class_2680 class_26802 = MiningActionTracker.resolveState(class_23382);
        miningMud = MiningActionTracker.isMudBlock(class_26802);
        MiningActionTracker.cacheTrackableTarget(class_23382, class_23502, class_2432, class_26802);
        lastRecordNano = System.nanoTime();
    }

    public static void recordContinue(class_2338 class_23382) {
        MiningActionTracker.recordContinue(class_23382, null);
    }

    public static void recordContinue(class_2338 class_23382, class_2350 class_23502) {
        MiningActionTracker.recordContinue(class_23382, class_23502, null);
    }

    public static void recordContinue(class_2338 class_23382, class_2350 class_23502, class_243 class_2432) {
        miningActive = true;
        miningPos = class_23382;
        if (class_23502 != null) {
            miningFace = class_23502;
        }
        miningHitLocation = class_2432;
        class_2680 class_26802 = MiningActionTracker.resolveState(class_23382);
        miningMud = MiningActionTracker.isMudBlock(class_26802);
        MiningActionTracker.cacheTrackableTarget(class_23382, class_23502, class_2432, class_26802);
        lastRecordNano = System.nanoTime();
    }

    public static void recordAttempt(class_2338 class_23382, class_2350 class_23502, class_243 class_2432) {
        triggered = true;
        ++triggerEpoch;
        miningPos = class_23382;
        if (class_23502 != null) {
            miningFace = class_23502;
        }
        miningHitLocation = class_2432;
        class_2680 class_26802 = MiningActionTracker.resolveState(class_23382);
        miningMud = MiningActionTracker.isMudBlock(class_26802);
        MiningActionTracker.cacheTrackableTarget(class_23382, class_23502, class_2432, class_26802);
        lastRecordNano = System.nanoTime();
    }

    public static void recordStop() {
        miningActive = false;
        miningMud = false;
        miningPos = null;
        miningFace = class_2350.field_11043;
        miningHitLocation = null;
    }

    public static boolean isMiningActive() {
        return miningActive;
    }

    public static boolean isMiningMud() {
        return miningMud;
    }

    public static boolean consumeTrigger() {
        if (!triggered) {
            return false;
        }
        triggered = false;
        return true;
    }

    public static Target peekTarget() {
        if (!miningActive) {
            return null;
        }
        class_2680 class_26802 = MiningActionTracker.resolveState(miningPos);
        return miningPos != null && class_26802 != null && !class_26802.method_26215() ? new Target(miningPos, miningFace, class_26802, miningHitLocation) : null;
    }

    public static Target peekRecentTarget(long l) {
        if (l > 0L && lastTrackableNano > 0L) {
            if (System.nanoTime() - lastTrackableNano > l) {
                return null;
            }
            return lastTrackablePos != null && lastTrackableState != null && !lastTrackableState.method_26215() ? new Target(lastTrackablePos, lastTrackableFace, lastTrackableState, lastTrackableHitLocation) : null;
        }
        return null;
    }

    public static class_2338 peekPos() {
        return miningPos;
    }

    public static class_2350 peekFace() {
        return miningFace;
    }

    public static class_2680 peekState() {
        return MiningActionTracker.resolveState(miningPos);
    }

    public static long peekEpoch() {
        return triggerEpoch;
    }

    public static boolean wasRecentMining(long l) {
        return l > 0L && lastRecordNano > 0L ? System.nanoTime() - lastRecordNano <= l : false;
    }

    private static class_2680 resolveState(class_2338 class_23382) {
        class_310 class_3102 = class_310.method_1551();
        return class_3102 != null && class_3102.field_1687 != null && class_23382 != null ? class_3102.field_1687.method_8320(class_23382) : null;
    }

    private static boolean isMudBlock(class_2680 class_26802) {
        return class_26802 == null ? false : class_26802.method_27852(class_2246.field_37576) || class_26802.method_27852(class_2246.field_37556) || class_26802.method_27852(class_2246.field_37557) || class_26802.method_27852(class_2246.field_37547);
    }

    private static void cacheTrackableTarget(class_2338 class_23382, class_2350 class_23502, class_243 class_2432, class_2680 class_26802) {
        if (class_23382 != null && class_26802 != null && !class_26802.method_26215()) {
            lastTrackablePos = class_23382.method_10062();
            lastTrackableFace = class_23502 != null ? class_23502 : miningFace;
            lastTrackableHitLocation = class_2432;
            lastTrackableState = class_26802;
            lastTrackableNano = System.nanoTime();
        }
    }

    static {
        miningFace = class_2350.field_11043;
        lastTrackableFace = class_2350.field_11043;
    }

    public record Target(class_2338 pos, class_2350 face, class_2680 state, class_243 hitLocation) {
    }
}

