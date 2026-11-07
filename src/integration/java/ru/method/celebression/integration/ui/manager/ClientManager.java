package ru.method.celebression.integration.ui.manager;
import java.nio.charset.StandardCharsets;
import java.util.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import ru.method.celebression.integration.ui.mixin.SessionAccessor;
import ru.method.celebression.integration.ui.util.KeyMappings;
public final class ClientManager {
    private ClientManager() {}
    public static void loginAccount(String name) {
        UUID id = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
        Session session = new Session(name, id, "invalid_token", Optional.empty(), Optional.empty(), Session.AccountType.MOJANG);
        ((SessionAccessor)MinecraftClient.getInstance()).celebression$setSession(session);
    }
    public static String getKey(int code) { return KeyMappings.keyMappings(code); }
}
