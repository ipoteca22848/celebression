package ru.method.celebression.punchy.client.render;

import java.util.EnumMap;

public final class ChestProxyTuning {
    private static final EnumMap<Kind, Profile> PROFILES = new EnumMap(Kind.class);

    private ChestProxyTuning() {
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
            Profile profile = switch (kind.ordinal()) {
                default -> throw new IllegalStateException("Unexpected enum value");
                case 0 -> new Profile(0.0f, 0.12f, -0.08f, 0.0f, 0.0f, 0.0f, 0.75f);
                case 1 -> new Profile(0.0f, 0.12f, -0.08f, 0.0f, 0.0f, 0.0f, 0.75f);
                case 2 -> new Profile(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.75f);
            };
            PROFILES.put(kind, profile);
        }
    }

    static {
        PROFILES.put(Kind.BOAT, new Profile(0.0f, 0.12f, -0.08f, 0.0f, 0.0f, 0.0f, 0.75f));
        PROFILES.put(Kind.RAFT, new Profile(0.0f, 0.12f, -0.08f, 0.0f, 0.0f, 0.0f, 0.75f));
        PROFILES.put(Kind.MINECART, new Profile(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.75f));
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
        BOAT,
        RAFT,
        MINECART;

    }
}

