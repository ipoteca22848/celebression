package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.api.setting.Setting;

import ru.method.celebression.api.setting.NamedOption;

public final class CosmeticEnums {
    private CosmeticEnums() {
    }

    public static enum BodyTrailMode implements NamedOption
    {
        STATIC("cosmetic.body_trail.mode.static"),
        GRADIENT("cosmetic.body_trail.mode.gradient");

        private final String name;

        private BodyTrailMode(String string2) {
            this.name = string2;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    public static enum SphereEditor implements NamedOption
    {
        EXORT("cosmetic.sphere.editor.exort"),
        WEX("cosmetic.sphere.editor.wex"),
        QUAS("cosmetic.sphere.editor.quas"),
        ALL("cosmetic.sphere.editor.all");

        private final String name;

        private SphereEditor(String string2) {
            this.name = string2;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    public static enum SphereEffect implements NamedOption
    {
        NONE("cosmetic.sphere.effect.none"),
        TRAIL("cosmetic.sphere.effect.trail"),
        FIRE_TRAIL("cosmetic.sphere.effect.fire_trail");

        private final String name;

        private SphereEffect(String string2) {
            this.name = string2;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    public static enum SphereMode implements NamedOption
    {
        STATIC("cosmetic.sphere.mode.static"),
        ORBIT("cosmetic.sphere.mode.orbit"),
        SPIRAL("cosmetic.sphere.mode.spiral"),
        SPIRAL_V2("cosmetic.sphere.mode.spiral_v2"),
        WAVES("cosmetic.sphere.mode.waves");

        private final String name;

        private SphereMode(String string2) {
            this.name = string2;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    public static enum HaloQuality implements NamedOption
    {
        LOW("cosmetic.halo.quality.low"),
        MEDIUM("cosmetic.halo.quality.medium"),
        HIGH("cosmetic.halo.quality.high"),
        ULTRA("cosmetic.halo.quality.ultra");

        private final String name;

        private HaloQuality(String string2) {
            this.name = string2;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    public static enum PetSkin implements NamedOption
    {
        BAT("cosmetic.pet.bat"),
        PARROT("cosmetic.pet.parrot"),
        RAVEN("cosmetic.pet.raven"),
        FAIRY("cosmetic.pet.fairy"),
        BEE("cosmetic.pet.bee"),
        VEX("cosmetic.pet.vex"),
        FOX("cosmetic.pet.fox"),
        PIG("cosmetic.pet.pig"),
        FROG("cosmetic.pet.frog"),
        PUFFERFISH("cosmetic.pet.pufferfish"),
        SLIME("cosmetic.pet.slime");

        private final String name;

        private PetSkin(String string2) {
            this.name = string2;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}

