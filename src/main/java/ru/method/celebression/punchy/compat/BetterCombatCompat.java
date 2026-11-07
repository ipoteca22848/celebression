package ru.method.celebression.punchy.compat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.state.AttackActionTracker;
import ru.method.celebression.punchy.compat.FirstPersonModelCompat;
import ru.method.celebression.punchy.config.PunchyConfig;

public final class BetterCombatCompat {
    private static final String EVENTS_CLASS = "net.bettercombat.api.client.BetterCombatClientEvents";
    private static final String ATTACK_START_LISTENER = "net.bettercombat.api.client.BetterCombatClientEvents$PlayerAttackStart";
    private static final String ATTACK_HIT_LISTENER = "net.bettercombat.api.client.BetterCombatClientEvents$PlayerAttackHit";
    private static final String CLIENT_MOD_CLASS = "net.bettercombat.client.BetterCombatClientMod";
    private static final String PLAYER_ANIM_ACCESS = "com.zigythebird.playeranim.api.PlayerAnimationAccess";
    private static final String WEAPON_REGISTRY_CLASS = "net.bettercombat.logic.WeaponRegistry";
    private static boolean available = false;
    private static boolean initialized = false;
    private static Object previousFirstPersonMode;
    private static Boolean previousShowArms;
    private static Boolean previousShowOtherHand;
    private static Method weaponAttributesMethod;

    private BetterCombatCompat() {
    }

    public static void initialize() {
        if (!initialized) {
            initialized = true;
            available = BetterCombatCompat.isClassPresent("net.bettercombat.api.client.BetterCombatClientEvents");
            if (available) {
                BetterCombatCompat.applyVanillaItemDisplay(PunchyConfig.isBetterCombatCompatEnabled());
                try {
                    Class<?> clazz = Class.forName("net.bettercombat.api.client.BetterCombatClientEvents");
                    Field field = clazz.getField("ATTACK_START");
                    Object object2 = field.get(null);
                    Class<?> clazz2 = Class.forName("net.bettercombat.api.client.BetterCombatClientEvents$PlayerAttackStart");
                    Method method2 = BetterCombatCompat.findRegisterMethod(object2, clazz2);
                    if (method2 == null) {
                        available = false;
                        return;
                    }
                    Object object3 = Proxy.newProxyInstance(clazz2.getClassLoader(), new Class[]{clazz2}, (object, method, objectArray) -> {
                        if ("onPlayerAttackStart".equals(method.getName()) && objectArray != null && objectArray.length >= 2) {
                            BetterCombatCompat.handleAttackStart(objectArray[0], objectArray[1]);
                        }
                        return null;
                    });
                    method2.invoke(object2, object3);
                    Field field2 = clazz.getField("ATTACK_HIT");
                    Object object4 = field2.get(null);
                    Class<?> clazz3 = Class.forName("net.bettercombat.api.client.BetterCombatClientEvents$PlayerAttackHit");
                    Method method3 = BetterCombatCompat.findRegisterMethod(object4, clazz3);
                    if (method3 == null) {
                        available = false;
                        return;
                    }
                    Object object5 = Proxy.newProxyInstance(clazz3.getClassLoader(), new Class[]{clazz3}, (object, method, objectArray) -> {
                        if ("onPlayerAttackStart".equals(method.getName()) && objectArray != null && objectArray.length >= 2) {
                            BetterCombatCompat.handleAttackHit(objectArray[0], objectArray[1]);
                        }
                        return null;
                    });
                    method3.invoke(object4, object5);
                }
                catch (Exception exception) {
                    available = false;
                }
            }
        }
    }

    public static boolean isAvailable() {
        return available;
    }

    public static boolean isWeaponStack(class_1799 class_17992) {
        if (available && class_17992 != null && !class_17992.method_7960()) {
            try {
                Object object;
                if (weaponAttributesMethod == null) {
                    object = Class.forName("net.bettercombat.logic.WeaponRegistry");
                    weaponAttributesMethod = ((Class)object).getMethod("getAttributes", class_1799.class);
                }
                return (object = weaponAttributesMethod.invoke(null, class_17992)) != null;
            }
            catch (Exception exception) {
                return false;
            }
        }
        return false;
    }

