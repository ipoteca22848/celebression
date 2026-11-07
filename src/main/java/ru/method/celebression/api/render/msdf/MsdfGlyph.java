package ru.method.celebression.api.render.msdf;

import net.minecraft.class_4588;
import org.joml.Matrix4f;
import ru.method.celebression.api.render.msdf.FontData;

public final class MsdfGlyph {
    private final int code;
    private final float minU;
    private final float maxU;
    private final float minV;
    private final float maxV;
    private final float advance;
    private final float topPosition;
    private final float width;
    private final float height;

    public MsdfGlyph(FontData.GlyphData glyphData, float f, float f2) {
        this.code = glyphData.unicode();
        this.advance = glyphData.advance();
        FontData.BoundsData boundsData = glyphData.atlasBounds();
        if (boundsData != null) {
            this.minU = boundsData.left() / f;
            this.maxU = boundsData.right() / f;
            this.minV = 1.0f - boundsData.top() / f2;
            this.maxV = 1.0f - boundsData.bottom() / f2;
        } else {
            this.maxV = 0.0f;
            this.minV = 0.0f;
            this.maxU = 0.0f;
            this.minU = 0.0f;
        }
        FontData.BoundsData boundsData2 = glyphData.planeBounds();
        if (boundsData2 != null) {
            this.width = boundsData2.right() - boundsData2.left();
            this.height = boundsData2.top() - boundsData2.bottom();
            this.topPosition = boundsData2.top();
        } else {
            this.topPosition = 0.0f;
            this.height = 0.0f;
            this.width = 0.0f;
        }
    }

    public float apply(Matrix4f matrix4f, class_4588 class_45882, float f, float f2, float f3, float f4, int n) {
        float f5 = this.width * f;
        float f6 = this.height * f;
        class_45882.method_22918(matrix4f, f2, f3 -= this.topPosition * f, f4).method_22913(this.minU, this.minV).method_39415(n);
        class_45882.method_22918(matrix4f, f2, f3 + f6, f4).method_22913(this.minU, this.maxV).method_39415(n);
        class_45882.method_22918(matrix4f, f2 + f5, f3 + f6, f4).method_22913(this.maxU, this.maxV).method_39415(n);
        class_45882.method_22918(matrix4f, f2 + f5, f3, f4).method_22913(this.maxU, this.minV).method_39415(n);
        return this.advance * f;
    }

    public float getWidth(float f) {
        return this.advance * f;
    }

    public float getHeight(float f) {
        return this.height * f;
    }

    public int getCharCode() {
        return this.code;
    }
}

