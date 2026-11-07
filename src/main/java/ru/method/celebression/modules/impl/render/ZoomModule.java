package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class ZoomModule
extends Module {
    private static final float IB = 0.05f;
    private static final float IC = 1.0f;
    private static final float ID = 0.01f;
    private static final float IE = 1.0f;
    private final BindSetting IF = new BindSetting("settings.zoom.bind", new KeyBind(67, false));
    private final SliderSetting IG = new SliderSetting("settings.zoom.multiplier", 0.8f, 0.05f, 1.0f, 0.01f);
    private final SliderSetting IH = new SliderSetting("settings.zoom.smooth_speed", 0.89f, 0.01f, 1.0f, 0.01f);
    private float II = 0.0f;
    private float IJ = 0.0f;

    public ZoomModule() {
        super("Zoom", ModuleCategory.ql);
        this.getSettings().add(this.IF);
        this.getSettings().add(this.IG);
        this.getSettings().add(this.IH);
    }

    @Override
    public void onDisable() {
        this.II = 0.0f;
        this.IJ = 0.0f;
        super.onDisable();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (ZoomModule.fullNullCheck()) {
            this.II = 0.0f;
            this.IJ = 0.0f;
            return;
        }
        float f = this.jO() ? 1.0f : 0.0f;
        this.II = this.IJ;
        float f2 = MathUtils.k(((Float)this.IH.jT()).floatValue(), 0.01f, 1.0f);
        float f3 = (f2 - 0.01f) / 0.99f;
        float f4 = MathUtils.lerp(1.0f, 0.01f, f3);
        this.IJ = MathUtils.l(this.IJ, f, f4);
    }

    public float aa(float f) {
        if (!this.isToggled() || ZoomModule.fullNullCheck()) {
            return 1.0f;
        }
        float f2 = MathUtils.k(f, 0.0f, 1.0f);
        float f3 = MathUtils.lerp(this.II, this.IJ, f2);
        float f4 = MathUtils.k(f3, 0.0f, 1.0f);
        float f5 = MathUtils.k(((Float)this.IG.jT()).floatValue(), 0.05f, 1.0f);
        float f6 = (f5 - 0.05f) / 0.95f;
        float f7 = MathUtils.lerp(1.0f, 0.05f, f6);
        return MathUtils.lerp(1.0f, f7, f4);
    }

    private boolean jO() {
        if (ZoomModule.aiG.field_1755 != null) {
            return false;
        }
        KeyBind keyBind = (KeyBind)this.IF.jT();
        if (keyBind == null || keyBind.isEmpty()) {
            return false;
        }
        long l = aiG.method_22683().method_4490();
        if (keyBind.jW()) {
            return GLFW.glfwGetMouseButton((long)l, (int)keyBind.c()) == 1;
        }
        return GLFW.glfwGetKey((long)l, (int)keyBind.c()) == 1;
    }
}

