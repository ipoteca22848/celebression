package ru.method.celebression.punchy.client.renderer.layer;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import ru.method.celebression.punchy.client.renderer.layer.PlayerArmModelLayer;

public final class ArmToolTuning {
    private static Profile RIGHT_GLOBAL;
    private static Profile LEFT_GLOBAL;
    private static final EnumMap<PlayerArmModelLayer.ToolKind, Profile> PROFILES_RIGHT;
    private static final EnumMap<PlayerArmModelLayer.ToolKind, Profile> PROFILES_LEFT;
    private static final EnumMap<PlayerArmModelLayer.ToolKind, TransformOverride> OVERRIDES_RIGHT;
    private static final EnumMap<PlayerArmModelLayer.ToolKind, TransformOverride> OVERRIDES_LEFT;
    private static final Map<class_2960, SpecificEntry> ITEM_SPECIFICS;
    private static final EnumMap<PlayerArmModelLayer.ToolKind, Boolean> ATTACHED_RIGHT;
    private static final EnumMap<PlayerArmModelLayer.ToolKind, Boolean> ATTACHED_LEFT;

    private ArmToolTuning() {
    }

    public static void resetDefaults() {
        RIGHT_GLOBAL = new Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        LEFT_GLOBAL = RIGHT_GLOBAL.mirror();
        PROFILES_RIGHT.clear();
        PROFILES_LEFT.clear();
        OVERRIDES_RIGHT.clear();
        OVERRIDES_LEFT.clear();
        ATTACHED_RIGHT.clear();
        ATTACHED_LEFT.clear();
        for (PlayerArmModelLayer.ToolKind toolKind : PlayerArmModelLayer.ToolKind.values()) {
            PROFILES_RIGHT.put(toolKind, ArmToolTuning.copyProfile(RIGHT_GLOBAL));
            PROFILES_LEFT.put(toolKind, ArmToolTuning.copyProfile(LEFT_GLOBAL));
            OVERRIDES_RIGHT.put(toolKind, new TransformOverride());
            OVERRIDES_LEFT.put(toolKind, new TransformOverride());
            ATTACHED_RIGHT.put(toolKind, true);
            ATTACHED_LEFT.put(toolKind, true);
        }
        ITEM_SPECIFICS.clear();
    }

    public static boolean isKindAttached(PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        return toolKind == null ? true : (bl ? ATTACHED_LEFT : ATTACHED_RIGHT).getOrDefault((Object)toolKind, true);
    }

    public static void markKindDetached(PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        if (toolKind != null) {
            (bl ? ATTACHED_LEFT : ATTACHED_RIGHT).put(toolKind, false);
        }
    }

    public static void markKindAttached(PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        if (toolKind != null) {
            (bl ? ATTACHED_LEFT : ATTACHED_RIGHT).put(toolKind, true);
            ArmToolTuning.refreshKind(toolKind, bl);
        }
    }

    public static Profile get(PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        return toolKind == null ? ArmToolTuning.getGlobal(bl) : (bl ? PROFILES_LEFT : PROFILES_RIGHT).get((Object)toolKind);
    }

    public static Profile getGlobal(boolean bl) {
        return bl ? LEFT_GLOBAL : RIGHT_GLOBAL;
    }

    public static void apply(PlayerArmModelLayer.ToolKind toolKind, boolean bl, Profile profile) {
        if (profile != null) {
            if (toolKind == null) {
                ArmToolTuning.applyGlobal(bl, profile);
            } else {
                Profile profile2 = ArmToolTuning.get(toolKind, bl);
                if (profile2 != null) {
                    profile2.copyFrom(profile);
                    ArmToolTuning.commitKind(toolKind, bl);
                }
            }
        }
    }

    public static void applyGlobal(boolean bl, Profile profile) {
        if (profile != null) {
            ArmToolTuning.getGlobal(bl).copyFrom(profile);
            ArmToolTuning.refresh(bl);
        }
    }

    public static void commitGlobal(boolean bl) {
        ArmToolTuning.refresh(bl);
    }

    public static Profile snapshot(PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        Profile profile = ArmToolTuning.get(toolKind, bl);
        return ArmToolTuning.copyProfile(profile);
    }

    public static Profile snapshotGlobal(boolean bl) {
        return ArmToolTuning.snapshot(null, bl);
    }

    private static Profile copyProfile(Profile profile) {
        return new Profile(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ);
    }

