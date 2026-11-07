package ru.method.celebression.api.event;

import lombok.Generated;
import ru.method.celebression.screen.LinkGuiScreen;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class CancellationState
implements MinecraftClientAccess {
    private static final CancellationState Tl = new CancellationState();

    private CancellationState() {
    }

    public void a(LinkGuiScreen linkGuiScreen) {
    }

    public void cancel() {
    }

    public boolean isActive() {
        return false;
    }

    @Generated
    public static CancellationState mE() {
        return Tl;
    }
}

