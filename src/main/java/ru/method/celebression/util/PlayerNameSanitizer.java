package ru.method.celebression.util;

import com.mojang.datafixers.util.Either;
import java.lang.invoke.CallSite;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_2561;
import net.minecraft.class_2578;
import net.minecraft.class_2583;
import net.minecraft.class_2588;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_5250;
import net.minecraft.class_5251;
import net.minecraft.class_5481;
import net.minecraft.class_7417;
import net.minecraft.class_8828;
import ru.method.celebression.Celebression;
import ru.method.celebression.util.StreamerTextSanitizer;
import ru.method.celebression.manager.UserSyncManager;
import ru.method.celebression.utils.debug.TabProfiler;

public final class PlayerNameSanitizer {
    private static final class_5251 alB;
    private static final String alC;
    private static final String alD;
    private static final Pattern alE;
    private static final Pattern alF;
    private static final Map<String, Pattern> alG;
    private static final class_2960 alH;
    private static final Method alI;
    private static final Method alJ;
    private static final Method alK;

    private PlayerNameSanitizer() {
    }

    public static String sU() {
        return "\u2726 ".trim() + " ";
    }

    public static class_2561 a(class_2561 class_25612, UUID uUID, String string) {
        return PlayerNameSanitizer.a(StreamerTextSanitizer.l(class_25612), uUID, string, false);
    }

    public static class_2561 b(class_2561 class_25612, UUID uUID, String string) {
        return PlayerNameSanitizer.a(class_25612, uUID, string, true);
    }

        private static class_2561 a(class_2561 class_25612, UUID uUID, String string, boolean bl) {
        long l = TabProfiler.startSection();
        try {
            if (class_25612 == null) {
                class_2561 class_25613 = null;
                return class_25613;
            }
            UserSyncManager userSyncManager = PlayerNameSanitizer.sV();
            boolean bl2 = PlayerNameSanitizer.i(uUID, string);
            if (!(userSyncManager != null && userSyncManager.e(uUID, string) || bl2)) {
                class_2561 class_25614 = class_25612;
                return class_25614;
            }
            if (PlayerNameSanitizer.aa(class_25612)) {
                class_2561 class_25615 = bl ? PlayerNameSanitizer.ac(class_25612) : class_25612;
                return class_25615;
            }
            String string2 = StreamerTextSanitizer.dm(string);
            if (StreamerTextSanitizer.dt(string) || StreamerTextSanitizer.dt(string2)) {
                class_2561 class_25616 = class_25612;
                return class_25616;
            }
            class_2561 class_25617 = PlayerNameSanitizer.b(class_25612, string2);
            if (class_25617 == null && string2 != null && string != null && !string2.equalsIgnoreCase(string)) {
                class_25617 = PlayerNameSanitizer.b(class_25612, string);
            }
            if (class_25617 != null) {
                class_2561 class_25618 = bl ? PlayerNameSanitizer.ac(class_25617) : class_25617;
                return class_25618;
            }
            class_2561 class_25619 = PlayerNameSanitizer.a(class_25612, string, string2);
            if (class_25619 != null) {
                class_2561 class_256110 = bl ? PlayerNameSanitizer.ac(class_25619) : class_25619;
                return class_256110;
            }
            if (bl2 || userSyncManager != null && userSyncManager.e(uUID, string)) {
                class_2561 class_256111 = PlayerNameSanitizer.z(class_25612);
                class_2561 class_256112 = bl ? PlayerNameSanitizer.ac(class_256111) : class_256111;
                return class_256112;
            }
            class_2561 class_256113 = class_25612;
            return class_256113;
        }
        catch (Throwable throwable) {
            class_2561 class_256114 = class_25612;
            return class_256114;
        }
        finally {
            TabProfiler.endSection("tabBadge", l);
        }
    }

    public static class_2561 r(class_2561 class_25612) {
        return PlayerNameSanitizer.s(class_25612);
    }

    public static class_2561 s(class_2561 class_25612) {
        class_2561 class_25613 = StreamerTextSanitizer.l(class_25612);
        return PlayerNameSanitizer.x(class_25613);
    }

    public static class_2561 t(class_2561 class_25612) {
        return PlayerNameSanitizer.x(class_25612);
    }

