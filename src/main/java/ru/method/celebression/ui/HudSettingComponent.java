package ru.method.celebression.ui;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.class_332;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Font;
import ru.method.celebression.utils.render.fonts.Fonts;

public abstract class HudSettingComponent
extends Component {
    protected Animation jU;

    public HudSettingComponent(String string) {
        super(string);
    }

    protected void a(class_332 class_3322, String string, float f, float f2, float f3, float f4, float f5, Color color, int n, int n2, boolean bl) {
        Font font = bl ? Fonts.ROUNDBOLD : Fonts.REGULAR;
        boolean bl2 = MathUtils.c(this.x, this.y, this.width, f5, n, n2);
        float f6 = font.getWidth(string, f);
        float f7 = this.getMarqueeOffset(f6, f4, bl2);
        Render2D.startScissor(class_3322, f2, f3, f4, f5);
        Render2D.drawFont(class_3322.method_51448(), font.getFont(f), string, f2 - f7, f3, color);
        if (f7 > 0.0f && f6 > f4) {
            Render2D.drawFont(class_3322.method_51448(), font.getFont(f), string, f2 - f7 + f6 + 12.0f, f3, color);
        }
        Render2D.stopScissor(class_3322);
    }

    @Generated
    public Animation dN() {
        return this.jU;
    }

    @Generated
    public void a(Animation animation) {
        this.jU = animation;
    }
}

