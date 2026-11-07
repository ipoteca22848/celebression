package ru.method.celebression.modules.impl.render.cosmetic;

import org.jetbrains.annotations.Nullable;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLoadout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;

public final class CosmeticPreviewContext {
    private static final ThreadLocal<PreviewState> dG = new ThreadLocal();

    private CosmeticPreviewContext() {
    }

    public static void a(PreviewState previewState) {
        dG.set(previewState);
    }

    public static void end() {
        dG.remove();
    }

    @Nullable
    public static PreviewState aO() {
        return dG.get();
    }

    public static boolean isActive() {
        return dG.get() != null;
    }

    public static boolean aP() {
        PreviewState previewState = dG.get();
        return previewState != null && previewState.aU();
    }

    public static final class PreviewState {
        private final CosmeticLoadout dH;
        private final CosmeticSlot dI;
        private final boolean dJ;
        private final float dK;
        private final float dL;
        private final float dM;
        private final boolean dN;

        public PreviewState(CosmeticLoadout cosmeticLoadout, CosmeticSlot cosmeticSlot, boolean bl, float f, float f2, float f3) {
            this(cosmeticLoadout, cosmeticSlot, bl, f, f2, f3, false);
        }

        public PreviewState(CosmeticLoadout cosmeticLoadout, CosmeticSlot cosmeticSlot, boolean bl, float f, float f2, float f3, boolean bl2) {
            this.dH = cosmeticLoadout;
            this.dI = cosmeticSlot;
            this.dJ = bl;
            this.dK = f;
            this.dL = f2;
            this.dM = f3;
            this.dN = bl2;
        }

        @Override
        public final String toString() {
            return "PreviewState[" + "profile=" + this.dH + ", " + "focusSlot=" + this.dI + ", " + "walking=" + this.dJ + ", " + "animationTimeSeconds=" + this.dK + ", " + "movingBlend=" + this.dL + ", " + "deltaSeconds=" + this.dM + ", " + "suppressEntityLabel=" + this.dN + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.dH, this.dI, this.dJ, this.dK, this.dL, this.dM, this.dN);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof PreviewState other)) return false;
            return java.util.Objects.deepEquals(this.dH, other.dH)
                && java.util.Objects.deepEquals(this.dI, other.dI)
                && java.util.Objects.deepEquals(this.dJ, other.dJ)
                && java.util.Objects.deepEquals(this.dK, other.dK)
                && java.util.Objects.deepEquals(this.dL, other.dL)
                && java.util.Objects.deepEquals(this.dM, other.dM)
                && java.util.Objects.deepEquals(this.dN, other.dN);
        }

        public CosmeticLoadout aQ() {
            return this.dH;
        }

        public CosmeticSlot aR() {
            return this.dI;
        }

        public boolean walking() {
            return this.dJ;
        }

        public float aS() {
            return this.dK;
        }

        public float bv() {
            return this.dL;
        }

        public float aT() {
            return this.dM;
        }

        public boolean aU() {
            return this.dN;
        }
    }
}

