package ru.method.celebression.hud;

import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.Setting;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Arrays;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_7923;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.hud.HudNotificationQueue;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import unkw.visualtest.client.hud.VisualHud;

public class ArmorHud
extends HudElement {
    private static final float gJ = 19.0f;
    private static final float gK = 4.0f;
    private static final float gL = 6.0f;
    private static final float gM = 5.6f;
    private static final float gN = 6.0f;
    private static final float gO = 17.0f;
    private static final float gP = 3.0f;
    private static final float gQ = 6.0f;
    private static final float gR = 24.0f;
    private static final float gS = 6.8f;
    private static final float gT = 49.0f;
    private static final float gU = 12.0f;
    private static final long gV = 1900L;
    private static final Color gW = new Color(108, 221, 122);
    private static final Color gX = new Color(255, 211, 82);
    private static final Color gY = new Color(255, 108, 108);
    private static final Color gZ = new Color(170, 170, 170);
    private final EnumSetting<ArmorSlotEntry> ha = new EnumSetting<ArmorSlotEntry>("settings.armorhud.mode", ArmorSlotEntry.hf);
    private final AnimatedComponent hb = new AnimatedComponent(Easing.aiZ);
    private float hc = 1.0f;
    private final int[] hd = new int[4];
    private final String[] he = new String[4];

    public ArmorHud() {
        super("ArmorHud");
    }

    @Override
    public void onEnable() {
        this.cj();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.cj();
        super.onDisable();
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (ArmorHud.fullNullCheck() || this.ha.jT() != ArmorSlotEntry.hg) {
            this.cj();
            return;
        }
        for (int i = 0; i < 4; ++i) {
            int n;
            class_1799 class_17992 = ArmorHud.aiG.field_1724.method_31548().method_7372(i);
            String string = this.c(class_17992);
            if (!string.equals(this.he[i])) {
                this.he[i] = string;
                this.hd[i] = 0;
            }
            if ((n = this.b(class_17992)) <= 0) {
                this.hd[i] = 0;
                continue;
            }
            if (n > this.hd[i]) {
                this.a(class_17992, this.a(class_17992), n);
            }
            this.hd[i] = n;
        }
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        if (ArmorHud.fullNullCheck() || this.bL()) {
            return;
        }
        if (HudThemeRenderer.active() && HudThemeRenderer.cc() == HudThemeRenderer.ThemeMetrics.gz && this.ha.jT() == ArmorSlotEntry.hf) {
            float f = this.getX();
            float f2 = this.getY();
            class_4587 class_45872 = hudRenderEvent.j().method_51448();
            float f3 = this.b(class_45872, f, f2);
            float f4 = this.gn.rk() * this.bJ();
            class_45872.method_22903();
            class_45872.method_46416(f, f2, 0.0f);
            class_45872.method_22905(f4, f4, 1.0f);
            class_45872.method_46416(-f, -f2, 0.0f);
            boolean bl = VisualHud.cardArmor(hudRenderEvent.j(), aiG, Math.round(f), Math.round(f2));
            class_45872.method_22909();
            this.b(class_45872);
            if (bl || HudEditorScreen.mb()) {
                this.b(f, f2, 82.0f, 25.0f, f3);
            }
            super.onRender2D(hudRenderEvent);
            return;
        }
        boolean bl = this.ci();
        boolean bl2 = !bl || HudEditorScreen.mb();
        this.hc = this.hb.a(bl2 ? 1.0f : 0.0f, 220L);
        if (bl && this.hc <= 0.02f) {
            return;
        }
        if (this.ha.jT() == ArmorSlotEntry.hg) {
            this.b(hudRenderEvent);
        } else {
            this.a(hudRenderEvent);
        }
        super.onRender2D(hudRenderEvent);
    }

    @Override
    protected boolean b(int n, int n2) {
        return this.ha.jT() == ArmorSlotEntry.hg;
    }

    private boolean ci() {
        if (ArmorHud.aiG.field_1724 == null) {
            return true;
        }
        for (int i = 0; i < 4; ++i) {
            class_1799 class_17992 = ArmorHud.aiG.field_1724.method_31548().method_7372(i);
            if (class_17992 == null || class_17992.method_7960()) continue;
            return false;
        }
        return true;
    }

    private void a(HudRenderEvent hudRenderEvent) {
        boolean bl = HudEditorScreen.mb();
        float f = 100.0f;
        float f2 = 39.0f;
        float f3 = this.getX();
        float f4 = this.getY();
        float f5 = this.b(hudRenderEvent.j().method_51448(), f3, f4);
        hudRenderEvent.j().method_51448().method_22903();
        float f6 = this.gn.rk() * this.bJ() * this.hc;
        float f7 = f3 + f / 2.0f;
        float f8 = f4 + f2 / 2.0f;
        hudRenderEvent.j().method_51448().method_46416(f7, f8, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f6, f6, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-f7, -f8, 0.0f);
        boolean bl2 = HudThemeRenderer.active();
        if (bl2) {
            HudThemeRenderer.b(hudRenderEvent.j().method_51448(), f3, f4, f, f2, HudThemeRenderer.f(f, f2), bl ? 1.0f : 0.96f);
        } else {
            Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f3, f4, f, f2, new BorderRadius(10.0f), new ColorRGBA(ColorUtils.getGlassColor(bl ? 235 : 210)), 2.5f, 22.0f, 10.0f, 175.0f, true, 0.0f, 0.2f);
        }
        for (int i = 0; i < 4; ++i) {
            float f9 = f3 + 6.0f + (float)i * 23.0f;
            float f10 = f4 + 6.0f;
            if (bl2) {
                HudThemeRenderer.c(hudRenderEvent.j().method_51448(), f9 + 1.0f, f10 + 1.0f, 17.0f, 17.0f, 5.0f, 1.0f);
            } else {
                Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f9 + 1.0f, f10 + 1.0f, 17.0f, 17.0f, 5.0f, new Color(255, 255, 255, 26));
            }
            class_1799 class_17992 = ArmorHud.aiG.field_1724.method_31548().method_7372(3 - i);
            if (class_17992.method_7960()) continue;
            this.b(hudRenderEvent, class_17992, f9 + 1.5f, f10 + 1.5f);
            this.a(hudRenderEvent, class_17992, f9, f10 + 19.0f + 0.6f);
        }
        hudRenderEvent.j().method_51448().method_22909();
        this.b(hudRenderEvent.j().method_51448());
        this.b(f3, f4, f, f2, f5);
    }

    private void a(HudRenderEvent hudRenderEvent, class_1799 class_17992, float f, float f2) {
        int n = this.a(class_17992);
        if (n < 0) {
            return;
        }
        String string = n + "%";
        float f3 = Fonts.ROUNDBOLD.getWidth(string, 5.6f);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(5.6f), string, f + (19.0f - f3) / 2.0f, f2, this.h(n));
    }

    private void b(HudRenderEvent hudRenderEvent) {
        boolean bl = HudEditorScreen.mb();
        float f = 49.0f;
        float f2 = aiG.method_22683().method_4486();
        float f3 = aiG.method_22683().method_4502();
        float f4 = f2 / 2.0f - 91.0f;
        float f5 = 182.0f;
        float f6 = f4 + f5;
        float f7 = f3 - 11.0f - 12.0f;
        float f8 = f7 - f / 2.0f;
        float f9 = f4 - 6.0f - 24.0f;
        float f10 = f6 + 6.0f;
        float f11 = Math.max(0.0f, this.gn.rk() * this.bI() * this.hc);
        this.a(hudRenderEvent, f9 - 49.0f, f8, f9, f7, f11, bl, new int[]{3, 2});
        this.a(hudRenderEvent, f10, f8, f10, f7, f11, bl, new int[]{1, 0});
        float f12 = 49.0f * f11;
        float f13 = f * f11;
        float f14 = f9 - f12;
        float f15 = f7 - f13 / 2.0f;
        float f16 = f10 + f12 - f14;
        this.b(f14, f15, f16, f13);
    }

    private void a(HudRenderEvent hudRenderEvent, float f, float f2, float f3, float f4, float f5, boolean bl, int[] nArray) {
        if (f5 <= 0.0f) {
            return;
        }
        hudRenderEvent.j().method_51448().method_22903();
        hudRenderEvent.j().method_51448().method_46416(f3, f4, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f5, f5, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-f3, -f4, 0.0f);
        this.a(hudRenderEvent, f, f2, bl, nArray);
        hudRenderEvent.j().method_51448().method_22909();
    }

    private void a(HudRenderEvent hudRenderEvent, float f, float f2, boolean bl, int[] nArray) {
        float f3 = 49.0f;
        if (HudThemeRenderer.active()) {
            HudThemeRenderer.b(hudRenderEvent.j().method_51448(), f, f2, 49.0f, f3, HudThemeRenderer.f(49.0f, f3), bl ? 1.0f : 0.96f);
        } else {
            Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f, f2, 49.0f, f3, new BorderRadius(10.0f), new ColorRGBA(ColorUtils.getGlassColor(bl ? 235 : 215)), 2.5f, 22.0f, 10.0f, 175.0f, true, 0.0f, 0.2f);
        }
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            float f4 = f2 + 6.0f + (float)i * 20.0f;
            this.a(hudRenderEvent, f + 6.0f, f4, n);
        }
    }

    private void a(HudRenderEvent hudRenderEvent, float f, float f2, int n) {
        class_1799 class_17992 = ArmorHud.aiG.field_1724.method_31548().method_7372(n);
        int n2 = this.a(class_17992);
        if (HudThemeRenderer.active()) {
            HudThemeRenderer.c(hudRenderEvent.j().method_51448(), f, f2, 17.0f, 17.0f, 5.0f, 1.0f);
        } else {
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f, f2, 17.0f, 17.0f, 5.0f, new Color(255, 255, 255, 24));
        }
        if (class_17992.method_7960()) {
            return;
        }
        this.b(hudRenderEvent, class_17992, f + 0.5f, f2 + 0.5f);
        Object object = n2 < 0 ? "--" : n2 + "%";
        Color color = n2 < 0 ? gZ : this.h(n2);
        float f3 = Fonts.ROUNDBOLD.getWidth((String)object, 6.8f);
        float f4 = f + 49.0f - 12.0f;
        float f5 = f4 - f3;
        float f6 = f2 + (17.0f - Fonts.ROUNDBOLD.getHeight(6.8f)) / 2.0f + 0.5f;
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(6.8f), (String)object, f5, f6, color);
    }

    private void b(HudRenderEvent hudRenderEvent, class_1799 class_17992, float f, float f2) {
        try {
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            RenderSystem.enableDepthTest();
            class_4587 class_45872 = hudRenderEvent.j().method_51448();
            int n = (int)Math.floor(f);
            int n2 = (int)Math.floor(f2);
            class_45872.method_22903();
            class_45872.method_46416(f - (float)n, f2 - (float)n2, 0.0f);
            hudRenderEvent.j().method_51423((class_1309)ArmorHud.aiG.field_1724, class_17992, n, n2, 0);
            class_45872.method_22909();
            RenderSystem.disableDepthTest();
        }
        catch (Exception exception) {
        }
    }

    private int a(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return -1;
        }
        if (!class_17992.method_7963()) {
            return 100;
        }
        int n = class_17992.method_7936();
        if (n <= 0) {
            return 100;
        }
        int n2 = Math.max(0, n - class_17992.method_7919());
        return Math.max(0, Math.min(100, Math.round((float)n2 * 100.0f / (float)n)));
    }

    private Color h(int n) {
        boolean bl = HudThemeRenderer.isLightTheme();
        if (n < 25) {
            return bl ? new Color(198, 40, 40) : gY;
        }
        if (n < 50) {
            return bl ? new Color(176, 116, 0) : gX;
        }
        return bl ? new Color(34, 138, 60) : gW;
    }

    private int b(class_1799 class_17992) {
        int n = this.a(class_17992);
        if (n < 0) {
            return 0;
        }
        if (n < 25) {
            return 2;
        }
        if (n < 50) {
            return 1;
        }
        return 0;
    }

    private void a(class_1799 class_17992, int n, int n2) {
        HudNotificationQueue hudNotificationQueue = HudNotificationQueue.dG();
        Color color = n2 >= 2 ? gY : gX;
        String string = class_1074.method_4662((String)(n2 >= 2 ? "notifications.armorhud.critical" : "notifications.armorhud.low"), (Object[])new Object[]{class_17992.method_7964().getString(), n});
        hudNotificationQueue.a(string, color, 1900L);
    }

    private void cj() {
        Arrays.fill(this.hd, 0);
        Arrays.fill(this.he, "");
    }

    private String c(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return "";
        }
        return String.valueOf(class_7923.field_41178.method_10221(class_17992.method_7909())) + "|" + class_17992.method_7936();
    }

    static enum ArmorSlotEntry
    implements NamedOption  {
        hf("settings.armorhud.mode.v1"),
        hg("settings.armorhud.mode.v2");
        private final String hh;

private ArmorSlotEntry(String string2) {
            this.hh = string2;
        }

        @Override
        public String getName() {
            return this.hh;
        }

}
}
