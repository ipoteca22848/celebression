package ru.method.celebression.screen.clickgui.components.impl;

import java.awt.Color;
import net.minecraft.class_1074;
import net.minecraft.class_332;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.screen.clickgui.components.Component;
import ru.method.celebression.utils.render.ColorUtils;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class DualActionButtonComponent
extends Component {
    private static final float BUTTON_HEIGHT = 14.0f;
    private static final float BUTTON_GAP = 4.0f;
    private final String leftLabelKey;
    private final String rightLabelKey;
    private final Runnable leftAction;
    private final Runnable rightAction;

    public DualActionButtonComponent(String string, Runnable runnable, String string2, Runnable runnable2) {
        super(string + "|" + string2);
        this.leftLabelKey = string;
        this.rightLabelKey = string2;
        this.leftAction = runnable;
        this.rightAction = runnable2;
        this.addHeight = () -> Float.valueOf(4.0f);
    }

    @Override
    public void render(class_332 class_3322, int n, int n2, float f) {
        float f2 = Math.max(0.0f, (this.width - 4.0f - 8.0f) / 2.0f);
        float f3 = this.x + 4.0f;
        float f4 = f3 + f2 + 4.0f;
        float f5 = this.y + 2.0f;
        this.renderButton(class_3322, f3, f5, f2, 14.0f, this.leftLabelKey, n, n2);
        this.renderButton(class_3322, f4, f5, f2, 14.0f, this.rightLabelKey, n, n2);
    }

    private void renderButton(class_332 class_3322, float f, float f2, float f3, float f4, String string, int n, int n2) {
        boolean bl = MathUtils.c(f, f2, f3, f4, n, n2);
        Color color = bl ? ColorUtils.alpha(ColorUtils.getPrimaryColor(), 95) : new Color(255, 255, 255, 24);
        Color color2 = bl ? ColorUtils.alpha(ColorUtils.getPrimaryColor(), 155) : ColorUtils.alpha(ColorUtils.getGlassOutlineColor(), 130);
        Color color3 = ColorUtils.getTextColor(245);
        Render2D.drawRoundedRect(class_3322.method_51448(), f, f2, f3, f4, 4.0f, color);
        Render2D.drawRoundedRect(class_3322.method_51448(), f, f2, f3, 1.0f, 1.0f, color2);
        String string2 = class_1074.method_4662((String)string, (Object[])new Object[0]);
        float f5 = 7.0f;
        float f6 = Fonts.SEMIBOLD.getWidth(string2, f5);
        float f7 = Fonts.SEMIBOLD.getHeight(f5);
        float f8 = f + (f3 - f6) / 2.0f;
        float f9 = f2 + (f4 - f7) / 2.0f - 0.5f;
        Render2D.drawFont(class_3322.method_51448(), Fonts.SEMIBOLD.getFont(f5), string2, f8, f9, color3);
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (n != 0) {
            return;
        }
        float f = Math.max(0.0f, (this.width - 4.0f - 8.0f) / 2.0f);
        float f2 = this.x + 4.0f;
        float f3 = f2 + f + 4.0f;
        float f4 = this.y + 2.0f;
        if (MathUtils.c(f2, f4, f, 14.0f, (float)d, (float)d2)) {
            this.leftAction.run();
            return;
        }
        if (MathUtils.c(f3, f4, f, 14.0f, (float)d, (float)d2)) {
            this.rightAction.run();
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

    @Override
    public float getLayoutHeight() {
        return 18.0f;
    }
}

