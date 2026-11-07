package ru.method.celebression.integration.ui.modules;

public enum TypeList {
    Combat("f"),
    Movement("w"),
    Render("E"),
    Display("q"),
    Player("r"),
    Misc("v");

    public final String icon;

    TypeList(String icon) {
        this.icon = icon;
    }
}