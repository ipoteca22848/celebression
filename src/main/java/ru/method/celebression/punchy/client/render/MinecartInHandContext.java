package ru.method.celebression.punchy.client.render;

import net.minecraft.class_1299;
import net.minecraft.class_1688;

public final class MinecartInHandContext {
    private static final ThreadLocal<State> STATE = ThreadLocal.withInitial(State::new);

    private MinecartInHandContext() {
    }

    public static void push() {
        ++MinecartInHandContext.STATE.get().depth;
    }

    public static void pop() {
        State state = STATE.get();
        state.depth = Math.max(0, state.depth - 1);
        if (state.depth == 0) {
            state.vy = 0.0;
            state.tntOffset = 0.0f;
            state.type = null;
        }
    }

    public static boolean isActive() {
        return MinecartInHandContext.STATE.get().depth > 0;
    }

    public static void setVy(double d) {
        MinecartInHandContext.STATE.get().vy = d;
    }

    public static double getVy() {
        return MinecartInHandContext.STATE.get().vy;
    }

    public static void setTntOffset(float f) {
        MinecartInHandContext.STATE.get().tntOffset = f;
    }

    public static float getTntOffset() {
        return MinecartInHandContext.STATE.get().tntOffset;
    }

    public static void setType(class_1299<? extends class_1688> class_12992) {
        MinecartInHandContext.STATE.get().type = class_12992;
    }

    public static class_1299<? extends class_1688> getType() {
        return MinecartInHandContext.STATE.get().type;
    }

    static final class State {
        int depth;
        double vy;
        float tntOffset;
        class_1299<? extends class_1688> type;

        private State() {
        }
    }
}

