package ru.method.celebression.screen;

import ru.method.celebression.hud.ArmorHud;
import ru.method.celebression.hud.CooldownsHud;
import ru.method.celebression.hud.CoordinatesHud;
import ru.method.celebression.hud.HelperItemsHud;
import ru.method.celebression.hud.InventoryHud;
import ru.method.celebression.hud.KeyBindsHud;
import ru.method.celebression.hud.LowArmorNotificationsHud;
import ru.method.celebression.hud.PotionHud;
import ru.method.celebression.hud.ScoreboardHud;
import ru.method.celebression.hud.TargetHud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1074;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_408;
import net.minecraft.class_437;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.modules.impl.client.ElementsModule;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.hud.HudManager;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.module.Module;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class HudEditorScreen
extends class_437 {
    private static float Ro;
    private static float Rp;
    private static float Rq;
    private static float Ns;
    private final Animation Rr = new Animation(220L, 1.0, true, Easing.aiZ);
    private final List<float[]> Rs = new ArrayList<float[]>();
    private final List<HudElement> Rt = new ArrayList<HudElement>();
    private final Map<String, Animation> Ru = new HashMap<String, Animation>();
    private static final List<String> Rv;
    private boolean closing;
    private boolean Rw;

    public HudEditorScreen() {
        super((class_2561)class_2561.method_43470((String)"HUD Editor"));
    }

    protected void method_25426() {
        this.Rr.reset();
    }

    public static boolean mb() {
        class_437 class_4372 = class_310.method_1551().field_1755;
        return class_4372 instanceof class_408 || class_4372 instanceof HudEditorScreen;
    }

    public static boolean g(double d, double d2) {
        if (!(class_310.method_1551().field_1755 instanceof HudEditorScreen)) {
            return false;
        }
        return d >= (double)Ro && d <= (double)(Ro + Rq) && d2 >= (double)Rp && d2 <= (double)(Rp + Ns);
    }

    public boolean method_25421() {
        return false;
    }

    private float mc() {
        return Math.max(0.0f, Math.min(1.0f, this.Rr.rk()));
    }

    private String a(HudManager hudManager, HudElement hudElement) {
        String string;
        String string2;
        switch (hudElement.getName()) {
            case "ScoreboardHud": {
                return "\u0421\u043a\u043e\u0440\u0431\u043e\u0440\u0434";
            }
            case "Watermark": {
                return "\u0412\u043e\u0442\u0435\u0440\u043c\u0430\u0440\u043a";
            }
        }
        Object object = hudManager.b(hudElement);
        if (object != null && !(string2 = ((Setting)object).getName()).equals(string = class_1074.method_4662((String)string2, (Object[])new Object[0]))) {
            return string;
        }
        return hudElement.getName();
    }

    private String b(HudManager hudManager, HudElement hudElement) {
        switch (hudElement.getName()) {
            case "TargetHud": {
                return "\u0422\u0430\u0440\u0433\u0435\u0442";
            }
            case "PotionHud": {
                return "\u042d\u0444\u0444\u0435\u043a\u0442\u044b";
            }
            case "CoordinatesHud": {
                return "\u041a\u043e\u043e\u0440\u0434\u044b";
            }
            case "CooldownsHud": {
                return "\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430";
            }
            case "KeyBindsHud": {
                return "\u041a\u043b\u0430\u0432\u0438\u0448\u0438";
            }
            case "HelperItemsHud": {
                return "\u0425\u0435\u043b\u043f\u0435\u0440";
            }
            case "ArmorHud": {
                return "\u0411\u0440\u043e\u043d\u044f";
            }
            case "InventoryHud": {
                return "\u0418\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044c";
            }
            case "LowArmorNotificationsHud": {
                return "\u0423\u0432\u0435\u0434. \u0431\u0440\u043e\u043d\u0438";
            }
            case "Watermark": {
                return "\u0412\u043e\u0442\u0435\u0440\u043c\u0430\u0440\u043a";
            }
            case "ScoreboardHud": {
                return "\u0421\u043a\u043e\u0440\u0431\u043e\u0440\u0434";
            }
        }
        return this.a(hudManager, hudElement);
    }

    private static Color mix(Color color, Color color2, float f) {
        float f2 = Math.max(0.0f, Math.min(1.0f, f));
        return new Color(Math.round((float)color.getRed() + (float)(color2.getRed() - color.getRed()) * f2), Math.round((float)color.getGreen() + (float)(color2.getGreen() - color.getGreen()) * f2), Math.round((float)color.getBlue() + (float)(color2.getBlue() - color.getBlue()) * f2));
    }

    private boolean f(HudElement hudElement) {
        String string = hudElement.getName();
        if (HudThemeRenderer.active() && string.equals("DynamicIsland")) {
            return false;
        }
        return !string.equals("ScoreboardHud") || UI.isCustomScoreboardEnabled();
    }

    public void method_25420(class_332 class_3322, int n, int n2, float f) {
        int n3 = Math.round(51.0f * this.mc());
        class_3322.method_25294(0, 0, this.field_22789, this.field_22790, n3 << 24);
    }

    public void md() {
        this.closing = true;
    }

    private void lp() {
        if (this.Rw) {
            return;
        }
        this.Rw = true;
        ElementsModule elementsModule = Celebression.getInstance().getModuleManager().getModule(ElementsModule.class);
        if (elementsModule != null && elementsModule.isToggle()) {
            Celebression.getInstance().setSuppressToggleFeedback(true);
            try {
                ((Module)elementsModule).setToggled(false);
            }
            finally {
                Celebression.getInstance().setSuppressToggleFeedback(false);
            }
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102.field_1755 == this) {
            class_3102.method_1507(null);
        }
    }

    public void method_25394(class_332 class_3322, int n, int n2, float f) {
        float f2;
        this.Rr.i(!this.closing);
        if (this.closing && this.mc() <= 0.02f) {
            this.lp();
            return;
        }
        super.method_25394(class_3322, n, n2, f);
        float f3 = this.mc();
        boolean bl = HudThemeRenderer.active();
        String string2 = "\u041f\u0435\u0440\u0435\u0442\u0430\u0441\u043a\u0438\u0432\u0430\u0439\u0442\u0435 HUD-\u044d\u043b\u0435\u043c\u0435\u043d\u0442\u044b   \u2022   Esc \u2014 \u0432\u044b\u0445\u043e\u0434";
        float f4 = 8.0f;
        float f5 = Fonts.ROUNDBOLD.getWidth(string2, f4) + 22.0f;
        float f6 = 18.0f;
        float f7 = ((float)this.field_22789 - f5) / 2.0f;
        float f8 = 8.0f - (1.0f - f3) * 8.0f;
        if (bl) {
            HudThemeRenderer.b(class_3322.method_51448(), f7, f8, f5, f6, HudThemeRenderer.f(f5, f6), f3);
        }
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(f4), string2, f7 + 11.0f, f8 + (f6 - Fonts.ROUNDBOLD.getHeight(f4)) / 2.0f, bl ? HudThemeRenderer.d(Math.round(255.0f * f3)) : new Color(255, 255, 255, Math.round(255.0f * f3)));
        this.Rs.clear();
        this.Rt.clear();
        HudManager hudManager = Celebression.getInstance().getHudManager();
        if (hudManager == null) {
            return;
        }
        ArrayList<HudElement> arrayList = new ArrayList<HudElement>();
        for (HudElement hudElement3 : hudManager.fq()) {
            if (!this.f(hudElement3)) continue;
            arrayList.add(hudElement3);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        arrayList.sort((hudElement, hudElement2) -> Integer.compare(HudEditorScreen.g(hudElement), HudEditorScreen.g(hudElement2)));
        int n3 = arrayList.size();
        float f9 = 17.0f;
        float f10 = 4.5f;
        float f11 = 4.5f;
        float f12 = 8.0f;
        float f13 = 6.8f;
        float[] fArray = new float[n3];
        float f14 = 0.0f;
        for (int i = 0; i < n3; ++i) {
            fArray[i] = Fonts.MEDIUM.getWidth(this.b(hudManager, (HudElement)arrayList.get(i)), f13);
            f14 += f10 + f11 + fArray[i] + f12 * 2.0f;
        }
        float f15 = (float)this.field_22789 - 60.0f;
        if (f14 > f15) {
            f2 = f15 / f14;
            f13 *= f2;
            f12 *= f2;
            f11 *= f2;
            f10 *= f2;
            f14 = 0.0f;
            for (int i = 0; i < n3; ++i) {
                fArray[i] = Fonts.MEDIUM.getWidth(this.b(hudManager, (HudElement)arrayList.get(i)), f13);
                f14 += f10 + f11 + fArray[i] + f12 * 2.0f;
            }
        }
        f2 = 50.0f;
        float f16 = ((float)this.field_22789 - f14) / 2.0f;
        float f17 = (float)this.field_22790 - f2 - f9 + (1.0f - f3) * 8.0f;
        float f18 = HudThemeRenderer.f(f14, f9);
        String string3 = "\u042d\u043b\u0435\u043c\u0435\u043d\u0442\u044b";
        float f19 = 7.0f;
        float f20 = Fonts.ROUNDBOLD.getWidth(string3, f19);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(f19), string3, ((float)this.field_22789 - f20) / 2.0f, f17 - f19 - 6.0f, bl ? HudThemeRenderer.d(Math.round(190.0f * f3)) : new Color(210, 214, 222, Math.round(190.0f * f3)));
        for (int i = 3; i >= 1; --i) {
            float f21 = (float)i * 2.0f;
            Render2D.drawRoundedRect(class_3322.method_51448(), f16 - f21, f17 - f21 + 1.5f, f14 + f21 * 2.0f, f9 + f21 * 2.0f, f18 + f21, new Color(0, 0, 0, Math.round(18.0f * f3 / (float)i)));
        }
        Color color = ColorUtils.getPrimaryColor();
        if (bl) {
            HudThemeRenderer.b(class_3322.method_51448(), f16, f17, f14, f9, f18, f3);
        } else {
            Render2D.drawRoundedRect(class_3322.method_51448(), f16, f17, f14, f9, f18, ColorUtils.getGlassColor(Math.round(175.0f * f3)));
        }
        Render2D.drawBorder(class_3322.method_51448(), f16, f17, f14, f9, f18, 1.0f, new ColorRGBA(new Color(255, 255, 255, Math.round(45.0f * f3))));
        Color color2 = new Color(140, 146, 158);
        Color color3 = new Color(76, 217, 124);
        float f22 = f16;
        for (int i = 0; i < n3; ++i) {
            float f23;
            float f24;
            HudElement hudElement4 = (HudElement)arrayList.get(i);
            boolean bl2 = hudManager.d(hudElement4);
            float f25 = f10 + f11 + fArray[i] + f12 * 2.0f;
            boolean bl3 = MathUtils.c(f22, f17, f25, f9, n, n2);
            Animation animation = this.Ru.computeIfAbsent(hudElement4.getName(), string -> new Animation(320L, 1.0, bl2, Easing.aiZ));
            animation.i(bl2);
            float f26 = Math.max(0.0f, Math.min(1.0f, animation.rk()));
            if (i > 0) {
                Render2D.drawRoundedRect(class_3322.method_51448(), f22, f17 + f9 * 0.26f, 1.0f, f9 * 0.48f, 0.5f, new Color(255, 255, 255, Math.round(24.0f * f3)));
            }
            if ((f24 = Math.max(f26, bl3 ? 0.55f : 0.0f)) > 0.01f) {
                f23 = f9 - 5.0f;
                Render2D.drawRoundedRect(class_3322.method_51448(), f22 + 2.5f, f17 + 2.5f, f25 - 5.0f, f23, f23 / 2.0f, ColorUtils.alpha(color, Math.round(42.0f * f24 * f3)));
            }
            f23 = f10 + f11 + fArray[i];
            float f27 = f22 + (f25 - f23) / 2.0f;
            float f28 = f27 + f10 / 2.0f;
            float f29 = f17 + f9 / 2.0f;
            if (f26 > 0.01f) {
                for (int j = 3; j >= 1; --j) {
                    float f30 = f10 + (float)j * 2.6f;
                    Render2D.drawRoundedRect(class_3322.method_51448(), f28 - f30 / 2.0f, f29 - f30 / 2.0f, f30, f30, f30 / 2.0f, new Color(color3.getRed(), color3.getGreen(), color3.getBlue(), Math.round(34.0f * f26 / (float)j * f3)));
                }
            }
            Color color4 = HudEditorScreen.mix(color2, color3, f26);
            Render2D.drawRoundedRect(class_3322.method_51448(), f28 - f10 / 2.0f, f29 - f10 / 2.0f, f10, f10, f10 / 2.0f, new Color(color4.getRed(), color4.getGreen(), color4.getBlue(), Math.round(238.0f * f3)));
            int n4 = Math.round((160.0f + 88.0f * f26) * f3);
            Color color5 = bl ? HudThemeRenderer.d(n4) : new Color(255, 255, 255, n4);
            Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(f13), this.b(hudManager, hudElement4), f27 + f10 + f11, f17 + (f9 - Fonts.MEDIUM.getHeight(f13)) / 2.0f, color5);
            this.Rs.add(new float[]{f22, f17, f25, f9});
            this.Rt.add(hudElement4);
            f22 += f25;
        }
        Ro = f16 - 4.0f;
        Rp = f17 - 4.0f;
        Rq = f14 + 8.0f;
        Ns = f9 + 8.0f;
    }

    private static int g(HudElement hudElement) {
        int n = Rv.indexOf(hudElement.getName());
        return n < 0 ? Rv.size() : n;
    }

    public boolean method_25402(double d, double d2, int n) {
        if (n == 0) {
            for (int i = 0; i < this.Rs.size(); ++i) {
                float[] fArray = this.Rs.get(i);
                if (!MathUtils.c(fArray[0], fArray[1], fArray[2], fArray[3], (float)d, (float)d2)) continue;
                HudManager hudManager = Celebression.getInstance().getHudManager();
                HudElement hudElement = this.Rt.get(i);
                if (hudManager != null) {
                    boolean bl;
                    BooleanSetting booleanSetting = hudManager.b(hudElement);
                    boolean bl2 = bl = !hudManager.d(hudElement);
                    if (booleanSetting != null) {
                        booleanSetting.a(bl);
                        hudManager.a(booleanSetting, bl, (float)d, (float)d2);
                    } else {
                        hudElement.setToggled(bl);
                    }
                }
                return true;
            }
        }
        return super.method_25402(d, d2, n);
    }

    public void method_25419() {
        this.md();
    }

    static {
        Rv = List.of("TargetHud", "PotionHud", "CoordinatesHud", "CooldownsHud", "KeyBindsHud", "HelperItemsHud", "ArmorHud", "InventoryHud", "ScoreboardHud", "LowArmorNotificationsHud", "Watermark", "DynamicIsland");
    }
}

