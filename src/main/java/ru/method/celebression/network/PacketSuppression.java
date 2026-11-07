package ru.method.celebression.network;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Generated;
import net.minecraft.class_2596;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class PacketSuppression
implements MinecraftClientAccess {
    private static final Set<class_2596<?>> aky = Collections.newSetFromMap(new ConcurrentHashMap());

    public static void a(class_2596<?> class_25962) {
        aky.add(class_25962);
        aiG.method_1562().method_52787(class_25962);
    }

    public static void b(class_2596<?> class_25962) {
        aiG.method_1562().method_52787(class_25962);
    }

    public static boolean c(class_2596<?> class_25962) {
        return aky.remove(class_25962);
    }

    @Generated
    private PacketSuppression() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    @Generated
    public static Set<class_2596<?>> rX() {
        return aky;
    }
}

