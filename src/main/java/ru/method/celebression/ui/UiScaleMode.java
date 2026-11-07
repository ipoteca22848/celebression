package ru.method.celebression.ui;

import ru.method.celebression.api.setting.Setting;

import ru.method.celebression.api.setting.NamedOption;

public enum UiScaleMode
implements NamedOption  {
    qr("Default"),
    qs("Middle");
    private final String qt;

private UiScaleMode(String string2) {
        this.qt = string2;
    }

    @Override
    public String getName() {
        return this.qt;
    }

}

