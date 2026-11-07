package ru.method.celebression.manager;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleDescriptor;

public class MacroManager
implements MinecraftClientAccess {
    private final List<ModuleDescriptor> nl = new ArrayList<ModuleDescriptor>();
    private final List<String> nm = new ArrayList<String>();

    public MacroManager() {
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
    }

    public void a(ModuleDescriptor moduleDescriptor) {
        this.nl.add(moduleDescriptor);
        this.nm.add(moduleDescriptor.getName());
    }

    public void b(ModuleDescriptor moduleDescriptor) {
        this.nl.remove(moduleDescriptor);
        this.nm.remove(moduleDescriptor.getName());
    }

    public void clear() {
        if (!this.nl.isEmpty()) {
            this.nl.clear();
        }
        if (!this.nm.isEmpty()) {
            this.nm.clear();
        }
    }

    public boolean isEmpty() {
        return this.nl.isEmpty() || this.nm.isEmpty();
    }

    public ModuleDescriptor bw(String string) {
        for (ModuleDescriptor moduleDescriptor : this.nl) {
            if (!moduleDescriptor.getName().equalsIgnoreCase(string)) continue;
            return moduleDescriptor;
        }
        return null;
    }

    @EventHandler
    public void onKey(KeyInputEvent keyInputEvent) {
        if (Module.fullNullCheck() || MacroManager.aiG.field_1755 != null || Celebression.getInstance().isPanic() || this.nl.isEmpty()) {
            return;
        }
        if (keyInputEvent.getAction() == 1) {
            for (ModuleDescriptor moduleDescriptor : this.nl) {
                if (!moduleDescriptor.getBind().af(keyInputEvent.c())) continue;
                if (moduleDescriptor.t().startsWith("/")) {
                    MacroManager.aiG.field_1724.field_3944.method_45730(moduleDescriptor.t().replace("/", ""));
                    continue;
                }
                MacroManager.aiG.field_1724.field_3944.method_45729(moduleDescriptor.t());
            }
        }
    }

    @Generated
    public List<ModuleDescriptor> ev() {
        return this.nl;
    }

    @Generated
    public List<String> ew() {
        return this.nm;
    }
}

