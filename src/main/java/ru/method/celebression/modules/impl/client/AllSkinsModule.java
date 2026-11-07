package ru.method.celebression.modules.impl.client;

import net.minecraft.class_2960;
import ru.method.celebression.Celebression;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;

public class AllSkinsModule
extends Module {
    public static final class_2960 yO = Celebression.id("textures/allskins/rudi.png");

    public AllSkinsModule() {
        super("AllSkins", ModuleCategory.qn);
    }

    public static class_2960 it() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return null;
        }
        AllSkinsModule allSkinsModule = celebression.getModuleManager().getModule(AllSkinsModule.class);
        return allSkinsModule != null && allSkinsModule.isToggle() ? yO : null;
    }
}

