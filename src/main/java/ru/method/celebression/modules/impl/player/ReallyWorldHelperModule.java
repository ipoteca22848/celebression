package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_124;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_243;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.inventory.InventoryUtils;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.server.ServerDetector;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.utils.render.Render3D;

public class ReallyWorldHelperModule
extends Module {
    private static final String wo = "\u041b\u043e\u0432\u0443\u0448\u043a\u0430";
    private static final String wp = "\u0422\u0451\u043c\u043d\u044b\u0439 \u041f\u0443\u043b\u044c\u0441";
    private final BindSetting wq = new BindSetting("settings.rwhelper.bindantifly", new KeyBind(-1, false));
    private final BindSetting wr = new BindSetting("settings.rwhelper.bindexpscroll", new KeyBind(-1, false));
    private final BindSetting ws = new BindSetting("settings.rwhelper.bindtrap", new KeyBind(-1, false));
    private final BindSetting wt = new BindSetting("settings.rwhelper.binddarkpulse", new KeyBind(-1, false));

    public ReallyWorldHelperModule() {
        super("RwHelper", ModuleCategory.qm);
    }

    @Override
    public void onEnable() {
        if (ReallyWorldHelperModule.fullNullCheck()) {
            this.toggled = false;
            return;
        }
        if (ServerDetector.sh()) {
            ChatOutput.cG(String.valueOf(class_124.field_1061) + "RwHelper \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043d\u0430 Funtime \u0441\u0435\u0440\u0432\u0435\u0440\u0435!");
            this.playCriticalSound();
            this.toggled = false;
            return;
        }
        super.onEnable();
    }

    @EventHandler
    public void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (ReallyWorldHelperModule.fullNullCheck() || ServerDetector.sh()) {
            return;
        }
        class_1799 class_17992 = ReallyWorldHelperModule.aiG.field_1724.method_6047();
        class_1799 class_17993 = ReallyWorldHelperModule.aiG.field_1724.method_6079();
        if (class_17992.method_7909() != class_1802.field_8450 && class_17993.method_7909() != class_1802.field_8450) {
            return;
        }
        class_243 class_2432 = ReallyWorldHelperModule.aiG.field_1724.method_19538();
        double d = 16.0;
        boolean bl = false;
        for (class_1657 class_16572 : ReallyWorldHelperModule.aiG.field_1687.method_18456()) {
            if (class_16572 == ReallyWorldHelperModule.aiG.field_1724 || !(class_16572.method_5858((class_1297)ReallyWorldHelperModule.aiG.field_1724) <= d * d)) continue;
            bl = true;
            break;
        }
        Color color = bl ? new Color(0, 255, 0, 255) : new Color(255, 0, 0, 255);
        Render3D.renderCircle(matrixStackStage.l(), class_2432, d, color);
    }

    @EventHandler
    public void onKey(KeyInputEvent keyInputEvent) {
        if (ReallyWorldHelperModule.fullNullCheck() || ReallyWorldHelperModule.aiG.field_1755 != null) {
            return;
        }
        if (ServerDetector.sh()) {
            ChatOutput.cG(String.valueOf(class_124.field_1061) + "RwHelper \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043d\u0430 Funtime \u0441\u0435\u0440\u0432\u0435\u0440\u0435!");
            this.playCriticalSound();
            this.toggle();
            return;
        }
        if (keyInputEvent.getAction() == 1) {
            if (((KeyBind)this.wq.jT()).af(keyInputEvent.c())) {
                this.c(class_1802.field_8450, "settings.rwhelper.bindantifly");
            }
            if (((KeyBind)this.wr.jT()).af(keyInputEvent.c())) {
                this.c(class_1802.field_8498, "settings.rwhelper.bindexpscroll");
            }
            if (((KeyBind)this.ws.jT()).af(keyInputEvent.c())) {
                this.hE();
            }
            if (((KeyBind)this.wt.jT()).af(keyInputEvent.c())) {
                this.a(class_1802.field_8450, "\u0422\u0451\u043c\u043d\u044b\u0439 \u041f\u0443\u043b\u044c\u0441", "settings.rwhelper.binddarkpulse");
            }
        }
    }

    @EventHandler
    public void onMouse(EventMouse eventMouse) {
        if (ReallyWorldHelperModule.fullNullCheck() || ReallyWorldHelperModule.aiG.field_1755 != null) {
            return;
        }
        if (ServerDetector.sh()) {
            ChatOutput.cG(String.valueOf(class_124.field_1061) + "RwHelper \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043d\u0430 Funtime \u0441\u0435\u0440\u0432\u0435\u0440\u0435!");
            this.playCriticalSound();
            this.toggle();
            return;
        }
        if (eventMouse.getAction() == 1) {
            if (((KeyBind)this.wq.jT()).ag(eventMouse.getButton())) {
                this.c(class_1802.field_8450, "settings.rwhelper.bindantifly");
            }
            if (((KeyBind)this.wr.jT()).ag(eventMouse.getButton())) {
                this.c(class_1802.field_8498, "settings.rwhelper.bindexpscroll");
            }
            if (((KeyBind)this.ws.jT()).ag(eventMouse.getButton())) {
                this.hE();
            }
            if (((KeyBind)this.wt.jT()).ag(eventMouse.getButton())) {
                this.a(class_1802.field_8450, "\u0422\u0451\u043c\u043d\u044b\u0439 \u041f\u0443\u043b\u044c\u0441", "settings.rwhelper.binddarkpulse");
            }
        }
    }

    private void hE() {
        this.a(class_1802.field_8207, "\u041b\u043e\u0432\u0443\u0448\u043a\u0430", "settings.rwhelper.bindtrap");
    }

    private void a(class_1792 class_17922, String string, String string2) {
        int n = this.b(class_17922, string);
        String string3 = class_1074.method_4662((String)string2, (Object[])new Object[0]);
        if (n == -1) {
            ChatOutput.cG(String.valueOf(class_124.field_1068) + "\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + String.valueOf(class_124.field_1061) + string3 + String.valueOf(class_124.field_1068) + " \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d!");
            return;
        }
        if (ReallyWorldHelperModule.aiG.field_1724.method_31548().field_7545 == n) {
            ChatOutput.cG(String.valueOf(class_124.field_1068) + "\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + String.valueOf(class_124.field_1054) + string3 + String.valueOf(class_124.field_1068) + " \u0443\u0436\u0435 \u0441\u0432\u0430\u043f\u043d\u0443\u0442!");
            return;
        }
        ReallyWorldHelperModule.aiG.field_1724.method_31548().field_7545 = n;
        ChatOutput.cG(String.valueOf(class_124.field_1068) + "\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + String.valueOf(class_124.field_1060) + string3 + String.valueOf(class_124.field_1068) + " \u0441\u0432\u0430\u043f\u043d\u0443\u0442!");
    }

    private int b(class_1792 class_17922, String string) {
        for (int i = 0; i < 9; ++i) {
            class_1799 class_17992 = ReallyWorldHelperModule.aiG.field_1724.method_31548().method_5438(i);
            if (!class_17992.method_31574(class_17922) || !string.equals(this.bH(class_17992.method_7964().getString()))) continue;
            return i;
        }
        return -1;
    }

    private String bH(String string) {
        if (string == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        boolean bl = false;
        for (int i = 0; i < string.length(); ++i) {
            char c;
            char c2 = string.charAt(i);
            if (bl) {
                bl = false;
                continue;
            }
            if ((c2 == '\u00a7' || c2 == '&') && i + 1 < string.length() && ((c = Character.toUpperCase(string.charAt(i + 1))) >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'K' && c <= 'O' || c == 'R')) {
                bl = true;
                continue;
            }
            stringBuilder.append(c2);
        }
        return stringBuilder.toString();
    }

    private void c(class_1792 class_17922, String string) {
        int n = InventoryUtils.h(class_17922);
        String string2 = class_1074.method_4662((String)string, (Object[])new Object[0]);
        if (n == -1) {
            ChatOutput.cG(String.valueOf(class_124.field_1068) + "\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + String.valueOf(class_124.field_1061) + string2 + String.valueOf(class_124.field_1068) + " \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d!");
            return;
        }
        if (ReallyWorldHelperModule.aiG.field_1724.method_31548().field_7545 == n) {
            ChatOutput.cG(String.valueOf(class_124.field_1068) + "\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + String.valueOf(class_124.field_1054) + string2 + String.valueOf(class_124.field_1068) + " \u0443\u0436\u0435 \u0441\u0432\u0430\u043f\u043d\u0443\u0442!");
            return;
        }
        ReallyWorldHelperModule.aiG.field_1724.method_31548().field_7545 = n;
        ChatOutput.cG(String.valueOf(class_124.field_1068) + "\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + String.valueOf(class_124.field_1060) + string2 + String.valueOf(class_124.field_1068) + " \u0441\u0432\u0430\u043f\u043d\u0443\u0442!");
    }
}

