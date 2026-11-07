package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1766;
import net.minecraft.class_1799;
import ru.method.celebression.api.event.BlockBreakEvent;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.SliderSetting;

public class MineHelperModule
extends Module {
    private final BooleanSetting vO = new BooleanSetting("modules.settings.minehelper.save_tools", true);
    private final SliderSetting vP = new SliderSetting("modules.settings.minehelper.percent", 5.0f, 1.0f, 100.0f, 1.0f, this.vO::jT);
    private long vQ;

    public MineHelperModule() {
        super("MineHelper", ModuleCategory.qm);
    }

    @EventHandler
    private void onStartBreakBlock(BlockBreakEvent blockBreakEvent) {
        if (MineHelperModule.fullNullCheck() || !((Boolean)this.vO.jT()).booleanValue()) {
            return;
        }
        class_1799 class_17992 = MineHelperModule.aiG.field_1724.method_6047();
        if (!this.n(class_17992)) {
            return;
        }
        double d = this.o(class_17992);
        if (d >= (double)((Float)this.vP.jT()).floatValue()) {
            return;
        }
        blockBreakEvent.cancel();
        long l = System.currentTimeMillis();
        if (l - this.vQ > 1500L) {
            ChatOutput.cG("MineHelper: \u0438\u043d\u0441\u0442\u0440\u0443\u043c\u0435\u043d\u0442 \u043f\u043e\u0447\u0442\u0438 \u0441\u043b\u043e\u043c\u0430\u043d (" + Math.round(d) + "%)");
            this.vQ = l;
        }
    }

    private boolean n(class_1799 class_17992) {
        return class_17992 != null && class_17992.method_7963() && class_17992.method_7909() instanceof class_1766;
    }

    private double o(class_1799 class_17992) {
        int n = class_17992.method_7936();
        if (n <= 0) {
            return 100.0;
        }
        return (double)(n - class_17992.method_7919()) * 100.0 / (double)n;
    }
}

