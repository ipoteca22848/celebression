package ru.method.celebression.modules.impl.render.cosmetic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import ru.method.celebression.Celebression;

public final class LabyModCapeService {
    private static final String ch = "https://laby.net/api/v3/texture/";
    private static final long ci = 30000L;
    private static final Pattern cj = Pattern.compile("([0-9a-fA-F]{32})");
    private static final Map<String, class_2960> ck = new ConcurrentHashMap<String, class_2960>();
    private static final Set<String> cl = ConcurrentHashMap.newKeySet();
    private static final Map<String, Long> cm = new ConcurrentHashMap<String, Long>();
    private static final LinkedHashSet<String> cn = new LinkedHashSet();
    private static boolean co;
    private static final Pattern cp;

    private LabyModCapeService() {
    }

    private static Path B() {
        return FabricLoader.getInstance().getConfigDir().resolve("celebression-laby-capes.txt");
    }

    private static synchronized void C() {
        if (co) {
            return;
        }
        co = true;
        try {
            Path path = LabyModCapeService.B();
            if (Files.exists(path, new LinkOption[0])) {
                for (String string : Files.readAllLines(path)) {
                    String string2 = string.trim().toLowerCase();
                    if (!string2.matches("[0-9a-f]{16,}")) continue;
                    cn.add(string2);
                }
            }
        }
        catch (Throwable throwable) {
        }
    }

    public static synchronized void ah(String string) {
        if (string == null || !string.matches("[0-9a-f]{16,}")) {
            return;
        }
        LabyModCapeService.C();
        if (cn.add(string)) {
            try {
                Files.write(LabyModCapeService.B(), cn, new OpenOption[0]);
            }
            catch (Throwable throwable) {
            }
        }
    }

    public static synchronized void ai(String string) {
        if (string == null) {
            return;
        }
        LabyModCapeService.C();
        if (cn.remove(string.toLowerCase())) {
            try {
                Files.write(LabyModCapeService.B(), cn, new OpenOption[0]);
            }
            catch (Throwable throwable) {
            }
        }
    }

    public static synchronized List<String> D() {
        LabyModCapeService.C();
        return new ArrayList<String>(cn);
    }

    public static CapeProvider aj(String string) {
        if (string == null || string.isBlank()) {
            return CapeProvider.cs;
        }
        if (ck.containsKey(string)) {
            return CapeProvider.cq;
        }
        return LabyModCapeService.ak(string) ? CapeProvider.cs : CapeProvider.cr;
    }

    private static boolean ak(String string) {
        Long l = cm.get(string);
        return l != null && System.currentTimeMillis() - l < 30000L;
    }

    public static String al(String string) {
        if (string == null || string.isBlank()) {
            return "Cape";
        }
        return "Cape \u00b7 " + string.substring(0, Math.min(6, string.length()));
    }

    public static String am(String string) {
        if (string == null) {
            return null;
        }
        String string2 = string.trim();
        Matcher matcher = cp.matcher(string2);
        if (matcher.find()) {
            return matcher.group(1).toLowerCase();
        }
        Matcher matcher2 = cj.matcher(string2);
        if (matcher2.find()) {
            return matcher2.group(1).toLowerCase();
        }
        matcher2 = cj.matcher(string2.replace("-", ""));
        if (matcher2.find()) {
            return matcher2.group(1).toLowerCase();
        }
        return null;
    }

    public static class_2960 an(String string) {
        if (string == null || string.isBlank()) {
            return null;
        }
        class_2960 class_29602 = ck.get(string);
        if (class_29602 != null) {
            return class_29602;
        }
        if (LabyModCapeService.ak(string)) {
            return null;
        }
        if (cl.add(string)) {
            LabyModCapeService.ao(string);
        }
        return null;
    }

    private static void ao(String string) {
        Thread thread = new Thread(() -> {
            try {
                class_1011 class_10112 = LabyModCapeService.ap(string);
                class_310.method_1551().execute(() -> LabyModCapeService.a(string, class_10112));
            }
            catch (Throwable throwable) {
                Celebression.LOGGER.warn("[LabyCape] \u043d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u0437\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u043f\u043b\u0430\u0449 {}: {}", (Object)string, (Object)throwable.toString());
                cm.put(string, System.currentTimeMillis());
                cl.remove(string);
            }
        }, "Celebression-LabyCape");
        thread.setDaemon(true);
        thread.start();
    }

    private static class_1011 ap(String string) throws IOException {
        class_1011 class_10112 = null;
        double d = Double.MAX_VALUE;
        for (String string2 : new String[]{"cloakFront.png", "capeFront.png", "cloak.png", "cape.png"}) {
            class_1011 class_10113;
            try {
                class_10113 = class_1011.method_4309((InputStream)new ByteArrayInputStream(LabyModCapeService.aq("https://laby.net/api/v3/texture/" + string + "/" + string2)));
            }
            catch (Throwable throwable) {
                continue;
            }
            if (LabyModCapeService.a(class_10113)) {
                class_10113.close();
                continue;
            }
            if (class_10113.method_4307() >= 64 && class_10113.method_4307() == class_10113.method_4323() * 2) {
                if (class_10112 != null) {
                    class_10112.close();
                }
                return class_10113;
            }
            double d2 = Math.abs((double)class_10113.method_4307() / (double)class_10113.method_4323() - 0.625);
            if (d2 < d) {
                if (class_10112 != null) {
                    class_10112.close();
                }
                class_10112 = class_10113;
                d = d2;
                continue;
            }
            class_10113.close();
        }
        if (class_10112 != null) {
            class_1011 class_10114 = LabyModCapeService.b(class_10112);
            class_10112.close();
            return class_10114;
        }
        throw new IOException("\u043d\u0435\u0442 \u043d\u0435\u043f\u0443\u0441\u0442\u043e\u0439 \u0442\u0435\u043a\u0441\u0442\u0443\u0440\u044b \u043f\u043b\u0430\u0449\u0430 (cloak/cape) \u0434\u043b\u044f " + string);
    }

