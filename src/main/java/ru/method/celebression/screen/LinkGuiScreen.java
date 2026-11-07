package ru.method.celebression.screen;

import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import lombok.Generated;
import net.minecraft.class_1074;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_276;
import net.minecraft.class_2960;
import net.minecraft.class_318;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_490;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ButtonSetting;
import ru.method.celebression.manager.ClickRippleManager;
import ru.method.celebression.ui.ClientTheme;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.manager.ConfigManager;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.modules.impl.client.ElementsModule;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.manager.FriendManager;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.hud.HudNotificationQueue;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.setting.KeyBindParser;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.ui.ModuleSettingsPanel;
import ru.method.celebression.util.NamedItem;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.ui.ScreenTransitionController;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.setting.StringSetting;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.animation.TimedProgress;
import ru.method.celebression.ui.UiInteractionState;
import ru.method.celebression.render.UiRenderUtils;
import ru.method.celebression.ui.UiThemeController;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.screen.clickgui.components.SearchBar;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiIcons;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiPins;
import ru.method.celebression.screen.clickgui.redesign.ClickGuiPlayer;
import ru.method.celebression.screen.clickgui.redesign.ModuleIcons;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.GifCache;
import ru.method.celebression.utils.render.GifRenderer;
import ru.method.celebression.utils.render.GifTexture;
import ru.method.celebression.utils.render.GuiIcons;
import ru.method.celebression.utils.render.GuiSkinTextures;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public class LinkGuiScreen
extends class_437
implements MinecraftClientAccess {
    private static final float Um = 1920.0f;
    private static final float Un = 1080.0f;
    private Color TEXT = new Color(238, 242, 239);
    private Color Uo = new Color(223, 229, 225);
    private Color TEXT3 = new Color(195, 204, 199);
    private Color Up = new Color(168, 177, 172);
    private Color MUTED = new Color(139, 150, 143);
    private Color Uq = new Color(120, 131, 125);
    private Color MUTED3 = new Color(93, 104, 98);
    private Color Ur = new Color(76, 86, 81);
    private Color Us = new Color(11, 18, 14);
    private final Color Ut = new Color(224, 140, 140);
    private boolean Uu = false;
    private static final class_2960 Uv = Celebression.id("textures/gui/confused_spoty.gif");
    private static final long Uw = 560L;
    private static final long Ux = 320L;
    private static final float Uy = 0.02f;
    private final Animation Uz = new Animation(560L, 1.0, true, Easing.aiZ);
    private final Animation UA = new Animation(320L, 1.0, true, Easing.aiR);
    private boolean close = false;
    private final SearchBar UB;
    private String description;
    private ModuleCategory Tm = ModuleCategory.qj;
    private boolean UC = false;
    private float UD;
    private float UE;
    private final Set<String> UF = new HashSet<String>();
    private boolean UG = false;
    private final HashMap<String, float[]> UH = new HashMap();
    private final HashMap<String, float[]> UI = new HashMap();
    private boolean UJ = true;
    private final float[] UK = new float[4];
    private final ClickGuiPlayer UL = new ClickGuiPlayer();
    private final UiInteractionState UM = new UiInteractionState();
    private final Animation UN = new Animation(150L, 1.0, true, Easing.aiZ);
    private float UO = 1.0f;
    private final Animation UP = new Animation(150L, 1.0, true, Easing.aiZ);
    private final Animation UQ = new Animation(150L, 1.0, false, Easing.aiZ);
    private final Animation UR = new Animation(150L, 1.0, false, Easing.aiZ);
    private final Animation US = new Animation(150L, 1.0, false, Easing.aiZ);
    private final SearchBar UT = new SearchBar(0.0f, 0.0f, 0.0f, 0.0f);
    private final SearchBar UU = new SearchBar(0.0f, 0.0f, 0.0f, 0.0f);
    private final SearchBar UV = new SearchBar(0.0f, 0.0f, 0.0f, 0.0f);
    private final SearchBar UW = new SearchBar(0.0f, 0.0f, 0.0f, 0.0f);
    private final SearchBar UX = new SearchBar(0.0f, 0.0f, 0.0f, 0.0f);
    private final SearchBar UY = new SearchBar(0.0f, 0.0f, 0.0f, 0.0f);
    private float UZ;
    private float Va;
    private float Vb;
    private float Vc;
    private float Vd;
    private float Ve;
    private Page Vf = Page.WC;
    private Module Vg;
    private BindSetting Vh;
    private Module Vi;
    private float Vj;
    private float Vk;
    private SettingRenderer Vl;
    private String Vm;
    private float Vn;
    private float Vo;
    private StringSetting Vp;
    private float Vq;
    private static final class_2960 Vr = Celebression.id("client/hue/hue.png");
    private ColorSetting Vs;
    private ColorSetting Vt;
    private float Vu;
    private float Vv;
    private float Vw;
    private float Vx;
    private int Vy;
    private float Vz;
    private float VA;
    private float VB;
    private float VC;
    private float VD;
    private float VE;
    private float VF;
    private float VG;
    private float VH;
    private float VI;
    private float VJ;
    private float VK;
    private float VL;
    private float VM;
    private float VN;
    private float VO;
    private float VP;
    private float VQ;
    private float VR;
    private float VS;
    private float VT;
    private EnumSetting<?> VU;
    private EnumSetting<?> VV;
    private float VW;
    private float VX;
    private float VY;
    private float VZ;
    private float Wa;
    private float Wb;
    private final List<SearchResult> Wc = new ArrayList<SearchResult>();
    private final HashMap<String, Float> Wd = new HashMap();
    private final Set<String> We = new HashSet<String>();
    private final HashMap<String, float[]> Wf = new HashMap();
    private boolean Wg = true;
    private long Wh;
    private float Wi;
    private long Wj;
    private final ArrayList<Section> Wk = new ArrayList();
    private final TimedProgress Wl = new TimedProgress(150L, Easing.aiZ, "");
    private final HashMap<String, TimedProgress> Wm = new HashMap();
    private boolean Wn;
    private float fade = 1.0f;
    private Color Wo = new Color(126, 223, 180);
    private Color Wp = null;
    private float Wq = -1.0f;
    private Color Wr = null;
    private float Ws = 1.0f;
    private float Wt;
    private float Wu;
    private final HashMap<String, List<String>> Wv = new HashMap();
    private String Ww = "";
    private static final Set<String> Wx = Set.of("ViewModel", "HitSound");
    private static final Color Wy = new Color(230, 180, 85);
    private static final Color Wz = new Color(62, 207, 122);
    private final HashMap<String, Float> WA = new HashMap();
    private boolean WB;

    private float[] h(double d, double d2) {
        return new float[]{(float)((d - (double)this.Wt) / (double)this.Ws), (float)((d2 - (double)this.Wu) / (double)this.Ws)};
    }

    public LinkGuiScreen() {
        super((class_2561)class_2561.method_43470((String)"LinkGui"));
        this.UB = new SearchBar(0.0f, 0.0f, 150.0f, 18.0f);
    }

    public void method_25420(class_332 class_3322, int n, int n2, float f) {
    }

        public void method_25394(class_332 class_3322, int n, int n2, float f) {
        if (aiG == null || LinkGuiScreen.aiG.field_1755 != this) {
            return;
        }
        boolean bl = false;
        if (this.close) {
            this.UA.i(false);
            if (this.UA.rk() <= 0.02f) {
                bl = true;
            }
        } else {
            this.Uz.i(true);
        }
        this.fade = this.getUiFade() * ru.method.celebression.modules.impl.client.UI.getGuiTransparency();
        long l = System.currentTimeMillis();
        this.Wi = this.Wh == 0L ? 0.0f : Math.min(0.1f, (float)(l - this.Wh) / 1000.0f);
        this.Wh = l;
        Color color = UiThemeController.oX().pe();
        if (this.Wp == null) {
            this.Wp = color;
        }
        float f2 = 1.0f - (float)Math.exp(-12.0f * this.Wi);
        this.Wp = new Color(Math.round((float)this.Wp.getRed() + (float)(color.getRed() - this.Wp.getRed()) * f2), Math.round((float)this.Wp.getGreen() + (float)(color.getGreen() - this.Wp.getGreen()) * f2), Math.round((float)this.Wp.getBlue() + (float)(color.getBlue() - this.Wp.getBlue()) * f2));
        if (Math.abs(color.getRed() - this.Wp.getRed()) <= 1 && Math.abs(color.getGreen() - this.Wp.getGreen()) <= 1 && Math.abs(color.getBlue() - this.Wp.getBlue()) <= 1) {
            this.Wp = color;
        }
        this.Wo = this.Wp;
        Color color2 = UiThemeController.oX().pf();
        if (this.Wr == null) {
            this.Wr = color2;
        }
        this.Wr = new Color(Math.round((float)this.Wr.getRed() + (float)(color2.getRed() - this.Wr.getRed()) * f2), Math.round((float)this.Wr.getGreen() + (float)(color2.getGreen() - this.Wr.getGreen()) * f2), Math.round((float)this.Wr.getBlue() + (float)(color2.getBlue() - this.Wr.getBlue()) * f2));
        this.nv();
        this.Wc.clear();
        ThemeTransition.h(this.UM.mF());
        LinkGuiRenderContext.begin();
        LinkGuiRenderContext.setFade(1.0f);
        try {
            float f3;
            float f4;
            float f5;
            Render2D.prepareHudFrame(class_3322);
            int n3 = aiG.method_22683().method_4486();
            int n4 = aiG.method_22683().method_4502();
            float f6 = this.close ? class_3532.method_15363((float)this.UA.rk(), (float)0.0f, (float)1.0f) : class_3532.method_15363((float)this.Uz.rk(), (float)0.0f, (float)1.0f);
            float f7 = 12.0f;
            float f8 = Math.min((float)n3 / 1920.0f, (float)n4 / 1080.0f);
            float f9 = Math.min(((float)n3 - f7 * 2.0f) / 1920.0f, ((float)n4 - f7 * 2.0f) / 1080.0f);
            float f10 = Math.min(f9 * ru.method.celebression.modules.impl.client.UI.getInterfaceScale(), f8);
            ModuleIcons.pixelScale(f10);
            float f11 = ((float)n3 - 1920.0f * f10) / 2.0f;
            float f12 = ((float)n4 - 1080.0f * f10) / 2.0f + (1.0f - f6) * 90.0f;
            this.Ws = f10;
            this.Wt = f11;
            this.Wu = f12;
            float f13 = ((float)n - f11) / f10;
            float f14 = ((float)n2 - f12) / f10;
            if (this.Vl != null && this.Vo > 0.0f) {
                if (GLFW.glfwGetMouseButton((long)aiG.method_22683().method_4490(), (int)0) == 1) {
                    this.Vq = class_3532.method_15363((float)((f13 - this.Vn) / this.Vo), (float)0.0f, (float)1.0f);
                    this.Vl.set(this.Vq);
                } else {
                    this.Vl = null;
                    this.Vm = null;
                }
            }
            if (this.Vy != 0 && this.Vs != null) {
                if (GLFW.glfwGetMouseButton((long)aiG.method_22683().method_4490(), (int)0) == 1) {
                    this.q(f13, f14);
                } else {
                    this.Vy = 0;
                }
            }
            class_4587 class_45872 = class_3322.method_51448();
            this.b(class_45872, n3, n4);
            class_45872.method_22903();
            class_45872.method_46416(f11, f12, 0.0f);
            class_45872.method_22905(f10, f10, 1.0f);
            this.a(class_3322, f13, f14);
            this.b(class_3322, f13, f14);
            this.e(class_45872, f13, f14);
            float f15 = class_3532.method_15363((float)this.UP.rk(), (float)0.0f, (float)1.0f);
            if (f15 > 0.01f) {
                f5 = this.fade;
                this.fade = f5 * f15;
                this.c(class_3322, f13, f14);
                this.fade = f5;
            }
            if ((f5 = class_3532.method_15363((float)this.UQ.rk(), (float)0.0f, (float)1.0f)) > 0.01f) {
                f4 = this.fade;
                this.fade = f4 * f5;
                this.d(class_3322, f13, f14);
                this.fade = f4;
            }
            if ((f4 = class_3532.method_15363((float)this.UR.rk(), (float)0.0f, (float)1.0f)) > 0.01f) {
                f3 = this.fade;
                this.fade = f3 * f4;
                this.e(class_3322, f13, f14);
                this.fade = f3;
            }
            if ((f3 = class_3532.method_15363((float)this.US.rk(), (float)0.0f, (float)1.0f)) > 0.01f) {
                float f16 = this.fade;
                this.fade = f16 * f3;
                this.f(class_3322, f13, f14);
                this.fade = f16;
            }
            ClickRippleManager.a(null, null);
            ClickRippleManager.r(0.0f, 1080.0f);
            ClickRippleManager.s(0.0f, 1920.0f);
            ClickRippleManager.nZ();
            List<ModuleSettingsPanel> list = ClickRippleManager.nW();
            list.removeIf(ModuleSettingsPanel::isFinishedClosing);
            LinkGuiRenderContext.setFade(this.fade);
            for (ModuleSettingsPanel moduleSettingsPanel : list) {
                moduleSettingsPanel.render(class_3322, Math.round(f13), Math.round(f14), f);
            }
            LinkGuiRenderContext.setFade(1.0f);
            this.f(class_45872, f13, f14);
            if (this.Vs != null) {
                this.d(class_45872, f13, f14);
            }
            float f17 = this.b("enumdd", this.VU != null ? 1.0f : 0.0f);
            if (this.VU != null) {
                this.VV = this.VU;
            }
            if (this.VV != null && f17 > 0.01f) {
                this.h(class_45872, f13, f14, f17);
            }
            class_45872.method_22909();
            if (this.UM.mH()) {
                this.c(class_3322, n, n2);
            } else if (!this.Wk.isEmpty()) {
                this.Wk.clear();
            }
        }
        finally {
            LinkGuiRenderContext.resetFade();
            LinkGuiRenderContext.end();
            ThemeTransition.end();
        }
        if (bl) {
            this.finishClosing();
        }
        if (Boolean.getBoolean("celebression.guiDebug") && !this.Wn && !this.close && this.Uz.rk() > 0.95f) {
            this.Wn = true;
            try {
                List<Module> list = this.ns();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < Math.min(6, list.size()); ++i) {
                    stringBuilder.append(list.get(i).getName()).append(',');
                }
                Celebression.LOGGER.info("[LinkGui3a] dbg sw={} sh={} scale={} ox={} oy={} cat={} mods={} [{}] grid=({},{},{},{})", new Object[]{aiG.method_22683().method_4486(), aiG.method_22683().method_4502(), Float.valueOf(this.Ws), Float.valueOf(this.Wt), Float.valueOf(this.Wu), this.Tm, list.size(), stringBuilder, Float.valueOf(this.UK[0]), Float.valueOf(this.UK[1]), Float.valueOf(this.UK[2]), Float.valueOf(this.UK[3])});
                class_318.method_22690((File)LinkGuiScreen.aiG.field_1697, (String)"celebression_gui_debug.png", (class_276)aiG.method_1522(), class_25612 -> {});
            }
            catch (Throwable throwable) {
                Celebression.LOGGER.warn("[LinkGui3a] debug capture failed", throwable);
            }
        }
    }

    private void b(class_4587 class_45872, int n, int n2) {
        Color color;
        Color color2 = color = this.Uu ? this.c(0.88f, 140) : this.c(0.55f, 140);
        if (ColorUtils.isBlurEnabled()) {
            Render2D.drawBlurredRect(class_45872, 0.0f, 0.0f, n, n2, 0.0f, 16.0f, color, true);
        } else {
            UiRenderUtils.drawRoundedRect(class_45872, 0.0f, 0.0f, n, n2, 0.0f, this.Uu ? this.c(0.88f, 153) : this.c(0.55f, 153));
        }
    }

    private void a(class_4587 class_45872, Instance instance, float f, float f2, Color color) {
        int n;
        String string = this.Wl.rh();
        if (!this.Wl.rg()) {
            Render2D.drawFont(class_45872, instance, string, f, f2, color);
            return;
        }
        String string2 = this.Wl.ri();
        float f3 = this.Wl.progress();
        int n2 = Math.min(string.length(), string2.length());
        for (n = 0; n < n2 && string.charAt(n) == string2.charAt(n); ++n) {
        }
        String string3 = string.substring(0, n);
        String string4 = string.substring(n);
        String string5 = string2.substring(n);
        Render2D.drawFont(class_45872, instance, string3, f, f2, color);
        float f4 = f + instance.getWidth(string3);
        if (!string5.isEmpty()) {
            Render2D.drawFont(class_45872, instance, string5, f4, f2, this.c(color, 1.0f - f3));
        }
        if (!string4.isEmpty()) {
            Render2D.drawFont(class_45872, instance, string4, f4, f2 + (1.0f - f3) * 3.0f, this.c(color, f3));
        }
    }

    private Color c(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, Math.round((float)color.getAlpha() * class_3532.method_15363((float)f, (float)0.0f, (float)1.0f)))));
    }

    private void a(class_4587 class_45872, Instance instance, String string, String string3, float f, float f2, Color color) {
        TimedProgress timedProgress = this.Wm.computeIfAbsent(string, string2 -> new TimedProgress(160L, Easing.aiZ, string3));
        timedProgress.cF(string3);
        if (!timedProgress.rg()) {
            Render2D.drawFont(class_45872, instance, timedProgress.rh(), f, f2, color);
            return;
        }
        float f3 = timedProgress.progress();
        Render2D.drawFont(class_45872, instance, timedProgress.ri(), f, f2 - f3 * 2.0f, this.c(color, 1.0f - f3));
        Render2D.drawFont(class_45872, instance, timedProgress.rh(), f, f2 + (1.0f - f3) * 2.0f, this.c(color, f3));
    }

    private void c(class_332 class_3322, int n, int n2) {
        boolean bl;
        class_4587 class_45872 = class_3322.method_51448();
        long l = System.currentTimeMillis();
        boolean bl2 = bl = this.Wk.isEmpty() || Math.hypot((float)n - this.Wk.get(this.Wk.size() - 1).x(), (float)n2 - this.Wk.get(this.Wk.size() - 1).y()) > 3.0;
        if (bl) {
            this.Wk.add(new Section(n, n2, l));
        }
        this.Wk.removeIf(section -> l - section.nV() > 450L);
        while (this.Wk.size() > 32) {
            this.Wk.remove(0);
        }
        int n3 = this.Wk.size();
        for (int i = 0; i < n3; ++i) {
            Section section2 = this.Wk.get(i);
            float f = n3 <= 1 ? 1.0f : (float)i / (float)(n3 - 1);
            float f2 = class_3532.method_15363((float)((float)(l - section2.nV()) / 450.0f), (float)0.0f, (float)1.0f);
            float f3 = (1.0f - f2) * (1.0f - f2) * (0.15f + 0.55f * f);
            if (f3 <= 0.02f) continue;
            float f4 = 1.4f + 2.8f * f;
            Render2D.drawCircle(class_45872, section2.x(), section2.y(), f4 * 2.2f, this.as(Math.round(60.0f * f3)));
            Render2D.drawCircle(class_45872, section2.x(), section2.y(), f4, this.as(Math.round(200.0f * f3)));
        }
        Render2D.drawCircle(class_45872, n, n2, 6.5f, this.as(60));
        Render2D.drawCircle(class_45872, n, n2, 3.0f, this.as(230));
        Render2D.drawCircle(class_45872, n, n2, 1.4f, this.at(235));
    }

    private void a(class_332 class_3322, float f, float f2) {
        boolean bl;
        float f3;
        int n;
        int n2 = 0;
        float f4;
        float f5;
        Object object;
        float f6;
        float f7;
        class_4587 class_45872 = class_3322.method_51448();
        float f8 = 40.0f;
        float f9 = 330.0f;
        float f10 = 90.0f;
        float f11 = class_3532.method_15363((float)this.UN.rk(), (float)0.0f, (float)1.0f);
        float f12 = 358.0f;
        if (f11 > 0.01f) {
            f7 = this.fade;
            this.fade = f7 * f11;
            class_45872.method_22903();
            class_45872.method_46416(-10.0f * (1.0f - f11), 0.0f, 0.0f);
            this.a(class_45872, f8, f10, f9, f12, 8.0f, 16.0f, 40, 1.0f);
            this.n(class_45872, f8, f10, f9, f12, 8.0f);
            float f13 = f8 + 16.0f;
            float f14 = f10 + 16.0f;
            f6 = f9 - 32.0f;
            Instance instance = Fonts.GOLOS_SEMI.getFont(13.0f);
            Instance instance2 = Fonts.ICONS2.getFont(12.0f);
            Render2D.drawFont(class_45872, instance2, "A", f13 + 6.0f - instance2.getWidth("A") / 2.0f, f14 + 6.0f - instance2.getHeight() / 2.0f, this.as(255));
            Render2D.drawFont(class_45872, instance, "\u041f\u0440\u043e\u0444\u0438\u043b\u044c", f13 + 18.0f, f14 + 6.0f - instance.getHeight() / 2.0f, this.i(this.Uo));
            float f15 = f13 + 18.0f + instance.getWidth("\u041f\u0440\u043e\u0444\u0438\u043b\u044c") + 10.0f;
            Render2D.drawRoundedRect(class_45872, f15, f14 + 6.0f, f13 + f6 - f15, 1.0f, 0.5f, this.at(18));
            float f16 = f14 + 24.0f;
            this.m(class_45872, f13, f16, 40.0f, 8.0f);
            String string = this.nick();
            object = Fonts.UNBOUNDED.getFont(13.5f);
            Render2D.drawFont(class_45872, (Instance)object, string, f13 + 52.0f, f16 + 3.0f, this.i(this.TEXT));
            Instance instance3 = Fonts.GOLOS.getFont(11.5f);
            f5 = f16 + 25.0f;
            Render2D.drawFont(class_45872, instance3, "\u041f\u043b\u0430\u043d: ", f13 + 52.0f, f5, this.i(this.Uq));
            Render2D.drawFont(class_45872, instance3, "Dev", f13 + 52.0f + instance3.getWidth("\u041f\u043b\u0430\u043d: "), f5, new ColorRGBA(255, 59, 59, 255));
            f4 = f14 + 24.0f + 40.0f + 12.0f;
            float f17 = 238.0f;
            Render2D.drawRoundedRect(class_45872, f13, f4, f6, f17, 8.0f, this.nH());
            Render2D.drawBorder(class_45872, f13, f4, f6, f17, 8.0f, 0.8f, new ColorRGBA(this.at(16)));
            Render2D.drawRoundedVerticalGradient(class_45872, f13, f4 + f17 - 72.0f, f6, 72.0f, 8.0f, this.as(0), this.as(this.Uu ? 30 : 46));
            if (LinkGuiScreen.aiG.field_1724 != null) {
                n2 = Math.round(f13 + 16.0f);
                int n3 = Math.round(f4 + 12.0f);
                int n4 = Math.round(f6 - 32.0f);
                n = Math.round(f17 - 22.0f);
                f3 = class_3532.method_15363((float)this.getUiFade(), (float)0.0f, (float)1.0f);
                float f18 = f11 * f11 * (3.0f - 2.0f * f11) * f3;
                int n5 = Math.round((float)Math.min(n4, n) / 3.05f * f18);
                if (n5 >= 2) {
                    class_490.method_2486((class_332)class_3322, (int)n2, (int)n3, (int)(n2 + n4), (int)(n3 + n), (int)n5, (float)0.0625f, (float)((float)n2 + (float)n4 * 0.5f), (float)((float)n3 + (float)n * 0.8f), (class_1309)LinkGuiScreen.aiG.field_1724);
                }
            }
            Render2D.drawRoundedRect(class_45872, f13 + 24.0f, f4 + f17 - 12.0f, f6 - 48.0f, 1.0f, 0.5f, this.as(90));
            class_45872.method_22909();
            this.fade = f7;
        }
        f7 = class_3532.method_15363((float)this.ng(), (float)0.0f, (float)1.0f);
        this.Wg = bl = !this.nh();
        this.nd();
        HashMap<String, Integer> hashMap = this.a(this.na(), (float)this.nb(), (float)1040.0f, (boolean)bl).WK;
        f6 = f10 + (f12 + 14.0f) * f11;
        float f19 = 90.0f;
        float f20 = 1.0f - (float)Math.exp(-14.0f * this.Wi);
        HashSet<String> hashSet = new HashSet<String>();
        for (String string : new ArrayList<String>(ClickGuiPins.names())) {
            float f21;
            float f22;
            object = this.ci(string);
            if (object == null) continue;
            if (!this.d((Module)object)) {
                ClickGuiPins.unpin(string);
                continue;
            }
            boolean bl2 = this.We.contains(string);
            f5 = this.b("pin:" + string, bl2 ? 0.0f : 1.0f);
            if (bl2 && f5 <= 0.01f) {
                ClickGuiPins.unpin(string);
                this.We.remove(string);
                this.Wd.remove("pin:" + string);
                this.Wf.remove(string);
                continue;
            }
            hashSet.add(string);
            f4 = this.c((Module)object) + 14.0f;
            float[] fArray = this.Wf.get(string);
            int n6 = bl2 ? (fArray == null || fArray[0] < f8 + 300.0f ? 1 : 0) : (n2 = hashMap.getOrDefault(string, 0) == 0 ? 1 : 0);
            if (bl2 && fArray != null) {
                f22 = fArray[0];
                f21 = fArray[1];
            } else {
                f22 = n2 != 0 ? f8 : 1540.0f;
                float f23 = f21 = n2 != 0 ? f6 : f19;
                if (n2 != 0) {
                    f6 += f4 * f5;
                } else {
                    f19 += f4 * f5;
                }
            }
            if (fArray == null) {
                fArray = new float[]{f22, f21};
                this.Wf.put(string, fArray);
            }
            fArray[0] = fArray[0] + (f22 - fArray[0]) * f20;
            fArray[1] = fArray[1] + (f21 - fArray[1]) * f20;
            if (!bl2) {
                fArray[1] = Math.max(f21, Math.min(fArray[1], 1040.0f - this.c((Module)object)));
            }
            int n7 = n = fArray[0] > f8 + 300.0f ? 1 : 0;
            if (n != 0 && !(f7 < 0.99f)) continue;
            f3 = this.fade;
            this.fade = f3 * f5 * (n != 0 ? 1.0f - f7 : 1.0f);
            this.a(class_45872, fArray[0], fArray[1], f9, (Module)object, f, f2);
            this.fade = f3;
        }
        this.Wf.keySet().retainAll(hashSet);
    }

    private float c(Module module) {
        int n = 0;
        for (Setting<?> setting : module.getSettings()) {
            if (!setting.jS() || !(setting instanceof BooleanSetting) && !(setting instanceof EnumSetting) && !(setting instanceof SliderSetting) && !(setting instanceof BindSetting) && !(setting instanceof ButtonSetting) && !(setting instanceof StringSetting) && !(setting instanceof ColorSetting)) continue;
            ++n;
        }
        float f = 30.0f;
        float f2 = 34.0f;
        float f3 = n == 0 ? 70.0f : 12.0f + (float)n * f2;
        return 14.0f + f + f3 + 14.0f;
    }

    private ModulePredicate a(List<String> list, float f, float f2, boolean bl) {
        int n;
        int n2;
        ModulePredicate modulePredicate = new ModulePredicate();
        int n3 = list.size();
        float[] fArray = new float[n3];
        float f3 = 0.0f;
        for (int i = 0; i < n3; ++i) {
            Module module = this.ci(list.get(i));
            fArray[i] = module == null ? 0.0f : this.c(module) + 14.0f;
            f3 += fArray[i];
        }
        float f4 = f2 - f;
        float f5 = f2 - 90.0f;
        if (!bl) {
            for (String string : list) {
                modulePredicate.WK.put(string, 0);
            }
            modulePredicate.WL = f3 <= f4 + 0.5f;
            return modulePredicate;
        }
        int[] nArray = new int[n3];
        float f6 = f;
        float f7 = 90.0f;
        for (n2 = 0; n2 < n3; ++n2) {
            n = f6 <= f + 0.001f || f6 + fArray[n2] <= f2 + 0.5f ? 1 : 0;
            int n4 = nArray[n2] = n != 0 ? 0 : 1;
            if (n != 0) {
                f6 += fArray[n2];
                continue;
            }
            f7 += fArray[n2];
        }
        if (f6 <= f2 + 0.5f && f7 <= f2 + 0.5f) {
            for (n2 = 0; n2 < n3; ++n2) {
                modulePredicate.WK.put(list.get(n2), nArray[n2]);
            }
            modulePredicate.WL = true;
            return modulePredicate;
        }
        int[] nArray2 = this.a(fArray, f4, f5, f3);
        if (nArray2 != null) {
            for (n = 0; n < n3; ++n) {
                modulePredicate.WK.put(list.get(n), nArray2[n]);
            }
            modulePredicate.WL = true;
            return modulePredicate;
        }
        for (n = 0; n < n3; ++n) {
            modulePredicate.WK.put(list.get(n), nArray[n]);
        }
        modulePredicate.WL = false;
        return modulePredicate;
    }

    private int[] a(float[] fArray, float f, float f2, float f3) {
        int n;
        int n2;
        int n3;
        int n4 = fArray.length;
        int n5 = Math.max(0, Math.round(f));
        int n6 = Math.max(0, Math.round(f2));
        int[] nArray = new int[n4];
        int n7 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            nArray[n3] = Math.max(0, Math.round(fArray[n3]));
            n7 += nArray[n3];
        }
        n3 = Math.max(0, n7 - n6);
        if (n3 > n5) {
            return null;
        }
        boolean[][] blArray = new boolean[n4 + 1][n5 + 1];
        blArray[0][0] = true;
        for (n2 = 0; n2 < n4; ++n2) {
            for (n = 0; n <= n5; ++n) {
                if (!blArray[n2][n]) continue;
                blArray[n2 + 1][n] = true;
                if (n + nArray[n2] > n5) continue;
                blArray[n2 + 1][n + nArray[n2]] = true;
            }
        }
        n2 = -1;
        for (n = n5; n >= n3; --n) {
            if (!blArray[n4][n]) continue;
            n2 = n;
            break;
        }
        if (n2 < 0) {
            return null;
        }
        int[] nArray2 = new int[n4];
        int n8 = n2;
        for (int i = n4 - 1; i >= 0; --i) {
            if (blArray[i][n8]) {
                nArray2[i] = 1;
                continue;
            }
            nArray2[i] = 0;
            n8 -= nArray[i];
        }
        return nArray2;
    }

    private List<String> na() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string : ClickGuiPins.names()) {
            Module module;
            if (this.We.contains(string) || (module = this.ci(string)) == null || !this.d(module)) continue;
            arrayList.add(string);
        }
        return arrayList;
    }

    private float nb() {
        float f = 358.0f;
        return 90.0f + (f + 14.0f) * (this.UN.rn() ? 1.0f : 0.0f);
    }

    private void nc() {
        boolean bl = !this.UN.rn();
        this.UN.i(bl);
        if (!bl) {
            return;
        }
        boolean bl2 = !this.nh();
        float f = 1040.0f;
        List<String> list = this.na();
        int n = 0;
        while (!list.isEmpty() && n++ < 128 && !this.a(list, (float)this.nb(), (float)f, (boolean)bl2).WL) {
            this.We.add(list.remove(0));
        }
    }

    private void nd() {
        boolean bl = !this.nh();
        float f = 1040.0f;
        List<String> list = this.na();
        if (this.UN.rn() && !list.isEmpty() && !this.a(list, (float)this.nb(), (float)f, (boolean)bl).WL && this.a(list, (float)90.0f, (float)f, (boolean)bl).WL) {
            this.UN.i(false);
        }
        float f2 = this.nb();
        int n = 0;
        while (list.size() > 1 && n++ < 128 && !this.a(list, (float)f2, (float)f, (boolean)bl).WL) {
            list.remove(0);
        }
        HashSet<String> hashSet = new HashSet<String>(list);
        for (String string : ClickGuiPins.names()) {
            Module module;
            if (hashSet.contains(string) || this.We.contains(string) || (module = this.ci(string)) == null || !this.d(module)) continue;
            this.We.add(string);
        }
    }

    private boolean d(Module module) {
        for (Setting<?> setting : module.getSettings()) {
            if (!setting.jS() || !(setting instanceof BooleanSetting) && !(setting instanceof EnumSetting) && !(setting instanceof SliderSetting) && !(setting instanceof BindSetting) && !(setting instanceof ButtonSetting) && !(setting instanceof StringSetting) && !(setting instanceof ColorSetting)) continue;
            return true;
        }
        return false;
    }

    private void cd(String string) {
        if (ClickGuiPins.names().contains(string)) {
            if (!this.We.add(string)) {
                this.We.remove(string);
            }
            return;
        }
        Module module = this.ci(string);
        if (module == null || !this.d(module)) {
            return;
        }
        this.Wd.put("pin:" + string, Float.valueOf(0.0f));
        ClickGuiPins.toggle(string);
        this.nd();
    }

    private float a(class_4587 class_45872, float f, float f2, float f3, Module module, float f4, float f5) {
        ArrayList<Setting<?>> arrayList = new ArrayList<>();
        for (Setting<?> setting : module.getSettings()) {
            if (!setting.jS() || !(setting instanceof BooleanSetting) && !(setting instanceof EnumSetting) && !(setting instanceof SliderSetting) && !(setting instanceof BindSetting) && !(setting instanceof ButtonSetting) && !(setting instanceof StringSetting) && !(setting instanceof ColorSetting)) continue;
            arrayList.add(setting);
        }
        float f6 = 16.0f;
        float f7 = 30.0f;
        float f8 = 34.0f;
        float f9 = arrayList.isEmpty() ? 70.0f : 12.0f + (float)arrayList.size() * f8;
        float f10 = 14.0f + f7 + f9 + 14.0f;
        this.a(class_45872, f, f2, f3, f10, 8.0f, 16.0f, 40, 1.0f);
        this.n(class_45872, f, f2, f3, f10, 8.0f);
        float f11 = f + f6;
        float f12 = f2 + 14.0f;
        float f13 = f3 - f6 * 2.0f;
        boolean bl = module.isToggled();
        float f14 = 30.0f;
        float f15 = this.b("pintile:" + module.getName(), bl ? 1.0f : 0.0f);
        this.a(class_45872, f11, f12, f14, f14, 6.0f, 6.0f, 90, f15);
        Render2D.drawRoundedRect(class_45872, f11, f12, f14, f14, 6.0f, bl ? this.as(28) : this.at(8));
        Render2D.drawBorder(class_45872, f11, f12, f14, f14, 6.0f, 0.8f, new ColorRGBA(bl ? this.as(90) : this.at(30)));
        this.a(class_45872, module.getCategory(), f11 + f14 / 2.0f, f12 + f14 / 2.0f, 14.0f, bl ? this.Wo : this.MUTED);
        this.a(f11, f12, f14, f14, 0, () -> this.f(module));
        Instance instance = Fonts.UNBOUNDED.getFont(12.0f);
        Render2D.drawFont(class_45872, instance, module.getDisplayName(), f11 + f14 + 11.0f, f12 + 2.0f, this.i(this.TEXT));
        Instance instance2 = Fonts.GOLOS.getFont(11.0f);
        Render2D.drawFont(class_45872, instance2, module.getCategory().name(), f11 + f14 + 11.0f, f12 + 15.0f, this.i(this.MUTED3));
        float f16 = 22.0f;
        float f17 = f11 + f13 - f16;
        float f18 = f12;
        boolean bl2 = this.hit(f4, f5, f17, f18, f16, f16);
        float f19 = this.b("pinx:" + module.getName(), bl2 ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f17, f18, f16, f16, 6.0f, this.mix(this.at(8), this.a(this.Ut, 34), f19));
        if (f19 > 0.01f) {
            Render2D.drawBorder(class_45872, f17, f18, f16, f16, 6.0f, 0.9f, new ColorRGBA(this.a(this.Ut, Math.round(150.0f * f19))));
        }
        ClickGuiIcons.close(class_45872, f17 + f16 / 2.0f, f18 + f16 / 2.0f, 12.0f, this.i(this.mix(this.MUTED, this.Ut, f19)));
        this.a(f17, f18, f16, f16, 0, () -> this.We.add(module.getName()));
        Render2D.drawRoundedRect(class_45872, f11, f12 + f7 + 6.0f, f13, 1.0f, 0.5f, this.at(16));
        if (arrayList.isEmpty()) {
            GifTexture gifTexture = GifCache.get(Uv);
            float f20 = 40.0f;
            float f21 = f12 + f7 + 14.0f;
            GifRenderer.drawGif(class_45872, gifTexture, f11 + (f13 - f20) / 2.0f, f21, f20, f20, this.fade);
            Instance instance3 = Fonts.GOLOS.getFont(11.0f);
            String string = class_1074.method_4662((String)"clickgui.settings.empty", (Object[])new Object[0]);
            Render2D.drawFont(class_45872, instance3, string, f11 + (f13 - instance3.getWidth(string)) / 2.0f, f21 + f20 + 2.0f, this.i(this.MUTED));
            return f10;
        }
        float f22 = f12 + f7 + 12.0f;
        for (Setting<?> setting : arrayList) {
            this.a(class_45872, f11, f22, f13, setting, f4, f5);
            f22 += f8;
        }
        return f10;
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, Setting<?> setting, float f4, float f5) {
        Setting setting2;
        Setting setting3;
        if (setting instanceof ButtonSetting) {
            ButtonSetting buttonSetting = (ButtonSetting)setting;
            float f6 = 22.0f;
            float f7 = f2 + (16.0f - f6) / 2.0f + 1.0f;
            boolean bl = this.hit(f4, f5, f, f7, f3, f6);
            float f8 = this.b("act:" + setting.getName(), bl ? 1.0f : 0.0f);
            Render2D.drawRoundedRect(class_45872, f, f7, f3, f6, 6.0f, this.mix(this.as(20), this.as(45), f8));
            Render2D.drawBorder(class_45872, f, f7, f3, f6, 6.0f, 0.8f, new ColorRGBA(this.mix(this.as(90), this.as(150), f8)));
            Instance instance = Fonts.UNBOUNDED.getFont(10.0f);
            String string = class_1074.method_4662((String)buttonSetting.jX(), (Object[])new Object[0]);
            Render2D.drawFont(class_45872, instance, string, f + (f3 - instance.getWidth(string)) / 2.0f, f7 + f6 / 2.0f - instance.getHeight() / 2.0f, this.as(255));
            this.a(f, f7, f3, f6, 0, buttonSetting::run);
            return;
        }
        Instance instance = Fonts.UNBOUNDED_L.getFont(10.5f);
        float f9 = f3 - 62.0f;
        if (setting instanceof EnumSetting) {
            setting3 = (EnumSetting)setting;
            f9 = f3 - this.ch(class_1074.method_4662((String)((EnumSetting)setting3).jZ(), (Object[])new Object[0])) - 10.0f;
        } else if (setting instanceof BindSetting) {
            setting2 = (BindSetting)setting;
            f9 = f3 - this.ch(this.c((KeyBind)setting2.jT())) - 10.0f;
        } else if (setting instanceof StringSetting) {
            f9 = f3 * 0.4f;
        }
        Render2D.drawFont(class_45872, instance, this.clip(instance, class_1074.method_4662((String)setting.getName(), (Object[])new Object[0]), Math.max(28.0f, f9)), f, f2 + 8.0f - instance.getHeight() / 2.0f, this.i(this.TEXT3));
        if (setting instanceof BooleanSetting) {
            BooleanSetting booleanSetting = (BooleanSetting)setting;
            float f10 = 36.0f;
            float f11 = 20.0f;
            float f12 = f + f3 - f10;
            float f13 = f2 + (16.0f - f11) / 2.0f + 1.0f;
            this.b(class_45872, f12, f13, "tgl:" + setting.getName(), (Boolean)booleanSetting.jT());
            this.a(f12, f13, f10, f11, 0, () -> LinkGuiScreen.d(booleanSetting));
        } else if (setting instanceof EnumSetting) {
            setting2 = (EnumSetting)setting;
            String string = class_1074.method_4662((String)((EnumSetting)setting2).jZ(), (Object[])new Object[0]);
            this.a(class_45872, f + f3, f2, setting.getName(), string, f4, f5);
            float f14 = this.ch(string);
            this.a(f + f3 - f14, f2 - 2.0f, f14 + 2.0f, 24.0f, 0, ((EnumSetting)setting2)::jY);
        } else if (setting instanceof BindSetting) {
            BindSetting bindSetting = (BindSetting)setting;
            boolean bl = this.Vf == Page.WE && this.Vh == bindSetting;
            String string = bl ? "..." : this.c((KeyBind)bindSetting.jT());
            this.a(class_45872, f + f3, f2, setting.getName(), string, f4, f5);
            float f15 = this.ch(string);
            this.a(f + f3 - f15, f2 - 2.0f, f15 + 2.0f, 24.0f, 0, () -> this.a(Page.WE, null, bindSetting));
        } else if (setting instanceof SliderSetting) {
            SliderSetting sliderSetting = (SliderSetting)setting;
            this.a(class_45872, f, f2, f3, sliderSetting, f4, f5);
        } else if (setting instanceof StringSetting) {
            StringSetting stringSetting = (StringSetting)setting;
            float f16 = f3 * 0.56f;
            float f17 = f + f3 - f16;
            float f18 = 22.0f;
            float f19 = f2 + (16.0f - f18) / 2.0f + 1.0f;
            boolean bl = this.Vp == stringSetting;
            float f20 = this.b("strf:" + setting.getName(), bl ? 1.0f : 0.0f);
            Render2D.drawRoundedRect(class_45872, f17, f19, f16, f18, 6.0f, this.at(10));
            UiRenderUtils.c(class_45872, f17, f19, f16, f18, 6.0f, 0.8f, this.mix(this.at(20), this.as(150), f20));
            Instance instance2 = Fonts.GOLOS.getFont(11.0f);
            String string = stringSetting.kg();
            boolean bl2 = string.isEmpty();
            String string2 = bl2 && !bl ? class_1074.method_4662((String)stringSetting.kh(), (Object[])new Object[0]) : string;
            String string3 = this.b(instance2, string2, f16 - 14.0f);
            if (bl2 && !bl) {
                Render2D.drawFont(class_45872, instance2, string3, f17 + 8.0f, f19 + f18 / 2.0f - instance2.getHeight() / 2.0f, this.i(this.MUTED3));
            } else {
                Render2D.drawFontRaw(class_45872, instance2, string3, f17 + 8.0f, f19 + f18 / 2.0f - instance2.getHeight() / 2.0f, this.i(this.Uo));
            }
            if (bl) {
                float f21 = Math.min(instance2.getWidth(string), f16 - 14.0f);
                float f22 = 0.35f + 0.65f * (0.5f + 0.5f * (float)Math.sin((double)(System.currentTimeMillis() % 1000L) / 1000.0 * Math.PI * 2.0));
                Render2D.drawRoundedRect(class_45872, f17 + 8.0f + f21 + 1.0f, f19 + f18 / 2.0f - 6.0f, 1.0f, 12.0f, 0.5f, this.a(this.Uo, Math.round(255.0f * f22)));
            }
            this.a(f17, f19, f16, f18, 0, () -> this.a(stringSetting));
        } else if (setting instanceof ColorSetting) {
            ColorSetting colorSetting = (ColorSetting)setting;
            float f23 = 26.0f;
            float f24 = 14.0f;
            float f25 = f + f3 - f23;
            float f26 = f2 + (16.0f - f24) / 2.0f + 1.0f;
            Color color = (Color)colorSetting.jT();
            Render2D.drawRoundedRect(class_45872, f25, f26, f23, f24, 4.0f, this.at(10));
            Render2D.drawRoundedRect(class_45872, f25, f26, f23, f24, 4.0f, this.a(color, color.getAlpha()));
            boolean bl = this.Vs == colorSetting;
            UiRenderUtils.c(class_45872, f25, f26, f23, f24, 4.0f, 0.9f, bl ? this.as(180) : this.at(28));
            this.a(f25, f26, f23, f24, 0, () -> {
                if (this.Vs == colorSetting) {
                    this.Vs = null;
                    this.Vy = 0;
                } else {
                    this.Vs = colorSetting;
                    this.Vt = null;
                    this.Vy = 0;
                    this.Vz = 386.0f;
                    this.VA = class_3532.method_15363((float)(f26 - 24.0f), (float)90.0f, (float)820.0f);
                }
            });
        }
    }

    private void a(class_4587 class_45872, String string, float f, float f2, float f3, ColorSetting colorSetting) {
        Render2D.drawFont(class_45872, Fonts.UNBOUNDED_L.getFont(10.0f), string, f, f2 + 4.0f, this.i(this.TEXT3));
        if (colorSetting == null) {
            return;
        }
        float f4 = 26.0f;
        float f5 = 14.0f;
        float f6 = f + f3 - f4;
        float f7 = f2 + (20.0f - f5) / 2.0f;
        Color color = (Color)colorSetting.jT();
        Render2D.drawRoundedRect(class_45872, f6, f7, f4, f5, 4.0f, this.at(10));
        Render2D.drawRoundedRect(class_45872, f6, f7, f4, f5, 4.0f, this.a(color, color.getAlpha()));
        boolean bl = this.Vs == colorSetting;
        UiRenderUtils.c(class_45872, f6, f7, f4, f5, 4.0f, 0.9f, bl ? this.as(180) : this.at(28));
        this.a(f6, f7, f4, f5, 0, () -> {
            if (this.Vs == colorSetting) {
                this.Vs = null;
                this.Vy = 0;
            } else {
                this.Vs = colorSetting;
                this.Vt = null;
                this.Vy = 0;
                this.Vz = class_3532.method_15363((float)(f6 - 190.0f), (float)12.0f, (float)1720.0f);
                this.VA = class_3532.method_15363((float)(f7 - 24.0f), (float)90.0f, (float)820.0f);
            }
        });
    }

    private void d(class_4587 class_45872, float f, float f2) {
        ColorSetting colorSetting = this.Vs;
        if (colorSetting == null) {
            return;
        }
        if (this.Vt != colorSetting) {
            Color color = (Color)colorSetting.jT();
            float[] fArray = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
            this.Vu = fArray[0];
            this.Vv = fArray[1];
            this.Vw = fArray[2];
            this.Vx = (float)color.getAlpha() / 255.0f;
            this.Vt = colorSetting;
        }
        float f3 = 12.0f;
        float f4 = 120.0f;
        float f5 = 8.0f;
        float f6 = 16.0f;
        float f7 = 12.0f;
        float f8 = 18.0f;
        float f9 = f3 + f4 + f5 + f6 + f3;
        float f10 = f3 + f8 + f4 + 10.0f + f7 + f3;
        float f11 = class_3532.method_15363((float)this.Vz, (float)0.0f, (float)(1920.0f - f9));
        float f12 = class_3532.method_15363((float)this.VA, (float)0.0f, (float)(1080.0f - f10));
        this.VN = f11;
        this.VO = f12;
        this.VP = f9;
        this.VQ = f10;
        this.n(class_45872, f11, f12, f9, f10, 8.0f);
        Instance instance = Fonts.UNBOUNDED.getFont(10.5f);
        Render2D.drawFont(class_45872, instance, this.clip(instance, class_1074.method_4662((String)colorSetting.getName(), (Object[])new Object[0]), f9 - f3 * 2.0f - 20.0f), f11 + f3, f12 + f3 + f8 / 2.0f - instance.getHeight() / 2.0f - 2.0f, this.i(this.Uo));
        float f13 = 16.0f;
        float f14 = f11 + f9 - f3 - f13;
        float f15 = f12 + f3 - 1.0f;
        this.VR = f14;
        this.VS = f15;
        this.VT = f13;
        boolean bl = this.hit(f, f2, f14, f15, f13, f13);
        float f16 = this.b("cpx:" + colorSetting.getName(), bl ? 1.0f : 0.0f);
        ClickGuiIcons.close(class_45872, f14 + f13 / 2.0f, f15 + f13 / 2.0f, 11.0f, this.i(this.mix(this.MUTED, this.Ut, f16)));
        float f17 = f11 + f3;
        float f18 = f12 + f3 + f8;
        this.VB = f17;
        this.VC = f18;
        this.VD = f4;
        this.VE = f4;
        Render2D.drawRoundedHorizontalGradient(class_45872, f17, f18, f4, f4, 6.0f, Color.WHITE, this.a(Color.getHSBColor(this.Vu, 1.0f, 1.0f), 255));
        Render2D.drawRoundedVerticalGradient(class_45872, f17, f18, f4, f4, 6.0f, new Color(0, 0, 0, 0), this.a(new Color(0, 0, 0, 255), 255));
        float f19 = f17 + this.Vv * f4;
        float f20 = f18 + (1.0f - this.Vw) * f4;
        f19 = class_3532.method_15363((float)f19, (float)(f17 + 2.0f), (float)(f17 + f4 - 2.0f));
        f20 = class_3532.method_15363((float)f20, (float)(f18 + 2.0f), (float)(f18 + f4 - 2.0f));
        Render2D.drawRoundedRect(class_45872, f19 - 4.0f, f20 - 4.0f, 8.0f, 8.0f, 4.0f, this.a(new Color(0, 0, 0, 160), 160));
        Render2D.drawBorder(class_45872, f19 - 4.0f, f20 - 4.0f, 8.0f, 8.0f, 4.0f, 1.2f, new ColorRGBA(this.a(Color.WHITE, 235)));
        float f21 = f17 + f4 + f5;
        float f22 = f18;
        this.VF = f21;
        this.VG = f22;
        this.VH = f6;
        this.VI = f4;
        Render2D.drawTexture(class_45872, f21, f22, f6, f4, 6.0f, Vr, this.a(Color.WHITE, 255));
        float f23 = class_3532.method_15363((float)(f22 + this.Vu * f4), (float)(f22 + 2.0f), (float)(f22 + f4 - 2.0f));
        Render2D.drawRoundedRect(class_45872, f21 - 1.0f, f23 - 1.6f, f6 + 2.0f, 3.2f, 1.6f, this.a(new Color(0, 0, 0, 140), 140));
        Render2D.drawRoundedRect(class_45872, f21, f23 - 1.0f, f6, 2.0f, 1.0f, this.a(Color.WHITE, 235));
        float f24 = f17;
        float f25 = f18 + f4 + 10.0f;
        float f26 = f4 + f5 + f6;
        this.VJ = f24;
        this.VK = f25;
        this.VL = f26;
        this.VM = f7;
        int n = Color.HSBtoRGB(this.Vu, this.Vv, this.Vw);
        Color color = new Color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF);
        Render2D.drawRoundedRect(class_45872, f24, f25, f26, f7, 6.0f, this.at(10));
        Render2D.drawRoundedHorizontalGradient(class_45872, f24, f25, f26, f7, 6.0f, this.a(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0), 0), this.a(color, 255));
        float f27 = class_3532.method_15363((float)(f24 + this.Vx * f26), (float)(f24 + 2.0f), (float)(f24 + f26 - 2.0f));
        Render2D.drawRoundedRect(class_45872, f27 - 1.6f, f25 - 1.0f, 3.2f, f7 + 2.0f, 1.6f, this.a(new Color(0, 0, 0, 140), 140));
        Render2D.drawRoundedRect(class_45872, f27 - 1.0f, f25, 2.0f, f7, 1.0f, this.a(Color.WHITE, 235));
    }

    private void ne() {
        if (this.Vs == null) {
            return;
        }
        int n = Color.HSBtoRGB(this.Vu, this.Vv, this.Vw);
        this.Vs.d(new Color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, Math.round(class_3532.method_15363((float)this.Vx, (float)0.0f, (float)1.0f) * 255.0f)));
    }

    private void q(float f, float f2) {
        if (this.Vy == 1) {
            this.Vv = class_3532.method_15363((float)((f - this.VB) / this.VD), (float)0.0f, (float)1.0f);
            this.Vw = 1.0f - class_3532.method_15363((float)((f2 - this.VC) / this.VE), (float)0.0f, (float)1.0f);
        } else if (this.Vy == 2) {
            this.Vu = class_3532.method_15363((float)((f2 - this.VG) / this.VI), (float)0.0f, (float)1.0f);
        } else if (this.Vy == 3) {
            this.Vx = class_3532.method_15363((float)((f - this.VJ) / this.VL), (float)0.0f, (float)1.0f);
        }
        this.ne();
    }

    private void b(class_332 class_3322, float f, float f2) {
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        Object object;
        Object object2;
        Object object3;
        Object object4;
        float f10;
        class_4587 class_45872 = class_3322.method_51448();
        float f11 = 410.0f;
        float f12 = 90.0f;
        float f13 = 1090.0f;
        float f14 = 830.0f;
        this.a(class_45872, f11, f12, f13, f14, 12.0f, 22.0f, 46, 1.0f);
        UiRenderUtils.b(class_45872, f11, f12, f13, f14, 12.0f, this.nx(), this.ny());
        Render2D.drawHatch(class_45872, f11, f12, f13, f14, 24.0f, 1.0f, this.at(4));
        UiRenderUtils.c(class_45872, f11, f12, f13, f14, 12.0f, 1.0f, this.at(23));
        float f15 = 62.0f;
        float f16 = f11 + 20.0f;
        float f17 = f12 + f15 / 2.0f;
        Instance instance = Fonts.GOLOS_SEMI.getFont(16.0f);
        this.drawSpaced(class_45872, instance, "celebression", f16, f17 - instance.getHeight() / 2.0f, this.i(this.TEXT), -0.56f);
        float f18 = this.widthSpaced(instance, "celebression", -0.56f);
        Render2D.drawFont(class_45872, instance, ".", f16 + f18, f17 - instance.getHeight() / 2.0f, this.as(255));
        float f19 = f16 + f18 + instance.getWidth(".") + 12.0f;
        Render2D.drawRoundedRect(class_45872, f19, f17 - 11.0f, 1.0f, 22.0f, 0.5f, this.at(26));
        this.m(class_45872, f19 += 13.0f, f17 - 17.0f, 34.0f, 7.0f);
        float f20 = this.b("tbav", this.hit(f, f2, f19, f17 - 17.0f, 34.0f, 34.0f) ? 1.0f : 0.0f);
        if (this.UN.rn()) {
            Render2D.drawBorder(class_45872, f19, f17 - 17.0f, 34.0f, 34.0f, 7.0f, 1.0f, new ColorRGBA(this.as(Math.round(90.0f + 60.0f * f20))));
        } else if (f20 > 0.01f) {
            Render2D.drawBorder(class_45872, f19, f17 - 17.0f, 34.0f, 34.0f, 7.0f, 1.0f, new ColorRGBA(this.as(Math.round(130.0f * f20))));
        }
        this.a(f19, f17 - 17.0f, 34.0f, 34.0f, 0, this::nc);
        Instance instance2 = Fonts.UNBOUNDED.getFont(10.5f);
        float f21 = instance2.getWidth("\u0412\u0441\u0435") + 28.0f;
        float f22 = this.b("tball", this.hit(f, f2, f19 += 46.0f, f17 - 17.0f, f21, 34.0f) ? 1.0f : 0.0f);
        if (this.UC) {
            this.a(class_45872, f19, f17 - 17.0f, f21, 34.0f, 7.0f, 8.0f, 128, 1.0f);
            Render2D.drawRoundedRect(class_45872, f19, f17 - 17.0f, f21, 34.0f, 7.0f, this.as(18));
            Render2D.drawBorder(class_45872, f19, f17 - 17.0f, f21, 34.0f, 7.0f, 6.0f, 0.5f, this.as(102));
            Render2D.drawBorder(class_45872, f19, f17 - 17.0f, f21, 34.0f, 7.0f, 0.8f, new ColorRGBA(this.as(140)));
            Render2D.drawFont(class_45872, instance2, "\u0412\u0441\u0435", f19 + 14.0f, f17 - instance2.getHeight() / 2.0f, this.as(255));
        } else {
            Render2D.drawRoundedRect(class_45872, f19, f17 - 17.0f, f21, 34.0f, 7.0f, this.at(10));
            Render2D.drawBorder(class_45872, f19, f17 - 17.0f, f21, 34.0f, 7.0f, 0.8f, new ColorRGBA(this.at(Math.round(18.0f + 40.0f * f22))));
            Render2D.drawFont(class_45872, instance2, "\u0412\u0441\u0435", f19 + 14.0f, f17 - instance2.getHeight() / 2.0f, this.i(this.mix(this.TEXT3, this.TEXT, f22)));
        }
        this.a(f19, f17 - 17.0f, f21, 34.0f, 0, () -> {
            this.UC = !this.UC;
            this.UB.reset();
            this.UE = 0.0f;
        });
        float f23 = 34.0f;
        float f24 = 34.0f;
        float f25 = f11 + f13 - 20.0f - f23 - 8.0f - f24 - 8.0f - (f19 += f21 + 12.0f);
        float f26 = f19;
        float f27 = f17 - 17.0f;
        boolean bl = this.UB.isFocused();
        float f28 = this.b("tbsearch", bl ? 1.0f : 0.0f);
        this.a(class_45872, f26, f27, f25, 34.0f, 7.0f, 7.0f, 70, f28);
        UiRenderUtils.drawRoundedRect(class_45872, f26, f27, f25, 34.0f, 7.0f, this.at(10));
        UiRenderUtils.c(class_45872, f26, f27, f25, 34.0f, 7.0f, 0.9f, this.mix(this.at(18), this.as(150), f28));
        ClickGuiIcons.search(class_45872, f26 + 16.0f, f17, 14.0f, this.mix(this.i(this.MUTED), this.as(255), f28));
        String string = this.UB.getSearchText();
        this.Wl.cF(string);
        Instance instance3 = Fonts.GOLOS.getFont(12.5f);
        if (string.isEmpty() && !bl && !this.Wl.rg()) {
            Render2D.drawFont(class_45872, instance3, "\u041f\u043e\u0438\u0441\u043a \u043f\u043e \u043c\u043e\u0434\u0443\u043b\u044f\u043c...", f26 + 30.0f, f17 - instance3.getHeight() / 2.0f, this.i(this.MUTED3));
        } else {
            this.a(class_45872, instance3, f26 + 30.0f, f17 - instance3.getHeight() / 2.0f, this.i(this.Uo));
            if (bl) {
                float f29 = instance3.getWidth(string);
                long l = System.currentTimeMillis() - this.Wj;
                f10 = l < 350L ? 1.0f : 0.35f + 0.65f * (0.5f + 0.5f * (float)Math.sin((double)(System.currentTimeMillis() % 1000L) / 1000.0 * Math.PI * 2.0));
                Render2D.drawRoundedRect(class_45872, f26 + 30.0f + f29 + 1.0f, f17 - 7.0f, 1.0f, 14.0f, 0.5f, this.a(this.Uo, Math.round(255.0f * f10)));
            }
        }
        this.a(f26, f27, f25, 34.0f, 0, () -> {
            this.UB.setFocused(true);
            this.UT.setFocused(false);
            this.UU.setFocused(false);
            this.Wj = System.currentTimeMillis();
        });
        f19 = f26 + f25 + 8.0f;
        boolean bl2 = this.UP.rn();
        float f30 = this.b("tbs", this.hit(f, f2, f19, f17 - 17.0f, 34.0f, 34.0f) ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f19, f17 - 17.0f, f24, 34.0f, 7.0f, bl2 ? this.as(18) : this.at(14));
        Render2D.drawBorder(class_45872, f19, f17 - 17.0f, f24, 34.0f, 7.0f, 1.0f, new ColorRGBA(bl2 ? this.as(90) : this.at(Math.round(34.0f + 36.0f * f30))));
        ClickGuiIcons.sliders(class_45872, f19 + 17.0f, f17, 15.0f, bl2 ? this.as(255) : this.i(this.mix(this.TEXT3, this.TEXT, f30)));
        this.a(f19, f17 - 17.0f, f24, 34.0f, 0, () -> this.b(this.UP));
        boolean bl3 = this.hit(f, f2, f19 += f24 + 8.0f, f17 - 17.0f, 34.0f, 34.0f);
        f10 = this.b("tbx", bl3 ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f19, f17 - 17.0f, f23, 34.0f, 7.0f, this.at(14));
        Render2D.drawBorder(class_45872, f19, f17 - 17.0f, f23, 34.0f, 7.0f, 1.0f, new ColorRGBA(this.mix(this.at(34), this.a(this.Ut, 160), f10)));
        ClickGuiIcons.close(class_45872, f19 + 17.0f, f17, 15.0f, this.i(this.mix(this.TEXT3, this.Ut, f10)));
        float f31 = f19;
        this.a(f31, f17 - 17.0f, f23, 34.0f, 0, this::method_25419);
        UiRenderUtils.drawRoundedRect(class_45872, f11, f12 + f15, f13, 1.0f, 0.5f, this.at(18));
        float f32 = f12 + f14 - 92.0f;
        float f33 = f11 + 20.0f;
        float f34 = f12 + f15 + 20.0f;
        float f35 = f13 - 40.0f;
        float f36 = f32 - f34 - 8.0f;
        this.UK[0] = f33;
        this.UK[1] = f34;
        this.UK[2] = f35;
        this.UK[3] = f36;
        List<Module> list = this.ns();
        float f37 = 12.0f;
        float f38 = 102.0f;
        float f39 = 12.0f;
        float f40 = (f35 - 2.0f * f37) / 3.0f;
        float f41 = (float)Math.ceil((double)list.size() / 3.0) * (f38 + f39);
        float f42 = Math.max(0.0f, f41 - f36);
        this.UE = class_3532.method_15363((float)this.UE, (float)0.0f, (float)f42);
        this.UD += (this.UE - this.UD) * (1.0f - (float)Math.exp(-16.0f * this.Wi));
        if (Math.abs(this.UE - this.UD) < 0.3f) {
            this.UD = this.UE;
        }
        this.UD = class_3532.method_15363((float)this.UD, (float)0.0f, (float)f42);
        HashMap<String, float[]> hashMap = new HashMap<String, float[]>();
        for (int i = 0; i < list.size(); ++i) {
            int n = i % 3;
            int n2 = i / 3;
            hashMap.put(list.get(i).getName(), new float[]{f33 + (float)n * (f40 + f37), (float)n2 * (f38 + f39)});
        }
        HashSet<String> hashSet = new HashSet<>(hashMap.keySet());
        if (!this.UG) {
            for (String string2 : hashSet) {
                this.Wd.put("cardin:" + string2, Float.valueOf(1.0f));
                this.UH.put(string2, (float[])((float[])hashMap.get(string2)).clone());
            }
            this.UG = true;
        } else {
            for (String string3 : hashSet) {
                if (this.UH.containsKey(string3)) continue;
                this.UH.put(string3, (float[])((float[])hashMap.get(string3)).clone());
                this.Wd.put("cardin:" + string3, Float.valueOf(0.0f));
                this.UI.remove(string3);
            }
            for (String string4 : this.UF) {
                float[] previousPosition = this.UH.get(string4);
                if (hashSet.contains(string4) || this.UI.containsKey(string4) || previousPosition == null) continue;
                this.UI.put(string4, new float[]{previousPosition[0], previousPosition[1] - this.UD, 1.0f});
            }
        }
        this.UH.keySet().retainAll(hashSet);
        this.UF.clear();
        this.UF.addAll(hashSet);
        float f43 = 1.0f - (float)Math.exp(-18.0f * this.Wi);
        for (String moduleName : hashSet) {
            float[] currentPosition = this.UH.get(moduleName);
            float[] targetPosition = hashMap.get(moduleName);
            currentPosition[0] += (targetPosition[0] - currentPosition[0]) * f43;
            currentPosition[1] += (targetPosition[1] - currentPosition[1]) * f43;
        }
        float f44 = this.fade;
        Render2D.startScissor(class_3322, f33 - 14.0f, f34 - 16.0f, f35 + 28.0f, f36 + 12.0f + 16.0f);
        Render2D.drawDotGrid(class_45872, f33, f34, f35, f36, 24.0f, 12.0f, this.at(9));
        for (Module cardModule : list) {
            String moduleName = cardModule.getName();
            float[] cardPosition = this.UH.get(moduleName);
            f9 = cardPosition[0];
            f8 = f34 + cardPosition[1] - this.UD;
            if (f8 + f38 < f34 - 6.0f || f8 > f34 + f36 + 6.0f) continue;
            f7 = this.b("cardin:" + moduleName, 1.0f);
            f7 = 1.0f - (1.0f - f7) * (1.0f - f7);
            this.fade = f44 * (0.15f + 0.85f * f7);
            this.a(class_45872, f9, f8 + (1.0f - f7) * 12.0f, f40, f38, cardModule, f, f2, true);
        }
        Iterator<Map.Entry<String, float[]>> exitIterator = this.UI.entrySet().iterator();
        while (exitIterator.hasNext()) {
            Map.Entry<String, float[]> entry = exitIterator.next();
            float[] exitState = entry.getValue();
            exitState[2] -= this.Wi * 6.5f;
            if (exitState[2] <= 0.02f) {
                exitIterator.remove();
                this.Wd.remove("cardin:" + entry.getKey());
                continue;
            }
            Module exitingModule = this.ci(entry.getKey());
            if (exitingModule == null) {
                exitIterator.remove();
                continue;
            }
            f9 = exitState[0];
            f8 = f34 + exitState[1];
            if (f8 + f38 < f34 - 40.0f || f8 > f34 + f36 + 40.0f) continue;
            f7 = exitState[2];
            f6 = f7 * f7 * (3.0f - 2.0f * f7);
            f5 = 0.88f + 0.12f * f6;
            this.fade = f44 * f6;
            class_45872.method_22903();
            f4 = f9 + f40 / 2.0f;
            f3 = f8 + f38 / 2.0f;
            class_45872.method_46416(f4, f3, 0.0f);
            class_45872.method_22905(f5, f5, 1.0f);
            class_45872.method_46416(-f4, -f3, 0.0f);
            this.a(class_45872, f9, f8, f40, f38, exitingModule, f, f2, false);
            class_45872.method_22909();
        }
        this.UJ = true;
        this.fade = f44;
        if (list.isEmpty() && this.UI.isEmpty()) {
            object3 = GifCache.get(Uv);
            object2 = class_1074.method_4662((String)(this.UB.getSearchText().isEmpty() ? "clickgui.category.empty" : "clickgui.search.empty"), (Object[])new Object[0]);
            float f45 = 90.0f;
            f9 = 14.0f;
            Instance instance4 = Fonts.GOLOS.getFont(12.5f);
            f7 = instance4.getWidth((String)object2);
            f6 = instance4.getHeight();
            f5 = f45 + f9 + f7;
            f4 = f33 + (f35 - f5) / 2.0f;
            f3 = f34 + f36 * 0.38f - f45 / 2.0f;
            GifRenderer.drawGif(class_45872, (GifTexture)object3, f4, f3, f45, f45, this.fade);
            Render2D.drawFont(class_45872, instance4, (String)object2, f4 + f45 + f9, f3 + (f45 - f6) / 2.0f, this.i(this.TEXT3));
        }
        Render2D.stopScissor(class_3322);
        this.fade = f44;
        float f46 = f32;
        float f47 = f32 + 16.0f + this.UL.height();
        float f48 = f11 + 20.0f;
        f9 = f11 + f13 - 20.0f;
        boolean bl4 = false;
        for (ModuleSettingsPanel moduleSettingsPanel : ClickRippleManager.nW()) {
            f5 = moduleSettingsPanel.getX();
            f4 = moduleSettingsPanel.getY();
            if (!(f5 < f9) || !(f5 + 258.0f > f48) || !(f4 < f47) || !(f4 + moduleSettingsPanel.og() > f46)) continue;
            bl4 = true;
            break;
        }
        float f49 = bl4 ? 0.0f : 1.0f;
        this.UO += (f49 - this.UO) * 0.25f;
        if (Math.abs(f49 - this.UO) < 0.01f) {
            this.UO = f49;
        }
        if (this.UO > 0.02f) {
            UiRenderUtils.drawRoundedRect(class_45872, f11 + 20.0f, f32, f13 - 40.0f, 1.0f, 0.5f, this.at(Math.round(55.0f * this.UO)));
            this.UL.setBounds(f11 + 20.0f, f32 + 16.0f, f13 - 40.0f);
            this.UL.render(class_3322, f, f2, this.Wo, this.fade * this.UO, this.Uu);
        }
        this.a(class_45872, f11 + 14.0f, f12 + 14.0f, true);
        this.a(class_45872, f11 + f13 - 14.0f + 2.0f, f12 + f14 - 14.0f + 2.0f, false);
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, Module module, float f5, float f6, boolean bl) {
        float f7;
        float f8;
        float f9;
        this.UJ = bl;
        boolean bl2 = module.isToggled();
        boolean bl3 = this.hit(f5, f6, f, f2, f3, f4);
        float f10 = this.b("mcg:" + module.getName(), bl2 ? 1.0f : 0.0f);
        float f11 = this.b("mch:" + module.getName(), bl3 ? 1.0f : 0.0f);
        float f12 = 10.0f;
        this.k(class_45872, f, f2, f3, f4, f12, f10);
        UiRenderUtils.b(class_45872, f, f2, f3, f4, f12, this.nB(), this.nC());
        if (f10 > 0.001f) {
            UiRenderUtils.b(class_45872, f, f2, f3, f4, f12, this.a(this.h(this.nD()), Math.round((float)this.nD().getAlpha() / this.nf() * f10)), this.a(this.h(this.nE()), Math.round((float)this.nE().getAlpha() / this.nf() * f10)));
            float f13 = Math.min(46.0f, f4 * 0.5f);
            UiRenderUtils.b(class_45872, f + 1.0f, f2 + 1.0f, f3 - 2.0f, f13, f12, this.as(Math.round(26.0f * f10)), this.as(0));
        }
        Color color = bl2 ? this.as(66) : this.at(20);
        UiRenderUtils.c(class_45872, f, f2, f3, f4, f12, 1.0f, this.mix(color, this.at(56), f11));
        Render2D.drawRoundedRect(class_45872, f + f12, f2 + 1.0f, f3 - f12 * 2.0f, 1.0f, 0.5f, this.at(bl2 ? 15 : 10));
        float f14 = 12.0f;
        float f15 = 11.0f;
        float f16 = 18.0f;
        float f17 = f2 + f15;
        float f18 = 32.0f;
        float f19 = 18.0f;
        float f20 = f + f3 - f14 - f18;
        float f21 = f17;
        boolean bl4 = !module.hidesToggle();
        float f22 = 17.0f;
        float f23 = f + f14 + f22 / 2.0f;
        float f24 = f17 + f16 / 2.0f;
        Color color2 = this.mix(this.MUTED, this.Wo, f10);
        this.a(class_45872, module, f23, f24, f22, color2);
        float f25 = f + f14 + f22 + 9.0f;
        float f26 = (bl4 ? f20 : f + f3 - f14) - 8.0f;
        Instance instance = Fonts.UNBOUNDED.getFont(11.5f);
        Render2D.drawFont(class_45872, instance, this.clip(instance, module.getDisplayName(), f26 - f25), f25, f17 + f16 / 2.0f - instance.getHeight() / 2.0f, this.i(this.TEXT));
        this.b(f, f2, f3, f4, 0, () -> this.f(module));
        this.b(f, f2, f3, f4, 1, () -> this.cd(module.getName()));
        if (bl4) {
            this.a(class_45872, f20, f21, "mtg:" + module.getName(), bl2);
            this.b(f20, f21, f18, f19, 0, () -> this.f(module));
        }
        float f27 = f + f14;
        float f28 = f17 + f16 + 7.0f;
        String string3 = aiG.method_1526().method_4669();
        if (!string3.equals(this.Ww)) {
            this.Wv.clear();
            this.Ww = string3;
        }
        Instance instance2 = Fonts.GOLOS.getFont(11.0f);
        List list = this.Wv.computeIfAbsent(module.getName(), string -> {
            String description = this.g(module);
            return description.isEmpty() ? List.of() : this.a(instance2, description, f3 - f14 * 2.0f, 2);
        });
        for (int i = 0; i < list.size(); ++i) {
            Render2D.drawFont(class_45872, instance2, (String)list.get(i), f27, f28 + (float)i * (instance2.getHeight() + 4.0f), this.i(this.Uq));
        }
        float f29 = 20.0f;
        float f30 = f2 + f4 - 9.0f - f29;
        boolean bl5 = this.e(module);
        boolean bl6 = this.Vf == Page.WD && this.Vg == module;
        String string4 = bl6 ? "..." : this.c(module.getBind());
        Instance instance3 = Fonts.UNBOUNDED.getFont(8.0f);
        float f31 = bl5 ? f + f3 - f14 - 30.0f : f + f3 - f14;
        float f32 = Math.max(16.0f, f31 - (f + f14) - 34.0f);
        string4 = this.clip(instance3, string4, f32);
        TimedProgress timedProgress = this.Wm.get("mbindv:" + module.getName());
        float f33 = instance3.getWidth(string4);
        if (timedProgress != null && timedProgress.rg()) {
            f33 = Math.max(f33, instance3.getWidth(timedProgress.ri()));
        }
        float f34 = 18.0f;
        float f35 = f30 + (f29 - f34) / 2.0f;
        float f36 = f + f14;
        float f37 = 20.0f + f33 + 7.0f;
        float f38 = this.b("bindlisten:" + module.getName(), bl6 ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f36, f35, f37, f34, 5.0f, this.mix(this.at(10), this.as(24), f38));
        Render2D.drawRoundedRect(class_45872, f36 + 1.5f, f35 + f34 - 2.0f, f37 - 3.0f, 2.0f, 1.0f, this.mix(this.at(51), this.as(120), f38));
        Render2D.drawBorder(class_45872, f36, f35, f37, f34, 5.0f, 0.8f, new ColorRGBA(this.mix(this.at(30), this.as(200), f38)));
        GuiIcons.draw(class_45872, GuiIcons.KEYBOARD, f36 + 7.0f + 4.5f, f35 + f34 / 2.0f, 9.0f, this.mix(this.i(this.MUTED), this.as(255), f38));
        this.a(class_45872, instance3, "mbindv:" + module.getName(), string4, f36 + 7.0f + 9.0f + 4.0f, f35 + f34 / 2.0f - instance3.getHeight() / 2.0f, this.mix(this.i(this.MUTED), this.as(255), f38));
        this.b(f36, f35, f37, f34, 0, () -> this.a(Page.WD, module, null));
        String string2 = module.isNew() ? "NEW" : (Wx.contains(module.getName()) ? "UPD" : null);
        if (string2 != null) {
            float f39;
            boolean bl7 = string2.equals("UPD");
            Instance instance4 = Fonts.UNBOUNDED.getFont(7.5f);
            f9 = instance4.getWidth(string2) + 12.0f;
            f8 = 15.0f;
            float f40 = f36 + f37 + 6.0f;
            f7 = f30 + (f29 - f8) / 2.0f;
            float f41 = f39 = bl5 ? f + f3 - f14 - 28.0f : f + f3 - f14;
            if (f40 + f9 <= f39) {
                Color color3 = bl7 ? this.a(Wy, 40) : this.a(Wz, 40);
                Color color4 = bl7 ? this.a(Wy, 165) : this.a(Wz, 165);
                Color color5 = bl7 ? this.a(Wy, 255) : this.a(Wz, 255);
                Render2D.drawRoundedRect(class_45872, f40, f7, f9, f8, 4.0f, color3);
                Render2D.drawBorder(class_45872, f40, f7, f9, f8, 4.0f, 0.8f, new ColorRGBA(color4));
                Render2D.drawFont(class_45872, instance4, string2, f40 + 6.0f, f7 + f8 / 2.0f - instance4.getHeight() / 2.0f, color5);
            }
        }
        if (bl5) {
            float f42 = 24.0f;
            float f43 = 20.0f;
            f9 = f + f3 - f14 - f42;
            f8 = f30;
            boolean bl8 = this.hit(f5, f6, f9, f8, f42, f43);
            f7 = this.b("mcd:" + module.getName(), bl8 ? 1.0f : 0.0f);
            if (f7 > 0.001f) {
                Render2D.drawRoundedRect(class_45872, f9, f8, f42, f43, 6.0f, this.at(Math.round(15.0f * f7)));
            }
            ClickGuiIcons.moreHorizontal(class_45872, f9 + f42 / 2.0f, f8 + f43 / 2.0f, 16.0f, this.i(this.mix(this.MUTED3, this.Uo, f7)));
            float menuCenterX = f9 + f42 / 2.0f;
            float menuCenterY = f8 + f43 / 2.0f + 8.0f;
            this.b(f9, f8, f42, f43, 0, () -> this.a(module, menuCenterX, menuCenterY));
        }
    }

    private void a(class_4587 class_45872, float f, float f2, String string, boolean bl) {
        float f3 = 32.0f;
        float f4 = 18.0f;
        float f5 = f4 / 2.0f;
        float f6 = this.b(string, bl ? 1.0f : 0.0f);
        this.a(class_45872, f, f2, f3, f4, f5, 6.0f, 178, f6);
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f5, this.at(26));
        if (f6 > 0.001f) {
            Color color = this.mix(this.Wo, Color.WHITE, 0.13f);
            Color color2 = this.mix(this.Wo, Color.BLACK, 0.055f);
            UiRenderUtils.b(class_45872, f, f2, f3, f4, f5, this.a(color, Math.round(255.0f * f6)), this.a(color2, Math.round(255.0f * f6)));
            Render2D.drawRoundedRect(class_45872, f + 3.0f, f2 + 1.0f, f3 - 6.0f, 1.0f, 0.5f, this.a(255, 255, 255, Math.round(128.0f * f6)));
        }
        float f7 = 12.0f;
        float f8 = f + 3.0f + (f3 - 6.0f - f7) * f6;
        Render2D.drawRoundedRect(class_45872, f8, f2 + 3.0f + 1.0f, f7, f7, f7 / 2.0f, this.a(0, 0, 0, 90));
        Color color = this.mix(this.a(195, 204, 199, 255), this.a(11, 18, 14, 255), f6);
        Render2D.drawRoundedRect(class_45872, f8, f2 + 3.0f, f7, f7, f7 / 2.0f, color);
    }

    private void k(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6) {
        float[][] fArrayArray;
        float f7 = class_3532.method_15363((float)f6, (float)0.0f, (float)1.0f);
        if (f7 <= 0.02f) {
            return;
        }
        for (float[] fArray : fArrayArray = new float[][]{{10.0f, 0.072f}, {6.5f, 0.116f}, {4.0f, 0.159f}, {2.0f, 0.188f}}) {
            float f8 = fArray[0];
            int n = Math.round(fArray[1] * f7 * 255.0f);
            if (n <= 1) continue;
            Render2D.drawBorder(class_45872, f - f8, f2 - f8, f3 + 2.0f * f8, f4 + 2.0f * f8, f5 + f8, 1.5f, f8, this.as(n));
        }
    }

    private float nf() {
        return Math.max(0.001f, this.fade);
    }

    private Color h(Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    private void a(class_4587 class_45872, Module module, float f, float f2, float f3, Color color) {
        this.a(class_45872, module.getCategory(), f, f2, f3, color);
    }

    private boolean e(Module module) {
        for (Setting<?> setting : module.getSettings()) {
            if (!setting.jS()) continue;
            return true;
        }
        return false;
    }

    private float a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, String string, boolean bl, ContentRenderer contentRenderer, String string2, Runnable runnable, float f6) {
        float f7 = 52.0f;
        Instance instance = Fonts.UNBOUNDED.getFont(10.5f);
        float f8 = f7 + (12.0f + instance.getWidth(string) + 18.0f) * f5;
        boolean bl2 = this.hit(f, f2, f3, f4, f8, f7);
        float f9 = this.b("dkh:" + string2, bl2 && !bl ? 1.0f : 0.0f);
        float f10 = this.b("dkg:" + string2, bl ? 1.0f : 0.0f);
        float f11 = f4 - 2.0f * f9;
        this.a(class_45872, f3, f11, f8, f7, 8.0f, 12.0f, 90, f10);
        if (f5 < 0.999f) {
            this.n(class_45872, f3, f11, f8, f7, 8.0f);
        }
        if (f5 > 0.001f) {
            Render2D.drawRoundedRect(class_45872, f3, f11, f8, f7, 8.0f, this.as(Math.round(255.0f * f5)));
        }
        float f12 = f3 + f7 / 2.0f + (26.0f - f7 / 2.0f) * f5;
        contentRenderer.draw(class_45872, f12, f11 + f7 / 2.0f + f6, 17.0f - f5, this.mix(this.mix(this.MUTED, this.Uo, f9), this.Us, f5));
        if (f5 > 0.02f) {
            Render2D.drawFont(class_45872, instance, string, f3 + 18.0f + 24.0f, f11 + f7 / 2.0f - instance.getHeight() / 2.0f, this.a(this.Us, Math.round(255.0f * f5)));
        }
        this.a(f3, f4, f8, f7, 0, runnable);
        return f8;
    }

    private void e(class_4587 class_45873, float f4, float f5) {
        ArrayList<ModuleCategory> arrayList = new ArrayList<ModuleCategory>();
        for (ModuleCategory moduleCategory : Celebression.getInstance().getModuleManager().getCategories()) {
            if (moduleCategory == ModuleCategory.qo) continue;
            arrayList.add(moduleCategory);
        }
        float f6 = 10.0f;
        float f7 = 52.0f;
        float f8 = 16.0f;
        Instance instance = Fonts.UNBOUNDED.getFont(10.5f);
        LinkedHashMap<ModuleCategory, Float> linkedHashMap = new LinkedHashMap<ModuleCategory, Float>();
        float f9 = 0.0f;
        for (ModuleCategory object2 : arrayList) {
            float string2 = this.b("dka:" + object2.name(), !this.UC && object2 == this.Tm ? 1.0f : 0.0f);
            linkedHashMap.put(object2, Float.valueOf(string2));
            f9 += f7 + (12.0f + instance.getWidth(object2.name()) + 18.0f) * string2 + f6;
        }
        String string = "\u0414\u0440\u0443\u0437\u044c\u044f";
        String string2 = "\u041a\u043e\u043d\u0444\u0438\u0433\u0438";
        String string3 = "\u041c\u0435\u0442\u043a\u0438";
        float f10 = this.b("dka:friends", this.UQ.rn() ? 1.0f : 0.0f);
        float f11 = this.b("dka:configs", this.US.rn() ? 1.0f : 0.0f);
        float f12 = this.b("dka:waypoints", this.UR.rn() ? 1.0f : 0.0f);
        f9 += f8;
        f9 += f7 + (12.0f + instance.getWidth(string) + 18.0f) * f10 + f6;
        f9 += f7 + (12.0f + instance.getWidth(string2) + 18.0f) * f11 + f6;
        f9 += f7 + (12.0f + instance.getWidth(string3) + 18.0f) * f12 + f6;
        f9 += f7 + f6;
        float f13 = 410.0f + (1090.0f - (f9 -= f6)) / 2.0f;
        float f14 = 1035.0f - f7;
        Iterator f17 = arrayList.iterator();
        while (f17.hasNext()) {
            ModuleCategory f18;
            ModuleCategory f19 = f18 = (ModuleCategory)((Object)f17.next());
            boolean bl = !this.UC && f18 == this.Tm;
            float f15 = this.a(class_45873, f4, f5, f13, f14, linkedHashMap.get(f18), f18.name(), bl, (class_45872, f, f2, f3, color) -> this.a(class_45872, f18, f, f2, f3, color), f18.name(), () -> this.b(f18), 0.0f);
            f13 += f15 + f6;
        }
        Render2D.drawRoundedRect(class_45873, f13 - f6 + (f8 - 1.0f) / 2.0f, f14 + f7 / 2.0f - 12.0f, 1.0f, 24.0f, 0.5f, this.at(22));
        float f16 = this.a(class_45873, f4, f5, f13 += f8 - f6, f14, f10, string, this.UQ.rn(), ClickGuiIcons::users, "friends", () -> this.b(this.UQ), 0.0f);
        float f18 = this.a(class_45873, f4, f5, f13 += f16 + f6, f14, f11, string2, this.US.rn(), ClickGuiIcons::gear, "configs", () -> this.b(this.US), 0.0f);
        float f19 = this.a(class_45873, f4, f5, f13 += f18 + f6, f14, f12, string3, this.UR.rn(), ClickGuiIcons::mapPin, "waypoints", () -> this.b(this.UR), 0.0f);
        this.a(class_45873, f4, f5, f13 += f19 + f6, f14, 0.0f, "\u042d\u043b\u0435\u043c\u0435\u043d\u0442\u044b", false, ClickGuiIcons::sliders, "elements", () -> aiG.method_1507((class_437)new HudEditorScreen()), 0.0f);
    }

    private void c(class_332 class_3322, float f2, float f3) {
        float f4;
        Object object;
        float f5;
        float f6;
        float f7;
        class_4587 class_45872 = class_3322.method_51448();
        float f8 = 1540.0f;
        float f9 = 90.0f;
        float f10 = 340.0f;
        float f11 = 920.0f;
        this.a(class_45872, f8, f9, f10, f11, 12.0f, 22.0f, 46, 1.0f);
        UiRenderUtils.drawRoundedRect(class_45872, f8, f9, f10, f11, 12.0f, this.mA());
        UiRenderUtils.b(class_45872, f8 + 1.0f, f9 + 1.0f, f10 - 2.0f, 200.0f, 11.0f, this.as(14), this.as(0));
        UiRenderUtils.c(class_45872, f8, f9, f10, f11, 12.0f, 1.0f, this.at(23));
        float f12 = f8 + 20.0f;
        float f13 = f10 - 40.0f;
        float f14 = f9 + 20.0f;
        ClickGuiIcons.droplet(class_45872, f12 + 6.0f, f14 + 6.0f, 12.0f, this.as(255));
        Instance instance = Fonts.GOLOS_SEMI.getFont(13.0f);
        Render2D.drawFont(class_45872, instance, "\u0422\u0435\u043c\u0430", f12 + 18.0f, f14 + 6.0f - instance.getHeight() / 2.0f, this.i(this.Uo));
        float f15 = f12 + 18.0f + instance.getWidth("\u0422\u0435\u043c\u0430") + 10.0f;
        Render2D.drawRoundedRect(class_45872, f15, f14 + 6.0f, f12 + f13 - f15, 1.0f, 0.5f, this.at(18));
        f14 += 24.0f;
        ClientTheme[] clientThemeArray = ClientTheme.values();
        float f16 = 8.0f;
        float f17 = (f13 - f16) / 2.0f;
        float f18 = 28.0f;
        ClientTheme clientTheme = UiThemeController.oX();
        for (int i = 0; i < clientThemeArray.length; ++i) {
            int n = i % 2;
            int n2 = i / 2;
            float f19 = f12 + (float)n * (f17 + f16);
            f7 = f14 + (float)n2 * (f18 + f16);
            ClientTheme clientTheme2 = clientThemeArray[i];
            boolean bl = clientTheme2 == clientTheme;
            f6 = this.b("thg:" + clientTheme2.getName(), bl ? 1.0f : 0.0f);
            f5 = this.b("thh:" + clientTheme2.getName(), this.hit(f2, f3, f19, f7, f17, f18) ? 1.0f : 0.0f);
            this.a(class_45872, f19, f7, f17, f18, 10.0f, 8.0f, 140, f6);
            Color[] colorArray = this.cj(clientTheme2.getName());
            Render2D.drawRoundedVerticalGradient(class_45872, f19, f7, f17, f18, 10.0f, this.i(colorArray[0]), this.i(colorArray[1]));
            Render2D.drawBorder(class_45872, f19, f7, f17, f18, 10.0f, 0.9f, new ColorRGBA(bl ? this.as(140) : this.at(Math.round(20.0f + 69.0f * f5))));
            Render2D.drawCircle(class_45872, f19 + 13.0f + 4.0f, f7 + f18 / 2.0f, 4.0f, this.i(clientTheme2.pe()));
            object = this.ck(clientTheme2.getName()) ? this.i(new Color(26, 31, 28)) : this.i(new Color(223, 229, 225));
            Render2D.drawFont(class_45872, Fonts.GOLOS.getFont(12.0f), clientTheme2.getName(), f19 + 26.0f, f7 + f18 / 2.0f - Fonts.GOLOS.getFont(12.0f).getHeight() / 2.0f, (Color)object);
            if (bl) {
                ClickGuiIcons.check(class_45872, f19 + f17 - 14.0f, f7 + f18 / 2.0f, 12.0f, this.as(255));
            }
            this.a(f19, f7, f17, f18, 0, () -> UiThemeController.c(clientTheme2));
        }
        ClickGuiIcons.sliders(class_45872, f12 + 6.0f, (f14 += 4.0f * (f18 + f16) + 4.0f) + 6.0f, 12.0f, this.as(255));
        Render2D.drawFont(class_45872, instance, "\u0418\u043d\u0442\u0435\u0440\u0444\u0435\u0439\u0441", f12 + 18.0f, f14 + 6.0f - instance.getHeight() / 2.0f, this.i(this.Uo));
        float f20 = f12 + 18.0f + instance.getWidth("\u0418\u043d\u0442\u0435\u0440\u0444\u0435\u0439\u0441") + 10.0f;
        Render2D.drawRoundedRect(class_45872, f20, f14 + 6.0f, f12 + f13 - f20, 1.0f, 0.5f, this.at(18));
        f14 += 24.0f;
        UI uI = Celebression.getInstance().getModuleManager().getModule(UI.class);
        if (uI == null) {
            return;
        }
        float f21 = 103.0f;
        this.l(class_45872, f12, f14, f13, f21);
        SliderSetting sliderSetting = this.a(uI, "settings.ui.scale");
        f7 = f14 + 13.0f;
        if (sliderSetting != null) {
            this.a(class_45872, f12 + 13.0f, f7, f13 - 26.0f, "\u041c\u0430\u0441\u0448\u0442\u0430\u0431 \u0438\u043d\u0442\u0435\u0440\u0444\u0435\u0439\u0441\u0430", this.aj(((Float)sliderSetting.jT()).floatValue()), this.a(sliderSetting), f2, f3, (Float f) -> this.a(sliderSetting, f.floatValue()), f12 + 13.0f, f13 - 26.0f);
            f7 += 43.0f;
        }
        float f22 = this.b("btnApply", this.hit(f2, f3, f12 + 13.0f, f7, f4 = (f13 - 26.0f - 8.0f) / 2.0f, 34.0f) ? 1.0f : 0.0f);
        this.a(class_45872, f12 + 13.0f, f7, f4, 34.0f, 7.0f, 7.0f, 128, 1.0f);
        Render2D.drawRoundedRect(class_45872, f12 + 13.0f, f7, f4, 34.0f, 7.0f, this.mix(this.as(255), this.at(255), 0.07f * f22));
        Render2D.drawFont(class_45872, Fonts.UNBOUNDED.getFont(10.0f), "\u041f\u0440\u0438\u043c\u0435\u043d\u0438\u0442\u044c", f12 + 13.0f + (f4 - Fonts.UNBOUNDED.getFont(10.0f).getWidth("\u041f\u0440\u0438\u043c\u0435\u043d\u0438\u0442\u044c")) / 2.0f, f7 + 17.0f - Fonts.UNBOUNDED.getFont(10.0f).getHeight() / 2.0f, this.i(this.Us));
        this.a(f12 + 13.0f, f7, f4, 34.0f, 0, uI::applyScaleSetting);
        f6 = f12 + 13.0f + f4 + 8.0f;
        f5 = this.b("btnReset", this.hit(f2, f3, f6, f7, f4, 34.0f) ? 1.0f : 0.0f);
        this.n(class_45872, f6, f7, f4, 34.0f, 7.0f);
        if (f5 > 0.01f) {
            Render2D.drawBorder(class_45872, f6, f7, f4, 34.0f, 7.0f, 0.9f, new ColorRGBA(this.at(Math.round(61.0f * f5))));
        }
        Render2D.drawFont(class_45872, Fonts.UNBOUNDED.getFont(10.0f), "\u0421\u0431\u0440\u043e\u0441", f6 + (f4 - Fonts.UNBOUNDED.getFont(10.0f).getWidth("\u0421\u0431\u0440\u043e\u0441")) / 2.0f, f7 + 17.0f - Fonts.UNBOUNDED.getFont(10.0f).getHeight() / 2.0f, this.i(this.mix(this.Up, this.Uo, f5)));
        this.a(f6, f7, f4, 34.0f, 0, uI::resetScaleSetting);
        float f23 = 156.0f;
        this.l(class_45872, f12, f14 += f21 + 12.0f, f13, f23);
        f7 = f14 + 13.0f;
        Render2D.drawFont(class_45872, Fonts.UNBOUNDED_L.getFont(10.0f), "\u0422\u0440\u0435\u043a\u0435\u0440 \u043a\u0443\u0440\u0441\u043e\u0440\u0430", f12 + 13.0f, f7 + 4.0f, this.i(this.TEXT3));
        this.b(class_45872, f12 + f13 - 13.0f - 36.0f, f7, "cursorTracker", this.UM.mH());
        this.a(f12 + f13 - 13.0f - 36.0f, f7, 36.0f, 20.0f, 0, () -> this.UM.y(!this.UM.mH()));
        f7 += 33.0f;
        SliderSetting transparencySetting = this.a(uI, "settings.ui.transparency");
        if (transparencySetting != null) {
            this.a(class_45872, f12 + 13.0f, f7, f13 - 26.0f, "\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c GUI", this.ak(((Float)transparencySetting.jT()).floatValue()), this.a(transparencySetting), f2, f3, value -> this.c(transparencySetting, value), f12 + 13.0f, f13 - 26.0f);
            f7 += 43.0f;
        }
        Render2D.drawFont(class_45872, Fonts.UNBOUNDED_L.getFont(10.0f), "\u041a\u043b\u0430\u0432\u0438\u0448\u0430 \u043e\u0442\u043a\u0440\u044b\u0442\u0438\u044f", f12 + 13.0f, f7, this.i(this.TEXT3));
        boolean bl = this.Vf == Page.WF;
        String string = bl ? "..." : this.c(uI.getBind()).toUpperCase(Locale.ROOT).replace('_', ' ');
        float f24 = this.b("openkey", this.hit(f2, f3, f12 + 13.0f, f7 += 8.0f + Fonts.UNBOUNDED_L.getFont(10.0f).getHeight() * 0.0f + 6.0f, f13 - 26.0f, 36.0f) || bl ? 1.0f : 0.0f);
        this.a(class_45872, f12 + 13.0f, f7, f13 - 26.0f, 36.0f, string, f24);
        this.a(f12 + 13.0f, f7, f13 - 26.0f, 36.0f, 0, () -> this.a(Page.WF, null, null));
        float f25 = 361.0f;
        this.l(class_45872, f12, f14 += f23 + 12.0f, f13, f25);
        f7 = f14 + 13.0f;
        EnumSetting<?> enumSetting = this.c(uI, "settings.ui.hud_style");
        EnumSetting<?> enumSetting2 = this.c(uI, "settings.ui.hud_shape");
        float f26 = (f13 - 26.0f - 8.0f) / 2.0f;
        this.a(class_45872, f12 + 13.0f, f7, f26, "\u0421\u0442\u0438\u043b\u044c HUD", enumSetting != null ? class_1074.method_4662((String)enumSetting.jZ(), (Object[])new Object[0]) : "\u2014", f2, f3, enumSetting);
        this.a(class_45872, f12 + 13.0f + f26 + 8.0f, f7, f26, "\u0424\u043e\u0440\u043c\u0430 HUD", enumSetting2 != null ? class_1074.method_4662((String)enumSetting2.jZ(), (Object[])new Object[0]) : "\u2014", f2, f3, enumSetting2);
        EnumSetting<?> enumSetting3 = this.c(uI, "settings.ui.gui");
        this.a(class_45872, f12 + 13.0f, f7 += 65.0f, f13 - 26.0f, "\u0421\u0442\u0438\u043b\u044c GUI", enumSetting3 != null ? class_1074.method_4662((String)enumSetting3.jZ(), (Object[])new Object[0]) : "\u2014", f2, f3, enumSetting3);
        BooleanSetting booleanSetting = this.b(uI, "settings.ui.scoreboard.custom");
        Render2D.drawFont(class_45872, Fonts.UNBOUNDED_L.getFont(10.0f), "\u0421\u0432\u043e\u0439 \u0441\u043a\u043e\u0440\u0431\u043e\u0440\u0434", f12 + 13.0f, (f7 += 65.0f) + 4.0f, this.i(this.TEXT3));
        if (booleanSetting != null) {
            this.b(class_45872, f12 + f13 - 13.0f - 36.0f, f7, "sbtgl", (Boolean)booleanSetting.jT());
            this.a(f12 + f13 - 13.0f - 36.0f, f7, 36.0f, 20.0f, 0, () -> booleanSetting.a((Boolean)booleanSetting.jT() == false));
        }
        this.a(class_45872, "\u0426\u0432\u0435\u0442 \u0444\u043e\u043d\u0430 \u0441\u043a\u043e\u0440\u0431\u043e\u0440\u0434\u0430", f12 + 13.0f, f7 += 33.0f, f13 - 26.0f, this.d(uI, "settings.ui.scoreboard.background"));
        f7 += 33.0f;
        ColorSetting colorSetting = this.d(uI, "settings.ui.scoreboard.background");
        if (colorSetting != null) {
            float f27 = (float)((Color)colorSetting.jT()).getAlpha() / 255.0f;
            this.a(class_45872, f12 + 13.0f, f7, f13 - 26.0f, "\u041d\u0435\u043f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c", Math.round(f27 * 100.0f) + "%", f27, f2, f3, (Float f) -> {
                Color color = (Color)colorSetting.jT();
                colorSetting.d(new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(class_3532.method_15363((float)f.floatValue(), (float)0.0f, (float)1.0f) * 255.0f)));
            }, f12 + 13.0f, f13 - 26.0f);
        }
        f7 += 43.0f;
        SliderSetting sliderSetting2 = this.a(uI, "settings.ui.scoreboard.radius");
        if (sliderSetting2 != null) {
            this.a(class_45872, f12 + 13.0f, f7, f13 - 26.0f, "\u0420\u0430\u0434\u0438\u0443\u0441 \u0443\u0433\u043b\u043e\u0432", String.valueOf(Math.round(((Float)sliderSetting2.jT()).floatValue())), this.a(sliderSetting2), f2, f3, (Float f) -> this.a(sliderSetting2, f.floatValue()), f12 + 13.0f, f13 - 26.0f);
        }
        BooleanSetting booleanSetting2 = this.b(uI, "settings.ui.tab.custom");
        Render2D.drawFont(class_45872, Fonts.UNBOUNDED_L.getFont(10.0f), "\u0421\u0432\u043e\u0439 \u0442\u0430\u0431", f12 + 13.0f, (f7 += 43.0f) + 4.0f, this.i(this.TEXT3));
        if (booleanSetting2 != null) {
            this.b(class_45872, f12 + f13 - 13.0f - 36.0f, f7, "tabtgl", (Boolean)booleanSetting2.jT());
            this.a(f12 + f13 - 13.0f - 36.0f, f7, 36.0f, 20.0f, 0, () -> booleanSetting2.a((Boolean)booleanSetting2.jT() == false));
        }
        this.a(class_45872, "\u0426\u0432\u0435\u0442 \u0444\u043e\u043d\u0430 \u0442\u0430\u0431\u0430", f12 + 13.0f, f7 += 33.0f, f13 - 26.0f, this.d(uI, "settings.ui.tab.color"));
        Instance instance2 = Fonts.GOLOS_SEMI.getFont(11.5f);
        float f28 = f9 + f11 - 20.0f - instance2.getHeight();
        this.drawSpaced(class_45872, instance2, "celebression", f12, f28, this.i(this.MUTED), -0.345f);
        float f29 = this.widthSpaced(instance2, "celebression", -0.345f);
        Render2D.drawFont(class_45872, instance2, ".", f12 + f29, f28, this.as(255));
        Instance instance3 = Fonts.PLEXMONO.getFont(9.5f);
        String string2 = "1.21.4 \u00b7 412";
        float f30 = instance3.getWidth(string2);
        Render2D.drawFont(class_45872, instance3, string2, f12 + f13 - f30, f28, this.i(this.Ur));
        float f31 = f12 + f29 + instance2.getWidth(".") + 10.0f;
        Render2D.drawRoundedRect(class_45872, f31, f28 + instance2.getHeight() / 2.0f, f12 + f13 - f30 - 10.0f - f31, 1.0f, 0.5f, this.at(16));
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, String string, String string2, float f4, float f5, EnumSetting<?> enumSetting) {
        Render2D.drawFont(class_45872, Fonts.UNBOUNDED_L.getFont(10.0f), string, f, f2, this.i(this.TEXT3));
        float f6 = f2 + 16.0f;
        float f7 = 36.0f;
        boolean bl = this.hit(f4, f5, f, f6, f3, f7);
        float f8 = this.b("sel:" + string, bl ? 1.0f : 0.0f);
        Render2D.drawRoundedVerticalGradient(class_45872, f, f6, f3, f7, 7.0f, this.nF(), this.nG());
        Render2D.drawBorder(class_45872, f, f6, f3, f7, 7.0f, 0.8f, new ColorRGBA(this.at(Math.round(24.0f + 37.0f * f8))));
        Instance instance = Fonts.UNBOUNDED.getFont(10.0f);
        this.a(class_45872, instance, "selv:" + string, this.clip(instance, string2, f3 - 30.0f), f + 12.0f, f6 + f7 / 2.0f - instance.getHeight() / 2.0f, this.i(this.Uo));
        ClickGuiIcons.chevronDown(class_45872, f + f3 - 14.0f, f6 + f7 / 2.0f, 12.0f, this.i(this.MUTED3));
        if (enumSetting != null) {
            if (enumSetting == this.VU) {
                this.VW = f;
                this.VX = f6;
                this.VY = f3;
                this.VZ = f7;
            }
            this.a(f, f6, f3, f7, 0, () -> this.a(enumSetting, f, f6, f3, f7));
        }
    }

    private void a(EnumSetting<?> enumSetting, float f, float f2, float f3, float f4) {
        if (enumSetting == null) {
            return;
        }
        if (this.VU == enumSetting) {
            this.VU = null;
            return;
        }
        this.VU = enumSetting;
        this.VW = f;
        this.VX = f2;
        this.VY = f3;
        this.VZ = f4;
    }

    private void h(class_4587 class_45872, float f, float f2, float f3) {
        EnumSetting<?> enumSetting = this.VV;
        if (enumSetting == null) {
            return;
        }
        Enum<?>[] EArray = (Enum<?>[])((Enum<?>)enumSetting.jT()).getDeclaringClass().getEnumConstants();
        if (EArray == null || EArray.length == 0) {
            return;
        }
        Object Value = enumSetting.jT();
        float f4 = 24.0f;
        float f5 = this.VW;
        float f6 = this.VY;
        float f7 = this.VX + this.VZ + 4.0f;
        float f8 = (float)EArray.length * f4 + 8.0f;
        if (f7 + f8 > 1080.0f) {
            f7 = Math.max(0.0f, this.VX - f8 - 4.0f);
        }
        this.Wa = f7;
        this.Wb = f4;
        float f9 = 1.0f - (1.0f - f3) * (1.0f - f3) * (1.0f - f3);
        float f10 = Math.max(1.0f, f8 * f9);
        this.n(class_45872, f5, f7, f6, f10, 8.0f);
        Instance instance = Fonts.UNBOUNDED.getFont(10.0f);
        float f11 = f7 + 4.0f;
        for (Object e : EArray) {
            float f12 = class_3532.method_15363((float)((f7 + f10 - f11) / f4), (float)0.0f, (float)1.0f);
            if (f12 > 0.02f) {
                String string;
                Object object;
                boolean bl = e == Value;
                boolean bl2 = f10 >= f8 - 1.0f && this.hit(f, f2, f5 + 3.0f, f11, f6 - 6.0f, f4);
                float f13 = this.b("edrow:" + enumSetting.getName() + ":" + String.valueOf(e), bl || bl2 ? 1.0f : 0.0f);
                if (f13 > 0.01f) {
                    Render2D.drawRoundedRect(class_45872, f5 + 4.0f, f11 + 1.0f, f6 - 8.0f, f4 - 2.0f, 6.0f, this.as(Math.round((float)(bl ? 42 : 26) * f13 * f12)));
                }
                if (e instanceof NamedOption) {
                    NamedOption option = (NamedOption)e;
                    string = class_1074.method_4662(option.getName(), (Object[])new Object[0]);
                } else {
                    string = String.valueOf(e);
                }
                String string2 = string;
                object = bl ? this.as(Math.round(255.0f * f12)) : this.a(this.Uo, Math.round(220.0f * f12));
                Render2D.drawFont(class_45872, instance, this.clip(instance, string2, f6 - 22.0f), f5 + 12.0f, f11 + f4 / 2.0f - instance.getHeight() / 2.0f, (Color)object);
            }
            f11 += f4;
        }
        Render2D.drawBorder(class_45872, f5, f7, f6, f10, 8.0f, 0.8f, new ColorRGBA(this.at(Math.round(34.0f * f9))));
    }

    private void a(Module module, float f, float f2) {
        this.Vi = module;
        this.Vj = f;
        this.Vk = f2;
    }

    private void f(class_4587 class_45872, float f, float f2) {
        Runnable[] runnableArray;
        String[] stringArray;
        if (this.Vi == null) {
            return;
        }
        this.WB = true;
        Module module = this.Vi;
        boolean bl = false;
        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof BooleanSetting || setting instanceof EnumSetting || setting instanceof SliderSetting || setting instanceof BindSetting || setting instanceof ColorSetting || setting instanceof StringSetting || setting instanceof ButtonSetting) continue;
            bl = true;
            break;
        }
        float f3 = 160.0f;
        float f4 = 30.0f;
        float f5 = f4 * (bl ? 3.0f : 2.0f) + 12.0f;
        float f6 = Math.min(this.Vj, 1920.0f - f3 - 10.0f);
        float f7 = Math.min(this.Vk, 1080.0f - f5 - 10.0f);
        this.n(class_45872, f6, f7, f3, f5, 8.0f);
        float f8 = f6 + f3 * 0.5f;
        float f9 = f7;
        if (bl) {
            String[] stringArray2 = new String[3];
            stringArray2[0] = "\u0411\u0438\u043d\u0434\u0438\u043d\u0433";
            stringArray2[1] = "\u0412\u0441\u0435 \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438";
            stringArray = stringArray2;
            stringArray2[2] = "\u0421\u0431\u0440\u043e\u0441 \u043d\u0430\u0441\u0442\u0440\u043e\u0435\u043a";
        } else {
            String[] stringArray3 = new String[2];
            stringArray3[0] = "\u0411\u0438\u043d\u0434\u0438\u043d\u0433";
            stringArray = stringArray3;
            stringArray3[1] = "\u0421\u0431\u0440\u043e\u0441 \u043d\u0430\u0441\u0442\u0440\u043e\u0435\u043a";
        }
        String[] stringArray4 = stringArray;
        if (bl) {
            Runnable[] runnableArray2 = new Runnable[3];
            runnableArray2[0] = () -> this.a(Page.WD, module, null);
            runnableArray2[1] = () -> ClickRippleManager.c(module, f8, f9);
            runnableArray = runnableArray2;
            runnableArray2[2] = () -> {
                for (Setting<?> setting : module.getSettings()) {
                    setting.reset();
                }
            };
        } else {
            Runnable[] runnableArray3 = new Runnable[2];
            runnableArray3[0] = () -> this.a(Page.WD, module, null);
            runnableArray = runnableArray3;
            runnableArray3[1] = () -> {
                for (Setting<?> setting : module.getSettings()) {
                    setting.reset();
                }
            };
        }
        Runnable[] runnableArray4 = runnableArray;
        for (int i = 0; i < stringArray4.length; ++i) {
            float f10 = f7 + 6.0f + (float)i * f4;
            boolean bl2 = this.hit(f, f2, f6 + 4.0f, f10, f3 - 8.0f, f4);
            float f11 = this.b("ctx:" + i, bl2 ? 1.0f : 0.0f);
            if (f11 > 0.01f) {
                Render2D.drawRoundedRect(class_45872, f6 + 4.0f, f10, f3 - 8.0f, f4, 6.0f, this.at(Math.round(12.0f * f11)));
            }
            Render2D.drawFont(class_45872, Fonts.GOLOS.getFont(11.5f), stringArray4[i], f6 + 12.0f, f10 + f4 / 2.0f - Fonts.GOLOS.getFont(11.5f).getHeight() / 2.0f, this.i(i == stringArray4.length - 1 ? this.Ut : this.Uo));
            Runnable runnable = runnableArray4[i];
            this.a(f6 + 4.0f, f10, f3 - 8.0f, f4, 0, () -> {
                runnable.run();
                this.Vi = null;
            });
        }
        this.WB = false;
    }

    private float ng() {
        return Math.max(Math.max(this.UP.rk(), this.US.rk()), Math.max(this.UQ.rk(), this.UR.rk()));
    }

    private boolean nh() {
        return this.UP.rn() || this.UQ.rn() || this.UR.rn() || this.US.rn();
    }

    private void b(Animation animation) {
        boolean bl = !animation.rn();
        this.UP.i(animation == this.UP && bl);
        this.UQ.i(animation == this.UQ && bl);
        this.UR.i(animation == this.UR && bl);
        this.US.i(animation == this.US && bl);
        this.ni().forEach(searchBar -> searchBar.setFocused(false));
    }

    private List<SearchBar> ni() {
        return List.of(this.UT, this.UU, this.UW, this.UX, this.UY, this.UV);
    }

    private void a(SearchBar searchBar) {
        this.UB.setFocused(false);
        this.Vp = null;
        for (SearchBar searchBar2 : this.ni()) {
            searchBar2.setFocused(searchBar2 == searchBar);
        }
    }

    private Integer ce(String string) {
        try {
            return Integer.valueOf(string.trim());
        }
        catch (Exception exception) {
            return null;
        }
    }

    private void nj() {
        String string = this.UU.getSearchText().trim();
        Integer n = this.ce(this.UW.getSearchText());
        Integer n2 = this.ce(this.UX.getSearchText());
        Integer n3 = this.ce(this.UY.getSearchText());
        if (string.isEmpty() || n == null || n2 == null || n3 == null) {
            return;
        }
        Celebression.getInstance().getWaypointManager().a(new NamedItem(string, n.intValue(), n2.intValue(), n3.intValue()));
        this.UU.setSearchText("");
        this.UW.setSearchText("");
        this.UX.setSearchText("");
        this.UY.setSearchText("");
    }

    private void nk() {
        boolean bl;
        boolean bl2 = bl = this.ce(this.UW.getSearchText()) != null && this.ce(this.UX.getSearchText()) != null && this.ce(this.UY.getSearchText()) != null;
        if (bl) {
            this.nj();
        } else {
            this.nm();
        }
    }

    private void a(StringSetting stringSetting) {
        this.Vp = stringSetting;
        this.UB.setFocused(false);
        this.UT.setFocused(false);
        this.UU.setFocused(false);
    }

    private String b(Instance instance, String string, float f) {
        int n;
        if (instance.getWidth(string) <= f) {
            return string;
        }
        for (n = 0; n < string.length() && instance.getWidth(string.substring(n)) > f; ++n) {
        }
        return string.substring(Math.min(n, string.length()));
    }

    private void nl() {
        String string = this.UT.getSearchText().trim();
        if (string.isEmpty()) {
            return;
        }
        FriendManager friendManager = Celebression.getInstance().getFriendManager();
        if (!friendManager.bv(string)) {
            friendManager.bt(string);
        }
        this.UT.setSearchText("");
        Celebression celebression = Celebression.getInstance();
        if (celebression.getConfigManager() != null) {
            celebression.getConfigManager().dU();
        }
    }

    private void nm() {
        String string = this.UU.getSearchText().trim();
        if (string.isEmpty() || LinkGuiScreen.aiG.field_1724 == null) {
            return;
        }
        class_2338 class_23382 = LinkGuiScreen.aiG.field_1724.method_24515();
        Celebression.getInstance().getWaypointManager().a(new NamedItem(string, class_23382.method_10263(), class_23382.method_10264(), class_23382.method_10260()));
        this.UU.setSearchText("");
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, SearchBar searchBar, String string, String string2) {
        boolean bl = searchBar.isFocused();
        float f5 = this.b(string2, bl ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, 7.0f, this.at(10));
        UiRenderUtils.c(class_45872, f, f2, f3, f4, 7.0f, 0.9f, this.mix(this.at(18), this.as(150), f5));
        Instance instance = Fonts.GOLOS.getFont(11.5f);
        String string3 = searchBar.getSearchText();
        float f6 = f + 12.0f;
        float f7 = f2 + f4 / 2.0f - instance.getHeight() / 2.0f;
        if (string3.isEmpty() && !bl) {
            Render2D.drawFont(class_45872, instance, string, f6, f7, this.i(this.MUTED3));
        } else {
            String string4 = this.clip(instance, string3, f3 - 22.0f);
            Render2D.drawFont(class_45872, instance, string4, f6, f7, this.i(this.Uo));
            if (bl) {
                float f8 = instance.getWidth(string4);
                float f9 = 0.35f + 0.65f * (0.5f + 0.5f * (float)Math.sin((double)(System.currentTimeMillis() % 1000L) / 1000.0 * Math.PI * 2.0));
                Render2D.drawRoundedRect(class_45872, f6 + f8 + 1.0f, f2 + f4 / 2.0f - 7.0f, 1.0f, 14.0f, 0.5f, this.a(this.Uo, Math.round(255.0f * f9)));
            }
        }
        this.a(f, f2, f3, f4, 0, () -> this.a(searchBar));
    }

    private float a(class_4587 class_45872, float f, float f2, float f3, float f4, ContentRenderer contentRenderer, String string, String string2, Animation animation, float f5, float f6) {
        this.n(class_45872, f, f2, f3, f4, 8.0f);
        float f7 = 16.0f;
        float f8 = f + f7;
        float f9 = f2 + 14.0f;
        float f10 = f3 - f7 * 2.0f;
        float f11 = 30.0f;
        this.a(class_45872, f8, f9, f11, f11, 6.0f, 6.0f, 90, 1.0f);
        Render2D.drawRoundedRect(class_45872, f8, f9, f11, f11, 6.0f, this.as(28));
        Render2D.drawBorder(class_45872, f8, f9, f11, f11, 6.0f, 0.8f, new ColorRGBA(this.as(90)));
        contentRenderer.draw(class_45872, f8 + f11 / 2.0f, f9 + f11 / 2.0f, 15.0f, this.as(255));
        Render2D.drawFont(class_45872, Fonts.UNBOUNDED.getFont(12.0f), string, f8 + f11 + 11.0f, f9 + 2.0f, this.i(this.TEXT));
        if (string2 != null) {
            Render2D.drawFont(class_45872, Fonts.GOLOS.getFont(11.0f), string2, f8 + f11 + 11.0f, f9 + 15.0f, this.i(this.MUTED3));
        }
        float f12 = 22.0f;
        float f13 = f8 + f10 - f12;
        float f14 = f9;
        float f15 = this.b("swx:" + string, this.hit(f5, f6, f13, f14, f12, f12) ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f13, f14, f12, f12, 6.0f, this.mix(this.at(8), this.a(this.Ut, 34), f15));
        if (f15 > 0.01f) {
            Render2D.drawBorder(class_45872, f13, f14, f12, f12, 6.0f, 0.9f, new ColorRGBA(this.a(this.Ut, Math.round(150.0f * f15))));
        }
        ClickGuiIcons.close(class_45872, f13 + f12 / 2.0f, f14 + f12 / 2.0f, 12.0f, this.i(this.mix(this.MUTED, this.Ut, f15)));
        this.a(f13, f14, f12, f12, 0, () -> {
            animation.i(false);
            this.ni().forEach(searchBar -> searchBar.setFocused(false));
        });
        Render2D.drawRoundedRect(class_45872, f8, f9 + 30.0f + 6.0f, f10, 1.0f, 0.5f, this.at(16));
        return f9 + 30.0f + 12.0f;
    }

    private void d(class_332 class_3322, float f, float f2) {
        class_4587 class_45872 = class_3322.method_51448();
        float f3 = 1540.0f;
        float f4 = 90.0f;
        float f5 = 340.0f;
        float f6 = 600.0f;
        List<String> list = Celebression.getInstance().getFriendManager().es();
        float f7 = this.a(class_45872, f3, f4, f5, f6, ClickGuiIcons::users, "\u0414\u0440\u0443\u0437\u044c\u044f", list.size() + " \u0432 \u0441\u043f\u0438\u0441\u043a\u0435", this.UQ, f, f2);
        float f8 = f3 + 16.0f;
        float f9 = f5 - 32.0f;
        float f10 = 82.0f;
        float f11 = 32.0f;
        float f12 = f9 - f10 - 8.0f;
        this.a(class_45872, f8, f7, f12, f11, this.UT, "\u041d\u0438\u043a \u0438\u0433\u0440\u043e\u043a\u0430...", "frinF");
        boolean bl = !this.UT.getSearchText().trim().isEmpty();
        float f13 = this.b("fradd", this.hit(f, f2, f8 + f12 + 8.0f, f7, f10, f11) && bl ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f8 + f12 + 8.0f, f7, f10, f11, 7.0f, bl ? this.mix(this.as(210), this.as(255), f13) : this.at(12));
        Instance instance = Fonts.UNBOUNDED.getFont(9.5f);
        Render2D.drawFont(class_45872, instance, "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c", f8 + f12 + 8.0f + (f10 - instance.getWidth("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c")) / 2.0f, f7 + f11 / 2.0f - instance.getHeight() / 2.0f, bl ? this.i(this.Us) : this.i(this.MUTED3));
        if (bl) {
            this.a(f8 + f12 + 8.0f, f7, f10, f11, 0, this::nl);
        }
        float f14 = f7 += f11 + 12.0f;
        float f15 = f4 + f6 - 16.0f;
        float f16 = f15 - f14;
        float f17 = 34.0f;
        float f18 = 6.0f;
        int n = list.size();
        float f19 = Math.max(0.0f, (float)n * (f17 + f18) - f16);
        this.Va = class_3532.method_15363((float)this.Va, (float)0.0f, (float)f19);
        this.UZ += (this.Va - this.UZ) * (1.0f - (float)Math.exp(-16.0f * this.Wi));
        Render2D.startScissor(class_3322, f8, f14, f9 + 6.0f, f16);
        if (n == 0) {
            Instance instance2 = Fonts.GOLOS.getFont(11.5f);
            String string = "\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439 \u043f\u0443\u0441\u0442";
            Render2D.drawFont(class_45872, instance2, string, f8 + (f9 - instance2.getWidth(string)) / 2.0f, f14 + 20.0f, this.i(this.MUTED3));
        } else {
            Instance instance3 = Fonts.GOLOS.getFont(12.0f);
            Instance instance4 = Fonts.ICONS2.getFont(11.0f);
            for (int i = 0; i < n; ++i) {
                String string = list.get(i);
                float f20 = f14 + (float)i * (f17 + f18) - this.UZ;
                if (f20 + f17 < f14 - 4.0f || f20 > f15 + 4.0f) continue;
                Render2D.drawRoundedRect(class_45872, f8, f20, f9, f17, 7.0f, this.at(8));
                Render2D.drawBorder(class_45872, f8, f20, f9, f17, 7.0f, 0.8f, new ColorRGBA(this.at(16)));
                Render2D.drawFont(class_45872, instance4, "A", f8 + 12.0f, f20 + f17 / 2.0f - instance4.getHeight() / 2.0f, this.i(this.MUTED));
                Render2D.drawFont(class_45872, instance3, this.clip(instance3, string, f9 - 62.0f), f8 + 28.0f, f20 + f17 / 2.0f - instance3.getHeight() / 2.0f, this.i(this.Uo));
                float f21 = 22.0f;
                float f22 = f8 + f9 - f21 - 6.0f;
                float f23 = f20 + (f17 - f21) / 2.0f;
                float f24 = this.b("frx:" + string, this.hit(f, f2, f22, f23, f21, f21) ? 1.0f : 0.0f);
                Render2D.drawRoundedRect(class_45872, f22, f23, f21, f21, 6.0f, this.mix(this.at(8), this.a(this.Ut, 34), f24));
                ClickGuiIcons.close(class_45872, f22 + f21 / 2.0f, f23 + f21 / 2.0f, 11.0f, this.i(this.mix(this.MUTED, this.Ut, f24)));
                if (!(f20 >= f14 - 1.0f) || !(f20 + f17 <= f15 + 1.0f)) continue;
                String string2 = string;
                this.a(f22, f23, f21, f21, 0, () -> {
                    Celebression.getInstance().getFriendManager().bu(string2);
                    Celebression celebression = Celebression.getInstance();
                    if (celebression.getConfigManager() != null) {
                        celebression.getConfigManager().dU();
                    }
                });
            }
        }
        Render2D.stopScissor(class_3322);
    }

    private void e(class_332 class_3322, float f, float f2) {
        class_4587 class_45872 = class_3322.method_51448();
        float f3 = 1540.0f;
        float f4 = 90.0f;
        float f5 = 340.0f;
        float f6 = 600.0f;
        List<NamedItem> list = Celebression.getInstance().getWaypointManager().ff();
        float f7 = this.a(class_45872, f3, f4, f5, f6, ClickGuiIcons::mapPin, "\u041c\u0435\u0442\u043a\u0438", list.size() + " \u043c\u0435\u0442\u043e\u043a", this.UR, f, f2);
        float f8 = f3 + 16.0f;
        float f9 = f5 - 32.0f;
        this.a(class_45872, f8, f7, f9, 32.0f, this.UU, "\u0418\u043c\u044f \u043c\u0435\u0442\u043a\u0438...", "wpinF");
        float f10 = (f9 - 12.0f) / 3.0f;
        float f11 = 30.0f;
        this.a(class_45872, f8, f7 += 40.0f, f10, f11, this.UW, "X", "wpX");
        this.a(class_45872, f8 + f10 + 6.0f, f7, f10, f11, this.UX, "Y", "wpY");
        this.a(class_45872, f8 + 2.0f * (f10 + 6.0f), f7, f10, f11, this.UY, "Z", "wpZ");
        f7 += f11 + 8.0f;
        boolean bl = !this.UU.getSearchText().trim().isEmpty();
        boolean bl2 = bl && LinkGuiScreen.aiG.field_1724 != null;
        boolean bl3 = bl && this.ce(this.UW.getSearchText()) != null && this.ce(this.UX.getSearchText()) != null && this.ce(this.UY.getSearchText()) != null;
        float f12 = 32.0f;
        float f13 = (f9 - 8.0f) / 2.0f;
        Instance instance = Fonts.UNBOUNDED.getFont(9.5f);
        float f14 = this.b("wphere", this.hit(f, f2, f8, f7, f13, f12) && bl2 ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f8, f7, f13, f12, 7.0f, bl2 ? this.mix(this.as(210), this.as(255), f14) : this.at(12));
        String string = LinkGuiScreen.aiG.field_1724 == null ? "\u041d\u0435\u0442 \u043c\u0438\u0440\u0430" : "\u0417\u0434\u0435\u0441\u044c";
        Render2D.drawFont(class_45872, instance, string, f8 + (f13 - instance.getWidth(string)) / 2.0f, f7 + f12 / 2.0f - instance.getHeight() / 2.0f, bl2 ? this.i(this.Us) : this.i(this.MUTED3));
        if (bl2) {
            this.a(f8, f7, f13, f12, 0, this::nm);
        }
        float f15 = this.b("wpcoord", this.hit(f, f2, f8 + f13 + 8.0f, f7, f13, f12) && bl3 ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f8 + f13 + 8.0f, f7, f13, f12, 7.0f, bl3 ? this.mix(this.as(210), this.as(255), f15) : this.at(12));
        String string2 = "\u041f\u043e \u043a\u043e\u043e\u0440\u0434.";
        Render2D.drawFont(class_45872, instance, string2, f8 + f13 + 8.0f + (f13 - instance.getWidth(string2)) / 2.0f, f7 + f12 / 2.0f - instance.getHeight() / 2.0f, bl3 ? this.i(this.Us) : this.i(this.MUTED3));
        if (bl3) {
            this.a(f8 + f13 + 8.0f, f7, f13, f12, 0, this::nj);
        }
        float f16 = f7 += f12 + 12.0f;
        float f17 = f4 + f6 - 16.0f;
        float f18 = f17 - f16;
        float f19 = 40.0f;
        float f20 = 6.0f;
        int n = list.size();
        float f21 = Math.max(0.0f, (float)n * (f19 + f20) - f18);
        this.Vc = class_3532.method_15363((float)this.Vc, (float)0.0f, (float)f21);
        this.Vb += (this.Vc - this.Vb) * (1.0f - (float)Math.exp(-16.0f * this.Wi));
        Render2D.startScissor(class_3322, f8, f16, f9, f18);
        if (n == 0) {
            Instance instance2 = Fonts.GOLOS.getFont(11.5f);
            String string3 = "\u041c\u0435\u0442\u043e\u043a \u043f\u043e\u043a\u0430 \u043d\u0435\u0442";
            Render2D.drawFont(class_45872, instance2, string3, f8 + (f9 - instance2.getWidth(string3)) / 2.0f, f16 + 20.0f, this.i(this.MUTED3));
        } else {
            Instance instance3 = Fonts.GOLOS.getFont(12.0f);
            Instance instance4 = Fonts.PLEXMONO.getFont(9.5f);
            for (int i = 0; i < n; ++i) {
                NamedItem namedItem = list.get(i);
                float f22 = f16 + (float)i * (f19 + f20) - this.Vb;
                if (f22 + f19 < f16 - 4.0f || f22 > f17 + 4.0f) continue;
                Render2D.drawRoundedRect(class_45872, f8, f22, f9, f19, 7.0f, this.at(8));
                Render2D.drawBorder(class_45872, f8, f22, f9, f19, 7.0f, 0.8f, new ColorRGBA(this.at(16)));
                ClickGuiIcons.mapPin(class_45872, f8 + 14.0f, f22 + f19 / 2.0f, 15.0f, this.as(220));
                Render2D.drawFont(class_45872, instance3, this.clip(instance3, namedItem.getName(), f9 - 70.0f), f8 + 30.0f, f22 + 9.0f, this.i(this.Uo));
                String string4 = (int)namedItem.ti() + ", " + (int)namedItem.tj() + ", " + (int)namedItem.tk();
                Render2D.drawFont(class_45872, instance4, string4, f8 + 30.0f, f22 + 23.0f, this.i(this.Uq));
                float f23 = 22.0f;
                float f24 = f8 + f9 - f23 - 6.0f;
                float f25 = f22 + (f19 - f23) / 2.0f;
                float f26 = this.b("wpx:" + i + namedItem.getName(), this.hit(f, f2, f24, f25, f23, f23) ? 1.0f : 0.0f);
                Render2D.drawRoundedRect(class_45872, f24, f25, f23, f23, 6.0f, this.mix(this.at(8), this.a(this.Ut, 34), f26));
                ClickGuiIcons.close(class_45872, f24 + f23 / 2.0f, f25 + f23 / 2.0f, 11.0f, this.i(this.mix(this.MUTED, this.Ut, f26)));
                if (!(f22 >= f16 - 1.0f) || !(f22 + f19 <= f17 + 1.0f)) continue;
                NamedItem namedItem2 = namedItem;
                this.a(f24, f25, f23, f23, 0, () -> Celebression.getInstance().getWaypointManager().b(namedItem2));
            }
        }
        Render2D.stopScissor(class_3322);
    }

    private void f(class_332 class_3322, float f, float f2) {
        class_4587 class_45872 = class_3322.method_51448();
        float f3 = 1540.0f;
        float f4 = 90.0f;
        float f5 = 340.0f;
        float f6 = 600.0f;
        ConfigManager configManager = Celebression.getInstance().getConfigManager();
        List<String> list = configManager == null ? List.of() : configManager.dW();
        String string = configManager == null ? "" : configManager.dQ();
        float f7 = this.a(class_45872, f3, f4, f5, f6, ClickGuiIcons::gear, "\u041a\u043e\u043d\u0444\u0438\u0433\u0438", list.size() + " \u0448\u0442.", this.US, f, f2);
        float f8 = f3 + 16.0f;
        float f9 = f5 - 32.0f;
        float f10 = 96.0f;
        float f11 = 32.0f;
        float f12 = f9 - f10 - 8.0f;
        this.a(class_45872, f8, f7, f12, f11, this.UV, "\u0418\u043c\u044f \u043a\u043e\u043d\u0444\u0438\u0433\u0430...", "cfinF");
        boolean bl = configManager != null && !this.UV.getSearchText().trim().isEmpty();
        float f13 = this.b("cfsave", this.hit(f, f2, f8 + f12 + 8.0f, f7, f10, f11) && bl ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f8 + f12 + 8.0f, f7, f10, f11, 7.0f, bl ? this.mix(this.as(210), this.as(255), f13) : this.at(12));
        Instance instance = Fonts.UNBOUNDED.getFont(9.5f);
        String string2 = "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c";
        Render2D.drawFont(class_45872, instance, string2, f8 + f12 + 8.0f + (f10 - instance.getWidth(string2)) / 2.0f, f7 + f11 / 2.0f - instance.getHeight() / 2.0f, bl ? this.i(this.Us) : this.i(this.MUTED3));
        if (bl) {
            this.a(f8 + f12 + 8.0f, f7, f10, f11, 0, this::nn);
        }
        float f14 = 28.0f;
        boolean bl2 = configManager != null && !string.isBlank();
        String string3 = "\u041e\u0431\u043d\u043e\u0432\u0438\u0442\u044c \u0442\u0435\u043a\u0443\u0449\u0438\u0439: " + string;
        Instance instance2 = Fonts.UNBOUNDED.getFont(9.0f);
        float f15 = this.b("cfupd", this.hit(f, f2, f8, f7 += f11 + 8.0f, f9, f14) && bl2 ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f8, f7, f9, f14, 7.0f, this.mix(this.at(10), this.as(40), f15));
        Render2D.drawBorder(class_45872, f8, f7, f9, f14, 7.0f, 0.8f, new ColorRGBA(this.mix(this.at(20), this.as(160), f15)));
        String string4 = this.clip(instance2, string3, f9 - 16.0f);
        Render2D.drawFont(class_45872, instance2, string4, f8 + (f9 - instance2.getWidth(string4)) / 2.0f, f7 + f14 / 2.0f - instance2.getHeight() / 2.0f, this.i(this.mix(this.MUTED, this.Uo, f15)));
        if (bl2) {
            this.a(f8, f7, f9, f14, 0, this::no);
        }
        float f16 = f7 += f14 + 12.0f;
        float f17 = f4 + f6 - 16.0f;
        float f18 = f17 - f16;
        float f19 = 34.0f;
        float f20 = 6.0f;
        int n = list.size();
        float f21 = Math.max(0.0f, (float)n * (f19 + f20) - f18);
        this.Ve = class_3532.method_15363((float)this.Ve, (float)0.0f, (float)f21);
        this.Vd += (this.Ve - this.Vd) * (1.0f - (float)Math.exp(-16.0f * this.Wi));
        Render2D.startScissor(class_3322, f8, f16, f9 + 6.0f, f18);
        if (n == 0) {
            Instance instance3 = Fonts.GOLOS.getFont(11.5f);
            String string5 = "\u041a\u043e\u043d\u0444\u0438\u0433\u043e\u0432 \u043f\u043e\u043a\u0430 \u043d\u0435\u0442";
            Render2D.drawFont(class_45872, instance3, string5, f8 + (f9 - instance3.getWidth(string5)) / 2.0f, f16 + 20.0f, this.i(this.MUTED3));
        } else {
            Instance instance4 = Fonts.GOLOS.getFont(12.0f);
            Instance instance5 = Fonts.GOLOS.getFont(12.0f);
            Instance instance6 = Fonts.GOLOS.getFont(11.0f);
            for (int i = 0; i < n; ++i) {
                float f22;
                float f23;
                String string6;
                String string7 = (String)list.get(i);
                boolean bl3 = string7.equalsIgnoreCase(string);
                float f24 = f16 + (float)i * (f19 + f20) - this.Vd;
                if (f24 + f19 < f16 - 4.0f || f24 > f17 + 4.0f) continue;
                Render2D.drawRoundedRect(class_45872, f8, f24, f9, f19, 7.0f, bl3 ? this.as(26) : this.at(8));
                Render2D.drawBorder(class_45872, f8, f24, f9, f19, 7.0f, 0.8f, new ColorRGBA(bl3 ? this.as(120) : this.at(16)));
                ClickGuiIcons.gear(class_45872, f8 + 14.0f, f24 + f19 / 2.0f, 13.0f, bl3 ? this.as(230) : this.i(this.MUTED));
                float f25 = f24 + f19 / 2.0f;
                float f26 = f8 + f9 - (bl3 ? 62.0f : 34.0f);
                String string8 = this.clip(instance4, string7, f26 - (f8 + 28.0f));
                Render2D.drawFont(class_45872, instance4, string8, f8 + 28.0f, f25 - instance4.getHeight() / 2.0f, this.i(this.Uo));
                String string9 = string6 = configManager == null ? "" : configManager.bj(string7);
                if (string6 != null && !string6.isBlank() && (f23 = f26 - (f22 = f8 + 28.0f + instance4.getWidth(string8) + 12.0f)) > 14.0f) {
                    Render2D.drawFont(class_45872, instance5, this.clip(instance5, "by " + string6, f23), f22, f25 - instance5.getHeight() / 2.0f, this.i(this.MUTED3));
                }
                if (bl3) {
                    String string10 = "\u0430\u043a\u0442\u0438\u0432\u0435\u043d";
                    Render2D.drawFont(class_45872, instance6, string10, f8 + f9 - instance6.getWidth(string10) - 12.0f, f24 + f19 / 2.0f - instance6.getHeight() / 2.0f, this.as(220));
                    continue;
                }
                f22 = 22.0f;
                f23 = f8 + f9 - f22 - 6.0f;
                float f27 = f24 + (f19 - f22) / 2.0f;
                float f28 = this.b("cfx:" + string7, this.hit(f, f2, f23, f27, f22, f22) ? 1.0f : 0.0f);
                Render2D.drawRoundedRect(class_45872, f23, f27, f22, f22, 6.0f, this.mix(this.at(8), this.a(this.Ut, 34), f28));
                ClickGuiIcons.close(class_45872, f23 + f22 / 2.0f, f27 + f22 / 2.0f, 11.0f, this.i(this.mix(this.MUTED, this.Ut, f28)));
                if (!(f24 >= f16 - 1.0f) || !(f24 + f19 <= f17 + 1.0f)) continue;
                String string11 = string7;
                this.a(f8, f24, f9 - f22 - 14.0f, f19, 0, () -> this.cf(string11));
                this.a(f23, f27, f22, f22, 0, () -> this.cg(string11));
            }
        }
        Render2D.stopScissor(class_3322);
    }

    private void nn() {
        ConfigManager configManager = Celebression.getInstance().getConfigManager();
        if (configManager == null) {
            return;
        }
        String string = this.UV.getSearchText().trim();
        if (string.isEmpty()) {
            return;
        }
        try {
            configManager.bh(string);
            configManager.bi(string);
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[LinkGui] Save config failed: " + string, (Throwable)exception);
        }
        this.UV.setSearchText("");
        this.UV.setFocused(false);
    }

    private void cf(String string) {
        ConfigManager configManager = Celebression.getInstance().getConfigManager();
        if (configManager == null) {
            return;
        }
        try {
            configManager.bi(string);
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[LinkGui] Load config failed: " + string, (Throwable)exception);
        }
    }

    private void no() {
        ConfigManager configManager = Celebression.getInstance().getConfigManager();
        if (configManager == null) {
            return;
        }
        try {
            configManager.bh(configManager.dQ());
            HudNotificationQueue.dG().bg("\u041a\u043e\u043d\u0444\u0438\u0433 \u0441\u043e\u0445\u0440\u0430\u043d\u0451\u043d!");
        }
        catch (Exception exception) {
            Celebression.LOGGER.error("[LinkGui] Update current config failed", (Throwable)exception);
        }
    }

    private void cg(String string) {
        ConfigManager configManager = Celebression.getInstance().getConfigManager();
        if (configManager != null) {
            configManager.bk(string);
        }
    }

    private void n(class_4587 class_45872, float f, float f2, float f3, float f4, float f5) {
        Render2D.drawRoundedVerticalGradient(class_45872, f, f2, f3, f4, f5, this.nz(), this.nA());
        Render2D.drawBorder(class_45872, f, f2, f3, f4, f5, 0.9f, new ColorRGBA(this.at(23)));
        Render2D.drawRoundedRect(class_45872, f + f5, f2 + 1.0f, f3 - f5 * 2.0f, 1.0f, 0.5f, this.at(13));
    }

    private void l(class_4587 class_45872, float f, float f2, float f3, float f4) {
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, 10.0f, this.at(8));
        Render2D.drawBorder(class_45872, f, f2, f3, f4, 10.0f, 0.8f, new ColorRGBA(this.at(16)));
    }

    private void a(class_4587 class_45872, float f, float f2, boolean bl) {
        Color color = this.as(238);
        float f3 = 20.0f;
        float f4 = 2.0f;
        if (bl) {
            this.a(class_45872, f, f2, f3, f4, 1.0f, 6.0f, 80, 1.0f);
            this.a(class_45872, f, f2, f4, f3, 1.0f, 6.0f, 80, 1.0f);
            Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f4 / 2.0f, color);
            Render2D.drawRoundedRect(class_45872, f, f2, f4, f3, f4 / 2.0f, color);
        } else {
            this.a(class_45872, f - f3, f2 - f4, f3, f4, 1.0f, 6.0f, 80, 1.0f);
            this.a(class_45872, f - f4, f2 - f3, f4, f3, 1.0f, 6.0f, 80, 1.0f);
            Render2D.drawRoundedRect(class_45872, f - f3, f2 - f4, f3, f4, f4 / 2.0f, color);
            Render2D.drawRoundedRect(class_45872, f - f4, f2 - f3, f4, f3, f4 / 2.0f, color);
        }
    }

    private void b(class_4587 class_45872, float f, float f2, String string, boolean bl) {
        float f3 = 36.0f;
        float f4 = 20.0f;
        float f5 = this.b(string, bl ? 1.0f : 0.0f);
        this.a(class_45872, f, f2, f3, f4, f4 / 2.0f, 7.0f, 140, f5);
        Color color = this.mix(this.at(36), this.as(255), f5);
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f4, f4 / 2.0f, color);
        float f6 = 14.0f;
        float f7 = f + 3.0f + (f3 - 6.0f - f6) * f5;
        Color color2 = this.mix(this.a(139, 150, 143, 255), this.a(this.Us, 255), f5);
        Render2D.drawRoundedRect(class_45872, f7, f2 + 3.0f, f6, f6, f6 / 2.0f, color2);
    }

    private void a(class_4587 class_45872, float f, float f2, String string, String string2, float f3, float f4) {
        Instance instance = Fonts.UNBOUNDED.getFont(9.5f);
        float f5 = instance.getWidth(string2) + 20.0f;
        float f6 = 26.0f;
        float f7 = f - f5;
        float f8 = f2 + 8.0f;
        float f9 = this.b("chip:" + string, this.hit(f3, f4, f7, f8 - f6 / 2.0f, f5, f6) ? 1.0f : 0.0f);
        Render2D.drawRoundedRect(class_45872, f7, f8 - f6 / 2.0f, f5, f6, 6.0f, this.at(10));
        Render2D.drawBorder(class_45872, f7, f8 - f6 / 2.0f, f5, f6, 6.0f, 0.8f, new ColorRGBA(this.at(Math.round(26.0f + 34.0f * f9))));
        this.a(class_45872, instance, "chipv:" + string, string2, f7 + 10.0f, f8 - f6 / 2.0f + f6 / 2.0f - instance.getHeight() / 2.0f, this.i(this.Uo));
    }

    private float ch(String string) {
        return Fonts.UNBOUNDED.getFont(9.5f).getWidth(string) + 20.0f;
    }

    private void a(class_4587 class_45872, float f2, float f3, float f4, SliderSetting sliderSetting, float f5, float f6) {
        float f7;
        String string = "sld:" + sliderSetting.getName();
        String string2 = this.a(string, sliderSetting);
        Instance instance = Fonts.UNBOUNDED.getFont(9.5f);
        float f8 = instance.getWidth(string2) + 12.0f;
        Render2D.drawRoundedRect(class_45872, f2 + f4 - f8, f3, f8, 18.0f, 4.0f, this.as(20));
        Render2D.drawBorder(class_45872, f2 + f4 - f8, f3, f8, 18.0f, 4.0f, 0.7f, new ColorRGBA(this.as(70)));
        Render2D.drawFont(class_45872, instance, string2, f2 + f4 - f8 + 6.0f, f3 + 9.0f - instance.getHeight() / 2.0f, this.as(255));
        float f9 = 4.0f;
        float f10 = f3 + 24.5f - f9 / 2.0f;
        float f11 = f4;
        float f12 = 8.0f;
        Render2D.drawRoundedRect(class_45872, f2, f10, f11, f9, f9 / 2.0f, this.at(41));
        float f13 = this.a(sliderSetting);
        boolean bl = string.equals(this.Vm);
        if (bl) {
            f7 = this.Vq;
            this.Wd.put(string, Float.valueOf(f7));
        } else {
            f7 = this.b(string, f13);
        }
        float f14 = f2 + f12 + (f11 - 2.0f * f12) * f7;
        Render2D.drawRoundedRect(class_45872, f2, f10, f14 - f2, f9, f9 / 2.0f, this.as(255));
        this.c(class_45872, f14, f10 + f9 / 2.0f, string, bl);
        this.a(f2, f10 - 9.0f, f11, 20.0f, f2 + f12, f11 - 2.0f * f12, string, (Float f) -> this.a(sliderSetting, f.floatValue()));
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, String string, String string2, float f4, float f5, float f6, Consumer<Float> consumer, float f7, float f8) {
        float f9;
        Render2D.drawFont(class_45872, Fonts.UNBOUNDED_L.getFont(10.0f), string, f, f2, this.i(this.TEXT3));
        Instance instance = Fonts.UNBOUNDED.getFont(9.5f);
        float f10 = instance.getWidth(string2) + 12.0f;
        Render2D.drawRoundedRect(class_45872, f + f3 - f10, f2 - 2.0f, f10, 18.0f, 4.0f, this.as(20));
        Render2D.drawBorder(class_45872, f + f3 - f10, f2 - 2.0f, f10, 18.0f, 4.0f, 0.7f, new ColorRGBA(this.as(70)));
        Render2D.drawFont(class_45872, instance, string2, f + f3 - f10 + 6.0f, f2 - 2.0f + 9.0f - instance.getHeight() / 2.0f, this.as(255));
        float f11 = 4.0f;
        float f12 = f2 + 23.5f - f11 / 2.0f;
        float f13 = 8.0f;
        Render2D.drawRoundedRect(class_45872, f, f12, f3, f11, f11 / 2.0f, this.at(41));
        String string3 = "sldp:" + string;
        float f14 = class_3532.method_15363((float)f4, (float)0.0f, (float)1.0f);
        boolean bl = string3.equals(this.Vm);
        if (bl) {
            f9 = this.Vq;
            this.Wd.put(string3, Float.valueOf(f9));
        } else {
            f9 = this.b(string3, f14);
        }
        float f15 = f + f13 + (f3 - 2.0f * f13) * f9;
        Render2D.drawRoundedRect(class_45872, f, f12, f15 - f, f11, f11 / 2.0f, this.as(255));
        this.c(class_45872, f15, f12 + f11 / 2.0f, string3, bl);
        this.a(f, f12 - 9.0f, f3, 20.0f, f + f13, f3 - 2.0f * f13, string3, consumer);
    }

    private void c(class_4587 class_45872, float f, float f2, String string, boolean bl) {
        float f3 = this.b("sldgrab:" + string, bl ? 1.0f : 0.0f);
        float f4 = 5.0f + 1.7f * f3;
        if (f3 > 0.01f) {
            float f5 = f4 + 2.4f;
            Render2D.drawRoundedRect(class_45872, f - f5, f2 - f5, f5 * 2.0f, f5 * 2.0f, f5, this.as(Math.round(210.0f * f3)));
        }
        Render2D.drawRoundedRect(class_45872, f - f4, f2 - f4, f4 * 2.0f, f4 * 2.0f, f4, this.i(this.TEXT));
        Render2D.drawBorder(class_45872, f - f4, f2 - f4, f4 * 2.0f, f4 * 2.0f, f4, 0.9f, new ColorRGBA(this.as(Math.round(110.0f + 120.0f * f3))));
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, String string, float f5) {
        float f6;
        float f7 = class_3532.method_15363((float)f5, (float)0.0f, (float)1.0f);
        Color color = this.mix(this.at(46), this.as(128), f7);
        float f8 = 4.0f;
        float f9 = 3.0f;
        for (f6 = f; f6 < f + f3; f6 += f8 + f9) {
            Render2D.drawRoundedRect(class_45872, f6, f2, Math.min(f8, f + f3 - f6), 1.0f, 0.5f, color);
            Render2D.drawRoundedRect(class_45872, f6, f2 + f4 - 1.0f, Math.min(f8, f + f3 - f6), 1.0f, 0.5f, color);
        }
        for (f6 = f2; f6 < f2 + f4; f6 += f8 + f9) {
            Render2D.drawRoundedRect(class_45872, f, f6, 1.0f, Math.min(f8, f2 + f4 - f6), 0.5f, color);
            Render2D.drawRoundedRect(class_45872, f + f3 - 1.0f, f6, 1.0f, Math.min(f8, f2 + f4 - f6), 0.5f, color);
        }
        Color color2 = this.mix(this.i(this.Uo), this.as(255), f7);
        GuiIcons.draw(class_45872, GuiIcons.KEYBOARD, f + f3 / 2.0f - Fonts.UNBOUNDED.getFont(9.5f).getWidth(string) / 2.0f - 9.0f, f2 + f4 / 2.0f, 13.0f, color2);
        Instance instance = Fonts.UNBOUNDED.getFont(9.5f);
        this.drawSpaced(class_45872, instance, string, f + f3 / 2.0f - instance.getWidth(string) / 2.0f + 4.0f, f2 + f4 / 2.0f - instance.getHeight() / 2.0f, color2, 1.71f);
    }

    public boolean method_25402(double d, double d2, int n) {
        int n2;
        if (this.close || LinkGuiScreen.aiG.field_1755 != this) {
            return false;
        }
        float[] fArray = this.h(d, d2);
        float f = fArray[0];
        float f2 = fArray[1];
        if (this.Vf != Page.WC) {
            if (n != 0) {
                this.ar(n);
            } else {
                this.nq();
            }
            return true;
        }
        List<ModuleSettingsPanel> list = ClickRippleManager.nW();
        for (n2 = list.size() - 1; n2 >= 0; --n2) {
            ModuleSettingsPanel object3 = list.get(n2);
            if (!object3.mouseClicked(f, f2, n)) continue;
            this.UB.setFocused(false);
            if (list.contains(object3)) {
                list.remove(object3);
                list.add(object3);
            }
            return true;
        }
        if (this.Vi != null) {
            for (n2 = this.Wc.size() - 1; n2 >= 0; --n2) {
                SearchResult searchResult = this.Wc.get(n2);
                if (!searchResult.WP || searchResult.button != n || !this.hit(f, f2, searchResult.x, searchResult.y, searchResult.Pn, searchResult.Po)) continue;
                searchResult.mo.run();
                return true;
            }
            this.Vi = null;
            return true;
        }
        if (this.Vs != null && n == 0) {
            if (this.hit(f, f2, this.VR, this.VS, this.VT, this.VT)) {
                this.Vs = null;
                this.Vy = 0;
                return true;
            }
            if (this.hit(f, f2, this.VB, this.VC, this.VD, this.VE)) {
                this.Vy = 1;
                this.q(f, f2);
                return true;
            }
            if (this.hit(f, f2, this.VF, this.VG, this.VH, this.VI)) {
                this.Vy = 2;
                this.q(f, f2);
                return true;
            }
            if (this.hit(f, f2, this.VJ, this.VK, this.VL, this.VM)) {
                this.Vy = 3;
                this.q(f, f2);
                return true;
            }
            if (this.hit(f, f2, this.VN, this.VO, this.VP, this.VQ)) {
                return true;
            }
            this.Vs = null;
            this.Vy = 0;
            return true;
        }
        if (this.VU != null && n == 0) {
            EnumSetting<?> enumSetting = this.VU;
            if (this.hit(f, f2, this.VW, this.VX, this.VY, this.VZ)) {
                this.VU = null;
                return true;
            }
            Enum<?>[] EArray = (Enum<?>[])((Enum<?>)enumSetting.jT()).getDeclaringClass().getEnumConstants();
            if (EArray != null) {
                float f3 = this.Wa + 4.0f;
                for (Object e : EArray) {
                    if (this.hit(f, f2, this.VW + 3.0f, f3, this.VY - 6.0f, this.Wb)) {
                        if (e instanceof NamedOption) {
                            NamedOption namedOption = (NamedOption)e;
                            enumSetting.bS(namedOption.getName());
                        }
                        this.VU = null;
                        return true;
                    }
                    f3 += this.Wb;
                }
            }
            this.VU = null;
            return true;
        }
        if (this.UO > 0.5f && this.UL.mouseClicked(f, f2, n)) {
            return true;
        }
        for (n2 = this.Wc.size() - 1; n2 >= 0; --n2) {
            SearchResult searchResult = this.Wc.get(n2);
            if (searchResult.button != n || !this.hit(f, f2, searchResult.x, searchResult.y, searchResult.Pn, searchResult.Po)) continue;
            if (searchResult.WM != null) {
                this.Vl = searchResult.WM;
                this.Vm = searchResult.WQ;
                this.Vn = searchResult.WN;
                this.Vo = searchResult.WO;
                this.Vq = class_3532.method_15363((float)((f - this.Vn) / this.Vo), (float)0.0f, (float)1.0f);
                this.Vl.set(this.Vq);
            } else if (searchResult.mo != null) {
                searchResult.mo.run();
            }
            return true;
        }
        this.UB.setFocused(false);
        for (SearchBar searchBar : this.ni()) {
            searchBar.setFocused(false);
        }
        this.Vp = null;
        return false;
    }

    public boolean method_25406(double d, double d2, int n) {
        float[] fArray = this.h(d, d2);
        for (ModuleSettingsPanel moduleSettingsPanel : ClickRippleManager.nW()) {
            moduleSettingsPanel.mouseReleased(fArray[0], fArray[1], n);
        }
        if (n == 0) {
            this.Vl = null;
            this.Vm = null;
            this.Vy = 0;
            this.UL.mouseReleased(fArray[0], fArray[1], n);
        }
        return false;
    }

    public boolean method_25403(double d, double d2, int n, double d3, double d4) {
        float[] fArray = this.h(d, d2);
        for (ModuleSettingsPanel moduleSettingsPanel : ClickRippleManager.nW()) {
            moduleSettingsPanel.a(fArray[0], fArray[1], n);
        }
        if (this.Vl != null && n == 0) {
            this.Vq = class_3532.method_15363((float)((fArray[0] - this.Vn) / this.Vo), (float)0.0f, (float)1.0f);
            this.Vl.set(this.Vq);
            return true;
        }
        this.UL.mouseDragged(fArray[0], fArray[1]);
        return false;
    }

    public boolean method_25401(double d, double d2, double d3, double d4) {
        if (this.close || LinkGuiScreen.aiG.field_1755 != this) {
            return false;
        }
        float[] fArray = this.h(d, d2);
        for (ModuleSettingsPanel moduleSettingsPanel : ClickRippleManager.nW()) {
            moduleSettingsPanel.mouseScrolled(fArray[0], fArray[1], d3, d4);
        }
        if (this.hit(fArray[0], fArray[1], this.UK[0], this.UK[1], this.UK[2], this.UK[3])) {
            this.UE -= (float)d4 * 45.0f;
        }
        if (fArray[0] > 1540.0f) {
            if (this.UQ.rn()) {
                this.Va -= (float)d4 * 40.0f;
            } else if (this.UR.rn()) {
                this.Vc -= (float)d4 * 40.0f;
            } else if (this.US.rn()) {
                this.Ve -= (float)d4 * 40.0f;
            }
        }
        return true;
    }

    public boolean method_25404(int n, int n2, int n3) {
        if (this.close || LinkGuiScreen.aiG.field_1755 != this) {
            return false;
        }
        if (this.Vf != Page.WC) {
            if (n == 256 || n == 261) {
                this.nr();
            } else {
                this.aq(n);
            }
            return true;
        }
        if (this.np()) {
            for (ModuleSettingsPanel moduleSettingsPanel : ClickRippleManager.nW()) {
                moduleSettingsPanel.keyPressed(n, n2, n3);
            }
            return true;
        }
        if (this.Vp != null) {
            if (n == 256 || n == 257 || n == 335) {
                this.Vp = null;
                return true;
            }
            if (n == 259) {
                String string = this.Vp.kg();
                if (!string.isEmpty()) {
                    this.Vp.bI(string.substring(0, string.length() - 1));
                }
                return true;
            }
            if (n == 86 && class_437.method_25441()) {
                String string = GLFW.glfwGetClipboardString((long)aiG.method_22683().method_4490());
                if (string != null) {
                    this.Vp.bI(this.Vp.kg() + string);
                }
                return true;
            }
            return true;
        }
        if (n == 70 && class_437.method_25441()) {
            this.UB.setFocused(true);
            return true;
        }
        for (SearchBar minecraftClientAccess : this.ni()) {
            if (!minecraftClientAccess.isFocused()) continue;
            if (n == 256) {
                minecraftClientAccess.setFocused(false);
                return true;
            }
            if (n == 257 || n == 335) {
                if (minecraftClientAccess == this.UT) {
                    this.nl();
                } else {
                    this.nk();
                }
                return true;
            }
            minecraftClientAccess.keyPressed(n, n2, n3);
            return true;
        }
        if (this.UB.isFocused()) {
            if (n == 256) {
                this.UB.setFocused(false);
                return true;
            }
            this.Wj = System.currentTimeMillis();
            if (this.UB.keyPressed(n, n2, n3)) {
                return true;
            }
            return true;
        }
        for (ModuleSettingsPanel moduleSettingsPanel : ClickRippleManager.nW()) {
            moduleSettingsPanel.keyPressed(n, n2, n3);
        }
        if (n == 256) {
            this.method_25419();
            return true;
        }
        return super.method_25404(n, n2, n3);
    }

    public boolean method_25400(char c, int n) {
        if (this.close || LinkGuiScreen.aiG.field_1755 != this) {
            return false;
        }
        if (this.np()) {
            return true;
        }
        if (this.Vp != null) {
            if (!Character.isISOControl(c)) {
                this.Vp.bI(this.Vp.kg() + c);
            }
            return true;
        }
        for (SearchBar minecraftClientAccess : this.ni()) {
            boolean bl;
            if (!minecraftClientAccess.isFocused()) continue;
            boolean bl2 = bl = minecraftClientAccess == this.UW || minecraftClientAccess == this.UX || minecraftClientAccess == this.UY;
            if (bl && !Character.isDigit(c) && c != '-') {
                return true;
            }
            minecraftClientAccess.charTyped(c, n);
            return true;
        }
        if (this.UB.isFocused()) {
            this.Wj = System.currentTimeMillis();
            this.UB.charTyped(c, n);
            return true;
        }
        for (ModuleSettingsPanel moduleSettingsPanel : ClickRippleManager.nW()) {
            moduleSettingsPanel.charTyped(c, n);
        }
        return super.method_25400(c, n);
    }

    private boolean np() {
        for (ModuleSettingsPanel moduleSettingsPanel : ClickRippleManager.nW()) {
            if (!moduleSettingsPanel.hasActiveBinding()) continue;
            return true;
        }
        return false;
    }

    private void a(Page page, Module module, BindSetting bindSetting) {
        this.Vf = page;
        this.Vg = module;
        this.Vh = bindSetting;
        this.Vi = null;
    }

    private void nq() {
        this.Vf = Page.WC;
        this.Vg = null;
        this.Vh = null;
    }

    private void aq(int n) {
        switch (this.Vf.ordinal()) {
            case 1: {
                if (this.Vg == null) break;
                this.Vg.setBind(new KeyBind(n, false));
                break;
            }
            case 2: {
                if (this.Vh == null) break;
                ((KeyBind)this.Vh.jT()).ah(n);
                ((KeyBind)this.Vh.jT()).u(false);
                break;
            }
            case 3: {
                this.b(new KeyBind(n, false));
                break;
            }
        }
        this.nq();
    }

    private void ar(int n) {
        switch (this.Vf.ordinal()) {
            case 1: {
                if (this.Vg == null) break;
                this.Vg.setBind(new KeyBind(n, true));
                break;
            }
            case 2: {
                if (this.Vh == null) break;
                ((KeyBind)this.Vh.jT()).ah(n);
                ((KeyBind)this.Vh.jT()).u(true);
                break;
            }
            case 3: {
                this.b(new KeyBind(n, true));
                break;
            }
        }
        this.nq();
    }

    private void nr() {
        switch (this.Vf.ordinal()) {
            case 1: {
                if (this.Vg == null) break;
                this.Vg.setBind(new KeyBind(-1, false));
                break;
            }
            case 2: {
                if (this.Vh == null) break;
                ((KeyBind)this.Vh.jT()).ah(-1);
                ((KeyBind)this.Vh.jT()).u(false);
                break;
            }
            case 3: {
                this.b(new KeyBind(344, false));
                break;
            }
        }
        this.nq();
    }

    private void b(KeyBind keyBind) {
        UI uI = Celebression.getInstance().getModuleManager().getModule(UI.class);
        if (uI == null) {
            return;
        }
        uI.setBind(keyBind);
        Celebression celebression = Celebression.getInstance();
        if (celebression.getConfigManager() != null) {
            celebression.getConfigManager().dU();
        }
    }

    private void b(ModuleCategory moduleCategory) {
        this.Tm = moduleCategory;
        this.UC = false;
        this.UE = 0.0f;
    }

    private void f(Module module) {
        if (module.isActionModule()) {
            module.activate();
        } else {
            module.toggle();
        }
    }

    private List<Module> ns() {
        String string = this.UB.getSearchText().trim().toLowerCase(Locale.ROOT);
        ArrayList<Module> arrayList = new ArrayList<Module>();
        List<Module> list = string.isEmpty() && !this.UC ? Celebression.getInstance().getModuleManager().getModules(this.Tm) : Celebression.getInstance().getModuleManager().getModules();
        for (Module module : list) {
            if (module.getCategory() == ModuleCategory.qo || module instanceof UI || module instanceof ElementsModule || !string.isEmpty() && !module.getName().toLowerCase(Locale.ROOT).contains(string) && !module.getDisplayName().toLowerCase(Locale.ROOT).contains(string)) continue;
            arrayList.add(module);
        }
        return arrayList;
    }

    private Module ci(String string) {
        for (Module module : Celebression.getInstance().getModuleManager().getModules()) {
            if (!module.getName().equals(string)) continue;
            return module;
        }
        return null;
    }

    private void a(class_4587 class_45872, ModuleCategory moduleCategory, float f, float f2, float f3, Color color) {
        class_2960 class_29602 = GuiIcons.forCategory(moduleCategory);
        if (class_29602 != null) {
            GuiIcons.draw(class_45872, class_29602, f, f2, f3, this.i(color));
            return;
        }
        Instance instance = Fonts.ICONSNEW.getFont(f3);
        Render2D.drawFont(class_45872, instance, moduleCategory.fK(), f - instance.getWidth(moduleCategory.fK()) / 2.0f, f2 - instance.getHeight() / 2.0f, this.i(color));
    }

    private void m(class_4587 class_45872, float f, float f2, float f3, float f4) {
        if (LinkGuiScreen.aiG.field_1724 == null) {
            return;
        }
        class_2960 class_29602 = GuiSkinTextures.resolveTexture((class_1657)LinkGuiScreen.aiG.field_1724);
        if (class_29602 == null) {
            return;
        }
        Color color = this.i(Color.WHITE);
        Render2D.drawTexture(class_45872, f, f2, f3, f3, f4, 0.125f, 0.125f, 0.125f, 0.125f, class_29602, color);
        Render2D.drawTexture(class_45872, f, f2, f3, f3, f4, 0.625f, 0.125f, 0.125f, 0.125f, class_29602, color);
        Render2D.drawBorder(class_45872, f, f2, f3, f3, f4, 0.8f, new ColorRGBA(this.at(30)));
    }

    private String g(Module module) {
        String string = module.getDescription();
        String string2 = class_1074.method_4662((String)string, (Object[])new Object[0]);
        return string2.equals(string) ? "" : string2;
    }

    private String c(KeyBind keyBind) {
        return keyBind == null || keyBind.isEmpty() ? "None" : KeyBindParser.d(keyBind).replace('_', ' ');
    }

    private String nick() {
        if (LinkGuiScreen.aiG.field_1724 != null) {
            return LinkGuiScreen.aiG.field_1724.method_7334().getName();
        }
        return aiG.method_1548() != null ? aiG.method_1548().method_1676() : "player";
    }

    private String nt() {
        if (LinkGuiScreen.aiG.field_1724 == null) {
            return "00000";
        }
        return String.valueOf(Math.abs(LinkGuiScreen.aiG.field_1724.method_5667().hashCode() % 100000));
    }

    private String nu() {
        return LocalDate.now().plusYears(1L).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private SliderSetting a(UI uI, String string) {
        SliderSetting sliderSetting;
        Setting<?> setting = this.e(uI, string);
        return setting instanceof SliderSetting ? (sliderSetting = (SliderSetting)setting) : null;
    }

    private BooleanSetting b(UI uI, String string) {
        BooleanSetting booleanSetting;
        Setting<?> setting = this.e(uI, string);
        return setting instanceof BooleanSetting ? (booleanSetting = (BooleanSetting)setting) : null;
    }

    private EnumSetting<?> c(UI uI, String string) {
        EnumSetting enumSetting;
        Setting<?> setting = this.e(uI, string);
        return setting instanceof EnumSetting ? (enumSetting = (EnumSetting)setting) : null;
    }

    private ColorSetting d(UI uI, String string) {
        ColorSetting colorSetting;
        Setting<?> setting = this.e(uI, string);
        return setting instanceof ColorSetting ? (colorSetting = (ColorSetting)setting) : null;
    }

    private Setting<?> e(UI uI, String string) {
        for (Setting<?> setting : uI.getSettings()) {
            if (!setting.getName().equals(string)) continue;
            return setting;
        }
        return null;
    }

    private float a(SliderSetting sliderSetting) {
        float f = sliderSetting.kc() - sliderSetting.kb();
        return f <= 0.0f ? 0.0f : class_3532.method_15363((float)((((Float)sliderSetting.jT()).floatValue() - sliderSetting.kb()) / f), (float)0.0f, (float)1.0f);
    }

    private void a(SliderSetting sliderSetting, float f) {
        float f2 = sliderSetting.kb() + f * (sliderSetting.kc() - sliderSetting.kb());
        float f3 = sliderSetting.kd();
        if (f3 > 0.0f) {
            f2 = (float)Math.round(f2 / f3) * f3;
        }
        sliderSetting.a(Float.valueOf(class_3532.method_15363((float)f2, (float)sliderSetting.kb(), (float)sliderSetting.kc())));
    }

    private String b(SliderSetting sliderSetting) {
        return this.a(((Float)sliderSetting.jT()).floatValue(), sliderSetting);
    }

    private String a(float f, SliderSetting sliderSetting) {
        return sliderSetting.kd() >= 1.0f ? String.valueOf(Math.round(f)) : String.format(Locale.ROOT, "%.2f", Float.valueOf(f));
    }

    private String a(String string, SliderSetting sliderSetting) {
        float f = ((Float)sliderSetting.jT()).floatValue();
        float f2 = this.WA.getOrDefault(string, Float.valueOf(f)).floatValue();
        if (Math.abs(f - (f2 += (f - f2) * (1.0f - (float)Math.exp(-14.0f * this.Wi)))) < Math.max(0.001f, sliderSetting.kd() * 0.25f)) {
            f2 = f;
        }
        this.WA.put(string, Float.valueOf(f2));
        return this.a(f2, sliderSetting);
    }

    private String aj(float f) {
        return Math.round(f * 100.0f) + "%";
    }

    private String ak(float f) {
        return Math.round(f) + "%";
    }

    private float b(String string, float f) {
        float f2 = this.Wd.getOrDefault(string, Float.valueOf(0.0f)).floatValue();
        if (Math.abs(f - (f2 += (f - f2) * (1.0f - (float)Math.exp(-20.0f * this.Wi)))) < 0.001f) {
            f2 = f;
        }
        this.Wd.put(string, Float.valueOf(f2));
        return class_3532.method_15363((float)f2, (float)0.0f, (float)1.0f);
    }

    private Color[] cj(String string) {
        Color[] colorArray;
        switch (string) {
            case "Midnight": {
                Color[] colorArray2 = new Color[2];
                colorArray2[0] = new Color(19, 37, 29);
                colorArray = colorArray2;
                colorArray2[1] = new Color(11, 23, 18);
                break;
            }
            case "Graphite": {
                Color[] colorArray3 = new Color[2];
                colorArray3[0] = new Color(30, 34, 37);
                colorArray = colorArray3;
                colorArray3[1] = new Color(20, 23, 25);
                break;
            }
            case "Amethyst": {
                Color[] colorArray4 = new Color[2];
                colorArray4[0] = new Color(32, 24, 48);
                colorArray = colorArray4;
                colorArray4[1] = new Color(20, 15, 33);
                break;
            }
            case "Ocean": {
                Color[] colorArray5 = new Color[2];
                colorArray5[0] = new Color(16, 32, 46);
                colorArray = colorArray5;
                colorArray5[1] = new Color(10, 21, 32);
                break;
            }
            case "Rose": {
                Color[] colorArray6 = new Color[2];
                colorArray6[0] = new Color(42, 21, 29);
                colorArray = colorArray6;
                colorArray6[1] = new Color(27, 13, 19);
                break;
            }
            case "Emerald": {
                Color[] colorArray7 = new Color[2];
                colorArray7[0] = new Color(14, 36, 23);
                colorArray = colorArray7;
                colorArray7[1] = new Color(8, 26, 16);
                break;
            }
            case "Light": {
                Color[] colorArray8 = new Color[2];
                colorArray8[0] = new Color(238, 241, 245);
                colorArray = colorArray8;
                colorArray8[1] = new Color(216, 221, 230);
                break;
            }
            case "Purple": {
                Color[] colorArray9 = new Color[2];
                colorArray9[0] = new Color(231, 222, 250);
                colorArray = colorArray9;
                colorArray9[1] = new Color(207, 194, 240);
                break;
            }
            default: {
                Color[] colorArray10 = new Color[2];
                colorArray10[0] = new Color(30, 34, 37);
                colorArray = colorArray10;
                colorArray10[1] = new Color(20, 23, 25);
            }
        }
        return colorArray;
    }

    private boolean ck(String string) {
        return string.equals("Light") || string.equals("Purple");
    }

    private void drawSpaced(class_4587 class_45872, Instance instance, String string, float f, float f2, Color color, float f3) {
        float f4 = f;
        for (int i = 0; i < string.length(); ++i) {
            String string2 = String.valueOf(string.charAt(i));
            Render2D.drawFont(class_45872, instance, string2, f4, f2, color);
            f4 += instance.getWidth(string2) + f3;
        }
    }

    private float widthSpaced(Instance instance, String string, float f) {
        float f2 = 0.0f;
        for (int i = 0; i < string.length(); ++i) {
            f2 += instance.getWidth(String.valueOf(string.charAt(i))) + f;
        }
        return Math.max(0.0f, f2 - f);
    }

    private String clip(Instance instance, String string, float f) {
        if (string == null) {
            return "";
        }
        if (instance.getWidth(string) <= f) {
            return string;
        }
        while (string.length() > 1 && instance.getWidth(string + "...") > f) {
            string = string.substring(0, string.length() - 1);
        }
        return string + "...";
    }

    private List<String> a(Instance instance, String string, float f, int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String[] stringArray = string.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : stringArray) {
            String string3;
            String string4 = string3 = stringBuilder.length() == 0 ? string2 : String.valueOf(stringBuilder) + " " + string2;
            if (instance.getWidth(string3) > f && stringBuilder.length() > 0) {
                arrayList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder(string2);
                if (arrayList.size() != n - 1) continue;
                break;
            }
            stringBuilder = new StringBuilder(string3);
        }
        String string5 = stringBuilder.toString();
        if (arrayList.size() < n) {
            arrayList.add(this.clip(instance, string5, f));
        }
        return arrayList;
    }

    private Color a(int n, int n2, int n3, int n4) {
        return new Color(n, n2, n3, Math.max(0, Math.min(255, Math.round((float)n4 * this.fade))));
    }

    private Color i(Color color) {
        return this.a(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    private Color a(Color color, int n) {
        return this.a(color.getRed(), color.getGreen(), color.getBlue(), n);
    }

    private Color as(int n) {
        return this.a(this.Wo.getRed(), this.Wo.getGreen(), this.Wo.getBlue(), n);
    }

    private void nv() {
        ClientTheme clientTheme = UiThemeController.oX();
        Color color = clientTheme != null ? clientTheme.pf() : new Color(15, 18, 23);
        float f = (0.299f * (float)color.getRed() + 0.587f * (float)color.getGreen() + 0.114f * (float)color.getBlue()) / 255.0f;
        if (this.Wq < 0.0f) {
            this.Wq = f;
        }
        this.Wq += (f - this.Wq) * (1.0f - (float)Math.exp(-12.0f * this.Wi));
        boolean bl = this.Uu = this.Wq > 0.6f;
        if (this.Uu) {
            this.TEXT = new Color(26, 31, 38);
            this.Uo = new Color(44, 50, 59);
            this.TEXT3 = new Color(64, 71, 82);
            this.Up = new Color(92, 100, 112);
            this.MUTED = new Color(120, 128, 140);
            this.Uq = new Color(142, 150, 161);
            this.MUTED3 = new Color(166, 173, 183);
            this.Ur = new Color(188, 194, 203);
            this.Us = new Color(18, 22, 28);
        } else {
            this.TEXT = new Color(238, 242, 239);
            this.Uo = new Color(223, 229, 225);
            this.TEXT3 = new Color(195, 204, 199);
            this.Up = new Color(168, 177, 172);
            this.MUTED = new Color(139, 150, 143);
            this.Uq = new Color(120, 131, 125);
            this.MUTED3 = new Color(93, 104, 98);
            this.Ur = new Color(76, 86, 81);
            this.Us = new Color(11, 18, 14);
        }
    }

    private Color at(int n) {
        return this.Uu ? this.a(18, 22, 28, n) : this.a(255, 255, 255, n);
    }

    private Color nw() {
        return this.Wr != null ? this.Wr : new Color(15, 18, 23);
    }

    private Color a(Color color, float f, int n) {
        Color color2 = this.mix(this.nw(), color, f);
        return this.a(color2.getRed(), color2.getGreen(), color2.getBlue(), n);
    }

    private Color b(float f, int n) {
        return this.a(Color.WHITE, f, n);
    }

    private Color c(float f, int n) {
        return this.a(Color.BLACK, f, n);
    }

    private Color nx() {
        return this.Uu ? this.b(0.55f, 248) : this.b(0.03f, 248);
    }

    private Color ny() {
        return this.Uu ? this.b(0.03f, 248) : this.c(0.6f, 248);
    }

    private Color mA() {
        return this.Uu ? this.b(0.5f, 248) : this.c(0.5f, 248);
    }

    private Color nz() {
        return this.Uu ? this.b(0.85f, 245) : this.b(0.05f, 245);
    }

    private Color nA() {
        return this.Uu ? this.b(0.3f, 245) : this.c(0.06f, 245);
    }

    private Color nB() {
        return this.Uu ? this.b(0.85f, 205) : this.b(0.05f, 178);
    }

    private Color nC() {
        return this.Uu ? this.b(0.25f, 205) : this.c(0.06f, 178);
    }

    private Color nD() {
        return this.Uu ? this.mix(this.b(0.85f, 225), this.as(225), 0.14f) : this.a(this.Wo, 0.16f, 204);
    }

    private Color nE() {
        return this.Uu ? this.mix(this.b(0.55f, 225), this.as(225), 0.1f) : this.a(this.Wo, 0.09f, 204);
    }

    private Color nF() {
        return this.Uu ? this.b(0.85f, 205) : this.b(0.05f, 179);
    }

    private Color nG() {
        return this.Uu ? this.b(0.25f, 205) : this.c(0.06f, 179);
    }

    private Color nH() {
        return this.Uu ? this.c(0.04f, 210) : this.c(0.68f, 140);
    }

    private Color mix(Color color, Color color2, float f) {
        f = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        return new Color(Math.round((float)color.getRed() + (float)(color2.getRed() - color.getRed()) * f), Math.round((float)color.getGreen() + (float)(color2.getGreen() - color.getGreen()) * f), Math.round((float)color.getBlue() + (float)(color2.getBlue() - color.getBlue()) * f), Math.round((float)color.getAlpha() + (float)(color2.getAlpha() - color.getAlpha()) * f));
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, float f5, float f6, int n, float f7) {
        int n2 = Math.round((float)n * class_3532.method_15363((float)f7, (float)0.0f, (float)1.0f));
        if (n2 <= 1) {
            return;
        }
        Render2D.drawBorder(class_45872, f - f6, f2 - f6, f3 + 2.0f * f6, f4 + 2.0f * f6, f5 + f6, 1.5f, f6, this.as(n2));
    }

    private void a(class_4587 class_45872, float f, float f2, float f3, float f4, int n) {
        Render2D.drawBorder(class_45872, f - f3 - f4, f2 - f3 - f4, 2.0f * (f3 + f4), 2.0f * (f3 + f4), f3 + f4, 1.5f, f4, this.as(n));
    }

    private boolean hit(float f, float f2, float f3, float f4, float f5, float f6) {
        return f >= f3 && f <= f3 + f5 && f2 >= f4 && f2 <= f4 + f6;
    }

    private void a(float f, float f2, float f3, float f4, int n, Runnable runnable) {
        SearchResult searchResult = new SearchResult();
        searchResult.x = f;
        searchResult.y = f2;
        searchResult.Pn = f3;
        searchResult.Po = f4;
        searchResult.button = n;
        searchResult.mo = runnable;
        searchResult.WP = this.Vi != null && this.WB;
        this.Wc.add(searchResult);
    }

    private void b(float f, float f2, float f3, float f4, int n, Runnable runnable) {
        if (!this.UJ) {
            return;
        }
        float f5 = this.UK[1];
        float f6 = this.UK[1] + this.UK[3];
        float f7 = Math.max(f2, f5);
        float f8 = Math.min(f2 + f4, f6);
        if (f8 <= f7) {
            return;
        }
        this.a(f, f7, f3, f8 - f7, n, runnable);
    }

    private void a(float f, float f2, float f3, float f4, float f5, float f6, String string, Consumer<Float> consumer) {
        SearchResult searchResult = new SearchResult();
        searchResult.x = f;
        searchResult.y = f2;
        searchResult.Pn = f3;
        searchResult.Po = f4;
        searchResult.button = 0;
        searchResult.WM = consumer::accept;
        searchResult.WN = f5;
        searchResult.WO = f6;
        searchResult.WQ = string;
        this.Wc.add(searchResult);
    }

    public void method_25426() {
        this.Wn = false;
        this.resetOpenAnimation();
        this.UB.reset();
        this.UL.init();
        ClickRippleManager.clear();
        UiThemeController.d(UiThemeController.oX());
    }

    public void method_25393() {
        this.UL.tick();
    }

    public boolean method_25421() {
        return false;
    }

    public void beginClose() {
        if (this.close) {
            return;
        }
        this.close = true;
        this.UA.ro();
        this.UA.i(false);
        this.Vl = null;
        this.Vm = null;
        this.Vi = null;
        this.nq();
        this.Vs = null;
        this.Vy = 0;
        this.Vt = null;
        ClickRippleManager.nX();
        this.UB.reset();
    }

    public void method_25419() {
        this.beginClose();
    }

    public void resetOpenAnimation() {
        this.close = false;
        this.Uz.w(560L);
        this.Uz.ae(true);
        this.UA.w(320L);
        this.UA.ro();
        this.UG = false;
        this.UF.clear();
        this.UH.clear();
        this.UI.clear();
    }

    public void method_25432() {
        this.UL.shutdown();
        this.syncClosedState();
        super.method_25432();
    }

    public boolean isClosing() {
        return this.close;
    }

    private void finishClosing() {
        if (!this.close) {
            return;
        }
        this.close = false;
        this.UA.ro();
        this.resetOpenAnimation();
        this.syncClosedState();
        if (aiG != null && LinkGuiScreen.aiG.field_1755 == this) {
            super.method_25419();
        }
    }

    private void syncClosedState() {
        UI uI = Celebression.getInstance().getModuleManager().getModule(UI.class);
        if (uI != null) {
            uI.syncClosedState();
        }
        ScreenTransitionController.mK().mI();
    }

    private float getUiFade() {
        if (this.close) {
            return this.UA.rk();
        }
        return Math.max(0.02f, this.Uz.rk());
    }

    public UiInteractionState nI() {
        return this.UM;
    }

    public boolean nJ() {
        return this.Vf != Page.WC;
    }

    public boolean nK() {
        return this.UB.isFocused();
    }

    public void b(class_332 class_3322) {
    }

    @Generated
    public void setDescription(String string) {
        this.description = string;
    }

    private void c(SliderSetting sliderSetting, Float f) {
        this.a(sliderSetting, f.floatValue());
    }

    private static void d(BooleanSetting booleanSetting) {
        booleanSetting.a((Boolean)booleanSetting.jT() == false);
    }

    static enum Page {
        WC,
        WD,
        WE,
        WF;
}

    static interface SettingRenderer {
        public void set(float var1);
    }

    static final class Section {
        private final float WH;
        private final float WI;
        private final long WJ;

        Section(float f, float f2, long l) {
            this.WH = f;
            this.WI = f2;
            this.WJ = l;
        }

        @Override
        public final String toString() {
            return "Section[" + "x=" + this.WH + ", " + "y=" + this.WI + ", " + "t=" + this.WJ + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.WH, this.WI, this.WJ);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof Section other)) return false;
            return java.util.Objects.deepEquals(this.WH, other.WH)
                && java.util.Objects.deepEquals(this.WI, other.WI)
                && java.util.Objects.deepEquals(this.WJ, other.WJ);
        }

        public float x() {
            return this.WH;
        }

        public float y() {
            return this.WI;
        }

        public long nV() {
            return this.WJ;
        }
    }

    static final class ModulePredicate {
        final HashMap<String, Integer> WK = new HashMap();
        boolean WL = true;

        ModulePredicate() {
        }
    }

    @FunctionalInterface
    static interface ContentRenderer {
        public void draw(class_4587 var1, float var2, float var3, float var4, Color var5);
    }

    static final class SearchResult {
        float x;
        float y;
        float Pn;
        float Po;
        int button;
        Runnable mo;
        SettingRenderer WM;
        float WN;
        float WO;
        boolean WP;
        String WQ;

        SearchResult() {
        }
    }
}
