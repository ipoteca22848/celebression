package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.Objects;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_1835;
import net.minecraft.class_239;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3965;
import net.minecraft.class_9334;
import net.minecraft.class_9336;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.render.ToolKindResolver;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.MiningActionTracker;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class MiningStateMachine {
    private static final long MINING_TARGET_LOCK_NANOS = 250000000L;
    private static final long RECENT_TARGET_GRACE_NANOS = 90000000L;
    private static final long RECENT_MINING_CHAIN_NANOS = 180000000L;
    private static final long MIN_MINING_DURATION_TICKS = 2L;
    private static final long MINING_V2_FASTEST_TRIGGER_TICKS = 1L;
    private static final long MINING_V2_SLOWEST_TRIGGER_TICKS = 6L;
    private static final float MINING_V2_START_SPEED_MIN = 1.0f;
    private static final float MINING_V2_START_SPEED_MAX = 2.35f;
    private static final float MINING_V2_START_SPEED_RESPONSE = 0.45f;
    private static final float STOP_IMMEDIATE_BLEND_OUT_SECONDS = 0.3f;
    private static final int TARGETED_GRACE_TICKS = 2;
    private static final int RECENT_TARGET_GRACE_TICKS = 1;
    private static final int RECENT_CHAIN_GRACE_TICKS = 2;
    private boolean wasMining;
    private long lastTriggerTick = -1L;
    private static final long FAST_TRIGGER_WINDOW_TICKS = 4L;
    private int stopGraceTicks = 0;
    private boolean stopImmediateOnRelease;
    private boolean skipBlendOut;
    private AnimationClip activeClip;
    private class_1306 activeArm;
    private class_1799 lastMiningStack = class_1799.field_8037;
    private long minStopTick = -1L;
    private MiningV2Phase miningV2Phase = MiningV2Phase.NONE;
    private PunchyTuningConfig.CustomAnimationEntry miningV2Entry;
    private AnimationClip miningV2StartClip;
    private AnimationClip miningV2LoopClip;
    private AnimationClip miningV2EndClip;
    private class_1799 lastMiningV2Stack = class_1799.field_8037;
    private long miningV2LastTriggerTick = -1L;
    private float miningV2StartSpeed = 1.0f;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null && class_3102.field_1761 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_6047();
            if (PunchyAnimationManager.isHandBlacklisted(class_3102, class_1268.field_5808)) {
                if (this.wasMining) {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "mining");
                    PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                    this.stopMiningAnimation(class_3102, true);
                    this.clearMiningState();
                    this.resetMiningV2();
                } else {
                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "mining");
                }
                this.wasMining = false;
            } else {
                boolean bl = MiningActionTracker.isMiningActive() && class_3102.field_1724 != null && !class_3102.field_1724.method_7325();
                long l = this.currentTick();
                boolean bl2 = this.shouldKeepMiningActive(bl, class_3102, l);
                boolean bl3 = MiningActionTracker.consumeTrigger();
                if (bl3 && !bl2 && !class_3102.field_1724.method_7325()) {
                    bl2 = true;
                }
                if (HandEquipStateMachine.shouldTreatHandOutActive()) {
                    this.clearMiningForEquipTransition();
                } else {
                    boolean bl4;
                    boolean bl5 = bl4 = this.hasMiningV2Entry(class_3102, class_17992) || this.miningV2Phase != MiningV2Phase.NONE;
                    if (!(this.isFunctionalSwapFromMainToOffhand(class_3102, class_17992) || bl2 && HandEquipStateMachine.isEquipInProgress() && this.shouldAbortEquipForMainHand(class_3102) && !HandEquipStateMachine.abortEquipForAction(class_3102, class_1268.field_5808) || bl3 && bl4 && HandEquipStateMachine.isEquipInProgress() && this.shouldAbortEquipForMainHand(class_3102) && !HandEquipStateMachine.abortEquipForAction(class_3102, class_1268.field_5808))) {
                        if (bl4) {
                            if (!bl2 && !bl3 && this.miningV2Phase != MiningV2Phase.NONE && this.stopImmediateOnRelease && !this.isAttackDown(class_3102)) {
                                PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                                if (this.skipBlendOut) {
                                    PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.0f);
                                } else {
                                    PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.3f);
                                }
                                PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                                ItemAnimationManager.clearPersistentType(class_1268.field_5808, "mining");
                                this.clearMiningState();
                                this.resetMiningV2();
                                this.wasMining = false;
                            } else {
                                if (!bl2 && !bl3 && this.miningV2Phase == MiningV2Phase.NONE) {
                                    ItemAnimationManager.clearPersistentType(class_1268.field_5808, "mining");
                                } else {
                                    ItemAnimationManager.setPersistentType(class_1268.field_5808, "mining", true, true);
                                    this.tickMiningV2(class_3102, class_17992, bl, bl2, bl3);
                                }
                                this.wasMining = bl2;
                            }
                        } else {
                            if (bl2) {
                                ItemAnimationManager.setPersistentType(class_1268.field_5808, "mining", true, true);
                                PunchyAnimationManager.POSE_HANDLER.setLooping(true);
                                boolean bl6 = this.hasItemChanged(this.lastMiningStack, class_17992);
                                if (bl6 && this.isFunctionalSwapFromMainToOffhand(class_3102, class_17992)) {
                                    this.wasMining = true;
                                    return;
                                }
                                if (!this.wasMining || !PunchyAnimationManager.POSE_HANDLER.isPlaying() || bl6) {
                                    this.playPunch();
                                }
                            } else if (this.wasMining) {
                                if (this.isFunctionalSwapFromMainToOffhand(class_3102, class_17992)) {
                                    this.wasMining = true;
                                    return;
                                }
                                ItemAnimationManager.clearPersistentType(class_1268.field_5808, "mining");
                                PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                                this.stopMiningAnimation(class_3102, false);
                                this.clearMiningState();
                            } else {
                                ItemAnimationManager.clearPersistentType(class_1268.field_5808, "mining");
                            }
                            this.wasMining = bl2;
                        }
                    }
                }
            }
        }
    }

    private void playPunch() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102.field_1724 != null) {
            String string;
            class_1799 class_17992 = class_3102.field_1724.method_6047();
            VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "mining");
            AnimationClip animationClip = this.resolveCustomMiningClip(class_3102, class_17992, customAnimationEntry);
            String string2 = "custom_entry";
            if (animationClip == null) {
                CustomAnimationResolver.applyLoopSpeed(null);
                string = PunchyTuningConfig.getMixPreferredOptionForStack(class_17992);
                String string3 = PunchyTuningConfig.getMixPreferredOptionForType(class_17992, "mining");
                animationClip = PunchyAnimationManager.resolveHandedClip("punch", class_3102, class_1268.field_5808, string);
                string2 = "fallback_punch";
                if (class_17992.method_7909() instanceof class_1835) {
                    AnimationClip animationClip2 = PunchyAnimationManager.resolveHandedClip("attack_trident", class_3102, class_1268.field_5808, string);
                    if (animationClip2 != null) {
                        animationClip = animationClip2;
                        string2 = "fallback_trident";
                    }
                } else if (toolKind == VanillaFirstPersonItemLayer.ToolKind.PICKAXE || toolKind == VanillaFirstPersonItemLayer.ToolKind.HOE || toolKind == VanillaFirstPersonItemLayer.ToolKind.AXE || toolKind == VanillaFirstPersonItemLayer.ToolKind.SHOVEL) {
                    if (toolKind == VanillaFirstPersonItemLayer.ToolKind.AXE && (animationClip = PunchyAnimationManager.resolveHandedClip("mining_sideways", class_3102, class_1268.field_5808, string)) != null) {
                        string2 = "fallback_kind_sideways";
                    }
                    if (animationClip == null && (animationClip = PunchyAnimationManager.resolveTypeFallbackClip("mining", class_3102, class_1268.field_5808, string)) != null) {
                        string2 = "fallback_type_alias";
                    }
                    if (animationClip == null) {
                        if (toolKind == VanillaFirstPersonItemLayer.ToolKind.PICKAXE || toolKind == VanillaFirstPersonItemLayer.ToolKind.HOE) {
                            animationClip = PunchyAnimationManager.resolveHandedClip("mining_foward", class_3102, class_1268.field_5808, string);
                            string2 = "fallback_kind_forward";
                        } else if (toolKind == VanillaFirstPersonItemLayer.ToolKind.AXE) {
                            animationClip = PunchyAnimationManager.resolveHandedClip("mining_foward", class_3102, class_1268.field_5808, string);
                            string2 = "fallback_kind_axe_forward";
                        } else {
                            animationClip = PunchyAnimationManager.resolveHandedClip("mining_digging", class_3102, class_1268.field_5808, string);
                            string2 = "fallback_kind_digging";
                        }
                    }
                }
            } else {
                CustomAnimationResolver.applyLoopSpeed(customAnimationEntry);
            }
            if (animationClip != null) {
                this.stopImmediateOnRelease = CustomAnimationResolver.shouldStopImmediately(customAnimationEntry);
                this.skipBlendOut = CustomAnimationResolver.shouldSkipBlendOut(customAnimationEntry);
                class_1306 miningArm = PunchyAnimationManager.getMiningArm();
                this.activeArm = miningArm;
                this.activeClip = animationClip;
                this.primeOppositeArmOverlapDuringEquip(class_3102, class_1268.field_5808, miningArm);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(miningArm);
                float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
                boolean bl = CustomAnimationResolver.shouldSkipBlendInOnIdlePose() || this.shouldSkipBlendIn() || CustomAnimationResolver.shouldSkipBlendIn(customAnimationEntry);
                CustomAnimationResolver.playConfiguredClip(animationClip, customAnimationEntry, f, bl, false);
                this.lastTriggerTick = this.currentTick();
                this.minStopTick = this.lastTriggerTick + 2L;
                this.lastMiningStack = this.copyStack(class_17992);
            }
        }
    }

    private class_1799 copyStack(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_17992.method_7972() : class_1799.field_8037;
    }

    private boolean hasItemChanged(class_1799 class_17992, class_1799 class_17993) {
        class_1799 class_17994;
        class_1799 class_17995 = class_17992 == null ? class_1799.field_8037 : class_17992;
        class_1799 class_17996 = class_17994 = class_17993 == null ? class_1799.field_8037 : class_17993;
        if (!class_17995.method_31574(class_17994.method_7909())) {
            return true;
        }
        for (class_9336 class_93362 : class_17994.method_57353()) {
            if (class_93362.comp_2443() == class_9334.field_49629) continue;
            if (!class_17995.method_57353().method_57832(class_93362.comp_2443())) {
                return true;
            }
            if (Objects.equals(class_17995.method_57824(class_93362.comp_2443()), class_93362.comp_2444())) continue;
            return true;
        }
        for (class_9336 class_93362 : class_17995.method_57353()) {
            if (class_93362.comp_2443() == class_9334.field_49629 || class_17994.method_57353().method_57832(class_93362.comp_2443())) continue;
            return true;
        }
        return false;
    }

    private boolean isFunctionalSwapFromMainToOffhand(class_310 class_3102, class_1799 class_17992) {
        if (class_3102 != null && class_3102.field_1724 != null && this.lastMiningStack != null && !this.lastMiningStack.method_7960()) {
            class_1799 class_17993 = class_3102.field_1724.method_6079();
            return !class_1799.method_31577((class_1799)this.lastMiningStack, (class_1799)class_17993) ? false : this.hasItemChanged(this.lastMiningStack, class_17992);
        }
        return false;
    }

    private AnimationClip resolveCustomMiningClip(class_310 class_3102, class_1799 class_17992, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        return class_17992 != null && customAnimationEntry != null ? CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, "mining", customAnimationEntry, class_3102, class_1268.field_5808).clip() : null;
    }

    private boolean hasMiningV2Entry(class_310 class_3102, class_1799 class_17992) {
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "mining_v2");
        if (customAnimationEntry == null) {
            return false;
        }
        MiningV2Selection miningV2Selection = this.resolveMiningV2Selection(class_3102, class_17992, customAnimationEntry);
        return miningV2Selection.isValid();
    }

    private void tickMiningV2(class_310 class_3102, class_1799 class_17992, boolean bl, boolean bl2, boolean bl3) {
        boolean bl4;
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "mining_v2");
        MiningV2Selection miningV2Selection = customAnimationEntry != null ? this.resolveMiningV2Selection(class_3102, class_17992, customAnimationEntry) : MiningV2Selection.INVALID;
        boolean bl5 = this.hasTrackableMiningTarget(class_3102);
        if (bl3) {
            this.registerMiningV2Trigger();
        }
        if (this.miningV2Phase == MiningV2Phase.NONE) {
            if (!miningV2Selection.isValid()) {
                if (bl2) {
                    PunchyAnimationManager.POSE_HANDLER.setLooping(true);
                    if (!this.wasMining || !PunchyAnimationManager.POSE_HANDLER.isPlaying() || this.hasItemChanged(this.lastMiningStack, class_17992)) {
                        this.playPunch();
                    }
                }
                return;
            }
            if (!bl2 && !bl3) {
                return;
            }
        }
        boolean bl6 = bl4 = this.miningV2Phase == MiningV2Phase.NONE || this.hasItemChanged(this.lastMiningV2Stack, class_17992) || this.miningV2Entry != customAnimationEntry;
        if (bl4) {
            if (!miningV2Selection.isValid()) {
                this.resetMiningV2();
            } else {
                this.miningV2Entry = customAnimationEntry;
                this.stopImmediateOnRelease = CustomAnimationResolver.shouldStopImmediately(customAnimationEntry);
                this.skipBlendOut = CustomAnimationResolver.shouldSkipBlendOut(customAnimationEntry);
                this.miningV2StartClip = miningV2Selection.start();
                this.miningV2LoopClip = miningV2Selection.loop();
                this.miningV2EndClip = miningV2Selection.end();
                this.startMiningV2(miningV2Selection.start());
                this.lastMiningV2Stack = this.copyStack(class_17992);
                this.lastMiningStack = this.copyStack(class_17992);
            }
        } else {
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry2;
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry3 = customAnimationEntry2 = customAnimationEntry != null ? customAnimationEntry : this.miningV2Entry;
            if (customAnimationEntry2 != null) {
                CustomAnimationResolver.applyLoopSpeed(customAnimationEntry2);
                this.stopImmediateOnRelease = CustomAnimationResolver.shouldStopImmediately(customAnimationEntry2);
                this.skipBlendOut = CustomAnimationResolver.shouldSkipBlendOut(customAnimationEntry2);
            }
            if (bl2 && this.miningV2Phase == MiningV2Phase.END && miningV2Selection.isValid()) {
                this.restartMiningV2FromTrigger(miningV2Selection.start());
                this.lastMiningV2Stack = this.copyStack(class_17992);
                this.lastMiningStack = this.copyStack(class_17992);
            } else if (bl3 && this.miningV2Phase == MiningV2Phase.END) {
                this.restartMiningV2FromTrigger(miningV2Selection.start());
                this.lastMiningV2Stack = this.copyStack(class_17992);
                this.lastMiningStack = this.copyStack(class_17992);
            } else {
                this.lastMiningV2Stack = this.copyStack(class_17992);
                this.lastMiningStack = this.copyStack(class_17992);
                PoseHandlerWrapper poseHandlerWrapper = new PoseHandlerWrapper();
                switch (this.miningV2Phase.ordinal()) {
                    case 1: {
                        this.applyMiningV2StartSpeed(miningV2Selection.start());
                        if (poseHandlerWrapper.isPlaying(miningV2Selection.start())) break;
                        if (this.isAttackDown(class_3102) && bl2) {
                            this.transitionToMiningV2Loop(miningV2Selection.loop());
                            break;
                        }
                        this.transitionToMiningV2End(this.miningV2EndClip);
                        break;
                    }
                    case 2: {
                        if (!this.isAttackDown(class_3102) || !bl2) {
                            this.beginMiningV2LoopEnd();
                            break;
                        }
                        if (!poseHandlerWrapper.isPlaying(miningV2Selection.loop())) {
                            this.transitionToMiningV2Loop(miningV2Selection.loop());
                            break;
                        }
                        PunchyAnimationManager.POSE_HANDLER.setLooping(true);
                        break;
                    }
                    case 3: {
                        if (bl3) {
                            if (!bl2) {
                                return;
                            }
                            this.restartMiningV2FromTrigger(miningV2Selection.start());
                            break;
                        }
                        if (poseHandlerWrapper.isPlaying(miningV2Selection.loop())) {
                            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                            break;
                        }
                        this.transitionToMiningV2End(this.miningV2EndClip);
                        break;
                    }
                    case 4: {
                        if (poseHandlerWrapper.isPlaying(this.miningV2EndClip) || poseHandlerWrapper.isCurrentOrBlending(this.miningV2EndClip)) break;
                        this.resetMiningV2();
                    }
                }
            }
        }
    }

    private void startMiningV2(AnimationClip animationClip) {
        if (animationClip != null) {
            class_1306 class_13062;
            this.activeArm = class_13062 = PunchyAnimationManager.getMiningArm();
            this.activeClip = animationClip;
            this.primeOppositeArmOverlapDuringEquip(class_310.method_1551(), class_1268.field_5808, class_13062);
            PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            float f = this.resolveMiningV2StartSpeed(animationClip);
            boolean bl = true;
            PunchyAnimationManager.POSE_HANDLER.setBlendInDurationNext(0.0f);
            PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl);
            this.miningV2Phase = MiningV2Phase.START;
            this.lastTriggerTick = this.currentTick();
            this.minStopTick = this.lastTriggerTick + 2L;
        }
    }

    private void restartMiningV2FromTrigger(AnimationClip animationClip) {
        if (animationClip != null) {
            PunchyAnimationManager.POSE_HANDLER.resetImmediate();
            this.startMiningV2(animationClip);
        }
    }

    private void transitionToMiningV2Loop(AnimationClip animationClip) {
        if (animationClip != null) {
            class_1306 class_13062;
            this.activeArm = class_13062 = PunchyAnimationManager.getMiningArm();
            this.activeClip = animationClip;
            this.primeOppositeArmOverlapDuringEquip(class_310.method_1551(), class_1268.field_5808, class_13062);
            PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            PunchyAnimationManager.POSE_HANDLER.setLooping(true);
            float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            if (PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
                PunchyAnimationManager.POSE_HANDLER.setBlendInDurationNext(0.06f);
                PunchyAnimationManager.POSE_HANDLER.setBlendInEasingNext(PoseHandler.BlendInEasing.SMOOTH);
                PunchyAnimationManager.POSE_HANDLER.playWithBlendFromCurrentDynamic(animationClip, f, false);
            } else {
                PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, true);
            }
            this.miningV2Phase = MiningV2Phase.LOOP;
        }
    }

    private void transitionToMiningV2End(AnimationClip animationClip) {
        this.transitionToMiningV2End(animationClip, PoseHandler.BlendInEasing.SMOOTH, 0.08f);
    }

    private void transitionToMiningV2End(AnimationClip animationClip, PoseHandler.BlendInEasing blendInEasing, float f) {
        if (animationClip == null) {
            this.resetMiningV2();
        } else {
            class_1306 class_13062;
            this.activeArm = class_13062 = PunchyAnimationManager.getMiningArm();
            this.activeClip = animationClip;
            this.primeOppositeArmOverlapDuringEquip(class_310.method_1551(), class_1268.field_5808, class_13062);
            PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            float f2 = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
            if (PunchyAnimationManager.POSE_HANDLER.isPlaying()) {
                PunchyAnimationManager.POSE_HANDLER.setRebaseReturnSpeedNext(4.5f);
                PunchyAnimationManager.POSE_HANDLER.playAtTimeRebasedFromCurrentReturnToOrigin(animationClip, f2, false, 0.0f);
            } else {
                PunchyAnimationManager.POSE_HANDLER.play(animationClip, f2, true);
            }
            this.miningV2Phase = MiningV2Phase.END;
        }
    }

    private void beginMiningV2LoopEnd() {
        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
        this.transitionToMiningV2End(this.miningV2EndClip, PoseHandler.BlendInEasing.BACK, 0.12f);
    }

    private void registerMiningV2Trigger() {
        long l = this.currentTick();
        float f = 1.0f;
        if (this.miningV2LastTriggerTick >= 0L) {
            long l2 = Math.max(1L, l - this.miningV2LastTriggerTick);
            float f2 = 1.0f - (float)(l2 - 1L) / 5.0f;
            f2 = class_3532.method_15363((float)f2, (float)0.0f, (float)1.0f);
            f2 *= f2;
            f = class_3532.method_16439((float)f2, (float)1.0f, (float)2.35f);
        }
        this.miningV2StartSpeed = class_3532.method_16439((float)0.45f, (float)this.miningV2StartSpeed, (float)f);
        this.miningV2StartSpeed = class_3532.method_15363((float)this.miningV2StartSpeed, (float)1.0f, (float)2.35f);
        this.miningV2LastTriggerTick = l;
    }

    private float resolveMiningV2StartSpeed(AnimationClip animationClip) {
        return PunchyAnimationManager.clipSpeedMultiplier(animationClip) * this.miningV2StartSpeed;
    }

    private void applyMiningV2StartSpeed(AnimationClip animationClip) {
        if (animationClip != null && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() == animationClip) {
            PunchyAnimationManager.POSE_HANDLER.setSpeedMultiplier(this.resolveMiningV2StartSpeed(animationClip));
        }
    }

    private boolean shouldSkipBlendIn() {
        long l = this.currentTick();
        return this.lastTriggerTick > 0L && l - this.lastTriggerTick <= 4L;
    }

    private boolean shouldKeepMiningActive(boolean bl, class_310 class_3102, long l) {
        if (bl) {
            this.stopGraceTicks = 0;
            return true;
        }
        if (this.wasMining && this.minStopTick >= 0L && l < this.minStopTick) {
            return true;
        }
        if (this.wasMining && this.isAttackDown(class_3102) && this.isAimingTrackableBlockNow(class_3102) && this.stopGraceTicks < 2) {
            ++this.stopGraceTicks;
            return true;
        }
        if (this.wasMining && this.isAttackDown(class_3102) && this.hasVeryRecentMiningTarget() && this.stopGraceTicks < 1) {
            ++this.stopGraceTicks;
            return true;
        }
        if (this.wasMining && this.isAttackDown(class_3102) && this.hasRecentMiningChain() && this.stopGraceTicks < 2) {
            ++this.stopGraceTicks;
            return true;
        }
        return false;
    }

    private boolean isAttackDown(class_310 class_3102) {
        return class_3102.field_1690 != null && class_3102.field_1690.field_1886.method_1434();
    }

    private boolean isAimingTrackableBlockNow(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1687 != null && class_3102.field_1765 != null) {
            class_239 class_2392 = class_3102.field_1765;
            if (class_2392.method_17783() == class_239.class_240.field_1332 && class_2392 instanceof class_3965) {
                class_3965 class_39652 = (class_3965)class_2392;
                class_2680 class_26802 = class_3102.field_1687.method_8320(class_39652.method_17777());
                return class_26802 != null && !class_26802.method_26215();
            }
            return false;
        }
        return false;
    }

    private boolean hasVeryRecentMiningTarget() {
        return MiningActionTracker.peekRecentTarget(90000000L) != null;
    }

    private boolean hasRecentMiningChain() {
        return MiningActionTracker.wasRecentMining(180000000L);
    }

    private void stopMiningAnimation(class_310 class_3102, boolean bl) {
        if (!bl && this.miningV2Phase != MiningV2Phase.NONE) {
            this.transitionToMiningV2End(this.miningV2EndClip);
        } else if (this.ownsActiveClip()) {
            if (this.skipBlendOut) {
                PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.0f);
            }
            if (this.stopImmediateOnRelease && !this.isAttackDown(class_3102)) {
                PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.3f);
                PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                return;
            }
            PunchyAnimationManager.POSE_HANDLER.requestStopAfterCurrent();
        }
    }

    private void clearMiningState() {
        this.stopImmediateOnRelease = false;
        this.skipBlendOut = false;
        this.activeClip = null;
        this.activeArm = null;
        this.lastMiningStack = class_1799.field_8037;
        this.minStopTick = -1L;
        this.stopGraceTicks = 0;
        this.lastTriggerTick = -1L;
    }

    private long currentTick() {
        class_310 class_3102 = class_310.method_1551();
        return class_3102.field_1687 == null ? 0L : class_3102.field_1687.method_8510();
    }

    private void resetMiningV2() {
        this.miningV2Phase = MiningV2Phase.NONE;
        this.miningV2Entry = null;
        this.miningV2StartClip = null;
        this.miningV2LoopClip = null;
        this.miningV2EndClip = null;
        this.activeClip = null;
        this.lastMiningV2Stack = class_1799.field_8037;
        this.miningV2LastTriggerTick = -1L;
        this.miningV2StartSpeed = 1.0f;
    }

    private boolean ownsActiveClip() {
        return PunchyAnimationManager.POSE_HANDLER.isCurrentClipOwnedBy(this.activeClip, this.activeArm);
    }

    private boolean shouldAbortEquipForMainHand(class_310 class_3102) {
        if (class_3102 == null) {
            return true;
        }
        class_1306 class_13062 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
        if (class_13062 == null) {
            return false;
        }
        class_1306 class_13063 = PunchyAnimationManager.getUseArm(class_3102, class_1268.field_5808);
        return class_13063 == null || class_13063 == class_13062;
    }

    private void primeOppositeArmOverlapDuringEquip(class_310 class_3102, class_1268 class_12682, class_1306 class_13062) {
        if (class_3102 != null && class_12682 != null && class_13062 != null && HandEquipStateMachine.isEquipInProgress()) {
            if (class_12682 == class_1268.field_5808) {
                HandEquipStateMachine.prepareOffhandForMainhandAction(class_3102);
            }
            if (PunchyAnimationManager.POSE_HANDLER.getActiveArm() == null) {
                class_1306 class_13063 = class_13062 == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182;
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13063);
            }
        }
    }

    private void clearMiningForEquipTransition() {
        ItemAnimationManager.clearPersistentType(class_1268.field_5808, "mining");
        this.wasMining = false;
        this.clearMiningState();
        this.resetMiningV2();
    }

    private MiningV2Selection resolveMiningV2Selection(class_310 class_3102, class_1799 class_17992, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (class_3102 != null && customAnimationEntry != null) {
            String string = PunchyTuningConfig.getMixPreferredOptionForEntry(class_17992, customAnimationEntry);
            AnimationClip animationClip = this.resolveNamedClip(CustomAnimationResolver.resolveStartName(customAnimationEntry), class_3102, string);
            AnimationClip animationClip2 = this.resolveNamedClip(CustomAnimationResolver.resolveLoopingName(customAnimationEntry), class_3102, string);
            AnimationClip animationClip3 = this.resolveNamedClip(CustomAnimationResolver.resolveEndName(customAnimationEntry), class_3102, string);
            return animationClip != null && animationClip2 != null && animationClip3 != null ? new MiningV2Selection(animationClip, animationClip2, animationClip3) : MiningV2Selection.INVALID;
        }
        return MiningV2Selection.INVALID;
    }

    private AnimationClip resolveNamedClip(String string, class_310 class_3102, String string2) {
        return string != null && !string.isBlank() ? PunchyAnimationManager.resolveNamedClip(string, class_3102, class_1268.field_5808, string2) : null;
    }

    private boolean hasTrackableMiningTarget(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1687 != null && class_3102.field_1765 != null) {
            class_239 class_2392 = class_3102.field_1765;
            if (class_2392.method_17783() == class_239.class_240.field_1332 && class_2392 instanceof class_3965) {
                class_3965 class_39652 = (class_3965)class_2392;
                class_2680 class_26802 = class_3102.field_1687.method_8320(class_39652.method_17777());
                return class_26802 != null && !class_26802.method_26215() ? true : MiningActionTracker.peekRecentTarget(250000000L) != null;
            }
            return MiningActionTracker.peekRecentTarget(250000000L) != null;
        }
        return MiningActionTracker.peekRecentTarget(250000000L) != null;
    }

    static enum MiningV2Phase {
        NONE,
        START,
        LOOP,
        LOOP_END_PENDING,
        END;

    }

    record MiningV2Selection(AnimationClip start, AnimationClip loop, AnimationClip end) {
        static final MiningV2Selection INVALID = new MiningV2Selection(null, null, null);

        boolean isValid() {
            return this.start != null && this.loop != null && this.end != null;
        }
    }

    static final class PoseHandlerWrapper {
        PoseHandlerWrapper() {
        }

        boolean isPlaying(AnimationClip animationClip) {
            return animationClip != null && PunchyAnimationManager.POSE_HANDLER.isPlaying() && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() == animationClip;
        }

        boolean isCurrentOrBlending(AnimationClip animationClip) {
            return animationClip != null && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() == animationClip;
        }
    }
}

