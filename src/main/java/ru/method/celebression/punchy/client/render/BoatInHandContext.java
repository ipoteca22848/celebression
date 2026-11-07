package ru.method.celebression.punchy.client.render;

public final class BoatInHandContext {
    private static final ThreadLocal<Boolean> ACTIVE = ThreadLocal.withInitial(() -> Boolean.FALSE);

    private BoatInHandContext() {
    }

    public static void push() {
        ACTIVE.set(Boolean.TRUE);
    }

    public static void pop() {
        ACTIVE.set(Boolean.FALSE);
    }

    public static boolean isActive() {
        return ACTIVE.get();
    }
}

