package ru.method.celebression.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_332;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.screen.SettingsSubScreen;
import ru.method.celebression.util.SettingsUiUtils;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class OptionsCategoryScreen
extends SettingsSubScreen {
    private static final float ahu = 1060.0f;
    private static final float ahv = 130.0f;
    private static final float ahw = 22.0f;
    private static final float ahx = 64.0f;
    private static final float ahy = 64.0f;
    private static final float ahz = 44.0f;
    private static final float ahA = 8.0f;
    private static final float ahB = 14.0f;
    private static final float ahC = 20.0f;
    private final String ahD;
    private final List<SettingsUiUtils> ahE;
    private final Animation ahF = MenuChrome.anim();
    private final Animation ahG = MenuChrome.anim();
    private final Animation ahH = MenuChrome.anim();
    private final float[] ahI = new float[4];
    private final float[] ahJ = new float[4];
    private final float[] ahK = new float[4];
    private final float[] ahL = new float[4];
    private final float[][] ahM;
    private final boolean[] ahN;
    private final float[] ahO;
    private final Animation[] ahP;
    private String Li = "";
    private boolean Lj;
    private long Lk;
    private float Ll;
    private final float[] ahQ;
    private float scroll;
    private float Lm;
    private long Ln;
    private int ahR = -1;
    private int ahS = -1;
    private int ahT = -1;
    private boolean ahU;
    private int ahV = -1;
    private float ahW;
    private float ahX;
    private float ahY;
    private float ahZ;
    private final List<float[]> aia = new ArrayList<float[]>();
    private final List<Integer> aib = new ArrayList<Integer>();
    private final float[] aic = new float[4];
    private float aid;
    private float aie;
    private float aif;
    private static final int aig = 8;

    public OptionsCategoryScreen(class_437 class_4372, String string, List<SettingsUiUtils> list) {
        super(class_4372, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u2014 " + string);
        int n;
        this.ahD = string;
        this.ahE = list;
        this.ahM = new float[list.size()][4];
        this.ahN = new boolean[list.size()];
        this.ahO = new float[list.size()];
        this.ahP = new Animation[list.size()];
        this.ahQ = new float[list.size()];
        for (n = 0; n < list.size(); ++n) {
            this.ahP[n] = MenuChrome.anim();
        }
        for (n = 0; n < list.size(); ++n) {
            SettingsUiUtils settingsUiUtils = list.get(n);
            if (settingsUiUtils.ahh == SettingsUiUtils.SettingsOption.ahr) {
                float f = this.ahO[n] = settingsUiUtils.ahn.getAsBoolean() ? 1.0f : 0.0f;
            }
            if (settingsUiUtils.ahh != SettingsUiUtils.SettingsOption.ahq) continue;
            this.ahQ[n] = (float)class_3532.method_15350((double)settingsUiUtils.ahl.getAsDouble(), (double)0.0, (double)1.0);
        }
    }

    private float kw() {
        int n = (this.ahE.size() + 1) / 2;
        float f = 40.0f + (float)n * 44.0f + (float)Math.max(0, n - 1) * 8.0f;
        float f2 = 64.0f + f + 64.0f;
        float f3 = (float)this.vh - 130.0f - 74.0f;
        return Math.min(f2, Math.max(172.0f, f3));
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
        this.a(class_3322, class_45872, f, f2 + 64.0f, f3, f4 - 64.0f - 64.0f, n, n2);
        this.b(class_45872, f, f2 + f4 - 64.0f, f3, n, n2);
        MenuPanelRenderer.p(class_45872, f, f2, f3, f4);
        if (this.ahT >= 0 || this.ahU) {
            this.c(class_3322, class_45872, n, n2);
        }
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 32.0f;
        float f5 = 36.0f;
        float f6 = f + 22.0f;
        float f7 = f4 - f5 / 2.0f + 6.0f;
        boolean bl = MenuChrome.hit(n, n2, f6, f7, f5, f5);
        float f8 = MenuChrome.upd(this.ahF, bl);
        MenuPanelRenderer.c(class_45872, f6, f7, f5, f5, f8, false);
        ClickGuiIcons.arrowLeft(class_45872, f6 + f5 / 2.0f, f7 + f5 / 2.0f, 16.0f, MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.TEXT2, f8));
        MenuChrome.set(this.ahI, f6, f7, f5, f5);
        float f9 = f6 + f5 + 16.0f;
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        float f10 = instance.getWidth("\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438");
        boolean bl2 = MenuChrome.hit(n, n2, f9, f4 - 9.0f, f10, 18.0f);
        Render2D.drawFont(class_45872, instance, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", f9, f4 - instance.getHeight() / 2.0f, bl2 ? MenuChrome.TEXT2 : MenuChrome.MUTED2);
        MenuChrome.set(this.ahL, f9, f4 - 9.0f, f10, 18.0f);
        Render2D.drawFont(class_45872, instance, "/", f9 += f10 + 8.0f, f4 - instance.getHeight() / 2.0f, MenuChrome.MUTED4);
        Instance instance2 = Fonts.GOLOS_SEMI.getFont(17.0f);
        Render2D.drawFont(class_45872, instance2, this.ahD, f9 += instance.getWidth("/") + 8.0f, f4 - instance2.getHeight() / 2.0f, MenuChrome.TEXT);
        float f11 = 250.0f;
        float f12 = 36.0f;
        float f13 = f + f3 - 22.0f - f11;
        float f14 = f4 - f12 / 2.0f;
        boolean bl3 = MenuChrome.hit(n, n2, f13, f14, f11, f12);
        float f15 = MenuChrome.upd(this.ahH, bl3 || this.Lj);
        MenuPanelRenderer.c(class_45872, f13, f14, f11, f12, f15, false);
        ClickGuiIcons.search(class_45872, f13 + 13.0f + 7.0f, f14 + f12 / 2.0f, 16.0f, MenuChrome.lerp(MenuChrome.MUTED3, MenuChrome.accent(), this.Lj ? 1.0f : 0.0f));
        Instance instance3 = Fonts.GOLOS.getFont(12.5f);
        float f16 = f13 + 13.0f + 14.0f + 10.0f;
        if (this.Li.isEmpty() && !this.Lj) {
            Render2D.drawFont(class_45872, instance3, "\u041f\u043e\u0438\u0441\u043a \u043f\u0430\u0440\u0430\u043c\u0435\u0442\u0440\u0430...", f16, f14 + f12 / 2.0f - instance3.getHeight() / 2.0f, MenuChrome.MUTED3);
        } else {
            Render2D.drawFont(class_45872, instance3, this.Li, f16, f14 + f12 / 2.0f - instance3.getHeight() / 2.0f, MenuChrome.TEXT2);
            if (this.Lj) {
                float f17 = f16 + instance3.getWidth(this.Li) + 1.0f;
                if (this.Ll <= 0.0f) {
                    this.Ll = f17;
                }
                this.Ll += (f17 - this.Ll) * 0.4f;
                float f18 = 0.35f + 0.65f * (0.5f + 0.5f * (float)Math.sin((double)System.currentTimeMillis() / 260.0));
                Color color = new Color(MenuChrome.TEXT2.getRed(), MenuChrome.TEXT2.getGreen(), MenuChrome.TEXT2.getBlue(), Math.round(255.0f * f18));
                Render2D.drawRoundedRect(class_45872, this.Ll, f14 + f12 / 2.0f - 6.0f, 1.0f, 12.0f, 0.5f, color);
            }
        }
        MenuChrome.set(this.ahK, f13, f14, f11, f12);
    }

    private void a(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        int n3;
        for (int i = 0; i < this.ahN.length; ++i) {
            this.ahN[i] = false;
        }
        this.ahT = this.ahU ? this.ahV : -1;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String string = this.Li.trim().toLowerCase(Locale.ROOT);
        for (n3 = 0; n3 < this.ahE.size(); ++n3) {
            if (!string.isEmpty() && !this.ahE.get((int)n3).ahg.toLowerCase(Locale.ROOT).contains(string)) continue;
            arrayList.add(n3);
        }
        n3 = (arrayList.size() + 1) / 2;
        float f5 = 40.0f + (float)n3 * 44.0f + (float)Math.max(0, n3 - 1) * 8.0f;
        float f6 = Math.max(0.0f, f5 - f4);
        long l = System.currentTimeMillis();
        float f7 = this.Ln == 0L ? 0.016f : Math.min(0.05f, (float)(l - this.Ln) / 1000.0f);
        this.Ln = l;
        this.Lm = class_3532.method_15363((float)this.Lm, (float)0.0f, (float)f6);
        this.scroll += (this.Lm - this.scroll) * (1.0f - (float)Math.exp(-13.0f * f7));
        if (Math.abs(this.Lm - this.scroll) < 0.4f) {
            this.scroll = this.Lm;
        }
        float f8 = (f3 - 44.0f - 14.0f) / 2.0f;
        long l2 = System.currentTimeMillis();
        Render2D.startScissor(class_3322, f, f2, f3, f4);
        for (int i = 0; i < arrayList.size(); ++i) {
            int n4 = (Integer)arrayList.get(i);
            int n5 = i % 2;
            int n6 = i / 2;
            float f9 = f + 22.0f + (float)n5 * (f8 + 14.0f);
            float f10 = f2 + 20.0f + (float)n6 * 52.0f - this.scroll;
            long l3 = Math.max(this.Rf, this.Lk);
            float f11 = class_3532.method_15363((float)((float)(l2 - l3 - (long)i * 16L) / 220.0f), (float)0.0f, (float)1.0f);
            float f12 = f10 + (1.0f - (f11 = 1.0f - (1.0f - f11) * (1.0f - f11))) * 12.0f;
            if (f12 + 44.0f < f2 || f12 > f2 + f4) continue;
            this.a(class_45872, n4, this.ahE.get(n4), f9, f12, f8, n, n2);
        }
        Render2D.stopScissor(class_3322);
        if (f6 > 0.5f) {
            float f13 = f4 - 8.0f;
            float f14 = Math.max(24.0f, f13 * (f4 / f5));
            float f15 = f2 + 4.0f + (f13 - f14) * (this.scroll / f6);
            Render2D.drawRoundedRect(class_45872, f + f3 - 6.0f, f15, 3.0f, f14, 1.5f, MenuChrome.accentA(0.5f));
        }
    }

    private void a(class_4587 class_45872, int n, SettingsUiUtils settingsUiUtils, float f, float f2, float f3, int n2, int n3) {
        boolean bl = MenuChrome.hit(n2, n3, f, f2, f3, 44.0f);
        float f4 = MenuChrome.upd(this.ahP[n], bl);
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, 44.0f, 10.0f, new Color(24, 30, 27, 179), new Color(15, 19, 17, 179));
        Render2D.drawBorder(class_45872, f, f2, f3, 44.0f, 10.0f, 1.0f, 1.4f, MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.22f), f4));
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        Render2D.drawFont(class_45872, instance, settingsUiUtils.ahg, f + 16.0f, f2 + 22.0f - instance.getHeight() / 2.0f, MenuChrome.TEXT2);
        float f5 = f2 + 22.0f;
        float f6 = f + f3 - 14.0f;
        switch (settingsUiUtils.ahh) {
            case ahr: {
                float f7 = 32.0f;
                float f8 = 18.0f;
                float f9 = f6 - f7;
                float f10 = f5 - f8 / 2.0f;
                float f11 = settingsUiUtils.ahn.getAsBoolean() ? 1.0f : 0.0f;
                int n4 = n;
                this.ahO[n4] = this.ahO[n4] + (f11 - this.ahO[n]) * 0.35f;
                if (Math.abs(f11 - this.ahO[n]) < 0.01f) {
                    this.ahO[n] = f11;
                }
                this.i(class_45872, f9, f10, f7, f8, this.ahO[n]);
                MenuChrome.set(this.ahM[n], f9, f10, f7, f8);
                this.ahN[n] = true;
                break;
            }
            case ahp: {
                Instance instance2 = Fonts.GOLOS.getFont(12.0f);
                String string = settingsUiUtils.ahi.get();
                boolean bl2 = settingsUiUtils.ahj != null;
                float f12 = 28.0f;
                float f13 = bl2 ? 12.0f : 0.0f;
                float f14 = 10.0f + instance2.getWidth(string) + (bl2 ? 7.0f + f13 : 0.0f) + 10.0f;
                float f15 = f6 - f14;
                float f16 = f5 - f12 / 2.0f;
                boolean bl3 = this.ahS == n;
                MenuPanelRenderer.c(class_45872, f15, f16, f14, f12, bl3 ? 1.0f : 0.0f, false);
                Render2D.drawFont(class_45872, instance2, string, f15 + 10.0f, f16 + f12 / 2.0f - instance2.getHeight() / 2.0f, bl2 ? MenuChrome.TEXT2 : MenuChrome.TEXT3);
                if (!bl2) break;
                ClickGuiIcons.chevronDown(class_45872, f15 + 10.0f + instance2.getWidth(string) + 7.0f + f13 / 2.0f, f16 + f12 / 2.0f, f13, bl3 ? MenuChrome.accent() : MenuChrome.MUTED3);
                MenuChrome.set(this.ahM[n], f15, f16, f14, f12);
                this.ahN[n] = true;
                if (!bl3) break;
                this.ahT = n;
                this.ahW = f15;
                this.ahX = f16;
                this.ahY = f14;
                break;
            }
            case ahs: {
                Instance instance3 = Fonts.GOLOS.getFont(12.0f);
                String string = settingsUiUtils.ahi == null ? "" : settingsUiUtils.ahi.get();
                float f17 = 28.0f;
                float f18 = 12.0f;
                float f19 = 10.0f + instance3.getWidth(string) + 7.0f + f18 + 10.0f;
                float f20 = f6 - f19;
                float f21 = f5 - f17 / 2.0f;
                MenuPanelRenderer.c(class_45872, f20, f21, f19, f17, bl ? 1.0f : 0.0f, false);
                Render2D.drawFont(class_45872, instance3, string, f20 + 10.0f, f21 + f17 / 2.0f - instance3.getHeight() / 2.0f, MenuChrome.TEXT2);
                ClickGuiIcons.arrowRight(class_45872, f20 + 10.0f + instance3.getWidth(string) + 7.0f + f18 / 2.0f, f21 + f17 / 2.0f, f18, bl ? MenuChrome.accent() : MenuChrome.MUTED3);
                MenuChrome.set(this.ahM[n], f20, f21, f19, f17);
                this.ahN[n] = true;
                break;
            }
            case ahq: {
                Instance instance4 = Fonts.GOLOS.getFont(11.5f);
                String string = settingsUiUtils.ahi.get();
                float f22 = Math.max(60.0f, instance4.getWidth(string));
                Render2D.drawFont(class_45872, instance4, string, f6 - instance4.getWidth(string), f5 - instance4.getHeight() / 2.0f, MenuChrome.TEXT3);
                float f23 = f6 - f22 - 12.0f;
                float f24 = 150.0f;
                float f25 = f23 - f24;
                if (this.ahR == n) {
                    long l = aiG.method_22683().method_4490();
                    if (GLFW.glfwGetMouseButton((long)l, (int)0) == 1) {
                        settingsUiUtils.ahm.accept(class_3532.method_15363((float)(((float)n2 - f25) / f24), (float)0.0f, (float)1.0f));
                    } else {
                        this.ahR = -1;
                    }
                }
                float f26 = (float)class_3532.method_15350((double)settingsUiUtils.ahl.getAsDouble(), (double)0.0, (double)1.0);
                int n5 = n;
                this.ahQ[n5] = this.ahQ[n5] + (f26 - this.ahQ[n]) * 0.4f;
                if (Math.abs(f26 - this.ahQ[n]) < 0.002f) {
                    this.ahQ[n] = f26;
                }
                float f27 = this.ahQ[n];
                float f28 = f5 - 2.0f;
                Render2D.drawRoundedRect(class_45872, f25, f28, f24, 4.0f, 2.0f, MenuChrome.white(0.09f));
                if (f27 > 0.001f) {
                    MenuChrome.glow(class_45872, f25, f28, f24 * f27, 4.0f, 2.0f, MenuChrome.accentA(0.5f), 0.0f, 3, 1.3f);
                    Render2D.drawRoundedRect(class_45872, f25, f28, f24 * f27, 4.0f, 2.0f, MenuChrome.accent());
                }
                float f29 = f25 + f24 * f27;
                Render2D.drawCircle(class_45872, f29, f5, 6.0f, new Color(0, 0, 0, 90));
                Render2D.drawCircle(class_45872, f29, f5, 6.0f, MenuChrome.TEXT);
                MenuChrome.set(this.ahM[n], f25 - 6.0f, f5 - 11.0f, f24 + 12.0f, 22.0f);
                this.ahN[n] = true;
            }
        }
    }

    private void ra() {
        if (this.ahS < 0) {
            return;
        }
        this.ahV = this.ahS;
        this.ahU = true;
        this.ahS = -1;
    }

    private void a(SettingsUiUtils settingsUiUtils) {
        float f;
        String string;
        this.aid = 0.0f;
        this.aie = 0.0f;
        if (settingsUiUtils.ahj == null || settingsUiUtils.ahi == null) {
            return;
        }
        List<String> list = settingsUiUtils.ahj.get();
        int n = list.indexOf(string = settingsUiUtils.ahi.get());
        if (n < 8) {
            return;
        }
        this.aid = f = ((float)n - 4.0f) * 26.0f;
        this.aie = f;
    }

    private void c(class_332 class_3322, class_4587 class_45872, int n, int n2) {
        float f;
        SettingsUiUtils settingsUiUtils = this.ahE.get(this.ahT);
        List<String> list = settingsUiUtils.ahj.get();
        float f2 = this.ahU ? 0.0f : 1.0f;
        this.ahZ += (f2 - this.ahZ) * 0.35f;
        if (Math.abs(f2 - this.ahZ) < 0.005f) {
            this.ahZ = f2;
        }
        if (this.ahU && this.ahZ <= 0.001f) {
            this.ahU = false;
            this.ahV = -1;
            this.aia.clear();
            this.aib.clear();
            return;
        }
        Instance instance = Fonts.GOLOS.getFont(12.0f);
        float f3 = 26.0f;
        float f4 = 4.0f;
        float f5 = 0.0f;
        for (String string : list) {
            f5 = Math.max(f5, instance.getWidth(string));
        }
        float f6 = Math.max(this.ahY, f5 + 24.0f);
        int n3 = Math.min(list.size(), 8);
        float f7 = (float)n3 * f3 + f4 * 2.0f;
        float f8 = (float)list.size() * f3;
        this.aif = Math.max(0.0f, f8 - (float)n3 * f3);
        this.aie = class_3532.method_15363((float)this.aie, (float)0.0f, (float)this.aif);
        this.aid += (this.aie - this.aid) * 0.35f;
        if (Math.abs(this.aie - this.aid) < 0.4f) {
            this.aid = this.aie;
        }
        float f9 = f7 * this.ahZ;
        float f10 = 28.0f;
        float f11 = this.ahX + f10 + 3.0f;
        float f12 = this.ahX - 3.0f - f7;
        float f13 = 130.0f + this.kw();
        float f14 = f11 + f7 > f13 - 10.0f && f12 > 194.0f ? f12 : f11;
        float f15 = this.ahW + this.ahY - f6;
        MenuChrome.set(this.aic, f15, f14, f6, f9);
        MenuChrome.glow(class_45872, f15, f14, f6, f9, 8.0f, new Color(0, 0, 0, 120), 2.0f, 5, 2.2f);
        Render2D.drawRoundedRect(class_45872, f15, f14, f6, f9, 8.0f, new Color(18, 24, 20, 250));
        Render2D.drawBorder(class_45872, f15, f14, f6, f9, 8.0f, 1.0f, 1.4f, MenuChrome.white(0.14f));
        this.aia.clear();
        this.aib.clear();
        if (this.ahZ < 0.55f) {
            return;
        }
        String string = settingsUiUtils.ahi.get();
        float f16 = f14 + f4;
        float f17 = (float)n3 * f3;
        Render2D.startScissor(class_3322, f15, f16, f6, Math.max(0.0f, Math.min(f17, f9 - f4 * 2.0f)));
        for (int i = 0; i < list.size(); ++i) {
            f = f16 + (float)i * f3 - this.aid;
            if (f + f3 < f16 || f > f16 + f17) continue;
            boolean bl = MenuChrome.hit(n, n2, f15 + f4, f, f6 - f4 * 2.0f, f3);
            boolean bl2 = list.get(i).equals(string);
            if (bl) {
                Render2D.drawRoundedRect(class_45872, f15 + f4, f, f6 - f4 * 2.0f, f3, 6.0f, MenuChrome.accentA(0.12f));
            } else if (bl2) {
                Render2D.drawRoundedRect(class_45872, f15 + f4, f, f6 - f4 * 2.0f, f3, 6.0f, MenuChrome.accentA(0.06f));
            }
            Color color = bl2 ? MenuChrome.accent() : (bl ? MenuChrome.TEXT : MenuChrome.TEXT2);
            Render2D.drawFont(class_45872, instance, list.get(i), f15 + 12.0f, f + f3 / 2.0f - instance.getHeight() / 2.0f, color);
            float f18 = Math.max(f, f16);
            float f19 = Math.min(f + f3, f16 + f17);
            if (f19 - f18 < 1.0f) continue;
            float[] fArray = new float[4];
            MenuChrome.set(fArray, f15 + f4, f18, f6 - f4 * 2.0f, f19 - f18);
            this.aia.add(fArray);
            this.aib.add(i);
        }
        Render2D.stopScissor(class_3322);
        if (this.ahZ > 0.95f && this.aif > 0.5f) {
            float f20 = f17 - 6.0f;
            f = Math.max(20.0f, f20 * (f17 / f8));
            float f21 = f16 + 3.0f + (f20 - f) * (this.aid / this.aif);
            Render2D.drawRoundedRect(class_45872, f15 + f6 - 6.0f, f21, 3.0f, f, 1.5f, MenuChrome.accentA(0.5f));
        }
    }

    private void i(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f4 / 2.0f, MenuChrome.white(0.1f));
        if (f5 > 0.01f) {
            Color color = MenuChrome.accent();
            int n = Math.round(255.0f * f5);
            Color color2 = new Color(Math.min(255, color.getRed() + 20), Math.min(255, color.getGreen() + 16), Math.min(255, color.getBlue() + 12), n);
            Color color3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
            MenuChrome.glow(class_45872, f, f2, f3, f4, f4 / 2.0f, MenuChrome.accentA(0.55f * f5), 0.0f, 3, 1.4f);
            Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, f4 / 2.0f, color2, color3);
            Render2D.drawRoundedRect(class_45872, f + 4.0f, f2 + 1.0f, f3 - 8.0f, 1.0f, 0.5f, new Color(255, 255, 255, Math.round(128.0f * f5)));
        }
        float f6 = f + 9.0f + f5 * 14.0f;
        float f7 = f2 + f4 / 2.0f;
        Render2D.drawCircle(class_45872, f6, f7, 6.0f, MenuChrome.lerp(MenuChrome.TEXT3, MenuChrome.INK, f5));
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
        float f10 = MenuChrome.upd(this.ahG, bl);
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
        MenuChrome.set(this.ahJ, f8, f9, f7, f6);
    }

    @Override
    protected boolean a(float f, float f2, int n) {
        int n2;
        if (n != 0) {
            return false;
        }
        if (this.ahS >= 0) {
            for (n2 = 0; n2 < this.aia.size(); ++n2) {
                if (!MenuChrome.in(this.aia.get(n2), f, f2)) continue;
                SettingsUiUtils settingsUiUtils = this.ahE.get(this.ahS);
                if (settingsUiUtils.ahk != null) {
                    settingsUiUtils.ahk.accept(this.aib.get(n2));
                }
                this.ra();
                return true;
            }
            n2 = this.ahS;
            this.ra();
            if (n2 < this.ahM.length && this.ahN[n2] && MenuChrome.in(this.ahM[n2], f, f2)) {
                return true;
            }
        }
        this.Lj = MenuChrome.in(this.ahK, f, f2);
        if (this.Lj) {
            return true;
        }
        if (MenuChrome.in(this.ahI, f, f2) || MenuChrome.in(this.ahL, f, f2)) {
            this.pJ();
            return true;
        }
        if (MenuChrome.in(this.ahJ, f, f2)) {
            OptionsCategoryScreen.aiG.field_1690.method_1640();
            this.pJ();
            return true;
        }
        for (n2 = 0; n2 < this.ahE.size(); ++n2) {
            if (!this.ahN[n2] || !MenuChrome.in(this.ahM[n2], f, f2)) continue;
            SettingsUiUtils settingsUiUtils = this.ahE.get(n2);
            switch (settingsUiUtils.ahh) {
                case ahr: 
                case ahs: {
                    settingsUiUtils.aho.run();
                    break;
                }
                case ahp: {
                    this.ahS = n2;
                    this.ahU = false;
                    this.ahV = -1;
                    this.ahZ = 0.0f;
                    this.a(settingsUiUtils);
                    break;
                }
                case ahq: {
                    this.ahR = n2;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected void b(float f, float f2, int n) {
        if (n == 0) {
            this.ahR = -1;
        }
    }

    @Override
    protected boolean a(float f, float f2, double d) {
        if (this.ahS >= 0 && MenuChrome.in(this.aic, f, f2)) {
            this.aie = class_3532.method_15363((float)(this.aie - (float)d * 26.0f * 2.0f), (float)0.0f, (float)this.aif);
            return true;
        }
        this.ra();
        this.Lm -= (float)d * 52.0f * 0.9f;
        return true;
    }

    @Override
    protected boolean c(int n, int n2, int n3) {
        if (this.Lj) {
            if (n == 259) {
                if (!this.Li.isEmpty()) {
                    this.Li = this.Li.substring(0, this.Li.length() - 1);
                    this.kA();
                }
                return true;
            }
            if (n == 257 || n == 335) {
                this.Lj = false;
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean a(char c, int n) {
        if (this.Lj && c >= ' ') {
            this.Li = this.Li + c;
            this.kA();
            return true;
        }
        return false;
    }

    private void kA() {
        this.Lk = System.currentTimeMillis();
        try {
            if (aiG.method_1483() == null) {
                return;
            }
            float f = 1.7f + (float)(Math.random() * 0.35);
            aiG.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)((class_3414)class_3417.field_15015.comp_349()), (float)f, (float)0.25f));
        }
        catch (Throwable throwable) {
        }
    }

    @Override
    protected void pJ() {
        OptionsCategoryScreen.aiG.field_1690.method_1640();
        super.pJ();
    }
}

