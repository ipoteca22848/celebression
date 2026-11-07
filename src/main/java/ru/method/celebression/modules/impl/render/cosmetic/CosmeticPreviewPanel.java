package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1074;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticCategory;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLoadout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticMathUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewPose;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticRenderUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticUiRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsService;
import ru.method.celebression.render.Rect;
import ru.method.celebression.modules.impl.render.cosmetic.WingTintPanel;

public final class CosmeticPreviewPanel {
    private final CosmeticPreviewPose QQ;
    private final WingTintPanel QR;

    public CosmeticPreviewPanel(CosmeticPreviewPose cosmeticPreviewPose, WingTintPanel wingTintPanel) {
        this.QQ = cosmeticPreviewPose;
        this.QR = wingTintPanel;
    }

    public PreviewGeometry a(class_332 class_3322, class_4587 class_45872, CosmeticMathUtils.Transform transform, CosmeticCategory cosmeticCategory, String string, float f, float f2, class_2960 class_29602, String string2, String string3) {
        boolean bl;
        float f3 = transform.Nm;
        float f4 = transform.lk();
        CosmeticUiRenderer.a(class_45872, transform, CosmeticUiRenderer.Style.Qw, class_1074.method_4662((String)"cosmetics.preview", (Object[])new Object[0]), f3 + transform.Nt, f4 + transform.Nt, 16.0f);
        Rect rect = CosmeticPreviewPanel.c(transform);
        CosmeticRenderUtils.c(class_45872, rect.x(), rect.y(), rect.lB(), rect.lC(), transform.NE);
        float f5 = 8.0f;
        float f6 = 40.0f;
        float f7 = rect.x() + f5;
        float f8 = rect.y() + f5;
        float f9 = rect.lB() - f5 * 2.0f;
        boolean bl2 = bl = cosmeticCategory == CosmeticCategory.Pr;
        if (bl) {
            float f10 = this.QR.ag(1.0f) + 8.0f;
            float f11 = rect.y() + rect.lC() - f5 - f10 + 8.0f;
            this.QR.a((int)f, (int)f2, f7, f11, f9, 1.0f);
        }
        if (class_29602 != null) {
            CosmeticUiRenderer.a(class_45872, f7, f8, f9, f6, class_29602, string2, string3, transform);
        }
        Rect rect2 = CosmeticPreviewPanel.a(rect, f8, f6, f5, bl);
        CosmeticRenderUtils.d(class_45872, rect2.x(), rect2.y(), rect2.lB(), rect2.lC());
        CosmeticRenderUtils.e(class_45872, rect2.x(), rect2.y(), rect2.lB(), rect2.lC());
        CosmeticSlot cosmeticSlot = cosmeticCategory.lG() ? cosmeticCategory.lD() : CosmeticSlot.dt;
        CosmeticsService cosmeticsService = Celebression.getInstance().getCosmeticsService();
        CosmeticLoadout cosmeticLoadout = cosmeticsService.aL();
        String string4 = string != null && cosmeticCategory.lG() ? string : cosmeticLoadout.f(cosmeticSlot);
        this.a(cosmeticLoadout, cosmeticSlot, string4, cosmeticCategory, bl);
        if (bl) {
            float f12 = this.QR.ag(1.0f) + 8.0f;
            float f13 = rect.y() + rect.lC() - f5 - f12 + 8.0f;
            this.QR.a(class_3322, f7, f13, f9, 1.0f, (int)f, (int)f2, 255);
            if (this.QR.lN()) {
                cosmeticsService.b(cosmeticSlot, this.QR.lL());
            }
        }
        return new PreviewGeometry(rect, rect2, bl ? CosmeticPreviewPanel.a(rect, f5) : null);
    }

    public CosmeticPreviewPose lV() {
        return this.QQ;
    }

    private void a(CosmeticLoadout cosmeticLoadout, CosmeticSlot cosmeticSlot, String string, CosmeticCategory cosmeticCategory, boolean bl) {
        this.QQ.i(cosmeticSlot);
        CosmeticLoadout cosmeticLoadout2 = cosmeticLoadout;
        if (cosmeticCategory.lG()) {
            cosmeticLoadout2 = cosmeticLoadout.b(cosmeticSlot, string);
        }
        if (bl) {
            int n;
            int n2 = n = this.QR.lM() ? this.QR.lL() : cosmeticLoadout.g(cosmeticSlot);
            if (!this.QR.lM()) {
                this.QR.am(n);
            }
            cosmeticLoadout2 = cosmeticLoadout2.a(cosmeticSlot, n);
        }
        this.QQ.e(cosmeticLoadout2);
    }

    public static Rect c(CosmeticMathUtils.Transform transform) {
        float f = transform.lk() + transform.Nz;
        return new Rect(transform.Nm + transform.Nt, f, transform.Nn - transform.Nt * 2.0f, transform.Ns - transform.Nz - transform.Nt);
    }

    public static Rect a(Rect rect, float f, float f2, float f3, boolean bl) {
        float f4 = rect.x() + f3;
        float f5 = f + f2 + f3;
        float f6 = rect.lB() - f3 * 2.0f;
        float f7 = bl ? 54.0f : f3;
        float f8 = Math.max(48.0f, rect.y() + rect.lC() - f3 - f7 - f5);
        return new Rect(f4, f5, f6, f8);
    }

    public static Rect a(Rect rect, float f) {
        float f2 = 46.0f;
        float f3 = rect.y() + rect.lC() - f - f2 + 8.0f;
        return new Rect(rect.x() + f, f3, rect.lB() - f * 2.0f, f2);
    }

    public static final class PreviewGeometry {
        private final Rect QS;
        private final Rect QT;
        private final Rect QU;

        public PreviewGeometry(Rect rect, Rect rect2, Rect rect3) {
            this.QS = rect;
            this.QT = rect2;
            this.QU = rect3;
        }

        @Override
        public final String toString() {
            return "PreviewGeometry[" + "viewport=" + this.QS + ", " + "stage=" + this.QT + ", " + "tint=" + this.QU + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.QS, this.QT, this.QU);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof PreviewGeometry other)) return false;
            return java.util.Objects.deepEquals(this.QS, other.QS)
                && java.util.Objects.deepEquals(this.QT, other.QT)
                && java.util.Objects.deepEquals(this.QU, other.QU);
        }

        public Rect lR() {
            return this.QS;
        }

        public Rect lW() {
            return this.QT;
        }

        public Rect lX() {
            return this.QU;
        }
    }
}

