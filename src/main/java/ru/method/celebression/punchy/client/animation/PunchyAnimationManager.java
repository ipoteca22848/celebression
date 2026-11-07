package ru.method.celebression.punchy.client.animation;

import ru.method.celebression.animation.Animation;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3300;
import net.minecraft.class_3414;
import net.minecraft.class_3532;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import ru.method.celebression.punchy.client.PunchyClient;
import ru.method.celebression.punchy.client.animation.PoseHandler;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.animation.io.PunchyAnimationLoader;
import ru.method.celebression.punchy.client.render.UseAnimationSuppressor;
import ru.method.celebression.punchy.client.state.ConsumeStateMachine;
import ru.method.celebression.punchy.client.state.HandEquipStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public class PunchyAnimationManager {
    private static final String EFFECT_DUAL_HANDED = "dual_handed";
    private static final String EFFECT_DUAL_HANDED_IF_HAS_OFF_ITEM = "dual_handed_if_has_off_item";
    private static final String EFFECT_START_WITH_OFFHAND = "start_here_if_off_hand_item";
    private static final String EFFECT_START_WITH_OFFHAND_ALIAS = "start_if_offhand_item";
    private static final String EFFECT_STOP_WITH_OFFHAND = "stop_if_offhand_item";
    public static final PoseHandler POSE_HANDLER;
    public static final PoseHandler SWIM_HANDLER;
    public static AnimationClip PUNCH_CLIP;
    public static AnimationClip INTERACT_CLIP;
    public static AnimationClip USE_BOW_RIGHT_CLIP;
    public static AnimationClip USE_BOW_LEFT_CLIP;
    public static AnimationClip ATTACK_CLIP;
    public static AnimationClip ATTACK_SPEAR_CLIP;
    public static AnimationClip ATTACK_TRIDENT_CLIP;
    public static AnimationClip ATTACK_MACE_CLIP;
    public static AnimationClip USE_SPEAR_POSE_1;
    public static AnimationClip USE_SPEAR_POSE_2;
    public static AnimationClip USE_SPEAR_POSE_3_RIGHT;
    public static AnimationClip USE_SPEAR_POSE_3_LEFT;
    public static AnimationClip USE_SHIELD_RIGHT_CLIP;
    public static AnimationClip USE_SHIELD_LEFT_CLIP;
    public static AnimationClip USE_CROSSBOW_RIGHT_CLIP;
    public static AnimationClip USE_CROSSBOW_LEFT_CLIP;
    public static AnimationClip USE_CROSSBOW_SHOT_CLIP;
    public static AnimationClip USE_TRIDENT_CLIP;
    public static AnimationClip USE_TRIDENT_THROW_CLIP;
    public static AnimationClip USE_EAT_CLIP;
    public static AnimationClip USE_DRINK_CLIP;
    public static AnimationClip SWIMING_CLIP;
    public static AnimationClip SWIMING_ABOVE_CLIP;
    public static AnimationClip ROWING_CLIP;
    public static AnimationClip ROWING_BACK_CLIP;
    public static AnimationClip FALLING_IN_WATER_CLIP;
    public static AnimationClip USE_TRIDENT_THROW_LOYALTY_CLIP;
    public static AnimationClip USE_TRIDENT_THROW_RIPTIDE_CLIP;
    public static AnimationClip MINING_FORWARD_CLIP;
    public static AnimationClip MINING_SIDEWAYS_CLIP;
    public static AnimationClip MINING_DIGGING_CLIP;
    public static AnimationClip MINING_USE_FORWARD_CLIP;
    public static AnimationClip MINING_USE_DIGGING_CLIP;
    public static AnimationClip PUT_OUT_FIRE_CLIP;
    public static AnimationClip HAND_IN_CLIP;
    public static AnimationClip HAND_OUT_CLIP;
    public static AnimationClip WAKE_UP_CLIP;
    public static float ANIMATION_SPEED;
    private static final EnumMap<class_1306, ItemOverrideState> ITEM_OVERRIDES;
    private static final EnumMap<class_1306, EquipOverrideState> EQUIP_OVERRIDES;
    private static class_1306 activeSourceArm;
    private static AnimationClip lastSoundClip;
    private static float lastSoundTime;
    public static boolean SOUND_ENABLED;
    private static boolean suppressOffhandForAttack;
    private static AnimationClip suppressOffhandClip;
    private static final Map<String, AnimationClip> ALL_CLIPS;
    private static final Map<String, LinkedHashMap<String, AnimationClip>> ALL_CLIP_VARIANTS;
    private static final Map<String, List<String>> TYPE_CLIP_ALIASES;
    private static final Map<String, ClipResolution> CLIP_RESOLUTION_CACHE;
    private static final String LEFT_SWITCH_PREFIX;
    private static final String RIGHT_SWITCH_PREFIX;
    private static final String HIDE_LEFT_SWITCH_PREFIX;
    private static final String HIDE_RIGHT_SWITCH_PREFIX;
    private static final String SHOW_LEFT_SWITCH;
    private static final String SHOW_RIGHT_SWITCH;
    private static final String SHOW_LEFT_SWITCH_PREFIX;
    private static final String SHOW_RIGHT_SWITCH_PREFIX;
    private static final String HIDE_ITEM_EFFECT;
    private static final long MIX_RELOAD_DEBOUNCE_NANOS = 150000000L;
    private static boolean MIX_RELOAD_PENDING;
    private static long MIX_RELOAD_DEADLINE_NANOS;

    private static Map<String, List<String>> buildTypeClipAliases() {
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("attack"), List.of("attack"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("mining"), List.of("mining", "mining_foward", "mining_forward", "mining_sideways", "mining_digging"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("mining_v2"), List.of("mining", "mining_foward", "mining_forward", "mining_sideways", "mining_digging"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("useItem"), List.of("use_item", "useitem", "interaction", "interact", "mining_use_foward", "mining_use_forward", "mining_use_digging"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("interaction"), List.of("interaction", "interact", "use_item", "useitem"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("spear_charge"), List.of("use_spear_pose_1", "use_spear_pose_1_left", "use_spear_pose_1_right", "use_spear_pose_2", "use_spear_pose_2_left", "use_spear_pose_2_right", "use_spear_pose_3", "use_spear_pose_3_left", "use_spear_pose_3_right"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("inspect"), List.of("inspect"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("hand_in"), List.of("hand_in", "handin"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("hand_out"), List.of("hand_out", "handout"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("falling"), List.of("falling"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("triggerable"), List.of("triggerable"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("use_shield"), List.of("use_shield"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("use_bow"), List.of("use_bow"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("use_crossbow"), List.of("use_crossbow"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("use_crossbow_shot"), List.of("use_crossbow_shot"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("useEat"), List.of("use_eat", "useeat"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("useDrink"), List.of("use_drink", "usedrink"));
        hashMap.put(PunchyAnimationManager.normalizeTypeKey("idle"), List.of("idle"));
        return hashMap;
    }

    public static void loadAnimations(class_3300 class_33002) {
        ALL_CLIPS.clear();
        ALL_CLIP_VARIANTS.clear();
        CLIP_RESOLUTION_CACHE.clear();
        SOUND_ENABLED = false;
        lastSoundClip = null;
        lastSoundTime = 0.0f;
        POSE_HANDLER.stopImmediateWithBlendOut();
        SWIM_HANDLER.stopImmediateWithBlendOut();
        Map<String, LinkedHashMap<String, AnimationClip>> map = PunchyAnimationLoader.loadAllWithPackVariants(class_33002);
        for (Map.Entry<String, LinkedHashMap<String, AnimationClip>> entry : map.entrySet()) {
            if (entry == null || entry.getKey() == null || entry.getValue() == null || entry.getValue().isEmpty()) continue;
            LinkedHashMap<String, AnimationClip> linkedHashMap = new LinkedHashMap<String, AnimationClip>();
            for (Map.Entry<String, AnimationClip> entry2 : entry.getValue().entrySet()) {
                if (entry2 == null || entry2.getKey() == null || entry2.getValue() == null) continue;
                if (!entry2.getValue().getSoundEffects().isEmpty()) {
                    SOUND_ENABLED = true;
                }
                linkedHashMap.put(entry2.getKey(), AnimationClip.copyOf(entry2.getValue()));
            }
            if (linkedHashMap.isEmpty()) continue;
            ALL_CLIP_VARIANTS.put(entry.getKey(), linkedHashMap);
            Object object = (AnimationClip)linkedHashMap.get("punchy_default");
            if (object == null) {
                object = (AnimationClip)linkedHashMap.values().iterator().next();
            }
            if (object == null) continue;
            ALL_CLIPS.put(entry.getKey(), AnimationClip.copyOf((AnimationClip)object));
        }
        PunchyAnimationManager.assignKnownClips();
    }

    public static List<String> getMixPackAnimationNames() {
        return PunchyTuningConfig.getMixPackSlotIds();
    }

    public static String getMixPackEntryLabel(String string) {
        return PunchyTuningConfig.getMixPackSlotLabel(string);
    }

    public static List<PackOption> getMixPackOptions(String string) {
        List<String> list = PunchyTuningConfig.getMixPackSlotOptionIds(string);
        if (list != null && !list.isEmpty()) {
            ArrayList<PackOption> arrayList = new ArrayList<PackOption>();
            for (String string2 : list) {
                if ("punchy_default".equals(string2)) continue;
                arrayList.add(new PackOption(string2, PunchyAnimationManager.packOptionLabel(string2)));
            }
            return arrayList;
        }
        return List.of();
    }

    public static String getSelectedMixPackOption(String string) {
        return PunchyTuningConfig.getMixPackSelectedOption(string);
    }

    public static boolean selectMixPackOption(String string, String string2) {
        boolean bl = PunchyTuningConfig.setMixPackSelection(string, string2);
        if (!bl) {
            return false;
        }
        PunchyAnimationManager.scheduleMixReload();
        return true;
    }

    public static List<String> getMixPackEntryTypes(String string) {
        return PunchyTuningConfig.getMixPackSlotEntryTypes(string);
    }

    public static List<String> getMixPackExpandableEntryTypes(String string) {
        return PunchyTuningConfig.getMixPackExpandableEntryTypes(string);
    }

    public static List<PackOption> getMixPackTypeOptions(String string, String string2) {
        List<String> list = PunchyTuningConfig.getMixPackSlotEntryTypeOptionIds(string, string2);
        if (list == null) {
            return List.of();
        }
        ArrayList<String> arrayList = new ArrayList<String>(list);
        String string3 = PunchyAnimationManager.getSelectedMixPackOption(string);
        if (string3 != null && !string3.isBlank() && !arrayList.contains(string3)) {
            arrayList.add(0, string3);
        }
        if (arrayList.isEmpty()) {
            return List.of();
        }
        ArrayList<PackOption> arrayList2 = new ArrayList<PackOption>();
        for (String string4 : arrayList) {
            if ("punchy_default".equals(string4)) continue;
            arrayList2.add(new PackOption(string4, PunchyAnimationManager.packOptionLabel(string4)));
        }
        return arrayList2;
    }

    public static String getSelectedMixPackTypeOption(String string, String string2) {
        return PunchyTuningConfig.getMixPackSelectedEntryTypeOption(string, string2);
    }

    public static boolean hasExplicitMixPackTypeOption(String string, String string2) {
        return PunchyTuningConfig.hasMixPackEntryTypeOverride(string, string2);
    }

    public static boolean selectMixPackTypeOption(String string, String string2, String string3) {
        boolean bl = PunchyTuningConfig.setMixPackEntryTypeSelection(string, string2, string3);
        if (!bl) {
            return false;
        }
        PunchyAnimationManager.scheduleMixReload();
        return true;
    }

    public static void resetMixPacksToResourcePackOrder() {
        PunchyTuningConfig.resetMixPackSelectionsToResourcepackOrder();
        PunchyAnimationManager.scheduleMixReload();
    }

    public static void flushPendingMixReload(class_310 class_3102) {
        long l;
        if (MIX_RELOAD_PENDING && (l = System.nanoTime()) >= MIX_RELOAD_DEADLINE_NANOS) {
            PunchyAnimationManager.applyMixReloadNow(class_3102);
        }
    }

    public static void requestTuningReload() {
        PunchyAnimationManager.scheduleMixReload();
    }

    private static void scheduleMixReload() {
        MIX_RELOAD_PENDING = true;
        MIX_RELOAD_DEADLINE_NANOS = System.nanoTime() + 150000000L;
    }

    private static void applyMixReloadNow(class_310 class_3102) {
        MIX_RELOAD_PENDING = false;
        MIX_RELOAD_DEADLINE_NANOS = 0L;
        if (class_3102 != null && class_3102.method_1478() != null) {
            PunchyTuningConfig.reloadActiveProfileFast(class_3102.method_1478());
        } else {
            PunchyTuningConfig.reloadActiveProfileFast(null);
        }
        PunchyAnimationManager.rebuildActiveClipsFromSelections();
    }

    private static void rebuildActiveClipsFromSelections() {
        CLIP_RESOLUTION_CACHE.clear();
        PunchyAnimationManager.assignKnownClips();
    }

    private static void assignKnownClips() {
        PUNCH_CLIP = ALL_CLIPS.get("punch");
        INTERACT_CLIP = ALL_CLIPS.get("interact");
        USE_BOW_RIGHT_CLIP = ALL_CLIPS.get("use_bow_right");
        USE_BOW_LEFT_CLIP = ALL_CLIPS.get("use_bow_left");
        ATTACK_CLIP = ALL_CLIPS.get("attack");
        ATTACK_SPEAR_CLIP = ALL_CLIPS.get("attack_spear");
        ATTACK_TRIDENT_CLIP = ALL_CLIPS.get("attack_trident");
        ATTACK_MACE_CLIP = ALL_CLIPS.get("attack_mace");
        USE_SPEAR_POSE_1 = ALL_CLIPS.get("use_spear_pose_1");
        USE_SPEAR_POSE_2 = ALL_CLIPS.get("use_spear_pose_2");
        USE_SPEAR_POSE_3_RIGHT = ALL_CLIPS.get("use_spear_pose_3_right");
        USE_SPEAR_POSE_3_LEFT = ALL_CLIPS.get("use_spear_pose_3_left");
        USE_SHIELD_RIGHT_CLIP = ALL_CLIPS.get("use_shield_right");
        USE_SHIELD_LEFT_CLIP = ALL_CLIPS.get("use_shield_left");
        USE_CROSSBOW_RIGHT_CLIP = ALL_CLIPS.get("use_crossbow_right");
        USE_CROSSBOW_LEFT_CLIP = ALL_CLIPS.get("use_crossbow_left");
        USE_CROSSBOW_SHOT_CLIP = ALL_CLIPS.get("use_crossbow_shot");
        USE_TRIDENT_CLIP = ALL_CLIPS.get("use_trident");
        USE_TRIDENT_THROW_CLIP = ALL_CLIPS.get("use_trident_throw");
        USE_EAT_CLIP = ALL_CLIPS.get("use_eat");
        USE_DRINK_CLIP = ALL_CLIPS.get("use_drink");
        SWIMING_CLIP = ALL_CLIPS.get("swiming");
        SWIMING_ABOVE_CLIP = ALL_CLIPS.get("swiming_above");
        ROWING_CLIP = ALL_CLIPS.get("rowing");
        ROWING_BACK_CLIP = ALL_CLIPS.get("rowing_back");
        FALLING_IN_WATER_CLIP = ALL_CLIPS.get("falling_in_water");
        USE_TRIDENT_THROW_LOYALTY_CLIP = ALL_CLIPS.get("use_trident_throw_loyalty");
        USE_TRIDENT_THROW_RIPTIDE_CLIP = ALL_CLIPS.get("use_trident_throw_riptide");
        MINING_FORWARD_CLIP = ALL_CLIPS.get("mining_foward");
        MINING_SIDEWAYS_CLIP = ALL_CLIPS.get("mining_sideways");
        MINING_DIGGING_CLIP = ALL_CLIPS.get("mining_digging");
        MINING_USE_FORWARD_CLIP = ALL_CLIPS.get("mining_use_foward");
        MINING_USE_DIGGING_CLIP = ALL_CLIPS.get("mining_use_digging");
        PUT_OUT_FIRE_CLIP = ALL_CLIPS.get("put_out_fire");
        HAND_IN_CLIP = ALL_CLIPS.get("hand_in");
        HAND_OUT_CLIP = ALL_CLIPS.get("hand_out");
        WAKE_UP_CLIP = ALL_CLIPS.get("wake_up");
    }

    private static String normalizeOptionId(String string) {
        return string != null && !string.isBlank() ? string.trim().toLowerCase(Locale.ROOT) : null;
    }

    private static String normalizeTypeKey(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "").replace(" ", "");
            return !"miningv2".equals(string2) && !"mining".equals(string2) ? string2 : "mining";
        }
        return "";
    }

    private static String packOptionLabel(String string) {
        if (string != null && !string.isBlank()) {
            if ("pack:punchy:punchy".equals(string)) {
                return "\u00a7bPunchy! \u00a7eDefault";
            }
            if ("punchy_default".equals(string)) {
                return "\u00a77Default";
            }
            String string2 = string;
            if (string.startsWith("pack:")) {
                string2 = string.substring("pack:".length());
            }
            if (string2.startsWith("file/")) {
                string2 = string2.substring("file/".length());
            }
            if (string2.startsWith("file\\")) {
                string2 = string2.substring("file\\".length());
            }
            if (string2.endsWith(".zip")) {
                string2 = string2.substring(0, string2.length() - 4);
            }
            if (string2.endsWith(".jar")) {
                string2 = string2.substring(0, string2.length() - 4);
            }
            if (string2.isBlank()) {
                return "Unknown";
            }
            string2 = string2.replace('_', ' ').replace('-', ' ').replace('/', ' ').replace('\\', ' ');
            String[] stringArray = string2.trim().split("\\s+");
            StringBuilder stringBuilder = new StringBuilder();
            for (String string3 : stringArray) {
                if (string3 == null || string3.isBlank() || "file".equalsIgnoreCase(string3)) continue;
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append(' ');
                }
                String string4 = string3.toLowerCase(Locale.ROOT);
                stringBuilder.append(Character.toUpperCase(string4.charAt(0)));
                if (string4.length() <= 1) continue;
                stringBuilder.append(string4.substring(1));
            }
            return stringBuilder.isEmpty() ? "Unknown" : stringBuilder.toString();
        }
        return "Unknown";
    }

    private static AnimationClip resolveClipByOption(String string, String string2) {
        return PunchyAnimationManager.resolveClipByOptionWithSource(string, string2).clip();
    }

    public static AnimationClip resolveCustomClip(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim();
            return ALL_CLIPS.get(string2);
        }
        return null;
    }

    public static AnimationClip resolveHandedClip(String string, class_310 class_3102, class_1268 class_12682) {
        return PunchyAnimationManager.resolveHandedClip(string, class_3102, class_12682, null);
    }

    public static AnimationClip resolveHandedClip(String string, class_310 class_3102, class_1268 class_12682, String string2) {
        if (string != null && !string.isBlank()) {
            boolean bl;
            String string3 = string2;
            if (string2 == null || string2.isBlank()) {
                string3 = PunchyAnimationManager.inferPreferredOptionFromHand(class_3102, class_12682);
            }
            String string4 = string.trim();
            AnimationClip animationClip = PunchyAnimationManager.resolveClipByOption(string4, string3);
            AnimationClip animationClip2 = PunchyAnimationManager.resolveClipByOption(string4 + "_right", string3);
            AnimationClip animationClip3 = PunchyAnimationManager.resolveClipByOption(string4 + "_left", string3);
            boolean bl2 = bl = animationClip2 != null || animationClip3 != null;
            if (!bl) {
                return animationClip;
            }
            if (class_3102 != null && class_3102.field_1724 != null && class_12682 != null) {
                boolean bl3;
                boolean bl4 = bl3 = class_12682 == class_1268.field_5808 == (class_3102.field_1724.method_6068() == class_1306.field_6183);
                if (bl3) {
                    if (animationClip2 != null) {
                        return animationClip2;
                    }
                    return animationClip != null ? animationClip : animationClip3;
                }
                if (animationClip3 != null) {
                    return animationClip3;
                }
                return animationClip != null ? animationClip : animationClip2;
            }
            return animationClip != null ? animationClip : (animationClip2 != null ? animationClip2 : animationClip3);
        }
        return null;
    }

    public static AnimationClip resolveNamedClip(String string, class_310 class_3102, class_1268 class_12682) {
        return PunchyAnimationManager.resolveNamedClip(string, class_3102, class_12682, null);
    }

    public static AnimationClip resolveNamedClip(String string, class_310 class_3102, class_1268 class_12682, String string2) {
        if (string != null && !string.isBlank()) {
            String string3;
            String string4 = string2;
            if (string2 == null || string2.isBlank()) {
                string4 = PunchyAnimationManager.inferPreferredOptionFromHand(class_3102, class_12682);
            }
            return !(string3 = string.trim()).endsWith("_left") && !string3.endsWith("_right") ? PunchyAnimationManager.resolveHandedClip(string3, class_3102, class_12682, string4) : PunchyAnimationManager.resolveClipByOption(string3, string4);
        }
        return null;
    }

    public static ClipResolution resolveNamedClipWithSource(String string, class_310 class_3102, class_1268 class_12682, String string2) {
        if (string != null && !string.isBlank()) {
            String string3;
            String string4 = string2;
            if (string2 == null || string2.isBlank()) {
                string4 = PunchyAnimationManager.inferPreferredOptionFromHand(class_3102, class_12682);
            }
            return !(string3 = string.trim()).endsWith("_left") && !string3.endsWith("_right") ? PunchyAnimationManager.resolveHandedClipWithSource(string3, class_3102, class_12682, string4) : PunchyAnimationManager.resolveClipByOptionWithSource(string3, string4);
        }
        return new ClipResolution(null, null, "name_blank");
    }

    public static ClipResolution resolveHandedClipWithSource(String string, class_310 class_3102, class_1268 class_12682, String string2) {
        if (string != null && !string.isBlank()) {
            boolean bl;
            String string3 = string2;
            if (string2 == null || string2.isBlank()) {
                string3 = PunchyAnimationManager.inferPreferredOptionFromHand(class_3102, class_12682);
            }
            String string4 = string.trim();
            ClipResolution clipResolution = PunchyAnimationManager.resolveClipByOptionWithSource(string4, string3);
            ClipResolution clipResolution2 = PunchyAnimationManager.resolveClipByOptionWithSource(string4 + "_right", string3);
            ClipResolution clipResolution3 = PunchyAnimationManager.resolveClipByOptionWithSource(string4 + "_left", string3);
            boolean bl2 = bl = clipResolution2.clip() != null || clipResolution3.clip() != null;
            if (!bl) {
                return clipResolution;
            }
            if (class_3102 != null && class_3102.field_1724 != null && class_12682 != null) {
                boolean bl3;
                boolean bl4 = bl3 = class_12682 == class_1268.field_5808 == (class_3102.field_1724.method_6068() == class_1306.field_6183);
                if (bl3) {
                    if (clipResolution2.clip() != null) {
                        return clipResolution2;
                    }
                    if (clipResolution.clip() != null) {
                        return new ClipResolution(clipResolution.clip(), clipResolution.optionId(), "suffixed_main_fallback_base:" + clipResolution.note());
                    }
                    return clipResolution3.clip() != null ? new ClipResolution(clipResolution3.clip(), clipResolution3.optionId(), "suffixed_main_fallback_left:" + clipResolution3.note()) : new ClipResolution(null, null, "suffixed_main_missing");
                }
                if (clipResolution3.clip() != null) {
                    return clipResolution3;
                }
                if (clipResolution.clip() != null) {
                    return new ClipResolution(clipResolution.clip(), clipResolution.optionId(), "suffixed_off_fallback_base:" + clipResolution.note());
                }
                return clipResolution2.clip() != null ? new ClipResolution(clipResolution2.clip(), clipResolution2.optionId(), "suffixed_off_fallback_right:" + clipResolution2.note()) : new ClipResolution(null, null, "suffixed_off_missing");
            }
            if (clipResolution.clip() != null) {
                return new ClipResolution(clipResolution.clip(), clipResolution.optionId(), "suffixed_no_context_base:" + clipResolution.note());
            }
            if (clipResolution2.clip() != null) {
                return new ClipResolution(clipResolution2.clip(), clipResolution2.optionId(), "suffixed_no_context_right:" + clipResolution2.note());
            }
            return clipResolution3.clip() != null ? new ClipResolution(clipResolution3.clip(), clipResolution3.optionId(), "suffixed_no_context_left:" + clipResolution3.note()) : new ClipResolution(null, null, "suffixed_no_context_missing");
        }
        return new ClipResolution(null, null, "base_blank");
    }

    public static AnimationClip resolveTypeFallbackClip(String string, class_310 class_3102, class_1268 class_12682) {
        return PunchyAnimationManager.resolveTypeFallbackClip(string, class_3102, class_12682, null);
    }

    public static AnimationClip resolveTypeFallbackClip(String string, class_310 class_3102, class_1268 class_12682, String string2) {
        return PunchyAnimationManager.resolveTypeFallbackClipWithSource(string, class_3102, class_12682, string2).clip();
    }

    public static ClipResolution resolveTypeFallbackClipWithSource(String string, class_310 class_3102, class_1268 class_12682, String string2) {
        List<String> list = TYPE_CLIP_ALIASES.get(PunchyAnimationManager.normalizeTypeKey(string));
        if (list != null && !list.isEmpty()) {
            for (String string3 : list) {
                ClipResolution clipResolution;
                if (string3 == null || string3.isBlank() || (clipResolution = PunchyAnimationManager.resolveNamedClipWithSource(string3, class_3102, class_12682, string2)) == null || clipResolution.clip() == null) continue;
                return new ClipResolution(clipResolution.clip(), clipResolution.optionId(), "type_fallback:" + string3 + ":" + clipResolution.note());
            }
            return new ClipResolution(null, null, "type_missing");
        }
        return new ClipResolution(null, null, "type_unmapped");
    }

    public static boolean hasTypeFallbackClip(String string, String string2) {
        List<String> list = TYPE_CLIP_ALIASES.get(PunchyAnimationManager.normalizeTypeKey(string));
        if (list != null && !list.isEmpty()) {
            for (String string3 : list) {
                if (!PunchyAnimationManager.hasNamedOrHandedClip(string3, string2)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static ClipResolution resolveClipByOptionWithSource(String string, String string2) {
        if (string != null && !string.isBlank()) {
            ClipResolution clipResolution;
            String string3 = PunchyAnimationManager.normalizeOptionId(string2);
            String string4 = string + "|" + (string3 == null ? "__auto__" : string3);
            ClipResolution clipResolution2 = CLIP_RESOLUTION_CACHE.get(string4);
            if (clipResolution2 != null) {
                return clipResolution2;
            }
            LinkedHashMap<String, AnimationClip> linkedHashMap = ALL_CLIP_VARIANTS.get(string);
            if (string3 != null && !string3.isBlank() && !"punchy_default".equals(string3)) {
                AnimationClip preferredClip = linkedHashMap == null ? null : linkedHashMap.get(string3);
                AnimationClip defaultClip = ALL_CLIPS.get(string);
                clipResolution = preferredClip != null
                    ? new ClipResolution(preferredClip, string3, "preferred_option_variant")
                    : new ClipResolution(defaultClip, defaultClip == null ? null : "punchy_default", defaultClip == null ? "preferred_option_missing" : "preferred_option_fallback_default");
            } else {
                AnimationClip defaultClip = ALL_CLIPS.get(string);
                clipResolution = new ClipResolution(defaultClip, defaultClip == null ? null : "punchy_default", defaultClip == null ? "clip_missing" : "resource_order_default");
            }
            CLIP_RESOLUTION_CACHE.put(string4, clipResolution);
            return clipResolution;
        }
        return new ClipResolution(null, null, "clip_blank");
    }

    private static boolean hasNamedOrHandedClip(String string, String string2) {
        if (string == null || string.isBlank()) {
            return false;
        }
        return PunchyAnimationManager.resolveClipByOption(string, string2) != null ? true : PunchyAnimationManager.resolveClipByOption(string + "_left", string2) != null || PunchyAnimationManager.resolveClipByOption(string + "_right", string2) != null;
    }

    private static String inferPreferredOptionFromHand(class_310 class_3102, class_1268 class_12682) {
        if (class_3102 != null && class_3102.field_1724 != null && class_12682 != null) {
            class_1799 class_17992 = class_3102.field_1724.method_5998(class_12682);
            return PunchyTuningConfig.getMixPreferredOptionForEntry(class_17992, null);
        }
        return null;
    }

    public static void logMixSelectionTrace(String string, String string2, String string3, String string4, ClipResolution clipResolution) {
    }

    public static boolean isClipNamed(AnimationClip animationClip, String string) {
        if (animationClip != null && string != null && !string.isBlank()) {
            String string2 = animationClip.getName();
            String string3 = string.trim();
            return string3.equals(string2) || (string3 + "_left").equals(string2) || (string3 + "_right").equals(string2);
        }
        return false;
    }

    public static boolean isHandBlacklisted(class_1306 class_13062) {
        if (class_13062 == null) {
            return false;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1268 class_12682 = PunchyAnimationManager.getHandForArm(class_3102, class_13062);
            return PunchyConfig.isHandBlacklisted((class_1657)class_3102.field_1724, class_12682);
        }
        return false;
    }

    public static boolean isHandBlacklisted(class_310 class_3102, class_1268 class_12682) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            class_1268 class_12683 = class_12682 == null ? class_1268.field_5808 : class_12682;
            return PunchyConfig.isHandBlacklisted((class_1657)class_3102.field_1724, class_12683);
        }
        return false;
    }

    public static boolean isAnyHandBlacklisted() {
        return PunchyAnimationManager.isHandBlacklisted(class_1306.field_6183) || PunchyAnimationManager.isHandBlacklisted(class_1306.field_6182);
    }

    public static boolean shouldBlockClip(AnimationClip animationClip, class_1306 class_13062) {
        if (animationClip == null) {
            return false;
        }
        if (class_13062 != null && PunchyAnimationManager.isHandBlacklisted(class_13062)) {
            return true;
        }
        if (PunchyAnimationManager.isDualHandedActive(animationClip, PunchyAnimationManager.resolveDualHandedEffectTime(animationClip)) && PunchyAnimationManager.isAnyHandBlacklisted()) {
            class_1306 class_13063 = PunchyAnimationManager.getSourceArm();
            return class_13063 == null ? true : PunchyAnimationManager.isHandBlacklisted(class_13063);
        }
        return false;
    }

    public static void enforceBlacklist(class_310 class_3102) {
        class_1306 class_13062;
        AnimationClip animationClip;
        if (class_3102 != null && class_3102.field_1724 != null && (animationClip = POSE_HANDLER.getCurrentClip()) != null && POSE_HANDLER.isPlaying() && PunchyAnimationManager.shouldBlockClip(animationClip, class_13062 = POSE_HANDLER.getActiveArm())) {
            POSE_HANDLER.setLooping(false);
            POSE_HANDLER.stopImmediateWithBlendOut();
        }
    }

    private static class_1799 getStackForArm(class_1657 class_16572, class_1306 class_13062) {
        if (class_16572 != null && class_13062 != null) {
            boolean bl;
            boolean bl2 = bl = class_16572.method_6068() == class_1306.field_6183;
            return class_13062 == class_1306.field_6183 ? (bl ? class_16572.method_6047() : class_16572.method_6079()) : (bl ? class_16572.method_6079() : class_16572.method_6047());
        }
        return class_1799.field_8037;
    }

    public static void setAnimationSpeed(float f) {
        ANIMATION_SPEED = class_3532.method_15363((float)f, (float)0.5f, (float)7.0f);
    }

    public static boolean isDualHandedActive() {
        AnimationClip animationClip = POSE_HANDLER.getCurrentClip();
        if (animationClip == null) {
            return false;
        }
        float f = POSE_HANDLER.getCurrentTime();
        return PunchyAnimationManager.isDualHandedActive(animationClip, f);
    }

    public static boolean hasAnyDualHandedEffect(AnimationClip animationClip) {
        return animationClip != null && (animationClip.hasEffectAny("dual_handed") || animationClip.hasEffectAny("dual_handed_if_has_off_item"));
    }

    public static boolean isDualHandedActive(AnimationClip animationClip, float f) {
        return PunchyAnimationManager.isDualHandedActive(class_310.method_1551(), animationClip, f);
    }

    public static boolean isDualHandedActive(class_310 class_3102, AnimationClip animationClip, float f) {
        if (animationClip == null) {
            return false;
        }
        return animationClip.isEffectActive("dual_handed", f) ? PunchyAnimationManager.isConditionalOffhandWindowActive(class_3102, animationClip, f) : PunchyAnimationManager.hasDualHandedOffhandContext(class_3102) && animationClip.isEffectActive("dual_handed_if_has_off_item", f);
    }

    private static boolean isConditionalOffhandWindowActive(class_310 class_3102, AnimationClip animationClip, float f) {
        boolean bl;
        if (animationClip == null) {
            return false;
        }
        if (!PunchyAnimationManager.hasOffhandItem(class_3102)) {
            return true;
        }
        float f2 = PunchyAnimationManager.resolveLastOffhandStartTime(animationClip, f);
        float f3 = animationClip.getLastEffectTime("stop_if_offhand_item", f);
        boolean bl2 = !Float.isNaN(f2);
        boolean bl3 = bl = !Float.isNaN(f3);
        if (!bl2 && !bl) {
            return true;
        }
        return !bl2 ? !bl : !bl || f2 > f3;
    }

    private static float resolveLastOffhandStartTime(AnimationClip animationClip, float f) {
        if (animationClip == null) {
            return Float.NaN;
        }
        float f2 = animationClip.getLastEffectTime("start_here_if_off_hand_item", f);
        float f3 = animationClip.getLastEffectTime("start_if_offhand_item", f);
        if (Float.isNaN(f2)) {
            return f3;
        }
        return Float.isNaN(f3) ? f2 : Math.max(f2, f3);
    }

    public static boolean isSwimDualHandedActive() {
        if (!SWIM_HANDLER.isPlaying()) {
            return false;
        }
        if (SWIM_HANDLER.getActiveArm() != null) {
            return false;
        }
        AnimationClip animationClip = SWIM_HANDLER.getCurrentClip();
        return animationClip == null ? false : PunchyAnimationManager.isDualHandedActive(animationClip, SWIM_HANDLER.getCurrentTime());
    }

    public static boolean isOffhandHiddenIfEmptyActive() {
        return POSE_HANDLER.isOffhandHiddenIfEmptyActive() || SWIM_HANDLER.isOffhandHiddenIfEmptyActive();
    }

    public static boolean isOffhandHiddenIfEmptyActive(float f) {
        return POSE_HANDLER.isOffhandHiddenIfEmptyActive(f) || SWIM_HANDLER.isOffhandHiddenIfEmptyActive(f);
    }

    public static boolean shouldKeepBlacklistedOppositeHandForDualHanded(class_310 class_3102, class_1268 class_12682) {
        if (class_3102 == null || class_3102.field_1724 == null || class_12682 == null) {
            return false;
        }
        if (!PunchyConfig.isHandBlacklisted((class_1657)class_3102.field_1724, class_12682)) {
            return false;
        }
        if (!POSE_HANDLER.isPlaying()) {
            return false;
        }
        AnimationClip animationClip = POSE_HANDLER.getCurrentClip();
        if (!PunchyAnimationManager.isDualHandedActive(animationClip, POSE_HANDLER.getCurrentTime())) {
            return false;
        }
        class_1306 class_13062 = PunchyAnimationManager.getSourceArm();
        return class_13062 == null ? false : PunchyAnimationManager.getHandForArm(class_3102, class_13062) != class_12682;
    }

    public static void updateUseMeshSuppression(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            if (class_3102.field_1724.method_6115() && PunchyConfig.isHandBlacklisted((class_1657)class_3102.field_1724, class_3102.field_1724.method_6058())) {
                UseAnimationSuppressor.clear();
                UseAnimationSuppressor.force(false);
            } else {
                AnimationClip animationClip = POSE_HANDLER.getCurrentClip();
                if (animationClip == null) {
                    UseAnimationSuppressor.clear();
                } else {
                    class_1268 class_12682 = PunchyAnimationManager.resolveMeshSuppressorHand(class_3102);
                    if (class_12682 == null) {
                        UseAnimationSuppressor.clear();
                    } else {
                        class_1268 class_12683 = class_12682 == class_1268.field_5808 ? class_1268.field_5810 : class_1268.field_5808;
                        UseAnimationSuppressor.clear(class_12683);
                        if (!PunchyAnimationManager.isClipNamed(animationClip, "use_shield") && !PunchyAnimationManager.isClipNamed(animationClip, "use_trident")) {
                            boolean bl;
                            float f = POSE_HANDLER.getCurrentTime();
                            boolean bl2 = bl = animationClip.hasEffectAny("start_mesh_animation") || animationClip.hasEffectAny("loadstart") || animationClip.hasEffectAny("load_start") || animationClip.hasEffectAny("suppress_mesh_animation");
                            if (!bl) {
                                UseAnimationSuppressor.clear();
                            } else {
                                float f2;
                                float f3 = PunchyAnimationManager.getLastEffectTime(animationClip, "suppress_mesh_animation", f);
                                if (f3 > (f2 = Math.max(PunchyAnimationManager.getLastEffectTime(animationClip, "start_mesh_animation", f), Math.max(PunchyAnimationManager.getLastEffectTime(animationClip, "loadstart", f), PunchyAnimationManager.getLastEffectTime(animationClip, "load_start", f))))) {
                                    UseAnimationSuppressor.suppress(class_12682);
                                } else {
                                    boolean bl3;
                                    boolean bl4 = bl3 = f2 >= 0.0f;
                                    if (!bl3) {
                                        UseAnimationSuppressor.suppress(class_12682);
                                    } else {
                                        class_1799 class_17992;
                                        int n;
                                        int n2 = n = class_3102.field_1724.method_6115() ? class_3102.field_1724.method_6014() : 0;
                                        if (class_3102.field_1724.method_6115() && !(class_17992 = class_3102.field_1724.method_6030()).method_7960()) {
                                            n = class_17992.method_7935((class_1309)class_3102.field_1724);
                                        }
                                        UseAnimationSuppressor.release(class_12682, class_3102.field_1687.method_8510(), n);
                                    }
                                }
                            }
                        } else {
                            UseAnimationSuppressor.suppress(class_12682);
                        }
                    }
                }
            }
        } else {
            UseAnimationSuppressor.clear();
        }
    }

    private static class_1268 resolveMeshSuppressorHand(class_310 class_3102) {
        if (class_3102 == null || class_3102.field_1724 == null) {
            return null;
        }
        if (class_3102.field_1724.method_6115()) {
            return class_3102.field_1724.method_6058();
        }
        return activeSourceArm == null ? null : PunchyAnimationManager.getHandForArm(class_3102, activeSourceArm);
    }

    private static float getLastEffectTime(AnimationClip animationClip, String string, float f) {
        if (animationClip != null && string != null && !string.isEmpty()) {
            if (animationClip.hasEffect(string)) {
                return Float.POSITIVE_INFINITY;
            }
            float f2 = Float.NEGATIVE_INFINITY;
            for (Map.Entry<Float, Set<String>> entry : animationClip.getTimelineEffectsUpTo(f)) {
                if (!entry.getValue().contains(string)) continue;
                f2 = entry.getKey().floatValue();
            }
            return f2;
        }
        return Float.NEGATIVE_INFINITY;
    }

    public static void updateItemOverrides(class_310 class_3102) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            AnimationClip animationClip = POSE_HANDLER.getCurrentClip();
            if (animationClip != null && POSE_HANDLER.isPlaying()) {
                float f = POSE_HANDLER.getCurrentTime();
                ItemOverrideState itemOverrideState = new ItemOverrideState();
                ItemOverrideState itemOverrideState2 = new ItemOverrideState();
                PunchyAnimationManager.applyItemEffectsOrdered(animationClip.getEffects(), itemOverrideState, itemOverrideState2);
                for (Map.Entry<Float, Set<String>> entry : animationClip.getTimelineEffectsUpTo(f)) {
                    PunchyAnimationManager.applyItemEffectsOrdered((Iterable<String>)entry.getValue(), itemOverrideState, itemOverrideState2);
                }
                ITEM_OVERRIDES.put(class_1306.field_6183, itemOverrideState2);
                ITEM_OVERRIDES.put(class_1306.field_6182, itemOverrideState);
            } else {
                PunchyAnimationManager.clearItemOverrides();
            }
        } else {
            PunchyAnimationManager.clearItemOverrides();
        }
    }

    public static void updateSoundEffects(class_310 class_3102) {
        if (!SOUND_ENABLED) {
            lastSoundClip = null;
            lastSoundTime = 0.0f;
        } else if (class_3102 == null) {
            lastSoundClip = null;
            lastSoundTime = 0.0f;
        } else {
            AnimationClip animationClip = POSE_HANDLER.getCurrentClip();
            if (animationClip != null && POSE_HANDLER.isPlaying()) {
                float f = POSE_HANDLER.getCurrentTime();
                if (animationClip != lastSoundClip) {
                    lastSoundClip = animationClip;
                    lastSoundTime = 0.0f;
                    PunchyAnimationManager.playSoundsAtTime(animationClip, 0.0f, class_3102);
                }
                if (f < lastSoundTime) {
                    PunchyAnimationManager.playSoundsBetween(animationClip, 0.0f, f, class_3102);
                } else {
                    PunchyAnimationManager.playSoundsBetween(animationClip, lastSoundTime, f, class_3102);
                }
                lastSoundTime = f;
            } else {
                lastSoundClip = null;
                lastSoundTime = 0.0f;
            }
        }
    }

    public static class_1799 resolveRenderStack(class_1657 class_16572, class_1306 class_13062, class_1799 class_17992) {
        if (class_16572 != null && class_13062 != null) {
            AnimationClip animationClip;
            class_1306 class_13063;
            boolean bl;
            ItemOverrideState itemOverrideState = ITEM_OVERRIDES.get(class_13062);
            boolean bl2 = bl = itemOverrideState != null && itemOverrideState.forceShow;
            if (class_16572.method_6115()) {
                class_1268 class_12682 = class_16572.method_6058();
                class_1306 class_13064 = class_13063 = class_12682 == class_1268.field_5808 ? class_16572.method_6068() : PunchyAnimationManager.opposite(class_16572.method_6068());
                if (class_13063 == class_13062) {
                    return class_17992;
                }
            }
            class_1799 forcedRenderStack = ConsumeStateMachine.getForcedRenderStack(class_13062);
            if (forcedRenderStack != null && !forcedRenderStack.method_7960()) {
                return forcedRenderStack;
            }
            if (!bl && PunchyClient.shouldSuppressOffhandRender(class_16572)) {
                class_1306 class_13065 = class_13063 = class_16572.method_6068() == class_1306.field_6183 ? class_1306.field_6182 : class_1306.field_6183;
                if (class_13062 == class_13063) {
                    return class_1799.field_8037;
                }
            }
            class_1306 class_13066 = class_13063 = class_16572.method_6068() == class_1306.field_6183 ? class_1306.field_6182 : class_1306.field_6183;
            if (class_13062 == class_13063 && HandEquipStateMachine.shouldHideOffhandTridentThrowCacheRender()) {
                return class_1799.field_8037;
            }
            class_1799 visualOffhandStack = HandEquipStateMachine.getVisualOffhandStack((class_746)class_16572);
            if (class_13062 == class_13063 && HandEquipStateMachine.isOffhandEquipTransitionActive() && visualOffhandStack != null && !visualOffhandStack.method_7960()) {
                return visualOffhandStack;
            }
            animationClip = POSE_HANDLER.getCurrentClip();
            if ((PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_loyalty") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_riptide")) && activeSourceArm != null && class_13062 == activeSourceArm) {
                return class_1799.field_8037;
            }
            if (itemOverrideState != null && itemOverrideState.hidden) {
                return class_1799.field_8037;
            }
            if (itemOverrideState != null && !itemOverrideState.stack.method_7960()) {
                return itemOverrideState.stack;
            }
            EquipOverrideState equipOverrideState = EQUIP_OVERRIDES.get(class_13062);
            if (equipOverrideState != null && equipOverrideState.active) {
                return equipOverrideState.stack;
            }
            return PunchyAnimationManager.shouldHideBaseItem(class_16572, class_13062) ? class_1799.field_8037 : class_17992;
        }
        return class_17992;
    }

    private static boolean shouldHideBaseItem(class_1657 class_16572, class_1306 class_13062) {
        if (class_16572 != null && class_13062 != null) {
            ItemOverrideState itemOverrideState = ITEM_OVERRIDES.get(class_13062);
            if (itemOverrideState != null && itemOverrideState.forceShow) {
                return false;
            }
            if (!PunchyAnimationManager.isDualHandedActive()) {
                return false;
            }
            if (activeSourceArm == null) {
                return false;
            }
            AnimationClip animationClip = POSE_HANDLER.getCurrentClip();
            return PunchyAnimationManager.isClipNamed(animationClip, "use_shield") ? false : class_13062 != activeSourceArm;
        }
        return false;
    }

    public static void setSourceHand(class_310 class_3102, class_1268 class_12682) {
        activeSourceArm = PunchyAnimationManager.getUseArm(class_3102, class_12682);
    }

    public static void clearSourceHand() {
        activeSourceArm = null;
    }

    public static class_1306 getSourceArm() {
        return activeSourceArm;
    }

    public static void syncSourceHandToActiveClip() {
        AnimationClip animationClip = POSE_HANDLER.getCurrentClip();
        if (!POSE_HANDLER.isPlaying() || !PunchyAnimationManager.isDualHandedActive(animationClip, POSE_HANDLER.getCurrentTime())) {
            activeSourceArm = null;
        }
    }

    private static boolean hasOffhandItem(class_310 class_3102) {
        return class_3102 != null && class_3102.field_1724 != null && class_3102.field_1724.method_6079() != null && !class_3102.field_1724.method_6079().method_7960();
    }

    private static boolean hasDualHandedOffhandContext(class_310 class_3102) {
        class_746 class_7462;
        if (PunchyAnimationManager.hasOffhandItem(class_3102)) {
            return true;
        }
        if (class_3102 != null && (class_7462 = class_3102.field_1724) instanceof class_746) {
            return PunchyClient.shouldSuppressOffhandRender((class_1657)class_7462) || PunchyClient.shouldLowerOffhand(class_7462) || HandEquipStateMachine.shouldKeepOffhandVisibleUntilHandOutEnds() || HandEquipStateMachine.isOffhandEquipTransitionActive();
        }
        return false;
    }

    private static float resolveDualHandedEffectTime(AnimationClip animationClip) {
        if (animationClip == null) {
            return 0.0f;
        }
        if (POSE_HANDLER.getCurrentClip() == animationClip && POSE_HANDLER.isPlaying()) {
            return POSE_HANDLER.getCurrentTime();
        }
        return SWIM_HANDLER.getCurrentClip() == animationClip && SWIM_HANDLER.isPlaying() ? SWIM_HANDLER.getCurrentTime() : 0.0f;
    }

    public static void setSuppressOffhandForAttack(AnimationClip animationClip, boolean bl) {
        if (bl && animationClip != null) {
            suppressOffhandForAttack = true;
            suppressOffhandClip = animationClip;
        } else {
            suppressOffhandForAttack = false;
            suppressOffhandClip = null;
        }
    }

    public static boolean shouldSuppressOffhandForAttack() {
        if (!suppressOffhandForAttack) {
            return false;
        }
        if (!POSE_HANDLER.isPlaying()) {
            suppressOffhandForAttack = false;
            suppressOffhandClip = null;
            return false;
        }
        AnimationClip animationClip = POSE_HANDLER.getCurrentClip();
        if (animationClip != null && animationClip == suppressOffhandClip) {
            return true;
        }
        suppressOffhandForAttack = false;
        suppressOffhandClip = null;
        return false;
    }

    public static boolean isItemForceShown(class_1306 class_13062) {
        if (class_13062 == null) {
            return false;
        }
        ItemOverrideState itemOverrideState = ITEM_OVERRIDES.get(class_13062);
        return itemOverrideState != null && itemOverrideState.forceShow;
    }

    private static void clearItemOverrides() {
        ITEM_OVERRIDES.clear();
    }

    private static void playSoundsBetween(AnimationClip animationClip, float f, float f2, class_310 class_3102) {
        if (animationClip != null && class_3102 != null) {
            for (Map.Entry<Float, Set<String>> entry : animationClip.getSoundEffectsBetween(f, f2).entrySet()) {
                for (String string : entry.getValue()) {
                    PunchyAnimationManager.playAnimationSound(string, class_3102);
                }
            }
        }
    }

    private static void playSoundsAtTime(AnimationClip animationClip, float f, class_310 class_3102) {
        if (animationClip != null && class_3102 != null) {
            for (String string : animationClip.getSoundEffectsAt(f)) {
                PunchyAnimationManager.playAnimationSound(string, class_3102);
            }
        }
    }

    private static void playAnimationSound(String string, class_310 class_3102) {
        if (string != null && !string.isBlank() && class_3102 != null) {
            String string2 = string.trim();
            class_2960 class_29602 = class_2960.method_60655((String)"punchy", (String)("sounds/animationssounds/" + string2 + ".ogg"));
            try {
                if (class_3102.method_1478().method_14486(class_29602).isEmpty()) {
                    return;
                }
            }
            catch (Exception exception) {
                return;
            }
            class_2960 class_29603 = class_2960.method_60655((String)"punchy", (String)("animationssounds/" + string2));
            class_3414 class_34142 = class_3414.method_47908((class_2960)class_29603);
            class_3102.method_1483().method_4873((class_1113)class_1109.method_4758((class_3414)class_34142, (float)1.0f));
        }
    }

    public static void setEquipOverride(class_1306 class_13063, class_1799 class_17992) {
        if (class_13063 != null) {
            EquipOverrideState equipOverrideState = EQUIP_OVERRIDES.computeIfAbsent(class_13063, class_13062 -> new EquipOverrideState());
            equipOverrideState.active = true;
            equipOverrideState.stack = class_17992 == null ? class_1799.field_8037 : class_17992;
        }
    }

    public static void clearEquipOverride(class_1306 class_13062) {
        EquipOverrideState equipOverrideState;
        if (class_13062 != null && (equipOverrideState = EQUIP_OVERRIDES.get(class_13062)) != null) {
            equipOverrideState.active = false;
            equipOverrideState.stack = class_1799.field_8037;
        }
    }

    public static void clearAllEquipOverrides() {
        for (class_1306 class_13062 : class_1306.values()) {
            PunchyAnimationManager.clearEquipOverride(class_13062);
        }
    }

    public static boolean isEquipOverrideActive(class_1306 class_13062) {
        if (class_13062 == null) {
            return false;
        }
        EquipOverrideState equipOverrideState = EQUIP_OVERRIDES.get(class_13062);
        return equipOverrideState != null && equipOverrideState.active;
    }

    public static boolean hasRenderableEquipOverride(class_1306 class_13062) {
        if (class_13062 == null) {
            return false;
        }
        EquipOverrideState equipOverrideState = EQUIP_OVERRIDES.get(class_13062);
        return equipOverrideState != null && equipOverrideState.active && equipOverrideState.stack != null && !equipOverrideState.stack.method_7960();
    }

    private static void applyItemEffectsOrdered(Iterable<String> iterable, ItemOverrideState itemOverrideState, ItemOverrideState itemOverrideState2) {
        if (iterable != null) {
            String string;
            for (String string2 : iterable) {
                if (string2 == null || string2.isBlank() || !(string = string2.toLowerCase(Locale.ROOT).trim()).startsWith("left_switch_item_") && !string.startsWith("right_switch_item_")) continue;
                PunchyAnimationManager.applyItemEffect(string, itemOverrideState, itemOverrideState2);
            }
            for (String string2 : iterable) {
                if (string2 == null || string2.isBlank()) continue;
                string = string2.toLowerCase(Locale.ROOT).trim();
                if (string.startsWith("hide_left_switch_item_") || string.startsWith("hide_right_switch_item_")) {
                    PunchyAnimationManager.applyItemEffect(string, itemOverrideState, itemOverrideState2);
                }
                if (string.equals("show_left_switch_item") || string.equals("show_right_switch_item") || string.startsWith("show_left_switch_item_") || string.startsWith("show_right_switch_item_")) {
                    PunchyAnimationManager.applyItemEffect(string, itemOverrideState, itemOverrideState2);
                }
                if (!"hide_item".equals(string)) continue;
                PunchyAnimationManager.applyHideItemEffect(itemOverrideState, itemOverrideState2);
            }
        }
    }

    private static void applyItemEffect(String string, ItemOverrideState itemOverrideState, ItemOverrideState itemOverrideState2) {
        if (string != null && !string.isBlank()) {
            String string2 = string.toLowerCase(Locale.ROOT).trim();
            if (string2.startsWith("left_switch_item_")) {
                itemOverrideState.stack = PunchyAnimationManager.resolveItemStack(string2.substring("left_switch_item_".length()));
                itemOverrideState.hidden = false;
                itemOverrideState.forceShow = true;
            } else if (string2.startsWith("right_switch_item_")) {
                itemOverrideState2.stack = PunchyAnimationManager.resolveItemStack(string2.substring("right_switch_item_".length()));
                itemOverrideState2.hidden = false;
                itemOverrideState2.forceShow = true;
            } else if (string2.startsWith("hide_left_switch_item_")) {
                itemOverrideState.stack = class_1799.field_8037;
                itemOverrideState.hidden = true;
                itemOverrideState.forceShow = false;
            } else if (string2.startsWith("hide_right_switch_item_")) {
                itemOverrideState2.stack = class_1799.field_8037;
                itemOverrideState2.hidden = true;
                itemOverrideState2.forceShow = false;
            } else if (string2.equals("show_left_switch_item")) {
                itemOverrideState.stack = class_1799.field_8037;
                itemOverrideState.hidden = false;
                itemOverrideState.forceShow = true;
            } else if (string2.equals("show_right_switch_item")) {
                itemOverrideState2.stack = class_1799.field_8037;
                itemOverrideState2.hidden = false;
                itemOverrideState2.forceShow = true;
            } else if (string2.startsWith("show_left_switch_item_")) {
                String string3 = string2.substring("show_left_switch_item_".length()).trim();
                itemOverrideState.stack = !string3.isBlank() && !"*".equals(string3) ? PunchyAnimationManager.resolveItemStack(string3) : class_1799.field_8037;
                itemOverrideState.hidden = false;
                itemOverrideState.forceShow = true;
            } else if (string2.startsWith("show_right_switch_item_")) {
                String string4 = string2.substring("show_right_switch_item_".length()).trim();
                itemOverrideState2.stack = !string4.isBlank() && !"*".equals(string4) ? PunchyAnimationManager.resolveItemStack(string4) : class_1799.field_8037;
                itemOverrideState2.hidden = false;
                itemOverrideState2.forceShow = true;
            }
        }
    }

    private static void applyHideItemEffect(ItemOverrideState itemOverrideState, ItemOverrideState itemOverrideState2) {
        if (activeSourceArm != null) {
            if (activeSourceArm == class_1306.field_6183) {
                itemOverrideState.stack = class_1799.field_8037;
                itemOverrideState.hidden = true;
            } else {
                itemOverrideState2.stack = class_1799.field_8037;
                itemOverrideState2.hidden = true;
            }
        }
    }

    private static class_1799 resolveItemStack(String string) {
        if (string != null && !string.isBlank()) {
            class_2960 class_29602 = class_2960.method_12829((String)string.trim());
            if (class_29602 == null) {
                return class_1799.field_8037;
            }
            class_1792 class_17922 = class_7923.field_41178.method_17966(class_29602).orElse(class_1802.field_8162);
            return class_17922 == class_1802.field_8162 ? class_1799.field_8037 : new class_1799((class_1935)class_17922);
        }
        return class_1799.field_8037;
    }

    public static float clipSpeedMultiplier(AnimationClip animationClip) {
        if (animationClip == null) {
            return 1.0f;
        }
        if (PunchyAnimationManager.isClipNamed(animationClip, "use_bow") || PunchyAnimationManager.isClipNamed(animationClip, "use_shield") || PunchyAnimationManager.isClipNamed(animationClip, "use_crossbow") || PunchyAnimationManager.isClipNamed(animationClip, "use_crossbow_shot")) {
            return 1.0f / Math.max(0.01f, ANIMATION_SPEED);
        }
        if (PunchyAnimationManager.isClipNamed(animationClip, "use_trident") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_loyalty") || PunchyAnimationManager.isClipNamed(animationClip, "use_trident_throw_riptide") || PunchyAnimationManager.isClipNamed(animationClip, "attack_mace")) {
            return 1.0f / Math.max(0.01f, ANIMATION_SPEED);
        }
        if (PunchyAnimationManager.isClipNamed(animationClip, "use_eat") || PunchyAnimationManager.isClipNamed(animationClip, "use_drink")) {
            return 1.0f;
        }
        if (animationClip == SWIMING_CLIP) {
            return 1.0f / Math.max(0.01f, ANIMATION_SPEED);
        }
        if (PunchyAnimationManager.isClipNamed(animationClip, "falling_in_water")) {
            return 1.0f;
        }
        if (PunchyAnimationManager.isClipNamed(animationClip, "dry_out")) {
            return 1.0f / Math.max(0.01f, ANIMATION_SPEED);
        }
        if (PunchyAnimationManager.isClipNamed(animationClip, "hand_in") || PunchyAnimationManager.isClipNamed(animationClip, "hand_out")) {
            return 1.0f / Math.max(0.01f, ANIMATION_SPEED);
        }
        return PunchyAnimationManager.isClipNamed(animationClip, "interact") ? 5.4f / Math.max(0.01f, ANIMATION_SPEED) : 1.0f;
    }

    public static class_1306 getMiningArm() {
        class_310 class_3102 = class_310.method_1551();
        return class_3102.field_1724 == null ? class_1306.field_6183 : class_3102.field_1724.method_6068();
    }

    public static class_1306 getUseArm(class_310 class_3102, class_1268 class_12682) {
        if (class_3102 != null && class_3102.field_1724 != null) {
            return class_12682 == class_1268.field_5810 ? PunchyAnimationManager.opposite(class_3102.field_1724.method_6068()) : class_3102.field_1724.method_6068();
        }
        return class_1306.field_6183;
    }

    public static class_1268 getHandForArm(class_310 class_3102, class_1306 class_13062) {
        if (class_3102 != null && class_3102.field_1724 != null && class_13062 != null) {
            boolean bl;
            boolean bl2 = bl = class_3102.field_1724.method_6068() == class_1306.field_6183;
            if (class_13062 == class_1306.field_6183) {
                return bl ? class_1268.field_5808 : class_1268.field_5810;
            }
            return bl ? class_1268.field_5810 : class_1268.field_5808;
        }
        return class_1268.field_5808;
    }

    private static class_1306 opposite(class_1306 class_13062) {
        return class_13062 == class_1306.field_6182 ? class_1306.field_6183 : class_1306.field_6182;
    }

    static {
        LEFT_SWITCH_PREFIX = "left_switch_item_";
        RIGHT_SWITCH_PREFIX = "right_switch_item_";
        HIDE_LEFT_SWITCH_PREFIX = "hide_left_switch_item_";
        HIDE_RIGHT_SWITCH_PREFIX = "hide_right_switch_item_";
        SHOW_LEFT_SWITCH = "show_left_switch_item";
        SHOW_RIGHT_SWITCH = "show_right_switch_item";
        SHOW_LEFT_SWITCH_PREFIX = "show_left_switch_item_";
        SHOW_RIGHT_SWITCH_PREFIX = "show_right_switch_item_";
        HIDE_ITEM_EFFECT = "hide_item";
        POSE_HANDLER = new PoseHandler();
        SWIM_HANDLER = new PoseHandler();
        ANIMATION_SPEED = 5.5f;
        ITEM_OVERRIDES = new EnumMap(class_1306.class);
        EQUIP_OVERRIDES = new EnumMap(class_1306.class);
        SOUND_ENABLED = false;
        ALL_CLIPS = new HashMap<String, AnimationClip>();
        ALL_CLIP_VARIANTS = new HashMap<String, LinkedHashMap<String, AnimationClip>>();
        TYPE_CLIP_ALIASES = PunchyAnimationManager.buildTypeClipAliases();
        CLIP_RESOLUTION_CACHE = new HashMap<String, ClipResolution>();
        MIX_RELOAD_PENDING = false;
        MIX_RELOAD_DEADLINE_NANOS = 0L;
    }

    public record PackOption(String optionId, String label) {
    }

    public record ClipResolution(AnimationClip clip, String optionId, String note) {
    }

    static final class ItemOverrideState {
        class_1799 stack = class_1799.field_8037;
        boolean hidden;
        boolean forceShow;

        ItemOverrideState() {
        }
    }

    static final class EquipOverrideState {
        class_1799 stack = class_1799.field_8037;
        boolean active;

        EquipOverrideState() {
        }
    }
}

