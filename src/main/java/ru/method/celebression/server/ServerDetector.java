package ru.method.celebression.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1934;
import net.minecraft.class_266;
import net.minecraft.class_345;
import net.minecraft.class_5250;
import net.minecraft.class_640;
import net.minecraft.class_8646;
import net.minecraft.class_9013;
import net.minecraft.class_9015;
import net.minecraft.class_9022;
import net.minecraft.class_9025;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.mixins.accessors.IBossBarHud;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.modules.impl.player.ScoreboardHealthModule;

public final class ServerDetector
implements MinecraftClientAccess {
    private static final Pattern akR = Pattern.compile("(?iu)(?<![\\p{L}\\p{N}_])(pvp|\u043f\u0432\u043f|\u0431\u043e\u044f|\u0431\u043e\u0439)(?![\\p{L}\\p{N}_])");
    private static final Pattern akS = Pattern.compile("(\\d+):(\\d{2})");
    private static final Pattern akT = Pattern.compile("(\\d+)\\s*(?:\u0441\u0435\u043a|s\\b|sec)");

    public static boolean di(String string) {
        if (aiG.method_1562() == null || aiG.method_1562().method_45734() == null) {
            return false;
        }
        return ServerDetector.aiG.method_1562().method_45734().field_3761.toLowerCase().contains(string);
    }

    public static boolean sh() {
        if (aiG.method_1562() == null || aiG.method_1562().method_45734() == null) {
            return false;
        }
        String string = ServerDetector.aiG.method_1562().method_45734().field_3761.toLowerCase();
        String string2 = aiG.method_1562().method_52790();
        if (string.contains("funtime")) {
            return true;
        }
        if (string.contains("spookytime")) {
            return true;
        }
        if (string.contains("funsky")) {
            return true;
        }
        if (string.contains("space-times")) {
            return true;
        }
        if (string.contains("skytime")) {
            return true;
        }
        if (string2 != null && string2.toLowerCase().contains("funtime")) {
            return true;
        }
        return ServerDetector.so().toLowerCase().contains("funtime");
    }

    public static int t(class_1657 class_16572) {
        if (class_16572 == null || aiG.method_1562() == null) {
            return 0;
        }
        class_640 class_6402 = aiG.method_1562().method_2871(class_16572.method_5667());
        if (class_6402 == null) {
            class_6402 = aiG.method_1562().method_2874(class_16572.method_7334().getName());
        }
        return class_6402 != null ? class_6402.method_2959() : 0;
    }

    public static boolean si() {
        return ServerDetector.sj();
    }

    public static boolean sj() {
        for (String string : ServerDetector.sn()) {
            if (!ServerDetector.dj(string)) continue;
            return true;
        }
        return false;
    }

    public static String sk() {
        for (String string : ServerDetector.sn()) {
            if (!ServerDetector.dj(string)) continue;
            return string;
        }
        return "";
    }

    public static String sl() {
        String string = ServerDetector.sk();
        return string.isBlank() ? "" : string.replaceAll("\\D", "");
    }

    public static int sm() {
        Matcher matcher;
        String string = ServerDetector.sk();
        if (string.isBlank()) {
            return -1;
        }
        Matcher matcher2 = akS.matcher(string);
        if (matcher2.find()) {
            try {
                return Integer.parseInt(matcher2.group(1)) * 60 + Integer.parseInt(matcher2.group(2));
            }
            catch (NumberFormatException numberFormatException) {
            }
        }
        if ((matcher = akT.matcher(string.toLowerCase())).find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            }
            catch (NumberFormatException numberFormatException) {
            }
        }
        return -1;
    }

    public static boolean dj(String string) {
        return string != null && !string.isBlank() && akR.matcher(string).find();
    }

    public static List<String> sn() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (class_345 class_3452 : ((IBossBarHud)ServerDetector.aiG.field_1705.method_1740()).getBossBars().values()) {
            if (class_3452 == null) continue;
            arrayList.add(class_3452.method_5414().getString());
        }
        return arrayList;
    }

    public static String so() {
        for (class_345 class_3452 : ((IBossBarHud)ServerDetector.aiG.field_1705.method_1740()).getBossBars().values()) {
            if (class_3452 == null) continue;
            return class_3452.method_5414().getString();
        }
        return "";
    }

    public static List<String> sp() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (class_640 class_6402 : aiG.method_1562().method_2880()) {
            if (class_6402 == null) {
                return Collections.emptyList();
            }
            if (class_6402.method_2958() != class_1934.field_9219) continue;
            arrayList.add(class_6402.method_2966().getName());
        }
        return arrayList;
    }

    public static float a(class_1309 class_13092, boolean bl) {
        if (Celebression.getInstance().getModuleManager().getModule(ScoreboardHealthModule.class).isToggled()) {
            if (class_13092 instanceof class_1657) {
                class_1657 class_16572 = (class_1657)class_13092;
                if (class_16572.method_7327().method_1189(class_8646.field_45158) == null) {
                    return class_13092.method_6032() + (bl ? class_13092.method_6067() : 0.0f);
                }
                class_266 class_2662 = class_16572.method_7327().method_1189(class_8646.field_45158);
                if (class_2662 == null) {
                    return class_13092.method_6032() + (bl ? class_13092.method_6067() : 0.0f);
                }
                class_9013 class_90132 = class_16572.method_7327().method_55430((class_9015)class_16572, class_2662);
                class_5250 class_52502 = class_9013.method_55398((class_9013)class_90132, (class_9022)class_2662.method_55380((class_9022)class_9025.field_47566));
                String string = class_52502.getString().replaceAll("\\D", "");
                if (string.isEmpty()) {
                    return class_13092.method_6032() + (bl ? class_13092.method_6067() : 0.0f);
                }
                try {
                    return Float.parseFloat(string);
                }
                catch (NumberFormatException numberFormatException) {
                    return class_13092.method_6032() + (bl ? class_13092.method_6067() : 0.0f);
                }
            }
            return class_13092.method_6032() + (bl ? class_13092.method_6067() : 0.0f);
        }
        return class_13092.method_6032() + (bl ? class_13092.method_6067() : 0.0f);
    }

    @Generated
    private ServerDetector() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

