package ru.method.celebression.punchy.platform.services;

import java.util.function.Consumer;
import net.minecraft.class_2960;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_3300;

public interface IClientPlatform {
    public void registerKeyMapping(class_304 var1);

    public void registerClientTickEnd(Consumer<class_310> var1);

    public void registerReloadListener(class_2960 var1, Consumer<class_3300> var2);
}

