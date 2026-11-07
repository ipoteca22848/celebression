package ru.method.celebression.api.accessor;

import net.minecraft.class_1799;

public interface HeldItemRendererStateAccessor {
    public float getEquippedProgressMainHand();

    public void setEquippedProgressMainHand(float var1);

    public float getEquippedProgressOffHand();

    public void setEquippedProgressOffHand(float var1);

    public void setItemStackMainHand(class_1799 var1);

    public void setItemStackOffHand(class_1799 var1);
}

