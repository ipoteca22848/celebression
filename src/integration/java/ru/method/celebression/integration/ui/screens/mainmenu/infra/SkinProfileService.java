package ru.method.celebression.integration.ui.screens.mainmenu.infra;

import com.mojang.authlib.GameProfile;
import ru.method.celebression.integration.ui.screens.mainmenu.MenuAccountBridge;

public final class SkinProfileService {
    private static final SkinProfileService INSTANCE = new SkinProfileService();

    private SkinProfileService() {}

    public static SkinProfileService getInstance() {
        return INSTANCE;
    }

    public GameProfile profile(String username) {
        return MenuAccountBridge.profile(username);
    }
}
