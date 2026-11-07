package ru.method.celebression.hud;

import ru.method.celebression.animation.Animation;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1074;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.ui.CooldownEntry;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.animation.EasingMath;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.animation.TimedProgress;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;
import unkw.visualtest.client.hud.VisualHud;

public class CooldownsHud
extends HudElement {
    private final AnimatedComponent hj = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent hk = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent hl = new AnimatedComponent(Easing.aiZ);
    private boolean hm = true;
    private int hn;
    private final Map<String, TimedProgress> ho = new LinkedHashMap<String, TimedProgress>();
    private static final float hp = 12.0f;
    private static final float hq = 2.0f;
    private static final float hr = 3.0f;
    private static final float hs = 0.78489f;
    private List<CooldownEntry.CooldownCategory> ht = Collections.emptyList();
    private long cacheExpiresAtMs;

    public CooldownsHud() {
        super("CooldownsHud");
    }

    private boolean a(List<CooldownEntry.CooldownCategory> list) {
        if (HudEditorScreen.mb()) {
            return true;
        }
        return CooldownsHud.aiG.field_1724 != null && list != null && !list.isEmpty();
    }

    private void c(HudRenderEvent hudRenderEvent, class_1799 class_17992, float f, float f2) {
        if (class_17992 == null || class_17992.method_7960()) {
            return;
        }
        try {
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            RenderSystem.enableDepthTest();
            float f3 = 0.75f;
            hudRenderEvent.j().method_51448().method_22903();
            hudRenderEvent.j().method_51448().method_46416(f, f2, 0.0f);
            hudRenderEvent.j().method_51448().method_22905(f3, f3, 1.0f);
            hudRenderEvent.j().method_51423((class_1309)CooldownsHud.aiG.field_1724, class_17992, 0, 0, 0);
            hudRenderEvent.j().method_51448().method_22909();
            RenderSystem.disableDepthTest();
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        catch (Exception exception) {
        }
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        float f;
        float f2;
        float f3;
        Object object;
        List<CooldownEntry.CooldownCategory> list;
        if (CooldownsHud.fullNullCheck() || this.bL()) {
            return;
        }
        if (HudThemeRenderer.active() && HudThemeRenderer.cc() == HudThemeRenderer.ThemeMetrics.gz) {
            int n;
            float f4 = this.getX();
            float f5 = this.getY();
            class_4587 class_45872 = hudRenderEvent.j().method_51448();
            float f6 = this.b(class_45872, f4, f5);
            float f7 = this.hl.a(this.hn > 0 ? 1.0f : 0.0f, 200L);
            float f8 = Math.max(1.0E-4f, this.gn.rk() * this.bJ() * 0.78489f * f7);
            float f9 = f4 + 58.86675f;
            float f10 = f5 + (float)Math.max(1, this.hn) * 0.78489f / 2.0f;
            class_45872.method_22903();
            class_45872.method_46416(f9, f10, 0.0f);
            class_45872.method_22905(f8, f8, 1.0f);
            class_45872.method_46416(-f9, -f10, 0.0f);
            this.hn = n = VisualHud.cardCooldowns(hudRenderEvent.j(), aiG, Math.round(f4), Math.round(f5));
            class_45872.method_22909();
            this.b(class_45872);
            if (n > 0) {
                this.b(f4, f5, 117.7335f, (float)n * 0.78489f, f6);
            }
            super.onRender2D(hudRenderEvent);
            return;
        }
        float f11 = aiG.method_61966().method_60637(false);
        boolean bl = HudEditorScreen.mb();
        long l = System.currentTimeMillis();
        if (l >= this.cacheExpiresAtMs) {
            this.ht = CooldownEntry.a(aiG, f11);
            this.cacheExpiresAtMs = l + 50L;
        }
        List<CooldownEntry.CooldownCategory> list2 = !(list = this.ht).isEmpty() ? list : (bl ? CooldownEntry.rG() : Collections.emptyList());
        boolean bl2 = this.a(list);
        float f12 = this.hl.a(bl2 ? 1.0f : 0.0f, 200L);
        if (f12 <= 0.0f) {
            this.hm = true;
            return;
        }
        float f13 = this.getX();
        float f14 = this.getY();
        float f15 = 6.0f;
        float f16 = 7.0f;
        float f17 = 7.5f;
        float f18 = Math.max(13.0f, 12.0f);
        float f19 = 90.0f;
        for (CooldownEntry.CooldownCategory object22 : list2) {
            Object f20 = object22.rJ();
            if (((String)f20).length() > 26) {
                f20 = ((String)f20).substring(0, 23) + "...";
            }
            object = object22.rH();
            f3 = Fonts.MEDIUM.getWidth((String)f20, f16);
            f = f15 * 4.0f + 3.0f + 12.0f + 2.0f + f3 + (f2 = Fonts.MEDIUM.getWidth((String)object, f16));
            if (!(f > f19)) continue;
            f19 = f;
        }
        String string3 = class_1074.method_4662((String)"elements.settings.elements.cooldownshud", (Object[])new Object[0]);
        String string4 = "Q";
        float f4 = 8.0f;
        object = Fonts.ICONSNEW;
        f3 = ((Font)object).getWidth(string4, f4);
        f2 = 4.0f;
        f = Fonts.SEMIBOLD.getWidth(string3, f17);
        f19 = Math.max(f19, f + f2 + f3 + f15 * 2.0f);
        float f5 = f19 + f15 * 2.0f;
        float f6 = 16.0f + (float)list2.size() * f18 + (f15 - 1.0f);
        long l2 = this.hm ? 1L : 200L;
        float f7 = this.hj.a(f5, l2);
        float f8 = this.hk.a(f6, l2);
        this.hm = false;
        if (bl) {
            f7 = f5;
            f8 = f6;
        }
        int n = Math.max(0, Math.min(255, Math.round(255.0f * f12)));
        float f9 = this.b(hudRenderEvent.j().method_51448(), f13, f14);
        hudRenderEvent.j().method_51448().method_22903();
        float[] fArray = this.bK();
        float f10 = this.gn.rk() * f12 * this.bJ() * 0.78489f;
        float f20 = f13 + f7 / 2.0f + fArray[0];
        float f21 = f14 + f8 / 2.0f + fArray[1];
        hudRenderEvent.j().method_51448().method_46416(f20, f21, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f10, f10, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-f20, -f21, 0.0f);
        boolean bl3 = HudThemeRenderer.active();
        if (bl3) {
            HudThemeRenderer.b(hudRenderEvent.j().method_51448(), f13, f14, f7 - 2.0f, f8 - 2.0f, HudThemeRenderer.f(f7 - 2.0f, f8 - 2.0f), f12);
        } else {
            Color f29 = ColorUtils.getGlassColor(255);
            Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f13, f14, f7 - 2.0f, f8 - 2.0f, new BorderRadius(10.0f), new ColorRGBA(f29), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        }
        float f22 = f13 + f15;
        float f23 = Fonts.ROUNDBOLD.getHeight(f17);
        float f24 = ((Font)object).getHeight(f4);
        float f25 = f14 + (18.0f - f24) / 2.0f;
        Color color = bl3 ? HudThemeRenderer.d(n) : ColorUtils.getTextColor(n);
        Color color2 = bl3 ? HudThemeRenderer.d(n) : ColorUtils.getTextColor(n);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.SEMIBOLD.getFont(f17), string3, f22, f14 + f15 - 1.0f, color2);
        float f26 = f13 + f7 - f15 - f3;
        this.a(hudRenderEvent.j(), Fonts.ICONSNEW, f4, string4, f26, f25, color);
        float f27 = 18.0f;
        HashSet<String> hashSet = new HashSet<String>();
        for (CooldownEntry.CooldownCategory cooldownCategory : list2) {
            float f28 = f14 + f27;
            float f29 = f28 + (f18 - f16) / 2.0f - 2.0f;
            Object object2 = cooldownCategory.rJ();
            if (((String)object2).length() > 26) {
                object2 = ((String)object2).substring(0, 23) + "...";
            }
            CooldownItem cooldownItem = new CooldownItem(cooldownCategory.stack(), cooldownCategory.rH());
            float f30 = f13 + f15 + 3.0f;
            float f31 = f28 + (f18 - 12.0f) / 2.0f - 1.0f;
            this.c(hudRenderEvent, cooldownItem.stack(), f30, f31);
            float f32 = f13 + f15 + 3.0f + 12.0f + 2.0f;
            String string5 = cooldownItem.cl();
            float f33 = f13 + f7 - f15 - Fonts.MEDIUM.getWidth(string5, f16) - 2.0f;
            object2 = CooldownsHud.a(Fonts.MEDIUM, (String)object2, f16, f33 - f32 - 4.0f);
            this.a(hudRenderEvent.j(), Fonts.MEDIUM, 7.0f, (String)object2, f32, f29, ColorUtils.getTextColor(n));
            Color color3 = cooldownCategory.rK() > 400.0f ? new Color(130, 200, 255) : (cooldownCategory.progress() > 0.75f ? Color.RED : (cooldownCategory.progress() > 0.5f ? Color.ORANGE : (cooldownCategory.progress() > 0.25f ? Color.YELLOW : Color.GREEN)));
            String string6 = this.a(cooldownCategory);
            TimedProgress timedProgress = this.ho.computeIfAbsent(string6, string2 -> new TimedProgress(220L, Easing.aiZ, string4));
            timedProgress.cF(string5);
            hashSet.add(string6);
            this.a(hudRenderEvent.j(), timedProgress, f33, f29, ColorUtils.safeColor(color3.getRed(), color3.getGreen(), color3.getBlue(), n), f18 * 0.6f, Fonts.MEDIUM, f16);
            f27 += f18;
        }
        this.ho.keySet().removeIf(string -> !hashSet.contains(string));
        hudRenderEvent.j().method_51448().method_22909();
        this.b(hudRenderEvent.j().method_51448());
        this.b(f13, f14, f7, f8, f9);
        super.onRender2D(hudRenderEvent);
    }

    private String a(CooldownEntry.CooldownCategory cooldownCategory) {
        return cooldownCategory.rJ();
    }

    private void a(class_332 class_3322, TimedProgress timedProgress, float f, float f2, Color color, float f3, Font font, float f4) {
        float f5;
        boolean bl = timedProgress.rg();
        float f6 = timedProgress.progress();
        if (!bl) {
            this.a(class_3322, Fonts.MEDIUM, 7.0f, timedProgress.rh(), f, f2, color);
            return;
        }
        float f7 = Math.max(0.0f, Math.min(1.0f, f6));
        int n = color.getAlpha();
        int n2 = Math.round((float)n * (1.0f - f7));
        int n3 = Math.round((float)n * f7);
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n2);
        Color color3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n3);
        float f8 = f2 - f3 * f7;
        float f9 = f2 + f3 * (1.0f - f7);
        String string = timedProgress.ri();
        String string2 = timedProgress.rh();
        int n4 = EasingMath.w(string, string2);
        int n5 = EasingMath.d(string, string2, n4);
        String string3 = EasingMath.a(string2, 0, n4);
        String string4 = EasingMath.a(string, n4, string.length() - n5);
        String string5 = EasingMath.a(string2, n4, string2.length() - n5);
        String string6 = EasingMath.a(string2, string2.length() - n5, string2.length());
        float f10 = font.getWidth(string3, f4);
        float f11 = font.getWidth(string5, f4);
        float f12 = font.getWidth(string4, f4);
        float f13 = f5 = f + f10;
        float f14 = f5 + (f11 - f12) / 2.0f;
        this.a(class_3322, Fonts.MEDIUM, 7.0f, string3, f, f2, color);
        this.a(class_3322, Fonts.MEDIUM, 7.0f, string6, f5 + f11, f2, color);
        this.a(class_3322, Fonts.MEDIUM, 7.0f, string4, f14, f8, color2);
        this.a(class_3322, Fonts.MEDIUM, 7.0f, string5, f13, f9, color3);
    }

    static final class CooldownItem {
        private final class_1799 stack;
        private final String hu;

        CooldownItem(class_1799 class_17992, String string) {
            this.stack = class_17992;
            this.hu = string;
        }

        @Override
        public final String toString() {
            return "CooldownItem[" + "stack=" + this.stack + ", " + "keyLabel=" + this.hu + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.stack, this.hu);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof CooldownItem other)) return false;
            return java.util.Objects.deepEquals(this.stack, other.stack)
                && java.util.Objects.deepEquals(this.hu, other.hu);
        }

        public class_1799 stack() {
            return this.stack;
        }

        public String cl() {
            return this.hu;
        }
    }
}

