package ru.method.celebression.punchy.compat;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_2248;
import net.minecraft.class_2336;
import net.minecraft.class_2531;
import net.minecraft.class_2960;
import net.minecraft.class_3300;
import net.minecraft.class_4739;
import ru.method.celebression.punchy.platform.Services;

public final class EmfEtfCemCompat {
    private static final String[] EMF_IDS = new String[]{"entity_model_features", "emf"};
    private static final String[] ETF_IDS = new String[]{"entity_texture_features", "etf"};
    private static final String[] CEM_IDS = new String[]{"cem"};
    private static final String[] CEM_PATHS = new String[]{"emf/cem", "optifine/cem", "cem"};
    private static final String[] ETF_PATHS = new String[]{"etf", "optifine/random/entity", "optifine/emissive"};
    private static final EnumSet<ProxyPhysicsTarget> DISABLED = EnumSet.noneOf(ProxyPhysicsTarget.class);
    private static boolean ACTIVE;

    private EmfEtfCemCompat() {
    }

    public static void onResourceReload(class_3300 class_33002) {
        DISABLED.clear();
        ACTIVE = EmfEtfCemCompat.isAnyModLoaded();
        if (ACTIVE && class_33002 != null) {
            for (String string : CEM_PATHS) {
                EmfEtfCemCompat.scan(class_33002, string, true);
            }
            if (EmfEtfCemCompat.isEtfLoaded()) {
                for (String string : ETF_PATHS) {
                    EmfEtfCemCompat.scan(class_33002, string, false);
                }
            }
        }
    }

    public static boolean disableProxyPhysics(ProxyPhysicsTarget proxyPhysicsTarget) {
        return ACTIVE && DISABLED.contains((Object)proxyPhysicsTarget);
    }

    public static ProxyPhysicsTarget resolveChestTarget(class_2248 class_22482) {
        if (class_22482 instanceof class_2336) {
            return ProxyPhysicsTarget.ENDER_CHEST;
        }
        if (class_22482 instanceof class_2531) {
            return ProxyPhysicsTarget.TRAPPED_CHEST;
        }
        return class_22482 instanceof class_4739 ? ProxyPhysicsTarget.CHEST : null;
    }

    private static boolean isAnyModLoaded() {
        return EmfEtfCemCompat.isEmfLoaded() || EmfEtfCemCompat.isEtfLoaded() || EmfEtfCemCompat.isCemLoaded();
    }

    private static boolean isEmfLoaded() {
        return EmfEtfCemCompat.isAnyLoaded(EMF_IDS);
    }

    private static boolean isEtfLoaded() {
        return EmfEtfCemCompat.isAnyLoaded(ETF_IDS);
    }

    private static boolean isCemLoaded() {
        return EmfEtfCemCompat.isAnyLoaded(CEM_IDS);
    }

    private static boolean isAnyLoaded(String[] stringArray) {
        if (stringArray != null && stringArray.length != 0) {
            for (String string : stringArray) {
                if (string == null || string.isBlank() || !Services.PLATFORM.isModLoaded(string)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static void scan(class_3300 class_33002, String string, boolean bl) {
        Map<class_2960, ?> map = class_33002.method_14488(string, class_29602 -> true);
        if (map != null && !map.isEmpty()) {
            for (class_2960 class_29603 : map.keySet()) {
                String string2;
                String string3;
                if (class_29603 == null || (string3 = class_29603.method_12832()) == null || !EmfEtfCemCompat.matchesExtension(string2 = string3.toLowerCase(Locale.ROOT), bl)) continue;
                EmfEtfCemCompat.registerMatch(string2);
            }
        }
    }

    private static boolean matchesExtension(String string, boolean bl) {
        return !string.endsWith(".jem") && !string.endsWith(".jpm") ? !bl && (string.endsWith(".properties") || string.endsWith(".png")) : true;
    }

    private static void registerMatch(String string) {
        String string2 = EmfEtfCemCompat.stripExtension(EmfEtfCemCompat.fileName(string));
        if (!string2.isEmpty()) {
            if (EmfEtfCemCompat.containsAny(string2, "ender_chest")) {
                DISABLED.add(ProxyPhysicsTarget.ENDER_CHEST);
            }
            if (EmfEtfCemCompat.containsAny(string2, "trapped_chest", "trapped_chest_left", "trapped_chest_right")) {
                DISABLED.add(ProxyPhysicsTarget.TRAPPED_CHEST);
            }
            if (EmfEtfCemCompat.containsAny(string2, "chest", "chest_left", "chest_right")) {
                DISABLED.add(ProxyPhysicsTarget.CHEST);
            }
            if (EmfEtfCemCompat.containsAny(string2, "enchanting_table", "enchantment_table")) {
                DISABLED.add(ProxyPhysicsTarget.ENCHANTING_TABLE);
            }
            if (EmfEtfCemCompat.containsAny(string2, "piston", "sticky_piston")) {
                DISABLED.add(ProxyPhysicsTarget.PISTON);
            }
        }
    }

    private static boolean containsAny(String string, String ... stringArray) {
        if (stringArray != null && stringArray.length != 0) {
            for (String string2 : stringArray) {
                if (string2 == null || string2.isBlank() || !string.contains(string2)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static String fileName(String string) {
        int n = string.lastIndexOf(47);
        return n >= 0 ? string.substring(n + 1) : string;
    }

    private static String stripExtension(String string) {
        int n = string.lastIndexOf(46);
        return n >= 0 ? string.substring(0, n) : string;
    }

    public static enum ProxyPhysicsTarget {
        ENCHANTING_TABLE,
        PISTON,
        CHEST,
        TRAPPED_CHEST,
        ENDER_CHEST;

    }
}
