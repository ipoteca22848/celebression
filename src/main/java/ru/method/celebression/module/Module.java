package ru.method.celebression.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import lombok.Generated;
import net.minecraft.class_1074;
import ru.method.celebression.Celebression;
import ru.method.celebression.hud.HudNotificationQueue;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.manager.SoundManager;

public abstract class Module
implements MinecraftClientAccess {
    private final String name;
    private final String description;
    private final ModuleCategory category;
    protected boolean toggled;
    private KeyBind bind = new KeyBind(-1, false);
    private final List<Setting<?>> settings = new ArrayList();
    private static final Set<String> NEW_MODULES = Set.of("AutoReply", "FakePlayer", "FastExp", "CustomVitals", "Drochka", "KillStreak", "Sounds");

    public Module(String string, ModuleCategory moduleCategory) {
        this.name = string;
        this.category = moduleCategory;
        this.description = "descriptions." + moduleCategory.name().toLowerCase() + "." + string.toLowerCase();
    }

    public String getDisplayName() {
        String string;
        String string2 = "modules." + this.name.toLowerCase(Locale.ROOT);
        return string2.equals(string = class_1074.method_4662((String)string2, (Object[])new Object[0])) ? this.name : string;
    }

    public void onEnable() {
        this.toggled = true;
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
        boolean bl = Celebression.getInstance().isSuppressToggleFeedback();
        if (!bl && !Module.fullNullCheck()) {
            HudNotificationQueue.dG().a(this.getDisplayName(), true);
        }
        SoundManager soundManager = Celebression.getInstance().getSoundManager();
        if (!bl && soundManager != null) {
            soundManager.fF();
        }
    }

    public void onDisable() {
        this.toggled = false;
        Celebression.getInstance().getEventHandler().unsubscribe((Object)this);
        boolean bl = Celebression.getInstance().isSuppressToggleFeedback();
        if (!bl && !Module.fullNullCheck()) {
            HudNotificationQueue.dG().a(this.getDisplayName(), false);
        }
        SoundManager soundManager = Celebression.getInstance().getSoundManager();
        if (!bl && soundManager != null) {
            soundManager.fG();
        }
    }

    public void setToggled(boolean bl) {
        if (bl && ru.method.celebression.service.UnhookService.isActive()) return;
        if (bl) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public final void disableForSession() {
        try {
            if (this.toggled) this.setToggled(false);
        } finally {
            this.toggled = false;
            Celebression.getInstance().getEventHandler().unsubscribe(this);
        }
    }

    public void toggle() {
        this.setToggled(!this.toggled);
    }

    public boolean isToggle() {
        return this.toggled;
    }

    public boolean isUnsafe() {
        return false;
    }

    public boolean isActionModule() {
        return false;
    }

    public void activate() {
        this.toggle();
    }

    public boolean hidesToggle() {
        return this.isActionModule();
    }

    public boolean isNew() {
        return NEW_MODULES.contains(this.name);
    }

    public String getUnsafeDescriptionKey() {
        return "descriptions.module.unsafe";
    }

    public static boolean fullNullCheck() {
        return Module.aiG.field_1724 == null || Module.aiG.field_1687 == null;
    }

    protected void playCriticalSound() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return;
        }
        SoundManager soundManager = celebression.getSoundManager();
        if (soundManager != null) {
            soundManager.a(this);
        }
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public ModuleCategory getCategory() {
        return this.category;
    }

    @Generated
    public boolean isToggled() {
        return this.toggled;
    }

    @Generated
    public KeyBind getBind() {
        return this.bind;
    }

    @Generated
    public List<Setting<?>> getSettings() {
        return this.settings;
    }

    @Generated
    public void setBind(KeyBind keyBind) {
        this.bind = keyBind;
    }
}

