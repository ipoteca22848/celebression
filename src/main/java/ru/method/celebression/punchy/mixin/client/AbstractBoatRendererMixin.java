package ru.method.celebression.punchy.mixin.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_10004;
import net.minecraft.class_10255;
import net.minecraft.class_10256;
import net.minecraft.class_10262;
import net.minecraft.class_583;
import net.minecraft.class_630;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.method.celebression.punchy.client.render.BoatInHandContext;
import ru.method.celebression.punchy.client.render.BoatLidState;
import ru.method.celebression.punchy.mixin.client.accessor.ModelPartAccessor;

@Mixin(value={class_10262.class})
public abstract class AbstractBoatRendererMixin {
    @Unique
    private static final Map<Object, class_630> RENDERER_LID_CACHE = new IdentityHashMap<Object, class_630>();
    @Unique
    private static final String[] LID_NAMES = new String[]{"chest_lid", "lid", "chestlid", "chest_top", "chest", "top"};

    @Shadow
    protected abstract class_583<?> method_64517();

    @Inject(method={"updateRenderState(Lnet/minecraft/entity/vehicle/AbstractBoatEntity;Lnet/minecraft/client/render/entity/state/BoatEntityRenderState;F)V"}, at={@At(value="TAIL")})
    private void punchy$storeBoatState(class_10255 class_102552, class_10004 class_100042, float f, CallbackInfo callbackInfo) {
        if (class_100042 instanceof BoatLidState) {
            BoatLidState boatLidState = (BoatLidState)class_100042;
            boatLidState.punchy$setInHand(BoatInHandContext.isActive());
            boatLidState.punchy$setVy(class_102552.method_18798().field_1351);
            boatLidState.punchy$setChestBoat(class_102552 instanceof class_10256);
            boatLidState.punchy$setLidApplied(false);
        }
    }

    @Unique
    private static class_630 resolveRendererLid(Object object) {
        if (object == null) {
            return null;
        }
        class_630 class_6302 = RENDERER_LID_CACHE.get(object);
        if (class_6302 != null) {
            return class_6302;
        }
        for (Class<?> clazz = object.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (Field field : clazz.getDeclaredFields()) {
                class_630 class_6303;
                class_630 class_6304;
                Object object2;
                field.setAccessible(true);
                try {
                    object2 = field.get(object);
                }
                catch (IllegalAccessException illegalAccessException) {
                    continue;
                }
                String string = field.getName().toLowerCase(Locale.ROOT);
                if (object2 instanceof class_583) {
                    class_583 class_5832 = (class_583)object2;
                    class_6304 = AbstractBoatRendererMixin.findLidInModel(class_5832);
                    if (class_6304 == null) continue;
                    RENDERER_LID_CACHE.put(object, class_6304);
                    return class_6304;
                }
                if (!(object2 instanceof class_630) || (class_6304 = AbstractBoatRendererMixin.findLidInPart(class_6303 = (class_630)object2, string)) == null) continue;
                RENDERER_LID_CACHE.put(object, class_6304);
                return class_6304;
            }
        }
        return null;
    }

    @Unique
    private static class_630 findLidInModel(class_583<?> class_5832) {
        if (class_5832 == null) {
            return null;
        }
        class_630 class_6302 = AbstractBoatRendererMixin.findRootPart(class_5832);
        if (class_6302 == null) {
            return null;
        }
        for (String string : LID_NAMES) {
            class_630 class_6303 = AbstractBoatRendererMixin.findChildRecursive(class_6302, string);
            if (class_6303 == null) continue;
            return class_6303;
        }
        return null;
    }

    @Unique
    private static class_630 findLidInPart(class_630 class_6302, String string) {
        if (class_6302 == null) {
            return null;
        }
        if (string != null && string.toLowerCase(Locale.ROOT).contains("lid")) {
            return class_6302;
        }
        for (String string2 : LID_NAMES) {
            class_630 class_6303 = AbstractBoatRendererMixin.findChildRecursive(class_6302, string2);
            if (class_6303 == null) continue;
            return class_6303;
        }
        return null;
    }

    @Unique
    private static class_630 findRootPart(class_583<?> class_5832) {
        for (Class<?> clazz = class_5832.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod("root", new Class[0]);
                if (class_630.class.isAssignableFrom(method.getReturnType())) {
                    method.setAccessible(true);
                    return (class_630)method.invoke(class_5832, new Object[0]);
                }
            }
            catch (Throwable throwable) {
            }
            for (Field field : clazz.getDeclaredFields()) {
                if (!class_630.class.isAssignableFrom(field.getType())) continue;
                try {
                    field.setAccessible(true);
                    return (class_630)field.get(class_5832);
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
        Map<String, class_630> children = AbstractBoatRendererMixin.getChildren(class_6302);
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
            class_630 match = AbstractBoatRendererMixin.findChildRecursive(child, normalizedName);
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