    private static boolean a(class_1011 class_10112) {
        int n = class_10112.method_4307();
        int n2 = class_10112.method_4323();
        if (n <= 0 || n2 <= 0) {
            return true;
        }
        int n3 = Math.max(1, n / 48);
        int n4 = Math.max(1, n2 / 48);
        int n5 = 0;
        int n6 = 0;
        for (int i = 0; i < n2; i += n4) {
            for (int j = 0; j < n; j += n3) {
                ++n5;
                int n7 = class_10112.method_61940(j, i);
                int n8 = n7 >>> 24 & 0xFF;
                int n9 = n7 >> 16 & 0xFF;
                int n10 = n7 >> 8 & 0xFF;
                int n11 = n7 & 0xFF;
                if (n8 <= 16 || n9 <= 16 && n10 <= 16 && n11 <= 16) continue;
                ++n6;
            }
        }
        return n5 == 0 || n6 * 100 < n5 * 3;
    }

    private static class_1011 b(class_1011 class_10112) {
        int n = Math.max(1, class_10112.method_4307() / 10);
        int n2 = 10 * n;
        int n3 = 16 * n;
        int[] nArray = LabyModCapeService.c(class_10112);
        class_1011 class_10113 = new class_1011(class_1011.class_1012.field_4997, 64 * n, 32 * n, false);
        LabyModCapeService.a(class_10112, nArray, class_10113, 0, 0, 22 * n, 17 * n);
        LabyModCapeService.a(class_10112, nArray, class_10113, n, n, n2, n3);
        LabyModCapeService.a(class_10112, nArray, class_10113, 12 * n, n, n2, n3);
        return class_10113;
    }

    private static int[] c(class_1011 class_10112) {
        int n;
        int n2;
        int n3 = class_10112.method_4307();
        int n4 = class_10112.method_4323();
        int n5 = n3;
        int n6 = n4;
        int n7 = -1;
        int n8 = -1;
        for (n2 = 0; n2 < n4; ++n2) {
            for (n = 0; n < n3; ++n) {
                int n9 = class_10112.method_61940(n, n2);
                int n10 = n9 >>> 24 & 0xFF;
                int n11 = n9 >> 16 & 0xFF;
                int n12 = n9 >> 8 & 0xFF;
                int n13 = n9 & 0xFF;
                if (n10 <= 16 || n11 <= 16 && n12 <= 16 && n13 <= 16) continue;
                if (n < n5) {
                    n5 = n;
                }
                if (n > n7) {
                    n7 = n;
                }
                if (n2 < n6) {
                    n6 = n2;
                }
                if (n2 <= n8) continue;
                n8 = n2;
            }
        }
        if (n7 < 0) {
            return new int[]{0, 0, n3, n4};
        }
        n2 = n7 - n5 + 1;
        n = n8 - n6 + 1;
        if (n2 * 100 >= n3 * 97 && n * 100 >= n4 * 97) {
            return new int[]{0, 0, n3, n4};
        }
        return new int[]{n5, n6, n2, n};
    }

        private static void a(String string, class_1011 class_10112) {
        try {
            class_2960 class_29602 = class_2960.method_60655((String)"celebression", (String)("laby_cape/" + string));
            class_1043 class_10432 = new class_1043(class_10112);
            class_10432.method_4527(false, false);
            class_10432.method_4524();
            class_310.method_1551().method_1531().method_4616(class_29602, (class_1044)class_10432);
            ck.put(string, class_29602);
            cm.remove(string);
            Celebression.LOGGER.info("[LabyCape] \u043f\u043b\u0430\u0449 {} \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d ({}x{})", (Object)string, (Object)class_10112.method_4307(), (Object)class_10112.method_4323());
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.warn("[LabyCape] \u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u044f \u043f\u043b\u0430\u0449\u0430 {} \u0443\u043f\u0430\u043b\u0430: {}", (Object)string, (Object)throwable.toString());
            cm.put(string, System.currentTimeMillis());
            try {
                class_10112.close();
            }
            catch (Throwable throwable2) {
            }
        }
        finally {
            cl.remove(string);
        }
    }

    private static void a(class_1011 class_10112, int[] nArray, class_1011 class_10113, int n, int n2, int n3, int n4) {
        int n5 = nArray[2];
        int n6 = nArray[3];
        for (int i = 0; i < n4; ++i) {
            for (int j = 0; j < n3; ++j) {
                int n7 = nArray[0] + (n5 == n3 ? j : j * n5 / n3);
                int n8 = nArray[1] + (n6 == n4 ? i : i * n6 / n4);
                class_10113.method_61941(n + j, n2 + i, class_10112.method_61940(n7, n8));
            }
        }
    }

    private static byte[] aq(String string) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection)URI.create(string).toURL().openConnection();
        httpURLConnection.setRequestProperty("User-Agent", "Celebression");
        httpURLConnection.setConnectTimeout(8000);
        httpURLConnection.setReadTimeout(8000);
        httpURLConnection.setInstanceFollowRedirects(true);
        try (InputStream inputStream = httpURLConnection.getInputStream()) {
            return inputStream.readAllBytes();
        }
        finally {
            httpURLConnection.disconnect();
        }
    }

    static {
        cp = Pattern.compile("(?:capes?|cloaks?)/([0-9a-fA-F]{16,})");
    }

    public static enum CapeProvider {
        cq,
        cr,
        cs;
}
}
