package ru.method.celebression.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_2588;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_5250;
import net.minecraft.class_5251;
import net.minecraft.class_5481;
import ru.method.celebression.Celebression;
import ru.method.celebression.manager.FriendManager;
import ru.method.celebression.util.PlayerNameSanitizer;
import ru.method.celebression.server.ServerTextPatternMatcher;
import ru.method.celebression.modules.impl.client.StreamerModeModule;
import ru.method.celebression.util.TextSanitizationMode;
import ru.method.celebression.utils.debug.TabProfiler;

public final class StreamerTextSanitizer {
    private static final Map<String, Pattern> akZ = new ConcurrentHashMap<String, Pattern>();
    private static final Map<String, Pattern> ala = new ConcurrentHashMap<String, Pattern>();
    private static final Map<String, Pattern> alb = new ConcurrentHashMap<String, Pattern>();
    private static final Map<String, Pattern> alc = new ConcurrentHashMap<String, Pattern>();
    private static final Map<String, Pattern> ald = new ConcurrentHashMap<String, Pattern>();
    private static final Map<String, Pattern> ale = new ConcurrentHashMap<String, Pattern>();
    private static final Map<class_2561, TooltipContext> alf = Collections.synchronizedMap(new WeakHashMap());
    private static volatile SanitizationContext alg = new SanitizationContext("", -1, Collections.emptyMap());
    private static int alh;
    private static final Pattern ali;

    private StreamerTextSanitizer() {
    }

