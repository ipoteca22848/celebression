package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.event.PlayerTickEvent;
import ru.method.celebression.api.setting.SliderSetting;
import ru.method.celebression.api.event.WorldRenderStages;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render3D;

public class JumpCirclesModule
extends Module {
    private static final class_2960 Ce = Celebression.id("client/other/circle.png");
    private static final float Cf = 1.15f;
    private static final long Cg = 500L;
    private static final long Ch = 500L;
    private static final long Ci = 600L;
    private final SliderSetting Cj = new SliderSetting("settings.jumpcircles.size", 1.5f, 1.0f, 2.0f, 0.1f);
    private final SliderSetting Ck = new SliderSetting("settings.jumpcircles.lifetime", 4.0f, 1.0f, 10.0f, 0.1f);
    private final List<Circle> Cl = new ArrayList<Circle>();
    private boolean wasOnGround;

    public JumpCirclesModule() {
        super("JumpCircles", ModuleCategory.ql);
    }

    @EventHandler
    private void onPlayerTick(PlayerTickEvent playerTickEvent) {
        if (JumpCirclesModule.fullNullCheck()) {
            return;
        }
        boolean bl = JumpCirclesModule.aiG.field_1724.method_24828();
        boolean bl2 = JumpCirclesModule.aiG.field_1690.field_1903.method_1434();
        if (this.wasOnGround && !bl && bl2) {
            class_243 class_2432 = new class_243(JumpCirclesModule.aiG.field_1724.method_23317(), (double)JumpCirclesModule.aiG.field_1724.method_24515().method_10264() + 0.01, JumpCirclesModule.aiG.field_1724.method_23321());
            this.Cl.add(new Circle(class_2432));
        }
        this.wasOnGround = bl;
    }

    @EventHandler
    private void onRender3D(WorldRenderStages.MatrixStackStage matrixStackStage) {
        if (JumpCirclesModule.fullNullCheck()) {
            return;
        }
        if (this.Cl.isEmpty()) {
            return;
        }
        long l = System.currentTimeMillis();
        Color color = ColorUtils.getGlobalColor();
        Iterator<Circle> iterator = this.Cl.iterator();
        while (iterator.hasNext()) {
            float f;
            Circle circle = iterator.next();
            long l2 = l - circle.Cn;
            long l3 = Math.max(600L, (long)(((Float)this.Ck.jT()).floatValue() * 1000.0f));
            long l4 = Math.max(0L, l3 - 500L);
            if (l2 >= l3) {
                iterator.remove();
                continue;
            }
            if (l2 >= l4 && !circle.fadingOut) {
                circle.Co.w(500L);
                circle.Co.i(false);
                circle.fadingOut = true;
            }
            if ((f = circle.Co.rk()) <= 1.0f && circle.fadingOut) continue;
            int n = class_3532.method_15340((int)Math.round(f), (int)0, (int)255);
            if (f <= 3.0f) continue;
            float f2 = Math.max(0.05f, ((Float)this.Cj.jT()).floatValue());
            Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
            Color color3 = ColorUtils.fade(color2, new Color(255, 255, 255, n), 0.35f);
            Render3D.renderOrientedTexture(circle.Cm, f2, Ce, color2.getRGB(), class_2350.field_11036, true, true);
            Render3D.renderOrientedTexture(circle.Cm, f2 * 1.15f, Ce, color3.getRGB(), class_2350.field_11036, true, true);
        }
    }

    @Override
    public void onDisable() {
        this.Cl.clear();
        super.onDisable();
    }

    static class Circle {
        final class_243 Cm;
        final long Cn;
        final Animation Co;
        boolean fadingOut;

        Circle(class_243 class_2432) {
            this.Cm = class_2432;
            this.Cn = System.currentTimeMillis();
            this.Co = new Animation(500L, 255.0, true, Easing.aiR);
        }
    }
}

