package unkw.visualtest.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.class_304;
import net.minecraft.class_3675;
import unkw.visualtest.client.hud.HudState;

public final class Visual_testClient
implements ClientModInitializer {
    private static final HudState HUD_STATE = new HudState();

    public static HudState state() {
        return HUD_STATE;
    }

    public void onInitializeClient() {
        class_304 class_3042 = KeyBindingHelper.registerKeyBinding((class_304)new class_304("key.visual-test.preview", class_3675.class_307.field_1668, 297, "category.visual-test"));
        ClientTickEvents.END_CLIENT_TICK.register(class_3102 -> HUD_STATE.tick(class_3102, class_3042));
    }
}

