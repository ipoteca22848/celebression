package ru.method.celebression.modules.impl.render.cosmetic;

import net.minecraft.class_1309;
import net.minecraft.class_332;
import net.minecraft.class_490;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticPetPreviewRenderer;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;

public final class CosmeticEntityPreviewRenderer {
    private static final float dX = 0.0625f;
    public static float dY = 0.0f;
    public static float dZ = 0.0f;

    private CosmeticEntityPreviewRenderer() {
    }

    public static void a(class_332 class_3322, int n, int n2, int n3, int n4, class_1309 class_13092, CosmeticSlot cosmeticSlot) {
        if (n3 <= 0 || n4 <= 0 || class_13092 == null) {
            return;
        }
        int n5 = Math.max(20, Math.min(n3, n4) / 3);
        switch (cosmeticSlot) {
            case ds: {
                n5 = Math.max(20, Math.round((float)Math.min(n3, n4) * 0.3f));
                break;
            }
            case dt: {
                n5 = Math.max(14, Math.round((float)Math.min(n3, n4) * 0.205f));
                break;
            }
        }
        CosmeticEntityPreviewRenderer.a(class_3322, n, n2, n + n3, n2 + n4, n5, 0.0625f, class_13092);
        CosmeticPetPreviewRenderer.a(class_3322, n, n2, n3, n4, n5);
    }

    public static void a(class_332 class_3322, int n, int n2, int n3, int n4, int n5, float f, class_1309 class_13092) {
        float f2;
        if (class_13092 == null) {
            return;
        }
        float f3 = (float)(n + n3) / 2.0f;
        float f4 = (float)(n2 + n4) / 2.0f;
        Quaternionf quaternionf = new Quaternionf().rotateZ((float)Math.PI);
        Quaternionf quaternionf2 = new Quaternionf().rotateX(dZ * ((float)Math.PI / 180));
        quaternionf.mul((Quaternionfc)quaternionf2);
        float f5 = class_13092.field_6283;
        float f6 = class_13092.method_36454();
        float f7 = class_13092.method_36455();
        float f8 = class_13092.field_6259;
        float f9 = class_13092.field_6241;
        class_13092.field_6283 = f2 = 180.0f + dY;
        class_13092.method_36456(f2);
        class_13092.method_36457(0.0f);
        class_13092.field_6241 = f2;
        class_13092.field_6259 = f2;
        float f10 = class_13092.method_55693();
        if (f10 <= 0.0f) {
            f10 = 1.0f;
        }
        Vector3f vector3f = new Vector3f(0.0f, class_13092.method_17682() / 2.0f + f * f10, 0.0f);
        class_490.method_48472((class_332)class_3322, (float)f3, (float)f4, (float)((float)n5 / f10), (Vector3f)vector3f, (Quaternionf)quaternionf, (Quaternionf)quaternionf2, (class_1309)class_13092);
        class_13092.field_6283 = f5;
        class_13092.method_36456(f6);
        class_13092.method_36457(f7);
        class_13092.field_6259 = f8;
        class_13092.field_6241 = f9;
    }
}

