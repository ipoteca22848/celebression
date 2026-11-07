package ru.method.celebression.punchy.compat;

import java.lang.reflect.Field;

public final class SpellEngineCompat {
    private static final String CLIENT_CLASS = "net.spell_engine.client.SpellEngineClient";
    private static final String CONFIG_FIELD = "config";
    private static final String FIRST_PERSON_FIELD = "firstPersonAnimations";
    private static boolean available = false;
    private static boolean initialized = false;
    private static Object previousFirstPersonMode;

    private SpellEngineCompat() {
    }

    public static void initialize() {
        if (!initialized) {
            initialized = true;
            available = SpellEngineCompat.isClassPresent("net.spell_engine.client.SpellEngineClient");
        }
    }

    public static boolean isAvailable() {
        return available;
    }

    public static void applyFirstPersonAnimations(boolean bl) {
        if (available) {
            try {
                Class<?> clazz = Class.forName("net.spell_engine.client.SpellEngineClient");
                Field field = clazz.getField("config");
                Object object = field.get(null);
                if (object == null) {
                    return;
                }
                Field field2 = object.getClass().getField("firstPersonAnimations");
                if (bl) {
                    Object object2;
                    if (previousFirstPersonMode == null) {
                        previousFirstPersonMode = field2.get(object);
                    }
                    if ((object2 = SpellEngineCompat.findEnumValue(field2.getType(), "NO")) != null) {
                        field2.set(object, object2);
                    }
                } else if (previousFirstPersonMode != null) {
                    field2.set(object, previousFirstPersonMode);
                    previousFirstPersonMode = null;
                }
            }
            catch (Exception exception) {
            }
        }
    }

    private static boolean isClassPresent(String string) {
        try {
            Class.forName(string);
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    private static Object findEnumValue(Class<?> clazz, String string) {
        if (clazz != null && string != null && !string.isEmpty()) {
            if (!clazz.isEnum()) {
                return null;
            }
            Object[] objArray = clazz.getEnumConstants();
            if (objArray == null) {
                return null;
            }
            for (Object obj : objArray) {
                if (obj == null || !string.equalsIgnoreCase(obj.toString())) continue;
                return obj;
            }
            return null;
        }
        return null;
    }
}
