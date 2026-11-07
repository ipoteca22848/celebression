package ru.method.celebression.punchy.client.render;

import java.util.EnumMap;
import net.minecraft.class_10017;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1657;
import net.minecraft.class_1688;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1808;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2595;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3730;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_7833;
import net.minecraft.class_7923;
import net.minecraft.class_824;
import net.minecraft.class_827;
import net.minecraft.class_897;
import net.minecraft.class_898;
import ru.method.celebression.punchy.client.render.ChestProxyTuning;
import ru.method.celebression.punchy.client.render.InHandFallPhysics;
import ru.method.celebression.punchy.client.render.InHandLidPhysics;
import ru.method.celebression.punchy.client.render.MinecartInHandContext;
import ru.method.celebression.punchy.client.render.MinecartLightState;
import ru.method.celebression.punchy.compat.EmfEtfCemCompat;
import ru.method.celebression.punchy.mixin.client.accessor.MinecartItemAccessor;

public final class MinecartInHandRenderer {
    private static final EnumMap<class_1268, MinecartCache> CACHE = new EnumMap(class_1268.class);
    private static final EnumMap<class_1268, Float> CHEST_LID_OPEN = new EnumMap(class_1268.class);
    private static final EnumMap<class_1268, Long> CHEST_LID_LAST_UPDATE = new EnumMap(class_1268.class);
    private static final float SCALE = 0.65f;
    private static final float ROT_Y = (float)Math.PI;

    private MinecartInHandRenderer() {
    }

    public static boolean isMinecartStack(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() && class_17992.method_7909() instanceof class_1808;
    }

        public static boolean tryRender(class_1799 class_17992, class_1268 class_12683, class_4587 class_45872, class_4597 class_45972, int n) {
        class_1792 class_17922 = class_17992.method_7909();
        if (!(class_17922 instanceof class_1808)) {
            return false;
        }
        class_1808 class_18082 = (class_1808)class_17922;
        class_310 client = class_310.method_1551();
        if (client != null && client.field_1687 != null) {
            MinecartCache minecartCache = CACHE.computeIfAbsent(class_12683, class_12682 -> new MinecartCache());
            class_1688 class_16882 = minecartCache.resolve(class_18082, class_17992, client);
            if (class_16882 == null) {
                return false;
            }
            class_1688 class_16883 = minecartCache.getRenderCart();
            if (class_16883 == null) {
                return false;
            }
            double d = 0.0;
            if (client.field_1724 != null) {
                d = client.field_1724.method_18798().field_1351;
            }
            class_16882.method_18800(0.0, d, 0.0);
            class_16883.method_18800(0.0, d, 0.0);
            MinecartInHandRenderer.stabilizePose(class_16882);
            MinecartInHandRenderer.stabilizePose(class_16883);
            class_898 class_8982 = client.method_1561();
            if (class_8982 == null) {
                return false;
            }
            class_897 class_8972 = class_8982.method_3953((class_1297)class_16883);
            if (class_8972 == null) {
                return false;
            }
            float f = client.method_61966().method_60637(true);
            class_10017 class_100172 = class_8972.method_55269();
            class_8972.method_62354((class_1297)class_16883, class_100172, f);
            if (class_100172 instanceof MinecartLightState) {
                MinecartLightState minecartLightState = (MinecartLightState)class_100172;
                minecartLightState.punchy$setLight(n);
            }
            MinecartInHandContext.push();
            try {
                MinecartInHandContext.setVy(d);
                MinecartInHandContext.setTntOffset(0.0f);
                MinecartInHandContext.setType(MinecartInHandRenderer.resolveEntityType(class_18082));
                class_45872.method_22903();
                float f2 = InHandFallPhysics.update(client.field_1724, class_12683);
                float f3 = f2 * 0.3f;
                MinecartInHandContext.setTntOffset(MinecartInHandRenderer.isTntMinecart(class_17992) ? f3 * 1.7f : 0.0f);
                if (f3 != 0.0f) {
                    class_45872.method_46416(0.0f, f3, 0.0f);
                }
                class_45872.method_22905(0.65f, 0.65f, 0.65f);
                class_45872.method_22907(class_7833.field_40716.rotation((float)Math.PI));
                class_8972.method_3936(class_100172, class_45872, class_45972, n);
                if (MinecartInHandRenderer.isChestMinecart(class_17992)) {
                    MinecartInHandRenderer.renderChestInMinecart(class_12683, class_45872, class_45972, client, n);
                } else if (MinecartInHandRenderer.isTntMinecart(class_17992)) {
                    MinecartInHandRenderer.renderTntInMinecart(class_45872, class_45972, client, n);
                }
                class_45872.method_22909();
            }
            finally {
                MinecartInHandContext.pop();
            }
            return true;
        }
        return false;
    }

    static boolean isChestMinecart(class_1799 class_17992) {
        class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
        return class_29602 != null && "chest_minecart".equals(class_29602.method_12832());
    }

    static boolean isTntMinecart(class_1799 class_17992) {
        class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
        return class_29602 != null && "tnt_minecart".equals(class_29602.method_12832());
    }

    static class_1299<? extends class_1688> resolveEntityType(class_1808 class_18082) {
        return class_18082 == null ? null : ((MinecartItemAccessor)class_18082).punchy$getType();
    }

