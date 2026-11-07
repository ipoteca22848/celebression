package ru.method.celebression.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_1076;
import net.minecraft.class_1077;
import net.minecraft.class_332;
import net.minecraft.class_3532;
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

public final class LanguageSettingsScreen
extends SettingsSubScreen {
    private static final float aeS = 1060.0f;
    private static final float aeT = 130.0f;
    private static final float aeU = 22.0f;
    private static final float aeV = 64.0f;
    private static final float aeW = 64.0f;
    private static final float aeX = 44.0f;
    private static final float aeY = 8.0f;
    private static final float aeZ = 20.0f;
    private static final String afa = "\u042f\u0437\u044b\u043a";
    private final List<LanguageEntry> afb = new ArrayList<LanguageEntry>();
    private final Animation afc = MenuChrome.anim();
    private final Animation afd = MenuChrome.anim();
    private final Animation afe = MenuChrome.anim();
    private final float[] aff = new float[4];
    private final float[] afg = new float[4];
    private final float[] afh = new float[4];
    private final float[] afi = new float[4];
    private float afj;
    private String Li = "";
    private boolean Lj;
    private float scroll;
    private float Lm;
    private long Ln;
    private final List<float[]> afk = new ArrayList<float[]>();
    private final List<Integer> afl = new ArrayList<Integer>();

    public LanguageSettingsScreen(class_437 class_4372) {
        super(class_4372, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u2014 \u042f\u0437\u044b\u043a");
        class_1076 class_10762 = aiG.method_1526();
        for (Map.Entry entry : class_10762.method_4665().entrySet()) {
            this.afb.add(new LanguageEntry((String)entry.getKey(), ((class_1077)entry.getValue()).method_48303().getString()));
        }
        this.afb.sort(Comparator.comparing(LanguageEntry::qA, String.CASE_INSENSITIVE_ORDER));
        this.afj = this.qy() ? 1.0f : 0.0f;
    }

    private boolean qy() {
        return (Boolean)LanguageSettingsScreen.aiG.field_1690.method_42437().method_41753();
    }

    private float kw() {
        float f = 40.0f + (float)(this.afb.size() + 1) * 44.0f + (float)this.afb.size() * 8.0f + 4.0f;
        float f2 = 64.0f + f + 64.0f;
        float f3 = (float)this.vh - 130.0f - 74.0f;
        return Math.min(f2, Math.max(216.0f, f3));
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
        float f5 = f2 + 64.0f;
        float f6 = f4 - 64.0f - 64.0f;
        this.d(class_45872, f + 22.0f, f5 + 20.0f, f3 - 44.0f, n, n2);
        float f7 = f5 + 20.0f + 44.0f + 12.0f;
        float f8 = f5 + f6 - f7 - 6.0f;
        this.d(class_3322, class_45872, f, f7, f3, f8, n, n2);
        this.b(class_45872, f, f2 + f4 - 64.0f, f3, n, n2);
        MenuPanelRenderer.p(class_45872, f, f2, f3, f4);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 32.0f;
        float f5 = 36.0f;
        float f6 = f + 22.0f;
        float f7 = f4 - f5 / 2.0f + 6.0f;
        boolean bl = MenuChrome.hit(n, n2, f6, f7, f5, f5);
        float f8 = MenuChrome.upd(this.afc, bl);
        MenuPanelRenderer.c(class_45872, f6, f7, f5, f5, f8, false);
        ClickGuiIcons.arrowLeft(class_45872, f6 + f5 / 2.0f, f7 + f5 / 2.0f, 16.0f, MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.TEXT2, f8));
        MenuChrome.set(this.aff, f6, f7, f5, f5);
        float f9 = f6 + f5 + 16.0f;
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        Render2D.drawFont(class_45872, instance, "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", f9, f4 - instance.getHeight() / 2.0f, MenuChrome.MUTED2);
        Render2D.drawFont(class_45872, instance, "/", f9 += instance.getWidth("\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438") + 8.0f, f4 - instance.getHeight() / 2.0f, MenuChrome.MUTED4);
        Instance instance2 = Fonts.GOLOS_SEMI.getFont(17.0f);
        Render2D.drawFont(class_45872, instance2, "\u042f\u0437\u044b\u043a", f9 += instance.getWidth("/") + 8.0f, f4 - instance2.getHeight() / 2.0f, MenuChrome.TEXT);
        float f10 = 250.0f;
        float f11 = 36.0f;
        float f12 = f + f3 - 22.0f - f10;
        float f13 = f4 - f11 / 2.0f;
        boolean bl2 = MenuChrome.hit(n, n2, f12, f13, f10, f11);
        float f14 = MenuChrome.upd(this.afe, bl2 || this.Lj);
        MenuPanelRenderer.c(class_45872, f12, f13, f10, f11, f14, false);
        ClickGuiIcons.search(class_45872, f12 + 13.0f + 7.0f, f13 + f11 / 2.0f, 16.0f, MenuChrome.lerp(MenuChrome.MUTED3, MenuChrome.accent(), this.Lj ? 1.0f : 0.0f));
        Instance instance3 = Fonts.GOLOS.getFont(12.5f);
        float f15 = f12 + 13.0f + 14.0f + 10.0f;
        if (this.Li.isEmpty() && !this.Lj) {
            Render2D.drawFont(class_45872, instance3, "\u041f\u043e\u0438\u0441\u043a \u044f\u0437\u044b\u043a\u0430...", f15, f13 + f11 / 2.0f - instance3.getHeight() / 2.0f, MenuChrome.MUTED3);
        } else {
            Render2D.drawFont(class_45872, instance3, this.Li, f15, f13 + f11 / 2.0f - instance3.getHeight() / 2.0f, MenuChrome.TEXT2);
            if (this.Lj && System.currentTimeMillis() / 500L % 2L == 0L) {
                Render2D.drawRoundedRect(class_45872, f15 + instance3.getWidth(this.Li) + 1.0f, f13 + f11 / 2.0f - 6.0f, 1.0f, 12.0f, 0.5f, MenuChrome.TEXT2);
            }
        }
        MenuChrome.set(this.afh, f12, f13, f10, f11);
    }

    private void d(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        boolean bl = MenuChrome.hit(n, n2, f, f2, f3, 44.0f);
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, 44.0f, 10.0f, new Color(24, 30, 27, 179), new Color(15, 19, 17, 179));
        Render2D.drawBorder(class_45872, f, f2, f3, 44.0f, 10.0f, 1.0f, 1.4f, MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.22f), bl ? 1.0f : 0.0f));
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        Render2D.drawFont(class_45872, instance, "\u041f\u0440\u0438\u043d\u0443\u0434\u0438\u0442\u0435\u043b\u044c\u043d\u043e \u044e\u043d\u0438\u043a\u043e\u0434", f + 16.0f, f2 + 22.0f - instance.getHeight() / 2.0f, MenuChrome.TEXT2);
        float f4 = f2 + 22.0f;
        float f5 = f + f3 - 14.0f;
        float f6 = 32.0f;
        float f7 = 18.0f;
        float f8 = f5 - f6;
        float f9 = f4 - f7 / 2.0f;
        float f10 = this.qy() ? 1.0f : 0.0f;
        this.afj += (f10 - this.afj) * 0.35f;
        if (Math.abs(f10 - this.afj) < 0.01f) {
            this.afj = f10;
        }
        this.i(class_45872, f8, f9, f6, f7, this.afj);
        MenuChrome.set(this.afi, f8, f9, f6, f7);
    }

    private void d(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        float f5;
        this.afk.clear();
        this.afl.clear();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String string = this.Li.trim().toLowerCase(Locale.ROOT);
        for (int i = 0; i < this.afb.size(); ++i) {
            LanguageEntry languageEntry = this.afb.get(i);
            if (!string.isEmpty() && !languageEntry.qA().toLowerCase(Locale.ROOT).contains(string) && !languageEntry.qz().contains(string)) continue;
            arrayList.add(i);
        }
        float f6 = (float)arrayList.size() * 44.0f + (float)Math.max(0, arrayList.size() - 1) * 8.0f;
        float f7 = Math.max(0.0f, f6 - f4);
        long l = System.currentTimeMillis();
        float f8 = this.Ln == 0L ? 0.016f : Math.min(0.05f, (float)(l - this.Ln) / 1000.0f);
        this.Ln = l;
        this.Lm = class_3532.method_15363((float)this.Lm, (float)0.0f, (float)f7);
        this.scroll += (this.Lm - this.scroll) * (1.0f - (float)Math.exp(-13.0f * f8));
        if (Math.abs(this.Lm - this.scroll) < 0.4f) {
            this.scroll = this.Lm;
        }
        String string2 = aiG.method_1526().method_4669();
        float f9 = f + 22.0f;
        float f10 = f3 - 44.0f;
        Render2D.startScissor(class_3322, f, f2, f3, f4);
        for (int i = 0; i < arrayList.size(); ++i) {
            int n3 = (Integer)arrayList.get(i);
            f5 = f2 + (float)i * 52.0f - this.scroll;
            float f11 = class_3532.method_15363((float)((float)(l - this.Rf - (long)i * 12L) / 220.0f), (float)0.0f, (float)1.0f);
            float f12 = f5 + (1.0f - (f11 = 1.0f - (1.0f - f11) * (1.0f - f11))) * 12.0f;
            if (f12 + 44.0f < f2 || f12 > f2 + f4) continue;
            this.a(class_45872, this.afb.get(n3), f9, f12, f10, string2, n, n2);
            float f13 = Math.max(f12, f2);
            float f14 = Math.min(f12 + 44.0f, f2 + f4);
            if (f14 - f13 < 1.0f) continue;
            float[] fArray = new float[4];
            MenuChrome.set(fArray, f9, f13, f10, f14 - f13);
            this.afk.add(fArray);
            this.afl.add(n3);
        }
        Render2D.stopScissor(class_3322);
        if (f7 > 0.5f) {
            float f15 = f4 - 8.0f;
            float f16 = Math.max(24.0f, f15 * (f4 / f6));
            f5 = f2 + 4.0f + (f15 - f16) * (this.scroll / f7);
            Render2D.drawRoundedRect(class_45872, f + f3 - 6.0f, f5, 3.0f, f16, 1.5f, MenuChrome.accentA(0.5f));
        }
    }

    private void a(class_4587 class_45872, LanguageEntry languageEntry, float f, float f2, float f3, String string, int n, int n2) {
        boolean bl = languageEntry.qz().equals(string);
        boolean bl2 = MenuChrome.hit(n, n2, f, f2, f3, 44.0f);
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, 44.0f, 10.0f, new Color(24, 30, 27, 179), new Color(15, 19, 17, 179));
        if (bl) {
            Render2D.drawRoundedRect(class_45872, f, f2, f3, 44.0f, 10.0f, MenuChrome.accentA(0.07f));
        }
        Color color = bl ? MenuChrome.accentA(0.35f) : MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.22f), bl2 ? 1.0f : 0.0f);
        Render2D.drawBorder(class_45872, f, f2, f3, 44.0f, 10.0f, 1.0f, 1.4f, color);
        if (bl) {
            Render2D.drawRoundedRect(class_45872, f + 1.0f, f2 + 10.0f, 3.0f, 24.0f, 1.5f, MenuChrome.accent());
        }
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        Color color2 = bl ? MenuChrome.TEXT : MenuChrome.TEXT2;
        Render2D.drawFont(class_45872, instance, languageEntry.qA(), f + 16.0f, f2 + 22.0f - instance.getHeight() / 2.0f, color2);
        float f4 = f2 + 22.0f;
        float f5 = f + f3 - 14.0f;
        if (bl) {
            ClickGuiIcons.check(class_45872, f5 - 7.0f, f4, 14.0f, MenuChrome.accent());
            f5 -= 24.0f;
        }
        Instance instance2 = Fonts.GOLOS.getFont(11.5f);
        Render2D.drawFont(class_45872, instance2, languageEntry.qz(), f5 - instance2.getWidth(languageEntry.qz()), f4 - instance2.getHeight() / 2.0f, MenuChrome.MUTED3);
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
        float f10 = MenuChrome.upd(this.afd, bl);
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
        MenuChrome.set(this.afg, f8, f9, f7, f6);
    }

    private void apply(String string) {
        class_1076 class_10762 = aiG.method_1526();
        if (string.equals(class_10762.method_4669())) {
            return;
        }
        class_10762.method_4667(string);
        LanguageSettingsScreen.aiG.field_1690.field_1883 = string;
        aiG.method_1521();
        LanguageSettingsScreen.aiG.field_1690.method_1640();
    }

    @Override
    protected boolean a(float f, float f2, int n) {
        if (n != 0) {
            return false;
        }
        this.Lj = MenuChrome.in(this.afh, f, f2);
        if (this.Lj) {
            return true;
        }
        if (MenuChrome.in(this.aff, f, f2)) {
            this.pJ();
            return true;
        }
        if (MenuChrome.in(this.afg, f, f2)) {
            LanguageSettingsScreen.aiG.field_1690.method_1640();
            this.pJ();
            return true;
        }
        if (MenuChrome.in(this.afi, f, f2)) {
            LanguageSettingsScreen.aiG.field_1690.method_42437().method_41748(!this.qy());
            LanguageSettingsScreen.aiG.field_1690.method_1640();
            return true;
        }
        for (int i = 0; i < this.afk.size(); ++i) {
            if (!MenuChrome.in(this.afk.get(i), f, f2)) continue;
            this.apply(this.afb.get(this.afl.get(i)).qz());
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
        LanguageSettingsScreen.aiG.field_1690.method_1640();
        super.pJ();
    }

    static final class LanguageEntry {
        private final String afm;
        private final String afn;

        LanguageEntry(String string, String string2) {
            this.afm = string;
            this.afn = string2;
        }

        @Override
        public final String toString() {
            return "LanguageEntry[" + "code=" + this.afm + ", " + "display=" + this.afn + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.afm, this.afn);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof LanguageEntry other)) return false;
            return java.util.Objects.deepEquals(this.afm, other.afm)
                && java.util.Objects.deepEquals(this.afn, other.afn);
        }

        public String qz() {
            return this.afm;
        }

        public String qA() {
            return this.afn;
        }
    }
}
