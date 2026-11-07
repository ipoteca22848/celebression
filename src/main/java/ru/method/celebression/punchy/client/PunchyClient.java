package ru.method.celebression.punchy.client;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3300;
import net.minecraft.class_638;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyThirdPersonAnimationManager;
import ru.method.celebression.punchy.client.bedrockitems.BedrockItemSystem;
import ru.method.celebression.punchy.client.input.AnimationKeybinds;
import ru.method.celebression.punchy.client.modelparts.ModelPartsParticleSystem;
import ru.method.celebression.punchy.client.modelparts.ModelPartsSystem;
import ru.method.celebression.punchy.client.render.MacePhysics;
import ru.method.celebression.punchy.client.state.AttackActionTracker;
import ru.method.celebression.punchy.client.state.AttackStateMachine;
import ru.method.celebression.punchy.client.state.BowStateMachine;
import ru.method.celebression.punchy.client.state.ClimbStateMachine;
import ru.method.celebression.punchy.client.state.ConsumeStateMachine;
import ru.method.celebression.punchy.client.state.CrawlStateMachine;
import ru.method.celebression.punchy.client.state.CrossbowStateMachine;
import ru.method.celebression.punchy.client.state.DryOutStateMachine;
import ru.method.celebression.punchy.client.state.FallingStateMachine;
import ru.method.celebression.punchy.client.state.FireStateMachine;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.IdlePoseStateMachine;
import ru.method.celebression.punchy.client.state.InspectActionTracker;
import ru.method.celebression.punchy.client.state.InspectStateMachine;
import ru.method.celebression.punchy.client.state.InterfaceDelayState;
import ru.method.celebression.punchy.client.state.MiningStateMachine;
import ru.method.celebression.punchy.client.state.MovementStateMachine;
import ru.method.celebression.punchy.client.state.MudStateMachine;
import ru.method.celebression.punchy.client.state.ShieldStateMachine;
import ru.method.celebression.punchy.client.state.SpearStateMachine;
import ru.method.celebression.punchy.client.state.SweatStateMachine;
import ru.method.celebression.punchy.client.state.SwimmingStateMachine;
import ru.method.celebression.punchy.client.state.TridentStateMachine;
import ru.method.celebression.punchy.client.state.TriggerableActionTracker;
import ru.method.celebression.punchy.client.state.TriggerableStateMachine;
import ru.method.celebression.punchy.client.state.UseActionTracker;
import ru.method.celebression.punchy.client.state.UseItemStateMachine;
import ru.method.celebression.punchy.client.state.WakeUpStateMachine;
import ru.method.celebression.punchy.client.state.WalkOnWaterStateMachine;
import ru.method.celebression.punchy.client.tuning.MixPackKeybinds;
import ru.method.celebression.punchy.client.tuning.TuningEditorKeybinds;
import ru.method.celebression.punchy.client.tuning.TuningKeybinds;
import ru.method.celebression.punchy.compat.BetterCombatCompat;
import ru.method.celebression.punchy.compat.EmfEtfCemCompat;
import ru.method.celebression.punchy.compat.FirstPersonModelCompat;
import ru.method.celebression.punchy.compat.SpellEngineCompat;
import ru.method.celebression.punchy.compat.SwordBlockingMechanicsCompat;
import ru.method.celebression.punchy.compat.entityculling.EntityCullingCompat;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;
import ru.method.celebression.punchy.platform.Services;

public final class PunchyClient {
    private static final float ELYTRA_OFFHAND_SHOW_SPEED_MIN = 0.55f;
    private static final MiningStateMachine MINING_STATE = new MiningStateMachine();
    public static final MudStateMachine MUD_STATE = new MudStateMachine();
    private static final SweatStateMachine SWEAT_STATE = new SweatStateMachine();
    private static final UseItemStateMachine USE_STATE = new UseItemStateMachine();
    private static final AttackStateMachine ATTACK_STATE = new AttackStateMachine();
    private static final BowStateMachine BOW_STATE = new BowStateMachine();
    private static final CrossbowStateMachine CROSSBOW_STATE = new CrossbowStateMachine();
    private static final SpearStateMachine SPEAR_STATE = new SpearStateMachine();
    private static final ShieldStateMachine SHIELD_STATE = new ShieldStateMachine();
    private static final TridentStateMachine TRIDENT_STATE = new TridentStateMachine();
    private static final ConsumeStateMachine CONSUME_STATE = new ConsumeStateMachine();
    private static final SwimmingStateMachine SWIM_STATE = new SwimmingStateMachine();
    private static final WalkOnWaterStateMachine WALK_ON_WATER_STATE = new WalkOnWaterStateMachine();
    private static final DryOutStateMachine DRY_OUT_STATE = new DryOutStateMachine();
    private static final CrawlStateMachine CRAWL_STATE = new CrawlStateMachine();
    private static final ClimbStateMachine CLIMB_STATE = new ClimbStateMachine();
    private static final FireStateMachine FIRE_STATE = new FireStateMachine();
    private static final MovementStateMachine MOVEMENT_STATE = MovementStateMachine.get();
    private static final HandEquipStateMachine HAND_EQUIP_STATE = new HandEquipStateMachine();
    private static final FallingStateMachine FALLING_STATE = new FallingStateMachine();
    private static final InspectStateMachine INSPECT_STATE = new InspectStateMachine();
    private static final IdlePoseStateMachine IDLE_POSE_STATE = new IdlePoseStateMachine();
    private static final TriggerableStateMachine TRIGGERABLE_STATE = new TriggerableStateMachine();
    private static final WakeUpStateMachine WAKE_UP_STATE = new WakeUpStateMachine();
    private static boolean RESOURCES_READY = false;
    private static Object LAST_RESOURCE_REFRESH_LEVEL;

