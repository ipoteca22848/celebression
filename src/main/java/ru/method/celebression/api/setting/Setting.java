package ru.method.celebression.api.setting;

import com.google.gson.JsonObject;
import java.util.function.Supplier;
import lombok.Generated;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.SettingChangedEvent;

public abstract class Setting<Value> {
    protected final String Kd;
    protected Value Ke;
    protected Value Kf;
    private Supplier<Boolean> visible = () -> true;

    public Setting(String string) {
        this.Kd = string;
    }

    public Setting(String string, Value Value) {
        this.Kd = string;
        this.Kf = Value;
        this.Ke = Value;
    }

    public Setting(String string, Value Value, Supplier<Boolean> supplier) {
        this.Kd = string;
        this.Kf = Value;
        this.Ke = Value;
        this.visible = supplier;
    }

    public void a(Value Value) {
        SettingChangedEvent settingChangedEvent = new SettingChangedEvent(this);
        Celebression.getInstance().getEventHandler().post((Object)settingChangedEvent);
        if (!settingChangedEvent.isCancelled()) {
            this.Ke = Value;
        }
    }

    public void reset() {
        this.Ke = this.Kf;
    }

    public boolean jS() {
        return this.visible.get();
    }

    public void p(JsonObject jsonObject) {
    }

    public void q(JsonObject jsonObject) {
    }

    @Generated
    public String getName() {
        return this.Kd;
    }

    @Generated
    public Value jT() {
        return this.Ke;
    }

    @Generated
    public Value jU() {
        return this.Kf;
    }

    @Generated
    public Supplier<Boolean> getVisible() {
        return this.visible;
    }

    @Generated
    public void b(Value Value) {
        this.Kf = Value;
    }

    @Generated
    public void setVisible(Supplier<Boolean> supplier) {
        this.visible = supplier;
    }
}

