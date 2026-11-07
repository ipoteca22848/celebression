package ru.method.celebression.punchy.mixin.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_10004;
import net.minecraft.class_10261;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_630;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.render.BoatLidState;
import ru.method.celebression.punchy.client.render.InHandLidPhysics;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.mixin.client.accessor.ModelPartAccessor;

@Mixin(value={class_10261.class})
public class AbstractBoatModelMixin {
    @Shadow
    private class_630 field_54480;
    @Shadow
    private class_630 field_54481;
    @Unique
    private static final Map<Object, class_630> LID_CACHE = new IdentityHashMap<Object, class_630>();
    @Unique
    private static final String[] LID_NAMES = new String[]{"chest_lid", "lid", "chestlid", "chest_top", "chest", "top"};

    @Inject(method={"setAngles(Lnet/minecraft/client/render/entity/state/BoatEntityRenderState;)V"}, at={@At(value="TAIL")})
    private void punchy$applyPaddlePhysics(class_10004 class_100042, CallbackInfo callbackInfo) {
        BoatLidState boatLidState;
        if (!PunchyConfig.disableBoatFirstPersonAnimations() && class_100042 instanceof BoatLidState && (boatLidState = (BoatLidState)class_100042).punchy$isInHand()) {
            double d = boatLidState.punchy$getVy();
            float f = d < -0.01 ? (float)(-d) * 1.275f : 0.0f;
            float f2 = 0.0f;
            float f3 = class_3532.method_15363((float)(f + f2), (float)-1.275f, (float)1.275f);
            this.field_54480.field_3654 += f3;
            this.field_54481.field_3654 += f3;
            class_630 class_6302 = AbstractBoatModelMixin.findChestRoot(this);
            if (class_6302 != null) {
                AbstractBoatModelMixin.setPartVisible(class_6302, false);
                boatLidState.punchy$setChestPose(class_6302.field_3657, class_6302.field_3656, class_6302.field_3655, class_6302.field_3654, class_6302.field_3675, class_6302.field_3674);
                boatLidState.punchy$setChestPoseValid(true);
            } else {
                boatLidState.punchy$setChestPoseValid(false);
            }
            class_630 class_6303 = AbstractBoatModelMixin.resolveLidPart(this);
            if (class_6303 != null) {
                float f4 = InHandLidPhysics.computeFallTarget((class_1657)class_310.method_1551().field_1724);
                float f5 = boatLidState.punchy$getLidOpen();
                long l = System.nanoTime();
                float f6 = InHandLidPhysics.updateLid(f5, f4, boatLidState.punchy$getLidLastUpdateNanos(), l);
                boatLidState.punchy$setLidOpen(f6);
                boatLidState.punchy$setLidLastUpdateNanos(l);
                boatLidState.punchy$setLidApplied(true);
                class_6303.field_3654 = -f6 * 1.5707964f;
            }
        }
    }

    @Unique
    private static class_630 resolveLidPart(Object object) {
        String string;
        if (object == null) {
            return null;
        }
        class_630 class_6302 = LID_CACHE.get(object);
        if (class_6302 != null) {
            return class_6302;
        }
        class_630 class_6303 = AbstractBoatModelMixin.findLidField(object);
        if (class_6303 != null) {
            LID_CACHE.put(object, class_6303);
            return class_6303;
        }
        class_630 class_6304 = AbstractBoatModelMixin.findRootPart(object);
        if (class_6304 == null) {
            return null;
        }
        String[] stringArray = LID_NAMES;
        int n = stringArray.length;
        for (int i = 0; i < n && (class_6303 = AbstractBoatModelMixin.findChildRecursive(class_6304, string = stringArray[i])) == null; ++i) {
        }
        if (class_6303 != null) {
            LID_CACHE.put(object, class_6303);
        }
        return class_6303;
    }

    @Unique
    private static class_630 findLidField(Object object) {
        for (Class<?> clazz = object.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (Field field : clazz.getDeclaredFields()) {
                String string;
                if (!class_630.class.isAssignableFrom(field.getType()) || !(string = field.getName().toLowerCase(Locale.ROOT)).contains("lid") && !string.contains("chest")) continue;
                try {
                    field.setAccessible(true);
                    class_630 class_6302 = (class_630)field.get(object);
                    if (class_6302 == null) continue;
                    if (string.contains("lid")) {
                        return class_6302;
                    }
                    for (String string2 : LID_NAMES) {
                        class_630 class_6303 = AbstractBoatModelMixin.findChildRecursive(class_6302, string2);
                        if (class_6303 == null) continue;
                        return class_6303;
                    }
                }
                catch (IllegalAccessException illegalAccessException) {
                }
            }
        }
        return null;
    }

    @Unique
    private static class_630 findChestRoot(Object object) {
        class_630 class_6302 = AbstractBoatModelMixin.findRootPart(object);
        if (class_6302 == null) {
            return null;
        }
        Map<String, class_630> children = AbstractBoatModelMixin.getChildren(class_6302);
        for (Map.Entry<String, class_630> entry : children.entrySet()) {
            String childName = entry.getKey().toLowerCase(Locale.ROOT);
            if (childName.contains("chest") && !childName.contains("lid")) {
                return entry.getValue();
            }
        }
        for (class_630 child : children.values()) {
            if (AbstractBoatModelMixin.findChildRecursive(child, "lid") != null) {
                return child;
            }
        }
        return null;
    }

    @Unique
    private static void setPartVisible(class_630 class_6302, boolean bl) {
        if (class_6302 != null) {
            try {
                Field field = class_630.class.getDeclaredField("visible");
                field.setAccessible(true);
                field.setBoolean(class_6302, bl);
            }
            catch (Throwable throwable) {
                try {
                    Field field = class_630.class.getDeclaredField("skipDraw");
                    field.setAccessible(true);
                    field.setBoolean(class_6302, !bl);
                }
                catch (Throwable throwable2) {
                }
            }
        }
    }

    @Unique
    private static class_630 findRootPart(Object object) {
        for (Class<?> clazz = object.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod("root", new Class[0]);
                if (class_630.class.isAssignableFrom(method.getReturnType())) {
                    method.setAccessible(true);
                    return (class_630)method.invoke(object, new Object[0]);
                }
            }
            catch (Throwable throwable) {
            }
            for (Field field : clazz.getDeclaredFields()) {
                if (!class_630.class.isAssignableFrom(field.getType())) continue;
                try {
                    field.setAccessible(true);
                    return (class_630)field.get(object);
                }
                catch (IllegalAccessException illegalAccessException) {
                }
            }
        }
        return null;
    }

    @Unique
    private static class_630 findChildRecursive(class_630 class_6302, String string) {
        if (class_6302 == null || string == null) {
            return null;
        }
        Map<String, class_630> children = AbstractBoatModelMixin.getChildren(class_6302);
        class_630 directChild = children.get(string);
        if (directChild != null) {
            return directChild;
        }
        String normalizedName = string.toLowerCase(Locale.ROOT);
        for (Map.Entry<String, class_630> entry : children.entrySet()) {
            if (entry.getKey().toLowerCase(Locale.ROOT).contains(normalizedName)) {
                return entry.getValue();
            }
        }
        for (class_630 child : children.values()) {
            class_630 match = AbstractBoatModelMixin.findChildRecursive(child, normalizedName);
            if (match != null) {
                return match;
            }
        }
        return null;
    }

    @Unique
    private static Map<String, class_630> getChildren(class_630 class_6302) {
        return ((ModelPartAccessor)(Object)class_6302).punchy$getChildren();
    }
}
