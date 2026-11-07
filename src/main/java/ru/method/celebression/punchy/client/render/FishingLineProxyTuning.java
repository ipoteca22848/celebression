package ru.method.celebression.punchy.client.render;

import java.util.EnumMap;

public final class FishingLineProxyTuning {
    private static final EnumMap<Kind, Profile> RIGHT = new EnumMap(Kind.class);
    private static final EnumMap<Kind, Profile> LEFT = new EnumMap(Kind.class);

    private FishingLineProxyTuning() {
    }

    public static Profile get(Kind kind, boolean bl) {
        return bl ? LEFT.get((Object)kind) : RIGHT.get((Object)kind);
    }

    public static void apply(Kind kind, boolean bl, Profile profile) {
        if (kind != null && profile != null) {
            if (bl) {
                LEFT.put(kind, profile);
            } else {
                RIGHT.put(kind, profile);
            }
        }
    }

    public static void reset(Kind kind, boolean bl) {
        if (kind != null) {
            Profile profile = new Profile(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
            FishingLineProxyTuning.apply(kind, bl, bl ? FishingLineProxyTuning.mirror(profile) : profile);
        }
    }

    public static Profile mirror(Profile profile) {
        return new Profile(-profile.offX, profile.offY, profile.offZ, profile.rotX, -profile.rotY, -profile.rotZ, profile.scale);
    }

    static {
        Profile profile = new Profile(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
        RIGHT.put(Kind.ROD, profile);
        LEFT.put(Kind.ROD, FishingLineProxyTuning.mirror(profile));
    }

    public static final class Profile {
        public float offX;
        public float offY;
        public float offZ;
        public float rotX;
        public float rotY;
        public float rotZ;
        public float scale;

        public Profile(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            this.offX = f;
            this.offY = f2;
            this.offZ = f3;
            this.rotX = f4;
            this.rotY = f5;
            this.rotZ = f6;
            this.scale = f7;
        }
    }

    public static enum Kind {
        ROD;

    }
}

