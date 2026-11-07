package ru.method.celebression.hud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_1074;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_408;
import net.minecraft.class_742;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.render.AudioVisualizerRenderer;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.animation.EasingMath;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.hud.HudNotification;
import ru.method.celebression.hud.HudNotificationQueue;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.media.LyricsService;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.media.MediaSessionRegistry;
import ru.method.celebression.media.MediaSessionTracker;
import ru.method.celebression.module.Module;
import ru.method.celebression.server.ServerDetector;
import ru.method.celebression.util.StreamerTextSanitizer;
import ru.method.celebression.animation.TimedProgress;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.HudRenderFrame;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;

public class DynamicIslandHud
extends HudElement {
    private static final class_2960 hE = Celebression.id("music/previous.png");
    private static final class_2960 hF = Celebression.id("music/play.png");
    private static final class_2960 hG = Celebression.id("music/pause.png");
    private static final class_2960 hH = Celebression.id("music/next.png");
    private static final float hI = 17.0f;
    private static final float hJ = 7.0f;
    private static final float hK = 4.5f;
    private static final float hL = 3.5f;
    private static final float hM = 6.0f;
    private static final float hN = 0.72f;
    private static final float hO = 4.0f;
    private static final float hP = 2.0f;
    private final Animation hQ = new Animation(900L, 1.0, false, Easing.aiR);
    private final Animation hR = new Animation(900L, 1.0, false, Easing.aiR);
    private final Animation hS = new Animation(300L, 1.0, false, Easing.aiR);
    private HudNotification hT;
    private final Animation hU = new Animation(520L, 1.0, false, Easing.aiS);
    private final Animation hV = new Animation(320L, 1.0, false, Easing.aiX);
    private final Animation hW = new Animation(420L, 1.0, false, Easing.aiX);
    private final Animation hX = new Animation(800L, 1.0, false, Easing.aiR);
    private final Animation hY = new Animation(280L, 1.0, false, Easing.aiX);
    private final TimedProgress hZ = new TimedProgress(200L, Easing.aiZ, "0:00");
    private final TimedProgress ia = new TimedProgress(200L, Easing.aiZ, "0:00");
    private final TimedProgress ib = new TimedProgress(380L, Easing.aiS, "");
    private final TimedProgress ic = new TimedProgress(380L, Easing.aiS, "");
    private final TimedProgress ie = new TimedProgress(220L, Easing.aiZ, "");
    private final TimedProgress secondaryText = new TimedProgress(220L, Easing.aiZ, "");
    private final TimedProgress ig = new TimedProgress(220L, Easing.aiZ, "");
    private final TimedProgress ih = new TimedProgress(220L, Easing.aiZ, "");
    private final Animation ii = new Animation(300L, 1.0, false, Easing.aiR);
    private final Color ij = new Color(0, 255, 0);
    private final Color ik = new Color(255, 0, 0);
    private Color il = this.ij;
    private Color im = this.ij;
    private String in = "";
    private boolean io;
    private boolean ip;
    private boolean iq = true;
    private float ir = 0.5f;
    private long is = 0L;
    private Color it = new Color(0, 0, 0);
    private Color iu = new Color(0, 0, 0);
    private boolean iv;
    private float iw;
    private float ix;
    private float iy;
    private boolean iz;
    private boolean iA;
    private float iB;
    private float iC;
    private float iD;
    private float iE;
    private float iF;
    private float iG;
    private float iH;
    private float iI;
    private float iJ;
    private float iK;
    private float iL;
    private float iM;
    private String iN = "";
    private String iO = "";
    private String iP = "";
    private String iQ = "";
    private String iR = "";
    private String iS = "";
    private long iT;
    private int iU;
    private float iV;
    private float iW;
    private float iX;
    private long iY;
    private float iZ;
    private float ja = 1.0f;
    private final Animation jb = new Animation(220L, 1.0, false, Easing.aiR);
    private final MediaSessionTracker jc = MediaSessionRegistry.dy();
    private final LyricsService jd = new LyricsService();
    private final AudioVisualizerRenderer je = new AudioVisualizerRenderer();
    private final Map<String, List<String>> jf = new HashMap<String, List<String>>();

    public DynamicIslandHud() {
        super("DynamicIsland");
    }

    private boolean cn() {
        return aiG != null && DynamicIslandHud.aiG.field_1755 instanceof CustomMainMenuScreen;
    }

    private float co() {
        long l = System.nanoTime();
        if (this.iY == 0L) {
            this.iY = l;
            this.iZ = 0.016666668f;
            return this.iZ;
        }
        float f = (float)(l - this.iY) / 1.0E9f;
        this.iY = l;
        this.iZ = class_3532.method_15363((float)f, (float)0.0f, (float)0.1f);
        return this.iZ;
    }

    private float g(float f, float f2) {
        float f3 = Math.max(0.0f, f);
        float f4 = class_3532.method_15363((float)f2, (float)0.0f, (float)1.0f);
        if (f3 == 0.0f || f4 == 0.0f) {
            return 0.0f;
        }
        return 1.0f - (float)Math.exp(-f3 * f4);
    }

    public boolean handleScreenClick(int n, int n2, int n3) {
        if (this.bL()) {
            return false;
        }
        if (!MathUtils.c(this.getX(), this.getY(), this.getWidth(), this.getHeight(), n, n2)) {
            return false;
        }
        return switch (n3) {
            case 0 -> this.b(n, n2);
            case 1 -> this.a(n, n2);
            case 2 -> this.c(n, n2);
            default -> false;
        };
    }

    @Override
    public void onEnable() {
        this.cp();
        this.iV = 0.0f;
        this.gn.ae(true);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.iV = 0.0f;
        this.gn.ae(false);
        HudNotificationQueue.dG().dH();
        this.cq();
        this.iv = false;
        this.jd.reset();
        this.hY.ae(false);
        this.secondaryText.cF("");
        this.ig.cF("");
        super.onDisable();
    }

    private void cp() {
        this.jd.dc();
        this.jc.dc();
    }

    private void cq() {
        this.jd.shutdown();
        this.jc.shutdown();
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (Celebression.getInstance().isPanic()) {
            return;
        }
        if (Module.fullNullCheck() && !this.cn()) {
            return;
        }
        if (!this.isToggled()) {
            return;
        }
        HudNotificationQueue.dG().dI();
        this.cy();
    }

    @Override
    @EventHandler
    public void onMouse(EventMouse eventMouse) {
        if (!Celebression.getInstance().isPanic() && eventMouse.getAction() == 1 && eventMouse.getButton() == 0 && DynamicIslandHud.aiG.field_1755 != null && !(DynamicIslandHud.aiG.field_1755 instanceof class_408) && !this.cn() && !DynamicIslandHud.fullNullCheck() && this.iv && this.iz && this.f(this.bG(), this.bH())) {
            eventMouse.consume();
            return;
        }
        super.onMouse(eventMouse);
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        String string;
        boolean bl;
        if (HudThemeRenderer.active()) {
            this.cz();
            return;
        }
        boolean bl2 = this.cn();
        HudRenderFrame.State state = HudRenderFrame.get();
        if (state != null && state.shouldDeferDynamicIsland()) {
            this.cz();
            return;
        }
        if (DynamicIslandHud.fullNullCheck() && !bl2 || !bl2 && this.bL()) {
            this.cz();
            return;
        }
        float f8 = this.co();
        HudNotification hudNotification = HudNotificationQueue.dG().dL();
        if (hudNotification != null) {
            this.hT = hudNotification;
        }
        boolean bl3 = DynamicIslandHud.aiG.field_1724 != null;
        boolean bl4 = bl3 && ServerDetector.si();
        boolean bl5 = this.cr();
        boolean bl6 = this.cs();
        boolean bl7 = hudNotification != null;
        this.hS.i(bl7);
        float f9 = this.hS.rk();
        HudNotification hudNotification2 = hudNotification != null ? hudNotification : this.hT;
        boolean bl8 = bl = hudNotification2 != null && f9 > 0.01f;
        if (hudNotification2 != null && bl) {
            this.a(hudNotification2);
        }
        boolean bl9 = !bl7 && bl4;
        boolean bl10 = bl;
        boolean bl11 = bl5 && !bl9 && !bl10;
        this.hQ.i(bl3 && ServerDetector.t((class_1657)DynamicIslandHud.aiG.field_1724) < 150);
        this.hR.i(bl4);
        boolean bl12 = this.iv && bl11;
        this.hV.i(bl12);
        this.hW.i(bl12);
        boolean bl13 = bl6 && bl5 && !bl9;
        this.hX.i(bl13);
        this.iz = false;
        this.hY.i(bl12 && this.jd.isOpen());
        float f10 = this.hV.rk();
        float f11 = this.hW.rk();
        float f12 = 2.3f;
        float f13 = 19.0f;
        float f14 = this.a(f12, bl, bl9);
        float f15 = 0.0f;
        float f16 = f13;
        if (bl10) {
            string = this.ih.rh();
        } else if (bl9) {
            string = class_1074.method_4662((String)"elements.dynamicisland.pvp", (Object[])new Object[0]);
        } else if (bl11) {
            f12 = MathUtils.lerp(2.3f, 3.5f, f10);
            f7 = MathUtils.lerp(19.0f, 76.0f, f10);
            f6 = this.hY.rk() * 36.0f * f10;
            f13 = f7 + f6;
            f14 = this.a(f12, false, false);
            f16 = f13;
            f15 = 6.0f * f10;
            string = this.ct();
            this.iW = f7;
            this.iX = f6;
        } else {
            string = bl3 ? this.cE() : "Celebression";
        }
        f6 = 0.0f;
        if (bl11) {
            f5 = this.e(f13, f12, 0.0f);
            f4 = Math.max(175.0f, this.e(f13, f12, 1.0f));
            f3 = Math.max(175.0f, 175.0f);
            f5 = Math.min(f5, f3);
            f4 = Math.min(f4, f3);
            f7 = MathUtils.lerp(f5, f4, f10);
        } else {
            f6 = bl10 ? this.cB() : Fonts.ROUNDBOLD.getWidth(string, 8.0f);
            f7 = f5 = f16 + f6 + f12 + f14;
        }
        float f17 = f5 = bl5 ? 0.12f : 0.08f;
        if (this.iV <= 0.01f) {
            this.iV = Math.max(1.0f, f7 * 0.6f);
        }
        f4 = bl5 ? 10.0f : 7.5f;
        f3 = this.g(f4, f8);
        float f18 = class_3532.method_15363((float)Math.max(f5, f3), (float)0.0f, (float)1.0f);
        float f19 = this.iV = MathUtils.lerp(this.iV, f7, f18);
        if (!bl11) {
            f6 = Math.min(f6, Math.max(0.0f, f19 - f16 - f12 - f14));
        }
        float f20 = (float)aiG.method_22683().method_4486() / 2.0f - f19 / 2.0f;
        float f21 = 1.0f + f15;
        float f22 = f12 * 2.1f;
        Color color = this.i(255);
        Color color2 = bl2 ? Color.WHITE : this.a(f20, f21, f19, f13, f22, string);
        float f23 = 20.0f;
        float[] fArray = this.bK();
        float f24 = class_3532.method_15363((float)((bl2 ? 1.0f : this.gn.rk()) * this.bJ()), (float)0.0f, (float)1.0f);
        if (f24 <= 0.001f) {
            this.cz();
            return;
        }
        this.ja = f2 = this.bI();
        float f25 = f20 + f19 / 2.0f + fArray[0];
        float f26 = f21 + f13 / 2.0f + fArray[1];
        hudRenderEvent.j().method_51448().method_22903();
        hudRenderEvent.j().method_51448().method_46416(f25, f26, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f2 * f24, f2 * f24, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-f25, -f26, 0.0f);
        f20 += fArray[0];
        f21 += fArray[1];
        Color color3 = ColorUtils.getGlassColor(255);
        if (bl2) {
            boolean bl14 = MathUtils.c(f20, f21, f19, f13, this.bG(), this.bH());
            this.jb.i(bl14);
            f = class_3532.method_15363((float)this.jb.rk(), (float)0.0f, (float)1.0f);
            float f27 = class_3532.method_15363((float)((float)Math.sin(1.5707963267948966 * (double)f)), (float)0.0f, (float)1.0f);
            float f28 = class_3532.method_15363((float)(Math.min(f19, f13) * 0.12f), (float)6.0f, (float)16.0f);
            float f29 = 0.02f + 0.018f * f27;
            Render2D.drawLiquidGlassRectMainMenu(hudRenderEvent.j().method_51448(), f20, f21, f19, f13, f23, color3, liquidGlassNewBuilder -> {
                float smoothness = 2.0f;
                liquidGlassNewBuilder.cornerSmoothness(smoothness);
                liquidGlassNewBuilder.smoothness(smoothness);
                liquidGlassNewBuilder.fresnelPower(11.0f);
                liquidGlassNewBuilder.fresnelAlpha(0.5f);
                liquidGlassNewBuilder.baseAlpha(175.0f);
                liquidGlassNewBuilder.fresnelInvert(true);
                liquidGlassNewBuilder.fresnelMix(0.0f);
                liquidGlassNewBuilder.distortStrength(f29);
                liquidGlassNewBuilder.blurRadius(f28);
            });
        } else {
            Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f20, f21, f19, f13, new BorderRadius(f23), new ColorRGBA(color3), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        }
        if (bl10) {
            this.a(hudRenderEvent, f20, f21, f13, f12, f6, hudNotification2, f9);
        } else if (bl9) {
            this.a(hudRenderEvent, f20, f21, f13, f12, f6, true, 1.0f);
        } else if (bl11) {
            float f30 = this.hX.rk();
            this.a(hudRenderEvent, f20, f21, f19, f13, this.iW, this.iX, f12, f10, f11, color, bl6, f30);
        } else {
            this.a(hudRenderEvent, f20, f21, f13, f12, f6, false, 1.0f);
        }
        if (!bl12) {
            this.a(hudRenderEvent, f20, f21, f19, f13, f12, f22, color2);
        }
        float f31 = f25 - f19 * f2 / 2.0f;
        f = f26 - f13 * f2 / 2.0f;
        this.f(f25, f26, f2);
        this.b(f31, f, f19 * f2, f13 * f2);
        hudRenderEvent.j().method_51448().method_22909();
        if (!bl7 && f9 <= 0.01f) {
            this.hT = null;
            this.cA();
        }
        super.onRender2D(hudRenderEvent);
    }

    @Override
    protected boolean a(int n, int n2) {
        return false;
    }

    @Override
    protected boolean b(int n, int n2) {
        if (!this.cr()) {
            return false;
        }
        if (this.iv && this.f(n, n2)) {
            return true;
        }
        if (this.d(n, n2)) {
            return true;
        }
        this.iv = !this.iv;
        return true;
    }

    private float e(float f, float f2, float f3) {
        float f4 = class_3532.method_15363((float)f3, (float)0.0f, (float)1.0f);
        float f5 = 25.0f * f4;
        float f6 = Math.max(6.0f, f - f2 * 2.0f - f5);
        float f7 = f6 * 0.72f;
        float f8 = MathUtils.lerp(f7, f7 + Math.min(4.0f, f6 * 0.35f), f4);
        f8 *= MathUtils.lerp(1.0f, 0.72f, f4);
        f8 += 4.0f * f4;
        float f9 = MathUtils.lerp(0.8f, 1.0f, f4);
        float f10 = Fonts.ROUNDBOLD.getWidth(this.ct(), 8.0f);
        String string = this.cu();
        boolean bl = f4 > 0.05f && !string.isEmpty();
        float f11 = bl ? Fonts.SEMIBOLD.getWidth(string, 7.0f) : 0.0f;
        float f12 = Math.max(f10, f11);
        float f13 = f12 + 4.0f;
        float f14 = MathUtils.lerp(f13, f12, f4);
        float f15 = this.hX.rk();
        if (f15 > 0.05f) {
            f14 += this.je.n(f9) + 1.0f * f9 + 2.8f;
        }
        float f16 = 65.0f;
        float f17 = Math.max(f16 + f2 * 2.0f, 63.0f);
        float f18 = 35.0f;
        float f19 = Math.max(f18, MathUtils.lerp(f18, f17, f4));
        float f20 = f4 > 0.05f ? f2 : 0.0f;
        float f21 = Math.max(f19, f2 + f8 + f2 + -1.0f + f14 + f20);
        if (f4 > 0.05f) {
            float f22 = this.cC();
            f21 = Math.max(f21, f22 + f2 * 2.0f);
        }
        return f21 + (f4 > 0.05f ? 8.0f : 0.0f);
    }

        private void a(HudRenderEvent hudRenderEvent, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Color color, boolean bl, float f10) {
        Object object;
        Object object2;
        Object object3;
        float f11;
        float f12;
        float f13;
        float f14;
        float f15;
        float f16;
        float f17;
        float f18;
        float f19;
        float f20;
        float f21;
        boolean bl2;
        if (!this.jc.cr()) {
            return;
        }
        this.iA = false;
        this.iN = "";
        this.iO = "";
        this.iP = "";
        this.iQ = "";
        this.iR = "";
        this.iS = "";
        float f22 = class_3532.method_15363((float)f8, (float)0.0f, (float)1.0f);
        float f23 = 25.0f * f22;
        float f24 = Math.max(6.0f, f5 - f7 * 2.0f - f23);
        float f25 = f24 * 0.72f;
        float f26 = MathUtils.lerp(f25, f25 + Math.min(4.0f, f24 * 0.35f), f9);
        f26 *= MathUtils.lerp(1.0f, 0.72f, f22);
        float f27 = Math.max(2.5f, (f26 += 4.0f * f22) * 0.22f);
        String string = this.ct();
        String string2 = this.cu();
        this.ib.cF(string);
        this.ic.cF(string2);
        String string3 = this.ib.rh();
        String string4 = this.ic.rh();
        boolean bl3 = f22 > 0.05f && !string4.isEmpty();
        float f28 = Fonts.ROUNDBOLD.getHeight(8.0f);
        float f29 = Fonts.SEMIBOLD.getHeight(7.0f);
        float f30 = 2.0f;
        float f31 = Fonts.ROUNDBOLD.getWidth(string3, 8.0f);
        float f32 = f + f7;
        float f33 = 2.0f * f22;
        float f34 = f32 + f33;
        float f35 = f2 + f7 + (f24 - f26) / 2.0f;
        float f36 = f35 + f26 / 2.0f;
        class_1043 class_10432 = this.jc.dq();
        if (class_10432 != null) {
            Render2D.drawTexture(hudRenderEvent.j().method_51448(), f34, f35, f26, f26, f27, (class_1044)class_10432, Color.WHITE);
        } else {
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f34, f35, f26, f26, f27, ColorUtils.getGlobalColor((int)(140.0f * Math.max(f8, 0.35f))));
        }
        float f37 = f34 + f26 + f7 + -1.0f;
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(255.0f * f22));
        float f38 = MathUtils.lerp(0.8f, 1.0f, f22);
        float f39 = MathUtils.lerp(1.5f, 0.65f, f22);
        boolean bl4 = f22 > 0.05f;
        float f40 = bl4 ? f7 : 0.0f;
        float f41 = bl4 ? 4.0f : 1.0f;
        float f42 = f3 - (f37 - f) - f40 - f41 - -1.0f;
        float f43 = f10 > 0.02f ? this.je.n(f38) + 1.0f * f38 + 2.8f : 0.0f;
        float f44 = f38;
        if (f10 <= 0.02f) {
            bl2 = false;
        } else if (bl4) {
            bl2 = f42 - f43 >= 32.0f;
        } else {
            f21 = f31 + f43 + 2.8f;
            if (f21 <= f42) {
                bl2 = true;
            } else {
                f20 = Math.max(0.0f, f42 - 6.0f);
                if (f20 > 2.0f) {
                    f19 = Math.min(1.0f, f20 / Math.max(1.0f, f43));
                    f44 = f38 * Math.max(0.8f, f19);
                    if ((f43 = this.je.n(f44) + 1.0f * f44 + 2.8f) > f20) {
                        f43 = f20;
                    }
                    bl2 = f43 > 2.0f;
                } else {
                    bl2 = false;
                }
            }
        }
        f21 = bl2 ? f43 : 0.0f;
        f20 = bl2 ? f10 : 0.0f;
        f19 = Math.max(0.0f, f42 - f21);
        float f45 = Math.min(f19, Math.max(f31, 0.0f) + 4.0f);
        float f46 = bl3 ? Fonts.SEMIBOLD.getWidth(string4, 7.0f) : 0.0f;
        float f47 = f31;
        if (!bl4) {
            f18 = Math.max(0.0f, f19);
            if (f31 <= f18 + (f17 = 1.25f)) {
                f47 = f31;
            } else {
                String string5 = this.b(string3, f18, 8.0f, Fonts.ROUNDBOLD);
                f47 = Fonts.ROUNDBOLD.getWidth(string5, 8.0f);
            }
        }
        f18 = bl4 ? Math.min(f19, Math.max(f31, f46)) : Math.min(f19, f47);
        f17 = bl4 ? f19 : f45;
        boolean bl5 = false;
        float f48 = 0.0f;
        float f49 = f28 + (bl3 ? f29 + f30 : 0.0f);
        float f50 = f2 + f7 + (f24 - f49) / 2.0f;
        this.je.i(bl);
        String string6 = this.b(string3, f17, 8.0f, Fonts.ROUNDBOLD);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(8.0f), string6, f37 - f48, f50, color);
        if (bl3) {
            String string7 = this.b(string4, f17, 7.0f, Fonts.SEMIBOLD);
            Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.SEMIBOLD.getFont(7.0f), string7, f37, f50 + f28 + f30, color2);
        }
        float f51 = f20 * (bl4 ? 0.75f : 1.0f);
        float f52 = bl4 ? 4.1f : 4.0f;
        this.je.a(hudRenderEvent, color, f37, f50, f7, f5, f3, f, f51, f18, f44, f39, f52, f36, bl4);
        float f53 = f2 + f4 - f7 - 0.0f;
        if (f22 > 0.02f) {
            f16 = f53 - 17.0f - 1.0f;
            f15 = 4.0f;
            f14 = 17.0f;
            f13 = f14 * 3.0f + 14.0f + f15 * 2.0f;
            f12 = f + (f3 - f13) / 2.0f;
            f11 = f12 + f15;
            float f54 = f11 + f14 + 7.0f;
            float f55 = f54 + f14 + 7.0f;
            object3 = this.jc.isPlaying() ? hG : hF;
            int n = color2.getRGB();
            object2 = new Color(n, true);
            this.a(hudRenderEvent.j(), hE, f11, f16, f14, (Color)object2, true);
            this.a(hudRenderEvent.j(), (class_2960)object3, f54, f16, f14, (Color)object2, true);
            this.a(hudRenderEvent.j(), hH, f55, f16, f14, (Color)object2, true);
            this.iz = true;
            this.iw = f11;
            this.ix = f16;
            this.iy = f13;
        } else {
            this.iz = false;
        }
        if (f22 > 0.05f) {
            float f56;
            f16 = f + f7;
            f15 = f53 - 17.0f - 3.5f - 4.5f - 2.0f - 1.0f;
            long l = this.cw();
            long l2 = this.cx();
            String string8 = this.a(l2);
            String string9 = this.a(l);
            this.hZ.cF(string8);
            this.ia.cF(string9);
            float f57 = 6.0f;
            float f58 = Fonts.SEMIBOLD.getWidth(string8, f57);
            float f59 = Fonts.SEMIBOLD.getWidth(string9, f57);
            float f60 = 1.8f;
            float f61 = Math.max(0.0f, f3 - f7 * 2.0f - f58 - f59 - f60 * 4.0f);
            float f62 = class_3532.method_15363((float)f61, (float)110.0f, (float)640.0f);
            float f63 = f58 + f59 + f60 * 4.0f + f62;
            float f64 = f + (f3 - f63) / 2.0f;
            float f65 = f + f7;
            float f66 = f + f3 - f7;
            float f67 = f64 + f63;
            if (f64 < f65) {
                f56 = f65 - f64;
                f64 += f56;
                f67 += f56;
            }
            if (f67 > f66) {
                f56 = f67 - f66;
                f64 -= f56;
                f67 -= f56;
            }
            f56 = f64 + f58 + f60 * 2.0f;
            float f68 = l > 0L ? MathUtils.k((float)l2 / (float)l, 0.0f, 1.0f) : 0.0f;
            Color color3 = new Color(255, 255, 255, (int)(70.0f * f22));
            object = new Color(255, 255, 255, (int)(200.0f * f22));
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f56, f15, f62, 4.5f, Math.min(1.6f, 2.25f), color3);
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f56, f15, f62 * f68, 4.5f, Math.min(1.6f, 2.25f), (Color)object);
            float f69 = f15 + (4.5f - Fonts.SEMIBOLD.getHeight(f57)) / 2.0f;
            float f70 = Fonts.SEMIBOLD.getHeight(f57) * 0.55f;
            this.a(hudRenderEvent.j(), this.hZ, f56 - f60 - f58, f69, color2, f70, f57);
            this.a(hudRenderEvent.j(), this.ia, f56 + f62 + f60, f69, color2, f70, f57);
        }
        if ((f16 = this.hY.rk() * f22) > 0.02f) {
            boolean bl6;
            String string10;
            f15 = 36.0f * f16;
            f14 = f2 + f5 - f7 - f15;
            f13 = 6.0f;
            f12 = Math.max(0.0f, f3 - f7 * 2.0f - f13 * 2.0f);
            f11 = 8.0f;
            long l = this.cx();
            object3 = null;
            LyricsService.LyricsLine lyricsLine = null;
            object2 = null;
            LyricsService.LyricsLine lyricsLine2 = null;
            if (this.jd.dd()) {
                object3 = new LyricsService.LyricsLine(0L, "Loading lyrics...");
            } else if (!this.jd.isLoaded() || this.jd.de().isEmpty()) {
                object3 = new LyricsService.LyricsLine(0L, "No lyrics");
            } else {
                List<LyricsService.LyricsLine> list;
                int n = this.jd.b(l);
                List<LyricsService.LyricsLine> list2 = list = this.jd.de();
                synchronized (list2) {
                    if (n >= 0 && n < list.size()) {
                        object3 = list.get(n);
                    }
                    if (n - 1 >= 0 && n - 1 < list.size()) {
                        lyricsLine2 = list.get(n - 1);
                    }
                    if (n + 1 < list.size()) {
                        lyricsLine = list.get(n + 1);
                    }
                    if (n + 2 < list.size()) {
                        object2 = list.get(n + 2);
                    }
                }
                if (object3 == null && !list.isEmpty()) {
                    object3 = list.get(0);
                    if (list.size() > 1) {
                        lyricsLine = list.get(1);
                    }
                    if (list.size() > 2) {
                        object2 = list.get(2);
                    }
                }
            }
            String string11 = lyricsLine2 != null ? this.a(lyricsLine2.text(), f12, f11) : "";
            String string12 = object3 != null ? ((LyricsService.LyricsLine)object3).text() : "";
            String string13 = lyricsLine != null ? lyricsLine.text() : "";
            String string14 = object2 != null ? ((LyricsService.LyricsLine)object2).text() : "";
            List<String> list = this.a(string12, f12, f11, 2);
            String string15 = !list.isEmpty() ? list.get(0) : "";
            String string16 = list.size() > 1 ? list.get(1) : "";
            boolean bl7 = !string16.isEmpty();
            String string17 = !string13.isBlank() ? this.a(string13, f12, f11) : "";
            boolean bl8 = string11.isEmpty();
            String primaryLine;
            if (bl8) {
                if (bl7) {
                    primaryLine = string16;
                    string10 = !string17.isEmpty() ? string17 : (!string14.isBlank() ? this.a(string14, f12, f11) : "");
                } else {
                    List<String> list3 = this.a(string13, f12, f11, 2);
                    if (list3.size() > 1) {
                        primaryLine = list3.get(0);
                        string10 = list3.get(1);
                    } else {
                        primaryLine = !list3.isEmpty() ? list3.get(0) : string17;
                        string10 = !string14.isBlank() ? this.a(string14, f12, f11) : "";
                    }
                }
            } else {
                primaryLine = string11;
                string10 = bl7 ? string16 : string17;
            }
            this.ie.cF(primaryLine);
            this.secondaryText.cF(string15);
            this.ig.cF(string10);
            this.iA = true;
            this.iN = primaryLine;
            this.iO = string15;
            this.iP = string10;
            this.iQ = bl8 ? (bl7 ? (object3 != null ? ((LyricsService.LyricsLine)object3).text() : string12) : (lyricsLine != null ? lyricsLine.text() : primaryLine)) : (lyricsLine2 != null ? lyricsLine2.text() : string11);
            String string18 = this.iR = object3 != null ? ((LyricsService.LyricsLine)object3).text() : string12;
            this.iS = bl8 ? (lyricsLine != null ? lyricsLine.text() : string10) : (bl7 ? this.iR : (lyricsLine != null ? lyricsLine.text() : string10));
            int n = class_3532.method_15340((int)Math.round((float)color2.getAlpha() * f16), (int)0, (int)255);
            Color color4 = this.i(n);
            Color color5 = ColorUtils.alpha(ColorUtils.getGlassBase(), n);
            Color color6 = ColorUtils.mix(color4, color5, 0.2f);
            Color color7 = ColorUtils.mix(color4, color5, 0.35f);
            Color color8 = bl7 ? color4 : color6;
            float f71 = Fonts.SEMIBOLD.getHeight(f11);
            float f72 = 3.0f;
            float f73 = f71 * 3.0f + f72 * 2.0f;
            float f74 = f2 + f5 - f7 - f73 - 0.0f - 17.0f - 3.5f - 4.5f - 2.0f + 33.0f;
            float f75 = f71 * 0.55f;
            float f76 = f71 + f72;
            float f77 = f74;
            float f78 = f77 + f76;
            float f79 = f78 + f76;
            boolean bl9 = !this.ie.rh().isEmpty();
            boolean bl10 = bl6 = !this.ig.rh().isEmpty();
            if (bl8) {
                float f80;
                float f81;
                float f82;
                float f83 = Fonts.SEMIBOLD.getWidth(this.secondaryText.rh(), f11);
                this.iF = f82 = f + (f3 - f83) / 2.0f;
                this.iG = f77;
                this.iH = f83;
                this.iI = f71;
                this.a(hudRenderEvent.j(), this.secondaryText, f82, f77, color4, f75, f11);
                if (bl9) {
                    f81 = Fonts.SEMIBOLD.getWidth(this.ie.rh(), f11);
                    this.iB = f80 = f + (f3 - f81) / 2.0f;
                    this.iC = f78;
                    this.iD = f81;
                    this.iE = f71;
                    Color color9 = bl7 ? color4 : color6;
                    this.a(hudRenderEvent.j(), this.ie, f80, f78, color9, f75, f11);
                }
                if (bl6) {
                    f81 = Fonts.SEMIBOLD.getWidth(this.ig.rh(), f11);
                    this.iJ = f80 = f + (f3 - f81) / 2.0f;
                    this.iK = f79;
                    this.iL = f81;
                    this.iM = f71;
                    this.a(hudRenderEvent.j(), this.ig, f80, f79, color6, f75, f11);
                }
            } else {
                float f84;
                float f85;
                boolean bl11;
                boolean bl12 = !string11.isEmpty();
                boolean bl13 = bl11 = !string17.isEmpty();
                int n2 = !bl12 ? 1 : (!bl11 && !bl7 ? 3 : 2);
                float f86 = switch (n2) {
                    case 1 -> f77;
                    case 3 -> f79;
                    default -> f78;
                };
                float f87 = f86 - f76;
                float f88 = f86 + f76;
                if (n2 == 1 && !bl7 && bl11) {
                    f88 = f86 + f76 * 2.0f;
                }
                if (n2 == 3 && !bl7 && bl12) {
                    f87 = f86 - f76 * 2.0f;
                }
                if (bl12) {
                    f85 = Fonts.SEMIBOLD.getWidth(this.ie.rh(), f11);
                    this.iB = f84 = f + (f3 - f85) / 2.0f;
                    this.iC = f87;
                    this.iD = f85;
                    this.iE = f71;
                    this.a(hudRenderEvent.j(), this.ie, f84, f87, color7, f75, f11);
                }
                f85 = Fonts.SEMIBOLD.getWidth(this.secondaryText.rh(), f11);
                this.iF = f84 = f + (f3 - f85) / 2.0f;
                this.iG = f86;
                this.iH = f85;
                this.iI = f71;
                this.a(hudRenderEvent.j(), this.secondaryText, f84, f86, color4, f75, f11);
                if (bl6) {
                    float f89;
                    float f90 = Fonts.SEMIBOLD.getWidth(this.ig.rh(), f11);
                    this.iJ = f89 = f + (f3 - f90) / 2.0f;
                    this.iK = f88;
                    this.iL = f90;
                    this.iM = f71;
                    this.a(hudRenderEvent.j(), this.ig, f89, f88, color8, f75, f11);
                }
            }
        }
    }

    private boolean d(int n, int n2) {
        String string;
        if (!this.iv) {
            return false;
        }
        if (!this.jd.isOpen()) {
            return false;
        }
        if (!this.iA) {
            return false;
        }
        int n3 = this.e(n, n2);
        if (n3 == 0) {
            return false;
        }
        long l = System.currentTimeMillis();
        boolean bl = n3 == this.iU && l - this.iT <= 280L;
        this.iT = l;
        this.iU = n3;
        if (!bl) {
            return true;
        }
        String link = switch (n3) {
            case 1 -> this.iQ;
            case 2 -> this.iR;
            case 3 -> this.iS;
            default -> "";
        };
        if (link == null || link.isBlank()) {
            return true;
        }
        DynamicIslandHud.aiG.field_1774.method_1455(link);
        return true;
    }

    private int e(int n, int n2) {
        if (!this.iA) {
            return 0;
        }
        if (!this.iN.isBlank() && MathUtils.c(this.iB, this.iC, this.iD, this.iE, n, n2)) {
            return 1;
        }
        if (!this.iO.isBlank() && MathUtils.c(this.iF, this.iG, this.iH, this.iI, n, n2)) {
            return 2;
        }
        if (!this.iP.isBlank() && MathUtils.c(this.iJ, this.iK, this.iL, this.iM, n, n2)) {
            return 3;
        }
        return 0;
    }

    private boolean cr() {
        return this.jc.cr();
    }

    private boolean cs() {
        return this.jc.isPlaying();
    }

    private String ct() {
        String string = this.jc.getTitle();
        if (this.aw(string)) {
            return this.cv();
        }
        return string;
    }

    private String cu() {
        return this.jc.getArtist();
    }

    private boolean aw(String string) {
        return string == null || string.isBlank() || "Unknown track".equalsIgnoreCase(string.trim());
    }

    private String cv() {
        String string = StreamerTextSanitizer.du(StreamerTextSanitizer.st());
        if (string != null && !string.isBlank()) {
            return string;
        }
        if (DynamicIslandHud.aiG.field_1724 != null && DynamicIslandHud.aiG.field_1724.method_7334() != null) {
            return DynamicIslandHud.aiG.field_1724.method_7334().getName();
        }
        return "Player";
    }

    private long cw() {
        return this.jc.cw();
    }

    private long cx() {
        return this.jc.cx();
    }

    private void cy() {
        MediaSessionTracker.MediaSessionInfo mediaSessionInfo = this.jc.dh();
        if (mediaSessionInfo.dx()) {
            this.iv = false;
            this.hZ.cF("0:00");
            this.ia.cF("0:00");
            this.jd.reset();
            this.hY.ae(false);
            this.ie.cF("");
            this.secondaryText.cF("");
            this.ig.cF("");
        }
        if (mediaSessionInfo.dw()) {
            this.iv = false;
            this.jd.reset();
            this.hY.ae(false);
            this.ie.cF("");
            this.secondaryText.cF("");
            this.ig.cF("");
            this.jd.b(this.jc.dj(), this.jc.dk(), this.jc.dl());
        }
    }

    private float a(float f, boolean bl, boolean bl2) {
        if (bl || bl2) {
            return f * 3.3f;
        }
        return f * 1.35f;
    }

    private boolean f(int n, int n2) {
        if (!this.iz) {
            return false;
        }
        float f = this.ix;
        float f2 = 17.0f * this.ja;
        float f3 = 17.0f * this.ja;
        float f4 = 7.0f * this.ja;
        float f5 = this.iw;
        float f6 = this.iw + f3 + f4;
        float f7 = this.iw + (f3 + f4) * 2.0f;
        try {
            if (MathUtils.c(f5, f, f3, f2, n, n2)) {
                return this.jc.dn();
            }
            if (MathUtils.c(f6, f, f3, f2, n, n2)) {
                return this.jc.togglePlayback();
            }
            if (MathUtils.c(f7, f, f3, f2, n, n2)) {
                return this.jc.dp();
            }
        }
        catch (Throwable throwable) {
        }
        return false;
    }

    private void cz() {
        if (aiG == null || aiG.method_22683() == null) {
            return;
        }
        float f = Math.max(this.iV, 72.0f);
        float f2 = 19.0f;
        float f3 = this.bI();
        float f4 = f * f3;
        float f5 = f2 * f3;
        float f6 = (float)aiG.method_22683().method_4486() / 2.0f - f4 / 2.0f;
        float f7 = 1.0f;
        this.b(f6, f7, f4, f5);
    }

    private void f(float f, float f2, float f3) {
        if (this.iz) {
            this.iw = this.c(f, this.iw, f3);
            this.ix = this.d(f2, this.ix, f3);
            this.iy *= f3;
        }
        if (!this.iA) {
            return;
        }
        this.iB = this.c(f, this.iB, f3);
        this.iC = this.d(f2, this.iC, f3);
        this.iD *= f3;
        this.iE *= f3;
        this.iF = this.c(f, this.iF, f3);
        this.iG = this.d(f2, this.iG, f3);
        this.iH *= f3;
        this.iI *= f3;
        this.iJ = this.c(f, this.iJ, f3);
        this.iK = this.d(f2, this.iK, f3);
        this.iL *= f3;
        this.iM *= f3;
    }

    private String a(long l) {
        long l2 = Math.max(0L, l / 1000L);
        long l3 = l2 / 60L;
        long l4 = l2 % 60L;
        return String.format("%d:%02d", l3, l4);
    }

    private void a(HudRenderEvent hudRenderEvent, float f, float f2, float f3, float f4, float f5, HudNotification hudNotification, float f6) {
        if (hudNotification == null || f6 <= 0.01f) {
            return;
        }
        int n = class_3532.method_15340((int)Math.round(255.0f * f6), (int)0, (int)255);
        float f7 = class_3532.method_15363((float)this.ii.rk(), (float)0.0f, (float)1.0f);
        float f8 = (float)Math.sin(Math.PI * (double)f7);
        float f9 = f3 - f4 * 2.0f;
        float f10 = 1.0f + f8 * 0.18f;
        float f11 = f9 * f10;
        float f12 = f11 / 2.0f;
        float f13 = f + f4 + f9 / 2.0f;
        float f14 = f2 + f3 / 2.0f;
        float f15 = f13 - f11 / 2.0f;
        float f16 = f14 - f11 / 2.0f;
        Color color = ColorUtils.mix(this.il, this.im, f7);
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
        Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f15, f16, f11, f11, f12, color2);
        Color color3 = this.i(n);
        float f17 = Fonts.ROUNDBOLD.getHeight(8.0f) * 0.55f;
        float f18 = f2 + (f3 - Fonts.ROUNDBOLD.getHeight(8.0f)) / 2.0f;
        String string = this.ih.rh();
        String string2 = this.b(string, f5, 8.0f, Fonts.ROUNDBOLD);
        if (!string2.equals(string)) {
            Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(8.0f), string2, f + f3, f18, color3);
            return;
        }
        this.a(hudRenderEvent.j(), this.ih, f + f3, f18, color3, f17, 8.0f, Fonts.ROUNDBOLD);
    }

    private void a(HudNotification hudNotification) {
        String string = hudNotification.dB();
        boolean bl = hudNotification.isEnabled();
        Color color = hudNotification.dF() != null ? hudNotification.dF() : (bl ? this.ij : this.ik);
        boolean bl2 = !Objects.equals(this.in, string);
        boolean bl3 = !this.ip || this.io != bl;
        boolean bl4 = !this.ip || !Objects.equals(this.im, color);
        this.ih.cF(string);
        if (bl2 || bl3 || bl4) {
            Color color2;
            float f = class_3532.method_15363((float)this.ii.rk(), (float)0.0f, (float)1.0f);
            this.il = color2 = ColorUtils.mix(this.il, this.im, f);
            this.im = color;
            this.ii.ae(false);
            this.ii.i(true);
        }
        this.in = string;
        this.io = bl;
        this.ip = true;
    }

    private void cA() {
        this.ip = false;
        this.in = "";
        this.io = false;
        this.il = this.ij;
        this.im = this.ij;
        this.ii.ae(false);
    }

    private float cB() {
        String string = this.ih.rh();
        float f = Fonts.ROUNDBOLD.getWidth(string, 8.0f);
        if (!this.ih.rg()) {
            return f;
        }
        float f2 = Fonts.ROUNDBOLD.getWidth(this.ih.ri(), 8.0f);
        return Math.max(f, f2);
    }

    private void a(HudRenderEvent hudRenderEvent, float f, float f2, float f3, float f4, float f5, boolean bl, float f6) {
        if (f6 <= 0.01f) {
            return;
        }
        float f7 = f3 - f4 * 2.0f;
        float f8 = f2 + (f3 - f7) / 2.0f;
        int n = (int)(255.0f * f6);
        Color color = this.i(n);
        Color color2 = this.i((int)(255.0f * this.hR.rk() * f6));
        if (!bl) {
            float f9 = (int)(255.0f * f6);
            if (DynamicIslandHud.aiG.field_1724 instanceof class_742) {
                float f10 = f7 / 2.5f;
                Render2D.drawTexture(hudRenderEvent.j().method_51448(), f + f4, f8, f7, f7, f10, 0.125f, 0.125f, 0.125f, 0.125f, DynamicIslandHud.aiG.field_1724.method_52814().comp_1626(), new Color(255, 255, 255, (int)f9));
            } else {
                Color color3 = ColorUtils.getGlobalColor((int)class_3532.method_15363((float)(90.0f + 80.0f * f6), (float)0.0f, (float)255.0f));
                Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f + f4, f8, f7, f7, f7 / 2.0f, color3);
            }
            String string = this.a(DynamicIslandHud.aiG.field_1724 != null ? this.cE() : "Celebression", f5, 8.0f, Fonts.ROUNDBOLD);
            float f11 = f2 + (f3 - Fonts.ROUNDBOLD.getHeight(8.0f)) / 2.0f;
            Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(8.0f), string, f + f3, f11, color);
        } else {
            float f12 = (int)(255.0f * this.hR.rk() * f6);
            String string = ServerDetector.sl().isEmpty() ? "30" : ServerDetector.sl();
            float f13 = f7 / 2.0f;
            float f14 = f + f4;
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f14, f8, f7, f7, f13, new Color(255, 0, 0, (int)f12));
            float f15 = Fonts.SEMIBOLD.getWidth(string, 7.0f);
            float f16 = f + f4 + f7 / 2.0f - f15 / 2.0f;
            Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.SEMIBOLD.getFont(7.0f), string, f16, f2 + (f3 - Fonts.SEMIBOLD.getHeight(7.0f)) / 2.0f, color2);
            String string2 = this.a(class_1074.method_4662((String)"elements.dynamicisland.pvp", (Object[])new Object[0]), f5, 8.0f, Fonts.ROUNDBOLD);
            float f17 = f2 + (f3 - Fonts.ROUNDBOLD.getHeight(8.0f)) / 2.0f;
            Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(8.0f), string2, f + f3, f17, color);
        }
    }

    private void a(HudRenderEvent hudRenderEvent, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        String string = MathUtils.rU();
        float f7 = Fonts.ROUNDBOLD.getWidth(string, 8.0f);
        int n = color.getRed();
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(8.0f), string, f - f6 - f7, f2 + (f4 - Fonts.ROUNDBOLD.getHeight(8.0f)) / 2.0f, color);
        float f8 = f2 + (f4 - Fonts.ICONSNEW.getHeight(8.0f)) / 2.0f;
        float f9 = f + f3 + f6;
        Color color2 = new Color(n, n, n, (int)(255.0f * this.hQ.rk()));
        Color color3 = new Color(n, n, n, (int)(255.0f * this.hQ.rm()));
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ICONSNEW.getFont(8.0f), "O", f9, f8, color2);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ICONSNEW.getFont(8.0f), "I", f9, f8, color3);
    }

    private Color a(float f, float f2, float f3, float f4, float f5, String string) {
        boolean bl;
        float f6 = Fonts.ROUNDBOLD.getWidth(MathUtils.rU(), 8.0f);
        float f7 = Fonts.ICONSNEW.getWidth("I", 8.0f);
        float f8 = f + f3 + f5 + 6.0f + f7 / 2.0f;
        float f9 = f2 + f4 / 2.0f - 6.0f;
        float f10 = f - f5 - f6 / 2.0f - 6.0f;
        float f11 = f2 + f4 / 2.0f - 6.0f;
        float f12 = aiG.method_22683().method_4486();
        float f13 = aiG.method_22683().method_4502();
        f8 = MathUtils.k(f8, 0.0f, f12);
        f9 = MathUtils.k(f9, 0.0f, f13);
        f10 = MathUtils.k(f10, 0.0f, f12);
        f11 = MathUtils.k(f11, 0.0f, f13);
        long l = System.currentTimeMillis();
        boolean bl2 = bl = l - this.is > 250L;
        if (bl) {
            this.it = ColorUtils.picker(f8, f9);
            this.iu = ColorUtils.picker(f10, f11);
            this.is = l;
        }
        float f14 = (this.b(this.it) + this.b(this.iu)) * 0.5f;
        float f15 = class_3532.method_15363((float)f14, (float)0.0f, (float)1.0f);
        float f16 = this.g(7.5f, this.iZ);
        this.ir = class_3532.method_16439((float)f16, (float)this.ir, (float)f15);
        if (this.iq) {
            if (this.ir > 0.58f) {
                this.iq = false;
            }
        } else if (this.ir < 0.42000002f) {
            this.iq = true;
        }
        this.hU.i(this.iq);
        float f17 = this.hU.rk();
        if (f17 > 0.99f) {
            f17 = 1.0f;
        } else if (f17 < 0.01f) {
            f17 = 0.0f;
        }
        int n = class_3532.method_15340((int)Math.round(255.0f * f17), (int)0, (int)255);
        return new Color(n, n, n);
    }

    private Color i(int n) {
        int n2 = class_3532.method_15340((int)n, (int)0, (int)255);
        if (this.cn()) {
            return new Color(255, 255, 255, n2);
        }
        return ColorUtils.getTextColor(n2);
    }

    private float b(Color color) {
        return (0.299f * (float)color.getRed() + 0.587f * (float)color.getGreen() + 0.114f * (float)color.getBlue()) / 255.0f;
    }

    private float h(float f, float f2) {
        if (f <= f2) {
            return 0.0f;
        }
        long l = System.currentTimeMillis();
        float f3 = Math.max(0.0f, f - f2);
        long l2 = 500L;
        long l3 = 5000L;
        long l4 = Math.max(1L, (l3 - l2 * 2L) / 2L);
        long l5 = l2 + l4 + l2 + l4;
        long l6 = l % l5;
        if (l6 < l2) {
            return 0.0f;
        }
        if ((l6 -= l2) < l4) {
            return f3 * ((float)l6 / (float)l4);
        }
        if ((l6 -= l4) < l2) {
            return f3;
        }
        if ((l6 -= l2) < l4) {
            return f3 * (1.0f - (float)l6 / (float)l4);
        }
        return 0.0f;
    }

    private float cC() {
        float f = 6.0f;
        String string = this.hZ.rh();
        String string2 = this.ia.rh();
        float f2 = Fonts.SEMIBOLD.getWidth(string, f);
        float f3 = Fonts.SEMIBOLD.getWidth(string2, f);
        return f2 + f3 + 90.0f + 7.2f;
    }

    private void cD() {
        if (this.jd.isUnavailable()) {
            return;
        }
        this.jd.a(this.jc.dj(), this.jc.dk(), this.jc.dl());
    }

    private void a(class_332 class_3322, class_2960 class_29602, float f, float f2, float f3, Color color, boolean bl) {
        if (class_3322 == null || class_29602 == null || f3 <= 0.0f) {
            return;
        }
        float f4 = Math.max(1.0f, (float)Math.round(f3));
        float f5 = Math.round(f);
        float f6 = Math.round(f2);
        class_1044 class_10442 = aiG.method_1531().method_4619(class_29602);
        if (class_10442 != null) {
            class_10442.method_4527(bl, false);
        }
        float f7 = class_3532.method_15363((float)(f4 * 0.22f), (float)1.6f, (float)4.2f);
        Render2D.drawTexture(class_3322.method_51448(), f5, f6, f4, f4, f7, class_10442, color);
    }

    private void a(class_332 class_3322, TimedProgress timedProgress, float f, float f2, Color color, float f3, float f4) {
        this.a(class_3322, timedProgress, f, f2, color, f3, f4, Fonts.SEMIBOLD);
    }

    private void a(class_332 class_3322, TimedProgress timedProgress, float f, float f2, Color color, float f3, float f4, Font font) {
        float f5;
        if (timedProgress == null) {
            return;
        }
        boolean bl = timedProgress.rg();
        float f6 = timedProgress.progress();
        String string = timedProgress.rh();
        if (!bl) {
            Render2D.drawFont(class_3322.method_51448(), font.getFont(f4), string, f, f2, color);
            return;
        }
        float f7 = Math.max(0.0f, Math.min(1.0f, f6));
        int n = color.getAlpha();
        int n2 = Math.round((float)n * (1.0f - f7));
        int n3 = Math.round((float)n * f7);
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n2);
        Color color3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n3);
        float f8 = f2 - f3 * f7;
        float f9 = f2 + f3 * (1.0f - f7);
        String string2 = timedProgress.ri();
        String string3 = timedProgress.rh();
        int n4 = EasingMath.w(string2, string3);
        int n5 = EasingMath.d(string2, string3, n4);
        String string4 = EasingMath.a(string3, 0, n4);
        String string5 = EasingMath.a(string2, n4, string2.length() - n5);
        String string6 = EasingMath.a(string3, n4, string3.length() - n5);
        String string7 = EasingMath.a(string3, string3.length() - n5, string3.length());
        float f10 = font.getWidth(string4, f4);
        float f11 = font.getWidth(string6, f4);
        float f12 = font.getWidth(string5, f4);
        float f13 = f5 = f + f10;
        float f14 = f5 + (f11 - f12) / 2.0f;
        Render2D.drawFont(class_3322.method_51448(), font.getFont(f4), string4, f, f2, color);
        Render2D.drawFont(class_3322.method_51448(), font.getFont(f4), string7, f5 + f11, f2, color);
        Render2D.drawFont(class_3322.method_51448(), font.getFont(f4), string5, f14, f8, color2);
        Render2D.drawFont(class_3322.method_51448(), font.getFont(f4), string6, f13, f9, color3);
    }

    private String a(String string, float f, float f2, Font font) {
        char c;
        float f3;
        if (string == null) {
            return "";
        }
        if (font.getWidth(string, f2) <= f) {
            return string;
        }
        String string2 = "...";
        float f4 = font.getWidth(string2, f2);
        if (f4 >= f) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        float f5 = 0.0f;
        for (int i = 0; i < string.length() && !(f5 + (f3 = font.getWidth(String.valueOf(c = string.charAt(i)), f2)) + f4 > f); ++i) {
            stringBuilder.append(c);
            f5 += f3;
        }
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    private String a(String string, float f, float f2) {
        String string2 = this.a(string, f, f2, Fonts.SEMIBOLD);
        if (!string2.isEmpty() || string == null || string.isEmpty()) {
            return string2;
        }
        return this.b(string, f, f2, Fonts.SEMIBOLD);
    }

    private List<String> a(String string, float f, float f2, int n) {
        if (string == null) {
            return List.of("");
        }
        if (f <= 0.0f) {
            return List.of("");
        }
        String string2 = string + "\u0000" + f + "\u0000" + f2 + "\u0000" + n;
        List<String> list = this.jf.get(string2);
        if (list != null) {
            return list;
        }
        List<String> list2 = this.b(string, f, f2, n);
        if (this.jf.size() > 64) {
            this.jf.clear();
        }
        this.jf.put(string2, list2);
        return list2;
    }

    private List<String> b(String string, float f, float f2, int n) {
        int n2 = Math.max(1, n);
        ArrayList<String> arrayList = new ArrayList<String>();
        String string2 = string.trim();
        if (string2.isEmpty()) {
            return List.of("");
        }
        StringBuilder stringBuilder = new StringBuilder();
        float f3 = 0.0f;
        float f4 = Fonts.SEMIBOLD.getWidth(" ", f2);
        String[] stringArray = string2.split("\\s+");
        for (int i = 0; i < stringArray.length; ++i) {
            float f5;
            String string3 = stringArray[i];
            float f6 = Fonts.SEMIBOLD.getWidth(string3, f2);
            float f7 = f5 = stringBuilder.length() == 0 ? f6 : f3 + f4 + f6;
            if (f5 <= f) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(' ');
                }
                stringBuilder.append(string3);
                f3 = f5;
                continue;
            }
            if (stringBuilder.length() > 0) {
                arrayList.add(stringBuilder.toString());
                if (arrayList.size() >= n2) {
                    arrayList.set(arrayList.size() - 1, this.a((String)arrayList.get(arrayList.size() - 1), f, f2));
                    return arrayList;
                }
                stringBuilder.setLength(0);
                f3 = 0.0f;
            }
            if (f6 <= f) {
                stringBuilder.append(string3);
                f3 = f6;
                continue;
            }
            int n3 = 0;
            while (n3 < string3.length()) {
                char c;
                float f8;
                StringBuilder stringBuilder2 = new StringBuilder();
                float f9 = 0.0f;
                while (n3 < string3.length() && !(f9 + (f8 = Fonts.SEMIBOLD.getWidth(String.valueOf(c = string3.charAt(n3)), f2)) > f)) {
                    stringBuilder2.append(c);
                    f9 += f8;
                    ++n3;
                }
                if (stringBuilder2.length() == 0 && n3 < string3.length()) {
                    stringBuilder2.append(string3.charAt(n3));
                    ++n3;
                }
                arrayList.add(stringBuilder2.toString());
                if (arrayList.size() < n2) continue;
                arrayList.set(arrayList.size() - 1, this.a((String)arrayList.get(arrayList.size() - 1), f, f2));
                return arrayList;
            }
        }
        String string4 = stringBuilder.toString().trim();
        if (!string4.isEmpty() && arrayList.size() < n2) {
            arrayList.add(string4);
        }
        if (arrayList.isEmpty()) {
            arrayList.add("");
        }
        return arrayList;
    }

    private String b(String string, float f, float f2, Font font) {
        char c;
        float f3;
        if (string == null) {
            return "";
        }
        if (font.getWidth(string, f2) <= f) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        float f4 = 0.0f;
        for (int i = 0; i < string.length() && !(f4 + (f3 = font.getWidth(String.valueOf(c = string.charAt(i)), f2)) > f); ++i) {
            stringBuilder.append(c);
            f4 += f3;
        }
        return stringBuilder.toString();
    }

    private String cE() {
        if (DynamicIslandHud.aiG.field_1724 == null) {
            return "Celebression";
        }
        return StreamerTextSanitizer.dr(DynamicIslandHud.aiG.field_1724.method_7334().getName());
    }
}