    public static void forceStopAttackAnimation(class_746 class_7462) {
        if (available && class_7462 != null && PunchyConfig.isBetterCombatCompatEnabled() && !FirstPersonModelCompat.isRenderingPlayer() && !FirstPersonModelCompat.shouldApplyThirdPersonAnimations(class_310.method_1551()) && !BetterCombatCompat.isPlayerBlacklisted((class_1657)class_7462)) {
            BetterCombatCompat.stopBetterCombatAnimation(class_7462);
        }
    }

    public static void suppressItemPoses(class_746 class_7462) {
        if (available && class_7462 != null && PunchyConfig.isBetterCombatCompatEnabled() && !FirstPersonModelCompat.isRenderingPlayer() && !FirstPersonModelCompat.shouldApplyThirdPersonAnimations(class_310.method_1551()) && !BetterCombatCompat.isPlayerBlacklisted((class_1657)class_7462)) {
            try {
                Class<?> clazz = Class.forName("com.zigythebird.playeranim.api.PlayerAnimationAccess");
                Method method = BetterCombatCompat.findMethod(clazz, "getPlayerAnimationLayer", 2);
                if (method == null) {
                    return;
                }
                Object object = method.invoke(null, class_7462, BetterCombatCompat.createIdentifier("bettercombat", "attack", method));
                BetterCombatCompat.stopLayerAnimation(object);
                Object object2 = method.invoke(null, class_7462, BetterCombatCompat.createIdentifier("bettercombat", "pose_main_hand_body", method));
                Object object3 = method.invoke(null, class_7462, BetterCombatCompat.createIdentifier("bettercombat", "pose_main_hand_item", method));
                Object object4 = method.invoke(null, class_7462, BetterCombatCompat.createIdentifier("bettercombat", "pose_off_hand_body", method));
                Object object5 = method.invoke(null, class_7462, BetterCombatCompat.createIdentifier("bettercombat", "pose_off_hand_item", method));
                boolean bl = class_7462.method_6068() == class_1306.field_6182;
                BetterCombatCompat.clearPoseLayer(object2, bl);
                BetterCombatCompat.clearPoseLayer(object3, bl);
                BetterCombatCompat.clearPoseLayer(object4, bl);
                BetterCombatCompat.clearPoseLayer(object5, bl);
            }
            catch (Exception exception) {
            }
        }
    }

    public static void applyVanillaItemDisplay(boolean bl) {
        if (available) {
            try {
                Class<?> clazz = Class.forName("net.bettercombat.client.BetterCombatClientMod");
                Field field = clazz.getField("config");
                Object object = field.get(null);
                if (object == null) {
                    return;
                }
                Field field2 = object.getClass().getField("firstPersonAnimations");
                Field field3 = object.getClass().getField("isShowingArmsInFirstPerson");
                Field field4 = object.getClass().getField("isShowingOtherHandFirstPerson");
                if (bl) {
                    Boolean bl2;
                    Object object2;
                    if (previousFirstPersonMode == null) {
                        previousFirstPersonMode = field2.get(object);
                    }
                    if (previousShowArms == null) {
                        object2 = field3.get(object);
                        Boolean bl3 = previousShowArms = object2 instanceof Boolean ? (bl2 = (Boolean)object2) : null;
                    }
                    if (previousShowOtherHand == null) {
                        object2 = field4.get(object);
                        Boolean bl4 = previousShowOtherHand = object2 instanceof Boolean ? (bl2 = (Boolean)object2) : null;
                    }
                    Object firstPersonMode = BetterCombatCompat.findEnumValue(field2.getType(), "NO");
                    if (firstPersonMode != null) {
                        field2.set(object, firstPersonMode);
                    }
                    field3.set(object, false);
                    field4.set(object, true);
                } else if (previousFirstPersonMode != null) {
                    field2.set(object, previousFirstPersonMode);
                    previousFirstPersonMode = null;
                    if (previousShowArms != null) {
                        field3.set(object, previousShowArms);
                    }
                    if (previousShowOtherHand != null) {
                        field4.set(object, previousShowOtherHand);
                    }
                    previousShowArms = null;
                    previousShowOtherHand = null;
                }
            }
            catch (Exception exception) {
            }
        }
    }

