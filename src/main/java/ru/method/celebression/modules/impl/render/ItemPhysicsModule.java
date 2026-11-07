package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import net.minecraft.class_10039;
import net.minecraft.class_1542;
import net.minecraft.class_2246;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_4587;
import ru.method.celebression.api.mixins.accessors.IEntityMovementMultiplier;
import ru.method.celebression.api.accessor.LivingEntityRenderStateAccessor;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class ItemPhysicsModule
extends Module {
    public static final float uT = 0.007957747f;
    private final SliderSetting uU = new SliderSetting("settings.itemphysics.rotation_speed", 1.0f, 0.0f, 8.0f, 0.1f);
    private final SliderSetting uV = new SliderSetting("settings.itemphysics.scale", 1.0f, 0.5f, 1.5f, 0.05f);

    public ItemPhysicsModule() {
        super("ItemPhysics", ModuleCategory.ql);
    }

    public void a(class_1542 class_15422, class_10039 class_100392, LivingEntityRenderStateAccessor livingEntityRenderStateAccessor) {
        boolean bl;
        float f = this.hq();
        class_243 class_2432 = ((IEntityMovementMultiplier)class_15422).celebression$getMovementMultiplier();
        if (class_2432 != null && class_2432.method_1027() > 0.0) {
            f *= (float)class_2432.field_1352 * 0.2f;
        }
        if (!(bl = class_15422.method_24828())) {
            f *= 2.0f;
            if (class_15422.method_5869()) {
                f *= 0.35f;
            } else if (class_15422.method_5771()) {
                f *= 0.22f;
            }
        }
        boolean bl2 = class_100392.field_55310.method_65607();
        float f2 = class_15422.method_36455();
        if (bl2) {
            if (!bl) {
                f2 += f;
            }
        } else {
            f2 = bl ? 0.0f : (f2 += f);
        }
        class_15422.method_36457(f2);
        livingEntityRenderStateAccessor.celebression$setXRotation(f2);
        livingEntityRenderStateAccessor.celebression$setYRotation((float)Math.toRadians(class_15422.method_36454()));
        livingEntityRenderStateAccessor.celebression$setAdditionalOffset(this.b(class_15422));
    }

    public float getScale() {
        return ((Float)this.uV.jT()).floatValue();
    }

    public void a(class_4587 class_45872, class_10039 class_100392, LivingEntityRenderStateAccessor livingEntityRenderStateAccessor) {
        float f = ((Float)this.uV.jT()).floatValue();
        class_45872.method_22905(f, f, f);
    }

    public float a(class_10039 class_100392, LivingEntityRenderStateAccessor livingEntityRenderStateAccessor) {
        if (class_100392.field_55310.method_65608()) {
            return -0.08f;
        }
        float f = livingEntityRenderStateAccessor.celebression$hasAdditionalOffset() ? -0.14f : -0.04f;
        return f - class_100392.field_53435 * 0.007957747f;
    }

    public float a(class_10039 class_100392) {
        return class_100392.field_55310.method_65608() ? -0.2f : 0.0f;
    }

    private float hq() {
        if (aiG == null || aiG.method_1493()) {
            return 0.0f;
        }
        return (float)((double)aiG.method_47600() / 2.0E8) * ((Float)this.uU.jT()).floatValue();
    }

    private boolean b(class_1542 class_15422) {
        class_2680 class_26802 = class_15422.method_37908().method_8320(class_15422.method_24515());
        if (this.a(class_26802)) {
            return true;
        }
        class_2680 class_26803 = class_15422.method_37908().method_8320(class_15422.method_24515().method_10074());
        return this.a(class_26803);
    }

    private boolean a(class_2680 class_26802) {
        return class_26802.method_27852(class_2246.field_10477) || class_26802.method_27852(class_2246.field_10114) || class_26802.method_27852(class_2246.field_37576);
    }

    public float o(float f) {
        return this.isToggled() ? 0.0f : f;
    }

    public float p(float f) {
        return this.isToggled() ? 0.0f : f;
    }
}

