package ru.method.celebression.manager;

import ru.method.celebression.api.setting.Setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.class_746;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.BooleanParser;
import ru.method.celebression.render.FrameTimeTracker;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public class UsageStatsManager
implements MinecraftClientAccess {
    private static final String nw = "LV_STATS_ENABLED";
    private static final String nx = "LV_STATS_ENDPOINT";
    private static final boolean ny = false;
    private static final String nz = "http://127.0.0.1:8091/stats/ping";
    private static final long nA = 30000L;
    private static final long nB = 60000L;
    private volatile long nC = 0L;
    private final HttpClient nD = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(4L)).version(HttpClient.Version.HTTP_1_1).build();
    private final AtomicBoolean nE = new AtomicBoolean(false);
    private boolean enabled = false;
    private String nF = "http://127.0.0.1:8091/stats/ping";

    public boolean isEnabled() {
        return this.enabled;
    }

    public String eB() {
        return this.nF;
    }

    public void a(boolean bl, String string) {
        this.enabled = bl;
        this.nF = FrameTimeTracker.cQ(string);
    }

    public void shutdown() {
        this.nE.set(false);
        try {
            this.nD.shutdownNow();
        }
        catch (Throwable throwable) {
        }
    }

    public void eC() {
        if (!this.nE.compareAndSet(false, true)) {
            return;
        }
        if (!this.eD().nG) {
            this.nE.set(false);
            return;
        }
        Thread thread = new Thread(() -> {
            try {
                this.A("launch");
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(30000L);
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    this.A("heartbeat");
                }
            }
            catch (Exception exception) {
                Celebression.LOGGER.warn("[UsageStats] Reporter stopped due to error", (Throwable)exception);
            }
            finally {
                this.nE.set(false);
            }
        }, "Celebression-UsageStats");
        thread.setDaemon(true);
        thread.start();
    }

    private void A(String string) {
        block5: {
            try {
                URI uRI;
                UsageSnapshot usageSnapshot = this.eD();
                if (!usageSnapshot.nG || usageSnapshot.nH.isBlank()) {
                    return;
                }
                try {
                    uRI = URI.create(usageSnapshot.nH);
                }
                catch (Exception exception) {
                    Celebression.LOGGER.warn("[UsageStats] Bad endpoint URL: {}", (Object)usageSnapshot.nH);
                    return;
                }
                class_746 class_7462 = aiG != null ? UsageStatsManager.aiG.field_1724 : null;
                GameProfile gameProfile = class_7462 != null ? class_7462.method_7334() : null;
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("event", string);
                jsonObject.addProperty("timestamp", (Number)(System.currentTimeMillis() / 1000L));
                jsonObject.addProperty("location", this.eE());
                jsonObject.addProperty("modId", "celebression");
                jsonObject.addProperty("modVersion", "unknown");
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("uuid", this.a(gameProfile));
                jsonObject2.addProperty("name", this.b(gameProfile));
                jsonObject.add("user", (JsonElement)jsonObject2);
                HttpRequest httpRequest = HttpRequest.newBuilder().uri(uRI).timeout(Duration.ofSeconds(4L)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString(), StandardCharsets.UTF_8)).build();
                this.nD.sendAsync(httpRequest, HttpResponse.BodyHandlers.discarding()).exceptionally(throwable -> null);
            }
            catch (Exception exception) {
                long l = System.currentTimeMillis();
                if (l - this.nC <= 60000L) break block5;
                this.nC = l;
                Celebression.LOGGER.warn("[UsageStats] sendPing failed (event={}), heartbeat continues", (Object)string, (Object)exception);
            }
        }
    }

    private UsageSnapshot eD() {
        String string;
        boolean bl = this.enabled;
        String string2 = this.nF;
        String string3 = FrameTimeTracker.cQ(System.getenv("LV_STATS_ENABLED"));
        if (!string3.isBlank()) {
            bl = BooleanParser.parseBoolean(string3, bl);
        }
        if (!(string = FrameTimeTracker.cQ(System.getenv("LV_STATS_ENDPOINT"))).isBlank()) {
            string2 = string;
        }
        return new UsageSnapshot(bl, string2);
    }

    private String eE() {
        String string;
        if (aiG == null) {
            return "menu";
        }
        if (aiG.method_1542()) {
            return "singleplayer";
        }
        if (aiG.method_1558() != null && UsageStatsManager.aiG.method_1558().field_3761 != null && !(string = FrameTimeTracker.cQ(UsageStatsManager.aiG.method_1558().field_3761)).isBlank()) {
            return string.toLowerCase(Locale.ROOT);
        }
        return "menu";
    }

    private String a(GameProfile gameProfile) {
        if (gameProfile != null && gameProfile.getId() != null) {
            return gameProfile.getId().toString();
        }
        String string = this.b(gameProfile);
        return UUID.nameUUIDFromBytes(string.getBytes(StandardCharsets.UTF_8)).toString();
    }

    private String b(GameProfile gameProfile) {
        String string;
        if (gameProfile != null && !(string = FrameTimeTracker.cQ(gameProfile.getName())).isBlank()) {
            return string;
        }
        if (aiG != null && aiG.method_1548() != null && !(string = FrameTimeTracker.cQ(aiG.method_1548().method_1676())).isBlank()) {
            return string;
        }
        return "unknown";
    }

    static final class UsageSnapshot {
        final boolean nG;
        final String nH;

        UsageSnapshot(boolean bl, String string) {
            this.nG = bl;
            this.nH = string;
        }

        @Override
        public final String toString() {
            return "UsageSnapshot[" + "enabled=" + this.nG + ", " + "endpointUrl=" + this.nH + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.nG, this.nH);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof UsageSnapshot other)) return false;
            return java.util.Objects.deepEquals(this.nG, other.nG)
                && java.util.Objects.deepEquals(this.nH, other.nH);
        }

        public boolean enabled() {
            return this.nG;
        }

        public String eG() {
            return this.nH;
        }
    }
}

