package ru.method.celebression.utils.debug;

import net.minecraft.class_332;
import ru.method.celebression.utils.debug.FullFrameProfiler;

@Deprecated
public final class RenderProfiler {
    private RenderProfiler() {
    }

    public static void setEnabled(boolean bl) {
        FullFrameProfiler.setEnabled(bl);
    }

    public static boolean isEnabled() {
        return FullFrameProfiler.isEnabled();
    }

    public static void beginFrame() {
        FullFrameProfiler.beginFrame();
    }

    public static void endFrame() {
        FullFrameProfiler.endFrame();
    }

    public static boolean isActive() {
        return FullFrameProfiler.isActive();
    }

    public static long startSection() {
        return FullFrameProfiler.startSection();
    }

    public static void endSection(String string, long l) {
        FullFrameProfiler.endSection(string, l);
    }

    public static void record(String string, long l) {
        FullFrameProfiler.recordSection(string, l);
    }

    public static void incrementCounter(String string) {
        FullFrameProfiler.incrementCounter(string);
    }

    public static void incrementCounter(String string, int n) {
        FullFrameProfiler.incrementCounter(string, n);
    }

    public static void renderOverlay(class_332 class_3322) {
        FullFrameProfiler.renderOverlay(class_3322);
    }
}

