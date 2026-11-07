package unkw.visualtest.client.hud;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_2561;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_6880;

public final class HudState {
    private boolean previewMode = true;
    private double speedBps;
    private double lastX;
    private double lastZ;
    private UUID trackedPlayer;
    private boolean hasPosition;
    private long hintVisibleUntil = System.currentTimeMillis() + 5000L;
    private final Map<class_6880<class_1291>, Long> effectReceivedOrder = new HashMap<class_6880<class_1291>, Long>();
    private long nextEffectOrder;

    public void tick(class_310 class_3102, class_304 class_3042) {
        while (class_3042.method_1436()) {
            this.previewMode = !this.previewMode;
            this.hintVisibleUntil = System.currentTimeMillis() + 2500L;
            if (class_3102.field_1724 == null) continue;
            class_3102.field_1724.method_7353((class_2561)class_2561.method_43470((String)(this.previewMode ? "\u0422\u0435\u0441\u0442\u043e\u0432\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435 HUD \u0432\u043a\u043b\u044e\u0447\u0435\u043d\u044b" : "HUD \u043f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u0442\u043e\u043b\u044c\u043a\u043e \u0440\u0435\u0430\u043b\u044c\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435")), true);
        }
        if (class_3102.field_1724 == null) {
            this.trackedPlayer = null;
            this.hasPosition = false;
            this.speedBps = 0.0;
            this.resetEffects();
            return;
        }
        UUID uUID = class_3102.field_1724.method_5667();
        if (!uUID.equals(this.trackedPlayer)) {
            this.trackedPlayer = uUID;
            this.hasPosition = false;
            this.speedBps = 0.0;
            this.resetEffects();
        }
        this.trackEffects(class_3102.field_1724.method_6026());
        double d = class_3102.field_1724.method_23317();
        double d2 = class_3102.field_1724.method_23321();
        if (this.hasPosition) {
            double d3 = Math.hypot(d - this.lastX, d2 - this.lastZ) * 20.0;
            this.speedBps = d3 <= 80.0 ? (this.speedBps += (d3 - this.speedBps) * 0.25) : 0.0;
        }
        this.lastX = d;
        this.lastZ = d2;
        this.hasPosition = true;
    }

    public boolean previewMode() {
        return this.previewMode;
    }

    public double speedBps() {
        return this.speedBps;
    }

    public boolean showHint() {
        return System.currentTimeMillis() < this.hintVisibleUntil;
    }

    void trackEffects(Iterable<class_1293> iterable) {
        HashSet<class_6880> hashSet = new HashSet<class_6880>();
        for (class_1293 class_12932 : iterable) {
            class_6880 class_68803 = class_12932.method_5579();
            hashSet.add(class_68803);
            if (this.effectReceivedOrder.containsKey(class_68803)) continue;
            this.effectReceivedOrder.put((class_6880<class_1291>)class_68803, ++this.nextEffectOrder);
        }
        this.effectReceivedOrder.keySet().removeIf(class_68802 -> !hashSet.contains(class_68802));
    }

    long effectOrder(class_6880<class_1291> class_68802) {
        return this.effectReceivedOrder.getOrDefault(class_68802, 0L);
    }

    private void resetEffects() {
        this.effectReceivedOrder.clear();
        this.nextEffectOrder = 0L;
    }
}

