package ru.method.celebression.api.setting;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_3675;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.input.KeyBind;

public final class KeyBindParser {
    private static final Map<String, String> ajO = new LinkedHashMap<String, String>();
    private static final Map<String, String> ajP = new LinkedHashMap<String, String>();
    private static volatile List<String> ajQ;

    private KeyBindParser() {
    }

    public static KeyBind cR(String string) {
        int n;
        if (string == null) {
            return null;
        }
        String string2 = KeyBindParser.cU(string);
        if (string2.isEmpty()) {
            return null;
        }
        if ("NONE".equals(string2)) {
            return new KeyBind(-1, false);
        }
        if ("WHEELUP".equals(string2) || "SCROLLUP".equals(string2) || "WHEEL_UP".equals(string2)) {
            return new KeyBind(1000, true);
        }
        if ("WHEELDOWN".equals(string2) || "SCROLLDOWN".equals(string2) || "WHEEL_DOWN".equals(string2)) {
            return new KeyBind(1001, true);
        }
        String string3 = null;
        if (string2.startsWith("MOUSE")) {
            string3 = string2.substring(5);
        } else if (string2.startsWith("MB")) {
            string3 = string2.substring(2);
        } else if (string2.startsWith("M")) {
            string3 = string2.substring(1);
        }
        if (string3 != null) {
            try {
                n = Integer.parseInt(string3);
                if (n >= 1) {
                    return new KeyBind(n - 1, true);
                }
            }
            catch (NumberFormatException numberFormatException) {
            }
        }
        try {
            n = GLFW.class.getField("GLFW_KEY_" + string2).getInt(null);
            return new KeyBind(n, false);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static String d(KeyBind keyBind) {
        if (keyBind == null || keyBind.isEmpty()) {
            return "None";
        }
        if (keyBind.jW()) {
            if (keyBind.c() == 1000) {
                return "Wheel Up";
            }
            if (keyBind.c() == 1001) {
                return "Wheel Down";
            }
            return "M" + (keyBind.c() + 1);
        }
        String string = KeyBindParser.bj(keyBind.c());
        if (string != null) {
            return KeyBindParser.cV(string);
        }
        try {
            class_3675.class_306 class_3062 = class_3675.class_307.field_1668.method_1447(keyBind.c());
            return class_3062.method_27445().getString();
        }
        catch (Exception exception) {
            return String.valueOf(keyBind.c());
        }
    }

    private static List<String> rF() {
        List<String> list = ajQ;
        if (list != null) {
            return list;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (Field field : GLFW.class.getDeclaredFields()) {
            String string;
            String string2 = field.getName();
            if (!string2.startsWith("GLFW_KEY_") || KeyBindParser.cW(string = string2.substring("GLFW_KEY_".length()))) continue;
            arrayList.add(string.toUpperCase(Locale.ROOT));
        }
        for (int i = 1; i <= 8; ++i) {
            arrayList.add("MOUSE" + i);
            arrayList.add("M" + i);
        }
        arrayList.add("WHEELUP");
        arrayList.add("WHEELDOWN");
        arrayList.add("SCROLLUP");
        arrayList.add("SCROLLDOWN");
        arrayList.add("NONE");
        ajQ = list = arrayList.stream().distinct().sorted().toList();
        return list;
    }

    public static List<String> cS(String string) {
        String string2 = (string == null ? "" : string.trim()).toUpperCase(Locale.ROOT);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string3 : KeyBindParser.rF()) {
            if (!string3.startsWith(string2)) continue;
            arrayList.add(string3);
        }
        return arrayList;
    }

    public static String cT(String string) {
        return KeyBindParser.cU(string);
    }

    private static void ab(String string, String string2) {
        ajO.put(string, string2);
        ajP.putIfAbsent(string2, string);
    }

    private static String cU(String string) {
        String string2 = string == null ? "" : string.trim();
        String string3 = ajO.get(string2);
        if (string3 != null) {
            return string3;
        }
        return string2.toUpperCase(Locale.ROOT).replace(' ', '_');
    }

    private static String cV(String string) {
        if (string == null || string.isBlank()) {
            return "NONE";
        }
        String string2 = ajP.get(string.toUpperCase(Locale.ROOT));
        if (string2 != null) {
            return string2;
        }
        return string.toUpperCase(Locale.ROOT);
    }

    private static boolean cW(String string) {
        return string.equalsIgnoreCase("UNKNOWN") || string.equalsIgnoreCase("WORLD_1") || string.equalsIgnoreCase("WORLD_2");
    }

    private static String bj(int n) {
        for (Field field : GLFW.class.getDeclaredFields()) {
            String string = field.getName();
            if (!string.startsWith("GLFW_KEY_")) continue;
            try {
                if (field.getInt(null) != n) continue;
                return string.substring("GLFW_KEY_".length());
            }
            catch (IllegalAccessException illegalAccessException) {
            }
        }
        return null;
    }

    static {
        KeyBindParser.ab("'", "APOSTROPHE");
        KeyBindParser.ab(";", "SEMICOLON");
        KeyBindParser.ab("[", "LEFT_BRACKET");
        KeyBindParser.ab("]", "RIGHT_BRACKET");
        KeyBindParser.ab("/", "SLASH");
        KeyBindParser.ab(".", "PERIOD");
        KeyBindParser.ab(",", "COMMA");
        KeyBindParser.ab("\\", "BACKSLASH");
        KeyBindParser.ab("-", "MINUS");
        KeyBindParser.ab("=", "EQUAL");
        KeyBindParser.ab("`", "GRAVE_ACCENT");
        KeyBindParser.ab("!", "1");
        KeyBindParser.ab("@", "2");
        KeyBindParser.ab("#", "3");
        KeyBindParser.ab("$", "4");
        KeyBindParser.ab("%", "5");
        KeyBindParser.ab("^", "6");
        KeyBindParser.ab("&", "7");
        KeyBindParser.ab("*", "8");
        KeyBindParser.ab("(", "9");
        KeyBindParser.ab(")", "0");
        KeyBindParser.ab("_", "MINUS");
        KeyBindParser.ab("+", "EQUAL");
    }
}
