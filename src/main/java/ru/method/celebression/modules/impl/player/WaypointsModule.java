package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_3965;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.events.impl.EventMouse;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.command.ChatOutput;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.api.event.KeyInputEvent;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.util.NamedItem;
import ru.method.celebression.manager.WaypointManager;

public class WaypointsModule
extends Module {
    private final BindSetting xq = new BindSetting("modules.settings.waypoints.quick_bind", new KeyBind(-1, false));
    private final BooleanSetting xr = new BooleanSetting("modules.settings.waypoints.auto_event", true);
    private final BooleanSetting xs = new BooleanSetting("modules.settings.waypoints.auto_event_ft", true);
    private final BooleanSetting xt = new BooleanSetting("modules.settings.waypoints.auto_death", true);
    private boolean xu;
    private int xv = 1;

    public WaypointsModule() {
        super("WayPoints", ModuleCategory.qm);
        this.getSettings().add(this.xq);
        this.getSettings().add(this.xr);
        this.getSettings().add(this.xs);
        this.getSettings().add(this.xt);
    }

    @EventHandler
    private void onKey(KeyInputEvent keyInputEvent) {
        if (WaypointsModule.fullNullCheck() || WaypointsModule.aiG.field_1755 != null || keyInputEvent.getAction() != 1) {
            return;
        }
        if (((KeyBind)this.xq.jT()).af(keyInputEvent.c())) {
            this.hU();
        }
    }

    @EventHandler
    private void onMouse(EventMouse eventMouse) {
        if (WaypointsModule.fullNullCheck() || WaypointsModule.aiG.field_1755 != null || eventMouse.getAction() != 1) {
            return;
        }
        if (((KeyBind)this.xq.jT()).ag(eventMouse.getButton())) {
            this.hU();
        }
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        boolean bl;
        if (WaypointsModule.fullNullCheck()) {
            return;
        }
        WaypointManager waypointManager = Celebression.getInstance().getWaypointManager();
        if (waypointManager != null) {
            if (((Boolean)this.xs.jT()).booleanValue() && waypointManager.eZ()) {
                waypointManager.eW();
            } else if (((Boolean)this.xr.jT()).booleanValue() && waypointManager.eY() && !waypointManager.eZ()) {
                waypointManager.eW();
            }
        }
        boolean bl2 = bl = WaypointsModule.aiG.field_1724.method_29504() || WaypointsModule.aiG.field_1724.method_6032() <= 0.0f;
        if (!bl) {
            this.xu = false;
            return;
        }
        if (!((Boolean)this.xt.jT()).booleanValue() || this.xu) {
            return;
        }
        class_2338 class_23382 = WaypointsModule.aiG.field_1724.method_24515();
        this.a(this.bJ("Death"), class_23382);
        this.xu = true;
    }

    private void hU() {
        if (WaypointsModule.fullNullCheck()) {
            return;
        }
        this.a(this.bJ("Way"), this.hV());
    }

    private class_2338 hV() {
        class_239 class_2392 = WaypointsModule.aiG.field_1724.method_5745(128.0, 1.0f, false);
        if (class_2392 instanceof class_3965) {
            class_3965 class_39652 = (class_3965)class_2392;
            if (class_2392.method_17783() == class_239.class_240.field_1332) {
                return class_39652.method_17777();
            }
        }
        return WaypointsModule.aiG.field_1724.method_24515();
    }

    private void a(String string, class_2338 class_23382) {
        WaypointManager waypointManager = Celebression.getInstance().getWaypointManager();
        if (waypointManager == null) {
            return;
        }
        waypointManager.a(new NamedItem(string, class_23382.method_10263(), class_23382.method_10264(), class_23382.method_10260()));
        ChatOutput.cG("\u041c\u0435\u0442\u043a\u0430 \u0441\u043e\u0437\u0434\u0430\u043d\u0430: " + string + " " + class_23382.method_10263() + " " + class_23382.method_10264() + " " + class_23382.method_10260());
    }

    private String bJ(String string) {
        String string2;
        WaypointManager waypointManager = Celebression.getInstance().getWaypointManager();
        if (waypointManager == null) {
            return string;
        }
        while (waypointManager.O(string2 = string + " " + this.xv++) != null) {
        }
        return string2;
    }
}

