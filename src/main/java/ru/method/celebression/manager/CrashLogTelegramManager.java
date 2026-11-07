package ru.method.celebression.manager;

import ru.method.celebression.api.setting.Setting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import lombok.Generated;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.BooleanParser;
import ru.method.celebression.render.FrameTimeTracker;
import ru.method.celebression.network.TelegramClient;

public class CrashLogTelegramManager {
    private static final String mP = "LV_TG_CRASH_ENABLED";
    private static final String mQ = "LV_TG_BOT_TOKEN";
    private static final String mR = "LV_TG_CHAT_ID";
    private static final String mS = "lv.tg.crash.enabled";
    private static final String mT = "lv.tg.botToken";
    private static final String mU = "lv.tg.chatId";
    private static final String mV = "tg-crash-sent.prime";
    private static final int mW = 0x200000;
    private static final String[] mX = new String[]{"celebression", "ru.method.celebression", "celebression.mixins", "[celebression]"};
    private static final boolean mY = false;
    private static final String mZ = "";
    private static final String na = "";
    private final TelegramClient nb = new TelegramClient(HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5L)).version(HttpClient.Version.HTTP_1_1).build());
    private final AtomicBoolean nc = new AtomicBoolean(false);
    private boolean enabled = false;
    private String nd = "";
    private String ne = "";

    public void a(boolean bl, String string, String string2) {
        this.enabled = bl;
        this.nd = FrameTimeTracker.cQ(string);
        this.ne = FrameTimeTracker.cQ(string2);
    }

    public void shutdown() {
        this.nc.set(false);
        this.nb.close();
    }

    public void eh() {
        if (!this.nc.compareAndSet(false, true)) {
            return;
        }
        Thread thread = new Thread(() -> {
            try {
                this.ei();
            }
            catch (Exception exception) {
                Celebression.LOGGER.warn("[CrashLogTelegram] Failed to send crash reports", (Throwable)exception);
            }
            finally {
                this.nc.set(false);
            }
        }, "Celebression-CrashLogTelegram");
        thread.setDaemon(true);
        thread.start();
    }

    void ei() {
        CrashLogPayload crashLogPayload = this.ej();
        if (!crashLogPayload.nf) {
            Celebression.LOGGER.info("[CrashLogTelegram] Sender is disabled");
            return;
        }
        if (this.bs(crashLogPayload.ng) || this.bs(crashLogPayload.nh)) {
            Celebression.LOGGER.warn("[CrashLogTelegram] Configure LV_TG_BOT_TOKEN/LV_TG_CHAT_ID or JVM properties lv.tg.botToken/lv.tg.chatId");
            return;
        }
        if (crashLogPayload.ng.isBlank() || crashLogPayload.nh.isBlank()) {
            Celebression.LOGGER.warn("[CrashLogTelegram] Missing bot token or chat id, skipping");
            return;
        }
        Path path = this.ek();
        if (!Files.isDirectory(path, new LinkOption[0])) {
            return;
        }
        Path path2 = this.el();
        Set<String> set = this.b(path2);
        List<Path> list = this.a(path);
        if (list.isEmpty()) {
            return;
        }
        if (!this.c(path2)) {
            for (Path path3 : list) {
                String string = this.d(path3);
                if (string.isBlank()) continue;
                set.add(string);
            }
            this.a(path2, set);
            Celebression.LOGGER.info("[CrashLogTelegram] Baseline created for {} existing crash reports", (Object)set.size());
            return;
        }
        boolean bl = false;
        for (Path path4 : list) {
            String string = this.d(path4);
            if (string.isBlank() || set.contains(string)) continue;
            if (!this.e(path4)) {
                set.add(string);
                bl = true;
                continue;
            }
            String string2 = "Celebression crash report: " + String.valueOf(path4.getFileName());
            TelegramClient.TelegramResponse telegramResponse = this.nb.a(crashLogPayload.ng, crashLogPayload.nh, path4, string2, Duration.ofSeconds(15L));
            if (!telegramResponse.rY()) {
                Celebression.LOGGER.warn("[CrashLogTelegram] Failed to send {}: {}", (Object)path4.getFileName(), (Object)telegramResponse.sb());
                continue;
            }
            set.add(string);
            bl = true;
            Celebression.LOGGER.info("[CrashLogTelegram] Sent crash report {}", (Object)path4.getFileName());
            break;
        }
        if (bl) {
            this.a(path2, set);
        }
    }

    private CrashLogPayload ej() {
        String string;
        String string2;
        String string3;
        String string4;
        String string5;
        boolean bl = this.enabled;
        String string6 = this.nd;
        String string7 = this.ne;
        String string8 = FrameTimeTracker.cQ(System.getenv("LV_TG_CRASH_ENABLED"));
        if (!string8.isBlank()) {
            bl = BooleanParser.parseBoolean(string8, bl);
        }
        if (!(string5 = FrameTimeTracker.cQ(System.getProperty("lv.tg.crash.enabled"))).isBlank()) {
            bl = BooleanParser.parseBoolean(string5, bl);
        }
        if (!(string4 = FrameTimeTracker.cQ(System.getenv("LV_TG_BOT_TOKEN"))).isBlank()) {
            string6 = string4;
        }
        if (!(string3 = FrameTimeTracker.cQ(System.getProperty("lv.tg.botToken"))).isBlank()) {
            string6 = string3;
        }
        if (!(string2 = FrameTimeTracker.cQ(System.getenv("LV_TG_CHAT_ID"))).isBlank()) {
            string7 = string2;
        }
        if (!(string = FrameTimeTracker.cQ(System.getProperty("lv.tg.chatId"))).isBlank()) {
            string7 = string;
        }
        return new CrashLogPayload(bl, string6, string7);
    }

    private boolean bs(String string) {
        String string2 = FrameTimeTracker.cQ(string);
        return "PASTE_BOT_TOKEN_HERE".equals(string2) || "PASTE_CHAT_ID_HERE".equals(string2);
    }

    private Path ek() {
        Path path = Celebression.getInstance().getGlobalsDir().toPath();
        Path path2 = path.getParent();
        if (path2 == null) {
            return path.resolve("crash-reports");
        }
        return path2.resolve("crash-reports");
    }

    private Path el() {
        return Celebression.getInstance().getGlobalsDir().toPath().resolve("tg-crash-sent.prime");
    }

    private List<Path> a(Path path2) {
        ArrayList<Path> arrayList = new ArrayList<Path>();
        try (Stream<Path> stream = Files.list(path2);){
            stream.filter(path -> Files.isRegularFile(path, new LinkOption[0]) && path.getFileName().toString().endsWith(".txt")).sorted(Comparator.comparing(Path::getFileName)).forEach(arrayList::add);
        }
        catch (IOException iOException) {
            Celebression.LOGGER.warn("[CrashLogTelegram] Failed to list crash reports directory {}", (Object)path2, (Object)iOException);
        }
        return arrayList;
    }

    private Set<String> b(Path path) {
        HashSet<String> hashSet = new HashSet<String>();
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            return hashSet;
        }
        try {
            String string = Files.readString(path, StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonParser.parseString((String)string).getAsJsonObject();
            if (!jsonObject.has("sent") || !jsonObject.get("sent").isJsonArray()) {
                return hashSet;
            }
            JsonArray jsonArray = jsonObject.getAsJsonArray("sent");
            for (JsonElement jsonElement : jsonArray) {
                String string2;
                if (!jsonElement.isJsonPrimitive() || (string2 = FrameTimeTracker.cQ(jsonElement.getAsString())).isBlank()) continue;
                hashSet.add(string2);
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[CrashLogTelegram] Failed to read sent state file {}", (Object)path, (Object)exception);
        }
        return hashSet;
    }

    private boolean c(Path path) {
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            return false;
        }
        try {
            String string = Files.readString(path, StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonParser.parseString((String)string).getAsJsonObject();
            return jsonObject.has("baselineCreated") && jsonObject.get("baselineCreated").getAsBoolean();
        }
        catch (Exception exception) {
            return false;
        }
    }

    private void a(Path path, Set<String> set) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        set.stream().sorted().forEach(arg_0 -> ((JsonArray)jsonArray).add(arg_0));
        jsonObject.add("sent", (JsonElement)jsonArray);
        jsonObject.addProperty("baselineCreated", Boolean.valueOf(true));
        try {
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            Files.writeString(path, (CharSequence)jsonObject.toString(), StandardCharsets.UTF_8, new OpenOption[0]);
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[CrashLogTelegram] Failed to write sent state file {}", (Object)path, (Object)exception);
        }
    }

    private String d(Path path) {
        try {
            return FrameTimeTracker.cQ(path.getFileName().toString()) + "|" + Files.size(path) + "|" + Files.getLastModifiedTime(path, new LinkOption[0]).toMillis();
        }
        catch (Exception exception) {
            return "";
        }
    }

    private boolean e(Path path) {
        try {
            long l = Files.size(path);
            int n = (int)Math.min(l, 0x200000L);
            byte[] byArray = new byte[n];
            try (InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);){
                int n2;
                int n3;
                for (n2 = 0; n2 < n && (n3 = inputStream.read(byArray, n2, n - n2)) >= 0; n2 += n3) {
                }
                if (n2 < n) {
                    byte[] byArray2 = new byte[n2];
                    System.arraycopy(byArray, 0, byArray2, 0, n2);
                    byArray = byArray2;
                }
            }
            String reportText = new String(byArray, StandardCharsets.UTF_8).toLowerCase(Locale.ROOT);
            for (String string : mX) {
                if (!reportText.contains(string)) continue;
                return true;
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[CrashLogTelegram] Failed to inspect crash report {}", (Object)path.getFileName(), (Object)exception);
        }
        return false;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String em() {
        return this.nd;
    }

    @Generated
    public String en() {
        return this.ne;
    }

    static final class CrashLogPayload {
        final boolean nf;
        final String ng;
        final String nh;

        CrashLogPayload(boolean bl, String string, String string2) {
            this.nf = bl;
            this.ng = string;
            this.nh = string2;
        }

        @Override
        public final String toString() {
            return "CrashLogPayload[" + "enabled=" + this.nf + ", " + "botToken=" + this.ng + ", " + "chatId=" + this.nh + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.nf, this.ng, this.nh);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof CrashLogPayload other)) return false;
            return java.util.Objects.deepEquals(this.nf, other.nf)
                && java.util.Objects.deepEquals(this.ng, other.ng)
                && java.util.Objects.deepEquals(this.nh, other.nh);
        }

        public boolean enabled() {
            return this.nf;
        }

        public String ep() {
            return this.ng;
        }

        public String eq() {
            return this.nh;
        }
    }
}
