package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_634;
import net.minecraft.class_7439;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.PacketEvent;
import ru.method.celebression.server.ServerDetector;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.setting.StringSetting;

public class AutoReplyModule
extends Module {
    private static final Pattern si = Pattern.compile("([A-Za-z\\u0410-\\u044f\\u0401\\u04510-9_]{2,16})\\s*(?:->|-->|\\u2192|\\u00bb|>>|\\u27a4)\\s*(?:\\u0432\\u0430\\u043c|\\u043c\\u043d\\u0435|\\u0442\\u0435\\u0431\\u0435|you|me)\\b", 66);
    private final StringSetting sj = new StringSetting("settings.autoreply.command", "/r", "/r  |  /msg <player>");
    private final StringSetting sk = new StringSetting("settings.autoreply.text", "\u0410\u0424\u041a, \u043e\u0442\u0432\u0435\u0447\u0443 \u043f\u043e\u0437\u0436\u0435", "");
    private final SliderSetting sl = new SliderSetting("settings.autoreply.cooldown", 30.0f, 3.0f, 300.0f, 1.0f);
    private final BooleanSetting sm = new BooleanSetting("settings.autoreply.only_funtime", true);
    private final BooleanSetting sn = new BooleanSetting("settings.autoreply.notify", true);
    private final Map<String, Long> so = new HashMap<String, Long>();
    private long sp = -1L;

    public AutoReplyModule() {
        super("AutoReply", ModuleCategory.qm);
        this.getSettings().add(this.sj);
        this.getSettings().add(this.sk);
        this.getSettings().add(this.sl);
        this.getSettings().add(this.sm);
        this.getSettings().add(this.sn);
    }

    @Override
    public void onDisable() {
        this.so.clear();
        this.sp = -1L;
        super.onDisable();
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive receive) {
        if (AutoReplyModule.fullNullCheck()) {
            return;
        }
        Object object = receive.i();
        if (!(object instanceof class_7439)) {
            return;
        }
        class_7439 class_74392 = (class_7439)object;
        if (((Boolean)this.sm.jT()).booleanValue() && !ServerDetector.sh()) {
            return;
        }
        object = class_74392.comp_763().getString();
        if (object == null || ((String)object).isBlank()) {
            return;
        }
        String string = this.aV((String)object);
        if (string == null) {
            return;
        }
        String string2 = AutoReplyModule.aiG.field_1724.method_5477().getString();
        if (string.equalsIgnoreCase(string2)) {
            return;
        }
        long l = System.currentTimeMillis();
        long l2 = (long)(((Float)this.sl.jT()).floatValue() * 1000.0f);
        if (this.sp > 0L && l - this.sp < 1000L) {
            return;
        }
        Long l3 = this.so.get(string.toLowerCase(Locale.ROOT));
        if (l3 != null && l - l3 < l2) {
            return;
        }
        this.aW(string);
        this.so.put(string.toLowerCase(Locale.ROOT), l);
        this.sp = l;
    }

    private String aV(String string) {
        Matcher matcher = si.matcher(string);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private void aW(String string) {
        class_634 class_6342 = aiG.method_1562();
        if (class_6342 == null) {
            return;
        }
        String string3 = this.sj.kg().replace("<player>", string).trim();
        String string4 = this.sk.kg().trim();
        if (string3.isEmpty() && string4.isEmpty()) {
            return;
        }
        String response = string3.isEmpty() ? string4 : (string4.isEmpty() ? string3 : string3 + " " + string4);
        if (response.startsWith("/")) {
            String string6 = response.substring(1).trim();
            if (!string6.isEmpty()) {
                class_6342.method_45730(string6);
            }
        } else {
            class_6342.method_45729(response);
        }
        if (((Boolean)this.sn.jT()).booleanValue()) {
            ChatOutput.cG("AutoReply \u2192 " + string);
        }
    }
}
