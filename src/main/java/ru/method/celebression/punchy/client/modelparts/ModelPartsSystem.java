package ru.method.celebression.punchy.client.modelparts;

import ru.method.celebression.animation.Animation;

import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3300;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_746;
import ru.method.celebression.punchy.client.model.ItemModelDefinition;
import ru.method.celebression.punchy.client.modelparts.AnimationStateMachine;
import ru.method.celebression.punchy.client.modelparts.RenderStateMachine;
import ru.method.celebression.punchy.client.modelparts.ResourceStateMachine;
import ru.method.celebression.punchy.client.modelparts.SelectionStateMachine;
import ru.method.celebression.punchy.config.PunchyConfig;

public final class ModelPartsSystem {
    private static final ModelPartsSystem INSTANCE = new ModelPartsSystem();
    private final ResourceStateMachine resources = new ResourceStateMachine();
    private final SelectionStateMachine selection = new SelectionStateMachine();
    private final AnimationStateMachine animation = new AnimationStateMachine();
    private final RenderStateMachine render = new RenderStateMachine();

    private ModelPartsSystem() {
    }

    public static ModelPartsSystem get() {
        return INSTANCE;
    }

    public void onResourceReload(class_3300 class_33002) {
        if (!PunchyConfig.disableResourcePackModelParts()) {
            this.resources.load(class_33002);
            this.selection.onResourcesReload();
        }
    }

    public void tick(class_310 class_3102) {
        if (!PunchyConfig.disableResourcePackModelParts()) {
            this.animation.tick(class_3102);
        }
    }

    public class_2960 resolveItemModelId(class_1799 class_17992, class_1657 class_16572) {
        return PunchyConfig.disableResourcePackModelParts() ? null : this.selection.resolveModelId(class_17992, class_16572);
    }

    public ItemModelDefinition getItemModel(class_2960 class_29602) {
        return PunchyConfig.disableResourcePackModelParts() ? null : this.selection.getItemModel(class_29602);
    }

    public boolean tryRenderItem(class_1799 class_17992, class_746 class_7462, class_1306 class_13062, class_4587 class_45872, class_4597 class_45972, int n, float f, boolean bl) {
        return PunchyConfig.disableResourcePackModelParts() ? false : this.render.tryRenderItem(class_17992, class_7462, class_13062, class_45872, class_45972, n, f, bl);
    }

    public int getModelWorldEmission(class_1657 class_16572, class_1799 class_17992) {
        return PunchyConfig.disableResourcePackModelParts() ? 0 : this.selection.getModelWorldEmission(class_16572, class_17992);
    }
}

