package ru.method.celebression.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.class_2561;
import net.minecraft.class_304;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_3675;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.screen.SettingsSubScreen;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class ControlsSettingsScreen
extends SettingsSubScreen {
    private static final float aen = 1060.0f;
    private static final float aeo = 130.0f;
    private static final float aep = 22.0f;
    private static final float aeq = 64.0f;
    private static final float aer = 64.0f;
    private static final float aes = 44.0f;
    private static final float aet = 8.0f;
    private static final float aeu = 20.0f;
    private static final float aev = 34.0f;
    private static final float aew = 120.0f;
    private static final float aex = 28.0f;
    private static final float aey = 28.0f;
    private final Animation aez = MenuChrome.anim();
    private final Animation aeA = MenuChrome.anim();
    private final Animation aeB = MenuChrome.anim();
    private final Animation aeC = MenuChrome.anim();
    private final float[] aeD = new float[4];
    private final float[] aeE = new float[4];
    private final float[] aeF = new float[4];
    private final float[] aeG = new float[4];
    private final List<KeyBindingRow> aeH = new ArrayList<KeyBindingRow>();
    private final Set<class_304> aeI = new HashSet<class_304>();
    private Animation[] aeJ;
    private Animation[] aeK;
    private float[][] aeL;
    private float[][] aeM;
    private boolean[] aeN;
    private boolean[] aeO;
    private String Li = "";
    private boolean Lj;
    private float scroll;
    private float Lm;
    private long Ln;
    private class_304 aeP;

    public ControlsSettingsScreen(class_437 class_4372) {
        super(class_4372, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u2014 \u041a\u043b\u0430\u0432\u0438\u0448\u0438");
        this.qv();
    }

    private void qv() {
        int n;
        ArrayList<class_304> arrayList = new ArrayList<class_304>(List.of(ControlsSettingsScreen.aiG.field_1690.field_1839));
        arrayList.sort(class_304::method_1430);
        String string = null;
        for (class_304 class_3042 : arrayList) {
            if (!class_3042.method_1423().equals(string)) {
                string = class_3042.method_1423();
                this.aeH.add(new KeyBindingRow(null, class_2561.method_43471((String)string).getString()));
            }
            this.aeH.add(new KeyBindingRow(class_3042, class_2561.method_43471((String)class_3042.method_1431()).getString()));
        }
        int n2 = this.aeH.size();
        for (n = 0; n < n2; ++n) {
            this.aeH.get((int)n).index = n;
        }
        this.aeJ = new Animation[n2];
        this.aeK = new Animation[n2];
        this.aeL = new float[n2][4];
        this.aeM = new float[n2][4];
        this.aeN = new boolean[n2];
        this.aeO = new boolean[n2];
        for (n = 0; n < n2; ++n) {
            this.aeJ[n] = MenuChrome.anim();
            this.aeK[n] = MenuChrome.anim();
        }
        this.qw();
    }

    private void qw() {
        this.aeI.clear();
        class_304[] class_304Array = ControlsSettingsScreen.aiG.field_1690.field_1839;
        for (int i = 0; i < class_304Array.length; ++i) {
            if (class_304Array[i].method_1415()) continue;
            for (int j = i + 1; j < class_304Array.length; ++j) {
                if (!class_304Array[i].method_1435(class_304Array[j])) continue;
                this.aeI.add(class_304Array[i]);
                this.aeI.add(class_304Array[j]);
            }
        }
    }

    private float kw() {
        float f = 40.0f + this.qx();
        float f2 = 64.0f + f + 64.0f;
        float f3 = (float)this.vh - 130.0f - 74.0f;
        return Math.min(f2, Math.max(172.0f, f3));
    }

    private float qx() {
        float f = 0.0f;
        for (KeyBindingRow keyBindingRow : this.ku()) {
            f += (keyBindingRow.aeQ == null ? 34.0f : 44.0f) + 8.0f;
        }
        return Math.max(0.0f, f - 8.0f);
    }

    private List<KeyBindingRow> ku() {
        String string = this.Li.trim().toLowerCase(Locale.ROOT);
        if (string.isEmpty()) {
            return this.aeH;
        }
        ArrayList<KeyBindingRow> arrayList = new ArrayList<KeyBindingRow>();
        for (int i = 0; i < this.aeH.size(); ++i) {
            KeyBindingRow keyBindingRow = this.aeH.get(i);
            if (keyBindingRow.aeQ == null) {
                boolean bl = false;
                for (int j = i + 1; j < this.aeH.size() && this.aeH.get((int)j).aeQ != null; ++j) {
                    if (!this.aeH.get((int)j).aeR.toLowerCase(Locale.ROOT).contains(string)) continue;
                    bl = true;
                    break;
                }
                if (!bl) continue;
                arrayList.add(keyBindingRow);
                continue;
            }
            if (!keyBindingRow.aeR.toLowerCase(Locale.ROOT).contains(string)) continue;
            arrayList.add(keyBindingRow);
        }
        return arrayList;
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
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 32.0f;
        float f5 = 36.0f;
        float f6 = f + 22.0f;
        float f7 = f4 - f5 / 2.0f + 6.0f;
        boolean bl = MenuChrome.hit(n, n2, f6, f7, f5, f5);
        float f8 = MenuChrome.upd(this.aez, bl);
        MenuPanelRenderer.c(class_45872, f6, f7, f5, f5, f8, false);
        ClickGuiIcons.arrowLeft(class_45872, f6 + f5 / 2.0f, f7 + f5 / 2.0f, 16.0f, MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.TEXT2, f8));
        MenuChrome.set(this.aeD, f6, f7, f5, f5);
        float f9 = f6 + f5 + 16.0f;
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        Render2D.drawFont(class_45872, instance, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", f9, f4 - instance.getHeight() / 2.0f, MenuChrome.MUTED2);
        Render2D.drawFont(class_45872, instance, "/", f9 += instance.getWidth("\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438") + 8.0f, f4 - instance.getHeight() / 2.0f, MenuChrome.MUTED4);
        Instance instance2 = Fonts.GOLOS_SEMI.getFont(17.0f);
        Render2D.drawFont(class_45872, instance2, "\u041a\u043b\u0430\u0432\u0438\u0448\u0438", f9 += instance.getWidth("/") + 8.0f, f4 - instance2.getHeight() / 2.0f, MenuChrome.TEXT);
        float f10 = 250.0f;
        float f11 = 36.0f;
        float f12 = f + f3 - 22.0f - f10;
        float f13 = f4 - f11 / 2.0f;
        boolean bl2 = MenuChrome.hit(n, n2, f12, f13, f10, f11);
        float f14 = MenuChrome.upd(this.aeB, bl2 || this.Lj);
        MenuPanelRenderer.c(class_45872, f12, f13, f10, f11, f14, false);
        ClickGuiIcons.search(class_45872, f12 + 13.0f + 7.0f, f13 + f11 / 2.0f, 16.0f, MenuChrome.lerp(MenuChrome.MUTED3, MenuChrome.accent(), this.Lj ? 1.0f : 0.0f));
        Instance instance3 = Fonts.GOLOS.getFont(12.5f);
        float f15 = f12 + 13.0f + 14.0f + 10.0f;
        if (this.Li.isEmpty() && !this.Lj) {
            Render2D.drawFont(class_45872, instance3, "\u041f\u043e\u0438\u0441\u043a \u043a\u043b\u0430\u0432\u0438\u0448\u0438...", f15, f13 + f11 / 2.0f - instance3.getHeight() / 2.0f, MenuChrome.MUTED3);
        } else {
            Render2D.drawFont(class_45872, instance3, this.Li, f15, f13 + f11 / 2.0f - instance3.getHeight() / 2.0f, MenuChrome.TEXT2);
            if (this.Lj && System.currentTimeMillis() / 500L % 2L == 0L) {
                Render2D.drawRoundedRect(class_45872, f15 + instance3.getWidth(this.Li) + 1.0f, f13 + f11 / 2.0f - 6.0f, 1.0f, 12.0f, 0.5f, MenuChrome.TEXT2);
            }
        }
        MenuChrome.set(this.aeF, f12, f13, f10, f11);
    }

    private void a(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        float f5;
        for (int i = 0; i < this.aeN.length; ++i) {
            this.aeN[i] = false;
            this.aeO[i] = false;
        }
        List<KeyBindingRow> list = this.ku();
        float f6 = 40.0f + this.qx();
        float f7 = Math.max(0.0f, f6 - f4);
        long l = System.currentTimeMillis();
        float f8 = this.Ln == 0L ? 0.016f : Math.min(0.05f, (float)(l - this.Ln) / 1000.0f);
        this.Ln = l;
        this.Lm = class_3532.method_15363((float)this.Lm, (float)0.0f, (float)f7);
        this.scroll += (this.Lm - this.scroll) * (1.0f - (float)Math.exp(-13.0f * f8));
        if (Math.abs(this.Lm - this.scroll) < 0.4f) {
            this.scroll = this.Lm;
        }
        float f9 = f3 - 44.0f;
        float f10 = f2 + 20.0f - this.scroll;
        Render2D.startScissor(class_3322, f, f2, f3, f4);
        for (int i = 0; i < list.size(); ++i) {
            KeyBindingRow keyBindingRow = list.get(i);
            f5 = keyBindingRow.aeQ == null ? 34.0f : 44.0f;
            float f11 = class_3532.method_15363((float)((float)(l - this.Rf - (long)i * 12L) / 220.0f), (float)0.0f, (float)1.0f);
            float f12 = f10 + (1.0f - (f11 = 1.0f - (1.0f - f11) * (1.0f - f11))) * 12.0f;
            if (f12 + f5 >= f2 && f12 <= f2 + f4) {
                if (keyBindingRow.aeQ == null) {
                    this.a(class_45872, keyBindingRow, f + 22.0f, f12, f9);
                } else {
                    this.a(class_45872, keyBindingRow.index, keyBindingRow, f + 22.0f, f12, f9, n, n2);
                }
            }
            f10 += f5 + 8.0f;
        }
        Render2D.stopScissor(class_3322);
        if (f7 > 0.5f) {
            float f13 = f4 - 8.0f;
            float f14 = Math.max(24.0f, f13 * (f4 / f6));
            f5 = f2 + 4.0f + (f13 - f14) * (this.scroll / f7);
            Render2D.drawRoundedRect(class_45872, f + f3 - 6.0f, f5, 3.0f, f14, 1.5f, MenuChrome.accentA(0.5f));
        }
    }

    private void a(class_4587 class_45872, KeyBindingRow keyBindingRow, float f, float f2, float f3) {
        Instance instance = Fonts.GOLOS_SEMI.getFont(12.5f);
        float f4 = f2 + 17.0f - instance.getHeight() / 2.0f;
        Render2D.drawFont(class_45872, instance, keyBindingRow.aeR, f + 2.0f, f4, MenuChrome.MUTED);
        float f5 = f + 2.0f + instance.getWidth(keyBindingRow.aeR) + 12.0f;
        Render2D.drawRoundedRect(class_45872, f5, f2 + 17.0f, f3 - (f5 - f) - 2.0f, 1.0f, 0.0f, MenuChrome.white(0.07f));
    }

    private void a(class_4587 class_45872, int n, KeyBindingRow keyBindingRow, float f, float f2, float f3, int n2, int n3) {
        class_304 class_3042 = keyBindingRow.aeQ;
        boolean bl = MenuChrome.hit(n2, n3, f, f2, f3, 44.0f);
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, 44.0f, 10.0f, new Color(24, 30, 27, 179), new Color(15, 19, 17, 179));
        Render2D.drawBorder(class_45872, f, f2, f3, 44.0f, 10.0f, 1.0f, 1.4f, MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.22f), bl ? 1.0f : 0.0f));
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        Render2D.drawFont(class_45872, instance, keyBindingRow.aeR, f + 16.0f, f2 + 22.0f - instance.getHeight() / 2.0f, MenuChrome.TEXT2);
        float f4 = f2 + 22.0f;
        float f5 = f + f3 - 14.0f;
        boolean bl2 = this.aeP == class_3042;
        boolean bl3 = !bl2 && this.aeI.contains(class_3042);
        Instance instance2 = Fonts.GOLOS.getFont(12.0f);
        String string = bl2 ? "> ... <" : class_3042.method_16007().getString();
        float f6 = Math.max(120.0f, instance2.getWidth(string) + 24.0f);
        float f7 = f5 - f6;
        float f8 = f4 - 14.0f;
        boolean bl4 = MenuChrome.hit(n2, n3, f7, f8, f6, 28.0f);
        float f9 = MenuChrome.upd(this.aeJ[n], bl4 || bl2);
        MenuPanelRenderer.c(class_45872, f7, f8, f6, 28.0f, f9, bl3);
        Color color = bl2 ? MenuChrome.accent() : (bl3 ? MenuChrome.DANGER : MenuChrome.TEXT2);
        Render2D.drawFont(class_45872, instance2, string, f7 + f6 / 2.0f - instance2.getWidth(string) / 2.0f, f8 + 14.0f - instance2.getHeight() / 2.0f, color);
        MenuChrome.set(this.aeL[n], f7, f8, f6, 28.0f);
        this.aeN[n] = true;
        float f10 = f7 - 10.0f;
        if (bl3) {
            ClickGuiIcons.info(class_45872, f10 - 7.0f, f4, 14.0f, MenuChrome.DANGER);
            f10 -= 24.0f;
        }
        if (!class_3042.method_1427()) {
            float f11 = f10 - 28.0f;
            float f12 = f4 - 14.0f;
            boolean bl5 = MenuChrome.hit(n2, n3, f11, f12, 28.0f, 28.0f);
            float f13 = MenuChrome.upd(this.aeK[n], bl5);
            MenuChrome.drawSecondaryMaterial(class_45872, f11, f12, 28.0f, 28.0f, 7.0f, f13, false);
            ClickGuiIcons.refresh(class_45872, f11 + 14.0f, f4, 13.0f, MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.TEXT2, f13));
            MenuChrome.set(this.aeM[n], f11, f12, 28.0f, 28.0f);
            this.aeO[n] = true;
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
        float f10 = MenuChrome.upd(this.aeA, bl);
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
        MenuChrome.set(this.aeE, f8, f9, f7, f6);
        Instance instance2 = Fonts.UNBOUNDED.getFont(11.0f);
        String string2 = "\u0421\u0431\u0440\u043e\u0441\u0438\u0442\u044c \u0432\u0441\u0451";
        float f12 = 44.0f + instance2.getWidth(string2) + 20.0f;
        float f13 = f8 - 10.0f - f12;
        float f14 = f4 - f6 / 2.0f - 9.0f;
        boolean bl2 = MenuChrome.hit(n, n2, f13, f14, f12, f6);
        float f15 = MenuChrome.upd(this.aeC, bl2);
        MenuChrome.drawSecondaryMaterial(class_45872, f13, f14 - f15, f12, f6, 8.0f, f15, false);
        ClickGuiIcons.refresh(class_45872, f13 + 20.0f + 7.5f, f14 - f15 + f6 / 2.0f, 15.0f, MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.TEXT2, f15));
        Render2D.drawFont(class_45872, instance2, string2, f13 + 20.0f + 15.0f + 9.0f, f14 - f15 + f6 / 2.0f - instance2.getHeight() / 2.0f, MenuChrome.lerp(MenuChrome.TEXT3, MenuChrome.TEXT, f15));
        MenuChrome.set(this.aeG, f13, f14, f12, f6);
    }

    private void a(class_3675.class_306 class_3062) {
        this.aeP.method_1422(class_3062);
        this.aeP = null;
        class_304.method_1426();
        this.qw();
        ControlsSettingsScreen.aiG.field_1690.method_1640();
    }

    @Override
    protected boolean a(float f, float f2, int n) {
        if (this.aeP != null) {
            this.a(class_3675.class_307.field_1672.method_1447(n));
            return true;
        }
        if (n != 0) {
            return false;
        }
        this.Lj = MenuChrome.in(this.aeF, f, f2);
        if (this.Lj) {
            return true;
        }
        if (MenuChrome.in(this.aeD, f, f2)) {
            this.pJ();
            return true;
        }
        if (MenuChrome.in(this.aeE, f, f2)) {
            ControlsSettingsScreen.aiG.field_1690.method_1640();
            this.pJ();
            return true;
        }
        if (MenuChrome.in(this.aeG, f, f2)) {
            for (class_304 class_3042 : ControlsSettingsScreen.aiG.field_1690.field_1839) {
                class_3042.method_1422(class_3042.method_1429());
            }
            class_304.method_1426();
            this.qw();
            ControlsSettingsScreen.aiG.field_1690.method_1640();
            return true;
        }
        if (f2 < 194.0f || f2 > 130.0f + this.kw() - 64.0f) {
            return false;
        }
        for (int i = 0; i < this.aeH.size(); ++i) {
            class_304 class_3043 = this.aeH.get((int)i).aeQ;
            if (class_3043 == null) continue;
            if (this.aeO[i] && MenuChrome.in(this.aeM[i], f, f2)) {
                class_3043.method_1422(class_3043.method_1429());
                class_304.method_1426();
                this.qw();
                ControlsSettingsScreen.aiG.field_1690.method_1640();
                return true;
            }
            if (!this.aeN[i] || !MenuChrome.in(this.aeL[i], f, f2)) continue;
            this.aeP = class_3043;
            return true;
        }
        return false;
    }

    @Override
    protected boolean a(float f, float f2, double d) {
        this.Lm -= (float)d * 52.0f * 0.9f;
        return true;
    }

    @Override
    protected boolean c(int n, int n2, int n3) {
        if (this.aeP != null) {
            if (n == 256) {
                this.aeP = null;
                return true;
            }
            this.a(class_3675.method_15985((int)n, (int)n2));
            return true;
        }
        if (this.Lj) {
            if (n == 259) {
                if (!this.Li.isEmpty()) {
                    this.Li = this.Li.substring(0, this.Li.length() - 1);
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
            return true;
        }
        return false;
    }

    @Override
    protected void pJ() {
        ControlsSettingsScreen.aiG.field_1690.method_1640();
        super.pJ();
    }

    static final class KeyBindingRow {
        final class_304 aeQ;
        final String aeR;
        int index;

        KeyBindingRow(class_304 class_3042, String string) {
            this.aeQ = class_3042;
            this.aeR = string;
        }
    }
}

