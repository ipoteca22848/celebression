package ru.method.celebression.screen;

import java.awt.Color;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_156;
import net.minecraft.class_2960;
import net.minecraft.class_3262;
import net.minecraft.class_3283;
import net.minecraft.class_3288;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_7367;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.screen.SettingsSubScreen;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class ResourcePackSettingsScreen
extends SettingsSubScreen {
    private static final float afS = 1060.0f;
    private static final float afT = 130.0f;
    private static final float afU = 22.0f;
    private static final float afV = 64.0f;
    private static final float afW = 64.0f;
    private static final float afX = 56.0f;
    private static final float afY = 8.0f;
    private static final float afZ = 14.0f;
    private static final float aga = 20.0f;
    private static final float agb = 28.0f;
    private static final float agc = 26.0f;
    private static final float agd = 6.0f;
    private static final String agf = "\u041d\u0430\u0431\u043e\u0440\u044b \u0440\u0435\u0441\u0443\u0440\u0441\u043e\u0432";
    private final Animation agg = MenuChrome.anim();
    private final Animation agh = MenuChrome.anim();
    private final Animation agi = MenuChrome.anim();
    private final float[] agj = new float[4];
    private final float[] agk = new float[4];
    private final float[] agl = new float[4];
    private final List<class_3288> agm = new ArrayList<class_3288>();
    private final List<class_3288> agn = new ArrayList<class_3288>();
    private final List<class_3288> ago = new ArrayList<class_3288>();
    private final List<float[]> agp = new ArrayList<float[]>();
    private final List<float[]> agq = new ArrayList<float[]>();
    private final List<float[]> agr = new ArrayList<float[]>();
    private final List<float[]> ags = new ArrayList<float[]>();
    private float agt;
    private float agu;
    private float agv;
    private float agw;
    private float agx;
    private float agy;
    private long Ln;
    private static Method agz;
    private static boolean agA;
    private static final class_2960 agB;
    private final Map<String, class_2960> agC = new HashMap<String, class_2960>();

    public ResourcePackSettingsScreen(class_437 class_4372) {
        super(class_4372, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u2014 \u041d\u0430\u0431\u043e\u0440\u044b \u0440\u0435\u0441\u0443\u0440\u0441\u043e\u0432");
        this.qL();
    }

    private void qL() {
        this.agm.clear();
        this.agn.clear();
        this.ago.clear();
        class_3283 class_32832 = aiG.method_1520();
        class_32832.method_14445();
        for (class_3288 class_32882 : class_32832.method_14444()) {
            if (ResourcePackSettingsScreen.a(class_32882)) {
                this.ago.add(class_32882);
                continue;
            }
            this.agn.add(class_32882);
        }
        Collections.reverse(this.agn);
        for (class_3288 class_32882 : class_32832.method_14441()) {
            if (ResourcePackSettingsScreen.a(class_32882) || this.agn.contains(class_32882)) continue;
            this.agm.add(class_32882);
        }
    }

    private static boolean a(class_3288 class_32882) {
        if ("fabric".equals(class_32882.method_14463())) {
            return true;
        }
        if (!agA) {
            agA = true;
            try {
                agz = class_32882.getClass().getMethod("fabric_isHidden", new Class[0]);
            }
            catch (Throwable throwable) {
            }
        }
        if (agz == null) {
            return false;
        }
        try {
            return (Boolean)agz.invoke((Object)class_32882, new Object[0]);
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    private class_2960 b(class_3288 class_32882) {
        return this.agC.computeIfAbsent(class_32882.method_14463(), string -> {
            try (class_3262 class_32622 = class_32882.method_14458();){
                class_7367 class_73672 = class_32622.method_14410(new String[]{"pack.png"});
                if (class_73672 == null) {
                    return agB;
                }
                try (InputStream inputStream = (InputStream)class_73672.get()) {
                    class_1011 class_10112 = class_1011.method_4309(inputStream);
                    class_2960 textureId = Celebression.id("packicon/" + string.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_.-]", "_"));
                    aiG.method_1531().method_4616(textureId, (class_1044)new class_1043(class_10112));
                    return textureId;
                }
            }
            catch (Throwable throwable) {
                return agB;
            }
        });
    }

    @Override
    public void method_25432() {
        for (class_2960 class_29602 : this.agC.values()) {
            if (class_29602 == agB) continue;
            try {
                aiG.method_1531().method_4615(class_29602);
            }
            catch (Throwable throwable) {}
        }
        this.agC.clear();
        super.method_25432();
    }

    private float kw() {
        int n = Math.max(this.agm.size(), this.agn.size());
        float f = 132.0f + (float)n * 56.0f + (float)Math.max(0, n - 1) * 8.0f + 64.0f;
        float f2 = (float)this.vh - 130.0f - 74.0f;
        return Math.min(f, Math.max(212.0f, f2));
    }

    @Override
    protected void a(class_332 class_3322, class_4587 class_45872, int n, int n2) {
        float f = this.av(1060.0f);
        float f2 = 130.0f;
        float f3 = 1060.0f;
        float f4 = this.kw();
        MenuPanelRenderer.n(class_45872, f, f2, f3, f4);
        this.a(class_45872, f, f2, f3, n, n2);
        Render2D.drawRoundedRect(class_45872, f, f2 + 64.0f - 1.0f, f3, 1.0f, 0.0f, MenuChrome.white(0.07f));
        this.f(class_3322, class_45872, f, f2 + 64.0f, f3, f4 - 64.0f - 64.0f, n, n2);
        this.b(class_45872, f, f2 + f4 - 64.0f, f3, n, n2);
        MenuPanelRenderer.p(class_45872, f, f2, f3, f4);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 32.0f;
        float f5 = 36.0f;
        float f6 = f + 22.0f;
        float f7 = f4 - f5 / 2.0f + 6.0f;
        boolean bl = MenuChrome.hit(n, n2, f6, f7, f5, f5);
        float f8 = MenuChrome.upd(this.agg, bl);
        MenuPanelRenderer.c(class_45872, f6, f7, f5, f5, f8, false);
        ClickGuiIcons.arrowLeft(class_45872, f6 + f5 / 2.0f, f7 + f5 / 2.0f, 16.0f, MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.TEXT2, f8));
        MenuChrome.set(this.agj, f6, f7, f5, f5);
        float f9 = f6 + f5 + 16.0f;
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        Render2D.drawFont(class_45872, instance, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", f9, f4 - instance.getHeight() / 2.0f, MenuChrome.MUTED2);
        Render2D.drawFont(class_45872, instance, "/", f9 += instance.getWidth("\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438") + 8.0f, f4 - instance.getHeight() / 2.0f, MenuChrome.MUTED4);
        Instance instance2 = Fonts.GOLOS_SEMI.getFont(17.0f);
        Render2D.drawFont(class_45872, instance2, "\u041d\u0430\u0431\u043e\u0440\u044b \u0440\u0435\u0441\u0443\u0440\u0441\u043e\u0432", f9 += instance.getWidth("/") + 8.0f, f4 - instance2.getHeight() / 2.0f, MenuChrome.TEXT);
    }

    private void f(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        this.agp.clear();
        this.agq.clear();
        this.agr.clear();
        this.ags.clear();
        long l = System.currentTimeMillis();
        float f5 = this.Ln == 0L ? 0.016f : Math.min(0.05f, (float)(l - this.Ln) / 1000.0f);
        this.Ln = l;
        float f6 = 1.0f - (float)Math.exp(-13.0f * f5);
        float f7 = (f3 - 44.0f - 14.0f) / 2.0f;
        float f8 = f2 + 20.0f;
        float f9 = f8 + 28.0f;
        float f10 = f4 - 40.0f - 28.0f;
        this.agx = f9;
        this.agy = f9 + f10;
        float f11 = f + 22.0f;
        float f12 = f11 + f7 + 14.0f;
        this.a(class_45872, f11, f8, f7, "\u0414\u043e\u0441\u0442\u0443\u043f\u043d\u044b", this.agm.size());
        this.a(class_45872, f12, f8, f7, "\u0412\u044b\u0431\u0440\u0430\u043d\u044b", this.agn.size());
        this.agu = class_3532.method_15363((float)this.agu, (float)0.0f, (float)ResourcePackSettingsScreen.j(this.agm.size(), f10));
        this.agt += (this.agu - this.agt) * f6;
        if (Math.abs(this.agu - this.agt) < 0.4f) {
            this.agt = this.agu;
        }
        this.agw = class_3532.method_15363((float)this.agw, (float)0.0f, (float)ResourcePackSettingsScreen.j(this.agn.size(), f10));
        this.agv += (this.agw - this.agv) * f6;
        if (Math.abs(this.agw - this.agv) < 0.4f) {
            this.agv = this.agw;
        }
        this.a(class_3322, class_45872, f11, f9, f7, f10, n, n2, true);
        this.a(class_3322, class_45872, f12, f9, f7, f10, n, n2, false);
    }

    private static float j(int n, float f) {
        float f2 = (float)n * 56.0f + (float)Math.max(0, n - 1) * 8.0f;
        return Math.max(0.0f, f2 - f);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, String string, int n) {
        Instance instance = Fonts.GOLOS_SEMI.getFont(12.5f);
        Render2D.drawFont(class_45872, instance, string, f + 2.0f, f2 + 2.0f, MenuChrome.TEXT3);
        Instance instance2 = Fonts.GOLOS.getFont(11.5f);
        String string2 = String.valueOf(n);
        Render2D.drawFont(class_45872, instance2, string2, f + f3 - 2.0f - instance2.getWidth(string2), f2 + 3.0f, MenuChrome.MUTED3);
        Render2D.drawRoundedRect(class_45872, f, f2 + 28.0f - 10.0f, f3, 1.0f, 0.0f, MenuChrome.white(0.06f));
    }

    private void a(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2, boolean bl) {
        float f5;
        float f6;
        float f7;
        float f8;
        List<class_3288> list;
        if (bl) {
            list = this.agm;
            f8 = this.agt;
        } else {
            list = this.agn;
            f8 = this.agv;
        }
        long l = System.currentTimeMillis();
        Render2D.startScissor(class_3322, f, f2, f3, f4);
        for (int i = 0; i < list.size(); ++i) {
            f7 = f2 + (float)i * 64.0f - f8;
            f6 = class_3532.method_15363((float)((float)(l - this.Rf - (long)i * 16L) / 220.0f), (float)0.0f, (float)1.0f);
            f5 = f7 + (1.0f - (f6 = 1.0f - (1.0f - f6) * (1.0f - f6))) * 12.0f;
            if (f5 + 56.0f < f2 || f5 > f2 + f4) continue;
            this.a(class_45872, list.get(i), i, f, f5, f3, n, n2, bl);
        }
        Render2D.stopScissor(class_3322);
        float f9 = ResourcePackSettingsScreen.j(list.size(), f4);
        if (f9 > 0.5f) {
            f7 = (float)list.size() * 56.0f + (float)Math.max(0, list.size() - 1) * 8.0f;
            f6 = f4 - 8.0f;
            f5 = Math.max(24.0f, f6 * (f4 / f7));
            float f10 = f2 + 4.0f + (f6 - f5) * (f8 / f9);
            Render2D.drawRoundedRect(class_45872, f + f3 - 4.0f, f10, 3.0f, f5, 1.5f, MenuChrome.accentA(0.5f));
        }
    }

    private void a(class_4587 class_45872, class_3288 class_32882, int n, float f, float f2, float f3, int n2, int n3, boolean bl) {
        boolean bl2 = MenuChrome.hit(n2, n3, f, f2, f3, 56.0f);
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, 56.0f, 10.0f, new Color(24, 30, 27, 179), new Color(15, 19, 17, 179));
        Render2D.drawBorder(class_45872, f, f2, f3, 56.0f, 10.0f, 1.0f, 1.4f, MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.22f), bl2 ? 1.0f : 0.0f));
        float f4 = 34.0f;
        float f5 = f + 11.0f;
        float f6 = f2 + (56.0f - f4) / 2.0f;
        Render2D.drawRoundedRect(class_45872, f5, f6, f4, f4, 8.0f, MenuChrome.white(0.04f));
        Render2D.drawTexture(class_45872, f5 + 1.0f, f6 + 1.0f, f4 - 2.0f, f4 - 2.0f, 7.0f, 0.0f, 0.0f, 1.0f, 1.0f, this.b(class_32882), Color.WHITE);
        Render2D.drawBorder(class_45872, f5, f6, f4, f4, 8.0f, 1.0f, 1.2f, MenuChrome.white(0.07f));
        int n4 = 1;
        if (!bl) {
            n4 = 3;
        }
        float f7 = (float)n4 * 26.0f + (float)(n4 - 1) * 6.0f;
        float f8 = f5 + f4 + 12.0f;
        float f9 = Math.max(20.0f, f + f3 - 12.0f - f7 - 10.0f - f8);
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        Render2D.drawFont(class_45872, instance, ResourcePackSettingsScreen.clip(instance, ResourcePackSettingsScreen.cD(class_32882.method_14457().getString()), f9), f8, f2 + 12.0f, MenuChrome.TEXT2);
        Instance instance2 = Fonts.GOLOS.getFont(11.5f);
        Render2D.drawFont(class_45872, instance2, ResourcePackSettingsScreen.clip(instance2, ResourcePackSettingsScreen.cD(class_32882.method_14459().getString()), f9), f8, f2 + 30.0f, MenuChrome.MUTED2);
        float f10 = f + f3 - 12.0f - f7;
        float f11 = f2 + 15.0f;
        if (bl) {
            this.a(class_45872, f10, f11, n2, n3, 0, n, this.agp);
        } else {
            this.a(class_45872, f10, f11, n2, n3, 1, n, this.agr);
            this.a(class_45872, f10 + 26.0f + 6.0f, f11, n2, n3, 2, n, this.ags);
            if (class_32882.method_14464()) {
                MenuPanelRenderer.c(class_45872, f10 + 64.0f, f11, 26.0f, 26.0f, 0.0f, false);
                ClickGuiIcons.arrowLeft(class_45872, f10 + 64.0f + 13.0f, f11 + 13.0f, 14.0f, MenuChrome.MUTED4);
            } else {
                this.a(class_45872, f10 + 64.0f, f11, n2, n3, 3, n, this.agq);
            }
        }
    }

    private void a(class_4587 class_45872, float f, float f2, int n, int n2, int n3, int n4, List<float[]> list) {
        boolean bl = MenuChrome.hit(n, n2, f, f2, 26.0f, 26.0f);
        float f3 = bl ? 1.0f : 0.0f;
        MenuPanelRenderer.c(class_45872, f, f2, 26.0f, 26.0f, f3, false);
        Color color = MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.accent(), f3);
        float f4 = f + 13.0f;
        float f5 = f2 + 13.0f;
        if (n3 == 0) {
            ClickGuiIcons.arrowRight(class_45872, f4, f5, 14.0f, color);
        } else if (n3 == 1) {
            ClickGuiIcons.chevronUp(class_45872, f4, f5, 14.0f, color);
        } else if (n3 == 2) {
            ClickGuiIcons.chevronDown(class_45872, f4, f5, 14.0f, color);
        } else {
            ClickGuiIcons.arrowLeft(class_45872, f4, f5, 14.0f, color);
        }
        list.add(new float[]{f, f2, 26.0f, 26.0f, n4});
    }

    private static String cD(String string) {
        return string.replace('\n', ' ').replace('\r', ' ').trim();
    }

    private static String clip(Instance instance, String string, float f) {
        if (instance.getWidth(string) <= f) {
            return string;
        }
        String string2 = string;
        while (!string2.isEmpty() && instance.getWidth(string2 + "...") > f) {
            string2 = string2.substring(0, string2.length() - 1);
        }
        return string2 + "...";
    }

    private void b(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 32.0f;
        Instance instance = Fonts.UNBOUNDED.getFont(11.0f);
        String string = "\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043f\u0430\u043f\u043a\u0443";
        float f5 = 15.0f;
        float f6 = 44.0f;
        float f7 = 18.0f + f5 + 9.0f + instance.getWidth(string) + 18.0f;
        float f8 = f + 22.0f;
        float f9 = f4 - f6 / 2.0f - 9.0f;
        boolean bl = MenuChrome.hit(n, n2, f8, f9, f7, f6);
        float f10 = MenuChrome.upd(this.agi, bl);
        MenuChrome.drawSecondaryMaterial(class_45872, f8, f9, f7, f6, 8.0f, f10, false);
        ClickGuiIcons.layers(class_45872, f8 + 18.0f + f5 / 2.0f, f9 + f6 / 2.0f, f5, MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.TEXT2, f10));
        Render2D.drawFont(class_45872, instance, string, f8 + 18.0f + f5 + 9.0f, f9 + f6 / 2.0f - instance.getHeight() / 2.0f, MenuChrome.lerp(MenuChrome.TEXT3, MenuChrome.TEXT, f10));
        MenuChrome.set(this.agl, f8, f9, f7, f6);
        String string2 = "\u0413\u043e\u0442\u043e\u0432\u043e";
        float f11 = 16.0f;
        float f12 = 44.0f;
        float f13 = 24.0f + f11 + 9.0f + instance.getWidth(string2) + 24.0f;
        float f14 = f + f3 - 22.0f - f13;
        float f15 = f4 - f12 / 2.0f - 9.0f;
        boolean bl2 = MenuChrome.hit(n, n2, f14, f15, f13, f12);
        float f16 = MenuChrome.upd(this.agh, bl2);
        float f17 = f15 - f16;
        Color color = MenuChrome.accent();
        Render2D.drawRoundedRect(class_45872, f14 + 4.0f, f17 + f12 - 6.0f, f13 - 8.0f, 12.0f, 8.0f, MenuChrome.accentA(0.24f + 0.14f * f16));
        int n3 = Math.min(255, Math.round((float)color.getRed() * (1.0f + 0.07f * f16)));
        int n4 = Math.min(255, Math.round((float)color.getGreen() * (1.0f + 0.07f * f16)));
        int n5 = Math.min(255, Math.round((float)color.getBlue() * (1.0f + 0.07f * f16)));
        Render2D.drawRoundedRect(class_45872, f14, f17, f13, f12, 8.0f, new Color(n3, n4, n5));
        Render2D.drawRoundedRect(class_45872, f14 + 8.0f, f17 + 1.0f, f13 - 16.0f, 1.0f, 0.5f, MenuChrome.white(0.35f));
        ClickGuiIcons.check(class_45872, f14 + 24.0f + f11 / 2.0f, f17 + f12 / 2.0f, f11, MenuChrome.INK);
        Render2D.drawFont(class_45872, instance, string2, f14 + 24.0f + f11 + 9.0f, f17 + f12 / 2.0f - instance.getHeight() / 2.0f, MenuChrome.INK);
        MenuChrome.set(this.agk, f14, f15, f13, f12);
    }

    @Override
    protected boolean a(float f, float f2, int n) {
        if (n != 0) {
            return false;
        }
        if (MenuChrome.in(this.agj, f, f2)) {
            this.pJ();
            return true;
        }
        if (MenuChrome.in(this.agk, f, f2)) {
            this.pl();
            this.pJ();
            return true;
        }
        if (MenuChrome.in(this.agl, f, f2)) {
            this.qM();
            return true;
        }
        if (f2 < this.agx || f2 > this.agy) {
            return false;
        }
        int n2 = ResourcePackSettingsScreen.a(this.agp, f, f2);
        if (n2 >= 0 && n2 < this.agm.size()) {
            this.agn.add(0, this.agm.remove(n2));
            return true;
        }
        n2 = ResourcePackSettingsScreen.a(this.agq, f, f2);
        if (n2 >= 0 && n2 < this.agn.size()) {
            this.agm.add(this.agn.remove(n2));
            return true;
        }
        n2 = ResourcePackSettingsScreen.a(this.agr, f, f2);
        if (n2 > 0 && n2 < this.agn.size()) {
            this.agn.add(n2 - 1, this.agn.remove(n2));
            return true;
        }
        n2 = ResourcePackSettingsScreen.a(this.ags, f, f2);
        if (n2 >= 0 && n2 < this.agn.size() - 1) {
            this.agn.add(n2 + 1, this.agn.remove(n2));
            return true;
        }
        return false;
    }

    private static int a(List<float[]> list, float f, float f2) {
        for (float[] fArray : list) {
            if (!(f >= fArray[0]) || !(f <= fArray[0] + fArray[2]) || !(f2 >= fArray[1]) || !(f2 <= fArray[1] + fArray[3])) continue;
            return (int)fArray[4];
        }
        return -1;
    }

    @Override
    protected boolean a(float f, float f2, double d) {
        float f3 = (float)d * 64.0f * 0.9f;
        if (f < this.av(1060.0f) + 530.0f) {
            this.agu -= f3;
        } else {
            this.agw -= f3;
        }
        return true;
    }

    private void pl() {
        class_3283 class_32832 = aiG.method_1520();
        ArrayList<String> arrayList = new ArrayList<String>();
        for (class_3288 class_32882 : this.ago) {
            arrayList.add(class_32882.method_14463());
        }
        for (int i = this.agn.size() - 1; i >= 0; --i) {
            arrayList.add(this.agn.get(i).method_14463());
        }
        class_32832.method_14447(arrayList);
        ResourcePackSettingsScreen.aiG.field_1690.method_49598(class_32832);
    }

    private void qM() {
        Path path = aiG.method_1479();
        try {
            Files.createDirectories(path, new FileAttribute[0]);
        }
        catch (Exception exception) {
        }
        class_156.method_668().method_60932(path);
    }

    static {
        agB = class_2960.method_60656((String)"textures/misc/unknown_pack.png");
    }
}

