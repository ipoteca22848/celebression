package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.HandsRenderer;

public class HandsModule
extends Module {
    public final EnumSetting<HandMode> Bi = new EnumSetting<HandMode>("modules.settings.hands.mode", HandMode.Bp);
    public final BooleanSetting Bj = new BooleanSetting("settings.hands.sync_with_theme", true, this::iV);
    public final ColorSetting Bk = new ColorSetting("settings.hands.shader_color", new Color(151, 71, 255, 255), () -> this.iV() && (Boolean)this.Bj.jT() == false);
    public final ColorSetting Bl = new ColorSetting("modules.settings.hands.solid_color", new Color(0, 255, 0, 255), () -> this.Bi.jT() == HandMode.Bp);
    public final ColorSetting Bm = new ColorSetting("modules.settings.hands.solid_color2", new Color(255, 0, 0, 255), () -> this.Bi.jT() == HandMode.Bp);
    public final SliderSetting Bn = new SliderSetting("modules.settings.hands.gradient_speed", 0.5f, 0.1f, 1.0f, 0.1f, this::iU);
    public final SliderSetting Bo = new SliderSetting("modules.settings.hands.blur_strength", 10.0f, 2.0f, 20.0f, 1.0f);

    public HandsModule() {
        super("Hands", ModuleCategory.ql);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        HandsRenderer.release();
    }

    public Color iT() {
        return (Boolean)this.Bj.jT() != false ? ColorUtils.getPrimaryColor() : (Color)this.Bk.jT();
    }

    public boolean iU() {
        return this.Bi.jT() != HandMode.Bq;
    }

    public boolean iV() {
        return this.Bi.jT() == HandMode.Br || this.Bi.jT() == HandMode.Bs || this.Bi.jT() == HandMode.Bt;
    }

    public static enum HandMode
    implements NamedOption  {
        Bp("settings.hands.mode.own_color"),
        Bq("settings.hands.mode.transparent"),
        Br("settings.hands.mode.plasma"),
        Bs("settings.hands.mode.nebula"),
        Bt("settings.hands.mode.cobweb");
        private final String Bu;

private HandMode(String string2) {
            this.Bu = string2;
        }

        @Override
        public String getName() {
            return this.Bu;
        }

}
}

