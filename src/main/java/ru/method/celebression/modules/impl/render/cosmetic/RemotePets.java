package ru.method.celebression.modules.impl.render.cosmetic;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_10017;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1420;
import net.minecraft.class_1452;
import net.minecraft.class_1453;
import net.minecraft.class_1454;
import net.minecraft.class_1621;
import net.minecraft.class_1634;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_4019;
import net.minecraft.class_4050;
import net.minecraft.class_4184;
import net.minecraft.class_4466;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_7102;
import net.minecraft.class_7298;
import net.minecraft.class_897;
import ru.method.celebression.Celebression;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsService;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.manager.UserSyncManager;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffects;
import ru.method.celebression.modules.impl.render.cosmetic.Nimb;
import ru.method.celebression.modules.impl.render.cosmetic.PetGroundSampler;
import ru.method.celebression.modules.impl.render.cosmetic.Raven;

public final class RemotePets
implements MinecraftClientAccess {
    private static final double MAX_RENDER_DISTANCE_SQ = 4096.0;
    private static final Set<String> GROUNDED_SKINS = Set.of("FOX", "PIG", "FROG", "SLIME");
    private static final class_2960 RAVEN_TEXTURE = Celebression.id("cosmetic/textures/voron.png");
    private final Map<UUID, PetInstance> pets = new ConcurrentHashMap<UUID, PetInstance>();

    public RemotePets() {
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
    }

        @EventHandler
    public void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (Module.fullNullCheck()) {
            return;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.isPanic()) {
            return;
        }
        CosmeticsService cosmeticsService = celebression.getCosmeticsService();
        UserSyncManager userSyncManager = celebression.getUserSyncManager();
        if (cosmeticsService == null || userSyncManager == null) {
            return;
        }
        if (!cosmeticsService.isEnabled() || !cosmeticsService.bB()) {
            this.pets.clear();
            return;
        }
        HashSet<UUID> hashSet = new HashSet<UUID>();
        class_4587 class_45872 = matrixStackStage.l();
        class_4184 class_41842 = RemotePets.aiG.field_1773.method_19418();
        class_243 class_2432 = class_41842.method_19326();
        float f = matrixStackStage.k().method_60637(true);
        long l = System.currentTimeMillis();
        CosmeticEffects cosmeticEffects = celebression.getModuleManager() == null ? null : celebression.getModuleManager().getModule(CosmeticEffects.class);
        Nimb nimb = cosmeticEffects == null ? null : cosmeticEffects.nimbRenderer();
        class_45872.method_22903();
        class_45872.method_22904(-class_2432.field_1352, -class_2432.field_1351, -class_2432.field_1350);
        try {
            for (class_1657 class_16572 : RemotePets.aiG.field_1687.method_18456()) {
                UserSyncManager.ServerSubscription serverSubscription;
                String string;
                UUID uUID;
                if (class_16572 == null || class_16572 == RemotePets.aiG.field_1724 || class_16572.method_5767() || class_16572.method_5858((class_1297)RemotePets.aiG.field_1724) > 4096.0 || class_16572.method_7334() == null || !cosmeticsService.d(uUID = class_16572.method_7334().getId(), string = class_16572.method_7334().getName())) continue;
                UserSyncManager.SyncResult syncResult = userSyncManager.g(uUID, string);
                if (syncResult != null && nimb != null) {
                    nimb.renderRemoteHalo(class_45872, class_41842, class_16572, f, syncResult);
                }
                if ((serverSubscription = userSyncManager.f(uUID, string)) == null || uUID == null) continue;
                PetInstance petInstance = this.pets.get(uUID);
                if (petInstance == null || !petInstance.matches(serverSubscription) || petInstance.entity.method_37908() != RemotePets.aiG.field_1687) {
                    class_1309 class_13092 = this.createPetEntity(serverSubscription.oB);
                    if (class_13092 == null) continue;
                    petInstance = new PetInstance(serverSubscription, class_13092);
                    this.pets.put(uUID, petInstance);
                }
                hashSet.add(uUID);
                this.renderPet(matrixStackStage, class_45872, petInstance, class_16572, f, l);
            }
        }
        finally {
            class_45872.method_22909();
        }
        Iterator<Map.Entry<UUID, PetInstance>> iterator = this.pets.entrySet().iterator();
        while (iterator.hasNext()) {
            if (hashSet.contains(iterator.next().getKey())) continue;
            iterator.remove();
        }
    }

        private void renderPet(WorldRenderStages.MatrixStackStage matrixStackStage, class_4587 class_45872, PetInstance petInstance, class_1657 class_16572, float f, long l) {
        class_897 class_8972;
        float f2;
        class_243 class_2432;
        class_243 class_2433;
        class_243 class_2434;
        UserSyncManager.ServerSubscription serverSubscription = petInstance.info;
        float f3 = petInstance.lastMs == 0L ? 0.016f : class_3532.method_15363((float)((float)(l - petInstance.lastMs) / 1000.0f), (float)0.0f, (float)0.06f);
        petInstance.lastMs = l;
        petInstance.orbitAngle += f3 * (1.6f * serverSubscription.speed);
        double d = class_3532.method_16436((double)f, (double)class_16572.field_6014, (double)class_16572.method_23317());
        double d2 = class_3532.method_16436((double)f, (double)class_16572.field_6036, (double)class_16572.method_23318());
        double d3 = class_3532.method_16436((double)f, (double)class_16572.field_5969, (double)class_16572.method_23321());
        boolean bl = GROUNDED_SKINS.contains(serverSubscription.oB);
        if (bl) {
            float f4 = class_3532.method_17821((float)f, (float)class_16572.field_6220, (float)class_16572.field_6283);
            double d4 = Math.toRadians(f4);
            class_2434 = new class_243(-Math.sin(d4), 0.0, Math.cos(d4));
            class_243 class_2435 = new class_243(class_2434.field_1350, 0.0, -class_2434.field_1352);
            class_2433 = new class_243(d, d2, d3).method_1019(class_2434.method_1021(-0.85)).method_1019(class_2435.method_1021(-0.55));
            double d5 = PetGroundSampler.sampleGroundYSmooth((class_1937)RemotePets.aiG.field_1687, class_2433.field_1352, class_2433.field_1350, d2);
            double d6 = !Double.isNaN(d5) && Math.abs(d5 - d2) <= 1.5 ? d5 : d2;
            class_2432 = new class_243(class_2433.field_1352, d6, class_2433.field_1350);
        } else {
            double d7 = Math.sin((double)l / 1000.0 * 2.1 + (double)petInstance.orbitAngle) * 0.05;
            double d8 = d2 + (double)serverSubscription.oD + d7;
            class_2432 = new class_243(d + Math.cos(petInstance.orbitAngle) * (double)serverSubscription.oC, d8, d3 + Math.sin(petInstance.orbitAngle) * (double)serverSubscription.oC);
        }
        class_243 class_2436 = petInstance.smoothPos;
        if (petInstance.smoothPos == null) {
            petInstance.smoothPos = class_2432;
        } else if (petInstance.smoothPos.method_1025(class_2432) > 256.0) {
            petInstance.smoothPos = class_2432;
        } else {
            float f5 = 1.0f - (float)Math.exp(-f3 / (bl ? 0.16f : 0.12f));
            petInstance.smoothPos = petInstance.smoothPos.method_1019(class_2432.method_1020(petInstance.smoothPos).method_1021((double)f5));
        }
        class_243 class_2437 = petInstance.smoothPos;
        if (bl) {
            class_2434 = class_2436 == null ? class_243.field_1353 : class_2437.method_1020(class_2436);
            double d9 = Math.sqrt(class_2434.field_1352 * class_2434.field_1352 + class_2434.field_1350 * class_2434.field_1350) / (double)Math.max(f3, 1.0E-4f);
            if (d9 > 0.35 && class_2434.field_1352 * class_2434.field_1352 + class_2434.field_1350 * class_2434.field_1350 > 1.0E-8) {
                petInstance.smoothYaw = (float)(class_3532.method_15349((double)class_2434.field_1350, (double)class_2434.field_1352) * 57.29577951308232) - 90.0f;
            }
            f2 = petInstance.smoothYaw;
        } else {
            class_2434 = new class_243(-Math.sin(petInstance.orbitAngle), 0.0, Math.cos(petInstance.orbitAngle));
            f2 = (float)Math.toDegrees(Math.atan2(-class_2434.field_1352, class_2434.field_1350));
        }
        class_1309 petEntity = petInstance.entity;
        petEntity.field_6014 = petEntity.method_23317();
        petEntity.field_6036 = petEntity.method_23318();
        petEntity.field_5969 = petEntity.method_23321();
        petEntity.method_23327(class_2437.field_1352, class_2437.field_1351, class_2437.field_1350);
        petEntity.field_5982 = petEntity.method_36454();
        petEntity.method_36456(f2);
        petEntity.field_6283 = f2;
        petEntity.field_6241 = f2;
        petEntity.field_6220 = f2;
        petEntity.field_6259 = f2;
        petEntity.field_6004 = petEntity.method_36455();
        petEntity.method_36457(0.0f);
        petEntity.method_24830(bl);
        petEntity.method_18380(class_4050.field_18076);
        petEntity.field_6012 = class_16572.field_6012;
        if (petEntity instanceof class_1453) {
            class_1453 class_14532 = (class_1453)petEntity;
            boolean bl2 = "RAVEN".equals(serverSubscription.oB);
            RemotePets.animateParrotWings(class_14532, (double)l / 1000.0, bl2 ? 1.15f : 5.25f);
        }
        if (!((class_8972 = aiG.method_1561().method_3953(petEntity)) instanceof class_897)) {
            return;
        }
        class_4597.class_4598 immediate = aiG.method_22940().method_23000();
        class_4597 vertexConsumers = "RAVEN".equals(serverSubscription.oB) ? new Raven.TextureSwapVcp(immediate, RAVEN_TEXTURE) : immediate;
        float f6 = serverSubscription.oE * ("RAVEN".equals(serverSubscription.oB) ? 1.28f : 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        class_45872.method_22903();
        class_45872.method_22904(class_2437.field_1352, class_2437.field_1351, class_2437.field_1350);
        class_45872.method_22905(f6, f6, f6);
        try {
            this.renderEntityCompat(class_8972, petEntity, f, class_45872, vertexConsumers);
        }
        finally {
            class_45872.method_22909();
        }
        immediate.method_22993();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
    }

    private void renderEntityCompat(class_897 class_8972, class_1309 class_13092, float f, class_4587 class_45872, class_4597 class_45972) {
        try {
            class_10017 class_100172 = class_8972.method_62425((class_1297)class_13092, f);
            if (class_100172 != null) {
                class_8972.method_3936(class_100172, class_45872, class_45972, 0xF000F0);
            }
        }
        catch (Throwable throwable) {
        }
    }

    private class_1309 createPetEntity(String string) {
        if (RemotePets.aiG.field_1687 == null || string == null) {
            return null;
        }
        class_1309 petEntity = null;
        try {
            if ("BAT".equals(string)) {
                petEntity = new class_1420(class_1299.field_6108, (class_1937)RemotePets.aiG.field_1687);
            } else if ("PARROT".equals(string) || "RAVEN".equals(string)) {
                petEntity = new class_1453(class_1299.field_6104, (class_1937)RemotePets.aiG.field_1687);
            } else if ("FAIRY".equals(string)) {
                petEntity = new class_7298(class_1299.field_38384, (class_1937)RemotePets.aiG.field_1687);
            } else if ("BEE".equals(string)) {
                petEntity = new class_4466(class_1299.field_20346, (class_1937)RemotePets.aiG.field_1687);
            } else if ("VEX".equals(string)) {
                petEntity = new class_1634(class_1299.field_6059, (class_1937)RemotePets.aiG.field_1687);
            } else if ("FOX".equals(string)) {
                petEntity = new class_4019(class_1299.field_17943, (class_1937)RemotePets.aiG.field_1687);
            } else if ("PIG".equals(string)) {
                petEntity = new class_1452(class_1299.field_6093, (class_1937)RemotePets.aiG.field_1687);
            } else if ("FROG".equals(string)) {
                petEntity = new class_7102(class_1299.field_37419, (class_1937)RemotePets.aiG.field_1687);
            } else if ("PUFFERFISH".equals(string)) {
                petEntity = new class_1454(class_1299.field_6062, (class_1937)RemotePets.aiG.field_1687);
            } else if ("SLIME".equals(string)) {
                petEntity = new class_1621(class_1299.field_6069, (class_1937)RemotePets.aiG.field_1687);
            }
        }
        catch (Throwable throwable) {
            return null;
        }
        if (petEntity == null) {
            return null;
        }
        class_1309 class_13092 = petEntity;
        class_13092.method_5875(true);
        class_13092.method_5803(true);
        class_13092.method_5648(false);
        class_13092.method_18380(class_4050.field_18076);
        try {
            class_13092.method_5880(false);
            class_13092.method_5665(null);
        }
        catch (Throwable throwable) {
        }
        try {
            class_13092.method_5684(true);
        }
        catch (Throwable throwable) {
        }
        return class_13092;
    }

    private static void animateParrotWings(class_1453 class_14532, double d, float f) {
        float f2 = (float)(Math.sin(d * (double)f * 6.2) * 0.5 + 0.5);
        class_14532.field_6827 = class_14532.field_6819;
        class_14532.field_6819 = 0.1f + f2 * 0.24f;
        class_14532.field_6829 = class_14532.field_6818;
        class_14532.field_6818 = (float)(d * (double)f * 3.6);
    }

    static final class PetInstance {
        final UserSyncManager.ServerSubscription info;
        final class_1309 entity;
        float orbitAngle;
        float smoothYaw;
        long lastMs;
        class_243 smoothPos;

        PetInstance(UserSyncManager.ServerSubscription serverSubscription, class_1309 class_13092) {
            this.info = serverSubscription;
            this.entity = class_13092;
        }

        boolean matches(UserSyncManager.ServerSubscription serverSubscription) {
            return serverSubscription != null && this.info.oB.equals(serverSubscription.oB) && this.info.oC == serverSubscription.oC && this.info.oD == serverSubscription.oD && this.info.speed == serverSubscription.speed && this.info.oE == serverSubscription.oE;
        }
    }
}
