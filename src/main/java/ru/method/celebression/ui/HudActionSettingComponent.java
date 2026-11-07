package ru.method.celebression.ui;

import java.awt.Color;
import net.minecraft.class_332;
import ru.method.celebression.api.render.msdf.FontData;
import ru.method.celebression.ui.HudSettingComponent;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class HudActionSettingComponent
extends HudSettingComponent {
    private final String mn;
    private final Runnable mo;
    private final Color mp;
    private static final float mq = 10.0f;
    private static final float mr = 9.0f;
    private static final float ms = 6.0f;
    private static final float mt = 6.0f;
    private static final float mu = 6.0f;

    public HudActionSettingComponent(String string, String string2, Color color, Runnable runnable) {
        super(string);
        this.mn = string2;
        this.mo = runnable;
        this.mp = color;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        float f2;
        float f3 = f2 = this.jU != null ? this.jU.rk() : 1.0f;
        if (f2 <= 0.001f) {
            return;
        }
        int n3 = Math.max(0, Math.min(255, (int)(255.0f * f2)));
        Color color = new Color(this.mp.getRed(), this.mp.getGreen(), this.mp.getBlue(), n3);
        float f4 = Fonts.ICONSNEW.getWidth(this.mn, 10.0f);
        float f5 = Fonts.MEDIUM.getWidth(this.getName(), 9.0f);
        float f6 = f4 + 6.0f + f5;
        float f7 = this.x + 6.0f;
        float f8 = this.x + this.width - 6.0f - f6;
        float f9 = this.x + (this.width - f6) / 2.0f;
        float f10 = f8 < f7 ? f7 : MathUtils.k(f9, f7, f8);
        FontData.MetricsData metricsData = Fonts.ICONSNEW.font().getMetrics();
        FontData.MetricsData metricsData2 = Fonts.MEDIUM.font().getMetrics();
        float f11 = metricsData.lineHeight() * 10.0f;
        float f12 = metricsData2.lineHeight() * 9.0f;
        float f13 = this.y - 8.0f + (this.height - f11) / 2.0f + metricsData.ascender() * 10.0f;
        float f14 = this.y - 7.0f + (this.height - f12) / 2.0f + metricsData2.ascender() * 9.0f;
        class_3322.method_51448().method_22903();
        float f15 = this.x + this.width / 2.0f;
        float f16 = this.y + this.height / 2.0f;
        class_3322.method_51448().method_46416(f15, f16, 0.0f);
        class_3322.method_51448().method_22905(f2, f2, 1.0f);
        class_3322.method_51448().method_46416(-f15, -f16, 0.0f);
        boolean bl = MathUtils.c(this.x, this.y, this.width, this.height, n, n2);
        float f17 = (bl ? 0.34f : 0.16f) * f2;
        int n4 = Math.max(0, Math.min(255, Math.round(255.0f * f17)));
        Render2D.drawRoundedRect(class_3322.method_51448(), this.x + 3.0f, this.y + 2.0f, this.width - 6.0f, this.height - 4.0f, 5.0f, new Color(this.mp.getRed(), this.mp.getGreen(), this.mp.getBlue(), n4));
        Color color2 = bl ? new Color(Math.min(255, this.mp.getRed() + 30), Math.min(255, this.mp.getGreen() + 30), Math.min(255, this.mp.getBlue() + 30), n3) : color;
        Render2D.drawFont(class_3322.method_51448(), Fonts.ICONSNEW.getFont(10.0f), this.mn, f10, f13, color2);
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(9.0f), this.getName(), f10 + f4 + 6.0f, f14, color2);
        class_3322.method_51448().method_22909();
    }

    public static float e(String string, String string2) {
        if (string == null) {
            string = "";
        }
        if (string2 == null) {
            string2 = "";
        }
        float f = Fonts.ICONSNEW.getWidth(string2, 10.0f);
        float f2 = Fonts.MEDIUM.getWidth(string, 9.0f);
        return 12.0f + f + 6.0f + f2;
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (n != 0) {
            return;
        }
        if (!MathUtils.c(this.x, this.y, this.width, this.height, (float)d, (float)d2)) {
            return;
        }
        try {
            this.mo.run();
        }
        catch (Exception exception) {
        }
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
    }

    @Override
    public void keyReleased(int n, int n2, int n3) {
    }

    @Override
    public void charTyped(char c, int n) {
    }
}

