package ru.method.celebression.punchy.compat.entityculling;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Supplier;
import ru.method.celebression.punchy.platform.Services;

public final class EntityCullingCompat {
    private static final String MOD_ID = "entityculling";
    private static final String MOD_BASE = "dev.tr7zw.entityculling.EntityCullingModBase";
    private static volatile boolean initialized;
    private static volatile boolean applied;
    private static volatile boolean loaded;
    private static volatile Field configField;
    private static volatile Field frustumField;

    private EntityCullingCompat() {
    }

    public static void tick() {
        if (!applied) {
            if (!EntityCullingCompat.ensureLoaded()) {
                applied = true;
            } else {
                EntityCullingCompat.tryWhitelistEnchantingTable();
            }
        }
    }

    public static <T> T withBlockEntityFrustumCullingDisabled(Supplier<T> supplier) {
        if (supplier == null) {
            return null;
        }
        if (!EntityCullingCompat.ensureLoaded()) {
            return supplier.get();
        }
        try {
            T t;
            Object object = EntityCullingCompat.resolveConfig();
            if (object == null) {
                return supplier.get();
            }
            Field field = EntityCullingCompat.resolveFrustumField(object);
            if (field == null) {
                return supplier.get();
            }
            boolean bl = field.getBoolean(object);
            if (!bl) {
                return supplier.get();
            }
            field.setBoolean(object, false);
            try {
                t = supplier.get();
            }
            finally {
                field.setBoolean(object, bl);
            }
            return t;
        }
        catch (Throwable throwable) {
            return supplier.get();
        }
    }

    private static void tryWhitelistEnchantingTable() {
        try {
            Class<?> clazz = Class.forName("dev.tr7zw.entityculling.EntityCullingModBase");
            Object object2 = clazz.getField("instance").get(null);
            if (object2 == null) {
                return;
            }
            Method method = clazz.getMethod("addDynamicBlockEntityWhitelist", Function.class);
            Function<Object, Boolean> function = object -> {
                if (object == null) {
                    return false;
                }
                String string = object.getClass().getName();
                return string.endsWith("EnchantmentTableBlockEntity") || string.endsWith("EnchantingTableBlockEntity");
            };
            method.invoke(object2, function);
            applied = true;
        }
        catch (Throwable ignored) {
        }
    }

    private static boolean ensureLoaded() {
        if (!initialized) {
            initialized = true;
            loaded = Services.PLATFORM.isModLoaded("entityculling");
        }
        return loaded;
    }

    private static Object resolveConfig() throws ReflectiveOperationException {
        Class<?> clazz = Class.forName("dev.tr7zw.entityculling.EntityCullingModBase");
        Object object = clazz.getField("instance").get(null);
        if (object == null) {
            return null;
        }
        Field field = configField;
        if (field == null) {
            configField = field = clazz.getSuperclass().getField("config");
        }
        return field.get(object);
    }

    private static Field resolveFrustumField(Object object) throws ReflectiveOperationException {
        if (object == null) {
            return null;
        }
        Field field = frustumField;
        if (field == null) {
            frustumField = field = object.getClass().getField("blockEntityFrustumCulling");
        }
        return field;
    }
}
