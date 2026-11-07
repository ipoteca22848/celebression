package ru.method.celebression.util;

import java.util.regex.Pattern;

public final class TextFormattingStripper {
    private static final Pattern akV = Pattern.compile("(?i)&x(?:&[0-9a-f]){6}");
    private static final Pattern akW = Pattern.compile("(?i)\u00a7x(?:\u00a7[0-9a-f]){6}");
    private static final Pattern akX = Pattern.compile("(?i)&[0-9a-fklmnor]");
    private static final Pattern akY = Pattern.compile("(?i)\u00a7[0-9a-fklmnor]");

    private TextFormattingStripper() {
    }

    public static String da(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        String string2 = akV.matcher(string).replaceAll("");
        string2 = akW.matcher(string2).replaceAll("");
        string2 = akX.matcher(string2).replaceAll("");
        string2 = akY.matcher(string2).replaceAll("");
        return string2.trim();
    }
}

