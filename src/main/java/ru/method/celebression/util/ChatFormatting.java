package ru.method.celebression.util;

import java.util.Arrays;
import java.util.List;
import lombok.Generated;
import net.minecraft.class_1074;
import net.minecraft.class_2558;
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_2583;
import net.minecraft.class_5250;
import net.minecraft.class_5251;
import ru.method.celebression.Celebression;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.utils.render.ColorUtils;

public final class ChatFormatting {
    public static final int ajk = 8246268;
    public static final int ajl = 16317180;
    public static final int ajm = 9741240;
    public static final int ajn = 16096779;
    public static final int ajo = 10980346;
    public static final int ajp = 16317180;
    public static final int ajq = 16096779;
    public static final int ajr = 16281969;

    public static class_5250 x(String string, String string2) {
        class_5250 class_52502 = class_2561.method_43470((String)"").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(9741240))));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)(ChatFormatting.rw() + string)).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(8246268))).method_10982(Boolean.valueOf(true))));
        if (string2 != null && !string2.isBlank()) {
            class_52502.method_10852((class_2561)class_2561.method_43470((String)"\n"));
            class_52502.method_10852((class_2561)class_2561.method_43470((String)string2).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(9741240)))));
        }
        return class_52502;
    }

    public static class_5250 y(String string, String string2) {
        class_5250 class_52502 = class_2561.method_43470((String)"");
        class_52502.method_10852((class_2561)class_2561.method_43470((String)"\u2022 ").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(9741240)))));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)(string == null ? "" : string)).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(8246268)))));
        if (string2 != null && !string2.isBlank()) {
            class_52502.method_10852((class_2561)class_2561.method_43470((String)string2).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(16317180)))));
        }
        return class_52502;
    }

    public static class_5250 z(String string, String string2) {
        class_5250 class_52502 = ChatFormatting.y(string, "");
        class_52502.method_10852((class_2561)class_2561.method_43470((String)": ").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(9741240)))));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)(string2 == null ? "" : string2)).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(16317180)))));
        return class_52502;
    }

    public static class_5250 a(String string, String string2, String string3, int n) {
        class_5250 class_52502 = ChatFormatting.x(string, string2);
        if (string3 != null && !string3.isBlank()) {
            ChatFormatting.a(class_52502, ChatFormatting.a(string3, n, false));
        }
        return class_52502;
    }

    public static void b(String string, String string2, String string3, int n) {
        ChatOutput.b(ChatFormatting.a(string, string2, string3, n));
    }

    public static void a(String string, String string2, String[] stringArray, List<String> list) {
        ChatOutput.b(ChatFormatting.b(string, string2, stringArray, list));
    }

    public static class_5250 b(String string, String string2, String[] stringArray, List<String> list) {
        return ChatFormatting.b(string, string2, stringArray, list, null, 16317180);
    }

    public static void a(String string, String string2, String[] stringArray, List<String> list, String string3, int n) {
        ChatOutput.b(ChatFormatting.b(string, string2, stringArray, list, string3, n));
    }

    public static class_5250 b(String string, String string2, String[] stringArray, List<String> list, String string3, int n) {
        class_5250 class_52502 = class_2561.method_43470((String)"").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(9741240))));
        class_52502.method_10852((class_2561)ChatFormatting.a(string, 8246268, true));
        if (string3 != null && !string3.isBlank()) {
            class_52502.method_10852((class_2561)class_2561.method_43470((String)"\n"));
            class_52502.method_10852((class_2561)ChatFormatting.a(string3, n, false));
        }
        if (list != null && !list.isEmpty()) {
            for (String string4 : list) {
                if (string4 == null || string4.isBlank()) continue;
                String string5 = ChatFormatting.rw() + string4;
                class_52502.method_10852((class_2561)class_2561.method_43470((String)"\n"));
                class_52502.method_10852((class_2561)class_2561.method_43470((String)"\u2022 ").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(9741240)))));
                class_52502.method_10852((class_2561)class_2561.method_43470((String)string5).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(10980346))).method_10958(new class_2558(class_2558.class_2559.field_11745, string5 + " ")).method_10949(new class_2568(class_2568.class_5247.field_24342, class_2561.method_43470((String)class_1074.method_4662((String)"commands.help.click_hint", (Object[])new Object[0]))))));
            }
        }
        return class_52502;
    }

    public static class_5250 a(String string, int n, class_2558.class_2559 class_25592, String string2, String string3) {
        class_2583 class_25832 = class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(n)));
        if (class_25592 != null && string2 != null && !string2.isBlank()) {
            class_25832 = class_25832.method_10958(new class_2558(class_25592, string2));
        }
        if (string3 != null && !string3.isBlank()) {
            class_25832 = class_25832.method_10949(new class_2568(class_2568.class_5247.field_24342, class_2561.method_43470((String)string3)));
        }
        return class_2561.method_43470((String)(" [" + string + "]")).method_10862(class_25832);
    }

    public static class_5250 cI(String string) {
        String string2 = ChatFormatting.cJ(string);
        return ChatFormatting.a("\u0423\u0434\u0430\u043b\u0438\u0442\u044c", 16281969, class_2558.class_2559.field_11750, string2, string);
    }

    public static String cJ(String string) {
        String normalized = string == null ? "" : string.trim();
        if (!normalized.isEmpty() && !normalized.startsWith("/")) {
            normalized = "/" + normalized;
        }
        return normalized;
    }

    public static void a(class_5250 class_52502, class_5250 class_52503) {
        if (class_52502 == null || class_52503 == null) {
            return;
        }
        class_52502.method_10852((class_2561)class_2561.method_43470((String)"\n"));
        class_52502.method_10852((class_2561)class_52503);
    }

    public static void cK(String string) {
        ChatOutput.b(ChatFormatting.e(string, 16317180));
    }

    public static void cL(String string) {
        ChatOutput.b(ChatFormatting.e(string, 16317180));
    }

    public static void cM(String string) {
        ChatOutput.b(ChatFormatting.e(string, 16096779));
    }

    public static void cN(String string) {
        ChatOutput.b(ChatFormatting.e(string, 16281969));
    }

    public static class_5250 a(String string, int n, boolean bl) {
        return class_2561.method_43470((String)(string == null ? "" : string)).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(n))).method_10982(Boolean.valueOf(bl)));
    }

    private static class_5250 e(String string, int n) {
        return class_2561.method_43470((String)(string == null ? "" : string)).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)ChatFormatting.bi(n))));
    }

    public static int bh(int n) {
        return ChatFormatting.bi(n);
    }

    private static String d(String[] stringArray) {
        if (stringArray == null || stringArray.length == 0) {
            return "";
        }
        return Arrays.stream(stringArray).filter(string -> string != null && !string.isBlank()).map(string -> ChatFormatting.rw() + string).distinct().reduce((string, string2) -> string + ", " + string2).orElse("");
    }

    private static int bi(int n) {
        if (n == 8246268 || n == 10980346) {
            return ColorUtils.getSecondaryColor().getRGB() & 0xFFFFFF;
        }
        return n;
    }

    private static String rw() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getCommandManager() == null) {
            return ".";
        }
        return celebression.getCommandManager().fj();
    }

    @Generated
    private ChatFormatting() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
