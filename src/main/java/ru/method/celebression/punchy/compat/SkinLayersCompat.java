package ru.method.celebression.punchy.compat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.class_591;
import net.minecraft.class_630;
import net.minecraft.class_742;
import ru.method.celebression.punchy.platform.Services;

public final class SkinLayersCompat {
    private static final String MOD_ID = "skinlayers3d";
    private static boolean checkedAvailability;
    private static boolean available;
    private static boolean reflectionReady;
    private static boolean reflectionFailed;
    private static Class<?> playerSettingsClass;
    private static Class<?> modelPartInjectorClass;
    private static Method setup3dLayersMethod;
    private static Method setInjectedMeshMethod;
    private static Method getLeftArmMeshMethod;
    private static Method getRightArmMeshMethod;
    private static Field configField;
    private static Field enableLeftSleeveField;
    private static Field enableRightSleeveField;
    private static Field fpLeftArmField;
    private static Field fpLeftArmSlimField;
    private static Field fpRightArmField;
    private static Field fpRightArmSlimField;

    private SkinLayersCompat() {
    }

    public static boolean isAvailable() {
        if (!checkedAvailability) {
            checkedAvailability = true;
            available = Services.PLATFORM != null && Services.PLATFORM.isModLoaded("skinlayers3d");
        }
        return available;
    }

    public static void applyFirstPersonSleeves(class_742 class_7422, class_591 class_5912, boolean bl, boolean bl2, boolean bl3) {
        if (class_5912 != null) {
            SkinLayersCompat.clearInjectedMesh(class_5912.field_3484);
            SkinLayersCompat.clearInjectedMesh(class_5912.field_3486);
            if (SkinLayersCompat.isAvailable() && class_7422 != null && SkinLayersCompat.ensureReflection() && playerSettingsClass.isInstance(class_7422)) {
                try {
                    Object object;
                    Boolean bl4;
                    Object object2 = setup3dLayersMethod.invoke(null, class_7422, class_7422, bl);
                    if (!(object2 instanceof Boolean) || !(bl4 = (Boolean)object2).booleanValue()) {
                        return;
                    }
                    if (bl2 && SkinLayersCompat.isSleeveEnabled(true)) {
                        object2 = getLeftArmMeshMethod.invoke((Object)class_7422, new Object[0]);
                        object = (bl ? fpLeftArmSlimField : fpLeftArmField).get(null);
                        setInjectedMeshMethod.invoke((Object)class_5912.field_3484, object2, object);
                    }
                    if (bl3 && SkinLayersCompat.isSleeveEnabled(false)) {
                        object2 = getRightArmMeshMethod.invoke((Object)class_7422, new Object[0]);
                        object = (bl ? fpRightArmSlimField : fpRightArmField).get(null);
                        setInjectedMeshMethod.invoke((Object)class_5912.field_3486, object2, object);
                    }
                }
                catch (Throwable throwable) {
                }
            }
        }
    }

    private static void clearInjectedMesh(class_630 class_6302) {
        if (class_6302 != null && SkinLayersCompat.isAvailable() && SkinLayersCompat.ensureReflection() && modelPartInjectorClass.isInstance(class_6302)) {
            try {
                setInjectedMeshMethod.invoke((Object)class_6302, new Object[]{null, null});
            }
            catch (Throwable throwable) {
            }
        }
    }

    private static boolean ensureReflection() {
        if (reflectionReady) {
            return true;
        }
        if (reflectionFailed) {
            return false;
        }
        try {
            playerSettingsClass = Class.forName("dev.tr7zw.skinlayers.accessor.PlayerSettings");
            modelPartInjectorClass = Class.forName("dev.tr7zw.skinlayers.accessor.ModelPartInjector");
            Class<?> clazz = Class.forName("dev.tr7zw.skinlayers.SkinUtil");
            Class<?> clazz2 = Class.forName("dev.tr7zw.skinlayers.SkinLayersModBase");
            Class<?> clazz3 = Class.forName("dev.tr7zw.skinlayers.api.OffsetProvider");
            setup3dLayersMethod = SkinLayersCompat.findSetup3dLayers(clazz);
            if (setup3dLayersMethod == null) {
                throw new NoSuchMethodException("setup3dLayers(player, settings, slim)");
            }
            setInjectedMeshMethod = modelPartInjectorClass.getMethod("setInjectedMesh", Class.forName("dev.tr7zw.skinlayers.api.Mesh"), clazz3);
            getLeftArmMeshMethod = playerSettingsClass.getMethod("getLeftArmMesh", new Class[0]);
            getRightArmMeshMethod = playerSettingsClass.getMethod("getRightArmMesh", new Class[0]);
            configField = clazz2.getField("config");
            Class<?> clazz4 = configField.getType();
            enableLeftSleeveField = clazz4.getField("enableLeftSleeve");
            enableRightSleeveField = clazz4.getField("enableRightSleeve");
            fpLeftArmField = clazz3.getField("FIRSTPERSON_LEFT_ARM");
            fpLeftArmSlimField = clazz3.getField("FIRSTPERSON_LEFT_ARM_SLIM");
            fpRightArmField = clazz3.getField("FIRSTPERSON_RIGHT_ARM");
            fpRightArmSlimField = clazz3.getField("FIRSTPERSON_RIGHT_ARM_SLIM");
            reflectionReady = true;
            return true;
        }
        catch (Throwable throwable) {
            reflectionFailed = true;
            return false;
        }
    }

    private static Method findSetup3dLayers(Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            Class<?>[] classArray;
            if (!"setup3dLayers".equals(method.getName()) || (classArray = method.getParameterTypes()).length != 3 || !classArray[1].getName().equals("dev.tr7zw.skinlayers.accessor.PlayerSettings") || classArray[2] != Boolean.TYPE && classArray[2] != Boolean.class) continue;
            return method;
        }
        return null;
    }

    private static boolean isSleeveEnabled(boolean bl) throws IllegalAccessException {
        Object object = configField.get(null);
        if (object == null) {
            return true;
        }
        return bl ? enableLeftSleeveField.getBoolean(object) : enableRightSleeveField.getBoolean(object);
    }
}

