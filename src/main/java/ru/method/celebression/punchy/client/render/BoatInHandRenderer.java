package ru.method.celebression.punchy.client.render;

import java.util.EnumMap;
import net.minecraft.class_10004;
import net.minecraft.class_10017;
import net.minecraft.class_10255;
import net.minecraft.class_10256;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1657;
import net.minecraft.class_1749;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
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
import ru.method.celebression.punchy.client.render.BoatInHandContext;
import ru.method.celebression.punchy.client.render.BoatLidState;
import ru.method.celebression.punchy.client.render.ChestProxyTuning;
import ru.method.celebression.punchy.client.render.InHandFallPhysics;
import ru.method.celebression.punchy.client.render.InHandLidPhysics;
import ru.method.celebression.punchy.compat.EmfEtfCemCompat;
import ru.method.celebression.punchy.mixin.client.accessor.BoatItemAccessor;
import ru.method.celebression.punchy.platform.Services;

public final class BoatInHandRenderer {
    private static final String BERYL_MOD_ID = "beryl";
    private static final EnumMap<class_1268, BoatCache> CACHE = new EnumMap(class_1268.class);
    private static final EnumMap<class_1268, Float> CHEST_LID_OPEN = new EnumMap(class_1268.class);
    private static final EnumMap<class_1268, Long> CHEST_LID_LAST_UPDATE = new EnumMap(class_1268.class);
    private static final float SCALE = 0.6f;
    private static final float ROT_Y = (float)Math.PI;

    private BoatInHandRenderer() {
    }

    public static boolean isBoatStack(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() && class_17992.method_7909() instanceof class_1749;
    }

        public static boolean tryRender(class_1799 class_17992, class_1268 class_12683, class_4587 class_45872, class_4597 class_45972, int n) {
        class_1792 class_17922 = class_17992.method_7909();
        if (class_17922 instanceof class_1749) {
            class_1749 class_17492 = (class_1749)class_17922;
            if (BoatInHandRenderer.shouldUseVanillaBoatRender(class_17992)) {
                return false;
            }
            class_310 client = class_310.method_1551();
            if (client != null && client.field_1687 != null) {
                BoatCache boatCache = CACHE.computeIfAbsent(class_12683, class_12682 -> new BoatCache());
                class_10255 class_102552 = boatCache.resolve(class_17492, class_17992, client);
                if (class_102552 == null) {
                    return false;
                }
                class_10255 class_102553 = boatCache.getRenderBoat();
                if (class_102553 == null) {
                    return false;
                }
                if (client.field_1724 != null) {
                    double d = client.field_1724.method_18798().field_1351;
                    class_102552.method_18800(0.0, d, 0.0);
                    class_102553.method_18800(0.0, d, 0.0);
                }
                BoatInHandRenderer.stabilizePose(class_102552);
                BoatInHandRenderer.stabilizePose(class_102553);
                class_898 class_8982 = client.method_1561();
                if (class_8982 == null) {
                    return false;
                }
                class_897 class_8972 = class_8982.method_3953((class_1297)class_102553);
                if (class_8972 == null) {
                    return false;
                }
                BoatInHandContext.push();
                try {
                    float f = client.method_61966().method_60637(true);
                    class_10017 class_100172 = class_8972.method_55269();
                    class_8972.method_62354((class_1297)class_102553, class_100172, f);
                    if (class_100172 instanceof BoatLidState) {
                        BoatLidState boatLidState = (BoatLidState)class_100172;
                        boatLidState.punchy$setLight(n);
                    }
                    class_45872.method_22903();
                    float f2 = InHandFallPhysics.update(client.field_1724, class_12683) * 0.3f;
                    if (f2 != 0.0f) {
                        class_45872.method_46416(0.0f, f2, 0.0f);
                    }
                    class_45872.method_22905(0.6f, 0.6f, 0.6f);
                    class_45872.method_22907(class_7833.field_40716.rotation((float)Math.PI));
                    class_8972.method_3936(class_100172, class_45872, class_45972, n);
                    if (class_102552 instanceof class_10256) {
                        ChestProxyTuning.Kind kind = BoatInHandRenderer.resolveChestProxyKind(class_17992);
                        BoatInHandRenderer.renderChestInBoat(class_12683, class_100172, class_45872, class_45972, client, kind, n);
                    }
                    class_45872.method_22909();
                }
                finally {
                    BoatInHandContext.pop();
                }
                return true;
            }
            return false;
        }
        return false;
    }

