package ru.method.celebression.punchy.config;

import ru.method.celebression.animation.Animation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import net.minecraft.class_156;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import net.minecraft.class_7923;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.method.celebression.punchy.Punchy;
import ru.method.celebression.punchy.client.animation.data.AnimationClip;
import ru.method.celebression.punchy.client.animation.io.PunchyAnimationLoader;
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
import ru.method.celebression.punchy.client.tuning.TuningProfileManager;
import ru.method.celebression.punchy.config.PunchyConfig;

public final class PunchyTuningConfig {
    private static final Logger LOGGER;
    private static final boolean PERF_DEBUG;
    private static final Gson GSON;
    private static final String FILE_NAME;
    private static final String MERGED_ANIMATION_SELECTION_FILE;
    private static final class_2960 DEFAULTS_ID;
    private static final String COMPAT_DIR_NAME;
    private static final float EPSILON = 1.0E-6f;
    private static final String[] BUNDLED_COMPAT_PROFILES;
    private static final String ARM_MESH_SIDE_KEY;
    private static final String MIX_DEFAULT_OPTION_ID;
    private static final String BUILTIN_PACK_OPTION_ID;
    private static final Set<String> MIX_HIDDEN_SPECIFIC_IDS;
    private static List<String> cachedSelectedResourcePackIdsHighToLow;
    private static class_3300 cachedSelectedResourcePackResourceManager;
    private static String cachedActiveBuiltInPackId;
    private static class_3300 cachedActiveBuiltInPackResourceManager;
    private static long selectedPackLookupWindowStartedNanos;
    private static int selectedPackLookupCount;
    private static int selectedPackCacheMissCount;
    private static int builtInPackLookupCount;
    private static Data BASELINE_STATE;
    private static Data INITIAL_STATE;
    private static Data DEFAULTS_RESOURCE_STATE;
    private static boolean APPLYING_COMPAT_OVERLAY;
    private static final Map<String, List<String>> REGISTRY_CUSTOM_GROUPS;
    private static MixCatalog MIX_CATALOG;
    private static Set<String> MIX_COMPAT_OPTION_IDS;
    private static Set<String> MIX_PUNCHY_OPTION_IDS;
    private static final EnumMap<VanillaFirstPersonItemLayer.ToolKind, ItemRuntimeFlags> GROUP_RUNTIME_FLAGS;
    private static final Map<class_2960, ItemRuntimeFlags> SPECIFIC_RUNTIME_FLAGS;
    private static final Set<String> SPEAR_MIX_CLIP_NAMES;
    private static final Set<String> SWORD_PASSIVE_CLIP_NAMES;
    private static final Set<String> GENERIC_PASSIVE_CLIP_NAMES;
    private static final Map<String, List<String>> MIX_TYPE_CLIP_ALIASES;

    private PunchyTuningConfig() {
    }

    private static void diag(String string) {
        if (string == null || string.isBlank()) {
            // empty if block
        }
    }

    private static Map<String, List<String>> createMixTypeClipAliases() {
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        hashMap.put("attack", List.of("attack", "attack_sword", "attack_spear"));
        hashMap.put("mining", List.of("mining", "mining_foward", "mining_forward", "mining_sideways", "mining_digging"));
        hashMap.put("mining_v2", List.of("mining", "mining_foward", "mining_forward", "mining_sideways", "mining_digging"));
        hashMap.put("useitem", List.of("use_item", "useitem", "interaction", "interact", "mining_use_foward", "mining_use_forward", "mining_use_digging"));
        hashMap.put("interaction", List.of("interaction", "interact", "use_item", "useitem"));
        hashMap.put("spear_charge", List.of("use_spear_pose_1", "use_spear_pose_1_left", "use_spear_pose_1_right", "use_spear_pose_2", "use_spear_pose_2_left", "use_spear_pose_2_right", "use_spear_pose_3", "use_spear_pose_3_left", "use_spear_pose_3_right"));
        hashMap.put("inspect", List.of("inspect"));
        hashMap.put("hand_in", List.of("hand_in", "handin"));
        hashMap.put("hand_out", List.of("hand_out", "handout"));
        hashMap.put("falling", List.of("falling"));
        hashMap.put("triggerable", List.of("triggerable"));
        hashMap.put("use_shield", List.of("use_shield"));
        hashMap.put("use_bow", List.of("use_bow"));
        hashMap.put("use_crossbow", List.of("use_crossbow"));
        hashMap.put("use_crossbow_shot", List.of("use_crossbow_shot"));
        hashMap.put("useeat", List.of("use_eat", "useeat"));
        hashMap.put("usedrink", List.of("use_drink", "usedrink"));
        hashMap.put("idle", List.of("idle"));
        return hashMap;
    }

    private static boolean isMixThePacksEnabled() {
        return true;
    }

    public static List<String> getMixPackSlotIds() {
        if (!PunchyTuningConfig.isMixThePacksEnabled()) {
            return List.of();
        }
        return PunchyTuningConfig.MIX_CATALOG.slots.isEmpty() ? List.of() : new ArrayList<String>(PunchyTuningConfig.MIX_CATALOG.slots.keySet());
    }

    public static String getMixPackSlotLabel(String string) {
        if (string != null && !string.isBlank()) {
            MixSlotDef mixSlotDef = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
            if (mixSlotDef == null) {
                return string;
            }
            return mixSlotDef.type == MixSlotType.SPECIFIC && !PunchyTuningConfig.isSpecificSlotForced(mixSlotDef) && !PunchyTuningConfig.shouldAutoUseSpecificSlot(mixSlotDef) ? mixSlotDef.label + " (Inherit Kind)" : mixSlotDef.label;
        }
        return "";
    }

    public static List<String> getMixPackSlotOptionIds(String string) {
        MixSlotDef mixSlotDef = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
        if (mixSlotDef == null) {
            return List.of();
        }
        Set<String> set = PunchyTuningConfig.getActiveMixPackOptionIds();
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("punchy_default");
        linkedHashSet.add("punchy_default");
        if (mixSlotDef.optionOrder != null && !mixSlotDef.optionOrder.isEmpty()) {
            for (String string2 : mixSlotDef.optionOrder) {
                if (string2 == null || string2.isBlank() || "punchy_default".equals(string2) || !set.contains(string2) || !PunchyTuningConfig.slotHasOptionPayload(mixSlotDef, string2) || !linkedHashSet.add(string2)) continue;
                arrayList.add(string2);
            }
        }
        return arrayList;
    }

    private static boolean slotHasOptionPayload(MixSlotDef mixSlotDef, String string) {
        if (mixSlotDef != null && string != null && !string.isBlank() && !"punchy_default".equals(string)) {
            MixSlotPayload mixSlotPayload = mixSlotDef.optionPayloads.get(string);
            if (mixSlotPayload != null) {
                return true;
            }
            Map map = mixSlotDef.optionSpecificPayloads.get(string);
            return map != null && !map.isEmpty();
        }
        return false;
    }

    public static boolean isMixCompatOption(String string) {
        String string2 = PunchyTuningConfig.normalizeMixOption(string);
        return string2 != null && !string2.isBlank() && !"punchy_default".equals(string2) ? MIX_COMPAT_OPTION_IDS.contains(string2) : false;
    }

    private static boolean isUserResourcePackId(String string) {
        String string2 = PunchyTuningConfig.normalizeMixOption(string);
        return string2 != null && !string2.isBlank() && !"punchy_default".equals(string2);
    }

    public static String getMixPackSelectedOption(String string) {
        MixSlotDef mixSlotDef = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
        if (mixSlotDef == null) {
            return "punchy_default";
        }
        if (mixSlotDef.type == MixSlotType.SPECIFIC && !PunchyTuningConfig.isSpecificSlotForced(mixSlotDef) && !PunchyTuningConfig.shouldAutoUseSpecificSlot(mixSlotDef)) {
            String string2 = PunchyTuningConfig.resolveInheritedKindOption(mixSlotDef);
            return string2 == null ? "punchy_default" : string2;
        }
        return PunchyTuningConfig.resolveSelectedOption(mixSlotDef);
    }

    public static boolean setMixPackSelection(String string, String string2) {
        if (!PunchyTuningConfig.isMixThePacksEnabled()) {
            return false;
        }
        MixSlotDef mixSlotDef = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
        if (mixSlotDef == null) {
            return false;
        }
        String string3 = PunchyTuningConfig.normalizeMixOption(string2);
        if (string3 == null) {
            return false;
        }
        List<String> list = PunchyTuningConfig.getMixPackSlotOptionIds(string);
        if (!"punchy_default".equals(string3) && !list.contains(string3)) {
            return false;
        }
        String string4 = PunchyConfig.getAnimationPackSelection(mixSlotDef.slotId);
        PunchyConfig.setAnimationPackSelection(mixSlotDef.slotId, string3);
        PunchyConfig.clearAnimationPackTypeSelections(mixSlotDef.slotId);
        if (mixSlotDef.type == MixSlotType.SPECIFIC) {
            boolean bl = !"punchy_default".equals(string3);
            PunchyConfig.setMixSpecificForceOverride(mixSlotDef.slotId, bl);
        }
        PunchyTuningConfig.persistMixSelectionsToActiveProfile();
        return true;
    }

    public static List<String> getMixPackSlotEntryTypes(String string) {
        MixSlotDef mixSlotDef = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
        if (mixSlotDef == null) {
            return List.of();
        }
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        MixSlotPayload mixSlotPayload = PunchyTuningConfig.baselinePayloadForSlot(mixSlotDef);
        PunchyTuningConfig.collectEntryTypes(linkedHashSet, PunchyTuningConfig.payloadCustomAnimation(mixSlotPayload));
        PunchyTuningConfig.collectEntryTypes(linkedHashSet, PunchyTuningConfig.payloadCustomItemAnimations(mixSlotPayload));
        for (MixSlotPayload mixSlotPayload2 : mixSlotDef.optionPayloads.values()) {
            PunchyTuningConfig.collectEntryTypes(linkedHashSet, PunchyTuningConfig.payloadCustomAnimation(mixSlotPayload2));
            PunchyTuningConfig.collectEntryTypes(linkedHashSet, PunchyTuningConfig.payloadCustomItemAnimations(mixSlotPayload2));
        }
        return new ArrayList<String>(linkedHashSet);
    }

    public static List<String> getMixPackExpandableEntryTypes(String string) {
        List<String> list = PunchyTuningConfig.getMixPackSlotEntryTypes(string);
        if (list.isEmpty()) {
            return List.of();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string2 : list) {
            if (PunchyTuningConfig.getMixPackSlotEntryTypeOptionIds(string, string2).size() <= 1) continue;
            arrayList.add(string2);
        }
        return arrayList;
    }

    public static List<String> getMixPackSlotEntryTypeOptionIds(String string, String string2) {
        MixSlotDef mixSlotDef = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
        String string3 = PunchyTuningConfig.normalizeEntryType(string2);
        if (mixSlotDef != null && string3 != null) {
            Set<String> set = PunchyTuningConfig.getActiveMixPackOptionIds();
            LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
            linkedHashSet.add("punchy_default");
            for (String string4 : mixSlotDef.optionOrder) {
                if (!set.contains(string4) || !PunchyTuningConfig.optionSupportsType(mixSlotDef, string4, string3)) continue;
                linkedHashSet.add(string4);
            }
            return new ArrayList<String>(linkedHashSet);
        }
        return List.of();
    }

    public static String getMixPackSelectedEntryTypeOption(String string, String string2) {
        MixSlotDef mixSlotDef = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
        String string3 = PunchyTuningConfig.normalizeEntryType(string2);
        if (mixSlotDef != null && string3 != null) {
            String string4 = PunchyTuningConfig.getMixPackSelectedOption(string);
            return PunchyTuningConfig.resolveSelectedTypeOption(mixSlotDef, string3, string4);
        }
        return "punchy_default";
    }

    public static boolean hasMixPackEntryTypeOverride(String string, String string2) {
        MixSlotDef mixSlotDef = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
        String string3 = PunchyTuningConfig.normalizeEntryType(string2);
        if (mixSlotDef != null && string3 != null) {
            String string4 = PunchyConfig.getAnimationPackTypeSelection(mixSlotDef.slotId, string3);
            return string4 != null && !string4.isBlank();
        }
        return false;
    }

    public static boolean setMixPackEntryTypeSelection(String string, String string2, String string3) {
        MixSlotDef mixSlotDef = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
        String string4 = PunchyTuningConfig.normalizeEntryType(string2);
        if (mixSlotDef != null && string4 != null) {
            String string5 = PunchyTuningConfig.normalizeMixOption(string3);
            if (string5 == null) {
                return false;
            }
            List<String> list = PunchyTuningConfig.getMixPackSlotEntryTypeOptionIds(string, string4);
            String string6 = PunchyTuningConfig.getMixPackSelectedOption(string);
            if (list.contains(string5) || string6 != null && !string6.isBlank() && string6.equals(string5)) {
                String string7 = PunchyConfig.getAnimationPackTypeSelection(mixSlotDef.slotId, string4);
                if (string5.equals(string6)) {
                    PunchyConfig.setAnimationPackTypeSelection(mixSlotDef.slotId, string4, null);
                } else if ("punchy_default".equals(string5)) {
                    PunchyConfig.setAnimationPackTypeSelection(mixSlotDef.slotId, string4, null);
                } else {
                    PunchyConfig.setAnimationPackTypeSelection(mixSlotDef.slotId, string4, string5);
                }
                PunchyTuningConfig.persistMixSelectionsToActiveProfile();
                String string8 = PunchyConfig.getAnimationPackTypeSelection(mixSlotDef.slotId, string4);
                return true;
            }
            return false;
        }
        return false;
    }

    public static String getMixPreferredOptionForStack(class_1799 class_17992) {
        MixSlotDef mixSlotDef = PunchyTuningConfig.resolveMixSlotForStack(class_17992);
        return mixSlotDef == null ? "punchy_default" : PunchyTuningConfig.resolveSelectedOption(mixSlotDef);
    }

    public static String getGlobalMixPriorityOptionId() {
        for (String string : PunchyTuningConfig.getSelectedResourcePackIdsHighToLow()) {
            String string2 = PunchyTuningConfig.normalizeMixOption(string);
            if (string2 == null || string2.isBlank() || "punchy_default".equals(string2)) continue;
            return string2;
        }
        return "punchy_default";
    }

    public static String getMixPreferredOptionForType(class_1799 class_17992, String string) {
        MixSlotDef mixSlotDef = PunchyTuningConfig.resolveMixSlotForStack(class_17992);
        String string2 = PunchyTuningConfig.normalizeEntryType(string);
        return mixSlotDef != null && string2 != null ? PunchyTuningConfig.resolveSelectedTypeOption(mixSlotDef, string2, PunchyTuningConfig.resolveSelectedOption(mixSlotDef)) : "punchy_default";
    }

