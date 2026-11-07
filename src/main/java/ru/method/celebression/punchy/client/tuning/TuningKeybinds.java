package ru.method.celebression.punchy.client.tuning;

import java.util.Locale;
import net.minecraft.class_10255;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_7923;
import ru.method.celebression.punchy.client.debug.PunchyDebug;
import ru.method.celebression.punchy.client.render.ChestProxyTuning;
import ru.method.celebression.punchy.client.render.EnchantParticleProxyTuning;
import ru.method.celebression.punchy.client.render.FishingLineProxyTuning;
import ru.method.celebression.punchy.client.render.FluidProxyTuning;
import ru.method.celebression.punchy.client.render.PaddleProxyTuning;
import ru.method.celebression.punchy.client.render.ToolKindResolver;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.renderer.layer.ArmMeshTuning;
import ru.method.celebression.punchy.client.renderer.layer.ArmToolTuning;
import ru.method.celebression.punchy.client.renderer.layer.PlayerArmModelLayer;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.client.state.SpearStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public final class TuningKeybinds {
    private static final String CATEGORY = "key.categories.punchy.tuning";
    private static final boolean ENABLE_TUNING_HOTKEYS = false;
    private static final VanillaFirstPersonItemLayer.ToolKind[] ITEM_KINDS = VanillaFirstPersonItemLayer.ToolKind.values();
    private static class_304 toggleContextKey;
    private static class_304 cycleKindKey;
    private static class_304 toggleSpecificityKey;
    private static class_304 toggleHandKey;
    private static class_304 cycleModeKey;
    private static class_304 cycleAxisKey;
    private static class_304 setAxisXKey;
    private static class_304 setAxisYKey;
    private static class_304 setAxisZKey;
    private static class_304 increaseKey;
    private static class_304 decreaseKey;
    private static class_304 saveKey;
    private static class_304 resetKey;
    private static class_304 mirrorKey;
    private static boolean registered;
    private static Target target;
    private static Axis axis;
    private static Mode mode;
    private static class_1268 hand;
    private static VanillaFirstPersonItemLayer.ToolKind currentKind;
    private static class_2960 currentItemId;
    private static ChestProxyTuning.Kind chestProxyKind;
    private static FluidProxyTuning.Kind fluidProxyKind;
    private static EnchantParticleProxyTuning.Kind enchantParticleKind;
    private static PaddleProxyTuning.Kind paddleProxyKind;
    private static FishingLineProxyTuning.Kind fishingLineProxyKind;

    private TuningKeybinds() {
    }

    public static void register() {
        if (!registered) {
            registered = true;
        }
    }

    public static void handleClientTick(class_310 class_3102) {
        if (registered && class_3102 != null && class_3102.field_1724 != null && PunchyConfig.isTuningEnabled() && class_3102.field_1755 == null && PunchyDebug.DEBUG_POSITION_ENABLED) {
            boolean bl;
            TuningKeybinds.autoSelectCurrentItem(class_3102);
            while (toggleContextKey.method_1436()) {
                TuningKeybinds.toggleContext(class_3102);
            }
            while (cycleKindKey.method_1436()) {
                TuningKeybinds.cycleKind(class_3102);
            }
            while (toggleSpecificityKey.method_1436()) {
                TuningKeybinds.toggleSpecificity(class_3102);
            }
            while (toggleHandKey.method_1436()) {
                hand = hand == class_1268.field_5808 ? class_1268.field_5810 : class_1268.field_5808;
                TuningKeybinds.autoSelectCurrentItem(class_3102);
                TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
            }
            while (cycleModeKey.method_1436()) {
                mode = Mode.values()[(mode.ordinal() + 1) % Mode.values().length];
                TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
            }
            while (cycleAxisKey.method_1436()) {
                TuningKeybinds.cycleAxis(class_3102);
            }
            if (setAxisXKey.method_1436()) {
                TuningKeybinds.setAxis(class_3102, Axis.X);
            }
            if (setAxisYKey.method_1436()) {
                TuningKeybinds.setAxis(class_3102, Axis.Y);
            }
            if (setAxisZKey.method_1436()) {
                TuningKeybinds.setAxis(class_3102, Axis.Z);
            }
            while (saveKey.method_1436()) {
                PunchyTuningConfig.saveCurrent();
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.saved_config"));
            }
            while (resetKey.method_1436()) {
                TuningKeybinds.resetCurrentTarget(class_3102);
            }
            while (mirrorKey.method_1436()) {
                TuningKeybinds.mirrorCurrentTarget(class_3102);
            }
            boolean bl2 = increaseKey.method_1434() && !decreaseKey.method_1434();
            boolean bl3 = bl = decreaseKey.method_1434() && !increaseKey.method_1434();
            if (bl2) {
                TuningKeybinds.applyDelta(class_3102, true);
            } else if (bl) {
                TuningKeybinds.applyDelta(class_3102, false);
            }
        }
    }

    private static void autoSelectCurrentItem(class_310 class_3102) {
        if (class_3102.field_1724 != null) {
            TuningKeybinds.autoSelectPaddleProxyKind(class_3102);
            class_1799 class_17992 = class_3102.field_1724.method_5998(hand);
            if (class_17992.method_7960()) {
                currentItemId = null;
            } else {
                currentItemId = class_7923.field_41178.method_10221(class_17992.method_7909());
                currentKind = TuningKeybinds.resolveTuningKind(class_17992);
                TuningKeybinds.autoSelectChestProxyKind();
                TuningKeybinds.autoSelectFluidProxyKind(class_17992);
            }
        }
    }

    private static VanillaFirstPersonItemLayer.ToolKind resolveTuningKind(class_1799 class_17992) {
        return SpearStateMachine.isSpearStack(class_17992) ? VanillaFirstPersonItemLayer.ToolKind.SPEAR : ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.AXE);
    }

    private static void autoSelectPaddleProxyKind(class_310 class_3102) {
        class_1297 class_12972;
        if (class_3102 != null && class_3102.field_1724 != null && (class_12972 = class_3102.field_1724.method_5854()) instanceof class_10255) {
            class_10255 class_102552 = (class_10255)class_12972;
            class_2960 entityTypeId = class_7923.field_41177.method_10221(class_102552.method_5864());
            paddleProxyKind = entityTypeId != null && entityTypeId.method_12832().contains("raft") ? PaddleProxyTuning.Kind.RAFT : PaddleProxyTuning.Kind.BOAT;
        }
    }

    private static void autoSelectChestProxyKind() {
        if (currentItemId != null) {
            String string = currentItemId.method_12832();
            if (string.endsWith("_chest_boat")) {
                chestProxyKind = ChestProxyTuning.Kind.BOAT;
            } else if (string.endsWith("_chest_raft")) {
                chestProxyKind = ChestProxyTuning.Kind.RAFT;
            } else if ("chest_minecart".equals(string)) {
                chestProxyKind = ChestProxyTuning.Kind.MINECART;
            }
        }
    }

    private static void autoSelectFluidProxyKind(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            if (class_17992.method_31574(class_1802.field_8705)) {
                fluidProxyKind = FluidProxyTuning.Kind.WATER;
            } else if (class_17992.method_31574(class_1802.field_8187)) {
                fluidProxyKind = FluidProxyTuning.Kind.LAVA;
            } else if (class_17992.method_31574(class_1802.field_8103)) {
                fluidProxyKind = FluidProxyTuning.Kind.MILK;
            }
        }
    }

    private static void toggleContext(class_310 class_3102) {
        target = switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> Target.ARM_KIND;
            case 1 -> Target.ARM_SPECIFIC;
            case 2 -> Target.ARM_GLOBAL;
            case 3, 4, 5, 6 -> Target.CHEST_PROXY;
            case 7 -> Target.FLUID_PROXY;
            case 8 -> Target.ENCHANT_PARTICLE_PROXY;
            case 9 -> Target.PADDLE_PROXY;
            case 10 -> Target.FISHING_LINE_PROXY;
            case 11 -> Target.ITEM_KIND;
        };
        TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
    }

    private static void toggleSpecificity(class_310 class_3102) {
        if (target != Target.CHEST_PROXY && target != Target.FLUID_PROXY && target != Target.ENCHANT_PARTICLE_PROXY && target != Target.PADDLE_PROXY && target != Target.FISHING_LINE_PROXY) {
            target = switch (target.ordinal()) {
                default -> throw new IllegalStateException("Unexpected enum value");
                case 0 -> Target.ITEM_SPECIFIC;
                case 1 -> Target.ITEM_GLOBAL;
                case 2 -> Target.ITEM_KIND;
                case 3 -> Target.ARM_SPECIFIC;
                case 4 -> Target.ARM_GLOBAL;
                case 5 -> Target.ARM_MESH;
                case 6 -> Target.ARM_KIND;
                case 7 -> Target.CHEST_PROXY;
                case 8 -> Target.FLUID_PROXY;
                case 9 -> Target.ENCHANT_PARTICLE_PROXY;
                case 10 -> Target.PADDLE_PROXY;
                case 11 -> Target.FISHING_LINE_PROXY;
            };
            TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
        } else {
            TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.use_home_proxy"));
        }
    }

    private static void cycleKind(class_310 class_3102) {
        if (target == Target.CHEST_PROXY) {
            ChestProxyTuning.Kind[] kindArray = ChestProxyTuning.Kind.values();
            int n = chestProxyKind.ordinal() + 1;
            if (n >= kindArray.length) {
                n = 0;
            }
            chestProxyKind = kindArray[n];
            TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
        } else if (target == Target.FLUID_PROXY) {
            FluidProxyTuning.Kind[] kindArray = FluidProxyTuning.Kind.values();
            int n = fluidProxyKind.ordinal() + 1;
            if (n >= kindArray.length) {
                n = 0;
            }
            fluidProxyKind = kindArray[n];
            TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
        } else if (target == Target.PADDLE_PROXY) {
            PaddleProxyTuning.Kind[] kindArray = PaddleProxyTuning.Kind.values();
            int n = paddleProxyKind.ordinal() + 1;
            if (n >= kindArray.length) {
                n = 0;
            }
            paddleProxyKind = kindArray[n];
            TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
        } else if (target == Target.FISHING_LINE_PROXY) {
            TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
        } else if (target == Target.ENCHANT_PARTICLE_PROXY) {
            TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
        } else if (target == Target.ARM_SPECIFIC) {
            if (currentItemId == null) {
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.hold_item_change_arm_kind"));
            } else {
                PlayerArmModelLayer.ToolKind toolKind;
                VanillaFirstPersonItemLayer.ToolKind toolKind2;
                int n;
                PlayerArmModelLayer.ToolKind[] toolKindArray = PlayerArmModelLayer.ToolKind.values();
                PlayerArmModelLayer.ToolKind toolKind3 = TuningKeybinds.toArmKind(currentKind);
                if (toolKind3 == null) {
                    toolKind3 = PlayerArmModelLayer.ToolKind.AXE;
                }
                if ((n = toolKind3.ordinal() + 1) >= toolKindArray.length) {
                    n = 0;
                }
                if ((toolKind2 = TuningKeybinds.findMatchingItemKind(toolKind = toolKindArray[n])) != null) {
                    currentKind = toolKind2;
                }
                boolean bl = hand == class_1268.field_5810;
                ArmToolTuning.Profile profile = ArmToolTuning.getSpecificProfile(currentItemId, toolKind, bl);
                ArmToolTuning.applySpecific(currentItemId, toolKind, bl ? null : profile, bl ? profile : null);
                ArmToolTuning.SpecificSnapshot specificSnapshot = ArmToolTuning.snapshotSpecifics().get(currentItemId);
                if (specificSnapshot != null) {
                    ArmToolTuning.Profile profile2 = specificSnapshot.right();
                    ArmToolTuning.Profile profile3 = specificSnapshot.left();
                    ArmToolTuning.applySpecific(currentItemId, toolKind, profile2, profile3);
                }
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.arm_kind_set", (Object[])new Object[]{toolKind.name()}));
            }
        } else {
            int n = currentKind.ordinal() + 1;
            if (n >= ITEM_KINDS.length) {
                n = 0;
            }
            currentKind = ITEM_KINDS[n];
            if (target != Target.ITEM_KIND && target != Target.ARM_KIND) {
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.kind_selected_group", (Object[])new Object[]{currentKind.name()}));
            } else {
                TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
            }
        }
    }

    private static VanillaFirstPersonItemLayer.ToolKind findMatchingItemKind(PlayerArmModelLayer.ToolKind toolKind) {
        try {
            return VanillaFirstPersonItemLayer.ToolKind.valueOf(toolKind.name());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    private static void cycleAxis(class_310 class_3102) {
        if (!TuningKeybinds.mode.usesAxis) {
            TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.axis_not_used", (Object[])new Object[]{TuningKeybinds.modeLabel(mode)}));
        } else {
            axis = Axis.values()[(axis.ordinal() + 1) % Axis.values().length];
            TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
        }
    }

    private static void setAxis(class_310 class_3102, Axis axis) {
        if (!TuningKeybinds.mode.usesAxis) {
            TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.axis_not_used", (Object[])new Object[]{TuningKeybinds.modeLabel(mode)}));
        } else {
            TuningKeybinds.axis = axis;
            TuningKeybinds.toast(class_3102, TuningKeybinds.describeState());
        }
    }

    private static void applyDelta(class_310 class_3102, boolean bl) {
        if (mode != Mode.PIVOT || target != Target.ARM_GLOBAL && target != Target.ARM_MESH && target != Target.ARM_KIND && target != Target.ARM_SPECIFIC && target != Target.CHEST_PROXY && target != Target.FLUID_PROXY && target != Target.ENCHANT_PARTICLE_PROXY && target != Target.PADDLE_PROXY && target != Target.FISHING_LINE_PROXY) {
            float f = TuningKeybinds.computeStep(class_3102);
            if (!bl) {
                f *= -1.0f;
            }
            boolean bl2 = hand == class_1268.field_5810;
            switch (target.ordinal()) {
                case 0: {
                    ToolTuning.Profile profile = ToolTuning.get(currentKind, bl2);
                    AdjustmentResult adjustmentResult = TuningKeybinds.adjustToolProfile(profile, f);
                    if (adjustmentResult == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.apply_failed"));
                        return;
                    }
                    ToolTuning.commitKind(currentKind, bl2);
                    ToolTuning.markKindDetached(currentKind, bl2);
                    TuningKeybinds.report(class_3102, adjustmentResult);
                    break;
                }
                case 1: {
                    if (currentItemId == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.hold_item_edit_specific"));
                        return;
                    }
                    ToolTuning.Profile profile = ToolTuning.getSpecificProfile(currentItemId, currentKind, bl2);
                    if (profile == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.prepare_specific_failed"));
                        return;
                    }
                    AdjustmentResult adjustmentResult = TuningKeybinds.adjustToolProfile(profile, f);
                    if (adjustmentResult == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.apply_specific_failed"));
                        return;
                    }
                    TuningKeybinds.report(class_3102, adjustmentResult);
                    break;
                }
                case 2: {
                    ToolTuning.Profile profile = ToolTuning.getGlobal(bl2);
                    AdjustmentResult adjustmentResult = TuningKeybinds.adjustToolProfile(profile, f);
                    if (adjustmentResult == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.apply_global_failed"));
                        return;
                    }
                    ToolTuning.commitGlobal(bl2);
                    TuningKeybinds.report(class_3102, adjustmentResult);
                    break;
                }
                case 3: {
                    PlayerArmModelLayer.ToolKind toolKind = TuningKeybinds.toArmKind(currentKind);
                    if (toolKind == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.item_kind_no_arm_kind"));
                        return;
                    }
                    ArmToolTuning.Profile profile = ArmToolTuning.get(toolKind, bl2);
                    if (profile == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.arm_kind_profile_missing"));
                        return;
                    }
                    AdjustmentResult adjustmentResult = TuningKeybinds.adjustArmProfile(profile, f);
                    ArmToolTuning.commitKind(toolKind, bl2);
                    ArmToolTuning.markKindDetached(toolKind, bl2);
                    TuningKeybinds.report(class_3102, adjustmentResult);
                    break;
                }
                case 4: {
                    if (currentItemId == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.hold_item_edit_arm_specific"));
                        return;
                    }
                    PlayerArmModelLayer.ToolKind toolKind = TuningKeybinds.toArmKind(currentKind);
                    if (toolKind == null) {
                        toolKind = PlayerArmModelLayer.ToolKind.AXE;
                    }
                    ArmToolTuning.Profile profile = ArmToolTuning.getSpecificProfile(currentItemId, toolKind, bl2);
                    TuningKeybinds.report(class_3102, TuningKeybinds.adjustArmProfile(profile, f));
                    ArmToolTuning.commitSpecific(currentItemId, bl2);
                    break;
                }
                case 5: {
                    ArmToolTuning.Profile profile = ArmToolTuning.getGlobal(bl2);
                    TuningKeybinds.report(class_3102, TuningKeybinds.adjustArmProfile(profile, f));
                    ArmToolTuning.commitGlobal(bl2);
                    break;
                }
                case 6: {
                    ArmMeshTuning.Profile profile = ArmMeshTuning.getGlobal(bl2);
                    TuningKeybinds.report(class_3102, TuningKeybinds.adjustArmMeshProfile(profile, f));
                    break;
                }
                case 7: {
                    ChestProxyTuning.Profile profile = ChestProxyTuning.get(chestProxyKind);
                    if (profile == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.chest_proxy_profile_missing"));
                        return;
                    }
                    AdjustmentResult adjustmentResult = TuningKeybinds.adjustChestProxyProfile(profile, f);
                    TuningKeybinds.report(class_3102, adjustmentResult);
                    break;
                }
                case 8: {
                    FluidProxyTuning.Profile profile = FluidProxyTuning.get(fluidProxyKind);
                    if (profile == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.fluid_proxy_profile_missing"));
                        return;
                    }
                    AdjustmentResult adjustmentResult = TuningKeybinds.adjustFluidProxyProfile(profile, f);
                    TuningKeybinds.report(class_3102, adjustmentResult);
                    break;
                }
                case 9: {
                    EnchantParticleProxyTuning.Profile profile = EnchantParticleProxyTuning.get(enchantParticleKind, bl2);
                    if (profile == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.enchant_proxy_profile_missing"));
                        return;
                    }
                    AdjustmentResult adjustmentResult = TuningKeybinds.adjustEnchantParticleProxyProfile(profile, f);
                    TuningKeybinds.report(class_3102, adjustmentResult);
                    break;
                }
                case 10: {
                    PaddleProxyTuning.Profile profile = PaddleProxyTuning.get(paddleProxyKind, bl2);
                    if (profile == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.paddle_proxy_profile_missing"));
                        return;
                    }
                    AdjustmentResult adjustmentResult = TuningKeybinds.adjustPaddleProxyProfile(profile, f);
                    TuningKeybinds.report(class_3102, adjustmentResult);
                    break;
                }
                case 11: {
                    FishingLineProxyTuning.Profile profile = FishingLineProxyTuning.get(fishingLineProxyKind, bl2);
                    if (profile == null) {
                        TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.fishing_line_proxy_profile_missing"));
                        return;
                    }
                    AdjustmentResult adjustmentResult = TuningKeybinds.adjustFishingLineProxyProfile(profile, f);
                    TuningKeybinds.report(class_3102, adjustmentResult);
                }
            }
        } else {
            TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.pivot_item_only"));
        }
    }

    private static PlayerArmModelLayer.ToolKind toArmKind(VanillaFirstPersonItemLayer.ToolKind toolKind) {
        try {
            return PlayerArmModelLayer.ToolKind.valueOf(toolKind.name());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    private static void resetCurrentTarget(class_310 class_3102) {
        boolean bl = hand == class_1268.field_5810;
        switch (target.ordinal()) {
            case 0: {
                ToolTuning.Profile profile = ToolTuning.get(currentKind, bl);
                if (profile == null) {
                    TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.no_item_profile_loaded"));
                    return;
                }
                TuningKeybinds.resetToolProfile(profile);
                ToolTuning.commitKind(currentKind, bl);
                ToolTuning.markKindAttached(currentKind, bl);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.offsets_reset_kind"));
                break;
            }
            case 1: {
                if (currentItemId == null) {
                    TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.hold_item_reset_specific"));
                    return;
                }
                ToolTuning.Profile profile = ToolTuning.getSpecificProfile(currentItemId, currentKind, bl);
                if (profile == null) {
                    TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.specific_profile_missing"));
                    return;
                }
                TuningKeybinds.resetToolProfile(profile);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.offsets_reset_specific"));
                break;
            }
            case 2: {
                ToolTuning.Profile profile = ToolTuning.getGlobal(bl);
                if (profile == null) {
                    TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.no_global_item_profile"));
                    return;
                }
                TuningKeybinds.resetToolProfile(profile);
                ToolTuning.commitGlobal(bl);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.offsets_reset_global"));
                break;
            }
            case 3: {
                ArmToolTuning.Profile profile;
                PlayerArmModelLayer.ToolKind toolKind = TuningKeybinds.toArmKind(currentKind);
                if (toolKind == null || (profile = ArmToolTuning.get(toolKind, bl)) == null) break;
                TuningKeybinds.resetArmProfile(profile, toolKind, bl);
                ArmToolTuning.commitKind(toolKind, bl);
                ArmToolTuning.markKindAttached(toolKind, bl);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.arm_reset_kind", (Object[])new Object[]{toolKind.name()}));
                break;
            }
            case 4: {
                ArmToolTuning.Profile profile;
                if (currentItemId == null) break;
                PlayerArmModelLayer.ToolKind toolKind = TuningKeybinds.toArmKind(currentKind);
                if (toolKind == null) {
                    toolKind = PlayerArmModelLayer.ToolKind.AXE;
                }
                if ((profile = ArmToolTuning.getSpecificProfile(currentItemId, toolKind, bl)) == null) break;
                TuningKeybinds.resetArmProfile(profile, toolKind, bl);
                ArmToolTuning.commitSpecific(currentItemId, bl);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.arm_reset_specific"));
                break;
            }
            case 5: {
                ArmToolTuning.Profile profile = ArmToolTuning.getGlobal(bl);
                if (profile == null) {
                    TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.arm_profile_missing"));
                    return;
                }
                TuningKeybinds.resetArmProfile(profile, null, bl);
                ArmToolTuning.commitGlobal(bl);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.arm_offsets_reset"));
                break;
            }
            case 6: {
                ArmMeshTuning.Profile profile = ArmMeshTuning.getGlobal(bl);
                if (profile == null) {
                    TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.arm_profile_missing"));
                    return;
                }
                TuningKeybinds.resetArmMeshProfile(profile);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.arm_mesh_offsets_reset"));
                break;
            }
            case 7: {
                ChestProxyTuning.reset(chestProxyKind);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.chest_proxy_reset", (Object[])new Object[]{chestProxyKind.name()}));
                break;
            }
            case 8: {
                FluidProxyTuning.reset(fluidProxyKind);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.fluid_proxy_reset", (Object[])new Object[]{fluidProxyKind.name()}));
                break;
            }
            case 9: {
                EnchantParticleProxyTuning.reset(enchantParticleKind, bl);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.enchant_proxy_reset", (Object[])new Object[]{enchantParticleKind.name()}));
                break;
            }
            case 10: {
                PaddleProxyTuning.reset(paddleProxyKind, bl);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.paddle_proxy_reset", (Object[])new Object[]{paddleProxyKind.name()}));
                break;
            }
            case 11: {
                FishingLineProxyTuning.reset(fishingLineProxyKind, bl);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.fishing_line_proxy_reset", (Object[])new Object[]{fishingLineProxyKind.name()}));
            }
        }
    }

    private static void mirrorCurrentTarget(class_310 class_3102) {
        boolean bl = hand == class_1268.field_5810;
        boolean bl2 = !bl;
        switch (target.ordinal()) {
            case 0: {
                ToolTuning.Profile profile = ToolTuning.get(currentKind, bl);
                if (profile == null) {
                    TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.item_profile_missing"));
                    return;
                }
                ToolTuning.apply(currentKind, bl2, profile.mirror());
                ToolTuning.markKindDetached(currentKind, bl2);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.mirror_hand", (Object[])new Object[]{bl2 ? class_2561.method_43471((String)"punchy.tuning.hand.off") : class_2561.method_43471((String)"punchy.tuning.hand.main")}));
                break;
            }
            case 1: {
                if (currentItemId == null) {
                    TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.hold_item_mirror"));
                    return;
                }
                ToolTuning.Profile profile = ToolTuning.getSpecificProfile(currentItemId, currentKind, bl);
                if (profile == null) {
                    TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.specific_profile_missing_mirror"));
                    return;
                }
                ToolTuning.Profile profile2 = profile.mirror();
                ToolTuning.Profile profile3 = bl ? profile2 : ToolTuning.getSpecificProfile(currentItemId, currentKind, false);
                ToolTuning.Profile profile4 = bl ? ToolTuning.getSpecificProfile(currentItemId, currentKind, true) : profile2;
                ToolTuning.applySpecific(currentItemId, currentKind, null, profile3, profile4);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.mirror_hand_specific", (Object[])new Object[]{bl2 ? class_2561.method_43471((String)"punchy.tuning.hand.off") : class_2561.method_43471((String)"punchy.tuning.hand.main")}));
                break;
            }
            case 2: {
                ToolTuning.Profile profile = ToolTuning.getGlobal(bl);
                if (profile == null) {
                    TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.global_profile_missing"));
                    return;
                }
                ToolTuning.applyGlobal(bl2, profile.mirror());
                ToolTuning.commitGlobal(bl2);
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43469((String)"punchy.tuning.toast.global_mirror_hand", (Object[])new Object[]{bl2 ? class_2561.method_43471((String)"punchy.tuning.hand.off") : class_2561.method_43471((String)"punchy.tuning.hand.main")}));
                break;
            }
            default: {
                TuningKeybinds.toast(class_3102, (class_2561)class_2561.method_43471((String)"punchy.tuning.toast.mirror_item_only"));
            }
        }
    }

    private static void resetToolProfile(ToolTuning.Profile profile) {
        profile.offZ = 0.0f;
        profile.offY = 0.0f;
        profile.offX = 0.0f;
        profile.rotZ = 0.0f;
        profile.rotY = 0.0f;
        profile.rotX = 0.0f;
        profile.pivotZ = 0.0f;
        profile.pivotY = 0.0f;
        profile.pivotX = 0.0f;
        profile.recomputeRadians();
    }

    private static void resetArmProfile(ArmToolTuning.Profile profile, PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        ArmToolTuning.Profile profile2 = toolKind == null ? PunchyTuningConfig.getDefaultGlobalArmBase(bl) : (toolKind == PlayerArmModelLayer.ToolKind.EMPTY_HAND ? PunchyTuningConfig.getDefaultArmEditorBase(toolKind, bl) : new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        profile.offX = profile2.offX;
        profile.offY = profile2.offY;
        profile.offZ = profile2.offZ;
        profile.rotX = profile2.rotX;
        profile.rotY = profile2.rotY;
        profile.rotZ = profile2.rotZ;
        profile.scale = profile2.scale;
    }

    private static void resetArmMeshProfile(ArmMeshTuning.Profile profile) {
        profile.offZ = 0.0f;
        profile.offY = 0.0f;
        profile.offX = 0.0f;
        profile.rotZ = 0.0f;
        profile.rotY = 0.0f;
        profile.rotX = 0.0f;
    }

    private static AdjustmentResult adjustToolProfile(ToolTuning.Profile profile, float f) {
        return switch (mode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("offX", profile.offX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("offY", profile.offY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("offZ", profile.offZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 1 -> {
                ToolTuning.RotationOrder rotationOrder = ToolTuning.rotationOrderForItemId(currentItemId);
                char rotationAxis = switch (axis.ordinal()) {
                    default -> throw new IllegalStateException("Unexpected enum value");
                    case 0 -> 'X';
                    case 1 -> 'Y';
                    case 2 -> 'Z';
                };
                ToolTuning.rotateProfileLocal(profile, rotationOrder, rotationAxis, f);
                yield switch (axis.ordinal()) {
                    default -> throw new IllegalStateException("Unexpected enum value");
                    case 0 -> new AdjustmentResult("rotX", profile.rotX);
                    case 1 -> new AdjustmentResult("rotY", profile.rotY);
                    case 2 -> new AdjustmentResult("rotZ", profile.rotZ);
                };
            }
            case 2 -> {
                profile.scale = Math.max(0.01f, profile.scale + f);
                AdjustmentResult v3 = new AdjustmentResult("scale", profile.scale);
                yield v3;
            }
            case 3 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("pivotX", profile.pivotX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("pivotY", profile.pivotY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("pivotZ", profile.pivotZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
        };
    }

    private static AdjustmentResult adjustArmProfile(ArmToolTuning.Profile profile, float f) {
        return switch (mode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("offX", profile.offX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("offY", profile.offY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("offZ", profile.offZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 1 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("rotX", profile.rotX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("rotY", profile.rotY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("rotZ", profile.rotZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 2 -> {
                profile.scale = Math.max(0.25f, profile.scale + f);
                AdjustmentResult v1 = new AdjustmentResult("scale", profile.scale);
                yield v1;
            }
            case 3 -> throw new IllegalStateException("Pivot editing should be gated for arms");
        };
    }

    private static AdjustmentResult adjustArmMeshProfile(ArmMeshTuning.Profile profile, float f) {
        return switch (mode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("offX", profile.offX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("offY", profile.offY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("offZ", profile.offZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 1 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("rotX", profile.rotX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("rotY", profile.rotY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("rotZ", profile.rotZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 2 -> {
                profile.scale = Math.max(0.25f, profile.scale + f);
                AdjustmentResult v1 = new AdjustmentResult("scale", profile.scale);
                yield v1;
            }
            case 3 -> throw new IllegalStateException("Pivot editing should be gated for arms");
        };
    }

    private static AdjustmentResult adjustChestProxyProfile(ChestProxyTuning.Profile profile, float f) {
        return switch (mode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("offX", profile.offX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("offY", profile.offY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("offZ", profile.offZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 1 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("rotX", profile.rotX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("rotY", profile.rotY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("rotZ", profile.rotZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 2 -> {
                profile.scale = Math.max(0.01f, profile.scale + f);
                AdjustmentResult v1 = new AdjustmentResult("scale", profile.scale);
                yield v1;
            }
            case 3 -> throw new IllegalStateException("Pivot editing should be gated for chest proxy");
        };
    }

    private static AdjustmentResult adjustFluidProxyProfile(FluidProxyTuning.Profile profile, float f) {
        return switch (mode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("offX", profile.offX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("offY", profile.offY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("offZ", profile.offZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 1 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("rotX", profile.rotX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("rotY", profile.rotY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("rotZ", profile.rotZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 2 -> {
                profile.scale = Math.max(0.01f, profile.scale + f);
                AdjustmentResult v1 = new AdjustmentResult("scale", profile.scale);
                yield v1;
            }
            case 3 -> throw new IllegalStateException("Pivot editing should be gated for fluid proxy");
        };
    }

    private static AdjustmentResult adjustEnchantParticleProxyProfile(EnchantParticleProxyTuning.Profile profile, float f) {
        return switch (mode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("offX", profile.offX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("offY", profile.offY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("offZ", profile.offZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 1 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("rotX", profile.rotX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("rotY", profile.rotY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("rotZ", profile.rotZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 2 -> {
                profile.scale = Math.max(0.01f, profile.scale + f);
                AdjustmentResult v1 = new AdjustmentResult("scale", profile.scale);
                yield v1;
            }
            case 3 -> throw new IllegalStateException("Pivot editing should be gated for particle proxy");
        };
    }

    private static AdjustmentResult adjustPaddleProxyProfile(PaddleProxyTuning.Profile profile, float f) {
        return switch (mode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("offX", profile.offX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("offY", profile.offY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("offZ", profile.offZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 1 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("rotX", profile.rotX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("rotY", profile.rotY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("rotZ", profile.rotZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 2 -> {
                profile.scale = Math.max(0.01f, profile.scale + f);
                AdjustmentResult v1 = new AdjustmentResult("scale", profile.scale);
                yield v1;
            }
            case 3 -> throw new IllegalStateException("Pivot editing should be gated for paddle proxy");
        };
    }

    private static AdjustmentResult adjustFishingLineProxyProfile(FishingLineProxyTuning.Profile profile, float f) {
        return switch (mode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("offX", profile.offX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("offY", profile.offY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("offZ", profile.offZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 1 -> {
                switch (axis.ordinal()) {
                    case 0: {
                        yield new AdjustmentResult("rotX", profile.rotX += f);
                    }
                    case 1: {
                        yield new AdjustmentResult("rotY", profile.rotY += f);
                    }
                    case 2: {
                        yield new AdjustmentResult("rotZ", profile.rotZ += f);
                    }
                }
                throw new IllegalStateException("Unexpected enum value");
            }
            case 2 -> {
                profile.scale = Math.max(0.01f, profile.scale + f);
                AdjustmentResult v1 = new AdjustmentResult("scale", profile.scale);
                yield v1;
            }
            case 3 -> throw new IllegalStateException("Pivot editing should be gated for fishing line proxy");
        };
    }

    private static float computeStep(class_310 class_3102) {
        float f = switch (mode) {
            case ROTATE -> 1.0f;
            case TRANSLATE, SCALE, PIVOT -> 0.01f;
        };
        if (class_3102.field_1690 == null) {
            return f;
        }
        if (class_3102.field_1690.field_1867.method_1434()) {
            f *= 5.0f;
        }
        if (class_3102.field_1690.field_1832.method_1434()) {
            f *= 0.2f;
        }
        return f;
    }

    private static void report(class_310 class_3102, AdjustmentResult adjustmentResult) {
        if (adjustmentResult != null) {
            TuningKeybinds.toast(class_3102, TuningKeybinds.describeState(adjustmentResult));
        }
    }

    private static class_2561 describeState() {
        return TuningKeybinds.describeState(null);
    }

    private static class_2561 describeState(AdjustmentResult adjustmentResult) {
        class_2561 class_25612 = TuningKeybinds.targetLabel(target);
        class_2561 class_25613 = TuningKeybinds.selectionLabel();
        class_2561 class_25614 = TuningKeybinds.handLabel(hand);
        class_2561 class_25615 = TuningKeybinds.modeLabel(mode);
        class_2561 class_25616 = TuningKeybinds.axisLabel(axis);
        if (adjustmentResult != null) {
            class_2561 class_25617 = TuningKeybinds.propertyLabel(adjustmentResult.property);
            String string = String.format(Locale.ROOT, "%.4f", Float.valueOf(adjustmentResult.value));
            return TuningKeybinds.mode.usesAxis ? class_2561.method_43469((String)"punchy.tuning.status.with_axis_change", (Object[])new Object[]{class_25612, class_25613, class_25614, class_25615, class_25616, class_25617, string}) : class_2561.method_43469((String)"punchy.tuning.status.no_axis_change", (Object[])new Object[]{class_25612, class_25613, class_25614, class_25615, class_25617, string});
        }
        return TuningKeybinds.mode.usesAxis ? class_2561.method_43469((String)"punchy.tuning.status.with_axis", (Object[])new Object[]{class_25612, class_25613, class_25614, class_25615, class_25616}) : class_2561.method_43469((String)"punchy.tuning.status.no_axis", (Object[])new Object[]{class_25612, class_25613, class_25614, class_25615});
    }

    private static void toast(class_310 class_3102, class_2561 class_25612) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_3102.field_1705.method_1758(class_25612, false);
        }
    }

    private static class_2561 targetLabel(Target target) {
        return switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> class_2561.method_43471((String)"punchy.tuning.target.item_kind");
            case 1 -> class_2561.method_43471((String)"punchy.tuning.target.item_specific");
            case 2 -> class_2561.method_43471((String)"punchy.tuning.target.item_global");
            case 3 -> class_2561.method_43471((String)"punchy.tuning.target.arm_kind");
            case 4 -> class_2561.method_43471((String)"punchy.tuning.target.arm_specific");
            case 5 -> class_2561.method_43471((String)"punchy.tuning.target.arm_global");
            case 6 -> class_2561.method_43471((String)"punchy.tuning.target.arm_mesh");
            case 7 -> class_2561.method_43471((String)"punchy.tuning.target.chest_proxy");
            case 8 -> class_2561.method_43471((String)"punchy.tuning.target.fluid_proxy");
            case 9 -> class_2561.method_43471((String)"punchy.tuning.target.enchant_proxy");
            case 10 -> class_2561.method_43471((String)"punchy.tuning.target.paddle_proxy");
            case 11 -> class_2561.method_43471((String)"punchy.tuning.target.fishing_line_proxy");
        };
    }

    private static class_2561 selectionLabel() {
        return switch (target.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0, 3 -> class_2561.method_43470((String)TuningKeybinds.friendlyName(currentKind.name()));
            case 1, 4 -> {
                if (currentItemId != null) {
                    yield class_2561.method_43470((String)currentItemId.toString());
                }
                yield class_2561.method_43471((String)"punchy.tuning.selection.none_item");
            }
            case 2 -> class_2561.method_43471((String)"punchy.tuning.selection.global_item");
            case 5 -> class_2561.method_43471((String)"punchy.tuning.selection.global_arm");
            case 6 -> class_2561.method_43471((String)"punchy.tuning.selection.arm_mesh");
            case 7 -> class_2561.method_43469((String)"punchy.tuning.selection.chest_proxy", (Object[])new Object[]{chestProxyKind.name()});
            case 8 -> class_2561.method_43469((String)"punchy.tuning.selection.fluid_proxy", (Object[])new Object[]{fluidProxyKind.name()});
            case 9 -> class_2561.method_43469((String)"punchy.tuning.selection.enchant_proxy", (Object[])new Object[]{enchantParticleKind.name()});
            case 10 -> class_2561.method_43469((String)"punchy.tuning.selection.paddle_proxy", (Object[])new Object[]{paddleProxyKind.name()});
            case 11 -> class_2561.method_43469((String)"punchy.tuning.selection.fishing_line_proxy", (Object[])new Object[]{fishingLineProxyKind.name()});
        };
    }

    private static class_2561 handLabel(class_1268 class_12682) {
        return class_12682 == class_1268.field_5808 ? class_2561.method_43471((String)"punchy.tuning.hand.main") : class_2561.method_43471((String)"punchy.tuning.hand.off");
    }

    private static class_2561 modeLabel(Mode mode) {
        return switch (mode.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> class_2561.method_43471((String)"punchy.tuning.mode.translate");
            case 1 -> class_2561.method_43471((String)"punchy.tuning.mode.rotate");
            case 2 -> class_2561.method_43471((String)"punchy.tuning.mode.scale");
            case 3 -> class_2561.method_43471((String)"punchy.tuning.mode.pivot");
        };
    }

    private static class_2561 axisLabel(Axis axis) {
        return switch (axis.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> class_2561.method_43471((String)"punchy.tuning.axis.x");
            case 1 -> class_2561.method_43471((String)"punchy.tuning.axis.y");
            case 2 -> class_2561.method_43471((String)"punchy.tuning.axis.z");
        };
    }

    private static class_2561 propertyLabel(String string) {
        return switch (string) {
            case "offX" -> class_2561.method_43471((String)"punchy.tuning.property.off_x");
            case "offY" -> class_2561.method_43471((String)"punchy.tuning.property.off_y");
            case "offZ" -> class_2561.method_43471((String)"punchy.tuning.property.off_z");
            case "rotX" -> class_2561.method_43471((String)"punchy.tuning.property.rot_x");
            case "rotY" -> class_2561.method_43471((String)"punchy.tuning.property.rot_y");
            case "rotZ" -> class_2561.method_43471((String)"punchy.tuning.property.rot_z");
            case "scale" -> class_2561.method_43471((String)"punchy.tuning.property.scale");
            case "pivotX" -> class_2561.method_43471((String)"punchy.tuning.property.pivot_x");
            case "pivotY" -> class_2561.method_43471((String)"punchy.tuning.property.pivot_y");
            case "pivotZ" -> class_2561.method_43471((String)"punchy.tuning.property.pivot_z");
            default -> class_2561.method_43470((String)string);
        };
    }

    private static String friendlyName(String string) {
        String string2 = string.toLowerCase(Locale.ROOT).replace('_', ' ');
        return Character.toUpperCase(string2.charAt(0)) + string2.substring(1);
    }

    static {
        registered = false;
        target = Target.ITEM_KIND;
        axis = Axis.X;
        mode = Mode.TRANSLATE;
        hand = class_1268.field_5808;
        currentKind = ITEM_KINDS[0];
        currentItemId = null;
        chestProxyKind = ChestProxyTuning.Kind.BOAT;
        fluidProxyKind = FluidProxyTuning.Kind.WATER;
        enchantParticleKind = EnchantParticleProxyTuning.Kind.BOOK;
        paddleProxyKind = PaddleProxyTuning.Kind.BOAT;
        fishingLineProxyKind = FishingLineProxyTuning.Kind.ROD;
    }

    static enum Mode {
        TRANSLATE("offsets", true),
        ROTATE("rotation", true),
        SCALE("scale", false),
        PIVOT("pivot", true);

        final String label;
        final boolean usesAxis;

        private Mode(String string2, boolean bl) {
            this.label = string2;
            this.usesAxis = bl;
        }
    }

    static enum Axis {
        X,
        Y,
        Z;

    }

    static enum Target {
        ITEM_KIND,
        ITEM_SPECIFIC,
        ITEM_GLOBAL,
        ARM_KIND,
        ARM_SPECIFIC,
        ARM_GLOBAL,
        ARM_MESH,
        CHEST_PROXY,
        FLUID_PROXY,
        ENCHANT_PARTICLE_PROXY,
        PADDLE_PROXY,
        FISHING_LINE_PROXY;

    }

    static final class AdjustmentResult {
        final String property;
        final float value;

        AdjustmentResult(String string, float f) {
            this.property = string;
            this.value = f;
        }

        @Override
        public final String toString() {
            return "AdjustmentResult[" + "property=" + this.property + ", " + "value=" + this.value + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.property, this.value);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof AdjustmentResult other)) return false;
            return java.util.Objects.deepEquals(this.property, other.property)
                && java.util.Objects.deepEquals(this.value, other.value);
        }

        public String property() {
            return this.property;
        }

        public float value() {
            return this.value;
        }
    }
}
