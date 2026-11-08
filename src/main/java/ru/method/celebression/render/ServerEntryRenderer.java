package ru.method.celebression.render;

import java.awt.Color;
import java.util.Locale;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_424;
import net.minecraft.class_434;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_8671;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class ServerEntryRenderer {
    public static final float[] ada = new float[4];
    public static float uiScale = 1.0f;
    private static String adb;
    private static String adc;
    private static boolean add;

    private ServerEntryRenderer() {
    }

    public static void u(String string, String string2) {
        adb = string;
        adc = string2;
        add = true;
    }

    public static void pZ() {
        add = false;
    }

    public static String qa() {
        return adb;
    }

    public static String qb() {
        return adc;
    }

    public static boolean b(class_437 class_4372) {
        if (!ru.method.celebression.service.AppearanceMode.isCustom()) return false;
        if (!add || class_4372 == null) {
            return false;
        }
        return class_4372 instanceof class_434 || class_4372 instanceof class_8671 || class_4372 instanceof class_424;
    }

    public static class_2561 c(class_437 class_4372) {
        class_2561 class_25612;
        class_2561 class_25613 = class_25612 = class_4372 == null ? null : class_4372.method_25440();
        if (class_25612 == null || class_25612.getString().isBlank()) {
            return class_2561.method_43470((String)"\u0412\u0445\u043e\u0434 \u0432 \u043c\u0438\u0440");
        }
        return class_25612;
    }

    public static void a(class_332 class_3322, int n, int n2, class_2561 class_25612, String string, String string2) {
        ServerEntryRenderer.a(class_3322, n, n2, class_25612, string, string2, true);
    }

    public static void a(class_332 class_3322, int n, int n2, class_2561 class_25612, String string, String string2, boolean bl) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || class_3102.method_22683() == null) {
            return;
        }
        uiScale = Math.max(0.05f, (float)class_3102.method_22683().method_4502() / 1080.0f);
        int n3 = Math.max(1, Math.round((float)class_3102.method_22683().method_4486() / uiScale));
        int n4 = Math.max(1, Math.round((float)class_3102.method_22683().method_4502() / uiScale));
        int n5 = Math.round((float)n / uiScale);
        int n6 = Math.round((float)n2 / uiScale);
        long l = System.currentTimeMillis();
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        class_45872.method_22905(uiScale, uiScale, 1.0f);
        ClickGuiIcons.pixelScale(uiScale);
        MenuChrome.drawScreenBackground(class_45872, n3, n4, 0.0f, 0.0f);
        MenuChrome.drawLogo(class_45872, n3);
        MenuChrome.drawClock(class_45872, n3);
        MenuChrome.drawFooterSignature(class_45872, n3, n4, false);
        float f = 420.0f;
        float f2 = bl ? 268.0f : 206.0f;
        float f3 = (float)n3 / 2.0f - f / 2.0f;
        float f4 = (float)n4 / 2.0f - f2 * 0.54f;
        MenuPanelRenderer.n(class_45872, f3, f4, f, f2);
        float f5 = f3 + f / 2.0f;
        float f6 = f4 + 30.0f;
        ServerEntryRenderer.a(class_45872, f5, f6 + 22.0f, l);
        Instance instance = Fonts.GOLOS_SEMI.getFont(16.0f);
        String string3 = "\u041f\u043e\u0434\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u0435 \u043a \u0441\u0435\u0440\u0432\u0435\u0440\u0443";
        Render2D.drawFont(class_45872, instance, string3, f5 - instance.getWidth(string3) / 2.0f, f6 += 62.0f, MenuChrome.TEXT);
        f6 += instance.getHeight() + 9.0f;
        Instance instance2 = Fonts.UNBOUNDED.getFont(12.0f);
        Instance instance3 = Fonts.PLEXMONO.getFont(12.5f);
        String string4 = ServerEntryRenderer.v(string, string2);
        String string5 = string2 == null ? "" : string2;
        boolean bl2 = !string5.isEmpty() && !string4.toLowerCase(Locale.ROOT).contains(string5.toLowerCase(Locale.ROOT).replaceAll(":\\d+$", ""));
        float f7 = f - 56.0f;
        float f8 = bl2 ? 10.0f : 0.0f;
        float f9 = bl2 ? instance3.getWidth(string5) : 0.0f;
        string4 = ServerEntryRenderer.d(instance2, string4, f7 - f8 - f9);
        float f10 = instance2.getWidth(string4) + f8 + f9;
        float f11 = f5 - f10 / 2.0f;
        Render2D.drawFont(class_45872, instance2, string4, f11, f6, MenuChrome.TEXT3);
        if (bl2) {
            Render2D.drawRoundedRect(class_45872, f11 += instance2.getWidth(string4) + f8 / 2.0f - 1.0f, f6 + instance3.getHeight() / 2.0f - 1.5f, 3.0f, 3.0f, 1.5f, MenuChrome.MUTED4);
            Render2D.drawFont(class_45872, instance3, string5, f11 += f8 / 2.0f + 1.0f, f6, MenuChrome.MUTED2);
        }
        float f12 = f - 56.0f;
        float f13 = f5 - f12 / 2.0f;
        Render2D.drawRoundedRect(class_45872, f13, f6 += Math.max(instance2.getHeight(), instance3.getHeight()) + 20.0f, f12, 3.0f, 1.5f, MenuChrome.white(0.08f));
        float f14 = f12 * 0.38f;
        float f15 = (float)(l % 1400L) / 1400.0f;
        float f16 = f15 < 0.5f ? 2.0f * f15 * f15 : 1.0f - (float)Math.pow(-2.0f * f15 + 2.0f, 2.0) / 2.0f;
        float f17 = f13 + (f12 - f14) * f16;
        MenuChrome.glow(class_45872, f17, f6, f14, 3.0f, 1.5f, MenuChrome.accentA(0.7f), 0.0f, 3, 1.3f);
        Render2D.drawRoundedRect(class_45872, f17, f6, f14, 3.0f, 1.5f, MenuChrome.accent());
        Instance instance4 = Fonts.GOLOS.getFont(12.0f);
        String string6 = class_25612 == null ? "" : class_25612.getString();
        Render2D.drawFont(class_45872, instance4, string6, f5 - instance4.getWidth(string6) / 2.0f, f6 += 12.0f, MenuChrome.MUTED2);
        f6 += instance4.getHeight() + 20.0f;
        if (bl) {
            float f18;
            float f19;
            float f20;
            float f21;
            boolean bl3 = MenuChrome.hit(n5, n6, f21 = f3 + 28.0f, f20 = f6, f19 = f - 56.0f, f18 = 42.0f);
            MenuChrome.drawSecondaryMaterial(class_45872, f21, f20, f19, f18, 8.0f, bl3 ? 1.0f : 0.0f, true);
            Instance instance5 = Fonts.UNBOUNDED.getFont(11.0f);
            String string7 = "\u041e\u0442\u043c\u0435\u043d\u0430";
            float f22 = 14.0f;
            float f23 = f22 + 9.0f + instance5.getWidth(string7);
            float f24 = f21 + (f19 - f23) / 2.0f;
            ClickGuiIcons.close(class_45872, f24 + f22 / 2.0f, f20 + f18 / 2.0f, f22, MenuChrome.MUTED);
            Render2D.drawFont(class_45872, instance5, string7, f24 + f22 + 9.0f, f20 + f18 / 2.0f - instance5.getHeight() / 2.0f, MenuChrome.TEXT2);
            MenuChrome.set(ada, f21, f20, f19, f18);
        } else {
            MenuChrome.set(ada, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        MenuPanelRenderer.p(class_45872, f3, f4, f, f2);
        class_45872.method_22909();
        ClickGuiIcons.pixelScale(1.0f);
    }

    private static String v(String string, String string2) {
        String string3 = string == null ? "" : string.trim();
        if ((string3 = string3.replaceAll("\\s*\\((?:[^)]*erid[^)]*|\\d{1,2}\\+[^)]*)\\)\\s*$", "").trim()).isEmpty()) {
            String string4 = string2 == null ? "" : string2.replaceAll(":\\d+$", "");
            return string4.isEmpty() ? "\u0421\u0435\u0440\u0432\u0435\u0440" : string4;
        }
        return string3;
    }

    private static String d(Instance instance, String string, float f) {
        if (f <= 0.0f || instance.getWidth(string) <= f) {
            return string;
        }
        int n = 0;
        int n2 = string.length();
        while (n < n2) {
            int n3 = n + n2 + 1 >>> 1;
            if (instance.getWidth(string.substring(0, n3) + "\u2026") <= f) {
                n = n3;
                continue;
            }
            n2 = n3 - 1;
        }
        return string.substring(0, n) + "\u2026";
    }

    private static void a(class_4587 class_45872, float f, float f2, long l) {
        float f3 = 18.0f;
        float f4 = 3.0f;
        Render2D.drawRing(class_45872, f, f2, f3, f4, MenuChrome.white(0.09f));
        float f5 = (float)(l % 1100L) / 1100.0f * 360.0f;
        float f6 = 110.0f;
        Color color = MenuChrome.accent();
        float f7 = (float)(Math.toRadians(f6) * (double)f3);
        int n = Math.max(24, Math.round(f7));
        for (int i = 0; i <= n; ++i) {
            double d = Math.toRadians(f5 + f6 * (float)i / (float)n);
            float f8 = 0.35f + 0.65f * ((float)i / (float)n);
            Render2D.drawCircle(class_45872, f + (float)Math.cos(d) * f3, f2 + (float)Math.sin(d) * f3, f4 / 2.0f, MenuChrome.accentA(f8));
        }
        Render2D.drawCircle(class_45872, f, f2, 3.0f, color);
    }
}