    public static void commitKind(PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        Profile profile = ArmToolTuning.getGlobal(bl);
        Profile profile2 = ArmToolTuning.get(toolKind, bl);
        TransformOverride transformOverride = ArmToolTuning.getOverride(toolKind, bl);
        transformOverride.scale = profile2.scale - profile.scale;
        transformOverride.offX = profile2.offX - profile.offX;
        transformOverride.offY = profile2.offY - profile.offY;
        transformOverride.offZ = profile2.offZ - profile.offZ;
        transformOverride.rotX = profile2.rotX;
        transformOverride.rotY = profile2.rotY;
        transformOverride.rotZ = profile2.rotZ;
    }

    private static TransformOverride getOverride(PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        return (bl ? OVERRIDES_LEFT : OVERRIDES_RIGHT).get((Object)toolKind);
    }

    private static void refresh(boolean bl) {
        for (PlayerArmModelLayer.ToolKind toolKind : PlayerArmModelLayer.ToolKind.values()) {
            ArmToolTuning.refreshKind(toolKind, bl);
        }
    }

    private static void refreshKind(PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        Profile profile = ArmToolTuning.get(toolKind, bl);
        Profile profile2 = ArmToolTuning.getGlobal(bl);
        if (profile != null && profile2 != null) {
            if (ArmToolTuning.isKindAttached(toolKind, bl)) {
                profile.copyFrom(profile2);
            } else {
                TransformOverride transformOverride = ArmToolTuning.getOverride(toolKind, bl);
                if (transformOverride != null) {
                    profile.scale = profile2.scale + transformOverride.scale;
                    profile.offX = profile2.offX + transformOverride.offX;
                    profile.offY = profile2.offY + transformOverride.offY;
                    profile.offZ = profile2.offZ + transformOverride.offZ;
                    profile.rotX = transformOverride.rotX;
                    profile.rotY = transformOverride.rotY;
                    profile.rotZ = transformOverride.rotZ;
                }
            }
        }
    }

    private static void refreshSpecifics(boolean bl) {
        ITEM_SPECIFICS.values().forEach(specificEntry -> ArmToolTuning.refreshSpecificEntry(specificEntry, bl));
    }

    private static void refreshSpecifics(PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        ITEM_SPECIFICS.values().forEach(specificEntry -> {
            if (specificEntry != null && specificEntry.kind == toolKind) {
                ArmToolTuning.refreshSpecificEntry(specificEntry, bl);
            }
        });
    }

