package ru.method.celebression.hud;

import ru.method.celebression.animation.Animation;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.class_1058;
import net.minecraft.class_1074;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1921;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.animation.EasingMath;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.util.TextFormattingStripper;
import ru.method.celebression.animation.TimedProgress;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;
import unkw.visualtest.client.hud.VisualHud;

public class PotionHud
extends HudElement {
    private static final float jV = 0.82365f;
    private final AnimatedComponent jW = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent jX = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent jY = new AnimatedComponent(Easing.aiZ);
    private boolean hm = true;
    private int hn;
    private final Map<String, TimedProgress> jZ = new LinkedHashMap<String, TimedProgress>();
    private final Map<String, String> ka = new HashMap<String, String>();
    private static final float kb = 12.0f;
    private static final float kc = 2.0f;
    private static final String[] kd = new String[]{"", "I", "II", "III", "IV", "V"};

    public PotionHud() {
        super("PotionHud");
    }

    private static String a(class_1293 class_12932) {
        if (class_12932.method_48559()) {
            return "\u221e";
        }
        int n = class_12932.method_5584();
        int n2 = n / 1200;
        int n3 = n % 1200 / 20;
        return n3 < 10 ? n2 + ":0" + n3 : n2 + ":" + n3;
    }

    private static String j(int n) {
        if (n <= 0) {
            return "";
        }
        return n < kd.length ? kd[n] : String.valueOf(n);
    }

    private Color c(Color color) {
        return new Color(Math.round((float)color.getRed() * 0.7f), Math.round((float)color.getGreen() * 0.7f), Math.round((float)color.getBlue() * 0.7f), color.getAlpha());
    }

    private boolean cm() {
        if (HudEditorScreen.mb()) {
            return true;
        }
        return PotionHud.aiG.field_1724 != null && !PotionHud.aiG.field_1724.method_6026().isEmpty();
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        float f;
        float f2;
        float f3;
        float f4;
        Object object;
        Object object22;
        Collection<class_1293> collection;
        if (PotionHud.fullNullCheck() || this.bL()) {
            return;
        }
        if (HudThemeRenderer.active() && HudThemeRenderer.cc() == HudThemeRenderer.ThemeMetrics.gz) {
            int n;
            float f5 = this.getX();
            float f6 = this.getY();
            class_4587 class_45872 = hudRenderEvent.j().method_51448();
            float f7 = this.b(class_45872, f5, f6);
            float f8 = this.jY.a(this.hn > 0 ? 1.0f : 0.0f, 200L);
            float f9 = Math.max(1.0E-4f, this.gn.rk() * this.bJ() * 0.82365f * f8);
            float f10 = f5 + 61.77375f;
            float f11 = f6 + (float)Math.max(1, this.hn) * 0.82365f / 2.0f;
            class_45872.method_22903();
            class_45872.method_46416(f10, f11, 0.0f);
            class_45872.method_22905(f9, f9, 1.0f);
            class_45872.method_46416(-f10, -f11, 0.0f);
            this.hn = n = VisualHud.cardEffects(hudRenderEvent.j(), aiG, Math.round(f5), Math.round(f6));
            class_45872.method_22909();
            this.b(class_45872);
            if (n > 0) {
                this.b(f5, f6, 123.5475f, (float)n * 0.82365f, f7);
            }
            super.onRender2D(hudRenderEvent);
            return;
        }
        boolean bl = this.cm();
        float f12 = this.jY.a(bl ? 1.0f : 0.0f, 200L);
        if (f12 <= 0.0f) {
            this.hm = true;
            return;
        }
        boolean bl2 = HudEditorScreen.mb();
        if (bl2) {
            if (PotionHud.aiG.field_1724 != null && !PotionHud.aiG.field_1724.method_6026().isEmpty()) {
                collection = PotionHud.aiG.field_1724.method_6026();
            } else {
                collection = new ArrayList<class_1293>();
                collection.add(new class_1293(class_1294.field_5904, 1200, 1));
                collection.add(new class_1293(class_1294.field_5910, 600, 0));
                collection.add(new class_1293(class_1294.field_5918, 3600, 0));
            }
        } else {
            collection = PotionHud.aiG.field_1724.method_6026();
        }
        float f13 = this.getX();
        float f14 = this.getY();
        float f15 = 6.0f;
        float f16 = 7.0f;
        float f17 = 13.0f;
        float f18 = 7.5f;
        float f19 = 80.0f;
        final class PotionEntry {
            private final class_1293 ke;
            private final String kf;
            private final String kg;
            private final String kh;
            private final float ki;
            private final float kj;
            private final float kk;

            PotionEntry(class_1293 class_12932, String string, String string2, String string3, float f, float f2, float f3) {
                this.ke = class_12932;
                this.kf = string;
                this.kg = string2;
                this.kh = string3;
                this.ki = f;
                this.kj = f2;
                this.kk = f3;
            }

            @Override
            public final String toString() {
                return "PotionEntry[" + "effect=" + this.ke + ", " + "name=" + this.kf + ", " + "level=" + this.kg + ", " + "duration=" + this.kh + ", " + "nameWidth=" + this.ki + ", " + "levelWidth=" + this.kj + ", " + "timeWidth=" + this.kk + "]";
            }

            @Override
            public final int hashCode() {
                return java.util.Objects.hash(this.ke, this.kf, this.kg, this.kh, this.ki, this.kj, this.kk);
            }

            @Override
            public final boolean equals(Object object) {
                if (this == object) return true;
                if (!(object instanceof PotionEntry other)) return false;
                return java.util.Objects.deepEquals(this.ke, other.ke)
                    && java.util.Objects.deepEquals(this.kf, other.kf)
                    && java.util.Objects.deepEquals(this.kg, other.kg)
                    && java.util.Objects.deepEquals(this.kh, other.kh)
                    && java.util.Objects.deepEquals(this.ki, other.ki)
                    && java.util.Objects.deepEquals(this.kj, other.kj)
                    && java.util.Objects.deepEquals(this.kk, other.kk);
            }

            public class_1293 cJ() {
                return this.ke;
            }

            public String name() {
                return this.kf;
            }

            public String cK() {
                return this.kg;
            }

            public String duration() {
                return this.kh;
            }

            public float cL() {
                return this.ki;
            }

            public float cM() {
                return this.kj;
            }

            public float cN() {
                return this.kk;
            }
        }
        ArrayList<PotionEntry> arrayList = new ArrayList<PotionEntry>(collection.size());
        for (class_1293 effect : collection) {
            class_1291 class_12912 = (class_1291)effect.method_5579().comp_349();
            String effectName = this.ka.computeIfAbsent(class_12912.method_5560().getString(), TextFormattingStripper::da);
            String string3 = PotionHud.j(effect.method_5578() + 1);
            String string4 = PotionHud.a(effect);
            f4 = Fonts.MEDIUM.getWidth(effectName, f16);
            f3 = string3.isEmpty() ? 0.0f : Fonts.MEDIUM.getWidth(string3, f16);
            f2 = Fonts.MEDIUM.getWidth(string4, f16);
            arrayList.add(new PotionEntry(effect, effectName, string3, string4, f4, f3, f2));
            float f20 = 14.0f + f4 + f3 + f2 + f15 * 4.0f;
            if (!(f20 > f19)) continue;
            f19 = f20;
        }
        String string5 = class_1074.method_4662((String)"elements.settings.elements.potionhud", (Object[])new Object[0]);
        object22 = "M";
        float f21 = 8.0f;
        object = Fonts.ICONSNEW;
        float f22 = ((Font)object).getWidth((String)object22, f21);
        float f23 = 4.0f;
        f4 = Fonts.SEMIBOLD.getWidth(string5, f18);
        f19 = Math.max(f19, f4 + f23 + f22 + f15 * 2.0f);
        f3 = f19 + f15 * 2.0f;
        f2 = 16.0f + (float)collection.size() * f17 + (f15 - 1.0f);
        long l = this.hm ? 1L : 200L;
        float f24 = this.jW.a(f3, l);
        float f25 = this.jX.a(f2, l);
        this.hm = false;
        if (bl2) {
            f24 = f3;
            f25 = f2;
        }
        int n = Math.max(0, Math.min(255, Math.round(255.0f * f12)));
        float f26 = this.b(hudRenderEvent.j().method_51448(), f13, f14);
        hudRenderEvent.j().method_51448().method_22903();
        float[] fArray = this.bK();
        float f27 = this.gn.rk() * f12 * this.bJ() * 0.82365f;
        float f28 = f13 + f24 / 2.0f + fArray[0];
        float f29 = f14 + f25 / 2.0f + fArray[1];
        hudRenderEvent.j().method_51448().method_46416(f28, f29, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f27, f27, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-f28, -f29, 0.0f);
        boolean bl3 = HudThemeRenderer.active();
        float f30 = HudThemeRenderer.f(f24 - 2.0f, f25 - 2.0f);
        Color color = ColorUtils.getPrimaryColor();
        if (bl3) {
            HudThemeRenderer.b(hudRenderEvent.j().method_51448(), f13, f14, f24 - 2.0f, f25 - 2.0f, f30, f12);
        } else {
            Color color2 = ColorUtils.getGlassColor(255);
            Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f13, f14, f24 - 2.0f, f25 - 2.0f, new BorderRadius(10.0f), new ColorRGBA(color2), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        }
        float f31 = f = f13 + f15;
        float f32 = Fonts.ROUNDBOLD.getHeight(f18);
        float f33 = ((Font)object).getHeight(f21);
        float f34 = f14 + (18.0f - f33) / 2.0f;
        Color color3 = bl3 ? HudThemeRenderer.d(n) : ColorUtils.getTextColor(n);
        Color color4 = bl3 ? HudThemeRenderer.d(n) : ColorUtils.getTextColor(n);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.SEMIBOLD.getFont(f18), string5, f31, f14 + f15 - 1.0f, color4);
        float f35 = f13 + f24 - f15 - f22;
        this.a(hudRenderEvent.j(), Fonts.ICONSNEW, f21, (String)object22, f35, f34, color3);
        float f36 = f14 + 18.0f - 2.0f;
        float f37 = f13 + f24 - f15 - f;
        if (f37 > 0.0f) {
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f, f36, f37, 0.8f, 0.4f, ColorUtils.alpha(color, Math.round(110.0f * f12)));
        }
        float f38 = 18.0f;
        Color color5 = ColorUtils.getTextColor(255);
        HashSet<String> hashSet = new HashSet<String>();
        for (PotionEntry potionEntry : arrayList) {
            class_1293 class_12932 = potionEntry.cJ();
            float f39 = f14 + f38;
            float f40 = f14 + f38 + (f17 - f16) / 2.0f - 2.0f;
            float f41 = f;
            float f42 = f39 + (f17 - 12.0f) / 2.0f - 1.0f;
            this.a(hudRenderEvent.j(), class_12932, f41, f42, 12.0f, n);
            float f43 = f + 12.0f + 2.0f;
            String string6 = potionEntry.cK();
            String string7 = potionEntry.duration();
            float f44 = f13 + f24 - f15 - potionEntry.cN() - 2.0f;
            float f45 = string6.isEmpty() ? 0.0f : potionEntry.cM() + 2.0f;
            String string8 = PotionHud.a(Fonts.MEDIUM, potionEntry.name(), f16, f44 - f43 - 4.0f - f45);
            this.a(hudRenderEvent.j(), Fonts.MEDIUM, 7.0f, string8, f43, f40, bl3 ? HudThemeRenderer.d(n) : ColorUtils.getTextColor(n));
            if (!string6.isEmpty()) {
                float f46 = f43 + Fonts.MEDIUM.getWidth(string8, f16) + 2.0f;
                this.a(hudRenderEvent.j(), Fonts.MEDIUM, 7.0f, string6, f46, f40, ColorUtils.safeColor(ColorUtils.getGlobalColor().getRed(), ColorUtils.getGlobalColor().getGreen(), ColorUtils.getGlobalColor().getBlue(), n));
            }
            boolean bl4 = HudThemeRenderer.isLightTheme();
            Color color6 = class_12932.method_48559() ? (bl4 ? this.c(Color.CYAN) : Color.CYAN) : (class_12932.method_5584() < 200 ? (bl4 ? this.c(Color.RED) : Color.RED) : (class_12932.method_5584() < 600 ? (bl4 ? this.c(Color.ORANGE) : Color.ORANGE) : (bl4 ? HudThemeRenderer.e(n) : Color.LIGHT_GRAY)));
            String string9 = this.b(class_12932);
            TimedProgress timedProgress = this.jZ.computeIfAbsent(string9, string2 -> new TimedProgress(220L, Easing.aiZ, string7));
            timedProgress.cF(string7);
            hashSet.add(string9);
            this.a(hudRenderEvent.j(), timedProgress, f44, f40, ColorUtils.safeColor(color6.getRed(), color6.getGreen(), color6.getBlue(), n), f17 * 0.6f, Fonts.MEDIUM, f16);
            f38 += f17;
        }
        this.jZ.keySet().removeIf(string -> !hashSet.contains(string));
        hudRenderEvent.j().method_51448().method_22909();
        this.b(hudRenderEvent.j().method_51448());
        this.b(f13, f14, f24, f25, f26);
        super.onRender2D(hudRenderEvent);
    }

    private String b(class_1293 class_12932) {
        class_1291 class_12912 = (class_1291)class_12932.method_5579().comp_349();
        String string = class_12912 != null ? class_12912.method_5567() : "unknown";
        return string + ":" + class_12932.method_5578();
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

    private void a(class_332 class_3322, class_1293 class_12932, float f, float f2, float f3, int n) {
        if (class_12932 == null || aiG == null) {
            return;
        }
        try {
            class_1058 class_10582 = aiG.method_18505().method_18663(class_12932.method_5579());
            if (class_10582 == null) {
                return;
            }
            int n2 = Math.max(0, Math.min(255, n));
            int n3 = n2 << 24 | 0xFFFFFF;
            int n4 = Math.max(1, Math.round(f3));
            RenderSystem.disableDepthTest();
            class_3322.method_52710(class_1921::method_62277, class_10582, Math.round(f), Math.round(f2), n4, n4, n3);
            RenderSystem.disableDepthTest();
        }
        catch (Exception exception) {
        }
    }
}
