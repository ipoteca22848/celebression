package ru.method.celebression.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2588;
import net.minecraft.class_310;
import net.minecraft.class_339;
import net.minecraft.class_364;
import net.minecraft.class_404;
import net.minecraft.class_4189;
import net.minecraft.class_426;
import net.minecraft.class_4264;
import net.minecraft.class_4288;
import net.minecraft.class_429;
import net.minecraft.class_437;
import net.minecraft.class_440;
import net.minecraft.class_443;
import net.minecraft.class_446;
import net.minecraft.class_458;
import net.minecraft.class_6599;
import net.minecraft.class_6777;
import net.minecraft.class_7417;
import net.minecraft.class_8219;
import ru.method.celebression.screen.ControlsSettingsScreen;
import ru.method.celebression.screen.CreditsSettingsScreen;
import ru.method.celebression.screen.LanguageSettingsScreen;
import ru.method.celebression.screen.OptionsCategoryScreen;
import ru.method.celebression.ui.PerformanceSettings;
import ru.method.celebression.screen.SettingsScreen;
import ru.method.celebression.screen.SettingsSubScreen;
import ru.method.celebression.util.SettingsUiUtils;

public final class SettingsEntryRenderer {
    private SettingsEntryRenderer() {
    }

    public static class_437 e(class_437 class_4372) {
        if (class_4372 == null) {
            return null;
        }
        class_437 class_4373 = SettingsEntryRenderer.f(class_4372);
        if (class_4372 instanceof class_429) {
            return new SettingsScreen(class_4373);
        }
        if (class_4372 instanceof class_446) {
            return SettingsEntryRenderer.a(class_4373, "\u0413\u0440\u0430\u0444\u0438\u043a\u0430", SettingsEntryRenderer.a(class_4372, PerformanceSettings.qN()));
        }
        if (class_4372 instanceof class_443) {
            return SettingsEntryRenderer.a(class_4373, "\u041c\u0443\u0437\u044b\u043a\u0430 \u0438 \u0437\u0432\u0443\u043a\u0438", PerformanceSettings.qP());
        }
        if (class_4372 instanceof class_440) {
            return SettingsEntryRenderer.a(class_4373, "\u0412\u043d\u0435\u0448\u043d\u0438\u0439 \u0432\u0438\u0434", PerformanceSettings.qO());
        }
        if (class_4372 instanceof class_404) {
            return SettingsEntryRenderer.a(class_4373, "\u0427\u0430\u0442", PerformanceSettings.qR());
        }
        if (class_4372 instanceof class_4189) {
            return SettingsEntryRenderer.a(class_4373, "\u0421\u043f\u0435\u0446. \u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e\u0441\u0442\u0438", PerformanceSettings.qS());
        }
        if (class_4372 instanceof class_4288 || class_4372 instanceof class_458) {
            return SettingsEntryRenderer.a(class_4373, "\u0423\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435", PerformanceSettings.qQ());
        }
        if (class_4372 instanceof class_6777) {
            return SettingsEntryRenderer.a(class_4373, "\u041e\u043d\u043b\u0430\u0439\u043d", PerformanceSettings.qT());
        }
        if (class_4372 instanceof class_6599) {
            return new ControlsSettingsScreen(class_4373);
        }
        if (class_4372 instanceof class_426) {
            return new LanguageSettingsScreen(class_4373);
        }
        if (class_4372 instanceof class_8219) {
            return new CreditsSettingsScreen(class_4373);
        }
        return null;
    }

    private static OptionsCategoryScreen a(class_437 class_4372, String string, List<SettingsUiUtils> list) {
        return new OptionsCategoryScreen(class_4372, string, list);
    }

    private static List<SettingsUiUtils> a(class_437 class_4372, List<SettingsUiUtils> list) {
        ArrayList<SettingsUiUtils> arrayList = new ArrayList<SettingsUiUtils>(list);
        try {
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 == null || class_3102.method_22683() == null) {
                return arrayList;
            }
            class_4372.method_25423(class_3102, class_3102.method_22683().method_4486(), class_3102.method_22683().method_4502());
            for (class_364 class_3642 : class_4372.method_25396()) {
                String string;
                String string2;
                if (!(class_3642 instanceof class_339)) continue;
                class_339 class_3392 = (class_339)class_3642;
                if (!(class_3642 instanceof class_4264)) continue;
                class_4264 class_42642 = (class_4264)class_3642;
                if (!class_3392.field_22764 || "gui.done".equals(string2 = SettingsEntryRenderer.g(class_3392.method_25369())) || "gui.back".equals(string2) || "options.done".equals(string2) || (string = class_3392.method_25369().getString().trim()).isEmpty()) continue;
                arrayList.add(SettingsUiUtils.a(string, () -> "", () -> ((class_4264)class_42642).method_25306()));
            }
        }
        catch (Throwable throwable) {
        }
        return arrayList;
    }

    private static String g(class_2561 class_25612) {
        String string;
        class_7417 class_74172;
        if (class_25612 != null && (class_74172 = class_25612.method_10851()) instanceof class_2588) {
            class_2588 class_25882 = (class_2588)class_74172;
            string = class_25882.method_11022();
        } else {
            string = "";
        }
        return string;
    }

    private static class_437 f(class_437 class_4372) {
        class_437 class_4373;
        class_310 class_3102 = class_310.method_1551();
        class_437 class_4374 = class_4373 = class_3102 == null ? null : class_3102.field_1755;
        if (class_4373 instanceof SettingsSubScreen && class_4373 != class_4372) {
            return class_4373;
        }
        return null;
    }
}

