package ru.method.celebression.punchy.client.state;

import net.minecraft.class_1268;
import net.minecraft.class_3965;
import ru.method.celebression.punchy.client.state.ConsumeStateMachine;

public final class UseActionTracker {
    private static final long TRIGGER_VALID_NANOS = 1250000000L;
    private static final long FALLBACK_VALID_NANOS = 900000000L;
    private static Trigger mainConfirmed;
    private static Trigger offConfirmed;
    private static Trigger mainFallback;
    private static Trigger offFallback;
    private static class_1268 triggerHand;
    private static long triggerEpoch;
    private static long lastRecordNano;
    private static long lastMainUseNano;
    private static long lastOffUseNano;

    private UseActionTracker() {
    }

    public static void recordUse(class_1268 class_12682, TriggerType triggerType) {
        UseActionTracker.recordUse(class_12682, triggerType, null);
    }

    public static void recordUse(class_1268 class_12682, TriggerType triggerType, class_3965 class_39652) {
        if (!ConsumeStateMachine.isUseSuppressedAfterConsume()) {
            long l = System.nanoTime();
            class_1268 class_12683 = class_12682 == null ? class_1268.field_5808 : class_12682;
            Trigger trigger = new Trigger(class_12683, triggerType == null ? TriggerType.USE_ITEM : triggerType, class_39652, false, l);
            UseActionTracker.setConfirmed(class_12683, trigger);
            UseActionTracker.discardFallbackForHand(class_12683);
            ++triggerEpoch;
            triggerHand = class_12683;
            lastRecordNano = l;
            UseActionTracker.markRecentUse(class_12683, l);
        }
    }

    public static void recordFallbackUse(class_1268 class_12682, TriggerType triggerType, class_3965 class_39652) {
        if (!ConsumeStateMachine.isUseSuppressedAfterConsume()) {
            long l = System.nanoTime();
            class_1268 class_12683 = class_12682 == null ? class_1268.field_5808 : class_12682;
            Trigger trigger = new Trigger(class_12683, triggerType == null ? TriggerType.USE_ITEM : triggerType, class_39652, true, l);
            UseActionTracker.setFallback(class_12683, trigger);
            UseActionTracker.markRecentUse(class_12683, l);
        }
    }

    public static Trigger peekTrigger() {
        UseActionTracker.trimExpired();
        return UseActionTracker.latest(mainConfirmed, offConfirmed);
    }

    public static class_1268 peekLastHand() {
        return triggerHand;
    }

    public static long peekEpoch() {
        return triggerEpoch;
    }

    public static boolean wasRecentUse(long l) {
        return l > 0L && lastRecordNano > 0L ? System.nanoTime() - lastRecordNano <= l : false;
    }

    public static Trigger consumeTrigger() {
        UseActionTracker.trimExpired();
        Trigger trigger = UseActionTracker.latest(mainConfirmed, offConfirmed);
        if (trigger == null) {
            return null;
        }
        UseActionTracker.clearConfirmed(trigger.hand());
        return trigger;
    }

    public static Trigger consumeLatestTrigger() {
        UseActionTracker.trimExpired();
        Trigger trigger = UseActionTracker.latest(mainConfirmed, offConfirmed);
        Trigger trigger2 = UseActionTracker.latest(mainFallback, offFallback);
        if (trigger2 == null || trigger != null && trigger2.recordNano() < trigger.recordNano()) {
            if (trigger != null) {
                UseActionTracker.clearConfirmed(trigger.hand());
            }
            return trigger;
        }
        UseActionTracker.clearFallback(trigger2.hand());
        return trigger2;
    }

    public static void pushBack(Trigger trigger) {
        if (trigger != null) {
            if (trigger.fallback()) {
                UseActionTracker.setFallback(trigger.hand(), trigger);
            } else {
                UseActionTracker.setConfirmed(trigger.hand(), trigger);
            }
        }
    }

    public static Trigger consumeFallbackTrigger() {
        UseActionTracker.trimExpired();
        Trigger trigger = UseActionTracker.latest(mainFallback, offFallback);
        if (trigger == null) {
            return null;
        }
        UseActionTracker.clearFallback(trigger.hand());
        return trigger;
    }

