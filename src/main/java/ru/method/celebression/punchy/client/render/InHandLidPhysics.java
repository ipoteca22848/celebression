package ru.method.celebression.punchy.client.render;

import java.util.EnumMap;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_3532;

public final class InHandLidPhysics {
    private static final float FALL_SPEED_MAX = 0.3f;
    private static final float FALL_DISTANCE_MAX = 2.0f;
    private static final float FALL_DISTANCE_FACTOR = 0.75f;
    private static final float LID_RISE_LERP = 0.05f;
    private static final float LID_DROP_LERP = 0.7f;

    private InHandLidPhysics() {
    }

    public static float computeFallTarget(class_1657 class_16572) {
        if (class_16572 != null && !class_16572.method_24828()) {
            float f = class_3532.method_15363((float)((float)(-class_16572.method_18798().field_1351)), (float)0.0f, (float)0.3f);
            float f2 = f / 0.3f;
            float f3 = class_3532.method_15363((float)(class_16572.field_6017 / 2.0f), (float)0.0f, (float)1.0f);
            return class_3532.method_15363((float)(f2 + f3 * 0.75f), (float)0.0f, (float)1.0f);
        }
        return 0.0f;
    }

    public static float updateLid(EnumMap<class_1268, Float> enumMap, EnumMap<class_1268, Long> enumMap2, class_1268 class_12682, float f) {
        if (class_12682 != null && enumMap != null && enumMap2 != null) {
            long l = System.nanoTime();
            long l2 = enumMap2.getOrDefault(class_12682, 0L);
            float f2 = enumMap.getOrDefault(class_12682, Float.valueOf(0.0f)).floatValue();
            float f3 = InHandLidPhysics.updateLid(f2, f, l2, l);
            enumMap.put(class_12682, Float.valueOf(f3));
            enumMap2.put(class_12682, l);
            return f3;
        }
        return 0.0f;
    }

    public static float updateLid(float f, float f2, long l, long l2) {
        float f3 = l == 0L ? 0.05f : (float)(l2 - l) / 1.0E9f;
        if (f3 <= 0.0f) {
            return f;
        }
        if (f3 > 0.1f) {
            f3 = 0.1f;
        }
        float f4 = f3 * 20.0f;
        float f5 = 1.0f - (float)Math.pow(0.95f, f4);
        float f6 = 1.0f - (float)Math.pow(0.3f, f4);
        float f7 = f2 > f ? f5 : f6;
        return class_3532.method_16439((float)f7, (float)f, (float)f2);
    }
}

