package ru.method.celebression.punchy.client.render;

import com.google.gson.JsonElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import net.minecraft.class_9334;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import ru.method.celebression.punchy.client.render.ToolKindResolver;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;
import ru.method.celebression.punchy.config.PunchyTuningConfig;

public final class ToolTuning {
    private static Profile GLOBAL_RIGHT;
    private static Profile GLOBAL_LEFT;
    private static Profile EMPTY_R;
    private static Profile EMPTY_L;
    private static Profile AXE_R;
    private static Profile AXE_L;
    private static Profile PICKAXE_R;
    private static Profile PICKAXE_L;
    private static Profile HOE_R;
    private static Profile HOE_L;
    private static Profile SWORD_R;
    private static Profile SWORD_L;
    private static Profile SHOVEL_R;
    private static Profile SHOVEL_L;
    private static Profile SHIELD_R;
    private static Profile SHIELD_L;
    private static Profile BLOCK_R;
    private static Profile BLOCK_L;
    private static Profile FULL_BLOCK_R;
    private static Profile FULL_BLOCK_L;
    private static Profile BUILDING_BLOCKS_R;
    private static Profile BUILDING_BLOCKS_L;
    private static Profile COLORED_BLOCKS_R;
    private static Profile COLORED_BLOCKS_L;
    private static Profile NATURAL_BLOCKS_R;
    private static Profile NATURAL_BLOCKS_L;
    private static Profile FUNCTIONAL_BLOCKS_R;
    private static Profile FUNCTIONAL_BLOCKS_L;
    private static Profile CHEST_R;
    private static Profile CHEST_L;
    private static Profile FLOWER_PENDULUM_R;
    private static Profile FLOWER_PENDULUM_L;
    private static Profile BOW_R;
    private static Profile BOW_L;
    private static Profile ARROW_R;
    private static Profile ARROW_L;
    private static Profile CROSSBOW_R;
    private static Profile CROSSBOW_L;
    private static Profile SPYGLASS_R;
    private static Profile SPYGLASS_L;
    private static Profile FLINT_R;
    private static Profile FLINT_L;
    private static Profile FOOD_R;
    private static Profile FOOD_L;
    private static Profile SHUBBA_R;
    private static Profile SHUBBA_L;
    private static Profile MACE_R;
    private static Profile MACE_L;
    private static Profile SPEAR_R;
    private static Profile SPEAR_L;
    private static Profile TRIDENT_R;
    private static Profile TRIDENT_L;
    private static Profile FISHING_ROD_R;
    private static Profile FISHING_ROD_L;
    private static Profile TORCH_R;
    private static Profile TORCH_L;
    private static Profile LANTERN_R;
    private static Profile LANTERN_L;
    private static Profile REDSTONE_R;
    private static Profile REDSTONE_L;
    private static Profile CREATURE_BUCKET_R;
    private static Profile CREATURE_BUCKET_L;
    private static Profile BUCKET_R;
    private static Profile BUCKET_L;
    private static Profile MINECART_R;
    private static Profile MINECART_L;
    private static Profile BOAT_R;
    private static Profile BOAT_L;
    private static Profile BUTTON_PLATE_R;
    private static Profile BUTTON_PLATE_L;
    private static Profile TRAPDOOR_R;
    private static Profile TRAPDOOR_L;
    private static Profile CARPET_R;
    private static Profile CARPET_L;
    private static Profile BED_R;
    private static Profile BED_L;
    private static Profile INGREDIENT_R;
    private static Profile INGREDIENT_L;
    private static final Map<VanillaFirstPersonItemLayer.ToolKind, TransformOverride> OVERRIDE_RIGHT;
    private static final Map<VanillaFirstPersonItemLayer.ToolKind, TransformOverride> OVERRIDE_LEFT;
    private static final Map<class_2960, SpecificEntry> ITEM_SPECIFICS;
    private static final Map<class_2960, Map<Integer, SpecificEntry>> ITEM_SPECIFICS_BY_MODEL;
    private static final Map<class_2960, Map<String, SpecificEntry>> ITEM_SPECIFICS_BY_RENAMED;
    private static final Map<class_2960, Map<Integer, Map<String, SpecificEntry>>> ITEM_SPECIFICS_BY_MODEL_AND_RENAMED;
    private static final EnumMap<VanillaFirstPersonItemLayer.ToolKind, Boolean> ATTACHED_RIGHT;
    private static final EnumMap<VanillaFirstPersonItemLayer.ToolKind, Boolean> ATTACHED_LEFT;
    private static final EnumMap<VanillaFirstPersonItemLayer.ToolKind, List<PunchyTuningConfig.CustomAnimationEntry>> GROUP_ANIMATIONS;
    private static final EnumMap<VanillaFirstPersonItemLayer.ToolKind, List<PunchyTuningConfig.CustomAnimationEntry>> GROUP_ITEM_ANIMATIONS;
    private static final EnumMap<VanillaFirstPersonItemLayer.ToolKind, List<String>> GROUP_PARTICLE_EMITTERS;
    private static final EnumMap<VanillaFirstPersonItemLayer.ToolKind, List<PunchyTuningConfig.BetterCombatEntry>> GROUP_BETTER_COMBAT;
    private static List<PunchyTuningConfig.CustomAnimationEntry> DEFAULT_ANIMATIONS;
    private static List<PunchyTuningConfig.BetterCombatEntry> DEFAULT_BETTER_COMBAT;

    private ToolTuning() {
    }

    public static RotationOrder rotationOrderForItemId(class_2960 class_29602) {
        if (class_29602 == null) {
            return RotationOrder.XYZ;
        }
        String string = class_29602.method_12832();
        return !"chest".equals(string) && !"trapped_chest".equals(string) && !"ender_chest".equals(string) && !"piston".equals(string) && !"sticky_piston".equals(string) ? RotationOrder.XYZ : RotationOrder.ZXY;
    }

    public static void rotateProfileLocal(Profile profile, RotationOrder rotationOrder, char c, float f) {
        if (profile != null && rotationOrder != null && f != 0.0f) {
            Quaternionf quaternionf = ToolTuning.quaternionFromProfile(profile, rotationOrder);
            float f2 = (float)Math.toRadians(f);
            Quaternionf quaternionf2 = new Quaternionf();
            switch (Character.toUpperCase(c)) {
                case 'X': {
                    quaternionf2.rotationX(f2);
                    break;
                }
                case 'Y': {
                    quaternionf2.rotationY(f2);
                    break;
                }
                case 'Z': {
                    quaternionf2.rotationZ(f2);
                    break;
                }
                default: {
                    return;
                }
            }
            quaternionf.mul((Quaternionfc)quaternionf2);
            Vector3f vector3f = ToolTuning.eulerFromQuaternion(quaternionf, rotationOrder);
            profile.rotX = ToolTuning.normalizeDegrees((float)Math.toDegrees(vector3f.x));
            profile.rotY = ToolTuning.normalizeDegrees((float)Math.toDegrees(vector3f.y));
            profile.rotZ = ToolTuning.normalizeDegrees((float)Math.toDegrees(vector3f.z));
            profile.recomputeRadians();
        }
    }

    private static Quaternionf quaternionFromProfile(Profile profile, RotationOrder rotationOrder) {
        Quaternionf quaternionf = new Quaternionf().identity();
        float f = (float)Math.toRadians(profile.rotX);
        float f2 = (float)Math.toRadians(profile.rotY);
        float f3 = (float)Math.toRadians(profile.rotZ);
        if (rotationOrder == RotationOrder.ZXY) {
            quaternionf.rotateZ(f3).rotateX(f).rotateY(f2);
        } else {
            quaternionf.rotateX(f).rotateY(f2).rotateZ(f3);
        }
        return quaternionf;
    }

    private static Vector3f eulerFromQuaternion(Quaternionf quaternionf, RotationOrder rotationOrder) {
        Matrix3f matrix3f = new Matrix3f().set((Quaternionfc)quaternionf);
        return rotationOrder == RotationOrder.ZXY ? ToolTuning.extractEulerZxy(matrix3f) : ToolTuning.extractEulerXyz(matrix3f);
    }

