package unkw.visualtest.client.hud;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_332;
import net.minecraft.class_4588;
import net.minecraft.class_9801;
import org.joml.Matrix4f;

final class RoundFontRenderer {
    private static final class_2960 METRICS = class_2960.method_60655((String)"visual-test", (String)"font/round.json");
    private static final class_2960 ATLAS = class_2960.method_60655((String)"visual-test", (String)"textures/font/round.png");
    private static final class_10156 PROGRAM = new class_10156(class_2960.method_60655((String)"visual-test", (String)"core/msdf_text"), class_290.field_1575, class_10149.field_53930);
    private static final List<TextCommand> COMMANDS = new ArrayList<TextCommand>();
    private static FontData data;

    private RoundFontRenderer() {
    }

    static void beginFrame() {
        COMMANDS.clear();
    }

    static void queue(String string, float f, float f2, float f3, int n) {
        if (!string.isEmpty() && f3 > 0.0f) {
            COMMANDS.add(new TextCommand(string, f, f2, f3, n));
        }
    }

    static float width(String string, float f) {
        FontData fontData = RoundFontRenderer.data();
        float f2 = 0.0f;
        float f3 = 0.0f;
        int n = 0;
        while (n < string.length()) {
            int n2 = string.codePointAt(n);
            n += Character.charCount(n2);
            if (n2 == 10) {
                f3 = Math.max(f3, f2);
                f2 = 0.0f;
                continue;
            }
            if (n2 == 13) continue;
            f2 += fontData.glyph(n2).advance() * f;
        }
        return Math.max(f3, f2);
    }

    static void flush(class_332 class_3322) {
        if (COMMANDS.isEmpty()) {
            return;
        }
        FontData fontData = RoundFontRenderer.data();
        Matrix4f matrix4f = class_3322.method_51448().method_23760().method_23761();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(PROGRAM);
        RenderSystem.setShaderTexture(0, ATLAS);
        class_287 buffer = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        for (TextCommand textCommand : COMMANDS) {
            RoundFontRenderer.drawCommand(buffer, matrix4f, fontData, textCommand);
        }
        class_286.method_43433((class_9801)buffer.method_60800());
        RenderSystem.disableBlend();
        COMMANDS.clear();
        RoundFontRenderer.resetTextureUnit0();
    }

    private static void resetTextureUnit0() {
        RenderSystem.activeTexture((int)33984);
        int n = class_310.method_1551().method_1522().method_30277();
        RenderSystem.bindTexture((int)n);
        RenderSystem.bindTexture((int)0);
        RenderSystem.setShaderTexture((int)0, (int)0);
    }

    private static void drawCommand(class_4588 class_45882, Matrix4f matrix4f, FontData fontData, TextCommand textCommand) {
        float f = textCommand.x();
        float f2 = textCommand.y() + fontData.ascender() * textCommand.size();
        int n = 0;
        while (n < textCommand.value().length()) {
            int n2 = textCommand.value().codePointAt(n);
            n += Character.charCount(n2);
            if (n2 == 10) {
                f = textCommand.x();
                f2 += fontData.lineHeight() * textCommand.size();
                continue;
            }
            if (n2 == 13) continue;
            Glyph glyph = fontData.glyph(n2);
            if (glyph.plane() != null && glyph.atlas() != null) {
                float f3 = f + glyph.plane().left() * textCommand.size();
                float f4 = f + glyph.plane().right() * textCommand.size();
                float f5 = f2 - glyph.plane().top() * textCommand.size();
                float f6 = f2 - glyph.plane().bottom() * textCommand.size();
                float f7 = glyph.atlas().left() / fontData.atlasWidth();
                float f8 = glyph.atlas().right() / fontData.atlasWidth();
                float f9 = (fontData.atlasHeight() - glyph.atlas().top()) / fontData.atlasHeight();
                float f10 = (fontData.atlasHeight() - glyph.atlas().bottom()) / fontData.atlasHeight();
                class_45882.method_22918(matrix4f, f3, f5, 0.0f).method_22913(f7, f9).method_39415(textCommand.color());
                class_45882.method_22918(matrix4f, f3, f6, 0.0f).method_22913(f7, f10).method_39415(textCommand.color());
                class_45882.method_22918(matrix4f, f4, f6, 0.0f).method_22913(f8, f10).method_39415(textCommand.color());
                class_45882.method_22918(matrix4f, f4, f5, 0.0f).method_22913(f8, f9).method_39415(textCommand.color());
            }
            f += glyph.advance() * textCommand.size();
        }
    }

    private static FontData data() {
        if (data == null) {
            data = RoundFontRenderer.loadData();
        }
        return data;
    }

    private static FontData loadData() {
        class_3298 resource = (class_3298)class_310.method_1551().method_1478().method_14486(METRICS).orElseThrow(() -> new IllegalStateException("Missing round font metrics"));
        try (BufferedReader reader = resource.method_43039()) {
            return FontData.read(reader);
        }
        catch (IOException exception) {
            throw new IllegalStateException("Unable to read round font metrics", exception);
        }
    }

    record TextCommand(String value, float x, float y, float size, int color) {
    }

    record FontData(float atlasWidth, float atlasHeight, float ascender, float lineHeight, Map<Integer, Glyph> glyphs, Glyph fallback) {
        static FontData read(Reader reader) {
            JsonObject jsonObject = JsonParser.parseReader((Reader)reader).getAsJsonObject();
            JsonObject jsonObject2 = jsonObject.getAsJsonObject("atlas");
            if (!"mtsdf".equals(jsonObject2.get("type").getAsString()) || !"bottom".equals(jsonObject2.get("yOrigin").getAsString())) {
                throw new IllegalStateException("Unsupported round font atlas");
            }
            float f = jsonObject2.get("width").getAsFloat();
            float f2 = jsonObject2.get("height").getAsFloat();
            JsonObject jsonObject3 = jsonObject.getAsJsonObject("metrics");
            float f3 = jsonObject3.get("ascender").getAsFloat();
            float f4 = jsonObject3.get("lineHeight").getAsFloat();
            HashMap<Integer, Glyph> hashMap = new HashMap<>();
            JsonArray jsonArray = jsonObject.getAsJsonArray("glyphs");
            jsonArray.forEach(jsonElement -> {
                JsonObject glyphObject = jsonElement.getAsJsonObject();
                int n = glyphObject.get("unicode").getAsInt();
                float advance = glyphObject.get("advance").getAsFloat();
                hashMap.put(n, new Glyph(advance, FontData.bounds(glyphObject, "planeBounds"), FontData.bounds(glyphObject, "atlasBounds")));
            });
            Glyph glyph = (Glyph)hashMap.get(63);
            if (glyph == null) {
                throw new IllegalStateException("Round font has no fallback glyph");
            }
            return new FontData(f, f2, f3, f4, Map.copyOf(hashMap), glyph);
        }

        private static Bounds bounds(JsonObject jsonObject, String string) {
            if (!jsonObject.has(string)) {
                return null;
            }
            JsonObject jsonObject2 = jsonObject.getAsJsonObject(string);
            return new Bounds(jsonObject2.get("left").getAsFloat(), jsonObject2.get("bottom").getAsFloat(), jsonObject2.get("right").getAsFloat(), jsonObject2.get("top").getAsFloat());
        }

        Glyph glyph(int n) {
            return this.glyphs.getOrDefault(n, this.fallback);
        }
    }

    record Glyph(float advance, Bounds plane, Bounds atlas) {
    }

    record Bounds(float left, float bottom, float right, float top) {
    }
}