    private static void renderChestInMinecart(class_1268 class_12682, class_4587 class_45872, class_4597 class_45972, class_310 class_3102, int n) {
        class_824 class_8242;
        if (class_3102 != null && class_3102.field_1687 != null && (class_8242 = class_3102.method_31975()) != null) {
            ChestProxyTuning.Profile profile;
            class_2680 class_26802 = class_2246.field_10034.method_9564();
            PunchyChestBlockEntity punchyChestBlockEntity = new PunchyChestBlockEntity(class_2338.field_10980, class_26802);
            punchyChestBlockEntity.method_31662((class_1937)class_3102.field_1687);
            class_827 class_8272 = class_8242.method_3550((class_2586)punchyChestBlockEntity);
            if (class_8272 != null && (profile = ChestProxyTuning.get(ChestProxyTuning.Kind.MINECART)) != null) {
                float f;
                boolean bl = EmfEtfCemCompat.disableProxyPhysics(EmfEtfCemCompat.ProxyPhysicsTarget.CHEST);
                if (bl) {
                    f = 0.0f;
                } else {
                    float f2 = InHandLidPhysics.computeFallTarget((class_1657)class_3102.field_1724);
                    f = InHandLidPhysics.updateLid(CHEST_LID_OPEN, CHEST_LID_LAST_UPDATE, class_12682, f2);
                }
                punchyChestBlockEntity.punchy$setOpen(f);
                class_45872.method_22903();
                class_45872.method_46416(profile.offX, profile.offY, profile.offZ);
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(profile.rotX));
                class_45872.method_22907(class_7833.field_40716.rotationDegrees(profile.rotY));
                class_45872.method_22907(class_7833.field_40718.rotationDegrees(profile.rotZ));
                class_45872.method_22905(profile.scale, profile.scale, profile.scale);
                class_8272.method_3569((class_2586)punchyChestBlockEntity, 0.0f, class_45872, class_45972, n, class_4608.field_21444);
                class_45872.method_22909();
            }
        }
    }

    private static void renderTntInMinecart(class_4587 class_45872, class_4597 class_45972, class_310 class_3102, int n) {
        ChestProxyTuning.Profile profile;
        if (class_3102 != null && (profile = ChestProxyTuning.get(ChestProxyTuning.Kind.MINECART)) != null) {
            float f = MinecartInHandContext.getTntOffset();
            class_45872.method_22903();
            class_45872.method_46416(profile.offX, profile.offY + f, profile.offZ);
            class_45872.method_22907(class_7833.field_40714.rotationDegrees(profile.rotX));
            class_45872.method_22907(class_7833.field_40716.rotationDegrees(profile.rotY));
            class_45872.method_22907(class_7833.field_40718.rotationDegrees(profile.rotZ));
            class_45872.method_22905(profile.scale, profile.scale, profile.scale);
            class_3102.method_1541().method_3353(class_2246.field_10375.method_9564(), class_45872, class_45972, n, class_4608.field_21444);
            class_45872.method_22909();
        }
    }

    private static void stabilizePose(class_1688 class_16882) {
        if (class_16882 != null) {
            class_16882.method_5814(0.0, 0.0, 0.0);
            class_16882.method_36456(180.0f);
            class_16882.method_36457(0.0f);
            class_16882.field_5982 = class_16882.method_36454();
            class_16882.field_6004 = class_16882.method_36455();
        }
    }

    static final class MinecartCache {
        private class_1792 lastItem;
        private class_1688 minecart;
        private class_1688 renderCart;

        MinecartCache() {
        }

        class_1688 resolve(class_1808 class_18082, class_1799 class_17992, class_310 class_3102) {
            class_1792 class_17922 = class_17992.method_7909();
            if (this.minecart == null || this.lastItem != class_17922) {
                class_2960 class_29602;
                this.lastItem = class_17922;
                class_1299 class_12992 = MinecartInHandRenderer.resolveEntityType(class_18082);
                if (class_12992 == null && (class_29602 = class_7923.field_41178.method_10221(class_17922)) != null) {
                    class_1299 class_12993;
                    class_12992 = class_12993 = (class_1299)class_7923.field_41177.method_63535(class_29602);
                }
                if (class_12992 == null) {
                    class_12992 = class_1299.field_6096;
                }
                this.minecart = (class_1688)class_12992.method_5883((class_1937)class_3102.field_1687, class_3730.field_16462);
                if (this.minecart != null) {
                    this.minecart.method_5814(0.0, 0.0, 0.0);
                    this.minecart.method_36456(180.0f);
                }
                this.renderCart = this.resolveRenderCart(class_3102, class_17992, this.minecart);
            }
            return this.minecart;
        }

        class_1688 getRenderCart() {
            return this.renderCart != null ? this.renderCart : this.minecart;
        }

        private class_1688 resolveRenderCart(class_310 class_3102, class_1799 class_17992, class_1688 class_16882) {
            if (!MinecartInHandRenderer.isChestMinecart(class_17992) && !MinecartInHandRenderer.isTntMinecart(class_17992)) {
                return class_16882;
            }
            class_1688 class_16883 = (class_1688)class_1299.field_6096.method_5883((class_1937)class_3102.field_1687, class_3730.field_16462);
            if (class_16883 != null) {
                class_16883.method_5814(0.0, 0.0, 0.0);
                class_16883.method_36456(180.0f);
            }
            return class_16883 != null ? class_16883 : class_16882;
        }
    }

    static final class PunchyChestBlockEntity
    extends class_2595 {
        private float punchy$open;

        PunchyChestBlockEntity(class_2338 class_23382, class_2680 class_26802) {
            super(class_23382, class_26802);
        }

        void punchy$setOpen(float f) {
            this.punchy$open = f;
        }

        public float method_11274(float f) {
            return this.punchy$open;
        }
    }
}