    private static Vector3f extractEulerXyz(Matrix3f matrix3f) {
        float f;
        float f2;
        float f3 = (float)Math.asin(ToolTuning.clampUnit(matrix3f.m02()));
        float f4 = (float)Math.cos(f3);
        if (Math.abs(f4) > 1.0E-5f) {
            f2 = (float)Math.atan2(-matrix3f.m12(), matrix3f.m22());
            f = (float)Math.atan2(-matrix3f.m01(), matrix3f.m00());
        } else {
            f2 = (float)Math.atan2(matrix3f.m21(), matrix3f.m11());
            f = 0.0f;
        }
        return new Vector3f(f2, f3, f);
    }

    private static Vector3f extractEulerZxy(Matrix3f matrix3f) {
        float f;
        float f2;
        float f3 = (float)Math.asin(ToolTuning.clampUnit(matrix3f.m21()));
        float f4 = (float)Math.cos(f3);
        if (Math.abs(f4) > 1.0E-5f) {
            f2 = (float)Math.atan2(-matrix3f.m20(), matrix3f.m22());
            f = (float)Math.atan2(-matrix3f.m01(), matrix3f.m11());
        } else {
            f2 = 0.0f;
            f = (float)Math.atan2(matrix3f.m10(), matrix3f.m00());
        }
        return new Vector3f(f3, f2, f);
    }

    private static float clampUnit(float f) {
        return Math.max(-1.0f, Math.min(1.0f, f));
    }

    private static float normalizeDegrees(float f) {
        float f2 = f % 360.0f;
        if (f2 > 180.0f) {
            f2 -= 360.0f;
        } else if (f2 < -180.0f) {
            f2 += 360.0f;
        }
        return f2;
    }

