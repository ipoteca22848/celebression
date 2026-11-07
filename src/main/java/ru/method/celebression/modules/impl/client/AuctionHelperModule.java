package ru.method.celebression.modules.impl.client;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.Locale;
import net.minecraft.class_1735;
import net.minecraft.class_2371;
import net.minecraft.class_332;
import net.minecraft.class_465;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.PriceParser;
import ru.method.celebression.api.setting.StringSetting;
import ru.method.celebression.utils.render.Render2D;

public class AuctionHelperModule
extends Module {
    private final ColorSetting rE = new ColorSetting("settings.auctionhelper.cheapest", new Color(75, 255, 75, 255));
    private final ColorSetting rF = new ColorSetting("settings.auctionhelper.effective", new Color(255, 75, 75, 255));
    private final StringSetting rG = new StringSetting("settings.auctionhelper.title", "\u0430\u0443\u043a\u0446\u0438\u043e\u043d, auction", "\u0430\u0443\u043a\u0446\u0438\u043e\u043d, auction");
    private class_1735 rH;
    private class_1735 rI;
    private float rJ;
    private static final int rK = 3;
    private static final String[] rL = new String[]{"ah", "auction", "\u0430\u0443\u043a\u0446\u0438\u043e\u043d", "\u0430\u0445", "market"};
    private static final long rM = 8000L;
    private static volatile long rN = -8000L;
    private Object rO;

    public AuctionHelperModule() {
        super("AuctionHelper", ModuleCategory.qn);
        this.getSettings().add(this.rE);
        this.getSettings().add(this.rF);
        this.getSettings().add(this.rG);
    }

    public static void aT(String string) {
        if (string == null) {
            return;
        }
        String string2 = string.trim().toLowerCase(Locale.ROOT);
        while (string2.startsWith("/")) {
            string2 = string2.substring(1);
        }
        int n = string2.indexOf(32);
        String string3 = n < 0 ? string2 : string2.substring(0, n);
        for (String string4 : rL) {
            if (!string3.equals(string4)) continue;
            rN = System.currentTimeMillis();
            return;
        }
    }

    public void a(class_465<?> class_4652) {
        this.rH = null;
        this.rI = null;
        if (AuctionHelperModule.aiG.field_1724 == null || class_4652 == null || class_4652.method_17577() == null) {
            return;
        }
        if (!this.b(class_4652)) {
            return;
        }
        class_2371<class_1735> class_23712 = class_4652.method_17577().field_7761;
        long l = Long.MAX_VALUE;
        long l2 = -1L;
        int n = 0;
        for (class_1735 class_17352 : class_23712) {
            long l3;
            if (!this.a(class_17352) || (l3 = PriceParser.w(class_17352.method_7677())) < 0L) continue;
            ++n;
            if (l3 < l) {
                l = l3;
                this.rH = class_17352;
            }
            if (l3 <= l2) continue;
            l2 = l3;
            this.rI = class_17352;
        }
        if (n < 3) {
            this.rH = null;
            this.rI = null;
            return;
        }
        if (this.rI == this.rH || l2 == l) {
            this.rI = null;
        }
        this.rJ = this.gl();
    }

    public void b(class_332 class_3322, int n, int n2) {
        this.a(class_3322, this.rH, n, n2, (Color)this.rE.jT());
        this.a(class_3322, this.rI, n, n2, (Color)this.rF.jT());
    }

    private void a(class_332 class_3322, class_1735 class_17352, int n, int n2, Color color) {
        if (class_17352 == null) {
            return;
        }
        int n3 = Math.max(0, Math.min(255, Math.round((float)color.getAlpha() * this.rJ * 0.55f)));
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n3);
        Render2D.drawRoundedRect(class_3322.method_51448(), n + class_17352.field_7873, n2 + class_17352.field_7872, 16.0f, 16.0f, 0.0f, color2);
    }

    private boolean b(class_465<?> class_4652) {
        if (class_4652 == this.rO) {
            return true;
        }
        if (this.c(class_4652)) {
            return true;
        }
        if (System.currentTimeMillis() - rN <= 8000L) {
            this.rO = class_4652;
            return true;
        }
        return false;
    }

    private boolean c(class_465<?> class_4652) {
        String string;
        if (class_4652.method_25440() == null) {
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (char n : class_4652.method_25440().getString().toLowerCase(Locale.ROOT).toCharArray()) {
            if (n >= '\ue000' && n <= '\uf8ff') continue;
            stringBuilder.append(n);
        }
        Object object = stringBuilder.toString();
        String string2 = string = this.rG.kg() == null ? "" : this.rG.kg().trim();
        if (string.isEmpty()) {
            string = "\u0430\u0443\u043a\u0446\u0438\u043e\u043d, auction";
        }
        for (String string3 : string.toLowerCase(Locale.ROOT).split(",")) {
            String string4 = string3.trim();
            if (string4.isEmpty() || !((String)object).contains(string4)) continue;
            return true;
        }
        return false;
    }

    private boolean a(class_1735 class_17352) {
        if (class_17352 == null) {
            return false;
        }
        if (AuctionHelperModule.aiG.field_1724 != null && class_17352.field_7871 == AuctionHelperModule.aiG.field_1724.method_31548()) {
            return false;
        }
        return !class_17352.method_7677().method_7960();
    }

    private float gl() {
        float f = (float)(System.currentTimeMillis() % 900L) / 900.0f;
        float f2 = 0.5f - 0.5f * (float)Math.cos((double)f * 2.0 * Math.PI);
        return 0.4f + 0.6f * f2;
    }
}
