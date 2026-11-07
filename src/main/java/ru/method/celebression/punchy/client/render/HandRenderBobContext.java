package ru.method.celebression.punchy.client.render;

public final class HandRenderBobContext {
    private static boolean renderingHands;
    private static float lastWalkDist;
    private static float lastBob;
    private static boolean hasBobSample;

    private HandRenderBobContext() {
    }

    public static void begin() {
        renderingHands = true;
    }

    public static void end() {
        renderingHands = false;
    }

    public static boolean isRenderingHands() {
        return renderingHands;
    }

    public static void updateBobSample(float f, float f2) {
        lastWalkDist = f;
        lastBob = f2;
        hasBobSample = true;
    }

    public static boolean hasBobSample() {
        return hasBobSample;
    }

    public static float getLastWalkDist() {
        return lastWalkDist;
    }

    public static float getLastBob() {
        return lastBob;
    }
}