    private PunchyClient() {
    }

    public static WalkOnWaterStateMachine getWalkOnWaterStateMachine() {
        return WALK_ON_WATER_STATE;
    }

    public static boolean shouldSuppressOffhandRender(class_1657 class_16572) {
        class_746 class_7462;
        if (HAND_EQUIP_STATE == null || class_16572 == null) {
            return false;
        }
        if (PunchyAnimationManager.shouldSuppressOffhandForAttack()) {
            return true;
        }
        boolean bl = PunchyClient.shouldKeepOffhandVisibleForFastFlight(class_16572);
        if (bl && class_16572 instanceof class_746) {
            class_7462 = (class_746)class_16572;
            if (HAND_EQUIP_STATE.isOffhandRenderSuppressed() || HAND_EQUIP_STATE.shouldLowerOffhand(class_7462)) {
                bl = false;
            }
        }
        if (bl) {
            return false;
        }
        class_1306 offhandArm = class_16572.method_6068() == class_1306.field_6183 ? class_1306.field_6182 : class_1306.field_6183;
        if (PunchyAnimationManager.isItemForceShown(offhandArm)) {
            return false;
        }
        boolean bl2 = HAND_EQUIP_STATE.isOffhandRenderSuppressed();
        if (bl2) {
            return true;
        }
        if (PunchyAnimationManager.isSwimDualHandedActive()) {
            return false;
        }
        if (PunchyAnimationManager.SWIM_HANDLER.isPlaying()) {
            class_1306 class_13063 = PunchyAnimationManager.SWIM_HANDLER.getActiveArm();
            if (class_13063 == null) {
                return false;
            }
            if (class_13063 == offhandArm) {
                return false;
            }
        }
        return false;
    }

    private static boolean shouldKeepOffhandVisibleForFastFlight(class_1657 class_16572) {
        if (class_16572 instanceof class_746) {
            class_746 class_7462 = (class_746)class_16572;
            if (!PunchyConfig.enableElytraFlightAnimation()) {
                return false;
            }
            if (!class_7462.method_24828() && !class_7462.method_5681() && !class_7462.method_5799() && class_7462.method_6128()) {
                class_243 class_2432 = class_7462.method_18798();
                float f = class_2432 == null ? 0.0f : (float)class_2432.method_1033();
                return f >= 0.55f;
            }
            return false;
        }
        return false;
    }

    public static boolean shouldLowerOffhand(class_746 class_7462) {
        return HAND_EQUIP_STATE != null && class_7462 != null ? HAND_EQUIP_STATE.shouldLowerOffhand(class_7462) : false;
    }

