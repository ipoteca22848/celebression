package ru.method.celebression.punchy.platform;

import net.fabricmc.loader.api.FabricLoader;
import ru.method.celebression.punchy.platform.services.IPlatformHelper;

public class FabricPlatformHelper
implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String string) {
        return FabricLoader.getInstance().isModLoaded(string);
    }

    @Override
    public String getModDisplayName(String string2) {
        return string2 != null && !string2.isBlank() ? FabricLoader.getInstance().getModContainer(string2).map(modContainer -> modContainer.getMetadata().getName()).filter(string -> string != null && !string.isBlank()).orElse(string2) : "";
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}

