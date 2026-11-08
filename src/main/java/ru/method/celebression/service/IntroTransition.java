package ru.method.celebression.service;

/** Elapsed-time smoothstep, independent of render frame rate. */
public final class IntroTransition {
    private IntroTransition() {}
    public static float progress(long start, long now, long durationNanos) {
        if (durationNanos <= 0) return 1f;
        double t = Math.max(0d, Math.min(1d, ((double)now - start) / durationNanos));
        return (float)(t * t * (3d - 2d * t));
    }
}
