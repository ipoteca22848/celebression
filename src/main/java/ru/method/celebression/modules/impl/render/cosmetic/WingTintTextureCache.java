package ru.method.celebression.modules.impl.render.cosmetic;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import ru.method.celebression.Celebression;

public final class WingTintTextureCache {
    private static final Map<String, class_2960> aW = new ConcurrentHashMap<String, class_2960>();

    private WingTintTextureCache() {
    }

    public static class_2960 a(class_2960 class_29602, int n) {
        if (class_29602 == null) {
            return null;
        }
        int n2 = n >> 16 & 0xF8;
        int n3 = n >> 8 & 0xF8;
        int n4 = n & 0xF8;
        if (n2 >= 248 && n3 >= 248 && n4 >= 248) {
            return class_29602;
        }
        String string = String.valueOf(class_29602) + "#" + n2 + "_" + n3 + "_" + n4;
        class_2960 class_29603 = aW.get(string);
        if (class_29603 != null) {
            return class_29603;
        }
        class_2960 class_29604 = WingTintTextureCache.a(class_29602, n2, n3, n4, string);
        if (class_29604 != null) {
            aW.put(string, class_29604);
            return class_29604;
        }
        return class_29602;
    }

    private static class_2960 a(class_2960 class_29602, int n, int n2, int n3, String string) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return null;
        }
        try {
            class_1011 class_10112;
            Optional optional = class_3102.method_1478().method_14486(class_29602);
            if (optional.isEmpty()) {
                return null;
            }
            try (InputStream inputStream = ((class_3298)optional.get()).method_14482();){
                class_10112 = class_1011.method_4309((InputStream)inputStream);
            }
            int n4 = class_10112.method_4307();
            int n5 = class_10112.method_4323();
            class_1011 class_10113 = new class_1011(n4, n5, true);
            for (int i = 0; i < n5; ++i) {
                for (int j = 0; j < n4; ++j) {
                    int n6 = class_10112.method_61940(j, i);
                    int n7 = n6 >> 24 & 0xFF;
                    int n8 = n6 >> 16 & 0xFF;
                    int n9 = n6 >> 8 & 0xFF;
                    int n10 = n6 & 0xFF;
                    int n11 = n8 * n / 255;
                    int n12 = n9 * n2 / 255;
                    int n13 = n10 * n3 / 255;
                    class_10113.method_61941(j, i, n7 << 24 | n11 << 16 | n12 << 8 | n13);
                }
            }
            class_10112.close();
            class_2960 class_29603 = Celebression.id("dynamic/wing_tint_" + Integer.toHexString(Math.abs(string.hashCode())));
            class_1043 class_10432 = new class_1043(class_10113);
            class_3102.method_1531().method_4616(class_29603, (class_1044)class_10432);
            return class_29603;
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[Wings] tint texture failed for {}", (Object)class_29602, (Object)exception);
            return null;
        }
    }
}

