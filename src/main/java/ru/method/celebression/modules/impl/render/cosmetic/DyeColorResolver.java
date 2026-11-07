package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.HashMap;
import java.util.Map;

public final class DyeColorResolver {
    private static final String[] yM = new String[]{"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
    private static final Map<String, String> yN = new HashMap<String, String>();

    private DyeColorResolver() {
    }

    private static void a(int n, String string) {
        yN.put(String.valueOf(n), string);
    }

    private static void a(int n, int n2, String string) {
        yN.put(n + ":" + n2, string);
        if (n2 == 0) {
            yN.put(String.valueOf(n), string);
        }
    }

    private static void a(int n, String string, String string2) {
        for (int i = 0; i < yM.length; ++i) {
            DyeColorResolver.a(n, i, string + yM[i] + string2);
        }
    }

    public static String bQ(String string) {
        return yN.get(string);
    }

    static {
        DyeColorResolver.a(0, "air");
        DyeColorResolver.a(1, 0, "stone");
        DyeColorResolver.a(1, 1, "granite");
        DyeColorResolver.a(1, 2, "polished_granite");
        DyeColorResolver.a(1, 3, "diorite");
        DyeColorResolver.a(1, 4, "polished_diorite");
        DyeColorResolver.a(1, 5, "andesite");
        DyeColorResolver.a(1, 6, "polished_andesite");
        DyeColorResolver.a(2, "grass_block");
        DyeColorResolver.a(3, 0, "dirt");
        DyeColorResolver.a(3, 1, "coarse_dirt");
        DyeColorResolver.a(3, 2, "podzol");
        DyeColorResolver.a(4, "cobblestone");
        DyeColorResolver.a(5, 0, "oak_planks");
        DyeColorResolver.a(5, 1, "spruce_planks");
        DyeColorResolver.a(5, 2, "birch_planks");
        DyeColorResolver.a(5, 3, "jungle_planks");
        DyeColorResolver.a(5, 4, "acacia_planks");
        DyeColorResolver.a(5, 5, "dark_oak_planks");
        DyeColorResolver.a(6, "oak_sapling");
        DyeColorResolver.a(7, "bedrock");
        DyeColorResolver.a(8, "water");
        DyeColorResolver.a(9, "water");
        DyeColorResolver.a(10, "lava");
        DyeColorResolver.a(11, "lava");
        DyeColorResolver.a(12, 0, "sand");
        DyeColorResolver.a(12, 1, "red_sand");
        DyeColorResolver.a(13, "gravel");
        DyeColorResolver.a(14, "gold_ore");
        DyeColorResolver.a(15, "iron_ore");
        DyeColorResolver.a(16, "coal_ore");
        DyeColorResolver.a(17, 0, "oak_log");
        DyeColorResolver.a(17, 1, "spruce_log");
        DyeColorResolver.a(17, 2, "birch_log");
        DyeColorResolver.a(17, 3, "jungle_log");
        DyeColorResolver.a(18, "oak_leaves");
        DyeColorResolver.a(19, "sponge");
        DyeColorResolver.a(20, "glass");
        DyeColorResolver.a(21, "lapis_ore");
        DyeColorResolver.a(22, "lapis_block");
        DyeColorResolver.a(23, "dispenser");
        DyeColorResolver.a(24, 0, "sandstone");
        DyeColorResolver.a(24, 1, "chiseled_sandstone");
        DyeColorResolver.a(24, 2, "cut_sandstone");
        DyeColorResolver.a(25, "note_block");
        DyeColorResolver.a(26, "red_bed");
        DyeColorResolver.a(27, "powered_rail");
        DyeColorResolver.a(28, "detector_rail");
        DyeColorResolver.a(29, "sticky_piston");
        DyeColorResolver.a(30, "cobweb");
        DyeColorResolver.a(31, "short_grass");
        DyeColorResolver.a(32, "dead_bush");
        DyeColorResolver.a(33, "piston");
        DyeColorResolver.a(35, "", "_wool");
        DyeColorResolver.a(37, "dandelion");
        DyeColorResolver.a(38, "poppy");
        DyeColorResolver.a(39, "brown_mushroom");
        DyeColorResolver.a(40, "red_mushroom");
        DyeColorResolver.a(41, "gold_block");
        DyeColorResolver.a(42, "iron_block");
        DyeColorResolver.a(43, "smooth_stone");
        DyeColorResolver.a(44, "smooth_stone_slab");
        DyeColorResolver.a(45, "bricks");
        DyeColorResolver.a(46, "tnt");
        DyeColorResolver.a(47, "bookshelf");
        DyeColorResolver.a(48, "mossy_cobblestone");
        DyeColorResolver.a(49, "obsidian");
        DyeColorResolver.a(50, "torch");
        DyeColorResolver.a(51, "fire");
        DyeColorResolver.a(52, "spawner");
        DyeColorResolver.a(53, "oak_stairs");
        DyeColorResolver.a(54, "chest");
        DyeColorResolver.a(55, "redstone_wire");
        DyeColorResolver.a(56, "diamond_ore");
        DyeColorResolver.a(57, "diamond_block");
        DyeColorResolver.a(58, "crafting_table");
        DyeColorResolver.a(60, "farmland");
        DyeColorResolver.a(61, "furnace");
        DyeColorResolver.a(63, "oak_sign");
        DyeColorResolver.a(64, "oak_door");
        DyeColorResolver.a(65, "ladder");
        DyeColorResolver.a(66, "rail");
        DyeColorResolver.a(67, "cobblestone_stairs");
        DyeColorResolver.a(68, "oak_wall_sign");
        DyeColorResolver.a(69, "lever");
        DyeColorResolver.a(70, "stone_pressure_plate");
        DyeColorResolver.a(72, "oak_pressure_plate");
        DyeColorResolver.a(73, "redstone_ore");
        DyeColorResolver.a(74, "redstone_ore");
        DyeColorResolver.a(76, "redstone_torch");
        DyeColorResolver.a(77, "stone_button");
        DyeColorResolver.a(78, "snow");
        DyeColorResolver.a(79, "ice");
        DyeColorResolver.a(80, "snow_block");
        DyeColorResolver.a(81, "cactus");
        DyeColorResolver.a(82, "clay");
        DyeColorResolver.a(83, "sugar_cane");
        DyeColorResolver.a(84, "jukebox");
        DyeColorResolver.a(85, "oak_fence");
        DyeColorResolver.a(86, "carved_pumpkin");
        DyeColorResolver.a(87, "netherrack");
        DyeColorResolver.a(88, "soul_sand");
        DyeColorResolver.a(89, "glowstone");
        DyeColorResolver.a(90, "nether_portal");
        DyeColorResolver.a(91, "jack_o_lantern");
        DyeColorResolver.a(92, "cake");
        DyeColorResolver.a(95, "", "_stained_glass");
        DyeColorResolver.a(96, "oak_trapdoor");
        DyeColorResolver.a(98, 0, "stone_bricks");
        DyeColorResolver.a(98, 1, "mossy_stone_bricks");
        DyeColorResolver.a(98, 2, "cracked_stone_bricks");
        DyeColorResolver.a(98, 3, "chiseled_stone_bricks");
        DyeColorResolver.a(99, "brown_mushroom_block");
        DyeColorResolver.a(100, "red_mushroom_block");
        DyeColorResolver.a(101, "iron_bars");
        DyeColorResolver.a(102, "glass_pane");
        DyeColorResolver.a(103, "melon");
        DyeColorResolver.a(106, "vine");
        DyeColorResolver.a(107, "oak_fence_gate");
        DyeColorResolver.a(108, "brick_stairs");
        DyeColorResolver.a(109, "stone_brick_stairs");
        DyeColorResolver.a(110, "mycelium");
        DyeColorResolver.a(111, "lily_pad");
        DyeColorResolver.a(112, "nether_bricks");
        DyeColorResolver.a(113, "nether_brick_fence");
        DyeColorResolver.a(114, "nether_brick_stairs");
        DyeColorResolver.a(116, "enchanting_table");
        DyeColorResolver.a(117, "brewing_stand");
        DyeColorResolver.a(120, "end_portal_frame");
        DyeColorResolver.a(121, "end_stone");
        DyeColorResolver.a(122, "dragon_egg");
        DyeColorResolver.a(123, "redstone_lamp");
        DyeColorResolver.a(124, "redstone_lamp");
        DyeColorResolver.a(129, "emerald_ore");
        DyeColorResolver.a(130, "ender_chest");
        DyeColorResolver.a(133, "emerald_block");
        DyeColorResolver.a(137, "command_block");
        DyeColorResolver.a(138, "beacon");
        DyeColorResolver.a(139, "cobblestone_wall");
        DyeColorResolver.a(145, "anvil");
        DyeColorResolver.a(146, "trapped_chest");
        DyeColorResolver.a(152, "redstone_block");
        DyeColorResolver.a(153, "nether_quartz_ore");
        DyeColorResolver.a(154, "hopper");
        DyeColorResolver.a(155, 0, "quartz_block");
        DyeColorResolver.a(155, 1, "chiseled_quartz_block");
        DyeColorResolver.a(155, 2, "quartz_pillar");
        DyeColorResolver.a(156, "quartz_stairs");
        DyeColorResolver.a(157, "activator_rail");
        DyeColorResolver.a(158, "dropper");
        DyeColorResolver.a(159, "", "_terracotta");
        DyeColorResolver.a(160, "", "_stained_glass_pane");
        DyeColorResolver.a(161, "acacia_leaves");
        DyeColorResolver.a(162, 0, "acacia_log");
        DyeColorResolver.a(162, 1, "dark_oak_log");
        DyeColorResolver.a(163, "acacia_stairs");
        DyeColorResolver.a(164, "dark_oak_stairs");
        DyeColorResolver.a(165, "slime_block");
        DyeColorResolver.a(166, "barrier");
        DyeColorResolver.a(167, "iron_trapdoor");
        DyeColorResolver.a(168, 0, "prismarine");
        DyeColorResolver.a(168, 1, "prismarine_bricks");
        DyeColorResolver.a(168, 2, "dark_prismarine");
        DyeColorResolver.a(169, "sea_lantern");
        DyeColorResolver.a(170, "hay_block");
        DyeColorResolver.a(171, "", "_carpet");
        DyeColorResolver.a(172, "terracotta");
        DyeColorResolver.a(173, "coal_block");
        DyeColorResolver.a(174, "packed_ice");
        DyeColorResolver.a(179, 0, "red_sandstone");
        DyeColorResolver.a(179, 1, "chiseled_red_sandstone");
        DyeColorResolver.a(179, 2, "cut_red_sandstone");
        DyeColorResolver.a(180, "red_sandstone_stairs");
        DyeColorResolver.a(183, "spruce_fence_gate");
        DyeColorResolver.a(184, "birch_fence_gate");
        DyeColorResolver.a(185, "jungle_fence_gate");
        DyeColorResolver.a(186, "dark_oak_fence_gate");
        DyeColorResolver.a(187, "acacia_fence_gate");
        DyeColorResolver.a(188, "spruce_fence");
        DyeColorResolver.a(189, "birch_fence");
        DyeColorResolver.a(190, "jungle_fence");
        DyeColorResolver.a(191, "dark_oak_fence");
        DyeColorResolver.a(192, "acacia_fence");
        DyeColorResolver.a(198, "end_rod");
        DyeColorResolver.a(199, "chorus_plant");
        DyeColorResolver.a(201, "purpur_block");
        DyeColorResolver.a(202, "purpur_pillar");
        DyeColorResolver.a(203, "purpur_stairs");
        DyeColorResolver.a(206, "end_stone_bricks");
        DyeColorResolver.a(208, "dirt_path");
        DyeColorResolver.a(213, "magma_block");
        DyeColorResolver.a(214, "nether_wart_block");
        DyeColorResolver.a(215, "red_nether_bricks");
        DyeColorResolver.a(216, "bone_block");
        DyeColorResolver.a(219, "white_shulker_box");
        DyeColorResolver.a(251, "", "_concrete");
        DyeColorResolver.a(252, "", "_concrete_powder");
    }
}

