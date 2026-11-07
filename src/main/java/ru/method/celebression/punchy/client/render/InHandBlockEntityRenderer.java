package ru.method.celebression.punchy.client.render;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.class_1047;
import net.minecraft.class_1058;
import net.minecraft.class_1059;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1921;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2586;
import net.minecraft.class_2605;
import net.minecraft.class_2665;
import net.minecraft.class_2671;
import net.minecraft.class_2680;
import net.minecraft.class_2764;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3481;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_746;
import net.minecraft.class_776;
import net.minecraft.class_7833;
import net.minecraft.class_824;
import net.minecraft.class_827;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import ru.method.celebression.punchy.client.debug.PunchyDebug;
import ru.method.celebression.punchy.client.render.EnchantParticleProxyTuning;
import ru.method.celebression.punchy.compat.EmfEtfCemCompat;
import ru.method.celebression.punchy.compat.entityculling.EntityCullingCompat;
import ru.method.celebression.punchy.config.PunchyConfig;

public final class InHandBlockEntityRenderer {
    private static final float ENCHANT_TABLE_SCALE = 0.52f;
    private static final float ENCHANT_TABLE_Y_OFFSET = -0.15f;
    private static final float ENCHANT_TABLE_Z_OFFSET = 0.05f;
    private static final float ENCHANT_TABLE_TILT_DEG = 45.0f;
    private static final float ENCHANT_BOOK_FLOAT_MAX = 0.65f;
    private static final float ENCHANT_BOOK_RISE_LERP = 0.1f;
    private static final float ENCHANT_BOOK_FALL_LERP = 0.06f;
    private static final int ENCHANT_OPEN_DELAY_TICKS = 20;
    private static final int ENCHANT_REEQUIP_RESET_TICKS = 4;
    private static final float ENCHANT_BOOK_SCALE = 1.25f;
    private static final float BOOK_ATTRACT_X = 0.0f;
    private static final float BOOK_ATTRACT_Y = 0.75f;
    private static final float BOOK_ATTRACT_Z = 0.0f;
    private static final float PISTON_SCALE = 0.62f;
    private static final float PISTON_Y_OFFSET = -0.12f;
    private static final float PISTON_Z_OFFSET = 0.08f;
    private static final float PISTON_EXTEND_DISTANCE = 1.0f;
    private static final float PISTON_FALL_MAX = 0.65f;
    private static final float PISTON_FALL_SPEED_MAX = 0.6f;
    private static final float PISTON_FALL_DISTANCE_MAX = 6.0f;
    private static final float PISTON_FALL_RISE_LERP = 0.08f;
    private static final float PISTON_FALL_DROP_LERP = 0.35f;
    private static final class_2960 PARTICLE_ATLAS_ID = class_1059.field_17898;
    private static final EnumMap<class_1306, class_2605> ENCHANT_ENTITIES = new EnumMap(class_1306.class);
    private static class_2680 enchantingState = class_2246.field_10485.method_9564();
    private static final EnumMap<class_1306, HeldBookState> ENCHANT_STATES = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, HeldPistonState> PISTON_STATES = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, class_243> HELD_PIVOT_WORLD = new EnumMap(class_1306.class);
    private static final EnumMap<class_1306, Long> HELD_PIVOT_TICK = new EnumMap(class_1306.class);
    private static final List<HandEnchantParticle> HAND_PARTICLES = new ArrayList<HandEnchantParticle>();
    private static long lastParticleUpdateTick = -1L;

    private InHandBlockEntityRenderer() {
    }

