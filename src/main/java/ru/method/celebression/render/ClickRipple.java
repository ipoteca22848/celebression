package ru.method.celebression.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;

public final class ClickRipple {
    private static final long Tq = 780L;
    private static final int Tr = 64;
    private static final float Ts = 2.4f;
    private final List<RippleState> Tt = new ArrayList<RippleState>(64);
    private float Tu = Float.NaN;
    private float Tv = Float.NaN;

    public void clear() {
        this.Tt.clear();
        this.Tu = Float.NaN;
        this.Tv = Float.NaN;
    }

    public void o(float f, float f2) {
        if (!Float.isFinite(f) || !Float.isFinite(f2) || Math.abs(f) > 100000.0f || Math.abs(f2) > 100000.0f) {
            this.Tu = Float.NaN;
            this.Tv = Float.NaN;
            return;
        }
        long l = System.currentTimeMillis();
        this.s(l);
        if (Float.isNaN(this.Tu)) {
            this.Tt.add(new RippleState(f, f2, l));
            this.Tu = f;
            this.Tv = f2;
            this.mL();
            return;
        }
        float f3 = f - this.Tu;
        float f4 = f2 - this.Tv;
        float f5 = (float)Math.hypot(f3, f4);
        if (f5 < 0.25f) {
            return;
        }
        int n = Math.max(1, Math.min(64, (int)Math.ceil(f5 / 2.4f)));
        for (int i = 1; i <= n; ++i) {
            float f6 = (float)i / (float)n;
            this.Tt.add(new RippleState(this.Tu + f3 * f6, this.Tv + f4 * f6, l));
        }
        this.Tu = f;
        this.Tv = f2;
        this.mL();
    }

    public void d(class_4587 class_45872) {
        float f;
        float f2;
        float f3;
        float f4;
        RippleState rippleState;
        int n;
        long l = System.currentTimeMillis();
        this.s(l);
        if (this.Tt.isEmpty()) {
            return;
        }
        Color color = ThemeTransition.accent();
        Color color2 = ColorUtils.getSecondaryColor();
        int n2 = this.Tt.size() - 1;
        for (n = 0; n < this.Tt.size(); ++n) {
            rippleState = this.Tt.get(n);
            float f5 = n2 <= 0 ? 1.0f : (float)n / (float)n2;
            f4 = class_3532.method_15363((float)((float)(l - rippleState.Ty) / 780.0f), (float)0.0f, (float)1.0f);
            f3 = (1.0f - f4) * (1.0f - f4);
            if (f3 <= 0.015f) continue;
            f2 = class_3532.method_16439((float)f5, (float)2.2f, (float)7.5f);
            f = f3 * class_3532.method_16439((float)f5, (float)0.18f, (float)0.92f);
            ClickRipple.a(class_45872, rippleState.Tw, rippleState.Tx, f2 * 2.6f, color, color2, f * 0.35f);
            ClickRipple.a(class_45872, rippleState.Tw, rippleState.Tx, f2 * 1.45f, color, color, f * 0.55f);
            if (n % 5 != 0 || !(f5 > 0.15f)) continue;
            float f6 = (float)(rippleState.Ty % 1000L) / 1000.0f;
            float f7 = (float)Math.sin((double)f6 * Math.PI * 2.0 + (double)((float)n * 0.7f)) * (2.5f + f5 * 2.0f);
            float f8 = (float)Math.cos((double)f6 * Math.PI * 2.0 + (double)((float)n * 0.55f)) * (2.0f + f5 * 1.5f);
            int n3 = Math.round(255.0f * f3 * class_3532.method_16439((float)f5, (float)0.2f, (float)0.85f));
            if (n3 <= 8) continue;
            Color color3 = ClickRipple.a(color, Color.WHITE, 0.65f, n3);
            Render2D.drawCircle(class_45872, rippleState.Tw + f7, rippleState.Tx + f8, 1.1f + f5 * 0.8f, color3);
        }
        for (n = 1; n < this.Tt.size(); ++n) {
            rippleState = this.Tt.get(n - 1);
            RippleState rippleState2 = this.Tt.get(n);
            f4 = (float)n / (float)n2;
            f3 = class_3532.method_15363((float)((float)(l - rippleState2.Ty) / 780.0f), (float)0.0f, (float)1.0f);
            f2 = (1.0f - f3) * (1.0f - f3);
            if (f2 <= 0.02f) continue;
            f = class_3532.method_16439((float)f4, (float)1.2f, (float)3.6f);
            int n4 = Math.round(255.0f * f2 * class_3532.method_16439((float)f4, (float)0.12f, (float)0.72f));
            if (n4 <= 6) continue;
            Color color4 = ClickRipple.a(color, color2, f4 * 0.45f, n4);
            Render2D.drawLine(class_45872, rippleState.Tw, rippleState.Tx, rippleState2.Tw, rippleState2.Tx, f, new ColorRGBA(color4));
        }
        RippleState rippleState3 = this.Tt.get(n2);
        float f9 = class_3532.method_15363((float)((float)(l - rippleState3.Ty) / 780.0f), (float)0.0f, (float)1.0f);
        float f10 = 1.0f - f9;
        if (f10 <= 0.02f) {
            return;
        }
        f4 = 0.86f + 0.14f * (float)Math.sin((double)l * 0.0085);
        this.a(class_45872, rippleState3.Tw, rippleState3.Tx, color, color2, f10, f4, l);
    }

