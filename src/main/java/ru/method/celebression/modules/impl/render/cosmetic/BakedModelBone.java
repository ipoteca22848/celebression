package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.List;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelCube;

public final class BakedModelBone {
    public final String aX;
    public final float aY;
    public final float aZ;
    public final float bA;
    public final float bB;
    public final float bC;
    public final float bD;
    public final List<BakedModelCube> bE;
    public final List<BakedModelBone> bF;

    public BakedModelBone(String string, float f, float f2, float f3, float f4, float f5, float f6, List<BakedModelCube> list, List<BakedModelBone> list2) {
        this.aX = string;
        this.aY = f;
        this.aZ = f2;
        this.bA = f3;
        this.bB = f4;
        this.bC = f5;
        this.bD = f6;
        this.bE = list == null ? List.of() : List.copyOf(list);
        this.bF = list2 == null ? List.of() : List.copyOf(list2);
    }
}

