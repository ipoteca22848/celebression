package ru.method.celebression.service;

import java.util.LinkedHashSet;
import net.minecraft.class_310;
import net.minecraft.class_442;
import ru.method.celebression.Celebression;
import ru.method.celebression.module.Module;
import ru.method.celebression.manager.ClickRippleManager;

/** Session-only deactivation. Fabric classes and installed mixins remain loaded. */
public final class UnhookService {
    private static final SessionDeactivation STATE = new SessionDeactivation();
    private UnhookService() {}
    public static boolean isActive() { return STATE.isActive(); }
    public static void disable() {
        Celebression client = Celebression.getInstance();
        if (isActive() || client == null) return;
        // Flush enabled state before switching off, so the next launch restores the user's settings.
        STATE.deactivate(() -> {
            if (client.getConfigManager() != null) client.getConfigManager().dV();
        }, () -> deactivateClient(client));
    }
    private static void deactivateClient(Celebression client) {
        client.setPanic(true);
        boolean oldFeedback = client.isSuppressToggleFeedback();
        client.setSuppressToggleFeedback(true);
        try {
            var modules = new LinkedHashSet<Module>(client.getModuleManager().getModules());
            modules.addAll(client.getHudManager().fq());
            for (Module module : modules) {
                try {
                    module.disableForSession();
                } catch (Exception error) {
                    Celebression.LOGGER.warn("Module cleanup failed: " + module.getName(), error);
                } finally {
                    client.getEventHandler().unsubscribe(module);
                }
            }
            ClickRippleManager.clear();
            client.getSettingsWindows().clear();
            if (client.getDiscordManager() != null) client.getDiscordManager().stopRPC();
            if (client.getUserSyncManager() != null) client.getUserSyncManager().shutdown();
            if (client.getUsageStatsManager() != null) client.getUsageStatsManager().shutdown();
            class_310 minecraft = class_310.method_1551();
            if (minecraft != null) minecraft.method_1507(minecraft.field_1687 == null ? new class_442() : null);
        } finally {
            client.setSuppressToggleFeedback(oldFeedback);
        }
        Celebression.LOGGER.info("celebression disabled until restart");
    }
}
