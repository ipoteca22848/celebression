package ru.method.celebression.animation;

public final class EasingMath {
    private EasingMath() {
    }

    public static int w(String string, String string2) {
        int n;
        int n2 = Math.min(string.length(), string2.length());
        for (n = 0; n < n2 && string.charAt(n) == string2.charAt(n); ++n) {
        }
        return n;
    }

    public static int d(String string, String string2, int n) {
        int n2;
        int n3 = string.length();
        int n4 = string2.length();
        int n5 = Math.min(n3 - n, n4 - n);
        for (n2 = 0; n2 < n5 && string.charAt(n3 - 1 - n2) == string2.charAt(n4 - 1 - n2); ++n2) {
        }
        return n2;
    }

    public static String a(String string, int n, int n2) {
        if (string == null) {
            return "";
        }
        int n3 = Math.max(0, Math.min(n, string.length()));
        int n4 = Math.max(n3, Math.min(n2, string.length()));
        return string.substring(n3, n4);
    }
}

