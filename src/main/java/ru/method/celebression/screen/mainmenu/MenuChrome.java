package ru.method.celebression.screen.mainmenu;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_320;
import net.minecraft.class_3414;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class MenuChrome {
    public static final float DESIGN_HEIGHT = 1080.0f;
    public static final String MC_VERSION = "1.21.4";
    public static final String BUILD = "2.0";
    public static final Color TEXT;
    public static final Color TEXT2;
    public static final Color TEXT3;
    public static final Color TEXT4;
    public static final Color MUTED;
    public static final Color MUTED2;
    public static final Color MUTED3;
    public static final Color MUTED4;
    public static final Color INK;
    public static final Color DANGER;
    public static final class_2960 BG;
    public static final float BG_W = 1270.0f;
    public static final float BG_H = 999.0f;
    private static final class_2960 MUSIC_PLAY;
    private static final class_2960 MUSIC_PAUSE;
    private static final String TRACK_TITLE;
    private static final String TRACK_ARTIST;
    private static final long TRACK_DURATION_MS = 131300L;
    private static final float TRACK_VOLUME = 0.4f;
    private static class_1113 musicInstance;
    private static boolean musicAutoStarted;
    private static boolean musicPlaying;
    private static boolean musicPaused;
    private static long musicStartMs;
    private static long musicPausedAtMs;
    private static final DateTimeFormatter TIME_FMT;
    public static final String IC_GLOBE;
    public static final String IC_PERSON;
    public static final String IC_GEAR;
    public static final String IC_POWER;
    public static final String IC_TG;
    public static final String IC_DISCORD;
    public static final String IC_CODE;
    private static class_1043 vignetteTex;
    private static final Locale RU;
    private static long clockSec;
    private static String clockHh;
    private static String clockMm;
    private static String clockDay;
    private static String clockRest;
    private static boolean keepMusicOnSwap;

    private MenuChrome() {
    }

    public static Color accent() {
        return CustomMainMenuScreen.ACCENT;
    }

    public static Color accent(int n) {
        Color color = MenuChrome.accent();
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), class_3532.method_15340((int)n, (int)0, (int)255));
    }

    public static Color accentA(float f) {
        return MenuChrome.accent(Math.round(f * 255.0f));
    }

    public static Color white(float f) {
        return new Color(255, 255, 255, class_3532.method_15340((int)Math.round(f * 255.0f), (int)0, (int)255));
    }

    public static void glow(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Color color, float f6, int n, float f7) {
        for (int i = n; i >= 1; --i) {
            float f8 = (float)i * f7;
            int n2 = Math.round((float)color.getAlpha() * (1.0f - (float)(i - 1) / (float)n) / (float)n * 2.2f);
            if (n2 <= 0) continue;
            Render2D.drawRoundedRect(class_45872, f - f8, f2 + f6 - f8, f3 + 2.0f * f8, f4 + 2.0f * f8, f5 + f8, new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.min(255, n2)));
        }
    }

    public static Animation anim() {
        return new Animation(130L, 1.0, false, Easing.aiZ);
    }

    public static void drawBackgroundArt(class_4587 class_45872, int n, int n2, float f, float f2) {
        float f3 = Math.max((float)n / 1270.0f, (float)n2 / 999.0f) * 1.07f;
        float f4 = 1270.0f * f3;
        float f5 = 999.0f * f3;
        float f6 = -f * Math.min((f4 - (float)n) / 2.0f, 42.0f);
        float f7 = -f2 * Math.min((f5 - (float)n2) / 2.0f, 26.0f);
        Render2D.drawTexture(class_45872, ((float)n - f4) / 2.0f + f6, ((float)n2 - f5) / 2.0f + f7, f4, f5, 0.0f, BG, new Color(255, 255, 255, 255));
    }

    public static void drawScreenBackground(class_4587 class_45872, int n, int n2, float f, float f2) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1687 != null) {
            Render2D.drawBlurredRect(class_45872, 0.0f, 0.0f, n, n2, 0.0f, 14.0f, new Color(4, 7, 6, 48), true);
        } else {
            MenuChrome.drawBackgroundArt(class_45872, n, n2, f, f2);
        }
        MenuChrome.drawRadialVignette(class_45872, n, n2);
    }

    public static void drawRadialVignette(class_4587 class_45872, int n, int n2) {
        class_1043 class_10432 = MenuChrome.vignetteTexture();
        if (class_10432 != null) {
            Render2D.drawTexture(class_45872, 0.0f, 0.0f, (float)n, (float)n2, 0.0f, (class_1044)class_10432, new Color(255, 255, 255, 255));
        }
    }

    private static class_1043 vignetteTexture() {
        if (vignetteTex != null) {
            return vignetteTex;
        }
        int n = 192;
        float f = 0.5f;
        float f2 = 0.44f;
        float f3 = 0.52f;
        float f4 = 0.46f;
        class_1011 class_10112 = new class_1011(class_1011.class_1012.field_4997, 192, 192, false);
        for (int i = 0; i < 192; ++i) {
            for (int j = 0; j < 192; ++j) {
                float f5 = ((float)j + 0.5f) / 192.0f;
                float f6 = ((float)i + 0.5f) / 192.0f;
                float f7 = (f5 - 0.5f) / 0.52f;
                float f8 = (f6 - 0.44f) / 0.46f;
                float f9 = class_3532.method_15363((float)((float)Math.sqrt(f7 * f7 + f8 * f8)), (float)0.0f, (float)1.0f);
                float f10 = 0.3f + 0.44f * f9;
                int n2 = class_3532.method_15340((int)Math.round(f10 * 255.0f), (int)0, (int)255);
                class_10112.method_61941(j, i, n2 << 24 | 0x40000 | 0x700 | 6);
            }
        }
        vignetteTex = new class_1043(class_10112);
        vignetteTex.method_4527(true, false);
        vignetteTex.method_4524();
        return vignetteTex;
    }

    public static void drawBackChip(class_4587 class_45872, int n, int n2, Animation animation, float[] fArray) {
        Instance instance = Fonts.UNBOUNDED.getFont(10.0f);
        String string = "\u041d\u0430\u0437\u0430\u0434";
        float f = 38.0f;
        float f2 = 17.0f;
        float f3 = 12.0f + f2 + 9.0f + instance.getWidth(string) + 15.0f;
        float f4 = 56.0f;
        float f5 = 44.0f;
        boolean bl = MenuChrome.hit(n, n2, f4, f5, f3, f);
        float f6 = MenuChrome.upd(animation, bl);
        float f7 = f5 - f6;
        MenuChrome.drawSecondaryMaterial(class_45872, f4, f7, f3, f, 8.0f, f6, false);
        ClickGuiIcons.arrowLeft(class_45872, f4 + 12.0f + f2 / 2.0f, f7 + f / 2.0f, f2, MUTED);
        Render2D.drawFont(class_45872, instance, string, f4 + 12.0f + f2 + 9.0f, f7 + f / 2.0f - instance.getHeight() / 2.0f, TEXT2);
        MenuChrome.set(fArray, f4, f5, f3, f);
    }

    public static void drawFooterSignature(class_4587 class_45872, int n, int n2, boolean bl) {
        // Promotional footer removed.
    }

    public static void drawLogo(class_4587 class_45872, int n) {
        Instance instance = Fonts.GOLOS_SEMI.getFont(23.0f);
        float f = MenuChrome.widthSpaced(instance, "celebression", -0.805f);
        float f2 = instance.getWidth(".");
        float f3 = f + f2;
        float f4 = (float)n / 2.0f - f3 / 2.0f;
        float f5 = 44.0f;
        MenuChrome.drawSpaced(class_45872, instance, "celebression", f4, f5, TEXT, -0.805f);
        Render2D.drawFont(class_45872, instance, ".", f4 + f, f5, MenuChrome.accent());
    }

    public static void drawClock(class_4587 class_45872, int n) {
        Object object;
        long l = System.currentTimeMillis() / 1000L;
        if (l != clockSec) {
            clockSec = l;
            object = LocalTime.now().format(TIME_FMT);
            clockHh = ((String)object).substring(0, 2);
            clockMm = ((String)object).substring(3, 5);
            LocalDate localDate = LocalDate.now();
            clockDay = MenuChrome.cap(localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, RU), RU) + ", ";
            clockRest = localDate.getDayOfMonth() + " " + localDate.getMonth().getDisplayName(TextStyle.FULL, RU);
        }
        object = Fonts.PLEXMONO.getFont(44.0f);
        float f = 6.6000004f;
        float f2 = 4.4f;
        float f3 = ((Instance)object).getWidth(clockHh) + f + f2 + f + ((Instance)object).getWidth(clockMm);
        float f4 = (float)n - 56.0f;
        MenuChrome.drawClockTime(class_45872, (Instance)object, clockHh, clockMm, f4 - f3, 38.0f, TEXT, 44.0f, f, f2);
        Instance instance = Fonts.GOLOS.getFont(12.5f);
        float f5 = 0.75f;
        float f6 = MenuChrome.widthSpaced(instance, clockDay, f5);
        float f7 = MenuChrome.widthSpaced(instance, clockRest, f5);
        float f8 = 88.0f;
        MenuChrome.drawSpaced(class_45872, instance, clockDay, f4 - f6 - f7, f8, MUTED2, f5);
        MenuChrome.drawSpaced(class_45872, instance, clockRest, f4 - f7, f8, TEXT3, f5);
    }

    public static void drawClockTime(class_4587 class_45872, Instance instance, String string, String string2, float f, float f2, Color color, float f3, float f4, float f5) {
        Render2D.drawFont(class_45872, instance, string, f, f2, color);
        float f6 = f + instance.getWidth(string) + f4;
        Render2D.drawRoundedRect(class_45872, f6, f2 + f3 * 0.52f, f5, f5, f5 / 2.0f, color);
        Render2D.drawRoundedRect(class_45872, f6, f2 + f3 * 0.74f, f5, f5, f5 / 2.0f, color);
        Render2D.drawFont(class_45872, instance, string2, f6 + f5 + f4, f2, color);
    }

    public static void drawButton(class_4587 class_45872, float f, float f2, float f3, float f4, boolean bl, String string, String string2, Color color, float f5, boolean bl2, boolean bl3) {
        float f6 = f2 - f5;
        float f7 = 8.0f;
        if (bl) {
            Color color2 = MenuChrome.accent();
            Render2D.drawRoundedRect(class_45872, f + 4.0f, f6 + f4 - 6.0f, f3 - 8.0f, 12.0f, 8.0f, new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), Math.round(60.0f + 40.0f * f5)));
            int n = Math.min(255, Math.round((float)color2.getRed() * (1.0f + 0.07f * f5)));
            int n2 = Math.min(255, Math.round((float)color2.getGreen() * (1.0f + 0.07f * f5)));
            int n3 = Math.min(255, Math.round((float)color2.getBlue() * (1.0f + 0.07f * f5)));
            Render2D.drawRoundedRect(class_45872, f, f6, f3, f4, f7, new Color(n, n2, n3));
            Render2D.drawRoundedRect(class_45872, f + f7, f6 + 1.0f, f3 - f7 * 2.0f, 1.0f, 0.5f, new Color(255, 255, 255, 90));
        } else {
            MenuChrome.drawSecondaryMaterial(class_45872, f, f6, f3, f4, f7, f5, bl3);
        }
        float f8 = 15.0f;
        Instance instance = Fonts.ICONS2.getFont(f8);
        Instance instance2 = (bl ? Fonts.UNBOUNDED : Fonts.UNBOUNDED_L).getFont(12.5f);
        float f9 = instance.getWidth(string);
        float f10 = instance2.getWidth(string2);
        float f11 = f6 + f4 / 2.0f;
        if (bl2) {
            float f12 = f9 + 9.0f + f10;
            float f13 = f + (f3 - f12) / 2.0f;
            Render2D.drawFont(class_45872, instance, string, f13, f11 - instance.getHeight() / 2.0f, color);
            Render2D.drawFont(class_45872, instance2, string2, f13 + f9 + 9.0f, f11 - instance2.getHeight() / 2.0f, color);
        } else {
            float f14 = f + 16.0f;
            Render2D.drawFont(class_45872, instance, string, f14, f11 - instance.getHeight() / 2.0f, bl ? INK : MUTED);
            Render2D.drawFont(class_45872, instance2, string2, f14 + f9 + 12.0f, f11 - instance2.getHeight() / 2.0f, color);
        }
    }

    public static void drawSecondaryMaterial(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, boolean bl) {
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, f5, new Color(24, 30, 27, 217), new Color(15, 19, 17, 217));
        Render2D.drawRoundedRect(class_45872, f + f5, f2 + 1.0f, f3 - f5 * 2.0f, 1.0f, 0.5f, new Color(255, 255, 255, 13));
        Color color = bl ? MenuChrome.lerp(new Color(255, 255, 255, 23), new Color(224, 110, 110, 140), f6) : MenuChrome.lerp(new Color(255, 255, 255, 23), new Color(255, 255, 255, 61), f6);
        Render2D.drawBorder(class_45872, f, f2, f3, f4, f5, 1.0f, 1.4f, color);
    }

    public static boolean hasMusic() {
        return musicPlaying || musicPaused;
    }

    public static void autoStartOnce() {
        if (musicAutoStarted) {
            return;
        }
        musicAutoStarted = true;
        MenuChrome.startMusic();
    }

    public static void tickMusic() {
        if (musicPlaying && !musicPaused && System.currentTimeMillis() - musicStartMs >= 131300L) {
            musicPlaying = false;
            musicInstance = null;
        }
    }

    public static void drawMusic(class_4587 class_45872, int n, int n2, int n3, int n4, Animation animation, float[] fArray) {
        float f = 320.0f;
        float f2 = (float)n / 2.0f - f / 2.0f;
        float f3 = (float)n2 - 44.0f;
        float f4 = 34.0f;
        Instance instance = Fonts.PLEXMONO.getFont(10.5f);
        float f5 = f3 - instance.getHeight();
        float f6 = f5 - 12.0f - f4;
        float f7 = f6 - 9.0f - Fonts.GOLOS.getFont(10.5f).getHeight();
        Instance instance2 = Fonts.GOLOS.getFont(10.5f);
        String string = "\u0421\u0435\u0439\u0447\u0430\u0441 \u0438\u0433\u0440\u0430\u0435\u0442";
        float f8 = MenuChrome.widthSpaced(instance2, string, 0.84f);
        MenuChrome.drawSpaced(class_45872, instance2, string, f2 + (f - f8) / 2.0f, f7, MUTED2, 0.84f);
        boolean bl = MenuChrome.hit(n3, n4, f2, f6, f4, f4);
        float f9 = MenuChrome.upd(animation, bl);
        Render2D.drawRoundedVerticalGradient(class_45872, f2, f6, f4, f4, f4 / 2.0f, new Color(55, 48, 42), new Color(18, 16, 14));
        class_2960 class_29602 = musicPlaying && !musicPaused ? MUSIC_PAUSE : MUSIC_PLAY;
        float f10 = 9.0f;
        Render2D.drawTexture(class_45872, f2 + (f4 - f10) / 2.0f, f6 + (f4 - f10) / 2.0f, f10, f10, 0.0f, class_29602, MenuChrome.lerp(MUTED, TEXT, f9));
        MenuChrome.set(fArray, f2, f6, f4, f4);
        Instance instance3 = Fonts.UNBOUNDED.getFont(13.0f);
        Instance instance4 = Fonts.UNBOUNDED_L.getFont(11.0f);
        float f11 = f2 + f4 + 12.0f;
        float f12 = f6 + (f4 - instance3.getHeight()) / 2.0f;
        Render2D.drawFont(class_45872, instance3, "EVILSHIT ", f11, f12, Color.WHITE);
        float f13 = instance3.getWidth("EVILSHIT ");
        Render2D.drawFont(class_45872, instance4, "by ", f11 + f13, f12 + 2.0f, MUTED2);
        float f14 = instance4.getWidth("by ");
        Render2D.drawFont(class_45872, instance3, "ARLEKIN 40 000", f11 + f13 + f14, f12, Color.WHITE);
        float f15 = 0.0f;
        if (musicPlaying && !musicPaused) {
            f15 = class_3532.method_15363((float)((float)(System.currentTimeMillis() - musicStartMs) / 131300.0f), (float)0.0f, (float)1.0f);
        } else if (musicPaused) {
            f15 = class_3532.method_15363((float)((float)(musicPausedAtMs - musicStartMs) / 131300.0f), (float)0.0f, (float)1.0f);
        }
        long l = Math.round((double)f15 * 131300.0);
        String string2 = MenuChrome.fmtTime(l);
        String string3 = "-" + MenuChrome.fmtTime(131300L - l);
        float f16 = instance.getWidth(string2);
        float f17 = instance.getWidth(string3);
        Render2D.drawFont(class_45872, instance, string2, f2, f5, MUTED);
        Render2D.drawFont(class_45872, instance, string3, f2 + f - f17, f5, MUTED);
        float f18 = f2 + f16 + 10.0f;
        float f19 = f - f16 - f17 - 20.0f;
        float f20 = f5 + instance.getHeight() / 2.0f - 1.0f;
        Render2D.drawRoundedRect(class_45872, f18, f20, f19, 2.0f, 1.0f, new Color(255, 255, 255, 41));
        if (f15 > 0.001f) {
            Render2D.drawRoundedRect(class_45872, f18, f20, f19 * f15, 2.0f, 1.0f, TEXT);
        }
    }

    public static void keepMusicAcrossSwap() {
        keepMusicOnSwap = true;
    }

    public static void onMenuScreenRemoved() {
        if (keepMusicOnSwap) {
            keepMusicOnSwap = false;
            return;
        }
        MenuChrome.stopMusic();
    }

    public static void toggleMusic() {
        if (musicPlaying && !musicPaused) {
            MenuChrome.pauseMusic();
        } else if (musicPaused) {
            MenuChrome.resumeMusic();
        } else {
            MenuChrome.startMusic();
        }
    }

    public static void startMusic() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return;
        }
        MenuChrome.stopMusic();
        musicInstance = class_1109.method_4757((class_3414)class_3414.method_47908((class_2960)Celebression.id("mainmenu/track")), (float)1.0f, (float)0.4f);
        class_3102.method_1483().method_4873(musicInstance);
        musicPlaying = true;
        musicPaused = false;
        musicStartMs = System.currentTimeMillis();
    }

    public static void pauseMusic() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || !musicPlaying || musicPaused) {
            return;
        }
        class_3102.method_1483().method_4879();
        musicPaused = true;
        musicPausedAtMs = System.currentTimeMillis();
    }

    public static void resumeMusic() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || !musicPaused) {
            return;
        }
        class_3102.method_1483().method_4880();
        musicStartMs += System.currentTimeMillis() - musicPausedAtMs;
        musicPaused = false;
    }

    public static void stopMusic() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null) {
            if (musicPaused) {
                class_3102.method_1483().method_4880();
            }
            if (musicInstance != null) {
                class_3102.method_1483().method_4870(musicInstance);
            }
        }
        musicInstance = null;
        musicPlaying = false;
        musicPaused = false;
    }

    public static void drawSpaced(class_4587 class_45872, Instance instance, String string, float f, float f2, Color color, float f3) {
        float f4 = f;
        for (int i = 0; i < string.length(); ++i) {
            String string2 = String.valueOf(string.charAt(i));
            Render2D.drawFont(class_45872, instance, string2, f4, f2, color);
            f4 += instance.getWidth(string2) + f3;
        }
    }

    public static float widthSpaced(Instance instance, String string, float f) {
        float f2 = 0.0f;
        for (int i = 0; i < string.length(); ++i) {
            f2 += instance.getWidth(String.valueOf(string.charAt(i))) + f;
        }
        return Math.max(0.0f, f2 - f);
    }

    public static boolean hit(float f, float f2, float f3, float f4, float f5, float f6) {
        return f >= f3 && f <= f3 + f5 && f2 >= f4 && f2 <= f4 + f6;
    }

    public static boolean in(float[] fArray, float f, float f2) {
        return f >= fArray[0] && f <= fArray[0] + fArray[2] && f2 >= fArray[1] && f2 <= fArray[1] + fArray[3];
    }

    public static void set(float[] fArray, float f, float f2, float f3, float f4) {
        fArray[0] = f;
        fArray[1] = f2;
        fArray[2] = f3;
        fArray[3] = f4;
    }

    public static float upd(Animation animation, boolean bl) {
        animation.i(bl);
        return class_3532.method_15363((float)animation.rk(), (float)0.0f, (float)1.0f);
    }

    public static Color lerp(Color color, Color color2, float f) {
        f = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        return new Color(Math.round((float)color.getRed() + (float)(color2.getRed() - color.getRed()) * f), Math.round((float)color.getGreen() + (float)(color2.getGreen() - color.getGreen()) * f), Math.round((float)color.getBlue() + (float)(color2.getBlue() - color.getBlue()) * f), Math.round((float)color.getAlpha() + (float)(color2.getAlpha() - color.getAlpha()) * f));
    }

    public static String cap(String string, Locale locale) {
        return string == null || string.isEmpty() ? string : string.substring(0, 1).toUpperCase(locale) + string.substring(1);
    }

    public static String nick() {
        class_310 class_3102 = class_310.method_1551();
        return class_3102 != null && class_3102.method_1548() != null ? class_3102.method_1548().method_1676() : "player";
    }

    public static String licenseWord() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || class_3102.method_1548() == null) {
            return "\u043e\u0444\u0444\u043b\u0430\u0439\u043d";
        }
        class_320.class_321 class_3212 = class_3102.method_1548().method_35718();
        return class_3212 == class_320.class_321.field_34962 || class_3212 == class_320.class_321.field_1988 ? "\u043b\u0438\u0446\u0435\u043d\u0437\u0438\u044f" : "\u043e\u0444\u0444\u043b\u0430\u0439\u043d";
    }

    public static String fmtTime(long l) {
        long l2 = Math.max(0L, l) / 1000L;
        return l2 / 60L + ":" + (l2 % 60L < 10L ? "0" : "") + l2 % 60L;
    }

    static {
        TRACK_TITLE = "EVILSHIT";
        TRACK_ARTIST = "ARLEKIN 40 000";
        IC_GLOBE = "C";
        IC_PERSON = "A";
        IC_GEAR = "B";
        IC_POWER = "D";
        IC_TG = "E";
        IC_DISCORD = "F";
        IC_CODE = "H";
        TEXT = new Color(238, 242, 239);
        TEXT2 = new Color(223, 229, 225);
        TEXT3 = new Color(195, 204, 199);
        TEXT4 = new Color(168, 177, 172);
        MUTED = new Color(139, 150, 143);
        MUTED2 = new Color(120, 131, 125);
        MUTED3 = new Color(93, 104, 98);
        MUTED4 = new Color(76, 86, 81);
        INK = new Color(11, 18, 14);
        DANGER = new Color(224, 140, 140);
        BG = Celebression.id("mainmenu/bg-tower.png");
        MUSIC_PLAY = Celebression.id("music/play.png");
        MUSIC_PAUSE = Celebression.id("music/pause.png");
        TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");
        RU = Locale.forLanguageTag("ru");
        clockSec = -1L;
        clockHh = "";
        clockMm = "";
        clockDay = "";
        clockRest = "";
    }
}

