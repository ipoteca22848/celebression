package ru.method.celebression.command;

import java.util.Locale;
import net.minecraft.class_124;
import net.minecraft.class_1934;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_5250;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import ru.method.celebression.command.CrasherChatOutput;
import ru.method.celebression.modules.impl.player.CrasherModule;
import ru.method.celebression.modules.impl.render.cosmetic.DyeColorResolver;

public final class CrasherCommandProcessor {
    private final CrasherModule yA;
    private class_2338 yB;
    private class_2338 yC;

    public CrasherCommandProcessor(CrasherModule crasherModule) {
        this.yA = crasherModule;
    }

    public void b(class_2338 class_23382) {
        this.yB = class_23382.method_10062();
        CrasherChatOutput.bN("First position set to (" + this.yB.method_10263() + ", " + this.yB.method_10264() + ", " + this.yB.method_10260() + ")" + this.iq() + ".");
    }

    public void c(class_2338 class_23382) {
        this.yC = class_23382.method_10062();
        CrasherChatOutput.bN("Second position set to (" + this.yC.method_10263() + ", " + this.yC.method_10264() + ", " + this.yC.method_10260() + ")" + this.iq() + ".");
    }

    private String iq() {
        if (this.yB == null || this.yC == null) {
            return "";
        }
        long l = (long)(Math.abs(this.yB.method_10263() - this.yC.method_10263()) + 1) * (long)(Math.abs(this.yB.method_10264() - this.yC.method_10264()) + 1) * (long)(Math.abs(this.yB.method_10260() - this.yC.method_10260()) + 1);
        return " (" + l + " blocks)";
    }

    public void ir() {
        this.yB = null;
        this.yC = null;
    }

    public boolean bK(String string) {
        String string2;
        class_310 class_3102 = class_310.method_1551();
        class_746 class_7462 = class_3102.field_1724;
        if (class_7462 == null) {
            return false;
        }
        String string3 = string.trim();
        if (string3.isEmpty()) {
            return false;
        }
        String[] stringArray = string3.split("\\s+");
        switch (string2 = stringArray[0].toLowerCase(Locale.ROOT)) {
            case "op": {
                return this.a(class_7462, stringArray, true);
            }
            case "deop": {
                return this.a(class_7462, stringArray, false);
            }
            case "gm": 
            case "gamemode": {
                return this.a(class_3102, stringArray);
            }
            case "kickall": {
                return this.is();
            }
            case "broadcast": 
            case "bc": {
                return this.a(class_7462, string3, stringArray[0].length());
            }
            case "/wand": {
                this.yA.equipSelectionWand();
                CrasherChatOutput.bN("Left click: select pos #1; Right click: select pos #2");
                CrasherChatOutput.bO("(Use //sel to switch modes)");
                return true;
            }
            case "/desel": 
            case "/sel": {
                this.ir();
                CrasherChatOutput.bN("Selection cleared.");
                return true;
            }
            case "/pos1": {
                this.b(class_7462.method_24515());
                return true;
            }
            case "/pos2": {
                this.c(class_7462.method_24515());
                return true;
            }
            case "/set": {
                return this.b(stringArray);
            }
            case "/sphere": {
                return this.b(class_7462, stringArray, false);
            }
            case "/hsphere": {
                return this.b(class_7462, stringArray, true);
            }
            case "/undo": {
                CrasherChatOutput.bN("Undo successful.");
                return true;
            }
        }
        return false;
    }

    private boolean a(class_746 class_7462, String[] stringArray, boolean bl) {
        String string = stringArray.length > 1 ? stringArray[1] : class_7462.method_7334().getName();
        this.yA.s(bl);
        CrasherChatOutput.e((class_2561)CrasherChatOutput.a("Made " + string + (bl ? " a server operator" : " no longer a server operator"), class_124.field_1068));
        return true;
    }

