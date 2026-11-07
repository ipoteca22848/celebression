package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticDefinition;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffectType;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelAnchor;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticSlot;

public final class CosmeticCatalog {
    private static final Map<String, CosmeticDefinition> cf = new LinkedHashMap<String, CosmeticDefinition>();
    private static final Map<CosmeticSlot, List<CosmeticDefinition>> cg = new EnumMap<CosmeticSlot, List<CosmeticDefinition>>(CosmeticSlot.class);

    private CosmeticCatalog() {
    }

    private static void a(CosmeticDefinition cosmeticDefinition) {
        cf.put(cosmeticDefinition.aA(), cosmeticDefinition);
        cg.computeIfAbsent(cosmeticDefinition.aB(), cosmeticSlot -> new ArrayList()).add(cosmeticDefinition);
    }

    public static Optional<CosmeticDefinition> ag(String string) {
        if (string == null || string.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(cf.get(string.trim().toLowerCase(Locale.ROOT)));
    }

    public static CosmeticDefinition a(String string, CosmeticSlot cosmeticSlot) {
        return CosmeticCatalog.ag(string).filter(cosmeticDefinition -> cosmeticDefinition.aB() == cosmeticSlot).orElseGet(() -> CosmeticCatalog.a(cosmeticSlot));
    }

    public static CosmeticDefinition a(CosmeticSlot cosmeticSlot) {
        List<CosmeticDefinition> list = cg.get((Object)cosmeticSlot);
        return list == null || list.isEmpty() ? CosmeticDefinition.a(cosmeticSlot) : list.getFirst();
    }

    public static List<CosmeticDefinition> b(CosmeticSlot cosmeticSlot) {
        List<CosmeticDefinition> list = cg.get((Object)cosmeticSlot);
        return list == null ? List.of(CosmeticCatalog.a(cosmeticSlot)) : Collections.unmodifiableList(list);
    }

    public static boolean b(String string, CosmeticSlot cosmeticSlot) {
        if (string == null || string.isBlank() || "none".equals(string.trim().toLowerCase(Locale.ROOT))) {
            return true;
        }
        if (cosmeticSlot == CosmeticSlot.dr && string.startsWith("laby:")) {
            return true;
        }
        return CosmeticCatalog.ag(string).map(cosmeticDefinition -> cosmeticDefinition.aB() == cosmeticSlot).orElse(false);
    }

    static {
        for (CosmeticSlot cosmeticSlot2 : CosmeticSlot.values()) {
            cg.computeIfAbsent(cosmeticSlot2, cosmeticSlot -> new ArrayList()).add(CosmeticDefinition.a(cosmeticSlot2));
        }
        CosmeticCatalog.a(CosmeticDefinition.a("celebression_signature", "cosmetics.cape.celebression", "cosmetics/capes/celebression_signature.png"));
        CosmeticCatalog.a(CosmeticDefinition.a("hat_crown", CosmeticSlot.ds, "cosmetics.hat.crown", "cosmetics/icons/crown.png", "cosmetics/models/hat_crown.json", CosmeticModelAnchor.cY));
        CosmeticCatalog.a(CosmeticDefinition.a("hat_horns", CosmeticSlot.ds, "cosmetics.hat.horns", "cosmetics/icons/horns.png", "cosmetics/models/hat_horns.json", CosmeticModelAnchor.cY));
        CosmeticCatalog.a(CosmeticDefinition.a("hat_cap", CosmeticSlot.ds, "cosmetics.hat.cap", "cosmetics/icons/cap.png", "cosmetics/models/hat_cap.json", CosmeticModelAnchor.cY));
        CosmeticCatalog.a(CosmeticDefinition.a("wings_angel", CosmeticSlot.dt, "cosmetics.wings.angel", "cosmetics/icons/angel.png", "cosmetics/models/wings_angel.json", CosmeticModelAnchor.cZ));
        CosmeticCatalog.a(CosmeticDefinition.a("wings_demon", CosmeticSlot.dt, "cosmetics.wings.demon", "cosmetics/icons/demon.png", "cosmetics/models/wings_demon.json", CosmeticModelAnchor.cZ));
        CosmeticCatalog.a(CosmeticDefinition.a("effect_spheres", "cosmetics.effect.spheres", "cosmetics/icons/effect_spheres.png", CosmeticEffectType.dl));
    }
}

