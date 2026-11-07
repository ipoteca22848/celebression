package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;

public class ClimbStateMachine {
    private static final String CLIMB_CLIP_NAME = "climb";
    private static final float RESUME_BLEND_IN_SECONDS = 0.25f;
    private static final int EQUIP_RELEASE_TICKS = 6;
    private static final int SWAP_HOLD_TICKS = 12;
    private boolean wasClimbing;
    private boolean suspendedByMain;
    private class_1306 suppressedMainArm;
    private boolean equipSequenceActive;
    private int equipReleaseTicks;
    private boolean equipTransitionActiveThisTick;
    private boolean swapHoldActiveThisTick;
    private int swapHoldTicks;
    private boolean swapKeyDown;
    private class_1799 lastMain = class_1799.field_8037;
    private class_1799 lastOff = class_1799.field_8037;
    private boolean swapSuspendActive;
    private static boolean suppressSwimForSwap;

    public void tick(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null) {
            this.stopClimbIfNeeded();
        } else if (!PunchyConfig.enableClimbAnimation()) {
            this.stopClimbIfNeeded();
        } else if (!class_3102.field_1690.method_31044().method_31034()) {
            this.stopClimbIfNeeded();
        } else {
            class_746 class_7462 = class_3102.field_1724;
            if (!this.isClimbingUp(class_3102, class_7462)) {
                this.stopClimbIfNeeded();
            } else {
                boolean bl = this.isMainAnimationActive();
                if (bl && this.isMainDualHanded()) {
                    this.suspendForMainAnimation();
                } else {
                    AnimationClip animationClip = PunchyAnimationManager.resolveCustomClip("climb");
                    if (animationClip == null) {
                        this.stopClimbIfNeeded();
                    } else {
                        boolean bl2;
                        if (!this.wasClimbing) {
                            this.lastMain = this.copyStack(class_7462.method_6047());
                            this.lastOff = this.copyStack(class_7462.method_6079());
                            this.swapHoldTicks = 0;
                            this.swapSuspendActive = false;
                        }
                        PoseHandler poseHandler = PunchyAnimationManager.SWIM_HANDLER;
                        float f = PunchyAnimationManager.clipSpeedMultiplier(animationClip);
                        this.swapHoldActiveThisTick = this.updateSwapHold(class_3102, class_7462);
                        suppressSwimForSwap = false;
                        this.equipTransitionActiveThisTick = bl2 = this.updateEquipSequenceState();
                        if (bl2) {
                            class_1306 class_13062 = this.resolveMainArm();
                            if (class_13062 != null) {
                                this.suppressedMainArm = class_13062;
                                poseHandler.requestSkipArm(class_13062);
                            } else if (this.suppressedMainArm != null) {
                                poseHandler.requestSkipArm(this.suppressedMainArm);
                            }
                        }
                        if (bl) {
                            this.suppressClimbArmForMainAnimation();
                        } else if (!bl2 && this.suppressedMainArm != null) {
                            poseHandler.requestBlendInArm(this.suppressedMainArm);
                            this.suppressedMainArm = null;
                        }
                        if (!bl2 && this.suppressedMainArm != null) {
                            poseHandler.setBlendInDurationNext(0.25f);
                        }
                        if (!bl && this.suspendedByMain) {
                            poseHandler.setBlendInDurationNext(0.25f);
                            this.suspendedByMain = false;
                        }
                        poseHandler.setLoopSpeedParams(Float.valueOf(1.0f), Float.valueOf(0.0f));
                        poseHandler.setIgnoreDualHanded(false);
                        poseHandler.setActiveArm(null);
                        poseHandler.setLooping(true);
                        poseHandler.setFrozen(false);
                        poseHandler.setPaused(!this.isMoving(class_3102));
                        if (!poseHandler.isPlaying() || poseHandler.getCurrentClip() != animationClip) {
                            poseHandler.play(animationClip, f, false, false);
                        }
                        this.lastMain = this.copyStack(class_7462.method_6047());
                        this.lastOff = this.copyStack(class_7462.method_6079());
                        this.wasClimbing = true;
                    }
                }
            }
        }
    }

    private boolean isClimbingUp(class_310 class_3102, class_746 class_7462) {
        if (class_3102 == null || class_7462 == null) {
            return false;
        }
        if (!class_7462.method_6101()) {
            return false;
        }
        double d = class_7462.method_18798().field_1351;
        if (d > 0.02) {
            return true;
        }
        return class_3102.field_1690 == null ? false : class_3102.field_1690.field_1894.method_1434() || class_3102.field_1690.field_1903.method_1434();
    }

    private void suspendForMainAnimation() {
        PoseHandler poseHandler = PunchyAnimationManager.SWIM_HANDLER;
        AnimationClip animationClip = poseHandler.getCurrentClip();
        if (animationClip != null && PunchyAnimationManager.isClipNamed(animationClip, "climb")) {
            poseHandler.setLooping(false);
            poseHandler.setPaused(false);
            poseHandler.setFrozen(false);
            poseHandler.stopImmediateWithBlendOut();
            this.suspendedByMain = true;
            this.suppressedMainArm = null;
        }
    }

    private void stopClimbIfNeeded() {
        if (this.wasClimbing) {
            PoseHandler poseHandler = PunchyAnimationManager.SWIM_HANDLER;
            AnimationClip animationClip = poseHandler.getCurrentClip();
            if (animationClip != null && PunchyAnimationManager.isClipNamed(animationClip, "climb")) {
                poseHandler.setLooping(false);
                poseHandler.setPaused(false);
                poseHandler.setFrozen(false);
                poseHandler.stopImmediateWithBlendOut();
            }
            this.wasClimbing = false;
            this.suspendedByMain = false;
            this.suppressedMainArm = null;
            this.equipSequenceActive = false;
            this.equipReleaseTicks = 0;
            this.equipTransitionActiveThisTick = false;
            this.swapHoldActiveThisTick = false;
            this.swapHoldTicks = 0;
            this.swapKeyDown = false;
            this.lastMain = class_1799.field_8037;
            this.lastOff = class_1799.field_8037;
            this.swapSuspendActive = false;
            suppressSwimForSwap = false;
        }
    }

    private boolean isMoving(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1690 != null && class_3102.field_1724 != null) {
            boolean bl = class_3102.field_1690.field_1894.method_1434();
            boolean bl2 = class_3102.field_1690.field_1881.method_1434();
            boolean bl3 = class_3102.field_1690.field_1913.method_1434();
            boolean bl4 = class_3102.field_1690.field_1849.method_1434();
            boolean bl5 = class_3102.field_1690.field_1903.method_1434();
            boolean bl6 = class_3102.field_1690.field_1832.method_1434();
            if (bl || bl2 || bl3 || bl4 || bl5 || bl6) {
                return true;
            }
            return class_3102.field_1724.method_6101() ? Math.abs(class_3102.field_1724.method_18798().field_1351) > 0.001 : Math.abs(class_3102.field_1724.field_6212) > 0.001f || Math.abs(class_3102.field_1724.field_6250) > 0.001f;
        }
        return false;
    }

    private boolean isMainAnimationActive() {
        return PunchyAnimationManager.POSE_HANDLER.isPlaying() && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() != null;
    }

    private boolean isEquipClipActive() {
        if (!this.isMainAnimationActive()) {
            return false;
        }
        AnimationClip animationClip = PunchyAnimationManager.POSE_HANDLER.getCurrentClip();
        if (animationClip == null) {
            return false;
        }
        String string = animationClip.getName();
        return "hand_in".equals(string) || "hand_out".equals(string);
    }

    private boolean isMainDualHanded() {
        if (!this.isMainAnimationActive()) {
            return false;
        }
        if (PunchyAnimationManager.isDualHandedActive()) {
            return true;
        }
        if (PunchyAnimationManager.POSE_HANDLER.getActiveArm() != null) {
            return false;
        }
        return PunchyAnimationManager.getSourceArm() != null ? false : PunchyAnimationManager.POSE_HANDLER.getActiveArm() == null;
    }

    private class_1306 resolveMainArm() {
        if (!this.isMainAnimationActive()) {
            return null;
        }
        if (this.isMainDualHanded()) {
            return null;
        }
        class_1306 class_13062 = PunchyAnimationManager.POSE_HANDLER.getActiveArm();
        return class_13062 != null ? class_13062 : PunchyAnimationManager.getSourceArm();
    }

    private void suppressClimbArmForMainAnimation() {
        PoseHandler poseHandler = PunchyAnimationManager.SWIM_HANDLER;
        if (!poseHandler.isPlaying()) {
            this.suppressedMainArm = null;
        } else {
            AnimationClip animationClip = poseHandler.getCurrentClip();
            if (!PunchyAnimationManager.isClipNamed(animationClip, "climb")) {
                this.suppressedMainArm = null;
            } else {
                class_1306 class_13062;
                class_1306 class_13063 = this.resolveMainArm();
                boolean bl = this.equipTransitionActiveThisTick;
                if (this.isEquipClipActive() && bl && (class_13062 = this.resolveMainArm()) != null) {
                    class_13063 = class_13062;
                }
                if (class_13063 != null) {
                    poseHandler.requestSkipArm(class_13063);
                    this.suppressedMainArm = class_13063;
                } else if (this.suppressedMainArm != null && !bl) {
                    poseHandler.requestBlendInArm(this.suppressedMainArm);
                    this.suppressedMainArm = null;
                }
            }
        }
    }

    private boolean isEquipTransitionActive() {
        return HandEquipStateMachine.isEquipInProgress() ? true : HandEquipStateMachine.getActiveType() != null;
    }

    private boolean isEquipActiveRaw() {
        if (HandEquipStateMachine.isEquipInProgress()) {
            return true;
        }
        return HandEquipStateMachine.getActiveType() != null ? true : this.isEquipClipActive();
    }

    private boolean updateEquipSequenceState() {
        boolean bl;
        boolean bl2 = bl = this.isEquipTransitionActive() || this.isEquipClipActive();
        if (bl) {
            this.equipSequenceActive = true;
            this.equipReleaseTicks = 6;
            return true;
        }
        if (!this.equipSequenceActive) {
            return false;
        }
        if (this.equipReleaseTicks > 0) {
            --this.equipReleaseTicks;
            return true;
        }
        this.equipSequenceActive = false;
        return false;
    }

    private boolean updateSwapHold(class_310 class_3102, class_746 class_7462) {
        if (class_7462 != null && class_3102 != null && class_3102.field_1690 != null) {
            boolean bl;
            boolean bl2 = class_3102.field_1690.field_1831.method_1434();
            boolean bl3 = bl2 && !this.swapKeyDown;
            this.swapKeyDown = bl2;
            if (bl3) {
                this.swapHoldTicks = 12;
                return true;
            }
            class_1799 class_17992 = class_7462.method_6047();
            class_1799 class_17993 = class_7462.method_6079();
            boolean bl4 = !this.isSameStack(this.lastMain, class_17992);
            boolean bl5 = !this.isSameStack(this.lastOff, class_17993);
            boolean bl6 = false;
            if (bl4 || bl5) {
                bl = this.isEmpty(this.lastMain);
                boolean bl7 = this.isEmpty(this.lastOff);
                boolean bl8 = this.isEmpty(class_17992);
                boolean bl9 = this.isEmpty(class_17993);
                if (!bl && !bl7 && this.isSameStack(class_17992, this.lastOff) && this.isSameStack(class_17993, this.lastMain)) {
                    bl6 = true;
                } else if (!bl && bl7 && bl8 && this.isSameStack(class_17993, this.lastMain)) {
                    bl6 = true;
                } else if (bl && !bl7 && bl9 && this.isSameStack(class_17992, this.lastOff)) {
                    bl6 = true;
                }
            }
            if (bl6) {
                this.swapHoldTicks = 6;
            }
            boolean bl10 = bl = this.swapHoldTicks > 0;
            if (this.swapHoldTicks > 0 && !bl6) {
                --this.swapHoldTicks;
            }
            return bl;
        }
        this.swapHoldTicks = 0;
        return false;
    }

    private boolean isSameStack(class_1799 class_17992, class_1799 class_17993) {
        if (this.isEmpty(class_17992) && this.isEmpty(class_17993)) {
            return true;
        }
        return !this.isEmpty(class_17992) && !this.isEmpty(class_17993) ? class_1799.method_31577((class_1799)class_17992, (class_1799)class_17993) : false;
    }

    private boolean isEmpty(class_1799 class_17992) {
        return class_17992 == null || class_17992.method_7960();
    }

    private class_1799 copyStack(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_17992.method_7972() : class_1799.field_8037;
    }

    public static boolean shouldSuppressSwimForSwap() {
        return suppressSwimForSwap;
    }
}

