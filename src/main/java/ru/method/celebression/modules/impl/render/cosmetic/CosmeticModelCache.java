package ru.method.celebression.modules.impl.render.cosmetic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModel;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelParser;

public final class CosmeticModelCache {
    private static final Map<class_2960, CosmeticModel> aD = new ConcurrentHashMap<class_2960, CosmeticModel>();

    private CosmeticModelCache() {
    }

    public static CosmeticModel b(class_2960 class_29602) {
        if (class_29602 == null) {
            return null;
        }
        return aD.computeIfAbsent(class_29602, CosmeticModelCache::c);
    }

    public static void bu() {
        aD.clear();
    }

    private static CosmeticModel c(class_2960 class_29602) {
        class_3300 class_33002 = class_310.method_1551().method_1478();
        try {
            JsonObject jsonObject;
            class_3298 class_32982 = (class_3298)class_33002.method_14486(class_29602).orElseThrow();
            try (InputStreamReader inputStreamReader = new InputStreamReader(class_32982.method_14482(), StandardCharsets.UTF_8);){
                jsonObject = JsonParser.parseReader((Reader)inputStreamReader).getAsJsonObject();
            }
            return CosmeticModelParser.a(jsonObject);
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[Cosmetics] Failed to load model asset {}", (Object)class_29602, (Object)exception);
            return null;
        }
    }
}

