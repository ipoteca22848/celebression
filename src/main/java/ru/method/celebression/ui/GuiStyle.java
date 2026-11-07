package ru.method.celebression.ui;

import ru.method.celebression.api.setting.Setting;

import ru.method.celebression.api.setting.NamedOption;

public enum GuiStyle
implements NamedOption  {
    ain("Panel"),
    aio("Link");
    private final String aip;

private GuiStyle(String string2) {
        this.aip = string2;
    }

    @Override
    public String getName() {
        return this.aip;
    }

}

