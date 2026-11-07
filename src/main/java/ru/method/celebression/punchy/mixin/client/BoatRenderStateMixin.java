package ru.method.celebression.punchy.mixin.client;

import net.minecraft.class_10004;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.method.celebression.punchy.client.render.BoatLidState;

@Mixin(value={class_10004.class})
public class BoatRenderStateMixin
implements BoatLidState {
    @Unique
    private double punchy$vy;
    @Unique
    private boolean punchy$chestBoat;
    @Unique
    private boolean punchy$inHand;
    @Unique
    private boolean punchy$lidApplied;
    @Unique
    private boolean punchy$chestPoseValid;
    @Unique
    private float punchy$chestX;
    @Unique
    private float punchy$chestY;
    @Unique
    private float punchy$chestZ;
    @Unique
    private float punchy$chestRotX;
    @Unique
    private float punchy$chestRotY;
    @Unique
    private float punchy$chestRotZ;
    @Unique
    private float punchy$lidOpen;
    @Unique
    private float punchy$lidVel;
    @Unique
    private long punchy$lidLastUpdateNanos;
    @Unique
    private int punchy$light = -1;

    @Override
    public void punchy$setVy(double d) {
        this.punchy$vy = d;
    }

    @Override
    public double punchy$getVy() {
        return this.punchy$vy;
    }

    @Override
    public void punchy$setChestBoat(boolean bl) {
        this.punchy$chestBoat = bl;
    }

    @Override
    public boolean punchy$isChestBoat() {
        return this.punchy$chestBoat;
    }

    @Override
    public void punchy$setInHand(boolean bl) {
        this.punchy$inHand = bl;
    }

    @Override
    public boolean punchy$isInHand() {
        return this.punchy$inHand;
    }

    @Override
    public void punchy$setLidApplied(boolean bl) {
        this.punchy$lidApplied = bl;
    }

    @Override
    public boolean punchy$isLidApplied() {
        return this.punchy$lidApplied;
    }

    @Override
    public void punchy$setChestPose(float f, float f2, float f3, float f4, float f5, float f6) {
        this.punchy$chestPoseValid = true;
        this.punchy$chestX = f;
        this.punchy$chestY = f2;
        this.punchy$chestZ = f3;
        this.punchy$chestRotX = f4;
        this.punchy$chestRotY = f5;
        this.punchy$chestRotZ = f6;
    }

    @Override
    public float punchy$getChestX() {
        return this.punchy$chestX;
    }

    @Override
    public float punchy$getChestY() {
        return this.punchy$chestY;
    }

    @Override
    public float punchy$getChestZ() {
        return this.punchy$chestZ;
    }

    @Override
    public float punchy$getChestRotX() {
        return this.punchy$chestRotX;
    }

    @Override
    public float punchy$getChestRotY() {
        return this.punchy$chestRotY;
    }

    @Override
    public float punchy$getChestRotZ() {
        return this.punchy$chestRotZ;
    }

    @Override
    public boolean punchy$isChestPoseValid() {
        return this.punchy$chestPoseValid;
    }

    @Override
    public void punchy$setChestPoseValid(boolean bl) {
        this.punchy$chestPoseValid = bl;
    }

    @Override
    public void punchy$setLidOpen(float f) {
        this.punchy$lidOpen = f;
    }

    @Override
    public float punchy$getLidOpen() {
        return this.punchy$lidOpen;
    }

    @Override
    public void punchy$setLidVel(float f) {
        this.punchy$lidVel = f;
    }

    @Override
    public float punchy$getLidVel() {
        return this.punchy$lidVel;
    }

    @Override
    public void punchy$setLidLastUpdateNanos(long l) {
        this.punchy$lidLastUpdateNanos = l;
    }

    @Override
    public long punchy$getLidLastUpdateNanos() {
        return this.punchy$lidLastUpdateNanos;
    }

    @Override
    public void punchy$setLight(int n) {
        this.punchy$light = n;
    }

    @Override
    public int punchy$getLight() {
        return this.punchy$light;
    }
}

