package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import java.util.List;
import java.util.Locale;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1835;
import net.minecraft.class_1839;
import net.minecraft.class_239;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_5321;
import net.minecraft.class_6862;
import net.minecraft.class_7924;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.state.AttackActionTracker;
import ru.method.celebression.punchy.client.state.CustomAnimationResolver;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.client.state.MovementStateMachine;
import ru.method.celebression.punchy.client.state.UseActionTracker;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class SpearStateMachine {
    private static final long COMBO_RESET_TICKS = 30L;
    private static final class_6862<class_1792> SPEAR_TAG = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"spears"));
    private boolean wasUsing;
    private class_1268 usingHand;
    private int activeStage;
    private AnimationClip activeUseClip;
    private boolean chargeMaxed;
    private boolean wasHitting;
    private long lastComboTick = -1L;
    private int comboIndex;
    private int lastComboSize;

    public void tick(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1268 class_12682;
            if (HandEquipStateMachine.isEquipInProgress()) {
                if (!this.shouldAbortEquipForAction(class_3102)) {
                    return;
                }
                if (!HandEquipStateMachine.abortEquipForAction(class_3102)) {
                    return;
                }
            }
            this.tickUse(class_3102);
            this.checkImpact(class_3102);
            class_1268 class_12683 = AttackActionTracker.peekHand();
            if (class_12683 != null && !PunchyAnimationManager.isHandBlacklisted(class_3102, class_12683) && (class_12682 = this.resolveSpearHand(class_3102)) != null && class_12682 == class_12683) {
                AttackActionTracker.consumeHand();
                AnimationClip animationClip = this.resolveSpearAttackClip(class_3102, class_12682);
                if (animationClip != null) {
                    this.playSpearAttack(class_3102, class_12682, animationClip);
                }
            }
        }
    }

    private boolean shouldAbortEquipForAction(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl = class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
            boolean bl2 = class_3102.field_1724.method_6115();
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            boolean bl3 = bl2 && bl && SpearStateMachine.isSpearStack(class_17992);
            class_1268 class_12682 = AttackActionTracker.peekHand();
            class_1268 class_12683 = this.resolveSpearHand(class_3102);
            boolean bl4 = class_12682 != null && class_12683 != null && class_12683 == class_12682;
            return bl3 || bl4;
        }
        return false;
    }

    private void tickUse(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1268 class_12682;
            boolean bl;
            boolean bl2 = class_3102.field_1690 != null && class_3102.field_1690.field_1904.method_1434();
            boolean bl3 = class_3102.field_1724.method_6115();
            class_1799 class_17992 = class_3102.field_1724.method_6030();
            if (!bl2) {
                this.chargeMaxed = false;
            }
            boolean bl4 = bl = bl3 && bl2 && SpearStateMachine.isSpearStack(class_17992);
            if (bl && PunchyAnimationManager.isHandBlacklisted(class_3102, class_12682 = class_3102.field_1724.method_6058())) {
                if (this.wasUsing) {
                    UseActionTracker.clear();
                    this.stopUse();
                    this.wasUsing = false;
                }
                return;
            }
            if (this.chargeMaxed && bl2) {
                if (this.wasUsing) {
                    UseActionTracker.clear();
                    this.stopUse();
                    this.wasUsing = false;
                }
            } else {
                if (bl) {
                    UseActionTracker.clear();
                    class_12682 = class_3102.field_1724.method_6058();
                    ChargeInfo chargeInfo = this.resolveChargeInfo(class_3102, class_17992);
                    if (chargeInfo.hasLimit && chargeInfo.elapsedTicks > chargeInfo.stage3Limit) {
                        this.chargeMaxed = true;
                        this.stopUse();
                        this.wasUsing = false;
                        return;
                    }
                    int n = chargeInfo.stage;
                    if (n == 0) {
                        this.stopUse();
                    } else {
                        this.playUseStage(class_3102, class_17992, class_12682, n);
                    }
                } else if (this.wasUsing) {
                    UseActionTracker.clear();
                    this.stopUse();
                }
                this.wasUsing = bl;
            }
        }
    }

    private void checkImpact(class_310 class_3102) {
        if (this.wasUsing && class_3102.field_1765 != null) {
            double d;
            boolean bl = false;
            if (class_3102.field_1765.method_17783() == class_239.class_240.field_1331) {
                bl = true;
            } else if (class_3102.field_1765.method_17783() == class_239.class_240.field_1332 && (d = class_3102.field_1765.method_17784().method_1022(class_3102.field_1724.method_33571())) < 3.0) {
                bl = true;
            }
            if (bl && !this.wasHitting) {
                class_1268 class_12682 = class_3102.field_1724.method_6058();
                class_1306 class_13062 = class_12682 == class_1268.field_5810 ? (class_3102.field_1724.method_6068() == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182) : class_3102.field_1724.method_6068();
                MovementStateMachine.get().triggerSpearImpact(class_13062);
            }
            this.wasHitting = bl;
        } else {
            this.wasHitting = false;
        }
    }

    private void playUseStage(class_310 class_3102, class_1799 class_17992, class_1268 class_12682, int n) {
        AnimationClip animationClip = this.resolveUseClip(class_3102, class_17992, class_12682, n);
        if (animationClip != null && (this.activeUseClip != animationClip || this.activeStage != n || this.usingHand != class_12682)) {
            PunchyAnimationManager.setSourceHand(class_3102, class_12682);
            boolean bl = PunchyAnimationManager.isDualHandedActive(class_3102, animationClip, 0.0f);
            if (bl) {
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(null);
            } else {
                class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
                PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
            }
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            float f = 1.0f / Math.max(0.01f, PunchyAnimationManager.ANIMATION_SPEED);
            boolean bl2 = CustomAnimationResolver.shouldSkipBlendInOnIdlePose();
            PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl2, animationClip.isHoldOnLastFrame());
            this.activeUseClip = animationClip;
            this.activeStage = n;
            this.usingHand = class_12682;
        }
    }

    private void stopUse() {
        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
        PunchyAnimationManager.POSE_HANDLER.stopImmediateWithBlendOut();
        PunchyAnimationManager.clearSourceHand();
        this.activeUseClip = null;
        this.activeStage = 0;
        this.usingHand = null;
    }

    private AnimationClip resolveUseClip(class_310 class_3102, class_1799 class_17992, class_1268 class_12682, int n) {
        AnimationClip animationClip = this.resolveCustomSpearChargeClip(class_3102, class_17992, class_12682, n);
        if (animationClip != null) {
            return animationClip;
        }
        if (n == 1) {
            return PunchyAnimationManager.USE_SPEAR_POSE_1;
        }
        if (n == 2) {
            return PunchyAnimationManager.USE_SPEAR_POSE_2;
        }
        return n == 3 ? PunchyAnimationManager.resolveHandedClip("use_spear_pose_3", class_3102, class_12682) : null;
    }

    private AnimationClip resolveCustomSpearChargeClip(class_310 class_3102, class_1799 class_17992, class_1268 class_12682, int n) {
        if (class_17992 != null && !class_17992.method_7960()) {
            List<PunchyTuningConfig.CustomAnimationEntry> list = ToolTuning.getCustomAnimation(class_17992);
            if (list != null && !list.isEmpty()) {
                for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list) {
                    if (!SpearStateMachine.isSpearChargeType(customAnimationEntry)) continue;
                    String clipName = switch (n) {
                        case 1 -> customAnimationEntry.stage_1;
                        case 2 -> customAnimationEntry.stage_2;
                        case 3 -> customAnimationEntry.stage_3;
                        default -> null;
                    };
                    if (clipName != null && !clipName.isBlank()) {
                        AnimationClip animationClip = PunchyAnimationManager.resolveCustomClip(clipName);
                        if (animationClip != null) {
                            return animationClip;
                        }
                        return PunchyAnimationManager.resolveNamedClip(clipName, class_3102, class_12682);
                    }
                    return null;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    private ChargeInfo resolveChargeInfo(class_310 class_3102, class_1799 class_17992) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            int n = class_17992.method_7935((class_1309)class_3102.field_1724) - (class_3102.field_1724.method_6014() + 1);
            int n2 = Math.max(0, n);
            int n3 = 10;
            int n4 = 20;
            int n5 = 30;
            if (n2 < n3) {
                return new ChargeInfo(1, n2, n5, false);
            }
            return n2 < n4 ? new ChargeInfo(2, n2, n5, false) : new ChargeInfo(3, n2, n5, false);
        }
        return new ChargeInfo(1, 0, Integer.MAX_VALUE, false);
    }

    private AnimationClip resolveSpearAttackClip(class_310 class_3102, class_1268 class_12682) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry;
            class_1799 class_17992 = class_3102.field_1724.method_5998(class_12682);
            AnimationClip animationClip = this.resolveCustomSpearAttackClip(class_17992, class_12682, class_3102, customAnimationEntry = CustomAnimationResolver.findEntry(class_17992, "attack"));
            if (animationClip != null) {
                return animationClip;
            }
            AnimationClip animationClip2 = PunchyAnimationManager.ATTACK_SPEAR_CLIP;
            return animationClip2 != null ? animationClip2 : PunchyAnimationManager.resolveHandedClip("attack_spear", class_3102, class_12682);
        }
        return null;
    }

    private AnimationClip resolveCustomSpearAttackClip(class_1799 class_17992, class_1268 class_12682, class_310 class_3102, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (class_17992 != null && !class_17992.method_7960()) {
            List<String> list = CustomAnimationResolver.resolveComboNames(customAnimationEntry);
            if (list.isEmpty()) {
                return null;
            }
            int n = this.nextComboIndex(this.currentTick(class_3102), list.size());
            String string = list.get(n);
            return PunchyAnimationManager.resolveNamedClip(string, class_3102, class_12682);
        }
        return null;
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

    private void playSpearAttack(class_310 class_3102, class_1268 class_12682, AnimationClip animationClip) {
        class_1306 class_13062 = PunchyAnimationManager.getUseArm(class_3102, class_12682);
        PunchyAnimationManager.POSE_HANDLER.setActiveArm(class_13062);
        PunchyAnimationManager.POSE_HANDLER.setLooping(false);
        float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
        boolean bl = CustomAnimationResolver.shouldSkipBlendInOnIdlePose();
        PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl);
    }

    private class_1268 resolveSpearHand(class_310 class_3102) {
        class_1799 class_17992 = class_3102.field_1724.method_6047();
        if (this.isSpear(class_17992)) {
            return class_1268.field_5808;
        }
        class_1799 class_17993 = class_3102.field_1724.method_6079();
        return this.isSpear(class_17993) ? class_1268.field_5810 : null;
    }

    public static boolean isSpearStack(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        if (class_17992.method_7909() instanceof class_1835) {
            return false;
        }
        if (SpearStateMachine.hasCustomSpearChargeAnimation(class_17992)) {
            return true;
        }
        if (ToolTuning.getSpecificCustomAnimation(class_17992) != null) {
            return false;
        }
        return class_17992.method_7976() == class_1839.field_8951 ? true : class_17992.method_31573(SPEAR_TAG);
    }

    private static boolean hasCustomSpearChargeAnimation(class_1799 class_17992) {
        List<PunchyTuningConfig.CustomAnimationEntry> list = ToolTuning.getCustomAnimation(class_17992);
        if (list != null && !list.isEmpty()) {
            for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list) {
                if (!SpearStateMachine.isSpearChargeType(customAnimationEntry)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static boolean isSpearChargeType(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry != null && customAnimationEntry.type != null && !customAnimationEntry.type.isBlank()) {
            String string = customAnimationEntry.type.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "").replace(" ", "");
            return "spearcharge".equals(string);
        }
        return false;
    }

    private long currentTick(class_310 class_3102) {
        return class_3102.field_1687 == null ? 0L : class_3102.field_1687.method_8510();
    }

    private boolean isSpear(class_1799 class_17992) {
        return SpearStateMachine.isSpearStack(class_17992);
    }

    static class ChargeInfo {
        final int stage;
        final int elapsedTicks;
        final int stage3Limit;
        final boolean hasLimit;

        ChargeInfo(int n, int n2, int n3, boolean bl) {
            this.stage = n;
            this.elapsedTicks = n2;
            this.stage3Limit = n3;
            this.hasLimit = bl;
        }
    }
}

