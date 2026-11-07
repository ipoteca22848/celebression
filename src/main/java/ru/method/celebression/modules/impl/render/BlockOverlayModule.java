package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.setting.SliderSetting;

public class BlockOverlayModule
extends Module {
    public final BooleanSetting yQ = new BooleanSetting("settings.blockoverlay.fill", true);
    public final BooleanSetting yR = new BooleanSetting("modules.settings.blockoverlay.use_shader", true, () -> (Boolean)this.yQ.jT());
    public final EnumSetting<OverlayShape> yS = new EnumSetting<OverlayShape>("settings.blockoverlay.fill_mode", OverlayShape.yW, () -> (Boolean)this.yQ.jT());
    public final SliderSetting yT = new SliderSetting("settings.blockoverlay.fill_alpha", 70.0f, 0.0f, 255.0f, 1.0f, () -> (Boolean)this.yQ.jT());
    public final SliderSetting yU = new SliderSetting("settings.blockoverlay.outline_alpha", 255.0f, 0.0f, 255.0f, 1.0f);
    public final SliderSetting yV = new SliderSetting("modules.settings.blockoverlay.shader_speed", 1.0f, 0.1f, 5.0f, 0.1f, () -> (Boolean)this.yQ.jT() != false && (Boolean)this.yR.jT() != false);

    public BlockOverlayModule() {
        super("BlockOverlay", ModuleCategory.ql);
    }

    public static enum OverlayShape
    implements NamedOption  {
        yW("settings.blockoverlay.fill_mode.aurora"),
        yX("settings.blockoverlay.fill_mode.plasma"),
        yY("settings.blockoverlay.fill_mode.cosmos"),
        yZ("settings.blockoverlay.fill_mode.cobweb"),
        za("settings.blockoverlay.fill_mode.fireworks"),
        zb("settings.blockoverlay.fill_mode.galaxy"),
        zc("settings.blockoverlay.fill_mode.nebula"),
        zd("settings.blockoverlay.fill_mode.starfield"),
        ze("settings.blockoverlay.fill_mode.stars");
        private final String zf;

private OverlayShape(String string2) {
            this.zf = string2;
        }

        @Override
        public String getName() {
            return this.zf;
        }

}
}

