package ru.method.celebression.command;

import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_5250;

public final class CrasherChatOutput {
    private CrasherChatOutput() {
    }

    public static void e(class_2561 class_25612) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102.field_1705 != null) {
            class_3102.field_1705.method_1743().method_1812(class_25612);
        }
    }

    public static class_5250 a(String string, class_124 ... class_124Array) {
        return class_2561.method_43470((String)string).method_27695(class_124Array);
    }

    public static void bN(String string) {
        CrasherChatOutput.e((class_2561)CrasherChatOutput.a(string, class_124.field_1076));
    }

    public static void bO(String string) {
        CrasherChatOutput.e((class_2561)CrasherChatOutput.a(string, class_124.field_1080));
    }

    public static void bP(String string) {
        CrasherChatOutput.e((class_2561)CrasherChatOutput.a(string, class_124.field_1061));
    }

    public static void a(long l, long l2) {
        double d = Math.max((double)l2 / 1.0E9, 0.001);
        long l3 = (long)((double)l / d);
        class_5250 class_52502 = CrasherChatOutput.a(l + " blocks have been changed.", class_124.field_1076);
        class_52502.method_10852((class_2561)CrasherChatOutput.a(String.format(" (%.3fs, %d blocks/sec)", d, l3), class_124.field_1080));
        CrasherChatOutput.e((class_2561)class_52502);
    }
}

