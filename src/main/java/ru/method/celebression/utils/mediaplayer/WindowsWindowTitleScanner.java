package ru.method.celebression.utils.mediaplayer;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public final class WindowsWindowTitleScanner {
    private static final int MAX_TITLE_LENGTH = 512;

    private WindowsWindowTitleScanner() {
    }

    public static WindowMediaCandidate scanBestCandidate() {
        if (!WindowsWindowTitleScanner.isWindows()) {
            return null;
        }
        ArrayList<WindowMediaCandidate> arrayList = new ArrayList<>();
        User32 user32 = User32.INSTANCE;
        user32.EnumWindows((hWND, pointer) -> {
            if (!user32.IsWindowVisible(hWND)) {
                return true;
            }
            if (user32.GetWindow(hWND, new WinDef.DWORD(4L)) != null) {
                return true;
            }
            char[] cArray = new char[512];
            int n = user32.GetWindowText(hWND, cArray, cArray.length);
            if (n <= 0) {
                return true;
            }
            String string = Native.toString((char[])cArray).trim();
            if (string.isEmpty()) {
                return true;
            }
            String string2 = WindowsWindowTitleScanner.resolveProcessName(user32, hWND);
            WindowMediaCandidate windowMediaCandidate = WindowsWindowTitleScanner.parseCandidate(string2, string);
            if (windowMediaCandidate != null) {
                arrayList.add(windowMediaCandidate);
            }
            return true;
        }, null);
        return arrayList.stream().max(Comparator.comparingInt(WindowMediaCandidate::score)).orElse(null);
    }

    private static String resolveProcessName(User32 user32, WinDef.HWND hWND) {
        IntByReference intByReference = new IntByReference();
        user32.GetWindowThreadProcessId(hWND, intByReference);
        int n = intByReference.getValue();
        if (n <= 0) {
            return "unknown";
        }
        try {
            String string = Kernel32Util.QueryFullProcessImageName((int)n, (int)0);
            if (string == null || string.isBlank()) {
                return "pid-" + n;
            }
            int n2 = Math.max(string.lastIndexOf(92), string.lastIndexOf(47));
            return n2 >= 0 ? string.substring(n2 + 1) : string;
        }
        catch (Throwable throwable) {
            return "pid-" + n;
        }
    }

    static WindowMediaCandidate parseCandidate(String string, String string2) {
        String string3;
        if (string == null || string2 == null || string2.isBlank()) {
            return null;
        }
        String string4 = string.toLowerCase(Locale.ROOT);
        if (WindowsWindowTitleScanner.isIgnoredWindow(string4, string3 = string2.toLowerCase(Locale.ROOT))) {
            return null;
        }
        int n = 0;
        boolean bl = WindowsWindowTitleScanner.containsAny(string3, "youtube", "spotify", "\u044f\u043d\u0434\u0435\u043a\u0441 \u043c\u0443\u0437\u044b\u043a\u0430", "yandex music", "soundcloud", "\u043c\u0443\u0437\u044b\u043a\u0430");
        if (WindowsWindowTitleScanner.containsAny(string4, "spotify")) {
            n += 100;
        }
        if (WindowsWindowTitleScanner.containsAny(string4, "yandex", "music", "aimp", "foobar", "vlc", "winamp")) {
            n += 85;
        }
        if (WindowsWindowTitleScanner.containsAny(string4, "chrome", "msedge", "firefox", "opera", "zen", "browser")) {
            if (!bl) {
                return null;
            }
            n += 55;
        }
        if (bl) {
            n += 25;
        }
        if (string2.contains(" - ") || string2.contains(" \u2014 ")) {
            n += 10;
        }
        if (n <= 0) {
            return null;
        }
        String string5 = WindowsWindowTitleScanner.cleanWindowMediaTitle(string2);
        if (string5.isBlank()) {
            return null;
        }
        return new WindowMediaCandidate(string, string5, string, n);
    }

    private static boolean isIgnoredWindow(String string, String string2) {
        if (WindowsWindowTitleScanner.containsAny(string, "java", "minecraft", "tlauncher", "javaw")) {
            return true;
        }
        if (WindowsWindowTitleScanner.containsAny(string2, "minecraft", "celebression", "primevisuals")) {
            return true;
        }
        return string2.equals("settings") || string2.equals("\u043f\u0430\u0440\u0430\u043c\u0435\u0442\u0440\u044b");
    }

    static String cleanWindowMediaTitle(String string) {
        String[] stringArray;
        String string2;
        String string3 = string.trim();
        if (string3.startsWith(string2 = "\u042f\u043d\u0434\u0435\u043a\u0441 \u041c\u0443\u0437\u044b\u043a\u0430 \u2014")) {
            string3 = string3.substring(string2.length()).trim();
        }
        for (String string4 : stringArray = new String[]{" - Google Chrome", " - \u042f\u043d\u0434\u0435\u043a\u0441 \u0411\u0440\u0430\u0443\u0437\u0435\u0440", " - Yandex Browser", " - Microsoft Edge", " - Mozilla Firefox", " - Opera", " - YouTube"}) {
            if (!string3.endsWith(string4)) continue;
            string3 = string3.substring(0, string3.length() - string4.length()).trim();
        }
        return string3.length() > 96 ? string3.substring(0, 96).trim() : string3;
    }

    private static boolean containsAny(String string, String ... stringArray) {
        for (String string2 : stringArray) {
            if (string2 == null || string2.isEmpty() || !string.contains(string2)) continue;
            return true;
        }
        return false;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
    }

    public record WindowMediaCandidate(String owner, String title, String artist, int score) {
    }
}
