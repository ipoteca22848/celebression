package ru.method.celebression.ui;

import lombok.Generated;
import net.minecraft.class_437;
import ru.method.celebression.api.event.CancellationState;
import ru.method.celebression.screen.LinkGuiScreen;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.modules.impl.client.UI;

public final class ScreenTransitionController
implements MinecraftClientAccess {
    private static final ScreenTransitionController To = new ScreenTransitionController();
    private final LinkGuiScreen Tp = new LinkGuiScreen();

    private ScreenTransitionController() {
    }

    public void open() {
        if (!UI.isInPlayWorld() || aiG == null) {
            return;
        }
        CancellationState.mE().cancel();
        this.Tp.resetOpenAnimation();
        if (ScreenTransitionController.aiG.field_1755 == this.Tp) {
            return;
        }
        aiG.method_1507((class_437)this.Tp);
    }

    public void beginClose() {
        if (aiG == null || ScreenTransitionController.aiG.field_1755 != this.Tp || this.Tp.isClosing()) {
            return;
        }
        this.Tp.beginClose();
    }

    public void mI() {
    }

    public boolean isOpen() {
        return aiG != null && ScreenTransitionController.aiG.field_1755 == this.Tp && !this.Tp.isClosing();
    }

    public boolean isDisplayed() {
        return aiG != null && ScreenTransitionController.aiG.field_1755 == this.Tp;
    }

    public boolean isClosing() {
        return this.Tp.isClosing();
    }

    public LinkGuiScreen mJ() {
        return this.Tp;
    }

    @Generated
    public static ScreenTransitionController mK() {
        return To;
    }
}
