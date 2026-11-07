package ru.method.celebression.punchy.platform;

import java.util.function.Consumer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.class_2960;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_3264;
import net.minecraft.class_3300;
import ru.method.celebression.punchy.platform.services.IClientPlatform;

public class FabricClientPlatform
implements IClientPlatform {
    @Override
    public void registerKeyMapping(class_304 class_3042) {
        KeyBindingHelper.registerKeyBinding((class_304)class_3042);
    }

    @Override
    public void registerClientTickEnd(Consumer<class_310> consumer) {
        ClientTickEvents.END_CLIENT_TICK.register(consumer::accept);
    }

    @Override
    public void registerReloadListener(final class_2960 class_29602, final Consumer<class_3300> consumer) {
        ResourceManagerHelper.get((class_3264)class_3264.field_14188).registerReloadListener((IdentifiableResourceReloadListener)new SimpleSynchronousResourceReloadListener(){

            public class_2960 getFabricId() {
                return class_29602;
            }

            public void method_14491(class_3300 class_33002) {
                consumer.accept(class_33002);
            }
        });
    }
}

