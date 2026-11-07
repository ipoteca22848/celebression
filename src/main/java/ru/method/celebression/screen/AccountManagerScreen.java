package ru.method.celebression.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_1068;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.service.IasIntegration;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.screen.SettingsSubScreen;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class AccountManagerScreen
extends SettingsSubScreen {
    private static final float KI = 620.0f;
    private static final float KJ = 130.0f;
    private static final float KK = 20.0f;
    private static final float KL = 64.0f;
    private static final float KM = 72.0f;
    private static final float KN = 52.0f;
    private static final float KO = 8.0f;
    private static final float KP = 16.0f;
    private static final float KQ = 30.0f;
    private List<IasIntegration.IntegrationState> KR = new ArrayList<IasIntegration.IntegrationState>();
    private UUID KS;
    private final Animation KT = MenuChrome.anim();
    private final Animation KU = MenuChrome.anim();
    private final Animation KV = MenuChrome.anim();
    private final Animation KW = MenuChrome.anim();
    private final Animation KX = MenuChrome.anim();
    private final Animation KY = MenuChrome.anim();
    private final float[] KZ = new float[4];
    private final float[] La = new float[4];
    private final float[] Lb = new float[4];
    private final float[] Lc = new float[4];
    private final float[] Ld = new float[4];
    private final float[] Le = new float[4];
    private final List<float[]> Lf = new ArrayList<float[]>();
    private final Map<UUID, Animation> Lg = new HashMap<UUID, Animation>();
    private final Map<UUID, class_2960> Lh = new HashMap<UUID, class_2960>();
    private String Li = "";
    private boolean Lj;
    private long Lk;
    private float Ll;
    private float scroll;
    private float Lm;
    private long Ln;
    private long Lo;
    private UUID Lp;
    private long Lq;
    private AccountEntry Lr = AccountEntry.LB;
    private String Ls = "";
    private boolean Lt;
    private long Lu;
    private boolean Lv;
    private final Animation Lw = MenuChrome.anim();
    private final Animation Lx = MenuChrome.anim();
    private final float[] Ly = new float[4];
    private final float[] Lz = new float[4];
    private final float[] LA = new float[4];

    public AccountManagerScreen(class_437 class_4372) {
        super(class_4372, "\u0410\u043a\u043a\u0430\u0443\u043d\u0442\u044b");
        this.refresh();
        UUID uUID = IasIntegration.kG();
        for (IasIntegration.IntegrationState integrationState : this.KR) {
            if (!integrationState.kK().equals(uUID)) continue;
            this.KS = integrationState.kK();
            break;
        }
        if (this.KS == null && !this.KR.isEmpty()) {
            this.KS = this.KR.get(0).kK();
        }
    }

    private void refresh() {
        this.KR = IasIntegration.kI();
    }

    @Override
    protected String kt() {
        return "\u043d\u0430\u0437\u0430\u0434";
    }

    private List<IasIntegration.IntegrationState> ku() {
        String string = this.Li.trim().toLowerCase(Locale.ROOT);
        if (string.isEmpty()) {
            return this.KR;
        }
        ArrayList<IasIntegration.IntegrationState> arrayList = new ArrayList<IasIntegration.IntegrationState>();
        for (IasIntegration.IntegrationState integrationState : this.KR) {
            if (!integrationState.name().toLowerCase(Locale.ROOT).contains(string)) continue;
            arrayList.add(integrationState);
        }
        return arrayList;
    }

    private IasIntegration.IntegrationState kv() {
        if (this.KS == null) {
            return null;
        }
        for (IasIntegration.IntegrationState integrationState : this.KR) {
            if (!integrationState.kK().equals(this.KS)) continue;
            return integrationState;
        }
        return null;
    }

    private boolean a(IasIntegration.IntegrationState integrationState) {
        UUID uUID = IasIntegration.kG();
        String string = IasIntegration.kH();
        return uUID != null && uUID.equals(integrationState.kK()) && integrationState.name().equals(string);
    }

    private float kw() {
        int n = Math.max(1, this.ku().size());
        float f = 32.0f + (float)n * 52.0f + (float)Math.max(0, n - 1) * 8.0f;
        float f2 = 64.0f + f + 72.0f;
        float f3 = (float)this.vh - 130.0f - 74.0f;
        return Math.min(f2, Math.max(188.0f, f3));
    }

    @Override
    protected void a(class_332 class_3322, class_4587 class_45872, int n, int n2) {
        float f = this.av(620.0f);
        float f2 = 130.0f;
        float f3 = 620.0f;
        float f4 = this.kw();
        MenuPanelRenderer.n(class_45872, f, f2, f3, f4);
        this.a(class_45872, f, f2, f3, n, n2);
        Render2D.drawRoundedRect(class_45872, f, f2 + 64.0f - 1.0f, f3, 1.0f, 0.0f, MenuChrome.white(0.07f));
        this.a(class_3322, class_45872, f, f2 + 64.0f, f3, f4 - 64.0f - 72.0f, n, n2);
        this.b(class_45872, f, f2 + f4 - 72.0f, f3, n, n2);
        MenuPanelRenderer.p(class_45872, f, f2, f3, f4);
        if (this.Lr != AccountEntry.LB) {
            this.a(class_45872, n, n2);
        }
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 32.0f;
        Instance instance = Fonts.GOLOS_SEMI.getFont(17.0f);
        float f5 = f + 20.0f;
        Render2D.drawFont(class_45872, instance, "\u0410\u043a\u043a\u0430\u0443\u043d\u0442\u044b", f5, f4 - instance.getHeight() / 2.0f, MenuChrome.TEXT);
        float f6 = f5 + instance.getWidth("\u0410\u043a\u043a\u0430\u0443\u043d\u0442\u044b") + 10.0f;
        Instance instance2 = Fonts.GOLOS.getFont(11.5f);
        String string = String.valueOf(this.KR.size());
        float f7 = instance2.getWidth(string) + 16.0f;
        float f8 = 20.0f;
        float f9 = f4 - f8 / 2.0f;
        Render2D.drawRoundedRect(class_45872, f6, f9, f7, f8, f8 / 2.0f, MenuChrome.accentA(0.12f));
        Render2D.drawFont(class_45872, instance2, string, f6 + 8.0f, f9 + f8 / 2.0f - instance2.getHeight() / 2.0f, MenuChrome.accent());
        float f10 = 230.0f;
        float f11 = 36.0f;
        float f12 = f + f3 - 20.0f - f10;
        float f13 = f4 - f11 / 2.0f;
        boolean bl = MenuChrome.hit(n, n2, f12, f13, f10, f11);
        float f14 = MenuChrome.upd(this.KT, bl || this.Lj);
        MenuPanelRenderer.c(class_45872, f12, f13, f10, f11, f14, false);
        ClickGuiIcons.search(class_45872, f12 + 20.0f, f13 + f11 / 2.0f, 16.0f, MenuChrome.lerp(MenuChrome.MUTED3, MenuChrome.accent(), this.Lj ? 1.0f : 0.0f));
        Instance instance3 = Fonts.GOLOS.getFont(12.5f);
        float f15 = f12 + 37.0f;
        if (this.Li.isEmpty() && !this.Lj) {
            Render2D.drawFont(class_45872, instance3, "\u041f\u043e\u0438\u0441\u043a \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u0430...", f15, f13 + f11 / 2.0f - instance3.getHeight() / 2.0f, MenuChrome.MUTED3);
        } else {
            Render2D.drawFont(class_45872, instance3, this.Li, f15, f13 + f11 / 2.0f - instance3.getHeight() / 2.0f, MenuChrome.TEXT2);
            if (this.Lj) {
                this.c(class_45872, f15 + instance3.getWidth(this.Li) + 1.0f, f13 + f11 / 2.0f);
            }
        }
        MenuChrome.set(this.KZ, f12, f13, f10, f11);
    }

    private void c(class_4587 class_45872, float f, float f2) {
        if (this.Ll <= 0.0f) {
            this.Ll = f;
        }
        this.Ll += (f - this.Ll) * 0.4f;
        float f3 = 0.35f + 0.65f * (0.5f + 0.5f * (float)Math.sin((double)System.currentTimeMillis() / 260.0));
        Color color = new Color(MenuChrome.TEXT2.getRed(), MenuChrome.TEXT2.getGreen(), MenuChrome.TEXT2.getBlue(), Math.round(255.0f * f3));
        Render2D.drawRoundedRect(class_45872, this.Ll, f2 - 6.0f, 1.0f, 12.0f, 0.5f, color);
    }

    private void a(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        float f5;
        this.Lf.clear();
        List<IasIntegration.IntegrationState> list = this.ku();
        float f6 = 32.0f + (float)list.size() * 52.0f + (float)Math.max(0, list.size() - 1) * 8.0f;
        float f7 = Math.max(0.0f, f6 - f4);
        long l = System.currentTimeMillis();
        float f8 = this.Ln == 0L ? 0.016f : Math.min(0.05f, (float)(l - this.Ln) / 1000.0f);
        this.Ln = l;
        this.Lm = class_3532.method_15363((float)this.Lm, (float)0.0f, (float)f7);
        this.scroll += (this.Lm - this.scroll) * (1.0f - (float)Math.exp(-13.0f * f8));
        if (Math.abs(this.Lm - this.scroll) < 0.4f) {
            this.scroll = this.Lm;
        }
        if (list.isEmpty()) {
            Instance instance = Fonts.GOLOS.getFont(13.0f);
            String string = this.KR.isEmpty() ? "\u041d\u0435\u0442 \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u043e\u0432" : "\u041d\u0438\u0447\u0435\u0433\u043e \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e";
            Render2D.drawFont(class_45872, instance, string, f + f3 / 2.0f - instance.getWidth(string) / 2.0f, f2 + f4 / 2.0f - instance.getHeight() / 2.0f, MenuChrome.MUTED2);
            return;
        }
        Render2D.startScissor(class_3322, f, f2, f3, f4);
        long l2 = Math.max(this.Rf, this.Lk);
        for (int i = 0; i < list.size(); ++i) {
            IasIntegration.IntegrationState integrationState = list.get(i);
            f5 = f2 + 16.0f + (float)i * 60.0f - this.scroll;
            float f9 = class_3532.method_15363((float)((float)(l - l2 - (long)i * 16L) / 220.0f), (float)0.0f, (float)1.0f);
            float f10 = f5 + (1.0f - (f9 = 1.0f - (1.0f - f9) * (1.0f - f9))) * 12.0f;
            if (f10 + 52.0f < f2 || f10 > f2 + f4) continue;
            float f11 = f + 20.0f;
            float f12 = f3 - 40.0f - 8.0f;
            this.a(class_45872, integrationState, f11, f10, f12, n, n2);
            float f13 = Math.max(f10, f2);
            float f14 = Math.min(f10 + 52.0f, f2 + f4);
            if (!(f14 - f13 >= 1.0f)) continue;
            this.Lf.add(new float[]{f11, f13, f12, f14 - f13, i});
        }
        Render2D.stopScissor(class_3322);
        if (f7 > 0.5f) {
            float f15 = f4 - 8.0f;
            float f16 = Math.max(24.0f, f15 * (f4 / f6));
            f5 = f2 + 4.0f + (f15 - f16) * (this.scroll / f7);
            Render2D.drawRoundedRect(class_45872, f + f3 - 6.0f, f5, 3.0f, f16, 1.5f, MenuChrome.accentA(0.5f));
        }
    }

    private void a(class_4587 class_45872, IasIntegration.IntegrationState integrationState, float f, float f2, float f3, int n, int n2) {
        boolean bl = this.a(integrationState);
        boolean bl2 = integrationState.kK().equals(this.KS);
        boolean bl3 = MenuChrome.hit(n, n2, f, f2, f3, 52.0f);
        float f4 = MenuChrome.upd(this.Lg.computeIfAbsent(integrationState.kK(), uUID -> MenuChrome.anim()), bl3);
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, 52.0f, 10.0f, new Color(24, 30, 27, 179), new Color(15, 19, 17, 179));
        Color color = bl2 ? MenuChrome.accentA(0.55f) : MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.22f), f4);
        Render2D.drawBorder(class_45872, f, f2, f3, 52.0f, 10.0f, 1.0f, 1.4f, color);
        if (bl) {
            Render2D.drawRoundedRect(class_45872, f + 2.0f, f2 + 8.0f, 3.0f, 36.0f, 1.5f, MenuChrome.accent());
        }
        float f5 = f + 16.0f;
        float f6 = f2 + 11.0f;
        this.a(class_45872, integrationState.kK(), f5, f6);
        Instance instance = Fonts.UNBOUNDED.getFont(13.0f);
        float f7 = f5 + 30.0f + 14.0f;
        Render2D.drawFont(class_45872, instance, integrationState.name(), f7, f2 + 26.0f - instance.getHeight() / 2.0f, MenuChrome.TEXT);
        float f8 = f2 + 26.0f;
        float f9 = f + f3 - 14.0f;
        f9 = this.a(class_45872, f9, f8, integrationState.kL() ? "\u043b\u0438\u0446\u0435\u043d\u0437\u0438\u044f" : "\u043e\u0444\u0444\u043b\u0430\u0439\u043d", integrationState.kL() ? MenuChrome.accent() : MenuChrome.MUTED, integrationState.kL() ? MenuChrome.accentA(0.12f) : MenuChrome.white(0.05f)) - 8.0f;
        if (bl) {
            this.a(class_45872, f9, f8, "\u0442\u0435\u043a\u0443\u0449\u0438\u0439", MenuChrome.INK, MenuChrome.accent());
        }
    }

    private float a(class_4587 class_45872, float f, float f2, String string, Color color, Color color2) {
        Instance instance = Fonts.GOLOS.getFont(11.0f);
        float f3 = 8.0f;
        float f4 = 19.0f;
        float f5 = instance.getWidth(string) + f3 * 2.0f;
        float f6 = f - f5;
        float f7 = f2 - f4 / 2.0f;
        Render2D.drawRoundedRect(class_45872, f6, f7, f5, f4, f4 / 2.0f, color2);
        Render2D.drawFont(class_45872, instance, string, f6 + f3, f7 + f4 / 2.0f - instance.getHeight() / 2.0f, color);
        return f6;
    }

    private void a(class_4587 class_45872, UUID uUID2, float f, float f2) {
        class_2960 class_29602 = this.Lh.computeIfAbsent(uUID2, uUID -> {
            try {
                return class_1068.method_4648((UUID)uUID).comp_1626();
            }
            catch (Throwable throwable) {
                return class_1068.method_4649();
            }
        });
        Render2D.drawRoundedRect(class_45872, f, f2, 30.0f, 30.0f, 6.0f, new Color(0, 0, 0, 90));
        if (class_29602 == null) {
            return;
        }
        Color color = new Color(255, 255, 255, 255);
        Render2D.drawTexture(class_45872, f, f2, 30.0f, 30.0f, 6.0f, 0.125f, 0.125f, 0.125f, 0.125f, class_29602, color);
        Render2D.drawTexture(class_45872, f, f2, 30.0f, 30.0f, 6.0f, 0.625f, 0.125f, 0.125f, 0.125f, class_29602, color);
    }

    private void b(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 36.0f;
        IasIntegration.IntegrationState integrationState = this.kv();
        float f5 = 38.0f;
        float f6 = f + 20.0f;
        float f7 = f4 - f5 / 2.0f;
        boolean bl = integrationState != null && !integrationState.kL();
        boolean bl2 = bl && MenuChrome.hit(n, n2, f6, f7, f5, f5);
        float f8 = MenuChrome.upd(this.KU, bl2);
        MenuPanelRenderer.c(class_45872, f6, f7, f5, f5, f8, false);
        ClickGuiIcons.sliders(class_45872, f6 + f5 / 2.0f, f7 + f5 / 2.0f, 16.0f, bl ? MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.TEXT2, f8) : MenuChrome.MUTED4);
        MenuChrome.set(this.La, f6, f7, f5, f5);
        float f9 = f6 + f5 + 8.0f;
        boolean bl3 = integrationState != null;
        boolean bl4 = System.currentTimeMillis() < this.Lq;
        boolean bl5 = bl3 && MenuChrome.hit(n, n2, f9, f7, f5, f5);
        float f10 = MenuChrome.upd(this.KV, bl5 || bl4);
        MenuPanelRenderer.c(class_45872, f9, f7, f5, f5, f10, true);
        Color color = !bl3 ? MenuChrome.MUTED4 : (bl4 ? MenuChrome.DANGER : MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.DANGER, f10));
        ClickGuiIcons.trash(class_45872, f9 + f5 / 2.0f, f7 + f5 / 2.0f, 15.0f, color);
        MenuChrome.set(this.Lb, f9, f7, f5, f5);
        float f11 = 44.0f;
        float f12 = f4 - f11 / 2.0f;
        boolean bl6 = integrationState != null && !IasIntegration.kF();
        float f13 = this.b("\u0412\u043e\u0439\u0442\u0438", true);
        float f14 = f + f3 - 20.0f - f13;
        float f15 = MenuChrome.upd(this.KY, bl6 && MenuChrome.hit(n, n2, f14, f12, f13, f11));
        this.a(class_45872, f14, f12, f13, f11, true, "arrowRight", "\u0412\u043e\u0439\u0442\u0438", f15, bl6);
        MenuChrome.set(this.Le, f14, f12, f13, f11);
        float f16 = this.b("\u0412\u043e\u0439\u0442\u0438 \u043e\u0444\u0444\u043b\u0430\u0439\u043d", false);
        float f17 = f14 - 10.0f - f16;
        boolean bl7 = !IasIntegration.kF();
        float f18 = MenuChrome.upd(this.KX, bl7 && MenuChrome.hit(n, n2, f17, f12, f16, f11));
        this.a(class_45872, f17, f12, f16, f11, false, "user", "\u0412\u043e\u0439\u0442\u0438 \u043e\u0444\u0444\u043b\u0430\u0439\u043d", f18, bl7);
        MenuChrome.set(this.Ld, f17, f12, f16, f11);
        float f19 = this.b("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c", false);
        float f20 = f17 - 10.0f - f19;
        float f21 = MenuChrome.upd(this.KW, MenuChrome.hit(n, n2, f20, f12, f19, f11));
        this.a(class_45872, f20, f12, f19, f11, false, "plus", "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c", f21, true);
        MenuChrome.set(this.Lc, f20, f12, f19, f11);
    }

    private float b(String string, boolean bl) {
        Instance instance = (bl ? Fonts.UNBOUNDED : Fonts.UNBOUNDED_L).getFont(11.0f);
        return 42.0f + instance.getWidth(string) + 18.0f;
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, boolean bl, String string, String string2, float f5, boolean bl2) {
        Color color;
        float f6 = bl2 ? 1.0f : 0.4f;
        Color color2 = bl ? MenuChrome.INK : new Color(MenuChrome.TEXT2.getRed(), MenuChrome.TEXT2.getGreen(), MenuChrome.TEXT2.getBlue(), Math.round(255.0f * f6));
        float f7 = f2;
        if (bl) {
            color = MenuChrome.accent();
            Render2D.drawRoundedRect(class_45872, f + 4.0f, f7 + f4 - 6.0f, f3 - 8.0f, 12.0f, 8.0f, MenuChrome.accentA((bl2 ? 0.24f : 0.1f) + 0.14f * f5));
            int n = Math.min(255, Math.round((float)color.getRed() * (1.0f + 0.07f * f5)));
            int n2 = Math.min(255, Math.round((float)color.getGreen() * (1.0f + 0.07f * f5)));
            int n3 = Math.min(255, Math.round((float)color.getBlue() * (1.0f + 0.07f * f5)));
            Render2D.drawRoundedRect(class_45872, f, f7, f3, f4, 8.0f, new Color(n, n2, n3, bl2 ? 255 : 130));
            Render2D.drawRoundedRect(class_45872, f + 8.0f, f7 + 1.0f, f3 - 16.0f, 1.0f, 0.5f, MenuChrome.white(0.35f));
        } else {
            MenuChrome.drawSecondaryMaterial(class_45872, f, f7, f3, f4, 8.0f, f5, false);
        }
        color = bl ? MenuChrome.INK : new Color(MenuChrome.MUTED.getRed(), MenuChrome.MUTED.getGreen(), MenuChrome.MUTED.getBlue(), Math.round(255.0f * f6));
        this.a(class_45872, string, f + 18.0f + 7.5f, f7 + f4 / 2.0f, 15.0f, color);
        Instance instance = (bl ? Fonts.UNBOUNDED : Fonts.UNBOUNDED_L).getFont(11.0f);
        Render2D.drawFont(class_45872, instance, string2, f + 18.0f + 15.0f + 9.0f, f7 + f4 / 2.0f - instance.getHeight() / 2.0f, color2);
    }

    private void a(class_4587 class_45872, String string, float f, float f2, float f3, Color color) {
        switch (string) {
            case "arrowRight": {
                ClickGuiIcons.arrowRight(class_45872, f, f2, f3, color);
                break;
            }
            case "user": {
                ClickGuiIcons.user(class_45872, f, f2, f3, color);
                break;
            }
            case "plus": {
                ClickGuiIcons.plus(class_45872, f, f2, f3, color);
                break;
            }
            case "check": {
                ClickGuiIcons.check(class_45872, f, f2, f3, color);
                break;
            }
            default: {
                ClickGuiIcons.user(class_45872, f, f2, f3, color);
            }
        }
    }

    private void a(class_4587 class_45872, int n, int n2) {
        Object object;
        float f;
        float f2;
        Render2D.drawRoundedRect(class_45872, 0.0f, 0.0f, this.vw, this.vh, 0.0f, new Color(0, 0, 0, 130));
        float f3 = 380.0f;
        float f4 = 190.0f;
        float f5 = (float)this.vw / 2.0f - f3 / 2.0f;
        float f6 = (float)this.vh / 2.0f - f4 / 2.0f;
        MenuChrome.set(this.LA, f5, f6, f3, f4);
        MenuPanelRenderer.n(class_45872, f5, f6, f3, f4);
        Instance instance = Fonts.GOLOS_SEMI.getFont(16.0f);
        String string = switch (this.Lr.ordinal()) {
            case 3 -> "\u0412\u043e\u0439\u0442\u0438 \u043e\u0444\u0444\u043b\u0430\u0439\u043d";
            case 2 -> "\u0418\u0437\u043c\u0435\u043d\u0438\u0442\u044c \u043d\u0438\u043a";
            default -> "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043e\u0444\u0444\u043b\u0430\u0439\u043d";
        };
        Render2D.drawFont(class_45872, instance, string, f5 + 22.0f, f6 + 24.0f, MenuChrome.TEXT);
        float f7 = f5 + 22.0f;
        float f8 = f3 - 44.0f;
        float f9 = 40.0f;
        float f10 = f6 + 56.0f;
        boolean bl = this.kx();
        MenuPanelRenderer.c(class_45872, f7, f10, f8, f9, 1.0f, false);
        Instance instance2 = Fonts.GOLOS.getFont(13.0f);
        float f11 = f7 + 14.0f;
        float f12 = f10 + f9 / 2.0f;
        float f13 = f12 - instance2.getHeight() / 2.0f;
        if (this.Ls.isEmpty()) {
            Render2D.drawFont(class_45872, instance2, "\u041d\u0438\u043a (3-16, A-Z 0-9 _)", f11, f13, MenuChrome.MUTED3);
        } else {
            boolean bl2;
            if (this.Lt) {
                float f14 = instance2.getWidth(this.Ls);
                Render2D.drawRoundedRect(class_45872, f11 - 2.0f, f12 - instance2.getHeight() / 2.0f - 2.0f, f14 + 4.0f, instance2.getHeight() + 4.0f, 3.0f, MenuChrome.accentA(0.3f));
            }
            Color color = bl ? MenuChrome.TEXT2 : MenuChrome.DANGER;
            f2 = class_3532.method_15363((float)((float)(System.currentTimeMillis() - this.Lu) / 150.0f), (float)0.0f, (float)1.0f);
            boolean bl3 = bl2 = this.Lv && f2 < 1.0f && !this.Lt;
            if (bl2) {
                String string2 = this.Ls.substring(0, this.Ls.length() - 1);
                String string3 = this.Ls.substring(this.Ls.length() - 1);
                Render2D.drawFont(class_45872, instance2, string2, f11, f13, color);
                f = 1.0f - (1.0f - f2) * (1.0f - f2);
                object = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(f * 255.0f));
                Render2D.drawFont(class_45872, instance2, string3, f11 + instance2.getWidth(string2), f13 + (1.0f - f) * 5.0f, (Color)object);
            } else {
                Render2D.drawFont(class_45872, instance2, this.Ls, f11, f13, color);
            }
        }
        if (!this.Lt) {
            this.c(class_45872, f11 + instance2.getWidth(this.Ls) + 1.0f, f12);
        }
        float f15 = 40.0f;
        f2 = f6 + f4 - 22.0f - f15;
        String string4 = switch (this.Lr.ordinal()) {
            case 3 -> "\u0412\u043e\u0439\u0442\u0438";
            case 2 -> "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c";
            default -> "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c";
        };
        float f16 = this.b(string4, true);
        float f17 = f5 + f3 - 22.0f - f16;
        f = bl ? MenuChrome.upd(this.Lw, MenuChrome.hit(n, n2, f17, f2, f16, f15)) : 0.0f;
        this.a(class_45872, f17, f2, f16, f15, true, this.Lr == AccountEntry.LE ? "play" : "check", string4, f, bl);
        MenuChrome.set(this.Ly, f17, f2, f16, f15);
        object = Fonts.UNBOUNDED_L.getFont(11.0f);
        String string5 = "\u041e\u0442\u043c\u0435\u043d\u0430";
        float f18 = 36.0f + ((Instance)object).getWidth(string5);
        float f19 = f17 - 10.0f - f18;
        float f20 = MenuChrome.upd(this.Lx, MenuChrome.hit(n, n2, f19, f2, f18, f15));
        MenuChrome.drawSecondaryMaterial(class_45872, f19, f2 - f20, f18, f15, 8.0f, f20, false);
        Render2D.drawFont(class_45872, (Instance)object, string5, f19 + (f18 - ((Instance)object).getWidth(string5)) / 2.0f, f2 - f20 + f15 / 2.0f - ((Instance)object).getHeight() / 2.0f, MenuChrome.TEXT3);
        MenuChrome.set(this.Lz, f19, f2, f18, f15);
    }

    private boolean kx() {
        int n = this.Ls.length();
        if (n < 3 || n > 16) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            boolean bl;
            char c = this.Ls.charAt(i);
            boolean bl2 = bl = c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '_';
            if (bl) continue;
            return false;
        }
        return true;
    }

    @Override
    protected boolean a(float f, float f2, int n) {
        if (n != 0) {
            return false;
        }
        if (this.Lr != AccountEntry.LB) {
            if (MenuChrome.in(this.Ly, f, f2)) {
                this.ky();
                return true;
            }
            if (MenuChrome.in(this.Lz, f, f2)) {
                this.kz();
                return true;
            }
            if (!MenuChrome.in(this.LA, f, f2)) {
                this.kz();
            }
            return true;
        }
        this.Lj = MenuChrome.in(this.KZ, f, f2);
        if (this.Lj) {
            return true;
        }
        IasIntegration.IntegrationState integrationState = this.kv();
        if (MenuChrome.in(this.La, f, f2)) {
            if (integrationState != null && !integrationState.kL()) {
                this.Lr = AccountEntry.LD;
                this.Ls = integrationState.name();
                this.kB();
            }
            return true;
        }
        if (MenuChrome.in(this.Lb, f, f2)) {
            if (integrationState != null) {
                this.b(integrationState);
            }
            return true;
        }
        if (MenuChrome.in(this.Lc, f, f2)) {
            this.Lr = AccountEntry.LC;
            this.Ls = "";
            this.kB();
            return true;
        }
        if (MenuChrome.in(this.Ld, f, f2)) {
            if (!IasIntegration.kF()) {
                this.Lr = AccountEntry.LE;
                this.Ls = integrationState != null && !integrationState.kL() ? integrationState.name() : "";
                this.kB();
            }
            return true;
        }
        if (MenuChrome.in(this.Le, f, f2)) {
            if (integrationState != null && !IasIntegration.kF()) {
                IasIntegration.a(integrationState, this.afL);
            }
            return true;
        }
        for (float[] fArray : this.Lf) {
            if (!MenuChrome.hit(f, f2, fArray[0], fArray[1], fArray[2], fArray[3])) continue;
            List<IasIntegration.IntegrationState> list = this.ku();
            int n2 = (int)fArray[4];
            if (n2 < 0 || n2 >= list.size()) {
                return true;
            }
            IasIntegration.IntegrationState integrationState2 = list.get(n2);
            long l = System.currentTimeMillis();
            boolean bl = integrationState2.kK().equals(this.Lp) && l - this.Lo < 300L;
            this.KS = integrationState2.kK();
            this.Lq = 0L;
            this.Lp = integrationState2.kK();
            this.Lo = l;
            if (bl && !IasIntegration.kF()) {
                IasIntegration.a(integrationState2, this.afL);
            }
            return true;
        }
        this.Lj = false;
        return false;
    }

    private void b(IasIntegration.IntegrationState integrationState) {
        if (System.currentTimeMillis() < this.Lq) {
            IasIntegration.c(integrationState);
            this.Lq = 0L;
            this.KS = null;
            this.refresh();
            List<IasIntegration.IntegrationState> list = this.ku();
            if (!list.isEmpty()) {
                this.KS = list.get(0).kK();
            }
            this.Lk = System.currentTimeMillis();
        } else {
            this.Lq = System.currentTimeMillis() + 2500L;
        }
        this.kB();
    }

    private void ky() {
        boolean bl;
        if (!this.kx()) {
            return;
        }
        this.Lq = 0L;
        if (this.Lr == AccountEntry.LE) {
            String string = this.Ls;
            this.kz();
            this.kB();
            IasIntegration.a(string, this.afL);
            return;
        }
        if (this.Lr == AccountEntry.LC) {
            bl = IasIntegration.bY(this.Ls);
        } else {
            IasIntegration.IntegrationState integrationState = this.kv();
            boolean bl2 = bl = integrationState != null && IasIntegration.a(integrationState, this.Ls);
        }
        if (bl) {
            this.Li = "";
            this.refresh();
            for (IasIntegration.IntegrationState integrationState : this.KR) {
                if (!integrationState.name().equals(this.Ls)) continue;
                this.KS = integrationState.kK();
                break;
            }
            this.Lk = System.currentTimeMillis();
        }
        this.kz();
        this.kB();
    }

    private void kz() {
        this.Lr = AccountEntry.LB;
        this.Ls = "";
        this.Lt = false;
        this.Lv = false;
        this.Lu = 0L;
    }

    private void v(boolean bl) {
        this.Lu = System.currentTimeMillis();
        this.Lv = bl;
    }

    private static String bX(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(16);
        for (int i = 0; i < string.length() && stringBuilder.length() < 16; ++i) {
            char c = string.charAt(i);
            if (c < ' ' || c == '\u007f') continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    @Override
    protected boolean a(float f, float f2, double d) {
        if (this.Lr != AccountEntry.LB) {
            return true;
        }
        this.Lm -= (float)d * 60.0f * 0.9f;
        return true;
    }

    @Override
    protected boolean c(int n, int n2, int n3) {
        boolean bl;
        boolean bl2 = bl = (n3 & 2) != 0;
        if (this.Lr != AccountEntry.LB) {
            if (n == 256) {
                this.kz();
                return true;
            }
            if (bl && n == 65) {
                this.Lt = !this.Ls.isEmpty();
                return true;
            }
            if (bl && n == 67) {
                if (!this.Ls.isEmpty()) {
                    AccountManagerScreen.aiG.field_1774.method_1455(this.Ls);
                }
                return true;
            }
            if (bl && n == 88) {
                if (!this.Ls.isEmpty()) {
                    AccountManagerScreen.aiG.field_1774.method_1455(this.Ls);
                }
                this.Ls = "";
                this.Lt = false;
                this.v(false);
                this.kB();
                return true;
            }
            if (bl && n == 86) {
                String string = AccountManagerScreen.bX(AccountManagerScreen.aiG.field_1774.method_1460());
                this.Ls = AccountManagerScreen.bX((this.Lt ? "" : this.Ls) + string);
                this.Lt = false;
                this.v(true);
                this.kB();
                return true;
            }
            if (n == 259) {
                if (this.Lt) {
                    this.Ls = "";
                    this.Lt = false;
                } else if (!this.Ls.isEmpty()) {
                    this.Ls = this.Ls.substring(0, this.Ls.length() - 1);
                }
                this.v(false);
                return true;
            }
            if (n == 257 || n == 335) {
                this.ky();
                return true;
            }
            return true;
        }
        if (this.Lj) {
            if (bl && n == 86) {
                this.Li = AccountManagerScreen.bX(this.Li + AccountManagerScreen.bX(AccountManagerScreen.aiG.field_1774.method_1460()));
                this.kA();
                return true;
            }
            if (bl && n == 65) {
                this.Li = "";
                this.kA();
                return true;
            }
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
        if (this.Lr != AccountEntry.LB) {
            if (c >= ' ' && c != '\u007f') {
                if (this.Lt) {
                    this.Ls = "";
                    this.Lt = false;
                }
                if (this.Ls.length() < 16) {
                    this.Ls = this.Ls + c;
                    this.v(true);
                    this.kB();
                }
            }
            return true;
        }
        if (this.Lj && c >= ' ') {
            this.Li = this.Li + c;
            this.kA();
            return true;
        }
        return false;
    }

    private void kA() {
        this.Lk = System.currentTimeMillis();
        this.Lm = 0.0f;
        this.kB();
    }

    private void kB() {
        try {
            if (aiG == null || aiG.method_1483() == null) {
                return;
            }
            float f = 1.7f + (float)(Math.random() * 0.35);
            aiG.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)((class_3414)class_3417.field_15015.comp_349()), (float)f, (float)0.25f));
        }
        catch (Throwable throwable) {
        }
    }

    static enum AccountEntry {
        LB,
        LC,
        LD,
        LE;
}
}

