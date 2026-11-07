package ru.method.celebression.punchy.client.renderer.layer;

public final class ArmMeshTuning {
    private static final Profile DEFAULT = new Profile(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    private static final Profile RIGHT_PROFILE = DEFAULT.copy();
    private static final Profile LEFT_PROFILE = RIGHT_PROFILE.mirror();

    private ArmMeshTuning() {
    }

    public static void resetDefaults() {
        ArmMeshTuning.RIGHT_PROFILE.scale = ArmMeshTuning.DEFAULT.scale;
        ArmMeshTuning.RIGHT_PROFILE.offX = ArmMeshTuning.DEFAULT.offX;
        ArmMeshTuning.RIGHT_PROFILE.offY = ArmMeshTuning.DEFAULT.offY;
        ArmMeshTuning.RIGHT_PROFILE.offZ = ArmMeshTuning.DEFAULT.offZ;
        ArmMeshTuning.RIGHT_PROFILE.rotX = ArmMeshTuning.DEFAULT.rotX;
        ArmMeshTuning.RIGHT_PROFILE.rotY = ArmMeshTuning.DEFAULT.rotY;
        ArmMeshTuning.RIGHT_PROFILE.rotZ = ArmMeshTuning.DEFAULT.rotZ;
        ArmMeshTuning.LEFT_PROFILE.scale = ArmMeshTuning.RIGHT_PROFILE.scale;
        ArmMeshTuning.LEFT_PROFILE.offX = -ArmMeshTuning.RIGHT_PROFILE.offX;
        ArmMeshTuning.LEFT_PROFILE.offY = ArmMeshTuning.RIGHT_PROFILE.offY;
        ArmMeshTuning.LEFT_PROFILE.offZ = ArmMeshTuning.RIGHT_PROFILE.offZ;
        ArmMeshTuning.LEFT_PROFILE.rotX = ArmMeshTuning.RIGHT_PROFILE.rotX;
        ArmMeshTuning.LEFT_PROFILE.rotY = -ArmMeshTuning.RIGHT_PROFILE.rotY;
        ArmMeshTuning.LEFT_PROFILE.rotZ = -ArmMeshTuning.RIGHT_PROFILE.rotZ;
    }

    public static Profile getGlobal(boolean bl) {
        return bl ? LEFT_PROFILE : RIGHT_PROFILE;
    }

    public static void applyGlobal(boolean bl, Profile profile) {
        if (profile != null) {
            Profile profile2 = ArmMeshTuning.getGlobal(bl);
            profile2.scale = profile.scale;
            profile2.offX = profile.offX;
            profile2.offY = profile.offY;
            profile2.offZ = profile.offZ;
            profile2.rotX = profile.rotX;
            profile2.rotY = profile.rotY;
            profile2.rotZ = profile.rotZ;
        }
    }

    public static Profile snapshotGlobal(boolean bl) {
        Profile profile = ArmMeshTuning.getGlobal(bl);
        return new Profile(profile.scale, profile.offX, profile.offY, profile.offZ, profile.rotX, profile.rotY, profile.rotZ);
    }

    public static void resetGlobal(boolean bl) {
        ArmMeshTuning.applyGlobal(bl, DEFAULT);
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

        public Profile copy() {
            return new Profile(this.scale, this.offX, this.offY, this.offZ, this.rotX, this.rotY, this.rotZ);
        }

        public Profile mirror() {
            return new Profile(this.scale, -this.offX, this.offY, this.offZ, this.rotX, -this.rotY, -this.rotZ);
        }
    }
}

