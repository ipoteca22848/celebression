package ru.method.celebression.util;

public final class TextSanitizationMode {
    public static final int amc = 0xFF0000;
    public static final int amd = 0xFFFFFF;
    public static final int ame = 0xFFFF55;
    public static final int amf = 16484360;
    public static final int amg = 160;
    private static volatile Strategy amh = Strategy.amn;
    private static volatile boolean ami;
    private static int amj;
    private static int amk;
    private static int aml;
    private static int amm;

    private TextSanitizationMode() {
    }

    public static Strategy sX() {
        return amh;
    }

    public static void a(Strategy strategy) {
        amh = strategy;
    }

    public static boolean sY() {
        return ami;
    }

    public static void ah(boolean bl) {
        ami = bl;
    }

    public static void sZ() {
        ++amj;
    }

    public static void ta() {
        if (amj > 0) {
            --amj;
        }
    }

    public static void tb() {
        ++amk;
    }

    public static void tc() {
        if (amk > 0) {
            --amk;
        }
    }

    public static void td() {
        ++aml;
    }

    public static void te() {
        if (aml > 0) {
            --aml;
        }
    }

    public static boolean sy() {
        return amj > 0 || amk > 0;
    }

    public static boolean sB() {
        return aml > 0;
    }

    public static int tf() {
        return amm;
    }

    public static void tg() {
        ++amm;
    }

    public static enum Strategy {
        amn,
        amo,
        amp;
}
}

