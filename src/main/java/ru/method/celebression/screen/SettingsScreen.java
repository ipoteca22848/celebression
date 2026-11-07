package ru.method.celebression.screen;

import java.awt.Color;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.screen.CreditsSettingsScreen;
import ru.method.celebression.screen.LanguageSettingsScreen;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.screen.OptionsCategoryScreen;
import ru.method.celebression.ui.PerformanceSettings;
import ru.method.celebression.screen.ResourcePackSettingsScreen;
import ru.method.celebression.screen.SettingsSubScreen;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class SettingsScreen
extends SettingsSubScreen {
    private static final float agL = 840.0f;
    private static final float agM = 150.0f;
    private static final float agN = 22.0f;
    private static final float agO = 68.0f;
    private static final float agP = 50.0f;
    private static final float agQ = 64.0f;
    private static final float agR = 20.0f;
    private static final float agS = 56.0f;
    private static final float agT = 10.0f;
    private static final float agU = 360.0f;
    private static final float agV = 542.0f;
    private static final int agW = 8;
    private static final String[] agX = new String[]{"\u0412\u043d\u0435\u0448\u043d\u0438\u0439 \u0432\u0438\u0434", "\u041c\u0443\u0437\u044b\u043a\u0430 \u0438 \u0437\u0432\u0443\u043a\u0438", "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u0433\u0440\u0430\u0444\u0438\u043a\u0438", "\u0423\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435", "\u042f\u0437\u044b\u043a", "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u0447\u0430\u0442\u0430", "\u041d\u0430\u0431\u043e\u0440\u044b \u0440\u0435\u0441\u0443\u0440\u0441\u043e\u0432", "\u0421\u043f\u0435\u0446\u0438\u0430\u043b\u044c\u043d\u044b\u0435 \u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e\u0441\u0442\u0438", "\u0421\u043e\u0431\u0438\u0440\u0430\u0435\u043c\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435", "\u0422\u0438\u0442\u0440\u044b \u0438 \u0430\u0442\u0440\u0438\u0431\u0443\u0446\u0438\u0438"};
    private final Animation agY = MenuChrome.anim();
    private final Animation agZ = MenuChrome.anim();
    private final float[] aha = new float[4];
    private final float[] ahb = new float[4];
    private final float[] ahc = new float[4];
    private final float[][] ahd = new float[10][4];
    private final Animation[] ahe = new Animation[10];
    private boolean ahf;

    public SettingsScreen(class_437 class_4372) {
        super(class_4372, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438");
        for (int i = 0; i < 10; ++i) {
            this.ahe[i] = MenuChrome.anim();
        }
    }

    @Override
    protected void a(class_332 class_3322, class_4587 class_45872, int n, int n2) {
        float f = this.av(840.0f);
        float f2 = 150.0f;
        float f3 = 840.0f;
        float f4 = 542.0f;
        MenuPanelRenderer.n(class_45872, f, f2, f3, f4);
        this.a(class_45872, f, f2, f3, n, n2);
        Render2D.drawRoundedRect(class_45872, f, f2 + 68.0f - 1.0f, f3, 1.0f, 0.0f, MenuChrome.white(0.07f));
        this.a(class_45872, f, f2 + 68.0f, f3, n);
        Render2D.drawRoundedRect(class_45872, f, f2 + 68.0f + 50.0f - 1.0f, f3, 1.0f, 0.0f, MenuChrome.white(0.07f));
        this.g(class_45872, f, f2 + 68.0f + 50.0f, f3, n, n2);
        this.b(class_45872, f, f2 + f4 - 64.0f, f3, n, n2);
        MenuPanelRenderer.p(class_45872, f, f2, f3, f4);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 34.0f;
        Instance instance = Fonts.GOLOS_SEMI.getFont(17.0f);
        Render2D.drawFont(class_45872, instance, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", f + 22.0f, f4 - instance.getHeight() / 2.0f, MenuChrome.TEXT);
        Instance instance2 = Fonts.UNBOUNDED.getFont(11.0f);
        String string = "\u041e\u043d\u043b\u0430\u0439\u043d";
        float f5 = 14.0f;
        float f6 = 36.0f;
        float f7 = 14.0f + f5 + 9.0f + instance2.getWidth(string) + 15.0f;
        float f8 = f + f3 - 22.0f - f7;
        float f9 = f4 - f6 / 2.0f;
        boolean bl = MenuChrome.hit(n, n2, f8, f9, f7, f6);
        float f10 = MenuChrome.upd(this.agY, bl);
        MenuChrome.drawSecondaryMaterial(class_45872, f8, f9, f7, f6, 8.0f, f10, false);
        ClickGuiIcons.globe(class_45872, f8 + 14.0f + f5 / 2.0f, f9 + f6 / 2.0f, f5, MenuChrome.MUTED);
        Render2D.drawFont(class_45872, instance2, string, f8 + 14.0f + f5 + 9.0f, f9 + f6 / 2.0f - instance2.getHeight() / 2.0f, MenuChrome.TEXT2);
        MenuChrome.set(this.aha, f8, f9, f7, f6);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, int n) {
        float f4;
        float f5 = f2 + 25.0f;
        Instance instance = Fonts.GOLOS.getFont(13.5f);
        String string = "\u041f\u043e\u043b\u0435 \u0437\u0440\u0435\u043d\u0438\u044f";
        Render2D.drawFont(class_45872, instance, string, f + 22.0f, f5 - instance.getHeight() / 2.0f, MenuChrome.TEXT2);
        Instance instance2 = Fonts.GOLOS.getFont(12.0f);
        float f6 = 118.0f;
        int n2 = (Integer)SettingsScreen.aiG.field_1690.method_41808().method_41753();
        String string2 = SettingsScreen.be(n2);
        Render2D.drawFont(class_45872, instance2, string2, f + f3 - 22.0f - f6 + (f6 - instance2.getWidth(string2)), f5 - instance2.getHeight() / 2.0f, MenuChrome.TEXT3);
        float f7 = f + 22.0f + instance.getWidth(string) + 18.0f;
        float f8 = f + f3 - 22.0f - f6 - 14.0f;
        float f9 = Math.max(20.0f, f8 - f7);
        if (this.ahf) {
            long l = aiG.method_22683().method_4490();
            if (GLFW.glfwGetMouseButton((long)l, (int)0) == 1) {
                f4 = class_3532.method_15363((float)(((float)n - f7) / f9), (float)0.0f, (float)1.0f);
                SettingsScreen.aiG.field_1690.method_41808().method_41748(30 + Math.round(f4 * 80.0f));
            } else {
                this.ahf = false;
            }
        }
        float f10 = class_3532.method_15363((float)((float)(n2 - 30) / 80.0f), (float)0.0f, (float)1.0f);
        float f11 = f5 - 2.0f;
        Render2D.drawRoundedRect(class_45872, f7, f11, f9, 4.0f, 2.0f, MenuChrome.white(0.09f));
        if (f10 > 0.001f) {
            MenuChrome.glow(class_45872, f7, f11, f9 * f10, 4.0f, 2.0f, MenuChrome.accentA(0.5f), 0.0f, 3, 1.3f);
            Render2D.drawRoundedRect(class_45872, f7, f11, f9 * f10, 4.0f, 2.0f, MenuChrome.accent());
        }
        f4 = f7 + f9 * f10;
        Render2D.drawCircle(class_45872, f4, f5, 6.0f, new Color(0, 0, 0, 90));
        Render2D.drawCircle(class_45872, f4, f5, 6.0f, MenuChrome.TEXT);
        MenuChrome.set(this.ahc, f7 - 6.0f, f5 - 11.0f, f9 + 12.0f, 22.0f);
    }

    private static String be(int n) {
        if (n >= 110) {
            return "\u041a\u0432\u0435\u0439\u043a \u041f\u0440\u043e \u00b7 " + n;
        }
        if (n == 70) {
            return "\u041d\u043e\u0440\u043c\u0430\u043b\u044c\u043d\u043e \u00b7 70";
        }
        if (n <= 30) {
            return "\u041c\u0438\u043d. \u00b7 " + n;
        }
        return String.valueOf(n);
    }

    private void g(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = (f3 - 44.0f - 10.0f) / 2.0f;
        float f5 = f2 + 20.0f;
        long l = System.currentTimeMillis();
        for (int i = 0; i < 10; ++i) {
            int n3 = i % 2;
            int n4 = i / 2;
            float f6 = f + 22.0f + (float)n3 * (f4 + 10.0f);
            float f7 = f5 + (float)n4 * 66.0f;
            boolean bl = i == 8;
            boolean bl2 = !bl && MenuChrome.hit(n, n2, f6, f7, f4, 56.0f);
            float f8 = MenuChrome.upd(this.ahe[i], bl2);
            float f9 = class_3532.method_15363((float)((float)(l - this.Rf - (long)i * 22L) / 240.0f), (float)0.0f, (float)1.0f);
            f9 = 1.0f - (1.0f - f9) * (1.0f - f9);
            float f10 = (bl ? 0.45f : 1.0f) * f9;
            float f11 = f7 + (1.0f - f9) * 14.0f - f8 * 2.0f;
            Render2D.drawRoundedVerticalGradient(class_45872, f6, f11, f4, 56.0f, 10.0f, SettingsScreen.b(new Color(24, 30, 27, 179), f10), SettingsScreen.b(new Color(15, 19, 17, 179), f10));
            Render2D.drawRoundedRect(class_45872, f6 + 10.0f, f11 + 1.0f, f4 - 20.0f, 1.0f, 0.5f, MenuChrome.white(0.04f * f10));
            Color color = bl ? MenuChrome.white(0.08f * f10) : MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.22f), f8);
            Render2D.drawBorder(class_45872, f6, f11, f4, 56.0f, 10.0f, 1.0f, 1.4f, color);
            float f12 = 36.0f;
            float f13 = f6 + 14.0f;
            float f14 = f11 + (56.0f - f12) / 2.0f;
            Render2D.drawRoundedRect(class_45872, f13, f14, f12, f12, 8.0f, MenuChrome.white(0.06f * f10));
            Render2D.drawBorder(class_45872, f13, f14, f12, f12, 8.0f, 1.0f, 1.2f, MenuChrome.white(0.1f * f10));
            Color color2 = bl ? MenuChrome.MUTED3 : MenuChrome.lerp(MenuChrome.TEXT3, MenuChrome.accent(), f8);
            this.a(class_45872, i, f13 + f12 / 2.0f, f14 + f12 / 2.0f, 20.0f, SettingsScreen.b(color2, f10));
            Instance instance = Fonts.GOLOS.getFont(13.5f);
            Render2D.drawFont(class_45872, instance, agX[i], f13 + f12 + 12.0f, f11 + 28.0f - instance.getHeight() / 2.0f, SettingsScreen.b(MenuChrome.TEXT2, f10));
            Instance instance2 = Fonts.GOLOS.getFont(15.0f);
            Render2D.drawFont(class_45872, instance2, "\u203a", f6 + f4 - 18.0f, f11 + 28.0f - instance2.getHeight() / 2.0f, SettingsScreen.b(MenuChrome.MUTED3, f10));
            MenuChrome.set(this.ahd[i], f6, f7, f4, 56.0f);
        }
    }

    private void a(class_4587 class_45872, int n, float f, float f2, float f3, Color color) {
        switch (n) {
            case 0: {
                ClickGuiIcons.user(class_45872, f, f2, f3, color);
                break;
            }
            case 1: {
                ClickGuiIcons.musicNote(class_45872, f, f2, f3, color);
                break;
            }
            case 2: {
                ClickGuiIcons.monitor(class_45872, f, f2, f3, color);
                break;
            }
            case 3: {
                ClickGuiIcons.keyboard(class_45872, f, f2, f3, color);
                break;
            }
            case 4: {
                ClickGuiIcons.globe(class_45872, f, f2, f3, color);
                break;
            }
            case 5: {
                ClickGuiIcons.chat(class_45872, f, f2, f3, color);
                break;
            }
            case 6: {
                ClickGuiIcons.layers(class_45872, f, f2, f3, color);
                break;
            }
            case 7: {
                ClickGuiIcons.accessibility(class_45872, f, f2, f3, color);
                break;
            }
            case 8: {
                ClickGuiIcons.database(class_45872, f, f2, f3, color);
                break;
            }
            default: {
                ClickGuiIcons.info(class_45872, f, f2, f3, color);
            }
        }
    }

    private void b(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 32.0f;
        Instance instance = Fonts.UNBOUNDED.getFont(11.0f);
        String string = "\u0413\u043e\u0442\u043e\u0432\u043e";
        float f5 = 16.0f;
        float f6 = 44.0f;
        float f7 = 24.0f + f5 + 9.0f + instance.getWidth(string) + 24.0f;
        float f8 = f + f3 - 22.0f - f7;
        float f9 = f4 - f6 / 2.0f - 9.0f;
        boolean bl = MenuChrome.hit(n, n2, f8, f9, f7, f6);
        float f10 = MenuChrome.upd(this.agZ, bl);
        float f11 = f9 - f10;
        Color color = MenuChrome.accent();
        Render2D.drawRoundedRect(class_45872, f8 + 4.0f, f11 + f6 - 6.0f, f7 - 8.0f, 12.0f, 8.0f, MenuChrome.accentA(0.24f + 0.14f * f10));
        int n3 = Math.min(255, Math.round((float)color.getRed() * (1.0f + 0.07f * f10)));
        int n4 = Math.min(255, Math.round((float)color.getGreen() * (1.0f + 0.07f * f10)));
        int n5 = Math.min(255, Math.round((float)color.getBlue() * (1.0f + 0.07f * f10)));
        Render2D.drawRoundedRect(class_45872, f8, f11, f7, f6, 8.0f, new Color(n3, n4, n5));
        Render2D.drawRoundedRect(class_45872, f8 + 8.0f, f11 + 1.0f, f7 - 16.0f, 1.0f, 0.5f, MenuChrome.white(0.35f));
        ClickGuiIcons.check(class_45872, f8 + 24.0f + f5 / 2.0f, f11 + f6 / 2.0f, f5, MenuChrome.INK);
        Render2D.drawFont(class_45872, instance, string, f8 + 24.0f + f5 + 9.0f, f11 + f6 / 2.0f - instance.getHeight() / 2.0f, MenuChrome.INK);
        MenuChrome.set(this.ahb, f8, f9, f7, f6);
    }

    private static Color b(Color color, float f) {
        if (f >= 0.999f) {
            return color;
        }
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((float)color.getAlpha() * class_3532.method_15363((float)f, (float)0.0f, (float)1.0f)));
    }

    @Override
    protected boolean a(float f, float f2, int n) {
        if (n != 0) {
            return false;
        }
        if (MenuChrome.in(this.ahc, f, f2)) {
            this.ahf = true;
            return true;
        }
        if (MenuChrome.in(this.ahb, f, f2)) {
            SettingsScreen.aiG.field_1690.method_1640();
            this.pJ();
            return true;
        }
        if (MenuChrome.in(this.aha, f, f2)) {
            this.qX();
            return true;
        }
        for (int i = 0; i < 10; ++i) {
            if (i == 8 || !MenuChrome.in(this.ahd[i], f, f2)) continue;
            this.bf(i);
            return true;
        }
        return false;
    }

    @Override
    protected void b(float f, float f2, int n) {
        if (n == 0) {
            this.ahf = false;
        }
    }

    private void bf(int n) {
        switch (n) {
            case 0: {
                this.d(new OptionsCategoryScreen(this, "\u0412\u043d\u0435\u0448\u043d\u0438\u0439 \u0432\u0438\u0434", PerformanceSettings.qO()));
                break;
            }
            case 1: {
                this.d(new OptionsCategoryScreen(this, "\u041c\u0443\u0437\u044b\u043a\u0430 \u0438 \u0437\u0432\u0443\u043a\u0438", PerformanceSettings.qP()));
                break;
            }
            case 2: {
                this.d(new OptionsCategoryScreen(this, "\u0413\u0440\u0430\u0444\u0438\u043a\u0430", PerformanceSettings.qN()));
                break;
            }
            case 3: {
                this.d(new OptionsCategoryScreen(this, "\u0423\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435", PerformanceSettings.qQ()));
                break;
            }
            case 4: {
                this.d(new LanguageSettingsScreen(this));
                break;
            }
            case 5: {
                this.d(new OptionsCategoryScreen(this, "\u0427\u0430\u0442", PerformanceSettings.qR()));
                break;
            }
            case 6: {
                this.d(new ResourcePackSettingsScreen(this));
                break;
            }
            case 7: {
                this.d(new OptionsCategoryScreen(this, "\u0421\u043f\u0435\u0446. \u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e\u0441\u0442\u0438", PerformanceSettings.qS()));
                break;
            }
            case 9: {
                this.d(new CreditsSettingsScreen(this));
                break;
            }
        }
    }

    private void qX() {
        MenuChrome.keepMusicAcrossSwap();
        this.d(new OptionsCategoryScreen(this, "\u041e\u043d\u043b\u0430\u0439\u043d", PerformanceSettings.qT()));
    }
}
