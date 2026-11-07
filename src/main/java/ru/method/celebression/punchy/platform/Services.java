package ru.method.celebression.punchy.platform;

import ru.method.celebression.punchy.platform.FabricClientPlatform;
import ru.method.celebression.punchy.platform.FabricPlatformHelper;
import ru.method.celebression.punchy.platform.services.IClientPlatform;
import ru.method.celebression.punchy.platform.services.IPlatformHelper;

public final class Services {
    public static final IPlatformHelper PLATFORM = new FabricPlatformHelper();
    public static final IClientPlatform CLIENT = new FabricClientPlatform();

    private Services() {
    }
}

