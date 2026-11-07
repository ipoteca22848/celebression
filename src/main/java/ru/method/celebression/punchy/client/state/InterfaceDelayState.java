package ru.method.celebression.punchy.client.state;

import net.minecraft.class_310;
import net.minecraft.class_437;
import net.minecraft.class_465;
import net.minecraft.class_481;
import net.minecraft.class_490;

public final class InterfaceDelayState {
    private static boolean pending;
    private static long delayUntilNanos;
    private static class_437 queuedScreen;
    private static boolean bypass;

    private InterfaceDelayState() {
    }

    public static void schedule(float f) {
        if (!(f <= 0.0f)) {
            pending = true;
            queuedScreen = null;
            delayUntilNanos = System.nanoTime() + (long)(f * 1.0E9f);
        }
    }

    public static boolean shouldDelay(class_437 class_4372) {
        if (bypass || !pending || class_4372 == null) {
            return false;
        }
        if (!InterfaceDelayState.isDelayableScreen(class_4372)) {
            return false;
        }
        if (System.nanoTime() >= delayUntilNanos) {
            pending = false;
            return false;
        }
        return true;
    }

    public static void queue(class_437 class_4372) {
        queuedScreen = class_4372;
    }

    public static void tick(class_310 class_3102) {
        if (pending) {
            if (queuedScreen == null) {
                if (System.nanoTime() >= delayUntilNanos) {
                    pending = false;
                }
            } else if (System.nanoTime() >= delayUntilNanos) {
                class_437 class_4372 = queuedScreen;
                queuedScreen = null;
                pending = false;
                InterfaceDelayState.openNow(class_3102, class_4372);
            }
        }
    }

    public static void openNow(class_310 class_3102, class_437 class_4372) {
        if (class_3102 != null) {
            bypass = true;
            class_3102.method_1507(class_4372);
            bypass = false;
        }
    }

    private static boolean isDelayableScreen(class_437 class_4372) {
        if (class_4372 instanceof class_490) {
            return false;
        }
        return class_4372 instanceof class_481 ? false : class_4372 instanceof class_465;
    }
}

