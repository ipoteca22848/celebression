package ru.method.celebression.punchy.compat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import ru.method.celebression.punchy.client.state.UseItemStateMachine;

public final class SwordBlockingMechanicsCompat {
    private static final String[] HANDLER_CLASSES;
    private static final String EVENT_RESULT_CLASS;
    private static boolean available;
    private static boolean initialized;
    private static Method isActiveItemStackBlocking;
    private static Method getParryStrengthScale;
    private static Object eventResultPass;
    private static class_1268 callbackBlockingHand;

    private SwordBlockingMechanicsCompat() {
    }

    public static void initialize() {
        if (!initialized) {
            initialized = true;
            Class<?> clazz = SwordBlockingMechanicsCompat.findClass(HANDLER_CLASSES);
            boolean bl = available = clazz != null;
            if (clazz != null) {
                try {
                    isActiveItemStackBlocking = SwordBlockingMechanicsCompat.findMethod(clazz, "isActiveItemStackBlocking", 1);
                    getParryStrengthScale = SwordBlockingMechanicsCompat.findMethod(clazz, "getParryStrengthScale", 1);
                }
                catch (Exception exception) {
                }
            }
        }
    }

    public static boolean isAvailable() {
        return available;
    }

    public static boolean shouldUseBlockingAnimation(class_1268 class_12682) {
        return class_12682 != null && callbackBlockingHand == class_12682;
    }

    public static void recordBlockingStart(class_1657 class_16572, class_1268 class_12682) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null && class_16572 == class_3102.field_1724 && class_12682 != null) {
            callbackBlockingHand = class_12682;
            UseItemStateMachine.requestSwordBlockingCompat(class_12682);
        }
    }

    public static void recordBlockingStop(class_1657 class_16572, class_1268 class_12682) {
        class_310 class_3102;
        if (class_16572 != null && class_12682 != null && (class_3102 = class_310.method_1551()) != null && class_3102.field_1724 != null && class_16572 == class_3102.field_1724 && callbackBlockingHand == class_12682) {
            callbackBlockingHand = null;
        }
    }

    public static boolean isBlocking(class_1309 class_13092) {
        if (!available || class_13092 == null) {
            return false;
        }
        if (isActiveItemStackBlocking == null) {
            return false;
        }
        try {
            Boolean bl;
            Object object = isActiveItemStackBlocking.invoke(null, class_13092);
            return object instanceof Boolean && (bl = (Boolean)object) != false;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static double getParryStrengthScale(class_1309 class_13092) {
        if (!available || class_13092 == null) {
            return 1.0;
        }
        if (getParryStrengthScale == null) {
            return 1.0;
        }
        try {
            Object object = getParryStrengthScale.invoke(null, class_13092);
            if (object instanceof Double) {
                Double d = (Double)object;
                return d;
            }
            if (object instanceof Float) {
                Float f = (Float)object;
                return f.doubleValue();
            }
        }
        catch (Exception exception) {
        }
        return 1.0;
    }

    public static Object getEventResultPass() {
        if (!available) {
            return null;
        }
        if (eventResultPass != null) {
            return eventResultPass;
        }
        try {
            Class<?> clazz = Class.forName("fuzs.puzzleslib.api.event.v1.core.EventResult");
            Field field = clazz.getField("PASS");
            eventResultPass = field.get(null);
            return eventResultPass;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Class<?> findClass(String ... stringArray) {
        if (stringArray == null) {
            return null;
        }
        for (String string : stringArray) {
            try {
                return Class.forName(string);
            }
            catch (ClassNotFoundException classNotFoundException) {
            }
        }
        return null;
    }

    private static Method findMethod(Class<?> clazz, String string, int n) {
        if (clazz != null && string != null) {
            for (Method method : clazz.getMethods()) {
                if (!method.getName().equals(string) || method.getParameterCount() != n) continue;
                return method;
            }
            return null;
        }
        return null;
    }

    static {
        EVENT_RESULT_CLASS = "fuzs.puzzleslib.api.event.v1.core.EventResult";
        HANDLER_CLASSES = new String[]{"fuzs.swordblockingmechanics.handler.SwordBlockingHandler", "fuzs.swordblockingmechanics.common.handler.SwordBlockingHandler"};
        available = false;
        initialized = false;
    }
}