    public static boolean isEnabled() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return false;
        }
        StreamerModeModule streamerModeModule = celebression.getModuleManager().getModule(StreamerModeModule.class);
        return streamerModeModule != null && streamerModeModule.isToggle();
    }

    public static String st() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return "";
        }
        StreamerModeModule streamerModeModule = celebression.getModuleManager().getModule(StreamerModeModule.class);
        if (streamerModeModule == null || !streamerModeModule.isToggle()) {
            return "";
        }
        String string = streamerModeModule.hL();
        return string == null ? "" : string.trim();
    }

    public static String dm(String string) {
        if (string == null || string.isBlank()) {
            return string;
        }
        String string2 = StreamerTextSanitizer.du(StreamerTextSanitizer.dy(string));
        return string2.isBlank() ? string : string2;
    }

    public static boolean su() {
        return TextSanitizationMode.sX() == TextSanitizationMode.Strategy.amp;
    }

    public static int f(String string, int n) {
        if (string == null || n <= 0 || n > string.length()) {
            return -1;
        }
        int n2 = StreamerTextSanitizer.k(string, n);
        if (n2 < 0 || n2 >= n) {
            return -1;
        }
        String string2 = string.substring(n2, n).trim();
        if (string2.isBlank() || StreamerTextSanitizer.dZ(string2)) {
            return -1;
        }
        if (StreamerTextSanitizer.su() || StreamerTextSanitizer.ef(string2) || string2.startsWith("[") && string2.endsWith("]")) {
            return n2;
        }
        return -1;
    }

    public static boolean hK() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return false;
        }
        StreamerModeModule streamerModeModule = celebression.getModuleManager().getModule(StreamerModeModule.class);
        return streamerModeModule != null && streamerModeModule.isToggle() && streamerModeModule.hK();
    }

    private static String af(boolean bl) {
        return bl ? "666" : "999";
    }

    public static void sv() {
    }

    public static void j(class_2561 class_25612) {
        if (class_25612 == null) {
            return;
        }
        StreamerTextSanitizer.dE(StreamerTextSanitizer.dN((String)class_25612.getString()).alo);
    }

    public static void dn(String string) {
        if (string == null || string.isEmpty()) {
            return;
        }
        StreamerTextSanitizer.dE(StreamerTextSanitizer.dN((String)string).alo);
    }

    public static void k(class_2561 class_25612) {
        if (class_25612 == null) {
            return;
        }
        StreamerTextSanitizer.dF(StreamerTextSanitizer.dN((String)class_25612.getString()).alo);
    }

    public static void sw() {
        TextSanitizationMode.sZ();
    }

    public static void sx() {
        TextSanitizationMode.ta();
    }

    public static boolean sy() {
        return TextSanitizationMode.sy();
    }

    public static void sz() {
        TextSanitizationMode.td();
    }

    public static void sA() {
        TextSanitizationMode.te();
    }

    public static boolean sB() {
        return TextSanitizationMode.sB();
    }

    public static void sC() {
        TextSanitizationMode.tb();
    }

    public static void sD() {
        TextSanitizationMode.tc();
    }

    private static boolean ag(String string, String string2) {
        if (string == null || string2 == null || string.equals(string2)) {
            return false;
        }
        String string3 = StreamerTextSanitizer.du(StreamerTextSanitizer.sG()).trim();
        if (string3.isEmpty()) {
            return false;
        }
        return StreamerTextSanitizer.c(StreamerTextSanitizer.du(string), StreamerTextSanitizer.du(string2), string3);
    }

    static boolean c(String string, String string2, String string3) {
        if (string == null || string2 == null || string3 == null || string3.isEmpty()) {
            return false;
        }
        if (!string2.contains(string3)) {
            return false;
        }
        if (string.contains(string3)) {
            return false;
        }
        return StreamerTextSanitizer.normalizeWhitespace(string2.replace(string3, " ")).equals(StreamerTextSanitizer.normalizeWhitespace(string));
    }

    private static String normalizeWhitespace(String string) {
        return ali.matcher(string).replaceAll(" ").trim();
    }

    private static class_2561 b(class_2561 class_25612, class_2561 class_25613) {
        if (class_25612 == null || class_25613 == null || class_25612 == class_25613) {
            return class_25613;
        }
        return StreamerTextSanitizer.ag(class_25612.getString(), class_25613.getString()) ? class_25612 : class_25613;
    }

        public static class_2561 l(class_2561 class_25612) {
        long l = TabProfiler.startSection();
        try {
            if (!StreamerTextSanitizer.isEnabled() || class_25612 == null) {
                class_2561 class_25613 = class_25612;
                return class_25613;
            }
            List<TextContext> list = StreamerTextSanitizer.c(class_25612.method_30937());
            if (list.isEmpty()) {
                class_2561 class_25614 = class_25612;
                return class_25614;
            }
            TabListContext tabListContext = StreamerTextSanitizer.a(list, false);
            class_2561 class_25615 = tabListContext.alv ? StreamerTextSanitizer.b(class_25612, StreamerTextSanitizer.r(tabListContext.alu)) : class_25612;
            return class_25615;
        }
        finally {
            TabProfiler.endSection("sanitizeText", l);
        }
    }

        public static class_2561 m(class_2561 class_25612) {
        long l = TabProfiler.startSection();
        try {
            List<TextContext> list;
            if (!StreamerTextSanitizer.isEnabled() || class_25612 == null) {
                class_2561 class_25613 = class_25612;
                return class_25613;
            }
            List<TextContext> list2 = StreamerTextSanitizer.c(class_25612.method_30937());
            if (list2.isEmpty()) {
                class_2561 class_25614 = class_25612;
                return class_25614;
            }
            TabListContext tabListContext = StreamerTextSanitizer.a(list2, false);
            List<TextContext> list3 = tabListContext.alu;
            boolean bl = tabListContext.alv;
            if (StreamerTextSanitizer.dU(StreamerTextSanitizer.n(list3))) {
                NameContext nameContext = StreamerTextSanitizer.l(list3);
                list3 = nameContext.alq;
                bl |= nameContext.alr;
            }
            if (StreamerTextSanitizer.dV(StreamerTextSanitizer.n(list3))) {
                class_2561 sanitizedLabel = PlayerNameSanitizer.ac(StreamerTextSanitizer.r(list3));
                if (sanitizedLabel != null && !(list = StreamerTextSanitizer.c(sanitizedLabel.method_30937())).isEmpty()) {
                    list3 = list;
                    bl = true;
                }
            }
            return bl ? StreamerTextSanitizer.b(class_25612, StreamerTextSanitizer.r(list3)) : class_25612;
        }
        finally {
            TabProfiler.endSection("sanitizePlayerLabel", l);
        }
    }

    public static class_2561 n(class_2561 class_25612) {
        String string;
        if (class_25612 == null) {
            return class_25612;
        }
        if (!StreamerTextSanitizer.isEnabled()) {
            return class_25612;
        }
        List<TextContext> list = StreamerTextSanitizer.c(class_25612.method_30937());
        if (list.isEmpty()) {
            return StreamerTextSanitizer.m(class_25612);
        }
        boolean bl = StreamerTextSanitizer.dV(StreamerTextSanitizer.n(list));
        String string2 = StreamerTextSanitizer.sG();
        if (string2.isBlank()) {
            class_2561 class_25613 = StreamerTextSanitizer.m(class_25612);
            if (bl && class_25613 != null && !StreamerTextSanitizer.dV(class_25613.getString())) {
                List<TextContext> list2 = StreamerTextSanitizer.c(class_25613.method_30937());
                ScoreboardContext scoreboardContext = StreamerTextSanitizer.m(list2);
                return scoreboardContext.alt ? StreamerTextSanitizer.r(scoreboardContext.als) : class_25613;
            }
            return class_25613;
        }
        String string3 = string2 + " ";
        ScoreboardContext scoreboardContext = new ScoreboardContext(list, false);
        Object object = "none";
        for (String string4 : StreamerTextSanitizer.sF()) {
            if (string4 == null || string4.isBlank()) continue;
            scoreboardContext = StreamerTextSanitizer.a(list, StreamerTextSanitizer.dH(string4), string2, false, true, true, 0xFF0000);
            if (scoreboardContext.alt) {
                object = "prefix:" + string4;
            }
            if (!scoreboardContext.alt) {
                scoreboardContext = StreamerTextSanitizer.a(list, StreamerTextSanitizer.dI(string4), string2, false, true, true, 0xFF0000);
                if (scoreboardContext.alt) {
                    object = "glyph:" + string4;
                }
            }
            if (!scoreboardContext.alt) {
                scoreboardContext = StreamerTextSanitizer.f(list, string4, string3, 0xFF0000, true);
                if (scoreboardContext.alt) {
                    object = "resource:" + string4;
                }
            }
            if (!scoreboardContext.alt) continue;
            break;
        }
        List<TextContext> list3 = scoreboardContext.alt ? scoreboardContext.als : list;
        boolean bl2 = scoreboardContext.alt;
        String string4 = string = StreamerTextSanitizer.isEnabled() ? StreamerTextSanitizer.st() : "";
        if (!string.isBlank()) {
            for (String object2 : StreamerTextSanitizer.sE()) {
                if (object2 == null || object2.isBlank() || object2.equalsIgnoreCase(string)) continue;
                ScoreboardContext scoreboardContext2 = StreamerTextSanitizer.d(list3, StreamerTextSanitizer.dG(object2), 0, string, null);
                list3 = scoreboardContext2.als;
                bl2 |= scoreboardContext2.alt;
            }
        }
        ScoreboardContext scoreboardContext3 = StreamerTextSanitizer.g(list3);
        list3 = scoreboardContext3.als;
        bl2 |= scoreboardContext3.alt;
        if (bl && !StreamerTextSanitizer.dV(StreamerTextSanitizer.n(list3))) {
            ScoreboardContext scoreboardContext4 = StreamerTextSanitizer.m(list3);
            list3 = scoreboardContext4.als;
            bl2 |= scoreboardContext4.alt;
        }
        return bl2 ? StreamerTextSanitizer.b(class_25612, StreamerTextSanitizer.r(list3)) : class_25612;
    }

    public static class_5481 a(class_5481 class_54812) {
        if (class_54812 == null) {
            return class_54812;
        }
        List<TextContext> list = StreamerTextSanitizer.c(class_54812);
        if (list.isEmpty()) {
            return class_54812;
        }
        class_2561 class_25612 = StreamerTextSanitizer.n(StreamerTextSanitizer.r(list));
        if (class_25612 == null) {
            return class_54812;
        }
        List<TextContext> list2 = StreamerTextSanitizer.c(class_25612.method_30937());
        return list2.isEmpty() ? class_54812 : StreamerTextSanitizer.o(list2);
    }

    public static class_2561 o(class_2561 class_25612) {
        ScoreboardContext scoreboardContext;
        boolean bl;
        if (!StreamerTextSanitizer.isEnabled() || class_25612 == null) {
            return class_25612;
        }
        List<TextContext> list = StreamerTextSanitizer.c(class_25612.method_30937());
        if (list.isEmpty()) {
            return class_25612;
        }
        String string = StreamerTextSanitizer.n(list);
        if (!StreamerTextSanitizer.dp(string)) {
            return StreamerTextSanitizer.l(class_25612);
        }
        int n = StreamerTextSanitizer.dJ(string);
        List<TextContext> list2 = list;
        List<TextContext> list3 = null;
        if (n > 0 && n < string.length()) {
            list2 = StreamerTextSanitizer.b(list, n + 1);
            list3 = StreamerTextSanitizer.c(list, n + 1);
        }
        StreamerTextSanitizer.dL("input plain='" + class_25612.getString() + "' customDonate='" + StreamerTextSanitizer.hM() + "' replacementName='" + StreamerTextSanitizer.st() + "' selfNames=" + String.valueOf(StreamerTextSanitizer.sE()));
        boolean bl2 = StreamerTextSanitizer.h(list2);
        ScoreboardContext scoreboardContext2 = StreamerTextSanitizer.i(list2);
        TabListContext tabListContext = StreamerTextSanitizer.a(scoreboardContext2.als, true);
        List<TextContext> list4 = tabListContext.alu;
        boolean bl3 = bl = scoreboardContext2.alt || tabListContext.alv;
        if (!bl2 && !scoreboardContext2.alt) {
            scoreboardContext = StreamerTextSanitizer.k(list4);
            list4 = scoreboardContext.als;
            bl |= scoreboardContext.alt;
        }
        scoreboardContext = StreamerTextSanitizer.g(list4);
        list4 = scoreboardContext.als;
        if (bl |= scoreboardContext.alt) {
            StreamerTextSanitizer.dL("output changed=true plain='" + StreamerTextSanitizer.n(list4) + "'");
        } else {
            StreamerTextSanitizer.dL("output changed=false");
        }
        if (!bl) {
            return class_25612;
        }
        if (list3 != null && !list3.isEmpty()) {
            ArrayList<TextContext> arrayList = new ArrayList<TextContext>(list4.size() + list3.size());
            arrayList.addAll(list4);
            arrayList.addAll(list3);
            return StreamerTextSanitizer.b(class_25612, StreamerTextSanitizer.r(arrayList));
        }
        return StreamerTextSanitizer.b(class_25612, StreamerTextSanitizer.r(list4));
    }

    public static void c(class_2561 class_25612, class_2561 class_25613) {
        if (!StreamerTextSanitizer.isEnabled() || class_25612 == null || class_25613 == null) {
            return;
        }
        String string = StreamerTextSanitizer.hM();
        if (string.isBlank()) {
            return;
        }
        StreamerTextSanitizer.dL("ChatHud before='" + class_25612.getString() + "' after='" + class_25613.getString() + "' changed=" + !class_25612.getString().equals(class_25613.getString()));
    }

    public static boolean p(class_2561 class_25612) {
        return class_25612 != null && StreamerTextSanitizer.dp(class_25612.getString());
    }

    public static boolean dp(String string) {
        int n = StreamerTextSanitizer.dJ(string);
        if (n <= 0 || n >= string.length() - 1) {
            return false;
        }
        char c = string.charAt(n);
        return c == '>' || c == '\u00bb' || c == '\u21e8' || c == '\u2192' || c == '\u27a4' || c == '\u279c';
    }

        public static class_2561 a(class_2561 class_25612, String string) {
        boolean bl;
        if (!StreamerTextSanitizer.isEnabled() || class_25612 == null || string == null || string.isBlank()) {
            return class_25612;
        }
        StreamerTextSanitizer.k(class_25612);
        boolean bl2 = StreamerTextSanitizer.dT(string);
        String string2 = StreamerTextSanitizer.dy(string);
        boolean bl3 = bl = !string2.isBlank() && !string.equalsIgnoreCase(string2);
        if (!bl2 && !bl) {
            return class_25612;
        }
        String string3 = bl2 ? StreamerTextSanitizer.sH() : "";
        TooltipContext tooltipContext = alf.get(class_25612);
        if (tooltipContext != null && tooltipContext.i(string, string3, string2)) {
            return tooltipContext.alx;
        }
        long l = TabProfiler.startSection();
        try {
            ScoreboardContext scoreboardContext;
            List<TextContext> list = StreamerTextSanitizer.c(class_25612.method_30937());
            if (list.isEmpty()) {
                class_2561 class_25613 = class_25612;
                return class_25613;
            }
            boolean bl4 = false;
            String string4 = StreamerTextSanitizer.n(list);
            if (bl2 && !string3.isBlank() && !StreamerTextSanitizer.d(string4, string, string3)) {
                scoreboardContext = new ScoreboardContext(list, false);
                for (String object2 : StreamerTextSanitizer.ag(true)) {
                    if (object2 == null || object2.isBlank()) continue;
                    scoreboardContext = StreamerTextSanitizer.a(list, StreamerTextSanitizer.dH(object2), string3, false, true, false, 0xFF0000);
                    if (!scoreboardContext.alt) {
                        scoreboardContext = StreamerTextSanitizer.a(list, StreamerTextSanitizer.dI(object2), string3, false, true, false, 0xFF0000);
                    }
                    if (!scoreboardContext.alt) {
                        scoreboardContext = StreamerTextSanitizer.a(list, object2, StreamerTextSanitizer.sJ(), (Integer)0xFF0000, true, false);
                    }
                    if (!scoreboardContext.alt) {
                        scoreboardContext = StreamerTextSanitizer.b(list, object2, StreamerTextSanitizer.sJ(), 0xFF0000, true);
                    }
                    if (!scoreboardContext.alt) continue;
                    break;
                }
                list = scoreboardContext.als;
                bl4 = scoreboardContext.alt;
                if (bl4) {
                    string4 = StreamerTextSanitizer.n(list);
                }
            }
            if (bl) {
                scoreboardContext = StreamerTextSanitizer.d(list, StreamerTextSanitizer.dG(string), 0, string2, null);
                list = scoreboardContext.als;
                bl4 |= scoreboardContext.alt;
            }
            scoreboardContext = StreamerTextSanitizer.g(list);
            list = scoreboardContext.als;
            class_2561 class_25614 = (bl4 |= scoreboardContext.alt) ? StreamerTextSanitizer.b(class_25612, StreamerTextSanitizer.r(list)) : class_25612;
            alf.put(class_25612, new TooltipContext(class_25614, string, string3, string2));
            return class_25614;
        }
        finally {
            TabProfiler.endSection("sanitizeTabName", l);
        }
    }

    public static class_2561 q(class_2561 class_25612) {
        return StreamerTextSanitizer.l(class_25612);
    }

        public static class_5481 b(class_5481 class_54812) {
        long l = TabProfiler.startSection();
        try {
            if (!StreamerTextSanitizer.isEnabled() || class_54812 == null) {
                class_5481 class_54813 = class_54812;
                return class_54813;
            }
            List<TextContext> list = StreamerTextSanitizer.c(class_54812);
            if (list.isEmpty()) {
                class_5481 class_54814 = class_54812;
                return class_54814;
            }
            TabListContext tabListContext = StreamerTextSanitizer.a(list, false);
            if (!tabListContext.alv) {
                class_5481 class_54815 = class_54812;
                return class_54815;
            }
            if (StreamerTextSanitizer.ag(StreamerTextSanitizer.n(list), StreamerTextSanitizer.n(tabListContext.alu))) {
                class_5481 class_54816 = class_54812;
                return class_54816;
            }
            class_5481 class_54817 = StreamerTextSanitizer.o(tabListContext.alu);
            return class_54817;
        }
        finally {
            TabProfiler.endSection("sanitizeOrderedText", l);
        }
    }

        public static String dq(String string) {
        long l = TabProfiler.startSection();
        try {
            if (!StreamerTextSanitizer.isEnabled() || string == null || string.isEmpty()) {
                String string2 = string;
                return string2;
            }
            String string3 = StreamerTextSanitizer.du(StreamerTextSanitizer.st());
            String string4 = string;
            if (!string3.isBlank()) {
                string4 = StreamerTextSanitizer.a(string4, ServerTextPatternMatcher.alX, string3);
            }
            string4 = StreamerTextSanitizer.dA(string4);
            string4 = StreamerTextSanitizer.dB(string4);
            if (!string3.isBlank()) {
                for (String candidate : StreamerTextSanitizer.sE()) {
                    if (candidate == null || candidate.isEmpty() || candidate.equals(string3)) continue;
                    string4 = StreamerTextSanitizer.b(string4, StreamerTextSanitizer.dG(candidate), string3);
                }
            }
            Map<String, String> replacements = StreamerTextSanitizer.sN();
            if (!replacements.isEmpty()) {
                for (Map.Entry<String, String> entry : replacements.entrySet()) {
                    String sourceName = entry.getKey();
                    String replacement = entry.getValue();
                    if (replacement == null || replacement.isBlank() || sourceName.equalsIgnoreCase(replacement)) continue;
                    string4 = StreamerTextSanitizer.b(string4, StreamerTextSanitizer.dG(sourceName), replacement);
                }
            }
            string4 = StreamerTextSanitizer.dC(string4);
            if (StreamerTextSanitizer.hK()) {
                string4 = StreamerTextSanitizer.a(string4, ServerTextPatternMatcher.alU, 3, StreamerTextSanitizer.af(false));
                string4 = StreamerTextSanitizer.a(string4, ServerTextPatternMatcher.alV, 3, StreamerTextSanitizer.af(true));
            }
            string4 = StreamerTextSanitizer.dO(string4);
            return StreamerTextSanitizer.ag(string, string4) ? string : string4;
        }
        finally {
            TabProfiler.endSection("sanitizeString", l);
        }
    }

    public static String dr(String string) {
        if (string == null || string.isBlank()) {
            return "";
        }
        if (!StreamerTextSanitizer.isEnabled()) {
            return StreamerTextSanitizer.du(string);
        }
        String string2 = StreamerTextSanitizer.dy(string);
        if (string2 != null && !string2.isBlank()) {
            return StreamerTextSanitizer.du(string2);
        }
        return StreamerTextSanitizer.du(StreamerTextSanitizer.dq(string));
    }

    public static boolean ds(String string) {
        return StreamerTextSanitizer.isEnabled() && StreamerTextSanitizer.dT(string) && StreamerTextSanitizer.du(StreamerTextSanitizer.st()).isBlank();
    }

    public static boolean dt(String string) {
        if (!StreamerTextSanitizer.isEnabled() || string == null || string.isBlank()) {
            return false;
        }
        String string2 = StreamerTextSanitizer.du(string).trim();
        if (string2.isEmpty()) {
            return false;
        }
        for (Map.Entry<String, String> entry : StreamerTextSanitizer.sN().entrySet()) {
            String string3 = entry.getKey();
            String string4 = entry.getValue();
            if (string4 == null || string4.isBlank() || string3.equalsIgnoreCase(string4) || !string2.equalsIgnoreCase(StreamerTextSanitizer.du(string3)) && !string2.equalsIgnoreCase(StreamerTextSanitizer.du(string4))) continue;
            return true;
        }
        return false;
    }

    public static String du(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if ((c == '&' || c == '\u00a7') && i + 1 < string.length() && class_124.method_544((char)Character.toLowerCase(string.charAt(i + 1))) != null) {
                ++i;
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String dv(String string) {
        if (!StreamerTextSanitizer.isEnabled() || string == null || string.isEmpty()) {
            return string;
        }
        int n = StreamerTextSanitizer.dP(string);
        return StreamerTextSanitizer.g(string, n);
    }

    public static String b(String string, int n, int n2) {
        if (!StreamerTextSanitizer.isEnabled() || string == null || string.isEmpty()) {
            return null;
        }
        int n3 = StreamerTextSanitizer.dP(string);
        if (n3 < 0) {
            return null;
        }
        String string2 = StreamerTextSanitizer.g(string, n3);
        int n4 = Math.max(0, Math.min(n, string2.length()));
        int n5 = Math.min(string2.length(), n4 + Math.max(0, n2));
        return string2.substring(n4, n5);
    }

    public static boolean dw(String string) {
        if (!StreamerTextSanitizer.isEnabled() || string == null || string.isEmpty()) {
            return false;
        }
        return StreamerTextSanitizer.dP(string) > -1;
    }

    private static List<String> sE() {
        String string;
        ArrayList<String> arrayList = new ArrayList<String>(2);
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return arrayList;
        }
        if (class_3102.field_1724 != null && class_3102.field_1724.method_7334() != null && (string = class_3102.field_1724.method_7334().getName()) != null && !string.isEmpty()) {
            arrayList.add(string);
        }
        if (class_3102.method_1548() != null && (string = class_3102.method_1548().method_1676()) != null && !string.isEmpty() && !arrayList.contains(string)) {
            arrayList.add(string);
        }
        return arrayList;
    }

    private static List<String> sF() {
        ArrayList<String> arrayList = new ArrayList<String>(StreamerTextSanitizer.sE());
        String string = StreamerTextSanitizer.du(StreamerTextSanitizer.st());
        if (string != null && !string.isBlank()) {
            boolean bl = false;
            for (String string2 : arrayList) {
                if (string2 == null || !string2.equalsIgnoreCase(string)) continue;
                bl = true;
                break;
            }
            if (!bl) {
                arrayList.add(string);
            }
        }
        return arrayList;
    }

    private static String hM() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return "";
        }
        StreamerModeModule streamerModeModule = celebression.getModuleManager().getModule(StreamerModeModule.class);
        if (streamerModeModule == null || !streamerModeModule.isToggle()) {
            return "";
        }
        String string = streamerModeModule.hM();
        return string == null ? "" : string.trim();
    }

    private static String sG() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return "";
        }
        StreamerModeModule streamerModeModule = celebression.getModuleManager().getModule(StreamerModeModule.class);
        if (streamerModeModule == null) {
            return "";
        }
        String string = streamerModeModule.hM();
        string = string == null ? "" : string.trim();
        return StreamerTextSanitizer.dx(string) ? "" : string;
    }

    private static String sH() {
        String string = StreamerTextSanitizer.hM();
        return StreamerTextSanitizer.dx(string) ? "" : string;
    }

    private static boolean dx(String string) {
        String string2 = StreamerTextSanitizer.du(string);
        String string3 = StreamerTextSanitizer.du(StreamerTextSanitizer.st());
        return string2 != null && string3 != null && !string2.trim().isBlank() && !string3.trim().isBlank() && string2.trim().equalsIgnoreCase(string3.trim());
    }

    private static String hN() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return "";
        }
        StreamerModeModule streamerModeModule = celebression.getModuleManager().getModule(StreamerModeModule.class);
        if (streamerModeModule == null || !streamerModeModule.isToggle()) {
            return "";
        }
        String string = streamerModeModule.hN();
        if (string == null || string.isBlank()) {
            return "";
        }
        if (TextSanitizationMode.sX() == TextSanitizationMode.Strategy.amp) {
            return StreamerTextSanitizer.dR(string);
        }
        return StreamerTextSanitizer.dR(string);
    }

    private static String sI() {
        String string = StreamerTextSanitizer.sH();
        if (string.isBlank()) {
            return string;
        }
        if (TextSanitizationMode.sX() == TextSanitizationMode.Strategy.amo) {
            return "[" + string + "]";
        }
        return string;
    }

    private static String sJ() {
        String string = StreamerTextSanitizer.sH();
        if (string.isBlank()) {
            return string;
        }
        return string + " ";
    }

    private static String sK() {
        String string = StreamerTextSanitizer.sH();
        if (string.isBlank()) {
            return string;
        }
        return StreamerTextSanitizer.sQ() ? "[" + string + "]" : string;
    }

    private static String sL() {
        String string = StreamerTextSanitizer.sK();
        if (string.isBlank()) {
            return string;
        }
        return string + " ";
    }

    private static String sM() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return "";
        }
        StreamerModeModule streamerModeModule = celebression.getModuleManager().getModule(StreamerModeModule.class);
        if (streamerModeModule == null || !streamerModeModule.isToggle()) {
            return "";
        }
        String string = streamerModeModule.hO();
        return string == null ? "" : string.trim();
    }

    private static String dy(String string) {
        if (string == null || string.isBlank()) {
            return "";
        }
        if (StreamerTextSanitizer.dT(string)) {
            return StreamerTextSanitizer.st();
        }
        return StreamerTextSanitizer.dz(string);
    }

    private static String dz(String string) {
        if (string == null || string.isBlank()) {
            return "";
        }
        return StreamerTextSanitizer.sN().getOrDefault(string.toLowerCase(Locale.ROOT), "");
    }

    private static Map<String, String> sN() {
        SanitizationContext sanitizationContext;
        Celebression celebression;
        FriendManager friendManager;
        int n;
        SanitizationContext sanitizationContext2 = alg;
        String string = StreamerTextSanitizer.sM();
        if (sanitizationContext2.matches(string, n = (friendManager = (celebression = Celebression.getInstance()) == null ? null : celebression.getFriendManager()) == null ? -1 : friendManager.er())) {
            return sanitizationContext2.aln;
        }
        Map<String, String> map = StreamerTextSanitizer.a(string, friendManager);
        alg = sanitizationContext = new SanitizationContext(string, n, map);
        return sanitizationContext.aln;
    }

    private static String dA(String string) {
        String string2 = string;
        String string3 = StreamerTextSanitizer.sJ();
        if (string3.isBlank()) {
            return string2;
        }
        for (String string4 : StreamerTextSanitizer.sE()) {
            if (string4 == null || string4.isBlank() || StreamerTextSanitizer.ah(string2, string4) || StreamerTextSanitizer.am(string2, string4)) continue;
            Pattern pattern = StreamerTextSanitizer.dH(string4);
            String string5 = StreamerTextSanitizer.c(string2, pattern, 1, string3);
            if (string5.equals(string2) && TextSanitizationMode.sX() == TextSanitizationMode.Strategy.amp) {
                string5 = StreamerTextSanitizer.c(string2, StreamerTextSanitizer.dI(string4), 1, string3);
            }
            if (string5.equals(string2) && TextSanitizationMode.sX() == TextSanitizationMode.Strategy.amp) {
                string5 = StreamerTextSanitizer.e(string2, string4, string3);
            }
            string2 = string5;
        }
        return string2;
    }

    private static String dB(String string) {
        String string2 = StreamerTextSanitizer.sH();
        if (string2.isBlank()) {
            return string;
        }
        return StreamerTextSanitizer.ai(string, string2);
    }

    private static boolean ah(String string, String string2) {
        if (string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return false;
        }
        ChatContext chatContext = StreamerTextSanitizer.dN(string);
        if (chatContext.alo.isBlank()) {
            return false;
        }
        Matcher matcher = StreamerTextSanitizer.dG(string2).matcher(chatContext.alo);
        if (!matcher.find()) {
            return false;
        }
        String string3 = chatContext.alo.substring(0, matcher.start());
        return ServerTextPatternMatcher.alZ.matcher(string3).find();
    }

    private static String sO() {
        String string = StreamerTextSanitizer.sI();
        if (string.isBlank()) {
            return string;
        }
        return string + " ";
    }

    private static String ai(String string, String string2) {
        if (string == null || string.isEmpty() || string2 == null || string2.isBlank()) {
            return string;
        }
        ChatContext chatContext = StreamerTextSanitizer.dN(string);
        if (chatContext.alo.isEmpty()) {
            return string;
        }
        Matcher matcher = ServerTextPatternMatcher.alS.matcher(chatContext.alo);
        if (!matcher.find()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        int n = 0;
        do {
            int n2 = matcher.end(1);
            int n3 = matcher.end(2);
            if (n2 <= 0 || n3 <= n2) continue;
            int n4 = chatContext.alp.get(n2 - 1) + 1;
            int n5 = chatContext.alp.get(n3 - 1) + 1;
            stringBuilder.append(string, n, n4);
            stringBuilder.append(' ').append(string2);
            n = n5;
        } while (matcher.find());
        stringBuilder.append(string.substring(n));
        return stringBuilder.toString();
    }

    private static String dC(String string) {
        String string2 = StreamerTextSanitizer.hN();
        if (string2 == null || string2.isBlank()) {
            return string;
        }
        ChatContext chatContext = StreamerTextSanitizer.dN(string);
        if (chatContext.alo.isEmpty()) {
            return string;
        }
        Matcher matcher = ServerTextPatternMatcher.alQ.matcher(chatContext.alo);
        if (!matcher.find()) {
            return string;
        }
        String string3 = StreamerTextSanitizer.dD(string2);
        StringBuilder stringBuilder = new StringBuilder(string.length() + 4);
        int n = 0;
        do {
            int n2 = matcher.start(2);
            int n3 = matcher.end(2);
            if (n2 < 0 || n3 <= n2) continue;
            int n4 = chatContext.alp.get(n2);
            int n5 = StreamerTextSanitizer.sS() ? StreamerTextSanitizer.m(chatContext.alo, n3) : n3;
            int n6 = chatContext.alp.get(n5 - 1) + 1;
            stringBuilder.append(string, n, n4);
            stringBuilder.append(string3);
            if (StreamerTextSanitizer.sS()) {
                stringBuilder.append(' ');
            }
            n = n6;
        } while (matcher.find());
        stringBuilder.append(string.substring(n));
        return stringBuilder.toString();
    }

    private static Map<String, String> a(String string, FriendManager friendManager) {
        if (string == null || string.isBlank() || friendManager == null || friendManager.isEmpty()) {
            return Collections.emptyMap();
        }
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        String string2 = string.trim();
        if (string2.isEmpty()) {
            return Collections.emptyMap();
        }
        for (String string3 : friendManager.es()) {
            if (string3 == null || string3.isBlank() || string3.equalsIgnoreCase(string2) || StreamerTextSanitizer.dT(string3)) continue;
            linkedHashMap.putIfAbsent(string3.toLowerCase(Locale.ROOT), string2);
        }
        return linkedHashMap.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(linkedHashMap);
    }

    private static int sP() {
        return TextSanitizationMode.sX() == TextSanitizationMode.Strategy.amp ? 16484360 : 0xFFFF55;
    }

    private static String dD(String string) {
        if (string == null || string.isBlank()) {
            return string;
        }
        return switch (TextSanitizationMode.sX()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case TextSanitizationMode.Strategy.amp -> StreamerTextSanitizer.bm(16484360) + string;
            case TextSanitizationMode.Strategy.amo -> "\u00a7e" + string;
            case TextSanitizationMode.Strategy.amn -> string;
        };
    }

    private static String bm(int n) {
        char[] cArray = String.format(Locale.ROOT, "%06X", n).toCharArray();
        return "\u00a7x\u00a7" + cArray[0] + "\u00a7" + cArray[1] + "\u00a7" + cArray[2] + "\u00a7" + cArray[3] + "\u00a7" + cArray[4] + "\u00a7" + cArray[5];
    }

    private static void dE(String string) {
        if (string == null || string.isBlank()) {
            return;
        }
        if (ServerTextPatternMatcher.amb.matcher(string).find()) {
            TextSanitizationMode.a(TextSanitizationMode.Strategy.amo);
            return;
        }
        if (ServerTextPatternMatcher.ama.matcher(string).find()) {
            TextSanitizationMode.a(TextSanitizationMode.Strategy.amp);
        }
    }

    private static void dF(String string) {
        if (string == null || string.isBlank()) {
            return;
        }
        if (ServerTextPatternMatcher.amb.matcher(string).find() || ServerTextPatternMatcher.alU.matcher(string).find()) {
            TextSanitizationMode.ah(true);
            alf.clear();
        }
    }

    private static boolean sQ() {
        return TextSanitizationMode.sX() == TextSanitizationMode.Strategy.amo;
    }

    private static Pattern dG(String string2) {
        return akZ.computeIfAbsent(string2, string -> Pattern.compile("(?iu)(?<![\\p{L}\\p{N}_])" + Pattern.quote(string) + "(?![\\p{L}\\p{N}_])"));
    }

    private static Pattern dH(String string) {
        String string3 = StreamerTextSanitizer.sT();
        return ala.computeIfAbsent(string, string2 -> Pattern.compile("(?iu)(?<![\\p{L}\\p{N}_])((?:(?:" + string3 + ")\\s*){1,6})" + Pattern.quote(string2) + "(?![\\p{L}\\p{N}_])"));
    }

    private static Pattern dI(String string2) {
        return alb.computeIfAbsent(string2, string -> Pattern.compile("(?u)(?<![\\p{L}\\p{N}_])((?:(?:[\\p{Co}\\p{So}\u2605\u2726\u2727\u269d\u269c\u265b]{1,8})\\s*){1,6})" + Pattern.quote(string) + "(?![\\p{L}\\p{N}_])"));
    }

    private static Pattern aj(String string, String string2) {
        return alc.computeIfAbsent(string + "\u0000" + string2, string3 -> Pattern.compile("(?iu)(?<![\\p{L}\\p{N}_])" + Pattern.quote(string) + "\\s+" + Pattern.quote(string2) + "(?![\\p{L}\\p{N}_])"));
    }

    private static Pattern ak(String string, String string2) {
        return ald.computeIfAbsent(string + "\u0000" + string2, string3 -> Pattern.compile("(?iu)(?<![\\p{L}\\p{N}_])(?:\\[" + Pattern.quote(string) + "]|" + Pattern.quote(string) + ")\\s+(" + Pattern.quote(string2) + ")(?![\\p{L}\\p{N}_])"));
    }

    private static Pattern al(String string, String string2) {
        return ale.computeIfAbsent(string + "\u0000" + string2, string3 -> Pattern.compile("(?iu)(?<![\\p{L}\\p{N}_])(?:\\[" + Pattern.quote(string) + "]|" + Pattern.quote(string) + ")\\s+" + Pattern.quote(string2) + "(?![\\p{L}\\p{N}_])"));
    }

    private static boolean d(String string, String string2, String string3) {
        if (string == null || string.isEmpty() || string2 == null || string2.isBlank() || string3 == null || string3.isBlank()) {
            return false;
        }
        String string4 = StreamerTextSanitizer.du(string3);
        if (string4 == null || string4.isBlank()) {
            return false;
        }
        Pattern pattern = StreamerTextSanitizer.aj(string4, string2);
        return pattern.matcher(string).find();
    }

    private static boolean am(String string, String string2) {
        String string3 = StreamerTextSanitizer.sH();
        if (StreamerTextSanitizer.d(string, string2, string3)) {
            return true;
        }
        String string4 = StreamerTextSanitizer.sI();
        return !string4.equals(string3) && StreamerTextSanitizer.d(string, string2, string4);
    }

    private static boolean an(String string, String string2) {
        return StreamerTextSanitizer.d(string, string2, StreamerTextSanitizer.sI());
    }

    private static ScoreboardContext g(List<TextContext> list) {
        if (list == null || list.isEmpty()) {
            return new ScoreboardContext(list, false);
        }
        String string = StreamerTextSanitizer.du(StreamerTextSanitizer.sH());
        String string2 = StreamerTextSanitizer.du(StreamerTextSanitizer.st());
        if (string == null || string2 == null) {
            return new ScoreboardContext(list, false);
        }
        string = string.trim();
        string2 = string2.trim();
        if (string.isBlank() || string2.isBlank() || !string.equalsIgnoreCase(string2)) {
            return new ScoreboardContext(list, false);
        }
        String string3 = StreamerTextSanitizer.n(list);
        Pattern pattern = StreamerTextSanitizer.ak(string, string2);
        Matcher matcher = pattern.matcher(string3);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        int n = 0;
        boolean bl = false;
        do {
            StreamerTextSanitizer.a(arrayList, string3, list2, n, matcher.start());
            class_2583 class_25832 = StreamerTextSanitizer.d(list2, matcher.start(1));
            StreamerTextSanitizer.c(arrayList, class_25832, StreamerTextSanitizer.st());
            n = matcher.end();
            bl = true;
        } while (matcher.find());
        StreamerTextSanitizer.a(arrayList, string3, list2, n, string3.length());
        return new ScoreboardContext(arrayList, bl);
    }

    private static boolean ao(String string, String string2) {
        if (string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return false;
        }
        Matcher matcher = StreamerTextSanitizer.dG(string2).matcher(string);
        if (!matcher.find()) {
            return false;
        }
        int n = StreamerTextSanitizer.dJ(string);
        return n >= 0 && matcher.start() < n;
    }

    private static int dJ(String string) {
        if (string == null || string.isEmpty()) {
            return -1;
        }
        int n = -1;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (StreamerTextSanitizer.c(c)) {
                if (n >= 0 && i >= n) continue;
                n = i;
                continue;
            }
            if (c != '>' && c != ':' && c != '\u00bb' || c == ':' && i + 1 < string.length() && Character.isDigit(string.charAt(i + 1)) || n >= 0 && i >= n) continue;
            n = i;
        }
        return n;
    }

    private static boolean c(char c) {
        return c == '\u00bb' || c == '\u21e8' || c == '\u2192' || c == '\u27a4' || c == '\u279c';
    }

    private static boolean h(List<TextContext> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        String string = StreamerTextSanitizer.n(list);
        if (string == null || string.isBlank()) {
            return false;
        }
        for (String string2 : StreamerTextSanitizer.sE()) {
            if (string2 == null || string2.isBlank() || !StreamerTextSanitizer.dH(string2).matcher(string).find() && !StreamerTextSanitizer.dI(string2).matcher(string).find()) continue;
            return true;
        }
        return false;
    }

    private static ScoreboardContext i(List<TextContext> list) {
        if (list == null || list.isEmpty()) {
            return new ScoreboardContext(list, false);
        }
        String string = StreamerTextSanitizer.sH();
        if (string.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        List<TextContext> list2 = list;
        boolean bl = false;
        String string2 = StreamerTextSanitizer.n(list2);
        StreamerTextSanitizer.dL("header initial='" + string2 + "' separator=" + StreamerTextSanitizer.dJ(string2));
        for (String string3 : StreamerTextSanitizer.ag(true)) {
            if (string3 == null || string3.isBlank()) continue;
            String string4 = StreamerTextSanitizer.n(list2);
            StreamerTextSanitizer.dL("header target='" + string3 + "' inHeader=" + StreamerTextSanitizer.ao(string4, string3));
            if (!StreamerTextSanitizer.ao(string4, string3)) continue;
            ScoreboardContext scoreboardContext = StreamerTextSanitizer.b(list2, string3, string);
            list2 = scoreboardContext.als;
            bl |= scoreboardContext.alt;
            if (!scoreboardContext.alt) continue;
            break;
        }
        return new ScoreboardContext(list2, bl);
    }

    private static ScoreboardContext b(List<TextContext> list, String string, String string2) {
        if (list == null || list.isEmpty() || string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string3 = StreamerTextSanitizer.n(list);
        int n = StreamerTextSanitizer.dJ(string3);
        if (n <= 0) {
            StreamerTextSanitizer.dL("replace skip: no separator joined='" + string3 + "'");
            return new ScoreboardContext(list, false);
        }
        if (!StreamerTextSanitizer.dp(string3)) {
            StreamerTextSanitizer.dL("replace skip: system header joined='" + string3 + "'");
            return new ScoreboardContext(list, false);
        }
        Matcher matcher = StreamerTextSanitizer.dG(string).matcher(string3);
        if (!matcher.find() || matcher.start() >= n) {
            StreamerTextSanitizer.dL("replace skip: name not before separator name='" + string + "' separator=" + n);
            return new ScoreboardContext(list, false);
        }
        int n2 = StreamerTextSanitizer.n(string3, matcher.start());
        if (n2 < 0 || n2 >= matcher.start() || n2 > n) {
            StreamerTextSanitizer.dL("replace skip: no prefixStart name='" + string + "' nameStart=" + matcher.start() + " prefixStart=" + n2);
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        String string4 = string3.substring(n2, matcher.start()).trim();
        boolean bl = StreamerTextSanitizer.a(list2, n2, matcher.start(), string4);
        if (string4.isBlank() || StreamerTextSanitizer.dZ(string4) || StreamerTextSanitizer.dK(string4) || string4.length() > 48) {
            StreamerTextSanitizer.dL("replace skip: prefix rejected slice='" + string4 + "' service=" + StreamerTextSanitizer.dZ(string4) + " likely=" + StreamerTextSanitizer.eb(string4) + " resourcePack=" + bl);
            return new ScoreboardContext(list, false);
        }
        if (!bl && !StreamerTextSanitizer.eb(string4)) {
            StreamerTextSanitizer.dL("replace skip: not a donate slice='" + string4 + "'");
            return new ScoreboardContext(list, false);
        }
        StreamerTextSanitizer.dL("replace apply: name='" + string + "' slice='" + string4 + "' donate='" + string2 + "'");
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StreamerTextSanitizer.a(arrayList, string3, list2, 0, n2);
        class_2583 class_25832 = StreamerTextSanitizer.d(list2, n2);
        StreamerTextSanitizer.a(arrayList, class_25832, string2);
        StreamerTextSanitizer.a(arrayList, string3, list2, matcher.start(), string3.length());
        return new ScoreboardContext(arrayList, true);
    }

    private static boolean dK(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c != '>' && c != ':' && c != '|' && c != '\n' && c != '\r' && !StreamerTextSanitizer.c(c)) continue;
            return true;
        }
        return false;
    }

    private static void a(List<TextContext> list, class_2583 class_25832, String string) {
        if (string.isBlank()) {
            return;
        }
        class_2583 class_25833 = StreamerTextSanitizer.a(class_25832, (Integer)0xFFFFFF, true);
        class_2583 class_25834 = StreamerTextSanitizer.a(class_25832, (Integer)0xFF0000, true);
        StreamerTextSanitizer.j(list);
        if (StreamerTextSanitizer.sQ()) {
            StreamerTextSanitizer.b(list, class_25833, "[");
            StreamerTextSanitizer.c(list, class_25834, string);
            StreamerTextSanitizer.b(list, class_25833, "]");
            StreamerTextSanitizer.b(list, class_25834, " ");
        } else {
            StreamerTextSanitizer.c(list, class_25834, string);
            StreamerTextSanitizer.b(list, class_25834, " ");
        }
    }

    private static void j(List<TextContext> list) {
        class_2583 class_25832 = class_2583.field_24360.method_27703(class_5251.method_27717((int)0x5555FF)).method_10982(Boolean.valueOf(true)).method_27704(class_2960.method_60655((String)"minecraft", (String)"uniform"));
        StreamerTextSanitizer.b(list, class_25832, PlayerNameSanitizer.sU());
    }

    private static void dL(String string) {
    }

    private static boolean dM(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        if (string.contains("replace apply") || string.contains("changed=true")) {
            return true;
        }
        String string2 = StreamerTextSanitizer.du(string).toLowerCase(Locale.ROOT);
        for (String string3 : StreamerTextSanitizer.ag(true)) {
            String string4 = StreamerTextSanitizer.du(string3);
            if (string4 == null || string4.isBlank() || !string2.contains(string4.toLowerCase(Locale.ROOT))) continue;
            return true;
        }
        String string5 = StreamerTextSanitizer.du(StreamerTextSanitizer.hM());
        return string5 != null && !string5.isBlank() && string2.contains(string5.toLowerCase(Locale.ROOT));
    }

    private static ScoreboardContext k(List<TextContext> list) {
        if (list == null || list.isEmpty()) {
            return new ScoreboardContext(list, false);
        }
        String string = StreamerTextSanitizer.du(StreamerTextSanitizer.hM());
        String string2 = StreamerTextSanitizer.du(StreamerTextSanitizer.st());
        if (string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        List<TextContext> list2 = list;
        boolean bl = false;
        String string3 = StreamerTextSanitizer.du(StreamerTextSanitizer.sI());
        for (String string4 : new String[]{string.trim(), string3 == null ? "" : string3.trim()}) {
            if (string4.isBlank()) continue;
            Pattern pattern = StreamerTextSanitizer.al(string4, string2.trim());
            ScoreboardContext scoreboardContext = StreamerTextSanitizer.c(list2, pattern, 0, StreamerTextSanitizer.st(), null);
            list2 = scoreboardContext.als;
            bl |= scoreboardContext.alt;
        }
        return new ScoreboardContext(list2, bl);
    }

    private static String sR() {
        return "(?:\\[[^\\]\\r\\n]{1,24}]|[\u2605\u2726\u2727\u269d\u269c\u265b]?[\\p{L}\\p{N}_+\\-]{2,24}|(?:owner|admin|moder|moderator|helper|media|streamer|legend|premium|prem|vip|elite|hero|king|lord|titan|yt|youtube|\u044e\u0442\u0443\u0431\u0435\u0440|\u0441\u0442\u0440\u0438\u043c\u0435\u0440|\u043c\u0435\u0434\u0438\u0430|\u0432\u0438\u043f|\u043f\u0440\u0435\u043c|\u044d\u043b\u0438\u0442\u0430|\u043b\u0435\u0433\u0435\u043d\u0434\u0430|\u0430\u0434\u043c\u0438\u043d|\u0445\u0435\u043b\u043f\u0435\u0440|\u043c\u043e\u0434\u0435\u0440)[\\p{L}\\p{N}_+\\-]*)";
    }

    private static String a(String string, Pattern pattern, String string2) {
        return StreamerTextSanitizer.a(string, pattern, string2, false);
    }

    private static String b(String string, Pattern pattern, String string2) {
        return StreamerTextSanitizer.a(string, pattern, string2, true);
    }

    private static String a(String string, Pattern pattern, String string2, boolean bl) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        ChatContext chatContext = StreamerTextSanitizer.dN(string);
        if (chatContext.alo.isEmpty()) {
            return string;
        }
        Matcher matcher = pattern.matcher(chatContext.alo);
        if (!matcher.find()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        int n = 0;
        do {
            int n2 = matcher.start();
            int n3 = matcher.end();
            if (n3 <= n2) continue;
            int n4 = bl ? StreamerTextSanitizer.l(chatContext.alo, n2) : n2;
            int n5 = chatContext.alp.get(n4);
            int n6 = chatContext.alp.get(n2);
            int n7 = chatContext.alp.get(n3 - 1) + 1;
            stringBuilder.append(string, n, n5);
            if (n4 < n2) {
                stringBuilder.append(string, n5, n6);
            }
            String string3 = StreamerTextSanitizer.a(matcher, string2);
            stringBuilder.append(string3);
            n = n7;
        } while (matcher.find());
        stringBuilder.append(string.substring(n));
        return stringBuilder.toString();
    }

    private static String a(String string, Pattern pattern, int n, String string2) {
        return StreamerTextSanitizer.a(string, pattern, n, string2, false, false);
    }

    private static String b(String string, Pattern pattern, int n, String string2) {
        return StreamerTextSanitizer.a(string, pattern, n, string2, false, true);
    }

    private static String c(String string, Pattern pattern, int n, String string2) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        ChatContext chatContext = StreamerTextSanitizer.dN(string);
        if (chatContext.alo.isEmpty()) {
            return string;
        }
        Matcher matcher = pattern.matcher(chatContext.alo);
        StringBuilder stringBuilder = new StringBuilder(string.length());
        int n2 = 0;
        boolean bl = false;
        while (matcher.find()) {
            if (n > matcher.groupCount()) continue;
            int n3 = matcher.start(n);
            int n4 = matcher.end(n);
            if (n3 < 0 || n4 <= n3 || !StreamerTextSanitizer.eb(chatContext.alo.substring(n3, n4))) continue;
            int n5 = chatContext.alp.get(n3);
            int n6 = n3 + StreamerTextSanitizer.h(chatContext.alo, n3, n4);
            int n7 = n6 > n3 ? chatContext.alp.get(n6 - 1) + 1 : n5;
            int n8 = chatContext.alp.get(n4 - 1) + 1;
            stringBuilder.append(string, n2, n5);
            if (n7 > n5) {
                stringBuilder.append(string, n5, n7);
            }
            stringBuilder.append(StreamerTextSanitizer.a(matcher, string2));
            n2 = n8;
            bl = true;
        }
        if (!bl) {
            return string;
        }
        stringBuilder.append(string.substring(n2));
        return stringBuilder.toString();
    }

    private static String a(String string, Pattern pattern, int n, String string2, boolean bl, boolean bl2) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        ChatContext chatContext = StreamerTextSanitizer.dN(string);
        if (chatContext.alo.isEmpty()) {
            return string;
        }
        Matcher matcher = pattern.matcher(chatContext.alo);
        if (!matcher.find()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        int n2 = 0;
        boolean bl3 = false;
        do {
            if (n > matcher.groupCount()) continue;
            int n3 = matcher.start(n);
            int n4 = matcher.end(n);
            if (n3 < 0 || n4 <= n3) continue;
            int n5 = bl ? StreamerTextSanitizer.l(chatContext.alo, n3) : n3;
            int n6 = bl2 ? StreamerTextSanitizer.m(chatContext.alo, n4) : n4;
            int n7 = chatContext.alp.get(n5);
            int n8 = chatContext.alp.get(n3);
            int n9 = chatContext.alp.get(n4 - 1) + 1;
            int n10 = chatContext.alp.get(n6 - 1) + 1;
            stringBuilder.append(string, n2, n7);
            if (n5 < n3) {
                stringBuilder.append(string, n7, n8);
            }
            Object object = StreamerTextSanitizer.a(matcher, string2);
            if (n6 > n4) {
                object = (String)object + string.substring(n9, n10);
            }
            stringBuilder.append((String)object);
            n2 = n10;
            bl3 = true;
        } while (matcher.find());
        if (!bl3) {
            return string;
        }
        stringBuilder.append(string.substring(n2));
        return stringBuilder.toString();
    }

    private static String a(Matcher matcher, String string) {
        if (string.indexOf(36) == -1 || matcher.groupCount() < 1) {
            return string;
        }
        String string2 = string;
        for (int i = 1; i <= matcher.groupCount(); ++i) {
            String string3 = matcher.group(i);
            string2 = string2.replace("$" + i, string3 == null ? "" : string3);
        }
        return string2;
    }

    private static ChatContext dN(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        ArrayList<Integer> arrayList = new ArrayList<Integer>(string.length());
        int n = 0;
        while (n < string.length()) {
            int n2;
            char c = string.charAt(n);
            int n3 = StreamerTextSanitizer.h(string, n);
            if (n3 > 0) {
                n += n3;
                continue;
            }
            if ((c == '\u00a7' || c == '&') && n + 1 < string.length() && StreamerTextSanitizer.d(string.charAt(n + 1))) {
                n += 2;
                continue;
            }
            if (c == '{' && (n2 = string.indexOf(125, n)) != -1 && StreamerTextSanitizer.e(string, n, n2)) {
                n = n2 + 1;
                continue;
            }
            if (c == '<' && (n2 = string.indexOf(62, n)) != -1 && StreamerTextSanitizer.d(string, n, n2)) {
                n = n2 + 1;
                continue;
            }
            stringBuilder.append(c);
            arrayList.add(n);
            ++n;
        }
        return new ChatContext(stringBuilder.toString(), arrayList);
    }

    private static String dO(String string) {
        int n = StreamerTextSanitizer.dP(string);
        return StreamerTextSanitizer.g(string, n);
    }

    private static int dP(String string) {
        char c;
        if (string == null || string.isEmpty()) {
            return -1;
        }
        Matcher matcher = ServerTextPatternMatcher.alY.matcher(string);
        if (!matcher.find()) {
            return -1;
        }
        int n = matcher.end();
        if (n < string.length() && ((c = string.charAt(n)) == ':' || c == '=')) {
            ++n;
        }
        return n;
    }

    private static String g(String string, int n) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        if (n < 0 || n >= string.length()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        stringBuilder.append(string, 0, n);
        for (int i = n; i < string.length(); ++i) {
            char c = string.charAt(i);
            stringBuilder.append(Character.isWhitespace(c) ? c : (char)'*');
        }
        return stringBuilder.toString();
    }

    private static int h(String string, int n) {
        if (n + 7 >= string.length()) {
            return 0;
        }
        char c = string.charAt(n);
        if (c != '\u00a7' && c != '&') {
            return 0;
        }
        char c2 = string.charAt(n + 1);
        if (c2 == '#') {
            return StreamerTextSanitizer.c(string, n + 2, n + 8) ? 8 : 0;
        }
        if (c2 == 'x' || c2 == 'X') {
            return StreamerTextSanitizer.c(string, n + 2, n + 8) ? 8 : 0;
        }
        return 0;
    }

    private static boolean c(String string, int n, int n2) {
        if (n < 0 || n2 > string.length()) {
            return false;
        }
        for (int i = n; i < n2; ++i) {
            boolean bl;
            char c = string.charAt(i);
            boolean bl2 = c >= '0' && c <= '9';
            boolean bl3 = c >= 'A' && c <= 'F';
            boolean bl4 = bl = c >= 'a' && c <= 'f';
            if (bl2 || bl3 || bl) continue;
            return false;
        }
        return true;
    }

    private static boolean d(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F' || c == 'x' || c == 'X' || c == 'k' || c == 'K' || c == 'l' || c == 'L' || c == 'm' || c == 'M' || c == 'n' || c == 'N' || c == 'o' || c == 'O' || c == 'r' || c == 'R';
    }

    private static boolean d(String string, int n, int n2) {
        if (n2 <= n + 2) {
            return false;
        }
        String string2 = string.substring(n + 1, n2);
        if (string2.length() == 7 && string2.charAt(0) == '#') {
            return StreamerTextSanitizer.i(string2, 1);
        }
        if (string2.length() == 8 && string2.charAt(0) == '/' && string2.charAt(1) == '#') {
            return StreamerTextSanitizer.i(string2, 2);
        }
        return false;
    }

    private static boolean e(String string, int n, int n2) {
        if (n2 <= n + 2) {
            return false;
        }
        String string2 = string.substring(n + 1, n2);
        if (string2.length() == 7 && string2.charAt(0) == '#') {
            return StreamerTextSanitizer.i(string2, 1);
        }
        if (string2.length() == 8 && string2.charAt(0) == '/' && string2.charAt(1) == '#') {
            return StreamerTextSanitizer.i(string2, 2);
        }
        return false;
    }

    private static boolean i(String string, int n) {
        for (int i = n; i < string.length(); ++i) {
            boolean bl;
            char c = string.charAt(i);
            boolean bl2 = c >= '0' && c <= '9';
            boolean bl3 = c >= 'A' && c <= 'F';
            boolean bl4 = bl = c >= 'a' && c <= 'f';
            if (bl2 || bl3 || bl) continue;
            return false;
        }
        return true;
    }

    private static String dQ(String string) {
        if (string == null || string.isBlank()) {
            return "";
        }
        String string2 = string.trim().replaceAll("[^0-9.,_\\s]", "");
        return string2.trim();
    }

    private static String dR(String string) {
        String string2 = StreamerTextSanitizer.dQ(string).replaceAll("[^0-9]", "");
        if (string2.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(string2.length() + string2.length() / 3);
        int n = string2.length() % 3;
        if (n == 0) {
            n = 3;
        }
        stringBuilder.append(string2, 0, n);
        for (int i = n; i < string2.length(); i += 3) {
            stringBuilder.append(',').append(string2, i, i + 3);
        }
        return stringBuilder.toString();
    }

    private static boolean dS(String string) {
        return string != null && string.codePointCount(0, string.length()) == 1;
    }

    private static boolean dT(String string) {
        String string2;
        if (string == null || string.isBlank()) {
            return false;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return false;
        }
        if (class_3102.field_1724 != null && class_3102.field_1724.method_7334() != null && (string2 = class_3102.field_1724.method_7334().getName()) != null && string.equalsIgnoreCase(string2)) {
            return true;
        }
        return class_3102.method_1548() != null && (string2 = class_3102.method_1548().method_1676()) != null && string.equalsIgnoreCase(string2);
    }

    private static boolean dU(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        for (String string2 : StreamerTextSanitizer.sE()) {
            if (string2 == null || string2.isBlank() || !StreamerTextSanitizer.dG(string2).matcher(string).find()) continue;
            return true;
        }
        String string3 = StreamerTextSanitizer.du(StreamerTextSanitizer.st());
        return string3 != null && !string3.isBlank() && StreamerTextSanitizer.dG(string3).matcher(string).find();
    }

    private static NameContext l(List<TextContext> list) {
        if (list.isEmpty()) {
            return new NameContext(list, false);
        }
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>(list.size());
        boolean bl = false;
        for (TextContext textContext : list) {
            class_2583 class_25832;
            class_2583 class_25833 = class_25832 = textContext.alw == null ? class_2583.field_24360 : textContext.alw;
            if (StreamerTextSanitizer.b(class_25832)) {
                arrayList.add(new TextContext(class_25832.method_27703(class_5251.method_27717((int)0xFFFFFF)), textContext.text));
                bl = true;
                continue;
            }
            arrayList.add(new TextContext(class_25832, textContext.text));
        }
        return new NameContext(bl ? arrayList : list, bl);
    }

    private static boolean b(class_2583 class_25832) {
        int n;
        int n2;
        class_5251 class_52512;
        class_5251 class_52513 = class_52512 = class_25832 == null ? null : class_25832.method_10973();
        if (class_52512 == null) {
            return true;
        }
        int n3 = class_52512.method_27716() & 0xFFFFFF;
        int n4 = n3 >> 16 & 0xFF;
        int n5 = Math.max(n4, Math.max(n2 = n3 >> 8 & 0xFF, n = n3 & 0xFF)) - Math.min(n4, Math.min(n2, n));
        return n5 <= 10 && n4 >= 80 && n4 <= 210;
    }

    private static String c(Object object) {
        Object object2;
        if (object == null) {
            return null;
        }
        try {
            object2 = object.getClass().getMethod("getNameForScoreboard", new Class[0]).invoke(object, new Object[0]);
            if (object2 instanceof String) {
                String string = (String)object2;
                return string;
            }
            if (object2 instanceof class_2561) {
                class_2561 class_25612 = (class_2561)object2;
                return class_25612.getString();
            }
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
        }
        try {
            object2 = object.getClass().getMethod("getName", new Class[0]).invoke(object, new Object[0]);
            if (object2 instanceof String) {
                String string = (String)object2;
                return string;
            }
            if (object2 instanceof class_2561) {
                class_2561 class_25613 = (class_2561)object2;
                return class_25613.getString();
            }
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
        }
        return object.toString();
    }

    private static class_5250 a(class_2561 class_25612, class_2588 class_25882) {
        Object[] sourceArguments = StreamerTextSanitizer.a(class_25882);
        Object[] sanitizedArguments = sourceArguments == null ? new Object[0] : new Object[sourceArguments.length];
        if (sourceArguments != null) {
            for (int i = 0; i < sourceArguments.length; ++i) {
                Object argument = sourceArguments[i];
                if (argument instanceof class_2561 textArgument) {
                    sanitizedArguments[i] = StreamerTextSanitizer.l(textArgument);
                } else if (argument instanceof String stringArgument) {
                    sanitizedArguments[i] = StreamerTextSanitizer.dq(stringArgument);
                } else {
                    sanitizedArguments[i] = argument;
                }
            }
        }
        String translationKey = StreamerTextSanitizer.b(class_25882);
        if (translationKey == null || translationKey.isBlank()) {
            return class_25612.method_27661();
        }
        String fallback = StreamerTextSanitizer.c(class_25882);
        class_5250 translated = fallback == null
            ? class_2561.method_43469(translationKey, sanitizedArguments)
            : class_2561.method_48322(translationKey, fallback, sanitizedArguments);
        return translated.method_10862(class_25612.method_10866());
    }

    private static Object[] a(class_2588 class_25882) {
        try {
            return (Object[])class_25882.getClass().getMethod("getArgs", new Class[0]).invoke((Object)class_25882, new Object[0]);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    private static String b(class_2588 class_25882) {
        try {
            return (String)class_25882.getClass().getMethod("getKey", new Class[0]).invoke((Object)class_25882, new Object[0]);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    private static String c(class_2588 class_25882) {
        try {
            return (String)class_25882.getClass().getMethod("getFallback", new Class[0]).invoke((Object)class_25882, new Object[0]);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    private static List<TextContext> c(class_5481 class_54812) {
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StringBuilder stringBuilder = new StringBuilder();
        class_2583[] class_2583Array = new class_2583[]{null};
        class_54812.accept((n, class_25832, n2) -> {
            class_2583 class_25833;
            class_2583 class_25834 = class_25833 = class_25832 == null ? class_2583.field_24360 : class_25832;
            if (class_2583Array[0] == null || !class_2583Array[0].equals((Object)class_25833)) {
                if (stringBuilder.length() > 0) {
                    arrayList.add(new TextContext(class_2583Array[0], stringBuilder.toString()));
                    stringBuilder.setLength(0);
                }
                class_2583Array[0] = class_25833;
            }
            stringBuilder.appendCodePoint(n2);
            return true;
        });
        if (stringBuilder.length() > 0) {
            arrayList.add(new TextContext(class_2583Array[0], stringBuilder.toString()));
        }
        return arrayList;
    }

    private static boolean dV(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        String string2 = PlayerNameSanitizer.sU().trim();
        return !string2.isEmpty() && string.contains(string2);
    }

    private static ScoreboardContext m(List<TextContext> list) {
        if (list == null || list.isEmpty()) {
            return new ScoreboardContext(list, false);
        }
        String string = StreamerTextSanitizer.n(list);
        if (string == null || string.isEmpty() || StreamerTextSanitizer.dV(string)) {
            return new ScoreboardContext(list, false);
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        String string2 = StreamerTextSanitizer.du(StreamerTextSanitizer.st());
        if (string2 != null && !string2.isBlank()) {
            arrayList.add(string2.trim());
        }
        for (String object : StreamerTextSanitizer.sE()) {
            String string3 = StreamerTextSanitizer.du(object);
            if (string3 == null || string3.isBlank()) continue;
            boolean bl = false;
            for (String string4 : arrayList) {
                if (!string4.equalsIgnoreCase(string3.trim())) continue;
                bl = true;
                break;
            }
            if (bl) continue;
            arrayList.add(string3.trim());
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        for (String string3 : arrayList) {
            Matcher matcher = StreamerTextSanitizer.dG(string3).matcher(string);
            if (!matcher.find()) continue;
            ArrayList arrayList2 = new ArrayList();
            StreamerTextSanitizer.a((List<TextContext>)arrayList2, string, list2, 0, matcher.start());
            class_2583 badgeStyle = class_2583.field_24360.method_27703(class_5251.method_27717((int)0x5555FF)).method_10982(Boolean.valueOf(true));
            StreamerTextSanitizer.b((List<TextContext>)arrayList2, badgeStyle, PlayerNameSanitizer.sU());
            StreamerTextSanitizer.a((List<TextContext>)arrayList2, string, list2, matcher.start(), string.length());
            return new ScoreboardContext(arrayList2, true);
        }
        return new ScoreboardContext(list, false);
    }

    private static String n(List<TextContext> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TextContext textContext : list) {
            stringBuilder.append(textContext.text);
        }
        return stringBuilder.toString();
    }

    private static List<TextContext> b(List<TextContext> list, int n) {
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        int n2 = 0;
        for (TextContext textContext : list) {
            int n3 = n2 + textContext.text.length();
            if (n3 <= n) {
                arrayList.add(textContext);
            } else if (n2 < n) {
                arrayList.add(new TextContext(textContext.alw, textContext.text.substring(0, n - n2)));
            }
            n2 = n3;
        }
        return arrayList;
    }

    private static List<TextContext> c(List<TextContext> list, int n) {
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        int n2 = 0;
        for (TextContext textContext : list) {
            int n3 = n2 + textContext.text.length();
            if (n2 >= n) {
                arrayList.add(textContext);
            } else if (n3 > n) {
                arrayList.add(new TextContext(textContext.alw, textContext.text.substring(n - n2)));
            }
            n2 = n3;
        }
        return arrayList;
    }

    private static class_5481 o(List<TextContext> list) {
        return class_52242 -> {
            int n = 0;
            for (TextContext textContext : list) {
                int n2;
                class_2583 class_25832 = textContext.alw == null ? class_2583.field_24360 : textContext.alw;
                String string = textContext.text;
                for (int i = 0; i < string.length(); i += n2) {
                    int n3 = string.codePointAt(i);
                    if (!class_52242.accept(n, class_25832, n3)) {
                        return false;
                    }
                    n2 = Character.charCount(n3);
                    n += n2;
                }
            }
            return true;
        };
    }

    private static class_5481 b(String string, class_2583 class_25832) {
        class_2583 class_25833 = class_25832 == null ? class_2583.field_24360 : class_25832;
        return class_52242 -> {
            int n;
            int n2 = 0;
            for (int i = 0; i < string.length(); i += n) {
                int n3 = string.codePointAt(i);
                if (!class_52242.accept(n2, class_25833, n3)) {
                    return false;
                }
                n = Character.charCount(n3);
                n2 += n;
            }
            return true;
        };
    }

    private static TabListContext a(List<TextContext> list, boolean bl) {
        String string;
        String string2;
        Map<String, String> map;
        List<TextContext> list2 = StreamerTextSanitizer.q(list);
        boolean bl2 = false;
        String string22 = StreamerTextSanitizer.st();
        String string3 = StreamerTextSanitizer.sH();
        boolean bl3 = bl && StreamerTextSanitizer.dW(StreamerTextSanitizer.n(list2));
        ScoreboardContext scoreboardContext = new ScoreboardContext(list2, false);
        if (!string22.isBlank()) {
            scoreboardContext = StreamerTextSanitizer.c(list2, ServerTextPatternMatcher.alX, 0, string22, null);
            list2 = scoreboardContext.als;
            bl2 |= scoreboardContext.alt;
        }
        if (!string3.isBlank() && !bl3 && bl) {
            List<String> candidates = bl ? StreamerTextSanitizer.sE() : StreamerTextSanitizer.ag(false);
            for (String candidate : candidates) {
                if (candidate == null || candidate.isBlank()) continue;
                string2 = StreamerTextSanitizer.n(list2);
                if (bl && !StreamerTextSanitizer.ao(string2, candidate)) continue;
                if (bl && TextSanitizationMode.sX() == TextSanitizationMode.Strategy.amo) {
                    if (StreamerTextSanitizer.an(string2, candidate)) continue;
                    scoreboardContext = StreamerTextSanitizer.a(list2, StreamerTextSanitizer.dH(candidate), string3);
                    if (!scoreboardContext.alt) {
                        scoreboardContext = StreamerTextSanitizer.a(list2, StreamerTextSanitizer.dI(candidate), string3, true, true, false, 0xFF0000);
                    }
                } else {
                    if (StreamerTextSanitizer.am(string2, candidate)) continue;
                    if (bl) {
                        scoreboardContext = StreamerTextSanitizer.a(list2, StreamerTextSanitizer.dH(candidate), string3, true, true);
                        if (!scoreboardContext.alt) {
                            scoreboardContext = StreamerTextSanitizer.a(list2, StreamerTextSanitizer.dI(candidate), string3, true, true);
                        }
                    } else if (TextSanitizationMode.sX() == TextSanitizationMode.Strategy.amp) {
                        scoreboardContext = StreamerTextSanitizer.a(list2, StreamerTextSanitizer.dH(candidate), string3, false, true);
                        if (!scoreboardContext.alt) {
                            scoreboardContext = StreamerTextSanitizer.a(list2, StreamerTextSanitizer.dI(candidate), string3, false, true);
                        }
                        if (!scoreboardContext.alt) {
                            scoreboardContext = StreamerTextSanitizer.a(list2, candidate, StreamerTextSanitizer.sJ(), (Integer)0xFF0000, true);
                        }
                        if (!scoreboardContext.alt) {
                            scoreboardContext = StreamerTextSanitizer.a(list2, candidate, StreamerTextSanitizer.sJ(), (Integer)0xFF0000, true, false);
                        }
                        if (!scoreboardContext.alt) {
                            scoreboardContext = StreamerTextSanitizer.b(list2, candidate, StreamerTextSanitizer.sJ(), 0xFF0000, true);
                        }
                    } else {
                        scoreboardContext = StreamerTextSanitizer.a(list2, StreamerTextSanitizer.dH(candidate), string3, false, false);
                        if (!scoreboardContext.alt) {
                            scoreboardContext = StreamerTextSanitizer.a(list2, candidate, StreamerTextSanitizer.sJ(), (Integer)0xFF0000, false, false);
                        }
                        if (!scoreboardContext.alt) {
                            scoreboardContext = StreamerTextSanitizer.b(list2, candidate, StreamerTextSanitizer.sJ(), 0xFF0000, false);
                        }
                    }
                }
                list2 = scoreboardContext.als;
                bl2 |= scoreboardContext.alt;
            }
            scoreboardContext = StreamerTextSanitizer.a(list2, string3, (Integer)0xFF0000);
            list2 = scoreboardContext.als;
            bl2 |= scoreboardContext.alt;
        } else if (!string3.isBlank() && !bl3) {
            scoreboardContext = StreamerTextSanitizer.a(list2, string3, (Integer)0xFF0000);
            list2 = scoreboardContext.als;
            bl2 |= scoreboardContext.alt;
        }
        if (!string22.isBlank()) {
            for (String string4 : StreamerTextSanitizer.sE()) {
                if (string4 == null || string4.isBlank() || string4.equalsIgnoreCase(string22)) continue;
                scoreboardContext = StreamerTextSanitizer.d(list2, StreamerTextSanitizer.dG(string4), 0, string22, null);
                list2 = scoreboardContext.als;
                bl2 |= scoreboardContext.alt;
            }
        }
        scoreboardContext = StreamerTextSanitizer.g(list2);
        list2 = scoreboardContext.als;
        bl2 |= scoreboardContext.alt;
        map = StreamerTextSanitizer.sN();
        if (!map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                string2 = entry.getKey();
                String string5 = entry.getValue();
                if (string5 == null || string5.isBlank() || string2.equalsIgnoreCase(string5)) continue;
                scoreboardContext = StreamerTextSanitizer.d(list2, StreamerTextSanitizer.dG(string2), 0, string5, null);
                list2 = scoreboardContext.als;
                bl2 |= scoreboardContext.alt;
            }
        }
        if (!(string = StreamerTextSanitizer.hN()).isBlank()) {
            scoreboardContext = StreamerTextSanitizer.b(list2, string, (Integer)StreamerTextSanitizer.sP());
            list2 = scoreboardContext.als;
            bl2 |= scoreboardContext.alt;
        }
        if (StreamerTextSanitizer.hK()) {
            scoreboardContext = StreamerTextSanitizer.a(list2, ServerTextPatternMatcher.alU, 3, StreamerTextSanitizer.af(false), null);
            list2 = scoreboardContext.als;
            bl2 |= scoreboardContext.alt;
            scoreboardContext = StreamerTextSanitizer.a(list2, ServerTextPatternMatcher.alV, 3, StreamerTextSanitizer.af(true), null);
            list2 = scoreboardContext.als;
            bl2 |= scoreboardContext.alt;
        }
        return new TabListContext(list2, bl2);
    }

    private static boolean dW(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        return string2.contains("\u0438\u0441\u0446\u0435\u043b\u0435\u043d") || string2.contains("\u0438\u0441\u0446\u0435\u043b\u0451\u043d") || string2.contains("\u0438\u0441\u0446\u0435\u043b\u0438\u043b\u0438") || string2.contains("\u0432\u044b\u043b\u0435\u0447\u0438\u043b") || string2.contains("\u0432\u044b\u043b\u0435\u0447\u0438\u043b\u0438") || string2.contains("healed");
    }

    private static List<String> ag(boolean bl) {
        ArrayList<String> arrayList = new ArrayList<String>(StreamerTextSanitizer.sE());
        if (!bl) {
            return arrayList;
        }
        String string = StreamerTextSanitizer.st();
        if (string.isBlank()) {
            return arrayList;
        }
        for (String string2 : arrayList) {
            if (!string.equalsIgnoreCase(string2)) continue;
            return arrayList;
        }
        arrayList.add(string);
        String string3 = StreamerTextSanitizer.du(string);
        if (!string3.isBlank()) {
            boolean bl2 = false;
            for (String string4 : arrayList) {
                if (!string3.equalsIgnoreCase(string4)) continue;
                bl2 = true;
                break;
            }
            if (!bl2) {
                arrayList.add(string3);
            }
        }
        return arrayList;
    }

    private static ScoreboardContext a(List<TextContext> list, String string, Integer n) {
        if (list.isEmpty() || string == null || string.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string2 = StreamerTextSanitizer.n(list);
        Matcher matcher = ServerTextPatternMatcher.alS.matcher(string2);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        int n2 = 0;
        boolean bl = false;
        do {
            int n3 = matcher.end(1);
            int n4 = matcher.start(2);
            int n5 = matcher.end(2);
            if (n3 <= 0 || n4 < 0 || n5 <= n4) continue;
            StreamerTextSanitizer.a(arrayList, string2, list2, n2, n3);
            class_2583 class_25832 = StreamerTextSanitizer.d(list2, Math.max(n3 - 1, 0));
            StreamerTextSanitizer.b(arrayList, class_25832, " ");
            class_2583 class_25833 = StreamerTextSanitizer.d(list2, n4);
            class_2583 class_25834 = n == null ? class_25833 : class_25833.method_27703(class_5251.method_27717((int)n));
            StreamerTextSanitizer.c(arrayList, class_25834, string);
            n2 = n5;
            bl = true;
        } while (matcher.find());
        StreamerTextSanitizer.a(arrayList, string2, list2, n2, string2.length());
        return new ScoreboardContext(arrayList, bl);
    }

    private static ScoreboardContext a(List<TextContext> list, Pattern pattern, String string) {
        return StreamerTextSanitizer.a(list, pattern, string, true, false);
    }

    private static ScoreboardContext a(List<TextContext> list, Pattern pattern, String string, boolean bl, boolean bl2) {
        return StreamerTextSanitizer.a(list, pattern, string, bl, bl2, true);
    }

    private static ScoreboardContext a(List<TextContext> list, Pattern pattern, String string, boolean bl, boolean bl2, boolean bl3) {
        return StreamerTextSanitizer.a(list, pattern, string, bl, bl2, bl3, 0xFF0000);
    }

    private static ScoreboardContext a(List<TextContext> list, Pattern pattern, String string, boolean bl, boolean bl2, boolean bl3, Integer n) {
        if (list.isEmpty() || string == null || string.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string2 = StreamerTextSanitizer.n(list);
        Matcher matcher = pattern.matcher(string2);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        int n2 = 0;
        boolean bl4 = false;
        do {
            int n3 = matcher.start(1);
            int n4 = matcher.end(1);
            if (n3 < 0 || n4 <= n3) continue;
            String string3 = string2.substring(n3, n4);
            boolean bl5 = StreamerTextSanitizer.a(list2, n3, n4);
            String string4 = StreamerTextSanitizer.dY(string3);
            boolean bl6 = StreamerTextSanitizer.ef(string4);
            if (!StreamerTextSanitizer.dX(string3) && !bl5 && !bl6 || bl3 && !bl5 && !bl6 && !StreamerTextSanitizer.eb(string4)) continue;
            StreamerTextSanitizer.a(arrayList, string2, list2, n2, n3);
            int n5 = StreamerTextSanitizer.f(string2, n3, n4);
            if (n5 <= n3) {
                n5 = n3 + StreamerTextSanitizer.h(string2, n3, n4);
            }
            if (n5 > n3) {
                StreamerTextSanitizer.a(arrayList, string2, list2, n3, n5);
            }
            class_2583 class_25832 = StreamerTextSanitizer.d(list2, Math.min(n5, n4 - 1));
            class_2583 class_25833 = StreamerTextSanitizer.a(class_25832, (Integer)0xFFFFFF, bl2);
            class_2583 class_25834 = StreamerTextSanitizer.a(class_25832, n, bl2);
            if (bl) {
                StreamerTextSanitizer.b(arrayList, class_25833, "[");
            }
            StreamerTextSanitizer.c(arrayList, class_25834, string);
            if (bl) {
                StreamerTextSanitizer.b(arrayList, class_25833, "] ");
            } else {
                StreamerTextSanitizer.b(arrayList, class_25834, " ");
            }
            n2 = n4;
            bl4 = true;
        } while (matcher.find());
        StreamerTextSanitizer.a(arrayList, string2, list2, n2, string2.length());
        return new ScoreboardContext(arrayList, bl4);
    }

    private static boolean dX(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        String string2 = StreamerTextSanitizer.dY(string);
        if (string2.isBlank()) {
            return false;
        }
        String string3 = StreamerTextSanitizer.eg(string2).trim();
        return !string3.isBlank() && !StreamerTextSanitizer.ed(string3);
    }

    private static String dY(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        String string2 = PlayerNameSanitizer.sU();
        String string3 = string;
        if (string2 != null && !string2.isBlank()) {
            string3 = string3.replace(string2, "");
            string3 = string3.replace(string2.trim(), "");
        }
        return string3;
    }

    private static int f(String string, int n, int n2) {
        int n3;
        if (string == null || n < 0 || n2 <= n || n >= string.length()) {
            return -1;
        }
        String string2 = PlayerNameSanitizer.sU();
        if (string2 == null || string2.isBlank()) {
            return -1;
        }
        for (n3 = n; n3 < n2 && Character.isWhitespace(string.charAt(n3)); ++n3) {
        }
        if (!string.regionMatches(n3, string2, 0, string2.length())) {
            String string3 = string2.trim();
            if (string3.isEmpty() || !string.regionMatches(n3, string3, 0, string3.length())) {
                return -1;
            }
            n3 += string3.length();
        } else {
            n3 += string2.length();
        }
        while (n3 < n2 && Character.isWhitespace(string.charAt(n3))) {
            ++n3;
        }
        return n3;
    }

    private static String e(String string, String string2, String string3) {
        if (string == null || string.isEmpty() || string2 == null || string2.isBlank() || string3 == null || string3.isBlank()) {
            return string;
        }
        ChatContext chatContext = StreamerTextSanitizer.dN(string);
        if (chatContext.alo.isEmpty()) {
            return string;
        }
        Matcher matcher = StreamerTextSanitizer.dG(string2).matcher(chatContext.alo);
        if (!matcher.find()) {
            return string;
        }
        int n = matcher.start();
        int n2 = StreamerTextSanitizer.n(chatContext.alo, n);
        if (n2 < 0 || n2 >= n) {
            return string;
        }
        if (!StreamerTextSanitizer.eb(chatContext.alo.substring(n2, n))) {
            return string;
        }
        int n3 = chatContext.alp.get(n2);
        int n4 = n2 + StreamerTextSanitizer.h(chatContext.alo, n2, n);
        int n5 = n4 > n2 ? chatContext.alp.get(n4 - 1) + 1 : n3;
        int n6 = chatContext.alp.get(n);
        return string.substring(0, n5) + string3 + string.substring(n6);
    }

    private static ScoreboardContext a(List<TextContext> list, String string, String string2, Integer n) {
        return StreamerTextSanitizer.a(list, string, string2, n, false);
    }

    private static ScoreboardContext a(List<TextContext> list, String string, String string2, Integer n, boolean bl) {
        return StreamerTextSanitizer.a(list, string, string2, n, bl, true);
    }

    private static ScoreboardContext a(List<TextContext> list, String string, String string2, Integer n, boolean bl, boolean bl2) {
        if (list.isEmpty() || string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string3 = StreamerTextSanitizer.n(list);
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        Matcher matcher = StreamerTextSanitizer.dG(string).matcher(string3);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        int n2 = matcher.start();
        int n3 = StreamerTextSanitizer.n(string3, n2);
        if (n3 < 0 || n3 >= n2) {
            return new ScoreboardContext(list, false);
        }
        String string4 = string3.substring(n3, n2);
        if (bl2 && !StreamerTextSanitizer.eb(string4)) {
            return new ScoreboardContext(list, false);
        }
        if (!(bl2 || StreamerTextSanitizer.ea(string4) || StreamerTextSanitizer.a(list2, n3, n2))) {
            return new ScoreboardContext(list, false);
        }
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StreamerTextSanitizer.a(arrayList, string3, list2, 0, n3);
        int n4 = n3 + StreamerTextSanitizer.h(string3, n3, n2);
        if (n4 > n3) {
            StreamerTextSanitizer.a(arrayList, string3, list2, n3, n4);
        }
        class_2583 class_25832 = StreamerTextSanitizer.d(list2, Math.min(n4, n2 - 1));
        class_2583 class_25833 = StreamerTextSanitizer.a(class_25832, n, bl);
        StreamerTextSanitizer.c(arrayList, class_25833, string2);
        StreamerTextSanitizer.a(arrayList, string3, list2, n2, string3.length());
        return new ScoreboardContext(arrayList, true);
    }

    private static boolean a(List<class_2583> list, int n, int n2) {
        if (list == null || list.isEmpty() || n < 0 || n2 <= n) {
            return false;
        }
        int n3 = Math.min(n2, list.size());
        for (int i = Math.min(n, list.size() - 1); i < n3; ++i) {
            class_2583 class_25832 = list.get(i);
            if (class_25832 == null || class_25832.method_27708() == null || class_25832.method_27708().equals((Object)class_2583.field_24360.method_27708())) continue;
            return true;
        }
        return false;
    }

    private static boolean a(List<class_2583> list, int n, int n2, String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.trim();
        if (string2.isBlank() || string2.length() > 32 || StreamerTextSanitizer.dZ(string2)) {
            return false;
        }
        if (string2.indexOf(62) >= 0 || string2.indexOf(58) >= 0 || string2.indexOf(124) >= 0 || string2.indexOf(10) >= 0 || string2.indexOf(13) >= 0) {
            return false;
        }
        return StreamerTextSanitizer.a(list, n, n2) || StreamerTextSanitizer.ef(string2) || StreamerTextSanitizer.ea(string2);
    }

    private static ScoreboardContext b(List<TextContext> list, String string, String string2, Integer n, boolean bl) {
        boolean bl2;
        if (list.isEmpty() || string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string3 = StreamerTextSanitizer.n(list);
        Matcher matcher = StreamerTextSanitizer.dG(string).matcher(string3);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        int n2 = matcher.start();
        int n3 = StreamerTextSanitizer.k(string3, n2);
        if (n3 < 0 || n3 >= n2) {
            return new ScoreboardContext(list, false);
        }
        String string4 = string3.substring(n3, n2).trim();
        if (string4.isBlank() || StreamerTextSanitizer.dZ(string4)) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        boolean bl3 = StreamerTextSanitizer.a(list2, n3, n2);
        boolean bl4 = bl2 = StreamerTextSanitizer.ef(string4) || StreamerTextSanitizer.ea(string4) || StreamerTextSanitizer.eb(string4);
        if (!bl3 && !bl2) {
            return new ScoreboardContext(list, false);
        }
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StreamerTextSanitizer.a(arrayList, string3, list2, 0, n3);
        int n4 = n3 + StreamerTextSanitizer.h(string3, n3, n2);
        if (n4 > n3) {
            StreamerTextSanitizer.a(arrayList, string3, list2, n3, n4);
        }
        class_2583 class_25832 = StreamerTextSanitizer.d(list2, Math.min(n4, n2 - 1));
        class_2583 class_25833 = StreamerTextSanitizer.a(class_25832, n, bl);
        StreamerTextSanitizer.c(arrayList, class_25833, string2);
        StreamerTextSanitizer.a(arrayList, string3, list2, n2, string3.length());
        return new ScoreboardContext(arrayList, true);
    }

    private static ScoreboardContext c(List<TextContext> list, String string, String string2, Integer n, boolean bl) {
        if (list.isEmpty() || string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string3 = StreamerTextSanitizer.n(list);
        Matcher matcher = StreamerTextSanitizer.dG(string).matcher(string3);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        int n2 = matcher.start();
        int n3 = StreamerTextSanitizer.k(string3, n2);
        if (n3 < 0 || n3 >= n2) {
            return new ScoreboardContext(list, false);
        }
        int n4 = StreamerTextSanitizer.g(string3, n3, n2);
        if (n4 <= n3 || n4 >= n2) {
            return new ScoreboardContext(list, false);
        }
        String string4 = string3.substring(n4, n2).trim();
        if (string4.isBlank() || StreamerTextSanitizer.dZ(string4)) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StreamerTextSanitizer.a(arrayList, string3, list2, 0, n4);
        class_2583 class_25832 = StreamerTextSanitizer.d(list2, n4);
        class_2583 class_25833 = StreamerTextSanitizer.a(class_25832, n, bl);
        StreamerTextSanitizer.c(arrayList, class_25833, string2);
        StreamerTextSanitizer.a(arrayList, string3, list2, n2, string3.length());
        return new ScoreboardContext(arrayList, true);
    }

    private static int g(String string, int n, int n2) {
        int n3;
        if (string == null || n < 0 || n2 <= n) {
            return -1;
        }
        boolean bl = false;
        for (n3 = n; n3 < n2 && Character.isWhitespace(string.charAt(n3)); ++n3) {
        }
        if (n3 >= n2) {
            return -1;
        }
        int n4 = n3;
        int n5 = string.codePointAt(n3);
        if (!Character.isLetterOrDigit(n5)) {
            bl = true;
            n3 += Character.charCount(n5);
        }
        if (!bl || n3 >= n2 || !Character.isWhitespace(string.charAt(n3))) {
            return -1;
        }
        while (n3 < n2 && Character.isWhitespace(string.charAt(n3))) {
            ++n3;
        }
        return n3 > n4 ? n3 : -1;
    }

    private static ScoreboardContext d(List<TextContext> list, String string, String string2, Integer n, boolean bl) {
        int n2;
        int n3;
        int n4;
        int n5;
        if (list.isEmpty() || string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string3 = StreamerTextSanitizer.n(list);
        Matcher matcher = StreamerTextSanitizer.dG(string).matcher(string3);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        for (n5 = n4 = matcher.start(); n5 > 0 && Character.isWhitespace(string3.charAt(n5 - 1)); --n5) {
        }
        if (n5 <= 0 || n5 >= n4) {
            return new ScoreboardContext(list, false);
        }
        for (n3 = n5; n3 > 0 && !Character.isWhitespace(string3.charAt(n3 - 1)); --n3) {
        }
        if (n3 >= n5) {
            return new ScoreboardContext(list, false);
        }
        for (n2 = n3; n2 > 0 && Character.isWhitespace(string3.charAt(n2 - 1)); --n2) {
        }
        if (n2 <= 0 || !StreamerTextSanitizer.bn(string3.codePointBefore(n2))) {
            return new ScoreboardContext(list, false);
        }
        String string4 = string3.substring(n3, n5).trim();
        if (string4.isBlank() || StreamerTextSanitizer.dZ(string4)) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StreamerTextSanitizer.a(arrayList, string3, list2, 0, n3);
        class_2583 class_25832 = StreamerTextSanitizer.d(list2, n3);
        class_2583 class_25833 = StreamerTextSanitizer.a(class_25832, n, bl);
        StreamerTextSanitizer.c(arrayList, class_25833, string2);
        StreamerTextSanitizer.a(arrayList, string3, list2, n4, string3.length());
        return new ScoreboardContext(arrayList, true);
    }

    private static ScoreboardContext e(List<TextContext> list, String string, String string2, Integer n, boolean bl) {
        int n2;
        int n3;
        int n4;
        if (list.isEmpty() || string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string3 = StreamerTextSanitizer.n(list);
        Matcher matcher = StreamerTextSanitizer.dG(string).matcher(string3);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        for (n4 = n3 = matcher.start(); n4 > 0 && Character.isWhitespace(string3.charAt(n4 - 1)); --n4) {
        }
        if (n4 <= 0 || n4 >= n3) {
            return new ScoreboardContext(list, false);
        }
        for (n2 = n4; n2 > 0 && !Character.isWhitespace(string3.charAt(n2 - 1)); --n2) {
        }
        if (n2 >= n4) {
            return new ScoreboardContext(list, false);
        }
        String string4 = string3.substring(n2, n4).trim();
        if (string4.isBlank() || StreamerTextSanitizer.dZ(string4)) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StreamerTextSanitizer.a(arrayList, string3, list2, 0, n2);
        class_2583 class_25832 = StreamerTextSanitizer.d(list2, n2);
        class_2583 class_25833 = StreamerTextSanitizer.a(class_25832, n, bl);
        StreamerTextSanitizer.c(arrayList, class_25833, string2);
        StreamerTextSanitizer.a(arrayList, string3, list2, n3, string3.length());
        return new ScoreboardContext(arrayList, true);
    }

    private static ScoreboardContext f(List<TextContext> list, String string, String string2, Integer n, boolean bl) {
        if (list.isEmpty() || string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string3 = StreamerTextSanitizer.n(list);
        Matcher matcher = StreamerTextSanitizer.dG(string).matcher(string3);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        int n2 = matcher.start();
        int n3 = StreamerTextSanitizer.k(string3, n2);
        if (n3 < 0 || n3 >= n2) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        int n4 = StreamerTextSanitizer.f(string3, n3, n2);
        if (n4 <= n3) {
            n4 = n3;
        }
        while (n4 < n2 && Character.isWhitespace(string3.charAt(n4))) {
            ++n4;
        }
        if (n4 >= n2) {
            return new ScoreboardContext(list, false);
        }
        String string4 = string3.substring(n4, n2).trim();
        if (string4.isBlank() || StreamerTextSanitizer.dZ(string4)) {
            return new ScoreboardContext(list, false);
        }
        if (!StreamerTextSanitizer.a(list2, n4, n2) && !StreamerTextSanitizer.ef(string4)) {
            return new ScoreboardContext(list, false);
        }
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StreamerTextSanitizer.a(arrayList, string3, list2, 0, n3);
        int n5 = StreamerTextSanitizer.f(string3, n3, n2);
        if (n5 > n3) {
            StreamerTextSanitizer.a(arrayList, string3, list2, n3, n5);
        }
        class_2583 class_25832 = StreamerTextSanitizer.d(list2, Math.max(n4, Math.min(n2 - 1, list2.size() - 1)));
        class_2583 class_25833 = StreamerTextSanitizer.a(class_25832, n, bl);
        StreamerTextSanitizer.c(arrayList, class_25833, string2);
        StreamerTextSanitizer.a(arrayList, string3, list2, n2, string3.length());
        return new ScoreboardContext(arrayList, true);
    }

    private static ScoreboardContext g(List<TextContext> list, String string, String string2, Integer n, boolean bl) {
        int n2;
        int n3;
        int n4;
        if (list.isEmpty() || string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string3 = StreamerTextSanitizer.n(list);
        Matcher matcher = StreamerTextSanitizer.dG(string).matcher(string3);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        for (n4 = n3 = matcher.end(); n4 < string3.length() && Character.isWhitespace(string3.charAt(n4)); ++n4) {
        }
        if (n4 >= string3.length()) {
            return new ScoreboardContext(list, false);
        }
        for (n2 = n4; n2 < string3.length() && !Character.isWhitespace(string3.charAt(n2)); ++n2) {
        }
        if (n4 >= n2) {
            return new ScoreboardContext(list, false);
        }
        String string4 = string3.substring(n4, n2).trim();
        if (string4.isBlank() || StreamerTextSanitizer.dZ(string4)) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StreamerTextSanitizer.a(arrayList, string3, list2, 0, n4);
        class_2583 class_25832 = StreamerTextSanitizer.d(list2, n4);
        class_2583 class_25833 = StreamerTextSanitizer.a(class_25832, n, bl);
        StreamerTextSanitizer.c(arrayList, class_25833, string2.trim());
        StreamerTextSanitizer.a(arrayList, string3, list2, n2, string3.length());
        return new ScoreboardContext(arrayList, true);
    }

    private static ScoreboardContext h(List<TextContext> list, String string, String string2, Integer n, boolean bl) {
        if (list.isEmpty() || string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string3 = StreamerTextSanitizer.n(list);
        Matcher matcher = StreamerTextSanitizer.dG(string).matcher(string3);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        int n2 = matcher.start();
        int n3 = StreamerTextSanitizer.j(string3, n2);
        if (n3 < 0 || n3 >= n2) {
            return new ScoreboardContext(list, false);
        }
        String string4 = string3.substring(n3, n2).trim();
        if (string4.isBlank() || StreamerTextSanitizer.dZ(string4)) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StreamerTextSanitizer.a(arrayList, string3, list2, 0, n3);
        class_2583 class_25832 = StreamerTextSanitizer.d(list2, n3);
        class_2583 class_25833 = StreamerTextSanitizer.a(class_25832, n, bl);
        StreamerTextSanitizer.c(arrayList, class_25833, string2);
        StreamerTextSanitizer.a(arrayList, string3, list2, n2, string3.length());
        return new ScoreboardContext(arrayList, true);
    }

    private static int j(String string, int n) {
        int n2;
        if (string == null || n <= 0 || n > string.length()) {
            return -1;
        }
        for (n2 = n - 1; n2 >= 0 && Character.isWhitespace(string.charAt(n2)); --n2) {
        }
        if (n2 < 0) {
            return -1;
        }
        int n3 = -1;
        int n4 = Math.max(0, n - 80);
        int n5 = n2;
        while (n5 >= n4) {
            int n6 = string.codePointBefore(n5 + 1);
            int n7 = n5 + 1 - Character.charCount(n6);
            if (StreamerTextSanitizer.bn(n6)) {
                n3 = n7 + Character.charCount(n6);
                break;
            }
            n5 = n7 - 1;
        }
        if (n3 < 0 || n3 >= n) {
            return -1;
        }
        for (n5 = n3; n5 < n && Character.isWhitespace(string.charAt(n5)); ++n5) {
        }
        return n5 >= n ? -1 : n5;
    }

    private static int h(String string, int n, int n2) {
        if (string == null || n < 0 || n2 <= n || n >= string.length()) {
            return 0;
        }
        int n3 = n;
        int n4 = Math.min(n2, string.length());
        boolean bl = false;
        while (n3 < n4) {
            int n5 = string.codePointAt(n3);
            if (Character.isWhitespace(n5)) {
                n3 += Character.charCount(n5);
                continue;
            }
            if (!StreamerTextSanitizer.bn(n5)) break;
            bl = true;
            n3 += Character.charCount(n5);
        }
        if (!bl || n3 >= n4) {
            return 0;
        }
        return n3 - n;
    }

    private static boolean bn(int n) {
        if (n == 91 || n == 38 || n == 167) {
            return false;
        }
        if (Character.isLetterOrDigit(n)) {
            return false;
        }
        int n2 = Character.getType(n);
        return n2 == 28 || n2 == 18 || n2 == 25 || n2 == 27 || n2 == 20 || n == 8226 || n == 9679 || n == 9670 || n == 10070 || n == 10010;
    }

    private static int k(String string, int n) {
        char c;
        int n2;
        int n3;
        if (string == null || string.isEmpty() || n <= 0 || n > string.length()) {
            return -1;
        }
        for (n3 = n - 1; n3 >= 0 && Character.isWhitespace(string.charAt(n3)); --n3) {
        }
        if (n3 < 0) {
            return -1;
        }
        int n4 = Math.max(0, n - 64);
        for (n2 = n3; n2 > n4 && (c = string.charAt(n2 - 1)) != '<' && c != '>' && c != ':' && c != '|' && c != ',' && c != '\n' && c != '\r'; --n2) {
        }
        while (n2 < n && Character.isWhitespace(string.charAt(n2))) {
            ++n2;
        }
        return n2 >= n ? -1 : n2;
    }

    private static boolean dZ(String string) {
        if (string == null || string.isBlank()) {
            return true;
        }
        String string2 = StreamerTextSanitizer.eg(string.trim());
        return string2.isBlank() || StreamerTextSanitizer.ed(string2);
    }

    private static boolean ea(String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.trim();
        if (!string2.startsWith("[") || !string2.endsWith("]")) {
            return false;
        }
        String string3 = StreamerTextSanitizer.eg(string2);
        return !string3.isBlank() && !StreamerTextSanitizer.ed(string3);
    }

    private static ScoreboardContext b(List<TextContext> list, String string, Integer n) {
        if (list.isEmpty() || string == null || string.isBlank()) {
            return new ScoreboardContext(list, false);
        }
        String string2 = StreamerTextSanitizer.n(list);
        Matcher matcher = ServerTextPatternMatcher.alQ.matcher(string2);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        int n2 = 0;
        boolean bl = false;
        do {
            int n3 = matcher.start(2);
            int n4 = matcher.end(2);
            if (n3 < 0 || n4 <= n3) continue;
            StreamerTextSanitizer.a(arrayList, string2, list2, n2, n3);
            class_2583 class_25832 = StreamerTextSanitizer.d(list2, n3);
            class_2583 class_25833 = n == null ? class_25832 : class_25832.method_27703(class_5251.method_27717((int)n));
            StreamerTextSanitizer.b(arrayList, class_25833, string);
            if (StreamerTextSanitizer.sS()) {
                StreamerTextSanitizer.b(arrayList, class_25833, " ");
            }
            n2 = StreamerTextSanitizer.sS() ? StreamerTextSanitizer.m(string2, n4) : n4;
            bl = true;
        } while (matcher.find());
        StreamerTextSanitizer.a(arrayList, string2, list2, n2, string2.length());
        return new ScoreboardContext(arrayList, bl);
    }

    private static ScoreboardContext a(List<TextContext> list, Pattern pattern, int n, String string, Integer n2) {
        return StreamerTextSanitizer.a(list, pattern, n, string, n2, false, false);
    }

    private static ScoreboardContext b(List<TextContext> list, Pattern pattern, int n, String string, Integer n2) {
        return StreamerTextSanitizer.a(list, pattern, n, string, n2, true, false);
    }

    private static ScoreboardContext c(List<TextContext> list, Pattern pattern, int n, String string, Integer n2) {
        return StreamerTextSanitizer.a(list, pattern, n, string, n2, false, false, true);
    }

    private static ScoreboardContext d(List<TextContext> list, Pattern pattern, int n, String string, Integer n2) {
        return StreamerTextSanitizer.a(list, pattern, n, string, n2, true, false, true);
    }

    private static ScoreboardContext e(List<TextContext> list, Pattern pattern, int n, String string, Integer n2) {
        return StreamerTextSanitizer.a(list, pattern, n, string, n2, false, true);
    }

    private static ScoreboardContext a(List<TextContext> list, Pattern pattern, int n, String string, Integer n2, boolean bl, boolean bl2) {
        return StreamerTextSanitizer.a(list, pattern, n, string, n2, bl, bl2, false);
    }

    private static ScoreboardContext a(List<TextContext> list, Pattern pattern, int n, String string, Integer n2, boolean bl, boolean bl2, boolean bl3) {
        if (list.isEmpty()) {
            return new ScoreboardContext(list, false);
        }
        String string2 = StreamerTextSanitizer.n(list);
        Matcher matcher = pattern.matcher(string2);
        if (!matcher.find()) {
            return new ScoreboardContext(list, false);
        }
        List<class_2583> list2 = StreamerTextSanitizer.p(list);
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        int n3 = 0;
        boolean bl4 = false;
        do {
            int n4;
            int n5 = n == 0 ? matcher.start() : matcher.start(n);
            int n6 = n4 = n == 0 ? matcher.end() : matcher.end(n);
            if (n5 < 0 || n4 <= n5) continue;
            int n7 = bl ? StreamerTextSanitizer.l(string2, n5) : n5;
            int n8 = bl2 ? StreamerTextSanitizer.m(string2, n4) : n4;
            StreamerTextSanitizer.a(arrayList, string2, list2, n3, n7);
            class_2583 class_25832 = StreamerTextSanitizer.d(list2, n5);
            class_2583 class_25833 = n2 == null ? class_25832 : class_25832.method_27703(class_5251.method_27717((int)n2));
            Object object = StreamerTextSanitizer.a(matcher, string);
            if (n7 < n5) {
                object = string2.substring(n7, n5) + (String)object;
            }
            if (n8 > n4) {
                object = (String)object + string2.substring(n4, n8);
            }
            if (bl3) {
                StreamerTextSanitizer.c(arrayList, class_25833, (String)object);
            } else {
                StreamerTextSanitizer.b(arrayList, class_25833, (String)object);
            }
            n3 = n8;
            bl4 = true;
        } while (matcher.find());
        StreamerTextSanitizer.a(arrayList, string2, list2, n3, string2.length());
        return new ScoreboardContext(arrayList, bl4);
    }

    private static int l(String string, int n) {
        if (string == null || n <= 0 || n > string.length()) {
            return n;
        }
        char c = string.charAt(n - 1);
        return Character.isWhitespace(c) ? n - 1 : n;
    }

    private static int m(String string, int n) {
        int n2;
        if (string == null || n < 0 || n >= string.length()) {
            return n;
        }
        for (n2 = n; n2 < string.length() && Character.isWhitespace(string.charAt(n2)); ++n2) {
        }
        return n2;
    }

    private static boolean sS() {
        return TextSanitizationMode.sX() == TextSanitizationMode.Strategy.amp;
    }

    private static class_2583 a(class_2583 class_25832, Integer n, boolean bl) {
        class_2583 class_25833 = bl ? class_2583.field_24360 : (class_25832 == null ? class_2583.field_24360 : class_25832);
        return n == null ? class_25833 : class_25833.method_27703(class_5251.method_27717((int)n));
    }

    private static int n(String string, int n) {
        int n2;
        int n3;
        if (string == null || string.isEmpty() || n <= 0 || n > string.length()) {
            return -1;
        }
        for (n3 = n - 1; n3 >= 0 && Character.isWhitespace(string.charAt(n3)); --n3) {
        }
        if (n3 < 0) {
            return -1;
        }
        int n4 = Math.max(0, n - 48);
        if (string.charAt(n3) == ']' && (n2 = string.lastIndexOf(91, n3)) >= n4) {
            return n2;
        }
        while (n3 >= n4 && !StreamerTextSanitizer.e(string.charAt(n3))) {
            --n3;
        }
        int n5 = n2 = n3 >= n4 && string.charAt(n3) == '[' ? n3 : n3 + 1;
        while (n2 < n && Character.isWhitespace(string.charAt(n2))) {
            ++n2;
        }
        if (n2 >= n) {
            return -1;
        }
        String string2 = string.substring(n2, n).trim();
        return string2.isEmpty() ? -1 : n2;
    }

    private static boolean e(char c) {
        return Character.isWhitespace(c) || c == '<' || c == '>' || c == '[' || c == ']' || c == '(' || c == ')' || c == '{' || c == '}' || c == ':' || c == ';' || c == '|' || c == ',' || c == '\u00ab' || c == '\u00bb';
    }

    private static boolean eb(String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.trim();
        if (string2.isEmpty()) {
            return false;
        }
        String[] stringArray = string2.split("\\s+");
        if (stringArray.length == 0 || stringArray.length > 6) {
            return false;
        }
        boolean bl = false;
        for (String string3 : stringArray) {
            if (string3 == null || string3.isBlank()) {
                return false;
            }
            if (!StreamerTextSanitizer.ec(string3)) {
                return false;
            }
            String string4 = StreamerTextSanitizer.eg(string3);
            if (StreamerTextSanitizer.ed(string4)) {
                return false;
            }
            if (!StreamerTextSanitizer.ef(string3) && !ServerTextPatternMatcher.alW.matcher(string4).matches() && !StreamerTextSanitizer.ap(string3, string4) && !StreamerTextSanitizer.ee(string4)) continue;
            bl = true;
        }
        return bl;
    }

    private static boolean ec(String string) {
        String string2 = StreamerTextSanitizer.eg(string);
        if (string2.isEmpty()) {
            return false;
        }
        if (ServerTextPatternMatcher.alW.matcher(string2).matches()) {
            return true;
        }
        if (StreamerTextSanitizer.ed(string2)) {
            return false;
        }
        if (StreamerTextSanitizer.ef(string)) {
            return true;
        }
        if (StreamerTextSanitizer.ap(string, string2)) {
            return true;
        }
        return StreamerTextSanitizer.ee(string2);
    }

    private static boolean ap(String string, String string2) {
        if (string == null || string2 == null || string2.isBlank()) {
            return false;
        }
        return string.indexOf(91) >= 0 && string.indexOf(93) >= 0 && !StreamerTextSanitizer.ed(string2);
    }

    private static boolean ed(String string) {
        return string != null && ServerTextPatternMatcher.alT.matcher(string).matches();
    }

    private static boolean ee(String string) {
        int n;
        boolean bl = false;
        boolean bl2 = false;
        for (int i = 0; i < string.length(); i += Character.charCount(n)) {
            n = string.codePointAt(i);
            if (!Character.isLetter(n)) continue;
            bl = true;
            if (!Character.isUpperCase(n)) {
                return false;
            }
            bl2 = true;
        }
        return bl && bl2;
    }

    private static boolean ef(String string) {
        int n;
        for (int i = 0; i < string.length(); i += Character.charCount(n)) {
            n = string.codePointAt(i);
            int n2 = Character.getType(n);
            if (n2 != 28 && n2 != 18) continue;
            return true;
        }
        return false;
    }

    private static String eg(String string) {
        int n;
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); i += Character.charCount(n)) {
            n = string.codePointAt(i);
            if (!Character.isLetterOrDigit(n) && n != 95 && n != 43 && n != 45) continue;
            stringBuilder.appendCodePoint(n);
        }
        return stringBuilder.toString();
    }

    private static String sT() {
        return "(?:\\[[^\\]\\r\\n]{1,24}]|(?:[\\p{Co}\\p{So}\\u2605\\u2726\\u2727\\u269D\\u269C\\u265B]{1,8})[\\p{L}\\p{N}_+\\-]{1,24}|(?:owner|admin|moder|moderator|helper|media|streamer|legend|premium|prem|vip|elite|hero|king|lord|titan|yt|youtube|\u044e\u0442\u0443\u0431\u0435\u0440|\u0441\u0442\u0440\u0438\u043c\u0435\u0440|\u043c\u0435\u0434\u0438\u0430|\u0432\u0438\u043f|\u043f\u0440\u0435\u043c|\u044d\u043b\u0438\u0442\u0430|\u043b\u0435\u0433\u0435\u043d\u0434\u0430|\u0430\u0434\u043c\u0438\u043d|\u0445\u0435\u043b\u043f\u0435\u0440|\u043c\u043e\u0434\u0435\u0440)[\\p{L}\\p{N}_+\\-]*)";
    }

    private static List<class_2583> p(List<TextContext> list) {
        ArrayList<class_2583> arrayList = new ArrayList<class_2583>();
        for (TextContext textContext : list) {
            class_2583 class_25832 = textContext.alw == null ? class_2583.field_24360 : textContext.alw;
            for (int i = 0; i < textContext.text.length(); ++i) {
                arrayList.add(class_25832);
            }
        }
        return arrayList;
    }

    private static void a(List<TextContext> list, String string, List<class_2583> list2, int n, int n2) {
        if (n >= n2) {
            return;
        }
        int n3 = n;
        while (n3 < n2) {
            int n4;
            class_2583 class_25832 = StreamerTextSanitizer.d(list2, n3);
            for (n4 = n3 + 1; n4 < n2 && StreamerTextSanitizer.d(list2, n4).equals((Object)class_25832); ++n4) {
            }
            StreamerTextSanitizer.b(list, class_25832, string.substring(n3, n4));
            n3 = n4;
        }
    }

    private static class_2583 d(List<class_2583> list, int n) {
        if (list.isEmpty()) {
            return class_2583.field_24360;
        }
        if (n < 0) {
            return class_2583.field_24360;
        }
        if (n >= list.size()) {
            return list.getLast();
        }
        return list.get(n) == null ? class_2583.field_24360 : list.get(n);
    }

    private static void b(List<TextContext> list, class_2583 class_25832, String string) {
        class_2583 class_25833;
        if (string == null || string.isEmpty()) {
            return;
        }
        class_2583 class_25834 = class_25833 = class_25832 == null ? class_2583.field_24360 : class_25832;
        if (!list.isEmpty()) {
            TextContext textContext = list.getLast();
            if (textContext.alw.equals((Object)class_25833)) {
                textContext.text = textContext.text + string;
                return;
            }
        }
        list.add(new TextContext(class_25833, string));
    }

    private static void c(List<TextContext> list, class_2583 class_25832, String string) {
        class_2583 class_25833;
        if (string == null || string.isEmpty()) {
            return;
        }
        if (StreamerTextSanitizer.du(string).isBlank()) {
            StreamerTextSanitizer.b(list, class_25832, string);
            return;
        }
        class_2583 class_25834 = class_25833 = class_25832 == null ? class_2583.field_24360 : class_25832;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            class_124 class_1242;
            char c = string.charAt(i);
            if ((c == '&' || c == '\u00a7') && i + 1 < string.length() && (class_1242 = class_124.method_544((char)Character.toLowerCase(string.charAt(i + 1)))) != null) {
                if (!stringBuilder.isEmpty()) {
                    StreamerTextSanitizer.b(list, class_25834, stringBuilder.toString());
                    stringBuilder.setLength(0);
                }
                class_25834 = StreamerTextSanitizer.a(class_25834, class_25833, class_1242);
                ++i;
                continue;
            }
            stringBuilder.append(c);
        }
        if (!stringBuilder.isEmpty()) {
            StreamerTextSanitizer.b(list, class_25834, stringBuilder.toString());
        }
    }

    private static class_2583 a(class_2583 class_25832, class_2583 class_25833, class_124 class_1242) {
        class_2583 class_25834;
        class_2583 class_25835 = class_25833 == null ? class_2583.field_24360 : class_25833;
        class_2583 class_25836 = class_25834 = class_25832 == null ? class_25835 : class_25832;
        if (class_1242 == class_124.field_1070) {
            return class_25835;
        }
        if (class_1242.method_543()) {
            class_5251 class_52512 = class_5251.method_27718((class_124)class_1242);
            return class_52512 == null ? class_25834 : class_25834.method_27703(class_52512);
        }
        return switch (class_1242) {
            case class_124.field_1051 -> class_25834.method_36141(Boolean.valueOf(true));
            case class_124.field_1067 -> class_25834.method_10982(Boolean.valueOf(true));
            case class_124.field_1055 -> class_25834.method_36140(Boolean.valueOf(true));
            case class_124.field_1073 -> class_25834.method_30938(Boolean.valueOf(true));
            case class_124.field_1056 -> class_25834.method_10978(Boolean.valueOf(true));
            default -> class_25834;
        };
    }

    private static List<TextContext> q(List<TextContext> list) {
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>(list.size());
        for (TextContext textContext : list) {
            arrayList.add(new TextContext(textContext.alw, textContext.text));
        }
        return arrayList;
    }

    private static class_2561 r(List<TextContext> list) {
        class_5250 class_52502 = class_2561.method_43470((String)"");
        for (TextContext textContext : list) {
            StreamerTextSanitizer.a(class_52502, textContext.alw, textContext.text);
        }
        return class_52502;
    }

    private static void a(class_5250 class_52502, class_2583 class_25832, String string) {
        if (string == null || string.isEmpty()) {
            return;
        }
        class_52502.method_10852((class_2561)class_2561.method_43470((String)string).method_10862(class_25832 == null ? class_2583.field_24360 : class_25832));
    }

    private static void b(class_5250 class_52502, class_2583 class_25832, String string) {
        if (class_52502 == null || string == null || string.isEmpty()) {
            return;
        }
        ArrayList<TextContext> arrayList = new ArrayList<TextContext>();
        StreamerTextSanitizer.c(arrayList, class_25832, string);
        for (TextContext textContext : arrayList) {
            StreamerTextSanitizer.a(class_52502, textContext.alw, textContext.text);
        }
    }

    static {
        ali = Pattern.compile("\\s+");
    }

    static final class ChatContext {
        final String alo;
        final List<Integer> alp;

        ChatContext(String string, List<Integer> list) {
            this.alo = string;
            this.alp = list;
        }
    }

    static final class TabListContext {
        final List<TextContext> alu;
        final boolean alv;

        TabListContext(List<TextContext> list, boolean bl) {
            this.alu = list;
            this.alv = bl;
        }
    }

    static final class NameContext {
        final List<TextContext> alq;
        final boolean alr;

        NameContext(List<TextContext> list, boolean bl) {
            this.alq = list;
            this.alr = bl;
        }
    }

    static final class ScoreboardContext {
        final List<TextContext> als;
        final boolean alt;

        ScoreboardContext(List<TextContext> list, boolean bl) {
            this.als = list;
            this.alt = bl;
        }
    }

    static final class TooltipContext {
        final class_2561 alx;
        private final String aly;
        private final String alz;
        private final String alA;

        TooltipContext(class_2561 class_25612, String string, String string2, String string3) {
            this.alx = class_25612;
            this.aly = string;
            this.alz = string2;
            this.alA = string3;
        }

        boolean i(String string, String string2, String string3) {
            return TooltipContext.ar(this.aly, string) && TooltipContext.ar(this.alz, string2) && TooltipContext.ar(this.alA, string3);
        }

        static boolean ar(String string, String string2) {
            if (string == null) {
                return string2 == null;
            }
            return string.equals(string2);
        }
    }

    static final class SanitizationContext {
        private final String all;
        private final int alm;
        final Map<String, String> aln;

        SanitizationContext(String string, int n, Map<String, String> map) {
            this.all = string;
            this.alm = n;
            this.aln = map;
        }

        boolean matches(String string, int n) {
            return this.alm == n && TooltipContext.ar(this.all, string);
        }
    }

    static final class TextContext {
        final class_2583 alw;
        String text;

        TextContext(class_2583 class_25832, String string) {
            this.alw = class_25832;
            this.text = string;
        }
    }
}
