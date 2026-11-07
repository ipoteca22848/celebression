package ru.method.celebression.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.minecraft.class_1657;
import net.minecraft.class_2561;
import net.minecraft.class_2588;
import net.minecraft.class_332;
import net.minecraft.class_339;
import net.minecraft.class_3532;
import net.minecraft.class_364;
import net.minecraft.class_4264;
import net.minecraft.class_433;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_642;
import net.minecraft.class_7417;
import ru.method.celebression.Celebression;
import ru.method.celebression.screen.AccountManagerScreen;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.service.IasIntegration;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.server.ServerDetector;
import ru.method.celebression.screen.SettingsSubScreen;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class PauseMenuScreen
extends SettingsSubScreen {
    private static final float afo = 440.0f;
    private static final float afp = 22.0f;
    private static final float afq = 56.0f;
    private static final float afr = 44.0f;
    private static final float afs = 8.0f;
    private static final float aft = 18.0f;
    private static final float afu = 20.0f;
    private static final float afv = 6.0f;
    private static final float afw = 640.0f;
    private final List<MenuAction> afx = new ArrayList<MenuAction>();
    private String afy = "\u041e\u0442\u043a\u043b\u044e\u0447\u0438\u0442\u044c\u0441\u044f";
    private Runnable afz;
    private final Animation afA = MenuChrome.anim();
    private final Animation afB = MenuChrome.anim();
    private final float[] afC = new float[4];
    private final float[] afD = new float[4];
    private final List<float[]> afE = new ArrayList<float[]>();
    private final List<Animation> afF = new ArrayList<Animation>();
    private float scroll;
    private float Lm;
    private float QO;
    private final float[] afG = new float[4];

    public PauseMenuScreen() {
        super(null, "\u041c\u0435\u043d\u044e");
        this.qC();
        this.qB();
    }

    private void qB() {
        this.v(this.cB("options"), this.cB("advancements"));
        this.v(this.cB("ias.switch"), this.cB("stats"));
    }

    private int cB(String string) {
        for (int i = 0; i < this.afx.size(); ++i) {
            String string2 = this.afx.get(i).cH();
            if (string2 == null || !string2.contains(string)) continue;
            return i;
        }
        return -1;
    }

    private void v(int n, int n2) {
        if (n < 0 || n2 < 0 || n == n2) {
            return;
        }
        Collections.swap(this.afx, n, n2);
    }

    private void qC() {
        try {
            class_433 class_4332 = new class_433(true);
            class_4332.method_25423(aiG, aiG.method_22683().method_4486(), aiG.method_22683().method_4502());
            for (class_364 class_3642 : class_4332.method_25396()) {
                String string;
                if (!(class_3642 instanceof class_339)) continue;
                class_339 class_3392 = (class_339)class_3642;
                if (!(class_3642 instanceof class_4264)) continue;
                class_4264 class_42642 = (class_4264)class_3642;
                if (!class_3392.field_22764 || "menu.returnToGame".equals(string = PauseMenuScreen.g(class_3392.method_25369()))) continue;
                String string2 = class_3392.method_25369().getString().trim();
                if ("menu.disconnect".equals(string) || "menu.returnToMenu".equals(string)) {
                    if (!string2.isEmpty()) {
                        this.afy = string2;
                    }
                    this.afz = () -> ((class_4264)class_42642).method_25306();
                    continue;
                }
                if (string2.isEmpty()) {
                    string2 = "\u041c\u043e\u0434";
                }
                this.afx.add(new MenuAction(string2, string, () -> ((class_4264)class_42642).method_25306(), class_3392.field_22763));
            }
        }
        catch (Throwable throwable) {
        }
        if (IasIntegration.kD()) {
            this.afx.add(new MenuAction("\u0410\u043a\u043a\u0430\u0443\u043d\u0442\u044b", "ias.switch", () -> aiG.method_1507((class_437)new AccountManagerScreen(this)), true));
        }
        while (this.afF.size() < this.afx.size()) {
            this.afF.add(MenuChrome.anim());
        }
    }

    private static String g(class_2561 class_25612) {
        String string;
        class_7417 class_74172;
        if (class_25612 != null && (class_74172 = class_25612.method_10851()) instanceof class_2588) {
            class_2588 class_25882 = (class_2588)class_74172;
            string = class_25882.method_11022();
        } else {
            string = "";
        }
        return string;
    }

    @Override
    protected String kt() {
        return "\u0432\u0435\u0440\u043d\u0443\u0442\u044c\u0441\u044f \u043a \u0438\u0433\u0440\u0435";
    }

    private int qD() {
        return (this.afx.size() + 1) / 2;
    }

    private float qE() {
        int n = this.qD();
        return n == 0 ? 0.0f : (float)n * 44.0f + (float)(n - 1) * 8.0f;
    }

    private float kw() {
        float f = 126.0f + this.qE() + 12.0f + 1.0f + 8.0f + 44.0f + 20.0f;
        return Math.min(f, 640.0f);
    }

    @Override
    protected void a(class_332 class_3322, class_4587 class_45872, int n, int n2) {
        float f = 440.0f;
        float f2 = this.kw();
        float f3 = this.av(f);
        float f4 = (float)this.vh / 2.0f - f2 * 0.53f;
        MenuPanelRenderer.n(class_45872, f3, f4, f, f2);
        this.k(class_45872, f3, f4, f);
        Render2D.drawRoundedRect(class_45872, f3, f4 + 56.0f - 1.0f, f, 1.0f, 0.0f, MenuChrome.white(0.07f));
        float f5 = f4 + 56.0f + 18.0f;
        this.e(class_45872, f3 + 22.0f, f5, f - 44.0f, n, n2);
        float f6 = f2 - ((f5 += 52.0f) - f4) - 85.0f;
        this.e(class_3322, class_45872, f3, f5, f, f6, n, n2);
        f5 += f6;
        Render2D.drawRoundedRect(class_45872, f3 + 22.0f, f5 += 6.0f, f - 44.0f, 1.0f, 0.0f, MenuChrome.white(0.07f));
        this.f(class_45872, f3 + 22.0f, f5 += 9.0f, f - 44.0f, n, n2);
        MenuPanelRenderer.p(class_45872, f3, f4, f, f2);
    }

    private void k(class_4587 class_45872, float f, float f2, float f3) {
        float f4 = f2 + 28.0f;
        Instance instance = Fonts.GOLOS_SEMI.getFont(17.0f);
        Render2D.drawFont(class_45872, instance, "\u041c\u0435\u043d\u044e", f + 22.0f, f4 - instance.getHeight() / 2.0f, MenuChrome.TEXT);
        boolean bl = aiG.method_1542();
        String string = this.ac(bl);
        Instance instance2 = Fonts.UNBOUNDED.getFont(9.5f);
        Instance instance3 = Fonts.PLEXMONO.getFont(10.0f);
        String string2 = bl ? "" : ServerDetector.t((class_1657)PauseMenuScreen.aiG.field_1724) + " \u043c\u0441";
        float f5 = f + f3 - 22.0f;
        float f6 = f5 - (string2.isEmpty() ? 0.0f : instance3.getWidth(string2));
        if (!string2.isEmpty()) {
            Render2D.drawFont(class_45872, instance3, string2, f6, f4 - instance3.getHeight() / 2.0f, MenuChrome.MUTED3);
            f6 -= 8.0f;
        }
        float f7 = f + 22.0f + instance.getWidth("\u041c\u0435\u043d\u044e");
        float f8 = bl ? 0.0f : 14.0f;
        string = PauseMenuScreen.e(instance2, string, Math.max(0.0f, f6 - f8 - (f7 + 12.0f)));
        float f9 = f6 - instance2.getWidth(string);
        Render2D.drawFont(class_45872, instance2, string, f9, f4 - instance2.getHeight() / 2.0f, MenuChrome.TEXT3);
        if (!bl) {
            float f10 = f9 - 8.0f - 3.0f;
            MenuChrome.glow(class_45872, f10 - 2.0f, f4 - 5.0f, 10.0f, 10.0f, 5.0f, MenuChrome.accentA(0.5f), 0.0f, 3, 1.3f);
            Render2D.drawCircle(class_45872, f10 + 3.0f, f4, 3.0f, MenuChrome.accent());
        }
    }

    private static String e(Instance instance, String string, float f) {
        int n;
        if (string == null || string.isEmpty()) {
            return "";
        }
        if (f <= 0.0f) {
            return "";
        }
        if (instance.getWidth(string) <= f) {
            return string;
        }
        float f2 = instance.getWidth("\u2026");
        for (n = string.length(); n > 0 && instance.getWidth(string.substring(0, n)) + f2 > f; --n) {
        }
        return string.substring(0, n) + "\u2026";
    }

    private static String cC(String string) {
        String string2 = string == null ? "" : string.trim();
        string2 = string2.replaceAll("\\s*\\((?:[^)]*erid[^)]*|\\d{1,2}\\+[^)]*)\\)\\s*$", "").trim();
        return string2.isEmpty() ? string : string2;
    }

    private String ac(boolean bl) {
        try {
            if (bl && aiG.method_1576() != null) {
                return aiG.method_1576().method_27728().method_150();
            }
            class_642 class_6422 = aiG.method_1558();
            if (class_6422 != null && class_6422.field_3752 != null && !class_6422.field_3752.isBlank()) {
                return PauseMenuScreen.cC(class_6422.field_3752);
            }
            return class_6422 != null ? class_6422.field_3761 : "\u0421\u0435\u0440\u0432\u0435\u0440";
        }
        catch (Throwable throwable) {
            return "\u0421\u0435\u0440\u0432\u0435\u0440";
        }
    }

    private void e(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        boolean bl = MenuChrome.hit(n, n2, f, f2, f3, 44.0f);
        float f4 = MenuChrome.upd(this.afA, bl);
        float f5 = f2 - f4;
        Color color = MenuChrome.accent();
        Render2D.drawRoundedRect(class_45872, f + 4.0f, f5 + 44.0f - 6.0f, f3 - 8.0f, 12.0f, 8.0f, MenuChrome.accentA(0.24f + 0.14f * f4));
        int n3 = Math.min(255, Math.round((float)color.getRed() * (1.0f + 0.07f * f4)));
        int n4 = Math.min(255, Math.round((float)color.getGreen() * (1.0f + 0.07f * f4)));
        int n5 = Math.min(255, Math.round((float)color.getBlue() * (1.0f + 0.07f * f4)));
        Render2D.drawRoundedRect(class_45872, f, f5, f3, 44.0f, 8.0f, new Color(n3, n4, n5));
        Render2D.drawRoundedRect(class_45872, f + 8.0f, f5 + 1.0f, f3 - 16.0f, 1.0f, 0.5f, MenuChrome.white(0.35f));
        Instance instance = Fonts.UNBOUNDED.getFont(11.0f);
        String string = "\u0412\u0435\u0440\u043d\u0443\u0442\u044c\u0441\u044f \u043a \u0438\u0433\u0440\u0435";
        float f6 = 14.0f;
        float f7 = f6 + 9.0f + instance.getWidth(string);
        float f8 = f + (f3 - f7) / 2.0f;
        ClickGuiIcons.play(class_45872, f8 + f6 / 2.0f, f5 + 22.0f, f6, MenuChrome.INK);
        Render2D.drawFont(class_45872, instance, string, f8 + f6 + 9.0f, f5 + 22.0f - instance.getHeight() / 2.0f, MenuChrome.INK);
        MenuChrome.set(this.afC, f, f2, f3, 44.0f);
    }

    private void e(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        boolean bl;
        this.afE.clear();
        float f5 = f + 22.0f;
        float f6 = f3 - 44.0f;
        float f7 = (f6 - 8.0f) / 2.0f;
        this.QO = Math.max(0.0f, this.qE() - f4);
        this.Lm = class_3532.method_15363((float)this.Lm, (float)0.0f, (float)this.QO);
        this.scroll += (this.Lm - this.scroll) * 0.35f;
        if (Math.abs(this.Lm - this.scroll) < 0.4f) {
            this.scroll = this.Lm;
        }
        MenuChrome.set(this.afG, f5, f2, f6, f4);
        boolean bl2 = bl = this.QO > 0.5f;
        if (bl) {
            Render2D.startScissor(class_3322, f5, f2 - 1.0f, f6 + 2.0f, f4 + 2.0f);
        }
        for (int i = 0; i < this.afx.size(); ++i) {
            MenuAction menuAction = this.afx.get(i);
            int n3 = i % 2;
            int n4 = i / 2;
            boolean bl3 = i == this.afx.size() - 1 && this.afx.size() % 2 == 1;
            float f8 = bl3 ? f6 : f7;
            float f9 = bl3 ? f5 : f5 + (float)n3 * (f7 + 8.0f);
            float f10 = f2 + (float)n4 * 52.0f - this.scroll;
            if (f10 + 44.0f < f2 || f10 > f2 + f4) continue;
            boolean bl4 = menuAction.active() && MenuChrome.hit(n, n2, f9, Math.max(f10, f2), f8, Math.min(f10 + 44.0f, f2 + f4) - Math.max(f10, f2));
            float f11 = MenuChrome.upd(this.afF.get(i), bl4);
            Render2D.drawRoundedRect(class_45872, f9, f10, f8, 44.0f, 8.0f, MenuChrome.white(menuAction.active() ? 0.04f : 0.02f));
            Render2D.drawBorder(class_45872, f9, f10, f8, 44.0f, 8.0f, 1.0f, 1.2f, MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.24f), f11));
            Color color = menuAction.active() ? MenuChrome.MUTED : MenuChrome.MUTED4;
            this.a(class_45872, menuAction, f9 + 15.0f + 7.5f, f10 + 22.0f, 15.0f, color);
            Instance instance = Fonts.UNBOUNDED.getFont(10.0f);
            float f12 = f9 + 15.0f + 15.0f + 10.0f;
            float f13 = f8 - (f12 - f9) - 12.0f;
            this.a(class_45872, instance, menuAction, f12, f10 + 22.0f - instance.getHeight() / 2.0f, f13);
            float f14 = Math.max(f10, f2);
            float f15 = Math.min(f10 + 44.0f, f2 + f4);
            if (!(f15 - f14 >= 1.0f) || !menuAction.active()) continue;
            this.afE.add(new float[]{f9, f14, f8, f15 - f14, i});
        }
        if (bl) {
            Render2D.stopScissor(class_3322);
        }
        if (this.QO > 0.5f) {
            float f16 = f4 - 6.0f;
            float f17 = Math.max(20.0f, f16 * (f4 / this.qE()));
            float f18 = f2 + 3.0f + (f16 - f17) * (this.scroll / this.QO);
            Render2D.drawRoundedRect(class_45872, f + f3 - 8.0f, f18, 3.0f, f17, 1.5f, MenuChrome.accentA(0.5f));
        }
    }

    private void a(class_4587 class_45872, Instance instance, MenuAction menuAction, float f, float f2, float f3) {
        String string = PauseMenuScreen.clip(instance, menuAction.label(), f3);
        String string2 = string.toLowerCase(Locale.ROOT);
        int n = string2.indexOf("world");
        if (n == 0 && string2.contains("tools")) {
            String string3 = string.substring(0, 5);
            String string4 = string.substring(5);
            Render2D.drawFont(class_45872, instance, string3, f, f2, MenuChrome.accent());
            Render2D.drawFont(class_45872, instance, string4, f + instance.getWidth(string3), f2, MenuChrome.TEXT2);
        } else {
            Render2D.drawFont(class_45872, instance, string, f, f2, MenuChrome.TEXT2);
        }
    }

    private void a(class_4587 class_45872, MenuAction menuAction, float f, float f2, float f3, Color color) {
        String string = menuAction.cH() == null ? "" : menuAction.cH();
        String string2 = menuAction.label().toLowerCase(Locale.ROOT);
        if (string.contains("advancements")) {
            ClickGuiIcons.trophy(class_45872, f, f2, f3, color);
        } else if (string.contains("stats")) {
            ClickGuiIcons.chartBar(class_45872, f, f2, f3, color);
        } else if (string.contains("options")) {
            ClickGuiIcons.sliders(class_45872, f, f2, f3, color);
        } else if (string.contains("Feedback") || string.contains("feedback")) {
            ClickGuiIcons.chat(class_45872, f, f2, f3, color);
        } else if (string.contains("reportBugs")) {
            ClickGuiIcons.info(class_45872, f, f2, f3, color);
        } else if (string.contains("playerReporting") || string2.contains("\u0436\u0430\u043b\u043e\u0431")) {
            ClickGuiIcons.flag(class_45872, f, f2, f3, color);
        } else if (string.contains("shareToLan")) {
            ClickGuiIcons.globe(class_45872, f, f2, f3, color);
        } else if (string2.contains("world")) {
            ClickGuiIcons.globe(class_45872, f, f2, f3, color);
        } else if (string2.contains("\u043c\u043e\u0434") || string2.contains("mod")) {
            ClickGuiIcons.cube(class_45872, f, f2, f3, color);
        } else {
            ClickGuiIcons.cube(class_45872, f, f2, f3, color);
        }
    }

    private void f(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        boolean bl = MenuChrome.hit(n, n2, f, f2, f3, 44.0f);
        float f4 = MenuChrome.upd(this.afB, bl);
        MenuChrome.drawSecondaryMaterial(class_45872, f, f2, f3, 44.0f, 8.0f, f4, true);
        Instance instance = Fonts.UNBOUNDED.getFont(11.0f);
        float f5 = 14.0f;
        float f6 = f5 + 9.0f + instance.getWidth(this.afy);
        float f7 = f + (f3 - f6) / 2.0f;
        ClickGuiIcons.signOut(class_45872, f7 + f5 / 2.0f, f2 + 22.0f, f5, MenuChrome.MUTED);
        Render2D.drawFont(class_45872, instance, this.afy, f7 + f5 + 9.0f, f2 + 22.0f - instance.getHeight() / 2.0f, MenuChrome.TEXT2);
        MenuChrome.set(this.afD, f, f2, f3, 44.0f);
    }

    private static String clip(Instance instance, String string, float f) {
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

    @Override
    protected boolean a(float f, float f2, int n) {
        if (n != 0) {
            return false;
        }
        if (MenuChrome.in(this.afC, f, f2)) {
            aiG.method_1507(null);
            return true;
        }
        if (MenuChrome.in(this.afD, f, f2)) {
            this.e(this.afz);
            return true;
        }
        for (float[] fArray : this.afE) {
            if (!MenuChrome.hit(f, f2, fArray[0], fArray[1], fArray[2], fArray[3])) continue;
            this.e(this.afx.get((int)fArray[4]).qG());
            return true;
        }
        return false;
    }

    private void e(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        try {
            runnable.run();
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.error("[Pause] \u043f\u0443\u043d\u043a\u0442 \u043c\u0435\u043d\u044e \u0443\u043f\u0430\u043b \u043f\u0440\u0438 \u043e\u0442\u043a\u0440\u044b\u0442\u0438\u0438", throwable);
        }
    }

    @Override
    protected boolean a(float f, float f2, double d) {
        if (this.QO > 0.5f && MenuChrome.in(this.afG, f, f2)) {
            this.Lm = class_3532.method_15363((float)(this.Lm - (float)d * 52.0f), (float)0.0f, (float)this.QO);
            return true;
        }
        return false;
    }

    static final class MenuAction {
        private final String afH;
        private final String afI;
        private final Runnable afJ;
        private final boolean afK;

        MenuAction(String string, String string2, Runnable runnable, boolean bl) {
            this.afH = string;
            this.afI = string2;
            this.afJ = runnable;
            this.afK = bl;
        }

        @Override
        public final String toString() {
            return "MenuAction[" + "label=" + this.afH + ", " + "key=" + this.afI + ", " + "action=" + this.afJ + ", " + "active=" + this.afK + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.afH, this.afI, this.afJ, this.afK);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof MenuAction other)) return false;
            return java.util.Objects.deepEquals(this.afH, other.afH)
                && java.util.Objects.deepEquals(this.afI, other.afI)
                && java.util.Objects.deepEquals(this.afJ, other.afJ)
                && java.util.Objects.deepEquals(this.afK, other.afK);
        }

        public String label() {
            return this.afH;
        }

        public String cH() {
            return this.afI;
        }

        public Runnable qG() {
            return this.afJ;
        }

        public boolean active() {
            return this.afK;
        }
    }
}

