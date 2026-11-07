package ru.method.celebression.punchy.client.model;

import org.joml.Vector3f;

public class BedrockBone {
    private final String name;
    private final String parent;
    private final Vector3f pivot;
    private final Vector3f rotation;
    private final Vector3f localPivot;

    public BedrockBone(String string, String string2, Vector3f vector3f, Vector3f vector3f2, Vector3f vector3f3) {
        this.name = string;
        this.parent = string2;
        this.pivot = vector3f;
        this.rotation = vector3f2;
        this.localPivot = vector3f3;
    }

    public String getName() {
        return this.name;
    }

    public String getParent() {
        return this.parent;
    }

    public Vector3f getPivot() {
        return this.pivot;
    }

    public Vector3f getRotation() {
        return this.rotation;
    }

    public Vector3f getLocalPivot() {
        return this.localPivot;
    }
}

