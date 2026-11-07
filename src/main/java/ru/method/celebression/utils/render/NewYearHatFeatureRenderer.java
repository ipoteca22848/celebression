package ru.method.celebression.utils.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_10055;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_310;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_591;
import net.minecraft.class_811;
import net.minecraft.class_9334;
import net.minecraft.class_976;
import ru.method.celebression.Celebression;

@Environment(value=EnvType.CLIENT)
public class NewYearHatFeatureRenderer
extends class_3887<class_10055, class_591> {
    private static final class_1799 NEW_YEAR_HAT_STACK = NewYearHatFeatureRenderer.createHatStack();

    public NewYearHatFeatureRenderer(class_3883<class_10055, class_591> class_38832) {
        super(class_38832);
    }

    private static class_1799 createHatStack() {
        class_1799 class_17992 = new class_1799((class_1935)class_1802.field_8600);
        class_17992.method_57379(class_9334.field_54199, Celebression.id("noviygod/hat"));
        return class_17992;
    }

    @Override
    public void method_4199(class_4587 class_45872, class_4597 class_45972, int n, class_10055 class_100552, float f, float f2) {
        if (class_100552.field_53542 || class_100552.field_53461) {
            return;
        }
        class_45872.method_22903();
        try {
            class_591 class_5912 = (class_591)this.method_17165();
            class_5912.method_63512().method_22703(class_45872);
            class_5912.method_2838().method_22703(class_45872);
            class_976.method_32798((class_4587)class_45872, (class_976.class_9994)class_976.class_9994.field_53212);
            class_310.method_1551().method_1480().method_23178(NEW_YEAR_HAT_STACK, class_811.field_4316, n, class_4608.field_21444, class_45872, class_45972, null, 0);
        }
        finally {
            class_45872.method_22909();
        }
    }
}
