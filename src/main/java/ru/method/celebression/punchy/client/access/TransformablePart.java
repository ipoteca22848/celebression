package ru.method.celebression.punchy.client.access;

import org.joml.Matrix4f;

public interface TransformablePart {
    public void punchy$setExplicitTransform(Matrix4f var1);

    public Matrix4f punchy$getExplicitTransform();

    public void punchy$setDirectPoseBase(float var1, float var2, float var3, float var4, float var5, float var6);

    public boolean punchy$hasDirectPoseBase();

    public float punchy$getDirectPoseBaseX();

    public float punchy$getDirectPoseBaseY();

    public float punchy$getDirectPoseBaseZ();

    public float punchy$getDirectPoseBaseXRot();

    public float punchy$getDirectPoseBaseYRot();

    public float punchy$getDirectPoseBaseZRot();

    public void punchy$clearDirectPoseBase();
}

