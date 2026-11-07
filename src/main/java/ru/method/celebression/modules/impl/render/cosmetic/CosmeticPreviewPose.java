package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_742;
import net.minecraft.class_746;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticDefinition;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEntityPreviewRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticIconRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLoadout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPreviewContext;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;

public final class CosmeticPreviewPose {
    private static final float MH = 195.0f;
    private CosmeticSlot dc = CosmeticSlot.dr;
    private CosmeticLoadout MI = new CosmeticLoadout();

    public void reset() {
        this.dc = CosmeticSlot.dr;
        this.MI = new CosmeticLoadout();
    }

    public void i(CosmeticSlot cosmeticSlot) {
        if (cosmeticSlot != null) {
            this.dc = cosmeticSlot;
        }
    }

    public void e(CosmeticLoadout cosmeticLoadout) {
        this.MI = cosmeticLoadout == null ? new CosmeticLoadout() : cosmeticLoadout;
    }

    public void a(class_332 class_3322, int n, int n2, int n3, int n4, float f) {
        this.a(class_3322, n, n2, n3, n4, f, true);
    }

        public void a(class_332 class_3322, int n, int n2, int n3, int n4, float f, boolean bl) {
        if (f <= 0.01f || n3 <= 0 || n4 <= 0) {
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102.field_1724 == null) {
            return;
        }
        class_746 class_7462 = class_3102.field_1724;
        BonePose bonePose = BonePose.f((class_1309)class_7462);
        CosmeticPreviewContext.a(new CosmeticPreviewContext.PreviewState(this.MI, this.dc, false, 0.0f, 0.0f, 0.016666668f, true));
        try {
            CosmeticPreviewPose.e((class_1309)class_7462);
            CosmeticEntityPreviewRenderer.a(class_3322, n, n2, n3, n4, (class_1309)class_7462, this.dc);
        }
        finally {
            bonePose.g((class_1309)class_7462);
            CosmeticPreviewContext.end();
        }
    }

    public void a(class_332 class_3322, int n, int n2, int n3, CosmeticDefinition cosmeticDefinition, CosmeticSlot cosmeticSlot, float f) {
        if (cosmeticDefinition == null || cosmeticDefinition.W() || f <= 0.01f || n3 <= 0) {
            return;
        }
        int n4 = CosmeticIconRenderer.a(-1, f);
        CosmeticIconRenderer.a(class_3322, n, n2, n3, cosmeticDefinition, cosmeticSlot, n4);
    }

    private static void e(class_1309 class_13092) {
        class_1657 class_16572;
        class_13092.method_36456(195.0f);
        class_13092.method_36457(0.0f);
        class_13092.field_5982 = 195.0f;
        class_13092.field_6004 = 0.0f;
        class_13092.method_5728(false);
        class_13092.method_5660(false);
        if (class_13092 instanceof class_1657) {
            class_16572 = (class_1657)class_13092;
            class_16572.field_6283 = 195.0f;
            class_16572.field_6220 = 195.0f;
            class_16572.field_6241 = 195.0f;
            class_16572.field_6259 = 195.0f;
        }
        if (class_13092 instanceof class_742) {
            class_16572 = (class_742)class_13092;
            class_16572.field_42108.method_48567(0.0f);
        }
    }

    static final class BonePose {
        private final float MJ;
        private final float MK;
        private final float ML;
        private final float MM;
        private final float MN;
        private final float MO;
        private final float MP;
        private final float MQ;
        private final boolean MR;
        private final boolean MS;
        private final float MT;

        private BonePose(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean bl, boolean bl2, float f9) {
            this.MJ = f;
            this.MK = f2;
            this.ML = f3;
            this.MM = f4;
            this.MN = f5;
            this.MO = f6;
            this.MP = f7;
            this.MQ = f8;
            this.MR = bl;
            this.MS = bl2;
            this.MT = f9;
        }

        static BonePose f(class_1309 class_13092) {
            float f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            float f4 = 0.0f;
            float f5 = 0.0f;
            if (class_13092 instanceof class_1657) {
                class_1657 class_16572 = (class_1657)class_13092;
                f2 = class_16572.field_6283;
                f3 = class_16572.field_6220;
                f4 = class_16572.field_6241;
                f5 = class_16572.field_6259;
            }
            if (class_13092 instanceof class_742) {
                class_742 class_7422 = (class_742)class_13092;
                f = class_7422.field_42108.method_48566();
            } else {
                f = 0.0f;
            }
            float f6 = f;
            return new BonePose(class_13092.method_36454(), class_13092.method_36455(), class_13092.field_5982, class_13092.field_6004, f2, f3, f4, f5, class_13092.method_5624(), class_13092.method_5715(), f6);
        }

        void g(class_1309 class_13092) {
            class_1657 class_16572;
            class_13092.method_36456(this.MJ);
            class_13092.method_36457(this.MK);
            class_13092.field_5982 = this.ML;
            class_13092.field_6004 = this.MM;
            class_13092.method_5728(this.MR);
            class_13092.method_5660(this.MS);
            if (class_13092 instanceof class_1657) {
                class_16572 = (class_1657)class_13092;
                class_16572.field_6283 = this.MN;
                class_16572.field_6220 = this.MO;
                class_16572.field_6241 = this.MP;
                class_16572.field_6259 = this.MQ;
            }
            if (class_13092 instanceof class_742) {
                class_16572 = (class_742)class_13092;
                class_16572.field_42108.method_48567(this.MT);
            }
        }

        @Override
        public final String toString() {
            return "BonePose[" + "yaw=" + this.MJ + ", " + "pitch=" + this.MK + ", " + "prevYaw=" + this.ML + ", " + "prevPrevPitch=" + this.MM + ", " + "bodyYaw=" + this.MN + ", " + "prevBodyYaw=" + this.MO + ", " + "headYaw=" + this.MP + ", " + "prevHeadYaw=" + this.MQ + ", " + "sprinting=" + this.MR + ", " + "sneaking=" + this.MS + ", " + "limbSpeed=" + this.MT + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.MJ, this.MK, this.ML, this.MM, this.MN, this.MO, this.MP, this.MQ, this.MR, this.MS, this.MT);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof BonePose other)) return false;
            return java.util.Objects.deepEquals(this.MJ, other.MJ)
                && java.util.Objects.deepEquals(this.MK, other.MK)
                && java.util.Objects.deepEquals(this.ML, other.ML)
                && java.util.Objects.deepEquals(this.MM, other.MM)
                && java.util.Objects.deepEquals(this.MN, other.MN)
                && java.util.Objects.deepEquals(this.MO, other.MO)
                && java.util.Objects.deepEquals(this.MP, other.MP)
                && java.util.Objects.deepEquals(this.MQ, other.MQ)
                && java.util.Objects.deepEquals(this.MR, other.MR)
                && java.util.Objects.deepEquals(this.MS, other.MS)
                && java.util.Objects.deepEquals(this.MT, other.MT);
        }

        public float bi() {
            return this.MJ;
        }

        public float bj() {
            return this.MK;
        }

        public float le() {
            return this.ML;
        }

        public float lf() {
            return this.MM;
        }

        public float bodyYaw() {
            return this.MN;
        }

        public float lg() {
            return this.MO;
        }

        public float headYaw() {
            return this.MP;
        }

        public float lh() {
            return this.MQ;
        }

        public boolean sprinting() {
            return this.MR;
        }

        public boolean li() {
            return this.MS;
        }

        public float lj() {
            return this.MT;
        }
    }
}

