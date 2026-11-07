package ru.method.celebression.hud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import unkw.visualtest.client.hud.VisualHud;

public class LowArmorNotificationsHud
extends HudElement {
    private static final long jN = 3500L;
    private static final long jO = 1000L;
    private static final List<ArmorWarning> jP = new ArrayList<ArmorWarning>();

    public LowArmorNotificationsHud() {
        super("LowArmorNotificationsHud");
    }

    public static void ay(String string) {
        if (string == null || string.isBlank()) {
            return;
        }
        jP.removeIf(armorWarning -> armorWarning.jQ.equals(string));
        jP.add(new ArmorWarning(string));
        if (jP.size() > 4) {
            jP.remove(0);
        }
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        if (LowArmorNotificationsHud.fullNullCheck() || this.bL()) {
            return;
        }
        if (HudThemeRenderer.active() && HudThemeRenderer.cc() == HudThemeRenderer.ThemeMetrics.gz) {
            float f = this.getX();
            float f2 = this.getY();
            class_4587 class_45872 = hudRenderEvent.j().method_51448();
            float f3 = this.b(class_45872, f, f2);
            float f4 = this.gn.rk() * this.bJ();
            class_45872.method_22903();
            class_45872.method_46416(f, f2, 0.0f);
            class_45872.method_22905(f4, f4, 1.0f);
            class_45872.method_46416(-f, -f2, 0.0f);
            boolean bl = VisualHud.cardDurability(hudRenderEvent.j(), aiG, Math.round(f), Math.round(f2));
            class_45872.method_22909();
            this.b(class_45872);
            if (bl) {
                this.b(f, f2, 112.0f, 27.0f, f3);
            }
            super.onRender2D(hudRenderEvent);
            return;
        }
        boolean bl = HudEditorScreen.mb();
        if (bl && jP.isEmpty()) {
            jP.add(new ArmorWarning("\u041f\u0440\u0435\u0434\u043c\u0435\u0442 \u0441\u043a\u043e\u0440\u043e \u0441\u043b\u043e\u043c\u0430\u0435\u0442\u0441\u044f: \u0448\u043b\u0435\u043c (12%)", true));
        }
        long l = System.currentTimeMillis();
        Iterator<ArmorWarning> iterator = jP.iterator();
        while (iterator.hasNext()) {
            ArmorWarning armorWarning = iterator.next();
            boolean bl2 = this.a(armorWarning, bl, l);
            armorWarning.jU.i(bl2);
            if (bl2 || !(armorWarning.jU.rk() <= 0.01f)) continue;
            iterator.remove();
        }
        if (jP.isEmpty()) {
            return;
        }
        float f = this.getX();
        float f5 = this.getY();
        float f6 = 7.0f;
        float f7 = 19.0f;
        float f8 = 5.0f;
        float f9 = 8.4f;
        float f10 = 10.0f;
        String string = "!";
        float f11 = Fonts.BOLD.getWidth(string, f10);
        float f12 = 6.0f;
        float f13 = 0.0f;
        for (ArmorWarning armorWarning : jP) {
            f13 = Math.max(f13, Fonts.MEDIUM.getWidth(armorWarning.jQ, f9));
        }
        float f14 = aiG.method_22683().method_4486();
        float f15 = Math.max(150.0f, Math.min(380.0f, f13 + f11 + f12 + f6 * 2.0f));
        f = class_3532.method_15363((float)f, (float)4.0f, (float)Math.max(4.0f, f14 - f15 - 4.0f));
        float f16 = (float)jP.size() * f7 + (float)Math.max(0, jP.size() - 1) * f8;
        float f17 = this.b(hudRenderEvent.j().method_51448(), f, f5);
        hudRenderEvent.j().method_51448().method_22903();
        float[] fArray = this.bK();
        float f18 = this.gn.rk() * this.bJ();
        float f19 = f + f15 / 2.0f + fArray[0];
        float f20 = f5 + f16 / 2.0f + fArray[1];
        hudRenderEvent.j().method_51448().method_46416(f19, f20, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f18, f18, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-f19, -f20, 0.0f);
        float f21 = f5;
        for (ArmorWarning armorWarning : jP) {
            float f22 = Math.max(0.0f, Math.min(1.0f, armorWarning.jU.rk()));
            if (f22 <= 0.01f) continue;
            int n = Math.round(255.0f * f22);
            if (HudThemeRenderer.active()) {
                HudThemeRenderer.b(hudRenderEvent.j().method_51448(), f, f21, f15, f7, HudThemeRenderer.f(f15, f7), f22);
            } else {
                Color color = ColorUtils.getGlassColor(n);
                Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f, f21, f15, f7, new BorderRadius(9.0f), new ColorRGBA(color), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
            }
            float f23 = f + f6;
            float f24 = Fonts.BOLD.getHeight(f10);
            Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.BOLD.getFont(f10), string, f23, f21 + (f7 - f24) / 2.0f, new Color(255, 86, 86, n));
            float f25 = f23 + f11 + f12;
            float f26 = Fonts.MEDIUM.getHeight(f9);
            boolean bl3 = HudThemeRenderer.active() && HudThemeRenderer.isLightTheme();
            Color color = bl3 ? new Color(0, 0, 0, n) : new Color(255, 255, 255, n);
            Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.MEDIUM.getFont(f9), this.b(armorWarning.jQ, f15 - (f25 - f) - f6, f9), f25, f21 + (f7 - f26) / 2.0f, color);
            f21 += f7 + f8;
        }
        hudRenderEvent.j().method_51448().method_22909();
        this.b(hudRenderEvent.j().method_51448());
        this.b(f, f5, f15, f16, f17);
        super.onRender2D(hudRenderEvent);
    }

    private String b(String string, float f, float f2) {
        if (Fonts.MEDIUM.getWidth(string, f2) <= f) {
            return string;
        }
        String string2 = "...";
        String string3 = string;
        while (!string3.isEmpty() && Fonts.MEDIUM.getWidth(string3 + string2, f2) > f) {
            string3 = string3.substring(0, string3.length() - 1);
        }
        return string3 + string2;
    }

    private boolean a(ArmorWarning armorWarning, boolean bl, long l) {
        if (bl) {
            if (armorWarning.jR) {
                armorWarning.jT = -1L;
            }
            return true;
        }
        if (armorWarning.jR) {
            if (armorWarning.jT < 0L) {
                armorWarning.jT = l;
            }
            return l - armorWarning.jT < 1000L;
        }
        return l - armorWarning.jS < 3500L;
    }

    static final class ArmorWarning {
        final String jQ;
        final boolean jR;
        final long jS = System.currentTimeMillis();
        long jT = -1L;
        final Animation jU = new Animation(220L, 1.0, true, Easing.aiR);

        ArmorWarning(String string) {
            this(string, false);
        }

        ArmorWarning(String string, boolean bl) {
            this.jQ = string;
            this.jR = bl;
        }
    }
}

