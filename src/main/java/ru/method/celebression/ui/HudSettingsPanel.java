package ru.method.celebression.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import net.minecraft.class_332;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.ui.HudBooleanSettingComponent;
import ru.method.celebression.ui.HudEnumSettingComponent;
import ru.method.celebression.ui.HudMultiSelectSettingComponent;
import ru.method.celebression.ui.HudSettingComponent;
import ru.method.celebression.hud.HudThemeRenderer;
import ru.method.celebression.api.setting.MultiSelectSetting;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;

public class HudSettingsPanel {
    private static final float mj = 12.0f;
    private float x;
    private float y;
    private float width;
    private float height;
    private final List<Setting<?>> mk;
    private final List<HudSettingComponent> ml = new ArrayList<HudSettingComponent>();
    private final Animation mm = new Animation(300L, 1.0, true, Easing.aiR);

    public HudSettingsPanel(float f, float f2, float f3, float f4, List<Setting<?>> list) {
        this(f, f2, f3, f4, list, null);
    }

    public HudSettingsPanel(float f, float f2, float f3, float f4, List<Setting<?>> list, List<HudSettingComponent> list2) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.mk = list;
        if (list2 != null) {
            this.ml.addAll(list2);
        }
        for (Setting<?> setting : list) {
            if (setting instanceof BooleanSetting) {
                this.ml.add(new HudBooleanSettingComponent(setting.getName(), (BooleanSetting)setting));
                continue;
            }
            if (setting instanceof EnumSetting) {
                this.ml.add(new HudEnumSettingComponent(setting.getName(), (EnumSetting)setting));
                continue;
            }
            if (!(setting instanceof MultiSelectSetting)) continue;
            this.ml.add(new HudMultiSelectSettingComponent(setting.getName(), (MultiSelectSetting)setting));
        }
        this.mm.i(true);
        this.mm.reset();
    }

    public void a(class_332 class_3322, int n, int n2) {
        float f = this.dM();
        float f2 = this.getRenderScale();
        float f3 = this.getX() + this.getWidth() / 2.0f;
        float f4 = this.getY() + f / 2.0f;
        float f5 = f3 - this.getWidth() * f2 / 2.0f;
        float f6 = f4 - f * f2 / 2.0f;
        float[] fArray = this.b(n, n2);
        int n3 = Math.round(fArray[0]);
        int n4 = Math.round(fArray[1]);
        Render2D.startScissor(class_3322, f5, f6, this.getWidth() * f2, f * f2);
        class_3322.method_51448().method_22903();
        class_3322.method_51448().method_46416(f3, f4, 0.0f);
        class_3322.method_51448().method_22905(f2, f2, 1.0f);
        class_3322.method_51448().method_46416(-f3, -f4, 0.0f);
        if (HudThemeRenderer.active()) {
            HudThemeRenderer.b(class_3322.method_51448(), this.getX(), this.getY(), this.getWidth(), f, HudThemeRenderer.f(this.getWidth(), f), 1.0f);
        } else {
            Color color = ColorUtils.getGlassColor(255);
            Render2D.drawLiquidRectHud(class_3322.method_51448(), this.getX(), this.getY(), this.getWidth(), f, new BorderRadius(12.0f), new ColorRGBA(color), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        }
        float f7 = this.y;
        for (HudSettingComponent hudSettingComponent : this.ml) {
            hudSettingComponent.setX(this.x);
            hudSettingComponent.setY(f7);
            hudSettingComponent.setWidth(this.width);
            hudSettingComponent.setHeight(this.height);
            hudSettingComponent.a(this.mm);
            hudSettingComponent.render(class_3322, n3, n4, 0.0f);
            f7 += hudSettingComponent.getHeight() + hudSettingComponent.getAddHeight().get().floatValue() + 4.5f;
        }
        class_3322.method_51448().method_22909();
        Render2D.stopScissor(class_3322);
    }

    public void reset() {
        this.mm.i(false);
    }

    public boolean bL() {
        return this.mm.ad(false) && this.mm.rk() <= 0.01f;
    }

    public void mouseClicked(double d, double d2, int n) {
        float[] fArray = this.b(d, d2);
        for (HudSettingComponent hudSettingComponent : this.ml) {
            hudSettingComponent.mouseClicked(fArray[0], fArray[1], n);
        }
    }

    public void mouseReleased(double d, double d2, int n) {
        float[] fArray = this.b(d, d2);
        for (HudSettingComponent hudSettingComponent : this.ml) {
            hudSettingComponent.mouseReleased(fArray[0], fArray[1], n);
        }
    }

    public boolean a(double d, double d2) {
        float[] fArray = this.b(d, d2);
        return fArray[0] >= this.x && fArray[0] <= this.x + this.width && fArray[1] >= this.y && fArray[1] <= this.y + this.dM();
    }

    public float dM() {
        float f = 0.0f;
        for (HudSettingComponent hudSettingComponent : this.ml) {
            if (!hudSettingComponent.getVisible().get().booleanValue()) continue;
            f += hudSettingComponent.getHeight() + hudSettingComponent.getAddHeight().get().floatValue() + 4.5f;
        }
        return f;
    }

    private float getRenderScale() {
        return Math.max(0.01f, this.mm.rk() * UI.getInterfaceScale());
    }

    private float[] b(double d, double d2) {
        float f = this.x + this.width / 2.0f;
        float f2 = this.y + this.dM() / 2.0f;
        float f3 = this.getRenderScale();
        return new float[]{(float)((double)f + (d - (double)f) / (double)f3), (float)((double)f2 + (d2 - (double)f2) / (double)f3)};
    }

    @Generated
    public float getX() {
        return this.x;
    }

    @Generated
    public float getY() {
        return this.y;
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
    public List<Setting<?>> getSettings() {
        return this.mk;
    }

    @Generated
    public List<HudSettingComponent> getComponents() {
        return this.ml;
    }

    @Generated
    public Animation dN() {
        return this.mm;
    }

    @Generated
    public void setX(float f) {
        this.x = f;
    }

    @Generated
    public void setY(float f) {
        this.y = f;
    }

    @Generated
    public void setWidth(float f) {
        this.width = f;
    }

    @Generated
    public void setHeight(float f) {
        this.height = f;
    }
}

