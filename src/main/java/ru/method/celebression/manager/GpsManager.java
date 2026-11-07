package ru.method.celebression.manager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_3532;
import net.minecraft.class_7833;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.render.MutablePoint;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class GpsManager
implements MinecraftClientAccess {
    private final List<MutablePoint> nk = new ArrayList<MutablePoint>();

    public GpsManager() {
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
    }

    public void a(MutablePoint mutablePoint) {
        this.clear();
        this.nk.add(mutablePoint);
    }

    public void b(MutablePoint mutablePoint) {
        this.nk.remove(mutablePoint);
    }

    public void clear() {
        if (!this.nk.isEmpty()) {
            this.nk.clear();
        }
    }

    public boolean isEmpty() {
        return this.nk.isEmpty();
    }

    public MutablePoint g(int n, int n2) {
        for (MutablePoint mutablePoint : this.nk) {
            if (mutablePoint.getX() != n || mutablePoint.rE() != n2) continue;
            return mutablePoint;
        }
        return null;
    }

    public MutablePoint et() {
        return this.nk.isEmpty() ? null : this.nk.getFirst();
    }

    @EventHandler
    public void onRender2D(HudRenderEvent hudRenderEvent) {
        if (Module.fullNullCheck()) {
            return;
        }
        float f = (float)aiG.method_22683().method_4486() / 2.0f;
        float f2 = (float)aiG.method_22683().method_4502() / 4.0f + 35.0f;
        float f3 = 16.0f;
        float f4 = 0.0f;
        for (MutablePoint mutablePoint : this.nk) {
            hudRenderEvent.j().method_51448().method_22903();
            double d = (double)mutablePoint.getX() - GpsManager.aiG.field_1724.method_23317();
            double d2 = (double)mutablePoint.rE() - GpsManager.aiG.field_1724.method_23321();
            float f5 = MathUtils.ax(class_3532.method_15355((float)((float)(d * d + d2 * d2))));
            float f6 = (float)(-(Math.atan2(d, d2) * 57.29577951308232)) - GpsManager.aiG.field_1773.method_19418().method_19330();
            Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ROUNDBOLD.getFont(7.0f), f5 + "m", f - Fonts.ROUNDBOLD.getWidth(f5 + "m", 7.0f) / 2.0f, f2 + f4 + 6.0f, Color.WHITE);
            hudRenderEvent.j().method_51448().method_46416(f, f2 + f4, 0.0f);
            hudRenderEvent.j().method_51448().method_22907(class_7833.field_40718.rotationDegrees(f6));
            hudRenderEvent.j().method_51448().method_46416(-f, -(f2 - f4), 0.0f);
            Render2D.drawTexture(hudRenderEvent.j().method_51448(), f - f3 / 2.0f, f2 - f3 / 2.0f - f4, f3, f3, 5.0f, Celebression.id("client/gps/arrow.png"), Color.WHITE);
            hudRenderEvent.j().method_51448().method_22909();
            f4 += 30.0f;
        }
    }

    @Generated
    public List<MutablePoint> eu() {
        return this.nk;
    }
}

