package ru.method.celebression.modules.impl.render.cosmetic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLoadout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticProfileJson;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;

public class PlayerCosmeticProfileStore {
    private volatile CosmeticLoadout dD = new CosmeticLoadout().b(CosmeticSlot.dr, "celebression_signature");
    private final Map<UUID, CosmeticLoadout> dE = new ConcurrentHashMap<UUID, CosmeticLoadout>();
    private final Map<String, CosmeticLoadout> dF = new ConcurrentHashMap<String, CosmeticLoadout>();

    public CosmeticLoadout aL() {
        return this.dD;
    }

    public void d(CosmeticLoadout cosmeticLoadout) {
        if (cosmeticLoadout == null) {
            this.dD = new CosmeticLoadout();
            return;
        }
        this.dD = new CosmeticLoadout(CosmeticProfileJson.b(cosmeticLoadout), CosmeticProfileJson.c(cosmeticLoadout));
    }

    public CosmeticLoadout a(UUID uUID) {
        return this.a(uUID, (String)null);
    }

    public CosmeticLoadout a(UUID uUID, String string) {
        CosmeticLoadout cosmeticLoadout;
        if (uUID != null && (cosmeticLoadout = this.dE.get(uUID)) != null) {
            return cosmeticLoadout;
        }
        if (string != null && !string.isBlank() && (cosmeticLoadout = this.dF.get(string.trim().toLowerCase(Locale.ROOT))) != null) {
            return cosmeticLoadout;
        }
        return new CosmeticLoadout();
    }

    public void a(UUID uUID, CosmeticLoadout cosmeticLoadout) {
        if (uUID == null || cosmeticLoadout == null || cosmeticLoadout.isEmpty()) {
            if (uUID != null) {
                this.dE.remove(uUID);
            }
            return;
        }
        this.dE.put(uUID, new CosmeticLoadout(CosmeticProfileJson.b(cosmeticLoadout), CosmeticProfileJson.c(cosmeticLoadout)));
    }

    public void b(Map<UUID, CosmeticLoadout> map, Map<String, CosmeticLoadout> map2) {
        if (map != null) {
            for (Map.Entry<UUID, CosmeticLoadout> object : map.entrySet()) {
                this.a(object.getKey(), object.getValue());
            }
            this.dE.keySet().retainAll(map.keySet());
        } else {
            this.dE.clear();
        }
        if (map2 != null) {
            HashSet hashSet = new HashSet();
            for (Map.Entry<String, CosmeticLoadout> entry : map2.entrySet()) {
                String string = entry.getKey();
                CosmeticLoadout cosmeticLoadout = entry.getValue();
                if (string == null || string.isBlank() || cosmeticLoadout == null || cosmeticLoadout.isEmpty()) continue;
                String string2 = string.trim().toLowerCase(Locale.ROOT);
                this.dF.put(string2, new CosmeticLoadout(CosmeticProfileJson.b(cosmeticLoadout), CosmeticProfileJson.c(cosmeticLoadout)));
                hashSet.add(string2);
            }
            this.dF.keySet().retainAll(hashSet);
        } else {
            this.dF.clear();
        }
    }

    public void aM() {
        this.dE.clear();
        this.dF.clear();
    }

    public JsonObject aN() {
        return CosmeticProfileJson.a(this.dD);
    }

    public void c(JsonElement jsonElement) {
        this.d(CosmeticProfileJson.b(jsonElement));
    }
}

