package ru.method.celebression.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.screen.MultiplayerScreen;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public abstract class ServerDialog
extends class_437
implements MinecraftClientAccess {
    protected static final float acu = 36.0f;
    protected static final float acv = 34.0f;
    private static final float acw = 22.0f;
    protected final MultiplayerScreen acx;
    private final String acy;
    private final String acz;
    private final boolean acA;
    protected final List<DialogField> acB = new ArrayList<DialogField>();
    protected final List<DialogButton> acC = new ArrayList<DialogButton>();
    private int acD = -1;
    private final Animation acE = MenuChrome.anim();
    private final Animation acF = MenuChrome.anim();
    private final float[] acG = new float[4];
    private final float[] acH = new float[4];
    private float uiScale = 1.0f;
    private int vw = 1;
    private int vh = 1;
    private long Rf;

    private static String c(Instance instance, String string, float f) {
        int n;
        if (string == null || string.isEmpty() || instance.getWidth(string) <= f) {
            return string;
        }
        float f2 = instance.getWidth("\u2026");
        for (n = string.length(); n > 0 && instance.getWidth(string.substring(0, n)) + f2 > f; --n) {
        }
        return string.substring(0, n) + "\u2026";
    }

    protected ServerDialog(MultiplayerScreen multiplayerScreen, String string, String string2, boolean bl) {
        super((class_2561)class_2561.method_43470((String)string));
        this.acx = multiplayerScreen;
        this.acy = string;
        this.acz = string2;
        this.acA = bl;
    }

    protected String pA() {
        return null;
    }

    protected abstract boolean pC();

    protected float pB() {
        return 460.0f;
    }

    private float pW() {
        float f = 22.0f + Fonts.GOLOS_SEMI.getFont(17.0f).getHeight() + 18.0f;
        if (this.pA() != null) {
            f += Fonts.GOLOS.getFont(12.5f).getHeight() + 14.0f;
        }
        for (DialogField object : this.acB) {
            f += Fonts.GOLOS.getFont(11.0f).getHeight() + 6.0f + 36.0f + 14.0f;
        }
        for (DialogButton dialogButton : this.acC) {
            f += Fonts.GOLOS.getFont(11.0f).getHeight() + 6.0f + 34.0f + 14.0f;
        }
        return f + 44.0f + 22.0f;
    }

    private void lY() {
        if (aiG == null || aiG.method_22683() == null) {
            return;
        }
        this.uiScale = Math.max(0.05f, (float)aiG.method_22683().method_4502() / 1080.0f);
        this.vw = Math.max(1, Math.round((float)aiG.method_22683().method_4486() / this.uiScale));
        this.vh = Math.max(1, Math.round((float)aiG.method_22683().method_4502() / this.uiScale));
    }

    public void method_25394(class_332 class_3322, int n, int n2, float f) {
        Object object;
        int n3;
        float f2;
        float f3;
        float f4;
        Object object2;
        Instance instance;
        this.lY();
        MenuChrome.tickMusic();
        class_4587 class_45872 = class_3322.method_51448();
        class_45872.method_22903();
        class_45872.method_22905(this.uiScale, this.uiScale, 1.0f);
        int n4 = Math.round((float)n / this.uiScale);
        int n5 = Math.round((float)n2 / this.uiScale);
        MenuChrome.drawScreenBackground(class_45872, this.vw, this.vh, 0.0f, 0.0f);
        Render2D.drawRoundedRect(class_45872, 0.0f, 0.0f, this.vw, this.vh, 0.0f, new Color(4, 7, 6, 140));
        if (this.Rf == 0L) {
            this.Rf = System.currentTimeMillis();
        }
        float f5 = class_3532.method_15363((float)((float)(System.currentTimeMillis() - this.Rf) / 200.0f), (float)0.0f, (float)1.0f);
        float f6 = 1.0f - (1.0f - f5) * (1.0f - f5) * (1.0f - f5);
        class_45872.method_46416(0.0f, (1.0f - f6) * 14.0f, 0.0f);
        float f7 = this.pB();
        float f8 = this.pW();
        float f9 = (float)this.vw / 2.0f - f7 / 2.0f;
        float f10 = (float)this.vh / 2.0f - f8 / 2.0f;
        MenuPanelRenderer.n(class_45872, f9, f10, f7, f8);
        float f11 = f10 + 22.0f;
        Instance instance2 = Fonts.GOLOS_SEMI.getFont(17.0f);
        Render2D.drawFont(class_45872, instance2, this.acy, f9 + 22.0f, f11, MenuChrome.TEXT);
        f11 += instance2.getHeight() + 18.0f;
        String string = this.pA();
        if (string != null) {
            instance = Fonts.GOLOS.getFont(12.5f);
            Render2D.drawFont(class_45872, instance, ServerDialog.c(instance, string, f7 - 44.0f), f9 + 22.0f, f11, MenuChrome.MUTED2);
            f11 += instance.getHeight() + 14.0f;
        }
        instance = Fonts.GOLOS.getFont(11.0f);
        Instance instance3 = Fonts.GOLOS.getFont(12.5f);
        for (int i = 0; i < this.acB.size(); ++i) {
            object2 = this.acB.get(i);
            Render2D.drawFont(class_45872, instance, ((DialogField)object2).acM, f9 + 22.0f, f11, MenuChrome.MUTED3);
            float f12 = f7 - 44.0f;
            boolean bl = MenuChrome.hit(n4, n5, f9 + 22.0f, f11 += instance.getHeight() + 6.0f, f12, 36.0f);
            float f13 = MenuChrome.upd(((DialogField)object2).acP, bl || this.acD == i);
            MenuPanelRenderer.c(class_45872, f9 + 22.0f, f11, f12, 36.0f, f13, false);
            f4 = class_3532.method_15363((float)((float)(System.currentTimeMillis() - ((DialogField)object2).acQ) / 150.0f), (float)0.0f, (float)1.0f);
            float f14 = f3 = this.acD == i ? 1.0f - f4 : 0.0f;
            if (this.acD == i) {
                Render2D.drawBorder(class_45872, f9 + 22.0f, f11, f12, 36.0f, 7.0f, 1.0f, 1.4f, MenuChrome.accentA(0.42f * f13));
            }
            float f15 = f9 + 22.0f + 13.0f;
            f2 = f11 + 18.0f - instance3.getHeight() / 2.0f;
            if (((DialogField)object2).Ko.isEmpty() && this.acD != i) {
                Render2D.drawFont(class_45872, instance3, ((DialogField)object2).acN, f15, f2, MenuChrome.MUTED3);
            } else {
                boolean bl2;
                int n6;
                float f16 = f12 - 26.0f;
                n3 = class_3532.method_15340((int)((DialogField)object2).acS, (int)0, (int)((DialogField)object2).Ko.length());
                for (n6 = 0; n6 < n3 && instance3.getWidth(((DialogField)object2).Ko.substring(n6, n3)) > f16; ++n6) {
                }
                object = ((DialogField)object2).Ko.substring(n6);
                while (!((String)object).isEmpty() && instance3.getWidth((String)object) > f16) {
                    object = ((String)object).substring(0, ((String)object).length() - 1);
                }
                if (this.acD == i && ((DialogField)object2).acT && !((String)object).isEmpty()) {
                    Render2D.drawRoundedRect(class_45872, f15 - 2.0f, f11 + 18.0f - 7.5f, instance3.getWidth((String)object) + 4.0f, 15.0f, 3.0f, MenuChrome.accentA(0.28f));
                }
                boolean bl3 = bl2 = ((DialogField)object2).acR && f4 < 1.0f && !((String)object).isEmpty() && ((DialogField)object2).acS == ((DialogField)object2).Ko.length();
                if (bl2) {
                    String string2 = ((String)object).substring(0, ((String)object).length() - 1);
                    String string3 = ((String)object).substring(((String)object).length() - 1);
                    Render2D.drawFont(class_45872, instance3, string2, f15, f2, MenuChrome.TEXT2);
                    float f17 = 1.0f - (1.0f - f4) * (1.0f - f4);
                    int n7 = Math.round(f17 * 255.0f);
                    Color color = new Color(MenuChrome.TEXT2.getRed(), MenuChrome.TEXT2.getGreen(), MenuChrome.TEXT2.getBlue(), n7);
                    Render2D.drawFont(class_45872, instance3, string3, f15 + instance3.getWidth(string2), f2 + (1.0f - f17) * 5.0f, color);
                } else {
                    Render2D.drawFont(class_45872, instance3, (String)object, f15, f2, MenuChrome.TEXT2);
                }
                if (this.acD == i) {
                    float f18 = 0.3f + 0.7f * (0.5f + 0.5f * (float)Math.sin((double)System.currentTimeMillis() / 210.0));
                    f18 = Math.min(1.0f, f18 + 0.5f * f3);
                    Render2D.drawRoundedRect(class_45872, f15 + instance3.getWidth(((DialogField)object2).Ko.substring(n6, n3)) + 1.5f, f11 + 18.0f - 6.0f, 1.5f, 12.0f, 0.75f, MenuChrome.accentA(f18));
                }
            }
            MenuChrome.set(((DialogField)object2).acO, f9 + 22.0f, f11, f12, 36.0f);
            f11 += 50.0f;
        }
        Instance instance4 = Fonts.GOLOS.getFont(12.0f);
        for (DialogButton dialogButton : this.acC) {
            Render2D.drawFont(class_45872, instance, dialogButton.acI, f9 + 22.0f, f11, MenuChrome.MUTED3);
            f11 += instance.getHeight() + 6.0f;
            float f19 = f7 - 44.0f;
            int n8 = dialogButton.acJ.length;
            f4 = 6.0f;
            f3 = (f19 - f4 * (float)(n8 - 1)) / (float)n8;
            for (int i = 0; i < n8; ++i) {
                f2 = f9 + 22.0f + (float)i * (f3 + f4);
                boolean bl = dialogButton.aby == i;
                boolean optionHovered = MenuChrome.hit(n4, n5, f2, f11, f3, 34.0f);
                float f20 = MenuChrome.upd(dialogButton.acL[i], optionHovered && !bl);
                if (bl) {
                    object = MenuChrome.accent();
                    Render2D.drawRoundedRect(class_45872, f2, f11, f3, 34.0f, 7.0f, (Color)object);
                    Render2D.drawRoundedRect(class_45872, f2 + 7.0f, f11 + 1.0f, f3 - 14.0f, 1.0f, 0.5f, MenuChrome.white(0.35f));
                } else {
                    Render2D.drawRoundedRect(class_45872, f2, f11, f3, 34.0f, 7.0f, MenuChrome.white(0.03f + 0.03f * f20));
                    Render2D.drawBorder(class_45872, f2, f11, f3, 34.0f, 7.0f, 1.0f, 1.4f, MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.22f), f20));
                }
                object = dialogButton.acJ[i];
                Color color = bl ? MenuChrome.INK : MenuChrome.lerp(MenuChrome.MUTED2, MenuChrome.TEXT2, f20);
                Render2D.drawFont(class_45872, instance4, (String)object, f2 + (f3 - instance4.getWidth((String)object)) / 2.0f, f11 + 17.0f - instance4.getHeight() / 2.0f, color);
                MenuChrome.set(dialogButton.acK[i], f2, f11, f3, 34.0f);
            }
            f11 += 48.0f;
        }
        object2 = Fonts.UNBOUNDED.getFont(11.0f);
        float f21 = 44.0f;
        float f22 = f10 + f8 - 22.0f - f21;
        float f23 = 24.0f + ((Instance)object2).getWidth(this.acz) + 24.0f;
        f4 = f9 + f7 - 22.0f - f23;
        boolean bl = MenuChrome.hit(n4, n5, f4, f22, f23, f21);
        float f24 = MenuChrome.upd(this.acE, bl);
        this.a(class_45872, f4, f22, f23, f21, f24, (Instance)object2);
        MenuChrome.set(this.acG, f4, f22, f23, f21);
        String string4 = "\u041e\u0442\u043c\u0435\u043d\u0430";
        float f25 = 18.0f + ((Instance)object2).getWidth(string4) + 18.0f;
        float f26 = f4 - 9.0f - f25;
        boolean bl4 = MenuChrome.hit(n4, n5, f26, f22, f25, f21);
        float f27 = MenuChrome.upd(this.acF, bl4);
        Render2D.drawRoundedRect(class_45872, f26, f22, f25, f21, 8.0f, MenuChrome.white(0.04f));
        Render2D.drawBorder(class_45872, f26, f22, f25, f21, 8.0f, 1.0f, 1.4f, MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.24f), f27));
        Render2D.drawFont(class_45872, (Instance)object2, string4, f26 + 18.0f, f22 + f21 / 2.0f - ((Instance)object2).getHeight() / 2.0f, MenuChrome.TEXT2);
        MenuChrome.set(this.acH, f26, f22, f25, f21);
        MenuPanelRenderer.p(class_45872, f9, f10, f7, f8);
        class_45872.method_22909();
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, Instance instance) {
        float f6 = f2 - f5;
        Color color = this.acA ? MenuChrome.DANGER : MenuChrome.accent();
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 90);
        MenuChrome.glow(class_45872, f, f6, f3, f4, 8.0f, color2, 4.0f, 5, 2.4f);
        int n = Math.min(255, Math.round((float)color.getRed() * (1.0f + 0.07f * f5)));
        int n2 = Math.min(255, Math.round((float)color.getGreen() * (1.0f + 0.07f * f5)));
        int n3 = Math.min(255, Math.round((float)color.getBlue() * (1.0f + 0.07f * f5)));
        Render2D.drawRoundedRect(class_45872, f, f6, f3, f4, 8.0f, new Color(n, n2, n3));
        Render2D.drawRoundedRect(class_45872, f + 8.0f, f6 + 1.0f, f3 - 16.0f, 1.0f, 0.5f, MenuChrome.white(0.35f));
        Render2D.drawFont(class_45872, instance, this.acz, f + (f3 - instance.getWidth(this.acz)) / 2.0f, f6 + f4 / 2.0f - instance.getHeight() / 2.0f, MenuChrome.INK);
    }

    private void a(DialogField dialogField) {
        String string = ServerDialog.aiG.field_1774.method_1460();
        if (string != null) {
            dialogField.cz(string.replace("\r", "").replace("\n", " ").trim());
        }
    }

    public boolean method_25402(double d, double d2, int n) {
        int n2;
        float f;
        float f2;
        if (n == 1) {
            f2 = (float)(d / (double)this.uiScale);
            f = (float)(d2 / (double)this.uiScale);
            for (n2 = 0; n2 < this.acB.size(); ++n2) {
                if (!MenuChrome.in(this.acB.get((int)n2).acO, f2, f)) continue;
                this.acD = n2;
                this.a(this.acB.get(n2));
                return true;
            }
        }
        if (n != 0) {
            return super.method_25402(d, d2, n);
        }
        f2 = (float)(d / (double)this.uiScale);
        f = (float)(d2 / (double)this.uiScale);
        if (MenuChrome.in(this.acH, f2, f)) {
            this.pV();
            return true;
        }
        if (MenuChrome.in(this.acG, f2, f)) {
            if (this.pC()) {
                this.pV();
            }
            return true;
        }
        this.acD = -1;
        for (n2 = 0; n2 < this.acB.size(); ++n2) {
            this.acB.get((int)n2).acT = false;
            if (!MenuChrome.in(this.acB.get((int)n2).acO, f2, f)) continue;
            this.acD = n2;
        }
        block2: for (DialogButton dialogButton : this.acC) {
            for (int i = 0; i < dialogButton.acJ.length; ++i) {
                if (!MenuChrome.in(dialogButton.acK[i], f2, f)) continue;
                dialogButton.aby = i;
                continue block2;
            }
        }
        return true;
    }

    public boolean method_25404(int n, int n2, int n3) {
        if (n == 256) {
            this.pV();
            return true;
        }
        if (n == 257 || n == 335) {
            if (this.pC()) {
                this.pV();
            }
            return true;
        }
        if (n == 258 && !this.acB.isEmpty()) {
            this.acD = (this.acD + 1) % this.acB.size();
            return true;
        }
        if (this.acD >= 0) {
            boolean bl;
            DialogField dialogField = this.acB.get(this.acD);
            boolean bl2 = bl = n == 260 && (n3 & 1) != 0;
            if (class_437.method_25437((int)n) || bl) {
                this.a(dialogField);
                return true;
            }
            if (class_437.method_25438((int)n)) {
                if (!dialogField.Ko.isEmpty()) {
                    ServerDialog.aiG.field_1774.method_1455(dialogField.Ko);
                }
                return true;
            }
            if (class_437.method_25436((int)n)) {
                if (!dialogField.Ko.isEmpty()) {
                    ServerDialog.aiG.field_1774.method_1455(dialogField.Ko);
                }
                dialogField.dH();
                return true;
            }
            if (class_437.method_25439((int)n)) {
                dialogField.acT = !dialogField.Ko.isEmpty();
                dialogField.acS = dialogField.Ko.length();
                return true;
            }
            switch (n) {
                case 259: {
                    dialogField.pX();
                    return true;
                }
                case 261: {
                    dialogField.pY();
                    return true;
                }
                case 263: {
                    dialogField.acT = false;
                    dialogField.acS = Math.max(0, dialogField.acS - 1);
                    return true;
                }
                case 262: {
                    dialogField.acT = false;
                    dialogField.acS = Math.min(dialogField.Ko.length(), dialogField.acS + 1);
                    return true;
                }
                case 268: {
                    dialogField.acT = false;
                    dialogField.acS = 0;
                    return true;
                }
                case 269: {
                    dialogField.acT = false;
                    dialogField.acS = dialogField.Ko.length();
                    return true;
                }
            }
        }
        return super.method_25404(n, n2, n3);
    }

    public boolean method_25400(char c, int n) {
        if (this.acD >= 0 && c >= ' ') {
            this.acB.get(this.acD).cz(String.valueOf(c));
            return true;
        }
        return super.method_25400(c, n);
    }

    protected void method_25426() {
        if (this.acD < 0 && !this.acB.isEmpty()) {
            this.acD = 0;
        }
    }

    protected void pV() {
        MenuChrome.keepMusicAcrossSwap();
        aiG.method_1507((class_437)this.acx);
    }

    public void method_25432() {
        MenuChrome.onMenuScreenRemoved();
        super.method_25432();
    }

    public boolean method_25422() {
        return false;
    }

    public void method_25420(class_332 class_3322, int n, int n2, float f) {
    }

    protected static final class DialogField {
        final String acM;
        final String acN;
        String Ko;
        final float[] acO = new float[4];
        final Animation acP = MenuChrome.anim();
        long acQ;
        boolean acR;
        int acS;
        boolean acT;

        DialogField(String string, String string2, String string3) {
            this.acM = string;
            this.acN = string2;
            this.Ko = string3 == null ? "" : string3;
            this.acS = this.Ko.length();
        }

        void ab(boolean bl) {
            this.acQ = System.currentTimeMillis();
            this.acR = bl;
        }

        void cz(String string) {
            if (string == null || string.isEmpty()) {
                return;
            }
            if (this.acT) {
                this.Ko = "";
                this.acS = 0;
                this.acT = false;
            }
            this.acS = class_3532.method_15340((int)this.acS, (int)0, (int)this.Ko.length());
            this.Ko = this.Ko.substring(0, this.acS) + string + this.Ko.substring(this.acS);
            this.acS += string.length();
            this.ab(true);
        }

        void pX() {
            if (this.acT) {
                this.dH();
                return;
            }
            this.acS = class_3532.method_15340((int)this.acS, (int)0, (int)this.Ko.length());
            if (this.acS > 0) {
                this.Ko = this.Ko.substring(0, this.acS - 1) + this.Ko.substring(this.acS);
                --this.acS;
            }
            this.ab(false);
        }

        void pY() {
            if (this.acT) {
                this.dH();
                return;
            }
            this.acS = class_3532.method_15340((int)this.acS, (int)0, (int)this.Ko.length());
            if (this.acS < this.Ko.length()) {
                this.Ko = this.Ko.substring(0, this.acS) + this.Ko.substring(this.acS + 1);
            }
            this.ab(false);
        }

        void dH() {
            this.Ko = "";
            this.acS = 0;
            this.acT = false;
            this.ab(false);
        }
    }

    protected static final class DialogButton {
        final String acI;
        final String[] acJ;
        int aby;
        final float[][] acK;
        final Animation[] acL;

        DialogButton(String string, int n, String ... stringArray) {
            this.acI = string;
            this.acJ = stringArray;
            this.aby = class_3532.method_15340((int)n, (int)0, (int)(stringArray.length - 1));
            this.acK = new float[stringArray.length][4];
            this.acL = new Animation[stringArray.length];
            for (int i = 0; i < this.acL.length; ++i) {
                this.acL[i] = MenuChrome.anim();
            }
        }
    }
}