    public static void resetDefaults() {
        ToolTuning.resetAttachments();
        GROUP_ANIMATIONS.clear();
        GROUP_ITEM_ANIMATIONS.clear();
        GROUP_PARTICLE_EMITTERS.clear();
        GROUP_BETTER_COMBAT.clear();
        DEFAULT_ANIMATIONS = null;
        DEFAULT_BETTER_COMBAT = null;
        GLOBAL_RIGHT = new Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        GLOBAL_LEFT = GLOBAL_RIGHT.mirror();
        EMPTY_R = ToolTuning.blankProfile();
        EMPTY_L = ToolTuning.blankProfile();
        AXE_R = ToolTuning.blankProfile();
        AXE_L = ToolTuning.blankProfile();
        PICKAXE_R = ToolTuning.blankProfile();
        PICKAXE_L = ToolTuning.blankProfile();
        HOE_R = ToolTuning.blankProfile();
        HOE_L = ToolTuning.blankProfile();
        SWORD_R = ToolTuning.blankProfile();
        SWORD_L = ToolTuning.blankProfile();
        SHOVEL_R = ToolTuning.blankProfile();
        SHOVEL_L = ToolTuning.blankProfile();
        SHIELD_R = ToolTuning.blankProfile();
        SHIELD_L = ToolTuning.blankProfile();
        BLOCK_R = ToolTuning.blankProfile();
        BLOCK_L = ToolTuning.blankProfile();
        FULL_BLOCK_R = ToolTuning.blankProfile();
        FULL_BLOCK_L = ToolTuning.blankProfile();
        BUILDING_BLOCKS_R = ToolTuning.blankProfile();
        BUILDING_BLOCKS_L = ToolTuning.blankProfile();
        COLORED_BLOCKS_R = ToolTuning.blankProfile();
        COLORED_BLOCKS_L = ToolTuning.blankProfile();
        NATURAL_BLOCKS_R = ToolTuning.blankProfile();
        NATURAL_BLOCKS_L = ToolTuning.blankProfile();
        FUNCTIONAL_BLOCKS_R = ToolTuning.blankProfile();
        FUNCTIONAL_BLOCKS_L = ToolTuning.blankProfile();
        CHEST_R = ToolTuning.blankProfile();
        CHEST_L = ToolTuning.blankProfile();
        FLOWER_PENDULUM_R = new Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, PhysicsMode.FORWARD_PENDULUM);
        FLOWER_PENDULUM_L = FLOWER_PENDULUM_R.mirror();
        TORCH_R = ToolTuning.blankProfile();
        TORCH_L = ToolTuning.blankProfile();
        LANTERN_R = ToolTuning.blankProfile();
        LANTERN_L = LANTERN_R.mirror();
        REDSTONE_R = ToolTuning.blankProfile();
        REDSTONE_L = ToolTuning.blankProfile();
        CREATURE_BUCKET_R = ToolTuning.blankProfile();
        CREATURE_BUCKET_L = ToolTuning.blankProfile();
        BUCKET_R = ToolTuning.blankProfile();
        BUCKET_L = ToolTuning.blankProfile();
        MINECART_R = ToolTuning.blankProfile();
        MINECART_L = ToolTuning.blankProfile();
        BOAT_R = ToolTuning.blankProfile();
        BOAT_L = ToolTuning.blankProfile();
        BUTTON_PLATE_R = ToolTuning.blankProfile();
        BUTTON_PLATE_L = ToolTuning.blankProfile();
        TRAPDOOR_R = ToolTuning.blankProfile();
        TRAPDOOR_L = ToolTuning.blankProfile();
        CARPET_R = ToolTuning.blankProfile();
        CARPET_L = ToolTuning.blankProfile();
        BED_R = ToolTuning.blankProfile();
        BED_L = ToolTuning.blankProfile();
        INGREDIENT_R = ToolTuning.blankProfile();
        INGREDIENT_L = ToolTuning.blankProfile();
        BOW_R = ToolTuning.blankProfile();
        BOW_L = ToolTuning.blankProfile();
        ARROW_R = ToolTuning.blankProfile();
        ARROW_L = ToolTuning.blankProfile();
        CROSSBOW_R = ToolTuning.blankProfile();
        CROSSBOW_L = ToolTuning.blankProfile();
        SPYGLASS_R = ToolTuning.blankProfile();
        SPYGLASS_L = ToolTuning.blankProfile();
        FLINT_R = ToolTuning.blankProfile();
        FLINT_L = ToolTuning.blankProfile();
        FOOD_R = ToolTuning.blankProfile();
        FOOD_L = ToolTuning.blankProfile();
        SHUBBA_R = ToolTuning.blankProfile();
        SHUBBA_L = ToolTuning.blankProfile();
        MACE_R = ToolTuning.blankProfile();
        MACE_L = ToolTuning.blankProfile();
        SPEAR_R = ToolTuning.blankProfile();
        SPEAR_L = ToolTuning.blankProfile();
        TRIDENT_R = ToolTuning.blankProfile();
        TRIDENT_L = ToolTuning.blankProfile();
        FISHING_ROD_R = ToolTuning.blankProfile();
        FISHING_ROD_L = ToolTuning.blankProfile();
        OVERRIDE_RIGHT.clear();
        OVERRIDE_LEFT.clear();
        for (VanillaFirstPersonItemLayer.ToolKind toolKind : VanillaFirstPersonItemLayer.ToolKind.values()) {
            OVERRIDE_RIGHT.put(toolKind, new TransformOverride());
            OVERRIDE_LEFT.put(toolKind, new TransformOverride());
        }
        ITEM_SPECIFICS.clear();
        ToolTuning.refreshAll();
    }

    public static void applyGroupAnimation(VanillaFirstPersonItemLayer.ToolKind toolKind, List<PunchyTuningConfig.CustomAnimationEntry> list) {
        if (toolKind != null && list != null) {
            GROUP_ANIMATIONS.put(toolKind, list);
        }
    }

    public static void setDefaultAnimations(List<PunchyTuningConfig.CustomAnimationEntry> list) {
        DEFAULT_ANIMATIONS = list != null && !list.isEmpty() ? list : null;
    }

    public static void setDefaultBetterCombat(List<PunchyTuningConfig.BetterCombatEntry> list) {
        DEFAULT_BETTER_COMBAT = list != null && !list.isEmpty() ? list : null;
    }

    public static void setGroupAnimation(VanillaFirstPersonItemLayer.ToolKind toolKind, List<PunchyTuningConfig.CustomAnimationEntry> list) {
        if (toolKind != null) {
            if (list != null && !list.isEmpty()) {
                GROUP_ANIMATIONS.put(toolKind, list);
            } else {
                GROUP_ANIMATIONS.remove((Object)toolKind);
            }
        }
    }

    public static void applyGroupItemAnimation(VanillaFirstPersonItemLayer.ToolKind toolKind, List<PunchyTuningConfig.CustomAnimationEntry> list) {
        if (toolKind != null && list != null) {
            GROUP_ITEM_ANIMATIONS.put(toolKind, list);
        }
    }

    public static void setGroupItemAnimation(VanillaFirstPersonItemLayer.ToolKind toolKind, List<PunchyTuningConfig.CustomAnimationEntry> list) {
        if (toolKind != null) {
            if (list != null && !list.isEmpty()) {
                GROUP_ITEM_ANIMATIONS.put(toolKind, list);
            } else {
                GROUP_ITEM_ANIMATIONS.remove((Object)toolKind);
            }
        }
    }

    public static void applyGroupParticleEmitters(VanillaFirstPersonItemLayer.ToolKind toolKind, List<String> list) {
        if (toolKind != null && list != null) {
            GROUP_PARTICLE_EMITTERS.put(toolKind, new ArrayList<String>(list));
        }
    }

    public static void applyGroupBetterCombat(VanillaFirstPersonItemLayer.ToolKind toolKind, List<PunchyTuningConfig.BetterCombatEntry> list) {
        if (toolKind != null && list != null) {
            GROUP_BETTER_COMBAT.put(toolKind, list);
        }
    }

    public static void resetAttachments() {
        for (VanillaFirstPersonItemLayer.ToolKind toolKind : VanillaFirstPersonItemLayer.ToolKind.values()) {
            ATTACHED_RIGHT.put(toolKind, true);
            ATTACHED_LEFT.put(toolKind, true);
        }
    }

    private static void setAttachmentState(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl, boolean bl2) {
        if (toolKind != null) {
            EnumMap<VanillaFirstPersonItemLayer.ToolKind, Boolean> enumMap = bl ? ATTACHED_LEFT : ATTACHED_RIGHT;
            enumMap.put(toolKind, bl2);
        }
    }

    public static boolean isKindAttached(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        return toolKind == null ? true : (bl ? ATTACHED_LEFT : ATTACHED_RIGHT).getOrDefault((Object)toolKind, true);
    }

    public static void markKindDetached(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        ToolTuning.setAttachmentState(toolKind, bl, false);
    }

    public static void markKindAttached(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        ToolTuning.setAttachmentState(toolKind, bl, true);
        ToolTuning.refreshKind(toolKind, bl);
    }

    private static Profile blankProfile() {
        return new Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public static Profile get(VanillaFirstPersonItemLayer.ToolKind toolKind) {
        return ToolTuning.get(toolKind, false);
    }

    public static Profile get(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        return switch (toolKind) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND -> {
                if (bl) {
                    yield EMPTY_L;
                }
                yield EMPTY_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.AXE -> {
                if (bl) {
                    yield AXE_L;
                }
                yield AXE_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.PICKAXE -> {
                if (bl) {
                    yield PICKAXE_L;
                }
                yield PICKAXE_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.HOE -> {
                if (bl) {
                    yield HOE_L;
                }
                yield HOE_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.SWORD -> {
                if (bl) {
                    yield SWORD_L;
                }
                yield SWORD_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.SHOVEL -> {
                if (bl) {
                    yield SHOVEL_L;
                }
                yield SHOVEL_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.SHIELD -> {
                if (bl) {
                    yield SHIELD_L;
                }
                yield SHIELD_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.BLOCK -> {
                if (bl) {
                    yield BLOCK_L;
                }
                yield BLOCK_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.FULL_BLOCK -> {
                if (bl) {
                    yield FULL_BLOCK_L;
                }
                yield FULL_BLOCK_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.BUILDING_BLOCKS -> {
                if (bl) {
                    yield BUILDING_BLOCKS_L;
                }
                yield BUILDING_BLOCKS_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.COLORED_BLOCKS -> {
                if (bl) {
                    yield COLORED_BLOCKS_L;
                }
                yield COLORED_BLOCKS_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.NATURAL_BLOCKS -> {
                if (bl) {
                    yield NATURAL_BLOCKS_L;
                }
                yield NATURAL_BLOCKS_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.FUNCTIONAL_BLOCKS -> {
                if (bl) {
                    yield FUNCTIONAL_BLOCKS_L;
                }
                yield FUNCTIONAL_BLOCKS_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.CHEST -> {
                if (bl) {
                    yield CHEST_L;
                }
                yield CHEST_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.FLOWER_PENDULUM -> {
                if (bl) {
                    yield FLOWER_PENDULUM_L;
                }
                yield FLOWER_PENDULUM_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.CREATURE_BUCKET -> {
                if (bl) {
                    yield CREATURE_BUCKET_L;
                }
                yield CREATURE_BUCKET_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.BUCKET -> {
                if (bl) {
                    yield BUCKET_L;
                }
                yield BUCKET_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.MINECART -> {
                if (bl) {
                    yield MINECART_L;
                }
                yield MINECART_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.BOAT -> {
                if (bl) {
                    yield BOAT_L;
                }
                yield BOAT_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.BUTTON_PLATE -> {
                if (bl) {
                    yield BUTTON_PLATE_L;
                }
                yield BUTTON_PLATE_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.TRAPDOOR -> {
                if (bl) {
                    yield TRAPDOOR_L;
                }
                yield TRAPDOOR_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.CARPET -> {
                if (bl) {
                    yield CARPET_L;
                }
                yield CARPET_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.BED -> {
                if (bl) {
                    yield BED_L;
                }
                yield BED_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.TORCH -> {
                if (bl) {
                    yield TORCH_L;
                }
                yield TORCH_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.LANTERN -> {
                if (bl) {
                    yield LANTERN_L;
                }
                yield LANTERN_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.REDSTONE -> {
                if (bl) {
                    yield REDSTONE_L;
                }
                yield REDSTONE_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.INGREDIENT -> {
                if (bl) {
                    yield INGREDIENT_L;
                }
                yield INGREDIENT_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.BOW -> {
                if (bl) {
                    yield BOW_L;
                }
                yield BOW_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.ARROW -> {
                if (bl) {
                    yield ARROW_L;
                }
                yield ARROW_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.CROSSBOW -> {
                if (bl) {
                    yield CROSSBOW_L;
                }
                yield CROSSBOW_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.SPYGLASS -> {
                if (bl) {
                    yield SPYGLASS_L;
                }
                yield SPYGLASS_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.FLINT -> {
                if (bl) {
                    yield FLINT_L;
                }
                yield FLINT_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.FOOD -> {
                if (bl) {
                    yield FOOD_L;
                }
                yield FOOD_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.SHUBBA_DUCK -> {
                if (bl) {
                    yield SHUBBA_L;
                }
                yield SHUBBA_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.MACE -> {
                if (bl) {
                    yield MACE_L;
                }
                yield MACE_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.SPEAR -> {
                if (bl) {
                    yield SPEAR_L;
                }
                yield SPEAR_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.TRIDENT -> {
                if (bl) {
                    yield TRIDENT_L;
                }
                yield TRIDENT_R;
            }
            case VanillaFirstPersonItemLayer.ToolKind.FISHING_ROD -> bl ? FISHING_ROD_L : FISHING_ROD_R;
        };
    }

    public static Profile getGlobal(boolean bl) {
        return bl ? GLOBAL_LEFT : GLOBAL_RIGHT;
    }

    public static void applyGlobal(boolean bl, Profile profile) {
        if (profile != null) {
            Profile profile2 = ToolTuning.getGlobal(bl);
            ToolTuning.copyAll(profile, profile2);
            profile2.recomputeRadians();
            ToolTuning.refresh(bl);
        }
    }

    public static Profile snapshotGlobal(boolean bl) {
        Profile profile = ToolTuning.getGlobal(bl);
        return ToolTuning.copyProfile(profile);
    }

    public static void commitGlobal(boolean bl) {
        ToolTuning.getGlobal(bl).recomputeRadians();
        ToolTuning.refresh(bl);
    }

    public static void apply(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl, Profile profile) {
        if (profile != null) {
            Profile profile2 = ToolTuning.get(toolKind, bl);
            ToolTuning.copyAll(profile, profile2);
            profile2.recomputeRadians();
            ToolTuning.commitKind(toolKind, bl);
        }
    }

    public static void commitKind(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        Profile profile = ToolTuning.getGlobal(bl);
        Profile profile2 = ToolTuning.get(toolKind, bl);
        TransformOverride transformOverride = ToolTuning.getOverride(toolKind, bl);
        transformOverride.set(profile, profile2);
        ToolTuning.refreshKind(toolKind, bl);
    }

    public static Profile snapshot(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        Profile profile = ToolTuning.get(toolKind, bl);
        return ToolTuning.copyProfile(profile);
    }

    private static void refresh(boolean bl) {
        for (VanillaFirstPersonItemLayer.ToolKind toolKind : VanillaFirstPersonItemLayer.ToolKind.values()) {
            ToolTuning.refreshKind(toolKind, bl);
        }
    }

    public static void refreshAll() {
        ToolTuning.refresh(false);
        ToolTuning.refresh(true);
    }

    private static void refreshKind(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        Profile profile = ToolTuning.get(toolKind, bl);
        if (profile != null) {
            if (!ToolTuning.isKindAttached(toolKind, bl)) {
                profile.recomputeRadians();
            } else {
                Profile profile2 = ToolTuning.getGlobal(bl);
                TransformOverride transformOverride = ToolTuning.getOverride(toolKind, bl);
                if (profile2 != null && transformOverride != null) {
                    ToolTuning.copyAnchor(profile2, profile);
                    profile.scale += transformOverride.scale;
                    profile.offX += transformOverride.offX;
                    profile.offY += transformOverride.offY;
                    profile.offZ += transformOverride.offZ;
                    profile.pivotX += transformOverride.pivotX;
                    profile.pivotY += transformOverride.pivotY;
                    profile.pivotZ += transformOverride.pivotZ;
                    profile.rotX = profile2.rotX + transformOverride.rotX;
                    profile.rotY = profile2.rotY + transformOverride.rotY;
                    profile.rotZ = profile2.rotZ + transformOverride.rotZ;
                    profile.recomputeRadians();
                }
            }
        }
    }

    private static TransformOverride getOverride(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        Map<VanillaFirstPersonItemLayer.ToolKind, TransformOverride> map = bl ? OVERRIDE_LEFT : OVERRIDE_RIGHT;
        TransformOverride transformOverride = map.get((Object)toolKind);
        if (transformOverride == null) {
            transformOverride = new TransformOverride();
            map.put(toolKind, transformOverride);
        }
        return transformOverride;
    }

    private static void copyAnchor(Profile profile, Profile profile2) {
        profile2.scale = profile.scale;
        profile2.offX = profile.offX;
        profile2.offY = profile.offY;
        profile2.offZ = profile.offZ;
        profile2.pivotX = profile.pivotX;
        profile2.pivotY = profile.pivotY;
        profile2.pivotZ = profile.pivotZ;
    }

    private static void copyAll(Profile profile, Profile profile2) {
        ToolTuning.copyAnchor(profile, profile2);
        profile2.rotX = profile.rotX;
        profile2.rotY = profile.rotY;
        profile2.rotZ = profile.rotZ;
        profile2.disableSprintLateralTilt = profile.disableSprintLateralTilt;
        profile2.keepArmTuning = profile.keepArmTuning;
        profile2.keepTriggerArmTuning = profile.keepTriggerArmTuning;
        profile2.physicsMode = profile.physicsMode;
        profile2.physicsSettings = profile.physicsSettings != null ? profile.physicsSettings.copy() : null;
    }

    private static Profile copyProfile(Profile profile) {
        Profile profile2 = new Profile(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ, profile.pivotX, profile.pivotY, profile.pivotZ, profile.physicsMode);
        profile2.disableSprintLateralTilt = profile.disableSprintLateralTilt;
        profile2.keepArmTuning = profile.keepArmTuning;
        profile2.keepTriggerArmTuning = profile.keepTriggerArmTuning;
        if (profile.physicsSettings != null) {
            profile2.physicsSettings = profile.physicsSettings.copy();
        }
        return profile2;
    }

    private static Profile createSpecificSeedProfile(VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        Profile profile = ToolTuning.copyProfile(ToolTuning.snapshot(toolKind, bl));
        if (toolKind == VanillaFirstPersonItemLayer.ToolKind.BUCKET || toolKind == VanillaFirstPersonItemLayer.ToolKind.CREATURE_BUCKET) {
            profile.physicsMode = PhysicsMode.NONE;
            profile.physicsSettings = null;
        }
        return profile;
    }

    private static class_2960 idFor(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_7923.field_41178.method_10221(class_17992.method_7909()) : null;
    }

    public static VanillaFirstPersonItemLayer.ToolKind resolveOverriddenKind(class_1799 class_17992) {
        SpecificEntry specificEntry = ToolTuning.getSpecificEntry(class_17992);
        return specificEntry != null && specificEntry.kind != null ? specificEntry.kind : null;
    }

    public static Profile resolveProfile(class_1799 class_17992, VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        Profile profile;
        SpecificEntry specificEntry = ToolTuning.getSpecificEntry(class_17992);
        if (specificEntry != null && (profile = specificEntry.get(bl)) != null) {
            return profile;
        }
        return ToolTuning.get(toolKind, bl);
    }

    public static boolean hasSpecificProfile(class_1799 class_17992) {
        return ToolTuning.getSpecificEntry(class_17992) != null;
    }

    public static JsonElement getSpecificBonePhysics(class_1799 class_17992) {
        SpecificEntry specificEntry = ToolTuning.getSpecificEntry(class_17992);
        return specificEntry != null && specificEntry.bonePhysics != null && !specificEntry.bonePhysics.isJsonNull() ? specificEntry.bonePhysics.deepCopy() : null;
    }

    public static Profile getSpecificProfile(class_2960 class_29603, VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        Profile profile;
        if (class_29603 == null) {
            return null;
        }
        SpecificEntry specificEntry = ITEM_SPECIFICS.computeIfAbsent(class_29603, class_29602 -> new SpecificEntry(toolKind));
        if (specificEntry.kind == null) {
            specificEntry.kind = toolKind;
        }
        if ((profile = specificEntry.get(bl)) == null) {
            profile = ToolTuning.createSpecificSeedProfile(toolKind, bl);
            specificEntry.set(bl, profile);
        }
        return profile;
    }

    public static Profile getSpecificProfile(class_1799 class_17992, VanillaFirstPersonItemLayer.ToolKind toolKind, boolean bl) {
        class_2960 class_29602 = ToolTuning.idFor(class_17992);
        if (class_29602 == null) {
            return null;
        }
        Integer n = ToolTuning.resolveCustomModelData(class_17992);
        String string = ToolTuning.resolveStackRenamedKey(class_17992);
        if (n != null) {
            ToolTuning.applySpecific(class_29602, n, toolKind, string, null, null, null, null, null, null, null);
            Map<Integer, Map<String, SpecificEntry>> renamedByModel = ITEM_SPECIFICS_BY_MODEL_AND_RENAMED.get(class_29602);
            Map<String, SpecificEntry> renamedEntries = renamedByModel == null ? null : renamedByModel.get(n);
            SpecificEntry specificEntry = string == null || renamedEntries == null ? null : renamedEntries.get(string);
            if (specificEntry != null) {
                Profile profile = specificEntry.get(bl);
                if (profile == null) {
                    profile = ToolTuning.createSpecificSeedProfile(toolKind, bl);
                    specificEntry.set(bl, profile);
                }
                return profile;
            }
            Map<Integer, SpecificEntry> modelEntries = ITEM_SPECIFICS_BY_MODEL.get(class_29602);
            specificEntry = modelEntries == null ? null : modelEntries.get(n);
            if (specificEntry != null) {
                Profile profile = specificEntry.get(bl);
                if (profile == null) {
                    profile = ToolTuning.createSpecificSeedProfile(toolKind, bl);
                    specificEntry.set(bl, profile);
                }
                return profile;
            }
        }
        if (string != null) {
            ToolTuning.applySpecific(class_29602, toolKind, string, null, null, null, null, null, null, null);
            Map<String, SpecificEntry> renamedEntries = ITEM_SPECIFICS_BY_RENAMED.get(class_29602);
            SpecificEntry specificEntry = renamedEntries == null ? null : renamedEntries.get(string);
            if (specificEntry != null) {
                Profile profile = specificEntry.get(bl);
                if (profile == null) {
                    profile = ToolTuning.createSpecificSeedProfile(toolKind, bl);
                    specificEntry.set(bl, profile);
                }
                return profile;
            }
        }
        return ToolTuning.getSpecificProfile(class_29602, toolKind, bl);
    }

    public static Map<class_2960, SpecificSnapshot> snapshotSpecifics() {
        HashMap<class_2960, SpecificSnapshot> hashMap = new HashMap<class_2960, SpecificSnapshot>();
        ITEM_SPECIFICS.forEach((class_29602, specificEntry) -> hashMap.put((class_2960)class_29602, new SpecificSnapshot(specificEntry.kind, specificEntry.right != null ? ToolTuning.copyProfile(specificEntry.right) : null, specificEntry.left != null ? ToolTuning.copyProfile(specificEntry.left) : null)));
        return hashMap;
    }

    public static List<SpecificSnapshotDetailed> snapshotSpecificsDetailed() {
        ArrayList<SpecificSnapshotDetailed> arrayList = new ArrayList<SpecificSnapshotDetailed>();
        ITEM_SPECIFICS.forEach((class_29602, specificEntry) -> arrayList.add(ToolTuning.toDetailedSnapshot(class_29602, null, null, specificEntry)));
        ITEM_SPECIFICS_BY_MODEL.forEach((class_29602, map) -> {
            if (map != null) {
                map.forEach((n, specificEntry) -> arrayList.add(ToolTuning.toDetailedSnapshot(class_29602, n, null, specificEntry)));
            }
        });
        ITEM_SPECIFICS_BY_RENAMED.forEach((class_29602, map) -> {
            if (map != null) {
                map.forEach((string, specificEntry) -> arrayList.add(ToolTuning.toDetailedSnapshot(class_29602, null, string, specificEntry)));
            }
        });
        ITEM_SPECIFICS_BY_MODEL_AND_RENAMED.forEach((class_29602, map2) -> {
            if (map2 != null) {
                map2.forEach((n, map) -> {
                    if (map != null) {
                        map.forEach((string, specificEntry) -> arrayList.add(ToolTuning.toDetailedSnapshot(class_29602, n, string, specificEntry)));
                    }
                });
            }
        });
        return arrayList;
    }

    private static SpecificSnapshotDetailed toDetailedSnapshot(class_2960 class_29602, Integer n, String string, SpecificEntry specificEntry) {
        return specificEntry == null ? null : new SpecificSnapshotDetailed(class_29602, n, string != null ? string : specificEntry.renamed, specificEntry.model, specificEntry.kind, specificEntry.right != null ? ToolTuning.copyProfile(specificEntry.right) : null, specificEntry.left != null ? ToolTuning.copyProfile(specificEntry.left) : null);
    }

    public static List<PunchyTuningConfig.CustomAnimationEntry> getCustomAnimation(class_1799 class_17992) {
        return ToolTuning.getCustomAnimationInternal(class_17992, true);
    }

    public static List<PunchyTuningConfig.CustomAnimationEntry> getCustomAnimationWithoutDefaults(class_1799 class_17992) {
        return ToolTuning.getCustomAnimationInternal(class_17992, false);
    }

    private static List<PunchyTuningConfig.CustomAnimationEntry> getCustomAnimationInternal(class_1799 class_17992, boolean bl) {
        List<PunchyTuningConfig.CustomAnimationEntry> list;
        VanillaFirstPersonItemLayer.ToolKind toolKind;
        List<PunchyTuningConfig.CustomAnimationEntry> list2 = null;
        SpecificEntry specificEntry = ToolTuning.getSpecificEntry(class_17992);
        if (specificEntry != null && specificEntry.customAnimation != null) {
            list2 = specificEntry.customAnimation;
        }
        if (list2 == null && (toolKind = ToolKindResolver.resolveItemKind(class_17992, null)) != null && (list = GROUP_ANIMATIONS.get((Object)toolKind)) != null) {
            list2 = list;
        }
        return bl ? ToolTuning.mergeAnimationEntries(list2, DEFAULT_ANIMATIONS) : list2;
    }

    public static boolean hasCustomAnimationType(class_1799 class_17992, String string) {
        if (string != null && !string.isBlank()) {
            List<PunchyTuningConfig.CustomAnimationEntry> list = ToolTuning.getCustomAnimation(class_17992);
            if (list != null && !list.isEmpty()) {
                String string2 = string.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "").replace(" ", "");
                for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list) {
                    String string3;
                    if (customAnimationEntry == null || customAnimationEntry.type == null || !string2.equals(string3 = customAnimationEntry.type.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "").replace(" ", ""))) continue;
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public static List<PunchyTuningConfig.CustomAnimationEntry> getCustomItemAnimation(class_1799 class_17992) {
        VanillaFirstPersonItemLayer.ToolKind toolKind;
        SpecificEntry specificEntry = ToolTuning.getSpecificEntry(class_17992);
        List<PunchyTuningConfig.CustomAnimationEntry> list = null;
        if (specificEntry != null) {
            if (specificEntry.customItemAnimations != null) {
                list = specificEntry.customItemAnimations;
            } else if (specificEntry.customAnimation != null) {
                list = specificEntry.customAnimation;
            }
        }
        if (list == null && (toolKind = ToolKindResolver.resolveItemKind(class_17992, null)) != null) {
            List<PunchyTuningConfig.CustomAnimationEntry> list2 = GROUP_ITEM_ANIMATIONS.get((Object)toolKind);
            list = list2 != null ? list2 : GROUP_ANIMATIONS.get((Object)toolKind);
        }
        return ToolTuning.mergeAnimationEntries(list, DEFAULT_ANIMATIONS);
    }

    private static List<PunchyTuningConfig.CustomAnimationEntry> mergeAnimationEntries(List<PunchyTuningConfig.CustomAnimationEntry> list, List<PunchyTuningConfig.CustomAnimationEntry> list2) {
        if (list != null && !list.isEmpty() || list2 != null && !list2.isEmpty()) {
            if (list != null && !list.isEmpty()) {
                if (list2 != null && !list2.isEmpty()) {
                    String string;
                    LinkedHashMap<String, PunchyTuningConfig.CustomAnimationEntry> linkedHashMap = new LinkedHashMap<String, PunchyTuningConfig.CustomAnimationEntry>();
                    for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list) {
                        string = ToolTuning.animationMergeKey(customAnimationEntry);
                        if (string == null) continue;
                        linkedHashMap.put(string, customAnimationEntry);
                    }
                    for (PunchyTuningConfig.CustomAnimationEntry customAnimationEntry : list2) {
                        string = ToolTuning.animationMergeKey(customAnimationEntry);
                        if (string == null || linkedHashMap.containsKey(string)) continue;
                        linkedHashMap.put(string, customAnimationEntry);
                    }
                    return new ArrayList<PunchyTuningConfig.CustomAnimationEntry>(linkedHashMap.values());
                }
                return list;
            }
            return list2;
        }
        return null;
    }

    private static String animationMergeKey(PunchyTuningConfig.CustomAnimationEntry customAnimationEntry) {
        if (customAnimationEntry != null && customAnimationEntry.type != null) {
            String string = customAnimationEntry.type.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "").replace(" ", "");
            String string2 = customAnimationEntry.key == null ? "" : customAnimationEntry.key.trim().toLowerCase(Locale.ROOT);
            return string + "|" + string2;
        }
        return null;
    }

    public static List<PunchyTuningConfig.BetterCombatEntry> getBetterCombat(class_1799 class_17992) {
        List<PunchyTuningConfig.BetterCombatEntry> list;
        SpecificEntry specificEntry = ToolTuning.getSpecificEntry(class_17992);
        if (specificEntry != null && specificEntry.betterCombat != null) {
            return specificEntry.betterCombat;
        }
        VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, null);
        if (toolKind != null && (list = GROUP_BETTER_COMBAT.get((Object)toolKind)) != null && !list.isEmpty()) {
            return list;
        }
        return DEFAULT_BETTER_COMBAT;
    }

    public static List<String> getParticleEmitters(class_1799 class_17992) {
        SpecificEntry specificEntry = ToolTuning.getSpecificEntry(class_17992);
        if (specificEntry != null && specificEntry.particleEmitters != null) {
            return specificEntry.particleEmitters;
        }
        VanillaFirstPersonItemLayer.ToolKind toolKind = ToolKindResolver.resolveItemKind(class_17992, null);
        return toolKind != null ? GROUP_PARTICLE_EMITTERS.get((Object)toolKind) : null;
    }

    public static List<PunchyTuningConfig.CustomAnimationEntry> getSpecificCustomAnimation(class_1799 class_17992) {
        SpecificEntry specificEntry = ToolTuning.getSpecificEntry(class_17992);
        return specificEntry != null && specificEntry.customAnimation != null ? specificEntry.customAnimation : null;
    }

    public static List<PunchyTuningConfig.CustomAnimationEntry> getSpecificCustomItemAnimation(class_1799 class_17992) {
        SpecificEntry specificEntry = ToolTuning.getSpecificEntry(class_17992);
        if (specificEntry != null) {
            if (specificEntry.customItemAnimations != null) {
                return specificEntry.customItemAnimations;
            }
            if (specificEntry.customAnimation != null) {
                return specificEntry.customAnimation;
            }
        }
        return null;
    }

    public static void applySpecific(class_2960 class_29602, VanillaFirstPersonItemLayer.ToolKind toolKind, String string, List<PunchyTuningConfig.CustomAnimationEntry> list, List<PunchyTuningConfig.CustomAnimationEntry> list2, List<String> list3, List<PunchyTuningConfig.BetterCombatEntry> list4, Profile profile, Profile profile2) {
        ToolTuning.applySpecific(class_29602, toolKind, null, string, list, list2, list3, list4, null, profile, profile2);
    }

    public static void applySpecific(class_2960 class_29602, VanillaFirstPersonItemLayer.ToolKind toolKind, String string, String string2, List<PunchyTuningConfig.CustomAnimationEntry> list, List<PunchyTuningConfig.CustomAnimationEntry> list2, List<String> list3, List<PunchyTuningConfig.BetterCombatEntry> list4, Profile profile, Profile profile2) {
        ToolTuning.applySpecific(class_29602, toolKind, string, string2, list, list2, list3, list4, null, profile, profile2);
    }

    public static void applySpecific(class_2960 class_29603, VanillaFirstPersonItemLayer.ToolKind toolKind, String string, String string2, List<PunchyTuningConfig.CustomAnimationEntry> list, List<PunchyTuningConfig.CustomAnimationEntry> list2, List<String> list3, List<PunchyTuningConfig.BetterCombatEntry> list4, JsonElement jsonElement, Profile profile, Profile profile2) {
        if (class_29603 != null && toolKind != null) {
            SpecificEntry specificEntry;
            String string3 = ToolTuning.normalizeRenamed(string);
            if (string3 != null) {
                Map map = ITEM_SPECIFICS_BY_RENAMED.computeIfAbsent(class_29603, class_29602 -> new HashMap());
                specificEntry = (SpecificEntry)map.get(string3);
                if (specificEntry == null) {
                    specificEntry = new SpecificEntry(toolKind);
                    map.put(string3, specificEntry);
                } else if (specificEntry.kind == null) {
                    specificEntry.kind = toolKind;
                }
            } else {
                specificEntry = ITEM_SPECIFICS.get(class_29603);
                if (specificEntry == null) {
                    specificEntry = new SpecificEntry(toolKind);
                    ITEM_SPECIFICS.put(class_29603, specificEntry);
                } else if (specificEntry.kind == null) {
                    specificEntry.kind = toolKind;
                }
            }
            specificEntry.renamed = string3 != null ? string3 : specificEntry.renamed;
            specificEntry.model = string2 != null ? string2 : specificEntry.model;
            specificEntry.customAnimation = list != null ? list : specificEntry.customAnimation;
            specificEntry.customItemAnimations = list2 != null ? list2 : specificEntry.customItemAnimations;
            specificEntry.particleEmitters = list3 != null ? new ArrayList<String>(list3) : specificEntry.particleEmitters;
            specificEntry.betterCombat = list4 != null ? list4 : specificEntry.betterCombat;
            JsonElement jsonElement2 = specificEntry.bonePhysics = jsonElement != null && !jsonElement.isJsonNull() ? jsonElement.deepCopy() : specificEntry.bonePhysics;
            if (profile != null) {
                specificEntry.right = ToolTuning.copyProfile(profile);
            }
            if (profile2 != null) {
                specificEntry.left = ToolTuning.copyProfile(profile2);
            }
        }
    }

    public static void applySpecific(class_2960 class_29602, VanillaFirstPersonItemLayer.ToolKind toolKind, String string, Profile profile, Profile profile2) {
        ToolTuning.applySpecific(class_29602, toolKind, string, null, null, null, null, profile, profile2);
    }

    public static void applySpecific(class_2960 class_29602, Integer n, VanillaFirstPersonItemLayer.ToolKind toolKind, String string, String string2, List<PunchyTuningConfig.CustomAnimationEntry> list, List<PunchyTuningConfig.CustomAnimationEntry> list2, List<String> list3, List<PunchyTuningConfig.BetterCombatEntry> list4, Profile profile, Profile profile2) {
        ToolTuning.applySpecific(class_29602, n, toolKind, string, string2, list, list2, list3, list4, null, profile, profile2);
    }

    public static void applySpecific(class_2960 class_29603, Integer n2, VanillaFirstPersonItemLayer.ToolKind toolKind, String string, String string2, List<PunchyTuningConfig.CustomAnimationEntry> list, List<PunchyTuningConfig.CustomAnimationEntry> list2, List<String> list3, List<PunchyTuningConfig.BetterCombatEntry> list4, JsonElement jsonElement, Profile profile, Profile profile2) {
        if (class_29603 != null && toolKind != null) {
            if (n2 == null) {
                ToolTuning.applySpecific(class_29603, toolKind, string, string2, list, list2, list3, list4, jsonElement, profile, profile2);
            } else {
                SpecificEntry specificEntry;
                String string3 = ToolTuning.normalizeRenamed(string);
                if (string3 != null) {
                    Map<Integer, Map<String, SpecificEntry>> modelEntries = ITEM_SPECIFICS_BY_MODEL_AND_RENAMED.computeIfAbsent(class_29603, class_29602 -> new HashMap<>());
                    Map<String, SpecificEntry> renamedEntries = modelEntries.computeIfAbsent(n2, n -> new HashMap<>());
                    specificEntry = renamedEntries.get(string3);
                    if (specificEntry == null) {
                        specificEntry = new SpecificEntry(toolKind);
                        renamedEntries.put(string3, specificEntry);
                    } else if (specificEntry.kind == null) {
                        specificEntry.kind = toolKind;
                    }
                } else {
                    Map<Integer, SpecificEntry> modelEntries = ITEM_SPECIFICS_BY_MODEL.computeIfAbsent(class_29603, class_29602 -> new HashMap<>());
                    specificEntry = modelEntries.get(n2);
                    if (specificEntry == null) {
                        specificEntry = new SpecificEntry(toolKind);
                        modelEntries.put(n2, specificEntry);
                    } else if (specificEntry.kind == null) {
                        specificEntry.kind = toolKind;
                    }
                }
                specificEntry.renamed = string3 != null ? string3 : specificEntry.renamed;
                specificEntry.model = string2 != null ? string2 : specificEntry.model;
                specificEntry.customAnimation = list != null ? list : specificEntry.customAnimation;
                specificEntry.customItemAnimations = list2 != null ? list2 : specificEntry.customItemAnimations;
                specificEntry.particleEmitters = list3 != null ? new ArrayList<String>(list3) : specificEntry.particleEmitters;
                specificEntry.betterCombat = list4 != null ? list4 : specificEntry.betterCombat;
                JsonElement jsonElement2 = specificEntry.bonePhysics = jsonElement != null && !jsonElement.isJsonNull() ? jsonElement.deepCopy() : specificEntry.bonePhysics;
                if (profile != null) {
                    specificEntry.right = ToolTuning.copyProfile(profile);
                }
                if (profile2 != null) {
                    specificEntry.left = ToolTuning.copyProfile(profile2);
                }
            }
        }
    }

    public static void clearSpecifics() {
        ITEM_SPECIFICS.clear();
        ITEM_SPECIFICS_BY_MODEL.clear();
        ITEM_SPECIFICS_BY_RENAMED.clear();
        ITEM_SPECIFICS_BY_MODEL_AND_RENAMED.clear();
    }

    public static void clearSpecificOverride(class_2960 class_29602) {
        if (class_29602 != null) {
            ITEM_SPECIFICS.remove(class_29602);
            ITEM_SPECIFICS_BY_MODEL.remove(class_29602);
            ITEM_SPECIFICS_BY_RENAMED.remove(class_29602);
            ITEM_SPECIFICS_BY_MODEL_AND_RENAMED.remove(class_29602);
        }
    }

    public static void clearSpecificAnimationOverrides(class_2960 class_29602) {
        if (class_29602 != null) {
            SpecificEntry specificEntry = ITEM_SPECIFICS.get(class_29602);
            if (specificEntry != null) {
                specificEntry.customAnimation = null;
                specificEntry.customItemAnimations = null;
            }
            Map<Integer, SpecificEntry> modelEntries = ITEM_SPECIFICS_BY_MODEL.get(class_29602);
            if (modelEntries != null) {
                for (SpecificEntry entry : modelEntries.values()) {
                    if (entry == null) continue;
                    entry.customAnimation = null;
                    entry.customItemAnimations = null;
                }
            }
            Map<String, SpecificEntry> renamedEntries = ITEM_SPECIFICS_BY_RENAMED.get(class_29602);
            if (renamedEntries != null) {
                for (SpecificEntry entry : renamedEntries.values()) {
                    if (entry == null) continue;
                    entry.customAnimation = null;
                    entry.customItemAnimations = null;
                }
            }
            Map<Integer, Map<String, SpecificEntry>> renamedByModel = ITEM_SPECIFICS_BY_MODEL_AND_RENAMED.get(class_29602);
            if (renamedByModel != null) {
                for (Map<String, SpecificEntry> entries : renamedByModel.values()) {
                    if (entries == null) continue;
                    for (SpecificEntry entry : entries.values()) {
                        if (entry == null) continue;
                        entry.customAnimation = null;
                        entry.customItemAnimations = null;
                    }
                }
            }
        }
    }

    public static void clearGroups() {
        GROUP_ANIMATIONS.clear();
        GROUP_ITEM_ANIMATIONS.clear();
        GROUP_PARTICLE_EMITTERS.clear();
        GROUP_BETTER_COMBAT.clear();
        DEFAULT_ANIMATIONS = null;
        DEFAULT_BETTER_COMBAT = null;
    }

    private static SpecificEntry getSpecificEntry(class_1799 class_17992) {
        class_2960 class_29602 = ToolTuning.idFor(class_17992);
        if (class_29602 == null) {
            return null;
        }
        Integer n = ToolTuning.resolveCustomModelData(class_17992);
        String string = ToolTuning.resolveStackRenamedKey(class_17992);
        Map<Integer, Map<String, SpecificEntry>> renamedByModel = ITEM_SPECIFICS_BY_MODEL_AND_RENAMED.get(class_29602);
        Map<String, SpecificEntry> modelRenamedEntries = n == null || renamedByModel == null ? null : renamedByModel.get(n);
        SpecificEntry specificEntry = string == null || modelRenamedEntries == null ? null : modelRenamedEntries.get(string);
        if (specificEntry != null) {
            return specificEntry;
        }
        Map<String, SpecificEntry> renamedEntries = ITEM_SPECIFICS_BY_RENAMED.get(class_29602);
        specificEntry = string == null || renamedEntries == null ? null : renamedEntries.get(string);
        if (specificEntry != null) {
            return specificEntry;
        }
        Map<Integer, SpecificEntry> modelEntries = ITEM_SPECIFICS_BY_MODEL.get(class_29602);
        specificEntry = n == null || modelEntries == null ? null : modelEntries.get(n);
        if (specificEntry != null) {
            return specificEntry;
        }
        return ITEM_SPECIFICS.get(class_29602);
    }

    private static String resolveStackRenamedKey(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            try {
                if (!class_17992.method_57826(class_9334.field_49631)) {
                    return null;
                }
            }
            catch (Throwable throwable) {
                return null;
            }
            try {
                return ToolTuning.normalizeRenamed(class_17992.method_7964().getString());
            }
            catch (Throwable throwable) {
                return null;
            }
        }
        return null;
    }

    public static String resolveStackRenamedKeyForDebug(class_1799 class_17992) {
        return ToolTuning.resolveStackRenamedKey(class_17992);
    }

    private static String normalizeRenamed(String string) {
        if (string == null) {
            return null;
        }
        String string2 = string.trim().toLowerCase(Locale.ROOT);
        return string2.isEmpty() ? null : string2;
    }

    private static Integer resolveCustomModelData(class_1799 class_17992) {
        if (class_17992 != null && !class_17992.method_7960()) {
            try {
                Object object;
                Method method;
                Object object2 = class_17992.method_57824(class_9334.field_49637);
                if (object2 == null) {
                    return null;
                }
                if (object2 instanceof Integer) {
                    Integer n = (Integer)object2;
                    return n;
                }
                try {
                    method = object2.getClass().getMethod("value", new Class[0]);
                    object = method.invoke(object2, new Object[0]);
                    if (object instanceof Integer) {
                        Integer n = (Integer)object;
                        return n;
                    }
                }
                catch (Throwable throwable) {
                }
                try {
                    method = object2.getClass().getMethod("getValue", new Class[0]);
                    object = method.invoke(object2, new Object[0]);
                    if (object instanceof Integer) {
                        Integer n = (Integer)object;
                        return n;
                    }
                }
                catch (Throwable throwable) {}
            }
            catch (Throwable throwable) {
            }
            return null;
        }
        return null;
    }

    public static Integer resolveCustomModelDataForDebug(class_1799 class_17992) {
        return ToolTuning.resolveCustomModelData(class_17992);
    }

    static {
        OVERRIDE_RIGHT = new EnumMap<VanillaFirstPersonItemLayer.ToolKind, TransformOverride>(VanillaFirstPersonItemLayer.ToolKind.class);
        OVERRIDE_LEFT = new EnumMap<VanillaFirstPersonItemLayer.ToolKind, TransformOverride>(VanillaFirstPersonItemLayer.ToolKind.class);
        ITEM_SPECIFICS = new HashMap<class_2960, SpecificEntry>();
        ITEM_SPECIFICS_BY_MODEL = new HashMap<class_2960, Map<Integer, SpecificEntry>>();
        ITEM_SPECIFICS_BY_RENAMED = new HashMap<class_2960, Map<String, SpecificEntry>>();
        ITEM_SPECIFICS_BY_MODEL_AND_RENAMED = new HashMap<class_2960, Map<Integer, Map<String, SpecificEntry>>>();
        ATTACHED_RIGHT = new EnumMap(VanillaFirstPersonItemLayer.ToolKind.class);
        ATTACHED_LEFT = new EnumMap(VanillaFirstPersonItemLayer.ToolKind.class);
        GROUP_ANIMATIONS = new EnumMap(VanillaFirstPersonItemLayer.ToolKind.class);
        GROUP_ITEM_ANIMATIONS = new EnumMap(VanillaFirstPersonItemLayer.ToolKind.class);
        GROUP_PARTICLE_EMITTERS = new EnumMap(VanillaFirstPersonItemLayer.ToolKind.class);
        GROUP_BETTER_COMBAT = new EnumMap(VanillaFirstPersonItemLayer.ToolKind.class);
        ToolTuning.resetDefaults();
    }

    public static enum RotationOrder {
        XYZ,
        ZXY;

    }

    public static final class Profile {
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
        public Boolean disableSprintLateralTilt;
        public Boolean keepArmTuning;
        public Boolean keepTriggerArmTuning;
        public PhysicsMode physicsMode;
        public PhysicsSettings physicsSettings;
        private float rotXRad;
        private float rotYRad;
        private float rotZRad;

        public Profile(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            this(f, f2, f3, f4, f5, f6, f7, 0.0f, 0.0f, 0.0f, PhysicsMode.NONE);
        }

        public Profile(float f, float f2, float f3, float f4, float f5, float f6, float f7, PhysicsMode physicsMode) {
            this(f, f2, f3, f4, f5, f6, f7, 0.0f, 0.0f, 0.0f, physicsMode);
        }

        public Profile(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            this(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, PhysicsMode.NONE);
        }

        public Profile(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, PhysicsMode physicsMode) {
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
            this.physicsMode = physicsMode != null ? physicsMode : PhysicsMode.NONE;
            this.recomputeRadians();
        }

        public void recomputeRadians() {
            this.rotXRad = (float)Math.toRadians(this.rotX);
            this.rotYRad = (float)Math.toRadians(this.rotY);
            this.rotZRad = (float)Math.toRadians(this.rotZ);
        }

        public float rotXRad() {
            return this.rotXRad;
        }

        public float rotYRad() {
            return this.rotYRad;
        }

        public float rotZRad() {
            return this.rotZRad;
        }

        public Profile mirror() {
            Profile profile = new Profile(this.scale, -this.offX, this.offY, this.offZ, this.rotX, -this.rotY, -this.rotZ, -this.pivotX, this.pivotY, this.pivotZ, this.physicsMode);
            profile.disableSprintLateralTilt = this.disableSprintLateralTilt;
            profile.keepArmTuning = this.keepArmTuning;
            profile.keepTriggerArmTuning = this.keepTriggerArmTuning;
            if (this.physicsSettings != null) {
                profile.physicsSettings = this.physicsSettings.copy();
            }
            return profile;
        }
    }

    public static enum PhysicsMode {
        NONE,
        FULL_PENDULUM,
        FORWARD_PENDULUM;

    }

    static final class TransformOverride {
        float scale;
        float offX;
        float offY;
        float offZ;
        float rotX;
        float rotY;
        float rotZ;
        float pivotX;
        float pivotY;
        float pivotZ;

        TransformOverride() {
        }

        void set(Profile profile, Profile profile2) {
            this.scale = profile2.scale - profile.scale;
            this.offX = profile2.offX - profile.offX;
            this.offY = profile2.offY - profile.offY;
            this.offZ = profile2.offZ - profile.offZ;
            this.rotX = profile2.rotX - profile.rotX;
            this.rotY = profile2.rotY - profile.rotY;
            this.rotZ = profile2.rotZ - profile.rotZ;
            this.pivotX = profile2.pivotX - profile.pivotX;
            this.pivotY = profile2.pivotY - profile.pivotY;
            this.pivotZ = profile2.pivotZ - profile.pivotZ;
        }
    }

    public static final class PhysicsSettings {
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

        public PhysicsSettings copy() {
            PhysicsSettings physicsSettings = new PhysicsSettings();
            physicsSettings.limitDeg = this.limitDeg;
            physicsSettings.limitDegUp = this.limitDegUp;
            physicsSettings.limitDegDown = this.limitDegDown;
            physicsSettings.force = this.force;
            physicsSettings.speed = this.speed;
            physicsSettings.cameraSens = this.cameraSens;
            physicsSettings.gravity = this.gravity;
            physicsSettings.motionSens = this.motionSens;
            physicsSettings.invert = this.invert;
            physicsSettings.handSpace = this.handSpace;
            physicsSettings.enableCreaturePhysics = this.enableCreaturePhysics;
            return physicsSettings;
        }
    }

    static final class SpecificEntry {
        VanillaFirstPersonItemLayer.ToolKind kind;
        String model;
        String renamed;
        List<PunchyTuningConfig.CustomAnimationEntry> customAnimation;
        List<PunchyTuningConfig.CustomAnimationEntry> customItemAnimations;
        List<String> particleEmitters;
        List<PunchyTuningConfig.BetterCombatEntry> betterCombat;
        JsonElement bonePhysics;
        Profile right;
        Profile left;

        SpecificEntry(VanillaFirstPersonItemLayer.ToolKind toolKind) {
            this.kind = toolKind;
        }

        Profile get(boolean bl) {
            return bl ? this.left : this.right;
        }

        void set(boolean bl, Profile profile) {
            if (bl) {
                this.left = profile;
            } else {
                this.right = profile;
            }
        }
    }

    public static final class SpecificSnapshotDetailed {
        private final class_2960 itemId;
        private final Integer customModelData;
        private final String renamed;
        private final String model;
        private final VanillaFirstPersonItemLayer.ToolKind kind;
        private final Profile right;
        private final Profile left;

        public SpecificSnapshotDetailed(class_2960 class_29602, Integer n, String string, String string2, VanillaFirstPersonItemLayer.ToolKind toolKind, Profile profile, Profile profile2) {
            this.itemId = class_29602;
            this.customModelData = n;
            this.renamed = string;
            this.model = string2;
            this.kind = toolKind;
            this.right = profile;
            this.left = profile2;
        }

        public class_2960 itemId() {
            return this.itemId;
        }

        public Integer customModelData() {
            return this.customModelData;
        }

        public String renamed() {
            return this.renamed;
        }

        public String model() {
            return this.model;
        }

        public VanillaFirstPersonItemLayer.ToolKind kind() {
            return this.kind;
        }

        public Profile right() {
            return this.right;
        }

        public Profile left() {
            return this.left;
        }
    }

    public static final class SpecificSnapshot {
        private final VanillaFirstPersonItemLayer.ToolKind kind;
        private final Profile right;
        private final Profile left;

        public SpecificSnapshot(VanillaFirstPersonItemLayer.ToolKind toolKind, Profile profile, Profile profile2) {
            this.kind = toolKind;
            this.right = profile;
            this.left = profile2;
        }

        public VanillaFirstPersonItemLayer.ToolKind kind() {
            return this.kind;
        }

        public Profile right() {
            return this.right;
        }

        public Profile left() {
            return this.left;
        }
    }
}