    public static boolean renderIfSupported(class_1799 class_17992, class_746 class_7462, class_1306 class_13062, class_4587 class_45872, class_4597 class_45972, int n, float f) {
        if (class_17992 == null || class_17992.method_7960() || class_7462 == null) {
            return false;
        }
        if (class_17992.method_31574(class_1802.field_8657)) {
            return PunchyConfig.disableEnchantingTableModels() ? false : InHandBlockEntityRenderer.renderEnchantingTable(class_7462, class_13062, class_45872, class_45972, n, f);
        }
        if (!class_17992.method_31574(class_1802.field_8249) && !class_17992.method_31574(class_1802.field_8105)) {
            return false;
        }
        return PunchyConfig.disablePistonModels() ? false : InHandBlockEntityRenderer.renderPiston(class_7462, class_13062, class_17992, class_45872, class_45972, n);
    }

    private static boolean renderEnchantingTable(class_746 class_7462, class_1306 class_13063, class_4587 class_45872, class_4597 class_45972, int n, float f) {
        Vector3f vector3f;
        class_310 class_3102 = class_310.method_1551();
        class_4184 class_41842 = class_3102.field_1773.method_19418();
        class_1937 class_19372 = class_7462.method_37908();
        if (class_19372 == null) {
            return false;
        }
        class_2605 class_26052 = InHandBlockEntityRenderer.ensureEnchantingTable(class_19372, class_7462.method_24515(), class_13063 != null ? class_13063 : class_1306.field_6183);
        if (class_26052 == null) {
            return false;
        }
        class_824 class_8242 = class_3102.method_31975();
        class_8242.method_3549(class_19372, class_41842, class_3102.field_1765);
        class_827 class_8272 = class_8242.method_3550((class_2586)class_26052);
        if (class_8272 == null) {
            return false;
        }
        class_45872.method_22903();
        class_45872.method_46416(0.0f, -0.15f, 0.05f);
        class_45872.method_22907(class_7833.field_40714.rotationDegrees(45.0f));
        class_45872.method_22905(0.52f, 0.52f, 0.52f);
        class_45872.method_46416(-0.5f, -0.5f, -0.5f);
        class_776 class_7762 = class_3102.method_1541();
        class_7762.method_3353(enchantingState, class_45872, class_45972, n, class_4608.field_21444);
        class_45872.method_22903();
        HeldBookState heldBookState = ENCHANT_STATES.computeIfAbsent(class_13063 != null ? class_13063 : class_1306.field_6183, class_13062 -> new HeldBookState());
        boolean bl = EmfEtfCemCompat.disableProxyPhysics(EmfEtfCemCompat.ProxyPhysicsTarget.ENCHANTING_TABLE);
        if (bl) {
            heldBookState.bookOffset = 0.0f;
            InHandBlockEntityRenderer.resetEnchantingBookState(class_26052);
        }
        if (heldBookState.bookOffset != 0.0f) {
            class_45872.method_46416(0.0f, heldBookState.bookOffset, 0.0f);
        }
        class_45872.method_46416(0.5f, 0.75f, 0.5f);
        class_45872.method_22905(1.25f, 1.25f, 1.25f);
        class_45872.method_46416(-0.5f, -0.75f, -0.5f);
        Vector3f vector3f2 = new Vector3f(0.0f, 0.75f, 0.0f);
        Vector3f vector3f3 = InHandBlockEntityRenderer.applyParticleProxy(new Vector3f((Vector3fc)vector3f2), class_13063);
        if (PunchyDebug.DEBUG_POSITION_ENABLED && vector3f3 != null) {
            class_45872.method_22903();
            class_45872.method_46416(vector3f3.x, vector3f3.y, vector3f3.z);
            InHandBlockEntityRenderer.renderDebugMarker(class_45972, class_45872, n);
            class_45872.method_22909();
        }
        class_243 class_2432 = InHandBlockEntityRenderer.resolvePoseWorld(class_45872, class_41842, vector3f2);
        Vector3f vector3f4 = vector3f = vector3f3 != null ? vector3f3 : vector3f2;
        if (!bl) {
            InHandBlockEntityRenderer.updateEnchantingBook(class_7462, heldBookState, class_26052, class_19372.method_8510(), class_2432);
            InHandBlockEntityRenderer.renderHandParticles(class_45972, class_41842, f, n, class_13063, class_45872, vector3f);
        }
        EntityCullingCompat.withBlockEntityFrustumCullingDisabled(() -> {
            class_8272.method_3569((class_2586)class_26052, f, class_45872, class_45972, n, class_4608.field_21444);
            return null;
        });
        class_45872.method_22909();
        class_45872.method_22909();
        return true;
    }