    private static void handleAttackStart(Object object, Object object2) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null && PunchyConfig.isBetterCombatCompatEnabled() && !BetterCombatCompat.isPlayerBlacklisted((class_1657)class_3102.field_1724) && class_3102.field_1690.method_31044().method_31034()) {
            AttackActionTracker.recordCriticalCandidate(class_3102);
            if (!FirstPersonModelCompat.isRenderingPlayer() && !FirstPersonModelCompat.shouldApplyThirdPersonAnimations(class_3102)) {
                BetterCombatCompat.stopBetterCombatAnimation(class_3102.field_1724);
            }
        }
    }

    private static void handleAttackHit(Object object, Object object2) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null && PunchyConfig.isBetterCombatCompatEnabled() && !BetterCombatCompat.isPlayerBlacklisted((class_1657)class_3102.field_1724) && class_3102.field_1690.method_31044().method_31034()) {
            class_1268 class_12682 = BetterCombatCompat.readIsOffHand(object2) ? class_1268.field_5810 : class_1268.field_5808;
            AttackActionTracker.recordAttack(class_12682);
            if (!FirstPersonModelCompat.isRenderingPlayer() && !FirstPersonModelCompat.shouldApplyThirdPersonAnimations(class_3102)) {
                BetterCombatCompat.stopBetterCombatAnimation(class_3102.field_1724);
            }
        }
    }

    private static boolean isPlayerBlacklisted(class_1657 class_16572) {
        return class_16572 == null ? false : PunchyConfig.isHandBlacklisted(class_16572, class_1268.field_5808) || PunchyConfig.isHandBlacklisted(class_16572, class_1268.field_5810);
    }

    private static boolean readIsOffHand(Object object) {
        if (object == null) {
            return false;
        }
        try {
            Boolean bl;
            Method method = object.getClass().getMethod("isOffHand", new Class[0]);
            Object object2 = method.invoke(object, new Object[0]);
            return object2 instanceof Boolean && (bl = (Boolean)object2) != false;
        }
        catch (Exception exception) {
            return false;
        }
    }

    private static void stopBetterCombatAnimation(class_746 class_7462) {
        try {
            Method method = class_7462.getClass().getMethod("stopAttackAnimation", Float.TYPE);
            method.invoke((Object)class_7462, Float.valueOf(0.0f));
        }
        catch (Exception exception) {
        }
    }

    private static Method findRegisterMethod(Object object, Class<?> clazz) {
        if (object != null && clazz != null) {
            for (Method method : object.getClass().getMethods()) {
                Class<?> clazz2;
                if (method.getParameterCount() != 1 || !(clazz2 = method.getParameterTypes()[0]).isAssignableFrom(clazz) && !clazz.isAssignableFrom(clazz2)) continue;
                return method;
            }
            return null;
        }
        return null;
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

    private static Object createIdentifier(String string, String string2, Method method) {
        if (method == null) {
            return null;
        }
        Class<?> clazz = method.getParameterTypes()[1];
        try {
            if ("net.minecraft.resources.ResourceLocation".equals(clazz.getName())) {
                return class_2960.method_60655((String)string, (String)string2);
            }
        }
        catch (Exception exception) {
        }
        try {
            if ("net.minecraft.util.ResourceLocation".equals(clazz.getName())) {
                Method method2 = clazz.getMethod("of", String.class, String.class);
                return method2.invoke(null, string, string2);
            }
        }
        catch (Exception exception) {
        }
        return null;
    }

    private static void clearPoseLayer(Object object, boolean bl) {
        if (object != null) {
            try {
                Method method = BetterCombatCompat.findMethod(object.getClass(), "setPose", 2);
                if (method != null) {
                    method.invoke(object, null, bl);
                }
            }
            catch (Exception exception) {
            }
        }
    }

    private static void stopLayerAnimation(Object object) {
        if (object != null) {
            try {
                Method method = BetterCombatCompat.findMethod(object.getClass(), "stopTriggeredAnimation", 0);
                if (method != null) {
                    method.invoke(object, new Object[0]);
                }
            }
            catch (Exception exception) {
            }
        }
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