    public static String getMixPreferredOptionForEntry(class_1799 class_17992, CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry != null && customAnimationEntry.mix_pack_option != null && !customAnimationEntry.mix_pack_option.isBlank()) {
            String normalizedOption = PunchyTuningConfig.normalizeMixOption(customAnimationEntry.mix_pack_option);
            if (normalizedOption != null && !normalizedOption.isBlank()) {
                return normalizedOption;
            }
        }
        MixSlotDef slot = PunchyTuningConfig.resolveMixSlotForStack(class_17992);
        return slot == null ? "punchy_default" : PunchyTuningConfig.resolveSelectedOption(slot);
    }

    private static MixSlotDef resolveMixSlotForStack(class_1799 class_17992) {
        if (MIX_CATALOG == null || PunchyTuningConfig.MIX_CATALOG.slots.isEmpty()) {
            return null;
        }
        if (class_17992 != null && !class_17992.method_7960()) {
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 == null) {
                return null;
            }
            String string = "specific:" + String.valueOf(class_29602);
            MixSlotDef mixSlotDef = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
            if (mixSlotDef == null || !PunchyTuningConfig.isSpecificSlotForced(mixSlotDef) && !PunchyTuningConfig.shouldAutoUseSpecificSlot(mixSlotDef)) {
                VanillaFirstPersonItemLayer.ToolKind toolKind = PunchyTuningConfig.resolveMixKindForStack(class_17992);
                if (toolKind == null) {
                    return null;
                }
                String string2 = "kind:" + toolKind.name().toLowerCase(Locale.ROOT);
                return PunchyTuningConfig.MIX_CATALOG.slots.get(string2);
            }
            return mixSlotDef;
        }
        return PunchyTuningConfig.MIX_CATALOG.slots.get("kind:empty_hand");
    }

    public static void resetMixPackSelectionsToResourcepackOrder() {
        PunchyConfig.clearAnimationPackSelections();
        PunchyTuningConfig.persistMixSelectionsToActiveProfile();
    }

    public static void clearGroupsRegistry() {
        REGISTRY_CUSTOM_GROUPS.clear();
    }

    public static boolean matchesItemList(class_1799 class_17992, List<String> list) {
        if (class_17992 != null && list != null && !list.isEmpty()) {
            String string;
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            String string2 = string = class_29602 == null ? null : class_29602.toString();
            if (string != null && !string.isBlank()) {
                for (String string3 : list) {
                    String string4;
                    if (string3 == null || (string4 = string3.trim()).isEmpty()) continue;
                    List<String> list2 = PunchyTuningConfig.resolveCustomGroupItems(string4);
                    if (list2 != null && list2.contains(string)) {
                        return true;
                    }
                    class_2960 class_29603 = class_2960.method_12829((String)string4);
                    if (class_29603 == null || !string.equals(class_29603.toString())) continue;
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private static List<String> resolveCustomGroupItems(String string) {
        if (string == null) {
            return null;
        }
        List<String> list = REGISTRY_CUSTOM_GROUPS.get(string);
        if (list != null) {
            return list;
        }
        for (Map.Entry<String, List<String>> entry : REGISTRY_CUSTOM_GROUPS.entrySet()) {
            if (entry.getKey() == null || !entry.getKey().equalsIgnoreCase(string)) continue;
            return entry.getValue();
        }
        return null;
    }

    private static void applyItemLevelFishingRodFlags(List<CustomAnimationEntry> list, Boolean bl, Boolean bl2, Boolean bl3) {
        if (list != null && !list.isEmpty()) {
            for (CustomAnimationEntry customAnimationEntry : list) {
                String string;
                if (customAnimationEntry == null || customAnimationEntry.type == null || !"fishingrod".equals(string = customAnimationEntry.type.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "").replace(" ", ""))) continue;
                if (customAnimationEntry.disable_walk_on_water == null && bl != null) {
                    customAnimationEntry.disable_walk_on_water = bl;
                }
                if (customAnimationEntry.disable_dry_out == null && bl2 != null) {
                    customAnimationEntry.disable_dry_out = bl2;
                }
                if (customAnimationEntry.disable_swim_animation != null || bl3 == null) continue;
                customAnimationEntry.disable_swim_animation = bl3;
            }
        }
    }

    private static void applyGroupRuntimeFlags(VanillaFirstPersonItemLayer.ToolKind toolKind2, Boolean bl, Boolean bl2, Boolean bl3) {
        if (toolKind2 != null && (bl != null || bl2 != null || bl3 != null)) {
            ItemRuntimeFlags itemRuntimeFlags = GROUP_RUNTIME_FLAGS.computeIfAbsent(toolKind2, toolKind -> new ItemRuntimeFlags());
            if (bl != null) {
                itemRuntimeFlags.disableWalkOnWater = bl;
            }
            if (bl2 != null) {
                itemRuntimeFlags.disableDryOut = bl2;
            }
            if (bl3 != null) {
                itemRuntimeFlags.disableSwiming = bl3;
            }
        }
    }

    private static void applySpecificRuntimeFlags(class_2960 class_29603, Boolean bl, Boolean bl2, Boolean bl3) {
        if (class_29603 != null && (bl != null || bl2 != null || bl3 != null)) {
            ItemRuntimeFlags itemRuntimeFlags = SPECIFIC_RUNTIME_FLAGS.computeIfAbsent(class_29603, class_29602 -> new ItemRuntimeFlags());
            if (bl != null) {
                itemRuntimeFlags.disableWalkOnWater = bl;
            }
            if (bl2 != null) {
                itemRuntimeFlags.disableDryOut = bl2;
            }
            if (bl3 != null) {
                itemRuntimeFlags.disableSwiming = bl3;
            }
        }
    }

    public static boolean isWalkOnWaterDisabledForStack(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            Object object;
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 != null && (object = SPECIFIC_RUNTIME_FLAGS.get(class_29602)) != null && Boolean.TRUE.equals(((ItemRuntimeFlags)object).disableWalkOnWater)) {
                return true;
            }
            object = PunchyTuningConfig.resolveMixKindForStack(class_17992);
            if (object == null) {
                return false;
            }
            ItemRuntimeFlags itemRuntimeFlags = GROUP_RUNTIME_FLAGS.get(object);
            return itemRuntimeFlags != null && Boolean.TRUE.equals(itemRuntimeFlags.disableWalkOnWater);
        }
        return false;
    }

    public static boolean isDryOutDisabledForStack(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            Object object;
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 != null && (object = SPECIFIC_RUNTIME_FLAGS.get(class_29602)) != null && Boolean.TRUE.equals(((ItemRuntimeFlags)object).disableDryOut)) {
                return true;
            }
            object = PunchyTuningConfig.resolveMixKindForStack(class_17992);
            if (object == null) {
                return false;
            }
            ItemRuntimeFlags itemRuntimeFlags = GROUP_RUNTIME_FLAGS.get(object);
            return itemRuntimeFlags != null && Boolean.TRUE.equals(itemRuntimeFlags.disableDryOut);
        }
        return false;
    }

    public static boolean isSwimingDisabledForStack(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            Object object;
            class_2960 class_29602 = class_7923.field_41178.method_10221(class_17992.method_7909());
            if (class_29602 != null && (object = SPECIFIC_RUNTIME_FLAGS.get(class_29602)) != null && Boolean.TRUE.equals(((ItemRuntimeFlags)object).disableSwiming)) {
                return true;
            }
            object = PunchyTuningConfig.resolveMixKindForStack(class_17992);
            if (object == null) {
                return false;
            }
            ItemRuntimeFlags itemRuntimeFlags = GROUP_RUNTIME_FLAGS.get(object);
            return itemRuntimeFlags != null && Boolean.TRUE.equals(itemRuntimeFlags.disableSwiming);
        }
        return false;
    }

    private static List<String> readCompatParticleEffects(SpecificEntry specificEntry) {
        if (specificEntry == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        PunchyTuningConfig.appendCompatParticleEffects(arrayList, specificEntry.particleEmitters);
        PunchyTuningConfig.appendCompatParticleEffects(arrayList, specificEntry.particle);
        PunchyTuningConfig.appendCompatParticleEffects(arrayList, specificEntry.particles);
        PunchyTuningConfig.appendCompatParticleEffects(arrayList, specificEntry.particleEffects);
        PunchyTuningConfig.appendCompatParticleEffects(arrayList, specificEntry.particleEmission);
        return arrayList.isEmpty() ? null : arrayList;
    }

    private static List<String> readCompatParticleEffects(GroupEntry groupEntry) {
        if (groupEntry == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        PunchyTuningConfig.appendCompatParticleEffects(arrayList, groupEntry.particleEmitters);
        PunchyTuningConfig.appendCompatParticleEffects(arrayList, groupEntry.particle);
        PunchyTuningConfig.appendCompatParticleEffects(arrayList, groupEntry.particles);
        PunchyTuningConfig.appendCompatParticleEffects(arrayList, groupEntry.particleEffects);
        PunchyTuningConfig.appendCompatParticleEffects(arrayList, groupEntry.particleEmission);
        return arrayList.isEmpty() ? null : arrayList;
    }

    private static void appendCompatParticleEffects(List<String> list, JsonElement jsonElement) {
        if (list != null && jsonElement != null && !jsonElement.isJsonNull()) {
            String string;
            if (jsonElement.isJsonArray()) {
                for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                    PunchyTuningConfig.appendCompatParticleEffects(list, jsonElement2);
                }
            } else if (jsonElement.isJsonObject()) {
                String string2 = PunchyTuningConfig.compatParticleObjectToScript(jsonElement.getAsJsonObject());
                if (string2 != null && !string2.isBlank()) {
                    list.add(string2);
                }
            } else if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString() && (string = PunchyTuningConfig.normalizeCompatParticleEffect(jsonElement.getAsString())) != null && !string.isBlank()) {
                list.add(string);
            }
        }
    }

    private static String normalizeCompatParticleEffect(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim();
            if (string2.startsWith("particle(") && string2.endsWith(")")) {
                return string2;
            }
            if (string2.contains("=")) {
                return "particle(" + string2 + ")";
            }
            class_2960 class_29602 = class_2960.method_12829((String)string2);
            return class_29602 != null ? "particle(type=" + String.valueOf(class_29602) + ")" : null;
        }
        return null;
    }

    private static String compatParticleObjectToScript(JsonObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        if (jsonObject.has("enabled") && jsonObject.get("enabled").isJsonPrimitive() && !PunchyTuningConfig.readCompatBoolean(jsonObject.get("enabled"), true)) {
            return null;
        }
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Map.Entry entry : jsonObject.entrySet()) {
            String string = (String)entry.getKey();
            if (string == null || string.isBlank() || "enabled".equalsIgnoreCase(string) || PunchyTuningConfig.isIgnoredCompatParticleRenderHint(string)) continue;
            if ("triggers".equalsIgnoreCase(string) && entry.getValue() != null && !((JsonElement)entry.getValue()).isJsonNull()) {
                PunchyTuningConfig.appendCompatTriggerTokens(stringJoiner, (JsonElement)entry.getValue());
                continue;
            }
            if ("on_animation".equalsIgnoreCase(string) && entry.getValue() != null && ((JsonElement)entry.getValue()).isJsonObject()) {
                PunchyTuningConfig.appendCompatOnAnimationTokens(stringJoiner, ((JsonElement)entry.getValue()).getAsJsonObject());
                continue;
            }
            String string2 = PunchyTuningConfig.compatParticleValueToToken((JsonElement)entry.getValue());
            if (string2 == null || string2.isBlank()) continue;
            stringJoiner.add(string.trim() + "=" + string2);
        }
        String string = stringJoiner.toString();
        return string.isBlank() ? null : "particle(" + (String)string + ")";
    }

    private static boolean isIgnoredCompatParticleRenderHint(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            return "emissive_strength".equals(string2) || "emission_strength".equals(string2) || "emissive_blur".equals(string2) || "emission_blur".equals(string2) || "emissive_clamp".equals(string2) || "emission_clamp".equals(string2);
        }
        return false;
    }

    private static String compatParticleValueToToken(JsonElement jsonElement) {
        Object object;
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        }
        if (!jsonElement.isJsonArray()) {
            JsonObject jsonObject;
            if (jsonElement.isJsonObject() && (jsonObject = jsonElement.getAsJsonObject()).has("x") && jsonObject.has("y") && jsonObject.has("z") && jsonObject.get("x").isJsonPrimitive() && jsonObject.get("y").isJsonPrimitive() && jsonObject.get("z").isJsonPrimitive()) {
                return jsonObject.get("x").getAsString() + ":" + jsonObject.get("y").getAsString() + ":" + jsonObject.get("z").getAsString();
            }
            return null;
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        boolean bl = true;
        for (JsonElement element : jsonArray) {
            if (element != null && element.isJsonPrimitive()) continue;
            bl = false;
            break;
        }
        if (!bl) {
            return null;
        }
        if (jsonArray.size() == 3) {
            object = new StringJoiner(":");
            for (JsonElement jsonElement2 : jsonArray) {
                ((StringJoiner)object).add(jsonElement2.getAsString());
            }
            return ((StringJoiner)object).toString();
        }
        object = new StringJoiner("|");
        for (JsonElement jsonElement3 : jsonArray) {
            ((StringJoiner)object).add(jsonElement3.getAsString());
        }
        return ((StringJoiner)object).toString();
    }

    private static void appendCompatOnAnimationTokens(StringJoiner stringJoiner, JsonObject jsonObject) {
        if (stringJoiner != null && jsonObject != null) {
            String string;
            JsonElement jsonElement = jsonObject.get("name");
            JsonElement jsonElement2 = jsonObject.get("timecode");
            if (jsonElement != null && jsonElement.isJsonPrimitive() && (string = jsonElement.getAsString()) != null && !string.isBlank()) {
                stringJoiner.add("on_animation_name=" + string.trim());
            }
            if (jsonElement2 != null && jsonElement2.isJsonPrimitive()) {
                stringJoiner.add("on_animation_timecode=" + jsonElement2.getAsString());
            }
        }
    }

    private static void appendCompatTriggerTokens(StringJoiner stringJoiner, JsonElement jsonElement) {
        if (stringJoiner != null && jsonElement != null && !jsonElement.isJsonNull()) {
            if (jsonElement.isJsonObject()) {
                PunchyTuningConfig.appendCompatTriggerTokens(stringJoiner, jsonElement.getAsJsonObject());
            } else if (jsonElement.isJsonArray()) {
                PunchyTuningConfig.appendCompatTriggerList(stringJoiner, jsonElement.getAsJsonArray());
            } else if (jsonElement.isJsonPrimitive()) {
                PunchyTuningConfig.appendCompatNamedTrigger(stringJoiner, jsonElement.getAsString());
            }
        }
    }

    private static void appendCompatTriggerTokens(StringJoiner stringJoiner, JsonObject jsonObject) {
        if (stringJoiner != null && jsonObject != null) {
            Object object;
            PunchyTuningConfig.appendCompatTrueTrigger(stringJoiner, jsonObject, "trigger_jump", "jump", "trigger_jump", "triggerJump");
            PunchyTuningConfig.appendCompatTrueTrigger(stringJoiner, jsonObject, "trigger_landing", "landing", "trigger_landing", "triggerLanding");
            PunchyTuningConfig.appendCompatTrueTrigger(stringJoiner, jsonObject, "trigger_walking", "walking", "walkgin", "trigger_walking", "triggerWalking");
            PunchyTuningConfig.appendCompatTrueTrigger(stringJoiner, jsonObject, "trigger_sprinting", "sprinting", "trigger_sprinting", "triggerSprinting");
            PunchyTuningConfig.appendCompatTrueTrigger(stringJoiner, jsonObject, "trigger_swimming", "swimming", "swiming", "trigger_swimming", "triggerSwimming");
            PunchyTuningConfig.appendCompatTrueTrigger(stringJoiner, jsonObject, "trigger_attacking", "attacking", "attack", "trigger_attacking", "triggerAttacking");
            PunchyTuningConfig.appendCompatTrueTrigger(stringJoiner, jsonObject, "trigger_use_item", "use_item", "useitem", "useItem", "trigger_use_item", "triggerUseItem", "trigger_useitem", "triggerUseitem");
            PunchyTuningConfig.appendCompatTrueTrigger(stringJoiner, jsonObject, "trigger_mining", "mining", "trigger_mining", "triggerMining");
            JsonElement jsonElement = jsonObject.get("falling");
            if (jsonElement != null && !jsonElement.isJsonNull()) {
                if (jsonElement.isJsonPrimitive()) {
                    if (PunchyTuningConfig.readCompatBoolean(jsonElement, false)) {
                        stringJoiner.add("trigger_falling=true");
                    }
                } else if (jsonElement.isJsonObject()) {
                    stringJoiner.add("trigger_falling=true");
                    object = PunchyTuningConfig.readCompatFallingMinBlocks(jsonElement.getAsJsonObject());
                    if (object != null) {
                        stringJoiner.add("trigger_falling_min_blocks=" + (Float)object);
                    }
                }
            }
            List<String> biomeNames = PunchyTuningConfig.readCompatTriggerBiomeNames(jsonObject.get("biome"));
            if (!biomeNames.isEmpty()) {
                stringJoiner.add("trigger_biomes=" + String.join("|", biomeNames));
            }
        }
    }

    private static void appendCompatTriggerList(StringJoiner stringJoiner, JsonArray jsonArray) {
        if (stringJoiner != null && jsonArray != null) {
            stringJoiner.add("trigger_mode=any");
            for (JsonElement jsonElement : jsonArray) {
                if (jsonElement == null || jsonElement.isJsonNull() || !jsonElement.isJsonPrimitive()) continue;
                PunchyTuningConfig.appendCompatNamedTrigger(stringJoiner, jsonElement.getAsString());
            }
        }
    }

    private static void appendCompatNamedTrigger(StringJoiner stringJoiner, String string) {
        if (stringJoiner != null && string != null && !string.isBlank()) {
            String string2;
            switch (string2 = string.trim().toLowerCase(Locale.ROOT)) {
                case "jump": 
                case "trigger_jump": 
                case "triggerjump": {
                    stringJoiner.add("trigger_jump=true");
                    break;
                }
                case "landing": 
                case "trigger_landing": 
                case "triggerlanding": {
                    stringJoiner.add("trigger_landing=true");
                    break;
                }
                case "walking": 
                case "walkgin": 
                case "trigger_walking": 
                case "triggerwalking": {
                    stringJoiner.add("trigger_walking=true");
                    break;
                }
                case "sprinting": 
                case "trigger_sprinting": 
                case "triggersprinting": {
                    stringJoiner.add("trigger_sprinting=true");
                    break;
                }
                case "swimming": 
                case "swiming": 
                case "trigger_swimming": 
                case "triggerswimming": {
                    stringJoiner.add("trigger_swimming=true");
                    break;
                }
                case "attacking": 
                case "attack": 
                case "trigger_attacking": 
                case "triggerattacking": {
                    stringJoiner.add("trigger_attacking=true");
                    break;
                }
                case "use_item": 
                case "useitem": 
                case "trigger_use_item": 
                case "triggeruseitem": {
                    stringJoiner.add("trigger_use_item=true");
                    break;
                }
                case "mining": 
                case "trigger_mining": 
                case "triggermining": {
                    stringJoiner.add("trigger_mining=true");
                    break;
                }
                case "falling": 
                case "trigger_falling": 
                case "triggerfalling": {
                    stringJoiner.add("trigger_falling=true");
                }
            }
        }
    }

    private static void appendCompatTrueTrigger(StringJoiner stringJoiner, JsonObject jsonObject, String string, String ... stringArray) {
        if (stringJoiner != null && jsonObject != null && string != null && stringArray != null) {
            for (String string2 : stringArray) {
                if (string2 == null || string2.isBlank() || !PunchyTuningConfig.readCompatBoolean(jsonObject.get(string2), false)) continue;
                stringJoiner.add(string + "=true");
                return;
            }
        }
    }

    private static boolean readCompatBoolean(JsonElement jsonElement, boolean bl) {
        if (jsonElement != null && !jsonElement.isJsonNull() && jsonElement.isJsonPrimitive()) {
            try {
                if (jsonElement.getAsJsonPrimitive().isBoolean()) {
                    return jsonElement.getAsBoolean();
                }
                if (jsonElement.getAsJsonPrimitive().isNumber()) {
                    return jsonElement.getAsInt() != 0;
                }
                String string = jsonElement.getAsString();
                if (string == null) {
                    return bl;
                }
                String string2 = string.trim().toLowerCase(Locale.ROOT);
                if ("true".equals(string2) || "1".equals(string2) || "yes".equals(string2)) {
                    return true;
                }
                if ("false".equals(string2) || "0".equals(string2) || "no".equals(string2)) {
                    return false;
                }
            }
            catch (Exception exception) {
            }
            return bl;
        }
        return bl;
    }

    private static Float readCompatFallingMinBlocks(JsonObject jsonObject) {
        String[] stringArray;
        if (jsonObject == null) {
            return null;
        }
        for (String string : stringArray = new String[]{"min_blocks", "minBlocks", "min_height", "minHeight", "min"}) {
            JsonElement jsonElement = jsonObject.get(string);
            if (jsonElement == null || !jsonElement.isJsonPrimitive() || !jsonElement.getAsJsonPrimitive().isNumber()) continue;
            float f = jsonElement.getAsFloat();
            return Float.valueOf(Math.max(0.0f, f));
        }
        return null;
    }

    private static List<String> readCompatTriggerBiomeNames(JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonElement jsonElement2;
            JsonElement jsonElement3;
            ArrayList<String> arrayList = new ArrayList<String>();
            if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
                String string = jsonElement.getAsString();
                if (string != null && !string.isBlank()) {
                    arrayList.add(string.trim());
                }
                return arrayList;
            }
            if (jsonElement.isJsonArray()) {
                for (JsonElement jsonElement4 : jsonElement.getAsJsonArray()) {
                    String string;
                    if (jsonElement4 == null || !jsonElement4.isJsonPrimitive() || !jsonElement4.getAsJsonPrimitive().isString() || (string = jsonElement4.getAsString()) == null || string.isBlank()) continue;
                    arrayList.add(string.trim());
                }
                return arrayList;
            }
            if (!jsonElement.isJsonObject()) {
                return arrayList;
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement jsonElement5 = jsonElement3 = jsonObject.has("names") ? jsonObject.get("names") : jsonObject.get("biomes");
            if (jsonElement3 != null) {
                arrayList.addAll(PunchyTuningConfig.readCompatTriggerBiomeNames(jsonElement3));
                return arrayList;
            }
            JsonElement jsonElement6 = jsonElement2 = jsonObject.has("name") ? jsonObject.get("name") : jsonObject.get("biome");
            if (jsonElement2 != null) {
                arrayList.addAll(PunchyTuningConfig.readCompatTriggerBiomeNames(jsonElement2));
            }
            return arrayList;
        }
        return List.of();
    }

    private static File getFile() {
        Path path = PunchyTuningConfig.getConfigDirectory();
        try {
            Files.createDirectories(path, new FileAttribute[0]);
        }
        catch (Exception exception) {
        }
        return path.resolve("punchy").resolve("punchy_tuning.json").toFile();
    }

    public static void loadAndApply() {
        PunchyTuningConfig.loadAndApply(class_310.method_1551() != null ? class_310.method_1551().method_1478() : null);
    }

    public static void loadAndApply(class_3300 class_33002) {
        try {
            PunchyTuningConfig.invalidateSelectedResourcePackCache();
            PunchyTuningConfig.clearGroupsRegistry();
            PunchyTuningConfig.resetAllTunings();
            DEFAULTS_RESOURCE_STATE = null;
            JsonObject jsonObject = null;
            Path path = TuningProfileManager.getActiveProfileFilePath();
            if (path != null && Files.isRegularFile(path, new LinkOption[0])) {
                try (FileReader reader = new FileReader(path.toFile())) {
                    JsonElement root = GSON.fromJson((Reader)reader, JsonElement.class);
                    if (root != null && root.isJsonObject()) {
                        jsonObject = root.getAsJsonObject();
                    }
                }
            }
            Data baseDefaults = PunchyTuningConfig.buildBaseDefaults(class_33002);
            DEFAULTS_RESOURCE_STATE = PunchyTuningConfig.copyData(baseDefaults);
            if (baseDefaults != null) {
                if (baseDefaults.customGroups != null) {
                    for (CustomGroupDefinition group : baseDefaults.customGroups) {
                        if (group == null || group.name == null || group.items == null) continue;
                        REGISTRY_CUSTOM_GROUPS.put(group.name, group.items);
                    }
                }
                PunchyTuningConfig.applyData(PunchyTuningConfig.copyData(baseDefaults));
            }
            Data activeData = PunchyTuningConfig.parseData(jsonObject);
            if (activeData != null) {
                if (activeData.customGroups != null) {
                    for (CustomGroupDefinition customGroupDefinition : activeData.customGroups) {
                        if (customGroupDefinition == null || customGroupDefinition.name == null || customGroupDefinition.items == null) continue;
                        REGISTRY_CUSTOM_GROUPS.put(customGroupDefinition.name, customGroupDefinition.items);
                    }
                }
                PunchyTuningConfig.applyData(PunchyTuningConfig.copyData(activeData));
                PunchyTuningConfig.saveData(activeData);
            }
            Set<class_2960> compatResourceIds = PunchyTuningConfig.discoverCompatResourceIds(class_33002).keySet();
            MIX_COMPAT_OPTION_IDS = PunchyTuningConfig.resolveMixCompatOptionIds(class_33002, compatResourceIds);
            MIX_PUNCHY_OPTION_IDS = PunchyTuningConfig.discoverPunchyOptionIds(class_33002, compatResourceIds);
            MIX_CATALOG = PunchyTuningConfig.buildMixCatalog(class_33002, PunchyTuningConfig.discoverCompatProfilesFromResourcePacks(class_33002));
            PunchyTuningConfig.applyMixSelections();
            PunchyTuningConfig.reapplyActiveProfileSpecificTransforms(activeData);
            PunchyTuningConfig.rememberBaseline();
            INITIAL_STATE = PunchyTuningConfig.captureCurrent();
        }
        catch (Exception exception) {
        }
    }

    public static void reloadActiveProfileFast(class_3300 class_33002) {
        try {
            Object object;
            Data data;
            PunchyTuningConfig.invalidateSelectedResourcePackCache();
            if (DEFAULTS_RESOURCE_STATE == null) {
                PunchyTuningConfig.loadAndApply(class_33002);
                return;
            }
            PunchyTuningConfig.clearGroupsRegistry();
            PunchyTuningConfig.resetAllTunings();
            Data data2 = PunchyTuningConfig.buildBaseDefaults(class_33002);
            if (data2 != null) {
                DEFAULTS_RESOURCE_STATE = PunchyTuningConfig.copyData(data2);
            }
            if ((data = PunchyTuningConfig.copyData(DEFAULTS_RESOURCE_STATE)) != null) {
                if (data.customGroups != null) {
                    for (CustomGroupDefinition object22 : data.customGroups) {
                        if (object22 == null || object22.name == null || object22.items == null) continue;
                        REGISTRY_CUSTOM_GROUPS.put(object22.name, object22.items);
                    }
                }
                PunchyTuningConfig.applyData(PunchyTuningConfig.copyData(data));
            }
            JsonObject jsonObject = null;
            Path path = TuningProfileManager.getActiveProfileFilePath();
            if (path != null && Files.isRegularFile(path, new LinkOption[0])) {
                object = new FileReader(path.toFile());
                try {
                    JsonElement jsonElement = (JsonElement)GSON.fromJson((Reader)object, JsonElement.class);
                    if (jsonElement != null && jsonElement.isJsonObject()) {
                        jsonObject = jsonElement.getAsJsonObject();
                    }
                }
                finally {
                    ((InputStreamReader)object).close();
                }
            }
            if ((object = PunchyTuningConfig.parseData(jsonObject)) != null) {
                if (((Data)object).customGroups != null) {
                    for (CustomGroupDefinition customGroupDefinition : ((Data)object).customGroups) {
                        if (customGroupDefinition == null || customGroupDefinition.name == null || customGroupDefinition.items == null) continue;
                        REGISTRY_CUSTOM_GROUPS.put(customGroupDefinition.name, customGroupDefinition.items);
                    }
                }
                PunchyTuningConfig.applyData(PunchyTuningConfig.copyData((Data)object));
                PunchyTuningConfig.saveData((Data)object);
            }
            PunchyTuningConfig.applyMixSelections();
            PunchyTuningConfig.reapplyActiveProfileSpecificTransforms((Data)object);
            PunchyTuningConfig.rememberBaseline();
            INITIAL_STATE = PunchyTuningConfig.captureCurrent();
        }
        catch (Exception exception) {
        }
    }

    private static JsonObject loadDefaultsJsonFromResource(class_3300 class_33002) {
        if (class_33002 == null) {
            return null;
        }
        try {
            class_3298 class_32982 = class_33002.getResourceOrThrow(DEFAULTS_ID);
            try (InputStream inputStream = class_32982.method_14482();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);){
                JsonElement jsonElement = (JsonElement)GSON.fromJson((Reader)inputStreamReader, JsonElement.class);
                if (jsonElement == null || !jsonElement.isJsonObject()) {
                    JsonObject jsonObject = null;
                    return jsonObject;
                }
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                return jsonObject;
            }
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Data buildBaseDefaults(class_3300 class_33002) {
        Data data = PunchyTuningConfig.parseData(PunchyTuningConfig.loadDefaultsJsonFromResource(class_33002));
        Data data2 = PunchyTuningConfig.buildMergedCompatOverlay(class_33002);
        return PunchyTuningConfig.mergeData(PunchyTuningConfig.copyData(data), PunchyTuningConfig.copyData(data2));
    }

    private static boolean isPunchyDefaultPackId(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            return "punchy".equals(string2) || string2.startsWith("punchy:") || string2.endsWith(":punchy") || string2.endsWith("/punchy") || string2.endsWith("\\punchy") || "builtin/punchy".equals(string2) || "builtin\\punchy".equals(string2);
        }
        return false;
    }

    private static Data parseData(JsonObject jsonObject) {
        return jsonObject == null ? null : (Data)GSON.fromJson((JsonElement)jsonObject, Data.class);
    }

    private static JsonObject deepMergeJson(JsonObject jsonObject, JsonObject jsonObject2) {
        if (jsonObject == null) {
            return jsonObject2 == null ? null : jsonObject2.deepCopy();
        }
        JsonObject jsonObject3 = jsonObject.deepCopy();
        if (jsonObject2 == null) {
            return jsonObject3;
        }
        for (Map.Entry entry : jsonObject2.entrySet()) {
            JsonElement jsonElement = (JsonElement)entry.getValue();
            if (jsonElement != null && !jsonElement.isJsonNull()) {
                JsonElement jsonElement2 = jsonObject3.get((String)entry.getKey());
                if (jsonElement2 != null && jsonElement2.isJsonObject() && jsonElement.isJsonObject()) {
                    jsonObject3.add((String)entry.getKey(), (JsonElement)PunchyTuningConfig.deepMergeJson(jsonElement2.getAsJsonObject(), jsonElement.getAsJsonObject()));
                    continue;
                }
                jsonObject3.add((String)entry.getKey(), jsonElement.deepCopy());
                continue;
            }
            jsonObject3.add((String)entry.getKey(), (JsonElement)JsonNull.INSTANCE);
        }
        return jsonObject3;
    }

    private static PhysicsEntry resolvePhysicsEntry(JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            try {
                if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
                    String string = jsonElement.getAsString();
                    if (string != null && !string.isBlank()) {
                        PhysicsEntry physicsEntry = new PhysicsEntry();
                        physicsEntry.name = string;
                        return physicsEntry;
                    }
                    return null;
                }
                if (jsonElement.isJsonObject()) {
                    PhysicsEntry physicsEntry = (PhysicsEntry)GSON.fromJson(jsonElement, PhysicsEntry.class);
                    if (physicsEntry != null && physicsEntry.name != null && physicsEntry.name.isBlank()) {
                        physicsEntry.name = null;
                    }
                    return physicsEntry;
                }
                if (jsonElement.isJsonArray()) {
                    for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                        PhysicsEntry physicsEntry = PunchyTuningConfig.resolvePhysicsEntry(jsonElement2);
                        if (physicsEntry == null || physicsEntry.name == null || physicsEntry.name.isBlank()) continue;
                        return physicsEntry;
                    }
                }
            }
            catch (Exception exception) {
            }
            return null;
        }
        return null;
    }

    private static ToolTuning.PhysicsMode resolvePhysicsMode(PhysicsEntry physicsEntry) {
        if (physicsEntry != null && physicsEntry.name != null && !physicsEntry.name.isBlank()) {
            try {
                return ToolTuning.PhysicsMode.valueOf(physicsEntry.name.toUpperCase(Locale.ROOT));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        return null;
    }

    private static ToolTuning.PhysicsSettings resolvePhysicsSettings(PhysicsEntry physicsEntry) {
        if (physicsEntry == null) {
            return null;
        }
        ToolTuning.PhysicsSettings physicsSettings = new ToolTuning.PhysicsSettings();
        boolean bl = false;
        if (physicsEntry.limitDeg != null) {
            physicsSettings.limitDeg = physicsEntry.limitDeg;
            bl = true;
        }
        if (physicsEntry.limitDegUp != null) {
            physicsSettings.limitDegUp = physicsEntry.limitDegUp;
            bl = true;
        }
        if (physicsEntry.limitDegDown != null) {
            physicsSettings.limitDegDown = physicsEntry.limitDegDown;
            bl = true;
        }
        if (physicsEntry.force != null) {
            physicsSettings.force = physicsEntry.force;
            bl = true;
        }
        if (physicsEntry.speed != null) {
            physicsSettings.speed = physicsEntry.speed;
            bl = true;
        }
        if (physicsEntry.cameraSens != null) {
            physicsSettings.cameraSens = physicsEntry.cameraSens;
            bl = true;
        }
        if (physicsEntry.gravity != null) {
            physicsSettings.gravity = physicsEntry.gravity;
            bl = true;
        }
        if (physicsEntry.motionSens != null) {
            physicsSettings.motionSens = physicsEntry.motionSens;
            bl = true;
        }
        if (physicsEntry.invert != null) {
            physicsSettings.invert = physicsEntry.invert;
            bl = true;
        }
        if (physicsEntry.handSpace != null) {
            physicsSettings.handSpace = physicsEntry.handSpace;
            bl = true;
        }
        if (physicsEntry.enableCreaturePhysics != null) {
            physicsSettings.enableCreaturePhysics = physicsEntry.enableCreaturePhysics;
            bl = true;
        }
        return bl ? physicsSettings : null;
    }

    private static void applyData(Data data) {
        PunchyTuningConfig.applyData(data, true);
    }

    private static void applyData(Data data, boolean bl) {
        if (data == null) {
            return;
        }
        if (data.item == null) {
            data.item = new HashMap<>();
        }
        if (bl) {
            GROUP_RUNTIME_FLAGS.clear();
            SPECIFIC_RUNTIME_FLAGS.clear();
        }

        for (Map.Entry<String, GroupEntry> entry : data.item.entrySet()) {
            GroupEntry group = entry.getValue();
            List<String> groupItems = REGISTRY_CUSTOM_GROUPS.get(entry.getKey());
            if (group == null || groupItems == null) continue;
            PunchyTuningConfig.normalizeLegacyTransforms(group);
            PunchyTuningConfig.applyItemLevelFishingRodFlags(group.customAnimation, group.disableWalkOnWater, group.disableDryOut, group.disableSwiming);
            PhysicsEntry physicsEntry = PunchyTuningConfig.resolvePhysicsEntry(group.physics);
            ToolTuning.PhysicsMode physicsMode = PunchyTuningConfig.resolvePhysicsMode(physicsEntry);
            ToolTuning.PhysicsSettings physicsSettings = PunchyTuningConfig.resolvePhysicsSettings(physicsEntry);

            for (String itemName : groupItems) {
                class_2960 itemId = class_2960.method_12829(itemName);
                if (itemId == null) continue;
                class_1792 item = class_7923.field_41178.method_17966(itemId).orElse(class_1802.field_8162);
                if (item == class_1802.field_8162) continue;
                class_1799 stack = new class_1799((class_1935)item);
                VanillaFirstPersonItemLayer.ToolKind kind = ToolKindResolver.resolveItemKind(stack, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
                PunchyTuningConfig.applySpecificRuntimeFlags(itemId, group.disableWalkOnWater, group.disableDryOut, group.disableSwiming);
                PunchyTuningConfig.applyGroupRuntimeFlags(kind, group.disableWalkOnWater, group.disableDryOut, group.disableSwiming);

                Pose rightPose = group.transforms != null ? group.transforms.right : null;
                Pose leftPose = group.transforms != null ? group.transforms.left : null;
                ToolTuning.Profile rightProfile = rightPose != null ? rightPose.toTool() : null;
                ToolTuning.Profile leftProfile = leftPose != null ? leftPose.toTool() : null;
                PunchyTuningConfig.applyToolProfileDefaults(rightProfile, rightPose, group.disableSprintLateralTilt,
                        PunchyTuningConfig.resolveSideKeepArmTuning(group.keepArmTuning, group.keepArmTuningR),
                        group.keepTriggerArmTuning, physicsMode, physicsSettings);
                PunchyTuningConfig.applyToolProfileDefaults(leftProfile, leftPose, group.disableSprintLateralTilt,
                        PunchyTuningConfig.resolveSideKeepArmTuning(group.keepArmTuning, group.keepArmTuningL),
                        group.keepTriggerArmTuning, physicsMode, physicsSettings);
                ToolTuning.applySpecific(itemId, kind, null, group.customAnimation, group.customItemAnimations,
                        PunchyTuningConfig.readCompatParticleEffects(group), group.betterCombat, rightProfile, leftProfile);
                PunchyTuningConfig.applyMissingSpecificDefaults(itemId, kind, rightPose, leftPose, group.disableSprintLateralTilt,
                        group.keepArmTuning, group.keepArmTuningR, group.keepArmTuningL, group.keepTriggerArmTuning,
                        physicsMode, physicsSettings);

                if (group.armTransforms != null) {
                    PlayerArmModelLayer.ToolKind armKind = ToolKindResolver.resolveArmKind(stack, PlayerArmModelLayer.ToolKind.AXE);
                    ArmToolTuning.Profile rightArm = group.armTransforms.right != null
                            ? PunchyTuningConfig.deserializeSpecificArmProfileForRuntime(armKind, false, group.armTransforms.right.toArm()) : null;
                    ArmToolTuning.Profile leftArm = group.armTransforms.left != null
                            ? PunchyTuningConfig.deserializeSpecificArmProfileForRuntime(armKind, true, group.armTransforms.left.toArm()) : null;
                    ArmToolTuning.applySpecific(itemId, armKind, rightArm, leftArm);
                }
            }
        }

        SideSet globalItem = PunchyTuningConfig.resolveGlobalItem(data);
        if (globalItem != null) {
            if (globalItem.right != null) {
                ToolTuning.applyGlobal(false, globalItem.right.toTool());
            }
            if (globalItem.left != null) {
                ToolTuning.applyGlobal(true, globalItem.left.toTool());
            }
        }
        if (data.default_animations != null && !data.default_animations.isEmpty()) {
            ToolTuning.setDefaultBetterCombat(PunchyTuningConfig.extractDefaultBetterCombatFromDefaultAnimations(data.default_animations));
            ToolTuning.setDefaultAnimations(PunchyTuningConfig.stripBetterCombatFromDefaultAnimations(data.default_animations));
        }

        for (VanillaFirstPersonItemLayer.ToolKind kind : VanillaFirstPersonItemLayer.ToolKind.values()) {
            GroupEntry group = data.item.get(kind.name());
            if (group == null) continue;
            PunchyTuningConfig.normalizeLegacyTransforms(group);
            PunchyTuningConfig.applyGroupRuntimeFlags(kind, group.disableWalkOnWater, group.disableDryOut, group.disableSwiming);
            PunchyTuningConfig.applyItemLevelFishingRodFlags(group.customAnimation, group.disableWalkOnWater, group.disableDryOut, group.disableSwiming);
            if (group.customAnimation != null) {
                ToolTuning.applyGroupAnimation(kind, group.customAnimation);
            }
            if (group.customItemAnimations != null) {
                ToolTuning.applyGroupItemAnimation(kind, group.customItemAnimations);
            }
            List<String> particleEffects = PunchyTuningConfig.readCompatParticleEffects(group);
            if (particleEffects != null) {
                ToolTuning.applyGroupParticleEmitters(kind, particleEffects);
            }
            if (group.betterCombat != null) {
                ToolTuning.applyGroupBetterCombat(kind, group.betterCombat);
            }

            PhysicsEntry physicsEntry = PunchyTuningConfig.resolvePhysicsEntry(group.physics);
            ToolTuning.PhysicsMode physicsMode = PunchyTuningConfig.resolvePhysicsMode(physicsEntry);
            ToolTuning.PhysicsSettings physicsSettings = PunchyTuningConfig.resolvePhysicsSettings(physicsEntry);
            Boolean rightKeepArm = PunchyTuningConfig.resolveSideKeepArmTuning(group.keepArmTuning, group.keepArmTuningR);
            Boolean leftKeepArm = PunchyTuningConfig.resolveSideKeepArmTuning(group.keepArmTuning, group.keepArmTuningL);
            Pose rightPose = group.transforms != null ? group.transforms.right : null;
            Pose leftPose = group.transforms != null ? group.transforms.left : null;

            if (rightPose != null) {
                ToolTuning.Profile profile = rightPose.toTool();
                PunchyTuningConfig.applyToolProfileDefaults(profile, rightPose, group.disableSprintLateralTilt, rightKeepArm,
                        group.keepTriggerArmTuning, physicsMode, physicsSettings);
                ToolTuning.apply(kind, false, profile);
                ToolTuning.markKindDetached(kind, false);
            } else {
                ToolTuning.Profile profile = ToolTuning.get(kind, false);
                PunchyTuningConfig.applyToolProfileDefaults(profile, null, group.disableSprintLateralTilt, rightKeepArm,
                        group.keepTriggerArmTuning, physicsMode, physicsSettings);
                ToolTuning.commitKind(kind, false);
            }
            if (leftPose != null) {
                ToolTuning.Profile profile = leftPose.toTool();
                PunchyTuningConfig.applyToolProfileDefaults(profile, leftPose, group.disableSprintLateralTilt, leftKeepArm,
                        group.keepTriggerArmTuning, physicsMode, physicsSettings);
                ToolTuning.apply(kind, true, profile);
                ToolTuning.markKindDetached(kind, true);
            } else {
                ToolTuning.Profile profile = ToolTuning.get(kind, true);
                PunchyTuningConfig.applyToolProfileDefaults(profile, null, group.disableSprintLateralTilt, leftKeepArm,
                        group.keepTriggerArmTuning, physicsMode, physicsSettings);
                ToolTuning.commitKind(kind, true);
            }

            if (group.armTransforms != null) {
                PlayerArmModelLayer.ToolKind armKind = PunchyTuningConfig.toArmKind(kind);
                if (armKind != null) {
                    if (group.armTransforms.right != null) {
                        ArmToolTuning.apply(armKind, false,
                                PunchyTuningConfig.deserializeKindArmProfileForRuntime(false, group.armTransforms.right.toArm()));
                        ArmToolTuning.markKindDetached(armKind, false);
                    }
                    if (group.armTransforms.left != null) {
                        ArmToolTuning.apply(armKind, true,
                                PunchyTuningConfig.deserializeKindArmProfileForRuntime(true, group.armTransforms.left.toArm()));
                        ArmToolTuning.markKindDetached(armKind, true);
                    }
                }
            }
        }

        PunchyTuningConfig.applyEmptyHand(data.emptyHand);

        SideSet globalArm = PunchyTuningConfig.resolveGlobalArm(data);
        if (globalArm != null) {
            if (globalArm.right != null) {
                ArmToolTuning.applyGlobal(false, globalArm.right.toArm());
            }
            if (globalArm.left != null) {
                ArmToolTuning.applyGlobal(true, globalArm.left.toArm());
            }
            ArmMeshTuning.resetGlobal(false);
            ArmMeshTuning.resetGlobal(true);
        }
        if (data.armMesh != null) {
            SideSet globalMesh = data.armMesh.get("global");
            if (globalMesh != null) {
                if (globalMesh.right != null) {
                    ArmMeshTuning.applyGlobal(false, globalMesh.right.toArmMesh());
                }
                if (globalMesh.left != null) {
                    ArmMeshTuning.applyGlobal(true, globalMesh.left.toArmMesh());
                }
            }
        }
        if (data.chestProxy != null) PunchyTuningConfig.applyChestProxy(data.chestProxy);
        if (data.fluidProxy != null) PunchyTuningConfig.applyFluidProxy(data.fluidProxy);
        if (data.enchantParticleProxy != null) PunchyTuningConfig.applyEnchantParticleProxy(data.enchantParticleProxy);
        if (data.paddleProxy != null) PunchyTuningConfig.applyPaddleProxy(data.paddleProxy);
        if (data.fishingLineProxy != null) PunchyTuningConfig.applyFishingLineProxy(data.fishingLineProxy);

        if (data.itemSpecific != null) {
            for (Map.Entry<String, SpecificEntry> entry : data.itemSpecific.entrySet()) {
                SpecificEntry specific = entry.getValue();
                class_2960 itemId = PunchyTuningConfig.resolveSpecificItemId(entry.getKey());
                if (specific == null || itemId == null) continue;
                VanillaFirstPersonItemLayer.ToolKind kind = PunchyTuningConfig.resolveSpecificKind(specific, itemId);
                SideSet transforms = specific.transforms;
                Pose rightPose = transforms != null ? transforms.right : null;
                Pose leftPose = transforms != null ? transforms.left : null;
                ToolTuning.Profile rightProfile = rightPose != null ? rightPose.toTool() : null;
                ToolTuning.Profile leftProfile = leftPose != null ? leftPose.toTool() : null;
                PhysicsEntry physicsEntry = PunchyTuningConfig.resolvePhysicsEntry(specific.physics);
                ToolTuning.PhysicsMode physicsMode = PunchyTuningConfig.resolvePhysicsMode(physicsEntry);
                ToolTuning.PhysicsSettings physicsSettings = PunchyTuningConfig.resolvePhysicsSettings(physicsEntry);
                PunchyTuningConfig.applyToolProfileDefaults(rightProfile, rightPose, specific.disableSprintLateralTilt,
                        PunchyTuningConfig.resolveSideKeepArmTuning(specific.keepArmTuning, specific.keepArmTuningR),
                        specific.keepTriggerArmTuning, physicsMode, physicsSettings);
                PunchyTuningConfig.applyToolProfileDefaults(leftProfile, leftPose, specific.disableSprintLateralTilt,
                        PunchyTuningConfig.resolveSideKeepArmTuning(specific.keepArmTuning, specific.keepArmTuningL),
                        specific.keepTriggerArmTuning, physicsMode, physicsSettings);

                PunchyTuningConfig.applySpecificRuntimeFlags(itemId, specific.disableWalkOnWater, specific.disableDryOut, specific.disableSwiming);
                PunchyTuningConfig.applyGroupRuntimeFlags(kind, specific.disableWalkOnWater, specific.disableDryOut, specific.disableSwiming);
                PunchyTuningConfig.applyItemLevelFishingRodFlags(specific.customAnimation, specific.disableWalkOnWater, specific.disableDryOut, specific.disableSwiming);
                ToolTuning.applySpecific(itemId, specific.customModelData, kind, specific.renamed, specific.model,
                        specific.customAnimation, specific.customItemAnimations, PunchyTuningConfig.readCompatParticleEffects(specific),
                        specific.betterCombat, specific.bonePhysics, rightProfile, leftProfile);
                PunchyTuningConfig.applyMissingSpecificDefaults(itemId, kind, rightPose, leftPose, specific.disableSprintLateralTilt,
                        specific.keepArmTuning, specific.keepArmTuningR, specific.keepArmTuningL, specific.keepTriggerArmTuning,
                        physicsMode, physicsSettings);

                if (specific.armTransforms != null) {
                    PlayerArmModelLayer.ToolKind armKind = PunchyTuningConfig.resolveSpecificArmKind(specific);
                    ArmToolTuning.Profile rightArm = specific.armTransforms.right != null
                            ? PunchyTuningConfig.deserializeSpecificArmProfileFromSource(armKind, false, specific.armTransforms.right.toArm()) : null;
                    ArmToolTuning.Profile leftArm = specific.armTransforms.left != null
                            ? PunchyTuningConfig.deserializeSpecificArmProfileFromSource(armKind, true, specific.armTransforms.left.toArm()) : null;
                    ArmToolTuning.applySpecific(itemId, armKind, rightArm, leftArm);
                }
            }
        }
        ToolTuning.refreshAll();
    }

    private static void normalizeLegacyTransforms(GroupEntry group) {
        if (group.transforms == null && (group.right != null || group.left != null)) {
            group.transforms = new SideSet();
            group.transforms.right = group.right;
            group.transforms.left = group.left;
        }
    }

    private static void applyToolProfileDefaults(ToolTuning.Profile profile, Pose source, Boolean disableSprintLateralTilt,
                                                  Boolean keepArmTuning, Boolean keepTriggerArmTuning,
                                                  ToolTuning.PhysicsMode physicsMode, ToolTuning.PhysicsSettings physicsSettings) {
        if (profile == null) return;
        if (disableSprintLateralTilt != null && (source == null || source.disableSprintLateralTilt == null)) {
            profile.disableSprintLateralTilt = disableSprintLateralTilt;
        }
        if (keepArmTuning != null && (source == null || source.keepArmTuning == null)) {
            profile.keepArmTuning = keepArmTuning;
        }
        if (keepTriggerArmTuning != null && (source == null || source.keepTriggerArmTuning == null)) {
            profile.keepTriggerArmTuning = keepTriggerArmTuning;
        }
        if (physicsMode != null && (source == null || source.physics == null || source.physics.isBlank())) {
            profile.physicsMode = physicsMode;
            if (physicsSettings != null) {
                profile.physicsSettings = physicsSettings.copy();
            }
        }
    }

    private static void applyMissingSpecificDefaults(class_2960 itemId, VanillaFirstPersonItemLayer.ToolKind kind,
                                                     Pose rightPose, Pose leftPose, Boolean disableSprintLateralTilt,
                                                     Boolean keepArmTuning, Boolean keepArmTuningR, Boolean keepArmTuningL,
                                                     Boolean keepTriggerArmTuning, ToolTuning.PhysicsMode physicsMode,
                                                     ToolTuning.PhysicsSettings physicsSettings) {
        Boolean rightKeepArm = PunchyTuningConfig.resolveSideKeepArmTuning(keepArmTuning, keepArmTuningR);
        Boolean leftKeepArm = PunchyTuningConfig.resolveSideKeepArmTuning(keepArmTuning, keepArmTuningL);
        if (rightPose == null && (disableSprintLateralTilt != null || rightKeepArm != null || keepTriggerArmTuning != null || physicsMode != null)) {
            ToolTuning.Profile profile = ToolTuning.getSpecificProfile(itemId, kind, false);
            PunchyTuningConfig.applyToolProfileDefaults(profile, null, disableSprintLateralTilt, rightKeepArm,
                    keepTriggerArmTuning, physicsMode, physicsSettings);
        }
        if (leftPose == null && (disableSprintLateralTilt != null || leftKeepArm != null || keepTriggerArmTuning != null || physicsMode != null)) {
            ToolTuning.Profile profile = ToolTuning.getSpecificProfile(itemId, kind, true);
            PunchyTuningConfig.applyToolProfileDefaults(profile, null, disableSprintLateralTilt, leftKeepArm,
                    keepTriggerArmTuning, physicsMode, physicsSettings);
        }
    }

    private static PlayerArmModelLayer.ToolKind toArmKind(VanillaFirstPersonItemLayer.ToolKind kind) {
        try {
            return PlayerArmModelLayer.ToolKind.valueOf(kind.name());
        }
        catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private static VanillaFirstPersonItemLayer.ToolKind resolveSpecificKind(SpecificEntry specific, class_2960 itemId) {
        if (specific.kind != null) {
            try {
                return VanillaFirstPersonItemLayer.ToolKind.valueOf(specific.kind);
            }
            catch (IllegalArgumentException ignored) {
            }
        }
        class_1792 item = class_7923.field_41178.method_17966(itemId).orElse(class_1802.field_8162);
        if (item != class_1802.field_8162) {
            return ToolKindResolver.resolveItemKind(new class_1799((class_1935)item), VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
        }
        return VanillaFirstPersonItemLayer.ToolKind.INGREDIENT;
    }

    private static PlayerArmModelLayer.ToolKind resolveSpecificArmKind(SpecificEntry specific) {
        String name = specific.armKind != null ? specific.armKind : specific.kind;
        if (name != null) {
            try {
                return PlayerArmModelLayer.ToolKind.valueOf(name);
            }
            catch (IllegalArgumentException ignored) {
            }
        }
        return PlayerArmModelLayer.ToolKind.AXE;
    }

    private static void applyEmptyHand(EmptyHandEntry emptyHand) {
        if (emptyHand == null) return;
        VanillaFirstPersonItemLayer.ToolKind kind = VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND;
        if (emptyHand.customAnimation != null) ToolTuning.applyGroupAnimation(kind, emptyHand.customAnimation);
        if (emptyHand.customItemAnimations != null) ToolTuning.applyGroupItemAnimation(kind, emptyHand.customItemAnimations);
        if (emptyHand.betterCombat != null) ToolTuning.applyGroupBetterCombat(kind, emptyHand.betterCombat);

        PhysicsEntry physicsEntry = PunchyTuningConfig.resolvePhysicsEntry(emptyHand.physics);
        ToolTuning.PhysicsMode physicsMode = PunchyTuningConfig.resolvePhysicsMode(physicsEntry);
        ToolTuning.PhysicsSettings physicsSettings = PunchyTuningConfig.resolvePhysicsSettings(physicsEntry);
        Boolean rightKeepArm = PunchyTuningConfig.resolveSideKeepArmTuning(emptyHand.keepArmTuning, emptyHand.keepArmTuningR);
        Boolean leftKeepArm = PunchyTuningConfig.resolveSideKeepArmTuning(emptyHand.keepArmTuning, emptyHand.keepArmTuningL);
        Pose rightPose = emptyHand.transforms != null ? emptyHand.transforms.right : null;
        Pose leftPose = emptyHand.transforms != null ? emptyHand.transforms.left : null;
        ToolTuning.Profile rightProfile = rightPose != null ? rightPose.toTool() : ToolTuning.get(kind, false);
        ToolTuning.Profile leftProfile = leftPose != null ? leftPose.toTool() : ToolTuning.get(kind, true);
        PunchyTuningConfig.applyToolProfileDefaults(rightProfile, rightPose, emptyHand.disableSprintLateralTilt, rightKeepArm,
                emptyHand.keepTriggerArmTuning, physicsMode, physicsSettings);
        PunchyTuningConfig.applyToolProfileDefaults(leftProfile, leftPose, emptyHand.disableSprintLateralTilt, leftKeepArm,
                emptyHand.keepTriggerArmTuning, physicsMode, physicsSettings);
        if (rightPose != null) {
            ToolTuning.apply(kind, false, rightProfile);
            ToolTuning.markKindDetached(kind, false);
        } else {
            ToolTuning.commitKind(kind, false);
        }
        if (leftPose != null) {
            ToolTuning.apply(kind, true, leftProfile);
            ToolTuning.markKindDetached(kind, true);
        } else {
            ToolTuning.commitKind(kind, true);
        }

        if (emptyHand.armTransforms != null) {
            PlayerArmModelLayer.ToolKind armKind = PlayerArmModelLayer.ToolKind.EMPTY_HAND;
            if (emptyHand.armTransforms.right != null) {
                ArmToolTuning.apply(armKind, false,
                        PunchyTuningConfig.deserializeKindArmProfileForRuntime(false, emptyHand.armTransforms.right.toArm()));
                ArmToolTuning.markKindDetached(armKind, false);
            }
            if (emptyHand.armTransforms.left != null) {
                ArmToolTuning.apply(armKind, true,
                        PunchyTuningConfig.deserializeKindArmProfileForRuntime(true, emptyHand.armTransforms.left.toArm()));
                ArmToolTuning.markKindDetached(armKind, true);
            }
        }
    }


    private static void applyChestProxy(Map<String, Pose> map) {
        if (map != null) {
            for (Map.Entry<String, Pose> entry : map.entrySet()) {
                ChestProxyTuning.Kind kind;
                if (entry == null || entry.getKey() == null || entry.getValue() == null || (kind = PunchyTuningConfig.parseChestProxyKind(entry.getKey())) == null) continue;
                ChestProxyTuning.Profile profile = entry.getValue().toChestProxy();
                ChestProxyTuning.apply(kind, profile);
            }
        }
    }

    private static void applyFluidProxy(Map<String, Pose> map) {
        if (map != null) {
            for (Map.Entry<String, Pose> entry : map.entrySet()) {
                FluidProxyTuning.Kind kind;
                if (entry == null || entry.getKey() == null || entry.getValue() == null || (kind = PunchyTuningConfig.parseFluidProxyKind(entry.getKey())) == null) continue;
                FluidProxyTuning.Profile profile = entry.getValue().toFluidProxy();
                FluidProxyTuning.apply(kind, profile);
            }
        }
    }

    private static void applyEnchantParticleProxy(Map<String, Pose> map) {
        if (map != null) {
            for (Map.Entry<String, Pose> entry : map.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                EnchantParticleProxySide enchantParticleProxySide = PunchyTuningConfig.parseEnchantParticleProxySide(entry.getKey());
                EnchantParticleProxyTuning.Kind kind = PunchyTuningConfig.parseEnchantParticleProxyKind(entry.getKey());
                if (kind == null) continue;
                EnchantParticleProxyTuning.Profile profile = entry.getValue().toEnchantParticleProxy();
                if (enchantParticleProxySide == EnchantParticleProxySide.LEFT) {
                    EnchantParticleProxyTuning.apply(kind, true, profile);
                    continue;
                }
                if (enchantParticleProxySide == EnchantParticleProxySide.RIGHT) {
                    EnchantParticleProxyTuning.apply(kind, false, profile);
                    continue;
                }
                EnchantParticleProxyTuning.apply(kind, false, profile);
                EnchantParticleProxyTuning.apply(kind, true, profile);
            }
        }
    }

    private static void applyPaddleProxy(Map<String, Pose> map) {
        if (map != null) {
            for (Map.Entry<String, Pose> entry : map.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                PaddleProxySide paddleProxySide = PunchyTuningConfig.parsePaddleProxySide(entry.getKey());
                PaddleProxyTuning.Kind kind = PunchyTuningConfig.parsePaddleProxyKind(entry.getKey());
                if (kind == null) continue;
                PaddleProxyTuning.Profile profile = entry.getValue().toPaddleProxy();
                if (paddleProxySide == PaddleProxySide.LEFT) {
                    PaddleProxyTuning.apply(kind, true, profile);
                    continue;
                }
                if (paddleProxySide == PaddleProxySide.RIGHT) {
                    PaddleProxyTuning.apply(kind, false, profile);
                    continue;
                }
                PaddleProxyTuning.apply(kind, false, profile);
                PaddleProxyTuning.apply(kind, true, profile);
            }
        }
    }

    private static void applyFishingLineProxy(Map<String, Pose> map) {
        if (map != null) {
            for (Map.Entry<String, Pose> entry : map.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                FishingLineProxySide fishingLineProxySide = PunchyTuningConfig.parseFishingLineProxySide(entry.getKey());
                FishingLineProxyTuning.Kind kind = PunchyTuningConfig.parseFishingLineProxyKind(entry.getKey());
                if (kind == null) continue;
                FishingLineProxyTuning.Profile profile = entry.getValue().toFishingLineProxy();
                if (fishingLineProxySide == FishingLineProxySide.LEFT) {
                    FishingLineProxyTuning.apply(kind, true, profile);
                    continue;
                }
                if (fishingLineProxySide == FishingLineProxySide.RIGHT) {
                    FishingLineProxyTuning.apply(kind, false, profile);
                    continue;
                }
                FishingLineProxyTuning.apply(kind, false, profile);
                FishingLineProxyTuning.apply(kind, true, profile);
            }
        }
    }

    private static Map<String, Pose> captureChestProxy() {
        HashMap<String, Pose> hashMap = new HashMap<String, Pose>();
        for (ChestProxyTuning.Kind kind : ChestProxyTuning.Kind.values()) {
            ChestProxyTuning.Profile profile = ChestProxyTuning.get(kind);
            if (profile == null) continue;
            hashMap.put(kind.name().toLowerCase(Locale.ROOT), Pose.fromChestProxy(profile));
        }
        return hashMap;
    }

    private static Map<String, Pose> captureFluidProxy() {
        HashMap<String, Pose> hashMap = new HashMap<String, Pose>();
        for (FluidProxyTuning.Kind kind : FluidProxyTuning.Kind.values()) {
            FluidProxyTuning.Profile profile = FluidProxyTuning.get(kind);
            if (profile == null) continue;
            hashMap.put(kind.name().toLowerCase(Locale.ROOT), Pose.fromFluidProxy(profile));
        }
        return hashMap;
    }

    private static Map<String, Pose> captureEnchantParticleProxy() {
        HashMap<String, Pose> hashMap = new HashMap<String, Pose>();
        for (EnchantParticleProxyTuning.Kind kind : EnchantParticleProxyTuning.Kind.values()) {
            EnchantParticleProxyTuning.Profile profile = EnchantParticleProxyTuning.get(kind, false);
            EnchantParticleProxyTuning.Profile profile2 = EnchantParticleProxyTuning.get(kind, true);
            if (profile != null) {
                hashMap.put(kind.name().toLowerCase(Locale.ROOT) + "_right", Pose.fromEnchantParticleProxy(profile));
            }
            if (profile2 == null) continue;
            hashMap.put(kind.name().toLowerCase(Locale.ROOT) + "_left", Pose.fromEnchantParticleProxy(profile2));
        }
        return hashMap;
    }

    private static Map<String, Pose> capturePaddleProxy() {
        HashMap<String, Pose> hashMap = new HashMap<String, Pose>();
        for (PaddleProxyTuning.Kind kind : PaddleProxyTuning.Kind.values()) {
            PaddleProxyTuning.Profile profile = PaddleProxyTuning.get(kind, false);
            PaddleProxyTuning.Profile profile2 = PaddleProxyTuning.get(kind, true);
            if (profile != null) {
                hashMap.put(kind.name().toLowerCase(Locale.ROOT) + "_right", Pose.fromPaddleProxy(profile));
            }
            if (profile2 == null) continue;
            hashMap.put(kind.name().toLowerCase(Locale.ROOT) + "_left", Pose.fromPaddleProxy(profile2));
        }
        return hashMap;
    }

    private static Map<String, Pose> captureFishingLineProxy() {
        HashMap<String, Pose> hashMap = new HashMap<String, Pose>();
        for (FishingLineProxyTuning.Kind kind : FishingLineProxyTuning.Kind.values()) {
            FishingLineProxyTuning.Profile profile = FishingLineProxyTuning.get(kind, false);
            FishingLineProxyTuning.Profile profile2 = FishingLineProxyTuning.get(kind, true);
            if (profile != null) {
                hashMap.put(kind.name().toLowerCase(Locale.ROOT) + "_right", Pose.fromFishingLineProxy(profile));
            }
            if (profile2 == null) continue;
            hashMap.put(kind.name().toLowerCase(Locale.ROOT) + "_left", Pose.fromFishingLineProxy(profile2));
        }
        return hashMap;
    }

    private static ChestProxyTuning.Kind parseChestProxyKind(String string) {
        String string2;
        if (string == null) {
            return null;
        }
        return switch (string2 = string.toLowerCase(Locale.ROOT)) {
            case "boat" -> ChestProxyTuning.Kind.BOAT;
            case "raft" -> ChestProxyTuning.Kind.RAFT;
            case "minecart" -> ChestProxyTuning.Kind.MINECART;
            default -> null;
        };
    }

    private static FluidProxyTuning.Kind parseFluidProxyKind(String string) {
        String string2;
        if (string == null) {
            return null;
        }
        return switch (string2 = string.toLowerCase(Locale.ROOT)) {
            case "water" -> FluidProxyTuning.Kind.WATER;
            case "lava" -> FluidProxyTuning.Kind.LAVA;
            case "milk" -> FluidProxyTuning.Kind.MILK;
            default -> null;
        };
    }

    private static EnchantParticleProxyTuning.Kind parseEnchantParticleProxyKind(String string) {
        if (string == null) {
            return null;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.endsWith("_right")) {
            string2 = string2.substring(0, string2.length() - "_right".length());
        } else if (string2.endsWith("_left")) {
            string2 = string2.substring(0, string2.length() - "_left".length());
        }
        int n = -1;
        switch (string2.hashCode()) {
            case 3029737: {
                if (!string2.equals("book")) break;
                n = 0;
            }
        }
        return switch (n) {
            case 0 -> EnchantParticleProxyTuning.Kind.BOOK;
            default -> null;
        };
    }

    private static PaddleProxyTuning.Kind parsePaddleProxyKind(String string) {
        if (string == null) {
            return null;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.endsWith("_right")) {
            string2 = string2.substring(0, string2.length() - "_right".length());
        } else if (string2.endsWith("_left")) {
            string2 = string2.substring(0, string2.length() - "_left".length());
        }
        return switch (string2) {
            case "boat" -> PaddleProxyTuning.Kind.BOAT;
            case "raft" -> PaddleProxyTuning.Kind.RAFT;
            default -> null;
        };
    }

    private static FishingLineProxyTuning.Kind parseFishingLineProxyKind(String string) {
        if (string == null) {
            return null;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.endsWith("_right")) {
            string2 = string2.substring(0, string2.length() - "_right".length());
        } else if (string2.endsWith("_left")) {
            string2 = string2.substring(0, string2.length() - "_left".length());
        }
        return switch (string2) {
            case "rod", "fishing_rod" -> FishingLineProxyTuning.Kind.ROD;
            default -> null;
        };
    }

    private static PaddleProxySide parsePaddleProxySide(String string) {
        if (string == null) {
            return PaddleProxySide.BOTH;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.endsWith("_left")) {
            return PaddleProxySide.LEFT;
        }
        return string2.endsWith("_right") ? PaddleProxySide.RIGHT : PaddleProxySide.BOTH;
    }

    private static FishingLineProxySide parseFishingLineProxySide(String string) {
        if (string == null) {
            return FishingLineProxySide.BOTH;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.endsWith("_left")) {
            return FishingLineProxySide.LEFT;
        }
        return string2.endsWith("_right") ? FishingLineProxySide.RIGHT : FishingLineProxySide.BOTH;
    }

    private static EnchantParticleProxySide parseEnchantParticleProxySide(String string) {
        if (string == null) {
            return EnchantParticleProxySide.BOTH;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.endsWith("_left")) {
            return EnchantParticleProxySide.LEFT;
        }
        return string2.endsWith("_right") ? EnchantParticleProxySide.RIGHT : EnchantParticleProxySide.BOTH;
    }

    public static void saveCurrent() {
        try {
            Object object;
            Data data = PunchyTuningConfig.captureCurrent();
            if (INITIAL_STATE != null) {
                object = PunchyTuningConfig.computeDelta(INITIAL_STATE, data);
                ((Data)object).globalItem = null;
                ((Data)object).globalArm = null;
                ((Data)object).armMesh = null;
                PunchyTuningConfig.saveHelperData((Data)object);
            }
            if ((object = Punchy.ACTIVE_COMPAT_EDIT_TARGET) != null && !((String)object).isBlank()) {
                Data data2 = BASELINE_STATE != null ? PunchyTuningConfig.computeDelta(BASELINE_STATE, data) : data;
                PunchyTuningConfig.saveCompatData((String)object, data2);
            } else {
                Path path = TuningProfileManager.getActiveProfileFilePath();
                if (path != null) {
                    PunchyTuningConfig.saveCurrentProfileDeltaToFile(path);
                } else {
                    Data data3 = INITIAL_STATE != null ? PunchyTuningConfig.computeDelta(INITIAL_STATE, data) : data;
                    PunchyTuningConfig.saveData(data3);
                }
                BASELINE_STATE = data;
            }
        }
        catch (Exception exception) {
        }
    }

    public static boolean saveCurrentDeltaToFile(Path path) {
        if (path == null) {
            return false;
        }
        try {
            Data data;
            Data data2 = PunchyTuningConfig.captureCurrent();
            Data data3 = data = BASELINE_STATE != null ? PunchyTuningConfig.computeDelta(BASELINE_STATE, data2) : data2;
            if (data == null) {
                data = new Data();
            }
            PunchyTuningConfig.pruneEmptyCollections(data);
            Path path2 = path.getParent();
            if (path2 != null) {
                Files.createDirectories(path2, new FileAttribute[0]);
            }
            try (FileWriter fileWriter = new FileWriter(path.toFile());){
                GSON.toJson((Object)data, (Appendable)fileWriter);
            }
            INITIAL_STATE = PunchyTuningConfig.copyData(data2);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static boolean saveCurrentProfileDeltaToFile(Path path) {
        if (path == null) {
            return false;
        }
        try {
            Data data = PunchyTuningConfig.captureCurrent();
            Data data2 = PunchyTuningConfig.buildProfileSaveData(path, data);
            if (data2 == null) {
                data2 = new Data();
            }
            PunchyTuningConfig.pruneEmptyCollections(data2);
            Path path2 = path.getParent();
            if (path2 != null) {
                Files.createDirectories(path2, new FileAttribute[0]);
            }
            try (FileWriter fileWriter = new FileWriter(path.toFile());){
                GSON.toJson((Object)data2, (Appendable)fileWriter);
            }
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    private static Data buildProfileSaveData(Path path, Data data) {
        Data data2;
        Data data3;
        Data data4 = data3 = INITIAL_STATE != null ? PunchyTuningConfig.computeDelta(INITIAL_STATE, data) : PunchyTuningConfig.copyData(data);
        if (data3 == null) {
            data3 = new Data();
        }
        if ((data2 = PunchyTuningConfig.mergeData(PunchyTuningConfig.readDataFile(path), PunchyTuningConfig.copyData(data3))) == null) {
            data2 = new Data();
        }
        if (DEFAULTS_RESOURCE_STATE == null) {
            PunchyTuningConfig.pruneEmptyCollections(data2);
            return data2;
        }
        Data data5 = PunchyTuningConfig.mergeData(PunchyTuningConfig.copyData(DEFAULTS_RESOURCE_STATE), PunchyTuningConfig.copyData(data2));
        Data data6 = PunchyTuningConfig.computeDelta(DEFAULTS_RESOURCE_STATE, data5);
        if (data6 == null) {
            data6 = new Data();
        }
        if (data2.itemSpecific != null && !data2.itemSpecific.isEmpty()) {
            data6.itemSpecific = PunchyTuningConfig.copySpecificMapForProfile(data2.itemSpecific);
        }
        PunchyTuningConfig.pruneEmptyCollections(data6);
        return data6;
    }

    private static Map<String, SpecificEntry> copySpecificMapForProfile(Map<String, SpecificEntry> map) {
        if (map != null && !map.isEmpty()) {
            HashMap<String, SpecificEntry> hashMap = new HashMap<String, SpecificEntry>();
            for (Map.Entry<String, SpecificEntry> entry : map.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                hashMap.put(entry.getKey(), PunchyTuningConfig.copySpecificEntryForProfile(entry.getValue()));
            }
            return hashMap.isEmpty() ? null : hashMap;
        }
        return null;
    }

    private static Data readDataFile(Path path) {
        if (path != null && Files.isRegularFile(path, new LinkOption[0])) {
            try {
                Data data;
                try (FileReader fileReader = new FileReader(path.toFile());){
                    data = (Data)GSON.fromJson((Reader)fileReader, Data.class);
                }
                return data;
            }
            catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    private static void applyMixSelectionsFromActiveProfile(JsonObject jsonObject) {
    }

    private static void persistMixSelectionsToActiveProfile() {
    }

    private static JsonObject readJsonObjectFile(Path path) {
        if (path != null && Files.isRegularFile(path, new LinkOption[0])) {
            try {
                JsonObject jsonObject;
                try (FileReader fileReader = new FileReader(path.toFile());){
                    JsonElement jsonElement = (JsonElement)GSON.fromJson((Reader)fileReader, JsonElement.class);
                    jsonObject = jsonElement != null && jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : null;
                }
                return jsonObject;
            }
            catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    private static void injectMixSelectionsIntoProfileJson(JsonObject jsonObject, PunchyConfig.Data data) {
        if (jsonObject != null) {
            if (data == null) {
                jsonObject.remove("animationPackSelections");
                jsonObject.remove("animationPackTypeSelections");
                jsonObject.remove("mixSpecificForceOverride");
            } else {
                PunchyTuningConfig.writeStringMap(jsonObject, "animationPackSelections", data.animationPackSelections);
                PunchyTuningConfig.writeStringMap(jsonObject, "animationPackTypeSelections", data.animationPackTypeSelections);
                PunchyTuningConfig.writeBooleanMap(jsonObject, "mixSpecificForceOverride", data.mixSpecificForceOverride);
            }
        }
    }

    private static LinkedHashMap<String, String> readStringMap(JsonObject jsonObject, String string) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        if (jsonObject != null && string != null && jsonObject.has(string) && jsonObject.get(string).isJsonObject()) {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject(string);
            for (Map.Entry entry : jsonObject2.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null || ((JsonElement)entry.getValue()).isJsonNull()) continue;
                try {
                    String string2 = ((JsonElement)entry.getValue()).getAsString();
                    if (string2 == null || string2.isBlank()) continue;
                    linkedHashMap.put((String)entry.getKey(), string2);
                }
                catch (Exception exception) {}
            }
            return linkedHashMap;
        }
        return linkedHashMap;
    }

    private static LinkedHashMap<String, Boolean> readBooleanMap(JsonObject jsonObject, String string) {
        LinkedHashMap<String, Boolean> linkedHashMap = new LinkedHashMap<String, Boolean>();
        if (jsonObject != null && string != null && jsonObject.has(string) && jsonObject.get(string).isJsonObject()) {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject(string);
            for (Map.Entry entry : jsonObject2.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null || ((JsonElement)entry.getValue()).isJsonNull()) continue;
                try {
                    linkedHashMap.put((String)entry.getKey(), ((JsonElement)entry.getValue()).getAsBoolean());
                }
                catch (Exception exception) {}
            }
            return linkedHashMap;
        }
        return linkedHashMap;
    }

    private static void writeStringMap(JsonObject jsonObject, String string, Map<String, String> map) {
        if (jsonObject != null && string != null) {
            if (map != null && !map.isEmpty()) {
                JsonObject jsonObject2 = new JsonObject();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (entry == null || entry.getKey() == null || entry.getValue() == null || entry.getValue().isBlank()) continue;
                    jsonObject2.addProperty(entry.getKey(), entry.getValue());
                }
                if (jsonObject2.size() == 0) {
                    jsonObject.remove(string);
                } else {
                    jsonObject.add(string, (JsonElement)jsonObject2);
                }
            } else {
                jsonObject.remove(string);
            }
        }
    }

    private static void writeBooleanMap(JsonObject jsonObject, String string, Map<String, Boolean> map) {
        if (jsonObject != null && string != null) {
            if (map != null && !map.isEmpty()) {
                JsonObject jsonObject2 = new JsonObject();
                for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                    if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                    jsonObject2.addProperty(entry.getKey(), entry.getValue());
                }
                if (jsonObject2.size() == 0) {
                    jsonObject.remove(string);
                } else {
                    jsonObject.add(string, (JsonElement)jsonObject2);
                }
            } else {
                jsonObject.remove(string);
            }
        }
    }

    public static boolean exportTuningHelperDevAndOpen() {
        try {
            Path path = PunchyTuningConfig.getConfigDirectory().resolve("punchy").resolve("dev_tuning_helper");
            Files.createDirectories(path, new FileAttribute[0]);
            Path path2 = path.resolve("tuning_helper_dev.json");
            if (!PunchyTuningConfig.saveCurrentDeltaToFile(path2)) {
                return false;
            }
            class_156.method_668().method_60932(path);
            return true;
        }
        catch (Exception exception) {
            try {
                Path path = PunchyTuningConfig.getConfigDirectory().resolve("punchy").resolve("dev_tuning_helper");
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(path.toFile());
                    return true;
                }
            }
            catch (Exception exception2) {
            }
            return false;
        }
    }

    private static void saveHelperData(Data data) {
        Path path = PunchyTuningConfig.getConfigDirectory();
        try {
            Files.createDirectories(path.resolve("punchy"), new FileAttribute[0]);
            File file = path.resolve("punchy").resolve("creator_tuning_helper.json").toFile();
            try (FileWriter fileWriter = new FileWriter(file);){
                GSON.toJson((Object)data, (Appendable)fileWriter);
            }
        }
        catch (Exception exception) {
        }
    }

    private static Data captureCurrent() {
        Data data = new Data();
        for (VanillaFirstPersonItemLayer.ToolKind toolKind : VanillaFirstPersonItemLayer.ToolKind.values()) {
            GroupEntry groupEntry = new GroupEntry();
            boolean bl = false;
            SideSet sideSet = null;
            if (!ToolTuning.isKindAttached(toolKind, false)) {
                sideSet = new SideSet();
                sideSet.right = Pose.fromTool(ToolTuning.snapshot(toolKind, false));
            }
            if (!ToolTuning.isKindAttached(toolKind, true)) {
                if (sideSet == null) {
                    sideSet = new SideSet();
                }
                sideSet.left = Pose.fromTool(ToolTuning.snapshot(toolKind, true));
            }
            if (sideSet != null) {
                groupEntry.transforms = sideSet;
                bl = true;
            }
            PlayerArmModelLayer.ToolKind toolKind2 = null;
            try {
                toolKind2 = PlayerArmModelLayer.ToolKind.valueOf(toolKind.name());
            }
            catch (IllegalArgumentException illegalArgumentException) {
            }
            if (toolKind2 != null) {
                ArmToolTuning.Profile profile;
                SideSet illegalArgumentException = null;
                if (!ArmToolTuning.isKindAttached(toolKind2, false) && !PunchyTuningConfig.isImplicitEmptyHandArmProfile(toolKind2, false, profile = ArmToolTuning.snapshot(toolKind2, false))) {
                    illegalArgumentException = new SideSet();
                    illegalArgumentException.right = Pose.fromArm(PunchyTuningConfig.normalizeKindArmProfileForSerialization(toolKind2, false, profile));
                }
                if (!ArmToolTuning.isKindAttached(toolKind2, true) && !PunchyTuningConfig.isImplicitEmptyHandArmProfile(toolKind2, true, profile = ArmToolTuning.snapshot(toolKind2, true))) {
                    if (illegalArgumentException == null) {
                        illegalArgumentException = new SideSet();
                    }
                    illegalArgumentException.left = Pose.fromArm(PunchyTuningConfig.normalizeKindArmProfileForSerialization(toolKind2, true, profile));
                }
                if (illegalArgumentException != null) {
                    groupEntry.armTransforms = illegalArgumentException;
                    bl = true;
                }
            }
            if (!bl) continue;
            data.item.put(toolKind.name(), groupEntry);
        }
        SideSet sideSet = new SideSet();
        sideSet.right = Pose.fromTool(ToolTuning.snapshotGlobal(false));
        sideSet.left = Pose.fromTool(ToolTuning.snapshotGlobal(true));
        data.globalItem = sideSet;
        SideSet sideSet2 = new SideSet();
        sideSet2.right = Pose.fromArm(ArmToolTuning.snapshotGlobal(false));
        sideSet2.left = Pose.fromArm(ArmToolTuning.snapshotGlobal(true));
        data.globalArm = sideSet2;
        SideSet sideSet3 = new SideSet();
        sideSet3.right = Pose.fromArmMesh(ArmMeshTuning.snapshotGlobal(false));
        sideSet3.left = Pose.fromArmMesh(ArmMeshTuning.snapshotGlobal(true));
        data.armMesh.put("global", sideSet3);
        data.itemSpecific = PunchyTuningConfig.captureSpecificCurrent();
        data.chestProxy = PunchyTuningConfig.captureChestProxy();
        data.fluidProxy = PunchyTuningConfig.captureFluidProxy();
        data.enchantParticleProxy = PunchyTuningConfig.captureEnchantParticleProxy();
        data.paddleProxy = PunchyTuningConfig.capturePaddleProxy();
        data.fishingLineProxy = PunchyTuningConfig.captureFishingLineProxy();
        return data;
    }

    private static void rememberBaseline() {
        BASELINE_STATE = PunchyTuningConfig.captureCurrent();
    }

    private static boolean isImplicitEmptyHandArmProfile(PlayerArmModelLayer.ToolKind toolKind, boolean bl, ArmToolTuning.Profile profile) {
        if (toolKind == PlayerArmModelLayer.ToolKind.EMPTY_HAND && profile != null) {
            ArmToolTuning.Profile profile2 = PunchyTuningConfig.getDefaultArmEditorBase(toolKind, bl);
            ArmToolTuning.Profile profile3 = PunchyTuningConfig.getDefaultGlobalArmBase(bl);
            ArmToolTuning.Profile profile4 = ArmToolTuning.snapshotGlobal(bl);
            return profile2 != null && profile3 != null && profile4 != null ? PunchyTuningConfig.nearlyEqual(profile.scale, profile4.scale + (profile2.scale - profile3.scale)) && PunchyTuningConfig.nearlyEqual(profile.offX, profile4.offX + (profile2.offX - profile3.offX)) && PunchyTuningConfig.nearlyEqual(profile.offY, profile4.offY + (profile2.offY - profile3.offY)) && PunchyTuningConfig.nearlyEqual(profile.offZ, profile4.offZ + (profile2.offZ - profile3.offZ)) && PunchyTuningConfig.nearlyEqual(profile.rotX, profile2.rotX) && PunchyTuningConfig.nearlyEqual(profile.rotY, profile2.rotY) && PunchyTuningConfig.nearlyEqual(profile.rotZ, profile2.rotZ) : false;
        }
        return false;
    }

    public static ArmToolTuning.Profile getDefaultArmEditorBase(PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        Data data = DEFAULTS_RESOURCE_STATE;
        if (data != null && toolKind != null) {
            SideSet sideSet = null;
            if (toolKind == PlayerArmModelLayer.ToolKind.EMPTY_HAND && data.emptyHand != null) {
                sideSet = data.emptyHand.armTransforms;
            }
            if (sideSet == null && data.item != null) {
                GroupEntry groupEntry = data.item.get(toolKind.name());
                if (groupEntry != null) {
                    sideSet = groupEntry.armTransforms;
                }
            }
            Pose selectedPose = sideSet != null ? (bl ? sideSet.left : sideSet.right) : null;
            if (selectedPose != null) {
                return selectedPose.toArm();
            }
            SideSet sideSet2 = data.globalArm;
            Pose pose = sideSet2 != null ? (bl ? sideSet2.left : sideSet2.right) : null;
            return pose != null ? pose.toArm() : new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        return new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public static ArmToolTuning.Profile getDefaultGlobalArmBase(boolean bl) {
        Data data = DEFAULTS_RESOURCE_STATE;
        if (data != null && data.globalArm != null) {
            Pose pose = bl ? data.globalArm.left : data.globalArm.right;
            return pose != null ? pose.toArm() : new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        return new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    private static Map<String, SpecificEntry> captureSpecificCurrent() {
        HashMap<String, SpecificEntry> hashMap = new HashMap<String, SpecificEntry>();
        List<ToolTuning.SpecificSnapshotDetailed> list = ToolTuning.snapshotSpecificsDetailed();
        Map<class_2960, ArmToolTuning.SpecificSnapshot> map = ArmToolTuning.snapshotSpecifics();
        HashSet<class_2960> hashSet = new HashSet<class_2960>();
        for (ToolTuning.SpecificSnapshotDetailed snapshot : list) {
            if (snapshot == null || snapshot.itemId() == null) continue;
            class_2960 itemId = snapshot.itemId();
            SpecificEntry entry = new SpecificEntry();
            VanillaFirstPersonItemLayer.ToolKind itemKind = snapshot.kind() != null ? snapshot.kind() : PunchyTuningConfig.resolveCurrentItemKind(itemId);
            if (itemKind != null) {
                entry.kind = itemKind.name();
            }
            ToolTuning.Profile rightItemProfile = PunchyTuningConfig.resolveEffectiveSpecificItemProfile(snapshot.right(), itemKind, false);
            ToolTuning.Profile leftItemProfile = PunchyTuningConfig.resolveEffectiveSpecificItemProfile(snapshot.left(), itemKind, true);
            entry.transforms = PunchyTuningConfig.captureSpecificItemTransforms(rightItemProfile, leftItemProfile);
            PunchyTuningConfig.applySpecificToolMetadata(entry, entry.transforms, rightItemProfile, leftItemProfile);
            entry.customModelData = snapshot.customModelData();
            entry.renamed = snapshot.renamed();
            if (snapshot.model() != null && !snapshot.model().isBlank()) {
                entry.model = snapshot.model();
            }
            ArmToolTuning.SpecificSnapshot armSnapshot = map.get(itemId);
            PlayerArmModelLayer.ToolKind armKind = armSnapshot != null && armSnapshot.kind() != null ? armSnapshot.kind() : PunchyTuningConfig.resolveCurrentArmKind(itemId);
            if (armKind != null) {
                entry.armKind = armKind.name();
            }
            ArmToolTuning.Profile rightArmProfile = PunchyTuningConfig.resolveEffectiveSpecificArmProfile(armSnapshot != null ? armSnapshot.right() : null, armKind, false);
            ArmToolTuning.Profile leftArmProfile = PunchyTuningConfig.resolveEffectiveSpecificArmProfile(armSnapshot != null ? armSnapshot.left() : null, armKind, true);
            entry.armTransforms = PunchyTuningConfig.captureSpecificArmTransforms(armKind, rightArmProfile, leftArmProfile);
            String storageKey = PunchyTuningConfig.specificStorageKey(itemId, entry.customModelData, entry.renamed);
            if (entry.transforms == null && entry.armTransforms == null && entry.kind == null && entry.armKind == null || storageKey == null || storageKey.isBlank()) continue;
            hashMap.put(storageKey, entry);
            hashSet.add(itemId);
        }
        for (Map.Entry<class_2960, ArmToolTuning.SpecificSnapshot> armEntry : map.entrySet()) {
            class_2960 itemId = armEntry.getKey();
            ArmToolTuning.SpecificSnapshot snapshot = armEntry.getValue();
            if (itemId == null || snapshot == null || hashSet.contains(itemId)) continue;
            String storageKey = itemId.toString();
            SpecificEntry entry = hashMap.getOrDefault(storageKey, new SpecificEntry());
            PlayerArmModelLayer.ToolKind armKind = snapshot.kind() != null ? snapshot.kind() : PunchyTuningConfig.resolveCurrentArmKind(itemId);
            if (armKind != null) {
                entry.armKind = armKind.name();
            }
            ArmToolTuning.Profile rightProfile = PunchyTuningConfig.resolveEffectiveSpecificArmProfile(snapshot.right(), armKind, false);
            ArmToolTuning.Profile leftProfile = PunchyTuningConfig.resolveEffectiveSpecificArmProfile(snapshot.left(), armKind, true);
            entry.armTransforms = PunchyTuningConfig.captureSpecificArmTransforms(armKind, rightProfile, leftProfile);
            if (entry.transforms == null && entry.armTransforms == null && entry.kind == null && entry.armKind == null) continue;
            hashMap.put(storageKey, entry);
        }
        return hashMap;
    }

    private static String specificStorageKey(class_2960 class_29602, Integer n, String string) {
        if (class_29602 == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(class_29602.toString());
        if (n != null) {
            stringBuilder.append('#').append(n);
        }
        if (string != null && !string.isBlank()) {
            stringBuilder.append('|').append(string);
        }
        return stringBuilder.toString();
    }

    private static VanillaFirstPersonItemLayer.ToolKind resolveCurrentItemKind(class_2960 class_29602) {
        if (class_29602 == null) {
            return null;
        }
        class_1792 class_17922 = class_7923.field_41178.method_17966(class_29602).orElse(class_1802.field_8162);
        if (class_17922 == class_1802.field_8162) {
            return null;
        }
        class_1799 class_17992 = new class_1799((class_1935)class_17922);
        return ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
    }

    private static PlayerArmModelLayer.ToolKind resolveCurrentArmKind(class_2960 class_29602) {
        if (class_29602 == null) {
            return null;
        }
        class_1792 class_17922 = class_7923.field_41178.method_17966(class_29602).orElse(class_1802.field_8162);
        if (class_17922 == class_1802.field_8162) {
            return null;
        }
        class_1799 class_17992 = new class_1799((class_1935)class_17922);
        return ToolKindResolver.resolveArmKind(class_17992, PlayerArmModelLayer.ToolKind.AXE);
    }

    private static SideSet captureCurrentItemKindTransforms(VanillaFirstPersonItemLayer.ToolKind toolKind) {
        if (toolKind == null) {
            return null;
        }
        SideSet sideSet = new SideSet();
        if (PunchyTuningConfig.shouldCaptureCurrentItemKindSide(toolKind, false)) {
            sideSet.right = Pose.fromTool(ToolTuning.snapshot(toolKind, false));
        }
        if (PunchyTuningConfig.shouldCaptureCurrentItemKindSide(toolKind, true)) {
            sideSet.left = Pose.fromTool(ToolTuning.snapshot(toolKind, true));
        }
        return sideSet.right == null && sideSet.left == null ? null : sideSet;
    }

    private static SideSet captureCurrentArmKindTransforms(PlayerArmModelLayer.ToolKind toolKind) {
        if (toolKind == null) {
            return null;
        }
        SideSet sideSet = new SideSet();
        if (!ArmToolTuning.isKindAttached(toolKind, false)) {
            sideSet.right = Pose.fromArm(ArmToolTuning.snapshot(toolKind, false));
        }
        if (!ArmToolTuning.isKindAttached(toolKind, true)) {
            sideSet.left = Pose.fromArm(ArmToolTuning.snapshot(toolKind, true));
        }
        return sideSet.right == null && sideSet.left == null ? null : sideSet;
    }

    private static boolean shouldCaptureCurrentItemKindSide(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        if (toolKind == null) {
            return false;
        }
        return !ToolTuning.isKindAttached(toolKind, bl) ? true : PunchyTuningConfig.hasSerializableToolMetadata(ToolTuning.get(toolKind, bl));
    }

    private static boolean hasSerializableToolMetadata(ToolTuning.Profile profile) {
        return profile == null ? false : profile.disableSprintLateralTilt != null || profile.keepArmTuning != null || profile.keepTriggerArmTuning != null || profile.physicsMode != null && profile.physicsMode != ToolTuning.PhysicsMode.NONE || profile.physicsSettings != null;
    }

    private static ToolTuning.Profile resolveEffectiveSpecificItemProfile(ToolTuning.Profile profile, VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        ToolTuning.Profile profile2 = null;
        if (toolKind != null && PunchyTuningConfig.shouldCaptureCurrentItemKindSide(toolKind, bl)) {
            profile2 = ToolTuning.snapshot(toolKind, bl);
        }
        if (profile == null) {
            return profile2;
        }
        ToolTuning.Profile profile3 = PunchyTuningConfig.copyToolProfile(profile);
        if (profile2 == null) {
            return profile3;
        }
        if (profile3.disableSprintLateralTilt == null) {
            profile3.disableSprintLateralTilt = profile2.disableSprintLateralTilt;
        }
        if (profile3.keepArmTuning == null) {
            profile3.keepArmTuning = profile2.keepArmTuning;
        }
        if (profile3.keepTriggerArmTuning == null) {
            profile3.keepTriggerArmTuning = profile2.keepTriggerArmTuning;
        }
        return profile3;
    }

    private static ArmToolTuning.Profile resolveEffectiveSpecificArmProfile(ArmToolTuning.Profile profile, PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        ArmToolTuning.Profile profile2 = toolKind != null ? ArmToolTuning.snapshot(toolKind, bl) : null;
        return profile == null ? profile2 : profile;
    }

    private static SideSet captureSpecificItemTransforms(ToolTuning.Profile profile, ToolTuning.Profile profile2) {
        if (profile == null && profile2 == null) {
            return null;
        }
        SideSet sideSet = new SideSet();
        if (profile != null) {
            sideSet.right = Pose.fromTool(profile);
        }
        if (profile2 != null) {
            sideSet.left = Pose.fromTool(profile2);
        }
        return sideSet;
    }

    private static SideSet captureSpecificArmTransforms(PlayerArmModelLayer.ToolKind toolKind, ArmToolTuning.Profile profile, ArmToolTuning.Profile profile2) {
        if (profile == null && profile2 == null) {
            return null;
        }
        SideSet sideSet = new SideSet();
        if (profile != null) {
            sideSet.right = Pose.fromArm(PunchyTuningConfig.normalizeSpecificArmProfileForSerialization(toolKind, false, profile));
        }
        if (profile2 != null) {
            sideSet.left = Pose.fromArm(PunchyTuningConfig.normalizeSpecificArmProfileForSerialization(toolKind, true, profile2));
        }
        return sideSet;
    }

    private static ArmToolTuning.Profile normalizeKindArmProfileForSerialization(PlayerArmModelLayer.ToolKind toolKind, boolean bl, ArmToolTuning.Profile profile) {
        return PunchyTuningConfig.rebaseArmProfileWithGlobalOffsetsOnly(profile, ArmToolTuning.getGlobal(bl), PunchyTuningConfig.getDefaultGlobalArmBase(bl));
    }

    private static ArmToolTuning.Profile normalizeSpecificArmProfileForSerialization(PlayerArmModelLayer.ToolKind toolKind, boolean bl, ArmToolTuning.Profile profile) {
        return PunchyTuningConfig.stripGlobalOffsetsFromSpecificArmProfile(profile, ArmToolTuning.getGlobal(bl));
    }

    private static ArmToolTuning.Profile deserializeKindArmProfileForRuntime(boolean bl, ArmToolTuning.Profile profile) {
        return PunchyTuningConfig.rebaseArmProfileWithGlobalOffsetsOnly(profile, PunchyTuningConfig.getDefaultGlobalArmBase(bl), ArmToolTuning.getGlobal(bl));
    }

    private static ArmToolTuning.Profile deserializeSpecificArmProfileForRuntime(PlayerArmModelLayer.ToolKind toolKind, boolean bl, ArmToolTuning.Profile profile) {
        return PunchyTuningConfig.applyGlobalOffsetsToSpecificArmProfile(profile, ArmToolTuning.getGlobal(bl));
    }

    private static ArmToolTuning.Profile deserializeSpecificArmProfileFromSource(PlayerArmModelLayer.ToolKind toolKind, boolean bl, ArmToolTuning.Profile profile) {
        return profile == null ? null : PunchyTuningConfig.deserializeSpecificArmProfileForRuntime(toolKind, bl, profile);
    }

    private static ArmToolTuning.Profile rebaseArmProfileWithGlobalOffsetsOnly(ArmToolTuning.Profile profile, ArmToolTuning.Profile profile2, ArmToolTuning.Profile profile3) {
        if (profile == null) {
            return null;
        }
        ArmToolTuning.Profile profile4 = profile2 != null ? profile2 : new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        ArmToolTuning.Profile profile5 = profile3 != null ? profile3 : new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        return new ArmToolTuning.Profile(profile5.scale + (profile.scale - profile4.scale), profile5.offX + (profile.offX - profile4.offX), profile5.offY + (profile.offY - profile4.offY), profile5.offZ + (profile.offZ - profile4.offZ), profile.rotX, profile.rotY, profile.rotZ);
    }

    private static ArmToolTuning.Profile stripGlobalOffsetsFromSpecificArmProfile(ArmToolTuning.Profile profile, ArmToolTuning.Profile profile2) {
        if (profile == null) {
            return null;
        }
        ArmToolTuning.Profile profile3 = profile2 != null ? profile2 : new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        return new ArmToolTuning.Profile(1.0f + (profile.scale - profile3.scale), profile.offX - profile3.offX, profile.offY - profile3.offY, profile.offZ - profile3.offZ, profile.rotX, profile.rotY, profile.rotZ);
    }

    private static ArmToolTuning.Profile applyGlobalOffsetsToSpecificArmProfile(ArmToolTuning.Profile profile, ArmToolTuning.Profile profile2) {
        if (profile == null) {
            return null;
        }
        ArmToolTuning.Profile profile3 = profile2 != null ? profile2 : new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        return new ArmToolTuning.Profile(profile3.scale + (profile.scale - 1.0f), profile3.offX + profile.offX, profile3.offY + profile.offY, profile3.offZ + profile.offZ, profile.rotX, profile.rotY, profile.rotZ);
    }

    private static ArmToolTuning.Profile rebaseArmProfile(ArmToolTuning.Profile profile, ArmToolTuning.Profile profile2, ArmToolTuning.Profile profile3) {
        if (profile == null) {
            return null;
        }
        ArmToolTuning.Profile profile4 = profile2 != null ? profile2 : new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        ArmToolTuning.Profile profile5 = profile3 != null ? profile3 : new ArmToolTuning.Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        return new ArmToolTuning.Profile(profile5.scale + (profile.scale - profile4.scale), profile5.offX + (profile.offX - profile4.offX), profile5.offY + (profile.offY - profile4.offY), profile5.offZ + (profile.offZ - profile4.offZ), profile5.rotX + (profile.rotX - profile4.rotX), profile5.rotY + (profile.rotY - profile4.rotY), profile5.rotZ + (profile.rotZ - profile4.rotZ));
    }

    private static void applySpecificToolMetadata(SpecificEntry specificEntry, SideSet sideSet, ToolTuning.Profile profile, ToolTuning.Profile profile2) {
        if (specificEntry != null) {
            JsonElement jsonElement;
            Boolean bl;
            Boolean bl2;
            Boolean bl3;
            Boolean bl4 = profile != null ? profile.disableSprintLateralTilt : null;
            Boolean bl5 = bl3 = profile2 != null ? profile2.disableSprintLateralTilt : null;
            if (profile != null && profile2 != null && Objects.equals(bl4, bl3) && bl4 != null) {
                specificEntry.disableSprintLateralTilt = bl4;
                PunchyTuningConfig.clearPoseDisableSprint(sideSet);
            }
            Boolean bl6 = profile != null ? profile.keepArmTuning : null;
            Boolean bl7 = bl2 = profile2 != null ? profile2.keepArmTuning : null;
            if (profile != null && profile2 != null) {
                if (Objects.equals(bl6, bl2) && bl6 != null) {
                    specificEntry.keepArmTuning = bl6;
                    PunchyTuningConfig.clearPoseKeepArmTuning(sideSet);
                } else {
                    if (bl6 != null) {
                        specificEntry.keepArmTuningR = bl6;
                        if (sideSet != null && sideSet.right != null) {
                            sideSet.right.keepArmTuning = null;
                        }
                    }
                    if (bl2 != null) {
                        specificEntry.keepArmTuningL = bl2;
                        if (sideSet != null && sideSet.left != null) {
                            sideSet.left.keepArmTuning = null;
                        }
                    }
                }
            }
            Boolean bl8 = profile != null ? profile.keepTriggerArmTuning : null;
            Boolean bl9 = bl = profile2 != null ? profile2.keepTriggerArmTuning : null;
            if (profile != null && profile2 != null && Objects.equals(bl8, bl) && bl8 != null) {
                specificEntry.keepTriggerArmTuning = bl8;
                PunchyTuningConfig.clearPoseKeepTriggerArmTuning(sideSet);
            }
            if ((jsonElement = PunchyTuningConfig.buildSharedPhysicsDefinition(profile, profile2)) != null) {
                specificEntry.physics = jsonElement;
            }
        }
    }

    private static void clearPoseDisableSprint(SideSet sideSet) {
        if (sideSet != null) {
            if (sideSet.right != null) {
                sideSet.right.disableSprintLateralTilt = null;
            }
            if (sideSet.left != null) {
                sideSet.left.disableSprintLateralTilt = null;
            }
        }
    }

    private static void clearPoseKeepArmTuning(SideSet sideSet) {
        if (sideSet != null) {
            if (sideSet.right != null) {
                sideSet.right.keepArmTuning = null;
            }
            if (sideSet.left != null) {
                sideSet.left.keepArmTuning = null;
            }
        }
    }

    private static void clearPoseKeepTriggerArmTuning(SideSet sideSet) {
        if (sideSet != null) {
            if (sideSet.right != null) {
                sideSet.right.keepTriggerArmTuning = null;
            }
            if (sideSet.left != null) {
                sideSet.left.keepTriggerArmTuning = null;
            }
        }
    }

    private static JsonElement buildSharedPhysicsDefinition(ToolTuning.Profile profile, ToolTuning.Profile profile2) {
        ToolTuning.PhysicsSettings physicsSettings;
        boolean bl;
        ToolTuning.Profile profile3;
        ToolTuning.Profile profile4 = profile3 = profile != null ? profile : profile2;
        if (profile3 == null) {
            return null;
        }
        if (profile != null && profile2 != null && !PunchyTuningConfig.samePhysicsDefinition(profile, profile2)) {
            return null;
        }
        boolean bl2 = bl = profile3.physicsMode != null && profile3.physicsMode != ToolTuning.PhysicsMode.NONE;
        if (!bl && profile3.physicsSettings == null) {
            return null;
        }
        JsonObject jsonObject = new JsonObject();
        if (bl) {
            jsonObject.addProperty("name", profile3.physicsMode.name());
        }
        if ((physicsSettings = profile3.physicsSettings) != null) {
            if (physicsSettings.limitDeg != null) {
                jsonObject.addProperty("limitDeg", (Number)physicsSettings.limitDeg);
            }
            if (physicsSettings.limitDegUp != null) {
                jsonObject.addProperty("limitDegUp", (Number)physicsSettings.limitDegUp);
            }
            if (physicsSettings.limitDegDown != null) {
                jsonObject.addProperty("limitDegDown", (Number)physicsSettings.limitDegDown);
            }
            if (physicsSettings.force != null) {
                jsonObject.addProperty("force", (Number)physicsSettings.force);
            }
            if (physicsSettings.speed != null) {
                jsonObject.addProperty("speed", (Number)physicsSettings.speed);
            }
            if (physicsSettings.cameraSens != null) {
                jsonObject.addProperty("cameraSens", (Number)physicsSettings.cameraSens);
            }
            if (physicsSettings.gravity != null) {
                jsonObject.addProperty("gravity", (Number)physicsSettings.gravity);
            }
            if (physicsSettings.motionSens != null) {
                jsonObject.addProperty("motionSens", (Number)physicsSettings.motionSens);
            }
            if (physicsSettings.invert != null) {
                jsonObject.addProperty("invert", physicsSettings.invert);
            }
            if (physicsSettings.handSpace != null) {
                jsonObject.addProperty("handSpace", physicsSettings.handSpace);
            }
            if (physicsSettings.enableCreaturePhysics != null) {
                jsonObject.addProperty("enableCreaturePhysics", physicsSettings.enableCreaturePhysics);
            }
        }
        if (jsonObject.entrySet().isEmpty()) {
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add((JsonElement)jsonObject);
        return jsonArray;
    }

    private static boolean samePhysicsDefinition(ToolTuning.Profile profile, ToolTuning.Profile profile2) {
        if (profile == profile2) {
            return true;
        }
        if (profile == null || profile2 == null) {
            return false;
        }
        if (profile.physicsMode != profile2.physicsMode) {
            return false;
        }
        ToolTuning.PhysicsSettings physicsSettings = profile.physicsSettings;
        ToolTuning.PhysicsSettings physicsSettings2 = profile2.physicsSettings;
        if (physicsSettings == physicsSettings2) {
            return true;
        }
        return physicsSettings != null && physicsSettings2 != null ? Objects.equals(physicsSettings.limitDeg, physicsSettings2.limitDeg) && Objects.equals(physicsSettings.limitDegUp, physicsSettings2.limitDegUp) && Objects.equals(physicsSettings.limitDegDown, physicsSettings2.limitDegDown) && Objects.equals(physicsSettings.force, physicsSettings2.force) && Objects.equals(physicsSettings.speed, physicsSettings2.speed) && Objects.equals(physicsSettings.cameraSens, physicsSettings2.cameraSens) && Objects.equals(physicsSettings.gravity, physicsSettings2.gravity) && Objects.equals(physicsSettings.motionSens, physicsSettings2.motionSens) && Objects.equals(physicsSettings.invert, physicsSettings2.invert) && Objects.equals(physicsSettings.handSpace, physicsSettings2.handSpace) && Objects.equals(physicsSettings.enableCreaturePhysics, physicsSettings2.enableCreaturePhysics) : false;
    }

    private static Data computeDelta(Data data, Data data2) {
        if (data == null) {
            return data2;
        }
        Data delta = new Data();
        boolean changed = false;
        delta.globalItem = PunchyTuningConfig.diffSideSet(data2.globalItem, data.globalItem);
        changed |= delta.globalItem != null;
        delta.globalArm = PunchyTuningConfig.diffSideSet(data2.globalArm, data.globalArm);
        changed |= delta.globalArm != null;

        HashMap<String, GroupEntry> itemDelta = new HashMap<>();
        if (data2.item != null) {
            for (VanillaFirstPersonItemLayer.ToolKind kind : VanillaFirstPersonItemLayer.ToolKind.values()) {
                GroupEntry current = data2.item.get(kind.name());
                GroupEntry baseline = data.item != null ? data.item.get(kind.name()) : null;
                if (current == null) {
                    continue;
                }
                SideSet transforms = PunchyTuningConfig.diffSideSet(current.transforms, baseline != null ? baseline.transforms : null);
                SideSet armTransforms = PunchyTuningConfig.diffSideSet(current.armTransforms, baseline != null ? baseline.armTransforms : null);
                if (transforms == null && armTransforms == null) continue;
                GroupEntry groupDelta = new GroupEntry();
                groupDelta.transforms = transforms;
                groupDelta.armTransforms = armTransforms;
                itemDelta.put(kind.name(), groupDelta);
                changed = true;
            }
        }
        delta.item = itemDelta.isEmpty() ? null : itemDelta;

        HashMap<String, SideSet> armMeshDelta = new HashMap<>();
        if (data2.armMesh != null) {
            SideSet current = data2.armMesh.get("global");
            SideSet baseline = data.armMesh != null ? data.armMesh.get("global") : null;
            SideSet difference = PunchyTuningConfig.diffSideSet(current, baseline);
            if (difference != null) {
                armMeshDelta.put("global", difference);
                changed = true;
            }
        }
        delta.armMesh = armMeshDelta.isEmpty() ? null : armMeshDelta;
        delta.chestProxy = nonEmpty(PunchyTuningConfig.diffPoseMap(data2.chestProxy, data.chestProxy));
        delta.fluidProxy = nonEmpty(PunchyTuningConfig.diffPoseMap(data2.fluidProxy, data.fluidProxy));
        delta.enchantParticleProxy = nonEmpty(PunchyTuningConfig.diffPoseMap(data2.enchantParticleProxy, data.enchantParticleProxy));
        delta.paddleProxy = nonEmpty(PunchyTuningConfig.diffPoseMap(data2.paddleProxy, data.paddleProxy));
        delta.fishingLineProxy = nonEmpty(PunchyTuningConfig.diffPoseMap(data2.fishingLineProxy, data.fishingLineProxy));
        changed |= delta.chestProxy != null || delta.fluidProxy != null || delta.enchantParticleProxy != null || delta.paddleProxy != null || delta.fishingLineProxy != null;

        HashMap<String, SpecificEntry> specificDelta = new HashMap<>();
        Map<String, SpecificEntry> currentSpecifics = data2.itemSpecific != null ? data2.itemSpecific : Collections.emptyMap();
        Map<String, SpecificEntry> baselineSpecifics = data.itemSpecific != null ? data.itemSpecific : Collections.emptyMap();
        for (Map.Entry<String, SpecificEntry> currentEntry : currentSpecifics.entrySet()) {
            String key = currentEntry.getKey();
            SpecificEntry current = currentEntry.getValue();
            SpecificEntry baseline = baselineSpecifics.get(key);
            if (current == null || baseline == null && PunchyTuningConfig.isRedundantResolvedSpecificEntry(key, current)) continue;
            JsonElement physics = baseline == null ? PunchyTuningConfig.copyJson(current.physics) : PunchyTuningConfig.diffJson(current.physics, baseline.physics);
            Boolean disableTilt = baseline == null ? current.disableSprintLateralTilt : PunchyTuningConfig.diffNullable(current.disableSprintLateralTilt, baseline.disableSprintLateralTilt);
            Boolean keepArm = baseline == null ? current.keepArmTuning : PunchyTuningConfig.diffNullable(current.keepArmTuning, baseline.keepArmTuning);
            Boolean keepTriggerArm = baseline == null ? current.keepTriggerArmTuning : PunchyTuningConfig.diffNullable(current.keepTriggerArmTuning, baseline.keepTriggerArmTuning);
            Boolean keepLeftArm = baseline == null ? current.keepArmTuningL : PunchyTuningConfig.diffNullable(current.keepArmTuningL, baseline.keepArmTuningL);
            Boolean keepRightArm = baseline == null ? current.keepArmTuningR : PunchyTuningConfig.diffNullable(current.keepArmTuningR, baseline.keepArmTuningR);
            SideSet transforms = baseline == null ? PunchyTuningConfig.copySideSet(current.transforms) : PunchyTuningConfig.diffSideSet(current.transforms, baseline.transforms);
            SideSet armTransforms = baseline == null ? PunchyTuningConfig.copySideSet(current.armTransforms) : PunchyTuningConfig.diffSideSet(current.armTransforms, baseline.armTransforms);
            boolean kindChanged = baseline == null ? current.kind != null : !Objects.equals(current.kind, baseline.kind);
            boolean armKindChanged = baseline == null ? current.armKind != null : !Objects.equals(current.armKind, baseline.armKind);
            if (transforms == null && armTransforms == null && !kindChanged && !armKindChanged && physics == null && disableTilt == null && keepArm == null && keepTriggerArm == null && keepLeftArm == null && keepRightArm == null) continue;
            SpecificEntry difference = new SpecificEntry();
            if (kindChanged) {
                difference.kind = current.kind;
            }
            if (armKindChanged) {
                difference.armKind = current.armKind;
            }
            if (physics != null) {
                difference.physics = physics;
            }
            difference.disableSprintLateralTilt = disableTilt;
            difference.keepArmTuning = keepArm;
            difference.keepTriggerArmTuning = keepTriggerArm;
            difference.keepArmTuningL = keepLeftArm;
            difference.keepArmTuningR = keepRightArm;
            difference.transforms = transforms;
            difference.armTransforms = armTransforms;
            specificDelta.put(key, difference);
            changed = true;
        }
        delta.itemSpecific = specificDelta.isEmpty() ? null : specificDelta;
        PunchyTuningConfig.pruneEmptyCollections(delta);
        return !changed && delta.itemSpecific == null && delta.item == null && delta.globalItem == null && delta.globalArm == null ? new Data() : delta;
    }

    private static Map<String, Pose> nonEmpty(Map<String, Pose> poses) {
        return poses == null || poses.isEmpty() ? null : poses;
    }

    private static boolean isRedundantResolvedSpecificEntry(String string, SpecificEntry specificEntry) {
        if (specificEntry != null && !PunchyTuningConfig.hasExplicitSpecificMetadata(specificEntry)) {
            String string2;
            class_2960 class_29602 = class_2960.method_12829((String)string);
            VanillaFirstPersonItemLayer.ToolKind toolKind = specificEntry.kind != null ? PunchyTuningConfig.parseItemKind(specificEntry.kind) : PunchyTuningConfig.resolveCurrentItemKind(class_29602);
            PlayerArmModelLayer.ToolKind toolKind2 = specificEntry.armKind != null ? PunchyTuningConfig.parseArmKind(specificEntry.armKind) : PunchyTuningConfig.resolveCurrentArmKind(class_29602);
            String string3 = toolKind != null ? toolKind.name() : null;
            String string4 = string2 = toolKind2 != null ? toolKind2.name() : null;
            if (!Objects.equals(specificEntry.kind, string3)) {
                return false;
            }
            if (!Objects.equals(specificEntry.armKind, string2)) {
                return false;
            }
            SideSet sideSet = PunchyTuningConfig.captureResolvedSpecificItemBase(toolKind);
            if (!PunchyTuningConfig.isSideSetEqual(specificEntry.transforms, sideSet)) {
                return false;
            }
            SideSet sideSet2 = PunchyTuningConfig.captureResolvedSpecificArmBase(toolKind2);
            return PunchyTuningConfig.isSideSetEqual(specificEntry.armTransforms, sideSet2);
        }
        return false;
    }

    private static VanillaFirstPersonItemLayer.ToolKind parseItemKind(String string) {
        if (string != null && !string.isBlank()) {
            try {
                return VanillaFirstPersonItemLayer.ToolKind.valueOf(string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        return null;
    }

    private static PlayerArmModelLayer.ToolKind parseArmKind(String string) {
        if (string != null && !string.isBlank()) {
            try {
                return PlayerArmModelLayer.ToolKind.valueOf(string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        return null;
    }

    private static SideSet captureResolvedSpecificItemBase(VanillaFirstPersonItemLayer.ToolKind toolKind) {
        if (toolKind == null) {
            return null;
        }
        SideSet sideSet = new SideSet();
        sideSet.right = Pose.fromTool(ToolTuning.snapshot(toolKind, false));
        sideSet.left = Pose.fromTool(ToolTuning.snapshot(toolKind, true));
        return sideSet.right == null && sideSet.left == null ? null : sideSet;
    }

    private static SideSet captureResolvedSpecificArmBase(PlayerArmModelLayer.ToolKind toolKind) {
        if (toolKind == null) {
            return null;
        }
        SideSet sideSet = new SideSet();
        ArmToolTuning.Profile profile = ArmToolTuning.get(toolKind, false);
        ArmToolTuning.Profile profile2 = ArmToolTuning.get(toolKind, true);
        if (profile != null) {
            sideSet.right = Pose.fromArm(PunchyTuningConfig.normalizeSpecificArmProfileForSerialization(toolKind, false, profile));
        }
        if (profile2 != null) {
            sideSet.left = Pose.fromArm(PunchyTuningConfig.normalizeSpecificArmProfileForSerialization(toolKind, true, profile2));
        }
        return sideSet.right == null && sideSet.left == null ? null : sideSet;
    }

    private static boolean hasExplicitSpecificMetadata(SpecificEntry specificEntry) {
        return specificEntry.renamed != null || specificEntry.model != null || specificEntry.customModelData != null || specificEntry.animation != null || specificEntry.customAnimation != null && !specificEntry.customAnimation.isEmpty() || specificEntry.customItemAnimations != null && !specificEntry.customItemAnimations.isEmpty() || specificEntry.betterCombat != null && !specificEntry.betterCombat.isEmpty() || specificEntry.physics != null && !specificEntry.physics.isJsonNull() || specificEntry.particleEmitters != null && !specificEntry.particleEmitters.isJsonNull() || specificEntry.particle != null && !specificEntry.particle.isJsonNull() || specificEntry.particles != null && !specificEntry.particles.isJsonNull() || specificEntry.particleEffects != null && !specificEntry.particleEffects.isJsonNull() || specificEntry.particleEmission != null && !specificEntry.particleEmission.isJsonNull() || specificEntry.disableSprintLateralTilt != null || specificEntry.keepArmTuning != null || specificEntry.keepTriggerArmTuning != null || specificEntry.keepArmTuningL != null || specificEntry.keepArmTuningR != null;
    }

    private static SideSet diffSideSet(SideSet sideSet, SideSet sideSet2) {
        if (sideSet == null) {
            return null;
        }
        SideSet sideSet3 = new SideSet();
        if (sideSet.right != null) {
            sideSet3.right = PunchyTuningConfig.diffPose(sideSet.right, sideSet2 != null ? sideSet2.right : null);
        }
        if (sideSet.left != null) {
            sideSet3.left = PunchyTuningConfig.diffPose(sideSet.left, sideSet2 != null ? sideSet2.left : null);
        }
        return sideSet3.right == null && sideSet3.left == null ? null : sideSet3;
    }

    private static Map<String, Pose> diffPoseMap(Map<String, Pose> map, Map<String, Pose> map2) {
        if (map == null) {
            return null;
        }
        HashMap<String, Pose> hashMap = new HashMap<String, Pose>();
        for (Map.Entry<String, Pose> entry : map.entrySet()) {
            Pose pose;
            Pose pose2;
            Pose pose3;
            if (entry == null || (pose3 = entry.getValue()) == null || (pose2 = PunchyTuningConfig.diffPose(pose3, pose = map2 != null ? map2.get(entry.getKey()) : null)) == null) continue;
            hashMap.put(entry.getKey(), pose2);
        }
        return hashMap.isEmpty() ? null : hashMap;
    }

    private static JsonElement diffJson(JsonElement jsonElement, JsonElement jsonElement2) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        return jsonElement2 != null && !jsonElement2.isJsonNull() && jsonElement.equals(jsonElement2) ? null : jsonElement.deepCopy();
    }

    private static JsonElement copyJson(JsonElement jsonElement) {
        return jsonElement != null && !jsonElement.isJsonNull() ? jsonElement.deepCopy() : null;
    }

    private static <T> T diffNullable(T t, T t2) {
        return (T)(!Objects.equals(t, t2) ? t : null);
    }

    private static SpecificEntry copySpecificEntryForProfile(SpecificEntry specificEntry) {
        if (specificEntry == null) {
            return null;
        }
        SpecificEntry specificEntry2 = new SpecificEntry();
        specificEntry2.kind = specificEntry.kind;
        specificEntry2.armKind = specificEntry.armKind;
        specificEntry2.renamed = specificEntry.renamed;
        specificEntry2.model = specificEntry.model;
        specificEntry2.customModelData = specificEntry.customModelData;
        specificEntry2.animation = specificEntry.animation;
        specificEntry2.customAnimation = specificEntry.customAnimation;
        specificEntry2.customItemAnimations = specificEntry.customItemAnimations;
        specificEntry2.particleEmitters = PunchyTuningConfig.copyJson(specificEntry.particleEmitters);
        specificEntry2.particle = PunchyTuningConfig.copyJson(specificEntry.particle);
        specificEntry2.particles = PunchyTuningConfig.copyJson(specificEntry.particles);
        specificEntry2.particleEffects = PunchyTuningConfig.copyJson(specificEntry.particleEffects);
        specificEntry2.particleEmission = PunchyTuningConfig.copyJson(specificEntry.particleEmission);
        specificEntry2.betterCombat = specificEntry.betterCombat;
        specificEntry2.physics = PunchyTuningConfig.copyJson(specificEntry.physics);
        specificEntry2.disableSprintLateralTilt = specificEntry.disableSprintLateralTilt;
        specificEntry2.keepArmTuning = specificEntry.keepArmTuning;
        specificEntry2.keepTriggerArmTuning = specificEntry.keepTriggerArmTuning;
        specificEntry2.keepArmTuningL = specificEntry.keepArmTuningL;
        specificEntry2.keepArmTuningR = specificEntry.keepArmTuningR;
        specificEntry2.transforms = PunchyTuningConfig.copySideSet(specificEntry.transforms);
        specificEntry2.armTransforms = PunchyTuningConfig.copySideSet(specificEntry.armTransforms);
        return specificEntry2;
    }

    private static ToolTuning.Profile copyToolProfile(ToolTuning.Profile profile) {
        if (profile == null) {
            return null;
        }
        ToolTuning.Profile profile2 = new ToolTuning.Profile(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ, profile.pivotX, profile.pivotY, profile.pivotZ, profile.physicsMode);
        profile2.disableSprintLateralTilt = profile.disableSprintLateralTilt;
        profile2.keepArmTuning = profile.keepArmTuning;
        profile2.keepTriggerArmTuning = profile.keepTriggerArmTuning;
        if (profile.physicsSettings != null) {
            profile2.physicsSettings = profile.physicsSettings.copy();
        }
        return profile2;
    }

    private static Pose diffPose(Pose pose, Pose pose2) {
        if (pose == null) {
            return null;
        }
        return pose2 != null && PunchyTuningConfig.poseEquals(pose, pose2) ? null : PunchyTuningConfig.copyPose(pose);
    }

    private static Pose copyPose(Pose pose) {
        if (pose == null) {
            return null;
        }
        Pose pose2 = new Pose(pose.scale, pose.offX, pose.offY, pose.offZ, pose.rotX, pose.rotY, pose.rotZ, pose.pivotX, pose.pivotY, pose.pivotZ, pose.physics);
        pose2.disableSprintLateralTilt = pose.disableSprintLateralTilt;
        pose2.keepArmTuning = pose.keepArmTuning;
        pose2.keepTriggerArmTuning = pose.keepTriggerArmTuning;
        pose2.keepArmTuningL = pose.keepArmTuningL;
        pose2.keepArmTuningR = pose.keepArmTuningR;
        return pose2;
    }

    private static SideSet copySideSet(SideSet sideSet) {
        if (sideSet == null) {
            return null;
        }
        SideSet sideSet2 = new SideSet();
        sideSet2.right = PunchyTuningConfig.copyPose(sideSet.right);
        sideSet2.left = PunchyTuningConfig.copyPose(sideSet.left);
        return sideSet2.right == null && sideSet2.left == null ? null : sideSet2;
    }

    private static boolean poseEquals(Pose pose, Pose pose2) {
        if (pose == pose2) {
            return true;
        }
        return pose != null && pose2 != null ? PunchyTuningConfig.nearlyEqual(pose.scale, pose2.scale) && PunchyTuningConfig.nearlyEqual(pose.offX, pose2.offX) && PunchyTuningConfig.nearlyEqual(pose.offY, pose2.offY) && PunchyTuningConfig.nearlyEqual(pose.offZ, pose2.offZ) && PunchyTuningConfig.nearlyEqual(pose.rotX, pose2.rotX) && PunchyTuningConfig.nearlyEqual(pose.rotY, pose2.rotY) && PunchyTuningConfig.nearlyEqual(pose.rotZ, pose2.rotZ) && PunchyTuningConfig.nearlyEqual(pose.pivotX, pose2.pivotX) && PunchyTuningConfig.nearlyEqual(pose.pivotY, pose2.pivotY) && PunchyTuningConfig.nearlyEqual(pose.pivotZ, pose2.pivotZ) && Objects.equals(pose.physics, pose2.physics) && Objects.equals(pose.disableSprintLateralTilt, pose2.disableSprintLateralTilt) && Objects.equals(pose.keepArmTuning, pose2.keepArmTuning) && Objects.equals(pose.keepTriggerArmTuning, pose2.keepTriggerArmTuning) && Objects.equals(pose.keepArmTuningL, pose2.keepArmTuningL) && Objects.equals(pose.keepArmTuningR, pose2.keepArmTuningR) : false;
    }

    private static boolean nearlyEqual(float f, float f2) {
        return Math.abs(f - f2) <= 1.0E-6f;
    }

    private static Boolean resolveSideKeepArmTuning(Boolean bl, Boolean bl2) {
        return bl2 != null ? bl2 : bl;
    }

    private static void pruneEmptyCollections(Data data) {
        if (data.item != null && data.item.isEmpty()) {
            data.item = null;
        }
        if (data.armMesh != null && data.armMesh.isEmpty()) {
            data.armMesh = null;
        }
        if (data.chestProxy != null && data.chestProxy.isEmpty()) {
            data.chestProxy = null;
        }
        if (data.fluidProxy != null && data.fluidProxy.isEmpty()) {
            data.fluidProxy = null;
        }
        if (data.enchantParticleProxy != null && data.enchantParticleProxy.isEmpty()) {
            data.enchantParticleProxy = null;
        }
        if (data.paddleProxy != null && data.paddleProxy.isEmpty()) {
            data.paddleProxy = null;
        }
        if (data.fishingLineProxy != null && data.fishingLineProxy.isEmpty()) {
            data.fishingLineProxy = null;
        }
        if (data.itemSpecific != null && data.itemSpecific.isEmpty()) {
            data.itemSpecific = null;
        }
    }

    private static boolean isSideSetEqual(SideSet sideSet, SideSet sideSet2) {
        if (sideSet == sideSet2) {
            return true;
        }
        return sideSet != null && sideSet2 != null ? PunchyTuningConfig.poseEquals(sideSet.right, sideSet2.right) && PunchyTuningConfig.poseEquals(sideSet.left, sideSet2.left) : false;
    }

    private static boolean isGroupEntryEqual(GroupEntry groupEntry, GroupEntry groupEntry2) {
        if (groupEntry == groupEntry2) {
            return true;
        }
        return groupEntry != null && groupEntry2 != null ? PunchyTuningConfig.isSideSetEqual(groupEntry.transforms, groupEntry2.transforms) && PunchyTuningConfig.isSideSetEqual(groupEntry.armTransforms, groupEntry2.armTransforms) && Objects.equals(groupEntry.disableSprintLateralTilt, groupEntry2.disableSprintLateralTilt) && Objects.equals(groupEntry.keepArmTuning, groupEntry2.keepArmTuning) && Objects.equals(groupEntry.keepTriggerArmTuning, groupEntry2.keepTriggerArmTuning) && Objects.equals(groupEntry.keepArmTuningL, groupEntry2.keepArmTuningL) && Objects.equals(groupEntry.keepArmTuningR, groupEntry2.keepArmTuningR) : false;
    }

    private static boolean isDataEmpty(Data data) {
        if (data == null) {
            return true;
        }
        boolean bl = (data.globalItem == null || data.globalItem.right == null && data.globalItem.left == null) && (data.globalArm == null || data.globalArm.right == null && data.globalArm.left == null);
        boolean bl2 = !(data.item != null && !data.item.isEmpty() || data.itemSpecific != null && !data.itemSpecific.isEmpty() || data.chestProxy != null && !data.chestProxy.isEmpty() || data.fluidProxy != null && !data.fluidProxy.isEmpty() || data.enchantParticleProxy != null && !data.enchantParticleProxy.isEmpty() || data.paddleProxy != null && !data.paddleProxy.isEmpty() || data.fishingLineProxy != null && !data.fishingLineProxy.isEmpty());
        return bl && bl2;
    }

    private static void stripRedundantEntries(Data data) {
        if (data != null && BASELINE_STATE != null) {
            if (PunchyTuningConfig.isSideSetEqual(data.globalItem, PunchyTuningConfig.BASELINE_STATE.globalItem)) {
                data.globalItem = null;
            }
            if (PunchyTuningConfig.isSideSetEqual(data.globalArm, PunchyTuningConfig.BASELINE_STATE.globalArm)) {
                data.globalArm = null;
            }
            if (data.item != null) {
                Map<String, GroupEntry> baseline = PunchyTuningConfig.BASELINE_STATE.item;
                data.item.entrySet().removeIf(entry -> PunchyTuningConfig.isGroupEntryEqual(entry.getValue(), baseline != null ? baseline.get(entry.getKey()) : null));
                if (data.item.isEmpty()) {
                    data.item = null;
                }
            }
            if (data.armMesh != null) {
                Map<String, SideSet> baseline = PunchyTuningConfig.BASELINE_STATE.armMesh;
                data.armMesh.entrySet().removeIf(entry -> PunchyTuningConfig.isSideSetEqual(entry.getValue(), baseline != null ? baseline.get(entry.getKey()) : null));
                if (data.armMesh.isEmpty()) {
                    data.armMesh = null;
                }
            }
            if (data.chestProxy != null) {
                Map<String, Pose> baseline = PunchyTuningConfig.BASELINE_STATE.chestProxy;
                data.chestProxy.entrySet().removeIf(entry -> PunchyTuningConfig.poseEquals(entry.getValue(), baseline != null ? baseline.get(entry.getKey()) : null));
                if (data.chestProxy.isEmpty()) {
                    data.chestProxy = null;
                }
            }
            if (data.fluidProxy != null) {
                Map<String, Pose> baseline = PunchyTuningConfig.BASELINE_STATE.fluidProxy;
                data.fluidProxy.entrySet().removeIf(entry -> PunchyTuningConfig.poseEquals(entry.getValue(), baseline != null ? baseline.get(entry.getKey()) : null));
                if (data.fluidProxy.isEmpty()) {
                    data.fluidProxy = null;
                }
            }
            if (data.enchantParticleProxy != null) {
                Map<String, Pose> baseline = PunchyTuningConfig.BASELINE_STATE.enchantParticleProxy;
                data.enchantParticleProxy.entrySet().removeIf(entry -> PunchyTuningConfig.poseEquals(entry.getValue(), baseline != null ? baseline.get(entry.getKey()) : null));
                if (data.enchantParticleProxy.isEmpty()) {
                    data.enchantParticleProxy = null;
                }
            }
            if (data.paddleProxy != null) {
                Map<String, Pose> baseline = PunchyTuningConfig.BASELINE_STATE.paddleProxy;
                data.paddleProxy.entrySet().removeIf(entry -> PunchyTuningConfig.poseEquals(entry.getValue(), baseline != null ? baseline.get(entry.getKey()) : null));
                if (data.paddleProxy.isEmpty()) {
                    data.paddleProxy = null;
                }
            }
            if (data.fishingLineProxy != null) {
                Map<String, Pose> baseline = PunchyTuningConfig.BASELINE_STATE.fishingLineProxy;
                data.fishingLineProxy.entrySet().removeIf(entry -> PunchyTuningConfig.poseEquals(entry.getValue(), baseline != null ? baseline.get(entry.getKey()) : null));
                if (data.fishingLineProxy.isEmpty()) {
                    data.fishingLineProxy = null;
                }
            }
            if (data.itemSpecific != null) {
                Map<String, SpecificEntry> map = PunchyTuningConfig.BASELINE_STATE.itemSpecific;
                data.itemSpecific.entrySet().removeIf(entry -> {
                    SpecificEntry specificEntry = map != null ? (SpecificEntry)map.get(entry.getKey()) : null;
                    String string = specificEntry != null ? specificEntry.kind : null;
                    boolean bl = Objects.equals(((SpecificEntry)entry.getValue()).kind, string);
                    string = specificEntry != null ? specificEntry.armKind : null;
                    boolean bl2 = Objects.equals(((SpecificEntry)entry.getValue()).armKind, string);
                    string = specificEntry != null ? specificEntry.renamed : null;
                    boolean bl3 = Objects.equals(((SpecificEntry)entry.getValue()).renamed, string);
                    Boolean bl4 = specificEntry != null ? specificEntry.disableSprintLateralTilt : null;
                    boolean bl5 = Objects.equals(((SpecificEntry)entry.getValue()).disableSprintLateralTilt, bl4);
                    Boolean bl6 = specificEntry != null ? specificEntry.keepArmTuning : null;
                    boolean bl7 = Objects.equals(((SpecificEntry)entry.getValue()).keepArmTuning, bl6);
                    Boolean bl8 = specificEntry != null ? specificEntry.keepTriggerArmTuning : null;
                    boolean bl9 = Objects.equals(((SpecificEntry)entry.getValue()).keepTriggerArmTuning, bl8);
                    Boolean bl10 = specificEntry != null ? specificEntry.keepArmTuningL : null;
                    boolean bl11 = Objects.equals(((SpecificEntry)entry.getValue()).keepArmTuningL, bl10);
                    Boolean bl12 = specificEntry != null ? specificEntry.keepArmTuningR : null;
                    boolean bl13 = Objects.equals(((SpecificEntry)entry.getValue()).keepArmTuningR, bl12);
                    SideSet sideSet = specificEntry != null ? specificEntry.transforms : null;
                    boolean bl14 = PunchyTuningConfig.isSideSetEqual(((SpecificEntry)entry.getValue()).transforms, sideSet);
                    SideSet sideSet2 = specificEntry != null ? specificEntry.armTransforms : null;
                    boolean bl15 = PunchyTuningConfig.isSideSetEqual(((SpecificEntry)entry.getValue()).armTransforms, sideSet2);
                    return bl && bl2 && bl3 && bl5 && bl7 && bl9 && bl11 && bl13 && bl14 && bl15;
                });
                if (data.itemSpecific.isEmpty()) {
                    data.itemSpecific = null;
                }
            }
            PunchyTuningConfig.pruneEmptyCollections(data);
        }
    }

    private static void saveData(Data data) {
        File file = PunchyTuningConfig.getFile();
        File file2 = file.getParentFile();
        if (file2 != null && !file2.exists()) {
            file2.mkdirs();
        }
        try (FileWriter fileWriter = new FileWriter(file);){
            GSON.toJson((Object)data, (Appendable)fileWriter);
        }
        catch (Exception exception) {
        }
    }

    private static void saveCompatData(String string, Data data) {
        Path path = PunchyTuningConfig.getCompatDirectory();
        try {
            Files.createDirectories(path, new FileAttribute[0]);
            Path path2 = path.resolve(string + ".json");
            try (FileWriter fileWriter = new FileWriter(path2.toFile());){
                GSON.toJson((Object)data, (Appendable)fileWriter);
            }
            PunchyTuningConfig.mirrorCompatResource(string, path2);
        }
        catch (Exception exception) {
        }
    }

    private static void mirrorCompatResource(String string, Path path) {
        if ("items_compat".equals(string) && path != null) {
            try {
                Path path2 = Path.of("", new String[0]).toAbsolutePath();
                Path path3 = path2.resolve("src");
                if (!Files.exists(path3, new LinkOption[0])) {
                    return;
                }
                Path path4 = path3.resolve("main").resolve("resources").resolve("assets").resolve("minecraft").resolve("punchy").resolve("compat");
                Files.createDirectories(path4, new FileAttribute[0]);
                Path path5 = path4.resolve(path.getFileName());
                Files.copy(path, path5, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (Exception exception) {
            }
        }
    }

    private static void applyCompatOverlays(class_3300 class_33002) {
        Data data = PunchyTuningConfig.buildBaseDefaults(class_33002);
        DEFAULTS_RESOURCE_STATE = PunchyTuningConfig.copyData(data);
        if (data != null) {
            PunchyTuningConfig.applyData(PunchyTuningConfig.copyData(data));
        }
        Set<class_2960> set = PunchyTuningConfig.discoverCompatResourceIds(class_33002).keySet();
        MIX_COMPAT_OPTION_IDS = PunchyTuningConfig.resolveMixCompatOptionIds(class_33002, set);
        MIX_PUNCHY_OPTION_IDS = PunchyTuningConfig.discoverPunchyOptionIds(class_33002, set);
        MIX_CATALOG = PunchyTuningConfig.buildMixCatalog(class_33002, PunchyTuningConfig.discoverCompatProfilesFromResourcePacks(class_33002));
        PunchyTuningConfig.applyMixSelections();
    }

    private static void reapplyActiveProfilePostCompatOverrides() {
        Data data = PunchyTuningConfig.readActiveProfilePostCompatOverrides();
        if (data != null && !PunchyTuningConfig.isDataEmpty(data)) {
            try {
                PunchyTuningConfig.applyData(data, false);
            }
            catch (Exception exception) {
            }
        }
    }

    private static Data readActiveProfilePostCompatOverrides() {
        Path path = TuningProfileManager.getActiveProfileFilePath();
        if (path != null && Files.isRegularFile(path, new LinkOption[0])) {
            Data data;
            try (FileReader reader = new FileReader(path.toFile())) {
                data = GSON.fromJson(reader, Data.class);
            }
            catch (Exception exception) {
                return null;
            }
            if (data == null) {
                return null;
            }
            Data overrides = new Data();
            if (PunchyTuningConfig.hasEmptyHandOverrideData(data.emptyHand)) {
                overrides.emptyHand = PunchyTuningConfig.copyEmptyHandEntry(data.emptyHand);
            }
            GroupEntry emptyHandGroup = data.item != null ? data.item.get(VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND.name()) : null;
            if (PunchyTuningConfig.hasGroupOverrideData(emptyHandGroup)) {
                overrides.item = new HashMap<>();
                overrides.item.put(VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND.name(), PunchyTuningConfig.copyGroupEntry(emptyHandGroup));
            }
            if (data.itemSpecific != null && !data.itemSpecific.isEmpty()) {
                for (Map.Entry<String, SpecificEntry> entry : data.itemSpecific.entrySet()) {
                    if (entry == null || entry.getKey() == null || entry.getValue() == null || !PunchyTuningConfig.hasMixPayload(entry.getValue())) continue;
                    if (overrides.itemSpecific == null) {
                        overrides.itemSpecific = new HashMap<>();
                    }
                    overrides.itemSpecific.put(entry.getKey(), PunchyTuningConfig.copySpecificEntry(entry.getValue()));
                }
            }
            PunchyTuningConfig.pruneEmptyCollections(overrides);
            return PunchyTuningConfig.isDataEmpty(overrides) ? null : overrides;
        }
        return null;
    }

    private static boolean hasEmptyHandOverrideData(EmptyHandEntry emptyHandEntry) {
        return emptyHandEntry != null && (emptyHandEntry.transforms != null || emptyHandEntry.armTransforms != null || PunchyTuningConfig.hasAnimationPayload(emptyHandEntry.customAnimation, emptyHandEntry.customItemAnimations) || emptyHandEntry.physics != null && !emptyHandEntry.physics.isJsonNull() || emptyHandEntry.disableSprintLateralTilt != null || emptyHandEntry.keepArmTuning != null || emptyHandEntry.keepTriggerArmTuning != null || emptyHandEntry.keepArmTuningL != null || emptyHandEntry.keepArmTuningR != null || emptyHandEntry.betterCombat != null && !emptyHandEntry.betterCombat.isEmpty());
    }

    private static boolean hasGroupOverrideData(GroupEntry groupEntry) {
        return groupEntry != null && (groupEntry.transforms != null || groupEntry.armTransforms != null || PunchyTuningConfig.hasAnimationPayload(groupEntry.customAnimation, groupEntry.customItemAnimations) || groupEntry.physics != null && !groupEntry.physics.isJsonNull() || groupEntry.disableSprintLateralTilt != null || groupEntry.keepArmTuning != null || groupEntry.keepTriggerArmTuning != null || groupEntry.keepArmTuningL != null || groupEntry.keepArmTuningR != null || groupEntry.betterCombat != null && !groupEntry.betterCombat.isEmpty());
    }

    private static Data buildMergedCompatOverlay(class_3300 class_33002) {
        List<CompatResourceEntry> list = PunchyTuningConfig.collectCompatResourceEntries(class_33002);
        if (list.isEmpty()) {
            return null;
        }
        Data data = null;
        for (CompatResourceEntry compatResourceEntry : list) {
            Data data2;
            if (compatResourceEntry == null || compatResourceEntry.resource == null || (data2 = PunchyTuningConfig.readCompatResource(compatResourceEntry.resource)) == null) continue;
            data = PunchyTuningConfig.mergeDataPreferBase(data, PunchyTuningConfig.copyData(data2));
        }
        return PunchyTuningConfig.finalizeCompatData("selected", data);
    }

    private static List<CompatResourceEntry> collectCompatResourceEntries(class_3300 class_33002) {
        Map<class_2960, String> map = PunchyTuningConfig.discoverCompatResourceIds(class_33002);
        if (map.isEmpty()) {
            return Collections.emptyList();
        }
        Map<String, Integer> map2 = PunchyTuningConfig.resolveCompatPackPriorities(class_33002, map.keySet());
        ArrayList<CompatResourceEntry> arrayList = new ArrayList<CompatResourceEntry>();
        AtomicInteger atomicInteger = new AtomicInteger(map2.values().stream().mapToInt(Integer::intValue).min().orElse(0) - 1);
        int n = 0;
        for (Map.Entry<class_2960, String> entry : map.entrySet()) {
            class_2960 class_29602 = entry.getKey();
            String string = entry.getValue();
            List<class_3298> resources = PunchyTuningConfig.tryGetResourceStack(class_33002, class_29602);
            if ((resources == null || resources.isEmpty()) && class_33002 != null) {
                String path = class_29602.method_12832();
                String directory = path.contains("/") ? path.substring(0, path.lastIndexOf('/')) : path;
                Map<class_2960, List<class_3298>> stacks = PunchyTuningConfig.tryListResourceStacks(class_33002, directory);
                if (stacks != null && !stacks.isEmpty()) {
                    resources = stacks.get(class_29602);
                }
            }
            if (resources != null && !resources.isEmpty()) {
                int n2 = 0;
                for (int i = resources.size() - 1; i >= 0; --i) {
                    class_3298 class_32983 = resources.get(i);
                    if (class_32983 == null) continue;
                    int n3 = PunchyTuningConfig.getOrAllocatePackPriority(map2, class_32983.method_14480(), atomicInteger);
                    arrayList.add(new CompatResourceEntry(class_29602, string, class_32983, n3, n, n2++));
                }
                ++n;
                continue;
            }
            try {
                Optional<class_3298> resource = class_33002.method_14486(class_29602);
                if (resource.isPresent()) {
                    class_3298 class_32982 = resource.get();
                    int n4 = PunchyTuningConfig.getOrAllocatePackPriority(map2, class_32982.method_14480(), atomicInteger);
                    arrayList.add(new CompatResourceEntry(class_29602, string, class_32982, n4, n, 0));
                }
            }
            catch (Exception exception) {
            }
            ++n;
        }
        arrayList.sort(Comparator.comparingInt((CompatResourceEntry entry) -> entry.packPriority).reversed().thenComparingInt(entry -> entry.discoveryOrder).thenComparingInt(entry -> entry.stackOrder).thenComparing(entry -> entry.id.toString()));
        if (!arrayList.isEmpty()) {
            StringJoiner stringJoiner = new StringJoiner(" | ");
            for (CompatResourceEntry compatResourceEntry2 : arrayList) {
                if (compatResourceEntry2 == null || compatResourceEntry2.resource == null) continue;
                stringJoiner.add(compatResourceEntry2.resource.method_14480() + "#" + compatResourceEntry2.id.method_12832() + "(p=" + compatResourceEntry2.packPriority + ",d=" + compatResourceEntry2.discoveryOrder + ",s=" + compatResourceEntry2.stackOrder + ")");
            }
        }
        return arrayList;
    }

    private static int getOrAllocatePackPriority(Map<String, Integer> map, String string, AtomicInteger atomicInteger) {
        if (string != null && !string.isBlank()) {
            Integer n = map.get(string);
            if (n != null) {
                return n;
            }
            int n2 = atomicInteger.getAndDecrement();
            map.put(string, n2);
            return n2;
        }
        return atomicInteger.getAndDecrement();
    }

    private static Map<class_2960, String> discoverCompatResourceIds(class_3300 class_33002) {
        LinkedHashMap<class_2960, String> linkedHashMap = new LinkedHashMap<class_2960, String>();
        if (class_33002 == null) {
            return linkedHashMap;
        }
        PunchyTuningConfig.collectCompatResourceIds(class_33002, "punchy/compat", "minecraft", "punchy/compat/", linkedHashMap);
        PunchyTuningConfig.collectCompatResourceIds(class_33002, "compat", "punchy", "compat/", linkedHashMap);
        return linkedHashMap;
    }

    private static void collectCompatResourceIds(class_3300 class_33002, String string, String string2, String string3, Map<class_2960, String> map) {
        Map<class_2960, class_3298> map2 = class_33002.method_14488(string, class_29602 -> class_29602.method_12836().equals(string2) && class_29602.method_12832().startsWith(string3) && class_29602.method_12832().endsWith(".json"));
        for (class_2960 class_29603 : map2.keySet()) {
            String string4 = class_29603.method_12832();
            String string5 = string4.substring(string3.length());
            if (string5.endsWith(".json")) {
                string5 = string5.substring(0, string5.length() - 5);
            }
            if (string5.isBlank()) continue;
            map.putIfAbsent(class_29603, string5.toLowerCase(Locale.ROOT));
        }
    }

    private static Map<String, Integer> resolveCompatPackPriorities(class_3300 class_33002, Set<class_2960> set) {
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<String, Integer>();
        List<String> list = PunchyTuningConfig.getSelectedResourcePackIdsHighToLow();
        int n = list.size();
        for (String string : list) {
            if (string == null || string.isBlank()) continue;
            linkedHashMap.putIfAbsent(string, n--);
        }
        return linkedHashMap;
    }

    private static Set<String> resolveMixCompatOptionIds(class_3300 class_33002, Set<class_2960> set) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        for (CompatResourceEntry compatResourceEntry : PunchyTuningConfig.collectCompatResourceEntries(class_33002)) {
            String string;
            if (compatResourceEntry == null || compatResourceEntry.resource == null || (string = PunchyTuningConfig.normalizeMixOption(compatResourceEntry.resource.method_14480())) == null || string.isBlank() || "punchy_default".equals(string)) continue;
            linkedHashSet.add(string);
        }
        return linkedHashSet;
    }

    private static Set<String> discoverPunchyOptionIds(class_3300 class_33002, Set<class_2960> set) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        for (String string : PunchyTuningConfig.getSelectedResourcePackIdsHighToLow()) {
            String string2 = PunchyTuningConfig.normalizeMixOption(string);
            if (string2 == null || string2.isBlank() || "punchy_default".equals(string2)) continue;
            linkedHashSet.add(string2);
        }
        linkedHashSet.addAll(PunchyTuningConfig.resolveMixCompatOptionIds(class_33002, set));
        return linkedHashSet;
    }

    private static List<String> getSelectedResourcePackIds() {
        List<String> list = PunchyTuningConfig.getSelectedResourcePackIdsHighToLow();
        if (list.isEmpty()) {
            return List.of();
        }
        ArrayList<String> arrayList = new ArrayList<String>(list);
        Collections.reverse(arrayList);
        return arrayList;
    }

    private static List<String> getSelectedResourcePackIdsHighToLow() {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return List.of();
        }
        class_3300 class_33002 = class_3102.method_1478();
        if (cachedSelectedResourcePackIdsHighToLow != null && cachedSelectedResourcePackResourceManager == class_33002) {
            PunchyTuningConfig.recordSelectedPackLookup(false);
            return cachedSelectedResourcePackIdsHighToLow;
        }
        PunchyTuningConfig.recordSelectedPackLookup(true);
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            var resourcePackManager = class_3102.method_1520();
            if (resourcePackManager != null) {
                for (String string : resourcePackManager.method_29210()) {
                    if (string == null || string.isBlank()) continue;
                    arrayList.add(string);
                }
            }
        }
        catch (Exception exception) {
        }
        Collections.reverse(arrayList);
        String builtInPackId = PunchyTuningConfig.resolveActiveBuiltInPackId(class_3102);
        if (builtInPackId != null && !builtInPackId.isBlank() && !arrayList.contains(builtInPackId)) {
            arrayList.add(0, builtInPackId);
        }
        cachedSelectedResourcePackResourceManager = class_33002;
        cachedSelectedResourcePackIdsHighToLow = List.copyOf(arrayList);
        return cachedSelectedResourcePackIdsHighToLow;
    }

    private static Set<String> getActiveMixPackOptionIds() {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        for (String string : PunchyTuningConfig.getSelectedResourcePackIdsHighToLow()) {
            String string2 = PunchyTuningConfig.normalizeMixOption(string);
            if (string2 == null || string2.isBlank() || "punchy_default".equals(string2)) continue;
            linkedHashSet.add(string2);
        }
        return linkedHashSet;
    }

    private static String resolveActiveBuiltInPackId(class_310 class_3102) {
        if (class_3102 != null && class_3102.method_1478() != null) {
            class_3300 class_33002 = class_3102.method_1478();
            if (cachedActiveBuiltInPackResourceManager == class_33002) {
                return cachedActiveBuiltInPackId;
            }
            ++builtInPackLookupCount;
            try {
                class_2960 class_29602 = class_2960.method_60655((String)"minecraft", (String)"punchy/compat/default_tuning.json");
                cachedActiveBuiltInPackResourceManager = class_33002;
                cachedActiveBuiltInPackId = class_33002.method_14486(class_29602).map(class_3298::method_14480).filter(string -> string != null && !string.isBlank()).orElse(null);
                return cachedActiveBuiltInPackId;
            }
            catch (Exception exception) {
                cachedActiveBuiltInPackResourceManager = class_33002;
                cachedActiveBuiltInPackId = null;
                return null;
            }
        }
        return null;
    }

    private static void invalidateSelectedResourcePackCache() {
        if (cachedSelectedResourcePackIdsHighToLow != null || cachedActiveBuiltInPackResourceManager != null) {
            // empty if block
        }
        cachedSelectedResourcePackIdsHighToLow = null;
        cachedSelectedResourcePackResourceManager = null;
        cachedActiveBuiltInPackId = null;
        cachedActiveBuiltInPackResourceManager = null;
    }

    private static void recordSelectedPackLookup(boolean bl) {
        long l = System.nanoTime();
        if (selectedPackLookupWindowStartedNanos == 0L) {
            selectedPackLookupWindowStartedNanos = l;
        }
        ++selectedPackLookupCount;
        if (bl) {
            ++selectedPackCacheMissCount;
        }
        if (l - selectedPackLookupWindowStartedNanos >= 1000000000L) {
            if (selectedPackLookupCount > 120 || selectedPackCacheMissCount > 1 || builtInPackLookupCount > 0) {
                // empty if block
            }
            selectedPackLookupWindowStartedNanos = l;
            selectedPackLookupCount = 0;
            selectedPackCacheMissCount = 0;
            builtInPackLookupCount = 0;
        }
    }

    private static String describeSelectedPackLookupCaller() {
        StackTraceElement[] stackTraceElementArray;
        for (StackTraceElement stackTraceElement : stackTraceElementArray = Thread.currentThread().getStackTrace()) {
            String string = stackTraceElement.getClassName();
            if (string.equals(PunchyTuningConfig.class.getName()) || string.equals(Thread.class.getName())) continue;
            return stackTraceElement.toString();
        }
        return "unknown";
    }

    private static Set<String> discoverCompatProfilesFromResourcePacks(class_3300 class_33002) {
        return new LinkedHashSet<String>(PunchyTuningConfig.discoverCompatResourceIds(class_33002).values());
    }

    private static MixCatalog buildMixCatalog(class_3300 class_33002, Set<String> set) {
        MixCatalog mixCatalog = new MixCatalog();
        if (DEFAULTS_RESOURCE_STATE != null) {
            PunchyTuningConfig.collectMixSlotsFromData(mixCatalog, DEFAULTS_RESOURCE_STATE, "punchy_default");
            PunchyTuningConfig.collectMixSlotsFromData(mixCatalog, PunchyTuningConfig.copyData(DEFAULTS_RESOURCE_STATE), "pack:punchy:punchy");
        }
        if (set != null) {
            for (String string : set) {
                if (string == null || string.isBlank()) continue;
                PunchyTuningConfig.collectMixCatalogProfile(class_33002, mixCatalog, string, "minecraft", "punchy/compat/" + string + ".json");
                PunchyTuningConfig.collectMixCatalogProfile(class_33002, mixCatalog, string, "punchy", "compat/" + string + ".json");
            }
        }
        PunchyTuningConfig.finalizeMixCatalog(class_33002, mixCatalog);
        return mixCatalog;
    }

    private static void collectMixCatalogProfile(class_3300 class_33002, MixCatalog mixCatalog, String string, String string2, String string3) {
        class_2960 class_29602 = class_2960.method_60655((String)string2, (String)string3);
        List<class_3298> resources = PunchyTuningConfig.tryGetResourceStack(class_33002, class_29602);
        if ((resources == null || resources.isEmpty()) && class_33002 != null) {
            String directory = string3.contains("/") ? string3.substring(0, string3.lastIndexOf('/')) : string3;
            Map<class_2960, List<class_3298>> stacks = PunchyTuningConfig.tryListResourceStacks(class_33002, directory);
            if (stacks != null && !stacks.isEmpty()) {
                resources = stacks.get(class_29602);
            }
        }
        if (resources != null && !resources.isEmpty()) {
            for (class_3298 resource : resources) {
                Data data = resource == null ? null : PunchyTuningConfig.readCompatResource(resource);
                if (data == null) continue;
                String string4 = PunchyTuningConfig.normalizeMixOption(resource.method_14480());
                PunchyTuningConfig.collectMixSlotsFromData(mixCatalog, PunchyTuningConfig.copyData(data), string4);
            }
        } else {
            try {
                Optional<class_3298> resource = class_33002.method_14486(class_29602);
                Data data = resource.isPresent() ? PunchyTuningConfig.readCompatResource(resource.get()) : null;
                if (data != null) {
                    String string5 = PunchyTuningConfig.normalizeMixOption(resource.get().method_14480());
                    PunchyTuningConfig.collectMixSlotsFromData(mixCatalog, data, string5);
                    return;
                }
            }
            catch (Exception exception) {
            }
        }
    }

    private static void collectMixSlotsFromData(MixCatalog mixCatalog, Data data, String string2) {
        if (mixCatalog != null && data != null && string2 != null && !string2.isBlank()) {
            if (data.item != null) {
                for (Map.Entry<String, GroupEntry> entry : data.item.entrySet()) {
                    GroupEntry groupEntry = entry != null ? entry.getValue() : null;
                    VanillaFirstPersonItemLayer.ToolKind toolKind = entry != null ? PunchyTuningConfig.parseToolKind(entry.getKey()) : null;
                    if (entry == null || entry.getKey() == null || groupEntry == null || !PunchyTuningConfig.hasAnimationMixPayload(groupEntry) || toolKind == null) continue;
                    String slotId = "kind:" + toolKind.name().toLowerCase(Locale.ROOT);
                    MixSlotDef slot = mixCatalog.slots.computeIfAbsent(slotId, id -> {
                        MixSlotDef mixSlotDef = new MixSlotDef();
                        mixSlotDef.slotId = id;
                        mixSlotDef.type = MixSlotType.KIND;
                        mixSlotDef.kind = toolKind;
                        mixSlotDef.label = PunchyTuningConfig.prettySlotLabel(toolKind.name());
                        return mixSlotDef;
                    });
                    if ("punchy_default".equals(string2)) continue;
                    PunchyTuningConfig.registerMixSlotOption(slot, string2);
                    PunchyTuningConfig.registerMixSlotPayload(slot, string2, PunchyTuningConfig.payloadFromGroup(groupEntry, toolKind, string2));
                }
            }
            if (data.emptyHand != null && PunchyTuningConfig.hasAnimationMixPayload(data.emptyHand)) {
                VanillaFirstPersonItemLayer.ToolKind toolKind2 = VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND;
                String string3 = "kind:" + toolKind2.name().toLowerCase(Locale.ROOT);
                MixSlotDef emptyHandSlot = mixCatalog.slots.computeIfAbsent(string3, string -> {
                    MixSlotDef mixSlotDef = new MixSlotDef();
                    mixSlotDef.slotId = string;
                    mixSlotDef.type = MixSlotType.KIND;
                    mixSlotDef.kind = toolKind2;
                    mixSlotDef.label = PunchyTuningConfig.prettySlotLabel(toolKind2.name());
                    return mixSlotDef;
                });
                if (!"punchy_default".equals(string2)) {
                    PunchyTuningConfig.registerMixSlotOption(emptyHandSlot, string2);
                    PunchyTuningConfig.registerMixSlotPayload(emptyHandSlot, string2, PunchyTuningConfig.payloadFromEmptyHand(data.emptyHand, toolKind2, string2));
                }
            }
            if (data.itemSpecific != null) {
                for (Map.Entry<String, SpecificEntry> entry : data.itemSpecific.entrySet()) {
                    SpecificEntry specificEntry = entry != null ? entry.getValue() : null;
                    class_2960 itemId = entry != null ? PunchyTuningConfig.resolveSpecificItemId(entry.getKey()) : null;
                    if (entry == null || entry.getKey() == null || specificEntry == null || !PunchyTuningConfig.hasAnimationMixPayload(specificEntry) || itemId == null || PunchyTuningConfig.isMixSpecificHidden(itemId)) continue;
                    VanillaFirstPersonItemLayer.ToolKind itemKind = PunchyTuningConfig.parseToolKind(specificEntry.kind);
                    if (itemKind == null) {
                        itemKind = PunchyTuningConfig.resolveKindForItem(itemId);
                    }
                    if (itemKind != null) {
                        VanillaFirstPersonItemLayer.ToolKind resolvedKind = itemKind;
                        String slotId = "kind:" + resolvedKind.name().toLowerCase(Locale.ROOT);
                        MixSlotDef mixSlotDef = mixCatalog.slots.computeIfAbsent(slotId, id -> PunchyTuningConfig.createKindMixSlot(resolvedKind, id));
                        mixSlotDef.mergedSpecificItems.add(itemId);
                        if ("punchy_default".equals(string2)) continue;
                        PunchyTuningConfig.registerMixSlotOption(mixSlotDef, string2);
                        PunchyTuningConfig.registerMixSlotSpecificPayload(mixSlotDef, string2, itemId, PunchyTuningConfig.payloadFromSpecific(specificEntry, resolvedKind, string2));
                        continue;
                    }
                    String slotId = "specific:" + itemId;
                    MixSlotDef slot = mixCatalog.slots.computeIfAbsent(slotId, id -> PunchyTuningConfig.createSpecificMixSlot(itemId, id));
                    if ("punchy_default".equals(string2)) continue;
                    PunchyTuningConfig.registerMixSlotOption(slot, string2);
                    PunchyTuningConfig.registerMixSlotPayload(slot, string2, PunchyTuningConfig.payloadFromSpecific(specificEntry, null, string2));
                }
            }
        }
    }

    private static boolean isMixSpecificHidden(class_2960 class_29602) {
        return class_29602 == null ? false : MIX_HIDDEN_SPECIFIC_IDS.contains(class_29602.toString().toLowerCase(Locale.ROOT));
    }

    private static void registerMixSlotOption(MixSlotDef mixSlotDef, String string) {
        if (!(mixSlotDef == null || string == null || string.isBlank() || "punchy_default".equals(string) || mixSlotDef.optionOrder.contains(string))) {
            mixSlotDef.optionOrder.add(string);
        }
    }

    private static void finalizeMixCatalog(class_3300 class_33002, MixCatalog mixCatalog) {
        if (mixCatalog != null && !mixCatalog.slots.isEmpty()) {
            MixCatalogOrdering mixCatalogOrdering = PunchyTuningConfig.resolveMixCatalogOrdering(class_33002);
            PunchyTuningConfig.registerAnimationOnlyOptions(mixCatalog, mixCatalogOrdering, class_33002);
            for (MixSlotDef mixSlotDef : mixCatalog.slots.values()) {
                if (mixSlotDef == null || mixSlotDef.optionOrder.isEmpty()) continue;
                ArrayList<String> arrayList = new ArrayList<String>(mixSlotDef.optionOrder);
                arrayList.sort((string, string2) -> {
                    int n;
                    int n2 = mixCatalogOrdering.optionPriorities.getOrDefault(string, Integer.MIN_VALUE);
                    return n2 != (n = mixCatalogOrdering.optionPriorities.getOrDefault(string2, Integer.MIN_VALUE).intValue()) ? Integer.compare(n, n2) : string.compareTo((String)string2);
                });
                mixSlotDef.optionOrder.clear();
                mixSlotDef.optionOrder.addAll(arrayList);
                mixSlotDef.resourceOrderOption = PunchyTuningConfig.resolveResourceOrderOption(arrayList, mixCatalogOrdering);
            }
        }
    }

    private static String resolveResourceOrderOption(List<String> list, MixCatalogOrdering mixCatalogOrdering) {
        if (list != null && !list.isEmpty()) {
            if (mixCatalogOrdering != null && !mixCatalogOrdering.selectedOptionIdsByPriority.isEmpty()) {
                for (String string : mixCatalogOrdering.selectedOptionIdsByPriority) {
                    if (string == null || string.isBlank() || "punchy_default".equals(string) || !list.contains(string)) continue;
                    return string;
                }
            }
            return list.getFirst();
        }
        return null;
    }

    private static MixCatalogOrdering resolveMixCatalogOrdering(class_3300 class_33002) {
        MixCatalogOrdering mixCatalogOrdering = new MixCatalogOrdering();
        List<String> list = PunchyTuningConfig.getSelectedResourcePackIdsHighToLow();
        int n = list.size();
        for (String string : list) {
            String string2 = PunchyTuningConfig.normalizeMixOption(string);
            if (string2 == null || string2.isBlank() || "punchy_default".equals(string2)) continue;
            mixCatalogOrdering.selectedOptionIdsByPriority.add(string2);
            mixCatalogOrdering.optionPriorities.putIfAbsent(string2, n--);
        }
        return mixCatalogOrdering;
    }

    private static void registerAnimationOnlyOptions(MixCatalog mixCatalog, MixCatalogOrdering mixCatalogOrdering, class_3300 class_33002) {
        Map<String, LinkedHashMap<String, AnimationClip>> map;
        if (mixCatalog != null && mixCatalogOrdering != null && class_33002 != null && !(map = PunchyAnimationLoader.loadAllWithPackVariants(class_33002)).isEmpty()) {
            for (MixSlotDef mixSlotDef : mixCatalog.slots.values()) {
                Set<String> set;
                if (mixSlotDef == null || (set = PunchyTuningConfig.collectReferencedClipNamesForSlot(mixSlotDef)).isEmpty()) continue;
                for (String string : set) {
                    LinkedHashMap<String, AnimationClip> linkedHashMap = map.get(string);
                    if (linkedHashMap == null || linkedHashMap.isEmpty()) continue;
                    for (String string2 : linkedHashMap.keySet()) {
                        String string3 = PunchyTuningConfig.normalizeMixOption(string2);
                        if (string3 == null || string3.isBlank()) continue;
                        PunchyTuningConfig.registerMixSlotClipVariant(mixSlotDef, string3, string);
                        if ("punchy_default".equals(string3) || !MIX_PUNCHY_OPTION_IDS.isEmpty() && !MIX_PUNCHY_OPTION_IDS.contains(string3)) continue;
                        PunchyTuningConfig.registerMixSlotOption(mixSlotDef, string3);
                        mixCatalogOrdering.optionPriorities.putIfAbsent(string3, mixCatalogOrdering.optionPriorities.size());
                    }
                }
            }
        }
    }

    private static void registerMixSlotClipVariant(MixSlotDef mixSlotDef, String string2, String string3) {
        String string4;
        if (mixSlotDef != null && string2 != null && !string2.isBlank() && string3 != null && !(string4 = string3.trim().toLowerCase(Locale.ROOT)).isBlank()) {
            LinkedHashSet<String> linkedHashSet = mixSlotDef.optionClipNames.computeIfAbsent(string2, string -> new LinkedHashSet<>());
            linkedHashSet.add(string4);
        }
    }

    private static Set<String> collectReferencedClipNamesForSlot(MixSlotDef mixSlotDef) {
        if (mixSlotDef == null) {
            return Set.of();
        }
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        PunchyTuningConfig.collectPayloadClipNames(linkedHashSet, PunchyTuningConfig.baselinePayloadForSlot(mixSlotDef));
        for (MixSlotPayload object : mixSlotDef.optionPayloads.values()) {
            PunchyTuningConfig.collectPayloadClipNames(linkedHashSet, object);
        }
        if (mixSlotDef.optionSpecificPayloads != null && !mixSlotDef.optionSpecificPayloads.isEmpty()) {
            for (Map<class_2960, MixSlotPayload> map : mixSlotDef.optionSpecificPayloads.values()) {
                if (map == null || map.isEmpty()) continue;
                for (MixSlotPayload mixSlotPayload : map.values()) {
                    PunchyTuningConfig.collectPayloadClipNames(linkedHashSet, mixSlotPayload);
                }
            }
        }
        PunchyTuningConfig.collectPassiveDefaultClipNamesForSlot(mixSlotDef, linkedHashSet);
        return linkedHashSet;
    }

    private static void collectPassiveDefaultClipNamesForSlot(MixSlotDef mixSlotDef, Set<String> set) {
        if (mixSlotDef != null && set != null) {
            set.addAll(GENERIC_PASSIVE_CLIP_NAMES);
            if (mixSlotDef.kind == VanillaFirstPersonItemLayer.ToolKind.SPEAR) {
                set.addAll(SPEAR_MIX_CLIP_NAMES);
            } else if (mixSlotDef.kind == VanillaFirstPersonItemLayer.ToolKind.SWORD) {
                set.addAll(SWORD_PASSIVE_CLIP_NAMES);
            }
        }
    }

    private static void collectPayloadClipNames(Set<String> set, MixSlotPayload mixSlotPayload) {
        if (set != null && mixSlotPayload != null) {
            PunchyTuningConfig.collectEntryClipNames(set, PunchyTuningConfig.payloadCustomAnimation(mixSlotPayload));
            PunchyTuningConfig.collectEntryClipNames(set, PunchyTuningConfig.payloadCustomItemAnimations(mixSlotPayload));
        }
    }

    private static void collectEntryClipNames(Set<String> set, List<CustomAnimationEntry> list) {
        if (set != null && list != null && !list.isEmpty()) {
            for (CustomAnimationEntry customAnimationEntry : list) {
                if (customAnimationEntry == null) continue;
                PunchyTuningConfig.addClipName(set, customAnimationEntry.name);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.animation);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.start);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.looping);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.end);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.keep_looping);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.stage_1);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.stage_2);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.stage_3);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.var_1);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.var_2);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.var_3);
                PunchyTuningConfig.addClipName(set, customAnimationEntry.critical_hit);
            }
        }
    }

    private static void addClipName(Set<String> set, String string) {
        String string2;
        if (set != null && string != null && !(string2 = string.trim()).isBlank()) {
            set.add(string2);
        }
    }

    private static void registerMixSlotPayload(MixSlotDef mixSlotDef, String string, MixSlotPayload mixSlotPayload) {
        if (mixSlotDef != null && string != null && !string.isBlank() && mixSlotPayload != null) {
            MixSlotPayload mixSlotPayload2 = mixSlotDef.optionPayloads.get(string);
            mixSlotDef.optionPayloads.put(string, PunchyTuningConfig.mergeMixSlotPayload(mixSlotPayload2, mixSlotPayload));
        }
    }

    private static void registerMixSlotSpecificPayload(MixSlotDef mixSlotDef, String string2, class_2960 class_29602, MixSlotPayload mixSlotPayload) {
        if (mixSlotDef != null && string2 != null && !string2.isBlank() && class_29602 != null && mixSlotPayload != null) {
            LinkedHashMap linkedHashMap = mixSlotDef.optionSpecificPayloads.computeIfAbsent(string2, string -> new LinkedHashMap());
            MixSlotPayload mixSlotPayload2 = (MixSlotPayload)linkedHashMap.get(class_29602);
            linkedHashMap.put(class_29602, PunchyTuningConfig.mergeMixSlotPayload(mixSlotPayload2, mixSlotPayload));
        }
    }

    private static MixSlotPayload mergeMixSlotPayload(MixSlotPayload mixSlotPayload, MixSlotPayload mixSlotPayload2) {
        if (mixSlotPayload2 == null) {
            return mixSlotPayload;
        }
        if (mixSlotPayload == null) {
            MixSlotPayload mixSlotPayload3 = new MixSlotPayload();
            mixSlotPayload3.kind = mixSlotPayload2.kind;
            mixSlotPayload3.groupEntry = PunchyTuningConfig.copyGroupEntry(mixSlotPayload2.groupEntry);
            mixSlotPayload3.emptyHandEntry = PunchyTuningConfig.copyEmptyHandEntry(mixSlotPayload2.emptyHandEntry);
            mixSlotPayload3.specificEntry = PunchyTuningConfig.copySpecificEntry(mixSlotPayload2.specificEntry);
            return mixSlotPayload3;
        }
        MixSlotPayload mixSlotPayload4 = new MixSlotPayload();
        mixSlotPayload4.kind = mixSlotPayload2.kind != null ? mixSlotPayload2.kind : mixSlotPayload.kind;
        mixSlotPayload4.groupEntry = PunchyTuningConfig.mergeGroupEntry(PunchyTuningConfig.copyGroupEntry(mixSlotPayload.groupEntry), PunchyTuningConfig.copyGroupEntry(mixSlotPayload2.groupEntry));
        mixSlotPayload4.emptyHandEntry = PunchyTuningConfig.mergeEmptyHand(PunchyTuningConfig.copyEmptyHandEntry(mixSlotPayload.emptyHandEntry), PunchyTuningConfig.copyEmptyHandEntry(mixSlotPayload2.emptyHandEntry));
        mixSlotPayload4.specificEntry = PunchyTuningConfig.mergeSpecificEntry(PunchyTuningConfig.copySpecificEntry(mixSlotPayload.specificEntry), PunchyTuningConfig.copySpecificEntry(mixSlotPayload2.specificEntry));
        return mixSlotPayload4;
    }

    private static boolean hasAnimationPayload(List<CustomAnimationEntry> list, List<CustomAnimationEntry> list2) {
        return list != null && !list.isEmpty() || list2 != null && !list2.isEmpty();
    }

    private static boolean hasMixPayload(GroupEntry groupEntry) {
        return groupEntry != null && (PunchyTuningConfig.hasAnimationPayload(groupEntry.customAnimation, groupEntry.customItemAnimations) || groupEntry.transforms != null || groupEntry.armTransforms != null || groupEntry.physics != null && !groupEntry.physics.isJsonNull() || groupEntry.disableSprintLateralTilt != null || groupEntry.keepArmTuning != null || groupEntry.keepTriggerArmTuning != null || groupEntry.keepArmTuningL != null || groupEntry.keepArmTuningR != null || PunchyTuningConfig.readCompatParticleEffects(groupEntry) != null || groupEntry.betterCombat != null && !groupEntry.betterCombat.isEmpty());
    }

    private static boolean hasAnimationMixPayload(GroupEntry groupEntry) {
        return groupEntry != null && PunchyTuningConfig.hasAnimationPayload(groupEntry.customAnimation, groupEntry.customItemAnimations);
    }

    private static boolean hasMixPayload(EmptyHandEntry emptyHandEntry) {
        return emptyHandEntry != null && (PunchyTuningConfig.hasAnimationPayload(emptyHandEntry.customAnimation, emptyHandEntry.customItemAnimations) || emptyHandEntry.transforms != null || emptyHandEntry.armTransforms != null || emptyHandEntry.physics != null && !emptyHandEntry.physics.isJsonNull() || emptyHandEntry.disableSprintLateralTilt != null || emptyHandEntry.keepArmTuning != null || emptyHandEntry.keepTriggerArmTuning != null || emptyHandEntry.keepArmTuningL != null || emptyHandEntry.keepArmTuningR != null || emptyHandEntry.betterCombat != null && !emptyHandEntry.betterCombat.isEmpty());
    }

    private static boolean hasAnimationMixPayload(EmptyHandEntry emptyHandEntry) {
        return emptyHandEntry != null && PunchyTuningConfig.hasAnimationPayload(emptyHandEntry.customAnimation, emptyHandEntry.customItemAnimations);
    }

    private static boolean hasMixPayload(SpecificEntry specificEntry) {
        return specificEntry != null && (PunchyTuningConfig.hasAnimationPayload(specificEntry.customAnimation, specificEntry.customItemAnimations) || specificEntry.kind != null || specificEntry.armKind != null || specificEntry.renamed != null || specificEntry.model != null || specificEntry.customModelData != null || specificEntry.physics != null && !specificEntry.physics.isJsonNull() || specificEntry.disableSprintLateralTilt != null || specificEntry.keepArmTuning != null || specificEntry.keepTriggerArmTuning != null || specificEntry.keepArmTuningL != null || specificEntry.keepArmTuningR != null || specificEntry.animation != null && !specificEntry.animation.isEmpty() || specificEntry.transforms != null || specificEntry.armTransforms != null || PunchyTuningConfig.readCompatParticleEffects(specificEntry) != null || specificEntry.betterCombat != null && !specificEntry.betterCombat.isEmpty());
    }

    private static boolean hasAnimationMixPayload(SpecificEntry specificEntry) {
        return PunchyTuningConfig.hasMixPayload(specificEntry);
    }

    private static List<CustomAnimationEntry> payloadCustomAnimation(MixSlotPayload mixSlotPayload) {
        if (mixSlotPayload == null) {
            return null;
        }
        if (mixSlotPayload.groupEntry != null) {
            return mixSlotPayload.groupEntry.customAnimation;
        }
        if (mixSlotPayload.emptyHandEntry != null) {
            return mixSlotPayload.emptyHandEntry.customAnimation;
        }
        return mixSlotPayload.specificEntry != null ? mixSlotPayload.specificEntry.customAnimation : null;
    }

    private static List<CustomAnimationEntry> payloadCustomItemAnimations(MixSlotPayload mixSlotPayload) {
        if (mixSlotPayload == null) {
            return null;
        }
        if (mixSlotPayload.groupEntry != null) {
            return mixSlotPayload.groupEntry.customItemAnimations;
        }
        if (mixSlotPayload.emptyHandEntry != null) {
            return mixSlotPayload.emptyHandEntry.customItemAnimations;
        }
        return mixSlotPayload.specificEntry != null ? mixSlotPayload.specificEntry.customItemAnimations : null;
    }

    private static void setPayloadCustomAnimation(MixSlotPayload mixSlotPayload, List<CustomAnimationEntry> list) {
        if (mixSlotPayload != null) {
            if (mixSlotPayload.groupEntry != null) {
                mixSlotPayload.groupEntry.customAnimation = list;
            } else if (mixSlotPayload.emptyHandEntry != null) {
                mixSlotPayload.emptyHandEntry.customAnimation = list;
            } else if (mixSlotPayload.specificEntry != null) {
                mixSlotPayload.specificEntry.customAnimation = list;
            }
        }
    }

    private static void setPayloadCustomItemAnimations(MixSlotPayload mixSlotPayload, List<CustomAnimationEntry> list) {
        if (mixSlotPayload != null) {
            if (mixSlotPayload.groupEntry != null) {
                mixSlotPayload.groupEntry.customItemAnimations = list;
            } else if (mixSlotPayload.emptyHandEntry != null) {
                mixSlotPayload.emptyHandEntry.customItemAnimations = list;
            } else if (mixSlotPayload.specificEntry != null) {
                mixSlotPayload.specificEntry.customItemAnimations = list;
            }
        }
    }

    private static MixSlotPayload payloadFromGroup(GroupEntry groupEntry, VanillaFirstPersonItemLayer.ToolKind toolKind, String string) {
        MixSlotPayload mixSlotPayload = new MixSlotPayload();
        mixSlotPayload.kind = toolKind;
        mixSlotPayload.groupEntry = PunchyTuningConfig.copyGroupEntry(groupEntry);
        if (mixSlotPayload.groupEntry != null) {
            mixSlotPayload.groupEntry.customAnimation = PunchyTuningConfig.copyCustomAnimationList(groupEntry.customAnimation, string);
            mixSlotPayload.groupEntry.customItemAnimations = PunchyTuningConfig.copyCustomAnimationList(groupEntry.customItemAnimations, string);
        }
        return mixSlotPayload;
    }

    private static MixSlotPayload payloadFromEmptyHand(EmptyHandEntry emptyHandEntry, VanillaFirstPersonItemLayer.ToolKind toolKind, String string) {
        MixSlotPayload mixSlotPayload = new MixSlotPayload();
        mixSlotPayload.kind = toolKind;
        mixSlotPayload.emptyHandEntry = PunchyTuningConfig.copyEmptyHandEntry(emptyHandEntry);
        if (mixSlotPayload.emptyHandEntry != null) {
            mixSlotPayload.emptyHandEntry.customAnimation = PunchyTuningConfig.copyCustomAnimationList(emptyHandEntry.customAnimation, string);
            mixSlotPayload.emptyHandEntry.customItemAnimations = PunchyTuningConfig.copyCustomAnimationList(emptyHandEntry.customItemAnimations, string);
        }
        return mixSlotPayload;
    }

    private static MixSlotPayload payloadFromSpecific(SpecificEntry specificEntry, VanillaFirstPersonItemLayer.ToolKind toolKind, String string) {
        MixSlotPayload mixSlotPayload = new MixSlotPayload();
        mixSlotPayload.kind = toolKind;
        mixSlotPayload.specificEntry = PunchyTuningConfig.copySpecificEntry(specificEntry);
        if (mixSlotPayload.specificEntry != null) {
            mixSlotPayload.specificEntry.customAnimation = PunchyTuningConfig.copyCustomAnimationList(specificEntry.customAnimation, string);
            mixSlotPayload.specificEntry.customItemAnimations = PunchyTuningConfig.copyCustomAnimationList(specificEntry.customItemAnimations, string);
        }
        return mixSlotPayload;
    }

    private static List<CustomAnimationEntry> copyCustomAnimationList(List<CustomAnimationEntry> list, String string) {
        if (list != null && !list.isEmpty()) {
            ArrayList<CustomAnimationEntry> arrayList = new ArrayList<CustomAnimationEntry>(list.size());
            String string2 = PunchyTuningConfig.normalizeMixOption(string);
            for (CustomAnimationEntry customAnimationEntry : list) {
                if (customAnimationEntry == null) continue;
                CustomAnimationEntry customAnimationEntry2 = (CustomAnimationEntry)GSON.fromJson(GSON.toJsonTree((Object)customAnimationEntry), CustomAnimationEntry.class);
                customAnimationEntry2.mix_pack_option = string2;
                arrayList.add(customAnimationEntry2);
            }
            return arrayList.isEmpty() ? null : arrayList;
        }
        return null;
    }

    private static String summarizeEntryTypes(List<CustomAnimationEntry> list) {
        if (list != null && !list.isEmpty()) {
            LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
            for (CustomAnimationEntry customAnimationEntry : list) {
                String string = PunchyTuningConfig.normalizeEntryType(customAnimationEntry == null ? null : customAnimationEntry.type);
                if (string == null) continue;
                linkedHashSet.add(string);
            }
            return linkedHashSet.toString();
        }
        return "[]";
    }

    private static String summarizeSideSet(SideSet sideSet) {
        return sideSet == null ? "none" : "R=" + (sideSet.right != null) + ",L=" + (sideSet.left != null);
    }

    private static String summarizeGroupEntry(GroupEntry groupEntry) {
        if (groupEntry == null) {
            return "null";
        }
        List<String> list = PunchyTuningConfig.readCompatParticleEffects(groupEntry);
        return "transforms{" + PunchyTuningConfig.summarizeSideSet(groupEntry.transforms) + "} arm{" + PunchyTuningConfig.summarizeSideSet(groupEntry.armTransforms) + "} physics=" + (groupEntry.physics != null && !groupEntry.physics.isJsonNull()) + " anim=" + (groupEntry.customAnimation == null ? 0 : groupEntry.customAnimation.size()) + PunchyTuningConfig.summarizeEntryTypes(groupEntry.customAnimation) + " itemAnim=" + (groupEntry.customItemAnimations == null ? 0 : groupEntry.customItemAnimations.size()) + PunchyTuningConfig.summarizeEntryTypes(groupEntry.customItemAnimations) + " particles=" + (list == null ? 0 : list.size()) + " betterCombat=" + (groupEntry.betterCombat == null ? 0 : groupEntry.betterCombat.size()) + " flags={disable=" + groupEntry.disableSprintLateralTilt + ",keep=" + groupEntry.keepArmTuning + ",keepMain=" + groupEntry.keepTriggerArmTuning + ",keepL=" + groupEntry.keepArmTuningL + ",keepR=" + groupEntry.keepArmTuningR + "}";
    }

    private static String summarizeEmptyHandEntry(EmptyHandEntry emptyHandEntry) {
        return emptyHandEntry == null ? "null" : "transforms{" + PunchyTuningConfig.summarizeSideSet(emptyHandEntry.transforms) + "} arm{" + PunchyTuningConfig.summarizeSideSet(emptyHandEntry.armTransforms) + "} physics=" + (emptyHandEntry.physics != null && !emptyHandEntry.physics.isJsonNull()) + " anim=" + (emptyHandEntry.customAnimation == null ? 0 : emptyHandEntry.customAnimation.size()) + PunchyTuningConfig.summarizeEntryTypes(emptyHandEntry.customAnimation) + " itemAnim=" + (emptyHandEntry.customItemAnimations == null ? 0 : emptyHandEntry.customItemAnimations.size()) + PunchyTuningConfig.summarizeEntryTypes(emptyHandEntry.customItemAnimations) + " betterCombat=" + (emptyHandEntry.betterCombat == null ? 0 : emptyHandEntry.betterCombat.size()) + " flags={disable=" + emptyHandEntry.disableSprintLateralTilt + ",keep=" + emptyHandEntry.keepArmTuning + ",keepMain=" + emptyHandEntry.keepTriggerArmTuning + ",keepL=" + emptyHandEntry.keepArmTuningL + ",keepR=" + emptyHandEntry.keepArmTuningR + "}";
    }

    private static String summarizeSpecificEntry(SpecificEntry specificEntry) {
        if (specificEntry == null) {
            return "null";
        }
        List<String> list = PunchyTuningConfig.readCompatParticleEffects(specificEntry);
        return "kind=" + specificEntry.kind + " armKind=" + specificEntry.armKind + " renamed=" + specificEntry.renamed + " model=" + specificEntry.model + " cmd=" + specificEntry.customModelData + " transforms{" + PunchyTuningConfig.summarizeSideSet(specificEntry.transforms) + "} arm{" + PunchyTuningConfig.summarizeSideSet(specificEntry.armTransforms) + "} physics=" + (specificEntry.physics != null && !specificEntry.physics.isJsonNull()) + " anim=" + (specificEntry.customAnimation == null ? 0 : specificEntry.customAnimation.size()) + PunchyTuningConfig.summarizeEntryTypes(specificEntry.customAnimation) + " itemAnim=" + (specificEntry.customItemAnimations == null ? 0 : specificEntry.customItemAnimations.size()) + PunchyTuningConfig.summarizeEntryTypes(specificEntry.customItemAnimations) + " particles=" + (list == null ? 0 : list.size()) + " betterCombat=" + (specificEntry.betterCombat == null ? 0 : specificEntry.betterCombat.size()) + " flags={disable=" + specificEntry.disableSprintLateralTilt + ",keep=" + specificEntry.keepArmTuning + ",keepMain=" + specificEntry.keepTriggerArmTuning + ",keepL=" + specificEntry.keepArmTuningL + ",keepR=" + specificEntry.keepArmTuningR + "}";
    }

    private static String summarizePayload(MixSlotPayload mixSlotPayload) {
        if (mixSlotPayload == null) {
            return "null";
        }
        if (mixSlotPayload.groupEntry != null) {
            return "kind=" + String.valueOf((Object)mixSlotPayload.kind) + " group{" + PunchyTuningConfig.summarizeGroupEntry(mixSlotPayload.groupEntry) + "}";
        }
        if (mixSlotPayload.emptyHandEntry != null) {
            return "kind=" + String.valueOf((Object)mixSlotPayload.kind) + " empty{" + PunchyTuningConfig.summarizeEmptyHandEntry(mixSlotPayload.emptyHandEntry) + "}";
        }
        return mixSlotPayload.specificEntry != null ? "kind=" + String.valueOf((Object)mixSlotPayload.kind) + " specific{" + PunchyTuningConfig.summarizeSpecificEntry(mixSlotPayload.specificEntry) + "}" : "kind=" + String.valueOf((Object)mixSlotPayload.kind) + " emptyPayload=true";
    }

    private static String summarizeData(Data data) {
        StringJoiner stringJoiner;
        int n;
        if (data == null) {
            return "null";
        }
        StringJoiner stringJoiner2 = new StringJoiner("; ");
        if (data.emptyHand != null) {
            stringJoiner2.add("emptyHand{" + PunchyTuningConfig.summarizeEmptyHandEntry(data.emptyHand) + "}");
        }
        if (data.item != null && !data.item.isEmpty()) {
            n = 0;
            stringJoiner = new StringJoiner(", ", "items[", "]");
            for (Map.Entry<String, GroupEntry> entry : data.item.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                stringJoiner.add(entry.getKey() + "{" + PunchyTuningConfig.summarizeGroupEntry(entry.getValue()) + "}");
                if (++n < 6) continue;
                break;
            }
            stringJoiner2.add(stringJoiner.toString());
        }
        if (data.itemSpecific != null && !data.itemSpecific.isEmpty()) {
            n = 0;
            stringJoiner = new StringJoiner(", ", "specific[", "]");
            for (Map.Entry<String, SpecificEntry> entry : data.itemSpecific.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                stringJoiner.add(entry.getKey() + "{" + PunchyTuningConfig.summarizeSpecificEntry(entry.getValue()) + "}");
                if (++n < 6) continue;
                break;
            }
            stringJoiner2.add(stringJoiner.toString());
        }
        return stringJoiner2.length() == 0 ? "empty" : stringJoiner2.toString();
    }

    private static String prettySlotLabel(String string) {
        if (string != null && !string.isBlank()) {
            String[] stringArray = string.toLowerCase(Locale.ROOT).split("_");
            StringBuilder stringBuilder = new StringBuilder();
            for (String string2 : stringArray) {
                if (string2 == null || string2.isBlank()) continue;
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append(' ');
                }
                stringBuilder.append(Character.toUpperCase(string2.charAt(0)));
                if (string2.length() <= 1) continue;
                stringBuilder.append(string2.substring(1));
            }
            return stringBuilder.toString();
        }
        return "";
    }

    private static VanillaFirstPersonItemLayer.ToolKind parseToolKind(String string) {
        if (string != null && !string.isBlank()) {
            try {
                return VanillaFirstPersonItemLayer.ToolKind.valueOf(string.trim().toUpperCase(Locale.ROOT));
            }
            catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    private static VanillaFirstPersonItemLayer.ToolKind resolveKindForItem(class_2960 class_29602) {
        if (class_29602 == null) {
            return null;
        }
        class_1792 class_17922 = class_7923.field_41178.method_17966(class_29602).orElse(null);
        return class_17922 == null ? null : PunchyTuningConfig.resolveMixKindForStack(new class_1799((class_1935)class_17922));
    }

    private static VanillaFirstPersonItemLayer.ToolKind resolveMixKindForStack(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return VanillaFirstPersonItemLayer.ToolKind.INGREDIENT;
        }
        return SpearStateMachine.isSpearStack(class_17992) ? VanillaFirstPersonItemLayer.ToolKind.SPEAR : ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
    }

    private static String normalizeMixOption(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim().toLowerCase(Locale.ROOT);
            if ("punchy_default".equals(string2)) {
                return "punchy_default";
            }
            if (string2.startsWith("pack:")) {
                string2 = string2.substring("pack:".length());
            }
            if (PunchyTuningConfig.isPunchyDefaultPackId(string2)) {
                return "pack:punchy:punchy";
            }
            return !"vanilla".equals(string2) && !"mod_resources".equals(string2) ? "pack:" + string2 : "punchy_default";
        }
        return "punchy_default";
    }

    private static String resolveSelectedOption(MixSlotDef mixSlotDef) {
        if (mixSlotDef == null) {
            return "punchy_default";
        }
        List<String> list = PunchyTuningConfig.getMixPackSlotOptionIds(mixSlotDef.slotId);
        String string = PunchyConfig.getAnimationPackSelection(mixSlotDef.slotId);
        if (string != null && !string.isBlank()) {
            String string2 = PunchyTuningConfig.normalizeMixOption(string);
            if ("punchy_default".equals(string2)) {
                if (mixSlotDef.resourceOrderOption != null && list.contains(mixSlotDef.resourceOrderOption)) {
                    return mixSlotDef.resourceOrderOption;
                }
                for (String string3 : list) {
                    if (string3 == null || string3.isBlank() || "punchy_default".equals(string3)) continue;
                    return string3;
                }
                return "punchy_default";
            }
            if (string2 != null && list.contains(string2)) {
                return string2;
            }
            if (mixSlotDef.resourceOrderOption != null && list.contains(mixSlotDef.resourceOrderOption)) {
                return mixSlotDef.resourceOrderOption;
            }
            for (String string4 : list) {
                if (string4 == null || string4.isBlank() || "punchy_default".equals(string4)) continue;
                return string4;
            }
            return "punchy_default";
        }
        if (mixSlotDef.resourceOrderOption != null && list.contains(mixSlotDef.resourceOrderOption)) {
            return mixSlotDef.resourceOrderOption;
        }
        for (String string5 : list) {
            if (string5 == null || string5.isBlank() || "punchy_default".equals(string5)) continue;
            return string5;
        }
        return "punchy_default";
    }

    private static String resolveSelectedTypeOption(MixSlotDef mixSlotDef, String string2, String string3) {
        if (mixSlotDef != null && string2 != null) {
            String string4;
            MixSlotPayload mixSlotPayload = PunchyTuningConfig.baselinePayloadForSlot(mixSlotDef);
            boolean bl = PunchyTuningConfig.payloadHasType(mixSlotPayload, string2);
            boolean bl2 = PunchyTuningConfig.isGlobalHandType(string2);
            String string5 = PunchyConfig.getAnimationPackTypeSelection(mixSlotDef.slotId, string2);
            String string6 = string4 = string5 != null && !string5.isBlank() ? PunchyTuningConfig.normalizeMixOption(string5) : null;
            if (bl2) {
                if (string4 != null && !string4.isBlank() && !"punchy_default".equals(string4) && mixSlotDef.optionOrder.contains(string4) && PunchyTuningConfig.optionDefinesTypeExactly(mixSlotDef, string4, string2)) {
                    return string4;
                }
                if (string3 != null && !string3.isBlank() && PunchyTuningConfig.optionDefinesTypeExactly(mixSlotDef, string3, string2)) {
                    return string3;
                }
                String string7 = PunchyTuningConfig.getGlobalMixPriorityOptionId();
                return string7 != null && !string7.isBlank() ? string7 : "punchy_default";
            }
            if (string4 != null && !string4.isBlank()) {
                if ("punchy_default".equals(string4)) {
                    return bl ? "punchy_default" : "punchy_default";
                }
                if (mixSlotDef.optionOrder.contains(string4) && PunchyTuningConfig.optionSupportsType(mixSlotDef, string4, string2)) {
                    return string4;
                }
                if (string3 != null && !string3.isBlank() && !"punchy_default".equals(string3)) {
                    return string3;
                }
                return bl ? "punchy_default" : "punchy_default";
            }
            if (string3 != null && !string3.isBlank() && !"punchy_default".equals(string3) && PunchyTuningConfig.optionSupportsType(mixSlotDef, string3, string2)) {
                return string3;
            }
            String string8 = PunchyTuningConfig.resolveAnimationTypeFallbackOption(mixSlotDef, string2, string3, bl, string -> null);
            if (string8 != null && !string8.isBlank()) {
                return string8;
            }
            return string3 != null && !string3.isBlank() && !"punchy_default".equals(string3) ? string3 : "punchy_default";
        }
        return "punchy_default";
    }

    private static String resolveSelectedAnimationTypeOption(MixSlotDef mixSlotDef, String string, String string2, List<CustomAnimationEntry> list, Function<String, List<CustomAnimationEntry>> function) {
        if (mixSlotDef != null && string != null && function != null) {
            String string3;
            boolean bl = PunchyTuningConfig.listHasType(list, string);
            boolean bl2 = PunchyTuningConfig.isGlobalHandType(string);
            boolean bl3 = string2 != null && !string2.isBlank() && (PunchyTuningConfig.listHasType(function.apply(string2), string) || PunchyTuningConfig.optionSupportsType(mixSlotDef, string2, string));
            String string4 = PunchyConfig.getAnimationPackTypeSelection(mixSlotDef.slotId, string);
            String string5 = string3 = string4 != null && !string4.isBlank() ? PunchyTuningConfig.normalizeMixOption(string4) : null;
            if (bl2) {
                if (string3 != null && !string3.isBlank() && !"punchy_default".equals(string3) && mixSlotDef.optionOrder.contains(string3) && PunchyTuningConfig.listHasExactType(function.apply(string3), string)) {
                    return string3;
                }
                if (string2 != null && !string2.isBlank() && PunchyTuningConfig.listHasExactType(function.apply(string2), string)) {
                    return string2;
                }
                String string6 = PunchyTuningConfig.getGlobalMixPriorityOptionId();
                return string6 != null && !string6.isBlank() ? string6 : "punchy_default";
            }
            if (string3 != null && !string3.isBlank()) {
                if ("punchy_default".equals(string3)) {
                    return bl ? "punchy_default" : "punchy_default";
                }
                if (!mixSlotDef.optionOrder.contains(string3) || !PunchyTuningConfig.listHasType(function.apply(string3), string) && !PunchyTuningConfig.optionSupportsType(mixSlotDef, string3, string)) {
                    if (bl3 && string2 != null && !string2.isBlank()) {
                        return string2;
                    }
                    return bl ? "punchy_default" : "punchy_default";
                }
                return string3;
            }
            return bl3 && string2 != null && !string2.isBlank() ? string2 : PunchyTuningConfig.resolveAnimationTypeFallbackOption(mixSlotDef, string, string2, bl, function);
        }
        return "punchy_default";
    }

    private static String resolveAnimationTypeFallbackOption(MixSlotDef mixSlotDef, String string, String string2, boolean bl, Function<String, List<CustomAnimationEntry>> function) {
        if (mixSlotDef != null && string != null && function != null) {
            String string3;
            int n;
            ArrayList<String> arrayList = new ArrayList<String>(mixSlotDef.optionOrder);
            for (n = 0; n < arrayList.size(); ++n) {
                string3 = (String)arrayList.get(n);
                if (string3 == null || string3.isBlank() || string3.equals(string2) || !PunchyTuningConfig.listHasType(function.apply(string3), string)) continue;
                return string3;
            }
            for (n = 0; n < arrayList.size(); ++n) {
                string3 = (String)arrayList.get(n);
                if (string3 == null || string3.isBlank() || string3.equals(string2) || !PunchyTuningConfig.optionSupportsType(mixSlotDef, string3, string)) continue;
                return string3;
            }
        }
        return bl ? "punchy_default" : "punchy_default";
    }

    private static boolean optionSupportsType(MixSlotDef mixSlotDef, String string, String string2) {
        if (mixSlotDef != null && string != null && !string.isBlank() && string2 != null && !string2.isBlank()) {
            MixSlotPayload mixSlotPayload;
            MixSlotPayload mixSlotPayload2;
            return "punchy_default".equals(string) ? PunchyTuningConfig.payloadHasType(mixSlotPayload2 = PunchyTuningConfig.baselinePayloadForSlot(mixSlotDef), string2) : PunchyTuningConfig.payloadHasType(mixSlotPayload = mixSlotDef.optionPayloads.get(string), string2);
        }
        return false;
    }

    private static boolean slotOptionHasTypeFallbackClip(MixSlotDef mixSlotDef, String string, String string2) {
        if (mixSlotDef != null && string != null && !string.isBlank() && string2 != null && !string2.isBlank()) {
            List<String> list = MIX_TYPE_CLIP_ALIASES.get(string2);
            if (list != null && !list.isEmpty()) {
                Set set = mixSlotDef.optionClipNames.get(string);
                if (set != null && !set.isEmpty()) {
                    for (String string3 : list) {
                        if (string3 == null || string3.isBlank() || !set.contains(string3.toLowerCase(Locale.ROOT))) continue;
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private static String normalizeEntryType(String string) {
        return string != null && !string.isBlank() ? string.trim().toLowerCase(Locale.ROOT) : null;
    }

    private static boolean isGlobalHandType(String string) {
        return false;
    }

    private static String normalizeEntryTypeFamily(String string) {
        String string2 = PunchyTuningConfig.normalizeEntryType(string);
        if (string2 != null && !string2.isBlank()) {
            String string3 = string2.replace("_", "").replace("-", "").replace(" ", "");
            return !"mining".equals(string3) && !"miningv2".equals(string3) ? string3 : "mining";
        }
        return null;
    }

    private static boolean isSameEntryTypeFamily(String string, String string2) {
        String string3 = PunchyTuningConfig.normalizeEntryTypeFamily(string);
        String string4 = PunchyTuningConfig.normalizeEntryTypeFamily(string2);
        return string3 != null && string3.equals(string4);
    }

    private static void collectEntryTypes(Set<String> set, List<CustomAnimationEntry> list) {
        if (set != null && list != null && !list.isEmpty()) {
            for (CustomAnimationEntry customAnimationEntry : list) {
                String string = PunchyTuningConfig.normalizeEntryType(customAnimationEntry == null ? null : customAnimationEntry.type);
                if (string == null) continue;
                set.add(string);
            }
        }
    }

    private static boolean payloadHasType(MixSlotPayload mixSlotPayload, String string) {
        return mixSlotPayload != null && string != null ? PunchyTuningConfig.listHasType(PunchyTuningConfig.payloadCustomAnimation(mixSlotPayload), string) || PunchyTuningConfig.listHasType(PunchyTuningConfig.payloadCustomItemAnimations(mixSlotPayload), string) : false;
    }

    private static boolean listHasType(List<CustomAnimationEntry> list, String string) {
        if (list != null && !list.isEmpty() && string != null) {
            for (CustomAnimationEntry customAnimationEntry : list) {
                if (!PunchyTuningConfig.isSameEntryTypeFamily(string, customAnimationEntry == null ? null : customAnimationEntry.type)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static List<CustomAnimationEntry> composeCustomAnimationByType(MixSlotDef mixSlotDef, String string, List<CustomAnimationEntry> list, Function<String, List<CustomAnimationEntry>> function) {
        if (mixSlotDef != null && function != null) {
            List<CustomAnimationEntry> list2 = list == null ? List.of() : list;
            List<String> list3 = PunchyTuningConfig.getMixPackSlotEntryTypes(mixSlotDef.slotId);
            if (list3.isEmpty()) {
                return list2.isEmpty() ? null : new ArrayList<>(list2);
            }
            LinkedHashMap<String, List<CustomAnimationEntry>> linkedHashMap = new LinkedHashMap<String, List<CustomAnimationEntry>>();
            for (String string2 : list3) {
                List<CustomAnimationEntry> list4;
                List<CustomAnimationEntry> list5;
                String string3;
                String string4 = PunchyTuningConfig.normalizeEntryType(string2);
                if (string4 == null || (string3 = PunchyTuningConfig.resolveSelectedAnimationTypeOption(mixSlotDef, string4, string, list2, function)) == null || string3.isBlank() || string3.equals(string) || (list5 = PunchyTuningConfig.filterCustomAnimationByType(list4 = function.apply(string3), string4)).isEmpty()) continue;
                linkedHashMap.put(string4, list5);
            }
            return PunchyTuningConfig.mergeCustomAnimationByType(list2, linkedHashMap);
        }
        return null;
    }

    private static MixSlotPayload composePayloadByType(MixSlotDef mixSlotDef, String string2, MixSlotPayload mixSlotPayload) {
        if (mixSlotDef != null && mixSlotPayload != null) {
            MixSlotPayload mixSlotPayload2 = PunchyTuningConfig.mergeMixSlotPayload(null, mixSlotPayload);
            PunchyTuningConfig.setPayloadCustomAnimation(mixSlotPayload2, PunchyTuningConfig.composeCustomAnimationByType(mixSlotDef, string2, PunchyTuningConfig.payloadCustomAnimation(mixSlotPayload), string -> {
                MixSlotPayload selectedPayload = "punchy_default".equals(string) ? PunchyTuningConfig.baselinePayloadForSlot(mixSlotDef) : mixSlotDef.optionPayloads.get(string);
                return PunchyTuningConfig.payloadCustomAnimation(selectedPayload);
            }));
            PunchyTuningConfig.setPayloadCustomItemAnimations(mixSlotPayload2, PunchyTuningConfig.composeCustomAnimationByType(mixSlotDef, string2, PunchyTuningConfig.payloadCustomItemAnimations(mixSlotPayload), string -> {
                MixSlotPayload selectedPayload = "punchy_default".equals(string) ? PunchyTuningConfig.baselinePayloadForSlot(mixSlotDef) : mixSlotDef.optionPayloads.get(string);
                return PunchyTuningConfig.payloadCustomItemAnimations(selectedPayload);
            }));
            PunchyTuningConfig.logTypeComposition(mixSlotDef, string2, PunchyTuningConfig.payloadCustomAnimation(mixSlotPayload2), PunchyTuningConfig.payloadCustomItemAnimations(mixSlotPayload2));
            return mixSlotPayload2;
        }
        return mixSlotPayload;
    }

    private static MixSlotPayload composeMergedSpecificPayloadByType(MixSlotDef mixSlotDef, String string2, class_2960 class_29602, MixSlotPayload mixSlotPayload) {
        if (mixSlotDef != null && class_29602 != null && mixSlotPayload != null) {
            MixSlotPayload mixSlotPayload2 = PunchyTuningConfig.mergeMixSlotPayload(null, mixSlotPayload);
            PunchyTuningConfig.setPayloadCustomAnimation(mixSlotPayload2, PunchyTuningConfig.composeCustomAnimationByType(mixSlotDef, string2, PunchyTuningConfig.payloadCustomAnimation(mixSlotPayload), string -> {
                Map<class_2960, MixSlotPayload> payloads = mixSlotDef.optionSpecificPayloads.get(string);
                MixSlotPayload selectedPayload = "punchy_default".equals(string) ? PunchyTuningConfig.baselineSpecificPayloadForItem(class_29602, mixSlotDef.kind) : (payloads == null ? null : payloads.get(class_29602));
                return PunchyTuningConfig.payloadCustomAnimation(selectedPayload);
            }));
            PunchyTuningConfig.setPayloadCustomItemAnimations(mixSlotPayload2, PunchyTuningConfig.composeCustomAnimationByType(mixSlotDef, string2, PunchyTuningConfig.payloadCustomItemAnimations(mixSlotPayload), string -> {
                Map<class_2960, MixSlotPayload> payloads = mixSlotDef.optionSpecificPayloads.get(string);
                MixSlotPayload selectedPayload = "punchy_default".equals(string) ? PunchyTuningConfig.baselineSpecificPayloadForItem(class_29602, mixSlotDef.kind) : (payloads == null ? null : payloads.get(class_29602));
                return PunchyTuningConfig.payloadCustomItemAnimations(selectedPayload);
            }));
            PunchyTuningConfig.logTypeComposition(mixSlotDef, string2, PunchyTuningConfig.payloadCustomAnimation(mixSlotPayload2), PunchyTuningConfig.payloadCustomItemAnimations(mixSlotPayload2));
            return mixSlotPayload2;
        }
        return mixSlotPayload;
    }

    private static void logTypeComposition(MixSlotDef mixSlotDef, String string, List<CustomAnimationEntry> list, List<CustomAnimationEntry> list2) {
        if (mixSlotDef != null) {
            for (String string2 : PunchyTuningConfig.getMixPackSlotEntryTypes(mixSlotDef.slotId)) {
                String string3;
                String string4 = PunchyTuningConfig.normalizeEntryType(string2);
                if (string4 == null || (string3 = PunchyTuningConfig.resolveSelectedTypeOption(mixSlotDef, string4, string)) == null || string3.isBlank() || string3.equals(string)) continue;
                int n = PunchyTuningConfig.filterCustomAnimationByType(list, string4).size();
                int n2 = PunchyTuningConfig.filterCustomAnimationByType(list2, string4).size();
            }
        }
    }

    private static List<CustomAnimationEntry> filterCustomAnimationByType(List<CustomAnimationEntry> list, String string) {
        if (list != null && !list.isEmpty() && string != null) {
            ArrayList<CustomAnimationEntry> arrayList = new ArrayList<CustomAnimationEntry>();
            for (CustomAnimationEntry customAnimationEntry : list) {
                if (!PunchyTuningConfig.isSameEntryTypeFamily(string, customAnimationEntry == null ? null : customAnimationEntry.type)) continue;
                arrayList.add(customAnimationEntry);
            }
            return arrayList;
        }
        return List.of();
    }

    private static List<CustomAnimationEntry> mergeCustomAnimationByType(List<CustomAnimationEntry> list, Map<String, List<CustomAnimationEntry>> map) {
        List<CustomAnimationEntry> list2 = list == null ? List.of() : list;
        if (map != null && !map.isEmpty()) {
            ArrayList<CustomAnimationEntry> arrayList = new ArrayList<>();
            HashSet<String> handledTypes = new HashSet<>();
            for (CustomAnimationEntry entry : list2) {
                String type = PunchyTuningConfig.normalizeEntryType(entry == null ? null : entry.type);
                if (type == null || !map.containsKey(type)) {
                    arrayList.add(entry);
                    continue;
                }
                if (!handledTypes.add(type)) continue;
                List<CustomAnimationEntry> replacements = map.get(type);
                if (replacements != null && !replacements.isEmpty()) {
                    arrayList.addAll(replacements);
                }
            }
            for (Map.Entry<String, List<CustomAnimationEntry>> entry : map.entrySet()) {
                List<CustomAnimationEntry> additions = entry != null ? entry.getValue() : null;
                if (entry == null || entry.getKey() == null || handledTypes.contains(entry.getKey()) || additions == null || additions.isEmpty()) continue;
                arrayList.addAll(additions);
            }
            return arrayList.isEmpty() ? null : arrayList;
        }
        return list2.isEmpty() ? null : new ArrayList<>(list2);
    }

    private static String customAnimationMergeKey(CustomAnimationEntry customAnimationEntry) {
        String string = PunchyTuningConfig.normalizeEntryTypeFamily(customAnimationEntry == null ? null : customAnimationEntry.type);
        return string != null ? string : "__untyped__";
    }

    private static List<CustomAnimationEntry> mergeCustomAnimationEntryLists(List<CustomAnimationEntry> list, List<CustomAnimationEntry> list2) {
        List<CustomAnimationEntry> list3 = list == null ? List.of() : list;
        if (list2 != null && !list2.isEmpty()) {
            LinkedHashMap<String, List<CustomAnimationEntry>> linkedHashMap = new LinkedHashMap<>();
            for (CustomAnimationEntry entry : list2) {
                String key = PunchyTuningConfig.customAnimationMergeKey(entry);
                linkedHashMap.computeIfAbsent(key, string -> new ArrayList<>()).add(entry);
            }
            ArrayList<CustomAnimationEntry> arrayList = new ArrayList<>();
            HashSet<String> hashSet = new HashSet<String>();
            for (CustomAnimationEntry customAnimationEntry : list3) {
                String string2 = PunchyTuningConfig.customAnimationMergeKey(customAnimationEntry);
                if (linkedHashMap.containsKey(string2)) {
                    if (!hashSet.add(string2)) continue;
                    arrayList.addAll(linkedHashMap.get(string2));
                    continue;
                }
                arrayList.add(customAnimationEntry);
            }
            for (Map.Entry<String, List<CustomAnimationEntry>> entry : linkedHashMap.entrySet()) {
                if (entry == null || entry.getKey() == null || !hashSet.add(entry.getKey())) continue;
                arrayList.addAll(entry.getValue());
            }
            return arrayList.isEmpty() ? null : arrayList;
        }
        return list3.isEmpty() ? null : new ArrayList<>(list3);
    }

    private static List<CustomAnimationEntry> mergeCustomAnimationEntryListsPreferBase(List<CustomAnimationEntry> list, List<CustomAnimationEntry> list2) {
        List<CustomAnimationEntry> list3 = list == null ? List.of() : list;
        if (list2 != null && !list2.isEmpty()) {
            if (list3.isEmpty()) {
                return new ArrayList<CustomAnimationEntry>(list2);
            }
            LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
            for (CustomAnimationEntry entry : list3) {
                linkedHashSet.add(PunchyTuningConfig.customAnimationMergeKey(entry));
            }
            ArrayList<CustomAnimationEntry> arrayList = new ArrayList<>(list3);
            for (CustomAnimationEntry customAnimationEntry : list2) {
                String string;
                if (customAnimationEntry == null || linkedHashSet.contains(string = PunchyTuningConfig.customAnimationMergeKey(customAnimationEntry))) continue;
                arrayList.add(customAnimationEntry);
                linkedHashSet.add(string);
            }
            return arrayList.isEmpty() ? null : arrayList;
        }
        return list3.isEmpty() ? null : new ArrayList<>(list3);
    }

    private static boolean isSpecificSlotForced(MixSlotDef mixSlotDef) {
        return mixSlotDef != null && mixSlotDef.type == MixSlotType.SPECIFIC ? PunchyConfig.isMixSpecificForceOverride(mixSlotDef.slotId) : false;
    }

    private static boolean shouldAutoUseSpecificSlot(MixSlotDef mixSlotDef) {
        return mixSlotDef != null && mixSlotDef.type == MixSlotType.SPECIFIC && !PunchyTuningConfig.isSpecificSlotForced(mixSlotDef) && mixSlotDef.optionOrder.size() == 1 && mixSlotDef.resourceOrderOption != null && mixSlotDef.optionOrder.contains(mixSlotDef.resourceOrderOption);
    }

    private static String resolveInheritedKindOption(MixSlotDef mixSlotDef) {
        if (mixSlotDef == null) {
            return null;
        }
        VanillaFirstPersonItemLayer.ToolKind toolKind = mixSlotDef.kind;
        if (toolKind == null && mixSlotDef.itemId != null) {
            toolKind = PunchyTuningConfig.resolveKindForItem(mixSlotDef.itemId);
        }
        if (toolKind == null) {
            return null;
        }
        String string = "kind:" + toolKind.name().toLowerCase(Locale.ROOT);
        MixSlotDef mixSlotDef2 = PunchyTuningConfig.MIX_CATALOG.slots.get(string);
        return mixSlotDef2 == null ? "punchy_default" : PunchyTuningConfig.resolveSelectedOption(mixSlotDef2);
    }

    private static void applyMixSelections() {
        if (MIX_CATALOG != null && !PunchyTuningConfig.MIX_CATALOG.slots.isEmpty()) {
            for (MixSlotDef mixSlotDef : PunchyTuningConfig.MIX_CATALOG.slots.values()) {
                if (mixSlotDef == null || mixSlotDef.type != MixSlotType.KIND || mixSlotDef.mergedSpecificItems.isEmpty()) continue;
                for (class_2960 itemId : mixSlotDef.mergedSpecificItems) {
                    if (itemId == null) continue;
                    PunchyTuningConfig.clearSpecificMixState(itemId);
                }
            }
            for (MixSlotDef mixSlotDef : PunchyTuningConfig.MIX_CATALOG.slots.values()) {
                if (mixSlotDef == null || mixSlotDef.type != MixSlotType.SPECIFIC || mixSlotDef.itemId == null || PunchyTuningConfig.isSpecificSlotForced(mixSlotDef) || PunchyTuningConfig.shouldAutoUseSpecificSlot(mixSlotDef)) continue;
                PunchyTuningConfig.clearSpecificMixState(mixSlotDef.itemId);
            }
            for (MixSlotDef mixSlotDef : PunchyTuningConfig.MIX_CATALOG.slots.values()) {
                if (mixSlotDef == null || mixSlotDef.type != MixSlotType.KIND) continue;
                String selectedOption = PunchyTuningConfig.resolveSelectedOption(mixSlotDef);
                MixSlotPayload selectedPayload = PunchyTuningConfig.resolveSelectedPayload(mixSlotDef, selectedOption);
                MixSlotPayload mixSlotPayload = PunchyTuningConfig.composePayloadByType(mixSlotDef, selectedOption, selectedPayload);
                PunchyTuningConfig.applyMixSlotPayload(mixSlotDef, mixSlotPayload);
                if (mixSlotDef.mergedSpecificItems.isEmpty()) continue;
                for (class_2960 class_29602 : mixSlotDef.mergedSpecificItems) {
                    if (class_29602 == null) continue;
                    MixSlotPayload mixSlotPayload2 = PunchyTuningConfig.resolveSelectedSpecificPayload(mixSlotDef, selectedOption, class_29602, mixSlotDef.kind);
                    mixSlotPayload2 = PunchyTuningConfig.composeMergedSpecificPayloadByType(mixSlotDef, selectedOption, class_29602, mixSlotPayload2);
                    PunchyTuningConfig.applySpecificPayload(class_29602, mixSlotDef.kind, mixSlotPayload2);
                }
            }
            for (MixSlotDef mixSlotDef : PunchyTuningConfig.MIX_CATALOG.slots.values()) {
                if (mixSlotDef == null || mixSlotDef.type != MixSlotType.SPECIFIC || !PunchyTuningConfig.isSpecificSlotForced(mixSlotDef) && !PunchyTuningConfig.shouldAutoUseSpecificSlot(mixSlotDef)) continue;
                String selectedOption = PunchyTuningConfig.resolveSelectedOption(mixSlotDef);
                MixSlotPayload selectedPayload = PunchyTuningConfig.resolveSelectedPayload(mixSlotDef, selectedOption);
                selectedPayload = PunchyTuningConfig.composePayloadByType(mixSlotDef, selectedOption, selectedPayload);
                PunchyTuningConfig.applyMixSlotPayload(mixSlotDef, selectedPayload);
            }
            PunchyTuningConfig.writeMergedAnimationSelectionSnapshot();
        } else {
            PunchyTuningConfig.writeMergedAnimationSelectionSnapshot();
        }
    }

    private static void writeMergedAnimationSelectionSnapshot() {
        try {
            Path path = PunchyTuningConfig.getConfigDirectory().resolve("punchy");
            Files.createDirectories(path, new FileAttribute[0]);
            Path path2 = path.resolve("merged_animation_selection.json");
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            jsonObject.add("selected_animations", (JsonElement)jsonArray);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            if (MIX_CATALOG != null && PunchyTuningConfig.MIX_CATALOG.slots != null && !PunchyTuningConfig.MIX_CATALOG.slots.isEmpty()) {
                JsonObject jsonObject2;
                JsonObject jsonObject3;
                for (MixSlotDef mixSlotDef : PunchyTuningConfig.MIX_CATALOG.slots.values()) {
                    if (mixSlotDef == null || mixSlotDef.type != MixSlotType.KIND || mixSlotDef.kind == null || (jsonObject3 = PunchyTuningConfig.collectSelectedAnimationTypesForSlot(mixSlotDef, null)).size() == 0) continue;
                    jsonObject2 = new JsonObject();
                    jsonObject2.addProperty("kind", "item");
                    jsonObject2.addProperty("definition", mixSlotDef.kind.name());
                    jsonObject2.add("animations", (JsonElement)jsonObject3);
                    jsonArray.add((JsonElement)jsonObject2);
                    LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
                    for (Map.Entry entry : jsonObject3.entrySet()) {
                        String string;
                        if (entry == null || entry.getKey() == null || (string = PunchyTuningConfig.normalizeEntryType((String)entry.getKey())) == null || string.isBlank()) continue;
                        linkedHashSet.add(string);
                    }
                    linkedHashMap.put(mixSlotDef.slotId, linkedHashSet);
                }
                for (MixSlotDef mixSlotDef : PunchyTuningConfig.MIX_CATALOG.slots.values()) {
                    if (mixSlotDef == null || mixSlotDef.type != MixSlotType.SPECIFIC || mixSlotDef.itemId == null || (jsonObject3 = PunchyTuningConfig.collectSelectedAnimationTypesForSlot(mixSlotDef, null)).size() == 0) continue;
                    jsonObject2 = new JsonObject();
                    jsonObject2.addProperty("kind", "itemSpecific");
                    jsonObject2.addProperty("definition", mixSlotDef.itemId.toString());
                    jsonObject2.add("animations", (JsonElement)jsonObject3);
                    jsonArray.add((JsonElement)jsonObject2);
                }
            }
            try (FileWriter writer = new FileWriter(path2.toFile())) {
                GSON.toJson(jsonObject, writer);
            }
        }
        catch (Exception exception) {
        }
    }

    private static void reapplyActiveProfileSpecificTransforms(Data data) {
        if (data != null && data.itemSpecific != null && !data.itemSpecific.isEmpty()) {
            Data data2 = new Data();
            data2.itemSpecific = new HashMap<String, SpecificEntry>();
            for (Map.Entry<String, SpecificEntry> entry : data.itemSpecific.entrySet()) {
                boolean bl;
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                SpecificEntry specificEntry = entry.getValue();
                boolean bl2 = PunchyTuningConfig.hasSideSetData(specificEntry.transforms);
                boolean bl3 = PunchyTuningConfig.hasSideSetData(specificEntry.armTransforms);
                boolean bl4 = specificEntry.physics != null && !specificEntry.physics.isJsonNull();
                boolean bl5 = bl = specificEntry.disableSprintLateralTilt != null || specificEntry.keepArmTuning != null || specificEntry.keepTriggerArmTuning != null || specificEntry.keepArmTuningL != null || specificEntry.keepArmTuningR != null;
                if (!bl2 && !bl3 && !bl4 && !bl) continue;
                SpecificEntry specificEntry2 = new SpecificEntry();
                specificEntry2.kind = specificEntry.kind;
                specificEntry2.armKind = specificEntry.armKind;
                specificEntry2.customModelData = specificEntry.customModelData;
                specificEntry2.renamed = specificEntry.renamed;
                specificEntry2.model = specificEntry.model;
                specificEntry2.transforms = PunchyTuningConfig.copySideSet(specificEntry.transforms);
                specificEntry2.armTransforms = PunchyTuningConfig.copySideSet(specificEntry.armTransforms);
                specificEntry2.physics = PunchyTuningConfig.copyJson(specificEntry.physics);
                specificEntry2.disableSprintLateralTilt = specificEntry.disableSprintLateralTilt;
                specificEntry2.keepArmTuning = specificEntry.keepArmTuning;
                specificEntry2.keepTriggerArmTuning = specificEntry.keepTriggerArmTuning;
                specificEntry2.keepArmTuningL = specificEntry.keepArmTuningL;
                specificEntry2.keepArmTuningR = specificEntry.keepArmTuningR;
                data2.itemSpecific.put(entry.getKey(), specificEntry2);
            }
            if (!data2.itemSpecific.isEmpty()) {
                try {
                    PunchyTuningConfig.applyData(data2, false);
                }
                catch (Exception exception) {
                }
            }
        }
    }

    private static JsonObject collectSelectedAnimationTypesForSlot(MixSlotDef mixSlotDef, Set<String> set) {
        JsonObject jsonObject = new JsonObject();
        if (mixSlotDef != null && mixSlotDef.slotId != null && !mixSlotDef.slotId.isBlank()) {
            List<String> list = PunchyTuningConfig.getMixPackSlotEntryTypes(mixSlotDef.slotId);
            if (list != null && !list.isEmpty()) {
                for (String string : list) {
                    String string2;
                    String string3;
                    String string4 = string == null ? null : string.trim();
                    if (string4 == null || string4.isBlank() || (string3 = PunchyTuningConfig.normalizeEntryType(string)) == null || string3.isBlank() || set != null && set.contains(string3) || (string2 = PunchyTuningConfig.getMixPackSelectedEntryTypeOption(mixSlotDef.slotId, string3)) == null || string2.isBlank() || !PunchyTuningConfig.isGlobalHandType(string3) && !PunchyTuningConfig.optionDefinesTypeExactly(mixSlotDef, string2, string3)) continue;
                    jsonObject.addProperty(string4, string2);
                }
                return jsonObject;
            }
            return jsonObject;
        }
        return jsonObject;
    }

    private static boolean optionDefinesTypeExactly(MixSlotDef mixSlotDef, String string, String string2) {
        if (mixSlotDef != null && string != null && !string.isBlank() && string2 != null && !string2.isBlank()) {
            MixSlotPayload mixSlotPayload;
            MixSlotPayload mixSlotPayload2 = mixSlotPayload = "punchy_default".equals(string) ? PunchyTuningConfig.baselinePayloadForSlot(mixSlotDef) : mixSlotDef.optionPayloads.get(string);
            return mixSlotPayload == null ? false : PunchyTuningConfig.listHasExactType(PunchyTuningConfig.payloadCustomAnimation(mixSlotPayload), string2) || PunchyTuningConfig.listHasExactType(PunchyTuningConfig.payloadCustomItemAnimations(mixSlotPayload), string2);
        }
        return false;
    }

    private static boolean listHasExactType(List<CustomAnimationEntry> list, String string) {
        if (list != null && !list.isEmpty() && string != null && !string.isBlank()) {
            for (CustomAnimationEntry customAnimationEntry : list) {
                if (!string.equals(PunchyTuningConfig.normalizeEntryType(customAnimationEntry == null ? null : customAnimationEntry.type))) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static String summarizeEntryByType(List<CustomAnimationEntry> list, String string) {
        if (list != null && !list.isEmpty()) {
            String string2 = PunchyTuningConfig.normalizeEntryType(string);
            for (CustomAnimationEntry customAnimationEntry : list) {
                if (customAnimationEntry == null || !Objects.equals(string2, PunchyTuningConfig.normalizeEntryType(customAnimationEntry.type))) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("type=").append(customAnimationEntry.type);
                if (customAnimationEntry.name != null) {
                    stringBuilder.append(",name=").append(customAnimationEntry.name);
                }
                if (customAnimationEntry.start != null) {
                    stringBuilder.append(",start=").append(customAnimationEntry.start);
                }
                if (customAnimationEntry.looping != null) {
                    stringBuilder.append(",looping=").append(customAnimationEntry.looping);
                }
                if (customAnimationEntry.end != null) {
                    stringBuilder.append(",end=").append(customAnimationEntry.end);
                }
                if (customAnimationEntry.var_1 != null) {
                    stringBuilder.append(",var_1=").append(customAnimationEntry.var_1);
                }
                if (customAnimationEntry.mix_pack_option != null) {
                    stringBuilder.append(",mix=").append(customAnimationEntry.mix_pack_option);
                }
                return stringBuilder.toString();
            }
            return "missing";
        }
        return "none";
    }

    private static List<BetterCombatEntry> extractDefaultBetterCombatFromDefaultAnimations(List<CustomAnimationEntry> list) {
        if (list != null && !list.isEmpty()) {
            ArrayList<BetterCombatEntry> arrayList = new ArrayList<BetterCombatEntry>();
            for (CustomAnimationEntry customAnimationEntry : list) {
                if (customAnimationEntry != null && customAnimationEntry.betterCombat != null && !customAnimationEntry.betterCombat.isEmpty()) {
                    for (BetterCombatEntry betterCombatEntry : customAnimationEntry.betterCombat) {
                        if (betterCombatEntry == null || !PunchyTuningConfig.isMeleeComboType(betterCombatEntry.type) || PunchyTuningConfig.allBlank(betterCombatEntry.attack_1, betterCombatEntry.attack_2, betterCombatEntry.attack_3, betterCombatEntry.attack_4, betterCombatEntry.attack_5, betterCombatEntry.attack_6)) continue;
                        arrayList.add(betterCombatEntry);
                    }
                }
                BetterCombatEntry converted = PunchyTuningConfig.isMeleeComboType(customAnimationEntry == null ? null : customAnimationEntry.type) ? PunchyTuningConfig.toBetterCombatEntry(customAnimationEntry) : null;
                if (converted == null) continue;
                arrayList.add(converted);
            }
            return arrayList.isEmpty() ? null : arrayList;
        }
        return null;
    }

    private static List<CustomAnimationEntry> stripBetterCombatFromDefaultAnimations(List<CustomAnimationEntry> list) {
        if (list != null && !list.isEmpty()) {
            ArrayList<CustomAnimationEntry> arrayList = new ArrayList<CustomAnimationEntry>();
            for (CustomAnimationEntry customAnimationEntry : list) {
                if (customAnimationEntry != null && customAnimationEntry.betterCombat != null && !customAnimationEntry.betterCombat.isEmpty() || PunchyTuningConfig.isMeleeComboType(customAnimationEntry == null ? null : customAnimationEntry.type)) continue;
                arrayList.add(customAnimationEntry);
            }
            return arrayList.isEmpty() ? null : arrayList;
        }
        return null;
    }

    private static boolean isMeleeComboType(String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "").replace(" ", "");
        return "meleecombo".equals(string2);
    }

    private static BetterCombatEntry toBetterCombatEntry(CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry == null) {
            return null;
        }
        BetterCombatEntry betterCombatEntry = new BetterCombatEntry();
        betterCombatEntry.type = "melee_combo";
        betterCombatEntry.attack_1 = PunchyTuningConfig.firstNonBlank(customAnimationEntry.attack_1, customAnimationEntry.var_1);
        betterCombatEntry.attack_2 = PunchyTuningConfig.firstNonBlank(customAnimationEntry.attack_2, customAnimationEntry.var_2);
        betterCombatEntry.attack_3 = PunchyTuningConfig.firstNonBlank(customAnimationEntry.attack_3, customAnimationEntry.var_3);
        betterCombatEntry.attack_4 = PunchyTuningConfig.firstNonBlank(customAnimationEntry.attack_4, null);
        betterCombatEntry.attack_5 = PunchyTuningConfig.firstNonBlank(customAnimationEntry.attack_5, null);
        betterCombatEntry.attack_6 = PunchyTuningConfig.firstNonBlank(customAnimationEntry.attack_6, null);
        return PunchyTuningConfig.allBlank(betterCombatEntry.attack_1, betterCombatEntry.attack_2, betterCombatEntry.attack_3, betterCombatEntry.attack_4, betterCombatEntry.attack_5, betterCombatEntry.attack_6) ? null : betterCombatEntry;
    }

    private static String firstNonBlank(String string, String string2) {
        if (string != null && !string.isBlank()) {
            return string;
        }
        return string2 != null && !string2.isBlank() ? string2 : null;
    }

    private static boolean allBlank(String ... stringArray) {
        if (stringArray != null && stringArray.length != 0) {
            for (String string : stringArray) {
                if (string == null || string.isBlank()) continue;
                return false;
            }
            return true;
        }
        return true;
    }

    private static String summarizeGroupTypeEntry(Data data, VanillaFirstPersonItemLayer.ToolKind toolKind, String string) {
        if (data != null && toolKind != null && string != null && data.item != null) {
            GroupEntry groupEntry = data.item.get(toolKind.name());
            if (groupEntry == null) {
                return "group-missing";
            }
            String string2 = PunchyTuningConfig.summarizeEntryByType(groupEntry.customAnimation, string);
            String string3 = PunchyTuningConfig.summarizeEntryByType(groupEntry.customItemAnimations, string);
            return "anim={" + string2 + "} itemAnim={" + string3 + "}";
        }
        return "none";
    }

    private static MixSlotPayload resolveSelectedPayload(MixSlotDef mixSlotDef, String string) {
        if (mixSlotDef != null && string != null && !string.isBlank() && !"punchy_default".equals(string)) {
            int n = new ArrayList<String>(mixSlotDef.optionOrder).indexOf(string);
            if (n < 0) {
                MixSlotPayload mixSlotPayload = PunchyTuningConfig.baselinePayloadForSlot(mixSlotDef);
                return mixSlotPayload == null ? null : PunchyTuningConfig.mergeMixSlotPayload(null, mixSlotPayload);
            }
            MixSlotPayload mixSlotPayload = PunchyTuningConfig.mergeMixSlotPayload(null, PunchyTuningConfig.baselinePayloadForSlot(mixSlotDef));
            ArrayList<String> arrayList = new ArrayList<String>(mixSlotDef.optionOrder);
            for (int i = arrayList.size() - 1; i >= n; --i) {
                String string2 = (String)arrayList.get(i);
                MixSlotPayload mixSlotPayload2 = mixSlotDef.optionPayloads.get(string2);
                mixSlotPayload = PunchyTuningConfig.mergeMixSlotPayload(mixSlotPayload, mixSlotPayload2);
            }
            return mixSlotPayload;
        }
        return PunchyTuningConfig.baselinePayloadForSlot(mixSlotDef);
    }

    private static MixSlotPayload resolveSelectedSpecificPayload(MixSlotDef mixSlotDef, String string, class_2960 class_29602, VanillaFirstPersonItemLayer.ToolKind toolKind) {
        if (mixSlotDef != null && class_29602 != null) {
            MixSlotPayload mixSlotPayload = PunchyTuningConfig.mergeMixSlotPayload(null, PunchyTuningConfig.baselineSpecificPayloadForItem(class_29602, toolKind));
            if (string != null && !string.isBlank() && !"punchy_default".equals(string)) {
                ArrayList<String> arrayList = new ArrayList<String>(mixSlotDef.optionOrder);
                int n = arrayList.indexOf(string);
                if (n < 0) {
                    return mixSlotPayload;
                }
                for (int i = arrayList.size() - 1; i >= n; --i) {
                    String string2 = (String)arrayList.get(i);
                    Map map = mixSlotDef.optionSpecificPayloads.get(string2);
                    MixSlotPayload mixSlotPayload2 = map == null ? null : (MixSlotPayload)map.get(class_29602);
                    mixSlotPayload = PunchyTuningConfig.mergeMixSlotPayload(mixSlotPayload, mixSlotPayload2);
                }
                return mixSlotPayload;
            }
            return mixSlotPayload;
        }
        return null;
    }

    private static MixSlotPayload baselineSpecificPayloadForItem(class_2960 class_29602, VanillaFirstPersonItemLayer.ToolKind toolKind) {
        if (class_29602 != null && DEFAULTS_RESOURCE_STATE != null && PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.itemSpecific != null && !PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.itemSpecific.isEmpty()) {
            SpecificEntry specificEntry = PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.itemSpecific.get(class_29602.toString());
            if (specificEntry == null) {
                for (Map.Entry entry : PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.itemSpecific.entrySet()) {
                    if (entry == null || entry.getKey() == null || entry.getValue() == null || !PunchyTuningConfig.specificKeyTargetsItem((String)entry.getKey(), class_29602)) continue;
                    specificEntry = (SpecificEntry)entry.getValue();
                    break;
                }
            }
            if (specificEntry == null) {
                return null;
            }
            Object object = PunchyTuningConfig.parseToolKind(specificEntry.kind);
            if (object == null) {
                object = toolKind != null ? toolKind : PunchyTuningConfig.resolveKindForItem(class_29602);
            }
            return PunchyTuningConfig.payloadFromSpecific(specificEntry, (VanillaFirstPersonItemLayer.ToolKind)((Object)object), "punchy_default");
        }
        return null;
    }

    private static void applySpecificPayload(class_2960 class_29602, VanillaFirstPersonItemLayer.ToolKind toolKind, MixSlotPayload mixSlotPayload) {
        if (class_29602 != null) {
            PunchyTuningConfig.clearSpecificMixState(class_29602);
            if (mixSlotPayload != null) {
                SpecificEntry specificEntry;
                SpecificEntry specificEntry2 = specificEntry = mixSlotPayload.specificEntry != null ? PunchyTuningConfig.copySpecificEntry(mixSlotPayload.specificEntry) : null;
                if (specificEntry != null) {
                    if ((specificEntry.kind == null || specificEntry.kind.isBlank()) && toolKind != null) {
                        specificEntry.kind = toolKind.name();
                    }
                    Data data = new Data();
                    data.itemSpecific.put(class_29602.toString(), specificEntry);
                    PunchyTuningConfig.applyData(data, false);
                }
            }
        }
    }

    private static MixSlotPayload baselinePayloadForSlot(MixSlotDef mixSlotDef) {
        if (mixSlotDef == null || DEFAULTS_RESOURCE_STATE == null) {
            return null;
        }
        if (mixSlotDef.type == MixSlotType.KIND) {
            if (mixSlotDef.kind == VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND && PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.emptyHand != null) {
                return PunchyTuningConfig.payloadFromEmptyHand(PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.emptyHand, VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND, "punchy_default");
            }
            if (PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.item != null && mixSlotDef.kind != null) {
                GroupEntry groupEntry = PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.item.get(mixSlotDef.kind.name());
                return groupEntry == null ? null : PunchyTuningConfig.payloadFromGroup(groupEntry, mixSlotDef.kind, "punchy_default");
            }
            return null;
        }
        if (mixSlotDef.type == MixSlotType.SPECIFIC && mixSlotDef.itemId != null && PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.itemSpecific != null) {
            SpecificEntry specificEntry = PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.itemSpecific.get(mixSlotDef.itemId.toString());
            if (specificEntry == null) {
                for (Map.Entry entry : PunchyTuningConfig.DEFAULTS_RESOURCE_STATE.itemSpecific.entrySet()) {
                    if (entry == null || entry.getKey() == null || entry.getValue() == null || !PunchyTuningConfig.specificKeyTargetsItem((String)entry.getKey(), mixSlotDef.itemId)) continue;
                    specificEntry = (SpecificEntry)entry.getValue();
                    break;
                }
            }
            if (specificEntry == null) {
                return null;
            }
            Object object = PunchyTuningConfig.parseToolKind(specificEntry.kind);
            if (object == null) {
                object = mixSlotDef.kind != null ? mixSlotDef.kind : PunchyTuningConfig.resolveKindForItem(mixSlotDef.itemId);
            }
            return PunchyTuningConfig.payloadFromSpecific(specificEntry, (VanillaFirstPersonItemLayer.ToolKind)((Object)object), "punchy_default");
        }
        return null;
    }

    private static void applyMixSlotPayload(MixSlotDef mixSlotDef, MixSlotPayload mixSlotPayload) {
        if (mixSlotDef != null) {
            if (mixSlotDef.type == MixSlotType.KIND) {
                if (mixSlotDef.kind != null) {
                    PunchyTuningConfig.resetKindMixState(mixSlotDef.kind);
                    if (mixSlotPayload != null) {
                        Data data = new Data();
                        if (mixSlotDef.kind == VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND) {
                            EmptyHandEntry emptyHandEntry;
                            EmptyHandEntry emptyHandEntry2 = emptyHandEntry = mixSlotPayload.emptyHandEntry != null ? PunchyTuningConfig.copyEmptyHandEntry(mixSlotPayload.emptyHandEntry) : PunchyTuningConfig.emptyHandEntryFromGroup(mixSlotPayload.groupEntry);
                            if (emptyHandEntry == null) {
                                return;
                            }
                            data.emptyHand = emptyHandEntry;
                        } else {
                            if (mixSlotPayload.groupEntry == null) {
                                return;
                            }
                            data.item.put(mixSlotDef.kind.name(), PunchyTuningConfig.copyGroupEntry(mixSlotPayload.groupEntry));
                        }
                        PunchyTuningConfig.applyData(data, false);
                    }
                }
            } else if (mixSlotDef.type == MixSlotType.SPECIFIC) {
                SpecificEntry specificEntry;
                if (mixSlotDef.itemId == null) {
                    return;
                }
                PunchyTuningConfig.clearSpecificMixState(mixSlotDef.itemId);
                if (mixSlotPayload == null) {
                    return;
                }
                SpecificEntry specificEntry2 = specificEntry = mixSlotPayload.specificEntry != null ? PunchyTuningConfig.copySpecificEntry(mixSlotPayload.specificEntry) : null;
                if (specificEntry == null) {
                    return;
                }
                if ((specificEntry.kind == null || specificEntry.kind.isBlank()) && mixSlotDef.kind != null) {
                    specificEntry.kind = mixSlotDef.kind.name();
                }
                Data data = new Data();
                data.itemSpecific.put(mixSlotDef.itemId.toString(), specificEntry);
                PunchyTuningConfig.applyData(data, false);
            }
        }
    }

    private static EmptyHandEntry emptyHandEntryFromGroup(GroupEntry groupEntry) {
        if (groupEntry == null) {
            return null;
        }
        try {
            return (EmptyHandEntry)GSON.fromJson(GSON.toJsonTree((Object)groupEntry), EmptyHandEntry.class);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static void clearSpecificMixState(class_2960 class_29602) {
        if (class_29602 != null) {
            ToolTuning.clearSpecificOverride(class_29602);
        }
    }

    private static void resetKindMixState(VanillaFirstPersonItemLayer.ToolKind toolKind) {
        if (toolKind != null) {
            ToolTuning.setGroupAnimation(toolKind, null);
            ToolTuning.setGroupItemAnimation(toolKind, null);
        }
    }

    private static void collectCompatProfiles(class_3300 class_33002, String string, String string2, String string3, Set<String> set) {
        Map<class_2960, class_3298> map = class_33002.method_14488(string, class_29602 -> class_29602.method_12836().equals(string2) && class_29602.method_12832().startsWith(string3) && class_29602.method_12832().endsWith(".json"));
        for (class_2960 class_29603 : map.keySet()) {
            String string4 = class_29603.method_12832();
            String string5 = string4.substring(string3.length());
            if (string5.endsWith(".json")) {
                string5 = string5.substring(0, string5.length() - 5);
            }
            if (string5.isBlank()) continue;
            set.add(string5.toLowerCase(Locale.ROOT));
        }
    }

    private static SideSet resolveGlobalArm(Data data) {
        return data == null ? null : data.globalArm;
    }

    private static SideSet resolveGlobalItem(Data data) {
        return data == null ? null : data.globalItem;
    }

    private static Data loadCompatProfile(class_3300 class_33002, String string) {
        String string2 = string.endsWith(".json") ? string.substring(0, string.length() - 5) : string;
        Data data = null;
        Path path = PunchyTuningConfig.getCompatDirectory();
        try {
            Files.createDirectories(path, new FileAttribute[0]);
        }
        catch (Exception exception) {
        }
        Path path2 = path.resolve(string2 + ".json");
        if (Files.exists(path2, new LinkOption[0])) {
            try (FileReader fileReader = new FileReader(path2.toFile());){
                Data data2 = (Data)GSON.fromJson((Reader)fileReader, Data.class);
                data = PunchyTuningConfig.mergeData(data, data2);
            }
            catch (Exception exception) {
            }
        }
        return PunchyTuningConfig.finalizeCompatData(string2, data);
    }

    private static Data loadCompatProfileFromResource(class_3300 class_33002, String string, String string2, String string3) {
        if (class_33002 == null || string == null || string.isBlank()) {
            return null;
        }
        String profileName = string.endsWith(".json") ? string.substring(0, string.length() - 5) : string;
        String namespace = string2 == null || string2.isBlank() ? "minecraft" : string2;
        String directory = string3 == null ? "" : string3;
        if (!directory.isEmpty() && !directory.endsWith("/")) {
            directory += "/";
        }
        try {
            class_2960 resourceId = class_2960.method_60655(namespace, directory + profileName + ".json");
            List<class_3298> resources = PunchyTuningConfig.tryGetResourceStack(class_33002, resourceId);
            Data merged = null;
            if (resources != null) {
                for (class_3298 resource : resources) {
                    merged = PunchyTuningConfig.mergeData(merged, PunchyTuningConfig.readCompatResource(resource));
                }
            }
            return PunchyTuningConfig.finalizeCompatData(profileName, merged);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static List<class_3298> tryGetResourceStack(class_3300 class_33002, class_2960 class_29602) {
        if (class_33002 != null && class_29602 != null) {
            try {
                List<class_3298> list = class_33002.method_14489(class_29602);
                return list != null && !list.isEmpty() ? new ArrayList<>(list) : null;
            }
            catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    private static Map<class_2960, List<class_3298>> tryListResourceStacks(class_3300 class_33002, String string) {
        if (class_33002 != null && string != null && !string.isBlank()) {
            try {
                Map<class_2960, List<class_3298>> map = class_33002.method_41265(string, class_29602 -> true);
                if (map != null && !map.isEmpty()) {
                    LinkedHashMap<class_2960, List<class_3298>> linkedHashMap = new LinkedHashMap<class_2960, List<class_3298>>();
                    for (Map.Entry<class_2960, List<class_3298>> entry : map.entrySet()) {
                        class_2960 class_29603 = entry.getKey();
                        List<class_3298> list = entry.getValue();
                        if (class_29603 == null || list == null || list.isEmpty()) continue;
                        linkedHashMap.put(class_29603, new ArrayList<>(list));
                    }
                    return linkedHashMap.isEmpty() ? null : linkedHashMap;
                }
                return null;
            }
            catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    private static Data readCompatResource(class_3298 class_32982) {
        if (class_32982 == null) {
            return null;
        }
        try {
            Data data;
            try (InputStream inputStream = class_32982.method_14482();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);){
                data = (Data)GSON.fromJson((Reader)inputStreamReader, Data.class);
            }
            return data;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Data copyData(Data data) {
        return data == null ? null : (Data)GSON.fromJson(GSON.toJsonTree((Object)data), Data.class);
    }

    private static GroupEntry copyGroupEntry(GroupEntry groupEntry) {
        return groupEntry == null ? null : (GroupEntry)GSON.fromJson(GSON.toJsonTree((Object)groupEntry), GroupEntry.class);
    }

    private static EmptyHandEntry copyEmptyHandEntry(EmptyHandEntry emptyHandEntry) {
        return emptyHandEntry == null ? null : (EmptyHandEntry)GSON.fromJson(GSON.toJsonTree((Object)emptyHandEntry), EmptyHandEntry.class);
    }

    private static SpecificEntry copySpecificEntry(SpecificEntry specificEntry) {
        return specificEntry == null ? null : (SpecificEntry)GSON.fromJson(GSON.toJsonTree((Object)specificEntry), SpecificEntry.class);
    }

    private static Data mergeDataPreferBase(Data data, Data data2) {
        if (data2 == null) {
            return data;
        }
        if (data == null) {
            return data2;
        }
        data.globalItem = PunchyTuningConfig.mergeSideSetPreferBase(data.globalItem, data2.globalItem);
        data.globalArm = PunchyTuningConfig.mergeSideSetPreferBase(data.globalArm, data2.globalArm);
        data.default_animations = PunchyTuningConfig.mergeCustomAnimationEntryListsPreferBase(data.default_animations, data2.default_animations);
        data.emptyHand = PunchyTuningConfig.mergeEmptyHandPreferBase(data.emptyHand, data2.emptyHand);
        data.item = PunchyTuningConfig.mergeGroupEntryMapPreferBase(data.item, data2.item);
        data.armMesh = PunchyTuningConfig.mergeSideSetMapPreferBase(data.armMesh, data2.armMesh);
        data.chestProxy = PunchyTuningConfig.mergePoseMapPreferBase(data.chestProxy, data2.chestProxy);
        data.fluidProxy = PunchyTuningConfig.mergePoseMapPreferBase(data.fluidProxy, data2.fluidProxy);
        data.enchantParticleProxy = PunchyTuningConfig.mergePoseMapPreferBase(data.enchantParticleProxy, data2.enchantParticleProxy);
        data.paddleProxy = PunchyTuningConfig.mergePoseMapPreferBase(data.paddleProxy, data2.paddleProxy);
        data.fishingLineProxy = PunchyTuningConfig.mergePoseMapPreferBase(data.fishingLineProxy, data2.fishingLineProxy);
        data.itemSpecific = PunchyTuningConfig.mergeSpecificMapPreferBase(data, data.itemSpecific, data2.itemSpecific);
        data.customGroups = PunchyTuningConfig.mergeCustomGroupsPreferBase(data.customGroups, data2.customGroups);
        return data;
    }

    private static SideSet mergeSideSetPreferBase(SideSet sideSet, SideSet sideSet2) {
        if (sideSet2 != null && (sideSet2.right != null || sideSet2.left != null)) {
            if (sideSet == null) {
                sideSet = new SideSet();
            }
            if (sideSet.right == null && sideSet2.right != null) {
                sideSet.right = sideSet2.right;
            }
            if (sideSet.left == null && sideSet2.left != null) {
                sideSet.left = sideSet2.left;
            }
            return sideSet;
        }
        return sideSet;
    }

    private static EmptyHandEntry mergeEmptyHandPreferBase(EmptyHandEntry emptyHandEntry, EmptyHandEntry emptyHandEntry2) {
        if (emptyHandEntry2 == null) {
            return emptyHandEntry;
        }
        if (emptyHandEntry == null) {
            emptyHandEntry = new EmptyHandEntry();
        }
        emptyHandEntry.transforms = PunchyTuningConfig.mergeSideSetPreferBase(emptyHandEntry.transforms, emptyHandEntry2.transforms);
        if ((emptyHandEntry.physics == null || emptyHandEntry.physics.isJsonNull()) && emptyHandEntry2.physics != null && !emptyHandEntry2.physics.isJsonNull()) {
            emptyHandEntry.physics = emptyHandEntry2.physics;
        }
        if (emptyHandEntry.disableSprintLateralTilt == null) {
            emptyHandEntry.disableSprintLateralTilt = emptyHandEntry2.disableSprintLateralTilt;
        }
        if (emptyHandEntry.keepArmTuning == null) {
            emptyHandEntry.keepArmTuning = emptyHandEntry2.keepArmTuning;
        }
        if (emptyHandEntry.keepTriggerArmTuning == null) {
            emptyHandEntry.keepTriggerArmTuning = emptyHandEntry2.keepTriggerArmTuning;
        }
        if (emptyHandEntry.keepArmTuningL == null) {
            emptyHandEntry.keepArmTuningL = emptyHandEntry2.keepArmTuningL;
        }
        if (emptyHandEntry.keepArmTuningR == null) {
            emptyHandEntry.keepArmTuningR = emptyHandEntry2.keepArmTuningR;
        }
        emptyHandEntry.armTransforms = PunchyTuningConfig.mergeSideSetPreferBase(emptyHandEntry.armTransforms, emptyHandEntry2.armTransforms);
        emptyHandEntry.customAnimation = PunchyTuningConfig.mergeCustomAnimationEntryListsPreferBase(emptyHandEntry.customAnimation, emptyHandEntry2.customAnimation);
        emptyHandEntry.customItemAnimations = PunchyTuningConfig.mergeCustomAnimationEntryListsPreferBase(emptyHandEntry.customItemAnimations, emptyHandEntry2.customItemAnimations);
        if ((emptyHandEntry.betterCombat == null || emptyHandEntry.betterCombat.isEmpty()) && emptyHandEntry2.betterCombat != null && !emptyHandEntry2.betterCombat.isEmpty()) {
            emptyHandEntry.betterCombat = emptyHandEntry2.betterCombat;
        }
        return emptyHandEntry;
    }

    private static Map<String, SideSet> mergeSideSetMapPreferBase(Map<String, SideSet> map, Map<String, SideSet> map2) {
        if (map2 != null && !map2.isEmpty()) {
            if (map == null) {
                map = new HashMap<String, SideSet>();
            }
            for (Map.Entry<String, SideSet> entry : map2.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                SideSet sideSet = map.get(entry.getKey());
                if (sideSet == null) {
                    map.put(entry.getKey(), entry.getValue());
                    continue;
                }
                map.put(entry.getKey(), PunchyTuningConfig.mergeSideSetPreferBase(sideSet, entry.getValue()));
            }
            return map;
        }
        return map;
    }

    private static Map<String, Pose> mergePoseMapPreferBase(Map<String, Pose> map, Map<String, Pose> map2) {
        if (map2 != null && !map2.isEmpty()) {
            if (map == null) {
                map = new HashMap<String, Pose>();
            }
            for (Map.Entry<String, Pose> entry : map2.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                map.putIfAbsent(entry.getKey(), entry.getValue());
            }
            return map;
        }
        return map;
    }

    private static Map<String, GroupEntry> mergeGroupEntryMapPreferBase(Map<String, GroupEntry> map, Map<String, GroupEntry> map2) {
        if (map2 != null && !map2.isEmpty()) {
            if (map == null) {
                map = new HashMap<String, GroupEntry>();
            }
            for (Map.Entry<String, GroupEntry> entry : map2.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                GroupEntry groupEntry = PunchyTuningConfig.mergeGroupEntryPreferBase(map.get(entry.getKey()), entry.getValue());
                map.put(entry.getKey(), groupEntry);
            }
            return map;
        }
        return map;
    }

    private static GroupEntry mergeGroupEntryPreferBase(GroupEntry groupEntry, GroupEntry groupEntry2) {
        if (groupEntry2 == null) {
            return groupEntry;
        }
        if (groupEntry == null) {
            return groupEntry2;
        }
        if (groupEntry2.transforms == null && (groupEntry2.right != null || groupEntry2.left != null)) {
            groupEntry2.transforms = new SideSet();
            groupEntry2.transforms.right = groupEntry2.right;
            groupEntry2.transforms.left = groupEntry2.left;
        }
        groupEntry.transforms = PunchyTuningConfig.mergeSideSetPreferBase(groupEntry.transforms, groupEntry2.transforms);
        groupEntry.armTransforms = PunchyTuningConfig.mergeSideSetPreferBase(groupEntry.armTransforms, groupEntry2.armTransforms);
        if ((groupEntry.physics == null || groupEntry.physics.isJsonNull()) && groupEntry2.physics != null && !groupEntry2.physics.isJsonNull()) {
            groupEntry.physics = groupEntry2.physics;
        }
        if (groupEntry.disableSprintLateralTilt == null) {
            groupEntry.disableSprintLateralTilt = groupEntry2.disableSprintLateralTilt;
        }
        if (groupEntry.keepArmTuning == null) {
            groupEntry.keepArmTuning = groupEntry2.keepArmTuning;
        }
        if (groupEntry.keepTriggerArmTuning == null) {
            groupEntry.keepTriggerArmTuning = groupEntry2.keepTriggerArmTuning;
        }
        if (groupEntry.keepArmTuningL == null) {
            groupEntry.keepArmTuningL = groupEntry2.keepArmTuningL;
        }
        if (groupEntry.keepArmTuningR == null) {
            groupEntry.keepArmTuningR = groupEntry2.keepArmTuningR;
        }
        if (groupEntry.disableWalkOnWater == null) {
            groupEntry.disableWalkOnWater = groupEntry2.disableWalkOnWater;
        }
        if (groupEntry.disableDryOut == null) {
            groupEntry.disableDryOut = groupEntry2.disableDryOut;
        }
        if (groupEntry.disableSwiming == null) {
            groupEntry.disableSwiming = groupEntry2.disableSwiming;
        }
        groupEntry.customAnimation = PunchyTuningConfig.mergeCustomAnimationEntryListsPreferBase(groupEntry.customAnimation, groupEntry2.customAnimation);
        groupEntry.customItemAnimations = PunchyTuningConfig.mergeCustomAnimationEntryListsPreferBase(groupEntry.customItemAnimations, groupEntry2.customItemAnimations);
        if ((groupEntry.particleEmitters == null || groupEntry.particleEmitters.isJsonNull()) && groupEntry2.particleEmitters != null && !groupEntry2.particleEmitters.isJsonNull()) {
            groupEntry.particleEmitters = groupEntry2.particleEmitters;
        }
        if ((groupEntry.particle == null || groupEntry.particle.isJsonNull()) && groupEntry2.particle != null && !groupEntry2.particle.isJsonNull()) {
            groupEntry.particle = groupEntry2.particle;
        }
        if ((groupEntry.particles == null || groupEntry.particles.isJsonNull()) && groupEntry2.particles != null && !groupEntry2.particles.isJsonNull()) {
            groupEntry.particles = groupEntry2.particles;
        }
        if ((groupEntry.particleEffects == null || groupEntry.particleEffects.isJsonNull()) && groupEntry2.particleEffects != null && !groupEntry2.particleEffects.isJsonNull()) {
            groupEntry.particleEffects = groupEntry2.particleEffects;
        }
        if ((groupEntry.particleEmission == null || groupEntry.particleEmission.isJsonNull()) && groupEntry2.particleEmission != null && !groupEntry2.particleEmission.isJsonNull()) {
            groupEntry.particleEmission = groupEntry2.particleEmission;
        }
        if ((groupEntry.betterCombat == null || groupEntry.betterCombat.isEmpty()) && groupEntry2.betterCombat != null && !groupEntry2.betterCombat.isEmpty()) {
            groupEntry.betterCombat = groupEntry2.betterCombat;
        }
        return groupEntry;
    }

    private static Map<String, SpecificEntry> mergeSpecificMapPreferBase(Data data, Map<String, SpecificEntry> map, Map<String, SpecificEntry> map2) {
        if (map2 != null && !map2.isEmpty()) {
            if (map == null) {
                map = new HashMap<String, SpecificEntry>();
            }
            for (Map.Entry<String, SpecificEntry> entry : map2.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                class_2960 class_29602 = PunchyTuningConfig.resolveSpecificItemId(entry.getKey());
                boolean bl = PunchyTuningConfig.hasHigherPriorityTransformsForItem(data, class_29602);
                boolean bl2 = PunchyTuningConfig.hasHigherPriorityArmTransformsForItem(data, class_29602);
                SpecificEntry specificEntry = PunchyTuningConfig.mergeSpecificEntryPreferBase(map.get(entry.getKey()), entry.getValue(), bl, bl2);
                map.put(entry.getKey(), specificEntry);
            }
            return map;
        }
        return map;
    }

    private static SpecificEntry mergeSpecificEntryPreferBase(SpecificEntry specificEntry, SpecificEntry specificEntry2) {
        return PunchyTuningConfig.mergeSpecificEntryPreferBase(specificEntry, specificEntry2, false, false);
    }

    private static SpecificEntry mergeSpecificEntryPreferBase(SpecificEntry specificEntry, SpecificEntry specificEntry2, boolean bl, boolean bl2) {
        if (specificEntry2 == null) {
            return specificEntry;
        }
        if (specificEntry == null) {
            if (!bl && !bl2) {
                return specificEntry2;
            }
            SpecificEntry specificEntry3 = PunchyTuningConfig.copySpecificEntry(specificEntry2);
            if (bl) {
                specificEntry3.transforms = null;
            }
            if (bl2) {
                specificEntry3.armTransforms = null;
            }
            return specificEntry3;
        }
        if (bl && bl2) {
            return specificEntry;
        }
        if (specificEntry.kind == null) {
            specificEntry.kind = specificEntry2.kind;
        }
        if (specificEntry.armKind == null) {
            specificEntry.armKind = specificEntry2.armKind;
        }
        if (specificEntry.renamed == null) {
            specificEntry.renamed = specificEntry2.renamed;
        }
        if (specificEntry.model == null) {
            specificEntry.model = specificEntry2.model;
        }
        if (specificEntry.customModelData == null) {
            specificEntry.customModelData = specificEntry2.customModelData;
        }
        if ((specificEntry.physics == null || specificEntry.physics.isJsonNull()) && specificEntry2.physics != null && !specificEntry2.physics.isJsonNull()) {
            specificEntry.physics = specificEntry2.physics;
        }
        if (specificEntry.disableSprintLateralTilt == null) {
            specificEntry.disableSprintLateralTilt = specificEntry2.disableSprintLateralTilt;
        }
        if (specificEntry.keepArmTuning == null) {
            specificEntry.keepArmTuning = specificEntry2.keepArmTuning;
        }
        if (specificEntry.keepTriggerArmTuning == null) {
            specificEntry.keepTriggerArmTuning = specificEntry2.keepTriggerArmTuning;
        }
        if (specificEntry.keepArmTuningL == null) {
            specificEntry.keepArmTuningL = specificEntry2.keepArmTuningL;
        }
        if (specificEntry.keepArmTuningR == null) {
            specificEntry.keepArmTuningR = specificEntry2.keepArmTuningR;
        }
        if (specificEntry.disableWalkOnWater == null) {
            specificEntry.disableWalkOnWater = specificEntry2.disableWalkOnWater;
        }
        if (specificEntry.disableDryOut == null) {
            specificEntry.disableDryOut = specificEntry2.disableDryOut;
        }
        if (specificEntry.disableSwiming == null) {
            specificEntry.disableSwiming = specificEntry2.disableSwiming;
        }
        if ((specificEntry.animation == null || specificEntry.animation.isEmpty()) && specificEntry2.animation != null && !specificEntry2.animation.isEmpty()) {
            specificEntry.animation = specificEntry2.animation;
        }
        specificEntry.customAnimation = PunchyTuningConfig.mergeCustomAnimationEntryListsPreferBase(specificEntry.customAnimation, specificEntry2.customAnimation);
        specificEntry.customItemAnimations = PunchyTuningConfig.mergeCustomAnimationEntryListsPreferBase(specificEntry.customItemAnimations, specificEntry2.customItemAnimations);
        if ((specificEntry.particleEmitters == null || specificEntry.particleEmitters.isJsonNull()) && specificEntry2.particleEmitters != null && !specificEntry2.particleEmitters.isJsonNull()) {
            specificEntry.particleEmitters = specificEntry2.particleEmitters;
        }
        if ((specificEntry.particle == null || specificEntry.particle.isJsonNull()) && specificEntry2.particle != null && !specificEntry2.particle.isJsonNull()) {
            specificEntry.particle = specificEntry2.particle;
        }
        if ((specificEntry.particles == null || specificEntry.particles.isJsonNull()) && specificEntry2.particles != null && !specificEntry2.particles.isJsonNull()) {
            specificEntry.particles = specificEntry2.particles;
        }
        if ((specificEntry.particleEffects == null || specificEntry.particleEffects.isJsonNull()) && specificEntry2.particleEffects != null && !specificEntry2.particleEffects.isJsonNull()) {
            specificEntry.particleEffects = specificEntry2.particleEffects;
        }
        if ((specificEntry.particleEmission == null || specificEntry.particleEmission.isJsonNull()) && specificEntry2.particleEmission != null && !specificEntry2.particleEmission.isJsonNull()) {
            specificEntry.particleEmission = specificEntry2.particleEmission;
        }
        if ((specificEntry.betterCombat == null || specificEntry.betterCombat.isEmpty()) && specificEntry2.betterCombat != null && !specificEntry2.betterCombat.isEmpty()) {
            specificEntry.betterCombat = specificEntry2.betterCombat;
        }
        if (!bl) {
            specificEntry.transforms = PunchyTuningConfig.mergeSideSetPreferBase(specificEntry.transforms, specificEntry2.transforms);
        }
        if (!bl2) {
            specificEntry.armTransforms = PunchyTuningConfig.mergeSideSetPreferBase(specificEntry.armTransforms, specificEntry2.armTransforms);
        }
        return specificEntry;
    }

    private static boolean hasHigherPriorityTransformsForItem(Data data, class_2960 class_29602) {
        if (data != null && class_29602 != null) {
            if (data.itemSpecific != null && !data.itemSpecific.isEmpty()) {
                for (Map.Entry<String, SpecificEntry> entry : data.itemSpecific.entrySet()) {
                    if (entry == null || entry.getValue() == null || !PunchyTuningConfig.specificKeyTargetsItem(entry.getKey(), class_29602) || !PunchyTuningConfig.hasSideSetData(entry.getValue().transforms)) continue;
                    return true;
                }
            }
            GroupEntry groupEntry = PunchyTuningConfig.resolveHighestPriorityGroupEntryForItem(data, class_29602);
            return groupEntry != null && PunchyTuningConfig.hasSideSetData(groupEntry.transforms);
        }
        return false;
    }

    private static boolean hasHigherPriorityArmTransformsForItem(Data data, class_2960 class_29602) {
        if (data != null && class_29602 != null) {
            if (data.itemSpecific != null && !data.itemSpecific.isEmpty()) {
                for (Map.Entry<String, SpecificEntry> entry : data.itemSpecific.entrySet()) {
                    if (entry == null || entry.getValue() == null || !PunchyTuningConfig.specificKeyTargetsItem(entry.getKey(), class_29602) || !PunchyTuningConfig.hasSideSetData(entry.getValue().armTransforms)) continue;
                    return true;
                }
            }
            GroupEntry groupEntry = PunchyTuningConfig.resolveHighestPriorityGroupEntryForItem(data, class_29602);
            return groupEntry != null && PunchyTuningConfig.hasSideSetData(groupEntry.armTransforms);
        }
        return false;
    }

    private static GroupEntry resolveHighestPriorityGroupEntryForItem(Data data, class_2960 class_29602) {
        if (data != null && class_29602 != null && data.item != null && !data.item.isEmpty()) {
            String string = class_29602.toString();
            for (Map.Entry<String, GroupEntry> entry : data.item.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null || !PunchyTuningConfig.groupEntryMatchesItem(data, entry.getKey(), string, class_29602)) continue;
                return entry.getValue();
            }
            return null;
        }
        return null;
    }

    private static boolean groupEntryMatchesItem(Data data, String string, String string2, class_2960 class_29602) {
        if (string != null && string2 != null && class_29602 != null) {
            List<String> list = PunchyTuningConfig.resolveCustomGroupItems(data != null ? data.customGroups : null, string);
            if (list != null && list.contains(string2)) {
                return true;
            }
            try {
                VanillaFirstPersonItemLayer.ToolKind toolKind = VanillaFirstPersonItemLayer.ToolKind.valueOf(string);
                class_1792 class_17922 = class_7923.field_41178.method_17966(class_29602).orElse(class_1802.field_8162);
                if (class_17922 == class_1802.field_8162) {
                    return false;
                }
                class_1799 class_17992 = new class_1799((class_1935)class_17922);
                VanillaFirstPersonItemLayer.ToolKind toolKind2 = class_17992 != null ? ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT) : VanillaFirstPersonItemLayer.ToolKind.INGREDIENT;
                return toolKind2 == toolKind;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return false;
            }
        }
        return false;
    }

    private static List<String> resolveCustomGroupItems(List<CustomGroupDefinition> list, String string) {
        if (list != null && !list.isEmpty() && string != null) {
            for (CustomGroupDefinition customGroupDefinition : list) {
                if (customGroupDefinition == null || customGroupDefinition.name == null || customGroupDefinition.items == null || !customGroupDefinition.name.equalsIgnoreCase(string)) continue;
                return customGroupDefinition.items;
            }
            return null;
        }
        return null;
    }

    private static boolean hasSideSetData(SideSet sideSet) {
        return sideSet != null && (sideSet.right != null || sideSet.left != null);
    }

    private static List<CustomGroupDefinition> mergeCustomGroupsPreferBase(List<CustomGroupDefinition> list, List<CustomGroupDefinition> list2) {
        if (list2 != null && !list2.isEmpty()) {
            if (list == null) {
                list = new ArrayList<CustomGroupDefinition>();
            }
            HashSet<String> hashSet = new HashSet<String>();
            for (CustomGroupDefinition customGroupDefinition : list) {
                if (customGroupDefinition == null || customGroupDefinition.name == null || customGroupDefinition.name.isBlank()) continue;
                hashSet.add(customGroupDefinition.name.toLowerCase(Locale.ROOT));
            }
            for (CustomGroupDefinition customGroupDefinition : list2) {
                if (customGroupDefinition == null) continue;
                if (customGroupDefinition.name != null && !customGroupDefinition.name.isBlank()) {
                    String string = customGroupDefinition.name.toLowerCase(Locale.ROOT);
                    if (!hashSet.add(string)) continue;
                    list.add(customGroupDefinition);
                    continue;
                }
                list.add(customGroupDefinition);
            }
            return list;
        }
        return list;
    }

    private static Data mergeData(Data data, Data data2) {
        if (data2 == null) {
            return data;
        }
        if (data == null) {
            return data2;
        }
        data.globalItem = PunchyTuningConfig.mergeSideSet(data.globalItem, data2.globalItem);
        data.globalArm = PunchyTuningConfig.mergeSideSet(data.globalArm, data2.globalArm);
        data.default_animations = PunchyTuningConfig.mergeCustomAnimationEntryLists(data.default_animations, data2.default_animations);
        data.emptyHand = PunchyTuningConfig.mergeEmptyHand(data.emptyHand, data2.emptyHand);
        data.item = PunchyTuningConfig.mergeGroupEntryMap(data.item, data2.item);
        data.armMesh = PunchyTuningConfig.mergeSideSetMap(data.armMesh, data2.armMesh);
        data.chestProxy = PunchyTuningConfig.mergePoseMap(data.chestProxy, data2.chestProxy);
        data.fluidProxy = PunchyTuningConfig.mergePoseMap(data.fluidProxy, data2.fluidProxy);
        data.enchantParticleProxy = PunchyTuningConfig.mergePoseMap(data.enchantParticleProxy, data2.enchantParticleProxy);
        data.paddleProxy = PunchyTuningConfig.mergePoseMap(data.paddleProxy, data2.paddleProxy);
        data.fishingLineProxy = PunchyTuningConfig.mergePoseMap(data.fishingLineProxy, data2.fishingLineProxy);
        data.itemSpecific = PunchyTuningConfig.mergeSpecificMap(data.itemSpecific, data2.itemSpecific);
        if (data2.customGroups != null && !data2.customGroups.isEmpty()) {
            if (data.customGroups == null) {
                data.customGroups = new ArrayList<CustomGroupDefinition>();
            }
            data.customGroups.addAll(data2.customGroups);
        }
        return data;
    }

    private static SideSet mergeSideSet(SideSet sideSet, SideSet sideSet2) {
        if (sideSet2 != null && (sideSet2.right != null || sideSet2.left != null)) {
            if (sideSet == null) {
                sideSet = new SideSet();
            }
            if (sideSet2.right != null) {
                sideSet.right = sideSet2.right;
            }
            if (sideSet2.left != null) {
                sideSet.left = sideSet2.left;
            }
            return sideSet;
        }
        return sideSet;
    }

    private static EmptyHandEntry mergeEmptyHand(EmptyHandEntry emptyHandEntry, EmptyHandEntry emptyHandEntry2) {
        if (emptyHandEntry2 == null) {
            return emptyHandEntry;
        }
        if (emptyHandEntry == null) {
            emptyHandEntry = new EmptyHandEntry();
        }
        emptyHandEntry.transforms = PunchyTuningConfig.mergeSideSet(emptyHandEntry.transforms, emptyHandEntry2.transforms);
        if (emptyHandEntry2.physics != null && !emptyHandEntry2.physics.isJsonNull()) {
            emptyHandEntry.physics = emptyHandEntry2.physics;
        }
        if (emptyHandEntry2.disableSprintLateralTilt != null) {
            emptyHandEntry.disableSprintLateralTilt = emptyHandEntry2.disableSprintLateralTilt;
        }
        if (emptyHandEntry2.keepArmTuning != null) {
            emptyHandEntry.keepArmTuning = emptyHandEntry2.keepArmTuning;
        }
        if (emptyHandEntry2.keepTriggerArmTuning != null) {
            emptyHandEntry.keepTriggerArmTuning = emptyHandEntry2.keepTriggerArmTuning;
        }
        if (emptyHandEntry2.keepArmTuningL != null) {
            emptyHandEntry.keepArmTuningL = emptyHandEntry2.keepArmTuningL;
        }
        if (emptyHandEntry2.keepArmTuningR != null) {
            emptyHandEntry.keepArmTuningR = emptyHandEntry2.keepArmTuningR;
        }
        emptyHandEntry.armTransforms = PunchyTuningConfig.mergeSideSet(emptyHandEntry.armTransforms, emptyHandEntry2.armTransforms);
        emptyHandEntry.customAnimation = PunchyTuningConfig.mergeCustomAnimationEntryLists(emptyHandEntry.customAnimation, emptyHandEntry2.customAnimation);
        emptyHandEntry.customItemAnimations = PunchyTuningConfig.mergeCustomAnimationEntryLists(emptyHandEntry.customItemAnimations, emptyHandEntry2.customItemAnimations);
        if (emptyHandEntry2.betterCombat != null && !emptyHandEntry2.betterCombat.isEmpty()) {
            emptyHandEntry.betterCombat = emptyHandEntry2.betterCombat;
        }
        return emptyHandEntry;
    }

    private static Map<String, SideSet> mergeSideSetMap(Map<String, SideSet> map, Map<String, SideSet> map2) {
        if (map2 != null && !map2.isEmpty()) {
            if (map == null) {
                map = new HashMap<String, SideSet>();
            }
            for (Map.Entry<String, SideSet> entry : map2.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                map.put(entry.getKey(), entry.getValue());
            }
            return map;
        }
        return map;
    }

    private static Map<String, Pose> mergePoseMap(Map<String, Pose> map, Map<String, Pose> map2) {
        if (map2 != null && !map2.isEmpty()) {
            if (map == null) {
                map = new HashMap<String, Pose>();
            }
            for (Map.Entry<String, Pose> entry : map2.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                map.put(entry.getKey(), entry.getValue());
            }
            return map;
        }
        return map;
    }

    private static Map<String, GroupEntry> mergeGroupEntryMap(Map<String, GroupEntry> map, Map<String, GroupEntry> map2) {
        if (map2 != null && !map2.isEmpty()) {
            if (map == null) {
                map = new HashMap<String, GroupEntry>();
            }
            for (Map.Entry<String, GroupEntry> entry : map2.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                GroupEntry groupEntry = PunchyTuningConfig.mergeGroupEntry(map.get(entry.getKey()), entry.getValue());
                map.put(entry.getKey(), groupEntry);
            }
            return map;
        }
        return map;
    }

    private static GroupEntry mergeGroupEntry(GroupEntry groupEntry, GroupEntry groupEntry2) {
        if (groupEntry2 == null) {
            return groupEntry;
        }
        if (groupEntry == null) {
            return groupEntry2;
        }
        if (groupEntry2.transforms == null && (groupEntry2.right != null || groupEntry2.left != null)) {
            groupEntry2.transforms = new SideSet();
            groupEntry2.transforms.right = groupEntry2.right;
            groupEntry2.transforms.left = groupEntry2.left;
        }
        groupEntry.transforms = PunchyTuningConfig.mergeSideSet(groupEntry.transforms, groupEntry2.transforms);
        groupEntry.armTransforms = PunchyTuningConfig.mergeSideSet(groupEntry.armTransforms, groupEntry2.armTransforms);
        if (groupEntry2.physics != null && !groupEntry2.physics.isJsonNull()) {
            groupEntry.physics = groupEntry2.physics;
        }
        if (groupEntry2.disableSprintLateralTilt != null) {
            groupEntry.disableSprintLateralTilt = groupEntry2.disableSprintLateralTilt;
        }
        if (groupEntry2.keepArmTuning != null) {
            groupEntry.keepArmTuning = groupEntry2.keepArmTuning;
        }
        if (groupEntry2.keepTriggerArmTuning != null) {
            groupEntry.keepTriggerArmTuning = groupEntry2.keepTriggerArmTuning;
        }
        if (groupEntry2.keepArmTuningL != null) {
            groupEntry.keepArmTuningL = groupEntry2.keepArmTuningL;
        }
        if (groupEntry2.keepArmTuningR != null) {
            groupEntry.keepArmTuningR = groupEntry2.keepArmTuningR;
        }
        if (groupEntry2.disableWalkOnWater != null) {
            groupEntry.disableWalkOnWater = groupEntry2.disableWalkOnWater;
        }
        if (groupEntry2.disableDryOut != null) {
            groupEntry.disableDryOut = groupEntry2.disableDryOut;
        }
        if (groupEntry2.disableSwiming != null) {
            groupEntry.disableSwiming = groupEntry2.disableSwiming;
        }
        groupEntry.customAnimation = PunchyTuningConfig.mergeCustomAnimationEntryLists(groupEntry.customAnimation, groupEntry2.customAnimation);
        groupEntry.customItemAnimations = PunchyTuningConfig.mergeCustomAnimationEntryLists(groupEntry.customItemAnimations, groupEntry2.customItemAnimations);
        if (groupEntry2.particleEmitters != null && !groupEntry2.particleEmitters.isJsonNull()) {
            groupEntry.particleEmitters = groupEntry2.particleEmitters;
        }
        if (groupEntry2.particle != null && !groupEntry2.particle.isJsonNull()) {
            groupEntry.particle = groupEntry2.particle;
        }
        if (groupEntry2.particles != null && !groupEntry2.particles.isJsonNull()) {
            groupEntry.particles = groupEntry2.particles;
        }
        if (groupEntry2.particleEffects != null && !groupEntry2.particleEffects.isJsonNull()) {
            groupEntry.particleEffects = groupEntry2.particleEffects;
        }
        if (groupEntry2.particleEmission != null && !groupEntry2.particleEmission.isJsonNull()) {
            groupEntry.particleEmission = groupEntry2.particleEmission;
        }
        if (groupEntry2.betterCombat != null && !groupEntry2.betterCombat.isEmpty()) {
            groupEntry.betterCombat = groupEntry2.betterCombat;
        }
        return groupEntry;
    }

    private static Map<String, SpecificEntry> mergeSpecificMap(Map<String, SpecificEntry> map, Map<String, SpecificEntry> map2) {
        if (map2 != null && !map2.isEmpty()) {
            if (map == null) {
                map = new HashMap<String, SpecificEntry>();
            }
            for (Map.Entry<String, SpecificEntry> entry : map2.entrySet()) {
                if (entry == null || entry.getKey() == null || entry.getValue() == null) continue;
                SpecificEntry specificEntry = PunchyTuningConfig.mergeSpecificEntry(map.get(entry.getKey()), entry.getValue());
                map.put(entry.getKey(), specificEntry);
            }
            return map;
        }
        return map;
    }

    private static SpecificEntry mergeSpecificEntry(SpecificEntry specificEntry, SpecificEntry specificEntry2) {
        if (specificEntry2 == null) {
            return specificEntry;
        }
        if (specificEntry == null) {
            return specificEntry2;
        }
        if (specificEntry2.kind != null) {
            specificEntry.kind = specificEntry2.kind;
        }
        if (specificEntry2.armKind != null) {
            specificEntry.armKind = specificEntry2.armKind;
        }
        if (specificEntry2.renamed != null) {
            specificEntry.renamed = specificEntry2.renamed;
        }
        if (specificEntry2.model != null) {
            specificEntry.model = specificEntry2.model;
        }
        if (specificEntry2.customModelData != null) {
            specificEntry.customModelData = specificEntry2.customModelData;
        }
        if (specificEntry2.physics != null && !specificEntry2.physics.isJsonNull()) {
            specificEntry.physics = specificEntry2.physics;
        }
        if (specificEntry2.disableSprintLateralTilt != null) {
            specificEntry.disableSprintLateralTilt = specificEntry2.disableSprintLateralTilt;
        }
        if (specificEntry2.keepArmTuning != null) {
            specificEntry.keepArmTuning = specificEntry2.keepArmTuning;
        }
        if (specificEntry2.keepTriggerArmTuning != null) {
            specificEntry.keepTriggerArmTuning = specificEntry2.keepTriggerArmTuning;
        }
        if (specificEntry2.keepArmTuningL != null) {
            specificEntry.keepArmTuningL = specificEntry2.keepArmTuningL;
        }
        if (specificEntry2.keepArmTuningR != null) {
            specificEntry.keepArmTuningR = specificEntry2.keepArmTuningR;
        }
        if (specificEntry2.disableWalkOnWater != null) {
            specificEntry.disableWalkOnWater = specificEntry2.disableWalkOnWater;
        }
        if (specificEntry2.disableDryOut != null) {
            specificEntry.disableDryOut = specificEntry2.disableDryOut;
        }
        if (specificEntry2.disableSwiming != null) {
            specificEntry.disableSwiming = specificEntry2.disableSwiming;
        }
        if (specificEntry2.animation != null && !specificEntry2.animation.isEmpty()) {
            specificEntry.animation = specificEntry2.animation;
        }
        specificEntry.customAnimation = PunchyTuningConfig.mergeCustomAnimationEntryLists(specificEntry.customAnimation, specificEntry2.customAnimation);
        specificEntry.customItemAnimations = PunchyTuningConfig.mergeCustomAnimationEntryLists(specificEntry.customItemAnimations, specificEntry2.customItemAnimations);
        if (specificEntry2.particleEmitters != null && !specificEntry2.particleEmitters.isJsonNull()) {
            specificEntry.particleEmitters = specificEntry2.particleEmitters;
        }
        if (specificEntry2.particle != null && !specificEntry2.particle.isJsonNull()) {
            specificEntry.particle = specificEntry2.particle;
        }
        if (specificEntry2.particles != null && !specificEntry2.particles.isJsonNull()) {
            specificEntry.particles = specificEntry2.particles;
        }
        if (specificEntry2.particleEffects != null && !specificEntry2.particleEffects.isJsonNull()) {
            specificEntry.particleEffects = specificEntry2.particleEffects;
        }
        if (specificEntry2.particleEmission != null && !specificEntry2.particleEmission.isJsonNull()) {
            specificEntry.particleEmission = specificEntry2.particleEmission;
        }
        if (specificEntry2.betterCombat != null && !specificEntry2.betterCombat.isEmpty()) {
            specificEntry.betterCombat = specificEntry2.betterCombat;
        }
        specificEntry.transforms = PunchyTuningConfig.mergeSideSet(specificEntry.transforms, specificEntry2.transforms);
        specificEntry.armTransforms = PunchyTuningConfig.mergeSideSet(specificEntry.armTransforms, specificEntry2.armTransforms);
        return specificEntry;
    }

    private static class_2960 resolveSpecificItemId(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim();
            class_2960 class_29602 = class_2960.method_12829((String)string2);
            if (class_29602 != null) {
                return class_29602;
            }
            int n = -1;
            int n2 = string2.indexOf(35);
            int n3 = string2.indexOf(124);
            int n4 = string2.indexOf(64);
            if (n2 > 0) {
                n = n2;
            }
            if (n3 > 0 && (n < 0 || n3 < n)) {
                n = n3;
            }
            if (n4 > 0 && (n < 0 || n4 < n)) {
                n = n4;
            }
            return n <= 0 ? null : class_2960.method_12829((String)string2.substring(0, n).trim());
        }
        return null;
    }

    private static boolean specificKeyTargetsItem(String string, class_2960 class_29602) {
        if (class_29602 == null) {
            return false;
        }
        class_2960 class_29603 = PunchyTuningConfig.resolveSpecificItemId(string);
        return class_29603 != null && class_29603.equals((Object)class_29602);
    }

    private static Data finalizeCompatData(String string, Data data) {
        return data;
    }

    private static Path getCompatDirectory() {
        Path path = PunchyTuningConfig.getConfigDirectory();
        return path.resolve("minecraft/punchy/compat");
    }

    private static void resetAllTunings() {
        ToolTuning.resetDefaults();
        ArmToolTuning.resetDefaults();
        ArmMeshTuning.resetDefaults();
        for (ChestProxyTuning.Kind enum_ : ChestProxyTuning.Kind.values()) {
            ChestProxyTuning.reset(enum_);
        }
        for (Enum enum_ : FluidProxyTuning.Kind.values()) {
            FluidProxyTuning.reset((FluidProxyTuning.Kind)enum_);
        }
        for (Enum enum_ : EnchantParticleProxyTuning.Kind.values()) {
            EnchantParticleProxyTuning.reset((EnchantParticleProxyTuning.Kind)enum_, false);
            EnchantParticleProxyTuning.reset((EnchantParticleProxyTuning.Kind)enum_, true);
        }
        for (Enum enum_ : PaddleProxyTuning.Kind.values()) {
            PaddleProxyTuning.reset((PaddleProxyTuning.Kind)enum_, false);
            PaddleProxyTuning.reset((PaddleProxyTuning.Kind)enum_, true);
        }
        for (Enum enum_ : FishingLineProxyTuning.Kind.values()) {
            FishingLineProxyTuning.reset((FishingLineProxyTuning.Kind)enum_, false);
            FishingLineProxyTuning.reset((FishingLineProxyTuning.Kind)enum_, true);
        }
    }

    private static Path getConfigDirectory() {
        class_310 class_3102 = class_310.method_1551();
        return ru.method.celebression.Celebression.getInstance().getGlobalsDir().toPath().resolve("config");
    }

    private static void installBundledCompatProfiles() {
        for (String string : BUNDLED_COMPAT_PROFILES) {
            PunchyTuningConfig.copyBundledCompatProfile(string);
        }
    }

    private static void copyBundledCompatProfile(String string) {
        try (InputStream inputStream = PunchyTuningConfig.openBundledCompatStream(string);){
            if (inputStream != null) {
                Path path = PunchyTuningConfig.getCompatDirectory();
                Files.createDirectories(path, new FileAttribute[0]);
                Path path2 = path.resolve(string + ".json");
                Files.copy(inputStream, path2, StandardCopyOption.REPLACE_EXISTING);
                return;
            }
        }
        catch (Exception exception) {
        }
    }

    private static InputStream openBundledCompatStream(String string) {
        if (string == null || string.isBlank()) {
            return null;
        }
        String fileName = string.endsWith(".json") ? string : string + ".json";
        String[] candidates = new String[]{
            "/resourcepacks/punchy/assets/minecraft/punchy/compat/" + fileName,
            "/assets/minecraft/punchy/compat/" + fileName,
            "/assets/punchy/punchy/compat/" + fileName
        };
        for (String candidate : candidates) {
            InputStream inputStream = PunchyTuningConfig.class.getResourceAsStream(candidate);
            if (inputStream != null) {
                return inputStream;
            }
        }
        return null;
    }

    private static MixSlotDef createSpecificMixSlot(class_2960 class_29602, String string) {
        MixSlotDef mixSlotDef = new MixSlotDef();
        mixSlotDef.slotId = string;
        mixSlotDef.type = MixSlotType.SPECIFIC;
        mixSlotDef.itemId = class_29602;
        mixSlotDef.label = "Item Specific: " + String.valueOf(class_29602);
        return mixSlotDef;
    }

    private static MixSlotDef createKindMixSlot(VanillaFirstPersonItemLayer.ToolKind toolKind, String string) {
        MixSlotDef mixSlotDef = new MixSlotDef();
        mixSlotDef.slotId = string;
        mixSlotDef.type = MixSlotType.KIND;
        mixSlotDef.kind = toolKind;
        mixSlotDef.label = PunchyTuningConfig.prettySlotLabel(toolKind.name());
        return mixSlotDef;
    }

    static {
        FILE_NAME = "punchy_tuning.json";
        MERGED_ANIMATION_SELECTION_FILE = "merged_animation_selection.json";
        COMPAT_DIR_NAME = "minecraft/punchy/compat";
        ARM_MESH_SIDE_KEY = "global";
        MIX_DEFAULT_OPTION_ID = "punchy_default";
        BUILTIN_PACK_OPTION_ID = "pack:punchy:punchy";
        LOGGER = LoggerFactory.getLogger((String)"Punchy/MixPacks");
        PERF_DEBUG = Boolean.getBoolean("punchy.perfDebug");
        GSON = new GsonBuilder().setPrettyPrinting().create();
        DEFAULTS_ID = class_2960.method_60655((String)"punchy", (String)"default_tuning.json");
        BUNDLED_COMPAT_PROFILES = new String[]{"items_compat"};
        MIX_HIDDEN_SPECIFIC_IDS = Set.of("adorablehamsterpets:cheese");
        REGISTRY_CUSTOM_GROUPS = new HashMap<String, List<String>>();
        MIX_CATALOG = new MixCatalog();
        MIX_COMPAT_OPTION_IDS = new LinkedHashSet<String>();
        MIX_PUNCHY_OPTION_IDS = new LinkedHashSet<String>();
        GROUP_RUNTIME_FLAGS = new EnumMap(VanillaFirstPersonItemLayer.ToolKind.class);
        SPECIFIC_RUNTIME_FLAGS = new HashMap<class_2960, ItemRuntimeFlags>();
        SPEAR_MIX_CLIP_NAMES = Set.of("attack_spear", "use_spear_pose_1", "use_spear_pose_2", "use_spear_pose_3", "use_spear_pose_3_left", "use_spear_pose_3_right");
        SWORD_PASSIVE_CLIP_NAMES = Set.of("attack", "attack_sword", "mining", "mining_foward", "mining_forward", "mining_sideways", "mining_digging", "mining_use_foward", "mining_use_forward", "mining_use_digging", "use_item", "useitem", "interaction", "interact", "inspect", "hand_in", "hand_out");
        GENERIC_PASSIVE_CLIP_NAMES = Set.of("attack", "use_item", "useitem", "interaction", "interact", "inspect", "hand_in", "hand_out");
        MIX_TYPE_CLIP_ALIASES = PunchyTuningConfig.createMixTypeClipAliases();
    }

    static final class MixCatalog {
        final LinkedHashMap<String, MixSlotDef> slots = new LinkedHashMap();

        MixCatalog() {
        }
    }

    static final class MixSlotDef {
        String slotId;
        String label;
        MixSlotType type;
        VanillaFirstPersonItemLayer.ToolKind kind;
        class_2960 itemId;
        final LinkedHashSet<String> optionOrder = new LinkedHashSet();
        final LinkedHashMap<String, MixSlotPayload> optionPayloads = new LinkedHashMap();
        final LinkedHashSet<class_2960> mergedSpecificItems = new LinkedHashSet();
        final LinkedHashMap<String, LinkedHashMap<class_2960, MixSlotPayload>> optionSpecificPayloads = new LinkedHashMap();
        final LinkedHashMap<String, LinkedHashSet<String>> optionClipNames = new LinkedHashMap();
        String resourceOrderOption;

        MixSlotDef() {
        }
    }

    static enum MixSlotType {
        KIND,
        SPECIFIC;

    }

    static final class MixSlotPayload {
        VanillaFirstPersonItemLayer.ToolKind kind;
        GroupEntry groupEntry;
        EmptyHandEntry emptyHandEntry;
        SpecificEntry specificEntry;

        MixSlotPayload() {
        }
    }

    public static final class CustomAnimationEntry {
        public String type;
        public List<BetterCombatEntry> betterCombat;
        public String key;
        @SerializedName(value="min_blocks", alternate={"minBlocks", "falling_min_blocks", "trigger_falling_min_blocks"})
        public Float min_blocks;
        public String var_1;
        public String var_2;
        public String var_3;
        public String attack_1;
        public String attack_2;
        public String attack_3;
        public String attack_4;
        public String attack_5;
        public String attack_6;
        public String critical_hit;
        public String name;
        public String animation;
        public String start;
        public String looping;
        public String end;
        @SerializedName(value="throw", alternate={"throw_animation", "cast", "start_cast"})
        public String throw_animation;
        @SerializedName(value="pull", alternate={"pull_animation", "reel", "reel_in"})
        public String pull_animation;
        @SerializedName(value="disable_walk_on_water", alternate={"disable_walkonwater", "disable_walk_on_water_v2", "disable_walk_on_water_animation"})
        public Boolean disable_walk_on_water;
        @SerializedName(value="disable_dry_out", alternate={"disable_dryout", "disable_dryout_animation"})
        public Boolean disable_dry_out;
        @SerializedName(value="stage_1", alternate={"stage1"})
        public String stage_1;
        @SerializedName(value="stage_2", alternate={"stage2"})
        public String stage_2;
        @SerializedName(value="stage_3", alternate={"stage3"})
        public String stage_3;
        public String keep_looping;
        public List<String> items;
        public JsonObject blockState;
        public Boolean in_water;
        public Boolean inside_water;
        public BlockDetection block_detection;
        public WeatherDetection weather_detection;
        public TimeDetection time_detection;
        public BiomeDetection biome_detection;
        public Boolean skip_blend_in;
        public Boolean skip_blend_out;
        @SerializedName(value="blend_from_current_origin", alternate={"blendFromCurrentOrigin", "rebase_blend_in", "rebaseBlendIn"})
        public Boolean blend_from_current_origin;
        public Boolean disable_swim_animation;
        public Boolean disable_all_particles;
        @SerializedName(value="disable_physics_bones", alternate={"disable_pendulum_bones"})
        public List<String> disable_physics_bones;
        public Float interfaceDelay;
        public Boolean stop_immediate;
        @SerializedName(value="skip_hand_equip", alternate={"disable_hand_equip", "no_hand_equip"})
        public Boolean skip_hand_equip;
        public Float loop_speed_max;
        public Float loop_speed_ramp;
        public Boolean isJavaBlock;
        public transient String mix_pack_option;
        public CustomFlags customFlags;
    }

    static final class ItemRuntimeFlags {
        Boolean disableWalkOnWater;
        Boolean disableDryOut;
        Boolean disableSwiming;

        ItemRuntimeFlags() {
        }
    }

    public static final class SpecificEntry {
        public String kind;
        public String armKind;
        public String renamed;
        public JsonElement physics;
        @SerializedName(value="disable_sprint_lateral_tilt", alternate={"disableSprintLateralTilt"})
        public Boolean disableSprintLateralTilt;
        @SerializedName(value="keep_arm_tuning", alternate={"keepArmTuning"})
        public Boolean keepArmTuning;
        @SerializedName(value="keep_trigger_arm_tuning", alternate={"keepTriggerArmTuning", "keep_main_arm_tuning", "keepMainArmTuning"})
        public Boolean keepTriggerArmTuning;
        @SerializedName(value="keep_arm_tuning_l", alternate={"keepArmTuningL"})
        public Boolean keepArmTuningL;
        @SerializedName(value="keep_arm_tuning_r", alternate={"keepArmTuningR"})
        public Boolean keepArmTuningR;
        public String model;
        @SerializedName(value="customModelData", alternate={"custom_model_data", "custommodeldata"})
        public Integer customModelData;
        public List<AnimationRef> animation;
        public List<CustomAnimationEntry> customAnimation;
        public List<CustomAnimationEntry> customItemAnimations;
        @SerializedName(value="disable_walk_on_water", alternate={"disable_walkonwater", "disable_walk_on_water_v2", "disable_walk_on_water_animation"})
        public Boolean disableWalkOnWater;
        @SerializedName(value="disable_dry_out", alternate={"disable_dryout", "disable_dryout_animation"})
        public Boolean disableDryOut;
        @SerializedName(value="disable_swiming", alternate={"disable_swimming", "disable_swim", "disable_swim_animation"})
        public Boolean disableSwiming;
        @SerializedName(value="particle_emitters", alternate={"particleEmitters"})
        public JsonElement particleEmitters;
        public JsonElement particle;
        public JsonElement particles;
        @SerializedName(value="particle_effects", alternate={"particleEffects"})
        public JsonElement particleEffects;
        @SerializedName(value="particle_emission", alternate={"particleEmission"})
        public JsonElement particleEmission;
        public List<BetterCombatEntry> betterCombat;
        @SerializedName(value="bone_physics", alternate={"bonePhysics"})
        public JsonElement bonePhysics;
        public SideSet transforms;
        public SideSet armTransforms;
    }

    public static final class GroupEntry {
        public SideSet transforms;
        public SideSet armTransforms;
        public JsonElement physics;
        @SerializedName(value="disable_sprint_lateral_tilt", alternate={"disableSprintLateralTilt"})
        public Boolean disableSprintLateralTilt;
        @SerializedName(value="keep_arm_tuning", alternate={"keepArmTuning"})
        public Boolean keepArmTuning;
        @SerializedName(value="keep_trigger_arm_tuning", alternate={"keepTriggerArmTuning", "keep_main_arm_tuning", "keepMainArmTuning"})
        public Boolean keepTriggerArmTuning;
        @SerializedName(value="keep_arm_tuning_l", alternate={"keepArmTuningL"})
        public Boolean keepArmTuningL;
        @SerializedName(value="keep_arm_tuning_r", alternate={"keepArmTuningR"})
        public Boolean keepArmTuningR;
        public List<CustomAnimationEntry> customAnimation;
        public List<CustomAnimationEntry> customItemAnimations;
        @SerializedName(value="disable_walk_on_water", alternate={"disable_walkonwater", "disable_walk_on_water_v2", "disable_walk_on_water_animation"})
        public Boolean disableWalkOnWater;
        @SerializedName(value="disable_dry_out", alternate={"disable_dryout", "disable_dryout_animation"})
        public Boolean disableDryOut;
        @SerializedName(value="disable_swiming", alternate={"disable_swimming", "disable_swim", "disable_swim_animation"})
        public Boolean disableSwiming;
        @SerializedName(value="particle_emitters", alternate={"particleEmitters"})
        public JsonElement particleEmitters;
        public JsonElement particle;
        public JsonElement particles;
        @SerializedName(value="particle_effects", alternate={"particleEffects"})
        public JsonElement particleEffects;
        @SerializedName(value="particle_emission", alternate={"particleEmission"})
        public JsonElement particleEmission;
        public List<BetterCombatEntry> betterCombat;
        public Pose right;
        public Pose left;
    }

    public static final class Data {
        public Map<String, GroupEntry> item = new HashMap<String, GroupEntry>();
        @SerializedName(value="default_animations", alternate={"defaultAnimations"})
        public List<CustomAnimationEntry> default_animations;
        public Map<String, SideSet> armMesh = new HashMap<String, SideSet>();
        public SideSet globalItem;
        public SideSet globalArm;
        public EmptyHandEntry emptyHand;
        public Map<String, Pose> chestProxy = new HashMap<String, Pose>();
        public Map<String, Pose> fluidProxy = new HashMap<String, Pose>();
        public Map<String, Pose> enchantParticleProxy = new HashMap<String, Pose>();
        public Map<String, Pose> paddleProxy = new HashMap<String, Pose>();
        public Map<String, Pose> fishingLineProxy = new HashMap<String, Pose>();
        public Map<String, SpecificEntry> itemSpecific = new HashMap<String, SpecificEntry>();
        @SerializedName(value="customGroups", alternate={"custom_groups"})
        public List<CustomGroupDefinition> customGroups;
    }

    public static final class CustomGroupDefinition {
        public String name;
        public List<String> items;
    }

    public static final class PhysicsEntry {
        public String name;
        public Float limitDeg;
        public Float limitDegUp;
        public Float limitDegDown;
        public Float force;
        public Float speed;
        public Float cameraSens;
        public Float gravity;
        public Float motionSens;
        public Boolean invert;
        public Boolean handSpace;
        public Boolean enableCreaturePhysics;
    }

    public static final class SideSet {
        public Pose right;
        public Pose left;
    }

    public static final class Pose {
        public float scale;
        public float offX;
        public float offY;
        public float offZ;
        public float rotX;
        public float rotY;
        public float rotZ;
        public float pivotX;
        public float pivotY;
        public float pivotZ;
        public String physics;
        @SerializedName(value="disable_sprint_lateral_tilt", alternate={"disableSprintLateralTilt"})
        public Boolean disableSprintLateralTilt;
        @SerializedName(value="keep_arm_tuning", alternate={"keepArmTuning"})
        public Boolean keepArmTuning;
        @SerializedName(value="keep_trigger_arm_tuning", alternate={"keepTriggerArmTuning", "keep_main_arm_tuning", "keepMainArmTuning"})
        public Boolean keepTriggerArmTuning;
        @SerializedName(value="keep_arm_tuning_l", alternate={"keepArmTuningL"})
        public Boolean keepArmTuningL;
        @SerializedName(value="keep_arm_tuning_r", alternate={"keepArmTuningR"})
        public Boolean keepArmTuningR;

        public Pose() {
        }

        public Pose(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            this(f, f2, f3, f4, f5, f6, f7, 0.0f, 0.0f, 0.0f, null);
        }

        public Pose(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            this(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, null);
        }

        public Pose(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, String string) {
            this.scale = f;
            this.offX = f2;
            this.offY = f3;
            this.offZ = f4;
            this.rotX = f5;
            this.rotY = f6;
            this.rotZ = f7;
            this.pivotX = f8;
            this.pivotY = f9;
            this.pivotZ = f10;
            this.physics = string;
        }

        public static Pose fromTool(ToolTuning.Profile profile) {
            String string = profile.physicsMode != null && profile.physicsMode != ToolTuning.PhysicsMode.NONE ? profile.physicsMode.name() : null;
            Pose pose = new Pose(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ, profile.pivotX, profile.pivotY, profile.pivotZ, string);
            pose.disableSprintLateralTilt = profile.disableSprintLateralTilt;
            pose.keepArmTuning = profile.keepArmTuning;
            pose.keepTriggerArmTuning = profile.keepTriggerArmTuning;
            return pose;
        }

        public static Pose fromArm(ArmToolTuning.Profile profile) {
            return new Pose(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ);
        }

        public ToolTuning.Profile toTool() {
            ToolTuning.PhysicsMode physicsMode = ToolTuning.PhysicsMode.NONE;
            if (this.physics != null && !this.physics.isBlank()) {
                try {
                    physicsMode = ToolTuning.PhysicsMode.valueOf(this.physics.toUpperCase(Locale.ROOT));
                }
                catch (IllegalArgumentException illegalArgumentException) {
                }
            }
            ToolTuning.Profile profile = new ToolTuning.Profile(this.scale, this.offX, this.offY, this.offZ, this.rotX, this.rotY, this.rotZ, this.pivotX, this.pivotY, this.pivotZ, physicsMode);
            profile.disableSprintLateralTilt = this.disableSprintLateralTilt;
            profile.keepArmTuning = this.keepArmTuning != null ? this.keepArmTuning : (this.keepArmTuningL != null ? this.keepArmTuningL : this.keepArmTuningR);
            profile.keepTriggerArmTuning = this.keepTriggerArmTuning;
            return profile;
        }

        public ArmToolTuning.Profile toArm() {
            return new ArmToolTuning.Profile(this.scale, this.offX, this.offY, this.offZ, this.rotX, this.rotY, this.rotZ);
        }

        public static Pose fromArmMesh(ArmMeshTuning.Profile profile) {
            return profile == null ? null : new Pose(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ);
        }

        public ArmMeshTuning.Profile toArmMesh() {
            return new ArmMeshTuning.Profile(this.scale, this.offX, this.offY, this.offZ, this.rotX, this.rotY, this.rotZ);
        }

        public static Pose fromChestProxy(ChestProxyTuning.Profile profile) {
            return profile == null ? null : new Pose(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ);
        }

        public ChestProxyTuning.Profile toChestProxy() {
            return new ChestProxyTuning.Profile(this.offX, this.offY, this.offZ, this.rotX, this.rotY, this.rotZ, this.scale);
        }

        public static Pose fromFluidProxy(FluidProxyTuning.Profile profile) {
            return profile == null ? null : new Pose(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ);
        }

        public FluidProxyTuning.Profile toFluidProxy() {
            return new FluidProxyTuning.Profile(this.offX, this.offY, this.offZ, this.rotX, this.rotY, this.rotZ, this.scale);
        }

        public static Pose fromEnchantParticleProxy(EnchantParticleProxyTuning.Profile profile) {
            return profile == null ? null : new Pose(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ);
        }

        public EnchantParticleProxyTuning.Profile toEnchantParticleProxy() {
            return new EnchantParticleProxyTuning.Profile(this.offX, this.offY, this.offZ, this.rotX, this.rotY, this.rotZ, this.scale);
        }

        public static Pose fromPaddleProxy(PaddleProxyTuning.Profile profile) {
            return profile == null ? null : new Pose(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ);
        }

        public PaddleProxyTuning.Profile toPaddleProxy() {
            return new PaddleProxyTuning.Profile(this.offX, this.offY, this.offZ, this.rotX, this.rotY, this.rotZ, this.scale);
        }

        public static Pose fromFishingLineProxy(FishingLineProxyTuning.Profile profile) {
            return profile == null ? null : new Pose(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ);
        }

        public FishingLineProxyTuning.Profile toFishingLineProxy() {
            return new FishingLineProxyTuning.Profile(this.offX, this.offY, this.offZ, this.rotX, this.rotY, this.rotZ, this.scale);
        }
    }

    public static final class EmptyHandEntry {
        public SideSet transforms;
        public JsonElement physics;
        @SerializedName(value="disable_sprint_lateral_tilt", alternate={"disableSprintLateralTilt"})
        public Boolean disableSprintLateralTilt;
        @SerializedName(value="keep_arm_tuning", alternate={"keepArmTuning"})
        public Boolean keepArmTuning;
        @SerializedName(value="keep_trigger_arm_tuning", alternate={"keepTriggerArmTuning", "keep_main_arm_tuning", "keepMainArmTuning"})
        public Boolean keepTriggerArmTuning;
        @SerializedName(value="keep_arm_tuning_l", alternate={"keepArmTuningL"})
        public Boolean keepArmTuningL;
        @SerializedName(value="keep_arm_tuning_r", alternate={"keepArmTuningR"})
        public Boolean keepArmTuningR;
        public SideSet armTransforms;
        public List<CustomAnimationEntry> customAnimation;
        public List<CustomAnimationEntry> customItemAnimations;
        public List<BetterCombatEntry> betterCombat;
    }

    static enum EnchantParticleProxySide {
        LEFT,
        RIGHT,
        BOTH;

    }

    static enum PaddleProxySide {
        LEFT,
        RIGHT,
        BOTH;

    }

    static enum FishingLineProxySide {
        LEFT,
        RIGHT,
        BOTH;

    }

    static final class CompatResourceEntry {
        final class_2960 id;
        final String profile;
        final class_3298 resource;
        final int packPriority;
        final int discoveryOrder;
        final int stackOrder;

        CompatResourceEntry(class_2960 class_29602, String string, class_3298 class_32982, int n, int n2, int n3) {
            this.id = class_29602;
            this.profile = string;
            this.resource = class_32982;
            this.packPriority = n;
            this.discoveryOrder = n2;
            this.stackOrder = n3;
        }
    }

    static final class MixCatalogOrdering {
        final LinkedHashMap<String, Integer> optionPriorities = new LinkedHashMap();
        final ArrayList<String> selectedOptionIdsByPriority = new ArrayList();

        MixCatalogOrdering() {
        }
    }

    public static final class BetterCombatEntry {
        public String type;
        public String attack_1;
        public String attack_2;
        public String attack_3;
        public String attack_4;
        public String attack_5;
        public String attack_6;
    }

    public static final class WeatherDetection {
        public Boolean raining;
        public Boolean thundering;
        public Boolean clear;
    }

    public static final class TimeDetection {
        public Integer min;
        public Integer max;
        public List<Integer> range;
    }

    public static final class CustomFlags {
        @SerializedName(value="sync_procedural_arm_animations", alternate={"syncProceduralArmAnimations"})
        public Boolean syncProceduralArmAnimations;
        @SerializedName(value="walk_and_sprint_force", alternate={"walkAndSprintForce"})
        public Integer walkAndSprintForce;
        @SerializedName(value="remaining_procedural_animations_force", alternate={"remainingProceduralAnimationsForce"})
        public Integer remainingProceduralAnimationsForce;
    }

    public static final class BlockDetection {
        public String block;
        public List<String> blocks;
        public Integer radius;
        public Integer offsetX;
        public Integer offsetY;
        public Integer offsetZ;
    }

    public static final class BiomeDetection {
        public String biome;
        public List<String> biomes;
        public Integer radius;
        public Integer offsetX;
        public Integer offsetY;
        public Integer offsetZ;
    }

    public static final class AnimationRef {
        public String type;
        public String name;
    }
}