    public static void discardOppositeOlderThan(class_1268 class_12682, long l) {
        if (class_12682 != null && l > 0L) {
            Trigger trigger;
            class_1268 class_12683 = class_12682 == class_1268.field_5808 ? class_1268.field_5810 : class_1268.field_5808;
            Trigger trigger2 = UseActionTracker.getConfirmed(class_12683);
            if (trigger2 != null && trigger2.recordNano() <= l) {
                UseActionTracker.clearConfirmed(class_12683);
            }
            if ((trigger = UseActionTracker.getFallback(class_12683)) != null && trigger.recordNano() <= l) {
                UseActionTracker.clearFallback(class_12683);
            }
        }
    }

    public static void discardFallbackForHand(class_1268 class_12682) {
        if (class_12682 != null) {
            UseActionTracker.clearFallback(class_12682);
        }
    }

    public static boolean wasRecentUseForHand(class_1268 class_12682, long l) {
        if (class_12682 != null && l > 0L) {
            long l2;
            long l3 = l2 = class_12682 == class_1268.field_5810 ? lastOffUseNano : lastMainUseNano;
            return l2 <= 0L ? false : System.nanoTime() - l2 <= l;
        }
        return false;
    }

    public static void clear() {
        mainConfirmed = null;
        offConfirmed = null;
        mainFallback = null;
        offFallback = null;
    }

    private static void trimExpired() {
        long l = System.nanoTime();
        mainConfirmed = UseActionTracker.keepIfFresh(mainConfirmed, l, 1250000000L);
        offConfirmed = UseActionTracker.keepIfFresh(offConfirmed, l, 1250000000L);
        mainFallback = UseActionTracker.keepIfFresh(mainFallback, l, 900000000L);
        offFallback = UseActionTracker.keepIfFresh(offFallback, l, 900000000L);
    }

    private static Trigger keepIfFresh(Trigger trigger, long l, long l2) {
        if (trigger == null) {
            return null;
        }
        if (trigger.recordNano() <= 0L) {
            return null;
        }
        return l - trigger.recordNano() <= l2 ? trigger : null;
    }

    private static Trigger latest(Trigger trigger, Trigger trigger2) {
        if (trigger == null) {
            return trigger2;
        }
        if (trigger2 == null) {
            return trigger;
        }
        return trigger2.recordNano() >= trigger.recordNano() ? trigger2 : trigger;
    }

    private static void setConfirmed(class_1268 class_12682, Trigger trigger) {
        if (class_12682 == class_1268.field_5810) {
            offConfirmed = UseActionTracker.newer(offConfirmed, trigger);
        } else {
            mainConfirmed = UseActionTracker.newer(mainConfirmed, trigger);
        }
    }

    private static void clearConfirmed(class_1268 class_12682) {
        if (class_12682 == class_1268.field_5810) {
            offConfirmed = null;
        } else {
            mainConfirmed = null;
        }
    }

    private static Trigger getConfirmed(class_1268 class_12682) {
        return class_12682 == class_1268.field_5810 ? offConfirmed : mainConfirmed;
    }

    private static void setFallback(class_1268 class_12682, Trigger trigger) {
        if (class_12682 == class_1268.field_5810) {
            offFallback = UseActionTracker.newer(offFallback, trigger);
        } else {
            mainFallback = UseActionTracker.newer(mainFallback, trigger);
        }
    }

    private static void clearFallback(class_1268 class_12682) {
        if (class_12682 == class_1268.field_5810) {
            offFallback = null;
        } else {
            mainFallback = null;
        }
    }

    private static Trigger getFallback(class_1268 class_12682) {
        return class_12682 == class_1268.field_5810 ? offFallback : mainFallback;
    }

    private static Trigger newer(Trigger trigger, Trigger trigger2) {
        if (trigger2 == null) {
            return trigger;
        }
        if (trigger == null) {
            return trigger2;
        }
        return trigger2.recordNano() >= trigger.recordNano() ? trigger2 : trigger;
    }

    private static void markRecentUse(class_1268 class_12682, long l) {
        if (class_12682 != null && l > 0L) {
            if (class_12682 == class_1268.field_5810) {
                lastOffUseNano = l;
            } else {
                lastMainUseNano = l;
            }
        }
    }

    static {
        triggerHand = class_1268.field_5808;
    }

    public static enum TriggerType {
        USE_ITEM,
        USE_ITEM_ON,
        DROP_ITEM,
        INTERACT,
        INTERACT_AT;

    }

    public record Trigger(class_1268 hand, TriggerType type, class_3965 blockHit, boolean fallback, long recordNano) {
    }
}

