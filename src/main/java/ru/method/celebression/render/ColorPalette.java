package ru.method.celebression.render;

import java.awt.Color;

public final class ColorPalette {
    public static final Color air = ColorPalette.cE("#222128");
    public static final Color ais = ColorPalette.cE("#111111");
    public static final Color ait = ColorPalette.cE("#FFFFFF");
    public static final Color aiu = ColorPalette.cE("#A39BD6");
    public static final Color aiv = ColorPalette.cE("#555170");
    public static final Color aiw = ColorPalette.cE("#A39BD6");
    public static final Color aix = ColorPalette.cE("#C7BEFF");
    public static final Color aiy = ColorPalette.cE("#55526E");
    public static final Color aiz = ColorPalette.cE("#25242D");
    public static final Color aiA = ColorPalette.cE("#787878");
    public static final Color aiB = ColorPalette.cE("#8E8E8E");

    private ColorPalette() {
    }

    public static Color cE(String string) {
        return Color.decode(string);
    }
}

