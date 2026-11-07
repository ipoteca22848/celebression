package ru.method.celebression.api.setting;

import java.util.function.Supplier;
import lombok.Generated;
import ru.method.celebression.api.setting.Setting;

public class SliderSetting
extends Setting<Float> {
    private final float min;
    private final float max;
    private final float Kk;

    public SliderSetting(String string, float f, float f2, float f3, float f4) {
        super(string, Float.valueOf(f));
        this.min = f2;
        this.max = f3;
        this.Kk = f4;
    }

    public SliderSetting(String string, float f, float f2, float f3, float f4, Supplier<Boolean> supplier) {
        super(string, Float.valueOf(f), supplier);
        this.min = f2;
        this.max = f3;
        this.Kk = f4;
    }

    @Generated
    public float kb() {
        return this.min;
    }

    @Generated
    public float kc() {
        return this.max;
    }

    @Generated
    public float kd() {
        return this.Kk;
    }
}

