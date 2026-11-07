package ru.method.celebression.modules.impl.render.cosmetic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3264;
import net.minecraft.class_3300;
import net.minecraft.class_3532;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelCache;
import ru.method.celebression.modules.impl.render.cosmetic.EmbeddedCosmeticAssets;

public final class CosmeticResourceRegistry {
    private static final List<String> eq = List.of("cosmetics/capes/classic.png", "cosmetics/capes/aurora.png", "cosmetics/capes/shadow.png", "cosmetics/icons/crown.png", "cosmetics/icons/halo.png", "cosmetics/icons/horns.png", "cosmetics/icons/cap.png", "cosmetics/icons/angel.png", "cosmetics/icons/demon.png", "cosmetics/icons/effect_halo.png", "cosmetics/icons/effect_spheres.png", "cosmetics/icons/effect_trail.png", "cosmetics/models/hat_crown.png", "cosmetics/models/hat_halo.png", "cosmetics/models/hat_horns.png", "cosmetics/models/hat_cap.png", "cosmetics/models/wings_angel.png", "cosmetics/models/wings_demon.png");
    private static final Map<String, String> er = Map.of("cosmetics/models/hat_crown.png", "1.json", "cosmetics/models/hat_halo.png", "21.json", "cosmetics/models/hat_horns.png", "7.json", "cosmetics/models/hat_cap.png", "3.json", "cosmetics/models/wings_angel.png", "24.json", "cosmetics/models/wings_demon.png", "23.json");
    private static final Map<String, String> es = Map.of("cosmetics/icons/crown.png", "cosmetics/models/hat_crown.png", "cosmetics/icons/halo.png", "cosmetics/models/hat_halo.png", "cosmetics/icons/horns.png", "cosmetics/models/hat_horns.png", "cosmetics/icons/cap.png", "cosmetics/models/hat_cap.png", "cosmetics/icons/angel.png", "cosmetics/models/wings_angel.png", "cosmetics/icons/demon.png", "cosmetics/models/wings_demon.png");
    private static final Map<String, int[]> et = Map.of("cosmetics/capes/classic.png", new int[]{184, 48, 48, 132, 28, 28}, "cosmetics/capes/aurora.png", new int[]{88, 168, 255, 48, 210, 190}, "cosmetics/capes/shadow.png", new int[]{42, 46, 58, 24, 26, 34});

    private CosmeticResourceRegistry() {
    }

    public static void register() {
        ResourceManagerHelper.get((class_3264)class_3264.field_14188).registerReloadListener((IdentifiableResourceReloadListener)new SimpleSynchronousResourceReloadListener(){

            public class_2960 getFabricId() {
                return Celebression.id("cosmetics/texture_registry");
            }

            public void method_14491(class_3300 class_33002) {
                CosmeticResourceRegistry.a(class_33002);
            }
        });
    }

