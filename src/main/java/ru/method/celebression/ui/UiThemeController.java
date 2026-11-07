package ru.method.celebression.ui;

import ru.method.celebression.Celebression;
import ru.method.celebression.ui.ClientTheme;
import ru.method.celebression.modules.impl.client.UI;

public final class UiThemeController {
    private static final long YU = 340L;
    private static volatile ClientTheme YV = ClientTheme.YZ;
    private static volatile ClientTheme YW = ClientTheme.YZ;
    private static volatile long YX;
    private static volatile boolean YY;

    private UiThemeController() {
    }

    public static ClientTheme oX() {
        return YV;
    }

    public static ClientTheme oY() {
        return YW;
    }

    public static float oZ() {
        if (YX <= 0L) {
            return 1.0f;
        }
        float f = (float)(System.currentTimeMillis() - YX) / 340.0f;
        if (f >= 1.0f) {
            return 1.0f;
        }
        f = Math.max(0.0f, f);
        return (float)(1.0 - Math.pow(1.0 - (double)f, 3.0));
    }

    public static ClientTheme pa() {
        return YV;
    }

    public static void pb() {
        if (YY) {
            return;
        }
        UiThemeController.f(YV);
        YY = true;
    }

    public static void c(ClientTheme clientTheme) {
        if (clientTheme == null) {
            clientTheme = ClientTheme.YZ;
        }
        if (clientTheme != YV) {
            YW = YV;
            YX = System.currentTimeMillis();
        }
        YV = clientTheme;
        UiThemeController.f(clientTheme);
        YY = true;
        UiThemeController.pd();
    }

    public static void d(ClientTheme clientTheme) {
        UiThemeController.c(clientTheme);
    }

    public static void e(ClientTheme clientTheme) {
        UiThemeController.c(clientTheme);
    }

    public static void co(String string) {
        ClientTheme clientTheme = ClientTheme.cp(string);
        if (clientTheme != null) {
            YV = clientTheme;
        }
    }

    public static void pc() {
        UiThemeController.f(YV);
        YY = true;
    }

    private static void f(ClientTheme clientTheme) {
        clientTheme.pj();
        UiThemeController.g(clientTheme);
    }

    private static void g(ClientTheme clientTheme) {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getModuleManager() == null) {
            return;
        }
        UI uI = celebression.getModuleManager().getModule(UI.class);
        if (uI != null) {
            uI.syncFromLinkTheme(clientTheme);
        }
    }

    private static void pd() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getConfigManager() == null || celebression.isConfigLoading()) {
            return;
        }
        celebression.getConfigManager().dU();
    }
}

