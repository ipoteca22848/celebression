package ru.method.celebression.modules.impl.player;

import java.util.List;
import java.util.Locale;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_2596;
import net.minecraft.class_7439;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.PacketEvent;
import ru.method.celebression.server.ServerDetector;

public class PvpSaveModule
extends Module {
    private static final List<String> wj = List.of("\u0440\u0435\u0436\u0438\u043c \u0431\u043e\u044f \u043e\u043a\u043e\u043d\u0447\u0435\u043d", "\u0440\u0435\u0436\u0438\u043c \u0431\u043e\u044f \u0437\u0430\u043a\u043e\u043d\u0447", "\u0432\u044b \u0432\u044b\u0448\u043b\u0438 \u0438\u0437 \u0440\u0435\u0436\u0438\u043c\u0430 \u0431\u043e\u044f", "pvp ended", "combat ended");
    private static final long wk = 2500L;
    private static final long wl = 8000L;
    private long wm = 0L;
    private long wn = -1L;

    public PvpSaveModule() {
        super("PvpSave", ModuleCategory.qm);
    }

    @Override
    public void onDisable() {
        this.clear();
        super.onDisable();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (PvpSaveModule.fullNullCheck()) {
            this.clear();
            return;
        }
        this.hC();
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive receive) {
        class_2596<?> class_25962 = receive.i();
        if (class_25962 instanceof class_7439) {
            class_7439 class_74392 = (class_7439)class_25962;
            this.bF(class_74392.comp_763().getString());
        }
    }

    public void bD(String string) {
        this.bF(string);
    }

    public boolean bE(String string) {
        this.hC();
        if (!this.hD() || string == null) {
            return false;
        }
        String string2 = this.aY(string);
        if (!this.bG(string2)) {
            return false;
        }
        long l = System.currentTimeMillis();
        if (this.wn > l) {
            this.wn = -1L;
            return false;
        }
        this.wn = l + 5000L;
        ChatOutput.cG("\u0422\u044b \u0441\u0435\u0439\u0447\u0430\u0441 \u0432 \u0440\u0435\u0436\u0438\u043c\u0435 \u0431\u043e\u044f. \u041d\u0430\u043f\u0438\u0448\u0438 /hub \u0435\u0449\u0451 \u0440\u0430\u0437 \u0432 \u0442\u0435\u0447\u0435\u043d\u0438\u0435 5 \u0441\u0435\u043a\u0443\u043d\u0434, \u0447\u0442\u043e\u0431\u044b \u0432\u044b\u0439\u0442\u0438.");
        return true;
    }

    private void hC() {
        if (PvpSaveModule.fullNullCheck()) {
            return;
        }
        if (ServerDetector.sj()) {
            long l = System.currentTimeMillis();
            int n = ServerDetector.sm();
            this.wm = n == 0 ? 0L : (n > 0 ? l + (long)n * 1000L + 2500L : Math.max(this.wm, l + 2500L));
        }
    }

    private void bF(String string) {
        if (string == null || string.isBlank()) {
            return;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (this.a(string2, wj)) {
            this.clear();
            return;
        }
        if (ServerDetector.dj(string)) {
            long l = System.currentTimeMillis();
            this.wm = Math.max(this.wm, l + 8000L);
        }
    }

    private boolean hD() {
        return System.currentTimeMillis() < this.wm;
    }

    private boolean a(String string, List<String> list) {
        for (String string2 : list) {
            if (!string.contains(string2)) continue;
            return true;
        }
        return false;
    }

    private boolean bG(String string) {
        return string.equals("hub") || string.startsWith("hub ") || string.equals("lobby") || string.startsWith("lobby ") || string.equals("spawn") || string.startsWith("spawn ");
    }

    private String aY(String string) {
        String string2 = string.trim();
        if (string2.startsWith("/")) {
            string2 = string2.substring(1).trim();
        }
        return string2.toLowerCase(Locale.ROOT);
    }

    private void clear() {
        this.wm = 0L;
        this.wn = -1L;
    }
}

