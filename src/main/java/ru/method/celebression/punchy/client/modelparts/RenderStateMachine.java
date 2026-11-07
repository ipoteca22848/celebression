package ru.method.celebression.punchy.client.modelparts;

import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.render.CustomItemModelRenderer;

final class RenderStateMachine {
    RenderStateMachine() {
    }

    boolean tryRenderItem(class_1799 class_17992, class_746 class_7462, class_1306 class_13062, class_4587 class_45872, class_4597 class_45972, int n, float f, boolean bl) {
        return CustomItemModelRenderer.tryRender(class_17992, class_7462, class_13062, class_45872, class_45972, n, f, bl);
    }
}

