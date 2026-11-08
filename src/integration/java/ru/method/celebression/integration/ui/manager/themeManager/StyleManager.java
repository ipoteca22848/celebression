package ru.method.celebression.integration.ui.manager.themeManager;

import com.google.gson.JsonObject;
import net.minecraft.util.math.MathHelper;
import ru.method.celebression.integration.ui.manager.IMinecraft;
import ru.method.celebression.integration.ui.manager.Manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("All")
public class StyleManager implements IMinecraft {
    public record ThemePreset(int previewColor, Map<String, Integer> colors) {
    }

    public static final ThemePreset[] PRESETS = {
            preset(0xFF8ED8FF, colors(
                    "primaryColor", 0xFF8ED8FF,
                    "fonColor", 0xF518202B,
                    "textColor", 0xFFFFFFFF,
                    "iconColor", 0xFF8ED8FF,
                    "iconnoColor", 0x64FFFFFF,
                    "infoColor", 0xFFFFFFFF,
                    "scrollColor", 0xFF636363,
                    "yesColor", 0xFF8ED8FF,
                    "crossColor", 0xFFDC4646,
                    "goldColor", 0xFFB2E5FF,
                    "sliderColor", 0xFF5A9FC4
            )),
            preset(0xFF1E1E1E, colors(
                    "primaryColor", 0xFF1E1E1E,
                    "fonColor", 0xF5101010,
                    "textColor", 0xFFE6E6E6,
                    "iconColor", 0xFF8A8A8A,
                    "iconnoColor", 0x55FFFFFF,
                    "infoColor", 0xFFFFFFFF,
                    "scrollColor", 0xFF444444,
                    "yesColor", 0xFF4CAF50,
                    "crossColor", 0xFFE57373,
                    "goldColor", 0xFF757575,
                    "sliderColor", 0xFF3A3A3A
            )),
            preset(0xFF8A9CFF, colors(
                    "primaryColor", 0xFF8A9CFF,
                    "fonColor", 0xF5141419,
                    "textColor", 0xFFE8EAFF,
                    "iconColor", 0xFF8A9CFF,
                    "iconnoColor", 0x64A0A8CC,
                    "infoColor", 0xFFFFFFFF,
                    "scrollColor", 0xFF5C6B8A,
                    "yesColor", 0xFF7CB8FF,
                    "crossColor", 0xFFFF7B9C,
                    "goldColor", 0xFF9BA8D4,
                    "sliderColor", 0xFF6B7FD4
            ))
    };

    private static Map<String, Integer> colors(Object... pairs) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            map.put((String) pairs[i], (Integer) pairs[i + 1]);
        }
        return map;
    }

    private static ThemePreset preset(int previewColor, Map<String, Integer> colors) {
        return new ThemePreset(previewColor, new LinkedHashMap<>(colors));
    }

    private final List<GuiThemeColor> colors = new ArrayList<>();
    private int selectedPreset = 0;

    public void init() {
        colors.clear();
        add("primaryColor", "Основной", "Главный акцентный цвет интерфейса", 0xFF8ED8FF);
        add("fonColor", "Фон", "Цвет фона окон и панелей", 0xF518202B);
        add("textColor", "Текст", "Основной цвет текста", 0xFFFFFFFF);
        add("iconColor", "Иконки", "Цвет активных иконок", 0xFF8ED8FF);
        add("iconnoColor", "Прочее", "Приглушённые иконки и элементы", 0x64FFFFFF);
        add("infoColor", "Заголовки", "Цвет заголовков и названий", 0xFFFFFFFF);
        add("scrollColor", "Скролл", "Полосы прокрутки и разделители", 0xFF636363);
        add("yesColor", "Включено", "Индикатор включённых элементов", 0xFF8ED8FF);
        add("crossColor", "Выключено", "Индикатор выключенных элементов", 0xFFDC4646);
        add("goldColor", "Акцент полоска", "Декоративные акцентные полоски", 0xFFB2E5FF);
        add("sliderColor", "Слайдер", "Цвет ползунков и слайдеров", 0xFF5A9FC4);
    }

    private void add(String key, String name, String description, int color) {
        colors.add(new GuiThemeColor(key, name, description, color));
    }

    public List<GuiThemeColor> getColors() {
        return colors;
    }

    public GuiThemeColor getEntry(String key) {
        for (GuiThemeColor c : colors) {
            if (c.key.equals(key)) return c;
        }
        return null;
    }

    public int getColorByName(String key) {
        GuiThemeColor e = getEntry(key);
        return e != null ? e.color : -1;
    }

    public int getFirstColor() {
        return getColorByName("primaryColor");
    }

    public void closeAllPickers() {
        for (GuiThemeColor c : colors) {
            c.pickerOpen = false;
        }
    }

    public int getSelectedPreset() {
        return selectedPreset;
    }

    public void applyPreset(int index) {
        if (index < 0 || index >= PRESETS.length) return;
        ThemePreset preset = PRESETS[index];
        for (Map.Entry<String, Integer> entry : preset.colors().entrySet()) {
            GuiThemeColor color = getEntry(entry.getKey());
            if (color != null) {
                color.setColor(entry.getValue());
            }
        }
        selectedPreset = index;
        closeAllPickers();
        scheduleSave();
    }

    public void scheduleSave() {
        if (Manager.CONFIG_MANAGER != null) {
            Manager.CONFIG_MANAGER.saveConfiguration("autocfg");
        }
    }

    public JsonObject saveColors() {
        JsonObject root = new JsonObject();
        root.addProperty("celebressionBlueTheme", true);
        for (GuiThemeColor e : colors) {
            JsonObject o = new JsonObject();
            o.addProperty("color", e.color);
            o.addProperty("hue", e.hue);
            o.addProperty("saturation", e.saturation);
            o.addProperty("brightness", e.brightness);
            o.addProperty("alpha", e.alpha);
            root.add(e.key, o);
        }
        return root;
    }

    public void loadColors(JsonObject root) {
        if (root == null) return;
        for (GuiThemeColor e : colors) {
            if (!root.has(e.key)) continue;
            JsonObject o = root.getAsJsonObject(e.key);
            if (o.has("color")) e.setColor(o.get("color").getAsInt());
            if (o.has("hue")) e.hue = o.get("hue").getAsFloat();
            if (o.has("saturation")) e.saturation = o.get("saturation").getAsFloat();
            if (o.has("brightness")) e.brightness = o.get("brightness").getAsFloat();
            if (o.has("alpha")) e.alpha = o.get("alpha").getAsFloat();
            e.syncColorFromHsb();
        }
        // Upgrade existing saved palettes once; subsequent user changes remain editable.
        if (!root.has("celebressionBlueTheme")) {
            for (var item : PRESETS[0].colors().entrySet()) {
                GuiThemeColor entry = getEntry(item.getKey());
                if (entry != null) entry.setColor(item.getValue());
            }
            selectedPreset = 0;
        }
    }

    public static class HexColor {
        public static int toColor(String hexColor) {
            int rgb = Integer.parseInt(hexColor.substring(1), 16);
            return reAlphaInt(rgb, 255);
        }

        public static int reAlphaInt(int color, int alpha) {
            return (MathHelper.clamp(alpha, 0, 255) << 24) | (color & 0xFFFFFF);
        }
    }
}
