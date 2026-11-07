package ru.method.celebression.punchy.client.modelparts;

import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import ru.method.celebression.punchy.client.model.ItemModelDefinition;
import ru.method.celebression.punchy.client.model.ResourcePackGeoRegistry;
import ru.method.celebression.punchy.client.model.ResourcePackItemModelRegistry;

final class SelectionStateMachine {
    SelectionStateMachine() {
    }

    void onResourcesReload() {
    }

    class_2960 resolveModelId(class_1799 class_17992, class_1657 class_16572) {
        return class_17992 != null && !class_17992.method_7960() && class_16572 != null ? ResourcePackGeoRegistry.getModelForTarget(class_17992, class_16572) : null;
    }

    ItemModelDefinition getItemModel(class_2960 class_29602) {
        return ResourcePackItemModelRegistry.getModel(class_29602);
    }

    int getModelWorldEmission(class_1657 class_16572, class_1799 class_17992) {
        if (class_16572 != null && class_17992 != null && !class_17992.method_7960()) {
            class_2960 class_29602 = this.resolveModelId(class_17992, class_16572);
            if (class_29602 == null) {
                return 0;
            }
            ItemModelDefinition itemModelDefinition = this.getItemModel(class_29602);
            if (itemModelDefinition == null) {
                return 0;
            }
            int n = 0;
            for (ItemModelDefinition.Element element : itemModelDefinition.getElements()) {
                if (!element.hasWorldLight()) continue;
                n = Math.max(n, element.getLightEmission());
            }
            return n;
        }
        return 0;
    }
}

