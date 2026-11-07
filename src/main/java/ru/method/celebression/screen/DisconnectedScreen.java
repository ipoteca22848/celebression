package ru.method.celebression.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_412;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_639;
import net.minecraft.class_642;
import net.minecraft.class_7833;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.screen.MultiplayerScreen;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class DisconnectedScreen
extends class_437
implements MinecraftClientAccess {
    private static final float QV = 520.0f;
    private static final float QW = 36.0f;
    private static final float QX = 28.0f;
    private static final float QY = 52.0f;
    private static final int QZ = 6;
    private final class_2561 Ra;
    private final class_2561 Rb;
    private final boolean Rc;
    private final class_642 Rd;
    private final boolean Re;
    private float uiScale = 1.0f;
    private int vw = 1;
    private int vh = 1;
    private long Rf;
    private final Animation Rg = MenuChrome.anim();
    private final Animation Rh = MenuChrome.anim();
    private final float[] Ri = new float[4];
    private final float[] Rj = new float[4];
    private List<String> Rk;
    private String Rl;
    private float Rm;
    private float Rn;

    public DisconnectedScreen(class_2561 class_25612, class_2561 class_25613, boolean bl, class_642 class_6422) {
        super((class_2561)(class_25612 == null ? class_2561.method_43470((String)"\u0421\u043e\u0435\u0434\u0438\u043d\u0435\u043d\u0438\u0435 \u043f\u043e\u0442\u0435\u0440\u044f\u043d\u043e") : class_25612));
        this.Ra = this.method_25440();
        this.Rb = class_25613 == null ? class_2561.method_43473() : class_25613;
        this.Rc = bl;
        this.Rd = class_6422;
        this.Re = bl && class_6422 != null;
    }

    private void lY() {
        if (this.field_22787 == null || this.field_22787.method_22683() == null) {
            return;
        }
        this.uiScale = Math.max(0.05f, (float)this.field_22787.method_22683().method_4502() / 1080.0f);
        this.vw = Math.max(1, Math.round((float)this.field_22787.method_22683().method_4486() / this.uiScale));
        this.vh = Math.max(1, Math.round((float)this.field_22787.method_22683().method_4502() / this.uiScale));
    }

    public void method_25394(class_332 class_3322, int n, int n2, float f) {
        if (this.Rf == 0L) {
            this.Rf = System.currentTimeMillis();
        }
        this.lY();
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        class_45872.method_22905(this.uiScale, this.uiScale, 1.0f);
        float f2 = (float)n / this.uiScale;
        float f3 = (float)n2 / this.uiScale;
        ClickGuiIcons.pixelScale(this.uiScale);
        Render2D.drawRoundedRect(class_45872, 0.0f, 0.0f, this.vw, this.vh, 0.0f, new Color(4, 7, 6, 235));
        MenuChrome.drawRadialVignette(class_45872, this.vw, this.vh);
        float f4 = 448.0f;
        List<String> list = this.ah(f4);
        int n3 = Math.min(list.size(), 6);
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        float f5 = instance.getHeight() + 5.0f;
        Instance instance2 = Fonts.PLEXMONO.getFont(10.0f);
        float f6 = instance2.getHeight() + 8.0f + (float)n3 * f5;
        float f7 = 12.0f + f6 + 12.0f;
        Instance instance3 = Fonts.GOLOS_SEMI.getFont(22.0f);
        Instance instance4 = Fonts.GOLOS.getFont(13.0f);
        float f8 = instance3.getHeight();
        float f9 = instance4.getHeight();
        float f10 = 44.0f;
        float f11 = f10 + (this.Re ? 8.0f + f10 : 0.0f);
        float f12 = 106.0f + f8 + 6.0f + f9 + 22.0f + f7 + 22.0f + f11 + 28.0f;
        float f13 = (float)this.vw / 2.0f - 260.0f;
        float f14 = (float)this.vh / 2.0f - f12 * 0.54f;
        long l = System.currentTimeMillis();
        float f15 = class_3532.method_15363((float)((float)(l - this.Rf) / 180.0f), (float)0.0f, (float)1.0f);
        float f16 = 1.0f - (1.0f - f15) * (1.0f - f15);
        float f17 = 0.98f + 0.02f * f16;
        float f18 = f13 + 260.0f;
        float f19 = f14 + f12 / 2.0f;
        class_45872.method_22903();
        class_45872.method_46416(f18, f19, 0.0f);
        class_45872.method_22905(f17, f17, 1.0f);
        class_45872.method_46416(-f18, -f19, 0.0f);
        MenuPanelRenderer.n(class_45872, f13, f14, 520.0f, f12);
        MenuPanelRenderer.p(class_45872, f13, f14, 520.0f, f12);
        float f20 = f13 + 260.0f;
        float f21 = f14 + 36.0f;
        float f22 = f20 - 26.0f;
        Color color = MenuChrome.DANGER;
        MenuChrome.glow(class_45872, f22, f21, 52.0f, 52.0f, 12.0f, new Color(color.getRed(), color.getGreen(), color.getBlue(), 60), 0.0f, 5, 3.0f);
        Render2D.drawRoundedRect(class_45872, f22, f21, 52.0f, 52.0f, 12.0f, new Color(224, 110, 110, 20));
        Render2D.drawBorder(class_45872, f22, f21, 52.0f, 52.0f, 12.0f, 1.0f, 1.4f, new Color(224, 110, 110, 102));
        float f23 = 22.0f;
        float f24 = f21 + 26.0f + f23 * 0.0625f;
        float f25 = f20 - 1.0f;
        ClickGuiIcons.globe(class_45872, f25, f24, f23, color);
        this.p(class_45872, f25, f24, 20.0f, color);
        Render2D.drawFont(class_45872, instance3, this.Ra.getString(), f20 - instance3.getWidth(this.Ra.getString()) / 2.0f, f21 += 70.0f, MenuChrome.TEXT);
        String string = "\u0421\u0435\u0440\u0432\u0435\u0440 \u0440\u0430\u0437\u043e\u0440\u0432\u0430\u043b \u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u0435";
        Render2D.drawFont(class_45872, instance4, string, f20 - instance4.getWidth(string) / 2.0f, f21 += f8 + 6.0f, MenuChrome.MUTED2);
        float f26 = f13 + 36.0f;
        float f27 = f4;
        float f28 = f21 += f9 + 22.0f;
        Render2D.drawRoundedRect(class_45872, f26, f28, f27, f7, 8.0f, new Color(255, 255, 255, 8));
        Render2D.drawBorder(class_45872, f26, f28, f27, f7, 8.0f, 1.0f, 1.2f, MenuChrome.white(0.07f));
        MenuChrome.drawSpaced(class_45872, instance2, "\u041f\u0420\u0418\u0427\u0418\u041d\u0410", f26 + 14.0f, f28 + 12.0f, MenuChrome.MUTED4, 1.4f);
        float f29 = f28 + 12.0f + instance2.getHeight() + 8.0f;
        float f30 = (float)n3 * f5;
        this.Rn = Math.max(0.0f, (float)list.size() * f5 - f30);
        this.Rm = class_3532.method_15363((float)this.Rm, (float)0.0f, (float)this.Rn);
        Render2D.startScissor(class_3322, f26 + 1.0f, f29 - 1.0f, f27 - 2.0f, f30 + 2.0f);
        float f31 = f29 - this.Rm;
        for (String string2 : list) {
            Render2D.drawFont(class_45872, instance, string2, f26 + 14.0f, f31, MenuChrome.TEXT3);
            f31 += f5;
        }
        Render2D.stopScissor(class_3322);
        float f32 = f13 + 36.0f;
        float f33 = f4;
        boolean bl = MenuChrome.hit(f2, f3, f32, f21 += f7 + 22.0f, f33, f10);
        float f34 = MenuChrome.upd(this.Rg, bl);
        this.a(class_45872, f32, f21, f33, f10, true, this.Rc ? "list" : "menu", this.Rc ? "\u041a \u0441\u043f\u0438\u0441\u043a\u0443 \u0441\u0435\u0440\u0432\u0435\u0440\u043e\u0432" : "\u0412 \u0433\u043b\u0430\u0432\u043d\u043e\u0435 \u043c\u0435\u043d\u044e", f34);
        MenuChrome.set(this.Ri, f32, f21, f33, f10);
        f21 += f10;
        if (this.Re) {
            boolean bl2 = MenuChrome.hit(f2, f3, f32, f21 += 8.0f, f33, f10);
            float f35 = MenuChrome.upd(this.Rh, bl2);
            this.a(class_45872, f32, f21, f33, f10, false, "reconnect", "\u041f\u0435\u0440\u0435\u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0438\u0442\u044c\u0441\u044f", f35);
            MenuChrome.set(this.Rj, f32, f21, f33, f10);
        } else {
            this.Rj[2] = 0.0f;
        }
        class_45872.method_22909();
        this.e(class_45872);
        MenuChrome.drawFooterSignature(class_45872, this.vw, this.vh, true);
        class_45872.method_22909();
        ClickGuiIcons.pixelScale(1.0f);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, boolean bl, String string, String string2, float f5) {
        Color color;
        Object object;
        float f6 = f2 - f5;
        float f7 = 8.0f;
        if (bl) {
            object = MenuChrome.accent();
            Render2D.drawRoundedRect(class_45872, f + 4.0f, f6 + f4 - 6.0f, f3 - 8.0f, 12.0f, 8.0f, new Color(((Color)object).getRed(), ((Color)object).getGreen(), ((Color)object).getBlue(), Math.round(70.0f + 50.0f * f5)));
            int n = Math.min(255, Math.round((float)((Color)object).getRed() * (1.0f + 0.07f * f5)));
            int n2 = Math.min(255, Math.round((float)((Color)object).getGreen() * (1.0f + 0.07f * f5)));
            int n3 = Math.min(255, Math.round((float)((Color)object).getBlue() * (1.0f + 0.07f * f5)));
            Render2D.drawRoundedRect(class_45872, f, f6, f3, f4, f7, new Color(n, n2, n3));
            Render2D.drawRoundedRect(class_45872, f + f7, f6 + 1.0f, f3 - f7 * 2.0f, 1.0f, 0.5f, new Color(255, 255, 255, 90));
            color = MenuChrome.INK;
        } else {
            MenuChrome.drawSecondaryMaterial(class_45872, f, f6, f3, f4, f7, f5, false);
            color = MenuChrome.TEXT2;
        }
        object = Fonts.UNBOUNDED.getFont(11.0f);
        float f8 = 14.0f;
        float f9 = ((Instance)object).getWidth(string2);
        float f10 = f8 + 9.0f + f9;
        float f11 = f + (f3 - f10) / 2.0f + f8 / 2.0f;
        float f12 = f6 + f4 / 2.0f;
        Color color2 = bl ? MenuChrome.INK : MenuChrome.MUTED;
        switch (string) {
            case "list": {
                ClickGuiIcons.list(class_45872, f11, f12, f8, color2);
                break;
            }
            case "reconnect": {
                ClickGuiIcons.refresh(class_45872, f11, f12, f8, color2);
                break;
            }
            default: {
                ClickGuiIcons.chevronLeft(class_45872, f11, f12, f8, color2);
            }
        }
        Render2D.drawFont(class_45872, (Instance)object, string2, f11 + f8 / 2.0f + 9.0f, f12 - ((Instance)object).getHeight() / 2.0f, color);
    }

    private void p(class_4587 class_45872, float f, float f2, float f3, Color color) {
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22907(class_7833.field_40718.rotationDegrees(-45.0f));
        Render2D.drawRoundedRect(class_45872, -f3 / 2.0f, -0.9f, f3, 1.8f, 0.9f, color);
        class_45872.method_22909();
    }

    private void e(class_4587 class_45872) {
        Instance instance = Fonts.PLEXMONO.getFont(10.0f);
        Instance instance2 = Fonts.GOLOS.getFont(11.5f);
        String string = this.Rc ? "\u043a \u0441\u043f\u0438\u0441\u043a\u0443 \u0441\u0435\u0440\u0432\u0435\u0440\u043e\u0432" : "\u0432 \u0433\u043b\u0430\u0432\u043d\u043e\u0435 \u043c\u0435\u043d\u044e";
        float f = instance.getWidth("ESC") + 16.0f;
        float f2 = 22.0f;
        float f3 = 8.0f;
        float f4 = f + f3 + instance2.getWidth(string);
        Instance instance3 = Fonts.PLEXMONO.getFont(9.5f);
        float f5 = (float)this.vh - 44.0f - instance3.getHeight();
        float f6 = f5 - 14.0f - f2;
        float f7 = (float)this.vw / 2.0f - f4 / 2.0f;
        Render2D.drawRoundedRect(class_45872, f7, f6, f, f2, 5.0f, new Color(255, 255, 255, 8));
        Render2D.drawBorder(class_45872, f7, f6, f, f2, 5.0f, 1.0f, 1.2f, MenuChrome.white(0.14f));
        Render2D.drawFont(class_45872, instance, "ESC", f7 + 8.0f, f6 + f2 / 2.0f - instance.getHeight() / 2.0f, MenuChrome.MUTED);
        Render2D.drawFont(class_45872, instance2, string, f7 + f + f3, f6 + f2 / 2.0f - instance2.getHeight() / 2.0f, MenuChrome.MUTED3);
    }

    private List<String> ah(float f) {
        float f2 = f - 28.0f;
        String string = this.Rb.getString();
        String string2 = (int)f2 + "|" + string;
        if (this.Rk != null && string2.equals(this.Rl)) {
            return this.Rk;
        }
        Instance instance = Fonts.GOLOS.getFont(13.0f);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string3 : string.split("\n", -1)) {
            if (string3.isEmpty()) {
                arrayList.add("");
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String string4 : string3.split(" ")) {
                String string5;
                String string6 = string5 = stringBuilder.length() == 0 ? string4 : String.valueOf(stringBuilder) + " " + string4;
                if (instance.getWidth(string5) <= f2 || stringBuilder.length() == 0) {
                    stringBuilder.setLength(0);
                    stringBuilder.append(string5);
                    continue;
                }
                arrayList.add(stringBuilder.toString());
                stringBuilder.setLength(0);
                stringBuilder.append(string4);
            }
            arrayList.add(stringBuilder.toString());
        }
        this.Rk = arrayList;
        this.Rl = string2;
        return arrayList;
    }

    private void lZ() {
        if (this.Rc) {
            this.field_22787.method_1507((class_437)new MultiplayerScreen(null));
        } else {
            this.field_22787.method_1507((class_437)new CustomMainMenuScreen());
        }
    }

    private void ma() {
        if (!this.Re) {
            return;
        }
        class_412.method_36877((class_437)new MultiplayerScreen(null), (class_310)this.field_22787, (class_639)class_639.method_2950((String)this.Rd.field_3761), (class_642)this.Rd, (boolean)false, null);
    }

    public boolean method_25402(double d, double d2, int n) {
        if (n == 0) {
            float f = (float)(d / (double)this.uiScale);
            float f2 = (float)(d2 / (double)this.uiScale);
            if (MenuChrome.in(this.Ri, f, f2)) {
                this.lZ();
                return true;
            }
            if (this.Re && MenuChrome.in(this.Rj, f, f2)) {
                this.ma();
                return true;
            }
        }
        return super.method_25402(d, d2, n);
    }

    public boolean method_25401(double d, double d2, double d3, double d4) {
        if (this.Rn > 0.0f) {
            this.Rm = class_3532.method_15363((float)(this.Rm - (float)d4 * 16.0f), (float)0.0f, (float)this.Rn);
            return true;
        }
        return super.method_25401(d, d2, d3, d4);
    }

    public boolean method_25404(int n, int n2, int n3) {
        if (n == 256 || n == 257 || n == 335) {
            this.lZ();
            return true;
        }
        return super.method_25404(n, n2, n3);
    }

    public boolean method_25422() {
        return false;
    }

    public void method_25420(class_332 class_3322, int n, int n2, float f) {
    }

    public boolean method_25421() {
        return false;
    }
}

