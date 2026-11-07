package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import java.util.Locale;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_2596;
import net.minecraft.class_2868;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.inventory.InventoryUtils;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.setting.PriceParser;
import ru.method.celebression.api.setting.SliderSetting;

public class AutoRelistModule
extends Module {
    private static final long rS = 500L;
    private static final long rT = 300L;
    private static final long rU = 5000L;
    private static final long rV = 2000L;
    private static final String[] rW = new String[]{"\u0445\u0440\u0430\u043d\u0438\u043b\u0438", "\u043c\u043e\u0438 \u0442\u043e\u0432\u0430\u0440", "\u043c\u043e\u0438 \u043b\u043e\u0442", "\u043c\u043e\u0438 \u043f\u0440\u043e\u0434\u0430\u0436", "\u0442\u043e\u0432\u0430\u0440", "\u043f\u0440\u0435\u0434\u043c\u0435\u0442", "\u043b\u043e\u0442", "storage", "items", "sales", "lots"};
    private final EnumSetting<RelistEntry> rX = new EnumSetting<RelistEntry>("modules.settings.auto_relist.mode", RelistEntry.se);
    private final SliderSetting rY = new SliderSetting("modules.settings.auto_relist.delay", 65.0f, 20.0f, 180.0f, 5.0f);
    private long rZ;
    private long sa;
    private int sb;
    private long sc = -1L;
    private class_1799 sd;

    public AutoRelistModule() {
        super("AutoRelist", ModuleCategory.qm);
    }

    @Override
    public void onEnable() {
        this.rZ = System.currentTimeMillis() + 2000L;
        this.gt();
        super.onEnable();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (AutoRelistModule.fullNullCheck() || AutoRelistModule.aiG.field_1724.field_3944 == null) {
            return;
        }
        long l = System.currentTimeMillis();
        if (this.sb == 0 && AutoRelistModule.aiG.field_1755 != null) {
            this.rZ = l + 2000L;
            return;
        }
        if (this.rX.jT() == RelistEntry.sf) {
            this.gn();
            return;
        }
        if (l < this.rZ) {
            return;
        }
        AutoRelistModule.aiG.field_1724.field_3944.method_45730("ah resell");
        this.rZ = l + (long)(((Float)this.rY.jT()).floatValue() * 1000.0f);
    }

    private void gn() {
        long l = System.currentTimeMillis();
        if (this.sb == 0) {
            if (l < this.rZ) {
                return;
            }
            AutoRelistModule.aiG.field_1724.field_3944.method_45730("ah");
            this.sb = 1;
            this.sa = l;
            return;
        }
        if (l - this.sa > 5000L) {
            this.gt();
            return;
        }
        switch (this.sb) {
            case 1: {
                if (l - this.sa < 500L || !this.gq()) {
                    return;
                }
                if (!this.go()) break;
                this.sb = 2;
                this.sa = l;
                break;
            }
            case 2: {
                if (l - this.sa < 500L || !this.gr()) {
                    return;
                }
                class_1703 class_17032 = this.gp();
                if (class_17032 == null) {
                    this.gt();
                    return;
                }
                int n = this.a(class_17032);
                if (n < 0) {
                    this.h(l);
                    return;
                }
                this.sc = PriceParser.w(class_17032.method_7611(n).method_7677());
                this.sd = class_17032.method_7611(n).method_7677().method_7972();
                this.a(class_17032, n);
                this.sb = 3;
                this.sa = l;
                break;
            }
            case 3: {
                if (l - this.sa < 500L) {
                    return;
                }
                if (AutoRelistModule.aiG.field_1724 != null) {
                    AutoRelistModule.aiG.field_1724.method_7346();
                }
                this.sb = 4;
                this.sa = l;
                break;
            }
            case 4: {
                if (l - this.sa < 300L) {
                    return;
                }
                if (this.gp() != null) {
                    if (AutoRelistModule.aiG.field_1724 != null) {
                        AutoRelistModule.aiG.field_1724.method_7346();
                    }
                    return;
                }
                if (this.sd != null && this.sc > 0L && this.l(this.sd)) {
                    AutoRelistModule.aiG.field_1724.field_3944.method_45730("ah sell " + this.sc);
                }
                this.h(l);
                break;
            }
            default: {
                this.gt();
            }
        }
    }

    private int a(class_1703 class_17032) {
        int n = this.b(class_17032);
        for (int i = 0; i < n; ++i) {
            class_1735 class_17352 = class_17032.method_7611(i);
            if (class_17352 == null || !class_17352.method_7681() || PriceParser.w(class_17352.method_7677()) < 0L) continue;
            return i;
        }
        return -1;
    }

    private boolean l(class_1799 class_17992) {
        if (AutoRelistModule.aiG.field_1724 == null || AutoRelistModule.aiG.field_1761 == null || AutoRelistModule.aiG.field_1724.field_3944 == null) {
            return false;
        }
        class_1661 class_16612 = AutoRelistModule.aiG.field_1724.method_31548();
        int n = -1;
        for (int i = 0; i < 36; ++i) {
            class_1799 class_17993 = class_16612.method_5438(i);
            if (class_17993.method_7960() || class_17993.method_7909() != class_17992.method_7909()) continue;
            n = i;
            break;
        }
        if (n < 0) {
            return false;
        }
        if (n <= 8) {
            class_16612.field_7545 = n;
            AutoRelistModule.aiG.field_1724.field_3944.method_52787((class_2596)new class_2868(n));
        } else {
            InventoryUtils.a(AutoRelistModule.aiG.field_1724.field_7498.field_7763, n, class_16612.field_7545, class_1713.field_7791);
        }
        return true;
    }

    private boolean go() {
        class_1703 class_17032 = this.gp();
        if (class_17032 == null) {
            return false;
        }
        int n = this.b(class_17032) - 9;
        if (n < 0) {
            return false;
        }
        if (this.a(class_17032, n, n + 2, true, rW)) {
            return true;
        }
        return this.a(class_17032, n + 2, n + 1, n);
    }

    private boolean a(class_1703 class_17032, int n, int n2, boolean bl, String ... stringArray) {
        if (stringArray.length == 0) {
            return false;
        }
        int n3 = Math.max(0, Math.min(n, n2));
        int n4 = Math.min(this.b(class_17032) - 1, Math.max(n, n2));
        if (n4 < n3) {
            return false;
        }
        if (bl) {
            for (int i = n4; i >= n3; --i) {
                if (!this.a(class_17032.method_7611(i), stringArray)) continue;
                this.a(class_17032, i);
                return true;
            }
            return false;
        }
        for (int i = n3; i <= n4; ++i) {
            if (!this.a(class_17032.method_7611(i), stringArray)) continue;
            this.a(class_17032, i);
            return true;
        }
        return false;
    }

    private boolean a(class_1735 class_17352, String ... stringArray) {
        if (class_17352 == null || !class_17352.method_7681()) {
            return false;
        }
        class_1799 class_17992 = class_17352.method_7677();
        String string = class_17992.method_7964().getString().toLowerCase(Locale.ROOT);
        for (String string2 : stringArray) {
            if (string2.isEmpty() || !string.contains(string2)) continue;
            return true;
        }
        return false;
    }

    private boolean a(class_1703 class_17032, int ... nArray) {
        int n = this.b(class_17032) - 1;
        for (int n2 : nArray) {
            if (n2 < 0 || n2 > n || !class_17032.method_7611(n2).method_7681()) continue;
            this.a(class_17032, n2);
            return true;
        }
        return false;
    }

    private void a(class_1703 class_17032, int n) {
        if (AutoRelistModule.aiG.field_1761 == null || AutoRelistModule.aiG.field_1724 == null) {
            return;
        }
        InventoryUtils.a(class_17032.field_7763, n, 0, class_1713.field_7790);
    }

    private class_1703 gp() {
        if (AutoRelistModule.aiG.field_1724 == null || AutoRelistModule.aiG.field_1761 == null) {
            return null;
        }
        class_1703 class_17032 = AutoRelistModule.aiG.field_1724.field_7512;
        return class_17032 == null || class_17032 == AutoRelistModule.aiG.field_1724.field_7498 ? null : class_17032;
    }

    private int b(class_1703 class_17032) {
        return Math.max(0, class_17032.field_7761.size() - 36);
    }

    private boolean gq() {
        String string = this.gs();
        return string.contains("\u0430\u0443\u043a\u0446\u0438\u043e\u043d") || string.contains("auction") || string.contains("ah");
    }

    private boolean gr() {
        String string = this.gs();
        return string.contains("\u0445\u0440\u0430\u043d\u0438\u043b\u0438") || string.contains("\u0441\u043a\u043b\u0430\u0434") || string.contains("storage");
    }

    private String gs() {
        if (AutoRelistModule.aiG.field_1755 == null || AutoRelistModule.aiG.field_1755.method_25440() == null) {
            return "";
        }
        return AutoRelistModule.aiG.field_1755.method_25440().getString().toLowerCase(Locale.ROOT);
    }

    private void h(long l) {
        this.gt();
        this.rZ = l + (long)(((Float)this.rY.jT()).floatValue() * 1000.0f);
    }

    private void gt() {
        this.sb = 0;
        this.sa = 0L;
        this.sc = -1L;
        this.sd = null;
    }

    static enum RelistEntry
    implements NamedOption  {
        se("modules.settings.auto_relist.mode.prince"),
        sf("modules.settings.auto_relist.mode.player");
        private final String sg;

private RelistEntry(String string2) {
            this.sg = string2;
        }

        @Override
        public String getName() {
            return this.sg;
        }

}
}

