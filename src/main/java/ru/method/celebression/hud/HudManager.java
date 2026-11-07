package ru.method.celebression.hud;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_332;
import net.minecraft.class_3675;
import net.minecraft.class_408;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.hud.ArmorHud;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.hud.CooldownsHud;
import ru.method.celebression.hud.CoordinatesHud;
import ru.method.celebression.hud.DynamicIslandHud;
import ru.method.celebression.hud.HelperItemsHud;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.modules.impl.hud.HudElement;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.ui.HudSettingsPanel;
import ru.method.celebression.hud.InventoryHud;
import ru.method.celebression.hud.KeyBindsHud;
import ru.method.celebression.hud.LowArmorNotificationsHud;
import ru.method.celebression.modules.impl.player.LowArmorNotifyModule;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.api.setting.MultiSelectSetting;
import ru.method.celebression.render.Point2f;
import ru.method.celebression.hud.PotionHud;
import ru.method.celebression.hud.ScoreboardHud;
import ru.method.celebression.api.setting.SettingPersistence;
import ru.method.celebression.hud.TargetHud;
import ru.method.celebression.hud.WatermarkHud;
import ru.method.celebression.modules.impl.client.UI;

public class HudManager
implements MinecraftClientAccess {
    private static final int pl = 7;
    private static final float pm = 25.0f;
    private static final float pn = 3.0f;
    private static final Color po;
    private static final Color pp;
    private static final String pq;
    private HudElement pr;
    private final List<HudElement> ps = new ArrayList<HudElement>();
    private final Map<HudElement, BooleanSetting> pt = new IdentityHashMap<HudElement, BooleanSetting>();
    private final Map<BooleanSetting, HudElement> pu = new IdentityHashMap<BooleanSetting, HudElement>();
    protected final MultiSelectSetting pv = new MultiSelectSetting("elements.settings.elements", new BooleanSetting("elements.settings.elements.targethud", true), new BooleanSetting("elements.settings.elements.coordinateshud", false), new BooleanSetting("elements.settings.elements.potionhud", true), new BooleanSetting("elements.settings.elements.cooldownshud", true), new BooleanSetting("elements.settings.elements.keybindshud", true), new BooleanSetting("elements.settings.elements.helperitemshud", false), new BooleanSetting("elements.settings.elements.armorhud", false), new BooleanSetting("elements.settings.elements.inventoryhud", true), new BooleanSetting("elements.settings.elements.lowarmornotificationshud", true), new BooleanSetting("elements.settings.elements.watermark", false), new BooleanSetting("elements.settings.elements.dynamicisland", false));
    private HudSettingsPanel gq;

    public HudElement h(int n, int n2) {
        for (int i = this.ps.size() - 1; i >= 0; --i) {
            HudElement hudElement = this.ps.get(i);
            if (hudElement == null || hudElement.bL() || !MathUtils.c(hudElement.getX(), hudElement.getY(), hudElement.getWidth(), hudElement.getHeight(), n, n2)) continue;
            return hudElement;
        }
        return null;
    }

    public boolean a(HudElement hudElement, int n, int n2) {
        if (hudElement == null) {
            return false;
        }
        return this.h(n, n2) == hudElement;
    }

    public void a(HudElement hudElement) {
        if (hudElement == null) {
            return;
        }
        if (this.ps.remove(hudElement)) {
            this.ps.add(hudElement);
            Celebression celebression = Celebression.getInstance();
            if (celebression != null && !celebression.isConfigLoading()) {
                celebression.getConfigManager().dU();
            }
        }
        aiG.execute(hudElement::bP);
    }

    public HudManager() {
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
        this.a(new TargetHud(), new PotionHud(), new CoordinatesHud(), new CooldownsHud(), new KeyBindsHud(), new HelperItemsHud(), new ArmorHud(), new InventoryHud(), new ScoreboardHud(), new LowArmorNotificationsHud(), new WatermarkHud(), new DynamicIslandHud());
        for (HudElement hudElement : this.ps) {
            SettingPersistence.a(hudElement, hudElement.getSettings(), hudElement.getName());
        }
        for (HudElement hudElement : this.ps) {
            BooleanSetting booleanSetting = this.pv.bT(this.c(hudElement));
            this.pt.put(hudElement, booleanSetting);
            if (booleanSetting == null) continue;
            this.pu.put(booleanSetting, hudElement);
        }
    }

    @EventHandler
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        if (Module.fullNullCheck()) {
            return;
        }
        HudElement hudElement = this.fm();
        if (hudElement != null && this.fk()) {
            this.a(hudRenderEvent.j(), hudElement);
        }
    }

    public void a(class_332 class_3322) {
        if (Module.fullNullCheck() || this.gq == null) {
            return;
        }
        if (!HudEditorScreen.mb()) {
            this.gq.reset();
        }
        if (this.gq.bL()) {
            this.gq = null;
            return;
        }
        this.gq.a(class_3322, this.bG(), this.bH());
    }

    public void c(HudRenderEvent hudRenderEvent) {
        for (HudElement hudElement : this.ps) {
            hudElement.onRender2DX2(hudRenderEvent);
        }
    }

    @EventHandler
    public void onMouse(EventMouse eventMouse) {
        if (!HudEditorScreen.mb() || Module.fullNullCheck()) {
            return;
        }
        if (eventMouse.getAction() == 1) {
            if (this.gq != null) {
                if (this.gq.a(this.bG(), this.bH())) {
                    this.gq.mouseClicked(this.bG(), this.bH(), eventMouse.getButton());
                    return;
                }
                this.gq.reset();
            }
            if (eventMouse.getButton() == 1 && !UI.isLinkGuiStyle()) {
                for (HudElement hudElement : this.ps) {
                    if (hudElement.bX() == null) continue;
                    hudElement.bX().reset();
                }
                float f = HudElement.a(List.of(this.pv), null, null);
                this.gq = new HudSettingsPanel(this.bG() + 3, this.bH() + 3, f, 12.5f, List.of(this.pv));
            }
        }
    }

    public boolean fk() {
        return HudEditorScreen.mb() && !this.fl();
    }

    public float i(float f, float f2) {
        float f3;
        float f4;
        float f5;
        float f6;
        float f7 = aiG.method_22683().method_4486();
        float f8 = Math.max(0.0f, f7 - f2);
        float f9 = Math.min(Math.max(f, 0.0f), f8);
        float f10 = this.fn();
        if (f10 <= 0.0f) {
            return f9;
        }
        float f11 = f9;
        float f12 = f9 + f2;
        float f13 = f9 + f2 / 2.0f;
        float f14 = f9;
        float f15 = 4.0f;
        float f16 = this.k(f11, f10);
        float f17 = Math.abs(f11 - f16);
        if (f17 <= 3.0f && f17 < f15) {
            f15 = f17;
            f14 = f16;
        }
        if ((f6 = Math.abs(f13 - (f5 = this.k(f13, f10)))) <= 3.0f && f6 < f15) {
            f15 = f6;
            f14 = f5 - f2 / 2.0f;
        }
        if ((f4 = Math.abs(f12 - (f3 = this.k(f12, f10)))) <= 3.0f && f4 < f15) {
            f14 = f3 - f2;
        }
        return Math.min(Math.max(f14, 0.0f), f8);
    }

    public float j(float f, float f2) {
        float f3;
        float f4;
        float f5;
        float f6;
        float f7 = aiG.method_22683().method_4502();
        float f8 = Math.max(0.0f, f7 - f2);
        float f9 = Math.min(Math.max(f, 0.0f), f8);
        float f10 = this.fo();
        if (f10 <= 0.0f) {
            return f9;
        }
        float f11 = f9;
        float f12 = f9 + f2;
        float f13 = f9 + f2 / 2.0f;
        float f14 = f9;
        float f15 = 4.0f;
        float f16 = this.k(f11, f10);
        float f17 = Math.abs(f11 - f16);
        if (f17 <= 3.0f && f17 < f15) {
            f15 = f17;
            f14 = f16;
        }
        if ((f6 = Math.abs(f13 - (f5 = this.k(f13, f10)))) <= 3.0f && f6 < f15) {
            f15 = f6;
            f14 = f5 - f2 / 2.0f;
        }
        if ((f4 = Math.abs(f12 - (f3 = this.k(f12, f10)))) <= 3.0f && f4 < f15) {
            f14 = f3 - f2;
        }
        return Math.min(Math.max(f14, 0.0f), f8);
    }

    public int bG() {
        return (int)(HudManager.aiG.field_1729.method_1603() * (double)aiG.method_22683().method_4486() / (double)aiG.method_22683().method_4480());
    }

    public int bH() {
        return (int)(HudManager.aiG.field_1729.method_1604() * (double)aiG.method_22683().method_4502() / (double)aiG.method_22683().method_4507());
    }

    private boolean fl() {
        long l = aiG.method_22683().method_4490();
        return class_3675.method_15987((long)l, (int)340) || class_3675.method_15987((long)l, (int)344);
    }

    private void a(class_332 class_3322, HudElement hudElement) {
        int n;
        int n2;
        boolean bl;
        float f;
        int n3;
        int n4 = aiG.method_22683().method_4486();
        int n5 = aiG.method_22683().method_4502();
        float f2 = this.fn();
        float f3 = this.fo();
        if (f2 <= 0.0f || f3 <= 0.0f) {
            return;
        }
        float f4 = Math.max(0.0f, hudElement.getX() - 25.0f);
        float f5 = Math.min((float)n4, hudElement.getX() + hudElement.getWidth() + 25.0f);
        float f6 = Math.max(0.0f, hudElement.getY() - 25.0f);
        float f7 = Math.min((float)n5, hudElement.getY() + hudElement.getHeight() + 25.0f);
        for (n3 = 0; n3 < 7; ++n3) {
            f = (float)n3 * f2;
            if (f < f4 || f > f5) continue;
            bl = n3 % 2 == 0;
            n2 = (bl ? pp : po).getRGB();
            n = Math.round(f);
            class_3322.method_25294(n, 0, n + 1, n5, n2);
        }
        for (n3 = 0; n3 < 7; ++n3) {
            f = (float)n3 * f3;
            if (f < f6 || f > f7) continue;
            bl = n3 % 2 == 0;
            n2 = (bl ? pp : po).getRGB();
            n = Math.round(f);
            class_3322.method_25294(0, n, n4, n + 1, n2);
        }
    }

    private HudElement fm() {
        if (!HudEditorScreen.mb() || Module.fullNullCheck()) {
            return null;
        }
        if (this.pr instanceof DynamicIslandHud) {
            return null;
        }
        if (this.pr != null) {
            return this.pr;
        }
        for (HudElement hudElement : this.ps) {
            if (hudElement instanceof DynamicIslandHud || !MathUtils.c(hudElement.getX(), hudElement.getY(), hudElement.getWidth(), hudElement.getHeight(), this.bG(), this.bH())) continue;
            return hudElement;
        }
        return null;
    }

    private float fn() {
        return (float)aiG.method_22683().method_4486() / 6.0f;
    }

    private float fo() {
        return (float)aiG.method_22683().method_4502() / 6.0f;
    }

    private float k(float f, float f2) {
        int n = Math.round(f / f2);
        n = Math.max(0, Math.min(6, n));
        return (float)n * f2;
    }

    public void a(BooleanSetting booleanSetting, boolean bl, float f, float f2) {
        LowArmorNotifyModule lowArmorNotifyModule;
        HudElement hudElement = this.a(booleanSetting);
        if (hudElement == null) {
            return;
        }
        hudElement.setToggled(bl);
        if (hudElement instanceof LowArmorNotificationsHud && (lowArmorNotifyModule = Celebression.getInstance().getModuleManager().getModule(LowArmorNotifyModule.class)) != null && lowArmorNotifyModule.isToggled() != bl) {
            lowArmorNotifyModule.setToggled(bl);
        }
        if (bl && !Celebression.getInstance().isConfigLoading()) {
            boolean bl2;
            float f3 = ((Point2f)hudElement.bR().jT()).getX();
            float f4 = ((Point2f)hudElement.bR().jT()).getY();
            float f5 = Math.max(1.0f, (float)aiG.method_22683().method_4486());
            float f6 = Math.max(1.0f, (float)aiG.method_22683().method_4502());
            float f7 = Math.max(32.0f, hudElement.getWidth());
            float f8 = Math.max(18.0f, hudElement.getHeight());
            float f9 = f3 * f5;
            float f10 = f4 * f6;
            float f11 = Math.max(0.0f, f5 - f7);
            float f12 = Math.max(0.0f, f6 - f8);
            boolean bl3 = f3 == 0.0f && f4 == 0.0f;
            boolean bl4 = bl2 = f9 < 0.0f || f9 > f11 || f10 < 0.0f || f10 > f12;
            if (bl3 || bl2) {
                float f13 = MathUtils.k((f5 - f7) / 2.0f, 0.0f, f11);
                float f14 = MathUtils.k((f6 - f8) / 2.0f, 0.0f, f12);
                ((Point2f)hudElement.bR().jT()).setX(f13 / f5);
                ((Point2f)hudElement.bR().jT()).setY(f14 / f6);
            }
        }
        this.bQ();
    }

    private void bQ() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.isConfigLoading()) {
            return;
        }
        celebression.getConfigManager().dU();
    }

    public BooleanSetting b(HudElement hudElement) {
        return this.pt.get(hudElement);
    }

    private HudElement a(BooleanSetting booleanSetting) {
        return this.pu.get(booleanSetting);
    }

    private String c(HudElement hudElement) {
        return "elements.settings.elements." + hudElement.getName().toLowerCase();
    }

    private void a(HudElement ... hudElementArray) {
        this.ps.addAll(List.of(hudElementArray));
    }

    public <T extends HudElement> T a(Class<T> clazz) {
        for (HudElement hudElement : this.ps) {
            if (!clazz.isInstance(hudElement)) continue;
            return (T)hudElement;
        }
        return null;
    }

    public boolean d(HudElement hudElement) {
        if (hudElement == null || hudElement.bL()) {
            return false;
        }
        BooleanSetting booleanSetting = this.b(hudElement);
        return booleanSetting == null || (Boolean)booleanSetting.jT() != false;
    }

    public boolean a(EventMouse eventMouse) {
        if (HudManager.aiG.field_1755 instanceof class_408) {
            return false;
        }
        if (eventMouse.getAction() != 1) {
            return false;
        }
        DynamicIslandHud dynamicIslandHud = this.a(DynamicIslandHud.class);
        if (dynamicIslandHud == null || !this.d(dynamicIslandHud)) {
            return false;
        }
        return dynamicIslandHud.handleScreenClick(this.bG(), this.bH(), eventMouse.getButton());
    }

    @Generated
    public HudElement fp() {
        return this.pr;
    }

    @Generated
    public List<HudElement> fq() {
        return this.ps;
    }

    @Generated
    public Map<HudElement, BooleanSetting> fr() {
        return this.pt;
    }

    @Generated
    public Map<BooleanSetting, HudElement> fs() {
        return this.pu;
    }

    @Generated
    public MultiSelectSetting ft() {
        return this.pv;
    }

    @Generated
    public HudSettingsPanel bX() {
        return this.gq;
    }

    @Generated
    public void e(HudElement hudElement) {
        this.pr = hudElement;
    }

    @Generated
    public void a(HudSettingsPanel hudSettingsPanel) {
        this.gq = hudSettingsPanel;
    }

    static {
        pq = "elements.settings.elements.";
        po = new Color(255, 255, 255, 30);
        pp = new Color(255, 255, 255, 55);
    }
}

