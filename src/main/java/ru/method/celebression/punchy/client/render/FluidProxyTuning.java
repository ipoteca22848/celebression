package ru.method.celebression.punchy.client.render;

import java.util.EnumMap;

public final class FluidProxyTuning {
    private static final EnumMap<Kind, Profile> PROFILES = new EnumMap(Kind.class);

    private FluidProxyTuning() {
    }

    public static Profile get(Kind kind) {
        return PROFILES.get((Object)kind);
    }

    public static void apply(Kind kind, Profile profile) {
        if (kind != null && profile != null) {
            PROFILES.put(kind, profile);
        }
    }

    public static void reset(Kind kind) {
        if (kind != null) {
            switch (kind.ordinal()) {
                case 0: 
                case 1: 
                case 2: {
                    Profile profile = new Profile(0.0f, 0.06f, 0.0f, 0.0f, 0.0f, 0.0f, 0.58f);
                    PROFILES.put(kind, profile);
                    return;
                }
            }
            throw new IllegalStateException("Unexpected enum value");
        }
    }

    static {
        PROFILES.put(Kind.WATER, new Profile(0.0f, 0.06f, 0.0f, 0.0f, 0.0f, 0.0f, 0.58f));
        PROFILES.put(Kind.LAVA, new Profile(0.0f, 0.06f, 0.0f, 0.0f, 0.0f, 0.0f, 0.58f));
        PROFILES.put(Kind.MILK, new Profile(0.0f, 0.06f, 0.0f, 0.0f, 0.0f, 0.0f, 0.58f));
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
        WATER,
        LAVA,
        MILK;

    }
}

