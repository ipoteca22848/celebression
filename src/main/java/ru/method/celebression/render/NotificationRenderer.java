package ru.method.celebression.render;

import java.awt.Color;
import lombok.Generated;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.ui.NotificationIcon;
import ru.method.celebression.animation.SmoothedVector;
import ru.method.celebression.utils.render.BorderRadius;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class NotificationRenderer
implements MinecraftClientAccess {
    private final NotificationIcon akI;
    private final String akJ;
    private final long akK;
    private float y;
    private final Animation akL = new Animation(300L, 1.0, true, Easing.aiR);
    private final SmoothedVector akM = new SmoothedVector();

    public NotificationRenderer(NotificationIcon notificationIcon, String string, long l) {
        this.akI = notificationIcon;
        this.akJ = string;
        this.akK = l;
        this.y = aiG.method_22683().method_4502();
        this.akM.reset();
    }

    public void a(HudRenderEvent hudRenderEvent, float f) {
        this.y = this.z(this.y, f);
        float f2 = Fonts.MEDIUM.getWidth(this.akJ, 9.0f);
        float f3 = Fonts.ICONSNEW.getWidth(this.akI.mN(), 8.0f);
        float f4 = f2 + f3 + 7.0f;
        float f5 = (float)aiG.method_22683().method_4486() / 2.0f - f4 / 2.0f;
        if (this.akM.x(this.akK)) {
            this.akL.i(false);
        }
        float f6 = this.akL.rk();
        Color color = ColorUtils.getGlassColor(Math.max(0, Math.round(255.0f * f6)));
        Render2D.drawLiquidRect(hudRenderEvent.j().method_51448(), f5 - 2.5f, this.y - 2.5f, f4 + 5.0f, 15.0f, new BorderRadius(20.0f), new ColorRGBA(color), 2.5f, 22.0f, 11.0f, 175.0f, true, 0.0f, 0.19f);
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.MEDIUM.getFont(9.0f), this.akJ, f5 + f3 + 4.0f, this.y - 0.5f, new Color(255, 255, 255, (int)(255.0f * f6)));
        Render2D.drawFont(hudRenderEvent.j().method_51448(), Fonts.ICONSNEW.getFont(8.0f), this.akI.mN(), f5 + 1.0f, this.y + 1.0f, new Color(255, 255, 255, (int)(255.0f * f6)));
    }

    public float z(float f, float f2) {
        return f + (f2 - f) / 8.0f;
    }

    public boolean sc() {
        return this.akM.x(this.akK) && this.akL.rk() < 0.01f;
    }

    @Generated
    public NotificationIcon sd() {
        return this.akI;
    }

    @Generated
    public String se() {
        return this.akJ;
    }

    @Generated
    public long sf() {
        return this.akK;
    }

    @Generated
    public float getY() {
        return this.y;
    }

    @Generated
    public Animation dN() {
        return this.akL;
    }

    @Generated
    public SmoothedVector sg() {
        return this.akM;
    }
}

