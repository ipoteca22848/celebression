package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import java.util.function.Supplier;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class ViewModelModule
extends Module {
    private final BooleanSetting In = new BooleanSetting("settings.viewmodel.punch_mode", false);
    private final Supplier<Boolean> Io = () -> (Boolean)this.In.jT() == false;
    public SliderSetting Ip = new SliderSetting("settings.viewmodel.main_x", 0.0f, -2.0f, 2.0f, 0.05f, this.Io);
    public SliderSetting Iq = new SliderSetting("settings.viewmodel.main_y", 0.0f, -2.0f, 2.0f, 0.05f, this.Io);
    public SliderSetting Ir = new SliderSetting("settings.viewmodel.main_z", 0.0f, -2.0f, 2.0f, 0.05f, this.Io);
    public SliderSetting Is = new SliderSetting("settings.viewmodel.main_scale_x", 1.0f, 0.0f, 3.0f, 0.05f, this.Io);
    public SliderSetting It = new SliderSetting("settings.viewmodel.main_scale_y", 1.0f, 0.0f, 3.0f, 0.05f, this.Io);
    public SliderSetting Iu = new SliderSetting("settings.viewmodel.main_scale_z", 1.0f, 0.0f, 3.0f, 0.05f, this.Io);
    public SliderSetting Iv = new SliderSetting("settings.viewmodel.off_x", 0.0f, -2.0f, 2.0f, 0.05f, this.Io);
    public SliderSetting Iw = new SliderSetting("settings.viewmodel.off_y", 0.0f, -2.0f, 2.0f, 0.05f, this.Io);
    public SliderSetting Ix = new SliderSetting("settings.viewmodel.off_z", 0.0f, -2.0f, 2.0f, 0.05f, this.Io);
    public SliderSetting Iy = new SliderSetting("settings.viewmodel.off_scale_x", 1.0f, 0.0f, 3.0f, 0.05f, this.Io);
    public SliderSetting Iz = new SliderSetting("settings.viewmodel.off_scale_y", 1.0f, 0.0f, 3.0f, 0.05f, this.Io);
    public SliderSetting IA = new SliderSetting("settings.viewmodel.off_scale_z", 1.0f, 0.0f, 3.0f, 0.05f, this.Io);

    public ViewModelModule() {
        super("ViewModel", ModuleCategory.ql);
    }

    public boolean jN() {
        return (Boolean)this.In.jT();
    }
}

