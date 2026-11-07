package ru.method.celebression.modules.impl.hud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.hud.DynamicIslandHud;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.ui.HudActionSettingComponent;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.hud.HudManager;
import ru.method.celebression.hud.HudNotificationQueue;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.ui.HudSettingComponent;
import ru.method.celebression.ui.HudSettingsPanel;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.MultiSelectSetting;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.render.Point2f;
import ru.method.celebression.api.setting.PositionSetting;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.manager.SoundManager;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.screen.mainmenu.CustomMainMenuScreen;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;

public abstract class HudElement
extends Module {
    protected static final float fY = 6.0f;
    protected static final float fZ = 7.0f;
    protected static final float ga = 8.0f;
    protected static final float gb = 10.0f;
    protected static final float gc = 13.0f;
    protected static final float gd = 90.0f;
    protected static final float ge = 18.0f;
    protected static final float gf = 15.0f;
    private static final float gg = 3.0f;
    private static final float gh = -10.0f;
    private static final float gi = 182.0f;
    private static final float gj = 22.0f;
    private static final float gk = 22.0f;
    private static final float gl = 2.0f;
    private final PositionSetting gm = new PositionSetting("Position", new Point2f(0.0f, 0.0f));
    protected final Animation gn = new Animation(300L, 1.0, false, Easing.aiR);
    private float dragX;
    private float dragY;
    private float width;
    private float height;
    private boolean dragging;
    private boolean go;
    private final List<Setting<?>> gp = new ArrayList();
    private HudSettingsPanel gq;
    private boolean gr;
    private boolean gs;
    private static final float[] gt = new float[]{0.0f, 0.0f};

    public HudElement(String string) {
        super(string, ModuleCategory.qo);
        this.gp.add(this.gm);
        this.toggled = false;
    }

    @EventHandler
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        if (HudElement.fullNullCheck()) {
            return;
        }
        boolean bl = HudEditorScreen.mb();
        if (!bl && (this.go || this.dragging)) {
            this.go = false;
            this.dragging = false;
            if (Celebression.getInstance().getHudManager().fp() == this) {
                Celebression.getInstance().getHudManager().e(null);
            }
        }
        if (this.go) {
            if (!this.dragging && MathUtils.c(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.bG(), this.bH()) && !HudEditorScreen.g(this.bG(), this.bH())) {
                this.dragX = (float)this.bG() - this.getX();
                this.dragY = (float)this.bH() - this.getY();
                this.dragging = true;
                Celebression.getInstance().getHudManager().e(this);
            }
            if (this.dragging) {
                HudManager hudManager = Celebression.getInstance().getHudManager();
                float f = aiG.method_22683().method_4486();
                float f2 = aiG.method_22683().method_4502();
                float f3 = f - this.width;
                float f4 = f2 - this.height;
                float f5 = Math.min(Math.max((float)this.bG() - this.dragX, 0.0f), f3);
                float f6 = Math.min(Math.max((float)this.bH() - this.dragY, 0.0f), f4);
                if (hudManager != null && hudManager.fk()) {
                    f5 = Math.min(Math.max(hudManager.i(f5, this.width), 0.0f), f3);
                    f6 = Math.min(Math.max(hudManager.j(f6, this.height), 0.0f), f4);
                }
                float[] fArray = this.b(f5, f6, this.width, this.height, f, f2);
                f5 = fArray[0];
                f6 = fArray[1];
                ((Point2f)this.gm.jT()).setX(f5 / f);
                ((Point2f)this.gm.jT()).setY(f6 / f2);
            }
        } else {
            this.dragging = false;
        }
        if (this.gq != null) {
            if (!bl) {
                this.gq.reset();
            }
            if (this.gq.bL()) {
                this.gq = null;
                return;
            }
            this.gq.a(hudRenderEvent.j(), this.bG(), this.bH());
        }
    }

    @EventHandler
    public void onRender2DX2(HudRenderEvent hudRenderEvent) {
        boolean bl;
        boolean bl2 = bl = HudElement.aiG.field_1755 instanceof CustomMainMenuScreen && this instanceof DynamicIslandHud;
        if (HudElement.fullNullCheck() && !bl) {
            if (this.gr) {
                this.gr = false;
                this.bO();
            }
            return;
        }
        try {
            BooleanSetting booleanSetting;
            HudManager hudManager = Celebression.getInstance().getHudManager();
            BooleanSetting booleanSetting2 = booleanSetting = hudManager != null ? hudManager.b(this) : null;
            boolean bl3 = bl || (booleanSetting != null ? (Boolean)booleanSetting.jT() : this.isToggled());
            if (!bl3 && !this.gr && this.gn.ad(false) && this.gn.rk() <= 0.01f) {
                return;
            }
            this.gn.i(bl3);
        }
        catch (Exception exception) {
            this.gn.i(bl || this.isToggled());
        }
        if (this.gr && this.gn.ad(false) && this.gn.rk() <= 0.01f) {
            this.gr = false;
            this.bO();
        }
    }

    @EventHandler
    public void onMouse(EventMouse eventMouse) {
        if (!HudEditorScreen.mb() || HudElement.fullNullCheck()) {
            return;
        }
        HudManager hudManager = Celebression.getInstance().getHudManager();
        if (eventMouse.getAction() == 0) {
            boolean bl = this.dragging;
            this.go = false;
            this.dragging = false;
            if (hudManager != null) {
                hudManager.e(null);
            }
            if (bl) {
                this.bQ();
            }
        } else if (eventMouse.getAction() == 1) {
            boolean bl;
            boolean bl2 = MathUtils.c(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.bG(), this.bH());
            boolean bl3 = bl = hudManager == null || hudManager.a(this, this.bG(), this.bH());
            if (this.gq != null) {
                if (this.gq.a(this.bG(), this.bH())) {
                    this.gq.mouseClicked(this.bG(), this.bH(), eventMouse.getButton());
                    return;
                }
                this.gq.reset();
            }
            if (eventMouse.getButton() == 0 && bl2 && bl && (hudManager == null || hudManager.fp() == null)) {
                if (this.b(this.bG(), this.bH())) {
                    return;
                }
                this.go = true;
                if (hudManager != null) {
                    hudManager.a(this);
                }
            }
            if (eventMouse.getButton() == 2) {
                if (bl2 && bl && this.c(this.bG(), this.bH())) {
                    return;
                }
                if (bl2 && bl && hudManager != null) {
                    hudManager.a(this);
                }
            }
            if (eventMouse.getButton() == 1) {
                if (bl2 && bl && this.a(this.bG(), this.bH())) {
                    return;
                }
                if (bl2 && bl) {
                    if (hudManager != null) {
                        hudManager.a(this);
                    }
                    if (hudManager != null && hudManager.bX() != null) {
                        hudManager.bX().reset();
                    }
                    for (HudElement hudElement : Celebression.getInstance().getHudManager().fq()) {
                        if (hudElement.bX() == null) continue;
                        hudElement.bX().reset();
                    }
                    List<HudSettingComponent> list = List.of(new HudActionSettingComponent("\u0423\u0434\u0430\u043b\u0438\u0442\u044c", "W", new Color(255, 70, 70), () -> {
                        HudManager manager = Celebression.getInstance().getHudManager();
                        if (manager != null) {
                            BooleanSetting booleanSetting = manager.b(this);
                            if (booleanSetting != null) {
                                booleanSetting.a(false);
                                manager.a(booleanSetting, false, 0.0f, 0.0f);
                            } else {
                                this.setToggled(false);
                            }
                        } else {
                            this.setToggled(false);
                        }
                        if (this.gq != null) {
                            this.gq.reset();
                            this.gq = null;
                        }
                    }));
                    float f = HudElement.a(this.gp, "\u0423\u0434\u0430\u043b\u0438\u0442\u044c", "W");
                    this.gq = new HudSettingsPanel((float)this.bG() + 3.0f, (float)this.bH() + -10.0f, f, 12.5f, this.gp, list);
                }
            }
        }
    }

    public float getX() {
        return (float)aiG.method_22683().method_4486() * ((Point2f)this.gm.jT()).getX();
    }

    public float getY() {
        return (float)aiG.method_22683().method_4502() * ((Point2f)this.gm.jT()).getY();
    }

    public int bG() {
        return (int)(HudElement.aiG.field_1729.method_1603() * (double)aiG.method_22683().method_4486() / (double)aiG.method_22683().method_4480());
    }

    public int bH() {
        return (int)(HudElement.aiG.field_1729.method_1604() * (double)aiG.method_22683().method_4502() / (double)aiG.method_22683().method_4507());
    }

    public void b(float f, float f2, float f3, float f4) {
        this.width = f3;
        this.height = f4;
        ((Point2f)this.gm.jT()).setX(f / (float)aiG.method_22683().method_4486());
        ((Point2f)this.gm.jT()).setY(f2 / (float)aiG.method_22683().method_4502());
    }

    protected float bI() {
        return UI.getInterfaceScale();
    }

    protected float b(class_4587 class_45872, float f, float f2) {
        float f3 = this.bI();
        class_45872.method_22903();
        class_45872.method_46416(f, f2, 0.0f);
        class_45872.method_22905(f3, f3, 1.0f);
        class_45872.method_46416(-f, -f2, 0.0f);
        return f3;
    }

    protected void b(class_4587 class_45872) {
        class_45872.method_22909();
    }

    protected void b(float f, float f2, float f3, float f4, float f5) {
        this.b(f, f2, f3 * f5, f4 * f5);
    }

    protected float c(float f, float f2, float f3) {
        return f + (f2 - f) * f3;
    }

    protected float d(float f, float f2, float f3) {
        return f + (f2 - f) * f3;
    }

    protected float e(float f, float f2) {
        return f * f2;
    }

    protected static String a(Font font, String string, float f, float f2) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        if (font.getWidth(string, f) <= f2) {
            return string;
        }
        String string2 = "...";
        float f3 = font.getWidth(string2, f);
        if (f3 >= f2) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            stringBuilder.append(string.charAt(i));
            if (!(font.getWidth(stringBuilder.toString(), f) + f3 > f2)) continue;
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            break;
        }
        return stringBuilder.append(string2).toString();
    }

    protected void a(class_332 class_3322, Font font, float f, String string, float f2, float f3, Color color) {
        if (string == null || string.trim().isEmpty()) {
            return;
        }
        try {
            Render2D.drawFont(class_3322.method_51448(), font.getFont(f), string, f2, f3, color);
        }
        catch (Exception exception) {
        }
    }

    protected float bJ() {
        return 1.0f;
    }

    protected float[] bK() {
        return gt;
    }

    public boolean bL() {
        return this.gn.rk() <= 0.0f;
    }

    @Override
    public void onEnable() {
        this.toggled = true;
        this.gr = false;
        this.bM();
        boolean bl = Celebression.getInstance().isSuppressToggleFeedback();
        if (!bl && !HudElement.fullNullCheck()) {
            HudNotificationQueue.dG().a(this.getName(), true);
        }
        SoundManager soundManager = Celebression.getInstance().getSoundManager();
        if (!bl && soundManager != null) {
            soundManager.fF();
        }
        this.b(true);
    }

    @Override
    public void onDisable() {
        this.toggled = false;
        this.gr = true;
        boolean bl = Celebression.getInstance().isSuppressToggleFeedback();
        if (!bl && !HudElement.fullNullCheck()) {
            HudNotificationQueue.dG().a(this.getName(), false);
        }
        SoundManager soundManager = Celebression.getInstance().getSoundManager();
        if (!bl && soundManager != null) {
            soundManager.fG();
        }
        this.b(false);
    }

    private void bM() {
        if (this.gs) {
            return;
        }
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
        this.gs = true;
    }

    protected void bN() {
        this.bM();
    }

    private void bO() {
        if (!this.gs) {
            return;
        }
        Celebression.getInstance().getEventHandler().unsubscribe((Object)this);
        this.gs = false;
    }

    public void bP() {
        if (!this.gs) {
            return;
        }
        Celebression.getInstance().getEventHandler().unsubscribe((Object)this);
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
        this.gs = true;
    }

    protected boolean a(int n, int n2) {
        return false;
    }

    protected boolean b(int n, int n2) {
        return false;
    }

    protected boolean c(int n, int n2) {
        return false;
    }

    public static float a(List<Setting<?>> list, String string, String string2) {
        float f = string != null && string2 != null ? HudActionSettingComponent.e(string, string2) : 60.0f;
        for (Setting<?> setting : list) {
            String string3;
            float f2;
            String string4;
            if (!setting.jS()) continue;
            if (setting instanceof BooleanSetting) {
                BooleanSetting booleanSetting = (BooleanSetting)setting;
                string4 = class_1074.method_4662((String)booleanSetting.getName(), (Object[])new Object[0]);
                float f3 = Fonts.ROUNDBOLD.getWidth(string4, 8.0f);
                f = Math.max(f, 5.0f + f3 + 20.0f + 5.0f);
                continue;
            }
            if (setting instanceof EnumSetting) {
                EnumSetting enumSetting = (EnumSetting)setting;
                string4 = class_1074.method_4662((String)enumSetting.getName(), (Object[])new Object[0]);
                float f4 = Fonts.ROUNDBOLD.getWidth(string4, 8.0f);
                f2 = 0.0f;
                for (Enum enum_ : (Enum[])((Enum)enumSetting.jT()).getDeclaringClass().getEnumConstants()) {
                    String string5;
                    if (enum_ instanceof NamedOption) {
                        NamedOption namedOption = (NamedOption)((Object)enum_);
                        string5 = class_1074.method_4662((String)namedOption.getName(), (Object[])new Object[0]);
                    } else {
                        string5 = enum_.name();
                    }
                    string3 = string5;
                    f2 = Math.max(f2, Fonts.ROUNDBOLD.getWidth(string3, 8.0f));
                }
                f = Math.max(f, 5.0f + f4 + 8.0f + f2 + 18.0f);
                continue;
            }
            if (!(setting instanceof MultiSelectSetting)) continue;
            MultiSelectSetting multiSelectSetting = (MultiSelectSetting)setting;
            string4 = class_1074.method_4662((String)multiSelectSetting.getName(), (Object[])new Object[0]);
            String string6 = "(" + multiSelectSetting.ka().size() + "/" + ((List)multiSelectSetting.jT()).size() + ")";
            f2 = Fonts.REGULAR.getWidth(string4, 7.5f);
            float f5 = Fonts.REGULAR.getWidth(string6, 7.5f);
            f = Math.max(f, 5.0f + f2 + 5.0f + f5 + 5.0f);
            float f6 = 0.0f;
            for (BooleanSetting booleanSetting : multiSelectSetting.jT()) {
                string3 = class_1074.method_4662((String)booleanSetting.getName(), (Object[])new Object[0]);
                f6 = Math.max(f6, Fonts.REGULAR.getWidth(string3, 7.5f));
            }
            f = Math.max(f, 6.0f + f6 + 18.0f);
        }
        return Math.max(60.0f, f);
    }

    private float[] b(float f, float f2, float f3, float f4, float f5, float f6) {
        HudManager hudManager;
        float f7 = f6 - 22.0f;
        float f8 = f5 / 2.0f;
        float f9 = f8 - 91.0f;
        float f10 = f9 - 22.0f;
        float f11 = f;
        float f12 = f2;
        if (this.a(f11, f12, f3, f4, f9 - 2.0f, f7 - 2.0f, 186.0f, 26.0f) || this.a(f11, f12, f3, f4, f10 - 2.0f, f7 - 2.0f, 26.0f, 26.0f)) {
            f12 = Math.max(0.0f, f7 - 2.0f - f4);
        }
        if ((hudManager = Celebression.getInstance().getHudManager()) != null) {
            for (HudElement hudElement : hudManager.fq()) {
                float f13;
                float f14;
                float f15;
                if (hudElement == this || hudElement.bL()) continue;
                float f16 = hudElement.getWidth();
                float f17 = hudElement.getHeight();
                if (f16 <= 0.0f || f17 <= 0.0f || !this.a(f11, f12, f3, f4, f15 = hudElement.getX(), f14 = hudElement.getY(), f16, f17)) continue;
                float f18 = Math.abs(f15 - (f11 + f3)) < Math.abs(f15 + f16 - f11) ? f15 - (f11 + f3) : f15 + f16 - f11;
                float f19 = f13 = Math.abs(f14 - (f12 + f4)) < Math.abs(f14 + f17 - f12) ? f14 - (f12 + f4) : f14 + f17 - f12;
                if (Math.abs(f18) <= Math.abs(f13)) {
                    f11 = Math.min(Math.max(f11 + f18, 0.0f), f5 - f3);
                    continue;
                }
                f12 = Math.min(Math.max(f12 + f13, 0.0f), f6 - f4);
            }
        }
        return new float[]{f11, f12};
    }

    private boolean a(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        return f < f5 + f7 && f + f3 > f5 && f2 < f6 + f8 && f2 + f4 > f6;
    }

    private void b(boolean bl) {
        try {
            HudManager hudManager = Celebression.getInstance().getHudManager();
            if (hudManager == null) {
                return;
            }
            BooleanSetting booleanSetting = hudManager.b(this);
            if (booleanSetting != null && (Boolean)booleanSetting.jT() != bl) {
                booleanSetting.a(bl);
            }
        }
        catch (Exception exception) {
        }
    }

    private void bQ() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.isConfigLoading()) {
            return;
        }
        celebression.getConfigManager().dU();
    }

    @Generated
    public PositionSetting bR() {
        return this.gm;
    }

    @Generated
    public Animation bS() {
        return this.gn;
    }

    @Generated
    public float bT() {
        return this.dragX;
    }

    @Generated
    public float bU() {
        return this.dragY;
    }

    @Generated
    public float getWidth() {
        return this.width;
    }

    @Generated
    public float getHeight() {
        return this.height;
    }

    @Generated
    public boolean bV() {
        return this.dragging;
    }

    @Generated
    public boolean bW() {
        return this.go;
    }

    @Override
    @Generated
    public List<Setting<?>> getSettings() {
        return this.gp;
    }

    @Generated
    public HudSettingsPanel bX() {
        return this.gq;
    }

    @Generated
    public boolean bY() {
        return this.gr;
    }

    @Generated
    public boolean bZ() {
        return this.gs;
    }

    @Generated
    public void g(float f) {
        this.dragX = f;
    }

    @Generated
    public void h(float f) {
        this.dragY = f;
    }

    @Generated
    public void setWidth(float f) {
        this.width = f;
    }

    @Generated
    public void setHeight(float f) {
        this.height = f;
    }

    @Generated
    public void c(boolean bl) {
        this.dragging = bl;
    }

    @Generated
    public void d(boolean bl) {
        this.go = bl;
    }

    @Generated
    public void a(HudSettingsPanel hudSettingsPanel) {
        this.gq = hudSettingsPanel;
    }

    @Generated
    public void e(boolean bl) {
        this.gr = bl;
    }

    @Generated
    public void f(boolean bl) {
        this.gs = bl;
    }
}
