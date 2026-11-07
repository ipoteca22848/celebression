package ru.method.celebression.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_642;
import net.minecraft.class_8646;
import org.joml.Vector3f;
import ru.method.celebression.Celebression;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLoadout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticProfileJson;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsService;
import ru.method.celebression.manager.FriendManager;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.service.NativeProtectionStatus;
import ru.method.celebression.modules.impl.client.PartyModule;
import ru.method.celebression.modules.impl.render.cosmetic.PlayerCosmeticProfileStore;
import ru.method.celebression.render.ProjectionUtils;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffects;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.DepthRenderScope;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class UserSyncManager
implements MinecraftClientAccess {
    private static final String nI = "http://91.92.33.75:8080/sync";
    private static final long nJ = 5000L;
    private static final long nK = 400L;
    private static final int nL = 64;
    private static final long nM = 450L;
    private static final int nN = 2;
    private static final float nO = 4.0f;
    private static final float nP = 24.0f;
    private static final float nQ = 48.0f;
    private static final float nR = 150.0f;
    private final HttpClient nS = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(4L)).version(HttpClient.Version.HTTP_1_1).build();
    private final AtomicBoolean nT = new AtomicBoolean(false);
    private final Set<UUID> nU = ConcurrentHashMap.newKeySet();
    private final Set<String> nV = ConcurrentHashMap.newKeySet();
    private final Map<UUID, String> nW = new ConcurrentHashMap<UUID, String>();
    private final Set<String> nX = ConcurrentHashMap.newKeySet();
    private volatile List<UserProfile> nY = List.of();
    private final Map<String, Long> nZ = new ConcurrentHashMap<String, Long>();
    private final Map<UUID, ServerSubscription> oa = new ConcurrentHashMap<UUID, ServerSubscription>();
    private final Map<String, ServerSubscription> ob = new ConcurrentHashMap<String, ServerSubscription>();
    private final Map<UUID, SyncResult> oc = new ConcurrentHashMap<UUID, SyncResult>();
    private final Map<String, SyncResult> od = new ConcurrentHashMap<String, SyncResult>();
    private final AtomicLong oe = new AtomicLong();
    private volatile boolean enabled = true;
    private volatile String nF = "http://91.92.33.75:8080/sync";
    private volatile String of = "";
    private volatile String og = null;
    private volatile long oh = 0L;
    private String oi = null;
    private long oj = 0L;
    private static final Map<String, String> ol = Map.of("artygrief.net", "artygrief.su");

    public void shutdown() {
        try {
            this.nS.shutdownNow();
        }
        catch (Throwable throwable) {
        }
    }

    public UserSyncManager() {
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
    }

    public void a(boolean bl, String string, String string2) {
        this.enabled = bl;
        this.nF = string == null ? "" : string.trim();
        this.of = string2 == null ? "" : string2.trim();
    }

    public boolean e(UUID uUID, String string) {
        if (!this.enabled) {
            return false;
        }
        if (uUID != null && this.nU.contains(uUID)) {
            return true;
        }
        return this.B(string);
    }

    public boolean B(String string) {
        if (!this.enabled || string == null || string.isBlank()) {
            return false;
        }
        return this.nV.contains(string.trim().toLowerCase(Locale.ROOT));
    }

    public int eH() {
        return this.enabled ? this.nV.size() : 0;
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (Module.fullNullCheck()) {
            return;
        }
        String string = this.eR();
        if (string == null) {
            if (this.oi != null) {
                this.eN();
                this.eO();
                this.eP();
                this.oi = null;
            }
            return;
        }
        if (!string.equals(this.oi)) {
            this.eN();
            this.eO();
            this.eP();
            this.oi = string;
        }
        if (!this.enabled || this.nF.isBlank()) {
            this.eO();
            return;
        }
        long l = System.currentTimeMillis();
        if (l < this.oh) {
            return;
        }
        this.oh = l + (this.eJ() ? 400L : 5000L);
        this.C(string);
    }

    private void C(String string) {
        HttpRequest httpRequest;
        if (!this.nT.compareAndSet(false, true)) {
            return;
        }
        if (UserSyncManager.aiG.field_1724 == null || UserSyncManager.aiG.field_1724.method_7334() == null) {
            this.nT.set(false);
            return;
        }
        UUID uUID = UserSyncManager.aiG.field_1724.method_7334().getId();
        String string2 = UserSyncManager.aiG.field_1724.method_7334().getName();
        if (uUID == null || string2 == null || string2.isBlank()) {
            this.nT.set(false);
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server", string);
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("uuid", uUID.toString());
        jsonObject2.addProperty("name", string2);
        jsonObject.add("user", (JsonElement)jsonObject2);
        this.l(jsonObject);
        if (!this.of.isBlank()) {
            jsonObject.addProperty("token", this.of);
        }
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(this.nF)).timeout(Duration.ofSeconds(4L)).header("Content-Type", "application/json");
            if (!this.of.isBlank()) {
                builder.header("X-Api-Key", this.of);
            }
            httpRequest = builder.POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).build();
        }
        catch (Exception exception) {
            this.nT.set(false);
            return;
        }
        long l = this.oe.get();
        this.nS.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).whenComplete((httpResponse, throwable) -> {
            this.nT.set(false);
            if (throwable != null || httpResponse == null || httpResponse.statusCode() != 200) {
                return;
            }
            if (!NativeProtectionStatus.ad((String)httpResponse.body(), httpResponse.headers().firstValue("X-Signature").orElse(null))) {
                return;
            }
            try {
                this.c((String)httpResponse.body(), l);
            }
            catch (Exception exception) {
                Celebression.LOGGER.warn("[Sync] Failed to apply /sync response", (Throwable)exception);
            }
        });
    }

    private void c(String string, long l) {
        JsonObject jsonObject;
        if (string == null || string.isBlank()) {
            return;
        }
        try {
            jsonObject = JsonParser.parseString((String)string).getAsJsonObject();
        }
        catch (Exception exception) {
            return;
        }
        this.m(jsonObject);
        if (this.oe.get() == l) {
            this.n(jsonObject);
        }
        this.k(jsonObject);
    }

    private void k(JsonObject jsonObject) {
        CosmeticsService cosmeticsService = Celebression.getInstance().getCosmeticsService();
        if (cosmeticsService == null) {
            return;
        }
        PlayerCosmeticProfileStore playerCosmeticProfileStore = cosmeticsService.bA();
        if (!jsonObject.has("users") || !jsonObject.get("users").isJsonArray()) {
            playerCosmeticProfileStore.aM();
            this.oa.clear();
            this.ob.clear();
            this.oc.clear();
            this.od.clear();
            return;
        }
        HashMap<UUID, CosmeticLoadout> hashMap = new HashMap<UUID, CosmeticLoadout>();
        HashMap<String, CosmeticLoadout> hashMap2 = new HashMap<String, CosmeticLoadout>();
        HashMap<UUID, ServerSubscription> hashMap3 = new HashMap<UUID, ServerSubscription>();
        HashMap<String, ServerSubscription> hashMap4 = new HashMap<String, ServerSubscription>();
        HashMap<UUID, SyncResult> hashMap5 = new HashMap<UUID, SyncResult>();
        HashMap<String, SyncResult> hashMap6 = new HashMap<String, SyncResult>();
        JsonArray jsonArray = jsonObject.getAsJsonArray("users");
        for (JsonElement jsonElement : jsonArray) {
            SyncResult syncResult;
            ServerSubscription serverSubscription;
            String string;
            if (!jsonElement.isJsonObject()) continue;
            JsonObject jsonObject2 = jsonElement.getAsJsonObject();
            String string2 = jsonObject2.has("uuid") ? jsonObject2.get("uuid").getAsString() : "";
            String string3 = jsonObject2.has("name") ? jsonObject2.get("name").getAsString() : "";
            UUID uUID = null;
            if (string2 != null && !string2.isBlank()) {
                try {
                    uUID = UUID.fromString(string2);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                }
            }
            String string4 = string = string3 == null || string3.isBlank() ? null : string3.trim().toLowerCase(Locale.ROOT);
            if (uUID == null && string == null || !jsonObject2.has("cosmetics") || !jsonObject2.get("cosmetics").isJsonObject()) continue;
            JsonObject jsonObject3 = jsonObject2.getAsJsonObject("cosmetics");
            CosmeticLoadout cosmeticLoadout = CosmeticProfileJson.b((JsonElement)jsonObject3);
            if (!cosmeticLoadout.isEmpty()) {
                if (uUID != null) {
                    hashMap.put(uUID, cosmeticLoadout);
                }
                if (string != null) {
                    hashMap2.put(string, cosmeticLoadout);
                }
            }
            if ((serverSubscription = ServerSubscription.e(jsonObject3.get("pet"))) != null) {
                if (uUID != null) {
                    hashMap3.put(uUID, serverSubscription);
                }
                if (string != null) {
                    hashMap4.put(string, serverSubscription);
                }
            }
            if ((syncResult = SyncResult.d(jsonObject3.get("halo"))) == null) continue;
            if (uUID != null) {
                hashMap5.put(uUID, syncResult);
            }
            if (string == null) continue;
            hashMap6.put(string, syncResult);
        }
        playerCosmeticProfileStore.b(hashMap, hashMap2);
        this.oa.putAll(hashMap3);
        this.oa.keySet().retainAll(hashMap3.keySet());
        this.ob.putAll(hashMap4);
        this.ob.keySet().retainAll(hashMap4.keySet());
        this.oc.putAll(hashMap5);
        this.oc.keySet().retainAll(hashMap5.keySet());
        this.od.putAll(hashMap6);
        this.od.keySet().retainAll(hashMap6.keySet());
    }

    public ServerSubscription f(UUID uUID, String string) {
        ServerSubscription serverSubscription;
        if (!this.enabled) {
            return null;
        }
        if (uUID != null && (serverSubscription = this.oa.get(uUID)) != null) {
            return serverSubscription;
        }
        if (string != null && !string.isBlank()) {
            return this.ob.get(string.trim().toLowerCase(Locale.ROOT));
        }
        return null;
    }

    public SyncResult g(UUID uUID, String string) {
        SyncResult syncResult;
        if (!this.enabled) {
            return null;
        }
        if (uUID != null && (syncResult = this.oc.get(uUID)) != null) {
            return syncResult;
        }
        if (string != null && !string.isBlank()) {
            return this.od.get(string.trim().toLowerCase(Locale.ROOT));
        }
        return null;
    }

    public void a(Consumer<Void> consumer, Consumer<String> consumer2) {
        String string = this.eR();
        if (!this.G(string)) {
            if (consumer2 != null) {
                consumer2.accept("not_ready");
            }
            return;
        }
        JsonObject jsonObject2 = this.F(string);
        this.l(jsonObject2);
        this.a("cosmetics/update", jsonObject2, jsonObject -> {
            if (consumer != null) {
                consumer.accept(null);
            }
        }, consumer2);
    }

    public void a(Consumer<List<SyncPayload>> consumer) {
        URI uRI;
        if (consumer == null) {
            return;
        }
        if (!this.enabled || this.nF.isBlank()) {
            consumer.accept(List.of());
            return;
        }
        try {
            uRI = this.J("pinned");
        }
        catch (Exception exception) {
            consumer.accept(List.of());
            return;
        }
        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(uRI).timeout(Duration.ofSeconds(4L)).GET();
        if (!this.of.isBlank()) {
            builder.header("X-Api-Key", this.of);
        }
        this.nS.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString()).whenComplete((httpResponse, throwable) -> {
            if (throwable != null || httpResponse == null || httpResponse.statusCode() != 200) {
                consumer.accept(List.of());
                return;
            }
            if (!NativeProtectionStatus.ad((String)httpResponse.body(), httpResponse.headers().firstValue("X-Signature").orElse(null))) {
                consumer.accept(List.of());
                return;
            }
            consumer.accept(UserSyncManager.D((String)httpResponse.body()));
        });
    }

    private static List<SyncPayload> D(String string) {
        ArrayList<SyncPayload> arrayList = new ArrayList<SyncPayload>();
        if (string == null || string.isBlank()) {
            return arrayList;
        }
        try {
            JsonObject jsonObject = JsonParser.parseString((String)string).getAsJsonObject();
            if (!jsonObject.has("servers") || !jsonObject.get("servers").isJsonArray()) {
                return arrayList;
            }
            for (JsonElement jsonElement : jsonObject.getAsJsonArray("servers")) {
                if (arrayList.size() < 20) {
                    JsonObject jsonObject2;
                    String string2;
                    if (!jsonElement.isJsonObject() || (string2 = (jsonObject2 = jsonElement.getAsJsonObject()).has("ip") ? jsonObject2.get("ip").getAsString() : "") == null || string2.isBlank()) continue;
                    if ((string2 = string2.trim()).length() > 128) {
                        string2 = string2.substring(0, 128);
                    }
                    String string3 = jsonObject2.has("name") ? jsonObject2.get("name").getAsString() : "";
                    String string4 = string3 = string3 == null || string3.isBlank() ? string2 : string3.trim();
                    if (string3.length() > 48) {
                        string3 = string3.substring(0, 48);
                    }
                    arrayList.add(new SyncPayload(string2, string3));
                    continue;
                }
                break;
            }
        }
        catch (Exception exception) {
        }
        return arrayList;
    }

    private void l(JsonObject jsonObject) {
        CosmeticsService cosmeticsService = Celebression.getInstance().getCosmeticsService();
        if (cosmeticsService == null) {
            return;
        }
        JsonObject jsonObject2 = cosmeticsService.bA().aN();
        CosmeticEffects cosmeticEffects = this.eI();
        if (cosmeticEffects != null) {
            JsonObject jsonObject3;
            JsonObject jsonObject4 = cosmeticEffects.petSyncPayload();
            if (jsonObject4 != null) {
                jsonObject2.add("pet", (JsonElement)jsonObject4);
            }
            if ((jsonObject3 = cosmeticEffects.haloSyncPayload()) != null) {
                jsonObject2.add("halo", (JsonElement)jsonObject3);
            }
        }
        jsonObject.add("cosmetics", (JsonElement)jsonObject2);
    }

    private CosmeticEffects eI() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return null;
        }
        return celebression.getModuleManager().getModule(CosmeticEffects.class);
    }

    private void m(JsonObject jsonObject) {
        if (!jsonObject.has("users")) {
            return;
        }
        JsonArray jsonArray = jsonObject.getAsJsonArray("users");
        HashSet<UUID> hashSet = new HashSet<UUID>();
        HashSet<String> hashSet2 = new HashSet<String>();
        for (JsonElement jsonElement : jsonArray) {
            String string;
            if (!jsonElement.isJsonObject()) continue;
            JsonObject jsonObject2 = jsonElement.getAsJsonObject();
            String string2 = jsonObject2.has("uuid") ? jsonObject2.get("uuid").getAsString() : "";
            String string3 = string = jsonObject2.has("name") ? jsonObject2.get("name").getAsString() : "";
            if (string2 == null || string2.isBlank() || string == null || string.isBlank()) continue;
            try {
                hashSet.add(UUID.fromString(string2));
            }
            catch (IllegalArgumentException illegalArgumentException) {
            }
            hashSet2.add(string.trim().toLowerCase(Locale.ROOT));
        }
        this.nU.addAll(hashSet);
        this.nU.retainAll(hashSet);
        this.nV.addAll(hashSet2);
        this.nV.retainAll(hashSet2);
    }

    public boolean eJ() {
        return this.og != null && !this.og.isBlank();
    }

    public boolean h(UUID uUID, String string) {
        if (!this.eJ()) {
            return false;
        }
        if (uUID != null && this.nW.containsKey(uUID)) {
            return true;
        }
        if (string != null && !string.isBlank()) {
            for (String string2 : this.nW.values()) {
                if (!string.equalsIgnoreCase(string2)) continue;
                return true;
            }
        }
        return false;
    }

    public List<String> eK() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string : this.nW.values()) {
            if (string == null || string.isBlank()) continue;
            arrayList.add(string);
        }
        arrayList.sort(String.CASE_INSENSITIVE_ORDER);
        return arrayList;
    }

    public boolean E(String string) {
        if (!this.eJ() || string == null || string.isBlank()) {
            return false;
        }
        for (String string2 : this.nW.values()) {
            if (string2 == null || string2.isBlank() || !string.contains(string2)) continue;
            return true;
        }
        return false;
    }

    public void b(Consumer<String> consumer, Consumer<String> consumer2) {
        String string = this.eR();
        if (!this.G(string)) {
            if (consumer2 != null) {
                consumer2.accept("not_ready");
            }
            return;
        }
        JsonObject jsonObject2 = this.F(string);
        this.a("party/create", jsonObject2, jsonObject -> {
            String partyCode = this.og;
            if (partyCode == null || partyCode.isBlank()) {
                if (consumer2 != null) {
                    consumer2.accept("bad_response");
                }
                return;
            }
            if (consumer != null) {
                consumer.accept(partyCode);
            }
        }, consumer2);
    }

    public void a(String string, Consumer<String> consumer, Consumer<String> consumer2) {
        String string2 = this.eR();
        String string3 = this.H(string);
        if (!this.G(string2) || string3.isBlank()) {
            if (consumer2 != null) {
                consumer2.accept("not_ready");
            }
            return;
        }
        JsonObject jsonObject2 = this.F(string2);
        jsonObject2.addProperty("code", string3);
        this.a("party/join", jsonObject2, jsonObject -> {
            String partyCode = this.og;
            if (partyCode == null || partyCode.isBlank()) {
                if (consumer2 != null) {
                    consumer2.accept("bad_response");
                }
                return;
            }
            if (consumer != null) {
                consumer.accept(partyCode);
            }
        }, consumer2);
    }

    public void c(Consumer<Void> consumer, Consumer<String> consumer2) {
        String string = this.eR();
        if (!this.G(string)) {
            if (consumer2 != null) {
                consumer2.accept("not_ready");
            }
            return;
        }
        if (!this.eJ()) {
            if (consumer2 != null) {
                consumer2.accept("not_in_party");
            }
            return;
        }
        JsonObject jsonObject2 = this.F(string);
        this.a("party/leave", jsonObject2, jsonObject -> {
            this.eO();
            if (consumer != null) {
                consumer.accept(null);
            }
        }, consumer2);
    }

    public void a(class_243 class_2432) {
        if (class_2432 == null) {
            return;
        }
        long l = System.currentTimeMillis();
        if (l - this.oj < 450L) {
            return;
        }
        this.oj = l;
        String string2 = this.eR();
        if (!this.G(string2) || !this.eJ()) {
            return;
        }
        JsonObject jsonObject2 = this.F(string2);
        jsonObject2.addProperty("code", this.H(this.og));
        JsonObject jsonObject3 = new JsonObject();
        jsonObject3.addProperty("x", (Number)class_2432.field_1352);
        jsonObject3.addProperty("y", (Number)class_2432.field_1351);
        jsonObject3.addProperty("z", (Number)class_2432.field_1350);
        String string3 = this.eM();
        String string4 = string3 == null ? "" : string3;
        jsonObject3.addProperty("dim", string4);
        jsonObject2.add("marker", (JsonElement)jsonObject3);
        if (UserSyncManager.aiG.field_1724 != null && UserSyncManager.aiG.field_1724.method_7334() != null) {
            String string5 = UserSyncManager.aiG.field_1724.method_7334().getName();
            UUID uUID = UserSyncManager.aiG.field_1724.method_7334().getId();
            String string6 = this.a(uUID, string5, class_2432.field_1352, class_2432.field_1351, class_2432.field_1350, string4);
            long l2 = this.nZ.computeIfAbsent(string6, string -> System.currentTimeMillis());
            ArrayList<UserProfile> arrayList = new ArrayList<UserProfile>(this.nY);
            arrayList.add(new UserProfile(string5, uUID, class_2432.field_1352, class_2432.field_1351, class_2432.field_1350, string4, l2));
            this.nY = List.copyOf(arrayList);
        }
        this.a("party/mark", jsonObject2, jsonObject -> {}, null);
    }

    private void a(String string, JsonObject jsonObject, Consumer<JsonObject> consumer, Consumer<String> consumer2) {
        URI uRI;
        try {
            uRI = this.J(string);
        }
        catch (Exception exception) {
            if (consumer2 != null) {
                consumer2.accept("bad_endpoint");
            }
            return;
        }
        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(uRI).timeout(Duration.ofSeconds(4L)).header("Content-Type", "application/json");
        if (!this.of.isBlank()) {
            builder.header("X-Api-Key", this.of);
        }
        HttpRequest httpRequest = builder.POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).build();
        boolean bl = string.startsWith("party/");
        if (bl) {
            this.oe.incrementAndGet();
        }
        long l = this.oe.get();
        this.nS.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).whenComplete((httpResponse, throwable) -> {
            JsonObject responseJson;
            if (throwable != null || httpResponse == null) {
                if (consumer2 != null) {
                    consumer2.accept("request_failed");
                }
                return;
            }
            if (httpResponse.statusCode() != 200) {
                if (consumer2 != null) {
                    consumer2.accept(UserSyncManager.K((String)httpResponse.body()));
                }
                return;
            }
            if (!NativeProtectionStatus.ad((String)httpResponse.body(), httpResponse.headers().firstValue("X-Signature").orElse(null))) {
                if (consumer2 != null) {
                    consumer2.accept("bad_response");
                }
                return;
            }
            try {
                responseJson = JsonParser.parseString((String)httpResponse.body()).getAsJsonObject();
            }
            catch (Exception exception) {
                if (consumer2 != null) {
                    consumer2.accept("bad_response");
                }
                return;
            }
            try {
                if (bl) {
                    this.oh = 0L;
                    this.n(responseJson);
                } else if (this.oe.get() == l) {
                    this.n(responseJson);
                }
                this.m(jsonObject);
                this.k(jsonObject);
            }
            catch (Exception exception) {
                Celebression.LOGGER.warn("[Sync] Failed to apply party response", (Throwable)exception);
                if (consumer2 != null) {
                    consumer2.accept("bad_response");
                }
                return;
            }
            if (consumer != null) {
                consumer.accept(jsonObject);
            }
        });
    }

    private JsonObject F(String string) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server", string);
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("uuid", UserSyncManager.aiG.field_1724.method_7334().getId().toString());
        jsonObject2.addProperty("name", UserSyncManager.aiG.field_1724.method_7334().getName());
        jsonObject.add("user", (JsonElement)jsonObject2);
        if (!this.of.isBlank()) {
            jsonObject.addProperty("token", this.of);
        }
        return jsonObject;
    }

    private boolean G(String string) {
        return this.enabled && !this.nF.isBlank() && string != null && UserSyncManager.aiG.field_1724 != null && UserSyncManager.aiG.field_1724.method_7334() != null;
    }

    private String H(String string) {
        return string == null ? "" : string.trim().toUpperCase(Locale.ROOT);
    }

    private static boolean I(String string) {
        if (string == null) {
            return false;
        }
        int n = string.length();
        if (n < 1 || n > 16) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            boolean bl;
            char c = string.charAt(i);
            boolean bl2 = bl = c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '_';
            if (bl) continue;
            return false;
        }
        return true;
    }

    private URI J(String string) {
        URI uRI = URI.create(this.nF).resolve(".");
        String string2 = string.startsWith("/") ? string.substring(1) : string;
        return uRI.resolve(string2);
    }

    private static String K(String string) {
        if (string == null || string.isBlank()) {
            return "server_error";
        }
        try {
            JsonObject jsonObject = JsonParser.parseString((String)string).getAsJsonObject();
            if (jsonObject.has("error")) {
                String string2 = jsonObject.get("error").getAsString();
                return switch (string2 == null ? "" : string2) {
                    case "party_not_found", "missing_code", "forbidden", "not_in_party", "missing_fields", "bad_json" -> string2;
                    default -> "server_error";
                };
            }
        }
        catch (Exception exception) {
        }
        return "server_error";
    }

    private static UUID eL() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null || class_3102.field_1724 == null || class_3102.field_1724.method_7334() == null) {
            return null;
        }
        return class_3102.field_1724.method_7334().getId();
    }

    private void f(String string, String string2) {
        if (string2 == null || string2.isBlank()) {
            return;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return;
        }
        class_3102.execute(() -> ChatOutput.cG(class_1074.method_4662((String)string, (Object[])new Object[]{string2})));
    }

    private void n(JsonObject jsonObject) {
        if (!jsonObject.has("party") || !jsonObject.get("party").isJsonObject()) {
            this.eO();
            return;
        }
        JsonObject party = jsonObject.getAsJsonObject("party");
        String partyCode = party.has("code") ? party.get("code").getAsString() : "";
        if (partyCode == null || partyCode.isBlank()) {
            this.eO();
            return;
        }
        this.og = partyCode;

        Map<UUID, String> members = new HashMap<>();
        if (party.has("members") && party.get("members").isJsonArray()) {
            for (JsonElement entry : party.getAsJsonArray("members")) {
                if (members.size() >= 64) break;
                if (!entry.isJsonObject()) continue;
                JsonObject member = entry.getAsJsonObject();
                String uuidText = member.has("uuid") ? member.get("uuid").getAsString() : "";
                String name = member.has("name") ? member.get("name").getAsString() : "";
                if (uuidText == null || uuidText.isBlank() || !UserSyncManager.I(name)) continue;
                try {
                    members.put(UUID.fromString(uuidText), name);
                }
                catch (IllegalArgumentException ignored) {
                }
            }
        }

        if (!this.nW.isEmpty()) {
            UUID localPlayerId = UserSyncManager.eL();
            for (Map.Entry<UUID, String> entry : members.entrySet()) {
                if (this.nW.containsKey(entry.getKey()) || entry.getKey().equals(localPlayerId)) continue;
                this.f("commands.party.member_joined", entry.getValue());
            }
            for (Map.Entry<UUID, String> entry : this.nW.entrySet()) {
                if (members.containsKey(entry.getKey()) || entry.getKey().equals(localPlayerId)) continue;
                this.f("commands.party.member_left", entry.getValue());
            }
        }
        this.nW.putAll(members);
        this.nW.keySet().retainAll(members.keySet());
        this.eQ();

        List<UserProfile> markers = new ArrayList<>();
        Set<String> markerKeys = new HashSet<>();
        long l = System.currentTimeMillis();
        if (party.has("markers") && party.get("markers").isJsonArray()) {
            for (JsonElement jsonElement : party.getAsJsonArray("markers")) {
                if (!jsonElement.isJsonObject()) continue;
                JsonObject marker = jsonElement.getAsJsonObject();
                double x = marker.has("x") ? marker.get("x").getAsDouble() : 0.0;
                double y = marker.has("y") ? marker.get("y").getAsDouble() : 0.0;
                double z = marker.has("z") ? marker.get("z").getAsDouble() : 0.0;
                String ownerName = marker.has("owner") ? marker.get("owner").getAsString() : "";
                String dimension = marker.has("dim") ? marker.get("dim").getAsString() : "";
                UUID ownerId = null;
                if (marker.has("owner_uuid")) {
                    try {
                        ownerId = UUID.fromString(marker.get("owner_uuid").getAsString());
                    }
                    catch (IllegalArgumentException ignored) {
                    }
                }
                if ((ownerName == null || ownerName.isBlank()) && ownerId != null) {
                    ownerName = this.nW.getOrDefault(ownerId, "");
                }
                String markerKey = this.a(ownerId, ownerName, x, y, z, dimension);
                markerKeys.add(markerKey);
                long createdAt = this.nZ.computeIfAbsent(markerKey, key -> l);
                markers.add(new UserProfile(ownerName, ownerId, x, y, z, dimension, createdAt));
            }
        }
        if (markerKeys.isEmpty()) {
            this.nZ.clear();
        } else {
            this.nZ.keySet().removeIf(key -> !markerKeys.contains(key));
        }
        if (markers.size() > 2) {
            markers = new ArrayList<>(markers.subList(markers.size() - 2, markers.size()));
        }
        this.nY = List.copyOf(markers);
    }

    @EventHandler
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        if (Module.fullNullCheck()) {
            return;
        }
        if (!this.eJ() || this.nY.isEmpty()) {
            return;
        }
        String string = this.eM();
        if (string == null) {
            return;
        }
        long l = System.currentTimeMillis();
        long l2 = this.eS();
        for (UserProfile userProfile : this.nY) {
            if (userProfile == null || l2 > 0L && l - userProfile.ot > l2 || !userProfile.os.isBlank() && !userProfile.os.equals(string)) continue;
            this.a(hudRenderEvent, userProfile);
        }
    }

    private void a(HudRenderEvent hudRenderEvent, UserProfile userProfile) {
        Vector3f vector3f;
        class_243 class_2432;
        class_243 class_2433 = new class_243(userProfile.oo, userProfile.oq, userProfile.or);
        class_243 class_2434 = UserSyncManager.aiG.field_1773.method_19418().method_19326();
        class_243 class_2435 = class_2433.method_1020(class_2434).method_1029();
        double d = class_2435.method_1026(class_2432 = new class_243(vector3f = UserSyncManager.aiG.field_1773.method_19418().method_19335()));
        if (d <= 0.05) {
            return;
        }
        class_243 class_2436 = ProjectionUtils.o(class_2433);
        if (class_2436.field_1350 <= 0.0) {
            return;
        }
        if (class_2436.method_10216() < -100.0 || class_2436.method_10216() > (double)(aiG.method_22683().method_4486() + 100)) {
            return;
        }
        if (class_2436.method_10214() < -100.0 || class_2436.method_10214() > (double)(aiG.method_22683().method_4502() + 100)) {
            return;
        }
        double d2 = UserSyncManager.aiG.field_1724.method_19538().method_1022(class_2433);
        float f = Math.max(0.4f, 1.0f - (float)(d2 / 2500.0));
        String string = userProfile.om == null || userProfile.om.isBlank() ? "Unknown" : userProfile.om;
        String string2 = Math.round(d2) + "m";
        float f2 = (float)class_2436.method_10216();
        float f3 = (float)class_2436.method_10214();
        Color color = ColorUtils.getGlobalColor();
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(255.0f * f));
        float f4 = 32.0f;
        try (DepthRenderScope depthRenderScope = DepthRenderScope.lequal();){
            hudRenderEvent.j().method_51448().method_22903();
            hudRenderEvent.j().method_51448().method_46416(f2, f3, -100.0f);
            Render2D.drawTexture(hudRenderEvent.j().method_51448(), -f4 / 2.0f, -f4 / 2.0f, f4, f4, 0.0f, Celebression.id("client/waypoint/way.png"), color2);
            hudRenderEvent.j().method_51448().method_22909();
        }
        float f5 = Fonts.ROUNDBOLD.getWidth(string, 8.0f);
        float f6 = Fonts.ROUNDBOLD.getWidth(string2, 7.0f);
        float f7 = Math.max(f5, f6) + 12.0f;
        float f8 = 26.0f;
        float f9 = f2 - f7 / 2.0f;
        float f10 = f3 + 16.0f;
        float[] fArray = this.c(f9, f10, f7, f8);
        f9 = fArray[0];
        f10 = fArray[1];
        Color color3 = new Color(0, 0, 0, (int)(150.0f * f));
        Render2D.drawRoundedRect(hudRenderEvent.j().method_51448(), f9, f10, f7, f8, 3.0f, color3);
        Color color4 = new Color(255, 255, 255, (int)(255.0f * f));
        Color color5 = new Color(200, 200, 200, (int)(220.0f * f));
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(8.0f), string, f9 + (f7 - f5) / 2.0f, f10 + 5.0f, color4);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(7.0f), string2, f9 + (f7 - f6) / 2.0f, f10 + 16.0f, color5);
    }

    private float[] c(float f, float f2, float f3, float f4) {
        float f5 = aiG.method_22683().method_4486();
        float f6 = aiG.method_22683().method_4502();
        float f7 = 4.0f;
        if (UserSyncManager.aiG.field_1687 != null && UserSyncManager.aiG.field_1687.method_8428() != null && UserSyncManager.aiG.field_1687.method_8428().method_1189(class_8646.field_45157) != null) {
            f7 = 150.0f;
        }
        float f8 = 4.0f;
        float f9 = Math.max(f8, f5 - f7 - f3);
        float f10 = 24.0f;
        float f11 = Math.max(f10, f6 - 48.0f - f4);
        float f12 = Math.max(f8, Math.min(f, f9));
        float f13 = Math.max(f10, Math.min(f2, f11));
        return new float[]{f12, f13};
    }

    private String eM() {
        if (UserSyncManager.aiG.field_1687 == null || UserSyncManager.aiG.field_1687.method_27983() == null) {
            return null;
        }
        return UserSyncManager.aiG.field_1687.method_27983().method_29177().toString();
    }

    private void eN() {
        this.nU.clear();
        this.nV.clear();
    }

    private void eO() {
        this.og = null;
        this.nW.clear();
        FriendManager friendManager = Celebression.getInstance().getFriendManager();
        if (friendManager != null) {
            for (String string : this.nX) {
                friendManager.bu(string);
            }
        }
        this.nX.clear();
        this.nY = List.of();
        this.nZ.clear();
    }

    private void eP() {
        CosmeticsService cosmeticsService = Celebression.getInstance().getCosmeticsService();
        if (cosmeticsService != null) {
            cosmeticsService.aM();
        }
        this.oa.clear();
        this.ob.clear();
        this.oc.clear();
        this.od.clear();
    }

    private void eQ() {
        FriendManager friendManager = Celebression.getInstance().getFriendManager();
        if (friendManager == null) {
            return;
        }
        String string2 = null;
        if (UserSyncManager.aiG.field_1724 != null && UserSyncManager.aiG.field_1724.method_7334() != null) {
            string2 = UserSyncManager.aiG.field_1724.method_7334().getName();
        }
        HashSet<String> hashSet = new HashSet<String>();
        for (String string3 : this.nW.values()) {
            if (string3 == null || string3.isBlank() || string2 != null && string2.equalsIgnoreCase(string3)) continue;
            hashSet.add(string3);
            if (friendManager.bv(string3)) continue;
            friendManager.bt(string3);
            this.nX.add(string3);
        }
        this.nX.removeIf(string -> {
            if (hashSet.contains(string)) {
                return false;
            }
            friendManager.bu((String)string);
            return true;
        });
    }

    private String eR() {
        class_642 class_6422 = aiG.method_1558();
        if (class_6422 == null || class_6422.field_3761 == null) {
            return null;
        }
        String string = class_6422.field_3761.trim().toLowerCase(Locale.ROOT);
        if (string.isEmpty()) {
            return null;
        }
        String string2 = UserSyncManager.L(string);
        return ol.getOrDefault(string2, string2);
    }

    private static String L(String string) {
        int n;
        int n2 = string.indexOf(58);
        if (n2 > 0 && n2 == string.lastIndexOf(58)) {
            string = string.substring(0, n2);
        }
        int n3 = string.length();
        for (n = 0; n < n3 && string.charAt(n) == '.'; ++n) {
        }
        while (n3 > n && string.charAt(n3 - 1) == '.') {
            --n3;
        }
        if ((string = string.substring(n, n3)).isEmpty()) {
            return string;
        }
        if (UserSyncManager.M(string)) {
            return string;
        }
        String[] stringArray = string.split("\\.");
        if (stringArray.length < 2) {
            return string;
        }
        return stringArray[stringArray.length - 2] + "." + stringArray[stringArray.length - 1];
    }

    private static boolean M(String string) {
        if (string.indexOf(58) >= 0) {
            return true;
        }
        boolean bl = false;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '.') continue;
            if (c < '0' || c > '9') {
                return false;
            }
            bl = true;
        }
        return bl;
    }

    private long eS() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return 60000L;
        }
        PartyModule partyModule = celebression.getModuleManager().getModule(PartyModule.class);
        if (partyModule == null) {
            return 60000L;
        }
        return Math.max(0L, partyModule.hw());
    }

    private String a(UUID uUID, String string, double d, double d2, double d3, String string2) {
        String string3 = uUID != null ? uUID.toString() : (string == null ? "" : string.trim().toLowerCase(Locale.ROOT));
        String string4 = string2 == null ? "" : string2;
        return string3 + "|" + string4 + "|" + Double.doubleToLongBits(d) + ":" + Double.doubleToLongBits(d2) + ":" + Double.doubleToLongBits(d3);
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String eB() {
        return this.nF;
    }

    @Generated
    public String eT() {
        return this.of;
    }

    @Generated
    public String eU() {
        return this.og;
    }

    private static boolean b(Set set, String string) {
        return !set.contains(string);
    }

    public static final class ServerSubscription {
        public final String oB;
        public final float oC;
        public final float oD;
        public final float speed;
        public final float oE;

        private ServerSubscription(String string, float f, float f2, float f3, float f4) {
            this.oB = string;
            this.oC = f;
            this.oD = f2;
            this.speed = f3;
            this.oE = f4;
        }

        static ServerSubscription e(JsonElement jsonElement) {
            String string;
            if (jsonElement == null || !jsonElement.isJsonObject()) {
                return null;
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String string2 = string = jsonObject.has("skin") ? jsonObject.get("skin").getAsString() : "";
            if (string == null || string.isBlank()) {
                return null;
            }
            return new ServerSubscription(string.trim().toUpperCase(Locale.ROOT), ServerSubscription.a(jsonObject, "radius", 0.65f, 0.15f, 2.5f), ServerSubscription.a(jsonObject, "height", 0.95f, -0.5f, 3.0f), ServerSubscription.a(jsonObject, "speed", 1.15f, 0.05f, 5.0f), ServerSubscription.a(jsonObject, "scale", 0.55f, 0.15f, 2.0f));
        }

        static float a(JsonObject jsonObject, String string, float f, float f2, float f3) {
            float f4 = f;
            if (jsonObject.has(string)) {
                try {
                    f4 = jsonObject.get(string).getAsFloat();
                }
                catch (Exception exception) {
                }
            }
            return Math.max(f2, Math.min(f3, f4));
        }
    }

    public static final class SyncResult {
        public final int ow;
        public final float ox;
        public final float oy;
        public final float oz;
        public final boolean oA;

        private SyncResult(int n, float f, float f2, float f3, boolean bl) {
            this.ow = n;
            this.ox = f;
            this.oy = f2;
            this.oz = f3;
            this.oA = bl;
        }

        static SyncResult d(JsonElement jsonElement) {
            if (jsonElement == null || !jsonElement.isJsonObject()) {
                return null;
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int n = -1;
            if (jsonObject.has("color")) {
                try {
                    n = jsonObject.get("color").getAsInt();
                }
                catch (Exception exception) {
                }
            }
            boolean bl = true;
            if (jsonObject.has("animate")) {
                try {
                    bl = jsonObject.get("animate").getAsBoolean();
                }
                catch (Exception exception) {
                }
            }
            return new SyncResult(n, ServerSubscription.a(jsonObject, "radius", 0.36f, 0.18f, 0.9f), ServerSubscription.a(jsonObject, "thickness", 0.06f, 0.02f, 0.16f), ServerSubscription.a(jsonObject, "y_offset", 0.1f, -0.6f, 0.6f), bl);
        }
    }

    public static final class SyncPayload {
        private final String ou;
        private final String ov;

        public SyncPayload(String string, String string2) {
            this.ou = string;
            this.ov = string2;
        }

        @Override
        public final String toString() {
            return "SyncPayload[" + "ip=" + this.ou + ", " + "name=" + this.ov + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.ou, this.ov);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof SyncPayload other)) return false;
            return java.util.Objects.deepEquals(this.ou, other.ou)
                && java.util.Objects.deepEquals(this.ov, other.ov);
        }

        public String eV() {
            return this.ou;
        }

        public String name() {
            return this.ov;
        }
    }

    static final class UserProfile {
        final String om;
        final UUID on;
        final double oo;
        final double oq;
        final double or;
        final String os;
        final long ot;

        UserProfile(String string, UUID uUID, double d, double d2, double d3, String string2, long l) {
            this.om = string == null ? "" : string;
            this.on = uUID;
            this.oo = d;
            this.oq = d2;
            this.or = d3;
            this.os = string2 == null ? "" : string2;
            this.ot = l;
        }
    }
}
