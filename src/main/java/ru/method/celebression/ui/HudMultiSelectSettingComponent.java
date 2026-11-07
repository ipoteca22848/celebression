package ru.method.celebression.ui;

import ru.method.celebression.api.setting.Setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.hud.HudManager;
import ru.method.celebression.ui.HudSettingComponent;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.setting.MultiSelectSetting;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class HudMultiSelectSettingComponent
extends HudSettingComponent {
    private final MultiSelectSetting my;
    private static final float mz = 14.0f;
    private final Map<BooleanSetting, Animation> mA = new HashMap<BooleanSetting, Animation>();

    public HudMultiSelectSettingComponent(String string, MultiSelectSetting multiSelectSetting) {
        super(string);
        this.my = multiSelectSetting;
        for (BooleanSetting booleanSetting : multiSelectSetting.jT()) {
            Animation animation = new Animation(300L, 1.0, false, Easing.aiR);
            animation.ae((Boolean)booleanSetting.jT());
            this.mA.put(booleanSetting, animation);
        }
        this.addHeight = () -> {
            float f;
            float f2 = f = this.jU != null ? this.jU.rk() : 1.0f;
            if (f <= 0.0f) {
                return Float.valueOf(0.0f);
            }
            return Float.valueOf((float)((List)multiSelectSetting.jT()).size() * 14.0f * f);
        };
        this.visible = multiSelectSetting::jS;
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        float f2 = this.jU != null ? this.jU.rk() : 1.0f;
        int n3 = Math.max(0, Math.min(255, (int)(255.0f * f2)));
        String string = "(" + this.my.ka().size() + "/" + ((List)this.my.jT()).size() + ")";
        float f3 = Fonts.REGULAR.getWidth(string, 7.5f);
        this.a(class_3322, class_1074.method_4662((String)this.my.getName(), (Object[])new Object[0]), 7.5f, this.x + 5.0f, this.y + 3.5f, Math.max(0.0f, this.width - f3 - 10.0f), this.height, ColorUtils.getTextColor(n3), n, n2, false);
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(7.5f), string, this.x + this.width - f3 - 5.0f, this.y + 3.5f, ColorUtils.getTextColor(n3));
        if (f2 <= 0.01f) {
            return;
        }
        float f4 = this.height;
        for (BooleanSetting booleanSetting : this.my.jT()) {
            Animation animation = this.mA.get(booleanSetting);
            if (animation == null) continue;
            animation.i((Boolean)booleanSetting.jT());
            Render2D.drawFont(class_3322.method_51448(), Fonts.ICONSNEW.getFont(10.0f), "H", this.x + this.width - 14.0f, this.y + f4 + 3.5f, ColorUtils.getTextColor((int)((float)n3 * animation.rk())));
            this.a(class_3322, class_1074.method_4662((String)booleanSetting.getName(), (Object[])new Object[0]), 7.5f, this.x + 6.0f, this.y + f4 + 3.5f, Math.max(0.0f, this.width - 20.0f), 14.0f, ColorUtils.getTextColor(n3), n, n2, false);
            f4 += 14.0f;
        }
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (n == 0) {
            float f;
            float f2 = f = this.jU != null ? this.jU.rk() : 1.0f;
            if (f <= 0.99f) {
                return;
            }
            float f3 = this.height;
            for (BooleanSetting booleanSetting : this.my.jT()) {
                if (MathUtils.c(this.x, this.y + f3, this.width, 14.0f, (float)d, (float)d2)) {
                    boolean bl = (Boolean)booleanSetting.jT() == false;
                    booleanSetting.a(bl);
                    HudManager hudManager = Celebression.getInstance().getHudManager();
                    if (hudManager == null) break;
                    float f4 = (float)d + 10.0f;
                    float f5 = (float)d2 - 6.0f;
                    hudManager.a(booleanSetting, bl, f4, f5);
                    break;
                }
                f3 += 14.0f;
            }
        }
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
    }

    @Override
    public void keyReleased(int n, int n2, int n3) {
    }

    @Override
    public void charTyped(char c, int n) {
    }
}
