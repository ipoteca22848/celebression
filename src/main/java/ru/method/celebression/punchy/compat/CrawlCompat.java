package ru.method.celebression.punchy.compat;

import net.minecraft.class_1657;
import ru.method.celebression.punchy.platform.Services;

public final class CrawlCompat {
    private static final String MOD_ID = "crawl";
    private static boolean checked;
    private static boolean available;

    private CrawlCompat() {
    }

    public static boolean isAvailable() {
        if (!checked) {
            checked = true;
            available = Services.PLATFORM != null && Services.PLATFORM.isModLoaded("crawl");
        }
        return available;
    }

    public static boolean isCrawling(class_1657 class_16572) {
        if (class_16572 == null) {
            return false;
        }
        String string = class_16572.method_18376().name();
        if ("CRAWLING".equals(string)) {
            return true;
        }
        return !"SWIMMING".equals(string) ? false : !class_16572.method_5799() && !class_16572.method_5869();
    }
}

