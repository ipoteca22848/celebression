package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.method.celebression.modules.impl.render.cosmetic.BedrockCubeDefinition;

public final class BedrockModelBone {
    private final String bf;
    private final float pivotX;
    private final float pivotY;
    private final float pivotZ;
    private final float bg;
    private final float bh;
    private final float bi;
    private final List<BedrockCubeDefinition> bj;
    private final List<BedrockModelBone> bk;

    public BedrockModelBone(String string, float f, float f2, float f3, float f4, float f5, float f6, List<BedrockCubeDefinition> list) {
        this.bf = string;
        this.pivotX = f;
        this.pivotY = f2;
        this.pivotZ = f3;
        this.bg = f4;
        this.bh = f5;
        this.bi = f6;
        this.bj = list == null ? List.of() : List.copyOf(list);
        this.bk = new ArrayList<BedrockModelBone>();
    }

    public String name() {
        return this.bf;
    }

    public float aj() {
        return this.pivotX;
    }

    public float ak() {
        return this.pivotY;
    }

    public float al() {
        return this.pivotZ;
    }

    public float am() {
        return this.bg;
    }

    public float an() {
        return this.bh;
    }

    public float ao() {
        return this.bi;
    }

    public List<BedrockCubeDefinition> ap() {
        return this.bj;
    }

    public List<BedrockModelBone> aq() {
        return Collections.unmodifiableList(this.bk);
    }

    void a(BedrockModelBone bedrockModelBone) {
        if (bedrockModelBone != null) {
            this.bk.add(bedrockModelBone);
        }
    }
}