    static class_10255 createBoat(class_1749 class_17492, class_310 class_3102) {
        if (class_17492 != null && class_3102 != null && class_3102.field_1687 != null) {
            BoatItemAccessor boatItemAccessor = (BoatItemAccessor)class_17492;
            class_1299<? extends class_10255> class_12992 = boatItemAccessor.punchy$getEntityType();
            if (class_12992 == null) {
                return null;
            }
            class_10255 class_102552 = (class_10255)class_12992.method_5883((class_1937)class_3102.field_1687, class_3730.field_16462);
            if (class_102552 == null) {
                return null;
            }
            class_102552.method_5814(0.0, 0.0, 0.0);
            class_102552.method_36456(180.0f);
            return class_102552;
        }
        return null;
    }

    static class_1299<?> resolveBoatEntityType(class_1749 class_17492) {
        return class_17492 == null ? null : ((BoatItemAccessor)class_17492).punchy$getEntityType();
    }

    static class_1299<?> resolveBaseBoatType(class_1299<?> class_12992) {
        if (class_12992 == null) {
            return null;
        }
        class_2960 class_29602 = class_7923.field_41177.method_10221(class_12992);
        if (class_29602 == null) {
            return null;
        }
        String string = class_29602.method_12832();
        String string2 = null;
        if (string.endsWith("_chest_boat")) {
            string2 = string.replace("_chest_boat", "_boat");
        } else if (string.endsWith("_chest_raft")) {
            string2 = string.replace("_chest_raft", "_raft");
        }
        if (string2 == null) {
            return null;
        }
        class_2960 class_29603 = class_2960.method_60655((String)class_29602.method_12836(), (String)string2);
        class_1299 class_12993 = (class_1299)class_7923.field_41177.method_63535(class_29603);
        if (class_12993 == null) {
            return null;
        }
        class_2960 class_29604 = class_7923.field_41177.method_10221(class_12993);
        return class_29603.equals((Object)class_29604) ? class_12993 : null;
    }

