package ru.method.celebression.screen.clickgui.components.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.Generated;
import net.minecraft.class_1074;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_437;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.ButtonSetting;
import ru.method.celebression.api.setting.ColorSetting;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.module.Module;
import ru.method.celebression.api.setting.MultiSelectSetting;
import ru.method.celebression.api.setting.Setting;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.manager.SoundManager;
import ru.method.celebression.api.setting.StringSetting;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.screen.clickgui.Panel;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.screen.clickgui.components.impl.ActionButtonComponent;
import ru.method.celebression.screen.clickgui.components.impl.BindComponent;
import ru.method.celebression.screen.clickgui.components.impl.BooleanComponent;
import ru.method.celebression.screen.clickgui.components.impl.ColorComponent;
import ru.method.celebression.screen.clickgui.components.impl.DualActionButtonComponent;
import ru.method.celebression.screen.clickgui.components.impl.EnumButtonComponent;
import ru.method.celebression.screen.clickgui.components.impl.EnumComponent;
import ru.method.celebression.screen.clickgui.components.impl.ListComponent;
import ru.method.celebression.screen.clickgui.components.impl.SliderComponent;
import ru.method.celebression.screen.clickgui.components.impl.StringComponent;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class ModuleComponent
extends Component {
    private static final class_2960 UNSAFE_ICON_TEXTURE = Celebression.id("client/warning/unsafe.png");
    private final Module module;
    private final String lowerName;
    private final boolean hasSettings;
    private Panel parentPanel;
    private boolean binding;
    private long lastBindTimeMs;
    private final List<Component> components = new ArrayList<Component>();
    private final Animation alphaAnimation = new Animation(180L, 1.0, false, Easing.aiR);
    private final Animation toggleAnimation = new Animation(90L, 1.0, false, Easing.aiR);
    private final Animation showingAnimation = new Animation(180L, 1.0, false, Easing.aiR);
    private final Animation openAnimation = new Animation(180L, 1.0, false, Easing.aiR);

    public ModuleComponent(Module module) {
        super(module.getName());
        this.module = module;
        this.lowerName = module.getName().toLowerCase();
        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                this.components.add(new BooleanComponent((BooleanSetting)setting));
                continue;
            }
            if (setting instanceof SliderSetting) {
                this.components.add(new SliderComponent((SliderSetting)setting));
                continue;
            }
            if (setting instanceof EnumSetting) {
                EnumSetting enumSetting = (EnumSetting)setting;
                if ("settings.hitsound.sound".equals(enumSetting.getName())) {
                    this.components.add(new EnumButtonComponent(enumSetting));
                    continue;
                }
                this.components.add(new EnumComponent(enumSetting));
                continue;
            }
            if (setting instanceof StringSetting) {
                this.components.add(new StringComponent((StringSetting)setting));
                continue;
            }
            if (setting instanceof MultiSelectSetting) {
                this.components.add(new ListComponent((MultiSelectSetting)setting));
                continue;
            }
            if (setting instanceof BindSetting) {
                this.components.add(new BindComponent((BindSetting)setting));
                continue;
            }
            if (setting instanceof ColorSetting) {
                this.components.add(new ColorComponent((ColorSetting)setting));
                continue;
            }
            if (!(setting instanceof ButtonSetting)) continue;
            this.components.add(new ActionButtonComponent((ButtonSetting)setting));
        }
        if (module instanceof UI) {
            UI uI = (UI)module;
            this.components.add(new DualActionButtonComponent("settings.ui.apply", uI::applyScaleSetting, "settings.ui.default", uI::resetScaleSetting));
        }
        this.hasSettings = !this.components.isEmpty();
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        boolean bl = class_437.method_25443() && !this.binding && !this.module.getBind().isEmpty() && !(this.module instanceof UI) && System.currentTimeMillis() - this.lastBindTimeMs > 300L;
        boolean bl2 = MathUtils.c(this.x, this.y, this.width, this.height, n, n2);
        boolean bl3 = this.module.isUnsafe();
        float f2 = this.x + 9.1f;
        float f3 = this.y + 9.0f;
        boolean bl4 = bl3 && MathUtils.c(f2 - 5.8f, f3 - 5.8f, 11.6f, 11.6f, n, n2);
        String string = this.module.getBind().toString().replace("_", " ");
        String string2 = "Key: " + string;
        if (!bl) {
            this.alphaAnimation.i(bl2 || this.module.isToggled());
        }
        this.toggleAnimation.i(this.module.isToggled());
        this.showingAnimation.i(bl);
        int n3 = (int)(200.0f + 55.0f * this.alphaAnimation.rk());
        Color color = ColorUtils.getTextColor(n3);
        Color color2 = ColorUtils.getTextColor(255);
        Color color3 = ColorUtils.getTextColor((int)(255.0f * this.toggleAnimation.rk()));
        int n4 = Math.max(170, (int)((float)color.getAlpha() * this.showingAnimation.rm()));
        Color color4 = this.showingAnimation.rk() > 0.2f ? new Color(color.getRed(), color.getGreen(), color.getBlue(), n4) : color;
        SoundManager soundManager = Celebression.getInstance().getSoundManager();
        float f4 = soundManager != null ? soundManager.b(this.module) : 0.0f;
        class_3322.method_51448().method_22903();
        if (f4 != 0.0f) {
            class_3322.method_51448().method_46416(f4, 0.0f, 0.0f);
        }
        if (this.binding) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(8.0f), string2, this.x + 7.0f, this.y + 5.0f, color);
        } else {
            Render2D.drawFont(class_3322.method_51448(), Fonts.ICONSNEW.getFont(5.5f), "H", this.x + this.width - 20.0f, this.y + 6.5f, color3);
            float f5 = this.x + 7.0f;
            if (bl3) {
                this.drawUnsafeIcon(class_3322, f2, f3);
                f5 += 15.0f;
            }
            Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(8.0f), this.module.getDisplayName(), f5, this.y + 5.0f, color4);
            if (this.hasSettings) {
                Render2D.drawFont(class_3322.method_51448(), Fonts.ICONSNEW.getFont(8.0f), "A", this.x + this.width - 9.5f, this.y + 5.0f, color4);
            }
        }
        Render2D.drawFont(class_3322.method_51448(), Fonts.REGULAR.getFont(8.0f), string, this.x + 7.0f - (Fonts.REGULAR.getWidth(string2, 8.0f) + 10.0f) * this.showingAnimation.rm(), this.y + 5.0f, color2);
        if (bl2 && this.parentPanel != null && this.parentPanel.isMouseOverContentArea(n, n2) && !this.parentPanel.isSettingsViewActive()) {
            String string3 = bl4 ? this.module.getUnsafeDescriptionKey() : this.module.getDescription();
            Celebression.getInstance().getClickGui().setDescription(class_1074.method_4662((String)string3, (Object[])new Object[0]));
        }
        class_3322.method_51448().method_22909();
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (this.binding) {
            this.module.setBind(new KeyBind(n, true));
            this.binding = false;
            return;
        }
        boolean bl = MathUtils.c(this.x, this.y, this.width, this.height, (float)d, (float)d2);
        if (!bl) {
            return;
        }
        boolean bl2 = MathUtils.c(this.x + this.width - 12.0f, this.y, 12.0f, this.height, (float)d, (float)d2);
        if (bl2 && n == 0 && this.hasSettings) {
            this.openSettingsView();
            return;
        }
        if (n == 0) {
            this.module.toggle();
        } else if (n == 1 && this.hasSettings) {
            this.openSettingsView();
        } else if (n == 2) {
            this.binding = true;
        }
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
        if (this.binding) {
            if (n == 256 || n == 261) {
                this.module.setBind(new KeyBind(-1, false));
            } else {
                this.module.setBind(new KeyBind(n, false));
            }
            this.binding = false;
            this.lastBindTimeMs = System.currentTimeMillis();
        }
    }

    @Override
    public void keyReleased(int n, int n2, int n3) {
    }

    @Override
    public void charTyped(char c, int n) {
    }

    @Override
    public void onClose() {
        this.binding = false;
    }

    @Override
    public boolean isBinding() {
        return this.binding;
    }

    private void openSettingsView() {
        if (this.components.isEmpty()) {
            return;
        }
        if (this.parentPanel != null) {
            this.parentPanel.openModuleSettings(this);
        }
    }

    public void setParentPanel(Panel panel) {
        this.parentPanel = panel;
    }

    public Module getModule() {
        return this.module;
    }

    public boolean matchesSearch(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }
        return this.lowerName.contains(string) || this.module.getDisplayName().toLowerCase(Locale.ROOT).contains(string);
    }

    private void drawUnsafeIcon(class_332 class_3322, float f, float f2) {
        float f3 = 11.4f;
        Render2D.drawCircle(class_3322.method_51448(), f, f2, 5.65f, 28, new Color(255, 54, 54, 28));
        Render2D.drawTexture(class_3322.method_51448(), f - f3 / 2.0f, f2 - f3 / 2.0f, f3, f3, 0.0f, UNSAFE_ICON_TEXTURE, Color.WHITE);
    }

    @Generated
    public List<Component> getComponents() {
        return this.components;
    }

    @Generated
    public Animation getOpenAnimation() {
        return this.openAnimation;
    }
}

