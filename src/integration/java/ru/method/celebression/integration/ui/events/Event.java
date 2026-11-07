package ru.method.celebression.integration.ui.events;

import ru.method.celebression.integration.ui.manager.ClientManager;
import ru.method.celebression.integration.ui.manager.IMinecraft;
import ru.method.celebression.integration.ui.manager.Manager;
import ru.method.celebression.integration.ui.modules.api.Module;

public class Event implements IMinecraft {

    public boolean isCancel;

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        this.isCancel = cancel;
    }

    public static void call(final Event event) {
        var client = ru.method.celebression.Celebression.getInstance();
        if (client != null && !event.isCancel()) client.getEventHandler().post(event);
    }
}
