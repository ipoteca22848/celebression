package ru.method.celebression.punchy.client.state;

public final class InspectActionTracker {
    private static boolean triggered;

    private InspectActionTracker() {
    }

    public static void record() {
        triggered = true;
    }

    public static boolean consume() {
        if (!triggered) {
            return false;
        }
        triggered = false;
        return true;
    }

    public static void clear() {
        triggered = false;
    }
}

