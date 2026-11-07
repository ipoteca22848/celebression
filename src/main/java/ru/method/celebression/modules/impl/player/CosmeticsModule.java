package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import net.minecraft.class_437;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.ButtonSetting;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticVisibility;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsScreen;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticsService;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SettingPersistence;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticEffects;
import ru.method.celebression.screen.clickgui.ClickGui;

public class CosmeticsModule
extends Module {
    private final ButtonSetting xz = new ButtonSetting("settings.cosmetics.open", "settings.cosmetics.open", this::hY);
    private final EnumSetting<CosmeticVisibility> xA = new EnumSetting<CosmeticVisibility>("settings.cosmetics.visibility", CosmeticVisibility.dw){

        @Override
        public void a(CosmeticVisibility cosmeticVisibility) {
            super.a(cosmeticVisibility);
            CosmeticsModule.this.ia();
        }
    };
    private final CosmeticEffects xB = new CosmeticEffects();

    public CosmeticsModule() {
        super("Cosmetics", ModuleCategory.qm);
        this.getSettings().add(this.xz);
        this.getSettings().add(this.xA);
        SettingPersistence.a(this.xB, this.xB.getSettings(), "Cosmetics");
        this.getSettings().addAll(this.xB.getSettings());
        this.ia();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Celebression.getInstance().getEventHandler().subscribe((Object)this.xB);
        this.ia();
    }

    @Override
    public void onDisable() {
        Celebression.getInstance().getEventHandler().unsubscribe((Object)this.xB);
        this.xB.shutdown();
        super.onDisable();
        this.ia();
    }

    public CosmeticEffects hX() {
        return this.xB;
    }

    private void hY() {
        class_437 class_4372;
        if (aiG == null) {
            return;
        }
        if (!this.isToggle()) {
            this.setToggled(true);
        }
        class_437 class_4373 = class_4372 = CosmeticsModule.aiG.field_1755 instanceof ClickGui ? null : CosmeticsModule.aiG.field_1755;
        if (!(CosmeticsModule.aiG.field_1755 instanceof CosmeticsScreen)) {
            aiG.method_1507((class_437)new CosmeticsScreen(this, class_4372));
        }
    }

    public void hZ() {
    }

    void ia() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return;
        }
        CosmeticsService cosmeticsService = celebression.getCosmeticsService();
        if (cosmeticsService == null) {
            return;
        }
        cosmeticsService.setEnabled(this.isToggle());
        cosmeticsService.a((CosmeticVisibility)this.xA.jT());
    }
}
