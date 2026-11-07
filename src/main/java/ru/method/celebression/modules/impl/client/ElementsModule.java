package ru.method.celebression.modules.impl.client;

import net.minecraft.class_437;
import ru.method.celebression.Celebression;
import ru.method.celebression.screen.HudEditorScreen;
import ru.method.celebression.input.KeyConstants;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.render.NotificationRenderer;
import ru.method.celebression.modules.impl.client.UI;

public class ElementsModule
extends Module {
    public ElementsModule() {
        super("Elements", ModuleCategory.qn);
    }

    @Override
    public boolean isActionModule() {
        return true;
    }

    @Override
    public void setToggled(boolean bl) {
    }

    @Override
    public void activate() {
        if (aiG == null) {
            return;
        }
        if (!UI.isLinkGuiStyle()) {
            Celebression.getInstance().getNotifyManager().a(new NotificationRenderer(KeyConstants.akP, "\u0420\u0435\u0434\u0430\u043a\u0442\u043e\u0440 HUD \u0434\u043e\u0441\u0442\u0443\u043f\u0435\u043d \u0442\u043e\u043b\u044c\u043a\u043e \u043f\u0440\u0438 GUI = Link", 1800L));
            return;
        }
        if (!(ElementsModule.aiG.field_1755 instanceof HudEditorScreen)) {
            aiG.method_1507((class_437)new HudEditorScreen());
        }
    }
}

