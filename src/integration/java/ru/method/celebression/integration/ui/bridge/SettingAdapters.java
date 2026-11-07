package ru.method.celebression.integration.ui.bridge;

import java.awt.Color;
import java.util.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import ru.method.celebression.api.setting.*;
import ru.method.celebression.render.Point2f;
import ru.method.celebression.integration.ui.modules.api.Module;

public final class SettingAdapters {
    private SettingAdapters() {}
    private static String label(String key) { return I18n.translate(key); }
    public static List<ru.method.celebression.integration.ui.modules.setting.Setting> adaptAll(List<Setting<?>> input) {
        var output = new ArrayList<ru.method.celebression.integration.ui.modules.setting.Setting>();
        for (Setting<?> setting : input) {
            if (setting instanceof PositionSetting position) {
                for (boolean horizontal : new boolean[]{true, false}) {
                    var slider = new ru.method.celebression.integration.ui.modules.setting.SliderSetting(
                            label(setting.getName()) + (horizontal ? " X" : " Y"), 0, 0, 8192, 1) {
                        public Number get() { return horizontal ? position.jT().getX() : position.jT().getY(); }
                        public void set(double value) {
                            Point2f previous = position.jT();
                            position.a(new Point2f(horizontal ? (float)value : previous.getX(), horizontal ? previous.getY() : (float)value));
                        }
                    };
                    slider.setVisible(setting::jS);
                    output.add(slider);
                }
                continue;
            }
            var adapted = adapt(setting);
            adapted.setVisible(setting::jS);
            output.add(adapted);
        }
        return output;
    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ru.method.celebression.integration.ui.modules.setting.Setting adapt(Setting<?> setting) {
        String name = label(setting.getName());
        if (setting instanceof BooleanSetting value) {
            return new ru.method.celebression.integration.ui.modules.setting.BooleanSetting(name, value.jT()) {
                public boolean get() { return value.jT(); }
                public void set(boolean next) { value.a(next); }
            };
        }
        if (setting instanceof SliderSetting value) {
            return new ru.method.celebression.integration.ui.modules.setting.SliderSetting(name, value.jT(), value.kb(), value.kc(), value.kd()) {
                public Number get() { return value.jT(); }
                public void set(double next) { value.a((float)Math.max(value.kb(), Math.min(value.kc(), next))); }
            };
        }
        if (setting instanceof ColorSetting value) {
            return new ru.method.celebression.integration.ui.modules.setting.ColorSetting(name, value.jT().getRGB()) {
                public int get() { return value.jT().getRGB(); }
                public void set(int next) { value.a(new Color(next, true)); }
                public void resetToDefault() { value.reset(); }
            };
        }
        if (setting instanceof StringSetting value) {
            return new ru.method.celebression.integration.ui.modules.setting.TextSetting(name, value.jT()) {
                public String getValue() { return value.jT(); }
                public void setValue(String next) { value.a(next); }
            };
        }
        if (setting instanceof BindSetting value) {
            return new ru.method.celebression.integration.ui.modules.setting.BindSetting(name, Module.encode(value.jT())) {
                public int getKey() { return Module.encode(value.jT()); }
                public void setKey(int next) { value.a(Module.decode(next)); }
            };
        }
        if (setting instanceof EnumSetting value) {
            Enum<?> current = (Enum<?>)value.jT();
            String[] options = Arrays.stream(current.getDeclaringClass().getEnumConstants())
                    .map(v -> label(((NamedOption)v).getName())).toArray(String[]::new);
            return new ru.method.celebression.integration.ui.modules.setting.ModeSetting(name, options[current.ordinal()], options) {
                public String get() { return options[((Enum<?>)value.jT()).ordinal()]; }
                public void set(String next) {
                    for (int i = 0; i < options.length; i++) if (options[i].equals(next)) {
                        value.a(current.getDeclaringClass().getEnumConstants()[i]); return;
                    }
                }
            };
        }
        if (setting instanceof MultiSelectSetting value) return new MultiAdapter(name, value);
        if (setting instanceof ButtonSetting value) return new ActionSetting(name, value::run);
        throw new IllegalArgumentException("Unsupported visual setting: " + setting.getClass().getName());
    }
    public static final class ActionSetting extends ru.method.celebression.integration.ui.modules.setting.Setting {
        private final Runnable action;
        ActionSetting(String name, Runnable action) { this.name = name; this.action = action; setVisible(() -> true); }
        public void run() { action.run(); }
    }
    private static final class MultiAdapter extends ru.method.celebression.integration.ui.modules.setting.MultiSetting {
        private MultiSelectSetting source;
        MultiAdapter(String name, MultiSelectSetting source) {
            super(name, List.of(), source.jT().stream().map(v -> label(v.getName())).toList());
            this.source = source;
        }
        public Set<String> getSelectedModes() {
            var result = new LinkedHashSet<String>();
            for (var value : source.jT()) if (value.jT()) result.add(label(value.getName()));
            return result;
        }
        public boolean get(String name) { return getSelectedModes().contains(name); }
        public void toggle(String name) {
            for (var value : source.jT()) if (label(value.getName()).equals(name)) value.a(!value.jT());
        }
        public void setSelected(Collection<String> names) {
            if (source == null) { super.setSelected(names); return; }
            for (var value : source.jT()) value.a(names.contains(label(value.getName())));
        }
        public void clearSelection() { setSelected(List.of()); }
        public boolean hasAnySelected() { return !getSelectedModes().isEmpty(); }
        public int getAllSelected() { return getSelectedModes().size(); }
    }
}
