package ru.method.celebression.screen;

import net.minecraft.class_642;
import ru.method.celebression.screen.MultiplayerScreen;
import ru.method.celebression.screen.ServerDialog;

public final class EditServerDialog
extends ServerDialog {
    private final class_642 acU;
    private final boolean acV;
    private final ServerDialog.DialogButton acW;
    private static final class_642.class_643[] acX = new class_642.class_643[]{class_642.class_643.field_3764, class_642.class_643.field_3767, class_642.class_643.field_3768};

    EditServerDialog(MultiplayerScreen multiplayerScreen, class_642 class_6422, boolean bl) {
        super(multiplayerScreen, bl ? "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0441\u0435\u0440\u0432\u0435\u0440" : "\u041d\u0430\u0441\u0442\u0440\u043e\u0438\u0442\u044c \u0441\u0435\u0440\u0432\u0435\u0440", bl ? "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c" : "\u0413\u043e\u0442\u043e\u0432\u043e", false);
        this.acU = class_6422;
        this.acV = bl;
        this.acB.add(new ServerDialog.DialogField("\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435", "\u0421\u0435\u0440\u0432\u0435\u0440", class_6422.field_3752));
        this.acB.add(new ServerDialog.DialogField("\u0410\u0434\u0440\u0435\u0441", "play.example.org", class_6422.field_3761));
        this.acW = new ServerDialog.DialogButton("\u0420\u0435\u0441\u0443\u0440\u0441\u043f\u0430\u043a \u0441\u0435\u0440\u0432\u0435\u0440\u0430", EditServerDialog.a(class_6422.method_2990()), "\u0412\u044b\u043a\u043b\u044e\u0447\u0435\u043d", "\u041f\u0440\u0438 \u0437\u0430\u043f\u0440\u043e\u0441\u0435", "\u0412\u043a\u043b\u044e\u0447\u0451\u043d");
        this.acC.add(this.acW);
    }

    private static int a(class_642.class_643 class_6432) {
        for (int i = 0; i < acX.length; ++i) {
            if (acX[i] != class_6432) continue;
            return i;
        }
        return 1;
    }

    @Override
    protected boolean pC() {
        String string = ((ServerDialog.DialogField)this.acB.get((int)0)).Ko.trim();
        String string2 = ((ServerDialog.DialogField)this.acB.get((int)1)).Ko.trim();
        if (string2.isEmpty()) {
            return false;
        }
        this.acU.field_3752 = string.isEmpty() ? string2 : string;
        this.acU.field_3761 = string2;
        this.acU.method_2995(acX[this.acW.aby]);
        this.acx.a(this.acU, this.acV);
        return true;
    }
}

