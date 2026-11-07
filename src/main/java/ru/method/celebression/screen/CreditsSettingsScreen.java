package ru.method.celebression.screen;

import java.awt.Color;
import java.net.URI;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_407;
import net.minecraft.class_437;
import net.minecraft.class_445;
import net.minecraft.class_4587;
import net.minecraft.class_8216;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.screen.SettingsSubScreen;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class CreditsSettingsScreen
extends SettingsSubScreen {
    private static final float adX = 1060.0f;
    private static final float adY = 130.0f;
    private static final float adZ = 22.0f;
    private static final float aea = 64.0f;
    private static final float aeb = 64.0f;
    private static final float aec = 44.0f;
    private static final float aed = 8.0f;
    private static final float aee = 20.0f;
    private static final String aef = "\u0422\u0438\u0442\u0440\u044b \u0438 \u0430\u0442\u0440\u0438\u0431\u0443\u0446\u0438\u0438";
    private static final String[] aeg = new String[]{"\u0422\u0438\u0442\u0440\u044b", "\u0410\u0442\u0440\u0438\u0431\u0443\u0446\u0438\u044f", "\u041b\u0438\u0446\u0435\u043d\u0437\u0438\u0438"};
    private final Animation aeh = MenuChrome.anim();
    private final Animation aei = MenuChrome.anim();
    private final Animation[] aej = new Animation[]{MenuChrome.anim(), MenuChrome.anim(), MenuChrome.anim()};
    private final float[] aek = new float[4];
    private final float[] ael = new float[4];
    private final float[][] aem = new float[aeg.length][4];

    public CreditsSettingsScreen(class_437 class_4372) {
        super(class_4372, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u2014 \u0422\u0438\u0442\u0440\u044b \u0438 \u0430\u0442\u0440\u0438\u0431\u0443\u0446\u0438\u0438");
    }

    private float kw() {
        return 104.0f + (float)aeg.length * 44.0f + (float)(aeg.length - 1) * 8.0f + 64.0f;
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
        this.c(class_45872, f, f2 + 64.0f, f3, n, n2);
        this.b(class_45872, f, f2 + f4 - 64.0f, f3, n, n2);
        MenuPanelRenderer.p(class_45872, f, f2, f3, f4);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 32.0f;
        float f5 = 36.0f;
        float f6 = f + 22.0f;
        float f7 = f4 - f5 / 2.0f + 6.0f;
        boolean bl = MenuChrome.hit(n, n2, f6, f7, f5, f5);
        float f8 = MenuChrome.upd(this.aeh, bl);
        MenuPanelRenderer.c(class_45872, f6, f7, f5, f5, f8, false);
        ClickGuiIcons.arrowLeft(class_45872, f6 + f5 / 2.0f, f7 + f5 / 2.0f, 16.0f, MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.TEXT2, f8));
        MenuChrome.set(this.aek, f6, f7, f5, f5);
        float f9 = f6 + f5 + 16.0f;
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        Render2D.drawFont(class_45872, instance, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", f9, f4 - instance.getHeight() / 2.0f, MenuChrome.MUTED2);
        Render2D.drawFont(class_45872, instance, "/", f9 += instance.getWidth("\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438") + 8.0f, f4 - instance.getHeight() / 2.0f, MenuChrome.MUTED4);
        Instance instance2 = Fonts.GOLOS_SEMI.getFont(17.0f);
        Render2D.drawFont(class_45872, instance2, "\u0422\u0438\u0442\u0440\u044b \u0438 \u0430\u0442\u0440\u0438\u0431\u0443\u0446\u0438\u0438", f9 += instance.getWidth("/") + 8.0f, f4 - instance2.getHeight() / 2.0f, MenuChrome.TEXT);
    }

    private void c(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        long l = System.currentTimeMillis();
        float f4 = f3 - 44.0f;
        float f5 = f + 22.0f;
        for (int i = 0; i < aeg.length; ++i) {
            float f6 = f2 + 20.0f + (float)i * 52.0f;
            boolean bl = MenuChrome.hit(n, n2, f5, f6, f4, 44.0f);
            float f7 = MenuChrome.upd(this.aej[i], bl);
            float f8 = class_3532.method_15363((float)((float)(l - this.Rf - (long)i * 22L) / 240.0f), (float)0.0f, (float)1.0f);
            f8 = 1.0f - (1.0f - f8) * (1.0f - f8);
            float f9 = f6 + (1.0f - f8) * 12.0f;
            Render2D.drawRoundedVerticalGradient(class_45872, f5, f9, f4, 44.0f, 10.0f, CreditsSettingsScreen.b(new Color(24, 30, 27, 179), f8), CreditsSettingsScreen.b(new Color(15, 19, 17, 179), f8));
            Render2D.drawBorder(class_45872, f5, f9, f4, 44.0f, 10.0f, 1.0f, 1.4f, MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.22f), f7));
            Instance instance = Fonts.GOLOS.getFont(13.0f);
            Render2D.drawFont(class_45872, instance, aeg[i], f5 + 16.0f, f9 + 22.0f - instance.getHeight() / 2.0f, CreditsSettingsScreen.b(MenuChrome.TEXT2, f8));
            Instance instance2 = Fonts.GOLOS.getFont(15.0f);
            Render2D.drawFont(class_45872, instance2, "\u203a", f5 + f4 - 18.0f, f9 + 22.0f - instance2.getHeight() / 2.0f, CreditsSettingsScreen.b(MenuChrome.lerp(MenuChrome.MUTED3, MenuChrome.TEXT3, f7), f8));
            MenuChrome.set(this.aem[i], f5, f6, f4, 44.0f);
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
        float f10 = MenuChrome.upd(this.aei, bl);
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
        MenuChrome.set(this.ael, f8, f9, f7, f6);
    }

    @Override
    protected boolean a(float f, float f2, int n) {
        if (n != 0) {
            return false;
        }
        if (MenuChrome.in(this.aek, f, f2)) {
            this.pJ();
            return true;
        }
        if (MenuChrome.in(this.ael, f, f2)) {
            this.pJ();
            return true;
        }
        for (int i = 0; i < this.aem.length; ++i) {
            if (!MenuChrome.in(this.aem[i], f, f2)) continue;
            this.ay(i);
            return true;
        }
        return false;
    }

    private void ay(int n) {
        if (n == 0) {
            aiG.method_1507((class_437)new class_445(false, () -> aiG.method_1507((class_437)this)));
        } else if (n == 1) {
            class_407.method_61033((class_437)this, (URI)class_8216.field_43119);
        } else if (n == 2) {
            class_407.method_61033((class_437)this, (URI)class_8216.field_43120);
        }
    }

    private static Color b(Color color, float f) {
        if (f >= 0.999f) {
            return color;
        }
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((float)color.getAlpha() * class_3532.method_15363((float)f, (float)0.0f, (float)1.0f)));
    }
}

