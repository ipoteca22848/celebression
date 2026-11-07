package ru.method.celebression.modules.impl.render.cosmetic;

import java.awt.Color;
import lombok.Generated;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticColors;

public final class CosmeticColorUtils {
    public static final int PJ = 1120;
    public static final int PK = 700;
    public static final float PL = 24.0f;
    public static final float PM = 34.0f;
    public static final float PN = 76.0f;
    public static final float PO = 28.0f;
    public static final float PP = 8.0f;
    public static final float PQ = 255.0f;
    public static final float PR = 357.0f;
    public static final float PS = 408.0f;
    public static final float PT = 16.0f;
    public static final float PU = 12.0f;
    public static final float PV = 10.0f;
    public static final float PW = 10.0f;
    public static final float PX = 16.0f;
    public static final float PY = 38.0f;
    public static final float PZ = 64.0f;
    public static final float Qa = 14.0f;
    public static final float Qb = 6.0f;
    public static final float Qc = 18.0f;
    public static final float Qd = 12.0f;
    public static final float Qe = 14.0f;
    public static final float Qf = 8.0f;
    public static final float Qg = 178.0f;
    public static final float Qh = 52.0f;
    public static final float Qi = 8.0f;
    public static final float Qj = 30.0f;
    public static final float Qk = 34.0f;
    public static final float Ql = 18.0f;
    public static final Color Qm = CosmeticColors.NH;
    public static final Color Qn = CosmeticColors.NJ;
    public static final Color Qo = CosmeticColors.NS;
    public static final Color Qp = CosmeticColors.NQ;
    public static final Color Qq = CosmeticColors.NR;
    public static final Color Qr = CosmeticColors.NT;
    public static final Color Qs = CosmeticColors.NU;
    public static final Color Qt = CosmeticColors.Oc;

    public static Color ae(float f) {
        return CosmeticColorUtils.c(CosmeticColors.Oa, f);
    }

    public static Color af(float f) {
        return CosmeticColorUtils.c(new Color(22, 18, 34), 0.82f * f);
    }

    public static Color c(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, Math.round(255.0f * f))));
    }

    @Generated
    private CosmeticColorUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

