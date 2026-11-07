package ru.method.celebression.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Generated;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class MathUtils
implements MinecraftClientAccess {
    private static final DateTimeFormatter aku = DateTimeFormatter.ofPattern("HH:mm");
    private static String akv = "";
    private static long akw = -1L;

    public static boolean c(float f, float f2, float f3, float f4, float f5, float f6) {
        return f5 > f && f5 < f + f3 && f6 > f2 && f6 < f2 + f4;
    }

    public static float k(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        return Math.min(f, f3);
    }

    public static float lerp(float f, float f2, float f3) {
        return f + f3 * (f2 - f);
    }

    public static float x(float f, float f2) {
        return (float)(Math.random() * (double)(f2 - f) + (double)f);
    }

    public static int ad(int n, int n2) {
        return (int)(Math.random() * (double)(n2 - n) + (double)n);
    }

    public static float ax(float f) {
        return (float)Math.round(f * 10.0f) / 10.0f;
    }

    public static float y(float f, float f2) {
        float f3 = (float)Math.round(f / f2) * f2;
        BigDecimal bigDecimal = new BigDecimal(f3);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }

    public static class_243 b(Matrix4f matrix4f, float f, float f2, float f3) {
        Vector3f vector3f = matrix4f.transformPosition(f, f2, f3, new Vector3f());
        return new class_243((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z());
    }

    public static String rU() {
        long l = System.currentTimeMillis();
        long l2 = l / 1000L;
        if (l2 != akw) {
            akw = l2;
            akv = ZonedDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneId.systemDefault()).format(aku);
        }
        return akv;
    }

    public static boolean a(class_243 class_2432, int n, float f) {
        double d = class_2432.method_10216() - MathUtils.aiG.field_1724.method_23317();
        double d2 = class_2432.method_10215() - MathUtils.aiG.field_1724.method_23321();
        float f2 = (float)Math.toDegrees(Math.atan2(d2, d)) - 90.0f;
        float f3 = class_3532.method_15393((float)(f2 - f));
        return Math.abs(f3) <= (float)n;
    }

    public static float l(float f, float f2, float f3) {
        if (Math.abs(f2 - f) <= f3) {
            return f2;
        }
        return f + Math.signum(f2 - f) * f3;
    }

    @Generated
    private MathUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

