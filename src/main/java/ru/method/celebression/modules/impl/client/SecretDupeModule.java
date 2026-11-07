package ru.method.celebression.modules.impl.client;

import ru.method.celebression.api.setting.Setting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3532;
import net.minecraft.class_465;
import net.minecraft.class_5250;
import net.minecraft.class_5251;
import net.minecraft.class_693;
import net.minecraft.class_703;
import net.minecraft.class_742;
import net.minecraft.class_746;
import net.minecraft.class_9290;
import net.minecraft.class_9334;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.server.ServerTextPatternMatcher;
import ru.method.celebression.api.setting.SliderSetting;

public class SecretDupeModule
extends Module {
    private static final String tE = "\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f";
    private static final int tF = 200;
    private static final Pattern tG = Pattern.compile("^ah\\s+sell\\s+([\\d][\\d .,]*)\\s*(\u043a\u043a\u043a|kkk|\u043a\u043a|kk|\u043c\u043c|mm|\u043c|m|\u043a|k)?", 2);
    private final SliderSetting tH = new SliderSetting("settings.fakecosmetic.quantity", 1.0f, 1.0f, 64.0f, 1.0f);
    private static final int tI = 40;
    private static final double tJ = 1.4;
    private final List<DupeState> tK = new ArrayList<DupeState>();
    private int tL;
    private int tM = -1;
    private final List<DupeAction> tN = new ArrayList<DupeAction>();
    private static int tO = -900000;
    private static volatile long tP;
    private static final int tQ = 0xFF6600;
    private static final int tR = 0xFFFFE6;
    private static final int tS = 0xFFFF00;
    private static final int tT = 65280;

    public SecretDupeModule() {
        super("SecretDupe", ModuleCategory.qn);
        this.getSettings().add(this.tH);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.tK.clear();
        tP = 0L;
        this.tM = -1;
        this.tL = 0;
    }

    @Override
    public void onDisable() {
        this.gX();
        this.tK.clear();
        tP = 0L;
        this.tM = -1;
        this.tL = 0;
        super.onDisable();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (SecretDupeModule.fullNullCheck()) {
            return;
        }
        this.gY();
        this.gZ();
        this.hb();
        Iterator<DupeState> iterator = this.tK.iterator();
        while (iterator.hasNext()) {
            DupeState dupeState = iterator.next();
            if (--dupeState.tW > 0) continue;
            tP += dupeState.tV;
            this.a(dupeState.tV, dupeState.count);
            iterator.remove();
        }
    }

    private void gX() {
        for (DupeAction dupeAction : this.tN) {
            dupeAction.tU.method_31472();
        }
        this.tN.clear();
        this.tL = 0;
        if (SecretDupeModule.fullNullCheck()) {
            return;
        }
        class_1661 class_16612 = SecretDupeModule.aiG.field_1724.method_31548();
        for (int i = 0; i < class_16612.method_5439(); ++i) {
            if (!this.m(class_16612.method_5438(i))) continue;
            class_16612.method_5447(i, class_1799.field_8037);
        }
    }

    private void gY() {
        int n = Math.round(((Float)this.tH.jT()).floatValue());
        if (n == this.tM) {
            return;
        }
        for (DupeAction dupeAction : this.tN) {
            dupeAction.tU.method_31472();
        }
        this.tN.clear();
        this.tM = n;
        this.tL = n;
    }

    private void gZ() {
        if (this.tN.isEmpty()) {
            return;
        }
        Iterator<DupeAction> iterator = this.tN.iterator();
        while (iterator.hasNext()) {
            int n;
            DupeAction dupeAction = iterator.next();
            class_1542 class_15422 = dupeAction.tU;
            if (class_15422.method_31481() || class_15422.method_37908() != SecretDupeModule.aiG.field_1687) {
                iterator.remove();
                continue;
            }
            class_243 class_2432 = class_15422.method_18798();
            if (class_15422.method_24828() && class_2432.field_1351 > 0.0) {
                class_15422.method_18800(class_2432.field_1352, 0.0, class_2432.field_1350);
            }
            if (++dupeAction.age < 40) continue;
            class_1657 class_16572 = this.a(class_15422);
            if (class_16572 != null) {
                this.a(class_15422, (class_1297)class_16572);
                class_15422.method_31472();
                iterator.remove();
                continue;
            }
            if (class_15422.method_5858((class_1297)SecretDupeModule.aiG.field_1724) > 1.9599999999999997 || (n = 64 - this.tL) <= 0) continue;
            int n2 = Math.min(n, class_15422.method_6983().method_7947());
            this.a(class_15422, (class_1297)SecretDupeModule.aiG.field_1724);
            this.tL += n2;
            class_15422.method_6983().method_7934(n2);
            if (!class_15422.method_6983().method_7960()) continue;
            class_15422.method_31472();
            iterator.remove();
        }
    }

    private void a(class_1542 class_15422, class_1297 class_12972) {
        SecretDupeModule.aiG.field_1713.method_3058((class_703)new class_693(aiG.method_1561(), SecretDupeModule.aiG.field_1687, (class_1297)class_15422, class_12972));
        SecretDupeModule.aiG.field_1687.method_43128((class_1657)SecretDupeModule.aiG.field_1724, class_12972.method_23317(), class_12972.method_23318(), class_12972.method_23321(), class_3417.field_15197, class_3419.field_15248, 0.2f, ((SecretDupeModule.aiG.field_1724.method_59922().method_43057() - SecretDupeModule.aiG.field_1724.method_59922().method_43057()) * 0.7f + 1.0f) * 2.0f);
    }

    public static boolean a(int n, int n2, class_1713 class_17132, class_1657 class_16572) {
        boolean bl;
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return false;
        }
        SecretDupeModule secretDupeModule = celebression.getModuleManager().getModule(SecretDupeModule.class);
        if (secretDupeModule == null || !secretDupeModule.isToggled()) {
            return false;
        }
        if (class_16572 == null || class_16572.field_7512 == null) {
            return false;
        }
        class_1703 class_17032 = class_16572.field_7512;
        boolean bl2 = secretDupeModule.m(class_17032.method_34255());
        boolean bl3 = bl = n >= 0 && n < class_17032.field_7761.size() && secretDupeModule.m(class_17032.method_7611(n).method_7677());
        if (!bl2 && !bl) {
            return false;
        }
        if (n == -999 && bl2 && class_17132 == class_1713.field_7790) {
            class_1799 class_17992 = class_17032.method_34255();
            int n3 = n2 == 1 ? 1 : class_17992.method_7947();
            for (int i = 0; i < n3; ++i) {
                secretDupeModule.ha();
            }
            class_17992.method_7934(n3);
            class_17032.method_34254(class_17992.method_7960() ? class_1799.field_8037 : class_17992);
            secretDupeModule.tL = Math.max(0, secretDupeModule.tL - n3);
            return true;
        }
        if (bl && class_17132 == class_1713.field_7795) {
            class_1799 class_17993 = class_17032.method_7611(n).method_7677();
            int n4 = n2 == 1 ? class_17993.method_7947() : 1;
            for (int i = 0; i < n4; ++i) {
                secretDupeModule.ha();
            }
            class_17993.method_7934(n4);
            if (class_17993.method_7960()) {
                class_17032.method_7611(n).method_53512(class_1799.field_8037);
            }
            secretDupeModule.tL = Math.max(0, secretDupeModule.tL - n4);
            return true;
        }
        class_17032.method_7593(n, n2, class_17132, class_16572);
        return true;
    }

    private class_1657 a(class_1542 class_15422) {
        class_742 class_7422 = null;
        double d = 1.9599999999999997;
        for (class_742 class_7423 : SecretDupeModule.aiG.field_1687.method_18456()) {
            double d2;
            if (class_7423 == SecretDupeModule.aiG.field_1724 || class_7423.method_7325() || !class_7423.method_5805() || !((d2 = class_15422.method_5858((class_1297)class_7423)) <= d)) continue;
            d = d2;
            class_7422 = class_7423;
        }
        return class_7422;
    }

    public static boolean o(boolean bl) {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return false;
        }
        SecretDupeModule secretDupeModule = celebression.getModuleManager().getModule(SecretDupeModule.class);
        if (secretDupeModule == null || !secretDupeModule.isToggled()) {
            return false;
        }
        return secretDupeModule.p(bl);
    }

    private boolean p(boolean bl) {
        if (SecretDupeModule.fullNullCheck() || SecretDupeModule.aiG.field_1687 == null) {
            return false;
        }
        class_1661 class_16612 = SecretDupeModule.aiG.field_1724.method_31548();
        class_1799 class_17992 = class_16612.method_5438(class_16612.field_7545);
        if (!this.m(class_17992)) {
            return false;
        }
        if (this.tL <= 0) {
            return false;
        }
        int n = 1;
        this.tL -= n;
        class_17992.method_7934(n);
        if (class_17992.method_7960()) {
            class_16612.method_5447(class_16612.field_7545, class_1799.field_8037);
        }
        this.ha();
        return true;
    }

    private void ha() {
        if (SecretDupeModule.fullNullCheck() || SecretDupeModule.aiG.field_1687 == null) {
            return;
        }
        class_746 class_7462 = SecretDupeModule.aiG.field_1724;
        class_1542 class_15422 = new class_1542((class_1937)SecretDupeModule.aiG.field_1687, class_7462.method_23317(), class_7462.method_23320() - 0.3, class_7462.method_23321(), this.x(1));
        class_15422.method_5838(tO--);
        class_15422.method_5826(UUID.randomUUID());
        float f = class_7462.method_36454() * ((float)Math.PI / 180);
        float f2 = class_7462.method_36455() * ((float)Math.PI / 180);
        class_15422.method_18800((double)(-class_3532.method_15374((float)f) * class_3532.method_15362((float)f2)) * 0.3, (double)(-class_3532.method_15374((float)f2)) * 0.3 + 0.1, (double)(class_3532.method_15362((float)f) * class_3532.method_15362((float)f2)) * 0.3);
        SecretDupeModule.aiG.field_1687.method_53875((class_1297)class_15422);
        this.tN.add(new DupeAction(class_15422));
    }

    private void hb() {
        int n;
        if (SecretDupeModule.aiG.field_1755 instanceof class_465) {
            return;
        }
        class_1661 class_16612 = SecretDupeModule.aiG.field_1724.method_31548();
        int n2 = this.a(class_16612);
        if (n2 == this.tL) {
            return;
        }
        if (n2 > this.tL) {
            this.tL = n2;
            return;
        }
        int n3 = this.tL - n2;
        for (n = 0; n < class_16612.method_5439() && n3 > 0; ++n) {
            int n4;
            class_1799 class_17992 = class_16612.method_5438(n);
            if (!this.m(class_17992) || (n4 = Math.min(n3, 64 - class_17992.method_7947())) <= 0) continue;
            class_17992.method_7939(class_17992.method_7947() + n4);
            n3 -= n4;
        }
        while (n3 > 0) {
            n = this.b(class_16612);
            if (n < 0) {
                return;
            }
            int n5 = Math.min(n3, 64);
            class_16612.method_5447(n, this.x(n5));
            n3 -= n5;
        }
    }

    private int a(class_1661 class_16612) {
        int n = 0;
        for (int i = 0; i < class_16612.method_5439(); ++i) {
            class_1799 class_17992 = class_16612.method_5438(i);
            if (!this.m(class_17992)) continue;
            n += class_17992.method_7947();
        }
        return n;
    }

    private int b(class_1661 class_16612) {
        for (int i = 0; i < class_16612.method_5439(); ++i) {
            if (!class_16612.method_5438(i).method_7960()) continue;
            return i;
        }
        return -1;
    }

    private boolean m(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960() || !class_17992.method_31574(class_1802.field_8288)) {
            return false;
        }
        class_2561 class_25612 = (class_2561)class_17992.method_57824(class_9334.field_49631);
        return class_25612 != null && class_25612.getString().contains("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f");
    }

    public static boolean aZ(String string) {
        if (string == null) {
            return false;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return false;
        }
        SecretDupeModule secretDupeModule = celebression.getModuleManager().getModule(SecretDupeModule.class);
        if (secretDupeModule == null || !secretDupeModule.isToggled()) {
            return false;
        }
        String string2 = string.trim();
        while (string2.startsWith("/")) {
            string2 = string2.substring(1).trim();
        }
        Matcher matcher = tG.matcher(string2);
        if (!matcher.find()) {
            return false;
        }
        long l = SecretDupeModule.p(matcher.group(1), matcher.group(2));
        if (l <= 0L) {
            return false;
        }
        class_1661 class_16612 = SecretDupeModule.aiG.field_1724.method_31548();
        class_1799 class_17992 = class_16612.method_5438(class_16612.field_7545);
        if (!secretDupeModule.m(class_17992)) {
            return false;
        }
        int n = class_17992.method_7947();
        secretDupeModule.tK.add(new DupeState(l, n));
        class_16612.method_5447(class_16612.field_7545, class_1799.field_8037);
        secretDupeModule.tL = Math.max(0, secretDupeModule.tL - n);
        secretDupeModule.v(n);
        return true;
    }

    public static class_2561 d(class_2561 class_25612) {
        long l;
        int n;
        long l2 = tP;
        if (l2 <= 0L || class_25612 == null) {
            return class_25612;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        class_25612.method_27658((class_25832, string) -> {
            arrayList.add(string);
            arrayList2.add(class_25832);
            return Optional.empty();
        }, class_2583.field_24360);
        String string2 = String.join((CharSequence)"", arrayList);
        Matcher matcher = ServerTextPatternMatcher.alQ.matcher(string2);
        if (!matcher.find()) {
            return class_25612;
        }
        int n2 = matcher.start(2);
        for (n = matcher.end(2); n > n2 && !Character.isDigit(string2.charAt(n - 1)); --n) {
        }
        String string3 = string2.substring(n2, n);
        String string4 = string3.replaceAll("[^\\d]", "");
        if (string4.isEmpty()) {
            return class_25612;
        }
        try {
            l = Long.parseLong(string4);
        }
        catch (NumberFormatException numberFormatException) {
            return class_25612;
        }
        int n3 = string3.indexOf(44) >= 0 ? 44 : (string3.indexOf(32) >= 0 ? 32 : (string3.indexOf(46) >= 0 ? 46 : 0));
        String string5 = SecretDupeModule.a(l + l2, (char)n3);
        class_5250 class_52502 = class_2561.method_43473();
        int n4 = 0;
        boolean bl = false;
        for (int i = 0; i < arrayList.size(); ++i) {
            int n5;
            String string6 = (String)arrayList.get(i);
            int n6 = n4;
            n4 = n5 = n4 + string6.length();
            if (n5 <= n2 || n6 >= n) {
                SecretDupeModule.a(class_52502, string6, (class_2583)arrayList2.get(i));
                continue;
            }
            SecretDupeModule.a(class_52502, string6.substring(0, Math.max(0, n2 - n6)), (class_2583)arrayList2.get(i));
            if (!bl) {
                SecretDupeModule.a(class_52502, string5, (class_2583)arrayList2.get(i));
                bl = true;
            }
            if (n >= n5) continue;
            SecretDupeModule.a(class_52502, string6.substring(n - n6), (class_2583)arrayList2.get(i));
        }
        return class_52502;
    }

    private static void a(class_5250 class_52502, String string, class_2583 class_25832) {
        if (!string.isEmpty()) {
            class_52502.method_10852((class_2561)class_2561.method_43470((String)string).method_10862(class_25832));
        }
    }

    private static String a(long l, char c) {
        String string = Long.toString(l);
        if (c == '\u0000') {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            if (i > 0 && (string.length() - i) % 3 == 0) {
                stringBuilder.append(c);
            }
            stringBuilder.append(string.charAt(i));
        }
        return stringBuilder.toString();
    }

    private static long p(String string, String string2) {
        long l;
        String string3 = string.replaceAll("[ .,]", "");
        try {
            l = Long.parseLong(string3);
        }
        catch (NumberFormatException numberFormatException) {
            return -1L;
        }
        if (string2 == null) {
            return l;
        }
        return switch (string2.toLowerCase()) {
            case "\u043a", "k" -> l * 1000L;
            case "\u043a\u043a", "kk", "\u043c", "m" -> l * 1000000L;
            case "\u043a\u043a\u043a", "kkk", "\u043c\u043c", "mm" -> l * 1000000000L;
            default -> l;
        };
    }

    private void v(int n) {
        SecretDupeModule.a(SecretDupeModule.hc().method_10852((class_2561)SecretDupeModule.d("\u041f\u0440\u0435\u0434\u043c\u0435\u0442 ", 0xFFFFE6)).method_10852((class_2561)SecretDupeModule.d(SecretDupeModule.w(n), 0xFFFF00)).method_10852((class_2561)SecretDupeModule.d(" \u0432\u044b\u0441\u0442\u0430\u0432\u043b\u0435\u043d \u043d\u0430 \u043f\u0440\u043e\u0434\u0430\u0436\u0443!", 0xFFFFE6)));
    }

    private void a(long l, int n) {
        SecretDupeModule.a(SecretDupeModule.hc().method_10852((class_2561)SecretDupeModule.d("\u0423 \u0412\u0430\u0441 \u043a\u0443\u043f\u0438\u043b\u0438 ", 0xFFFFE6)).method_10852((class_2561)SecretDupeModule.d(SecretDupeModule.w(n), 0xFF6600)).method_10852((class_2561)SecretDupeModule.d(" \u0437\u0430 ", 0xFFFFE6)).method_10852((class_2561)SecretDupeModule.d("$" + SecretDupeModule.i(l), 65280)).method_10852((class_2561)SecretDupeModule.d(" \u043d\u0430 ", 0xFFFFE6)).method_10852((class_2561)SecretDupeModule.d("/ah", 0xFFFF00)));
    }

    private static String w(int n) {
        return n > 1 ? "\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f x" + n : "\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f";
    }

    private static class_5250 hc() {
        return class_2561.method_43473().method_10852((class_2561)SecretDupeModule.d("[\u2603] ", 0xFF6600));
    }

    private static class_5250 d(String string, int n) {
        return class_2561.method_43470((String)string).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717((int)n)).method_10978(Boolean.valueOf(false)));
    }

    private static void a(class_5250 class_52502) {
        if (SecretDupeModule.aiG.field_1724 == null) {
            return;
        }
        SecretDupeModule.aiG.field_1724.method_7353((class_2561)class_52502, false);
    }

    private static String i(long l) {
        return SecretDupeModule.a(l, ',');
    }

    private class_1799 x(int n) {
        class_1799 class_17992 = new class_1799((class_1935)class_1802.field_8288, n);
        class_17992.method_57379(class_9334.field_50071, 64);
        class_17992.method_57379(class_9334.field_49631, this.hd());
        class_17992.method_57379(class_9334.field_49632, new class_9290(this.he()));
        class_17992.method_57379(class_9334.field_49641, true);
        return class_17992;
    }

    private class_5250 hd() {
        class_2583 class_25832 = class_2583.field_24360.method_10982(Boolean.valueOf(true)).method_10978(Boolean.valueOf(false));
        class_5250 class_52502 = class_2561.method_43473().method_10852((class_2561)class_2561.method_43470((String)"xxx").method_10862(class_25832.method_36141(Boolean.valueOf(true)).method_27703(class_5251.method_27717((int)0x950000)))).method_10852((class_2561)class_2561.method_43470((String)" ").method_10862(class_25832));
        char[] cArray = "\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f".toCharArray();
        int n = (cArray.length - 1) / 2;
        for (int i = 0; i < cArray.length; ++i) {
            float f = n == 0 ? 1.0f : (float)(n - Math.abs(i - n)) / (float)n;
            int n2 = 149 + Math.round(106.0f * f);
            class_52502.method_10852((class_2561)class_2561.method_43470((String)String.valueOf(cArray[i])).method_10862(class_25832.method_27703(class_5251.method_27717((int)(n2 << 16)))));
        }
        return class_52502.method_10852((class_2561)class_2561.method_43470((String)" ").method_10862(class_25832)).method_10852((class_2561)class_2561.method_43470((String)"xxx").method_10862(class_25832.method_36141(Boolean.valueOf(true)).method_27703(class_5251.method_27717((int)0x950000))));
    }

    private List<class_2561> he() {
        class_2583 class_25832 = class_2583.field_24360.method_27703(class_5251.method_27717((int)0xAAAAAA)).method_10978(Boolean.valueOf(false));
        class_2583 class_25833 = class_2583.field_24360.method_27703(class_5251.method_27717((int)0x5555FF)).method_10978(Boolean.valueOf(false));
        ArrayList<class_2561> arrayList = new ArrayList<class_2561>();
        arrayList.add((class_2561)class_2561.method_43470((String)"\u041b\u0435\u0433\u0435\u043d\u0434\u0430\u0440\u043d\u044b\u0439 \u0441\u0438\u043c\u0432\u043e\u043b.").method_10862(class_25832));
        arrayList.add((class_2561)class_2561.method_43470((String)"\u041d\u0435\u0441\u043e\u043a\u0440\u0443\u0448\u0438\u043c\u0430\u044f \u043c\u043e\u0449\u044c,").method_10862(class_25832));
        arrayList.add((class_2561)class_2561.method_43470((String)"\u041b\u043e\u043c\u0430\u044e\u0449\u0430\u044f \u043f\u0440\u0435\u0433\u0440\u0430\u0434\u044b.").method_10862(class_25832));
        arrayList.add((class_2561)class_2561.method_43470((String)"").method_10862(class_25832));
        arrayList.add((class_2561)class_2561.method_43470((String)"\u041a\u043e\u0433\u0434\u0430 \u0432\u043e \u0432\u0442\u043e\u0440\u043e\u0441\u0442\u0435\u043f\u0435\u043d\u043d\u043e\u0439 \u0440\u0443\u043a\u0435:").method_10862(class_25832));
        arrayList.add((class_2561)class_2561.method_43470((String)"+4 \u041c\u0430\u043a\u0441\u0438\u043c\u0430\u043b\u044c\u043d\u043e\u0435 \u0437\u0434\u043e\u0440\u043e\u0432\u044c\u0435").method_10862(class_25833));
        arrayList.add((class_2561)class_2561.method_43470((String)"+3 \u0423\u0440\u043e\u043d \u043f\u0440\u0438 \u0430\u0442\u0430\u043a\u0435").method_10862(class_25833));
        arrayList.add((class_2561)class_2561.method_43470((String)"+2 \u0422\u0432\u0451\u0440\u0434\u043e\u0441\u0442\u044c \u0431\u0440\u043e\u043d\u0438").method_10862(class_25833));
        arrayList.add((class_2561)class_2561.method_43470((String)"+2 \u0411\u0440\u043e\u043d\u044f").method_10862(class_25833));
        return arrayList;
    }

    static final class DupeState {
        final long tV;
        final int count;
        int tW = 200;

        DupeState(long l, int n) {
            this.tV = l;
            this.count = n;
        }
    }

    static final class DupeAction {
        final class_1542 tU;
        int age;

        DupeAction(class_1542 class_15422) {
            this.tU = class_15422;
        }
    }
}
