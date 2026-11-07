package ru.method.celebression.modules.impl.render.cosmetic;

import ru.method.celebression.modules.impl.render.cosmetic.BakedModelVertex;

public final class BakedModelQuad {
    public final BakedModelVertex[] bY;
    public final float bZ;
    public final float ca;
    public final float cb;

    public BakedModelQuad(BakedModelVertex[] bakedModelVertexArray, float f, float f2, float f3) {
        this.bY = bakedModelVertexArray;
        this.bZ = f;
        this.ca = f2;
        this.cb = f3;
    }
}

