package ru.method.celebression.server;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_642;

public class ServerListSnapshot {
    private static final List<class_642> akU = new ArrayList<class_642>();

    public static List<class_642> sr() {
        return new ArrayList<class_642>(akU);
    }

    public static boolean q(class_642 class_6422) {
        return class_6422 != null && akU.stream().anyMatch(class_6423 -> class_6423.field_3761.equals(class_6422.field_3761) && class_6423.field_3752.equals(class_6422.field_3752));
    }

    public static boolean dk(String string) {
        if (string == null) {
            return false;
        }
        for (class_642 class_6422 : akU) {
            if (!string.equals(class_6422.field_3761)) continue;
            return true;
        }
        return false;
    }

    public static class_642 dl(String string) {
        if (string == null) {
            return null;
        }
        for (class_642 class_6422 : akU) {
            if (!string.equals(class_6422.field_3761)) continue;
            return class_6422;
        }
        return null;
    }

    public static boolean r(class_642 class_6422) {
        return ServerListSnapshot.q(class_6422);
    }

    public static int ss() {
        return akU.size();
    }
}

