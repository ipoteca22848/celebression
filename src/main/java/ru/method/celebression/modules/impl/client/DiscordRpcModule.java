package ru.method.celebression.modules.impl.client;

import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.utils.client.discord.DiscordRichPresenceUtil;

public class DiscordRpcModule
extends Module {
    public DiscordRpcModule() {
        super("DiscordRPC", ModuleCategory.qn);
    }

    @Override
    public void setToggled(boolean enabled) {
        if (ru.method.celebression.service.UnhookService.isActive()) {
            if (isToggled()) super.setToggled(false);
            return;
        }
        if (!isToggled()) super.setToggled(true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        DiscordRichPresenceUtil.discordrpc();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        DiscordRichPresenceUtil.shutdownDiscord();
    }
}

