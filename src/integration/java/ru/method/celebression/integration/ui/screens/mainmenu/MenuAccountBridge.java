package ru.method.celebression.integration.ui.screens.mainmenu;

import com.mojang.authlib.GameProfile;
import ru.method.celebression.integration.ui.manager.ClientManager;
import ru.method.celebression.integration.ui.manager.Manager;
import ru.method.celebression.integration.ui.screens.mainmenu.domain.Account;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MenuAccountBridge {
    private MenuAccountBridge() {}

    public static List<Account> accounts() {
        List<Account> result = new ArrayList<>();
        long now = System.currentTimeMillis();
        for (String username : Manager.ACCOUNT_MANAGER.getAccounts()) {
            result.add(new Account(username, now));
        }
        return result;
    }

    public static boolean add(String raw) {
        if (raw == null) {
            return false;
        }
        String username = raw.trim();
        if (username.isEmpty() || username.length() > 16) {
            return false;
        }
        if (!username.chars().allMatch(c -> c == '_' || c == '-' || Character.isLetterOrDigit(c))) {
            return false;
        }
        if (Manager.ACCOUNT_MANAGER.getAccounts().stream().anyMatch(a -> a.equalsIgnoreCase(username))) {
            return false;
        }
        Manager.ACCOUNT_MANAGER.addAccount(username);
        return true;
    }

    public static void select(String username) {
        if (username == null || username.isBlank()) {
            return;
        }
        Manager.ACCOUNT_MANAGER.setLastSelectedAccount(username);
        ClientManager.loginAccount(username);
    }

    public static String selectedUsername() {
        return Manager.ACCOUNT_MANAGER.getLastSelectedAccount();
    }

    public static void reload() {
        Manager.ACCOUNT_MANAGER.load();
    }

    public static void saveAsync() {
        Manager.ACCOUNT_MANAGER.save();
    }

    public static GameProfile profile(String username) {
        UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
        return new GameProfile(uuid, username);
    }
}
