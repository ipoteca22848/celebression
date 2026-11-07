package ru.method.celebression.utils.debug;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import ru.method.celebression.Celebression;

public final class TabProfiler {
    private static final long LOG_INTERVAL_NANOS = 1000000000L;
    private static final int MAX_HOTSPOTS = 6;
    private static final Map<String, Stat> STATS = new LinkedHashMap<String, Stat>();
    private static final Comparator<Map.Entry<String, Stat>> STAT_DESCENDING = new Comparator<Map.Entry<String, Stat>>(){

        @Override
        public int compare(Map.Entry<String, Stat> entry, Map.Entry<String, Stat> entry2) {
            return Long.compare(entry2.getValue().totalNanos, entry.getValue().totalNanos);
        }
    };
    private static int activeDepth;
    private static long frameStartNanos;
    private static int frameCount;
    private static long totalFrameNanos;
    private static long maxFrameNanos;
    private static long lastLogNanos;

    private TabProfiler() {
    }

    public static void beginFrame() {
        long l = System.nanoTime();
        if (activeDepth++ == 0) {
            frameStartNanos = l;
            if (lastLogNanos == 0L) {
                lastLogNanos = l;
            }
        }
    }

    public static void endFrame() {
        if (activeDepth <= 0) {
            activeDepth = 0;
            return;
        }
        if (--activeDepth > 0) {
            return;
        }
        long l = System.nanoTime();
        long l2 = l - frameStartNanos;
        ++frameCount;
        totalFrameNanos += l2;
        maxFrameNanos = Math.max(maxFrameNanos, l2);
        TabProfiler.maybeLog(l);
    }

    public static boolean isActive() {
        return activeDepth > 0;
    }

    public static long startSection() {
        return TabProfiler.isActive() ? System.nanoTime() : 0L;
    }

    public static void endSection(String string, long l) {
        if (l == 0L) {
            return;
        }
        TabProfiler.record(string, System.nanoTime() - l);
    }

    public static void record(String string, long l) {
        if (!TabProfiler.isActive() || string == null || string.isBlank() || l <= 0L) {
            return;
        }
        Stat stat = STATS.get(string);
        if (stat == null) {
            stat = new Stat();
            STATS.put(string, stat);
        }
        ++stat.count;
        stat.totalNanos += l;
    }

    private static void maybeLog(long l) {
        if (frameCount == 0 || l - lastLogNanos < 1000000000L) {
            return;
        }
        ArrayList<Map.Entry<String, Stat>> arrayList = new ArrayList<Map.Entry<String, Stat>>(STATS.entrySet());
        arrayList.sort(STAT_DESCENDING);
        StringBuilder stringBuilder = new StringBuilder();
        int n = Math.min(6, arrayList.size());
        for (int i = 0; i < n; ++i) {
            Map.Entry entry = (Map.Entry)arrayList.get(i);
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append((String)entry.getKey()).append('=').append(TabProfiler.formatMillis((double)((Stat)entry.getValue()).totalNanos / 1000000.0)).append("ms x").append(((Stat)entry.getValue()).count);
        }
        Celebression.LOGGER.info("[TabProfiler] frames={} avg={}ms max={}ms hotspots=[{}]", (Object)frameCount, (Object)TabProfiler.formatMillis((double)totalFrameNanos / (double)frameCount / 1000000.0), (Object)TabProfiler.formatMillis((double)maxFrameNanos / 1000000.0), (Object)(stringBuilder.isEmpty() ? "none" : stringBuilder));
        STATS.clear();
        frameCount = 0;
        totalFrameNanos = 0L;
        maxFrameNanos = 0L;
        lastLogNanos = l;
    }

    private static String formatMillis(double d) {
        return String.format(Locale.ROOT, "%.2f", d);
    }

    static final class Stat {
        int count;
        long totalNanos;

        Stat() {
        }
    }
}

