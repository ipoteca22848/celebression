package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.List;
import ru.method.celebression.modules.impl.render.cosmetic.BakedModelQuad;

public final class BakedModelCube {
    public final float bG;
    public final float bH;
    public final float bI;
    public final float bJ;
    public final float bK;
    public final float bL;
    public final float bM;
    public final float bN;
    public final float bO;
    public final List<BakedModelQuad> bP;

    public BakedModelCube(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, List<BakedModelQuad> list) {
        this.bG = f;
        this.bH = f2;
        this.bI = f3;
        this.bJ = f4;
        this.bK = f5;
        this.bL = f6;
        this.bM = f7;
        this.bN = f8;
        this.bO = f9;
        this.bP = list == null ? List.of() : List.copyOf(list);
    }
}

