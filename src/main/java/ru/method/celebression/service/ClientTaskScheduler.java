package ru.method.celebression.service;

import java.util.concurrent.CopyOnWriteArrayList;
import meteordevelopment.orbit.EventHandler;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.ClientTickEvent;

public final class ClientTaskScheduler {
    private static final CopyOnWriteArrayList<ScheduledTask> ajs = new CopyOnWriteArrayList();
    private static ClientTaskScheduler ajt;

    private ClientTaskScheduler() {
    }

    public static void register() {
        if (ajt != null) {
            return;
        }
        ajt = new ClientTaskScheduler();
        Celebression.getInstance().getEventHandler().subscribe((Object)ajt);
    }

    public static void a(int n, Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (n <= 0) {
            runnable.run();
            return;
        }
        ajs.add(new ScheduledTask(n, runnable));
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        for (ScheduledTask scheduledTask : ajs) {
            if (--scheduledTask.aju > 0) continue;
            ajs.remove(scheduledTask);
            try {
                scheduledTask.ajv.run();
            }
            catch (Exception exception) {
                Celebression.LOGGER.warn("[ClientTasks] Scheduled task failed", (Throwable)exception);
            }
        }
    }

    static final class ScheduledTask {
        int aju;
        final Runnable ajv;

        ScheduledTask(int n, Runnable runnable) {
            this.aju = n;
            this.ajv = runnable;
        }
    }
}