    private boolean a(class_310 class_3102, String[] stringArray) {
        if (stringArray.length < 2) {
            CrasherChatOutput.bP("Incorrect argument for command");
            return true;
        }
        class_1934 class_19342 = CrasherCommandProcessor.bL(stringArray[1]);
        if (class_19342 == null) {
            CrasherChatOutput.bP("Unknown game mode: " + stringArray[1]);
            return true;
        }
        if (class_3102.field_1761 != null) {
            class_3102.field_1761.method_2907(class_19342);
        }
        if (class_3102.field_1724 != null) {
            boolean bl = class_3102.field_1724.method_31549().field_7478 = class_19342 == class_1934.field_9220 || class_19342 == class_1934.field_9219;
            if (!class_3102.field_1724.method_31549().field_7478) {
                class_3102.field_1724.method_31549().field_7479 = false;
            }
        }
        String string = CrasherCommandProcessor.a(class_19342);
        CrasherChatOutput.e((class_2561)CrasherChatOutput.a("Set own game mode to " + string, class_124.field_1068));
        CrasherChatOutput.e((class_2561)CrasherChatOutput.a("Your game mode has been updated to " + string, class_124.field_1068));
        return true;
    }

    private static class_1934 bL(String string) {
        return switch (string.toLowerCase(Locale.ROOT)) {
            case "0", "s", "survival" -> class_1934.field_9215;
            case "1", "c", "creative" -> class_1934.field_9220;
            case "2", "a", "adventure" -> class_1934.field_9216;
            case "3", "sp", "spectator" -> class_1934.field_9219;
            default -> null;
        };
    }

