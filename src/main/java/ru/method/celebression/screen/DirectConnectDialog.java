package ru.method.celebression.screen;

import net.minecraft.class_310;
import ru.method.celebression.screen.MultiplayerScreen;
import ru.method.celebression.screen.ServerDialog;

public final class DirectConnectDialog
extends ServerDialog {
    private static String act = "";

    DirectConnectDialog(MultiplayerScreen multiplayerScreen) {
        super(multiplayerScreen, "\u041f\u043e\u0434\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u0435 \u043f\u043e \u0430\u0434\u0440\u0435\u0441\u0443", "\u041f\u043e\u0434\u043a\u043b\u044e\u0447\u0438\u0442\u044c\u0441\u044f", false);
        String string;
        if (act.isEmpty() && (string = class_310.method_1551().field_1690.field_1864) != null) {
            act = string;
        }
        this.acB.add(new ServerDialog.DialogField("\u0410\u0434\u0440\u0435\u0441 \u0441\u0435\u0440\u0432\u0435\u0440\u0430", "mc.artygrief.su", act));
    }

    @Override
    protected boolean pC() {
        String string = ((ServerDialog.DialogField)this.acB.get((int)0)).Ko.trim();
        if (string.isEmpty()) {
            return false;
        }
        DirectConnectDialog.cy(string);
        this.acx.cx(string);
        return false;
    }

    @Override
    protected void pV() {
        DirectConnectDialog.cy(((ServerDialog.DialogField)this.acB.get((int)0)).Ko.trim());
        super.pV();
    }

    private static void cy(String string) {
        act = string;
        class_310 class_3102 = class_310.method_1551();
        if (!string.isEmpty() && !string.equals(class_3102.field_1690.field_1864)) {
            class_3102.field_1690.field_1864 = string;
            class_3102.field_1690.method_1640();
        }
    }
}

