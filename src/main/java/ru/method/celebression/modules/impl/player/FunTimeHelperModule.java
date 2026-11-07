package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.Locale;
import java.util.regex.Pattern;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_124;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_243;
import net.minecraft.class_3544;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.utils.render.Render3D;

public class FunTimeHelperModule
extends Module {
    private final BindSetting up = new BindSetting("settings.funtimehelper.binddesor", new KeyBind(-1, false));
    private final BindSetting uq = new BindSetting("settings.funtimehelper.bindtrapka", new KeyBind(-1, false));
    private final BindSetting ur = new BindSetting("settings.funtimehelper.bindyavka", new KeyBind(-1, false));
    private final BindSetting us = new BindSetting("settings.funtimehelper.bindbozhka", new KeyBind(-1, false));
    private final BindSetting ut = new BindSetting("settings.funtimehelper.bindplast", new KeyBind(-1, false));
    private final BindSetting uu = new BindSetting("settings.funtimehelper.bindsnowball", new KeyBind(-1, false));
    private static final String[] uv = new String[]{"\u0434\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446"};
    private static final String[] uw = new String[]{"\u0442\u0440\u0430\u043f\u043a\u0430"};
    private static final String[] uA = new String[]{"\u044f\u0432\u043d\u0430\u044f", "\u043f\u044b\u043b\u044c"};
    private static final String[] uB = new String[]{"\u0431\u043e\u0436\u044c\u044f", "\u0430\u0443\u0440\u0430"};
    private static final String[] uC = new String[]{"\u043f\u043b\u0430\u0441\u0442"};
    private static final String[] uD = new String[]{"\u0441\u043d\u0435\u0436\u043e\u043a", "\u0437\u0430\u043c\u043e\u0440\u043e\u0437"};
    private static final Pattern uE = Pattern.compile("\u00a7x(?:\u00a7[0-9a-fA-F]){6}");
    private static final Pattern uF = Pattern.compile("\u00a7.");

    public FunTimeHelperModule() {
        super("FTHelper", ModuleCategory.qm);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventHandler
    public void onKey(KeyInputEvent keyInputEvent) {
        if (FunTimeHelperModule.fullNullCheck() || FunTimeHelperModule.aiG.field_1755 != null) {
            return;
        }
        if (keyInputEvent.getAction() == 1) {
            if (((KeyBind)this.up.jT()).af(keyInputEvent.c())) {
                this.a("settings.funtimehelper.binddesor", uv);
            }
            if (((KeyBind)this.uq.jT()).af(keyInputEvent.c())) {
                this.a("settings.funtimehelper.bindtrapka", uw);
            }
            if (((KeyBind)this.ur.jT()).af(keyInputEvent.c())) {
                this.a("settings.funtimehelper.bindyavka", uA);
            }
            if (((KeyBind)this.us.jT()).af(keyInputEvent.c())) {
                this.a("settings.funtimehelper.bindbozhka", uB);
            }
            if (((KeyBind)this.ut.jT()).af(keyInputEvent.c())) {
                this.a("settings.funtimehelper.bindplast", uC);
            }
            if (((KeyBind)this.uu.jT()).af(keyInputEvent.c())) {
                this.a("settings.funtimehelper.bindsnowball", uD);
            }
        }
    }

    @EventHandler
    public void onMouse(EventMouse eventMouse) {
        if (FunTimeHelperModule.fullNullCheck() || FunTimeHelperModule.aiG.field_1755 != null) {
            return;
        }
        if (eventMouse.getAction() == 1) {
            if (((KeyBind)this.up.jT()).ag(eventMouse.getButton())) {
                this.a("settings.funtimehelper.binddesor", uv);
            }
            if (((KeyBind)this.uq.jT()).ag(eventMouse.getButton())) {
                this.a("settings.funtimehelper.bindtrapka", uw);
            }
            if (((KeyBind)this.ur.jT()).ag(eventMouse.getButton())) {
                this.a("settings.funtimehelper.bindyavka", uA);
            }
            if (((KeyBind)this.us.jT()).ag(eventMouse.getButton())) {
                this.a("settings.funtimehelper.bindbozhka", uB);
            }
            if (((KeyBind)this.ut.jT()).ag(eventMouse.getButton())) {
                this.a("settings.funtimehelper.bindplast", uC);
            }
            if (((KeyBind)this.uu.jT()).ag(eventMouse.getButton())) {
                this.a("settings.funtimehelper.bindsnowball", uD);
            }
        }
    }

    @EventHandler
    public void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (FunTimeHelperModule.fullNullCheck()) {
            return;
        }
        class_1799 class_17992 = FunTimeHelperModule.aiG.field_1724.method_6047();
        if (class_17992.method_7909() != class_1802.field_8449 && class_17992.method_7909() != class_1802.field_8479) {
            return;
        }
        class_243 class_2432 = FunTimeHelperModule.aiG.field_1724.method_19538();
        double d = 10.0;
        boolean bl = false;
        for (class_1657 class_16572 : FunTimeHelperModule.aiG.field_1687.method_18456()) {
            if (class_16572 == FunTimeHelperModule.aiG.field_1724 || !(class_16572.method_5858((class_1297)FunTimeHelperModule.aiG.field_1724) <= d * d)) continue;
            bl = true;
            break;
        }
        Color color = bl ? new Color(0, 255, 0, 255) : new Color(255, 0, 0, 255);
        Render3D.renderCircle(matrixStackStage.l(), class_2432, d, color);
    }

    private void a(String string, String[] stringArray) {
        int n = this.a(stringArray);
        String string2 = class_1074.method_4662((String)string, (Object[])new Object[0]);
        if (n == -1) {
            ChatOutput.cG(String.valueOf(class_124.field_1068) + "\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + String.valueOf(class_124.field_1061) + string2 + String.valueOf(class_124.field_1068) + " \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d!!");
            return;
        }
        if (FunTimeHelperModule.aiG.field_1724.method_31548().field_7545 == n) {
            ChatOutput.cG("\u0421\u043b\u043e\u0442 \u0443\u0436\u0435 \u0432\u044b\u0431\u0440\u0430\u043d");
            return;
        }
        FunTimeHelperModule.aiG.field_1724.method_31548().field_7545 = n;
        ChatOutput.cG(String.valueOf(class_124.field_1068) + "\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + String.valueOf(class_124.field_1060) + string2 + String.valueOf(class_124.field_1068) + " \u0432\u0437\u044f\u0442!");
    }

    private int a(String[] stringArray) {
        for (int i = 0; i <= 8; ++i) {
            String string;
            class_1799 class_17992 = FunTimeHelperModule.aiG.field_1724.method_31548().method_5438(i);
            if (class_17992.method_7960() || (string = this.aR(class_17992.method_7964().getString())).isEmpty() || !this.b(string, stringArray)) continue;
            return i;
        }
        return -1;
    }

    private String aR(String string) {
        if (class_3544.method_15438((String)string)) {
            return "";
        }
        String string2 = uE.matcher(string).replaceAll("");
        string2 = uF.matcher(string2).replaceAll("");
        string2 = string2.replace("[\u2605]", "").trim();
        return string2.toLowerCase(Locale.ROOT).replaceAll("\\s+", " ").trim();
    }

    private boolean b(String string, String[] stringArray) {
        for (String string2 : stringArray) {
            if (string.contains(string2)) continue;
            return false;
        }
        return true;
    }
}

