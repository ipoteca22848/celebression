package ru.method.celebression.punchy.client.state;

import net.minecraft.class_1268;
import net.minecraft.class_1294;
import net.minecraft.class_310;

public final class AttackActionTracker {
    private static boolean triggered;
    private static class_1268 lastHand;
    private static boolean lastCritical;
    private static boolean pendingCritical;
    private static long triggerEpoch;

    private AttackActionTracker() {
    }

    public static void recordCriticalCandidate(class_310 class_3102) {
        pendingCritical = class_3102 != null && class_3102.field_1724 != null ? AttackActionTracker.isCriticalHit(class_3102) : false;
    }

    public static void clearPendingCritical() {
        pendingCritical = false;
    }

    public static void recordAttack() {
        boolean bl = triggered;
        triggered = true;
        if (!bl) {
            ++triggerEpoch;
            lastHand = class_1268.field_5808;
            lastCritical = pendingCritical;
            pendingCritical = false;
        }
    }

    public static void recordAttack(class_1268 class_12682) {
        boolean bl = triggered;
        triggered = true;
        if (class_12682 != null) {
            lastHand = class_12682;
        }
        if (!bl) {
            ++triggerEpoch;
            lastCritical = pendingCritical;
            pendingCritical = false;
        }
    }

    public static boolean isTriggered() {
        return triggered;
    }

    public static boolean consumeTrigger() {
        if (!triggered) {
            return false;
        }
        triggered = false;
        return true;
    }

    public static class_1268 peekHand() {
        return !triggered ? null : lastHand;
    }

    public static long peekEpoch() {
        return triggerEpoch;
    }

    public static class_1268 peekLastRecordedHand() {
        return lastHand;
    }

    public static class_1268 consumeHand() {
        if (!triggered) {
            return null;
        }
        triggered = false;
        return lastHand;
    }

    public static boolean consumeCritical() {
        boolean bl = lastCritical;
        lastCritical = false;
        return bl;
    }

    public static void clear() {
        triggered = false;
        lastHand = class_1268.field_5808;
        lastCritical = false;
        pendingCritical = false;
    }

    private static boolean isCriticalHit(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            if (class_3102.field_1724.method_7261(1.0f) < 0.9f) {
                return false;
            }
            if (class_3102.field_1724.method_24828()) {
                return false;
            }
            if ((double)class_3102.field_1724.field_6017 <= 0.0) {
                return false;
            }
            if (class_3102.field_1724.method_5799() || class_3102.field_1724.method_5771()) {
                return false;
            }
            if (class_3102.field_1724.method_6101()) {
                return false;
            }
            if (class_3102.field_1724.method_5765()) {
                return false;
            }
            return class_3102.field_1724.method_5624() ? false : !class_3102.field_1724.method_6059(class_1294.field_5919);
        }
        return false;
    }

    static {
        lastHand = class_1268.field_5808;
    }
}

