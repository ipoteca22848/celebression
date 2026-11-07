package ru.method.celebression.utils.render;

import meteordevelopment.orbit.IEventBus;
import net.minecraft.class_332;
import net.minecraft.class_9779;
import ru.method.celebression.api.event.HudRenderEvent;
import ru.method.celebression.utils.render.Render2D;

public final class HudRenderFrame {
    private static final ThreadLocal<State> STATE = ThreadLocal.withInitial(State::new);

    private HudRenderFrame() {
    }

    public static void begin(class_332 class_3322, class_9779 class_97792) {
        State state = STATE.get();
        state.context = class_3322;
        state.tickCounter = class_97792;
        state.hudRenderedBeforeOverlays = false;
        state.hudEventPosted = false;
        state.dynamicIslandDeferredToTopPass = false;
        state.dynamicIslandTopPass = false;
        state.active = true;
    }

    public static void end() {
        State state = STATE.get();
        state.active = false;
        state.context = null;
        state.tickCounter = null;
        state.hudRenderedBeforeOverlays = false;
        state.hudEventPosted = false;
        state.dynamicIslandDeferredToTopPass = false;
        state.dynamicIslandTopPass = false;
    }

    public static State get() {
        State state = STATE.get();
        return state.active ? state : null;
    }

    public static void tryDispatchBeforeOverlays(IEventBus iEventBus) {
        State state = HudRenderFrame.get();
        if (state == null || !state.markHudRenderedBeforeOverlays()) {
            return;
        }
        HudRenderFrame.dispatchHudRender(iEventBus);
    }

    public static void dispatchHudRender(IEventBus iEventBus) {
        State state = HudRenderFrame.get();
        if (state == null || !state.tryBeginHudEventPost()) {
            return;
        }
        Render2D.prepareHudFrame(state.context());
        iEventBus.post((Object)new HudRenderEvent(state.context(), state.tickCounter()));
    }

    public static final class State {
        boolean active;
        class_332 context;
        class_9779 tickCounter;
        boolean hudRenderedBeforeOverlays;
        boolean hudEventPosted;
        boolean dynamicIslandDeferredToTopPass;
        boolean dynamicIslandTopPass;

        private State() {
        }

        public class_332 context() {
            return this.context;
        }

        public class_9779 tickCounter() {
            return this.tickCounter;
        }

        public boolean markHudRenderedBeforeOverlays() {
            if (this.hudRenderedBeforeOverlays) {
                return false;
            }
            this.hudRenderedBeforeOverlays = true;
            this.dynamicIslandDeferredToTopPass = true;
            return true;
        }

        public boolean hudRenderedBeforeOverlays() {
            return this.hudRenderedBeforeOverlays;
        }

        public boolean tryBeginHudEventPost() {
            if (this.hudEventPosted) {
                return false;
            }
            this.hudEventPosted = true;
            return true;
        }

        public boolean hudEventPosted() {
            return this.hudEventPosted;
        }

        public boolean shouldDeferDynamicIsland() {
            return this.dynamicIslandDeferredToTopPass && !this.dynamicIslandTopPass;
        }

        public boolean needsDynamicIslandTopPass() {
            return this.dynamicIslandDeferredToTopPass && !this.dynamicIslandTopPass;
        }

        public void beginDynamicIslandTopPass() {
            this.dynamicIslandTopPass = true;
        }
    }
}

