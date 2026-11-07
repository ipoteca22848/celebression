package ru.method.celebression.api.setting;

import java.util.Locale;
import ru.method.celebression.render.FrameTimeTracker;

public final class BooleanParser {
    private BooleanParser() {
    }

    public static boolean parseBoolean(String string, boolean bl) {
        String string2 = FrameTimeTracker.cQ(string).toLowerCase(Locale.ROOT);
        if (string2.equals("true") || string2.equals("1") || string2.equals("yes") || string2.equals("on")) {
            return true;
        }
        if (string2.equals("false") || string2.equals("0") || string2.equals("no") || string2.equals("off")) {
            return false;
        }
        return bl;
    }
}

