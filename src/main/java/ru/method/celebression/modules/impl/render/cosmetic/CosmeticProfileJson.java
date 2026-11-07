package ru.method.celebression.modules.impl.render.cosmetic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticCatalog;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticLoadout;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;

public final class CosmeticProfileJson {
    private CosmeticProfileJson() {
    }

    public static JsonObject a(CosmeticLoadout cosmeticLoadout) {
        JsonObject jsonObject = new JsonObject();
        if (cosmeticLoadout == null) {
            return jsonObject;
        }
        for (CosmeticSlot cosmeticSlot : CosmeticSlot.values()) {
            String string = cosmeticLoadout.f(cosmeticSlot);
            if (string != null && !"none".equals(string)) {
                jsonObject.addProperty(CosmeticProfileJson.h(cosmeticSlot), string);
            }
            int n = cosmeticLoadout.g(cosmeticSlot);
            if (cosmeticSlot == CosmeticSlot.dt && n != -1) {
                jsonObject.addProperty("wings_color", (Number)n);
            }
            if (cosmeticSlot != CosmeticSlot.du || n == -1) continue;
            jsonObject.addProperty("effect_color", (Number)n);
        }
        return jsonObject;
    }

    public static CosmeticLoadout b(JsonElement jsonElement) {
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            return new CosmeticLoadout();
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        EnumMap<CosmeticSlot, String> enumMap = new EnumMap<CosmeticSlot, String>(CosmeticSlot.class);
        EnumMap<CosmeticSlot, Integer> enumMap2 = new EnumMap<CosmeticSlot, Integer>(CosmeticSlot.class);
        for (CosmeticSlot cosmeticSlot : CosmeticSlot.values()) {
            enumMap2.put(cosmeticSlot, -1);
        }
        if (jsonObject.has("wings_color")) {
            try {
                enumMap2.put(CosmeticSlot.dt, jsonObject.get("wings_color").getAsInt());
            }
            catch (Exception exception) {
            }
        }
        if (jsonObject.has("effect_color")) {
            try {
                enumMap2.put(CosmeticSlot.du, jsonObject.get("effect_color").getAsInt());
            }
            catch (Exception exception) {
            }
        }
        for (CosmeticSlot cosmeticSlot : CosmeticSlot.values()) {
            String string;
            String string2 = CosmeticProfileJson.h(cosmeticSlot);
            if (!jsonObject.has(string2) || !CosmeticCatalog.b(string = jsonObject.get(string2).getAsString(), cosmeticSlot)) continue;
            enumMap.put(cosmeticSlot, string.trim().toLowerCase(Locale.ROOT));
        }
        return new CosmeticLoadout(enumMap, enumMap2);
    }

    public static Map<CosmeticSlot, String> b(CosmeticLoadout cosmeticLoadout) {
        EnumMap<CosmeticSlot, String> enumMap = new EnumMap<CosmeticSlot, String>(CosmeticSlot.class);
        for (CosmeticSlot cosmeticSlot : CosmeticSlot.values()) {
            String string = cosmeticLoadout == null ? "none" : cosmeticLoadout.f(cosmeticSlot);
            enumMap.put(cosmeticSlot, CosmeticCatalog.b(string, cosmeticSlot) ? string : "none");
        }
        return enumMap;
    }

    public static Map<CosmeticSlot, Integer> c(CosmeticLoadout cosmeticLoadout) {
        EnumMap<CosmeticSlot, Integer> enumMap = new EnumMap<CosmeticSlot, Integer>(CosmeticSlot.class);
        for (CosmeticSlot cosmeticSlot : CosmeticSlot.values()) {
            int n = cosmeticLoadout == null ? -1 : cosmeticLoadout.g(cosmeticSlot);
            enumMap.put(cosmeticSlot, n);
        }
        return enumMap;
    }

    private static String h(CosmeticSlot cosmeticSlot) {
        return switch (cosmeticSlot) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case CosmeticSlot.dr -> "cape";
            case CosmeticSlot.ds -> "hat";
            case CosmeticSlot.dt -> "wings";
            case CosmeticSlot.du -> "effect";
        };
    }
}