    private static void renderChestInBoat(class_1268 class_12682, class_10017 class_100172, class_4587 class_45872, class_4597 class_45972, class_310 class_3102, ChestProxyTuning.Kind kind, int n) {
        class_824 class_8242;
        if (class_3102 != null && class_3102.field_1687 != null && (class_8242 = class_3102.method_31975()) != null) {
            ChestProxyTuning.Profile profile;
            class_2680 class_26802 = class_2246.field_10034.method_9564();
            PunchyChestBlockEntity punchyChestBlockEntity = new PunchyChestBlockEntity(class_2338.field_10980, class_26802);
            punchyChestBlockEntity.method_31662((class_1937)class_3102.field_1687);
            class_827 class_8272 = class_8242.method_3550((class_2586)punchyChestBlockEntity);
            if (class_8272 != null && (profile = ChestProxyTuning.get(kind != null ? kind : ChestProxyTuning.Kind.BOAT)) != null) {
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
                BoatInHandRenderer.applyBoatTransforms(class_100172, class_45872, false);
                BoatInHandRenderer.applyChestPose(class_100172, class_45872);
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

    private static ChestProxyTuning.Kind resolveChestProxyKind(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 == null) {
                return ChestProxyTuning.Kind.BOAT;
            }
            String string = class_29602.method_12832();
            return string.endsWith("_chest_raft") ? ChestProxyTuning.Kind.RAFT : ChestProxyTuning.Kind.BOAT;
        }
        return ChestProxyTuning.Kind.BOAT;
    }

    private static void applyBoatTransforms(class_10017 class_100172, class_4587 class_45872, boolean bl) {
        float f;
        if (class_100172 instanceof class_10004) {
            class_10004 class_100042 = (class_10004)class_100172;
            f = class_100042.field_53274;
        } else {
            f = 0.0f;
        }
        float f2 = f;
        class_45872.method_46416(0.0f, 0.375f, 0.0f);
        class_45872.method_22907(class_7833.field_40716.rotationDegrees(180.0f - f2));
        if (bl) {
            class_45872.method_22905(-1.0f, -1.0f, 1.0f);
        }
        class_45872.method_22907(class_7833.field_40716.rotationDegrees(90.0f));
    }

    private static void applyChestPose(class_10017 class_100172, class_4587 class_45872) {
        BoatLidState boatLidState;
        if (class_100172 instanceof BoatLidState && (boatLidState = (BoatLidState)class_100172).punchy$isChestPoseValid()) {
            float f = boatLidState.punchy$getChestX() / 16.0f;
            float f2 = boatLidState.punchy$getChestY() / 16.0f;
            float f3 = boatLidState.punchy$getChestZ() / 16.0f;
            class_45872.method_46416(f, f2, f3);
            class_45872.method_22907(class_7833.field_40714.rotation(boatLidState.punchy$getChestRotX()));
            class_45872.method_22907(class_7833.field_40716.rotation(boatLidState.punchy$getChestRotY()));
            class_45872.method_22907(class_7833.field_40718.rotation(boatLidState.punchy$getChestRotZ()));
        }
    }

    private static void stabilizePose(class_10255 class_102552) {
        if (class_102552 != null) {
            class_102552.method_5814(0.0, 0.0, 0.0);
            class_102552.method_36456(180.0f);
            class_102552.method_36457(0.0f);
            class_102552.field_5982 = class_102552.method_36454();
            class_102552.field_6004 = class_102552.method_36455();
        }
    }

    private static boolean shouldUseVanillaBoatRender(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        if (Services.PLATFORM != null && Services.PLATFORM.isModLoaded("beryl")) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 == null) {
                return false;
            }
            String string = class_29602.method_12832();
            return string.endsWith("_boat") || string.endsWith("_chest_boat");
        }
        return false;
    }

    static final class BoatCache {
        private class_1799 lastStack;
        private class_10255 boat;
        private class_10255 renderBoat;

        BoatCache() {
        }

        class_10255 resolve(class_1749 class_17492, class_1799 class_17992, class_310 class_3102) {
            if (this.boat == null || this.lastStack == null || this.lastStack.method_7909() != class_17992.method_7909()) {
                this.lastStack = class_17992;
                this.boat = BoatInHandRenderer.createBoat(class_17492, class_3102);
                this.renderBoat = this.resolveRenderBoat(class_3102, class_17492, this.boat);
            }
            return this.boat;
        }

        class_10255 getRenderBoat() {
            return this.renderBoat != null ? this.renderBoat : this.boat;
        }

        private class_10255 resolveRenderBoat(class_310 class_3102, class_1749 class_17492, class_10255 class_102552) {
            class_1299<?> class_12992;
            if (class_102552 instanceof class_10256 && (class_12992 = BoatInHandRenderer.resolveBaseBoatType(BoatInHandRenderer.resolveBoatEntityType(class_17492))) != null) {
                class_10255 class_102553;
                class_10255 class_102554;
                class_1297 class_12972 = class_12992.method_5883((class_1937)class_3102.field_1687, class_3730.field_16462);
                class_10255 class_102555 = class_102554 = class_12972 instanceof class_10255 ? (class_102553 = (class_10255)class_12972) : null;
                if (class_102554 != null) {
                    class_102554.method_5814(0.0, 0.0, 0.0);
                    class_102554.method_36456(180.0f);
                    return class_102554;
                }
            }
            return class_102552;
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
