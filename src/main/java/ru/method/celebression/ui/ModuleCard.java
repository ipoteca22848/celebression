package ru.method.celebression.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.Generated;
import net.minecraft.class_1074;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.manager.ClickRippleManager;
import ru.method.celebression.render.ColorPalette;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.animation.InterpolationUtils;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.render.LinkGuiBrandRenderer;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.module.Module;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.animation.TimedProgress;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.utils.render.GuiIcons;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class ModuleCard {
    private static final float XL = 34.0f;
    private static final float XM = 8.0f;
    private static final float XN = 12.0f;
    private static final float XO = 13.0f;
    private final Module XP;
    private float x;
    private float y;
    private float width;
    private float XQ = 1.0f;
    private static ModuleCard XR;
    private float XS;
    private float XT;
    private float XU;
    private float XV;
    private List<String> XW;
    private String XX;
    private float XY = -1.0f;
    private String XZ;
    private String Ya;
    private String Yb;
    private String Yc;
    private float Yd;
    private int Ye = Integer.MIN_VALUE;
    private boolean Yf;
    private boolean Yg;
    private final TimedProgress Yh = new TimedProgress(220L, Easing.aiZ, "None");
    private final Animation Yi = new Animation(320L, 1.0, false, Easing.aiT);
    private final Animation Yj = new Animation(180L, 1.0, false, Easing.aiT);
    private final Animation Yk = new Animation(300L, 1.0, false, Easing.aiZ);
    private final Animation Yl = new Animation(520L, 1.0, false, Easing.aiZ);
    private final Animation Ym = new Animation(280L, 1.0, true, Easing.aiZ);
    private final Animation Yn = new Animation(840L, 1.0, true, Easing.aiT);

    public ModuleCard(Module module) {
        this.XP = module;
    }

    public String oq() {
        if (this.Ya == null) {
            this.Ya = this.XP.getName().toLowerCase(Locale.ROOT);
        }
        return this.Ya;
    }

    public String or() {
        if (this.Yb == null) {
            this.Yb = this.XP.getDisplayName().toLowerCase(Locale.ROOT);
        }
        return this.Yb;
    }

    public void av(int n) {
        this.Yn.w(780L + (long)n * 84L);
        this.Yn.reset();
        boolean bl = this.XP.isToggle();
        if (bl) {
            this.Yi.ro();
            this.Yk.ro();
            this.Yl.ro();
        } else {
            this.Yi.ae(false);
            this.Yk.ae(false);
            this.Yl.ae(false);
        }
    }

    private static float d(Animation animation) {
        if (animation.rn() && animation.rj()) {
            return 1.0f;
        }
        float f = animation.rl();
        if (animation.rn()) {
            float f2 = ModuleCard.aq(f);
            return f >= 0.985f ? 1.0f : f2;
        }
        return f;
    }

    private static float aq(float f) {
        float f2 = Math.max(0.0f, Math.min(1.0f, f));
        return f2 * f2 * (3.0f - 2.0f * f2);
    }

    public float getHeight() {
        return 86.0f;
    }

    public void z(boolean bl) {
        this.Ym.i(bl);
    }

    public void aa(boolean bl) {
        if (bl) {
            this.Ym.ro();
        } else {
            this.Ym.ae(false);
        }
    }

    public float os() {
        return this.Ym.rk();
    }

    public boolean ot() {
        return this.os() > 0.015f;
    }

    public float ou() {
        float f = this.os();
        return f * f * (3.0f - 2.0f * f);
    }

    public void render(class_332 class_3322, int n, int n2, float f) {
        this.b(class_3322.method_51448(), n, n2, f);
    }

    public void b(class_4587 class_45872, int n, int n2, float f) {
        float f2;
        Object object;
        float f3;
        float f4 = this.Ym.rk() * this.XQ;
        if (f4 < 0.02f) {
            return;
        }
        boolean bl = MathUtils.c(this.x, this.y, this.width, this.getHeight(), n, n2);
        boolean bl2 = this.XP.isToggle();
        this.Yi.i(bl2);
        this.Yk.i(bl2);
        this.Yj.i(bl);
        this.Yl.i(bl2);
        float f5 = this.Yi.rk();
        float f6 = this.Yj.rk();
        float f7 = this.Yk.rk();
        float f8 = ModuleCard.d(this.Yl);
        boolean bl3 = bl2 && (this.Yl.rj() || f8 >= 0.992f);
        float f9 = Math.max(f7, f8);
        float f10 = Math.max(f9, f6 * 0.42f);
        float f11 = this.Yn.rk();
        float f12 = LinkGuiRenderContext.getFadeMultiplier();
        LinkGuiRenderContext.setFade(f12 * Math.max(0.0f, Math.min(1.0f, f4 * f11)));
        class_45872.method_22903();
        float f13 = this.x + this.width / 2.0f;
        float f14 = this.y + this.getHeight() / 2.0f;
        float f15 = 0.94f + 0.06f * f11;
        class_45872.method_46416(f13, f14, 0.0f);
        class_45872.method_22905(f15, f15, 1.0f);
        class_45872.method_46416(-f13, -f14, 0.0f);
        class_45872.method_46416(0.0f, -3.0f * f6 + (1.0f - f11) * 16.0f, 0.0f);
        LinkGuiBrandRenderer.g(class_45872, this.x, this.y, this.width, this.getHeight(), 10.0f, f6);
        LinkGuiBrandRenderer.a(class_45872, this.x, this.y, this.width, this.getHeight(), 10.0f, bl2, f10);
        Color color = ThemeTransition.accent();
        float f16 = Math.max(ModuleCard.d(this.Yl) * 0.9f, this.Yj.rk() * 0.22f);
        if (f16 > 0.02f) {
            UiRenderUtils.c(class_45872, this.x, this.y, this.width * 0.55f, this.getHeight(), 10.0f, InterpolationUtils.e(color, 0.11f * f16), InterpolationUtils.e(color, 0.0f));
        }
        if ((f3 = Math.max(ModuleCard.d(this.Yl) * 0.5f, this.Yj.rk())) > 0.02f) {
            UiRenderUtils.c(class_45872, this.x, this.y, this.width, this.getHeight(), 10.0f, 1.0f, InterpolationUtils.e(color, 0.38f * f3));
        }
        float f17 = 3.0f;
        float f18 = this.getHeight() * 0.6f;
        float f19 = this.y + (this.getHeight() - f18) / 2.0f;
        LinkGuiBrandRenderer.a(class_45872, this.x + 5.0f, f19, f17, f18, f8, bl3);
        Color color2 = ThemeTransition.accent();
        float f20 = this.x + 12.0f;
        float f21 = this.y + (this.getHeight() - 34.0f) / 2.0f - 5.0f;
        float f22 = Math.max(f9, f6 * 0.4f);
        LinkGuiBrandRenderer.c(class_45872, f20, f21, 34.0f, 8.0f, bl2, f22);
        Color color3 = InterpolationUtils.lerp(InterpolationUtils.e(ColorPalette.aiu, 0.35f), color2, f22);
        class_2960 class_29602 = GuiIcons.forCategory(this.XP.getCategory());
        if (class_29602 != null) {
            GuiIcons.draw(class_45872, class_29602, f20 + 17.0f, f21 + 17.0f, 16.0f, color3);
        } else {
            LinkGuiBrandRenderer.a(class_45872, f20, f21, 34.0f, this.XP.getCategory().fK(), color3, 13.0f);
        }
        if (!this.XP.isActionModule()) {
            boolean bl4;
            object = this.XP.getBind();
            boolean bl5 = XR == this;
            float f23 = 6.6f;
            f2 = 12.0f;
            int n3 = object == null ? -1 : ((KeyBind)object).c();
            boolean bl6 = bl4 = object != null && ((KeyBind)object).jW();
            if (this.Yc == null || this.Ye != n3 || this.Yf != bl4 || this.Yg != bl5) {
                this.Yc = bl5 ? "..." : (object == null || n3 == -1 ? "None" : ((KeyBind)object).toString().replace('_', ' ').replace('-', ' '));
                this.Yd = 8.0f + f2 + 4.0f + Fonts.MEDIUM.getWidth(this.Yc, f23) + 8.0f;
                this.Ye = n3;
                this.Yf = bl4;
                this.Yg = bl5;
            }
            String string = this.Yc;
            this.XV = 14.0f;
            this.XS = f20;
            this.XT = f21 + 34.0f + 5.0f;
            this.XU = this.Yd;
            boolean bl7 = object != null && ((KeyBind)object).c() != -1;
            float f24 = bl7 ? 0.5f : 0.26f;
            LinkGuiBrandRenderer.h(class_45872, this.XS, this.XT, this.XU, this.XV, 5.0f, bl5 ? 1.0f : Math.max(f24, Math.max(f9, f6) * 0.85f));
            Color color4 = bl7 || bl5 ? color2 : new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), 150);
            GuiIcons.draw(class_45872, GuiIcons.KEYBOARD, this.XS + 8.0f + f2 / 2.0f, this.XT + this.XV / 2.0f, f2, color4);
            this.Yh.cF(string);
            this.q(class_45872, this.XS + 8.0f + f2 + 4.0f, this.XT + this.XV / 2.0f, f23, ThemeTransition.d(bl7 || bl5 ? 235 : 160));
        } else {
            this.XU = 0.0f;
            this.XV = 0.0f;
        }
        object = InterpolationUtils.lerp(ThemeTransition.d(140), ThemeTransition.d(255), Math.max(f9, f6 * 0.55f));
        Color color5 = InterpolationUtils.lerp(ThemeTransition.d(75), ThemeTransition.d(170), Math.max(f9, f6 * 0.45f));
        UiRenderUtils.a(class_45872, Fonts.ROUNDBOLD, this.XP.getDisplayName(), this.x + 54.0f, this.y + 23.0f, (Color)object, 9.4f);
        if (this.XZ == null) {
            String string = class_1074.method_4662((String)this.XP.getDescription(), (Object[])new Object[0]);
            this.XZ = string.equals(this.XP.getDescription()) ? this.XP.getName() : string;
        }
        String string = this.XZ;
        f2 = 7.9f;
        float f25 = this.width - 104.0f;
        if (this.XW == null || !string.equals(this.XX) || this.XY != f25) {
            this.XW = ModuleCard.c(string, f25, f2, 2);
            this.XX = string;
            this.XY = f25;
        }
        List<String> list = this.XW;
        float f26 = Fonts.MEDIUM.getHeight(f2) + 2.5f;
        float f27 = list.size() <= 1 ? this.y + 41.0f : this.y + 36.0f;
        for (int i = 0; i < list.size(); ++i) {
            UiRenderUtils.a(class_45872, Fonts.MEDIUM, list.get(i), this.x + 54.0f, f27 + (float)i * f26, color5, f2);
        }
        float f28 = this.x + this.width - 46.0f + 18.0f;
        float f29 = this.y + this.getHeight() / 2.0f - 5.0f;
        if (!this.XP.hidesToggle()) {
            LinkGuiBrandRenderer.i(class_45872, this.x + this.width - 46.0f, f29 - 9.0f, 36.0f, 18.0f, f5);
        }
        if (!this.XP.getSettings().isEmpty()) {
            GuiIcons.draw(class_45872, GuiIcons.DOT, f28, f29 + 20.0f, 15.0f, ThemeTransition.d((int)(110.0f + 90.0f * Math.max(f9, f6))));
        }
        class_45872.method_22909();
        LinkGuiRenderContext.setFade(f12);
    }

    public boolean mouseClicked(double d, double d2, int n) {
        if (this.Ym.rk() < 0.35f || this.XQ < 0.35f) {
            return false;
        }
        if (!MathUtils.c(this.x, this.y, this.width, this.getHeight(), (float)d, (float)d2)) {
            return false;
        }
        if (this.XP.isActionModule()) {
            if (n == 0) {
                this.XP.activate();
            } else if (n == 1 && !this.XP.getSettings().isEmpty()) {
                ClickRippleManager.c(this.XP, (float)d, (float)d2);
            }
            return true;
        }
        if (XR == this) {
            this.XP.getBind().ah(n);
            this.XP.getBind().u(true);
            XR = null;
            return true;
        }
        boolean bl = MathUtils.c(this.XS, this.XT, this.XU, this.XV, (float)d, (float)d2);
        if (n == 2 || n == 0 && bl) {
            XR = this;
            return true;
        }
        if (XR != null) {
            XR = null;
        }
        if (n == 0) {
            this.XP.toggle();
        } else if (n == 1 && !this.XP.getSettings().isEmpty()) {
            ClickRippleManager.c(this.XP, (float)d, (float)d2);
        }
        return true;
    }

    public static boolean ov() {
        return XR != null;
    }

    public static void ow() {
        XR = null;
    }

    public static boolean aw(int n) {
        if (XR == null) {
            return false;
        }
        Module module = ModuleCard.XR.XP;
        if (n == 256 || n == 261) {
            module.getBind().ah(-1);
            module.getBind().u(false);
        } else {
            module.getBind().ah(n);
            module.getBind().u(false);
        }
        XR = null;
        return true;
    }

    public static boolean d(double d) {
        if (XR == null || d == 0.0) {
            return false;
        }
        int n = d > 0.0 ? 1000 : 1001;
        ModuleCard.XR.XP.getBind().ah(n);
        ModuleCard.XR.XP.getBind().u(true);
        XR = null;
        return true;
    }

    private void q(class_4587 class_45872, float f, float f2, float f3, Color color) {
        float f4 = Fonts.MEDIUM.getHeight(f3);
        float f5 = f2 - f4 / 2.0f;
        if (!this.Yh.rg()) {
            Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f3), this.Yh.rh(), f, f5, color);
            return;
        }
        float f6 = Math.max(0.0f, Math.min(1.0f, this.Yh.progress()));
        float f7 = f4 * 1.1f;
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((float)color.getAlpha() * (1.0f - f6)));
        Color color3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((float)color.getAlpha() * f6));
        Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f3), this.Yh.ri(), f, f5 - f7 * f6, color2);
        Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f3), this.Yh.rh(), f, f5 + f7 * (1.0f - f6), color3);
    }

    private static String b(String string, float f, float f2) {
        if (Fonts.MEDIUM.getWidth(string, f2) <= f) {
            return string;
        }
        while (string.length() > 3 && Fonts.MEDIUM.getWidth(string + "...", f2) > f) {
            string = string.substring(0, string.length() - 1);
        }
        return string + "...";
    }

    private static List<String> c(String string, float f, float f2, int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        while (n2 < string.length() && arrayList.size() < n) {
            int n3;
            int n4 = -1;
            for (n3 = n2; n3 < string.length(); ++n3) {
                if (string.charAt(n3) == ' ') {
                    n4 = n3;
                }
                if (!(Fonts.MEDIUM.getWidth(string.substring(n2, n3 + 1), f2) > f) || n3 <= n2) continue;
                if (n4 > n2) {
                    n3 = n4;
                    break;
                }
                n3 = Math.max(n2, n3 - 1);
                break;
            }
            if (n3 >= string.length()) {
                arrayList.add(string.substring(n2).trim());
                break;
            }
            if (arrayList.size() == n - 1) {
                arrayList.add(ModuleCard.b(string.substring(n2), f, f2));
                break;
            }
            arrayList.add(string.substring(n2, n3).trim());
            for (n2 = n3; n2 < string.length() && string.charAt(n2) == ' '; ++n2) {
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add("");
        }
        return arrayList;
    }

    @Generated
    public Module getModule() {
        return this.XP;
    }

    @Generated
    public float getX() {
        return this.x;
    }

    @Generated
    public float getY() {
        return this.y;
    }

    @Generated
    public float getWidth() {
        return this.width;
    }

    @Generated
    public float ox() {
        return this.XQ;
    }

    @Generated
    public float oy() {
        return this.XS;
    }

    @Generated
    public float oz() {
        return this.XT;
    }

    @Generated
    public float oA() {
        return this.XU;
    }

    @Generated
    public float oB() {
        return this.XV;
    }

    @Generated
    public List<String> oC() {
        return this.XW;
    }

    @Generated
    public String oD() {
        return this.XX;
    }

    @Generated
    public float oE() {
        return this.XY;
    }

    @Generated
    public String oF() {
        return this.XZ;
    }

    @Generated
    public String oG() {
        return this.Ya;
    }

    @Generated
    public String oH() {
        return this.Yb;
    }

    @Generated
    public String oI() {
        return this.Yc;
    }

    @Generated
    public float oJ() {
        return this.Yd;
    }

    @Generated
    public int oK() {
        return this.Ye;
    }

    @Generated
    public boolean oL() {
        return this.Yf;
    }

    @Generated
    public boolean oM() {
        return this.Yg;
    }

    @Generated
    public TimedProgress oN() {
        return this.Yh;
    }

    @Generated
    public Animation oO() {
        return this.Yi;
    }

    @Generated
    public Animation oP() {
        return this.Yj;
    }

    @Generated
    public Animation oQ() {
        return this.Yk;
    }

    @Generated
    public Animation oR() {
        return this.Yl;
    }

    @Generated
    public Animation oS() {
        return this.Ym;
    }

    @Generated
    public Animation oT() {
        return this.Yn;
    }

    @Generated
    public void setX(float f) {
        this.x = f;
    }

    @Generated
    public void setY(float f) {
        this.y = f;
    }

    @Generated
    public void setWidth(float f) {
        this.width = f;
    }

    @Generated
    public void ar(float f) {
        this.XQ = f;
    }
}