    private void a(class_4587 class_45872, float f, float f2, Color color, Color color2, float f3, float f4, long l) {
        float f5 = 22.0f * f4;
        ClickRipple.a(class_45872, f, f2, f5, color, color2, f3 * 0.22f);
        ClickRipple.a(class_45872, f, f2, 14.0f * f4, color, color, f3 * 0.38f);
        int n = Math.round(255.0f * f3 * 0.55f);
        Color color3 = ClickRipple.a(color, Color.WHITE, 0.35f, n);
        Render2D.drawCircle(class_45872, f, f2, 5.8f * f4, color3);
        int n2 = Math.round(255.0f * f3);
        Color color4 = ClickRipple.a(color, Color.WHITE, 0.55f, n2);
        Render2D.drawCircle(class_45872, f, f2, 3.2f * f4, color4);
        int n3 = Math.round(255.0f * f3 * 0.95f);
        Render2D.drawCircle(class_45872, f, f2, 1.35f, new Color(255, 255, 255, n3));
        float f6 = (float)l * 0.0042f;
        for (int i = 0; i < 3; ++i) {
            float f7 = f6 + (float)i * 2.0943952f;
            float f8 = 9.5f * f4;
            float f9 = f + (float)Math.cos(f7) * f8;
            float f10 = f2 + (float)Math.sin(f7) * f8;
            int n4 = Math.round(255.0f * f3 * 0.42f);
            Render2D.drawCircle(class_45872, f9, f10, 1.4f, ClickRipple.a(color2, color, 0.5f, n4));
        }
    }

    private static void a(class_4587 class_45872, float f, float f2, float f3, Color color, Color color2, float f4) {
        int n;
        if (f4 <= 0.01f || f3 <= 0.5f) {
            return;
        }
        int n2 = Math.round(255.0f * class_3532.method_15363((float)f4, (float)0.0f, (float)1.0f));
        if (n2 <= 4) {
            return;
        }
        Color color3 = ClickRipple.a(color, color2, 0.35f, n2);
        float f5 = f3 / 2.0f;
        for (int i = n = 4; i >= 1; --i) {
            float f6 = f5 * ((float)i / (float)n);
            int n3 = Math.round((float)n2 * 0.55f / (float)i);
            if (n3 <= 0) continue;
            Render2D.drawCircle(class_45872, f, f2, f6, new Color(color3.getRed(), color3.getGreen(), color3.getBlue(), Math.min(255, n3)));
        }
    }

    private static Color a(Color color, Color color2, float f, int n) {
        f = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        int n2 = Math.round(class_3532.method_48781((float)f, (int)color.getRed(), (int)color2.getRed()));
        int n3 = Math.round(class_3532.method_48781((float)f, (int)color.getGreen(), (int)color2.getGreen()));
        int n4 = Math.round(class_3532.method_48781((float)f, (int)color.getBlue(), (int)color2.getBlue()));
        return new Color(n2, n3, n4, Math.max(0, Math.min(255, n)));
    }

    private void s(long l) {
        long l2 = l - 780L;
        this.Tt.removeIf(rippleState -> rippleState.Ty < l2);
    }

    private void mL() {
        while (this.Tt.size() > 64) {
            this.Tt.remove(0);
        }
    }

    static final class RippleState {
        final float Tw;
        final float Tx;
        final long Ty;

        RippleState(float f, float f2, long l) {
            this.Tw = f;
            this.Tx = f2;
            this.Ty = l;
        }

        @Override
        public final String toString() {
            return "RippleState[" + "x=" + this.Tw + ", " + "y=" + this.Tx + ", " + "timeMs=" + this.Ty + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.Tw, this.Tx, this.Ty);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof RippleState other)) return false;
            return java.util.Objects.deepEquals(this.Tw, other.Tw)
                && java.util.Objects.deepEquals(this.Tx, other.Tx)
                && java.util.Objects.deepEquals(this.Ty, other.Ty);
        }

        public float x() {
            return this.Tw;
        }

        public float y() {
            return this.Tx;
        }

        public long df() {
            return this.Ty;
        }
    }
}

