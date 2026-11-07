package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.animation.Animation;

import java.util.List;
import net.minecraft.class_2960;
import ru.method.celebression.animation.AnimationLibrary;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelBone;
import ru.method.celebression.modules.impl.render.cosmetic.BedrockModelBone;
import ru.method.celebression.modules.impl.render.cosmetic.CosmeticModelAnchor;

public final class CosmeticModel {
    private final class_2960 M;
    private final CosmeticModelAnchor N;
    private final float offsetX;
    private final float O;
    private final float offsetZ;
    private final float scale;
    private final float P;
    private final float Q;
    private final float roll;
    private final float R;
    private final float S;
    private final int textureWidth;
    private final int textureHeight;
    private final List<BedrockModelBone> T;
    private final List<BakedModelBone> U;
    private final AnimationLibrary V;

    public CosmeticModel(class_2960 class_29602, CosmeticModelAnchor cosmeticModelAnchor, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n, int n2, List<BedrockModelBone> list, List<BakedModelBone> list2, AnimationLibrary animationLibrary) {
        this.M = class_29602;
        this.N = cosmeticModelAnchor;
        this.offsetX = f;
        this.O = f2;
        this.offsetZ = f3;
        this.scale = f4;
        this.P = f5;
        this.Q = f6;
        this.roll = f7;
        this.R = f8;
        this.S = f9;
        this.textureWidth = Math.max(1, n);
        this.textureHeight = Math.max(1, n2);
        this.T = list == null ? List.of() : List.copyOf(list);
        this.U = list2 == null ? List.of() : List.copyOf(list2);
        this.V = animationLibrary == null ? AnimationLibrary.bx() : animationLibrary;
    }

    public class_2960 texture() {
        return this.M;
    }

    public CosmeticModelAnchor be() {
        return this.N;
    }

    public float bf() {
        return this.offsetX;
    }

    public float bg() {
        return this.O;
    }

    public float bh() {
        return this.offsetZ;
    }

    public float scale() {
        return this.scale;
    }

    public float bi() {
        return this.P;
    }

    public float bj() {
        return this.Q;
    }

    public float bk() {
        return this.roll;
    }

    public float bl() {
        return this.R;
    }

    public float bm() {
        return this.S;
    }

    public int bn() {
        return this.textureWidth;
    }

    public int bo() {
        return this.textureHeight;
    }

    public List<BedrockModelBone> bp() {
        return this.T;
    }

    public List<BakedModelBone> bq() {
        return this.U;
    }

    public AnimationLibrary br() {
        return this.V;
    }

    public boolean isEmpty() {
        return this.U.isEmpty();
    }

    public boolean bs() {
        return !this.V.isEmpty();
    }
}

