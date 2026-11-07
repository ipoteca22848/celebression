package ru.method.celebression.punchy.client.render;

import java.util.Set;
import net.minecraft.class_1747;
import net.minecraft.class_1749;
import net.minecraft.class_1753;
import net.minecraft.class_1755;
import net.minecraft.class_1764;
import net.minecraft.class_1785;
import net.minecraft.class_1786;
import net.minecraft.class_1787;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1808;
import net.minecraft.class_1819;
import net.minecraft.class_1835;
import net.minecraft.class_1839;
import net.minecraft.class_1922;
import net.minecraft.class_2244;
import net.minecraft.class_2248;
import net.minecraft.class_2269;
import net.minecraft.class_2338;
import net.minecraft.class_2440;
import net.minecraft.class_2527;
import net.minecraft.class_2533;
import net.minecraft.class_2577;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2682;
import net.minecraft.class_2960;
import net.minecraft.class_3489;
import net.minecraft.class_4739;
import net.minecraft.class_5321;
import net.minecraft.class_5538;
import net.minecraft.class_6862;
import net.minecraft.class_7923;
import net.minecraft.class_7924;
import net.minecraft.class_9334;
import net.minecraft.class_9362;
import ru.method.celebression.punchy.client.render.ToolTuning;
import ru.method.celebression.punchy.client.renderer.layer.PlayerArmModelLayer;
import ru.method.celebression.punchy.client.renderer.layer.VanillaFirstPersonItemLayer;

