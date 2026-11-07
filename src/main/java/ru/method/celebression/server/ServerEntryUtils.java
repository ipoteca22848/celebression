package ru.method.celebression.server;

import net.minecraft.class_4267;
import net.minecraft.class_642;
import ru.method.celebression.api.mixins.accessors.IServerEntry;

public final class ServerEntryUtils {
    private ServerEntryUtils() {
    }

    public static class_642 a(class_4267.class_504 class_5042) {
        if (!(class_5042 instanceof class_4267.class_4270)) {
            return null;
        }
        class_4267.class_4270 class_42702 = (class_4267.class_4270)class_5042;
        return ((IServerEntry)class_42702).celebression$getServer();
    }
}

