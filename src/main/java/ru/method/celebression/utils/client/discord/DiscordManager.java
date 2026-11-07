package ru.method.celebression.utils.client.discord;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import lombok.Generated;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_1060;
import net.minecraft.class_2960;
import net.minecraft.class_642;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.utils.client.discord.utils.DiscordEventHandlers;
import ru.method.celebression.utils.client.discord.utils.DiscordRPC;
import ru.method.celebression.utils.client.discord.utils.DiscordRichPresence;
import ru.method.celebression.utils.client.discord.utils.DiscordRpcProvider;

public class DiscordManager
implements MinecraftClientAccess {
    private static final String APPLICATION_ID = "1568650316876877974";
    private static final String LARGE_IMAGE_KEY = "photo_2026-05-03_12-21-54";
    private static final String SMALL_IMAGE_KEY = "photo_2026-05-03_12-21-54";
    private static final String CLIENT_NAME = "celebression";
    private static final String CLIENT_VERSION = "2.0.2";
    private static final String MAIN_MENU_DETAILS = "\u0412 \u0433\u043b\u0430\u0432\u043d\u043e\u043c \u043c\u0435\u043d\u044e";
    private static final String SINGLEPLAYER_DETAILS = "\u041e\u0434\u0438\u043d\u043e\u0447\u043d\u0430\u044f \u0438\u0433\u0440\u0430";
    private static final String PLAYING_ON_PREFIX = "\u0418\u0433\u0440\u0430\u0435\u0442 \u043d\u0430 ";
    private static final String PLAYER_PREFIX = "\u0418\u0433\u0440\u043e\u043a: ";
    private static final String NICK_PREFIX = "\u041d\u0438\u043a: ";
    private static final String MULTIPLAYER_FALLBACK = "multiplayer";
    private static final long PRESENCE_UPDATE_INTERVAL_MS = 3000L;
    volatile boolean running;
    private volatile boolean ready;
    private volatile String lastStatus = "idle";
    private volatile String lastError = "";
    private volatile String lastDetails = "";
    private volatile String lastState = "";
    private volatile long lastInitializeAt;
    private volatile long lastReadyAt;
    private volatile long lastPresenceUpdateAt;
    private volatile long presenceUpdateCount;
    private long rpcStartTimestamp;
    private DiscordInfo info = new DiscordInfo("Unknown", "", "");
    private class_2960 avatarId;
    private Thread daemonThread;
    private DiscordEventHandlers handlers;
    DiscordRPC rpc;

    private long nextAutomaticRetry;
    @meteordevelopment.orbit.EventHandler
    private void maintainPresence(ru.method.celebression.api.event.ClientTickEvent event) {
        if (ru.method.celebression.service.UnhookService.isActive() || running) return;
        long now = System.currentTimeMillis();
        if (now < nextAutomaticRetry) return;
        nextAutomaticRetry = now + 15000L;
        init();
    }
    public synchronized void init() {
        if (ru.method.celebression.service.UnhookService.isActive()) return;
        if (this.running) {
            this.forcePresenceReplace();
            return;
        }
        try {
            this.lastInitializeAt = System.currentTimeMillis();
            this.lastStatus = "loading native library";
            this.lastError = "";
            DiscordRPC discordRPC = DiscordRpcProvider.get();
            if (discordRPC == null) {
                Throwable throwable = DiscordRpcProvider.getLoadError();
                if (throwable != null) {
                    Celebression.LOGGER.warn("Discord RPC native library unavailable", throwable);
                    this.lastError = throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
                } else {
                    Celebression.LOGGER.warn("Discord RPC native library unavailable");
                    this.lastError = "native library unavailable";
                }
                this.lastStatus = "native library unavailable";
                this.running = false;
                return;
            }
            this.rpc = discordRPC;
            this.lastStatus = "initializing discord rpc";
            Celebression.LOGGER.info("[DiscordRPC] Native library loaded from {}", (Object)this.debugLibraryPath());
            DiscordEventHandlers.Builder builder = new DiscordEventHandlers.Builder().ready(discordUser -> {
                String string = discordUser.username != null ? discordUser.username : "Unknown";
                String string2 = discordUser.userId != null ? discordUser.userId : "N/A";
                this.setInfo(new DiscordInfo(string, this.buildAvatarUrl(discordUser.userId, discordUser.avatar), string2));
                this.ready = true;
                this.lastReadyAt = System.currentTimeMillis();
                this.lastStatus = "ready: " + string;
                this.lastError = "";
                Celebression.LOGGER.info("[DiscordRPC] Ready as {} ({})", (Object)string, (Object)string2);
                this.updatePresence();
            }).errored((n, string) -> {
                this.lastStatus = "error " + n;
                this.lastError = string;
                Celebression.LOGGER.error("Discord RPC error {}: {}", (Object)n, (Object)string);
            }).disconnected((n, string) -> {
                this.ready = false;
                this.lastStatus = "disconnected " + n;
                this.lastError = string;
                Celebression.LOGGER.warn("Discord RPC disconnected {}: {}", (Object)n, (Object)string);
            });
            this.handlers = builder.build();
            this.rpc.Discord_Initialize(APPLICATION_ID, this.handlers, true, "");
            this.running = true;
            this.ready = false;
            this.lastStatus = "initialized, waiting for Discord ready";
            this.rpcStartTimestamp = System.currentTimeMillis() / 1000L;
            this.forcePresenceReplace();
            this.daemonThread = new DiscordDaemonThread();
            this.daemonThread.start();
            this.updatePresence();
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.error("Failed to initialize Discord RPC", throwable);
            this.lastStatus = "initialize failed";
            this.lastError = throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
            this.running = false;
            this.ready = false;
            this.rpc = null;
            DiscordRpcProvider.resetLoadError();
        }
    }

    public synchronized void stopRPC() {
        if (!this.running) {
            return;
        }
        this.running = false;
        this.ready = false;
        this.lastStatus = "stopped";
        DiscordRPC discordRPC = this.rpc;
        this.rpc = null;
        if (discordRPC != null) {
            try {
                discordRPC.Discord_ClearPresence();
            }
            catch (Throwable throwable) {
            }
            try {
                discordRPC.Discord_Shutdown();
            }
            catch (Throwable throwable) {
            }
        }
        if (this.daemonThread != null) {
            this.daemonThread.interrupt();
            this.daemonThread = null;
        }
        this.rpcStartTimestamp = 0L;
        if (this.avatarId != null && aiG != null) {
            aiG.method_1531().method_4615(this.avatarId);
            this.avatarId = null;
        }
        this.info = new DiscordInfo("Unknown", "", "");
    }

    public void load() {
        if (this.avatarId == null && this.info.avatarUrl() != null && !this.info.avatarUrl().isEmpty()) {
            try {
                class_1011 class_10112 = this.downloadImage(this.info.avatarUrl());
                if (class_10112 != null) {
                    this.registerDynamicTexture("discord-avatar-" + this.info.userId(), class_10112);
                }
            }
            catch (Exception exception) {
                Celebression.LOGGER.warn("Failed to load Discord avatar", exception);
            }
        }
    }

    void updatePresence() {
        try {
            DiscordRPC discordRPC = this.rpc;
            if (!this.running || discordRPC == null) {
                return;
            }
            DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder().setStartTimestamp(this.rpcStartTimestamp > 0L ? this.rpcStartTimestamp : System.currentTimeMillis() / 1000L).setDetails(this.resolveDetails()).setState(this.resolveState()).setLargeImage("photo_2026-05-03_12-21-54", "Celebression Visuals 2.0").setSmallImage("photo_2026-05-03_12-21-54", this.resolveSmallImageText());
            DiscordRichPresence discordRichPresence = builder.build();
            discordRPC.Discord_UpdatePresence(discordRichPresence);
            this.lastDetails = discordRichPresence.details;
            this.lastState = discordRichPresence.state;
            this.lastPresenceUpdateAt = System.currentTimeMillis();
            ++this.presenceUpdateCount;
            if (!this.ready) {
                this.lastStatus = "presence sent, waiting for Discord ready";
            }
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.error("Failed to update Discord presence", throwable);
            this.lastStatus = "presence update failed";
            this.lastError = throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
        }
    }

    public synchronized void forcePresenceReplace() {
        DiscordRPC discordRPC = this.rpc;
        if (!this.running || discordRPC == null) {
            return;
        }
        try {
            discordRPC.Discord_ClearPresence();
            discordRPC.Discord_RunCallbacks();
            this.updatePresence();
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.error("Failed to replace Discord presence", throwable);
        }
    }

    public void ensurePresence() {
        if (!this.running || this.rpc == null) {
            this.init();
            return;
        }
        this.forcePresenceReplace();
    }

    public String debugStatus() {
        return "DiscordRPC\nrunning=" + this.running + "\nready=" + this.ready + "\nstatus=" + this.lastStatus + "\nerror=" + (this.lastError == null || this.lastError.isBlank() ? "-" : this.lastError) + "\nupdates=" + this.presenceUpdateCount + "\nlastUpdateMsAgo=" + this.ageMs(this.lastPresenceUpdateAt) + "\nlastReadyMsAgo=" + this.ageMs(this.lastReadyAt) + "\ndetails=" + (this.lastDetails == null || this.lastDetails.isBlank() ? "-" : this.lastDetails) + "\nstate=" + (this.lastState == null || this.lastState.isBlank() ? "-" : this.lastState) + "\ndll=" + this.debugLibraryPath();
    }

    private String debugLibraryPath() {
        Path path = DiscordRpcProvider.getExtractedLibraryPath();
        return path == null ? "-" : path.toAbsolutePath().toString();
    }

    private long ageMs(long l) {
        return l <= 0L ? -1L : Math.max(0L, System.currentTimeMillis() - l);
    }

    private String resolveDetails() {
        if (aiG == null || DiscordManager.aiG.field_1724 == null || DiscordManager.aiG.field_1687 == null) {
            return "\u0412 \u0433\u043b\u0430\u0432\u043d\u043e\u043c \u043c\u0435\u043d\u044e";
        }
        if (aiG.method_1542()) {
            return "\u041e\u0434\u0438\u043d\u043e\u0447\u043d\u0430\u044f \u0438\u0433\u0440\u0430";
        }
        return "\u0418\u0433\u0440\u0430\u0435\u0442 \u043d\u0430 " + this.resolveServerDomain();
    }

    private String resolveState() {
        String string = this.resolvePlayerName();
        if (aiG == null || DiscordManager.aiG.field_1724 == null || DiscordManager.aiG.field_1687 == null) {
            return "Celebression Visuals 2.0";
        }
        if (aiG.method_1542()) {
            return "\u0418\u0433\u0440\u043e\u043a: " + string;
        }
        return "\u041d\u0438\u043a: " + string;
    }

    private String resolveSmallImageText() {
        if (aiG == null || DiscordManager.aiG.field_1724 == null || DiscordManager.aiG.field_1687 == null) {
            return "Minecraft 1.21.4";
        }
        if (aiG.method_1542()) {
            return "Singleplayer";
        }
        return this.resolveServerDomain();
    }

    private String resolvePlayerName() {
        if (aiG != null && DiscordManager.aiG.field_1724 != null && DiscordManager.aiG.field_1724.method_7334() != null) {
            return DiscordManager.aiG.field_1724.method_7334().getName();
        }
        if (aiG != null && aiG.method_1548() != null && aiG.method_1548().method_1676() != null) {
            return aiG.method_1548().method_1676();
        }
        return "Unknown";
    }

    private String resolveServerAddress() {
        class_642 class_6422;
        class_642 class_6423 = class_6422 = aiG == null ? null : aiG.method_1558();
        if (class_6422 == null || class_6422.field_3761 == null || class_6422.field_3761.isBlank()) {
            return "multiplayer";
        }
        return class_6422.field_3761.trim();
    }

    private String resolveServerDomain() {
        String string = this.resolveServerAddress();
        if ("multiplayer".equals(string)) {
            return string;
        }
        if (string.startsWith("[")) {
            int n = string.indexOf(93);
            return n > 0 ? string.substring(0, n + 1) : string;
        }
        int n = string.indexOf(58);
        return n > 0 ? string.substring(0, n) : string;
    }

    private String buildAvatarUrl(String string, String string2) {
        if (string == null || string2 == null || string.isEmpty() || string2.isEmpty()) {
            return "";
        }
        return "https://cdn.discordapp.com/avatars/" + string + "/" + string2 + ".png";
    }

    private class_1011 downloadImage(String string) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection)new URL(string).openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setRequestProperty("User-Agent", "celebression-DiscordRPC");
            try (InputStream inputStream = httpURLConnection.getInputStream()) {
                return class_1011.method_4309(inputStream);
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("Failed to download Discord avatar", exception);
            return null;
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    private void registerDynamicTexture(String string, class_1011 class_10112) {
        class_1060 class_10602;
        class_1060 class_10603 = class_10602 = aiG != null ? aiG.method_1531() : null;
        if (class_10602 == null) {
            class_10112.close();
            return;
        }
        class_2960 class_29602 = Celebression.id(string);
        class_10602.method_4616(class_29602, (class_1044)new class_1043(class_10112));
        this.avatarId = class_29602;
    }

    @Generated
    public boolean isRunning() {
        return this.running;
    }

    @Generated
    public boolean isReady() {
        return this.ready;
    }

    @Generated
    public String getLastStatus() {
        return this.lastStatus;
    }

    @Generated
    public String getLastError() {
        return this.lastError;
    }

    @Generated
    public String getLastDetails() {
        return this.lastDetails;
    }

    @Generated
    public String getLastState() {
        return this.lastState;
    }

    @Generated
    public long getLastInitializeAt() {
        return this.lastInitializeAt;
    }

    @Generated
    public long getLastReadyAt() {
        return this.lastReadyAt;
    }

    @Generated
    public long getLastPresenceUpdateAt() {
        return this.lastPresenceUpdateAt;
    }

    @Generated
    public long getPresenceUpdateCount() {
        return this.presenceUpdateCount;
    }

    @Generated
    public long getRpcStartTimestamp() {
        return this.rpcStartTimestamp;
    }

    @Generated
    public DiscordInfo getInfo() {
        return this.info;
    }

    @Generated
    public class_2960 getAvatarId() {
        return this.avatarId;
    }

    @Generated
    public Thread getDaemonThread() {
        return this.daemonThread;
    }

    @Generated
    public DiscordEventHandlers getHandlers() {
        return this.handlers;
    }

    @Generated
    public DiscordRPC getRpc() {
        return this.rpc;
    }

    @Generated
    public void setRunning(boolean bl) {
        this.running = bl;
    }

    @Generated
    public void setReady(boolean bl) {
        this.ready = bl;
    }

    @Generated
    public void setLastStatus(String string) {
        this.lastStatus = string;
    }

    @Generated
    public void setLastError(String string) {
        this.lastError = string;
    }

    @Generated
    public void setLastDetails(String string) {
        this.lastDetails = string;
    }

    @Generated
    public void setLastState(String string) {
        this.lastState = string;
    }

    @Generated
    public void setLastInitializeAt(long l) {
        this.lastInitializeAt = l;
    }

    @Generated
    public void setLastReadyAt(long l) {
        this.lastReadyAt = l;
    }

    @Generated
    public void setLastPresenceUpdateAt(long l) {
        this.lastPresenceUpdateAt = l;
    }

    @Generated
    public void setPresenceUpdateCount(long l) {
        this.presenceUpdateCount = l;
    }

    @Generated
    public void setRpcStartTimestamp(long l) {
        this.rpcStartTimestamp = l;
    }

    @Generated
    public void setInfo(DiscordInfo discordInfo) {
        this.info = discordInfo;
    }

    @Generated
    public void setAvatarId(class_2960 class_29602) {
        this.avatarId = class_29602;
    }

    @Generated
    public void setDaemonThread(Thread thread) {
        this.daemonThread = thread;
    }

    @Generated
    public void setHandlers(DiscordEventHandlers discordEventHandlers) {
        this.handlers = discordEventHandlers;
    }

    @Generated
    public void setRpc(DiscordRPC discordRPC) {
        this.rpc = discordRPC;
    }

    public record DiscordInfo(String userName, String avatarUrl, String userId) {
    }

    class DiscordDaemonThread
    extends Thread {
        DiscordDaemonThread() {
            this.setName("Discord-RPC");
            this.setDaemon(true);
        }

        @Override
        public void run() {
            try {
                DiscordRPC discordRPC;
                while (DiscordManager.this.running && (discordRPC = DiscordManager.this.rpc) != null) {
                    discordRPC.Discord_RunCallbacks();
                    DiscordManager.this.load();
                    DiscordManager.this.updatePresence();
                    Thread.sleep(3000L);
                }
            }
            catch (InterruptedException interruptedException) {
            }
            catch (Exception exception) {
                DiscordManager.this.stopRPC();
            }
        }
    }
}
