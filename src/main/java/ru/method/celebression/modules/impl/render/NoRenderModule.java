package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import net.minecraft.class_1074;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.TimedBooleanSetting;

public class NoRenderModule
extends Module {
    public BooleanSetting Fa = new TimedBooleanSetting(class_1074.method_4662((String)"settings.norender.hurtcam", (Object[])new Object[0]), (Boolean)true, 2.0f);
    public BooleanSetting Fb = new TimedBooleanSetting(class_1074.method_4662((String)"settings.norender.fire", (Object[])new Object[0]), (Boolean)true, 2.0f);
    public BooleanSetting wH = new TimedBooleanSetting(class_1074.method_4662((String)"settings.norender.totem", (Object[])new Object[0]), (Boolean)true, 2.0f);
    public BooleanSetting Fc = new TimedBooleanSetting(class_1074.method_4662((String)"settings.norender.scoreboard", (Object[])new Object[0]), (Boolean)false, 2.0f);
    public BooleanSetting Fd = new TimedBooleanSetting(class_1074.method_4662((String)"settings.norender.bossbar", (Object[])new Object[0]), (Boolean)false, 2.0f);
    public BooleanSetting Fe = new TimedBooleanSetting(class_1074.method_4662((String)"settings.norender.particles", (Object[])new Object[0]), (Boolean)true, 2.0f);
    public BooleanSetting Ff = new TimedBooleanSetting(class_1074.method_4662((String)"settings.norender.armor", (Object[])new Object[0]), (Boolean)false, 2.0f);
    public BooleanSetting Fg = new TimedBooleanSetting(class_1074.method_4662((String)"settings.norender.inventory_background", (Object[])new Object[0]), (Boolean)false, 2.0f);

    public NoRenderModule() {
        super("NoRender", ModuleCategory.ql);
        this.getSettings().add(this.Fa);
        this.getSettings().add(this.Fb);
        this.getSettings().add(this.wH);
        this.getSettings().add(this.Fc);
        this.getSettings().add(this.Fd);
        this.getSettings().add(this.Fe);
        this.getSettings().add(this.Ff);
        this.getSettings().add(this.Fg);
    }
}

