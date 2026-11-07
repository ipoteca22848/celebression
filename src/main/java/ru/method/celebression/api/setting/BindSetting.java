package ru.method.celebression.api.setting;

import java.util.function.Supplier;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.setting.Setting;

public class BindSetting
extends Setting<KeyBind> {
    public BindSetting(String string, KeyBind keyBind) {
        super(string, keyBind);
    }

    public BindSetting(String string, KeyBind keyBind, Supplier<Boolean> supplier) {
        super(string, keyBind, supplier);
    }
}

