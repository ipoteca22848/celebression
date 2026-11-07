package ru.method.celebression.api.setting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_9290;
import net.minecraft.class_9334;

public final class PriceParser {
    private static final Pattern ajR = Pattern.compile("((?:\\d{1,3}(?:[ .,]\\d{3})+)|(?:\\d+(?:[.,]\\d+)?))\\s*(\u043a\u043a\u043a|kkk|\u043a\u043a|kk|\u043c\u043c|mm|\u043c|m|\u043a|k)?");

    private PriceParser() {
    }

    public static long w(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return -1L;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PriceParser.da(class_17992.method_7964().getString())).append(' ');
        class_9290 class_92902 = (class_9290)class_17992.method_57824(class_9334.field_49632);
        if (class_92902 != null) {
            for (class_2561 class_25612 : class_92902.comp_2400()) {
                stringBuilder.append(PriceParser.da(class_25612.getString())).append(' ');
            }
        }
        return PriceParser.cX(stringBuilder.toString().toLowerCase());
    }

    private static long cX(String string) {
        if (string == null || string.isEmpty()) {
            return -1L;
        }
        Matcher matcher = ajR.matcher(string);
        long l = -1L;
        while (matcher.find()) {
            int n;
            int n2;
            String string2;
            boolean bl;
            long l2 = PriceParser.ac(matcher.group(1), matcher.group(2));
            if (l2 <= 0L || !(bl = (string2 = string.substring(n2 = Math.max(0, matcher.start() - 24), n = Math.min(string.length(), matcher.end() + 6))).contains("\u0446\u0435\u043d\u0430") || string2.contains("price") || string2.contains("\u0441\u0442\u043e\u0438\u043c") || string2.contains("$") || string2.contains("\u043c\u043e\u043d\u0435\u0442") || string2.contains("\u043a\u043e\u0438\u043d") || string2.contains("coins") || string2.contains("\u0440\u0443\u0431") || string2.contains("\u043a\u0443\u043f\u0438\u0442\u044c")) || l2 <= l) continue;
            l = l2;
        }
        return l;
    }

    private static long ac(String string, String string2) {
        if (string == null) {
            return -1L;
        }
        long l = PriceParser.cY(string2);
        String string3 = string.trim().replace(" ", "");
        if (l > 1L) {
            string3 = string3.replace(",", ".");
            int n = 0;
            for (int i = 0; i < string3.length(); ++i) {
                if (string3.charAt(i) != '.') continue;
                ++n;
            }
            try {
                double d = n <= 1 ? Double.parseDouble(string3) : Double.parseDouble(string3.replace(".", ""));
                return (long)Math.floor(d * (double)l);
            }
            catch (Exception exception) {
                return PriceParser.cZ(string3) * l;
            }
        }
        return PriceParser.cZ(string3);
    }

    private static long cY(String string) {
        if (string == null) {
            return 1L;
        }
        return switch (string) {
            case "\u043a", "k" -> 1000L;
            case "\u043a\u043a", "kk", "\u043c", "m", "\u043c\u043c", "mm" -> 1000000L;
            case "\u043a\u043a\u043a", "kkk" -> 1000000000L;
            default -> 1L;
        };
    }

    private static long cZ(String string) {
        long l = 0L;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c < '0' || c > '9') continue;
            l = l * 10L + (long)(c - 48);
        }
        return l;
    }

    private static String da(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\u00a7') {
                ++i;
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}

