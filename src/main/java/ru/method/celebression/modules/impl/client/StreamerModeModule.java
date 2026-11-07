package ru.method.celebression.modules.impl.client;

import ru.method.celebression.api.setting.Setting;

import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.StringSetting;

public class StreamerModeModule
extends Module {
    private final BooleanSetting wW = new BooleanSetting("settings.streamermode.hide_anarchy", true);
    private final StringSetting wX = new StringSetting("settings.streamermode.name", "", "settings.streamermode.name.placeholder", "", 20, null);
    private final StringSetting wY = new StringSetting("settings.streamermode.donate", "", "settings.streamermode.donate.placeholder", "", 20, null){

        @Override
        public void bI(String string) {
            super.bI(StreamerModeModule.stripObfuscatedFormatting(string));
        }
    };
    private final StringSetting wZ = new StringSetting("settings.streamermode.coins", "", "settings.streamermode.coins.placeholder", "", 16, null);
    private final StringSetting xa = new StringSetting("settings.streamermode.friend_nicks", "", "settings.streamermode.friend_nicks.placeholder", "", 256, null);

    public StreamerModeModule() {
        super("StreamerMode", ModuleCategory.qn);
        this.getSettings().add(this.wW);
        this.getSettings().add(this.wX);
        this.getSettings().add(this.wY);
        this.getSettings().add(this.wZ);
        this.getSettings().add(this.xa);
    }

    public boolean hK() {
        return (Boolean)this.wW.jT();
    }

    public String hL() {
        return this.wX.kg();
    }

    public String hM() {
        return this.wY.kg();
    }

    public String hN() {
        return this.wZ.kg();
    }

    public String hO() {
        return this.xa.kg();
    }

    static String stripObfuscatedFormatting(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if ((c == '&' || c == '\u00a7') && i + 1 < string.length() && Character.toLowerCase(string.charAt(i + 1)) == 'k') {
                ++i;
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
