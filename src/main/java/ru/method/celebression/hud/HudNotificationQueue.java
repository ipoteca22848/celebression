package ru.method.celebression.hud;

import java.awt.Color;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.Generated;
import ru.method.celebression.Celebression;
import ru.method.celebression.hud.DynamicIslandHud;
import ru.method.celebression.hud.HudManager;
import ru.method.celebression.hud.HudNotification;

public class HudNotificationQueue {
    private static final HudNotificationQueue md = new HudNotificationQueue();
    private final Queue<HudNotification> me = new ConcurrentLinkedQueue<HudNotification>();
    private HudNotification mf;

    public static HudNotificationQueue dG() {
        return md;
    }

    public void a(String string, boolean bl) {
        if (!this.dJ()) {
            return;
        }
        this.mf = new HudNotification(string, bl, 1000L);
        this.me.clear();
    }

    public void bg(String string) {
        if (!this.dJ()) {
            return;
        }
        this.me.offer(new HudNotification(null, true, 1400L, string));
    }

    public void a(String string, Color color, long l) {
        if (!this.dJ() || string == null || string.isBlank()) {
            return;
        }
        if (this.mf != null && !this.mf.dz() && string.equals(this.mf.dB())) {
            return;
        }
        this.me.removeIf(hudNotification -> string.equals(hudNotification.dB()));
        this.me.offer(new HudNotification(null, true, l, string, color));
    }

    public void dH() {
        this.mf = null;
        this.me.clear();
    }

    public void dI() {
        if (!this.dJ()) {
            this.dH();
            return;
        }
        if (this.mf != null && this.mf.dz()) {
            this.mf = null;
        }
        if (this.mf == null && !this.me.isEmpty()) {
            this.mf = this.me.poll();
        }
    }

    private boolean dJ() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return false;
        }
        HudManager hudManager = celebression.getHudManager();
        if (hudManager == null) {
            return false;
        }
        DynamicIslandHud dynamicIslandHud = hudManager.a(DynamicIslandHud.class);
        return dynamicIslandHud != null && dynamicIslandHud.isToggled();
    }

    @Generated
    public Queue<HudNotification> dK() {
        return this.me;
    }

    @Generated
    public HudNotification dL() {
        return this.mf;
    }
}

