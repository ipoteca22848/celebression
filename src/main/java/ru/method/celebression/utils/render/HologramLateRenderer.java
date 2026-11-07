package ru.method.celebression.utils.render;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_10071;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_8138;
import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.WorldRenderEvent;

public final class HologramLateRenderer {
    public static volatile boolean replaying;
    private static final int QUEUE_CAP = 512;
    private static final List<Entry> QUEUE;
    private static boolean loggedActive;

    public static void queue(class_8138.class_8141 class_81412, class_10071 class_100712, class_4587 class_45872, int n, float f) {
        class_10071 class_100713 = new class_10071();
        class_100713.field_53588 = class_100712.field_53588;
        class_100713.field_53589 = class_100712.field_53589;
        if (QUEUE.size() >= 512) {
            QUEUE.clear();
        }
        QUEUE.add(new Entry(class_81412, class_100713, new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761()), new Matrix3f((Matrix3fc)class_45872.method_23760().method_23762()), n, f));
        if (!loggedActive) {
            loggedActive = true;
            Celebression.LOGGER.info("[HologramLateRenderer] \u0430\u043a\u0442\u0438\u0432\u0435\u043d \u2014 \u0433\u043e\u043b\u043e\u0433\u0440\u0430\u043c\u043c\u044b \u0440\u0438\u0441\u0443\u044e\u0442\u0441\u044f \u043f\u043e\u0432\u0435\u0440\u0445 \u043c\u0438\u0440\u0430");
        }
    }

    @EventHandler
    public void onRenderWorld(WorldRenderEvent worldRenderEvent) {
        HologramLateRenderer.flush();
    }

        private static void flush() {
        if (QUEUE.isEmpty()) {
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        class_4597.class_4598 class_45982 = class_3102.method_22940().method_23000();
        replaying = true;
        try {
            for (Entry entry : QUEUE) {
                class_4587 class_45872 = new class_4587();
                class_45872.method_23760().method_23761().set((Matrix4fc)entry.pose());
                class_45872.method_23760().method_23762().set((Matrix3fc)entry.normal());
                entry.renderer().method_49056(entry.state(), class_45872, (class_4597)class_45982, entry.light(), entry.progress());
            }
            class_45982.method_22993();
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[HologramLateRenderer] \u043e\u0448\u0438\u0431\u043a\u0430 \u043e\u0442\u043b\u043e\u0436\u0435\u043d\u043d\u043e\u0433\u043e \u0440\u0435\u043d\u0434\u0435\u0440\u0430:", (Throwable)exception);
        }
        finally {
            replaying = false;
            QUEUE.clear();
        }
    }

    static {
        QUEUE = new ArrayList<Entry>();
    }

    record Entry(class_8138.class_8141 renderer, class_10071 state, Matrix4f pose, Matrix3f normal, int light, float progress) {
    }
}

