package ru.method.celebression.api.setting;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.Setting;

public class MultiSelectSetting
extends Setting<List<BooleanSetting>> {
    public MultiSelectSetting(String string, BooleanSetting ... booleanSettingArray) {
        super(string, Arrays.asList(booleanSettingArray));
    }

    public MultiSelectSetting(String string, Supplier<Boolean> supplier, BooleanSetting ... booleanSettingArray) {
        super(string, Arrays.asList(booleanSettingArray), supplier);
    }

    public BooleanSetting bT(String string) {
        return this.jT().stream().filter(booleanSetting -> booleanSetting.getName().equalsIgnoreCase(string)).findFirst().orElse(null);
    }

    public List<BooleanSetting> ka() {
        return this.jT().stream().filter(Setting::jT).toList();
    }
}
