package ru.method.celebression.input;

import net.minecraft.class_310;
import ru.method.celebression.Celebression;
import ru.method.celebression.manager.ClickRippleManager;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.screen.LinkGuiScreen;
import ru.method.celebression.ui.ModuleCard;
import ru.method.celebression.ui.ModuleSettingsPanel;
import ru.method.celebression.ui.ScreenTransitionController;
import ru.method.celebression.modules.impl.client.UI;
import ru.method.celebression.screen.clickgui.ClickGui;

public final class ClickGuiKeyHandler {
    public static final int qw = 344;
    private static final int qx = 54;
    private static final long qy = 120000000L;
    private static long qz;

    private ClickGuiKeyHandler() {
    }

    public static boolean b(int n, int n2, int n3) {
        if (n3 != 1 || n < 0) {
            return false;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.isPanic() || celebression.getModuleManager() == null) {
            return false;
        }
        UI uI = celebression.getModuleManager().getModule(UI.class);
        if (uI == null || !ClickGuiKeyHandler.a(uI, n, n2) || !ClickGuiKeyHandler.fN()) {
            return false;
        }
        if (ClickGuiKeyHandler.a(celebression)) {
            return false;
        }
        long l = System.nanoTime();
        if (l - qz < 120000000L) {
            return true;
        }
        qz = l;
        uI.toggle();
        return true;
    }

    public static boolean a(UI uI, int n) {
        return ClickGuiKeyHandler.a(uI, n, -1);
    }

    public static boolean a(UI uI, int n, int n2) {
        KeyBind keyBind = uI.getBind();
        return keyBind != null && keyBind.af(n);
    }

    private static boolean a(Celebression celebression) {
        if (ru.method.celebression.integration.ui.bridge.ClientScreens.hasBinding()) return true;
        if (ModuleCard.ov()) {
            return true;
        }
        LinkGuiScreen linkGuiScreen = ScreenTransitionController.mK().mJ();
        if (linkGuiScreen != null && linkGuiScreen.nJ()) {
            return true;
        }
        for (ModuleSettingsPanel moduleSettingsPanel : ClickRippleManager.nW()) {
            if (!moduleSettingsPanel.hasActiveBinding()) continue;
            return true;
        }
        ClickGui clickGui = celebression.getClickGui();
        return clickGui != null && clickGui.isDisplayed() && clickGui.hasActiveBinding();
    }

    public static boolean fN() {
        if (!UI.isInPlayWorld()) {
            return false;
        }
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return false;
        }
        ClickGui clickGui = celebression.getClickGui();
        ScreenTransitionController screenTransitionController = ScreenTransitionController.mK();
        if (clickGui == null) {
            return false;
        }
        class_310 class_3102 = class_310.method_1551();
        return ru.method.celebression.integration.ui.bridge.ClientScreens.isOpen() || class_3102.field_1755 == null || class_3102.field_1755 == clickGui || class_3102.field_1755 == screenTransitionController.mJ();
    }
}

