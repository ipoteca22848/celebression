package ru.method.celebression.api.event;

import lombok.Generated;
import ru.method.celebression.api.event.CancellableEvent;
import ru.method.celebression.api.setting.Setting;

public class SettingChangedEvent
extends CancellableEvent {
    private final Setting<?> ab;

    @Generated
    public SettingChangedEvent(Setting<?> setting) {
        this.ab = setting;
    }

    @Generated
    public Setting<?> o() {
        return this.ab;
    }
}

