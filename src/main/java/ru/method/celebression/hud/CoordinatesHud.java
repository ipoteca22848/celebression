package ru.method.celebression.hud;

import ru.method.celebression.animation.Animation;

import java.awt.Color;
import net.minecraft.class_1657;
import ru.method.celebression.Celebression;
import ru.method.celebression.ui.AnimatedComponent;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.input.KeyConstants;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.render.NotificationRenderer;
import ru.method.celebression.util.PlayerSpeedCalculator;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;

public class CoordinatesHud
extends HudElement {
    private final AnimatedComponent hv = new AnimatedComponent(Easing.aiZ);
    private final AnimatedComponent hw = new AnimatedComponent(Easing.aiZ);
    private boolean hx = false;
    private float hy = 0.0f;
    private float hz = 0.0f;
    private float hA = 0.0f;
    private float hB = 0.0f;
    private String hC = null;
    private long hD = 0L;

    public CoordinatesHud() {
        super("CoordinatesHud");
    }

    private boolean cm() {
        return HudEditorScreen.mb() || CoordinatesHud.aiG.field_1724 != null && CoordinatesHud.aiG.field_1687 != null;
    }

    @Override
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        if (CoordinatesHud.fullNullCheck() || this.bL()) {
            return;
        }
        boolean bl = this.cm();
        float f = this.hv.a(bl ? 1.0f : 0.0f, 200L);
        if (f <= 0.0f) {
            this.hx = false;
            return;
        }
        float f2 = this.getX();
        float f3 = this.getY();
        float f4 = 5.0f;
        float f5 = 7.5f;
        float f6 = 9.0f;
        String string = "J";
        Font font = Fonts.ICONSNEW;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        double d = 0.0;
        if (CoordinatesHud.aiG.field_1724 != null) {
            n = (int)Math.floor(CoordinatesHud.aiG.field_1724.method_23317());
            n2 = (int)Math.floor(CoordinatesHud.aiG.field_1724.method_23318());
            n3 = (int)Math.floor(CoordinatesHud.aiG.field_1724.method_23321());
            d = PlayerSpeedCalculator.u((class_1657)CoordinatesHud.aiG.field_1724);
        } else if (HudEditorScreen.mb()) {
            n = 1234;
            n2 = 64;
            n3 = -567;
            d = 4.4;
        }
        String string2 = " " + n + "  " + n2 + "  " + n3;
        String string3 = n + " " + n2 + " " + n3;
        int n4 = (int)Math.round(Math.abs(d) * 100.0);
        String string4 = (n4 % 100 < 10 ? "0" : "") + n4 % 100;
        String string5 = (d < 0.0 ? "-" : "") + n4 / 100 + "." + string4;
        String string6 = "BPS";
        float f7 = f5 - 1.0f;
        float f8 = Fonts.ROUNDBOLD.getWidth(string2, f5);
        float f9 = Fonts.ROUNDBOLD.getWidth(string5, f5);
        float f10 = Fonts.SEMIBOLD.getWidth(string6, f7);
        float f11 = 1.5f;
        float f12 = 4.0f;
        float f13 = 2.0f;
        float f14 = f12 + f13 * 2.0f;
        float f15 = f9 + f11 + f10;
        float f16 = f8 + f14 + f15;
        float f17 = font.getWidth(string, f6);
        float f18 = 4.0f;
        float f19 = 4.0f;
        float f20 = f17 + f18 + f16 + f4 * 2.0f + f19;
        float f21 = 17.0f;
        float f22 = this.hw.a(f20, 200L);
        boolean bl2 = HudEditorScreen.mb();
        if (bl2) {
            f22 = f20;
        }
        int n5 = Math.max(0, Math.min(255, Math.round(255.0f * f)));
        float f23 = this.b(hudRenderEvent.j().method_51448(), f2, f3);
        hudRenderEvent.j().method_51448().method_22903();
        float[] fArray = this.bK();
        float f24 = this.gn.rk() * f * this.bJ();
        float f25 = f2 + f22 / 2.0f + fArray[0];
        float f26 = f3 + f21 / 2.0f + fArray[1];
        hudRenderEvent.j().method_51448().method_46416(f25, f26, 0.0f);
        hudRenderEvent.j().method_51448().method_22905(f24, f24, 1.0f);
        hudRenderEvent.j().method_51448().method_46416(-f25, -f26, 0.0f);
        boolean bl3 = HudThemeRenderer.active();
        if (bl3) {
            HudThemeRenderer.b(hudRenderEvent.j().method_51448(), f2, f3, f22, f21, HudThemeRenderer.f(f22, f21), f);
        } else {
            Color color = ColorUtils.getGlassColor(255);
            Render2D.drawLiquidRectHud(hudRenderEvent.j().method_51448(), f2, f3, f22, f21, new BorderRadius(20.0f), new ColorRGBA(color), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        }
        float f27 = font.getHeight(f6);
        float f28 = Fonts.ROUNDBOLD.getHeight(f5);
        float f29 = f3 + f21 / 2.0f;
        float f30 = f29 - f27 / 2.0f;
        float f31 = f29 - f28 / 2.0f;
        Color color = ColorUtils.getGlobalColor();
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), n5);
        float f32 = f2 + f4;
        Render2D.drawFont(hudRenderEvent.j().method_51448(), font.getFont(f6), string, f32, f30, color);
        float f33 = f32 + f17 + f18;
        Color color2 = bl3 ? HudThemeRenderer.d(n5) : ColorUtils.getTextColor(n5);
        boolean bl4 = HudThemeRenderer.isLightTheme();
        Color color3 = bl4 ? new Color(90, 98, 112, Math.round(200.0f * f)) : new Color(190, 198, 212, Math.round(200.0f * f));
        Color color4 = bl4 ? new Color(110, 120, 134, Math.round(150.0f * f)) : new Color(180, 190, 204, Math.round(150.0f * f));
        float f34 = f33;
        this.a(hudRenderEvent.j(), Fonts.ROUNDBOLD, 7.5f, string2, f34, f31, color2);
        float f35 = f25 + (f34 - f25) * f24;
        float f36 = f26 + (f31 - f26) * f24;
        this.hy = this.c(f2, f35, f23);
        this.hz = this.d(f3, f36, f23);
        this.hA = f8 * f24 * f23;
        this.hB = f28 * f24 * f23;
        this.hC = string3;
        this.hx = f24 > 0.001f && this.hA > 0.001f && this.hB > 0.001f;
        Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), (f34 += f8) + f13, f31 + (f28 - f12) / 2.0f, f12, f12, f12 / 2.0f, color4);
        this.a(hudRenderEvent.j(), Fonts.ROUNDBOLD, 7.5f, string5, f34 += f14, f31, color2);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.SEMIBOLD.getFont(f7), string6, f34 += f9 + f11, f31 + 0.7f, color3);
        hudRenderEvent.j().method_51448().method_22909();
        this.b(hudRenderEvent.j().method_51448());
        this.b(f2, f3, f22, f21, f23);
        super.onRender2D(hudRenderEvent);
    }

    @Override
    protected boolean c(int n, int n2) {
        if (!this.hx) {
            return false;
        }
        if (!MathUtils.c(this.hy, this.hz, this.hA, this.hB, n, n2)) {
            return false;
        }
        if (this.hC == null || this.hC.isBlank()) {
            return true;
        }
        long l = System.currentTimeMillis();
        if (l - this.hD < 250L) {
            return true;
        }
        this.hD = l;
        CoordinatesHud.aiG.field_1774.method_1455(this.hC);
        Celebression.getInstance().getNotifyManager().a(new NotificationRenderer(KeyConstants.akO, "\u041a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b \u0441\u043a\u043e\u043f\u0438\u0440\u043e\u0432\u0430\u043d\u044b", 1000L));
        return true;
    }
}

