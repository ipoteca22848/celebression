package ru.method.celebression.api.mixins;

import net.minecraft.class_10039;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.method.celebression.api.accessor.LivingEntityRenderStateAccessor;

@Mixin(value={class_10039.class})
public class ItemEntityRenderStateMixin
implements LivingEntityRenderStateAccessor {
    @Unique
    private boolean celebression$onGround;
    @Unique
    private float celebression$horizontalSpeed;
    @Unique
    private float celebression$verticalSpeed;
    @Unique
    private float celebression$xRotation;
    @Unique
    private float celebression$yRotation;
    @Unique
    private boolean celebression$additionalOffset;

    @Override
    public boolean celebression$isOnGround() {
        return this.celebression$onGround;
    }

    @Override
    public void celebression$setOnGround(boolean bl) {
        this.celebression$onGround = bl;
    }

    @Override
    public float celebression$getHorizontalSpeed() {
        return this.celebression$horizontalSpeed;
    }

    @Override
    public void celebression$setHorizontalSpeed(float f) {
        this.celebression$horizontalSpeed = f;
    }

    @Override
    public float celebression$getVerticalSpeed() {
        return this.celebression$verticalSpeed;
    }

    @Override
    public void celebression$setVerticalSpeed(float f) {
        this.celebression$verticalSpeed = f;
    }

    @Override
    public float celebression$getXRotation() {
        return this.celebression$xRotation;
    }

    @Override
    public void celebression$setXRotation(float f) {
        this.celebression$xRotation = f;
    }

    @Override
    public float celebression$getYRotation() {
        return this.celebression$yRotation;
    }

    @Override
    public void celebression$setYRotation(float f) {
        this.celebression$yRotation = f;
    }

    @Override
    public boolean celebression$hasAdditionalOffset() {
        return this.celebression$additionalOffset;
    }

    @Override
    public void celebression$setAdditionalOffset(boolean bl) {
        this.celebression$additionalOffset = bl;
    }
}