public final class ToolKindResolver {
    private static final class_6862<class_1792> SPEAR_TAG = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"spears"));
    private static final class_6862<class_1792> BUILDING_BLOCKS_TAG = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"building_blocks"));
    private static final class_6862<class_1792> COLORED_BLOCKS_TAG = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"colored_blocks"));
    private static final class_6862<class_1792> NATURAL_BLOCKS_TAG = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"natural_blocks"));
    private static final class_6862<class_1792> FUNCTIONAL_BLOCKS_TAG = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"functional_blocks"));
    private static final class_6862<class_1792> FISHING_RODS_TAG = class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)class_2960.method_60655((String)"minecraft", (String)"fishing_rods"));
    private static final Set<class_1792> REDSTONE_COMPONENTS = Set.of(class_1802.field_8725, class_1802.field_8793, class_1802.field_8530, class_1802.field_8619, class_1802.field_8857, class_1802.field_8230, class_1802.field_8537, class_1802.field_8865, class_1802.field_8357, class_1802.field_8878, class_1802.field_8643, class_1802.field_8566, class_1802.field_8366, class_1802.field_22420, class_1802.field_8239, class_1802.field_8249, class_1802.field_8105, class_1802.field_27051, class_1802.field_8848, class_1802.field_8211, class_1802.field_8655, class_1802.field_28101, class_1802.field_43194);

    private ToolKindResolver() {
    }

    private static boolean isRedstoneComponent(class_1792 class_17922) {
        return REDSTONE_COMPONENTS.contains(class_17922);
    }

    private static boolean isCreatureBucket(class_1792 class_17922) {
        return class_17922 == class_1802.field_28354 || class_17922 == class_1802.field_8478 || class_17922 == class_1802.field_8108 || class_17922 == class_1802.field_8666 || class_17922 == class_1802.field_8714 || class_17922 == class_1802.field_37533;
    }

    private static boolean isBucket(class_1792 class_17922) {
        if (class_17922 == null) {
            return false;
        }
        if (class_17922 instanceof class_1785) {
            return true;
        }
        if (class_17922 instanceof class_1755) {
            return true;
        }
        class_2960 class_29602 = class_7923.field_41178.method_10221(class_17922);
        return class_29602 != null && class_29602.method_12832().contains("bucket");
    }

    private static boolean isMinecart(class_1792 class_17922) {
        return class_17922 instanceof class_1808;
    }

    private static boolean isBoat(class_1792 class_17922) {
        return class_17922 instanceof class_1749;
    }

    private static boolean isButtonOrPlate(class_1747 class_17472) {
        class_2248 class_22482 = class_17472.method_7711();
        return class_22482 instanceof class_2269 || class_22482 instanceof class_2440;
    }

    private static boolean isTrapdoor(class_1747 class_17472) {
        return class_17472.method_7711() instanceof class_2533;
    }

    private static boolean isCarpet(class_1747 class_17472) {
        return class_17472.method_7711() instanceof class_2577;
    }

    private static boolean isBed(class_1747 class_17472) {
        return class_17472.method_7711() instanceof class_2244;
    }

    private static boolean isChest(class_1747 class_17472) {
        return class_17472.method_7711() instanceof class_4739;
    }

    private static boolean isCopperLantern(class_1792 class_17922) {
        class_2960 class_29602 = class_7923.field_41178.method_10221(class_17922);
        return class_29602 != null && class_29602.method_12832().contains("copper_lantern");
    }

    private static boolean isFullCube(class_1747 class_17472) {
        class_2680 class_26802 = class_17472.method_7711().method_9564();
        return class_26802.method_26234((class_1922)class_2682.field_12294, class_2338.field_10980) && class_2248.method_9614((class_265)class_26802.method_26220((class_1922)class_2682.field_12294, class_2338.field_10980));
    }

    private static boolean isSpearStack(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        if (ToolTuning.getSpecificCustomAnimation(class_17992) != null) {
            return false;
        }
        if (class_17992.method_7909() instanceof class_1835) {
            return false;
        }
        return class_17992.method_31573(SPEAR_TAG) ? true : class_17992.method_7976() == class_1839.field_8951;
    }

    private static boolean isFishingRodStack(class_1799 class_17992) {
        if (class_17992 == null || class_17992.method_7960()) {
            return false;
        }
        if (class_17992.method_31573(FISHING_RODS_TAG)) {
            return true;
        }
        class_1792 class_17922 = class_17992.method_7909();
        return class_17922 != class_1802.field_8184 && class_17922 != class_1802.field_23254 ? class_17922 instanceof class_1787 : true;
    }

    public static VanillaFirstPersonItemLayer.ToolKind resolveItemKind(class_1799 class_17992, VanillaFirstPersonItemLayer.ToolKind toolKind) {
        if (class_17992 != null && !class_17992.method_7960()) {
            class_1792 class_17922;
            VanillaFirstPersonItemLayer.ToolKind toolKind2 = ToolTuning.resolveOverriddenKind(class_17992);
            if (toolKind2 != null) {
                return toolKind2;
            }
            class_1792 class_17923 = class_17922 = class_17992 == null ? null : class_17992.method_7909();
            if (class_17922 == null) {
                return toolKind;
            }
            if (class_17992.method_31573(class_3489.field_42611)) {
                return VanillaFirstPersonItemLayer.ToolKind.SWORD;
            }
            if (class_17992.method_31573(class_3489.field_42612)) {
                return VanillaFirstPersonItemLayer.ToolKind.AXE;
            }
            if (class_17992.method_31573(class_3489.field_42614)) {
                return VanillaFirstPersonItemLayer.ToolKind.PICKAXE;
            }
            if (class_17992.method_31573(class_3489.field_42615)) {
                return VanillaFirstPersonItemLayer.ToolKind.SHOVEL;
            }
            if (class_17992.method_31573(class_3489.field_42613)) {
                return VanillaFirstPersonItemLayer.ToolKind.HOE;
            }
            if (ToolKindResolver.isSpearStack(class_17992)) {
                return VanillaFirstPersonItemLayer.ToolKind.SPEAR;
            }
            if (class_17992.method_57824(class_9334.field_50075) != null) {
                return VanillaFirstPersonItemLayer.ToolKind.FOOD;
            }
            if (class_17922 == class_1802.field_16539 || class_17922 == class_1802.field_22016 || ToolKindResolver.isCopperLantern(class_17922)) {
                return VanillaFirstPersonItemLayer.ToolKind.LANTERN;
            }
            if (ToolKindResolver.isCreatureBucket(class_17922)) {
                return VanillaFirstPersonItemLayer.ToolKind.CREATURE_BUCKET;
            }
            if (ToolKindResolver.isBucket(class_17922)) {
                return VanillaFirstPersonItemLayer.ToolKind.BUCKET;
            }
            if (ToolKindResolver.isMinecart(class_17922)) {
                return VanillaFirstPersonItemLayer.ToolKind.MINECART;
            }
            if (ToolKindResolver.isBoat(class_17922)) {
                return VanillaFirstPersonItemLayer.ToolKind.BOAT;
            }
            if (class_17922 instanceof class_1753) {
                return VanillaFirstPersonItemLayer.ToolKind.BOW;
            }
            if (class_17922 == class_1802.field_8107) {
                return VanillaFirstPersonItemLayer.ToolKind.ARROW;
            }
            if (class_17922 instanceof class_1764) {
                return VanillaFirstPersonItemLayer.ToolKind.CROSSBOW;
            }
            if (class_17922 instanceof class_1819) {
                return VanillaFirstPersonItemLayer.ToolKind.SHIELD;
            }
            if (ToolKindResolver.isFishingRodStack(class_17992)) {
                return VanillaFirstPersonItemLayer.ToolKind.FISHING_ROD;
            }
            if (class_17922 instanceof class_1835) {
                return VanillaFirstPersonItemLayer.ToolKind.TRIDENT;
            }
            if (class_17922 instanceof class_5538) {
                return VanillaFirstPersonItemLayer.ToolKind.SPYGLASS;
            }
            if (class_17922 instanceof class_1786) {
                return VanillaFirstPersonItemLayer.ToolKind.FLINT;
            }
            if (class_17922 instanceof class_9362) {
                return VanillaFirstPersonItemLayer.ToolKind.MACE;
            }
            if (class_17922 instanceof class_1747) {
                class_1747 class_17472 = (class_1747)class_17922;
                if (class_17472.method_7711() instanceof class_2527) {
                    return VanillaFirstPersonItemLayer.ToolKind.TORCH;
                }
                if (ToolKindResolver.isChest(class_17472)) {
                    return VanillaFirstPersonItemLayer.ToolKind.CHEST;
                }
                if (ToolKindResolver.isButtonOrPlate(class_17472)) {
                    return VanillaFirstPersonItemLayer.ToolKind.BUTTON_PLATE;
                }
                if (ToolKindResolver.isTrapdoor(class_17472)) {
                    return VanillaFirstPersonItemLayer.ToolKind.TRAPDOOR;
                }
                if (ToolKindResolver.isCarpet(class_17472)) {
                    return VanillaFirstPersonItemLayer.ToolKind.CARPET;
                }
                if (ToolKindResolver.isBed(class_17472)) {
                    return VanillaFirstPersonItemLayer.ToolKind.BED;
                }
                if (ToolKindResolver.isFullCube(class_17472)) {
                    return VanillaFirstPersonItemLayer.ToolKind.FULL_BLOCK;
                }
                if (class_17992.method_31573(BUILDING_BLOCKS_TAG)) {
                    return VanillaFirstPersonItemLayer.ToolKind.BUILDING_BLOCKS;
                }
                if (class_17992.method_31573(COLORED_BLOCKS_TAG)) {
                    return VanillaFirstPersonItemLayer.ToolKind.COLORED_BLOCKS;
                }
                if (class_17992.method_31573(NATURAL_BLOCKS_TAG)) {
                    return VanillaFirstPersonItemLayer.ToolKind.NATURAL_BLOCKS;
                }
                return class_17992.method_31573(FUNCTIONAL_BLOCKS_TAG) ? VanillaFirstPersonItemLayer.ToolKind.FUNCTIONAL_BLOCKS : VanillaFirstPersonItemLayer.ToolKind.BLOCK;
            }
            return ToolKindResolver.isRedstoneComponent(class_17922) ? VanillaFirstPersonItemLayer.ToolKind.REDSTONE : VanillaFirstPersonItemLayer.ToolKind.INGREDIENT;
        }
        return VanillaFirstPersonItemLayer.ToolKind.EMPTY_HAND;
    }

    public static PlayerArmModelLayer.ToolKind resolveArmKind(class_1799 class_17992, PlayerArmModelLayer.ToolKind toolKind) {
        if (class_17992 != null && !class_17992.method_7960()) {
            class_1792 class_17922;
            VanillaFirstPersonItemLayer.ToolKind toolKind2 = ToolKindResolver.resolveItemKind(class_17992, VanillaFirstPersonItemLayer.ToolKind.INGREDIENT);
            if (toolKind2 != null) {
                try {
                    return PlayerArmModelLayer.ToolKind.valueOf(toolKind2.name());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                }
            }
            class_1792 class_17923 = class_17922 = class_17992 == null ? null : class_17992.method_7909();
            if (class_17922 == null) {
                return toolKind;
            }
            if (class_17992.method_31573(class_3489.field_42611)) {
                return PlayerArmModelLayer.ToolKind.SWORD;
            }
            if (class_17992.method_31573(class_3489.field_42612)) {
                return PlayerArmModelLayer.ToolKind.AXE;
            }
            if (class_17992.method_31573(class_3489.field_42614)) {
                return PlayerArmModelLayer.ToolKind.PICKAXE;
            }
            if (class_17992.method_31573(class_3489.field_42615)) {
                return PlayerArmModelLayer.ToolKind.SHOVEL;
            }
            if (class_17992.method_31573(class_3489.field_42613)) {
                return PlayerArmModelLayer.ToolKind.HOE;
            }
            if (ToolKindResolver.isSpearStack(class_17992)) {
                return PlayerArmModelLayer.ToolKind.SPEAR;
            }
            if (class_17992.method_57824(class_9334.field_50075) != null) {
                return PlayerArmModelLayer.ToolKind.FOOD;
            }
            if (class_17922 instanceof class_1753) {
                return PlayerArmModelLayer.ToolKind.BOW;
            }
            if (class_17922 instanceof class_1764) {
                return PlayerArmModelLayer.ToolKind.CROSSBOW;
            }
            if (class_17922 instanceof class_1819) {
                return PlayerArmModelLayer.ToolKind.SHIELD;
            }
            if (class_17922 instanceof class_1835) {
                return PlayerArmModelLayer.ToolKind.TRIDENT;
            }
            if (class_17922 instanceof class_9362) {
                return PlayerArmModelLayer.ToolKind.MACE;
            }
            return class_17922 instanceof class_1747 ? PlayerArmModelLayer.ToolKind.BLOCK : toolKind;
        }
        return PlayerArmModelLayer.ToolKind.EMPTY_HAND;
    }
}