    public static void a(class_3300 class_33002) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return;
        }
        int n = 0;
        for (String string : eq) {
            if (!CosmeticResourceRegistry.a(class_33002, class_3102, Celebression.id(string))) continue;
            ++n;
        }
        CosmeticModelCache.bu();
        Celebression.LOGGER.info("[Cosmetics] Registered {}/{} textures", (Object)n, (Object)eq.size());
    }

    private static boolean a(class_3300 class_33002, class_310 class_3102, class_2960 class_29602) {
        class_1011 class_10112 = CosmeticResourceRegistry.a(class_33002, class_29602);
        if (class_10112 == null) {
            Celebression.LOGGER.warn("[Cosmetics] Missing texture {}", (Object)class_29602);
            return false;
        }
        try {
            class_1043 class_10432 = new class_1043(class_10112);
            class_10432.method_4527(false, false);
            class_3102.method_1531().method_4616(class_29602, (class_1044)class_10432);
            return true;
        }
        catch (Exception exception) {
            class_10112.close();
            Celebression.LOGGER.warn("[Cosmetics] Failed to register texture {}", (Object)class_29602, (Object)exception);
            return false;
        }
    }

    private static class_1011 a(class_3300 class_33002, class_2960 class_29602) {
        class_1011 class_10112 = CosmeticResourceRegistry.b(class_33002, class_29602);
        if (class_10112 != null) {
            return class_10112;
        }
        class_10112 = CosmeticResourceRegistry.f(class_29602);
        if (class_10112 != null) {
            return class_10112;
        }
        class_10112 = CosmeticResourceRegistry.g(class_29602);
        if (class_10112 != null) {
            return class_10112;
        }
        String string = es.get(class_29602.method_12832());
        if (string != null && (class_10112 = CosmeticResourceRegistry.a(class_33002, Celebression.id(string))) != null) {
            return class_10112;
        }
        return CosmeticResourceRegistry.h(class_29602);
    }

    private static class_1011 b(class_3300 class_33002, class_2960 class_29602) {
        class_1011 class_10112 = CosmeticResourceRegistry.d(class_29602);
        if (class_10112 != null) {
            return class_10112;
        }
        for (class_2960 class_29603 : CosmeticResourceRegistry.e(class_29602)) {
            try (InputStream inputStream = class_33002.open(class_29603)) {
                return class_1011.method_4309(inputStream);
            }
            catch (Exception ignored) {
            }
        }
        return null;
    }

    private static class_1011 d(class_2960 class_29602) {
        String string = "/assets/" + class_29602.method_12836() + "/textures/" + class_29602.method_12832();
        try (InputStream inputStream = CosmeticResourceRegistry.class.getResourceAsStream(string);){
            if (inputStream == null) {
                class_1011 class_10113 = null;
                return class_10113;
            }
            class_1011 class_10114 = class_1011.method_4309((InputStream)inputStream);
            Celebression.LOGGER.debug("[Cosmetics] Loaded texture {} from classpath {}", (Object)class_29602, (Object)string);
            class_1011 class_10112 = class_10114;
            return class_10112;
        }
        catch (Exception exception) {
            Celebression.LOGGER.debug("[Cosmetics] Classpath miss for {}", (Object)class_29602);
            return null;
        }
    }

    private static List<class_2960> e(class_2960 class_29602) {
        String string = class_29602.method_12832();
        String string2 = class_29602.method_12836();
        String string3 = string.endsWith(".png") ? string.substring(0, string.length() - 4) : string;
        return List.of(class_2960.method_60655((String)string2, (String)("textures/" + string)), class_2960.method_60655((String)string2, (String)("textures/" + string3 + ".png")), class_2960.method_60655((String)string2, (String)string), class_2960.method_60655((String)string2, (String)(string3 + ".png")));
    }

    private static class_1011 f(class_2960 class_29602) {
        byte[] byArray = EmbeddedCosmeticAssets.as(class_29602.method_12832());
        if (byArray == null) {
            return null;
        }
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(byArray)) {
            return class_1011.method_4309(inputStream);
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[Cosmetics] Failed to decode embedded texture {}", (Object)class_29602, (Object)exception);
            return null;
        }
    }

    private static class_1011 g(class_2960 class_29602) {
        String string = er.get(class_29602.method_12832());
        if (string == null) {
            return null;
        }
        Path path = FabricLoader.getInstance().getGameDir().resolve("MoreCosmetics/data/models").resolve(string);
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            return null;
        }
        try {
            String string2 = Files.readString(path, StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonParser.parseString(string2).getAsJsonObject();
            if (!jsonObject.has("texture") || !jsonObject.get("texture").isJsonPrimitive()) {
                return null;
            }
            byte[] byArray = Base64.getDecoder().decode(jsonObject.get("texture").getAsString());
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(byArray)) {
                class_1011 image = class_1011.method_4309(inputStream);
                Celebression.LOGGER.info("[Cosmetics] Loaded fallback texture {} from {}", (Object)class_29602, (Object)path.getFileName());
                return image;
            }
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[Cosmetics] Failed to read MoreCosmetics fallback for {}", (Object)class_29602, (Object)exception);
            return null;
        }
    }

    private static class_1011 h(class_2960 class_29602) {
        int n;
        int[] nArray = et.get(class_29602.method_12832());
        if (nArray == null) {
            return null;
        }
        class_1011 class_10112 = new class_1011(64, 32, true);
        int n2 = CosmeticResourceRegistry.a(nArray[0], nArray[1], nArray[2]);
        int n3 = CosmeticResourceRegistry.a(nArray[3], nArray[4], nArray[5]);
        int n4 = CosmeticResourceRegistry.b(n2, 0.72f);
        int n5 = CosmeticResourceRegistry.b(n3, 0.82f);
        for (n = 0; n < 32; ++n) {
            for (int i = 0; i < 64; ++i) {
                class_10112.method_61941(i, n, 0);
            }
        }
        for (n = 1; n <= 16; ++n) {
            int n6;
            int n7;
            float f = (float)(n - 1) / 15.0f;
            int n8 = CosmeticResourceRegistry.blend(n2, n3, f * 0.35f);
            int n9 = CosmeticResourceRegistry.blend(n3, n2, f * 0.2f);
            for (n7 = 1; n7 <= 10; ++n7) {
                n6 = n8;
                if (n7 <= 2 || n <= 2) {
                    n6 = n4;
                } else if ((n7 + n) % 5 == 0) {
                    n6 = CosmeticResourceRegistry.blend(n8, -1, 0.08f);
                }
                class_10112.method_61941(n7, n, 0xFF000000 | n6);
            }
            for (n7 = 11; n7 <= 22; ++n7) {
                n6 = n9;
                if (n7 >= 21 || n <= 2) {
                    n6 = n4;
                } else if (n7 * n % 7 == 0) {
                    n6 = CosmeticResourceRegistry.blend(n9, -1, 0.1f);
                }
                class_10112.method_61941(n7, n, 0xFF000000 | n6);
            }
        }
        for (n = 1; n <= 22; ++n) {
            class_10112.method_61941(n, 17, 0xFF000000 | n5);
            if (n % 3 != 0) continue;
            class_10112.method_61941(n, 16, 0xFF000000 | CosmeticResourceRegistry.b(n5, 0.9f));
        }
        for (n = 1; n <= 10; ++n) {
            class_10112.method_61941(n, 0, 0xFF000000 | n4);
        }
        for (n = 11; n <= 22; ++n) {
            class_10112.method_61941(n, 0, 0xFF000000 | n4);
        }
        Celebression.LOGGER.info("[Cosmetics] Generated procedural cape texture {}", (Object)class_29602);
        return class_10112;
    }

    private static int a(int n, int n2, int n3) {
        return n << 16 | n2 << 8 | n3;
    }

    private static int b(int n, float f) {
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        return CosmeticResourceRegistry.a(Math.round((float)n2 * f), Math.round((float)n3 * f), Math.round((float)n4 * f));
    }

    private static int blend(int n, int n2, float f) {
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        int n3 = n >> 16 & 0xFF;
        int n4 = n >> 8 & 0xFF;
        int n5 = n & 0xFF;
        int n6 = n2 >> 16 & 0xFF;
        int n7 = n2 >> 8 & 0xFF;
        int n8 = n2 & 0xFF;
        return CosmeticResourceRegistry.a(Math.round((float)n3 + (float)(n6 - n3) * f2), Math.round((float)n4 + (float)(n7 - n4) * f2), Math.round((float)n5 + (float)(n8 - n5) * f2));
    }
}