    private static String a(class_1934 class_19342) {
        return switch (class_19342) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case class_1934.field_9215 -> "Survival Mode";
            case class_1934.field_9220 -> "Creative Mode";
            case class_1934.field_9216 -> "Adventure Mode";
            case class_1934.field_9219 -> "Spectator Mode";
        };
    }

    private boolean is() {
        int n = this.yA.ij();
        CrasherChatOutput.e((class_2561)CrasherChatOutput.a("Kicked " + n + " player" + (n == 1 ? "" : "s") + " from the server", class_124.field_1068));
        return true;
    }

    private boolean a(class_746 class_7462, String string, int n) {
        String string2;
        String string3 = string2 = string.length() > n ? string.substring(n).trim() : "";
        if (string2.isEmpty()) {
            CrasherChatOutput.bP("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435: /broadcast <\u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435>");
            return true;
        }
        String string4 = class_7462.method_7334().getName();
        class_5250 class_52502 = class_2561.method_43470((String)"\u298f").method_27692(class_124.field_1060).method_10852((class_2561)class_2561.method_43470((String)"\u041e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u0435").method_27695(new class_124[]{class_124.field_1079, class_124.field_1067})).method_10852((class_2561)class_2561.method_43470((String)"\u2990").method_27692(class_124.field_1060)).method_10852((class_2561)class_2561.method_43470((String)(" " + string2 + " ")).method_27692(class_124.field_1065)).method_10852((class_2561)class_2561.method_43470((String)"[\u0410\u0432\u0442\u043e\u0440: ").method_27695(new class_124[]{class_124.field_1068, class_124.field_1056})).method_10852((class_2561)class_2561.method_43470((String)string4).method_27695(new class_124[]{class_124.field_1054, class_124.field_1056})).method_10852((class_2561)class_2561.method_43470((String)"]").method_27695(new class_124[]{class_124.field_1068, class_124.field_1056}));
        CrasherChatOutput.e((class_2561)class_52502);
        return true;
    }

    private boolean b(String[] stringArray) {
        if (stringArray.length < 2) {
            CrasherChatOutput.bP("Too few arguments.");
            CrasherChatOutput.bO("Usage: //set <pattern>");
            return true;
        }
        if (this.yB == null || this.yC == null) {
            CrasherChatOutput.bP("Make a region selection first.");
            return true;
        }
        class_2680 class_26802 = CrasherCommandProcessor.bM(stringArray[1]);
        if (class_26802 == null) {
            CrasherChatOutput.bP("Block not found: " + stringArray[1]);
            return true;
        }
        long l = System.nanoTime();
        long l2 = 0L;
        int n = Math.min(this.yB.method_10263(), this.yC.method_10263());
        int n2 = Math.min(this.yB.method_10264(), this.yC.method_10264());
        int n3 = Math.min(this.yB.method_10260(), this.yC.method_10260());
        int n4 = Math.max(this.yB.method_10263(), this.yC.method_10263());
        int n5 = Math.max(this.yB.method_10264(), this.yC.method_10264());
        int n6 = Math.max(this.yB.method_10260(), this.yC.method_10260());
        class_2338.class_2339 class_23392 = new class_2338.class_2339();
        for (int i = n; i <= n4; ++i) {
            for (int j = n2; j <= n5; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    class_23392.method_10103(i, j, k);
                    this.yA.a((class_2338)class_23392, class_26802);
                    ++l2;
                }
            }
        }
        CrasherChatOutput.a(l2, System.nanoTime() - l);
        this.yA.ic();
        return true;
    }

    private boolean b(class_746 class_7462, String[] stringArray, boolean bl) {
        double d;
        if (stringArray.length < 3) {
            CrasherChatOutput.bP("Too few arguments.");
            CrasherChatOutput.bO("Usage: " + (bl ? "//hsphere" : "//sphere") + " <pattern> <radius>");
            return true;
        }
        class_2680 class_26802 = CrasherCommandProcessor.bM(stringArray[1]);
        if (class_26802 == null) {
            CrasherChatOutput.bP("Block not found: " + stringArray[1]);
            return true;
        }
        try {
            d = Double.parseDouble(stringArray[2]);
        }
        catch (NumberFormatException numberFormatException) {
            CrasherChatOutput.bP("Expected a number; got '" + stringArray[2] + "'");
            return true;
        }
        if (d <= 0.0 || d > 128.0) {
            CrasherChatOutput.bP("Radius must be between 1 and 128.");
            return true;
        }
        long l = System.nanoTime();
        long l2 = 0L;
        class_2338 class_23382 = class_7462.method_24515();
        int n = (int)Math.ceil(d);
        class_2338.class_2339 class_23392 = new class_2338.class_2339();
        for (int i = -n; i <= n; ++i) {
            for (int j = -n; j <= n; ++j) {
                for (int k = -n; k <= n; ++k) {
                    double d2 = Math.sqrt(i * i + j * j + k * k);
                    if (d2 > d || bl && d2 < d - 1.0) continue;
                    class_23392.method_10103(class_23382.method_10263() + i, class_23382.method_10264() + j, class_23382.method_10260() + k);
                    this.yA.a((class_2338)class_23392, class_26802);
                    ++l2;
                }
            }
        }
        CrasherChatOutput.a(l2, System.nanoTime() - l);
        this.yA.ic();
        return true;
    }

    private static class_2680 bM(String string) {
        String string2;
        String string3 = string.toLowerCase(Locale.ROOT).trim();
        int n = string3.indexOf(91);
        if (n > 0) {
            string3 = string3.substring(0, n);
        }
        if (string3.matches("\\d+(:\\d+)?")) {
            string2 = DyeColorResolver.bQ(string3);
            if (string2 != null) {
                string3 = string2;
            } else {
                int n2 = Integer.parseInt(string3.split(":")[0]);
                class_2248 class_22482 = (class_2248)class_7923.field_41175.method_10200(n2);
                return class_22482 == null ? null : class_22482.method_9564();
            }
        }
        class_2960 identifier = class_2960.method_12829(string3.contains(":") ? string3 : "minecraft:" + string3);
        if (identifier == null) {
            return null;
        }
        if (!class_7923.field_41175.method_10250(identifier)) {
            return null;
        }
        class_2248 class_22483 = (class_2248)class_7923.field_41175.method_63535(identifier);
        return class_22483 == null ? null : class_22483.method_9564();
    }
}
