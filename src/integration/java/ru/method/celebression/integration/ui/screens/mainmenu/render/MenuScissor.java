package ru.method.celebression.integration.ui.screens.mainmenu.render;

import ru.method.celebression.integration.ui.manager.IMinecraft;
import ru.method.celebression.integration.ui.util.math.geometry.Bound;
import org.lwjgl.opengl.GL11;

import java.util.ArrayDeque;
import java.util.Deque;

public final class MenuScissor implements IMinecraft {
    private static final Deque<int[]> SCISSOR_STACK = new ArrayDeque<>();

    public record Snapshot(boolean enabled, int x, int y, int width, int height) {
        public static Snapshot disabled() {
            return new Snapshot(false, 0, 0, 0, 0);
        }
    }

    private MenuScissor() {}

    public static void enable(float x, float y, float width, float height, float guiScale) {
        int framebufferHeight = mc.getWindow().getFramebufferHeight();
        int scissorX = Math.round(x * guiScale);
        int scissorY = Math.round(framebufferHeight - (y + height) * guiScale);
        int scissorWidth = Math.round(width * guiScale);
        int scissorHeight = Math.round(height * guiScale);

        scissorX = Math.max(0, scissorX);
        scissorY = Math.max(0, scissorY);
        scissorWidth = Math.max(0, scissorWidth);
        scissorHeight = Math.max(0, scissorHeight);

        if (!SCISSOR_STACK.isEmpty()) {
            int[] parent = SCISSOR_STACK.peek();
            int parentX2 = parent[0] + parent[2];
            int parentY2 = parent[1] + parent[3];
            int newX2 = scissorX + scissorWidth;
            int newY2 = scissorY + scissorHeight;
            scissorX = Math.max(scissorX, parent[0]);
            scissorY = Math.max(scissorY, parent[1]);
            newX2 = Math.min(newX2, parentX2);
            newY2 = Math.min(newY2, parentY2);
            scissorWidth = Math.max(0, newX2 - scissorX);
            scissorHeight = Math.max(0, newY2 - scissorY);
        }

        SCISSOR_STACK.push(new int[]{scissorX, scissorY, scissorWidth, scissorHeight});
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
    }

    public static void enable(float x, float y, float width, float height) {
        enable(x, y, width, height, 1.0f);
    }

    public static void enable(Bound bound) {
        if (bound == null) {
            return;
        }
        enable(bound.x, bound.y, bound.width, bound.height, 1.0f);
    }

    public static void disable() {
        if (!SCISSOR_STACK.isEmpty()) {
            SCISSOR_STACK.pop();
        }
        if (SCISSOR_STACK.isEmpty()) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        } else {
            int[] parent = SCISSOR_STACK.peek();
            GL11.glScissor(parent[0], parent[1], parent[2], parent[3]);
        }
    }

    public static void reset() {
        SCISSOR_STACK.clear();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
