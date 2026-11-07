package ru.method.celebression.modules.impl.render.cosmetic;

import java.awt.Color;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class CosmeticCardRenderer {
    private static final int Qz = 24;
    private int QA = -1;
    private boolean draggingHue;
    private boolean QB;
    private boolean QC;
    private float hue;
    private float saturation = 1.0f;
    private float value = 1.0f;
    private boolean hsvInitialized;

    public void am(int n) {
        if (this.QA != n) {
            this.QA = n;
            this.hsvInitialized = false;
        }
    }

    public int lL() {
        return this.QA;
    }

    public boolean lM() {
        return this.draggingHue || this.QB;
    }

    public boolean lN() {
        if (!this.QC) {
            return false;
        }
        this.QC = false;
        return true;
    }

    public void b(int n, int n2, float f, float f2, float f3) {
        float f4 = f2 + 22.0f;
        float f5 = 10.0f;
        float f6 = f4 + f5 + 5.0f;
        float f7 = 7.0f;
        float f8 = f + 4.0f;
        float f9 = f3 - 8.0f;
        if (this.draggingHue) {
            this.a(n, f8, f9);
        }
        if (this.QB) {
            this.b(n, f8, f9);
        }
    }

    public void a(class_332 class_3322, float f, float f2, float f3, int n, int n2, int n3, String string) {
        this.lQ();
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(6.8f), class_1074.method_4662((String)string, (Object[])new Object[0]), f + 3.0f, f2 + 3.0f, ColorUtils.getTextColor(n3));
        float f4 = 14.0f;
        float f5 = f + f3 - f4 - 6.0f;
        float f6 = f2;
        Render2D.drawRoundedRect(class_3322.method_51448(), f5, f6, f4, f4, 4.0f, new Color(this.QA, true));
        Render2D.drawBorder(class_3322.method_51448(), f5, f6, f4, f4, 4.0f, 0.55f, new ColorRGBA(ColorUtils.alpha(Color.WHITE, 90)));
        float f7 = f + 4.0f;
        float f8 = f2 + 22.0f;
        float f9 = f3 - 8.0f;
        float f10 = 10.0f;
        this.a(class_3322, f7, f8, f9, f10, n3);
        float f11 = f8 + f10 + 5.0f;
        float f12 = 7.0f;
        this.b(class_3322, f7, f11, f9, f12, n3);
        if (this.draggingHue) {
            this.a(n, f7, f9);
        }
        if (this.QB) {
            this.b(n, f7, f9);
        }
    }

    public float lO() {
        return 48.0f;
    }

    public boolean a(float f, float f2, float f3, double d, double d2, int n) {
        if (n != 0) {
            return false;
        }
        float f4 = f + 4.0f;
        float f5 = f2 + 22.0f;
        float f6 = f3 - 8.0f;
        float f7 = 10.0f;
        if (MathUtils.c(f4, f5, f6, f7, (float)d, (float)d2)) {
            this.draggingHue = true;
            this.a((int)d, f4, f6);
            return true;
        }
        float f8 = f5 + f7 + 5.0f;
        float f9 = 7.0f;
        if (MathUtils.c(f4, f8, f6, f9, (float)d, (float)d2)) {
            this.QB = true;
            this.b((int)d, f4, f6);
            return true;
        }
        return false;
    }

    public boolean an(int n) {
        if (n != 0) {
            return false;
        }
        boolean bl = this.draggingHue || this.QB;
        this.draggingHue = false;
        this.QB = false;
        return bl;
    }

    public boolean ak(int n) {
        return false;
    }

    private void a(class_332 class_3322, float f, float f2, float f3, float f4, int n) {
        Render2D.drawRoundedRect(class_3322.method_51448(), f, f2, f3, f4, 3.0f, new Color(255, 255, 255, Math.round(30.0f * ((float)n / 255.0f))));
        float f5 = f3 / 24.0f;
        for (int i = 0; i < 24; ++i) {
            float f6 = f + (float)i * f5;
            float f7 = i == 23 ? f + f3 : f6 + f5;
            Color color = Color.getHSBColor((float)i / 24.0f, 1.0f, 1.0f);
            Color color2 = Color.getHSBColor((float)(i + 1) / 24.0f, 1.0f, 1.0f);
            Render2D.drawHorizontalGradient(class_3322.method_51448(), f6, f2, f7, f2 + f4, color, color2);
        }
        float f8 = f + this.hue * f3;
        Render2D.drawRoundedRect(class_3322.method_51448(), f8 - 1.5f, f2 - 1.0f, 3.0f, f4 + 2.0f, 1.5f, Color.WHITE);
    }

    private void b(class_332 class_3322, float f, float f2, float f3, float f4, int n) {
        Color color = Color.getHSBColor(this.hue, 1.0f, 1.0f);
        Color color2 = new Color(18, 20, 28, Math.round(220.0f * ((float)n / 255.0f)));
        Render2D.drawRoundedRect(class_3322.method_51448(), f, f2, f3, f4, 3.0f, color2);
        float f5 = f3 * 0.5f;
        Render2D.drawHorizontalGradient(class_3322.method_51448(), f, f2, f + f5, f2 + f4, Color.WHITE, color);
        Render2D.drawHorizontalGradient(class_3322.method_51448(), f + f5, f2, f + f3, f2 + f4, color, Color.BLACK);
        float f6 = this.value >= 0.999f ? this.saturation * 0.5f : 0.5f + (1.0f - this.value) * 0.5f;
        float f7 = f + f6 * f3;
        Render2D.drawRoundedRect(class_3322.method_51448(), f7 - 1.5f, f2 - 1.0f, 3.0f, f4 + 2.0f, 1.5f, Color.WHITE);
    }

    private void a(int n, float f, float f2) {
        this.hue = class_3532.method_15363((float)(((float)n - f) / f2), (float)0.0f, (float)1.0f);
        if (this.saturation < 0.15f) {
            this.saturation = 1.0f;
        }
        if (this.value < 0.15f) {
            this.value = 1.0f;
        }
        this.lP();
    }

    private void b(int n, float f, float f2) {
        float f3 = class_3532.method_15363((float)(((float)n - f) / f2), (float)0.0f, (float)1.0f);
        if (f3 <= 0.5f) {
            this.saturation = f3 * 2.0f;
            this.value = 1.0f;
        } else {
            this.saturation = 1.0f;
            this.value = 1.0f - (f3 - 0.5f) * 2.0f;
        }
        this.lP();
    }

    private void lP() {
        int n = Color.HSBtoRGB(this.hue, this.saturation, this.value) & 0xFFFFFF;
        this.QA = 0xFF000000 | n;
        this.QC = true;
    }

    private void lQ() {
        if (this.hsvInitialized) {
            return;
        }
        int n = this.QA & 0xFFFFFF;
        float[] fArray = Color.RGBtoHSB(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, null);
        this.hue = fArray[0];
        this.saturation = fArray[1];
        this.value = fArray[2];
        this.hsvInitialized = true;
    }
}

