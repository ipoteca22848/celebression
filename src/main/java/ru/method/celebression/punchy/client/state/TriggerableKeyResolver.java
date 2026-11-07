package ru.method.celebression.punchy.client.state;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

final class TriggerableKeyResolver {
    private static final Map<String, Integer> CACHE = new HashMap<String, Integer>();

    private TriggerableKeyResolver() {
    }

    static int resolveKeyCode(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = TriggerableKeyResolver.normalize(string);
            if (string2.isEmpty()) {
                return -1;
            }
            Integer n = CACHE.get(string2);
            if (n != null) {
                return n;
            }
            int n2 = TriggerableKeyResolver.resolveNormalized(string2);
            CACHE.put(string2, n2);
            return n2;
        }
        return -1;
    }

    static boolean matches(String string, int n) {
        return n < 0 ? false : TriggerableKeyResolver.resolveKeyCode(string) == n;
    }

    private static String normalize(String string) {
        String string2 = string.trim().toUpperCase(Locale.ROOT);
        if (string2.startsWith("KEY.KEYBOARD.")) {
            string2 = string2.substring("KEY.KEYBOARD.".length());
        } else if (string2.startsWith("KEY.KEYPAD.")) {
            string2 = string2.substring("KEY.KEYPAD.".length());
        }
        return string2.replace(" ", "").replace("-", "").replace("_", "");
    }

    private static int resolveNormalized(String string) {
        int n;
        if (string.length() == 1) {
            char c = string.charAt(0);
            if (c >= 'A' && c <= 'Z') {
                return 65 + (c - 65);
            }
            if (c >= '0' && c <= '9') {
                return 48 + (c - 48);
            }
        }
        if (string.startsWith("F")) {
            String string2 = string.substring(1);
            try {
                n = Integer.parseInt(string2);
                if (n >= 1 && n <= 25) {
                    return 290 + (n - 1);
                }
            }
            catch (NumberFormatException numberFormatException) {
            }
        }
        if (string.startsWith("NUMPAD") || string.startsWith("KP")) {
            String string3 = string.replace("NUMPAD", "").replace("KP", "");
            if (string3.length() == 1 && (n = (int)string3.charAt(0)) >= 48 && n <= 57) {
                return 320 + (n - 48);
            }
            if ("PLUS".equals(string3)) {
                return 334;
            }
            if ("MINUS".equals(string3)) {
                return 333;
            }
            if ("MULTIPLY".equals(string3)) {
                return 332;
            }
            if ("DIVIDE".equals(string3)) {
                return 331;
            }
            if ("DECIMAL".equals(string3) || "DOT".equals(string3)) {
                return 330;
            }
            if ("ENTER".equals(string3)) {
                return 335;
            }
        }
        return switch (string) {
            case "ESC", "ESCAPE" -> 256;
            case "TAB" -> 258;
            case "ENTER", "RETURN" -> 257;
            case "SPACE" -> 32;
            case "BACKSPACE" -> 259;
            case "INSERT", "INS" -> 260;
            case "DELETE", "DEL" -> 261;
            case "HOME" -> 268;
            case "END" -> 269;
            case "PAGEUP", "PGUP" -> 266;
            case "PAGEDOWN", "PGDN" -> 267;
            case "UP" -> 265;
            case "DOWN" -> 264;
            case "LEFT" -> 263;
            case "RIGHT" -> 262;
            case "LEFTSHIFT", "LSHIFT" -> 340;
            case "RIGHTSHIFT", "RSHIFT" -> 344;
            case "SHIFT" -> 340;
            case "LEFTCTRL", "LCTRL", "CONTROL" -> 341;
            case "RIGHTCTRL", "RCTRL" -> 345;
            case "LEFTALT", "LALT", "ALT" -> 342;
            case "RIGHTALT", "RALT" -> 346;
            case "CAPSLOCK" -> 280;
            case "SCROLLLOCK" -> 281;
            case "NUMLOCK" -> 282;
            case "PRINTSCREEN", "PRTSCR", "PRTSC" -> 283;
            case "PAUSE" -> 284;
            case "APOSTROPHE", "QUOTE" -> 39;
            case "COMMA" -> 44;
            case "PERIOD", "DOT" -> 46;
            case "SLASH" -> 47;
            case "SEMICOLON" -> 59;
            case "EQUAL", "PLUS" -> 61;
            case "MINUS", "DASH" -> 45;
            case "LEFTBRACKET", "LBRACKET" -> 91;
            case "RIGHTBRACKET", "RBRACKET" -> 93;
            case "BACKSLASH" -> 92;
            case "GRAVE", "TILDE" -> 96;
            default -> -1;
        };
    }
}

