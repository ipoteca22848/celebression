package ru.method.celebression.utils.render;

import net.minecraft.class_276;
import net.minecraft.class_6367;
import ru.method.celebression.utils.render.LiquidGlassReflectionCache;

public final class WorldSnapshotCache {
    private static final int BLUR_DOWNSAMPLE = 10;
    private static final class_6367[] chain = new class_6367[4];
    private static class_6367 blurFbo;
    private static boolean valid;

    private WorldSnapshotCache() {
    }

    public static boolean isValid() {
        return valid && blurFbo != null;
    }

    public static void invalidate() {
        valid = false;
    }

    public static void capture(class_276 class_2762) {
        if (class_2762 == null) {
            return;
        }
        int n = class_2762.field_1482;
        int n2 = class_2762.field_1481;
        if (n <= 0 || n2 <= 0) {
            return;
        }
        int n3 = Math.max(1, n / 10);
        int n4 = Math.max(1, n2 / 10);
        int n5 = Math.max(n3, n / 2);
        int n6 = Math.max(n4, n2 / 2);
        WorldSnapshotCache.chain[0] = WorldSnapshotCache.ensure(chain[0], n5, n6);
        LiquidGlassReflectionCache.blitColor(class_2762, (class_276)chain[0]);
        int n7 = 0;
        while (n7 + 1 < chain.length && n5 > n3 && n6 > n4) {
            n5 = Math.max(n3, n5 / 2);
            n6 = Math.max(n4, n6 / 2);
            WorldSnapshotCache.chain[n7 + 1] = WorldSnapshotCache.ensure(chain[n7 + 1], n5, n6);
            LiquidGlassReflectionCache.blitColor((class_276)chain[n7], (class_276)chain[n7 + 1]);
            ++n7;
        }
        blurFbo = chain[n7];
        class_2762.method_1235(true);
        valid = blurFbo != null;
    }

    public static void blitTo(class_276 class_2762) {
        if (!WorldSnapshotCache.isValid() || class_2762 == null) {
            return;
        }
        LiquidGlassReflectionCache.blitColor((class_276)blurFbo, class_2762);
        class_2762.method_1235(true);
    }

    private static class_6367 ensure(class_6367 class_63672, int n, int n2) {
        if (class_63672 == null || class_63672.field_1482 != n || class_63672.field_1481 != n2) {
            if (class_63672 != null) {
                class_63672.method_1238();
            }
            class_63672 = new class_6367(n, n2, false);
        }
        return class_63672;
    }

    public static void release() {
        for (int i = 0; i < chain.length; ++i) {
            if (chain[i] == null) continue;
            chain[i].method_1238();
            WorldSnapshotCache.chain[i] = null;
        }
        blurFbo = null;
        valid = false;
    }
}

