package ru.method.celebression.utils.render;

import java.awt.Color;
import net.minecraft.class_10042;
import net.minecraft.class_3532;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.HitColorModule;
import ru.method.celebression.api.accessor.ItemEntityRenderStateAccessor;

public final class HitColorRenderHelper {
    private HitColorRenderHelper() {
    }

    public static int tint(class_10042 class_100422, int n, boolean bl) {
        Color color;
        float f;
        if (class_100422 == null) {
            return n;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return n;
        }
        HitColorModule hitColorModule = celebression.getModuleManager().getModule(HitColorModule.class);
        if (hitColorModule == null || !hitColorModule.isToggled()) {
            return n;
        }
        if (bl && !((Boolean)hitColorModule.BA.jT()).booleanValue()) {
            return n;
        }
        if (!(class_100422 instanceof ItemEntityRenderStateAccessor)) {
            return n;
        }
        ItemEntityRenderStateAccessor itemEntityRenderStateAccessor = (ItemEntityRenderStateAccessor)class_100422;
        float f2 = class_3532.method_15363((float)itemEntityRenderStateAccessor.celebression$getHitColorStrength(), (float)0.0f, (float)1.0f);
        if (f2 <= 0.001f) {
            return n;
        }
        float f3 = class_3532.method_15363((float)(((Float)hitColorModule.Bz.jT()).floatValue() / 255.0f), (float)0.0f, (float)1.0f);
        float f4 = class_3532.method_15363((float)(f2 * f3 * (f = (float)(color = (Color)hitColorModule.By.jT()).getAlpha() / 255.0f)), (float)0.0f, (float)1.0f);
        if (f4 <= 0.001f) {
            return n;
        }
        int n2 = n >>> 24;
        if (n2 == 0) {
            return n;
        }
        int n3 = n >> 16 & 0xFF;
        int n4 = n >> 8 & 0xFF;
        int n5 = n & 0xFF;
        int n6 = class_3532.method_15340((int)Math.round(class_3532.method_48781((float)f4, (int)n3, (int)color.getRed())), (int)0, (int)255);
        int n7 = class_3532.method_15340((int)Math.round(class_3532.method_48781((float)f4, (int)n4, (int)color.getGreen())), (int)0, (int)255);
        int n8 = class_3532.method_15340((int)Math.round(class_3532.method_48781((float)f4, (int)n5, (int)color.getBlue())), (int)0, (int)255);
        return n2 << 24 | n6 << 16 | n7 << 8 | n8;
    }
}

