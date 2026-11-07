package ru.method.celebression.integration.ui.screens.mainmenu.render;

import ru.method.celebression.integration.ui.manager.IMinecraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import ru.method.celebression.integration.ui.util.math.geometry.Bound;

public final class MenuDrawBridge implements IMinecraft {
    private static final ThreadLocal<DrawContext> CONTEXT = new ThreadLocal<>();
    private static float scaleX = 1.0f;
    private static float scaleY = 1.0f;

    private MenuDrawBridge() {}

    public static void begin(DrawContext context) {
        CONTEXT.set(context);
        int fbW = Math.max(1, mc.getWindow().getFramebufferWidth());
        int fbH = Math.max(1, mc.getWindow().getFramebufferHeight());
        scaleX = mc.getWindow().getScaledWidth() / (float) fbW;
        scaleY = mc.getWindow().getScaledHeight() / (float) fbH;
    }

    public static void end() {
        CONTEXT.remove();
    }

    public static MatrixStack matrices() {
        DrawContext context = CONTEXT.get();
        return context != null ? context.getMatrices() : new MatrixStack();
    }

    public static DrawContext context() {
        return CONTEXT.get();
    }

    public static float scaleX() {
        return scaleX;
    }

    public static float scaleY() {
        return scaleY;
    }

    public static float toScreenX(float fbX) {
        return fbX * scaleX;
    }

    public static float toScreenY(float fbY) {
        return fbY * scaleY;
    }

    public static float toScreenW(float fbW) {
        return fbW * scaleX;
    }

    public static float toScreenH(float fbH) {
        return fbH * scaleY;
    }

    public static float toScreenRadius(float fbRadius) {
        return fbRadius * Math.min(scaleX, scaleY);
    }

    public static Bound toScreen(Bound bound) {
        return Bound.of(
                toScreenX(bound.x),
                toScreenY(bound.y),
                toScreenW(bound.width),
                toScreenH(bound.height)
        );
    }
}
