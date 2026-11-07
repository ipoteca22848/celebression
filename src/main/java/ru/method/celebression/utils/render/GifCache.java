package ru.method.celebression.utils.render;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_2960;
import ru.method.celebression.utils.render.GifTexture;

public final class GifCache {
    private static final Map<class_2960, GifTexture> CACHE = new ConcurrentHashMap<class_2960, GifTexture>();

    private GifCache() {
    }

    public static GifTexture get(class_2960 class_29602) {
        if (class_29602 == null) {
            return null;
        }
        return CACHE.computeIfAbsent(class_29602, GifTexture::new);
    }

    public static void preload(class_2960 class_29602) {
        GifCache.get(class_29602);
    }

    public static void clear() {
        CACHE.clear();
    }
}

