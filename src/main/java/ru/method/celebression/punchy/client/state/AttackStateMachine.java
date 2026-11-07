package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_1835;
import net.minecraft.class_239;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3966;
import net.minecraft.class_5321;
import net.minecraft.class_6862;
import net.minecraft.class_7924;
import net.minecraft.class_9362;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.render.ToolKindResolver;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.client.state.AttackActionTracker;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.MiningActionTracker;
import ru.method.celebression.punchy.compat.BetterCombatCompat;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class AttackStateMachine {
    private static final long FAST_TRIGGER_WINDOW_TICKS = 4L;
    private static final double VISUAL_ATTACK_MAX_CPS = 3.5;
    private static final double VISUAL_ATTACK_INTERVAL_TICKS = 5.714285714285714;
    private static final float THROTTLED_VISUAL_SPEED_MULTIPLIER = 1.12f;
    private static final int RECENT_EQUIP_SKIP_BLEND_WINDOW_TICKS = 8;
    private static final long COMBO_RESET_TICKS = 30L;
    private static final long TOOL_MINING_FALLBACK_MAX_TICKS = 4L;
    private long lastTriggerTick = -1L;
    private long lastComboTick = -1L;
    private int comboIndex;
    private int lastComboSize;
    private boolean toolMiningFallbackActive;
    private boolean toolMiningFallbackStopImmediate;
    private boolean toolMiningFallbackSkipBlendOut;
    private long toolMiningFallbackExpireTick = -1L;
    private String toolMiningFallbackClipName;
    private double nextVisualAttackTick = -1.0;
    private boolean visualThrottlePending;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            this.updateToolMiningFallback(class_3102);
            boolean bl = false;
            class_1268 class_12682 = AttackActionTracker.peekHand();
            if (class_12682 != null) {
                if (this.isAttackAnimationCoolingDown(class_3102)) {
                    this.visualThrottlePending = true;
                } else if (PunchyAnimationManager.isHandBlacklisted(class_3102, class_12682)) {
                    AttackActionTracker.consumeHand();
                } else {
                    if (this.shouldAbortEquipForHand(class_3102, class_12682) && HandEquipStateMachine.isEquipInProgress()) {
                        if (!HandEquipStateMachine.abortEquipForAction(class_3102, class_12682)) {
                            return;
                        }
                        bl = true;
                    }
                    if ((class_12682 = AttackActionTracker.consumeHand()) != null) {
                        this.playAttack(class_3102, class_12682, bl);
                    }
                }
            }
        }
    }

    private void playAttack(class_310 class_3102, class_1268 class_12682, boolean bl) {
        class_1799 class_17992 = this.resolveAttackStack(class_3102, class_12682);
        PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "attack");
        if (!this.requiresEntityHit(class_17992) || this.isEntityTarget(class_3102) || customAnimationEntry != null) {
            AnimationClip animationClip;
            String string;
            ItemAnimationManager.forceTypeOnce(class_12682, "attack", true);
            boolean bl2 = AttackActionTracker.consumeCritical();
            AnimationClip animationClip2 = null;
            boolean bl3 = false;
            if (bl2 && (string = CustomAnimationResolver.resolveCriticalName(customAnimationEntry)) != null) {
                animationClip2 = PunchyAnimationManager.resolveNamedClip(string, class_3102, class_12682);
            }
            if (animationClip2 == null && BetterCombatCompat.isAvailable() && PunchyConfig.isBetterCombatCompatEnabled()) {
                animationClip2 = this.resolveBetterCombatClip(class_17992, class_12682, class_3102);
                boolean bl4 = bl3 = animationClip2 != null;
            }
            if (animationClip2 == null) {
                animationClip2 = this.resolveCustomAttackClip(class_17992, class_12682, class_3102, customAnimationEntry);
            }
            string = PunchyTuningConfig.getMixPreferredOptionForStack(class_17992);
            if (animationClip2 == null) {
                CustomAnimationResolver.applyLoopSpeed(null);
                animationClip2 = PunchyAnimationManager.resolveHandedClip("attack", class_3102, class_12682, string);
            } else if (bl3) {
                CustomAnimationResolver.applyLoopSpeed(null);
            } else {
                CustomAnimationResolver.applyLoopSpeed(customAnimationEntry);
            }
            if (PunchyAnimationManager.isClipNamed(animationClip2, "attack") && class_17992.method_7909() instanceof class_9362 && (animationClip = PunchyAnimationManager.resolveHandedClip("attack_mace", class_3102, class_12682, string)) != null) {
                animationClip2 = animationClip;
            }
            if (PunchyAnimationManager.isClipNamed(animationClip2, "attack") && class_17992.method_7909() instanceof class_1835 && (animationClip = PunchyAnimationManager.resolveHandedClip("attack_trident", class_3102, class_12682, string)) != null) {
                animationClip2 = animationClip;
            }
            if (animationClip2 != null) {
                class_1306 useArm = PunchyAnimationManager.getUseArm(class_3102, class_12682);
                boolean bl5 = PunchyAnimationManager.isDualHandedActive(class_3102, animationClip2, 0.0f);
                boolean bl6 = bl5 && bl3 && !this.isOffhandWeapon(class_3102, class_12682);
                boolean bl7 = bl5 && HandEquipStateMachine.isEquipInProgress() && useArm != null;
                class_1306 class_13062 = useArm == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182;
                PunchyAnimationManager.POSE_HANDLER.setForcedSkipArmForNextPlay((class_1306)(!bl6 && !bl7 ? null : class_13062));
                PunchyAnimationManager.setSuppressOffhandForAttack(animationClip2, false);
                if (bl5 && !bl6 && !bl7) {
                    useArm = null;
                }
                this.primeOppositeArmOverlapDuringEquip(class_3102, class_12682, useArm);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(useArm);
                PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                long l = this.currentTick(class_3102);
                float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip2);
                if (this.visualThrottlePending) {
                    f *= 1.12f;
                }
                boolean bl8 = bl || HandEquipStateMachine.wasEquipTransitionRecent(class_3102, 8) || this.shouldSkipBlendIn(class_3102) || CustomAnimationResolver.shouldSkipBlendIn(customAnimationEntry);
                CustomAnimationResolver.playConfiguredClip(animationClip2, customAnimationEntry, f, bl8, false);
                this.lastTriggerTick = l;
                this.scheduleNextVisualAttack(l);
                this.visualThrottlePending = false;
            }
        } else if (this.shouldSuppressToolMiningFallback(class_3102)) {
            this.clearToolMiningFallbackState();
        } else {
            this.playToolMiningFallback(class_3102, class_12682, class_17992);
        }
    }

    private boolean isOffhandWeapon(class_310 class_3102, class_1268 class_12682) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1268 class_12683 = class_12682 == class_1268.field_5808 ? class_1268.field_5810 : class_1268.field_5808;
            class_1799 class_17992 = class_3102.field_1724.method_5998(class_12683);
            return BetterCombatCompat.isWeaponStack(class_17992);
        }
        return false;
    }

    private class_1799 resolveAttackStack(class_310 class_3102, class_1268 class_12682) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1268 class_12683 = class_12682 == null ? class_1268.field_5808 : class_12682;
            return class_3102.field_1724.method_5998(class_12683);
        }
        return class_1799.field_8037;
    }

    private boolean requiresEntityHit(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_17992.method_31573(class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"pickaxes"))) || class_17992.method_31573(class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"hoes"))) || class_17992.method_31573(class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"shovels"))) : false;
    }

    private boolean isEntityTarget(class_310 class_3102) {
        class_3966 class_39662;
        if (class_3102 == null) {
            return false;
        }
        class_239 class_2392 = class_3102.field_1765;
        if (class_2392 == null || class_2392.method_17783() != class_239.class_240.field_1331) {
            return false;
        }
        return class_2392 instanceof class_3966 ? (class_39662 = (class_3966)class_2392).method_17782() != null : false;
    }

    private void playToolMiningFallback(class_310 class_3102, class_1268 class_12682, class_1799 class_17992) {
        if (class_3102 != null && class_17992 != null && !class_17992.method_7960()) {
            ItemAnimationManager.forceTypeOnce(class_12682, "mining", true);
            String string = PunchyTuningConfig.getMixPreferredOptionForStack(class_17992);
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "mining");
            AnimationClip animationClip = this.resolveCustomMiningClip(class_3102, class_12682, customAnimationEntry);
            if (animationClip == null) {
                VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
                if (toolKind == VanillaFirstPersonItemLayer.ToolKind.PICKAXE || toolKind == VanillaFirstPersonItemLayer.ToolKind.HOE || toolKind == VanillaFirstPersonItemLayer.ToolKind.AXE || toolKind == VanillaFirstPersonItemLayer.ToolKind.SHOVEL) {
                    if (toolKind == VanillaFirstPersonItemLayer.ToolKind.AXE) {
                        animationClip = PunchyAnimationManager.resolveHandedClip("mining_sideways", class_3102, class_12682, string);
                    }
                    if (animationClip == null) {
                        animationClip = PunchyAnimationManager.resolveTypeFallbackClip("mining", class_3102, class_12682, string);
                    }
                    if (animationClip == null) {
                        animationClip = toolKind == VanillaFirstPersonItemLayer.ToolKind.PICKAXE || toolKind == VanillaFirstPersonItemLayer.ToolKind.HOE ? PunchyAnimationManager.resolveHandedClip("mining_foward", class_3102, class_12682, string) : (toolKind == VanillaFirstPersonItemLayer.ToolKind.AXE ? PunchyAnimationManager.resolveHandedClip("mining_foward", class_3102, class_12682, string) : PunchyAnimationManager.resolveHandedClip("mining_digging", class_3102, class_12682, string));
                    }
                }
            } else {
                CustomAnimationResolver.applyLoopSpeed(customAnimationEntry);
            }
            if (animationClip != null) {
                boolean bl = CustomAnimationResolver.shouldStopImmediately(customAnimationEntry);
                boolean bl2 = CustomAnimationResolver.shouldSkipBlendOut(customAnimationEntry);
                class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
                this.primeOppositeArmOverlapDuringEquip(class_3102, class_12682, class_13062);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
                PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
                boolean bl3 = this.shouldSkipBlendIn(class_3102) || CustomAnimationResolver.shouldSkipBlendIn(customAnimationEntry);
                CustomAnimationResolver.playConfiguredClip(animationClip, customAnimationEntry, f, bl3, false);
                this.lastTriggerTick = this.currentTick(class_3102);
                this.toolMiningFallbackActive = true;
                this.toolMiningFallbackStopImmediate = bl;
                this.toolMiningFallbackSkipBlendOut = bl2;
                this.toolMiningFallbackExpireTick = this.lastTriggerTick + 4L;
                this.toolMiningFallbackClipName = animationClip.getName();
            }
        }
    }

    private void updateToolMiningFallback(class_310 class_3102) {
        if (this.toolMiningFallbackActive) {
            if (class_3102 == null || class_3102.field_1724 == null) {
                this.clearToolMiningFallbackState();
            } else if (this.shouldSuppressToolMiningFallback(class_3102)) {
                this.clearToolMiningFallbackState();
            } else {
                AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
                if (PunchyAnimationManager.POSE_HANDLER.isPlaying() && animationClip != null && this.toolMiningFallbackClipName != null && this.toolMiningFallbackClipName.equals(animationClip.getName())) {
                    boolean bl;
                    long l = this.currentTick(class_3102);
                    boolean bl2 = bl = this.toolMiningFallbackExpireTick >= 0L && l >= this.toolMiningFallbackExpireTick;
                    if (bl) {
                        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
                        if (this.toolMiningFallbackSkipBlendOut) {
                            PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.0f);
                        }
                        if (this.toolMiningFallbackStopImmediate) {
                            PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
                        } else {
                            PunchyAnimationManager.POSE_HANDLER.requestStopAfterCurrent();
                        }
                        this.clearToolMiningFallbackState();
                    }
                } else {
                    this.clearToolMiningFallbackState();
                }
            }
        }
    }

    private boolean shouldSuppressToolMiningFallback(class_310 class_3102) {
        if (class_3102 == null) {
            return false;
        }
        if (MiningActionTracker.isMiningActive()) {
            return true;
        }
        class_239 class_2392 = class_3102.field_1765;
        return class_2392 != null && class_2392.method_17783() == class_239.class_240.field_1332;
    }

    private void clearToolMiningFallbackState() {
        this.toolMiningFallbackActive = false;
        this.toolMiningFallbackStopImmediate = false;
        this.toolMiningFallbackSkipBlendOut = false;
        this.toolMiningFallbackExpireTick = -1L;
        this.toolMiningFallbackClipName = null;
    }

    public void resetSessionState() {
        this.lastTriggerTick = -1L;
        this.lastComboTick = -1L;
        this.comboIndex = 0;
        this.lastComboSize = 0;
        this.nextVisualAttackTick = -1.0;
        this.visualThrottlePending = false;
        this.clearToolMiningFallbackState();
    }

    private AnimationClip resolveCustomMiningClip(class_310 class_3102, class_1268 class_12682, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (class_3102 == null) {
            return null;
        }
        class_1799 class_17992 = this.resolveAttackStack(class_3102, class_12682);
        return CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, "mining", customAnimationEntry, class_3102, class_12682).clip();
    }

    private boolean shouldSkipBlendIn(class_310 class_3102) {
        long l = this.currentTick(class_3102);
        return this.lastTriggerTick > 0L && l - this.lastTriggerTick <= 4L;
    }

    private boolean isAttackAnimationCoolingDown(class_310 class_3102) {
        double d = this.currentTick(class_3102);
        return this.nextVisualAttackTick >= 0.0 && d < this.nextVisualAttackTick;
    }

    private void scheduleNextVisualAttack(long l) {
        this.nextVisualAttackTick = !(this.nextVisualAttackTick < 0.0) && !((double)l >= this.nextVisualAttackTick + 5.714285714285714) ? (this.nextVisualAttackTick += 5.714285714285714) : (double)l + 5.714285714285714;
    }

    private long currentTick(class_310 class_3102) {
        return class_3102.field_1687 == null ? 0L : class_3102.field_1687.method_8510();
    }

    private boolean shouldAbortEquipForHand(class_310 class_3102, class_1268 class_12682) {
        if (class_3102 != null && class_12682 != null) {
            class_1306 class_13062 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
            if (class_13062 == null) {
                return false;
            }
            class_1306 class_13063 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
            return class_13063 == null || class_13063 == class_13062;
        }
        return true;
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

    private AnimationClip resolveCustomAttackClip(class_1799 class_17992, class_1268 class_12682, class_310 class_3102, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (class_17992 == null) {
            return null;
        }
        String string = PunchyTuningConfig.getMixPreferredOptionForEntry(class_17992, customAnimationEntry);
        List<String> list = CustomAnimationResolver.resolveComboNames(customAnimationEntry);
        if (list.isEmpty()) {
            return null;
        }
        int n = this.nextComboIndex(this.currentTick(class_3102), list.size());
        String string2 = list.get(n);
        return PunchyAnimationManager.resolveNamedClip(string2, class_3102, class_12682, string);
    }

    private AnimationClip resolveBetterCombatClip(class_1799 class_17992, class_1268 class_12682, class_310 class_3102) {
        List<String> list;
        PunchyTuningConfig.BetterCombatEntry betterCombatEntry = CustomAnimationResolver.findBetterCombatEntry(class_17992, "melee_combo");
        if (betterCombatEntry == null) {
            return null;
        }
        String string = PunchyTuningConfig.getMixPreferredOptionForStack(class_17992);
        boolean bl = !this.isOffhandWeapon(class_3102, class_12682);
        List<String> list2 = list = bl ? this.resolveSingleWeaponBetterCombatNames(betterCombatEntry) : CustomAnimationResolver.resolveBetterCombatComboNames(betterCombatEntry);
        if (list.isEmpty()) {
            return null;
        }
        int n = this.nextComboIndex(this.currentTick(class_3102), list.size());
        String string2 = list.get(n);
        return PunchyAnimationManager.resolveNamedClip(string2, class_3102, class_12682, string);
    }

    private List<String> resolveSingleWeaponBetterCombatNames(PunchyTuningConfig.BetterCombatEntry betterCombatEntry) {
        ArrayList<String> arrayList = new ArrayList<String>(2);
        AttackStateMachine.addIfPresent(arrayList, betterCombatEntry == null ? null : betterCombatEntry.attack_1);
        AttackStateMachine.addIfPresent(arrayList, betterCombatEntry == null ? null : betterCombatEntry.attack_3);
        return !arrayList.isEmpty() ? arrayList : CustomAnimationResolver.resolveBetterCombatComboNames(betterCombatEntry);
    }

    private static void addIfPresent(List<String> list, String string) {
        String string2;
        if (list != null && string != null && !string.isBlank() && !(string2 = string.trim()).isEmpty()) {
            list.add(string2);
        }
    }

    private int nextComboIndex(long l, int n) {
        if (n <= 1) {
            return 0;
        }
        if (this.lastComboTick < 0L || l - this.lastComboTick > 30L || n != this.lastComboSize) {
            this.comboIndex = 0;
        }
        if (this.comboIndex < 0 || this.comboIndex >= n) {
            this.comboIndex = 0;
        }
        int n2 = this.comboIndex;
        this.comboIndex = (this.comboIndex + 1) % n;
        this.lastComboTick = l;
        this.lastComboSize = n;
        return n2;
    }
}