    private static boolean renderPiston(class_746 class_7462, class_1306 class_13063, class_1799 class_17992, class_4587 class_45872, class_4597 class_45972, int n) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return false;
        }
        class_1937 class_19372 = class_7462.method_37908();
        if (class_19372 == null) {
            return false;
        }
        HeldPistonState heldPistonState = PISTON_STATES.computeIfAbsent(class_13063 != null ? class_13063 : class_1306.field_6183, class_13062 -> new HeldPistonState());
        boolean bl = class_17992.method_31574(class_1802.field_8105);
        boolean bl2 = InHandBlockEntityRenderer.isRedstoneBlock(class_19372.method_8320(class_7462.method_24515().method_10074()));
        boolean bl3 = EmfEtfCemCompat.disableProxyPhysics(EmfEtfCemCompat.ProxyPhysicsTarget.PISTON);
        if (bl3) {
            heldPistonState.extension = 0.0f;
            heldPistonState.fallOffset = 0.0f;
            heldPistonState.extended = false;
        } else {
            heldPistonState.update(class_7462, class_19372, bl2, class_7462.method_24828(), class_7462.field_6017, (float)class_7462.method_18798().field_1351);
        }
        boolean bl4 = heldPistonState.extension > 0.01f || heldPistonState.fallOffset > 0.01f;
        class_2680 class_26802 = (class_2680)((class_2680)(bl ? class_2246.field_10615 : class_2246.field_10560).method_9564().method_11657((class_2769)class_2665.field_12191, (Comparable)Boolean.valueOf(bl4))).method_11657((class_2769)class_2665.field_10927, (Comparable)class_2350.field_11036);
        class_2680 class_26803 = (class_2680)((class_2680)class_2246.field_10379.method_9564().method_11657((class_2769)class_2671.field_10927, (Comparable)class_2350.field_11036)).method_11657((class_2769)class_2671.field_12224, (Comparable)(bl ? class_2764.field_12634 : class_2764.field_12637));
        class_45872.method_22903();
        class_45872.method_46416(0.0f, -0.12f, 0.08f);
        class_45872.method_22905(0.62f, 0.62f, 0.62f);
        class_45872.method_46416(-0.5f, -0.5f, -0.5f);
        class_776 class_7762 = class_3102.method_1541();
        class_7762.method_3353(class_26802, class_45872, class_45972, n, class_4608.field_21444);
        if (bl4) {
            class_45872.method_22903();
            class_45872.method_46416(0.0f, heldPistonState.extension * 1.0f + heldPistonState.fallOffset, 0.0f);
            class_7762.method_3353(class_26803, class_45872, class_45972, n, class_4608.field_21444);
            class_45872.method_22909();
        }
        class_45872.method_22909();
        return true;
    }

    private static boolean isRedstoneBlock(class_2680 class_26802) {
        return class_26802 == null ? false : class_26802.method_27852(class_2246.field_10002) || class_26802.method_27852(class_2246.field_10523) || class_26802.method_27852(class_2246.field_10301) || class_26802.method_27852(class_2246.field_10091) || class_26802.method_27852(class_2246.field_10450) || class_26802.method_27852(class_2246.field_10377) || class_26802.method_27852(class_2246.field_10363) || class_26802.method_27852(class_2246.field_10282) || class_26802.method_27852(class_2246.field_10429) || class_26802.method_27852(class_2246.field_22422) || class_26802.method_27852(class_2246.field_10348) || class_26802.method_27852(class_2246.field_10179) || class_26802.method_27852(class_2246.field_10200) || class_26802.method_27852(class_2246.field_10228) || class_26802.method_27852(class_2246.field_10312) || class_26802.method_27852(class_2246.field_27171) || class_26802.method_27852(class_2246.field_10425) || class_26802.method_27852(class_2246.field_10025) || class_26802.method_27852(class_2246.field_10546) || class_26802.method_27852(class_2246.field_28108) || class_26802.method_27852(class_2246.field_43231) || class_26802.method_26164(class_3481.field_15493) || class_26802.method_26164(class_3481.field_24076);
    }

    private static void updateEnchantingBook(class_746 class_7462, HeldBookState heldBookState, class_2605 class_26052, long l, class_243 class_2432) {
        heldBookState.onSeen(l);
        heldBookState.updateFallPhysics(class_7462.method_24828(), class_7462.field_6017, (float)class_7462.method_18798().field_1351);
        class_26052.field_11965 = class_26052.field_11966;
        class_26052.field_11963 = class_26052.field_11964;
        class_26052.field_11960 = class_26052.field_11958;
        float f = heldBookState.shouldOpen(l) ? 1.0f : 0.0f;
        class_26052.field_11966 = class_3532.method_16439((float)0.18f, (float)class_26052.field_11966, (float)f);
        class_26052.field_11962 = 0.0f;
        class_26052.field_11964 = 0.0f;
        class_26052.field_11963 = 0.0f;
        boolean bl = false;
        if (heldBookState.shouldOpen(l) && heldBookState.lastParticleTick != l) {
            heldBookState.lastParticleTick = l;
            class_2338 class_23382 = class_7462.method_24515();
            for (int i = -2; i <= 2; ++i) {
                for (int j = -2; j <= 2; ++j) {
                    if (i > -2 && i < 2 && j == -1) {
                        j = 2;
                    }
                    if (class_7462.method_59922().method_43048(16) != 0) continue;
                    for (int k = 0; k <= 1; ++k) {
                        class_2338 class_23383 = class_23382.method_10069(i, k, j);
                        if (!class_7462.method_37908().method_8320(class_23383).method_27852(class_2246.field_10504) || !class_7462.method_37908().method_22347(class_23382.method_10069(i / 2, k, j / 2))) continue;
                        double d = (double)class_23383.method_10263() + class_7462.method_59922().method_43058();
                        double d2 = (double)class_23383.method_10264() + class_7462.method_59922().method_43058();
                        double d3 = (double)class_23383.method_10260() + class_7462.method_59922().method_43058();
                        InHandBlockEntityRenderer.spawnHandParticle(class_2432, new class_243(d, d2, d3));
                        bl = true;
                    }
                }
            }
        }
        if (heldBookState.lastFlipTick != l) {
            heldBookState.lastFlipTick = l;
            class_26052.field_11969 = bl && heldBookState.shouldOpen(l) ? (class_26052.field_11969 += (class_7462.method_59922().method_43057() - 0.5f) * 0.6f) : (class_26052.field_11969 *= 0.6f);
            float f2 = class_26052.field_11969 - class_26052.field_11958;
            class_26052.field_11967 += f2 * 0.4f;
            class_26052.field_11967 *= 0.9f;
            class_26052.field_11958 += class_26052.field_11967;
        }
        ++class_26052.field_11961;
    }

    private static void resetEnchantingBookState(class_2605 class_26052) {
        if (class_26052 != null) {
            class_26052.field_11966 = 0.0f;
            class_26052.field_11965 = 0.0f;
            class_26052.field_11964 = 0.0f;
            class_26052.field_11963 = 0.0f;
            class_26052.field_11958 = 0.0f;
            class_26052.field_11960 = 0.0f;
            class_26052.field_11967 = 0.0f;
            class_26052.field_11969 = 0.0f;
            class_26052.field_11962 = 0.0f;
            class_26052.field_11961 = 0;
        }
    }

    private static class_2605 ensureEnchantingTable(class_1937 class_19372, class_2338 class_23382, class_1306 class_13062) {
        class_2605 class_26052;
        if (class_23382 == null) {
            class_23382 = class_2338.field_10980;
        }
        if (class_13062 == null) {
            class_13062 = class_1306.field_6183;
        }
        if ((class_26052 = ENCHANT_ENTITIES.get(class_13062)) == null) {
            enchantingState = class_2246.field_10485.method_9564();
            class_26052 = new class_2605(class_23382, enchantingState);
            ENCHANT_ENTITIES.put(class_13062, class_26052);
        } else if (!class_23382.equals((Object)class_26052.method_11016())) {
            enchantingState = class_2246.field_10485.method_9564();
            class_2605 class_26053 = new class_2605(class_23382, enchantingState);
            class_26053.field_11961 = class_26052.field_11961;
            class_26053.field_11958 = class_26052.field_11958;
            class_26053.field_11960 = class_26052.field_11960;
            class_26053.field_11969 = class_26052.field_11969;
            class_26053.field_11967 = class_26052.field_11967;
            class_26053.field_11966 = class_26052.field_11966;
            class_26053.field_11965 = class_26052.field_11965;
            class_26053.field_11964 = class_26052.field_11964;
            class_26053.field_11963 = class_26052.field_11963;
            class_26053.field_11962 = class_26052.field_11962;
            class_26052 = class_26053;
            ENCHANT_ENTITIES.put(class_13062, class_26053);
        }
        class_26052.method_31662(class_19372);
        return class_26052;
    }

    public static void updateHeldPivot(class_746 class_7462, class_1306 class_13062, class_4587 class_45872) {
        if (class_7462 != null && class_45872 != null && class_7462.method_37908() != null) {
            class_4184 class_41842;
            class_243 class_2432;
            if (class_13062 == null) {
                class_13062 = class_1306.field_6183;
            }
            if ((class_2432 = InHandBlockEntityRenderer.resolvePoseWorld(class_45872, class_41842 = class_310.method_1551().field_1773.method_19418())) != null) {
                HELD_PIVOT_WORLD.put(class_13062, class_2432);
                HELD_PIVOT_TICK.put(class_13062, class_7462.method_37908().method_8510());
            }
        }
    }

    private static class_243 getHeldPivotWorld(class_1306 class_13062, long l) {
        class_243 class_2432;
        if (class_13062 == null) {
            class_13062 = class_1306.field_6183;
        }
        if ((class_2432 = HELD_PIVOT_WORLD.get(class_13062)) == null) {
            return null;
        }
        Long l2 = HELD_PIVOT_TICK.get(class_13062);
        return l2 != null && l2 == l ? class_2432 : class_2432;
    }

    private static class_243 resolvePoseWorld(class_4587 class_45872, class_4184 class_41842) {
        if (class_45872 != null && class_41842 != null) {
            Matrix4f matrix4f = new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
            Vector4f vector4f = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
            matrix4f.transform(vector4f);
            Vector3f vector3f = new Vector3f(vector4f.x(), vector4f.y(), vector4f.z());
            vector3f.rotate((Quaternionfc)class_41842.method_23767());
            return class_41842.method_19326().method_1031((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z());
        }
        return null;
    }

    private static class_243 resolvePoseWorld(class_4587 class_45872, class_4184 class_41842, Vector3f vector3f) {
        if (class_45872 != null && class_41842 != null && vector3f != null) {
            Matrix4f matrix4f = new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
            Vector4f vector4f = new Vector4f(vector3f.x, vector3f.y, vector3f.z, 1.0f);
            matrix4f.transform(vector4f);
            Vector3f vector3f2 = new Vector3f(vector4f.x(), vector4f.y(), vector4f.z());
            vector3f2.rotate((Quaternionfc)class_41842.method_23767());
            return class_41842.method_19326().method_1031((double)vector3f2.x(), (double)vector3f2.y(), (double)vector3f2.z());
        }
        return null;
    }

    private static Vector3f applyParticleProxy(Vector3f vector3f, class_1306 class_13062) {
        if (vector3f == null) {
            return null;
        }
        boolean bl = class_13062 == class_1306.field_6182;
        EnchantParticleProxyTuning.Profile profile = EnchantParticleProxyTuning.get(EnchantParticleProxyTuning.Kind.BOOK, bl);
        if (profile == null) {
            return vector3f;
        }
        Matrix4f matrix4f = new Matrix4f().scale(profile.scale).rotateX((float)Math.toRadians(profile.rotX)).rotateY((float)Math.toRadians(profile.rotY)).rotateZ((float)Math.toRadians(profile.rotZ)).translate(profile.offX, profile.offY, profile.offZ);
        Vector4f vector4f = new Vector4f(vector3f.x, vector3f.y, vector3f.z, 1.0f);
        matrix4f.transform(vector4f);
        return new Vector3f(vector4f.x, vector4f.y, vector4f.z);
    }

    private static void renderDebugMarker(class_4597 class_45972, class_4587 class_45872, int n) {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 != null) {
            class_45872.method_22903();
            class_45872.method_22905(0.15f, 0.15f, 0.15f);
            class_45872.method_46416(-0.5f, -0.5f, -0.5f);
            class_776 class_7762 = class_3102.method_1541();
            class_7762.method_3353(class_2246.field_10058.method_9564(), class_45872, class_45972, n, class_4608.field_21444);
            class_45872.method_22909();
        }
    }

    private static void spawnHandParticle(class_243 class_2432, class_243 class_2433) {
        if (class_2432 != null && class_2433 != null) {
            int n = 30 + (int)(Math.random() * 10.0);
            HAND_PARTICLES.add(new HandEnchantParticle(class_2433, n));
        }
    }

    private static void tickHandParticles(long l) {
        if (l != lastParticleUpdateTick) {
            lastParticleUpdateTick = l;
            Iterator<HandEnchantParticle> iterator = HAND_PARTICLES.iterator();
            while (iterator.hasNext()) {
                HandEnchantParticle handEnchantParticle = iterator.next();
                ++handEnchantParticle.age;
                if (handEnchantParticle.age < handEnchantParticle.lifetime) continue;
                iterator.remove();
            }
        }
    }

    private static void renderHandParticles(class_4597 class_45972, class_4184 class_41842, float f, int n, class_1306 class_13062, class_4587 class_45872, Vector3f vector3f) {
        if (class_41842 != null && class_45972 != null) {
            long l;
            long l2 = l = class_310.method_1551().field_1687 != null ? class_310.method_1551().field_1687.method_8510() : -1L;
            if (l >= 0L) {
                InHandBlockEntityRenderer.tickHandParticles(l);
            }
            if (!HAND_PARTICLES.isEmpty() && class_45872 != null && vector3f != null) {
                Matrix4f matrix4f = new Matrix4f((Matrix4fc)class_45872.method_23760().method_23761());
                Matrix4f matrix4f2 = new Matrix4f((Matrix4fc)matrix4f).invert();
                Quaternionf quaternionf = matrix4f.getNormalizedRotation(new Quaternionf()).invert();
                Quaternionf quaternionf2 = new Quaternionf((Quaternionfc)class_41842.method_23767()).invert();
                class_243 class_2432 = class_41842.method_19326();
                for (HandEnchantParticle handEnchantParticle : HAND_PARTICLES) {
                    class_1058 class_10582 = InHandBlockEntityRenderer.getGlyphSprite(handEnchantParticle.spriteId);
                    if (class_10582 == null) continue;
                    float f2 = (float)handEnchantParticle.age + f;
                    float f3 = f2 / (float)handEnchantParticle.lifetime;
                    f3 = class_3532.method_15363((float)f3, (float)0.0f, (float)1.0f);
                    float f4 = 1.0f - f3;
                    float f5 = 1.0f - f4;
                    f5 *= f5;
                    f5 *= f5;
                    class_243 class_2433 = handEnchantParticle.shelf.method_1020(class_2432);
                    Vector3f vector3f2 = new Vector3f((float)class_2433.field_1352, (float)class_2433.field_1351, (float)class_2433.field_1350);
                    vector3f2.rotate((Quaternionfc)quaternionf2);
                    Vector4f vector4f = new Vector4f(vector3f2.x, vector3f2.y, vector3f2.z, 1.0f);
                    matrix4f2.transform(vector4f);
                    Vector3f vector3f3 = new Vector3f(vector4f.x, vector4f.y, vector4f.z);
                    Vector3f vector3f4 = new Vector3f((Vector3fc)vector3f3).sub((Vector3fc)vector3f);
                    float f6 = vector3f.x + vector3f4.x * f4;
                    float f7 = vector3f.y + vector3f4.y * f4 - f5 * 1.2f;
                    float f8 = vector3f.z + vector3f4.z * f4;
                    class_45872.method_22903();
                    class_45872.method_46416(f6, f7, f8);
                    class_45872.method_22907(quaternionf);
                    InHandBlockEntityRenderer.renderParticleQuad(class_45972, class_45872, class_10582, 0.04f, 0xF000F0);
                    class_45872.method_22909();
                }
            }
        }
    }

    private static void renderParticleQuad(class_4597 class_45972, class_4587 class_45872, class_1058 class_10582, float f, int n) {
        float f2 = f * 0.5f;
        float f3 = class_10582.method_4594();
        float f4 = class_10582.method_4577();
        float f5 = class_10582.method_4593();
        float f6 = class_10582.method_4575();
        class_4588 class_45882 = class_45972.getBuffer(class_1921.method_23580((class_2960)class_1059.field_17898));
        class_4587.class_4665 class_46652 = class_45872.method_23760();
        class_45882.method_22918(class_46652.method_23761(), -f2, -f2, 0.0f).method_1336(255, 255, 255, 255).method_22913(f3, f6).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, 0.0f, 0.0f, 1.0f);
        class_45882.method_22918(class_46652.method_23761(), f2, -f2, 0.0f).method_1336(255, 255, 255, 255).method_22913(f4, f6).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, 0.0f, 0.0f, 1.0f);
        class_45882.method_22918(class_46652.method_23761(), f2, f2, 0.0f).method_1336(255, 255, 255, 255).method_22913(f4, f5).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, 0.0f, 0.0f, 1.0f);
        class_45882.method_22918(class_46652.method_23761(), -f2, f2, 0.0f).method_1336(255, 255, 255, 255).method_22913(f3, f5).method_22922(class_4608.field_21444).method_60803(n).method_60831(class_46652, 0.0f, 0.0f, 1.0f);
    }

    private static class_1058 getGlyphSprite(class_2960 class_29602) {
        if (class_29602 == null) {
            return null;
        }
        class_310 class_3102 = class_310.method_1551();
        if (class_3102 == null) {
            return null;
        }
        Function function = class_3102.method_1549(PARTICLE_ATLAS_ID);
        if (function == null) {
            return null;
        }
        class_1058 class_10582 = (class_1058)function.apply(class_1047.method_4539());
        class_1058 class_10583 = (class_1058)function.apply(class_29602);
        if (class_10583 != null && class_10583 != class_10582) {
            return class_10583;
        }
        class_2960 class_29603 = class_2960.method_60655((String)"minecraft", (String)"enchanted_hit");
        class_1058 class_10584 = (class_1058)function.apply(class_29603);
        return class_10584 != null && class_10584 != class_10582 ? class_10584 : null;
    }

    static class_2960 randomGlyphId() {
        int n = (int)(Math.random() * 26.0);
        char c = (char)(97 + n);
        return class_2960.method_60655((String)"minecraft", (String)("sga_" + c));
    }

    static final class HeldBookState {
        private long lastEquipTick;
        private long lastSeenTick;
        long lastParticleTick;
        float bookOffset;
        private boolean wasOnGround = true;
        long lastFlipTick = Long.MIN_VALUE;

        HeldBookState() {
        }

        void onSeen(long l) {
            if (l - this.lastSeenTick > 4L) {
                this.lastEquipTick = l;
            }
            this.lastSeenTick = l;
        }

        boolean shouldOpen(long l) {
            return l - this.lastEquipTick >= 20L;
        }

        void updateFallPhysics(boolean bl, float f, float f2) {
            float f3;
            float f4 = 0.0f;
            if (!bl) {
                f3 = class_3532.method_15363((float)(-f2), (float)0.0f, (float)0.3f);
                float f5 = f3 / 0.3f;
                float f6 = class_3532.method_15363((float)(f / 2.0f), (float)0.0f, (float)1.0f);
                f4 = 0.65f * class_3532.method_15363((float)(f5 + f6 * 0.75f), (float)0.0f, (float)1.0f);
            }
            f3 = f4 > this.bookOffset ? 0.1f : 0.06f;
            this.bookOffset = class_3532.method_16439((float)f3, (float)this.bookOffset, (float)f4);
            this.wasOnGround = bl;
        }
    }

    static final class HeldPistonState {
        boolean extended;
        float extension;
        float fallOffset;
        private long lastUpdateTime = System.nanoTime();

        HeldPistonState() {
        }

        void update(class_746 class_7462, class_1937 class_19372, boolean bl, boolean bl2, float f, float f2) {
            if (class_7462 != null && class_19372 != null) {
                long l = System.nanoTime();
                float f3 = (float)(l - this.lastUpdateTime) / 1.0E9f;
                this.lastUpdateTime = l;
                if (!(f3 <= 0.0f)) {
                    float f4;
                    float f5;
                    float f6;
                    if (f3 > 0.1f) {
                        f3 = 0.1f;
                    }
                    float f7 = f3 * 20.0f;
                    float f8 = 1.0f - (float)Math.pow(0.25, f7);
                    float f9 = 1.0f - (float)Math.pow(0.92f, f7);
                    float f10 = 1.0f - (float)Math.pow(0.65f, f7);
                    float f11 = bl ? 0.95f : 0.0f;
                    this.extension = class_3532.method_16439((float)f8, (float)this.extension, (float)f11);
                    float f12 = 0.0f;
                    if (!bl2) {
                        f6 = class_3532.method_15363((float)(-f2), (float)0.0f, (float)0.6f);
                        f5 = f6 / 0.6f;
                        f4 = class_3532.method_15363((float)(f / 6.0f), (float)0.0f, (float)1.0f);
                        f12 = 0.65f * Math.max(f5, f4) * 1.15f;
                    }
                    float f13 = f6 = f12 > this.fallOffset ? f9 : f10;
                    if (bl2 && this.fallOffset > 0.0f && f12 == 0.0f) {
                        f6 = Math.min(1.0f, f6 * 1.5f);
                    }
                    this.fallOffset = class_3532.method_16439((float)f6, (float)this.fallOffset, (float)f12);
                    if (bl != this.extended) {
                        this.extended = bl;
                        f5 = 0.3f;
                        f4 = bl ? 0.7f : 0.6f;
                        class_19372.method_8486(class_7462.method_23317(), class_7462.method_23318(), class_7462.method_23321(), bl ? class_3417.field_15134 : class_3417.field_15228, class_3419.field_15245, f5, f4, false);
                    }
                }
            }
        }
    }

    static final class HandEnchantParticle {
        final class_243 shelf;
        final int lifetime;
        final class_2960 spriteId;
        int age;

        HandEnchantParticle(class_243 class_2432, int n) {
            this.shelf = class_2432;
            this.lifetime = n;
            this.spriteId = InHandBlockEntityRenderer.randomGlyphId();
        }
    }
}