    public static class_2561 u(class_2561 class_25612) {
        if (class_25612 == null || !StreamerTextSanitizer.p(class_25612)) {
            return class_25612;
        }
        UserSyncManager userSyncManager = PlayerNameSanitizer.sV();
        List<TextSegment> list = PlayerNameSanitizer.c(class_25612.method_30937());
        if (list.isEmpty()) {
            return class_25612;
        }
        String string = PlayerNameSanitizer.n(list);
        int n = PlayerNameSanitizer.dJ(string);
        if (n <= 0) {
            return class_25612;
        }
        String string2 = string.substring(0, n);
        boolean bl = PlayerNameSanitizer.ej(string2);
        if (bl) {
            return PlayerNameSanitizer.w(class_25612);
        }
        List<class_2583> list2 = PlayerNameSanitizer.p(list);
        Matcher matcher = alE.matcher(string2);
        int n2 = 0;
        boolean bl2 = false;
        class_5250 class_52502 = class_2561.method_43470((String)"");
        while (matcher.find()) {
            boolean bl3;
            String string3 = string2.substring(matcher.start(), matcher.end());
            if (StreamerTextSanitizer.dt(string3)) continue;
            boolean bl4 = PlayerNameSanitizer.ek(string3);
            boolean bl5 = bl3 = userSyncManager != null && userSyncManager.B(string3);
            if (!bl4 && !bl3 && !bl) continue;
            int n3 = Math.max(matcher.start(), PlayerNameSanitizer.el(string2));
            bl2 = true;
            PlayerNameSanitizer.b(class_52502, string, list2, n2, n3);
            class_52502.method_10852((class_2561)class_2561.method_43470((String)PlayerNameSanitizer.sU()).method_10862(PlayerNameSanitizer.d(class_2583.field_24360)));
            PlayerNameSanitizer.b(class_52502, string, list2, n3, matcher.end());
            n2 = matcher.end();
            break;
        }
        if (!bl2) {
            return class_25612;
        }
        PlayerNameSanitizer.b(class_52502, string, list2, n2, string.length());
        return PlayerNameSanitizer.w((class_2561)class_52502);
    }

    public static class_2561 v(class_2561 class_25612) {
        return PlayerNameSanitizer.w(class_25612);
    }

    public static class_2561 w(class_2561 class_25612) {
        if (class_25612 == null || !StreamerTextSanitizer.p(class_25612)) {
            return class_25612;
        }
        List<TextSegment> list = PlayerNameSanitizer.c(class_25612.method_30937());
        if (list.isEmpty()) {
            return class_25612;
        }
        String string = PlayerNameSanitizer.n(list);
        int n = PlayerNameSanitizer.dJ(string);
        if (n <= 0) {
            return class_25612;
        }
        String string2 = string.substring(0, n);
        if (!PlayerNameSanitizer.ej(string2)) {
            return class_25612;
        }
        int n2 = PlayerNameSanitizer.eo(string2);
        if (n2 < 0) {
            return class_25612;
        }
        List<class_2583> list2 = PlayerNameSanitizer.p(list);
        class_5250 class_52502 = class_2561.method_43470((String)"");
        int n3 = Math.max(n2, PlayerNameSanitizer.el(string2));
        if (PlayerNameSanitizer.t(string2, n3)) {
            return class_25612;
        }
        PlayerNameSanitizer.b(class_52502, string, list2, 0, n3);
        class_52502.method_10852((class_2561)class_2561.method_43470((String)PlayerNameSanitizer.sU()).method_10862(PlayerNameSanitizer.d(class_2583.field_24360)));
        PlayerNameSanitizer.b(class_52502, string, list2, n3, n);
        PlayerNameSanitizer.a(class_52502, string, list2, n, string.length());
        return class_52502;
    }

    public static class_5481 d(class_5481 class_54812) {
        if (class_54812 == null) {
            return null;
        }
        if (!PlayerNameSanitizer.e(class_54812)) {
            return class_54812;
        }
        List<TextSegment> list = PlayerNameSanitizer.c(class_54812);
        if (list.isEmpty()) {
            return class_54812;
        }
        class_2561 class_25612 = PlayerNameSanitizer.w(PlayerNameSanitizer.r(list));
        List<TextSegment> list2 = PlayerNameSanitizer.c(class_25612.method_30937());
        return list2.isEmpty() ? class_54812 : PlayerNameSanitizer.o(list2);
    }