    private static Profile getSpecificBase(boolean bl) {
        Profile profile = ArmToolTuning.getGlobal(bl);
        return profile == null ? new Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f) : new Profile(profile.scale, profile.offX, profile.offY, profile.offZ, 0.0f, 0.0f, 0.0f);
    }

    private static void refreshSpecificEntry(SpecificEntry specificEntry, boolean bl) {
        Profile profile;
        Profile profile2;
        if (specificEntry != null && (profile2 = specificEntry.get(bl)) != null && (profile = ArmToolTuning.getSpecificBase(bl)) != null) {
            TransformOverride transformOverride = specificEntry.getOverride(bl);
            profile2.scale = profile.scale + transformOverride.scale;
            profile2.offX = profile.offX + transformOverride.offX;
            profile2.offY = profile.offY + transformOverride.offY;
            profile2.offZ = profile.offZ + transformOverride.offZ;
            profile2.rotX = transformOverride.rotX;
            profile2.rotY = transformOverride.rotY;
            profile2.rotZ = transformOverride.rotZ;
        }
    }

    public static void commitSpecific(class_2960 class_29602, boolean bl) {
        Profile profile;
        Profile profile2;
        SpecificEntry specificEntry;
        if (class_29602 != null && (specificEntry = ITEM_SPECIFICS.get(class_29602)) != null && (profile2 = specificEntry.get(bl)) != null && (profile = ArmToolTuning.getSpecificBase(bl)) != null) {
            TransformOverride transformOverride = specificEntry.getOverride(bl);
            transformOverride.scale = profile2.scale - profile.scale;
            transformOverride.offX = profile2.offX - profile.offX;
            transformOverride.offY = profile2.offY - profile.offY;
            transformOverride.offZ = profile2.offZ - profile.offZ;
            transformOverride.rotX = profile2.rotX;
            transformOverride.rotY = profile2.rotY;
            transformOverride.rotZ = profile2.rotZ;
            ArmToolTuning.refreshSpecificEntry(specificEntry, bl);
            ArmToolTuning.pruneSpecificEntry(class_29602);
        }
    }

    private static class_2960 idFor(class_1799 class_17992) {
        return class_17992 != null && !class_17992.method_7960() ? class_7923.field_41178.method_10221(class_17992.method_7909()) : null;
    }

    public static Profile resolveProfile(class_1799 class_17992, PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        Profile profile;
        SpecificEntry specificEntry;
        class_2960 class_29602 = ArmToolTuning.idFor(class_17992);
        if (class_29602 != null && (specificEntry = ITEM_SPECIFICS.get(class_29602)) != null && (profile = specificEntry.get(bl)) != null) {
            ArmToolTuning.refreshSpecificEntry(specificEntry, bl);
            return profile;
        }
        return ArmToolTuning.get(toolKind, bl);
    }

    public static boolean hasSpecificProfile(class_1799 class_17992, boolean bl) {
        class_2960 class_29602 = ArmToolTuning.idFor(class_17992);
        if (class_29602 == null) {
            return false;
        }
        SpecificEntry specificEntry = ITEM_SPECIFICS.get(class_29602);
        return specificEntry != null && specificEntry.get(bl) != null;
    }

    public static void applySpecific(class_2960 class_29603, PlayerArmModelLayer.ToolKind toolKind, Profile profile, Profile profile2) {
        if (class_29603 != null && toolKind != null) {
            if (profile == null && profile2 == null) {
                ITEM_SPECIFICS.remove(class_29603);
            } else {
                Profile profile3;
                SpecificEntry specificEntry = ITEM_SPECIFICS.computeIfAbsent(class_29603, class_29602 -> new SpecificEntry(toolKind));
                specificEntry.kind = toolKind;
                specificEntry.right = profile != null ? ArmToolTuning.copyProfile(profile) : null;
                Profile profile4 = specificEntry.left = profile2 != null ? ArmToolTuning.copyProfile(profile2) : null;
                if (specificEntry.right != null) {
                    profile3 = ArmToolTuning.getSpecificBase(false);
                    if (profile3 != null) {
                        specificEntry.rightOverride.scale = specificEntry.right.scale - profile3.scale;
                        specificEntry.rightOverride.offX = specificEntry.right.offX - profile3.offX;
                        specificEntry.rightOverride.offY = specificEntry.right.offY - profile3.offY;
                        specificEntry.rightOverride.offZ = specificEntry.right.offZ - profile3.offZ;
                        specificEntry.rightOverride.rotX = specificEntry.right.rotX;
                        specificEntry.rightOverride.rotY = specificEntry.right.rotY;
                        specificEntry.rightOverride.rotZ = specificEntry.right.rotZ;
                    }
                    ArmToolTuning.refreshSpecificEntry(specificEntry, false);
                }
                if (specificEntry.left != null) {
                    profile3 = ArmToolTuning.getSpecificBase(true);
                    if (profile3 != null) {
                        specificEntry.leftOverride.scale = specificEntry.left.scale - profile3.scale;
                        specificEntry.leftOverride.offX = specificEntry.left.offX - profile3.offX;
                        specificEntry.leftOverride.offY = specificEntry.left.offY - profile3.offY;
                        specificEntry.leftOverride.offZ = specificEntry.left.offZ - profile3.offZ;
                        specificEntry.leftOverride.rotX = specificEntry.left.rotX;
                        specificEntry.leftOverride.rotY = specificEntry.left.rotY;
                        specificEntry.leftOverride.rotZ = specificEntry.left.rotZ;
                    }
                    ArmToolTuning.refreshSpecificEntry(specificEntry, true);
                }
                ArmToolTuning.pruneSpecificEntry(class_29603);
            }
        }
    }

    public static Profile getSpecificProfile(class_2960 class_29603, PlayerArmModelLayer.ToolKind toolKind, boolean bl) {
        Profile profile;
        if (class_29603 == null) {
            return null;
        }
        SpecificEntry specificEntry = ITEM_SPECIFICS.computeIfAbsent(class_29603, class_29602 -> new SpecificEntry(toolKind));
        if (specificEntry.kind == null) {
            specificEntry.kind = toolKind;
        }
        if ((profile = specificEntry.get(bl)) == null) {
            Profile profile2 = toolKind != null ? ArmToolTuning.snapshot(toolKind, bl) : ArmToolTuning.getSpecificBase(bl);
            profile = profile2 != null ? ArmToolTuning.copyProfile(profile2) : ArmToolTuning.copyProfile(ArmToolTuning.getSpecificBase(bl));
            specificEntry.set(bl, profile);
            ArmToolTuning.commitSpecific(class_29603, bl);
        } else {
            ArmToolTuning.refreshSpecificEntry(specificEntry, bl);
        }
        return profile;
    }

    public static Map<class_2960, SpecificSnapshot> snapshotSpecifics() {
        HashMap<class_2960, SpecificSnapshot> hashMap = new HashMap<class_2960, SpecificSnapshot>();
        ITEM_SPECIFICS.forEach((class_29602, specificEntry) -> {
            ArmToolTuning.refreshSpecificEntry(specificEntry, false);
            ArmToolTuning.refreshSpecificEntry(specificEntry, true);
            hashMap.put((class_2960)class_29602, new SpecificSnapshot(specificEntry.kind, specificEntry.right != null ? ArmToolTuning.copyProfile(specificEntry.right) : null, specificEntry.left != null ? ArmToolTuning.copyProfile(specificEntry.left) : null));
        });
        return hashMap;
    }

    public static void clearSpecificOverride(class_2960 class_29602) {
        if (class_29602 != null) {
            ITEM_SPECIFICS.remove(class_29602);
        }
    }

    public static void clearSpecifics() {
        ITEM_SPECIFICS.clear();
    }

    private static void pruneSpecificEntry(class_2960 class_29602) {
        SpecificEntry specificEntry = ITEM_SPECIFICS.get(class_29602);
        if (specificEntry != null && specificEntry.right == null && specificEntry.left == null) {
            ITEM_SPECIFICS.remove(class_29602);
        }
    }

    private static boolean profileMatchesBase(Profile profile, Profile profile2) {
        return profile != null && profile2 != null ? ArmToolTuning.nearlyEqual(profile.scale, profile2.scale) && ArmToolTuning.nearlyEqual(profile.offX, profile2.offX) && ArmToolTuning.nearlyEqual(profile.offY, profile2.offY) && ArmToolTuning.nearlyEqual(profile.offZ, profile2.offZ) && ArmToolTuning.nearlyEqual(profile.rotX, profile2.rotX) && ArmToolTuning.nearlyEqual(profile.rotY, profile2.rotY) && ArmToolTuning.nearlyEqual(profile.rotZ, profile2.rotZ) : false;
    }

    private static boolean nearlyEqual(float f, float f2) {
        return Math.abs(f - f2) <= 1.0E-6f;
    }

    static {
        PROFILES_RIGHT = new EnumMap(PlayerArmModelLayer.ToolKind.class);
        PROFILES_LEFT = new EnumMap(PlayerArmModelLayer.ToolKind.class);
        OVERRIDES_RIGHT = new EnumMap(PlayerArmModelLayer.ToolKind.class);
        OVERRIDES_LEFT = new EnumMap(PlayerArmModelLayer.ToolKind.class);
        ITEM_SPECIFICS = new HashMap<class_2960, SpecificEntry>();
        ATTACHED_RIGHT = new EnumMap(PlayerArmModelLayer.ToolKind.class);
        ATTACHED_LEFT = new EnumMap(PlayerArmModelLayer.ToolKind.class);
        ArmToolTuning.resetDefaults();
    }

    public static final class Profile {
        public float scale;
        public float offX;
        public float offY;
        public float offZ;
        public float rotX;
        public float rotY;
        public float rotZ;

        public Profile(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            this.scale = f;
            this.offX = f2;
            this.offY = f3;
            this.offZ = f4;
            this.rotX = f5;
            this.rotY = f6;
            this.rotZ = f7;
        }

        public Profile mirror() {
            return new Profile(this.scale, -this.offX, this.offY, this.offZ, this.rotX, -this.rotY, -this.rotZ);
        }

        public void copyFrom(Profile profile) {
            this.scale = profile.scale;
            this.offX = profile.offX;
            this.offY = profile.offY;
            this.offZ = profile.offZ;
            this.rotX = profile.rotX;
            this.rotY = profile.rotY;
            this.rotZ = profile.rotZ;
        }
    }

    static final class TransformOverride {
        float scale;
        float offX;
        float offY;
        float offZ;
        float rotX;
        float rotY;
        float rotZ;

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
        }
    }

    public static final class SpecificEntry {
        PlayerArmModelLayer.ToolKind kind;
        Profile right;
        Profile left;
        TransformOverride rightOverride = new TransformOverride();
        TransformOverride leftOverride = new TransformOverride();

        SpecificEntry(PlayerArmModelLayer.ToolKind toolKind) {
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

        TransformOverride getOverride(boolean bl) {
            return bl ? this.leftOverride : this.rightOverride;
        }

        public PlayerArmModelLayer.ToolKind kind() {
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
        private final PlayerArmModelLayer.ToolKind kind;
        private final Profile right;
        private final Profile left;

        public SpecificSnapshot(PlayerArmModelLayer.ToolKind toolKind, Profile profile, Profile profile2) {
            this.kind = toolKind;
            this.right = profile;
            this.left = profile2;
        }

        public PlayerArmModelLayer.ToolKind kind() {
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
