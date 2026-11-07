package ru.method.celebression.utils.debug;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import meteordevelopment.orbit.listeners.IListener;
import net.minecraft.class_310;
import net.minecraft.class_332;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.Stopwatch;
import ru.method.celebression.utils.debug.ProfiledEventBus;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class FullFrameProfiler {
    private static final long LOG_INTERVAL_NANOS = 3000000000L;
    private static final int MAX_LOG_SECTIONS = 12;
    private static final int MAX_LOG_EVENTS = 8;
    private static final int MAX_LOG_LISTENERS = 10;
    private static final int MAX_OVERLAY_LINES = 18;
    private static final Map<String, Stat> SECTIONS = new LinkedHashMap<String, Stat>();
    private static final Map<String, Stat> EVENTS = new LinkedHashMap<String, Stat>();
    private static final Map<String, Stat> LISTENERS = new LinkedHashMap<String, Stat>();
    private static final Map<String, CounterStat> COUNTERS = new LinkedHashMap<String, CounterStat>();
    private static final IdentityHashMap<IListener, String> LISTENER_OWNERS = new IdentityHashMap();
    private static final List<String> OVERLAY_LINES = new ArrayList<String>(18);
    private static final Comparator<Map.Entry<String, Stat>> STAT_DESCENDING = new Comparator<Map.Entry<String, Stat>>(){

        @Override
        public int compare(Map.Entry<String, Stat> entry, Map.Entry<String, Stat> entry2) {
            return Long.compare(entry2.getValue().totalNanos, entry.getValue().totalNanos);
        }
    };
    private static volatile boolean enabled;
    private static int frameDepth;
    private static int tickDepth;
    private static long frameStartNanos;
    private static long tickStartNanos;
    private static int frameCount;
    private static long totalFrameNanos;
    private static long maxFrameNanos;
    private static long totalTickNanos;
    private static long maxTickNanos;
    private static int tickCount;
    private static long lastLogNanos;
    private static long lastOverlayUpdateNanos;
    private static double lastAvgFrameMs;
    private static double lastMaxFrameMs;
    private static double lastAvgTickMs;
    private static boolean listenersBound;

    private FullFrameProfiler() {
    }

    public static void setEnabled(boolean bl) {
        enabled = bl;
        if (bl) {
            listenersBound = false;
            FullFrameProfiler.bindExistingListeners();
        } else {
            FullFrameProfiler.reset();
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static boolean isActive() {
        return enabled && frameDepth > 0;
    }

    public static void beginFrame() {
        if (!enabled) {
            return;
        }
        long l = System.nanoTime();
        if (frameDepth++ == 0) {
            frameStartNanos = l;
            if (!listenersBound) {
                FullFrameProfiler.bindExistingListeners();
            }
            FullFrameProfiler.resetFrameCounters();
            if (lastLogNanos == 0L) {
                lastLogNanos = l;
            }
        }
    }

    public static void endFrame() {
        if (!enabled || frameDepth <= 0) {
            frameDepth = 0;
            return;
        }
        if (--frameDepth > 0) {
            return;
        }
        long l = System.nanoTime();
        long l2 = Math.max(0L, l - frameStartNanos);
        ++frameCount;
        totalFrameNanos += l2;
        maxFrameNanos = Math.max(maxFrameNanos, l2);
        FullFrameProfiler.maybeLog(l);
        FullFrameProfiler.maybeRefreshOverlay(l, l2);
    }

    public static void beginTick() {
        if (!enabled) {
            return;
        }
        if (tickDepth++ == 0) {
            tickStartNanos = System.nanoTime();
        }
    }

    public static void endTick() {
        if (!enabled || tickDepth <= 0) {
            tickDepth = 0;
            return;
        }
        if (--tickDepth > 0) {
            return;
        }
        long l = Math.max(0L, System.nanoTime() - tickStartNanos);
        ++tickCount;
        totalTickNanos += l;
        maxTickNanos = Math.max(maxTickNanos, l);
        FullFrameProfiler.recordSection("tick.total", l);
    }

    public static long startSection() {
        return FullFrameProfiler.isActive() ? System.nanoTime() : 0L;
    }

    public static void endSection(String string, long l) {
        if (l == 0L) {
            return;
        }
        FullFrameProfiler.recordSection(string, System.nanoTime() - l);
    }

    public static void recordSection(String string, long l) {
        if (!FullFrameProfiler.isActive() || string == null || string.isBlank() || l <= 0L) {
            return;
        }
        FullFrameProfiler.accumulate(SECTIONS, string, l);
    }

    public static void recordEventPost(Object object, long l) {
        if (!FullFrameProfiler.isActive() || object == null || l <= 0L) {
            return;
        }
        FullFrameProfiler.accumulate(EVENTS, "event." + object.getClass().getSimpleName(), l);
    }

    public static void bindListener(IListener iListener, String string) {
        if (!enabled || iListener == null || string == null || string.isBlank()) {
            return;
        }
        LISTENER_OWNERS.put(iListener, string);
    }

    public static void recordListener(IListener iListener, Object object, long l) {
        if (!FullFrameProfiler.isActive() || iListener == null || object == null || l <= 0L) {
            return;
        }
        String string = LISTENER_OWNERS.getOrDefault(iListener, iListener.getClass().getSimpleName());
        String string2 = "handler." + string + "." + object.getClass().getSimpleName();
        FullFrameProfiler.accumulate(LISTENERS, string2, l);
    }

    public static void incrementCounter(String string) {
        FullFrameProfiler.incrementCounter(string, 1);
    }

    public static void incrementCounter(String string, int n) {
        if (!FullFrameProfiler.isActive() || string == null || string.isBlank() || n <= 0) {
            return;
        }
        CounterStat counterStat = COUNTERS.get(string);
        if (counterStat == null) {
            counterStat = new CounterStat();
            COUNTERS.put(string, counterStat);
        }
        counterStat.total += n;
        ++counterStat.frames;
        counterStat.lastFrame += n;
    }

    public static void renderOverlay(class_332 class_3322) {
        if (!enabled || class_3322 == null || OVERLAY_LINES.isEmpty()) {
            return;
        }
        float f = 6.0f;
        float f2 = 6.0f;
        float f3 = 8.5f;
        float f4 = 0.0f;
        for (String string : OVERLAY_LINES) {
            f4 = Math.max(f4, Fonts.REGULAR.getFont(6.5f).getWidth(string));
        }
        Render2D.drawRoundedRect(class_3322.method_51448(), f - 3.0f, f2 - 3.0f, f4 + 10.0f, (float)OVERLAY_LINES.size() * f3 + 6.0f, 4.0f, new Color(6, 8, 12, 185));
        for (String string : OVERLAY_LINES) {
            Color color = FullFrameProfiler.colorForLine(string);
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(6.5f), string, f, f2, color);
            f2 += f3;
        }
    }

    private static Color colorForLine(String string) {
        if (string.startsWith("handler.")) {
            return new Color(255, 180, 120, 235);
        }
        if (string.startsWith("event.")) {
            return new Color(180, 220, 255, 235);
        }
        if (string.startsWith("glass.") || string.startsWith("gui.") || string.startsWith("hud.") || string.startsWith("render.")) {
            return new Color(120, 210, 255, 235);
        }
        return new Color(235, 235, 235, 235);
    }

    private static void maybeRefreshOverlay(long l, long l2) {
        if (l - lastOverlayUpdateNanos < 250000000L && frameCount > 1) {
            return;
        }
        lastOverlayUpdateNanos = l;
        OVERLAY_LINES.clear();
        class_310 class_3102 = class_310.method_1551();
        String string = class_3102 != null && class_3102.field_1755 != null ? class_3102.field_1755.getClass().getSimpleName() : "in-game";
        OVERLAY_LINES.add(String.format(Locale.ROOT, "FPS %d | frame %.2fms | tick avg %.2fms | %s", Stopwatch.rT(), (double)l2 / 1000000.0, lastAvgTickMs, string));
        FullFrameProfiler.appendTopStats(OVERLAY_LINES, SECTIONS, 5, false);
        FullFrameProfiler.appendTopStats(OVERLAY_LINES, EVENTS, 3, false);
        FullFrameProfiler.appendTopStats(OVERLAY_LINES, LISTENERS, 4, true);
        FullFrameProfiler.appendCounterLine("glass.draws");
        FullFrameProfiler.appendCounterLine("glass.captures");
        FullFrameProfiler.appendCounterLine("handlers");
    }

    private static void appendTopStats(List<String> list, Map<String, Stat> map, int n, boolean bl) {
        List<Map.Entry<String, Stat>> list2 = FullFrameProfiler.sorted(map);
        int n2 = Math.min(n, list2.size());
        for (int i = 0; i < n2 && list.size() < 18; ++i) {
            Map.Entry<String, Stat> entry = list2.get(i);
            Stat stat = entry.getValue();
            double d = (double)stat.totalNanos / (double)Math.max(1, stat.count) / 1000000.0;
            if (bl && d < 0.02) continue;
            list.add(String.format(Locale.ROOT, "%s %.2fms x%d", entry.getKey(), d, stat.count));
        }
    }

    private static void appendCounterLine(String string) {
        CounterStat counterStat = COUNTERS.get(string);
        if (counterStat == null || counterStat.lastFrame <= 0) {
            return;
        }
        OVERLAY_LINES.add(string + " " + counterStat.lastFrame + "/frame");
    }

    private static void maybeLog(long l) {
        if (frameCount == 0 || l - lastLogNanos < 3000000000L) {
            return;
        }
        lastAvgFrameMs = (double)totalFrameNanos / (double)frameCount / 1000000.0;
        lastMaxFrameMs = (double)maxFrameNanos / 1000000.0;
        lastAvgTickMs = tickCount > 0 ? (double)totalTickNanos / (double)tickCount / 1000000.0 : 0.0;
        Celebression.LOGGER.info("[FullProfiler] fps={} frames={} avgFrame={}ms maxFrame={}ms avgTick={}ms sections=[{}] events=[{}] handlers=[{}] counters=[{}]", (Object)Stopwatch.rT(), (Object)frameCount, (Object)FullFrameProfiler.formatMillis(lastAvgFrameMs), (Object)FullFrameProfiler.formatMillis(lastMaxFrameMs), (Object)FullFrameProfiler.formatMillis(lastAvgTickMs), (Object)FullFrameProfiler.formatStats(SECTIONS, 12), (Object)FullFrameProfiler.formatStats(EVENTS, 8), (Object)FullFrameProfiler.formatStats(LISTENERS, 10), (Object)FullFrameProfiler.formatCounters());
        SECTIONS.clear();
        EVENTS.clear();
        LISTENERS.clear();
        COUNTERS.clear();
        frameCount = 0;
        totalFrameNanos = 0L;
        maxFrameNanos = 0L;
        tickCount = 0;
        totalTickNanos = 0L;
        maxTickNanos = 0L;
        lastLogNanos = l;
    }

    private static String formatStats(Map<String, Stat> map, int n) {
        List<Map.Entry<String, Stat>> list = FullFrameProfiler.sorted(map);
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = Math.min(n, list.size());
        for (int i = 0; i < n2; ++i) {
            Map.Entry<String, Stat> entry = list.get(i);
            Stat stat = entry.getValue();
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(entry.getKey()).append('=').append(FullFrameProfiler.formatMillis((double)stat.totalNanos / (double)Math.max(1, stat.count) / 1000000.0)).append("ms x").append(stat.count);
        }
        return stringBuilder.length() == 0 ? "none" : stringBuilder.toString();
    }

    private static String formatCounters() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, CounterStat> entry : COUNTERS.entrySet()) {
            CounterStat counterStat = entry.getValue();
            if (counterStat.total <= 0) continue;
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(entry.getKey()).append('=').append(counterStat.total / Math.max(1, frameCount)).append("/frame");
        }
        return stringBuilder.isEmpty() ? "none" : stringBuilder.toString();
    }

    private static List<Map.Entry<String, Stat>> sorted(Map<String, Stat> map) {
        ArrayList<Map.Entry<String, Stat>> arrayList = new ArrayList<Map.Entry<String, Stat>>(map.entrySet());
        arrayList.sort(STAT_DESCENDING);
        return arrayList;
    }

    private static void accumulate(Map<String, Stat> map, String string, long l) {
        Stat stat = map.get(string);
        if (stat == null) {
            stat = new Stat();
            map.put(string, stat);
        }
        ++stat.count;
        stat.totalNanos += l;
        stat.maxNanos = Math.max(stat.maxNanos, l);
    }

    private static void resetFrameCounters() {
        for (CounterStat counterStat : COUNTERS.values()) {
            counterStat.lastFrame = 0;
        }
    }

    private static void bindExistingListeners() {
        ProfiledEventBus.bindAllFromInstance();
        listenersBound = true;
    }

    private static void reset() {
        SECTIONS.clear();
        EVENTS.clear();
        LISTENERS.clear();
        COUNTERS.clear();
        LISTENER_OWNERS.clear();
        OVERLAY_LINES.clear();
        frameDepth = 0;
        tickDepth = 0;
        frameCount = 0;
        tickCount = 0;
        totalFrameNanos = 0L;
        maxFrameNanos = 0L;
        totalTickNanos = 0L;
        maxTickNanos = 0L;
        lastLogNanos = 0L;
        lastOverlayUpdateNanos = 0L;
        lastAvgFrameMs = 0.0;
        lastMaxFrameMs = 0.0;
        lastAvgTickMs = 0.0;
        listenersBound = false;
    }

    private static String formatMillis(double d) {
        return String.format(Locale.ROOT, "%.2f", d);
    }

    static final class CounterStat {
        int total;
        int frames;
        int lastFrame;

        CounterStat() {
        }
    }

    static final class Stat {
        int count;
        long totalNanos;
        long maxNanos;

        Stat() {
        }
    }
}

