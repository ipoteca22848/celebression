package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import net.minecraft.class_1306;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.setting.SliderSetting;

public class SwingAnimationModule
extends Module {
    private static final float Gi = 0.5f;
    private final EnumSetting<SwingMode> Gj = new EnumSetting<SwingMode>("settings.swinganimation.mode", SwingMode.Gl);
    private final SliderSetting Gk = new SliderSetting("settings.swinganimation.power", 5.0f, 1.0f, 10.0f, 0.05f);

    public SwingAnimationModule() {
        super("SwingAnimation", ModuleCategory.ql);
    }

    public void a(class_4587 class_45872, float f, float f2, class_1306 class_13062) {
        SwingMode swingMode = (SwingMode)this.Gj.jT();
        float f3 = ((Float)this.Gk.jT()).floatValue();
        float f4 = class_3532.method_15374((float)(class_3532.method_15355((float)f) * (float)Math.PI));
        int n = class_13062 == class_1306.field_6183 ? 1 : -1;
        switch (swingMode.ordinal()) {
            case 0: {
                class_45872.method_46416((float)n * 0.56f, -0.52f, -0.72f);
                float f5 = class_3532.method_15374((float)(class_3532.method_15355((float)f) * (float)Math.PI));
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(f5 * -60.0f * f3 / 5.0f));
                class_45872.method_22907(class_7833.field_40716.rotationDegrees((float)n * f5 * -30.0f * f3 / 5.0f));
                return;
            }
            case 1: {
                if (f > 0.0f) {
                    float f6 = class_3532.method_15374((float)(class_3532.method_15355((float)f) * (float)Math.PI));
                    class_45872.method_46416((float)n * 0.56f, f2 * -0.2f - 0.5f, -0.7f);
                    class_45872.method_22907(class_7833.field_40716.rotationDegrees((float)(n * 45)));
                    class_45872.method_22907(class_7833.field_40714.rotationDegrees(f6 * -85.0f * f3 / 5.0f));
                    class_45872.method_46416((float)n * -0.1f, 0.28f, 0.2f);
                    class_45872.method_22907(class_7833.field_40714.rotationDegrees(-85.0f));
                } else {
                    float f7 = -0.4f * class_3532.method_15374((float)(class_3532.method_15355((float)f) * (float)Math.PI));
                    float f8 = 0.2f * class_3532.method_15374((float)(class_3532.method_15355((float)f) * (float)Math.PI * 2.0f));
                    float f9 = -0.2f * class_3532.method_15374((float)(f * (float)Math.PI));
                    class_45872.method_46416((float)n * f7, f8, f9);
                    this.a(class_45872, class_13062, f2);
                    this.b(class_45872, class_13062, f);
                }
                return;
            }
            case 2: {
                float f10 = class_3532.method_15374((float)(class_3532.method_15355((float)f) * (float)Math.PI));
                this.a(class_45872, class_13062, 0.0f);
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(50.0f));
                class_45872.method_22907(class_7833.field_40716.rotationDegrees((float)n * -60.0f));
                class_45872.method_22907(class_7833.field_40718.rotationDegrees((float)n * (110.0f + 20.0f * f10 * f3 / 5.0f)));
                return;
            }
            case 3: {
                float f11 = class_3532.method_15374((float)(class_3532.method_15355((float)f) * (float)Math.PI));
                this.a(class_45872, class_13062, 0.0f);
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(50.0f));
                class_45872.method_22907(class_7833.field_40716.rotationDegrees((float)n * (-30.0f * (1.0f - f11) - 30.0f)));
                class_45872.method_22907(class_7833.field_40718.rotationDegrees((float)n * 110.0f));
                return;
            }
            case 4: {
                float f12 = class_3532.method_15374((float)(f * (float)Math.PI));
                float f13 = f3 / 5.0f;
                this.a(class_45872, class_13062, f2);
                class_45872.method_46416(0.0f, -0.28f + f12 * -0.18f, -0.48f + f12 * -0.12f);
                class_45872.method_22907(class_7833.field_40716.rotationDegrees((float)n * (95.0f * f12 * f13)));
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(-110.0f * f12 * f13 - 15.0f * f2));
                class_45872.method_22907(class_7833.field_40718.rotationDegrees((float)n * (35.0f * f12)));
                class_45872.method_22905(0.9f + 0.08f * f12, 0.92f + 0.05f * f12, 0.9f);
                return;
            }
            case 5: {
                class_45872.method_22905(0.5f, 0.5f, 0.5f);
                this.a(class_45872, class_13062, 0.0f);
                class_45872.method_22907(class_7833.field_40716.rotationDegrees((float)n * (15.0f * f4)));
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(-(f3 * 10.0f) * f4));
                return;
            }
            case 6: {
                class_45872.method_22905(0.6f, 0.5f, 0.4f);
                this.a(class_45872, class_13062, 0.0f);
                class_45872.method_46416((float)n * 0.2f * f4, 0.0f, -0.5f);
                class_45872.method_22907(class_7833.field_40716.rotationDegrees((float)n * (90.0f * f4 * f3 / 5.0f)));
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(-140.0f * f4 * f3 / 5.0f));
                return;
            }
        }
    }

    private void a(class_4587 class_45872, class_1306 class_13062, float f) {
        int n = class_13062 == class_1306.field_6183 ? 1 : -1;
        class_45872.method_46416((float)n * 0.56f, -0.52f + f * -0.6f, -0.72f);
    }

    private void b(class_4587 class_45872, class_1306 class_13062, float f) {
        int n = class_13062 == class_1306.field_6183 ? 1 : -1;
        float f2 = class_3532.method_15374((float)(f * f * (float)Math.PI));
        class_45872.method_22907(class_7833.field_40716.rotationDegrees((float)n * (45.0f + f2 * -20.0f)));
        float f3 = class_3532.method_15374((float)(class_3532.method_15355((float)f) * (float)Math.PI));
        class_45872.method_22907(class_7833.field_40718.rotationDegrees((float)n * f3 * -20.0f));
        class_45872.method_22907(class_7833.field_40714.rotationDegrees(f3 * -80.0f));
        class_45872.method_22907(class_7833.field_40716.rotationDegrees((float)n * -45.0f));
    }

    public static enum SwingMode
    implements NamedOption  {
        Gl("settings.swinganimation.mode.classic"),
        Gm("settings.swinganimation.mode.riposte"),
        Gn("settings.swinganimation.mode.lunge"),
        Go("settings.swinganimation.mode.cleave"),
        Gp("settings.swinganimation.mode.whirl"),
        Gq("settings.swinganimation.mode.cascade"),
        Gr("settings.swinganimation.mode.orbit");
        private final String Gs;

private SwingMode(String string2) {
            this.Gs = string2;
        }

        @Override
        public String getName() {
            return this.Gs;
        }

}
}

