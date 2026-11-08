package ru.method.celebression.server;

import java.util.Locale;

public final class ServerAds {
    private ServerAds() {}
    public static boolean isPromotion(String name, String address) {
        return branded(name) || branded(address);
    }
    private static boolean branded(String value) {
        if (value == null) return false;
        String clean = value.replaceAll("[§&][0-9a-fk-orA-FK-OR]", "").toLowerCase(Locale.ROOT);
        return clean.contains("artygrief") || clean.contains("artigrief")
                || clean.contains("artigrif") || clean.contains("cuteworld") || clean.contains("cute-world")
                || clean.contains("артигриф") || clean.contains("кьютворлд");
    }
}
