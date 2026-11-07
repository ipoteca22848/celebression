package ru.method.celebression.api.setting;

import java.util.function.Supplier;
import ru.method.celebression.api.setting.Setting;

public class ButtonSetting
extends Setting<Runnable> {
    private final String Kj;

    public ButtonSetting(String string, String string2, Runnable runnable) {
        super(string, runnable);
        this.Kj = string2;
    }

    public ButtonSetting(String string, String string2, Runnable runnable, Supplier<Boolean> supplier) {
        super(string, runnable, supplier);
        this.Kj = string2;
    }

    public String jX() {
        return this.Kj;
    }

    public void run() {
        Runnable runnable = (Runnable)this.jT();
        if (runnable != null) {
            runnable.run();
        }
    }
}