    public static void init() {
        TuningEditorKeybinds.register();
        TuningKeybinds.register();
        MixPackKeybinds.register();
        AnimationKeybinds.register();
        PunchyConfig.load();
        PunchyAnimationManager.setAnimationSpeed(PunchyConfig.getAnimationSpeed());
        BetterCombatCompat.initialize();
        SpellEngineCompat.initialize();
        SwordBlockingMechanicsCompat.initialize();
        Services.CLIENT.registerReloadListener(class_2960.method_60655((String)"punchy", (String)"animation_loader"), PunchyClient::onResourceReload);
        Services.CLIENT.registerClientTickEnd(class_3102 -> {
            class_638 class_6382;
            if (!RESOURCES_READY && class_3102.method_1478() != null) {
                PunchyClient.onResourceReload(class_3102.method_1478());
            }
            if (class_3102.field_1724 != null && class_3102.field_1687 != null && class_3102.method_1478() != null) {
                class_6382 = class_3102.field_1687;
                if (LAST_RESOURCE_REFRESH_LEVEL != class_6382) {
                    PunchyClient.resetTransientWorldState();
                    LAST_RESOURCE_REFRESH_LEVEL = class_6382;
                }
            } else if (class_3102.field_1687 == null) {
                PunchyClient.resetTransientWorldState();
                LAST_RESOURCE_REFRESH_LEVEL = null;
            }
            if (class_3102.field_1724 != null && !class_3102.method_1493()) {
                boolean bl;
                boolean bl2;
                boolean bl3;
                if (!PunchyConfig.isModEnabled()) {
                    return;
                }
                PunchyAnimationManager.flushPendingMixReload(class_3102);
                EntityCullingCompat.tick();
                class_746 player = class_3102.field_1724;
                if (BetterCombatCompat.isAvailable()) {
                    bl3 = class_3102.field_1690.method_31044().method_31034();
                    bl2 = PunchyConfig.isHandBlacklisted(player, class_1268.field_5808) || PunchyConfig.isHandBlacklisted(player, class_1268.field_5810);
                    bl = FirstPersonModelCompat.isRenderingPlayer() || FirstPersonModelCompat.shouldApplyThirdPersonAnimations(class_3102);
                    boolean bl4 = PunchyConfig.isBetterCombatCompatEnabled() && bl3 && !bl2 && !bl;
                    BetterCombatCompat.applyVanillaItemDisplay(bl4);
                    if (bl4) {
                        BetterCombatCompat.forceStopAttackAnimation(player);
                        if (!PunchyAnimationManager.isAnyHandBlacklisted()) {
                            BetterCombatCompat.suppressItemPoses(player);
                        }
                    }
                }
                if (SpellEngineCompat.isAvailable()) {
                    bl3 = class_3102.field_1690.method_31044().method_31034();
                    bl2 = PunchyConfig.isHandBlacklisted(player, class_1268.field_5808) || PunchyConfig.isHandBlacklisted(player, class_1268.field_5810);
                    bl = PunchyConfig.isBetterCombatCompatEnabled() && bl3 && !bl2;
                    SpellEngineCompat.applyFirstPersonAnimations(bl);
                }
                FirstPersonModelCompat.tick(class_3102);
                PunchyAnimationManager.enforceBlacklist(class_3102);
                float f = PunchyAnimationManager.ANIMATION_SPEED;
                PunchyAnimationManager.POSE_HANDLER.tick(0.05f, f);
                PunchyAnimationManager.syncSourceHandToActiveClip();
                PunchyAnimationManager.SWIM_HANDLER.tick(0.05f, f);
                PunchyThirdPersonAnimationManager.tick(class_3102);
                MINING_STATE.tick((class_310)class_3102);
                MUD_STATE.tick((class_310)class_3102);
                SweatStateMachine.stopWet();
                CONSUME_STATE.tick((class_310)class_3102);
                USE_STATE.tick((class_310)class_3102);
                BOW_STATE.tick((class_310)class_3102);
                CROSSBOW_STATE.tick((class_310)class_3102);
                SPEAR_STATE.tick((class_310)class_3102);
                SHIELD_STATE.tick((class_310)class_3102);
                TRIDENT_STATE.tick((class_310)class_3102);
                FIRE_STATE.tick((class_310)class_3102);
                ATTACK_STATE.tick((class_310)class_3102);
                FALLING_STATE.tick((class_310)class_3102);
                INSPECT_STATE.tick((class_310)class_3102);
                TRIGGERABLE_STATE.tick((class_310)class_3102);
                BedrockItemSystem.get().tick((class_310)class_3102);
                WALK_ON_WATER_STATE.tick((class_310)class_3102);
                SWIM_STATE.tick((class_310)class_3102);
                DRY_OUT_STATE.tick((class_310)class_3102);
                CRAWL_STATE.tick((class_310)class_3102);
                CLIMB_STATE.tick((class_310)class_3102);
                WAKE_UP_STATE.tick((class_310)class_3102);
                HAND_EQUIP_STATE.tick((class_310)class_3102);
                ModelPartsSystem.get().tick((class_310)class_3102);
                IDLE_POSE_STATE.tick((class_310)class_3102);
                if (!PunchyConfig.disableNativeItemPhysics()) {
                    MacePhysics.get().tick(player);
                }
                InterfaceDelayState.tick(class_3102);
                PunchyAnimationManager.updateUseMeshSuppression(class_3102);
                PunchyAnimationManager.updateItemOverrides(class_3102);
                PunchyAnimationManager.updateSoundEffects(class_3102);
            }
        });
    }

    private static void onResourceReload(class_3300 class_33002) {
        if (class_33002 == null) {
            RESOURCES_READY = false;
        } else {
            class_310 class_3102 = class_310.method_1551();
            if (class_3102 != null && class_3102.field_1697 != null) {
                PunchyConfig.load();
                PunchyAnimationManager.setAnimationSpeed(PunchyConfig.getAnimationSpeed());
                PunchyAnimationManager.loadAnimations(class_33002);
                PunchyThirdPersonAnimationManager.loadAnimations(class_33002);
                PunchyTuningConfig.loadAndApply(class_33002);
                ModelPartsParticleSystem.resetAll();
                ModelPartsSystem.get().onResourceReload(class_33002);
                BedrockItemSystem.get().onResourceReload(class_33002);
                EmfEtfCemCompat.onResourceReload(class_33002);
                RESOURCES_READY = true;
            } else {
                RESOURCES_READY = false;
            }
        }
    }

    private static void resetTransientWorldState() {
        ATTACK_STATE.resetSessionState();
        AttackActionTracker.clear();
        UseActionTracker.clear();
        InspectActionTracker.clear();
        TriggerableActionTracker.clear();
    }
}
