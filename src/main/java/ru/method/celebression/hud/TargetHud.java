package ru.method.celebression.hud;

import ru.method.celebression.animation.Animation;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_308;
import net.minecraft.class_332;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_408;
import net.minecraft.class_4587;
import net.minecraft.class_640;
import net.minecraft.class_742;
import net.minecraft.class_746;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.client.AllSkinsModule;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.ui.ClientTheme;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.input.KeyConstants;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.render.NotificationRenderer;
import ru.method.celebression.server.ServerDetector;
import ru.method.celebression.util.StreamerTextSanitizer;
import ru.method.celebression.ui.UiThemeController;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import unkw.visualtest.client.hud.VisualHud;

public class TargetHud
extends HudElement {
    private final AnimatedComponent kz = new AnimatedComponent(Easing.aiQ);
    private final AnimatedComponent kA = new AnimatedComponent(Easing.aiQ);
    private final AnimatedComponent kB = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent kC = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent kD = new AnimatedComponent(Easing.aiQ);
    private float kE = -1.0f;
    private float kF = 0.0f;
    private class_1657 kG = null;
    private class_1657 kH = null;
    private long kI = 0L;
    private final long kJ = 1000L;
    private final double kK = 3.5;
    private static final double kL = 0.75;
    private boolean kM = false;
    private float kN = 0.0f;
    private float kO = 0.0f;
    private float kP = 0.0f;
    private float kQ = 0.0f;
    private String kR = null;
    private long hD = 0L;
    private float kS = 0.0f;
    private float kT = 0.0f;
    private float kU = 0.0f;
    private float kV = 0.0f;
    private boolean kW = false;
    private String kX = null;
    private float kY = -1.0f;
    private String kZ = null;
    private long la = Long.MIN_VALUE;
    private String lb = null;
    private final List<class_1799> lc = new ArrayList<class_1799>();
    private long ld = 0L;
    private class_1657 le = null;
    private static final long lf = 100L;
    private class_1657 lg;
    private long lh;

    public TargetHud() {
        super("TargetHud");
    }

    private boolean d(class_1657 class_16572) {
        return class_16572 == null || class_16572.method_5767() || class_16572.method_6059(class_1294.field_5905);
    }

    private boolean e(class_1657 class_16572) {
        if (class_16572 == null) {
            return false;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getFriendManager() == null) {
            return false;
        }
        GameProfile gameProfile = class_16572.method_7334();
        if (gameProfile == null || gameProfile.getName() == null) {
            return false;
        }
        return celebression.getFriendManager().av(gameProfile.getName());
    }

    private static String m(float f) {
        if (f < 1000.0f) {
            return String.format("%.1f", Float.valueOf(f));
        }
        if (f < 1000000.0f) {
            return String.format("%.1fK", Float.valueOf(f / 1000.0f));
        }
        return String.format("%.1fM", Float.valueOf(f / 1000000.0f));
    }

    private class_1657 cW() {
        class_3966 class_39662;
        class_1297 class_12972;
        if (TargetHud.fullNullCheck()) {
            return null;
        }
        class_239 class_2392 = TargetHud.aiG.field_1765;
        if (class_2392 != null && class_2392.method_17783() == class_239.class_240.field_1331 && (class_12972 = (class_39662 = (class_3966)class_2392).method_17782()) instanceof class_1657 && class_12972 != TargetHud.aiG.field_1724) {
            class_1657 class_16572 = (class_1657)class_12972;
            if (this.d(class_16572)) {
                return null;
            }
            double d = TargetHud.aiG.field_1724.method_5739(class_12972);
            if (d <= 3.5) {
                return class_16572;
            }
        }
        return this.cX();
    }

    private class_1657 cX() {
        long l = System.currentTimeMillis();
        if (l - this.lh < 120L) {
            return this.lg;
        }
        this.lh = l;
        this.lg = this.cY();
        return this.lg;
    }

    private class_1657 cY() {
        if (TargetHud.aiG.field_1687 == null || TargetHud.aiG.field_1724 == null) {
            return null;
        }
        class_243 class_2432 = TargetHud.aiG.field_1724.method_33571();
        class_243 class_2433 = TargetHud.aiG.field_1724.method_5828(1.0f);
        class_243 class_2434 = class_2432.method_1019(class_2433.method_1021(3.5));
        class_1657 class_16572 = null;
        double d = Double.MAX_VALUE;
        for (class_1657 class_16573 : TargetHud.aiG.field_1687.method_18456()) {
            double d2;
            Optional optional;
            if (class_16573 == TargetHud.aiG.field_1724 || this.d(class_16573) || this.e(class_16573) || (double)TargetHud.aiG.field_1724.method_5739((class_1297)class_16573) > 3.5 || (optional = class_16573.method_5829().method_1014(0.75).method_992(class_2432, class_2434)).isEmpty() || !((d2 = ((class_243)optional.get()).method_1025(class_2432)) < d)) continue;
            d = d2;
            class_16572 = class_16573;
        }
        return class_16572 != null && this.a(class_2432, class_16572) ? class_16572 : null;
    }

    private boolean a(class_243 class_2432, class_1657 class_16572) {
        class_243 class_2433 = class_16572.method_5829().method_1005();
        class_3965 class_39652 = TargetHud.aiG.field_1687.method_17742(new class_3959(class_2432, class_2433, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)TargetHud.aiG.field_1724));
        return class_39652 == null || class_39652.method_17783() != class_239.class_240.field_1332 || class_39652.method_17784().method_1025(class_2432) >= class_2433.method_1025(class_2432) - 0.001;
    }

    private boolean cZ() {
        return aiG != null && TargetHud.aiG.field_1755 instanceof class_408;
    }

    private boolean da() {
        if (HudEditorScreen.mb() || this.cZ()) {
            class_1657 class_16572 = this.cW();
            if (class_16572 != null && this.e(class_16572)) {
                this.kH = null;
                this.kG = null;
                return false;
            }
            if (class_16572 != null && !this.d(class_16572)) {
                this.kH = class_16572;
                return true;
            }
            class_746 class_7462 = TargetHud.aiG.field_1724;
            if (class_7462 == null || this.d((class_1657)class_7462)) {
                this.kH = null;
                return false;
            }
            this.kH = class_7462;
            return true;
        }
        class_1657 class_16573 = this.cW();
        long l = System.currentTimeMillis();
        if (class_16573 != null) {
            if (this.e(class_16573)) {
                this.kH = null;
                this.kG = null;
                return false;
            }
            if (this.d(class_16573)) {
                this.kH = null;
                return false;
            }
            this.kG = class_16573;
            this.kH = class_16573;
            this.kI = l;
            return true;
        }
        if (this.kG != null) {
            if (l - this.kI < 1000L && !this.kG.method_31481() && this.kG.method_5805() && (double)TargetHud.aiG.field_1724.method_5739((class_1297)this.kG) <= 3.5 && !this.d(this.kG) && !this.e(this.kG)) {
                this.kH = this.kG;
                return true;
            }
            this.kG = null;
            return false;
        }
        return false;
    }

    private Color db() {
        ClientTheme clientTheme = UiThemeController.oX();
        return clientTheme != null ? clientTheme.pf() : ColorUtils.getBackgroundColor();
    }

    private Color k(int n) {
        if (!HudThemeRenderer.active()) {
            return ColorUtils.safeColor(18, 18, 21, n);
        }
        Color color = this.db();
        return ColorUtils.safeColor(color.getRed(), color.getGreen(), color.getBlue(), n);
    }

    private Color l(int n) {
        if (!HudThemeRenderer.active()) {
            return ColorUtils.safeColor(255, 255, 255, n);
        }
        ClientTheme clientTheme = UiThemeController.oX();
        Color color = clientTheme != null ? clientTheme.pg() : ColorUtils.getTextColor();
        return ColorUtils.safeColor(color.getRed(), color.getGreen(), color.getBlue(), n);
    }

    private Color m(int n) {
        return this.f(n, 0.16f);
    }

    private Color f(int n, float f) {
        Color color = HudThemeRenderer.active() ? this.db() : ColorUtils.safeColor(18, 18, 21, 255);
        boolean bl = (0.299f * (float)color.getRed() + 0.587f * (float)color.getGreen() + 0.114f * (float)color.getBlue()) / 255.0f > 0.6f;
        Color color2 = ColorUtils.mix(color, bl ? Color.BLACK : Color.WHITE, f);
        return ColorUtils.safeColor(color2.getRed(), color2.getGreen(), color2.getBlue(), n);
    }

    private void a(class_4587 class_45872, class_332 class_3322, class_1799 class_17992, float f, float f2, float f3, float f4, float f5, int n, boolean bl) {
        Color color = this.f(Math.round(235.0f * f5), bl ? 0.42f : 0.16f);
        Render2D.drawRoundedRect(class_45872, f, f2, f3, f3, 3.5f, color);
        Color color2 = HudThemeRenderer.active() ? ColorUtils.alpha(ColorUtils.getPrimaryColor(), Math.round(170.0f * f5)) : ColorUtils.safeColor(255, 255, 255, Math.round(55.0f * f5));
        Render2D.drawBorder(class_45872, f, f2, f3, f3, 3.5f, 1.0f, 2.0f, color2);
        float f6 = 16.0f * f4;
        float f7 = f + (f3 - f6) / 2.0f;
        float f8 = f2 + (f3 - f6) / 2.0f;
        class_45872.method_22903();
        class_45872.method_46416(f7, f8, 200.0f);
        class_45872.method_22905(f4, f4, 1.0f);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_3322.method_51427(class_17992, 0, 0);
        class_45872.method_22909();
        if (class_17992.method_7963()) {
            float f9 = 1.0f - (float)class_17992.method_7919() / (float)class_17992.method_7936();
            int n2 = Math.max(0, Math.min(100, Math.round(f9 * 100.0f)));
            boolean bl2 = HudThemeRenderer.isLightTheme();
            Color color3 = n2 < 25 ? (bl2 ? new Color(198, 40, 40) : new Color(255, 108, 108)) : (n2 < 50 ? (bl2 ? new Color(176, 116, 0) : new Color(255, 211, 82)) : (bl2 ? new Color(34, 138, 60) : new Color(108, 221, 122)));
            String string = n2 + "%";
            float f10 = 4.6f;
            float f11 = Fonts.MEDIUM.getWidth(string, f10);
            Render2D.drawFont(class_45872, Fonts.MEDIUM.getFont(f10), string, f + f3 / 2.0f - f11 / 2.0f, f2 + f3 + 1.5f, ColorUtils.safeColor(color3.getRed(), color3.getGreen(), color3.getBlue(), n));
        }
    }

    private Color a(class_1657 class_16572, int n) {
        int n2 = Math.max(0, Math.min(255, n));
        if (class_16572 instanceof class_1309) {
            class_1657 class_16573 = class_16572;
            if (class_16573.field_6235 > 0) {
                float f = Math.max(0.0f, Math.min(10.0f, (float)class_16573.field_6235));
                float f2 = f / 10.0f;
                float f3 = Math.max(0.0f, Math.min(1.0f, this.kC.a(f2, 300L)));
                int n3 = 255;
                float f4 = 255.0f - 105.0f * f3;
                float f5 = 255.0f - 105.0f * f3;
                int n4 = Math.max(0, Math.min(255, Math.round(f4)));
                int n5 = Math.max(0, Math.min(255, Math.round(f5)));
                return ColorUtils.safeColor(n3, n4, n5, n2);
            }
        }
        this.kC.a(0.0f, 300L);
        return ColorUtils.safeColor(255, 255, 255, n2);
    }

    private class_2960 f(class_1657 class_16572) {
        class_640 class_6402;
        if (class_16572 == null) {
            return null;
        }
        class_2960 class_29602 = AllSkinsModule.it();
        if (class_29602 != null) {
            return class_29602;
        }
        if (aiG.method_1562() != null && (class_6402 = aiG.method_1562().method_2871(class_16572.method_5667())) != null && class_6402.method_52810() != null) {
            return class_6402.method_52810().comp_1626();
        }
        if (class_16572 instanceof class_742) {
            class_742 clientPlayer = (class_742)class_16572;
            return clientPlayer.method_52814().comp_1626();
        }
        return null;
    }

    private void a(HudRenderEvent hudRenderEvent, class_1657 class_16572, float f, float f2, float f3, float f4, Color color) {
        class_2960 class_29602 = this.f(class_16572);
        if (class_29602 == null) {
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f, f2, f3, f3, f4, ColorUtils.alpha(ColorUtils.getPrimaryColor(), color.getAlpha()));
            return;
        }
        class_4587 class_45872 = hudRenderEvent.j().method_51448();
        Render2D.drawTexture(class_45872, f, f2, f3, f3, f4, 0.125f, 0.125f, 0.125f, 0.125f, class_29602, color);
        Render2D.drawTexture(class_45872, f, f2, f3, f3, f4, 0.625f, 0.125f, 0.125f, 0.125f, class_29602, color);
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        String string;
        int n;
        float f;
        Object object;
        if (TargetHud.fullNullCheck() || this.bL()) {
            return;
        }
        if (HudThemeRenderer.active() && HudThemeRenderer.cc() == HudThemeRenderer.ThemeMetrics.gz) {
            float f2 = this.getX();
            float f3 = this.getY();
            class_4587 class_45872 = hudRenderEvent.j().method_51448();
            float f4 = this.b(class_45872, f2, f3);
            float f5 = this.gn.rk() * this.bJ();
            class_45872.method_22903();
            class_45872.method_46416(f2, f3, 0.0f);
            class_45872.method_22905(f5, f5, 1.0f);
            class_45872.method_46416(-f2, -f3, 0.0f);
            boolean bl = VisualHud.cardTarget(hudRenderEvent.j(), aiG, Math.round(f2), Math.round(f3));
            class_45872.method_22909();
            this.b(class_45872);
            if (bl) {
                this.b(f2, f3, 144.0f, 45.0f, f4);
            }
            super.onRender2D(hudRenderEvent);
            return;
        }
        boolean bl = this.da();
        float f6 = this.kB.a(bl ? 1.0f : 0.0f, 200L);
        if (f6 <= 0.0f) {
            this.kH = null;
            this.kM = false;
            this.kW = false;
            return;
        }
        if (this.kH == null || this.d(this.kH) || this.e(this.kH)) {
            this.kH = null;
            this.kM = false;
            this.kW = false;
            return;
        }
        float f7 = this.getX();
        float f8 = this.getY();
        float f9 = 104.0f;
        float f10 = 30.0f;
        float f11 = 5.0f;
        float f12 = 20.0f;
        float f13 = 4.0f;
        float f14 = 4.0f;
        float f15 = 15.0f;
        float f16 = 4.0f;
        class_1657 class_16572 = this.kH;
        long l = System.currentTimeMillis();
        boolean bl2 = HudEditorScreen.mb();
        if (bl2 || class_16572 != this.le || l - this.ld >= 100L) {
            this.lc.clear();
            if (!class_16572.method_6079().method_7960()) {
                this.lc.add(class_16572.method_6079());
            }
            var inventoryItems = class_16572.method_31548().field_7548;
            for (int i = inventoryItems.size() - 1; i >= 0; --i) {
                class_1799 class_17992 = inventoryItems.get(i);
                if (class_17992.method_7960() || class_17992.method_7909() == class_1802.field_8162) continue;
                this.lc.add(class_17992);
            }
            this.ld = l;
            this.le = class_16572;
        }
        float f17 = this.lc.isEmpty() ? 0.0f : f15 + 10.0f;
        float f18 = f = f10 + f17;
        float f19 = MathUtils.ax(ServerDetector.a((class_1309)class_16572, false));
        float f20 = Math.max(class_16572.method_6063(), 1.0f);
        float f21 = MathUtils.ax(class_16572.method_6067());
        boolean bl3 = ServerDetector.sh();
        if (bl3) {
            f21 = 0.0f;
        }
        if (this.kE >= 0.0f && f19 < this.kE) {
            this.kF = 1.0f;
        }
        this.kE = f19;
        if (this.kF > 0.0f) {
            this.kF = Math.max(0.0f, this.kF - 0.05f);
        }
        float f22 = Math.min(f19, f20) / f20;
        float f23 = Math.max(0.0f, f21) / f20;
        float f24 = Math.max(0.0f, Math.min(1.0f, f6));
        int n2 = n = Math.max(0, Math.min(255, Math.round(255.0f * f24)));
        class_4587 class_45873 = hudRenderEvent.j().method_51448();
        class_332 class_3322 = hudRenderEvent.j();
        float f25 = this.b(class_45873, f7, f8);
        class_45873.method_22903();
        float[] fArray = this.bK();
        float f26 = this.gn.rk() * f24 * this.bJ();
        float f27 = f7 + f9 / 2.0f + fArray[0];
        float f28 = f8 + f18 / 2.0f + fArray[1];
        class_45873.method_46416(f27, f28, 0.0f);
        class_45873.method_22905(f26, f26, 1.0f);
        class_45873.method_46416(-f27, -f28, 0.0f);
        if (HudThemeRenderer.active()) {
            HudThemeRenderer.b(class_45873, f7, f8, f9, f, f11, f24);
        } else {
            Render2D.drawLiquidRectPreset(class_45873, f7, f8, f9, f, f9, f, new BorderRadius(10.0f), new ColorRGBA(ColorUtils.getGlassColor(255)), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        }
        if (this.kF > 0.01f) {
            Render2D.drawRoundedRect(class_45873, f7, f8, f9, f, f11, ColorUtils.safeColor(255, 50, 50, Math.round(55.0f * this.kF * f24)));
        }
        float f29 = f7 + f13;
        float f30 = f8 + (f10 - f12) / 2.0f;
        Render2D.drawRoundedRect(class_45873, f29, f30, f12, f12, 2.5f, this.m(Math.round(205.0f * f24)));
        Color color = this.a(class_16572, n);
        this.a(hudRenderEvent, class_16572, f29, f30, f12, 2.5f, color);
        float f31 = f29 + f12 + f14;
        float f32 = 7.6f;
        float f33 = 6.6f;
        float f34 = f7 + f9 - f13;
        float f35 = Math.max(0.0f, f34 - f31);
        String string2 = StreamerTextSanitizer.dr(class_16572.method_7334().getName());
        if (this.kZ != null && Objects.equals(string2, this.kX) && this.kY == f35) {
            string = this.kZ;
        } else {
            string = TargetHud.a(Fonts.ROUNDBOLD, string2, f32, f35);
            this.kX = string2;
            this.kY = f35;
            this.kZ = string;
        }
        Color color2 = this.l(n2);
        Render2D.drawFont(class_45873, Fonts.ROUNDBOLD.getFont(f32), string, f31, f8 + f13 + 1.0f, color2);
        float f36 = Math.max(0.0f, f19 + f21);
        long l2 = Math.round(f36 * 10.0f);
        if (this.lb == null || l2 != this.la) {
            this.lb = TargetHud.m(f36) + " HP";
            this.la = l2;
        }
        String string3 = this.lb;
        Render2D.drawFont(class_45873, Fonts.MEDIUM.getFont(f33), string3, f31, f8 + f13 + f32 + 3.0f, this.l(Math.round(205.0f * f24)));
        this.kR = string2;
        float f37 = f27 + (f31 - f27) * f26;
        float f38 = f28 + (f8 + f13 + 1.0f - f28) * f26;
        this.kN = this.c(f7, f37, f25);
        this.kO = this.d(f8, f38, f25);
        this.kP = Math.max(Fonts.ROUNDBOLD.getWidth(string, f32), 1.0f) * f26 * f25;
        this.kQ = Fonts.ROUNDBOLD.getHeight(f32) * f26 * f25;
        this.kM = f26 > 0.001f;
        this.kW = false;
        float f39 = 2.0f;
        float f40 = f31;
        float f41 = f30 + f12 - f39;
        float f42 = f34 - f31;
        float f43 = 1.0f;
        Render2D.drawRoundedRect(class_45873, f40, f41, f42, f39, f43, ColorUtils.safeColor(0, 0, 0, Math.round(180.0f * f24)));
        float f44 = this.kz.a(f42 * f22, 200L);
        float f45 = this.kA.a(f42 * f23, 200L);
        Color color3 = ColorUtils.alpha(ColorUtils.getPrimaryColor(), n2);
        Color color4 = ColorUtils.alpha(ColorUtils.getSecondaryColor(), n2);
        if (f44 > 0.5f) {
            Render2D.drawRoundedHorizontalGradient(class_45873, f40, f41, Math.max(0.5f, f44), f39, f43, color3, color4);
        }
        if (!bl3 && f45 > 0.5f) {
            Render2D.drawRoundedHorizontalGradient(class_45873, f40, f41, Math.max(0.5f, f45), f39, f43, ColorUtils.safeColor(255, 215, 0, n2), ColorUtils.safeColor(218, 165, 32, n2));
        }
        if (!this.lc.isEmpty()) {
            class_308.method_24211();
            float f46 = 0.72f * f24;
            float f47 = (float)this.lc.size() * f15 + (float)Math.max(0, this.lc.size() - 1) * f16;
            float f48 = f7 + (f9 - f47) / 2.0f;
            float f49 = f8 + f10 - 1.0f;
            for (int i = 0; i < this.lc.size(); ++i) {
                this.a(class_45873, class_3322, this.lc.get(i), f48 + (float)i * (f15 + f16), f49, f15, f46, f24, n2, true);
            }
        }
        class_45873.method_22909();
        this.b(class_45873);
        this.b(f7, f8, f9, f18, f25);
        super.onRender2D(hudRenderEvent);
    }

    @Override
    protected boolean c(int n, int n2) {
        boolean bl;
        if (this.kR == null || this.kR.isBlank()) {
            return false;
        }
        boolean bl2 = bl = this.kM && MathUtils.c(this.kN, this.kO, this.kP, this.kQ, n, n2);
        if (!bl && this.kW) {
            bl = MathUtils.c(this.kS, this.kT, this.kU, this.kV, n, n2);
        }
        if (!bl) {
            return false;
        }
        long l = System.currentTimeMillis();
        if (l - this.hD < 250L) {
            return true;
        }
        this.hD = l;
        TargetHud.aiG.field_1774.method_1455(this.kR);
        Celebression.getInstance().getNotifyManager().a(new NotificationRenderer(KeyConstants.akO, "\u041d\u0438\u043a \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0441\u043a\u043e\u043f\u0438\u0440\u043e\u0432\u0430\u043d", 1000L));
        return true;
    }
}
