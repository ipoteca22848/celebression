package ru.method.celebression.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_332;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.manager.InventorySetupManager;
import ru.method.celebression.inventory.InventorySetupModel;
import ru.method.celebression.render.MenuPanelRenderer;
import ru.method.celebression.screen.SettingsSubScreen;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.mainmenu.MenuChrome;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class InventorySetupManagerScreen
extends SettingsSubScreen {
    private static final float Rx = 1060.0f;
    private static final float Ry = 600.0f;
    private static final float Rz = 20.0f;
    private static final float RA = 64.0f;
    private static final float RB = 452.0f;
    private static final float RC = 20.0f;
    private static final float RD = 54.0f;
    private static final float RE = 8.0f;
    private static final float RF = 14.0f;
    private static final float RG = 34.0f;
    private static final float RH = 42.0f;
    private static final float RI = 4.0f;
    private static final float RJ = 410.0f;
    private static final int[] RK = new int[]{39, 38, 37, 36};
    private InventorySetupModel RL;
    private String RM = "";
    private boolean RN;
    private float Ll;
    private float scroll;
    private float Lm;
    private long Ln;
    private long Lk;
    private InventorySetupModel RP;
    private long Lq;
    private final Animation RQ = MenuChrome.anim();
    private final Animation RR = MenuChrome.anim();
    private final Animation RS = MenuChrome.anim();
    private final Animation RT = MenuChrome.anim();
    private final Map<InventorySetupModel, Animation> RV = new IdentityHashMap<InventorySetupModel, Animation>();
    private final Map<InventorySetupModel, Animation> RW = new IdentityHashMap<InventorySetupModel, Animation>();
    private final Map<InventorySetupModel, Animation> RX = new IdentityHashMap<InventorySetupModel, Animation>();
    private final float[] RY = new float[4];
    private final float[] RZ = new float[4];
    private final float[] Sa = new float[4];
    private final float[] Sb = new float[4];
    private final List<Object[]> Sc = new ArrayList<Object[]>();
    private boolean Sd;

    public InventorySetupManagerScreen(class_437 class_4372) {
        super(class_4372, "\u041c\u0435\u043d\u0435\u0434\u0436\u0435\u0440 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435\u0439");
        this.RL = InventorySetupManager.mj();
        List<InventorySetupModel> list = InventorySetupManager.mi();
        if (this.RL == null && !list.isEmpty()) {
            this.RL = list.get(0);
        }
        boolean bl = this.Sd = !InventorySetupManager.mn();
        if (this.Sd) {
            InventorySetupManager.x(true);
        }
    }

    @Override
    protected String kt() {
        return "\u043d\u0430\u0437\u0430\u0434";
    }

    private float kw() {
        return Math.min(600.0f, Math.max(420.0f, (float)this.vh - 100.0f));
    }

    @Override
    protected void a(class_332 class_3322, class_4587 class_45872, int n, int n2) {
        float f = 1060.0f;
        float f2 = this.kw();
        float f3 = this.av(f);
        float f4 = (float)this.vh / 2.0f - f2 * 0.54f;
        MenuPanelRenderer.n(class_45872, f3, f4, f, f2);
        this.a(class_45872, f3, f4, f, n, n2);
        Render2D.drawRoundedRect(class_45872, f3, f4 + 64.0f - 1.0f, f, 1.0f, 0.0f, MenuChrome.white(0.07f));
        float f5 = f4 + 64.0f;
        float f6 = f2 - 64.0f;
        float f7 = f3 + f - 20.0f - 452.0f;
        float f8 = f7 - 20.0f;
        Render2D.drawRoundedRect(class_45872, f8, f5, 1.0f, f6, 0.0f, MenuChrome.white(0.07f));
        float f9 = f3 + 20.0f;
        float f10 = f8 - 20.0f - f9;
        this.b(class_3322, class_45872, f9, f5, f10, f6, n, n2);
        this.c(class_3322, class_45872, f7, f5, 452.0f, f6, n, n2);
        MenuPanelRenderer.p(class_45872, f3, f4, f, f2);
        if (this.Sd) {
            this.a(class_45872, f3, f4, f, f2, n, n2);
        }
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, int n, int n2) {
        float f4 = f2 + 32.0f;
        Instance instance = Fonts.GOLOS_SEMI.getFont(17.0f);
        String string = "\u041c\u0435\u043d\u0435\u0434\u0436\u0435\u0440 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435\u0439";
        Render2D.drawFont(class_45872, instance, string, f + 20.0f, f4 - instance.getHeight() / 2.0f, MenuChrome.TEXT);
        Instance instance2 = Fonts.PLEXMONO.getFont(10.0f);
        String string2 = String.valueOf(InventorySetupManager.mi().size());
        float f5 = instance2.getWidth(string2) + 16.0f;
        float f6 = 20.0f;
        float f7 = f + 20.0f + instance.getWidth(string) + 10.0f;
        float f8 = f4 - f6 / 2.0f;
        Render2D.drawRoundedRect(class_45872, f7, f8, f5, f6, f6 / 2.0f, MenuChrome.accentA(0.12f));
        Render2D.drawFont(class_45872, instance2, string2, f7 + 8.0f, f8 + f6 / 2.0f - instance2.getHeight() / 2.0f, MenuChrome.accent());
        this.a(class_45872, f + f3 - 20.0f, f4, n, n2);
    }

    private void a(class_4587 class_45872, float f, float f2, int n, int n2) {
        boolean bl = InventorySetupManager.mm();
        String string = bl ? "\u041e\u0432\u0435\u0440\u043b\u0435\u0439 \u0432\u043a\u043b\u044e\u0447\u0451\u043d" : "\u041e\u0432\u0435\u0440\u043b\u0435\u0439 \u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d";
        Instance instance = Fonts.GOLOS.getFont(12.0f);
        float f3 = 30.0f;
        float f4 = 31.0f + instance.getWidth(string) + 16.0f;
        float f5 = f - f4;
        float f6 = f2 - f3 / 2.0f;
        float f7 = MenuChrome.upd(this.RS, MenuChrome.hit(n, n2, f5, f6, f4, f3));
        Render2D.drawRoundedRect(class_45872, f5, f6, f4, f3, f3 / 2.0f, bl ? MenuChrome.accentA(0.1f + 0.04f * f7) : MenuChrome.white(0.04f + 0.03f * f7));
        Render2D.drawBorder(class_45872, f5, f6, f4, f3, f3 / 2.0f, 1.0f, 1.4f, bl ? MenuChrome.accentA(0.35f) : MenuChrome.white(0.08f + 0.1f * f7));
        float f8 = f5 + 15.0f;
        float f9 = f2;
        if (bl) {
            MenuChrome.glow(class_45872, f8 - 3.5f, f9 - 3.5f, 7.0f, 7.0f, 3.5f, MenuChrome.accentA(0.3f), 0.0f, 4, 1.7f);
            Render2D.drawCircle(class_45872, f8, f9, 3.5f, MenuChrome.accent());
        } else {
            Render2D.drawCircle(class_45872, f8, f9, 3.5f, MenuChrome.MUTED3);
        }
        Render2D.drawFont(class_45872, instance, string, f5 + 15.0f + 7.0f + 9.0f, f2 - instance.getHeight() / 2.0f, bl ? MenuChrome.TEXT3 : MenuChrome.MUTED2);
        MenuChrome.set(this.Sa, f5, f6, f4, f3);
    }

    private void b(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        float f5 = f2 + 18.0f;
        float f6 = 44.0f;
        float f7 = this.b("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c", true);
        float f8 = f3 - 10.0f - f7;
        boolean bl = this.qH();
        float f9 = MenuChrome.upd(this.RQ, MenuChrome.hit(n, n2, f, f5, f8, f6) || this.RN);
        MenuPanelRenderer.c(class_45872, f, f5, f8, f6, f9, false);
        Instance instance = Fonts.GOLOS.getFont(12.5f);
        float f10 = f + 16.0f;
        float f11 = f5 + f6 / 2.0f;
        if (this.RM.isEmpty() && !this.RN) {
            Render2D.drawFont(class_45872, instance, "\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044f...", f10, f11 - instance.getHeight() / 2.0f, MenuChrome.MUTED3);
        } else {
            Render2D.drawFont(class_45872, instance, this.RM, f10, f11 - instance.getHeight() / 2.0f, MenuChrome.TEXT2);
            if (this.RN) {
                this.c(class_45872, f10 + instance.getWidth(this.RM) + 1.0f, f11);
            }
        }
        MenuChrome.set(this.RY, f, f5, f8, f6);
        float f12 = f + f8 + 10.0f;
        float f13 = MenuChrome.upd(this.RR, bl && MenuChrome.hit(n, n2, f12, f5, f7, f6));
        this.a(class_45872, f12, f5, f7, f6, true, "plus", "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c", f13, bl);
        MenuChrome.set(this.RZ, f12, f5, f7, f6);
        Instance instance2 = Fonts.GOLOS.getFont(10.5f);
        float f14 = f5 + f6 + 20.0f;
        MenuChrome.drawSpaced(class_45872, instance2, "\u0421\u041e\u0425\u0420\u0410\u041d\u0415\u041d\u0418\u042f", f, f14, MenuChrome.MUTED4, 1.4f);
        float f15 = 18.0f;
        float f16 = f2 + f4 - 22.0f - f15;
        ClickGuiIcons.info(class_45872, f + 6.0f, f16 + f15 / 2.0f, 12.0f, MenuChrome.MUTED3);
        Instance instance3 = Fonts.GOLOS.getFont(11.0f);
        Render2D.drawFont(class_45872, instance3, "\u0417\u0430\u0433\u0440\u0443\u0436\u0435\u043d\u043d\u044b\u0439 \u0448\u0430\u0431\u043b\u043e\u043d \u043f\u043e\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0435\u0442 \u043d\u0435\u0434\u043e\u0441\u0442\u0430\u044e\u0449\u0438\u0435 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u043f\u0440\u0438\u0437\u0440\u0430\u0447\u043d\u044b\u043c\u0438 \u0438\u043a\u043e\u043d\u043a\u0430\u043c\u0438", f + 18.0f, f16 + f15 / 2.0f - instance3.getHeight() / 2.0f, MenuChrome.MUTED2);
        float f17 = f14 + instance2.getHeight() + 12.0f;
        this.a(class_3322, class_45872, f, f17, f3, f16 - 12.0f - f17, n, n2);
    }

    private void a(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        float f5;
        this.Sc.clear();
        List<InventorySetupModel> list = InventorySetupManager.mi();
        float f6 = 28.0f + (float)list.size() * 54.0f + (float)Math.max(0, list.size() - 1) * 8.0f;
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
            String string = "\u041d\u0435\u0442 \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u0439";
            Render2D.drawFont(class_45872, instance, string, f + f3 / 2.0f - instance.getWidth(string) / 2.0f, f2 + f4 / 2.0f - instance.getHeight() / 2.0f, MenuChrome.MUTED2);
            return;
        }
        Render2D.startScissor(class_3322, f, f2, f3, f4);
        long l2 = Math.max(this.Rf, this.Lk);
        for (int i = 0; i < list.size(); ++i) {
            InventorySetupModel inventorySetupModel = list.get(i);
            f5 = f2 + 14.0f + (float)i * 62.0f - this.scroll;
            float f9 = class_3532.method_15363((float)((float)(l - l2 - (long)i * 16L) / 220.0f), (float)0.0f, (float)1.0f);
            float f10 = f5 + (1.0f - (f9 = 1.0f - (1.0f - f9) * (1.0f - f9))) * 12.0f;
            if (f10 + 54.0f < f2 || f10 > f2 + f4) continue;
            float f11 = f3 - 8.0f;
            this.a(class_45872, inventorySetupModel, f, f10, f11, n, n2);
            float f12 = 32.0f;
            float f13 = f10 + (54.0f - f12) / 2.0f;
            float f14 = f + f11 - 12.0f - f12;
            float f15 = this.c(inventorySetupModel);
            float f16 = f14 - 8.0f - f15;
            this.a(f16, f13, f15, f12, f2, f4, inventorySetupModel, "load");
            this.a(f14, f13, f12, f12, f2, f4, inventorySetupModel, "del");
            float f17 = Math.max(f10, f2);
            float f18 = Math.min(f10 + 54.0f, f2 + f4);
            if (!(f18 - f17 >= 1.0f)) continue;
            this.Sc.add(new Object[]{new float[]{f, f17, f11, f18 - f17}, inventorySetupModel, "row"});
        }
        Render2D.stopScissor(class_3322);
        if (f7 > 0.5f) {
            float f19 = f4 - 8.0f;
            float f20 = Math.max(24.0f, f19 * (f4 / f6));
            f5 = f2 + 4.0f + (f19 - f20) * (this.scroll / f7);
            Render2D.drawRoundedRect(class_45872, f + f3 - 6.0f, f5, 3.0f, f20, 1.5f, MenuChrome.accentA(0.5f));
        }
    }

    private void a(float f, float f2, float f3, float f4, float f5, float f6, InventorySetupModel inventorySetupModel, String string) {
        float f7 = Math.max(f2, f5);
        float f8 = Math.min(f2 + f4, f5 + f6);
        if (f8 - f7 >= 1.0f) {
            this.Sc.add(new Object[]{new float[]{f, f7, f3, f8 - f7}, inventorySetupModel, string});
        }
    }

    private void a(class_4587 class_45872, InventorySetupModel inventorySetupModel2, float f, float f2, float f3, int n, int n2) {
        boolean bl = inventorySetupModel2 == this.RL;
        boolean bl2 = MenuChrome.hit(n, n2, f, f2, f3, 54.0f);
        float f4 = MenuChrome.upd(this.RV.computeIfAbsent(inventorySetupModel2, inventorySetupModel -> MenuChrome.anim()), bl2);
        if (bl) {
            Render2D.drawRoundedRect(class_45872, f, f2, f3, 54.0f, 10.0f, MenuChrome.accentA(0.05f));
        } else {
            Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, 54.0f, 10.0f, new Color(24, 30, 27, 179), new Color(15, 19, 17, 179));
        }
        Color color = bl ? MenuChrome.accentA(0.55f) : MenuChrome.lerp(MenuChrome.white(0.08f), MenuChrome.white(0.22f), f4);
        Render2D.drawBorder(class_45872, f, f2, f3, 54.0f, 10.0f, 1.0f, 1.4f, color);
        if (bl) {
            Render2D.drawRoundedRect(class_45872, f + 2.0f, f2 + 8.0f, 2.0f, 38.0f, 1.0f, MenuChrome.accent());
        }
        float f5 = f + 14.0f;
        float f6 = f2 + 10.0f;
        Render2D.drawRoundedRect(class_45872, f5, f6, 34.0f, 34.0f, 8.0f, bl ? MenuChrome.accentA(0.1f) : MenuChrome.white(0.04f));
        Render2D.drawBorder(class_45872, f5, f6, 34.0f, 34.0f, 8.0f, 1.0f, 1.4f, bl ? MenuChrome.accentA(0.45f) : MenuChrome.white(0.08f));
        ClickGuiIcons.cube(class_45872, f5 + 17.0f, f6 + 17.0f, 15.0f, bl ? MenuChrome.accent() : MenuChrome.MUTED);
        float f7 = f5 + 34.0f + 14.0f;
        float f8 = f2 + 27.0f;
        Instance instance = Fonts.UNBOUNDED.getFont(11.5f);
        Render2D.drawFont(class_45872, instance, inventorySetupModel2.name, f7, f8 - instance.getHeight() - 2.0f, MenuChrome.TEXT);
        Instance instance2 = Fonts.GOLOS.getFont(12.0f);
        Render2D.drawFont(class_45872, instance2, inventorySetupModel2.SI + " \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432", f7, f8 + 3.0f, MenuChrome.MUTED2);
        float f9 = 32.0f;
        float f10 = f2 + (54.0f - f9) / 2.0f;
        float f11 = f + f3 - 12.0f - f9;
        boolean bl3 = inventorySetupModel2 == this.RP && System.currentTimeMillis() < this.Lq;
        float f12 = MenuChrome.upd(this.RX.computeIfAbsent(inventorySetupModel2, inventorySetupModel -> MenuChrome.anim()), MenuChrome.hit(n, n2, f11, f10, f9, f9) || bl3);
        MenuPanelRenderer.c(class_45872, f11, f10, f9, f9, f12, true);
        ClickGuiIcons.trash(class_45872, f11 + f9 / 2.0f, f10 + f9 / 2.0f, 14.0f, bl3 ? MenuChrome.DANGER : MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.DANGER, f12));
        boolean bl4 = InventorySetupManagerScreen.a(inventorySetupModel2);
        float f13 = this.c(inventorySetupModel2);
        float f14 = f11 - 8.0f - f13;
        float f15 = MenuChrome.upd(this.RW.computeIfAbsent(inventorySetupModel2, inventorySetupModel -> MenuChrome.anim()), MenuChrome.hit(n, n2, f14, f10, f13, f9));
        this.a(class_45872, f14, f10, f13, f9, bl4, bl4 ? "check" : "arrowRight", InventorySetupManagerScreen.b(inventorySetupModel2), f15, true);
    }

    private static boolean a(InventorySetupModel inventorySetupModel) {
        return inventorySetupModel == InventorySetupManager.mj();
    }

    private static String b(InventorySetupModel inventorySetupModel) {
        return InventorySetupManagerScreen.a(inventorySetupModel) ? "\u0417\u0430\u0433\u0440\u0443\u0436\u0435\u043d" : "\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c";
    }

    private float c(InventorySetupModel inventorySetupModel) {
        return this.b(InventorySetupManagerScreen.b(inventorySetupModel), InventorySetupManagerScreen.a(inventorySetupModel));
    }

    private void c(class_332 class_3322, class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        float f5;
        float f6;
        Instance instance;
        InventorySetupModel inventorySetupModel = this.RL;
        float f7 = f2 + 16.0f;
        float f8 = 24.0f;
        float f9 = f7 + f8 / 2.0f;
        ClickGuiIcons.search(class_45872, f + 8.0f, f9, 14.0f, MenuChrome.MUTED);
        Instance instance2 = Fonts.GOLOS_SEMI.getFont(13.0f);
        Render2D.drawFont(class_45872, instance2, "\u041f\u0440\u043e\u0441\u043c\u043e\u0442\u0440", f + 20.0f, f9 - instance2.getHeight() / 2.0f, MenuChrome.TEXT2);
        float f10 = f + 20.0f + instance2.getWidth("\u041f\u0440\u043e\u0441\u043c\u043e\u0442\u0440") + 10.0f;
        if (inventorySetupModel != null) {
            instance = Fonts.GOLOS.getFont(11.0f);
            float f11 = instance.getWidth(inventorySetupModel.name) + 16.0f;
            f6 = 19.0f;
            f5 = f9 - f6 / 2.0f;
            Render2D.drawRoundedRect(class_45872, f10, f5, f11, f6, f6 / 2.0f, MenuChrome.accentA(0.12f));
            Render2D.drawFont(class_45872, instance, inventorySetupModel.name, f10 + 8.0f, f5 + f6 / 2.0f - instance.getHeight() / 2.0f, MenuChrome.accent());
        }
        instance = Fonts.GOLOS_SEMI.getFont(9.0f);
        String string = (inventorySetupModel == null ? 0 : inventorySetupModel.SI) + " \u043f\u0440\u0435\u0434\u043c.";
        Render2D.drawFont(class_45872, instance, string, f + f3 - instance.getWidth(string), f9 - instance.getHeight() / 2.0f, MenuChrome.MUTED2);
        f6 = f + (f3 - 410.0f) / 2.0f;
        f5 = f2 + 56.0f;
        for (int i = 0; i < 4; ++i) {
            this.a(class_3322, class_45872, f6 + (float)i * 46.0f, f5, this.a(inventorySetupModel, RK[i]));
        }
        this.a(class_3322, class_45872, f6 + 410.0f - 42.0f, f5, this.a(inventorySetupModel, 40));
        float f12 = f5 + 42.0f + 16.0f;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(class_3322, class_45872, f6 + (float)j * 46.0f, f12 + (float)i * 46.0f, this.a(inventorySetupModel, 9 + i * 9 + j));
            }
        }
        float f13 = f12 + 126.0f + 8.0f;
        Render2D.drawRoundedRect(class_45872, f6, f13 + 14.0f, 410.0f, 1.0f, 0.0f, MenuChrome.white(0.07f));
        float f14 = f13 + 28.0f;
        for (int i = 0; i < 9; ++i) {
            this.a(class_3322, class_45872, f6 + (float)i * 46.0f, f14, this.a(inventorySetupModel, i));
        }
    }

    private class_1799 a(InventorySetupModel inventorySetupModel, int n) {
        if (inventorySetupModel == null || n < 0 || n >= inventorySetupModel.SH.length) {
            return class_1799.field_8037;
        }
        class_1799 class_17992 = inventorySetupModel.SH[n];
        return class_17992 == null ? class_1799.field_8037 : class_17992;
    }

    private void a(class_332 class_3322, class_4587 class_45872, float f, float f2, class_1799 class_17992) {
        boolean bl = class_17992.method_7960();
        Render2D.drawRoundedRect(class_45872, f, f2, 42.0f, 42.0f, 6.0f, MenuChrome.white(bl ? 0.03f : 0.05f));
        Render2D.drawBorder(class_45872, f, f2, 42.0f, 42.0f, 6.0f, 1.0f, 1.4f, MenuChrome.white(bl ? 0.07f : 0.11f));
        if (bl) {
            return;
        }
        float f3 = 2.625f;
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22905(1.0f, 1.0f, this.uiScale);
        class_45872.method_22905(f3, f3, f3);
        RenderSystem.enableDepthTest();
        if (InventorySetupManagerScreen.aiG.field_1724 != null) {
            class_3322.method_51423((class_1309)InventorySetupManagerScreen.aiG.field_1724, class_17992, 0, 0, 0);
        } else {
            class_3322.method_51427(class_17992, 0, 0);
        }
        class_3322.method_51431(InventorySetupManagerScreen.aiG.field_1772, class_17992, 0, 0);
        RenderSystem.disableDepthTest();
        class_45872.method_22909();
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, int n, int n2) {
        long l = System.currentTimeMillis();
        float f5 = class_3532.method_15363((float)((float)(l - this.Rf) / 260.0f), (float)0.0f, (float)1.0f);
        f5 = 1.0f - (1.0f - f5) * (1.0f - f5) * (1.0f - f5);
        float f6 = class_3532.method_15363((float)((float)(l - this.Rf - 10000L) / 220.0f), (float)0.0f, (float)1.0f);
        if (f6 >= 1.0f) {
            this.Sd = false;
            return;
        }
        float f7 = f5 * (1.0f - f6);
        float f8 = (1.0f - f5) * 8.0f + f6 * 8.0f;
        float f9 = 620.0f;
        float f10 = 76.0f;
        float f11 = f + f3 / 2.0f - f9 / 2.0f;
        float f12 = f2 + f4 - 92.0f - f10 + f8;
        Render2D.drawRoundedRect(class_45872, f11, f12, f9, f10, 12.0f, InventorySetupManagerScreen.b(new Color(17, 23, 20), 0.96f * f7));
        Render2D.drawBorder(class_45872, f11, f12, f9, f10, 12.0f, 1.0f, 1.4f, MenuChrome.white(0.1f * f7));
        float f13 = 36.0f;
        float f14 = f11 + 18.0f;
        float f15 = f12 + (f10 - f13) / 2.0f;
        Render2D.drawRoundedRect(class_45872, f14, f15, f13, f13, 9.0f, MenuChrome.accentA(0.12f * f7));
        ClickGuiIcons.cube(class_45872, f14 + f13 / 2.0f, f15 + f13 / 2.0f, 16.0f, MenuChrome.accentA(f7));
        Instance instance = Fonts.GOLOS_SEMI.getFont(13.0f);
        Instance instance2 = Fonts.GOLOS.getFont(11.5f);
        float f16 = f14 + f13 + 14.0f;
        float f17 = f11 + f9 - 14.0f - 26.0f - 10.0f - f16;
        Render2D.drawFont(class_45872, instance, InventorySetupManagerScreen.clip(instance, "\u041c\u0435\u043d\u0435\u0434\u0436\u0435\u0440 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435\u0439", f17), f16, f12 + 20.0f, InventorySetupManagerScreen.b(MenuChrome.TEXT, f7));
        Render2D.drawFont(class_45872, instance2, InventorySetupManagerScreen.clip(instance2, "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u0435 \u0440\u0430\u0441\u043a\u043b\u0430\u0434\u043a\u0443 \u0438 \u0437\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u0435 \u0435\u0451 \u043a\u0430\u043a \u0448\u0430\u0431\u043b\u043e\u043d \u2014 \u043d\u0435\u0434\u043e\u0441\u0442\u0430\u044e\u0449\u0435\u0435 \u043f\u043e\u0434\u0441\u0432\u0435\u0442\u0438\u0442\u0441\u044f \u0432 \u0438\u0433\u0440\u0435.", f17), f16, f12 + 40.0f, InventorySetupManagerScreen.b(MenuChrome.MUTED2, f7));
        float f18 = 26.0f;
        float f19 = f11 + f9 - 14.0f - f18;
        float f20 = f12 + (f10 - f18) / 2.0f;
        float f21 = MenuChrome.upd(this.RT, MenuChrome.hit(n, n2, f19, f20, f18, f18));
        Render2D.drawRoundedRect(class_45872, f19, f20, f18, f18, 7.0f, MenuChrome.white((0.04f + 0.05f * f21) * f7));
        ClickGuiIcons.close(class_45872, f19 + f18 / 2.0f, f20 + f18 / 2.0f, 12.0f, InventorySetupManagerScreen.b(MenuChrome.lerp(MenuChrome.MUTED, MenuChrome.TEXT2, f21), f7));
        MenuChrome.set(this.Sb, f19, f20, f18, f18);
    }

    private static Color b(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), class_3532.method_15340((int)Math.round((float)color.getAlpha() * f), (int)0, (int)255));
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

    private float b(String string, boolean bl) {
        Instance instance = (bl ? Fonts.UNBOUNDED : Fonts.UNBOUNDED_L).getFont(11.0f);
        return 42.0f + instance.getWidth(string) + 18.0f;
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, boolean bl, String string, String string2, float f5, boolean bl2) {
        Color color;
        float f6 = bl2 ? 1.0f : 0.4f;
        Color color2 = bl ? MenuChrome.INK : new Color(MenuChrome.TEXT2.getRed(), MenuChrome.TEXT2.getGreen(), MenuChrome.TEXT2.getBlue(), Math.round(255.0f * f6));
        float f7 = f2 - f5;
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
        float f8 = f + 18.0f + 7.5f;
        float f9 = f7 + f4 / 2.0f;
        if ("plus".equals(string)) {
            ClickGuiIcons.plus(class_45872, f8, f9, 15.0f, color);
        } else if ("check".equals(string)) {
            ClickGuiIcons.check(class_45872, f8, f9, 15.0f, color);
        } else {
            ClickGuiIcons.arrowRight(class_45872, f8, f9, 15.0f, color);
        }
        Instance instance = (bl ? Fonts.UNBOUNDED : Fonts.UNBOUNDED_L).getFont(11.0f);
        Render2D.drawFont(class_45872, instance, string2, f + 18.0f + 15.0f + 9.0f, f7 + f4 / 2.0f - instance.getHeight() / 2.0f, color2);
    }

    @Override
    protected boolean a(float f, float f2, int n) {
        if (n != 0) {
            return false;
        }
        if (this.Sd && MenuChrome.in(this.Sb, f, f2)) {
            this.Sd = false;
            this.kB();
            return true;
        }
        if (MenuChrome.in(this.Sa, f, f2)) {
            InventorySetupManager.w(!InventorySetupManager.mm());
            this.kB();
            return true;
        }
        this.RN = MenuChrome.in(this.RY, f, f2);
        if (this.RN) {
            return true;
        }
        if (MenuChrome.in(this.RZ, f, f2)) {
            this.save();
            return true;
        }
        for (Object[] objectArray : this.Sc) {
            String string;
            float[] fArray = (float[])objectArray[0];
            if (!MenuChrome.hit(f, f2, fArray[0], fArray[1], fArray[2], fArray[3])) continue;
            InventorySetupModel inventorySetupModel = (InventorySetupModel)objectArray[1];
            switch (string = (String)objectArray[2]) {
                case "del": {
                    this.d(inventorySetupModel);
                    break;
                }
                case "load": {
                    this.RL = inventorySetupModel;
                    this.RP = null;
                    InventorySetupManager.h(inventorySetupModel);
                    this.kB();
                    break;
                }
                default: {
                    this.RL = inventorySetupModel;
                    this.RP = null;
                    this.kB();
                }
            }
            return true;
        }
        return false;
    }

    private void save() {
        InventorySetupModel inventorySetupModel;
        if (!this.qH()) {
            return;
        }
        String string = this.RM.trim();
        if (string.isEmpty()) {
            string = this.me();
        }
        if ((inventorySetupModel = InventorySetupManager.cc(string)) != null) {
            this.RL = inventorySetupModel;
        }
        this.RM = "";
        this.RN = false;
        this.RP = null;
        this.Lk = System.currentTimeMillis();
        this.kB();
    }

    private String me() {
        String string;
        List<InventorySetupModel> list = InventorySetupManager.mi();
        int n = list.size() + 1;
        block0: while (true) {
            string = "inv" + n;
            for (InventorySetupModel inventorySetupModel : list) {
                if (!string.equals(inventorySetupModel.name)) continue;
                ++n;
                continue block0;
            }
            break;
        }
        return string;
    }

    private void d(InventorySetupModel inventorySetupModel) {
        if (inventorySetupModel == this.RP && System.currentTimeMillis() < this.Lq) {
            InventorySetupManager.i(inventorySetupModel);
            this.RP = null;
            this.RV.remove(inventorySetupModel);
            this.RW.remove(inventorySetupModel);
            this.RX.remove(inventorySetupModel);
            if (this.RL == inventorySetupModel) {
                List<InventorySetupModel> list = InventorySetupManager.mi();
                this.RL = list.isEmpty() ? null : list.get(0);
            }
            this.Lk = System.currentTimeMillis();
        } else {
            this.RP = inventorySetupModel;
            this.Lq = System.currentTimeMillis() + 2500L;
        }
        this.kB();
    }

    @Override
    protected boolean a(float f, float f2, double d) {
        this.Lm -= (float)d * 62.0f * 0.9f;
        return true;
    }

    @Override
    protected boolean c(int n, int n2, int n3) {
        if (!this.RN) {
            return false;
        }
        if (n == 259) {
            if (!this.RM.isEmpty()) {
                this.RM = this.RM.substring(0, this.RM.length() - 1);
            }
            return true;
        }
        if (n == 257 || n == 335) {
            this.save();
            return true;
        }
        return false;
    }

    @Override
    protected boolean a(char c, int n) {
        if (this.RN && c >= ' ' && this.RM.length() < 24) {
            this.RM = this.RM + c;
            return true;
        }
        return false;
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
}

