package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;

public final class CosmeticLoadout {
    public static final int DEFAULT_COLOR = -1;
    private final Map<CosmeticSlot, String> dp = new EnumMap<CosmeticSlot, String>(CosmeticSlot.class);
    private final Map<CosmeticSlot, Integer> dq = new EnumMap<CosmeticSlot, Integer>(CosmeticSlot.class);

    public CosmeticLoadout() {
        this(new EnumMap<CosmeticSlot, String>(CosmeticSlot.class), new EnumMap<CosmeticSlot, Integer>(CosmeticSlot.class));
    }

    public CosmeticLoadout(Map<CosmeticSlot, String> map) {
        this(map, new EnumMap<CosmeticSlot, Integer>(CosmeticSlot.class));
    }

    public CosmeticLoadout(Map<CosmeticSlot, String> map, Map<CosmeticSlot, Integer> map2) {
        for (CosmeticSlot cosmeticSlot : CosmeticSlot.values()) {
            String string = map == null ? null : map.get((Object)cosmeticSlot);
            String string2 = cosmeticSlot == CosmeticSlot.dr && (string == null || string.isBlank()) ? "celebression_signature" : CosmeticLoadout.normalize(string);
            this.dp.put(cosmeticSlot, string2);
            Integer n = map2 == null ? null : map2.get((Object)cosmeticSlot);
            this.dq.put(cosmeticSlot, n == null ? -1 : n);
        }
    }

    public static CosmeticLoadout a(CosmeticSlot cosmeticSlot, String string) {
        EnumMap<CosmeticSlot, String> enumMap = new EnumMap<CosmeticSlot, String>(CosmeticSlot.class);
        EnumMap<CosmeticSlot, Integer> enumMap2 = new EnumMap<CosmeticSlot, Integer>(CosmeticSlot.class);
        for (CosmeticSlot cosmeticSlot2 : CosmeticSlot.values()) {
            enumMap.put(cosmeticSlot2, "none");
            enumMap2.put(cosmeticSlot2, -1);
        }
        if (cosmeticSlot != null) {
            enumMap.put(cosmeticSlot, CosmeticLoadout.normalize(string));
        }
        return new CosmeticLoadout(enumMap, enumMap2);
    }

    public String f(CosmeticSlot cosmeticSlot) {
        return this.dp.getOrDefault((Object)cosmeticSlot, "none");
    }

    public int g(CosmeticSlot cosmeticSlot) {
        return this.dq.getOrDefault((Object)cosmeticSlot, -1);
    }

    public CosmeticLoadout b(CosmeticSlot cosmeticSlot, String string) {
        EnumMap<CosmeticSlot, String> enumMap = new EnumMap<CosmeticSlot, String>(this.dp);
        enumMap.put(cosmeticSlot, CosmeticLoadout.normalize(string));
        return new CosmeticLoadout(enumMap, this.dq);
    }

    public CosmeticLoadout a(CosmeticSlot cosmeticSlot, int n) {
        EnumMap<CosmeticSlot, Integer> enumMap = new EnumMap<CosmeticSlot, Integer>(this.dq);
        enumMap.put(cosmeticSlot, n);
        return new CosmeticLoadout(this.dp, enumMap);
    }

    public Map<CosmeticSlot, String> aH() {
        return Map.copyOf(this.dp);
    }

    public Map<CosmeticSlot, Integer> aI() {
        return Map.copyOf(this.dq);
    }

    public boolean isEmpty() {
        for (CosmeticSlot cosmeticSlot : CosmeticSlot.values()) {
            if ("none".equals(this.f(cosmeticSlot))) continue;
            return false;
        }
        return true;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CosmeticLoadout)) {
            return false;
        }
        CosmeticLoadout cosmeticLoadout = (CosmeticLoadout)object;
        return Objects.equals(this.dp, cosmeticLoadout.dp) && Objects.equals(this.dq, cosmeticLoadout.dq);
    }

    public int hashCode() {
        return Objects.hash(this.dp, this.dq);
    }

    private static String normalize(String string) {
        if (string == null || string.isBlank()) {
            return "none";
        }
        return string.trim().toLowerCase();
    }
}
