package ru.method.celebression.screen;

import net.minecraft.class_642;
import ru.method.celebression.screen.MultiplayerScreen;
import ru.method.celebression.screen.ServerDialog;

public final class DeleteServerDialog
extends ServerDialog {
    private final String aaS;

    DeleteServerDialog(MultiplayerScreen multiplayerScreen, class_642 class_6422) {
        super(multiplayerScreen, "\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0441\u0435\u0440\u0432\u0435\u0440?", "\u0423\u0434\u0430\u043b\u0438\u0442\u044c", true);
        this.aaS = class_6422.field_3752 == null || class_6422.field_3752.isBlank() ? class_6422.field_3761 : class_6422.field_3752;
    }

    @Override
    protected String pA() {
        return "\u00ab" + this.aaS + "\u00bb \u0431\u0443\u0434\u0435\u0442 \u0443\u0434\u0430\u043b\u0451\u043d \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430. \u0414\u0435\u0439\u0441\u0442\u0432\u0438\u0435 \u043d\u0435\u043e\u0431\u0440\u0430\u0442\u0438\u043c\u043e.";
    }

    @Override
    protected float pB() {
        return 420.0f;
    }

    @Override
    protected boolean pC() {
        this.acx.pQ();
        return true;
    }
}

