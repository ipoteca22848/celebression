package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.api.setting.Setting;

import com.google.gson.JsonObject;
import java.awt.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1309;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.hud.HudNotificationQueue;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.event.WorldRenderEvent;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEnums;
import ru.method.celebression.modules.impl.render.cosmetic.Naruto;
import ru.method.celebression.modules.impl.render.cosmetic.Nimb;
import ru.method.celebression.modules.impl.render.cosmetic.Raven;
import ru.method.celebression.modules.impl.render.cosmetic.Spheres;

public class CosmeticEffects
extends Module {
    final class_2960 defaultTexture = Celebression.id("client/xuynya/ghost_bloom.png");
    final BooleanSetting spheresEnabled = new BooleanSetting("cosmetic.spheres", false);
    final BooleanSetting haloEnabled = new BooleanSetting("cosmetic.halo", false);
    final BooleanSetting petEnabled = new BooleanSetting("cosmetic.pet", false);
    final BooleanSetting narutoEnabled = new BooleanSetting("cosmetic.naruto", false);
    final EnumSetting<CosmeticEnums.PetSkin> petSkin = new EnumSetting<CosmeticEnums.PetSkin>("cosmetic.pet.skin", CosmeticEnums.PetSkin.PARROT, this::petOn);
    final BooleanSetting petFirstPerson = new BooleanSetting("cosmetic.pet.first_person", true, this::petOn);
    final BindSetting ravenSceneBind = new BindSetting("cosmetic.raven.scene_bind", new KeyBind(-1, false), this::ravenUiOn);
    boolean ravenSceneRequested = false;
    final BooleanSetting haloOthers = new BooleanSetting("cosmetic.halo.others", false, this::haloOn);
    final BooleanSetting haloFirstPerson = new BooleanSetting("cosmetic.halo.first_person", true, this::haloOn);
    final SliderSetting haloDistance = new SliderSetting("cosmetic.halo.distance", 72.0f, 8.0f, 192.0f, 1.0f, this::haloOn);
    final EnumSetting<CosmeticEnums.HaloQuality> haloQuality = new EnumSetting<CosmeticEnums.HaloQuality>("cosmetic.halo.quality", CosmeticEnums.HaloQuality.MEDIUM, this::haloOn);
    final ColorSetting haloColor = new ColorSetting("cosmetic.halo.color", new Color(255, 255, 255, 255), this::haloOn);
    final BooleanSetting haloAnimate = new BooleanSetting("cosmetic.halo.animate", true, this::haloOn);
    final SliderSetting haloRadius = new SliderSetting("cosmetic.halo.radius", 0.36f, 0.18f, 0.9f, 0.01f, this::haloOn);
    final SliderSetting haloThickness = new SliderSetting("cosmetic.halo.thickness", 0.06f, 0.02f, 0.16f, 0.01f, this::haloOn);
    final SliderSetting haloYOffset = new SliderSetting("cosmetic.halo.y_offset", 0.1f, -0.6f, 0.6f, 0.01f, this::haloOn);
    final EnumSetting<CosmeticEnums.SphereMode> mode = new EnumSetting<CosmeticEnums.SphereMode>("cosmetic.sphere.mode", CosmeticEnums.SphereMode.STATIC, this::spheresOn);
    final EnumSetting<CosmeticEnums.SphereEffect> effect = new EnumSetting<CosmeticEnums.SphereEffect>("cosmetic.sphere.effect", CosmeticEnums.SphereEffect.TRAIL, this::spheresOn);
    final ColorSetting exort = new ColorSetting("cosmetic.sphere.exort", new Color(255, 200, 100, 200), this::spheresOn);
    final ColorSetting wex = new ColorSetting("cosmetic.sphere.wex", new Color(255, 150, 255, 200), this::spheresOn);
    final ColorSetting quas = new ColorSetting("cosmetic.sphere.quas", new Color(150, 150, 255, 200), this::spheresOn);
    final SliderSetting size = new SliderSetting("cosmetic.sphere.size", 0.12f, 0.05f, 0.22f, 0.01f, this::spheresOn);
    final SliderSetting radius = new SliderSetting("cosmetic.sphere.radius", 0.42f, 0.18f, 1.25f, 0.01f, this::spheresOn);
    final SliderSetting height = new SliderSetting("cosmetic.sphere.height", 0.05f, -1.2f, 3.5f, 0.01f, this::spheresOn);
    final SliderSetting speed = new SliderSetting("cosmetic.sphere.speed", 1.85f, 0.2f, 4.0f, 0.01f, () -> this.spheresOn() && this.mode.jT() != CosmeticEnums.SphereMode.STATIC);
    final SliderSetting breathe = new SliderSetting("cosmetic.sphere.breathe", 0.08f, 0.0f, 0.3f, 0.01f, this::spheresOn);
    final SliderSetting spiralHeight = new SliderSetting("cosmetic.sphere.spiral_height", 1.25f, 0.2f, 6.0f, 0.01f, () -> this.spheresOn() && (this.mode.jT() == CosmeticEnums.SphereMode.SPIRAL || this.mode.jT() == CosmeticEnums.SphereMode.SPIRAL_V2));
    final SliderSetting waveAmp = new SliderSetting("cosmetic.sphere.wave_amp", 0.22f, 0.0f, 1.2f, 0.01f, () -> this.spheresOn() && this.mode.jT() == CosmeticEnums.SphereMode.WAVES);
    final SliderSetting waveFreq = new SliderSetting("cosmetic.sphere.wave_freq", 2.4f, 0.6f, 8.0f, 0.01f, () -> this.spheresOn() && this.mode.jT() == CosmeticEnums.SphereMode.WAVES);
    final EnumSetting<CosmeticEnums.SphereEditor> sphereEditor = new EnumSetting<CosmeticEnums.SphereEditor>("cosmetic.sphere.editor", CosmeticEnums.SphereEditor.ALL, this::spheresOn);
    final SliderSetting editLR = new SliderSetting("cosmetic.sphere.edit_lr", 0.0f, -1.5f, 1.5f, 0.01f, this::spheresOn);
    final SliderSetting editFB = new SliderSetting("cosmetic.sphere.edit_fb", 0.0f, -1.5f, 1.5f, 0.01f, this::spheresOn);
    final SliderSetting trailLen = new SliderSetting("cosmetic.sphere.trail_len", 18.0f, 6.0f, 48.0f, 1.0f, () -> this.spheresOn() && this.effect.jT() != CosmeticEnums.SphereEffect.NONE);
    final SliderSetting trailPower = new SliderSetting("cosmetic.sphere.trail_power", 1.1f, 0.2f, 2.2f, 0.01f, () -> this.spheresOn() && this.effect.jT() != CosmeticEnums.SphereEffect.NONE);
    final BooleanSetting self = new BooleanSetting("cosmetic.sphere.self", true, this::spheresOn);
    final BooleanSetting others = new BooleanSetting("cosmetic.sphere.others", false, this::spheresOn);
    final BooleanSetting firstPerson = new BooleanSetting("cosmetic.sphere.first_person", true, this::spheresOn);
    final SliderSetting petRadius = new SliderSetting("cosmetic.pet.radius", 0.65f, 0.15f, 2.5f, 0.01f, this::petOn);
    final SliderSetting petHeight = new SliderSetting("cosmetic.pet.height", 0.95f, -0.5f, 3.0f, 0.01f, this::petOn);
    final SliderSetting petSpeed = new SliderSetting("cosmetic.pet.speed", 1.15f, 0.05f, 5.0f, 0.01f, this::petOn);
    final SliderSetting petScale = new SliderSetting("cosmetic.pet.scale", 0.55f, 0.15f, 2.0f, 0.01f, this::petOn);
    final SliderSetting ravenAlpha = new SliderSetting("cosmetic.raven.alpha", 210.0f, 0.0f, 255.0f, 1.0f, this::ravenUiOn);
    final ColorSetting ravenColor = new ColorSetting("cosmetic.raven.color", new Color(20, 20, 20, 255), this::ravenUiOn);
    final SliderSetting ravenGlow = new SliderSetting("cosmetic.raven.glow", 65.0f, 0.0f, 100.0f, 1.0f, this::ravenUiOn);
    final Nimb nimb = new Nimb(this);
    final Spheres spheres = new Spheres(this);
    final Raven raven = new Raven(this);
    final Naruto naruto = new Naruto(this);

    public CosmeticEffects() {
        super("CosmeticEffects", ModuleCategory.ql);
    }

    @Override
    public void onDisable() {
        this.shutdown();
        super.onDisable();
    }

    public void shutdown() {
        this.ravenSceneRequested = false;
        if (this.spheres != null) {
            this.spheres.deactivate();
        }
        if (this.nimb != null) {
            this.nimb.deactivate();
        }
        if (this.raven != null) {
            this.raven.deactivate();
        }
        if (this.naruto != null) {
            this.naruto.onDisable();
        }
    }

        @EventHandler
    public void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (CosmeticEffects.fullNullCheck()) {
            return;
        }
        boolean bl = this.spheresOn();
        boolean bl2 = this.petOn();
        if (!bl && !bl2) {
            return;
        }
        class_4587 class_45872 = matrixStackStage.l();
        class_243 class_2432 = CosmeticEffects.aiG.field_1773.method_19418().method_19326();
        class_45872.method_22903();
        class_45872.method_22904(-class_2432.field_1352, -class_2432.field_1351, -class_2432.field_1350);
        try {
            boolean bl3;
            if (bl) {
                this.spheres.onWorldRender(matrixStackStage);
            }
            if (bl2 && (!(bl3 = CosmeticEffects.aiG.field_1690.method_31044().method_31034()) || CosmeticEffects.bool(this.petFirstPerson))) {
                this.raven.onWorldRender(matrixStackStage);
            }
        }
        finally {
            class_45872.method_22909();
        }
    }

        @EventHandler
    public void onRenderWorld(WorldRenderEvent worldRenderEvent) {
        if (CosmeticEffects.fullNullCheck() || !this.haloOn()) {
            return;
        }
        WorldRenderStages.MatrixStackStage matrixStackStage = new WorldRenderStages.MatrixStackStage(worldRenderEvent.k(), worldRenderEvent.l());
        class_4587 class_45872 = matrixStackStage.l();
        class_243 class_2432 = CosmeticEffects.aiG.field_1773.method_19418().method_19326();
        class_45872.method_22903();
        class_45872.method_22904(-class_2432.field_1352, -class_2432.field_1351, -class_2432.field_1350);
        try {
            this.nimb.onWorldRender(matrixStackStage);
        }
        finally {
            class_45872.method_22909();
        }
    }

    @EventHandler
    public void onKey(KeyInputEvent keyInputEvent) {
        if (CosmeticEffects.fullNullCheck()) {
            return;
        }
        if (keyInputEvent.getAction() != 1) {
            return;
        }
        if (this.ravenUiOn() && ((KeyBind)this.ravenSceneBind.jT()).af(keyInputEvent.c())) {
            this.ravenSceneRequested = true;
            HudNotificationQueue.dG().bg("Raven Scene");
            Celebression.getInstance().getSoundManager().fF();
        }
    }

    boolean pollRavenScene() {
        if (!this.ravenSceneRequested) {
            return false;
        }
        this.ravenSceneRequested = false;
        return true;
    }

    boolean spheresOn() {
        return CosmeticEffects.bool(this.spheresEnabled);
    }

    boolean haloOn() {
        return CosmeticEffects.bool(this.haloEnabled);
    }

    boolean petOn() {
        return CosmeticEffects.bool(this.petEnabled);
    }

    public class_1309 getPreviewPetEntity() {
        if (!this.petOn() || this.raven == null) {
            return null;
        }
        return this.raven.getPreviewPetEntity();
    }

    public class_1309 getPreviewPetEntity(CosmeticEnums.PetSkin petSkin) {
        if (this.raven == null) {
            return null;
        }
        return this.raven.getPreviewPetEntity(petSkin);
    }

    public boolean isPetOn() {
        return this.petOn();
    }

    public JsonObject petSyncPayload() {
        if (!this.isToggled() || !this.petOn()) {
            return null;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("skin", ((CosmeticEnums.PetSkin)this.petSkin.jT()).name());
        jsonObject.addProperty("radius", (Number)this.petRadius.jT());
        jsonObject.addProperty("height", (Number)this.petHeight.jT());
        jsonObject.addProperty("speed", (Number)this.petSpeed.jT());
        jsonObject.addProperty("scale", (Number)this.petScale.jT());
        return jsonObject;
    }

    public JsonObject haloSyncPayload() {
        if (!this.isToggled() || !this.haloOn()) {
            return null;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("color", (Number)((Color)this.haloColor.jT()).getRGB());
        jsonObject.addProperty("radius", (Number)this.haloRadius.jT());
        jsonObject.addProperty("thickness", (Number)this.haloThickness.jT());
        jsonObject.addProperty("y_offset", (Number)this.haloYOffset.jT());
        jsonObject.addProperty("animate", Boolean.valueOf(CosmeticEffects.bool(this.haloAnimate)));
        return jsonObject;
    }

    public Nimb nimbRenderer() {
        return this.nimb;
    }

    boolean narutoOn() {
        return CosmeticEffects.bool(this.narutoEnabled);
    }

    boolean petIsBat() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.BAT;
    }

    boolean petIsParrot() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.PARROT;
    }

    boolean petIsRaven() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.RAVEN;
    }

    boolean petIsFairy() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.FAIRY;
    }

    boolean petIsBee() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.BEE;
    }

    boolean petIsVex() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.VEX;
    }

    boolean petIsFox() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.FOX;
    }

    boolean petIsPig() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.PIG;
    }

    boolean petIsFrog() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.FROG;
    }

    boolean petIsPufferfish() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.PUFFERFISH;
    }

    boolean petIsSlime() {
        return this.petSkin.jT() == CosmeticEnums.PetSkin.SLIME;
    }

    boolean ravenUiOn() {
        return this.petOn() && this.petIsRaven();
    }

    public static boolean bool(BooleanSetting booleanSetting) {
        return booleanSetting != null && Boolean.TRUE.equals(booleanSetting.jT());
    }

    public BooleanSetting spheresEnabledSetting() {
        return this.spheresEnabled;
    }

    public BooleanSetting haloEnabledSetting() {
        return this.haloEnabled;
    }

    public BooleanSetting petEnabledSetting() {
        return this.petEnabled;
    }

    public BooleanSetting narutoEnabledSetting() {
        return this.narutoEnabled;
    }

    public EnumSetting<CosmeticEnums.PetSkin> petSkinSetting() {
        return this.petSkin;
    }
}

