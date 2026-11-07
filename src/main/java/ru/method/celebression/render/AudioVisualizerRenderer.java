package ru.method.celebression.render;

import java.awt.Color;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public final class AudioVisualizerRenderer {
    private final float[] mg = new float[]{0.1f, 1.05f, 2.1f, 2.95f};
    private final float[] mh = new float[]{0.25f, 0.25f, 0.25f, 0.25f};
    private long mi;

    public float n(float f) {
        float f2 = 3.0f * f * 0.95f;
        float f3 = 1.0f * f * 0.95f;
        return 4.0f * f2 + 3.0f * f3;
    }

    public void a(HudRenderEvent hudRenderEvent, Color color, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, boolean bl) {
        if (f7 <= 0.02f || f8 <= 0.01f) {
            return;
        }
        float f13 = f6 + f5 - f3;
        float f14 = 3.0f * f9 * 0.95f;
        float f15 = 1.0f * f9 * 0.95f;
        float f16 = this.n(f9);
        float f17 = f + f8 + 2.8f;
        float f18 = f13 - f11 - f16;
        if (bl) {
            f17 = f18;
        } else if (f17 > f18) {
            f17 = f18;
        }
        float f19 = (f4 - f3 * 2.0f) * 0.55f * f10 * 0.95f;
        float f20 = Math.max(2.0f, (f4 - f3 * 1.0f) * 0.65f * f10 * 0.95f);
        float f21 = f2 + Fonts.ROUNDBOLD.getHeight(8.0f) * 0.55f;
        float f22 = bl ? MathUtils.lerp(f21, f12, 0.35f) : f21;
        for (int i = 0; i < 4; ++i) {
            float f23 = this.mh[i];
            float f24 = 1.0f - Math.abs((float)i - 1.5f) * 0.18f;
            float f25 = Math.max(2.0f, Math.min(f20, f19 * (0.38f + f23 * 1.05f) * f24));
            float f26 = f17 + (float)i * (f14 + f15);
            float f27 = f22 - f25 / 2.0f;
            Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f26, f27, f14, f25, f14 * 0.4f, new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(200.0f * f7)));
        }
    }

    public void i(boolean bl) {
        long l = System.currentTimeMillis();
        if (this.mi == 0L) {
            this.mi = l;
            return;
        }
        float f = Math.max(1.0f, (float)(l - this.mi));
        this.mi = l;
        if (!bl) {
            for (int i = 0; i < this.mh.length; ++i) {
                this.mh[i] = MathUtils.lerp(this.mh[i], 0.22f, 0.16f);
            }
            return;
        }
        float f2 = 0.012f;
        float f3 = 0.16f;
        for (int i = 0; i < this.mg.length; ++i) {
            int n = i;
            this.mg[n] = this.mg[n] + f2 * f * (1.0f + (float)i * 0.15f);
            double d = Math.sin(this.mg[i]) + Math.sin((double)this.mg[i] * 1.35 + (double)0.9f) * 0.6 + Math.sin((double)this.mg[i] * 0.72 + (double)1.4f) * 0.4;
            float f4 = MathUtils.k((float)((d + 2.0) / 4.0), 0.12f, 1.0f);
            float f5 = (float)Math.pow(f4, 1.35f);
            this.mh[i] = MathUtils.lerp(this.mh[i], f5, f3);
        }
    }
}

