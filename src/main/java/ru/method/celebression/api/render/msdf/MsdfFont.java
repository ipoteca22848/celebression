package ru.method.celebression.api.render.msdf;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.class_1044;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4588;
import org.joml.Matrix4f;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.render.msdf.FontData;
import ru.method.celebression.api.render.msdf.MsdfGlyph;
import ru.method.celebression.api.render.providers.ResourceProvider;

public final class MsdfFont {
    private final String name;
    private final class_1044 texture;
    private final FontData.AtlasData atlas;
    private final FontData.MetricsData metrics;
    private final Int2ObjectOpenHashMap<MsdfGlyph> glyphs;
    private final Int2ObjectOpenHashMap<Int2FloatOpenHashMap> kernings;
    private volatile boolean linearFilteringApplied;

    MsdfFont(String string, class_1044 class_10442, FontData.AtlasData atlasData, FontData.MetricsData metricsData, Int2ObjectOpenHashMap<MsdfGlyph> int2ObjectOpenHashMap, Int2ObjectOpenHashMap<Int2FloatOpenHashMap> int2ObjectOpenHashMap2) {
        this.name = string;
        this.texture = class_10442;
        this.atlas = atlasData;
        this.metrics = metricsData;
        this.glyphs = int2ObjectOpenHashMap;
        this.kernings = int2ObjectOpenHashMap2;
        RenderSystem.recordRenderCall(this::ensureLinearFiltering);
    }

    public int getTextureId() {
        return this.texture.method_4624();
    }

    public void ensureLinearFiltering() {
        if (this.linearFilteringApplied) {
            return;
        }
        RenderSystem.assertOnRenderThread();
        int n = this.texture.method_4624();
        if (n <= 0) {
            return;
        }
        this.texture.method_4527(true, false);
        this.linearFilteringApplied = true;
    }

    public void applyGlyphs(Matrix4f matrix4f, class_4588 class_45882, String string, float f, float f2, float f3, float f4, float f5, float f6, int n) {
        int n2 = -1;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            MsdfGlyph msdfGlyph = (MsdfGlyph)this.glyphs.get((int)c);
            if (msdfGlyph == null) continue;
            Int2FloatOpenHashMap int2FloatOpenHashMap = (Int2FloatOpenHashMap)this.kernings.get(n2);
            if (int2FloatOpenHashMap != null) {
                f4 += int2FloatOpenHashMap.get((int)c) * f;
            }
            f4 += msdfGlyph.apply(matrix4f, class_45882, f, f4, f5, f6, n) + f2 + f3;
            n2 = c;
        }
    }

    public float getWidth(String string, float f) {
        int n = -1;
        float f2 = 0.0f;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            MsdfGlyph msdfGlyph = (MsdfGlyph)this.glyphs.get((int)c);
            if (msdfGlyph == null) continue;
            Int2FloatOpenHashMap int2FloatOpenHashMap = (Int2FloatOpenHashMap)this.kernings.get(n);
            if (int2FloatOpenHashMap != null) {
                f2 += int2FloatOpenHashMap.get((int)c) * f;
            }
            f2 += msdfGlyph.getWidth(f) * 1.05f;
            n = c;
        }
        return f2;
    }

    public float getHeight(float f) {
        return (this.metrics.ascender() * f + Math.abs(this.metrics.descender() * f)) * 1.05f;
    }

    public String getName() {
        return this.name;
    }

    public FontData.AtlasData getAtlas() {
        return this.atlas;
    }

    public FontData.MetricsData getMetrics() {
        return this.metrics;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name = "?";
        private class_2960 dataIdentifer;
        private class_2960 atlasIdentifier;

        Builder() {
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder data(String string) {
            this.dataIdentifer = Celebression.id("fonts/" + string + ".json");
            return this;
        }

        public Builder atlas(String string) {
            this.atlasIdentifier = Celebression.id("fonts/" + string + ".png");
            return this;
        }

        public MsdfFont build() {
            FontData fontData = ResourceProvider.fromJsonToInstance(this.dataIdentifer, FontData.class);
            class_1044 class_10442 = class_310.method_1551().method_1531().method_4619(this.atlasIdentifier);
            if (fontData == null) {
                throw new RuntimeException("Failed to read font data file: " + this.dataIdentifer.toString() + "; Are you sure this is json file? Try to check the correctness of its syntax.");
            }
            float f = fontData.atlas().width();
            float f2 = fontData.atlas().height();
            Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap();
            for (FontData.GlyphData glyphData : fontData.glyphs()) {
                int2ObjectOpenHashMap.put(glyphData.unicode(), (Object)new MsdfGlyph(glyphData, f, f2));
            }
            Int2ObjectOpenHashMap int2ObjectOpenHashMap2 = new Int2ObjectOpenHashMap();
            fontData.kernings().forEach(kerningData -> {
                Int2FloatOpenHashMap int2FloatOpenHashMap = (Int2FloatOpenHashMap)int2ObjectOpenHashMap2.get(kerningData.leftChar());
                if (int2FloatOpenHashMap == null) {
                    int2FloatOpenHashMap = new Int2FloatOpenHashMap();
                    int2FloatOpenHashMap.defaultReturnValue(0.0f);
                    int2ObjectOpenHashMap2.put(kerningData.leftChar(), (Object)int2FloatOpenHashMap);
                }
                int2FloatOpenHashMap.put(kerningData.rightChar(), kerningData.advance());
            });
            return new MsdfFont(this.name, class_10442, fontData.atlas(), fontData.metrics(), (Int2ObjectOpenHashMap<MsdfGlyph>)int2ObjectOpenHashMap, (Int2ObjectOpenHashMap<Int2FloatOpenHashMap>)int2ObjectOpenHashMap2);
        }
    }
}

