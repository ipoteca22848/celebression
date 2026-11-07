package ru.method.celebression.punchy.client.render;

import java.util.EnumMap;

public final class EnchantParticleProxyTuning {
    private static final EnumMap<Kind, Profile> PROFILES_RIGHT = new EnumMap(Kind.class);
    private static final EnumMap<Kind, Profile> PROFILES_LEFT = new EnumMap(Kind.class);

    private EnchantParticleProxyTuning() {
    }

    public static Profile get(Kind kind, boolean bl) {
        return (bl ? PROFILES_LEFT : PROFILES_RIGHT).get((Object)kind);
    }

    public static void apply(Kind kind, boolean bl, Profile profile) {
        if (kind != null && profile != null) {
            (bl ? PROFILES_LEFT : PROFILES_RIGHT).put(kind, profile);
        }
    }

    public static void reset(Kind kind, boolean bl) {
        if (kind != null) {
            switch (kind.ordinal()) {
                case 0: {
                    Profile profile = new Profile(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
                    (bl ? PROFILES_LEFT : PROFILES_RIGHT).put(kind, profile);
                    return;
                }
            }
            throw new IllegalStateException("Unexpected enum value");
        }
    }

    static {
        PROFILES_RIGHT.put(Kind.BOOK, new Profile(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f));
        PROFILES_LEFT.put(Kind.BOOK, new Profile(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f));
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
        BOOK;

    }
}

