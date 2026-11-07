package ru.method.celebression.integration.ui.modules.api;

import java.util.List;
import net.minecraft.client.resource.language.I18n;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.integration.ui.modules.TypeList;
import ru.method.celebression.integration.ui.modules.setting.Setting;
import ru.method.celebression.integration.ui.bridge.SettingAdapters;

/** A view of an existing visual module, never a donor gameplay module. */
public final class Module {
    public final ru.method.celebression.module.Module source;
    public String name;
    public String desc;
    public boolean state;
    public boolean expanded;
    public final TypeList category;
    private final List<Setting> settings;

    public Module(ru.method.celebression.module.Module source) {
        this.source = source;
        this.category = switch (source.getCategory().ordinal()) {
            case 0 -> TypeList.Combat;
            case 1 -> TypeList.Movement;
            case 2 -> TypeList.Render;
            case 3 -> TypeList.Player;
            case 4 -> TypeList.Display;
            default -> TypeList.Misc;
        };
        settings = SettingAdapters.adaptAll(source.getSettings());
        refresh();
    }
    public void refresh() {
        name = source.getDisplayName();
        desc = I18n.translate(source.getDescription());
        state = source.isToggled();
    }
    public boolean isState() { return source.isToggled(); }
    public void toggle() { source.toggle(); refresh(); }
    public List<Setting> getSettings() { return settings; }
    public int getBindCode() { return encode(source.getBind()); }
    public void setBindCode(int code) { source.setBind(decode(code)); }
    public static int encode(KeyBind bind) {
        if (bind == null || bind.isEmpty()) return 0;
        return bind.jW() ? -(bind.c() + 2) : bind.c();
    }
    public static KeyBind decode(int code) {
        return code == 0 || code == -1 ? new KeyBind(-1, false)
                : code <= -2 ? new KeyBind(-code - 2, true) : new KeyBind(code, false);
    }
}
