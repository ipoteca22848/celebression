package ru.method.celebression.punchy.client.state;

import ru.method.celebression.animation.Animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3486;
import net.minecraft.class_5321;
import net.minecraft.class_638;
import net.minecraft.class_6862;
import net.minecraft.class_6880;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import net.minecraft.class_7924;
import org.joml.Vector3f;
import ru.method.celebression.punchy.client.animation.ItemAnimationManager;
import ru.method.celebression.punchy.client.animation.PunchyAnimationManager;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.animation.data.BoneTrack;
import ru.method.celebression.punchy.client.animation.data.Keyframe;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

final class CustomAnimationResolver {
    private static final String EFFECT_BLEND_FROM_CURRENT_ORIGIN = "blend_from_current_origin";
    private static final String EFFECT_REBASE_BLEND_IN = "rebase_blend_in";
    static final float WALK_ON_WATER_ACTION_REBASE_SPEED = 0.95f;

    private CustomAnimationResolver() {
    }

    static PunchyTuningConfig.CustomAnimationEntry findEntry(class_1799 class_17992, String string) {
        List<PunchyTuningConfig.CustomAnimationEntry> list = CustomAnimationResolver.findEntries(class_17992, string, true);
        return list.isEmpty() ? null : list.get(0);
    }

    static PunchyTuningConfig.CustomAnimationEntry findEntry(class_1799 class_17992, String string, boolean bl) {
        List<PunchyTuningConfig.CustomAnimationEntry> list = CustomAnimationResolver.findEntries(class_17992, string, bl);
        return list.isEmpty() ? null : list.get(0);
    }

