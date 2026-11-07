package ru.method.celebression.punchy.client.render;

import java.util.EnumMap;
import net.minecraft.class_1268;
import net.minecraft.class_3532;
import net.minecraft.class_746;

public final class InHandFallPhysics {
    private static final float FLOAT_MAX = 0.65f;
    private static final float RISE_LERP = 0.1f;
    private static final float FALL_LERP = 0.12f;
    private static final EnumMap<class_1268, Float> OFFSETS = new EnumMap(class_1268.class);

    private InHandFallPhysics() {
    }

    public static float update(class_746 class_7462, class_1268 class_12682) {
        if (class_7462 != null && class_12682 != null) {
            float f;
            float f2;
            float f3 = OFFSETS.getOrDefault(class_12682, Float.valueOf(0.0f)).floatValue();
            float f4 = 0.0f;
            if (!class_7462.method_24828()) {
                f2 = class_3532.method_15363((float)((float)(-class_7462.method_18798().field_1351)), (float)0.0f, (float)0.3f);
                f = f2 / 0.3f;
                float f5 = class_3532.method_15363((float)(class_7462.field_6017 / 2.0f), (float)0.0f, (float)1.0f);
                f4 = 0.65f * class_3532.method_15363((float)(f + f5 * 0.75f), (float)0.0f, (float)1.0f);
            }
            f2 = f4 > f3 ? 0.1f : 0.12f;
            f = class_3532.method_16439((float)f2, (float)f3, (float)f4);
            OFFSETS.put(class_12682, Float.valueOf(f));
            return f;
        }
        return 0.0f;
    }

    public static void reset(class_1268 class_12682) {
        if (class_12682 != null) {
            OFFSETS.put(class_12682, Float.valueOf(0.0f));
        }
    }
}

