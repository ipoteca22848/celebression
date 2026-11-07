package ru.method.celebression.screen;

import java.awt.Color;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.screen.ControlsSettingsScreen;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public abstract class SettingsSubScreen
extends class_437
implements MinecraftClientAccess {
    protected final class_437 afL;
    protected float uiScale = 1.0f;
    protected int vw = 1;
    protected int vh = 1;
    private float parX;
    private float parY;
    protected long Rf;
    private static final float afM = 150.0f;
    private long afN;
    private class_437 afO;
    private boolean afP;
    private final Animation afQ = MenuChrome.anim();
    private final float[] afR = new float[4];

    protected SettingsSubScreen(class_437 class_4372, String string) {
        super((class_2561)class_2561.method_43470((String)string));
        this.afL = class_4372;
    }

    private void lY() {
        if (aiG == null || aiG.method_22683() == null) {
            return;
        }
        this.uiScale = Math.max(0.05f, (float)aiG.method_22683().method_4502() / 1080.0f);
        this.vw = Math.max(1, Math.round((float)aiG.method_22683().method_4486() / this.uiScale));
        this.vh = Math.max(1, Math.round((float)aiG.method_22683().method_4502() / this.uiScale));
    }

    protected boolean qH() {
        return aiG != null && SettingsSubScreen.aiG.field_1687 != null;
    }

    public void method_25394(class_332 class_3322, int n, int n2, float f) {
        if (this.Rf == 0L) {
            this.Rf = System.currentTimeMillis();
        }
        this.lY();
        boolean bl = this.qH();
        if (bl) {
            this.method_57734();
        }
        if (!bl) {
            MenuChrome.tickMusic();
        }
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        class_45872.method_22905(this.uiScale, this.uiScale, 1.0f);
        int n3 = Math.round((float)n / this.uiScale);
        int n4 = Math.round((float)n2 / this.uiScale);
        ClickGuiIcons.pixelScale(this.uiScale);
        if (bl) {
            Render2D.drawRoundedRect(class_45872, 0.0f, 0.0f, this.vw, this.vh, 0.0f, new Color(4, 7, 6, 97));
            MenuChrome.drawRadialVignette(class_45872, this.vw, this.vh);
        } else {
            float f2 = class_3532.method_15363((float)(((float)n3 - (float)this.vw / 2.0f) / ((float)this.vw / 2.0f)), (float)-1.0f, (float)1.0f);
            float f3 = class_3532.method_15363((float)(((float)n4 - (float)this.vh / 2.0f) / ((float)this.vh / 2.0f)), (float)-1.0f, (float)1.0f);
            this.parX += (f2 - this.parX) * 0.08f;
            this.parY += (f3 - this.parY) * 0.08f;
            MenuChrome.drawScreenBackground(class_45872, this.vw, this.vh, this.parX, this.parY);
            MenuChrome.drawBackChip(class_45872, n3, n4, this.afQ, this.afR);
            MenuChrome.drawLogo(class_45872, this.vw);
            MenuChrome.drawClock(class_45872, this.vw);
            MenuChrome.drawFooterSignature(class_45872, this.vw, this.vh, false);
        }
        long l = System.currentTimeMillis();
        float f4 = class_3532.method_15363((float)((float)(l - this.Rf) / (bl ? 180.0f : 240.0f)), (float)0.0f, (float)1.0f);
        float f5 = this.afN == 0L ? 0.0f : class_3532.method_15363((float)((float)(l - this.afN) / 150.0f), (float)0.0f, (float)1.0f);
        float f6 = this.afN == 0L ? f4 : 1.0f - f5;
        class_45872.method_22903();
        if (bl) {
            float f7 = 0.98f + 0.02f * (1.0f - (1.0f - f6) * (1.0f - f6));
            class_45872.method_46416((float)this.vw / 2.0f, (float)this.vh / 2.0f, 0.0f);
            class_45872.method_22905(f7, f7, 1.0f);
            class_45872.method_46416((float)(-this.vw) / 2.0f, (float)(-this.vh) / 2.0f, 0.0f);
        } else {
            float f8 = 1.0f - (1.0f - f6) * (1.0f - f6) * (1.0f - f6);
            class_45872.method_46416(0.0f, (1.0f - f8) * 18.0f, 0.0f);
        }
        this.a(class_3322, class_45872, n3, n4);
        class_45872.method_22909();
        if (this.afN != 0L && f5 >= 1.0f) {
            this.qI();
        }
        if (bl) {
            this.i(class_45872);
        }
        class_45872.method_22909();
        ClickGuiIcons.pixelScale(1.0f);
    }

    protected String kt() {
        return "\u0437\u0430\u043a\u0440\u044b\u0442\u044c";
    }

    private void i(class_4587 class_45872) {
        Instance instance = Fonts.PLEXMONO.getFont(8.5f);
        Instance instance2 = Fonts.GOLOS.getFont(11.0f);
        String string = this.kt();
        float f = instance.getWidth("ESC") + 12.0f;
        float f2 = 15.0f;
        float f3 = 26.0f;
        float f4 = 10.0f + f + 8.0f + instance2.getWidth(string) + 12.0f;
        float f5 = (float)this.vw / 2.0f - f4 / 2.0f;
        float f6 = (float)this.vh - 40.0f - f3;
        Render2D.drawRoundedRect(class_45872, f5, f6, f4, f3, 8.0f, new Color(5, 8, 7, 140));
        Render2D.drawBorder(class_45872, f5, f6, f4, f3, 8.0f, 1.0f, 1.2f, MenuChrome.white(0.07f));
        float f7 = f5 + 10.0f;
        float f8 = f6 + (f3 - f2) / 2.0f;
        Render2D.drawBorder(class_45872, f7, f8, f, f2, 4.0f, 1.0f, 1.2f, MenuChrome.white(0.14f));
        Render2D.drawFont(class_45872, instance, "ESC", f7 + 6.0f, f8 + f2 / 2.0f - instance.getHeight() / 2.0f, MenuChrome.accent());
        Render2D.drawFont(class_45872, instance2, string, f7 + f + 8.0f, f6 + f3 / 2.0f - instance2.getHeight() / 2.0f, MenuChrome.TEXT3);
    }

    protected abstract void a(class_332 var1, class_4587 var2, int var3, int var4);

    protected float av(float f) {
        return (float)this.vw / 2.0f - f / 2.0f;
    }

    public boolean method_25402(double d, double d2, int n) {
        if (this.qJ()) {
            return true;
        }
        float f = (float)(d / (double)this.uiScale);
        float f2 = (float)(d2 / (double)this.uiScale);
        if (this.a(f, f2, n)) {
            return true;
        }
        if (n == 0 && MenuChrome.in(this.afR, f, f2)) {
            this.pJ();
            return true;
        }
        return super.method_25402(d, d2, n);
    }

    protected boolean a(float f, float f2, int n) {
        return false;
    }

    public boolean method_25406(double d, double d2, int n) {
        this.b((float)(d / (double)this.uiScale), (float)(d2 / (double)this.uiScale), n);
        return super.method_25406(d, d2, n);
    }

    protected void b(float f, float f2, int n) {
    }

    public boolean method_25401(double d, double d2, double d3, double d4) {
        float f = (float)(d / (double)this.uiScale);
        float f2 = (float)(d2 / (double)this.uiScale);
        if (this.a(f, f2, d4)) {
            return true;
        }
        return super.method_25401(d, d2, d3, d4);
    }

    protected boolean a(float f, float f2, double d) {
        return false;
    }

    public boolean method_25404(int n, int n2, int n3) {
        if (this.qJ()) {
            return true;
        }
        if (this.c(n, n2, n3)) {
            return true;
        }
        if (n == 256) {
            this.pJ();
            return true;
        }
        return super.method_25404(n, n2, n3);
    }

    protected boolean c(int n, int n2, int n3) {
        return false;
    }

    public boolean method_25400(char c, int n) {
        if (this.a(c, n)) {
            return true;
        }
        return super.method_25400(c, n);
    }

    protected boolean a(char c, int n) {
        return false;
    }

    protected void pJ() {
        SettingsSubScreen.aiG.field_1690.method_1640();
        this.a(this.afL, true);
    }

    protected void d(class_437 class_4372) {
        this.a(class_4372, false);
    }

    private void a(class_437 class_4372, boolean bl) {
        if (this.afN != 0L) {
            return;
        }
        this.afO = class_4372;
        this.afP = bl;
        this.afN = System.currentTimeMillis();
    }

    private void qI() {
        class_437 class_4372 = this.afO;
        boolean bl = this.afP;
        this.afN = 0L;
        this.afO = null;
        if (class_4372 != null) {
            MenuChrome.keepMusicAcrossSwap();
            aiG.method_1507(class_4372);
            return;
        }
        if (bl && this.qH()) {
            aiG.method_1507(null);
            return;
        }
        MenuChrome.keepMusicAcrossSwap();
        aiG.method_1507((class_437)new CustomMainMenuScreen());
    }

    protected boolean qJ() {
        return this.afN != 0L;
    }

    public void qK() {
        this.d(new ControlsSettingsScreen(this));
    }

    public void method_25432() {
        this.Rf = 0L;
        MenuChrome.onMenuScreenRemoved();
        super.method_25432();
    }

    public boolean method_25422() {
        return false;
    }

    public void method_25420(class_332 class_3322, int n, int n2, float f) {
    }
}

