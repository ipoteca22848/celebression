package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import net.minecraft.class_2561;
import net.minecraft.class_5250;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.utils.render.ColorUtils;

public class ChatHelperModule
extends Module {
    private static ChatHelperModule sy;
    private static final DateTimeFormatter sz;
    private final BooleanSetting sA = new BooleanSetting("modules.settings.chat_helper.chat_history", true);
    private final BooleanSetting sB = new BooleanSetting("modules.settings.chat_helper.anti_spam", true);
    private final BooleanSetting sC = new BooleanSetting("modules.settings.chat_helper.show_time", false);

    public ChatHelperModule() {
        super("ChatHelper", ModuleCategory.qm);
        sy = this;
    }

    public static boolean gC() {
        return sy != null && sy.isToggled() && (Boolean)ChatHelperModule.sy.sA.jT() != false;
    }

    public static boolean gD() {
        return sy != null && sy.isToggled() && (Boolean)ChatHelperModule.sy.sB.jT() != false;
    }

    public static boolean gE() {
        return sy != null && sy.isToggled() && (Boolean)ChatHelperModule.sy.sC.jT() != false;
    }

    public static class_2561 c(class_2561 class_25612) {
        if (!ChatHelperModule.gE() || class_25612 == null) {
            return class_25612;
        }
        String string = "[" + LocalTime.now().format(sz) + "] ";
        int n = ColorUtils.getPrimaryColor().getRGB() & 0xFFFFFF;
        class_5250 class_52502 = class_2561.method_43470((String)string).method_27694(class_25832 -> class_25832.method_36139(n));
        return class_2561.method_43473().method_10852((class_2561)class_52502).method_10852((class_2561)class_25612.method_27661());
    }

    static {
        sz = DateTimeFormatter.ofPattern("HH:mm:ss");
    }
}

