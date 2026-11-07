package ru.method.celebression.punchy.client.state;

import net.minecraft.class_1268;

public final class TriggerableActionTracker {
    private static boolean triggered;
    private static class_1268 triggerHand;
    private static int triggerKeyCode;

    private TriggerableActionTracker() {
    }

    public static void record(class_1268 class_12682, int n) {
        triggered = true;
        triggerHand = class_12682;
        triggerKeyCode = n;
    }

    public static Trigger consumeTrigger() {
        if (!triggered) {
            return null;
        }
        triggered = false;
        return new Trigger(triggerHand, triggerKeyCode);
    }

    public static void clear() {
        triggered = false;
        triggerHand = null;
        triggerKeyCode = -1;
    }

    static {
        triggerKeyCode = -1;
    }

    public record Trigger(class_1268 hand, int keyCode) {
    }
}