    static PunchyTuningConfig.CustomAnimationEntry findInteractionEntry(class_1799 class_17992, class_2680 class_26802) {
        if (class_17992 == null) {
            return null;
        }
        List<PunchyTuningConfig.CustomAnimationEntry> list = ToolTuning.getCustomAnimation(class_17992);
        if (list != null && !list.isEmpty()) {
            String string = CustomAnimationResolver.normalizeType("interaction");
            for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list) {
                if (customAnimationEntry == null || customAnimationEntry.type == null || !string.equals(CustomAnimationResolver.normalizeType(customAnimationEntry.type)) || !CustomAnimationResolver.matchesWorldTriggers(customAnimationEntry) || !CustomAnimationResolver.matchesInteractionBlocks(customAnimationEntry.items, class_26802, customAnimationEntry.blockState)) continue;
                return customAnimationEntry;
            }
            return null;
        }
        return null;
    }

    static List<PunchyTuningConfig.CustomAnimationEntry> findEntries(class_1799 class_17992, String string) {
        return CustomAnimationResolver.findEntries(class_17992, string, true);
    }

    static List<PunchyTuningConfig.CustomAnimationEntry> findEntries(class_1799 class_17992, String string, boolean bl) {
        if (class_17992 != null && string != null && !string.isBlank()) {
            List<PunchyTuningConfig.CustomAnimationEntry> list;
            List<PunchyTuningConfig.CustomAnimationEntry> list2 = list = bl ? ToolTuning.getCustomAnimation(class_17992) : ToolTuning.getCustomAnimationWithoutDefaults(class_17992);
            if (list != null && !list.isEmpty()) {
                String string2 = CustomAnimationResolver.normalizeType(string);
                ArrayList<PunchyTuningConfig.CustomAnimationEntry> arrayList = new ArrayList<PunchyTuningConfig.CustomAnimationEntry>();
                for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry3 : list) {
                    if (customAnimationEntry3 == null || customAnimationEntry3.type == null || !string2.equals(CustomAnimationResolver.normalizeType(customAnimationEntry3.type)) || customAnimationEntry3.items != null && !customAnimationEntry3.items.isEmpty() && !CustomAnimationResolver.matchesItemList(class_17992, customAnimationEntry3.items) || !CustomAnimationResolver.matchesWorldTriggers(customAnimationEntry3)) continue;
                    arrayList.add(customAnimationEntry3);
                }
                if (arrayList.size() > 1) {
                    arrayList.sort((customAnimationEntry, customAnimationEntry2) -> Integer.compare(CustomAnimationResolver.entrySpecificityScore(customAnimationEntry2), CustomAnimationResolver.entrySpecificityScore(customAnimationEntry)));
                }
                return arrayList;
            }
            return List.of();
        }
        return List.of();
    }

    static PunchyTuningConfig.BetterCombatEntry findBetterCombatEntry(class_1799 class_17992, String string) {
        List<PunchyTuningConfig.BetterCombatEntry> list = CustomAnimationResolver.findBetterCombatEntries(class_17992, string);
        return list.isEmpty() ? null : list.get(0);
    }

    static List<PunchyTuningConfig.BetterCombatEntry> findBetterCombatEntries(class_1799 class_17992, String string) {
        if (class_17992 != null && string != null && !string.isBlank()) {
            List<PunchyTuningConfig.BetterCombatEntry> list = ToolTuning.getBetterCombat(class_17992);
            if (list != null && !list.isEmpty()) {
                String string2 = CustomAnimationResolver.normalizeType(string);
                ArrayList<PunchyTuningConfig.BetterCombatEntry> arrayList = new ArrayList<PunchyTuningConfig.BetterCombatEntry>();
                for (PunchyTuningConfig.BetterCombatEntry betterCombatEntry : list) {
                    if (betterCombatEntry == null || betterCombatEntry.type == null || !string2.equals(CustomAnimationResolver.normalizeType(betterCombatEntry.type))) continue;
                    arrayList.add(betterCombatEntry);
                }
                return arrayList;
            }
            return List.of();
        }
        return List.of();
    }

    static List<String> resolveBetterCombatComboNames(PunchyTuningConfig.BetterCombatEntry betterCombatEntry) {
        ArrayList<String> arrayList = new ArrayList<String>(6);
        if (betterCombatEntry == null) {
            return arrayList;
        }
        CustomAnimationResolver.addIfPresent(arrayList, betterCombatEntry.attack_1);
        CustomAnimationResolver.addIfPresent(arrayList, betterCombatEntry.attack_2);
        CustomAnimationResolver.addIfPresent(arrayList, betterCombatEntry.attack_3);
        CustomAnimationResolver.addIfPresent(arrayList, betterCombatEntry.attack_4);
        CustomAnimationResolver.addIfPresent(arrayList, betterCombatEntry.attack_5);
        CustomAnimationResolver.addIfPresent(arrayList, betterCombatEntry.attack_6);
        return arrayList;
    }

    static List<String> resolveComboNames(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        ArrayList<String> arrayList = new ArrayList<String>(3);
        if (customAnimationEntry == null) {
            return arrayList;
        }
        CustomAnimationResolver.addIfPresent(arrayList, customAnimationEntry.var_1);
        CustomAnimationResolver.addIfPresent(arrayList, customAnimationEntry.var_2);
        CustomAnimationResolver.addIfPresent(arrayList, customAnimationEntry.var_3);
        if (arrayList.isEmpty()) {
            CustomAnimationResolver.addIfPresent(arrayList, customAnimationEntry.name);
        }
        return arrayList;
    }

    static String resolveSingleName(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return null;
        }
        if (customAnimationEntry.animation != null && !customAnimationEntry.animation.isBlank()) {
            return customAnimationEntry.animation.trim();
        }
        if (customAnimationEntry.name != null && !customAnimationEntry.name.isBlank()) {
            return customAnimationEntry.name.trim();
        }
        return customAnimationEntry.var_1 != null && !customAnimationEntry.var_1.isBlank() ? customAnimationEntry.var_1.trim() : null;
    }

    static String resolveInteractionName(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return null;
        }
        return customAnimationEntry.animation != null && !customAnimationEntry.animation.isBlank() ? customAnimationEntry.animation.trim() : CustomAnimationResolver.resolveSingleName(customAnimationEntry);
    }

    static String resolveStartName(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return null;
        }
        return customAnimationEntry.start != null && !customAnimationEntry.start.isBlank() ? customAnimationEntry.start.trim() : CustomAnimationResolver.resolveSingleName(customAnimationEntry);
    }

    static String resolveLoopingName(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return null;
        }
        if (customAnimationEntry.looping != null && !customAnimationEntry.looping.isBlank()) {
            return customAnimationEntry.looping.trim();
        }
        return customAnimationEntry.keep_looping != null && !customAnimationEntry.keep_looping.isBlank() ? customAnimationEntry.keep_looping.trim() : CustomAnimationResolver.resolveSingleName(customAnimationEntry);
    }

    static String resolveEndName(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return null;
        }
        return customAnimationEntry.end != null && !customAnimationEntry.end.isBlank() ? customAnimationEntry.end.trim() : CustomAnimationResolver.resolveSingleName(customAnimationEntry);
    }

    static boolean isInteractionEntry(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        return customAnimationEntry != null && customAnimationEntry.type != null && "interaction".equalsIgnoreCase(customAnimationEntry.type.trim());
    }

    static String resolveCriticalName(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return null;
        }
        return customAnimationEntry.critical_hit != null && !customAnimationEntry.critical_hit.isBlank() ? customAnimationEntry.critical_hit.trim() : null;
    }

    static void applyLoopSpeed(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            PunchyAnimationManager.POSE_HANDLER.resetLoopSpeedParams();
        } else {
            PunchyAnimationManager.POSE_HANDLER.setLoopSpeedParams(customAnimationEntry.loop_speed_max, customAnimationEntry.loop_speed_ramp);
        }
    }

    static boolean shouldStopImmediately(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        return customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.stop_immediate);
    }

    static boolean shouldSkipBlendIn(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        return customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.skip_blend_in);
    }

    static boolean shouldSkipBlendOut(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        return customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.skip_blend_out);
    }

    static boolean shouldSkipHandEquip(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        return customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.skip_hand_equip);
    }

    static boolean shouldSkipBlendInOnIdlePose() {
        return !PunchyAnimationManager.POSE_HANDLER.isPlaying() && !PunchyAnimationManager.POSE_HANDLER.isBlendOutActive();
    }

    static boolean shouldBlendFromCurrentOrigin(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, AnimationClip animationClip) {
        return customAnimationEntry != null && Boolean.TRUE.equals(customAnimationEntry.blend_from_current_origin) ? true : animationClip != null && (animationClip.hasEffectAny("blend_from_current_origin") || animationClip.hasEffectAny("rebase_blend_in") || CustomAnimationResolver.shouldAutoBlendFromWalkOnWater());
    }

    static boolean shouldAutoBlendFromWalkOnWater() {
        return ItemAnimationManager.isTypeActive("walk_on_water_v2") && PunchyAnimationManager.POSE_HANDLER.getCurrentClip() != null && (PunchyAnimationManager.POSE_HANDLER.isPlaying() || PunchyAnimationManager.POSE_HANDLER.isBlendOutActive());
    }

    static void playConfiguredClip(AnimationClip animationClip, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, float f, boolean bl, boolean bl2) {
        if (animationClip != null) {
            if (CustomAnimationResolver.shouldBlendFromCurrentOrigin(customAnimationEntry, animationClip)) {
                float f2 = CustomAnimationResolver.resolveBlendFromCurrentOriginStartTime(animationClip);
                if (CustomAnimationResolver.shouldAutoBlendFromWalkOnWater()) {
                    PunchyAnimationManager.POSE_HANDLER.setRebaseReturnSpeedNext(0.95f);
                }
                PunchyAnimationManager.POSE_HANDLER.playAtTimeRebasedFromCurrentReturnToOrigin(animationClip, f, bl2, f2);
            } else {
                boolean bl3 = bl || CustomAnimationResolver.shouldSkipBlendInOnIdlePose();
                PunchyAnimationManager.POSE_HANDLER.play(animationClip, f, bl3, bl2);
            }
        }
    }

    static ClipLookup resolveNamedOrTypeFallback(class_1799 class_17992, String string, PunchyTuningConfig.CustomAnimationEntry customAnimationEntry, class_310 class_3102, class_1268 class_12682) {
        if (string != null && !string.isBlank()) {
            Object object;
            Object object2;
            String string2;
            boolean bl = CustomAnimationResolver.isGlobalHandEquipType(string);
            String string3 = PunchyTuningConfig.getMixPreferredOptionForType(class_17992, string);
            if (bl && (string3 == null || string3.isBlank())) {
                string3 = PunchyTuningConfig.getGlobalMixPriorityOptionId();
            }
            if (bl) {
                PunchyAnimationManager.ClipResolution clipResolution;
                String string4 = CustomAnimationResolver.resolveSingleName(customAnimationEntry);
                if (string4 != null && !string4.isBlank() && (clipResolution = PunchyAnimationManager.resolveNamedClipWithSource(string4, class_3102, class_12682, string3)) != null && clipResolution.clip() != null && CustomAnimationResolver.optionMatchesPreferred(clipResolution.optionId(), string3)) {
                    return new ClipLookup(clipResolution.clip(), customAnimationEntry, string4, false);
                }
                clipResolution = PunchyAnimationManager.resolveTypeFallbackClipWithSource(string, class_3102, class_12682, string3);
                return clipResolution != null && clipResolution.clip() != null ? new ClipLookup(clipResolution.clip(), null, clipResolution.clip().getName(), true) : new ClipLookup(null, null, string4, false);
            }
            PunchyTuningConfig.CustomAnimationEntry customAnimationEntry2 = customAnimationEntry;
            if (customAnimationEntry != null && string3 != null && !string3.isBlank() && (string2 = customAnimationEntry.mix_pack_option) != null && !string2.isBlank()) {
                object2 = string2.trim().toLowerCase(Locale.ROOT);
                object = string3.trim().toLowerCase(Locale.ROOT);
                if (!((String)object).equals(object2) && PunchyTuningConfig.isMixCompatOption((String)object2)) {
                    customAnimationEntry2 = null;
                }
            }
            if (customAnimationEntry2 != null && (string2 = customAnimationEntry2.mix_pack_option) != null && !string2.isBlank()) {
                string3 = string2.trim().toLowerCase(Locale.ROOT);
            }
            if (string3 == null || string3.isBlank()) {
                string3 = PunchyTuningConfig.getMixPreferredOptionForEntry(class_17992, customAnimationEntry2);
            }
            string2 = CustomAnimationResolver.resolveSingleName(customAnimationEntry2);
            object2 = null;
            if (string2 != null && !string2.isBlank()) {
                object2 = PunchyAnimationManager.resolveNamedClip(string2, class_3102, class_12682, string3);
            }
            if (object2 != null) {
                return new ClipLookup((AnimationClip)object2, customAnimationEntry2, string2, false);
            }
            object = PunchyAnimationManager.resolveTypeFallbackClipWithSource(string, class_3102, class_12682, string3);
            return object != null && ((PunchyAnimationManager.ClipResolution)object).clip() != null ? new ClipLookup(((PunchyAnimationManager.ClipResolution)object).clip(), customAnimationEntry2, ((PunchyAnimationManager.ClipResolution)object).clip().getName(), true) : new ClipLookup(null, customAnimationEntry2, string2, false);
        }
        return new ClipLookup(null, customAnimationEntry, null, false);
    }

    static ClipLookup resolveNamedOrTypeFallback(class_1799 class_17992, String string, class_310 class_3102, class_1268 class_12682) {
        return CustomAnimationResolver.resolveNamedOrTypeFallback(class_17992, string, CustomAnimationResolver.findEntry(class_17992, string), class_3102, class_12682);
    }

    static void applySkipBlendOut(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (CustomAnimationResolver.shouldSkipBlendOut(customAnimationEntry)) {
            PunchyAnimationManager.POSE_HANDLER.setBlendOutDurationNext(0.0f);
        }
    }

    private static float resolveBlendFromCurrentOriginStartTime(AnimationClip animationClip) {
        if (animationClip == null) {
            return 0.0f;
        }
        float f = Float.POSITIVE_INFINITY;
        block0: for (BoneTrack boneTrack : animationClip.getTracks().values()) {
            if (boneTrack == null) continue;
            for (Keyframe keyframe : boneTrack.getKeyframes()) {
                float f2;
                if (keyframe == null || (f2 = keyframe.timeStamp()) <= 0.0f || CustomAnimationResolver.isNeutralKeyframe(keyframe)) continue;
                f = Math.min(f, f2);
                continue block0;
            }
        }
        return !Float.isFinite(f) ? 0.0f : Math.max(0.0f, Math.min(f, animationClip.getLength()));
    }

    private static boolean isNeutralKeyframe(Keyframe keyframe) {
        return CustomAnimationResolver.isZeroVector(keyframe.position()) && CustomAnimationResolver.isZeroVector(keyframe.rotationEuler()) && CustomAnimationResolver.isIdentityScale(keyframe.scale());
    }

    private static boolean isZeroVector(Vector3f vector3f) {
        return vector3f == null || Math.abs(vector3f.x) < 1.0E-4f && Math.abs(vector3f.y) < 1.0E-4f && Math.abs(vector3f.z) < 1.0E-4f;
    }

    private static boolean isIdentityScale(Vector3f vector3f) {
        return vector3f == null || Math.abs(vector3f.x - 1.0f) < 1.0E-4f && Math.abs(vector3f.y - 1.0f) < 1.0E-4f && Math.abs(vector3f.z - 1.0f) < 1.0E-4f;
    }

    private static void addIfPresent(List<String> list, String string) {
        String string2;
        if (string != null && !(string2 = string.trim()).isEmpty()) {
            list.add(string2);
        }
    }

    private static String normalizeType(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "").replace(" ", "");
        return !"miningv2".equals(string2) && !"mining".equals(string2) ? string2 : "mining";
    }

    private static boolean isGlobalHandEquipType(String string) {
        return false;
    }

    private static boolean optionMatchesPreferred(String string, String string2) {
        return string != null && !string.isBlank() && string2 != null && !string2.isBlank() ? string.trim().equalsIgnoreCase(string2.trim()) : false;
    }

    private static boolean matchesInteractionBlocks(List<String> list, class_2680 class_26802, JsonObject jsonObject) {
        if (list != null && !list.isEmpty()) {
            if (class_26802 == null) {
                return false;
            }
            class_2960 class_29602 = class_7923.field_41175.method_10221(class_26802.method_26204());
            if (class_29602 == null) {
                return false;
            }
            String string = class_29602.toString();
            for (String string2 : list) {
                class_2960 class_29603;
                String string3;
                if (string2 == null || (string3 = string2.trim()).isEmpty() || (class_29603 = class_2960.method_12829((String)string3)) == null || !string.equals(class_29603.toString())) continue;
                return CustomAnimationResolver.matchesBlockState(class_26802, jsonObject);
            }
            return false;
        }
        return CustomAnimationResolver.matchesBlockState(class_26802, jsonObject);
    }

    private static boolean matchesBlockState(class_2680 class_26802, JsonObject jsonObject) {
        if (jsonObject == null || jsonObject.isEmpty()) {
            return true;
        }
        if (class_26802 == null) {
            return false;
        }
        for (Map.Entry entry : jsonObject.entrySet()) {
            String string = (String)entry.getKey();
            if (string == null || string.isBlank()) continue;
            class_2769<?> class_27692 = CustomAnimationResolver.findProperty(class_26802, string.trim());
            if (class_27692 == null) {
                return false;
            }
            JsonElement jsonElement = (JsonElement)entry.getValue();
            if (jsonElement == null || !jsonElement.isJsonPrimitive()) {
                return false;
            }
            String string2 = jsonElement.getAsString();
            if (string2 == null) {
                return false;
            }
            String string3 = String.valueOf(class_26802.method_11654(class_27692));
            if (string2.trim().equalsIgnoreCase(string3)) continue;
            return false;
        }
        return true;
    }

    private static class_2769<?> findProperty(class_2680 class_26802, String string) {
        for (class_2769 class_27692 : class_26802.method_28501()) {
            if (!class_27692.method_11899().equalsIgnoreCase(string)) continue;
            return class_27692;
        }
        return null;
    }

    private static int triggerSpecificityScore(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return 0;
        }
        int n = 0;
        if (customAnimationEntry.items != null && !customAnimationEntry.items.isEmpty()) {
            n += 100;
        }
        if (customAnimationEntry.in_water != null) {
            ++n;
        }
        if (customAnimationEntry.inside_water != null) {
            ++n;
        }
        if (customAnimationEntry.block_detection != null) {
            ++n;
        }
        if (customAnimationEntry.weather_detection != null) {
            ++n;
        }
        if (customAnimationEntry.time_detection != null) {
            ++n;
        }
        if (customAnimationEntry.biome_detection != null) {
            ++n;
        }
        return n;
    }

    private static int entrySpecificityScore(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        return CustomAnimationResolver.triggerSpecificityScore(customAnimationEntry);
    }

    private static boolean matchesWorldTriggers(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return false;
        }
        if (customAnimationEntry.in_water == null && customAnimationEntry.inside_water == null && customAnimationEntry.block_detection == null && customAnimationEntry.weather_detection == null && customAnimationEntry.time_detection == null && customAnimationEntry.biome_detection == null) {
            return true;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null && class_3102.field_1724 != null) {
            boolean bl;
            class_746 class_7462 = class_3102.field_1724;
            class_638 class_6382 = class_3102.field_1687;
            if (class_6382 == null) {
                return false;
            }
            if (customAnimationEntry.in_water != null) {
                bl = CustomAnimationResolver.isInWaterContact((class_1937)class_6382, (class_1657)class_7462);
                if (customAnimationEntry.in_water != bl) {
                    return false;
                }
            }
            if (customAnimationEntry.inside_water != null) {
                bl = class_7462.method_5777(class_3486.field_15517);
                if (customAnimationEntry.inside_water != bl) {
                    return false;
                }
            }
            if (customAnimationEntry.weather_detection != null && !CustomAnimationResolver.matchesWeather(customAnimationEntry.weather_detection, (class_1937)class_6382)) {
                return false;
            }
            if (customAnimationEntry.time_detection != null && !CustomAnimationResolver.matchesTime(customAnimationEntry.time_detection, (class_1937)class_6382)) {
                return false;
            }
            return customAnimationEntry.block_detection != null && !CustomAnimationResolver.matchesBlock(customAnimationEntry.block_detection, (class_1937)class_6382, class_7462.method_24515()) ? false : customAnimationEntry.biome_detection == null || CustomAnimationResolver.matchesBiome(customAnimationEntry.biome_detection, (class_1937)class_6382, class_7462.method_24515());
        }
        return false;
    }

    private static boolean isInWaterContact(class_1937 class_19372, class_1657 class_16572) {
        if (class_19372 != null && class_16572 != null) {
            if (!class_16572.method_5799() && !class_16572.method_5777(class_3486.field_15517)) {
                double d = class_16572.method_23317();
                double d2 = class_16572.method_23318();
                double d3 = class_16572.method_23321();
                double[] dArray = new double[]{d2 + 0.05, d2 + 0.6, d2 + 1.1};
                double[] dArray2 = new double[]{d, d - 0.3, d + 0.3};
                double[] dArray3 = new double[]{d3, d3 - 0.3, d3 + 0.3};
                for (double d4 : dArray) {
                    for (double d5 : dArray2) {
                        for (double d6 : dArray3) {
                            if (!class_19372.method_8316(class_2338.method_49637((double)d5, (double)d4, (double)d6)).method_15767(class_3486.field_15517)) continue;
                            return true;
                        }
                    }
                }
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean matchesWeather(PunchyTuningConfig.WeatherDetection weatherDetection, class_1937 class_19372) {
        if (weatherDetection != null && class_19372 != null) {
            boolean bl = class_19372.method_8419();
            boolean bl2 = class_19372.method_8546();
            if (weatherDetection.raining != null && weatherDetection.raining != bl) {
                return false;
            }
            if (weatherDetection.thundering != null && weatherDetection.thundering != bl2) {
                return false;
            }
            if (weatherDetection.clear != null) {
                boolean bl3;
                boolean bl4 = bl3 = !bl && !bl2;
                if (weatherDetection.clear != bl3) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    private static boolean matchesTime(PunchyTuningConfig.TimeDetection timeDetection, class_1937 class_19372) {
        if (timeDetection != null && class_19372 != null) {
            int n;
            int n2 = timeDetection.min != null ? timeDetection.min : -1;
            int n3 = n = timeDetection.max != null ? timeDetection.max : -1;
            if (timeDetection.range != null && timeDetection.range.size() >= 2) {
                n2 = timeDetection.range.get(0);
                n = timeDetection.range.get(1);
            }
            if (n2 < 0 && n < 0) {
                return true;
            }
            int n4 = (int)(class_19372.method_8532() % 24000L);
            if (n2 < 0) {
                return n4 <= n;
            }
            if (n < 0) {
                return n4 >= n2;
            }
            return n2 <= n ? n4 >= n2 && n4 <= n : n4 >= n2 || n4 <= n;
        }
        return true;
    }

    private static boolean matchesBlock(PunchyTuningConfig.BlockDetection blockDetection, class_1937 class_19372, class_2338 class_23382) {
        if (blockDetection != null && class_19372 != null && class_23382 != null) {
            List<String> list = blockDetection.blocks;
            if ((list == null || list.isEmpty()) && blockDetection.block != null) {
                list = List.of(blockDetection.block);
            }
            if (list != null && !list.isEmpty()) {
                int n = blockDetection.radius != null ? Math.max(0, blockDetection.radius) : 0;
                int n2 = blockDetection.offsetX != null ? blockDetection.offsetX : 0;
                int n3 = blockDetection.offsetY != null ? blockDetection.offsetY : 0;
                int n4 = blockDetection.offsetZ != null ? blockDetection.offsetZ : 0;
                class_2338 class_23383 = class_23382.method_10069(n2, n3, n4);
                if (n == 0) {
                    return CustomAnimationResolver.matchesBlockList(class_19372.method_8320(class_23383), list);
                }
                class_2338.class_2339 class_23392 = new class_2338.class_2339();
                for (int i = -n; i <= n; ++i) {
                    for (int j = -n; j <= n; ++j) {
                        for (int k = -n; k <= n; ++k) {
                            class_23392.method_10103(class_23383.method_10263() + i, class_23383.method_10264() + j, class_23383.method_10260() + k);
                            if (!CustomAnimationResolver.matchesBlockList(class_19372.method_8320((class_2338)class_23392), list)) continue;
                            return true;
                        }
                    }
                }
                return false;
            }
            return true;
        }
        return true;
    }

    private static boolean matchesBiome(PunchyTuningConfig.BiomeDetection biomeDetection, class_1937 class_19372, class_2338 class_23382) {
        if (biomeDetection != null && class_19372 != null && class_23382 != null) {
            List<String> list = biomeDetection.biomes;
            if ((list == null || list.isEmpty()) && biomeDetection.biome != null) {
                list = List.of(biomeDetection.biome);
            }
            if (list != null && !list.isEmpty()) {
                int n = biomeDetection.radius != null ? Math.max(0, biomeDetection.radius) : 0;
                int n2 = biomeDetection.offsetX != null ? biomeDetection.offsetX : 0;
                int n3 = biomeDetection.offsetY != null ? biomeDetection.offsetY : 0;
                int n4 = biomeDetection.offsetZ != null ? biomeDetection.offsetZ : 0;
                class_2338 class_23383 = class_23382.method_10069(n2, n3, n4);
                if (n == 0) {
                    return CustomAnimationResolver.matchesBiomeList(class_19372, class_23383, list);
                }
                class_2338.class_2339 class_23392 = new class_2338.class_2339();
                for (int i = -n; i <= n; ++i) {
                    for (int j = -n; j <= n; ++j) {
                        for (int k = -n; k <= n; ++k) {
                            class_23392.method_10103(class_23383.method_10263() + i, class_23383.method_10264() + j, class_23383.method_10260() + k);
                            if (!CustomAnimationResolver.matchesBiomeList(class_19372, (class_2338)class_23392, list)) continue;
                            return true;
                        }
                    }
                }
                return false;
            }
            return true;
        }
        return true;
    }

    private static boolean matchesBiomeList(class_1937 class_19372, class_2338 class_23382, List<String> list) {
        if (class_19372 != null && class_23382 != null && list != null && !list.isEmpty()) {
            class_6880 class_68802 = class_19372.method_23753(class_23382);
            for (String string : list) {
                if (string == null || string.isBlank()) continue;
                String value = string.trim();
                if (value.startsWith("#")) {
                    class_2960 id = class_2960.method_12829(value.substring(1));
                    class_6862 tag = id == null ? null : class_6862.method_40092(class_7924.field_41236, id);
                    if (tag != null && class_68802.method_40220(tag)) return true;
                } else {
                    class_2960 id = class_2960.method_12829(value);
                    class_5321 key = id == null ? null : class_5321.method_29179(class_7924.field_41236, id);
                    if (key != null && class_68802.method_40225(key)) return true;
                }
            }
            return false;
        }
        return false;
    }

    private static boolean matchesBlockList(class_2680 class_26802, List<String> list) {
        if (class_26802 != null && list != null && !list.isEmpty()) {
            class_2960 class_29602 = class_7923.field_41175.method_10221(class_26802.method_26204());
            String string = class_29602 == null ? null : class_29602.toString();
            for (String string2 : list) {
                class_6862 class_68622;
                class_2960 class_29603;
                String string3;
                if (string2 == null || string2.isBlank() || !((string3 = string2.trim()).startsWith("#") ? (class_29603 = class_2960.method_12829((String)string3.substring(1))) != null && class_26802.method_26164(class_68622 = class_6862.method_40092((class_5321)class_7924.field_41254, (class_2960)class_29603)) : (class_29603 = class_2960.method_12829((String)string3)) != null && string != null && string.equals(class_29603.toString()))) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static boolean matchesItemList(class_1799 class_17992, List<String> list) {
        if (class_17992 != null && !class_17992.method_7960() && list != null && !list.isEmpty()) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 == null) {
                return false;
            }
            String string = class_29602.toString();
            for (String string2 : list) {
                class_6862 class_68622;
                class_2960 class_29603;
                String string3;
                if (string2 == null || string2.isBlank() || !((string3 = string2.trim()).startsWith("#") ? (class_29603 = class_2960.method_12829((String)string3.substring(1))) != null && class_17992.method_31573(class_68622 = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_29603)) : (class_29603 = class_2960.method_12829((String)string3)) != null && string.equals(class_29603.toString()))) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    record ClipLookup(AnimationClip clip, PunchyTuningConfig.CustomAnimationEntry entry, String resolvedName, boolean usedTypeFallback) {
    }
}