    private static UserSyncManager sV() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return null;
        }
        return celebression.getUserSyncManager();
    }

    private static class_2561 x(class_2561 class_25612) {
        UserSyncManager userSyncManager = PlayerNameSanitizer.sV();
        if (userSyncManager == null) {
            return class_25612;
        }
        boolean[] blArray = new boolean[]{false};
        class_2561 class_25613 = PlayerNameSanitizer.a(class_25612, userSyncManager::B, false, blArray);
        return blArray[0] ? class_25613 : class_25612;
    }

    public static class_2561 y(class_2561 class_25612) {
        class_2561 class_25613 = StreamerTextSanitizer.l(class_25612);
        UserSyncManager userSyncManager = PlayerNameSanitizer.sV();
        Predicate<String> predicate = string -> PlayerNameSanitizer.ek(string) || userSyncManager != null && userSyncManager.B((String)string);
        boolean[] blArray = new boolean[]{false};
        class_2561 class_25614 = PlayerNameSanitizer.a(class_25613, predicate, true, blArray);
        return blArray[0] ? class_25614 : class_25613;
    }

    public static String d(class_2561 class_25612, class_2561 class_25613) {
        try {
            UserSyncManager userSyncManager = PlayerNameSanitizer.sV();
            String string = class_25612 == null ? "" : class_25612.getString();
            Matcher matcher = alE.matcher(string);
            ArrayList<String> arrayList = new ArrayList<>();
            int n = 0;
            while (matcher.find() && arrayList.size() < 8) {
                boolean bl;
                String string2 = string.substring(matcher.start(), matcher.end());
                boolean bl2 = bl = PlayerNameSanitizer.ek(string2) || userSyncManager != null && userSyncManager.B(string2);
                if (bl) {
                    ++n;
                }
                arrayList.add(string2 + (bl ? "*" : ""));
            }
            boolean bl = class_25613 != null && PlayerNameSanitizer.ej(class_25613.getString());
            return "listSize=" + (userSyncManager == null ? -1 : userSyncManager.eH()) + " len=" + string.length() + " tokens=" + String.valueOf(arrayList) + " matched=" + n + " badgeInResult=" + bl;
        }
        catch (Throwable throwable) {
            return "debug failed: " + String.valueOf(throwable);
        }
    }

    private static class_2561 a(class_2561 class_25612, Predicate<String> predicate, boolean bl, boolean[] blArray) {
        if (class_25612 == null) {
            return null;
        }
        class_2561 class_25613 = PlayerNameSanitizer.b(class_25612, predicate, bl, blArray);
        if (class_25613 == null) {
            class_25613 = class_25612;
        }
        if (class_25612.method_10855().isEmpty()) {
            return class_25613;
        }
        class_5250 class_52502 = class_25613.method_27661();
        class_52502.method_10855().clear();
        for (class_2561 class_25614 : class_25612.method_10855()) {
            class_52502.method_10852(PlayerNameSanitizer.a(class_25614, predicate, bl, blArray));
        }
        return class_52502;
    }

    private static class_2561 b(class_2561 class_25612, Predicate<String> predicate, boolean bl, boolean[] blArray) {
        class_7417 content = class_25612.method_10851();
        if (content instanceof class_8828 literalContent) {
            String literal = literalContent.comp_737();
            class_2561 sanitizedLiteral = PlayerNameSanitizer.a(literal, class_25612.method_10866(), predicate, bl);
            if (sanitizedLiteral != null) {
                blArray[0] = true;
                return sanitizedLiteral;
            }
            return class_2561.method_43470(literal).method_10862(class_25612.method_10866());
        }
        if (content instanceof class_2578 selectorContent) {
            String pattern = selectorContent.comp_3079();
            Either<?, ?> separator = selectorContent.comp_3078();
            Object rightValue = separator.right().orElse(null);
            String playerName = rightValue instanceof String value ? value : null;
            if (playerName == null) {
                Object leftValue = separator.left().orElse(null);
                playerName = StreamerTextSanitizer.dq(leftValue == null ? null : leftValue.toString());
            }
            if (playerName == null) {
                return class_25612;
            }
            class_2561 selectorText = class_2561.method_43466(playerName, pattern).method_10862(class_25612.method_10866());
            if (predicate.test(playerName)) {
                blArray[0] = true;
                return PlayerNameSanitizer.z(selectorText);
            }
            return selectorText;
        }
        if (content instanceof class_2588 translatableContent) {
            Object[] sourceArguments = PlayerNameSanitizer.a(translatableContent);
            Object[] sanitizedArguments = sourceArguments == null ? new Object[0] : new Object[sourceArguments.length];
            if (sourceArguments != null) {
                for (int i = 0; i < sourceArguments.length; ++i) {
                    Object argument = sourceArguments[i];
                    if (argument instanceof class_2561 textArgument) {
                        sanitizedArguments[i] = PlayerNameSanitizer.a(textArgument, predicate, bl, blArray);
                    } else if (argument instanceof String stringArgument) {
                        class_2561 sanitizedArgument = PlayerNameSanitizer.a(stringArgument, class_2583.field_24360, predicate, bl);
                        if (sanitizedArgument != null) {
                            blArray[0] = true;
                        }
                        sanitizedArguments[i] = sanitizedArgument != null ? sanitizedArgument : stringArgument;
                    } else {
                        sanitizedArguments[i] = argument;
                    }
                }
            }
            String translationKey = PlayerNameSanitizer.b(translatableContent);
            if (translationKey == null || translationKey.isBlank()) {
                return class_25612;
            }
            String fallback = PlayerNameSanitizer.c(translatableContent);
            class_5250 translated = fallback == null
                ? class_2561.method_43469(translationKey, sanitizedArguments)
                : class_2561.method_48322(translationKey, fallback, sanitizedArguments);
            return translated.method_10862(class_25612.method_10866());
        }
        return class_25612;
    }

    private static class_2561 z(class_2561 class_25612) {
        if (class_25612 == null) {
            return null;
        }
        return class_2561.method_43470((String)"").method_10852((class_2561)class_2561.method_43470((String)PlayerNameSanitizer.sU()).method_10862(PlayerNameSanitizer.d(class_2583.field_24360))).method_10852((class_2561)class_25612.method_27661());
    }

    private static boolean aa(class_2561 class_25612) {
        if (class_25612 == null) {
            return false;
        }
        String string = class_25612.getString();
        return PlayerNameSanitizer.ej(string);
    }

    private static boolean ej(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        return PlayerNameSanitizer.i(string, 0, string.length()) >= 0;
    }

    private static boolean e(class_5481 class_54812) {
        boolean[] blArray = new boolean[]{false};
        class_54812.accept((n, class_25832, n2) -> {
            if (n2 == 10022) {
                blArray[0] = true;
                return false;
            }
            return true;
        });
        return blArray[0];
    }

    private static class_2561 ab(class_2561 class_25612) {
        if (class_25612 == null) {
            return null;
        }
        List<TextSegment> list = PlayerNameSanitizer.c(class_25612.method_30937());
        if (list.isEmpty()) {
            return class_25612;
        }
        String string = PlayerNameSanitizer.n(list);
        if (!PlayerNameSanitizer.ej(string)) {
            return class_25612;
        }
        List<class_2583> list2 = PlayerNameSanitizer.p(list);
        class_5250 class_52502 = class_2561.method_43470((String)"");
        int n = PlayerNameSanitizer.ep(string);
        PlayerNameSanitizer.a(class_52502, string, list2, 0, n);
        class_52502.method_10852((class_2561)class_2561.method_43470((String)PlayerNameSanitizer.sU()).method_10862(PlayerNameSanitizer.d(class_2583.field_24360)));
        PlayerNameSanitizer.b(class_52502, string, list2, n, string.length());
        return class_52502;
    }

    public static class_2561 ac(class_2561 class_25612) {
        if (class_25612 == null) {
            return null;
        }
        List<TextSegment> list = PlayerNameSanitizer.c(class_25612.method_30937());
        if (list.isEmpty()) {
            return class_25612;
        }
        String string = PlayerNameSanitizer.n(list);
        if (!PlayerNameSanitizer.ej(string)) {
            return class_25612;
        }
        int n = PlayerNameSanitizer.en(string);
        if (n < 0) {
            return class_25612;
        }
        int n2 = PlayerNameSanitizer.p(string, n);
        if (n2 < 0 || n2 > n) {
            return class_25612;
        }
        if (PlayerNameSanitizer.t(string, n2)) {
            return class_25612;
        }
        List<class_2583> list2 = PlayerNameSanitizer.p(list);
        class_5250 class_52502 = class_2561.method_43470((String)"");
        PlayerNameSanitizer.b(class_52502, string, list2, 0, n2);
        class_52502.method_10852((class_2561)class_2561.method_43470((String)PlayerNameSanitizer.sU()).method_10862(PlayerNameSanitizer.d(class_2583.field_24360)));
        PlayerNameSanitizer.b(class_52502, string, list2, n2, string.length());
        return class_52502;
    }

    private static class_2561 ad(class_2561 class_25612) {
        if (class_25612 == null) {
            return null;
        }
        List<TextSegment> list = PlayerNameSanitizer.c(class_25612.method_30937());
        if (list.isEmpty()) {
            return class_25612;
        }
        String string = PlayerNameSanitizer.n(list);
        if (!PlayerNameSanitizer.ej(string)) {
            return class_25612;
        }
        List<class_2583> list2 = PlayerNameSanitizer.p(list);
        class_5250 class_52502 = class_2561.method_43470((String)"");
        PlayerNameSanitizer.b(class_52502, string, list2, 0, string.length());
        return class_52502;
    }

    public static class_5481 f(class_5481 class_54812) {
        if (class_54812 == null) {
            return null;
        }
        if (!PlayerNameSanitizer.e(class_54812)) {
            return class_54812;
        }
        List<TextSegment> list = PlayerNameSanitizer.c(class_54812);
        if (list.isEmpty()) {
            return class_54812;
        }
        class_2561 class_25612 = PlayerNameSanitizer.ac(PlayerNameSanitizer.r(list));
        List<TextSegment> list2 = PlayerNameSanitizer.c(class_25612.method_30937());
        return list2.isEmpty() ? class_54812 : PlayerNameSanitizer.o(list2);
    }

    private static boolean i(UUID uUID, String string) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1724.method_7334() == null) {
            return false;
        }
        UUID uUID2 = class_3102.field_1724.method_7334().getId();
        if (uUID != null && uUID2 != null && uUID.equals(uUID2)) {
            return true;
        }
        String string2 = class_3102.field_1724.method_7334().getName();
        return string != null && string2 != null && string.equalsIgnoreCase(string2);
    }

    private static boolean ek(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        if (PlayerNameSanitizer.i(null, string)) {
            return true;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1724.method_7334() == null) {
            return false;
        }
        String string2 = class_3102.field_1724.method_7334().getName();
        String string3 = StreamerTextSanitizer.dm(string2);
        return !string3.equalsIgnoreCase(string2) && string.equalsIgnoreCase(string3);
    }

    private static class_2561 ae(class_2561 class_25612) {
        if (class_25612 == null) {
            return null;
        }
        class_5250 class_52502 = class_25612.method_27661();
        class_52502.method_10862(PlayerNameSanitizer.c(class_52502.method_10866()));
        if (!class_52502.method_10855().isEmpty()) {
            for (int i = 0; i < class_52502.method_10855().size(); ++i) {
                class_52502.method_10855().set(i, PlayerNameSanitizer.ae((class_2561)class_52502.method_10855().get(i)));
            }
        }
        return class_52502;
    }

    private static class_2561 b(class_2561 class_25612, String string) {
        if (class_25612 == null || string == null || string.isBlank()) {
            return null;
        }
        boolean[] blArray = new boolean[]{false};
        class_2561 class_25613 = PlayerNameSanitizer.a(class_25612, string, blArray);
        return blArray[0] ? class_25613 : null;
    }

    private static class_2561 a(class_2561 class_25612, String string, boolean[] blArray) {
        if (class_25612 == null) {
            return null;
        }
        class_2561 class_25613 = PlayerNameSanitizer.b(class_25612, string, blArray);
        if (class_25613 == null) {
            class_25613 = class_25612;
        }
        if (class_25612.method_10855().isEmpty()) {
            return class_25613;
        }
        class_5250 class_52502 = class_25613.method_27661();
        class_52502.method_10855().clear();
        for (class_2561 class_25614 : class_25612.method_10855()) {
            class_52502.method_10852(PlayerNameSanitizer.a(class_25614, string, blArray));
        }
        return class_52502;
    }

    private static class_2561 b(class_2561 class_25612, String string, boolean[] blArray) {
        class_8828 class_88282;
        class_2561 class_25613;
        Object object = class_25612.method_10851();
        if (object instanceof class_8828 && (class_25613 = PlayerNameSanitizer.a((String)(object = (class_88282 = (class_8828)object).comp_737()), class_25612.method_10866(), (String string2) -> string2.equalsIgnoreCase(string))) != null) {
            blArray[0] = true;
            return class_25613;
        }
        return class_25612;
    }

    private static class_2561 a(String string, class_2583 class_25832, Predicate<String> predicate) {
        return PlayerNameSanitizer.a(string, class_25832, predicate, false);
    }

    private static class_2561 a(String string, class_2583 class_25832, Predicate<String> predicate, boolean bl) {
        if (string == null || string.isEmpty() || predicate == null) {
            return null;
        }
        Matcher matcher = alE.matcher(string);
        int n = 0;
        boolean bl2 = false;
        class_2583 class_25833 = class_25832 == null ? class_2583.field_24360 : class_25832;
        class_5250 class_52502 = class_2561.method_43470((String)"").method_10862(class_25833);
        while (matcher.find()) {
            String string2 = string.substring(matcher.start(), matcher.end());
            if (StreamerTextSanitizer.dt(string2) || !predicate.test(string2)) continue;
            bl2 = true;
            int n2 = PlayerNameSanitizer.o(string, matcher.start());
            if (n2 < n || n2 > matcher.start()) {
                n2 = matcher.start();
            }
            if (n2 > n) {
                class_52502.method_10852((class_2561)class_2561.method_43470((String)string.substring(n, n2)).method_10862(class_25833));
            }
            class_52502.method_10852((class_2561)class_2561.method_43470((String)PlayerNameSanitizer.sU()).method_10862(PlayerNameSanitizer.d(class_25833)));
            if (matcher.start() > n2) {
                class_52502.method_10852((class_2561)class_2561.method_43470((String)string.substring(n2, matcher.start())).method_10862(class_25833));
            }
            class_52502.method_10852((class_2561)class_2561.method_43470((String)string2).method_10862(class_25833));
            n = matcher.end();
        }
        if (!bl2) {
            return null;
        }
        if (n < string.length()) {
            class_52502.method_10852((class_2561)class_2561.method_43470((String)string.substring(n)).method_10862(class_25833));
        }
        return class_52502;
    }

    private static class_2583 c(class_2583 class_25832) {
        return (class_25832 == null ? class_2583.field_24360 : class_25832).method_27703(alB);
    }

    private static class_2583 d(class_2583 class_25832) {
        return PlayerNameSanitizer.c(class_25832).method_10982(Boolean.valueOf(true)).method_27704(alH);
    }

    private static List<TextSegment> c(class_5481 class_54812) {
        ArrayList<TextSegment> arrayList = new ArrayList<TextSegment>();
        StringBuilder stringBuilder = new StringBuilder();
        class_2583[] class_2583Array = new class_2583[]{null};
        class_54812.accept((n, class_25832, n2) -> {
            class_2583 class_25833;
            class_2583 class_25834 = class_25833 = class_25832 == null ? class_2583.field_24360 : class_25832;
            if (class_2583Array[0] == null || !class_2583Array[0].equals((Object)class_25833)) {
                if (!stringBuilder.isEmpty()) {
                    arrayList.add(new TextSegment(class_2583Array[0], stringBuilder.toString()));
                    stringBuilder.setLength(0);
                }
                class_2583Array[0] = class_25833;
            }
            stringBuilder.appendCodePoint(n2);
            return true;
        });
        if (!stringBuilder.isEmpty()) {
            arrayList.add(new TextSegment(class_2583Array[0], stringBuilder.toString()));
        }
        return arrayList;
    }

    private static String n(List<TextSegment> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TextSegment textSegment : list) {
            stringBuilder.append(textSegment.alM);
        }
        return stringBuilder.toString();
    }

    private static List<class_2583> p(List<TextSegment> list) {
        ArrayList<class_2583> arrayList = new ArrayList<class_2583>();
        for (TextSegment textSegment : list) {
            class_2583 class_25832 = textSegment.alL == null ? class_2583.field_24360 : textSegment.alL;
            for (int i = 0; i < textSegment.alM.length(); ++i) {
                arrayList.add(class_25832);
            }
        }
        return arrayList;
    }

    private static class_2583 d(List<class_2583> list, int n) {
        if (list.isEmpty() || n < 0) {
            return class_2583.field_24360;
        }
        if (n >= list.size()) {
            return list.getLast();
        }
        return list.get(n) == null ? class_2583.field_24360 : list.get(n);
    }

    private static void a(class_5250 class_52502, String string, List<class_2583> list, int n, int n2) {
        if (class_52502 == null || string == null || n >= n2) {
            return;
        }
        int n3 = Math.max(0, n);
        int n4 = Math.min(n2, string.length());
        while (n3 < n4) {
            int n5;
            class_2583 class_25832 = PlayerNameSanitizer.d(list, n3);
            for (n5 = n3 + 1; n5 < n4 && PlayerNameSanitizer.d(list, n5).equals((Object)class_25832); ++n5) {
            }
            class_52502.method_10852((class_2561)class_2561.method_43470((String)string.substring(n3, n5)).method_10862(class_25832));
            n3 = n5;
        }
    }

    private static void b(class_5250 class_52502, String string, List<class_2583> list, int n, int n2) {
        if (class_52502 == null || string == null || n >= n2) {
            return;
        }
        int n3 = Math.max(0, n);
        int n4 = Math.min(n2, string.length());
        while (n3 < n4) {
            int n5 = PlayerNameSanitizer.i(string, n3, n4);
            if (n5 < 0 || n5 >= n4) {
                PlayerNameSanitizer.a(class_52502, string, list, n3, n4);
                return;
            }
            PlayerNameSanitizer.a(class_52502, string, list, n3, n5);
            for (n3 = n5 + PlayerNameSanitizer.s(string, n5); n3 < n4 && Character.isWhitespace(string.charAt(n3)); ++n3) {
            }
        }
    }

    private static int el(String string) {
        if (string == null || string.isEmpty()) {
            return 0;
        }
        Matcher matcher = alF.matcher(string);
        return matcher.find() && matcher.start() == 0 ? matcher.end() : 0;
    }

    private static int dJ(String string) {
        char[] cArray;
        if (string == null || string.isEmpty()) {
            return -1;
        }
        int n = -1;
        for (char c : cArray = new char[]{'>', '\u00bb', '\u21e8', '\u2192', '\u27a4', '\u279c'}) {
            int n2 = string.indexOf(c);
            if (n2 < 0 || n >= 0 && n2 >= n) continue;
            n = n2;
        }
        return n;
    }

    private static int o(String string, int n) {
        int n2;
        int n3;
        if (string == null || n <= 0 || n > string.length()) {
            return Math.max(0, n);
        }
        for (n3 = n; n3 > 0 && Character.isWhitespace(string.charAt(n3 - 1)); --n3) {
        }
        if (n3 <= 0) {
            return Math.max(0, n);
        }
        if (string.charAt(n3 - 1) == ']' && (n2 = string.lastIndexOf(91, n3 - 1)) >= 0) {
            return n2;
        }
        for (n2 = n3; n2 > 0 && !Character.isWhitespace(string.charAt(n2 - 1)); --n2) {
        }
        while (n2 < n3 && Character.isWhitespace(string.charAt(n2))) {
            ++n2;
        }
        String string2 = string.substring(n2, n3).trim();
        if (string2.isBlank() || string2.length() > 48) {
            return Math.max(0, n);
        }
        return n2;
    }

    private static int p(String string, int n) {
        int n2;
        if (string == null || n <= 0 || n > string.length()) {
            return Math.max(0, n);
        }
        int n3 = PlayerNameSanitizer.r(string, n);
        if (n3 >= 0) {
            return n3;
        }
        int n4 = StreamerTextSanitizer.f(string, n);
        if (n4 >= 0 && n4 < n) {
            return n4;
        }
        for (n2 = n; n2 > 0 && Character.isWhitespace(string.charAt(n2 - 1)); --n2) {
        }
        int n5 = PlayerNameSanitizer.j(string, 0, n2);
        if (n5 >= 0) {
            int n6 = n5 + PlayerNameSanitizer.s(string, n5);
            boolean bl = true;
            for (int i = n6; i < n2; ++i) {
                if (Character.isWhitespace(string.charAt(i))) continue;
                bl = false;
                break;
            }
            if (bl) {
                for (n2 = n5; n2 > 0 && Character.isWhitespace(string.charAt(n2 - 1)); --n2) {
                }
            }
        }
        return PlayerNameSanitizer.o(string, n2);
    }

    private static int q(String string, int n) {
        int n2;
        int n3;
        if (string == null || n <= 0 || n > string.length()) {
            return -1;
        }
        int n4 = PlayerNameSanitizer.r(string, n);
        if (n4 >= 0) {
            int n5 = PlayerNameSanitizer.j(string, n4, Math.min(n, string.length()));
            int n6 = n5 >= 0 ? n5 : n;
            String string2 = string.substring(n4, n6).trim();
            return PlayerNameSanitizer.em(string2) ? n4 : -1;
        }
        for (n3 = n; n3 > 0 && Character.isWhitespace(string.charAt(n3 - 1)); --n3) {
        }
        int n7 = PlayerNameSanitizer.j(string, 0, n3);
        if (n7 >= 0) {
            n2 = n7 + PlayerNameSanitizer.s(string, n7);
            boolean bl = true;
            for (int i = n2; i < n3; ++i) {
                if (Character.isWhitespace(string.charAt(i))) continue;
                bl = false;
                break;
            }
            if (bl) {
                for (n3 = n7; n3 > 0 && Character.isWhitespace(string.charAt(n3 - 1)); --n3) {
                }
            }
        }
        if ((n2 = PlayerNameSanitizer.o(string, n3)) < 0 || n2 >= n3) {
            return -1;
        }
        String string3 = string.substring(n2, n3).trim();
        if (!PlayerNameSanitizer.em(string3)) {
            return -1;
        }
        return n2;
    }

    private static boolean em(String string) {
        int n;
        if (string == null) {
            return false;
        }
        String string2 = string.trim();
        if (string2.isBlank() || string2.length() > 48) {
            return false;
        }
        boolean bl = false;
        for (int i = 0; i < string2.length(); i += Character.charCount(n)) {
            n = string2.codePointAt(i);
            if (!Character.isLetterOrDigit(n)) continue;
            bl = true;
            break;
        }
        return bl || string2.codePointCount(0, string2.length()) >= 1;
    }

    private static int r(String string, int n) {
        int n2;
        int n3;
        if (string == null || string.isEmpty()) {
            return -1;
        }
        int n4 = PlayerNameSanitizer.j(string, 0, Math.min(n, string.length()));
        if (n4 < 0) {
            return -1;
        }
        for (n3 = n4; n3 > 0 && Character.isWhitespace(string.charAt(n3 - 1)); --n3) {
        }
        if (n3 <= 0) {
            return -1;
        }
        if (string.charAt(n3 - 1) == ']' && (n2 = string.lastIndexOf(91, n3 - 1)) >= 0) {
            return n2;
        }
        for (n2 = n3; n2 > 0 && !Character.isWhitespace(string.charAt(n2 - 1)); --n2) {
        }
        return n2 < n3 ? n2 : -1;
    }

    private static String[] sW() {
        return new String[]{PlayerNameSanitizer.sU(), PlayerNameSanitizer.sU().trim(), "\u2726"};
    }

    private static int i(String string, int n, int n2) {
        if (string == null || string.isEmpty()) {
            return -1;
        }
        int n3 = Math.max(0, n);
        int n4 = Math.min(Math.max(n3, n2), string.length());
        int n5 = -1;
        for (String string2 : PlayerNameSanitizer.sW()) {
            int n6;
            if (string2 == null || string2.isEmpty() || (n6 = string.indexOf(string2, n3)) < n3 || n6 >= n4 || n5 >= 0 && n6 >= n5) continue;
            n5 = n6;
        }
        return n5;
    }

    private static int j(String string, int n, int n2) {
        if (string == null || string.isEmpty()) {
            return -1;
        }
        int n3 = Math.max(0, n);
        int n4 = Math.min(Math.max(n3, n2), string.length());
        int n5 = -1;
        for (String string2 : PlayerNameSanitizer.sW()) {
            int n6;
            if (string2 == null || string2.isEmpty() || (n6 = string.lastIndexOf(string2, Math.max(0, n4 - 1))) < n3 || n6 >= n4 || n6 <= n5) continue;
            n5 = n6;
        }
        return n5;
    }

    private static int s(String string, int n) {
        if (string == null || n < 0 || n >= string.length()) {
            return 0;
        }
        int n2 = 0;
        for (String string2 : PlayerNameSanitizer.sW()) {
            if (string2 == null || string2.isEmpty() || !string.startsWith(string2, n) || string2.length() <= n2) continue;
            n2 = string2.length();
        }
        return n2;
    }

    private static class_2561 r(List<TextSegment> list) {
        class_5250 class_52502 = class_2561.method_43470((String)"");
        for (TextSegment textSegment : list) {
            if (textSegment == null || textSegment.alM == null || textSegment.alM.isEmpty()) continue;
            class_52502.method_10852((class_2561)class_2561.method_43470((String)textSegment.alM).method_10862(textSegment.alL == null ? class_2583.field_24360 : textSegment.alL));
        }
        return class_52502;
    }

    private static class_5481 o(List<TextSegment> list) {
        return PlayerNameSanitizer.r(list).method_30937();
    }

    private static class_2561 a(class_2561 class_25612, String string, String string2) {
        if (class_25612 == null) {
            return null;
        }
        List<TextSegment> list = PlayerNameSanitizer.c(class_25612.method_30937());
        if (list.isEmpty()) {
            return null;
        }
        String string3 = PlayerNameSanitizer.n(list);
        int n = PlayerNameSanitizer.a(string3, 0, string, string2);
        if (n < 0) {
            return null;
        }
        int n2 = PlayerNameSanitizer.p(string3, n);
        if (n2 < 0 || n2 > n) {
            n2 = Math.max(0, n);
        }
        if (PlayerNameSanitizer.t(string3, n2)) {
            return class_25612;
        }
        List<class_2583> list2 = PlayerNameSanitizer.p(list);
        class_5250 class_52502 = class_2561.method_43470((String)"");
        PlayerNameSanitizer.b(class_52502, string3, list2, 0, n2);
        class_52502.method_10852((class_2561)class_2561.method_43470((String)PlayerNameSanitizer.sU()).method_10862(PlayerNameSanitizer.d(class_2583.field_24360)));
        PlayerNameSanitizer.b(class_52502, string3, list2, n2, string3.length());
        return class_52502;
    }

    private static int a(String string2, int n, String string3, String string4) {
        Object object;
        if (string2 == null || string2.isEmpty()) {
            return -1;
        }
        int n2 = Math.max(0, Math.min(n, string2.length()));
        for (String string5 : new String[]{string4, string3}) {
            Pattern pattern;
            Matcher matcher;
            if (string5 == null || string5.isBlank()) continue;
            if (alG.size() > 512) {
                alG.clear();
            }
            if (!(matcher = (pattern = alG.computeIfAbsent(string5, string -> Pattern.compile("(?iu)(?<![\\p{L}\\p{N}_])" + Pattern.quote(string) + "(?![\\p{L}\\p{N}_])"))).matcher(string2)).find(n2)) continue;
            return matcher.start();
        }
        if (string3 != null && !string3.isBlank()) {
            object = alE.matcher(string2);
            ((Matcher)object).region(n2, string2.length());
            while (((Matcher)object).find()) {
                if (!string3.equalsIgnoreCase(((Matcher)object).group())) continue;
                return ((Matcher)object).start();
            }
        }
        object = alE.matcher(string2);
        ((Matcher)object).region(n2, string2.length());
        int n3 = -1;
        while (((Matcher)object).find()) {
            String string6 = ((Matcher)object).group();
            if (StreamerTextSanitizer.dt(string6)) {
                if (n3 >= 0) continue;
                n3 = ((Matcher)object).start();
                continue;
            }
            return ((Matcher)object).start();
        }
        return n3;
    }

    private static int en(String string) {
        if (string == null || string.isEmpty()) {
            return -1;
        }
        int n = PlayerNameSanitizer.i(string, 0, string.length());
        int n2 = n < 0 ? 0 : n + PlayerNameSanitizer.s(string, n);
        return PlayerNameSanitizer.a(string, n2, null, null);
    }

    private static int eo(String string) {
        if (string == null || string.isEmpty()) {
            return -1;
        }
        UserSyncManager userSyncManager = PlayerNameSanitizer.sV();
        Matcher matcher = alE.matcher(string);
        while (matcher.find()) {
            String string2 = string.substring(matcher.start(), matcher.end());
            if (PlayerNameSanitizer.ek(string2)) {
                return matcher.start();
            }
            if (userSyncManager == null || !userSyncManager.B(string2)) continue;
            return matcher.start();
        }
        return -1;
    }

    private static boolean t(String string, int n) {
        int n2;
        if (string == null || n < 0) {
            return false;
        }
        int n3 = PlayerNameSanitizer.j(string, 0, n);
        if (n3 < 0) {
            return false;
        }
        for (n2 = n3 + PlayerNameSanitizer.s(string, n3); n2 < n && n2 < string.length() && Character.isWhitespace(string.charAt(n2)); ++n2) {
        }
        return n2 == n;
    }

    private static int ep(String string) {
        int n;
        if (string == null || string.isEmpty()) {
            return 0;
        }
        for (n = 0; n < string.length() && Character.isWhitespace(string.charAt(n)); ++n) {
        }
        return n;
    }

    private static Method eq(String string) {
        try {
            return class_2588.class.getMethod(string, new Class[0]);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    private static Object[] a(class_2588 class_25882) {
        if (alI == null) {
            return null;
        }
        try {
            return (Object[])alI.invoke((Object)class_25882, new Object[0]);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    private static String b(class_2588 class_25882) {
        if (alJ == null) {
            return null;
        }
        try {
            return (String)alJ.invoke((Object)class_25882, new Object[0]);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    private static String c(class_2588 class_25882) {
        if (alK == null) {
            return null;
        }
        try {
            return (String)alK.invoke((Object)class_25882, new Object[0]);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    static {
        alC = "\u2726 ";
        alD = "\u2726";
        alB = class_5251.method_27717((int)0x5555FF);
        alE = Pattern.compile("[A-Za-z0-9_]{3,16}");
        alF = Pattern.compile("^\\[\\d{1,2}:\\d{2}(?::\\d{2})?]\\s*");
        alG = new ConcurrentHashMap<String, Pattern>();
        alH = class_2960.method_60655((String)"minecraft", (String)"uniform");
        alI = PlayerNameSanitizer.eq("getArgs");
        alJ = PlayerNameSanitizer.eq("getKey");
        alK = PlayerNameSanitizer.eq("getFallback");
    }

    static final class TextSegment {
        final class_2583 alL;
        final String alM;

        TextSegment(class_2583 class_25832, String string) {
            this.alL = class_25832;
            this.alM = string;
        }
    }
}
