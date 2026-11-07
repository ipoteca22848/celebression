package ru.method.celebression.api.setting;

import java.util.function.Supplier;
import ru.method.celebression.api.setting.Setting;

public class BooleanSetting
extends Setting<Boolean> {
    public BooleanSetting(String string, Boolean bl, Supplier<Boolean> supplier) {
        super(string, bl, supplier);
    }

    public BooleanSetting(String string, Boolean bl) {
        super(string, bl);
    }
}

