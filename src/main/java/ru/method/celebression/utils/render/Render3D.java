package ru.method.celebression.utils.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_265;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import ru.method.celebression.api.mixins.accessors.IWorldRenderer;
import ru.method.celebression.api.accessor.MinecraftClientAccess;

public final class Render3D
implements MinecraftClientAccess {
    public static boolean rendering = false;
    public static final List<VertexCollection> QUADS = new ArrayList<VertexCollection>();
    public static final List<VertexCollection> DEPTH_QUADS = new ArrayList<VertexCollection>();
    public static final List<VertexCollection> LIGHTNING_QUADS = new ArrayList<VertexCollection>();
    public static final List<VertexCollection> DEBUG_LINES = new ArrayList<VertexCollection>();
    public static final List<VertexCollection> DEPTH_DEBUG_LINES = new ArrayList<VertexCollection>();
    public static final List<VertexCollection> SHINE_QUADS = new ArrayList<VertexCollection>();
    public static final List<VertexCollection> DEPTH_SHINE_QUADS = new ArrayList<VertexCollection>();
    public static final List<VertexCollection> SHINE_DEBUG_LINES = new ArrayList<VertexCollection>();
    public static final List<VertexCollection> DEPTH_SHINE_DEBUG_LINES = new ArrayList<VertexCollection>();
    public static final List<TexturedQuad> TEXTURED_QUADS = new ArrayList<TexturedQuad>();
    public static final List<TexturedQuad> TEXTURED_QUADS_ADDITIVE = new ArrayList<TexturedQuad>();
    public static final List<TexturedQuad> TEXTURED_QUADS_DEPTH = new ArrayList<TexturedQuad>();
    public static final List<TexturedQuad> TEXTURED_QUADS_DEPTH_ADDITIVE = new ArrayList<TexturedQuad>();
    public static final List<VariableLine> VARIABLE_LINES = new ArrayList<VariableLine>();
    public static final List<VariableLine> DEPTH_VARIABLE_LINES = new ArrayList<VariableLine>();
    private static final Map<class_2960, List<TexturedQuad>> textureBatches = new LinkedHashMap<class_2960, List<TexturedQuad>>();
    private static final int SHAPE_CACHE_CAPACITY = 256;
    private static final Map<class_265, ShapeCache> SHAPE_CACHE = new LinkedHashMap<class_265, ShapeCache>(64, 0.75f, true){

        @Override
        protected boolean removeEldestEntry(Map.Entry<class_265, ShapeCache> entry) {
            return this.size() > 256;
        }
    };
    private static Matrix4f lastProjectionMatrix = new Matrix4f();
    private static Matrix4f lastWorldSpaceMatrix = new Matrix4f();
    private static final class_4587 billboardScratch = new class_4587();
    private static final int MAX_TEXTURE_BATCHES = 256;
    private static float[] widthScratch = new float[8];

    public static void setLastProjectionMatrix(Matrix4f matrix4f) {
        if (matrix4f == null) {
            return;
        }
        lastProjectionMatrix.set((Matrix4fc)matrix4f);
    }

    public static Matrix4f getLastProjectionMatrix() {
        return new Matrix4f((Matrix4fc)lastProjectionMatrix);
    }

    public static void setLastWorldSpaceMatrix(class_4587.class_4665 class_46652) {
        if (class_46652 == null) {
            return;
        }
        lastWorldSpaceMatrix.set((Matrix4fc)class_46652.method_23761());
    }

    public static Matrix4f getLastWorldSpaceMatrix() {
        return new Matrix4f((Matrix4fc)lastWorldSpaceMatrix);
    }

    public static void renderCube(class_4587 class_45872, class_243 class_2432, double d, boolean bl, Color color, boolean bl2, Color color2) {
        if (bl2) {
            Render3D.renderBoxOutline(class_45872, new class_238(class_2432.method_1031(-0.5 * d, -0.5 * d, -0.5 * d), class_2432.method_1031(0.5 * d, 0.5 * d, 0.5 * d)), color2);
        }
        if (bl) {
            Render3D.renderBox(class_45872, new class_238(class_2432.method_1031(-0.5 * d, -0.5 * d, -0.5 * d), class_2432.method_1031(0.5 * d, 0.5 * d, 0.5 * d)), color);
        }
    }

    public static void renderCube(class_4587 class_45872, class_238 class_2383, boolean bl, Color color, boolean bl2, Color color2) {
        if (bl2) {
            Render3D.renderBoxOutline(class_45872, class_2383, color2);
        }
        if (bl) {
            Render3D.renderBox(class_45872, class_2383, color);
        }
    }

    public static void drawLine(class_4587 class_45872, class_243 class_2432, class_243 class_2433, Color color) {
        Render3D.drawLine(class_45872, class_2432, class_2433, color, false);
    }

    public static void drawLine(class_4587 class_45872, class_243 class_2432, class_243 class_2433, Color color, boolean bl) {
        if (!rendering) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_243 class_2434 = Render3D.aiG.field_1773.method_19418().method_19326();
        float f = (float)(class_2432.field_1352 - class_2434.field_1352);
        float f2 = (float)(class_2432.field_1351 - class_2434.field_1351);
        float f3 = (float)(class_2432.field_1350 - class_2434.field_1350);
        float f4 = (float)(class_2433.field_1352 - class_2434.field_1352);
        float f5 = (float)(class_2433.field_1351 - class_2434.field_1351);
        float f6 = (float)(class_2433.field_1350 - class_2434.field_1350);
        (bl ? DEPTH_DEBUG_LINES : DEBUG_LINES).add(new VertexCollection(new Vertex(matrix4f, f, f2, f3, color.getRGB()), new Vertex(matrix4f, f4, f5, f6, color.getRGB())));
    }

    public static void drawLine(class_4587 class_45872, class_243 class_2432, class_243 class_2433, Color color, Color color2, float f, boolean bl) {
        if (color == null || color2 == null) {
            return;
        }
        Render3D.drawLine(class_45872, class_2432, class_2433, color.getRGB(), color2.getRGB(), f, bl);
    }

    public static void drawLine(class_4587 class_45872, class_243 class_2432, class_243 class_2433, Color color, float f, boolean bl) {
        if (color == null) {
            return;
        }
        int n = color.getRGB();
        Render3D.drawLine(class_45872, class_2432, class_2433, n, n, f, bl);
    }

    public static void drawLine(class_4587 class_45872, class_243 class_2432, class_243 class_2433, int n, float f, boolean bl) {
        Render3D.drawLine(class_45872, class_2432, class_2433, n, n, f, bl);
    }

    public static void drawLine(class_4587 class_45872, class_243 class_2432, class_243 class_2433, int n, int n2, float f, boolean bl) {
        if (!rendering) {
            return;
        }
        if (class_45872 == null || class_2432 == null || class_2433 == null) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_243 class_2434 = Render3D.aiG.field_1773.method_19418().method_19326();
        float f2 = (float)(class_2432.field_1352 - class_2434.field_1352);
        float f3 = (float)(class_2432.field_1351 - class_2434.field_1351);
        float f4 = (float)(class_2432.field_1350 - class_2434.field_1350);
        float f5 = (float)(class_2433.field_1352 - class_2434.field_1352);
        float f6 = (float)(class_2433.field_1351 - class_2434.field_1351);
        float f7 = (float)(class_2433.field_1350 - class_2434.field_1350);
        VariableLine variableLine = new VariableLine(new Vertex(matrix4f, f2, f3, f4, n), new Vertex(matrix4f, f5, f6, f7, n2), Math.max(1.0f, f));
        (bl ? DEPTH_VARIABLE_LINES : VARIABLE_LINES).add(variableLine);
    }

    public static void renderBox(class_4587 class_45872, class_238 class_2383, Color color) {
        Render3D.renderGradientBox(class_45872, class_2383, color, color);
    }

    public static void renderBoxOutline(class_4587 class_45872, class_238 class_2383, Color color) {
        Render3D.renderGradientBoxOutline(class_45872, class_2383, color, color);
    }

    public static void renderTriangle(class_4587 class_45872, class_243 class_2432, class_243 class_2433, class_243 class_2434, Color color) {
        Render3D.renderTriangle(class_45872, class_2432, class_2433, class_2434, color, false);
    }

    public static void renderTriangle(class_4587 class_45872, class_243 class_2432, class_243 class_2433, class_243 class_2434, Color color, boolean bl) {
        if (!rendering) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_243 class_2435 = Render3D.aiG.field_1773.method_19418().method_19326();
        float f = (float)(class_2432.field_1352 - class_2435.field_1352);
        float f2 = (float)(class_2432.field_1351 - class_2435.field_1351);
        float f3 = (float)(class_2432.field_1350 - class_2435.field_1350);
        float f4 = (float)(class_2433.field_1352 - class_2435.field_1352);
        float f5 = (float)(class_2433.field_1351 - class_2435.field_1351);
        float f6 = (float)(class_2433.field_1350 - class_2435.field_1350);
        float f7 = (float)(class_2434.field_1352 - class_2435.field_1352);
        float f8 = (float)(class_2434.field_1351 - class_2435.field_1351);
        float f9 = (float)(class_2434.field_1350 - class_2435.field_1350);
        (bl ? DEPTH_QUADS : QUADS).add(new VertexCollection(new Vertex(matrix4f, f, f2, f3, color.getRGB()), new Vertex(matrix4f, f4, f5, f6, color.getRGB()), new Vertex(matrix4f, f7, f8, f9, color.getRGB()), new Vertex(matrix4f, f, f2, f3, color.getRGB())));
    }

    public static void renderGradientBox(class_4587 class_45872, class_238 class_2383, Color color, Color color2) {
        if (!rendering) {
            return;
        }
        if (!Render3D.isVisible(class_2383)) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_2383 = Render3D.cameraTransform(class_2383, matrix4f);
        QUADS.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB())));
        QUADS.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB())));
        QUADS.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB())));
        QUADS.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB())));
        QUADS.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB())));
        QUADS.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB())));
    }

    public static void renderCircle(class_4587 class_45872, class_243 class_2432, double d, Color color) {
        if (!rendering) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_243 class_2433 = Render3D.aiG.field_1773.method_19418().method_19326();
        int n = 64;
        for (int i = 0; i < n; ++i) {
            double d2 = Math.PI * 2 * (double)i / (double)n;
            double d3 = Math.PI * 2 * (double)(i + 1) / (double)n;
            double d4 = class_2432.field_1352 + d * Math.cos(d2) - class_2433.field_1352;
            double d5 = class_2432.field_1350 + d * Math.sin(d2) - class_2433.field_1350;
            double d6 = class_2432.field_1352 + d * Math.cos(d3) - class_2433.field_1352;
            double d7 = class_2432.field_1350 + d * Math.sin(d3) - class_2433.field_1350;
            double d8 = class_2432.field_1351 - class_2433.field_1351;
            DEBUG_LINES.add(new VertexCollection(new Vertex(matrix4f, (float)d4, (float)d8, (float)d5, color.getRGB()), new Vertex(matrix4f, (float)d6, (float)d8, (float)d7, color.getRGB())));
        }
    }

    public static void renderGradientBoxOutline(class_4587 class_45872, class_238 class_2383, Color color, Color color2) {
        if (!rendering) {
            return;
        }
        if (!Render3D.isVisible(class_2383)) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_2383 = Render3D.cameraTransform(class_2383, matrix4f);
        DEBUG_LINES.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB())));
        DEBUG_LINES.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB())));
        DEBUG_LINES.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB())));
        DEBUG_LINES.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB())));
        DEBUG_LINES.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1321, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1321, color.getRGB())));
        DEBUG_LINES.add(new VertexCollection(new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1320, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1322, (float)class_2383.field_1324, color2.getRGB()), new Vertex(matrix4f, (float)class_2383.field_1323, (float)class_2383.field_1325, (float)class_2383.field_1324, color.getRGB())));
    }

    public static void drawQuad(class_4587 class_45872, class_243 class_2432, class_243 class_2433, class_243 class_2434, class_243 class_2435, Color color, boolean bl) {
        if (!rendering) {
            return;
        }
        if (class_45872 == null || class_2432 == null || class_2433 == null || class_2434 == null || class_2435 == null || color == null) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_243 class_2436 = Render3D.aiG.field_1773.method_19418().method_19326();
        Vertex vertex = new Vertex(matrix4f, (float)(class_2432.field_1352 - class_2436.field_1352), (float)(class_2432.field_1351 - class_2436.field_1351), (float)(class_2432.field_1350 - class_2436.field_1350), color.getRGB());
        Vertex vertex2 = new Vertex(matrix4f, (float)(class_2433.field_1352 - class_2436.field_1352), (float)(class_2433.field_1351 - class_2436.field_1351), (float)(class_2433.field_1350 - class_2436.field_1350), color.getRGB());
        Vertex vertex3 = new Vertex(matrix4f, (float)(class_2434.field_1352 - class_2436.field_1352), (float)(class_2434.field_1351 - class_2436.field_1351), (float)(class_2434.field_1350 - class_2436.field_1350), color.getRGB());
        Vertex vertex4 = new Vertex(matrix4f, (float)(class_2435.field_1352 - class_2436.field_1352), (float)(class_2435.field_1351 - class_2436.field_1351), (float)(class_2435.field_1350 - class_2436.field_1350), color.getRGB());
        (bl ? DEPTH_QUADS : QUADS).add(new VertexCollection(vertex, vertex2, vertex3, vertex4));
    }

    public static void drawQuad(class_4587 class_45872, class_243 class_2432, class_243 class_2433, class_243 class_2434, class_243 class_2435, int n, boolean bl) {
        Render3D.drawQuad(class_45872, class_2432, class_2433, class_2434, class_2435, Render3D.fromArgb(n), bl);
    }

    public static void drawLightningQuad(class_4587 class_45872, class_243 class_2432, class_243 class_2433, class_243 class_2434, class_243 class_2435, int n) {
        if (!rendering) {
            return;
        }
        if (class_45872 == null || class_2432 == null || class_2433 == null || class_2434 == null || class_2435 == null) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_243 class_2436 = Render3D.aiG.field_1773.method_19418().method_19326();
        Vertex vertex = new Vertex(matrix4f, (float)(class_2432.field_1352 - class_2436.field_1352), (float)(class_2432.field_1351 - class_2436.field_1351), (float)(class_2432.field_1350 - class_2436.field_1350), n);
        Vertex vertex2 = new Vertex(matrix4f, (float)(class_2433.field_1352 - class_2436.field_1352), (float)(class_2433.field_1351 - class_2436.field_1351), (float)(class_2433.field_1350 - class_2436.field_1350), n);
        Vertex vertex3 = new Vertex(matrix4f, (float)(class_2434.field_1352 - class_2436.field_1352), (float)(class_2434.field_1351 - class_2436.field_1351), (float)(class_2434.field_1350 - class_2436.field_1350), n);
        Vertex vertex4 = new Vertex(matrix4f, (float)(class_2435.field_1352 - class_2436.field_1352), (float)(class_2435.field_1351 - class_2436.field_1351), (float)(class_2435.field_1350 - class_2436.field_1350), n);
        LIGHTNING_QUADS.add(new VertexCollection(vertex, vertex2, vertex3, vertex4));
    }

    public static void drawLightning() {
        if (LIGHTNING_QUADS.isEmpty()) {
            return;
        }
        Render3D.drawDepth(LIGHTNING_QUADS, List.of(), true);
    }

    public static void drawGradientQuad(class_4587 class_45872, class_243 class_2432, class_243 class_2433, class_243 class_2434, class_243 class_2435, Color color, Color color2, Color color3, Color color4, boolean bl) {
        if (!rendering) {
            return;
        }
        if (class_45872 == null || class_2432 == null || class_2433 == null || class_2434 == null || class_2435 == null || color == null || color2 == null || color3 == null || color4 == null) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_243 class_2436 = Render3D.aiG.field_1773.method_19418().method_19326();
        Vertex vertex = new Vertex(matrix4f, (float)(class_2432.field_1352 - class_2436.field_1352), (float)(class_2432.field_1351 - class_2436.field_1351), (float)(class_2432.field_1350 - class_2436.field_1350), color.getRGB());
        Vertex vertex2 = new Vertex(matrix4f, (float)(class_2433.field_1352 - class_2436.field_1352), (float)(class_2433.field_1351 - class_2436.field_1351), (float)(class_2433.field_1350 - class_2436.field_1350), color2.getRGB());
        Vertex vertex3 = new Vertex(matrix4f, (float)(class_2434.field_1352 - class_2436.field_1352), (float)(class_2434.field_1351 - class_2436.field_1351), (float)(class_2434.field_1350 - class_2436.field_1350), color3.getRGB());
        Vertex vertex4 = new Vertex(matrix4f, (float)(class_2435.field_1352 - class_2436.field_1352), (float)(class_2435.field_1351 - class_2436.field_1351), (float)(class_2435.field_1350 - class_2436.field_1350), color4.getRGB());
        (bl ? DEPTH_QUADS : QUADS).add(new VertexCollection(vertex, vertex2, vertex3, vertex4));
    }

    public static void drawGradientQuad(class_4587 class_45872, class_243 class_2432, class_243 class_2433, class_243 class_2434, class_243 class_2435, int n, int n2, int n3, int n4, boolean bl) {
        Render3D.drawGradientQuad(class_45872, class_2432, class_2433, class_2434, class_2435, Render3D.fromArgb(n), Render3D.fromArgb(n2), Render3D.fromArgb(n3), Render3D.fromArgb(n4), bl);
    }

    public static void drawBox(class_4587 class_45872, class_238 class_2383, Color color, float f, boolean bl, boolean bl2, boolean bl3) {
        if (!rendering) {
            return;
        }
        if (class_45872 == null || class_2383 == null || color == null) {
            return;
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_243 class_2432 = Render3D.aiG.field_1773.method_19418().method_19326();
        class_238 class_2384 = class_2383.method_1014(0.001);
        float f2 = (float)(class_2384.field_1323 - class_2432.field_1352);
        float f3 = (float)(class_2384.field_1322 - class_2432.field_1351);
        float f4 = (float)(class_2384.field_1321 - class_2432.field_1350);
        float f5 = (float)(class_2384.field_1320 - class_2432.field_1352);
        float f6 = (float)(class_2384.field_1325 - class_2432.field_1351);
        float f7 = (float)(class_2384.field_1324 - class_2432.field_1350);
        int n = color.getRGB();
        if (bl2) {
            int n2 = Render3D.multiplyAlpha(n, 0.1f);
            List<VertexCollection> quadList = bl3 ? DEPTH_QUADS : QUADS;
            Render3D.quad(quadList, matrix4f, f2, f3, f4, f5, f3, f4, f5, f3, f7, f2, f3, f7, n2);
            Render3D.quad(quadList, matrix4f, f2, f3, f4, f2, f6, f4, f5, f6, f4, f5, f3, f4, n2);
            Render3D.quad(quadList, matrix4f, f5, f3, f4, f5, f6, f4, f5, f6, f7, f5, f3, f7, n2);
            Render3D.quad(quadList, matrix4f, f2, f3, f7, f5, f3, f7, f5, f6, f7, f2, f6, f7, n2);
            Render3D.quad(quadList, matrix4f, f2, f3, f4, f2, f3, f7, f2, f6, f7, f2, f6, f4, n2);
            Render3D.quad(quadList, matrix4f, f2, f6, f4, f2, f6, f7, f5, f6, f7, f5, f6, f4, n2);
        }
        if (bl) {
            float f8 = Math.max(1.0f, f);
            List<VariableLine> lineList = bl3 ? DEPTH_VARIABLE_LINES : VARIABLE_LINES;
            Render3D.edge(lineList, matrix4f, f2, f3, f4, f5, f3, f4, n, f8);
            Render3D.edge(lineList, matrix4f, f5, f3, f4, f5, f3, f7, n, f8);
            Render3D.edge(lineList, matrix4f, f5, f3, f7, f2, f3, f7, n, f8);
            Render3D.edge(lineList, matrix4f, f2, f3, f7, f2, f3, f4, n, f8);
            Render3D.edge(lineList, matrix4f, f2, f3, f7, f2, f6, f7, n, f8);
            Render3D.edge(lineList, matrix4f, f2, f3, f4, f2, f6, f4, n, f8);
            Render3D.edge(lineList, matrix4f, f5, f3, f7, f5, f6, f7, n, f8);
            Render3D.edge(lineList, matrix4f, f5, f3, f4, f5, f6, f4, n, f8);
            Render3D.edge(lineList, matrix4f, f2, f6, f4, f5, f6, f4, n, f8);
            Render3D.edge(lineList, matrix4f, f5, f6, f4, f5, f6, f7, n, f8);
            Render3D.edge(lineList, matrix4f, f5, f6, f7, f2, f6, f7, n, f8);
            Render3D.edge(lineList, matrix4f, f2, f6, f7, f2, f6, f4, n, f8);
        }
    }

    private static void quad(List<VertexCollection> list, Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, int n) {
        list.add(new VertexCollection(new Vertex(matrix4f, f, f2, f3, n), new Vertex(matrix4f, f4, f5, f6, n), new Vertex(matrix4f, f7, f8, f9, n), new Vertex(matrix4f, f10, f11, f12, n)));
    }

    private static void edge(List<VariableLine> list, Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, float f6, int n, float f7) {
        list.add(new VariableLine(new Vertex(matrix4f, f, f2, f3, n), new Vertex(matrix4f, f4, f5, f6, n), f7));
    }

    public static void drawBox(class_4587 class_45872, class_238 class_2383, int n, float f, boolean bl, boolean bl2, boolean bl3) {
        Render3D.drawBox(class_45872, class_2383, Render3D.fromArgb(n), f, bl, bl2, bl3);
    }

    public static void drawShape(class_4587 class_45872, class_2338 class_23382, class_265 class_2652, Color color, float f, boolean bl, boolean bl2) {
        if (!rendering) {
            return;
        }
        if (class_45872 == null || class_23382 == null || class_2652 == null || class_2652.method_1110() || color == null) {
            return;
        }
        ShapeCache shapeCache = SHAPE_CACHE.computeIfAbsent(class_2652, Render3D::buildShapeCache);
        double d = class_23382.method_10263();
        double d2 = class_23382.method_10264();
        double d3 = class_23382.method_10260();
        if (bl) {
            for (class_238 class_24322 : shapeCache.boxes()) {
                Render3D.drawBox(class_45872, class_24322.method_989(d, d2, d3), color, f, false, true, bl2);
            }
        }
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_243 class_2432 = Render3D.aiG.field_1773.method_19418().method_19326();
        int n = color.getRGB();
        float f2 = Math.max(1.0f, f);
        List<VariableLine> list = bl2 ? DEPTH_VARIABLE_LINES : VARIABLE_LINES;
        for (ShapeLine shapeLine : shapeCache.lines()) {
            Render3D.edge(list, matrix4f, (float)(shapeLine.start().field_1352 + d - class_2432.field_1352), (float)(shapeLine.start().field_1351 + d2 - class_2432.field_1351), (float)(shapeLine.start().field_1350 + d3 - class_2432.field_1350), (float)(shapeLine.end().field_1352 + d - class_2432.field_1352), (float)(shapeLine.end().field_1351 + d2 - class_2432.field_1351), (float)(shapeLine.end().field_1350 + d3 - class_2432.field_1350), n, f2);
        }
    }

    public static void drawShape(class_4587 class_45872, class_2338 class_23382, class_265 class_2652, int n, float f, boolean bl, boolean bl2) {
        Render3D.drawShape(class_45872, class_23382, class_2652, Render3D.fromArgb(n), f, bl, bl2);
    }

    private static ShapeCache buildShapeCache(class_265 class_2652) {
        List list = class_2652.method_1090();
        ArrayList<ShapeLine> arrayList = new ArrayList<ShapeLine>();
        class_2652.method_1104((d, d2, d3, d4, d5, d6) -> arrayList.add(new ShapeLine(new class_243(d, d2, d3), new class_243(d4, d5, d6))));
        return new ShapeCache(list, arrayList);
    }

    private static int multiplyAlpha(int n, float f) {
        int n2 = Math.max(0, Math.min(255, Math.round((float)(n >>> 24 & 0xFF) * f)));
        return n2 << 24 | n & 0xFFFFFF;
    }

    private static Color fromArgb(int n) {
        return new Color(n, true);
    }

    public static void prepare() {
        QUADS.clear();
        DEPTH_QUADS.clear();
        LIGHTNING_QUADS.clear();
        DEBUG_LINES.clear();
        DEPTH_DEBUG_LINES.clear();
        SHINE_QUADS.clear();
        DEPTH_SHINE_QUADS.clear();
        SHINE_DEBUG_LINES.clear();
        DEPTH_SHINE_DEBUG_LINES.clear();
        TEXTURED_QUADS.clear();
        TEXTURED_QUADS_ADDITIVE.clear();
        TEXTURED_QUADS_DEPTH.clear();
        TEXTURED_QUADS_DEPTH_ADDITIVE.clear();
        VARIABLE_LINES.clear();
        DEPTH_VARIABLE_LINES.clear();
        rendering = true;
    }

    public static void draw(List<VertexCollection> list, List<VertexCollection> list2, boolean bl) {
        class_287 class_2872;
        if (list.isEmpty() && list2.isEmpty()) {
            return;
        }
        RenderSystem.enableBlend();
        if (bl) {
            RenderSystem.blendFunc((int)770, (int)32772);
        } else {
            RenderSystem.blendFuncSeparate((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA, (GlStateManager.class_4535)GlStateManager.class_4535.ONE, (GlStateManager.class_4534)GlStateManager.class_4534.ZERO);
        }
        if (!list.isEmpty()) {
            class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
            for (VertexCollection vertexCollection : list) {
                vertexCollection.vertex(class_2872);
            }
            RenderSystem.setShader((class_10156)class_10142.field_53876);
            RenderSystem.disableCull();
            class_286.method_43433((class_9801)class_2872.method_60800());
            RenderSystem.enableCull();
        }
        if (!list2.isEmpty()) {
            class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_29344, class_290.field_1576);
            for (VertexCollection vertexCollection : list2) {
                vertexCollection.vertex(class_2872);
            }
            RenderSystem.setShader((class_10156)class_10142.field_53876);
            GL11.glHint((int)3154, (int)4354);
            GL11.glEnable((int)2848);
            class_286.method_43433((class_9801)class_2872.method_60800());
            GL11.glDisable((int)2848);
        }
        RenderSystem.disableBlend();
    }

    public static void drawVariableWidthLines(boolean bl) {
        List<VariableLine> list;
        List<VariableLine> list2 = list = bl ? DEPTH_VARIABLE_LINES : VARIABLE_LINES;
        if (list.isEmpty()) {
            return;
        }
        if (bl) {
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask((boolean)false);
        } else {
            RenderSystem.disableDepthTest();
        }
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.blendFuncSeparate((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA, (GlStateManager.class_4535)GlStateManager.class_4535.ONE, (GlStateManager.class_4534)GlStateManager.class_4534.ZERO);
        Render3D.drawVariableLines(list);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        if (bl) {
            RenderSystem.depthMask((boolean)true);
        } else {
            RenderSystem.enableDepthTest();
        }
        list.clear();
    }

    private static void drawVariableLines(List<VariableLine> list) {
        if (list.isEmpty()) {
            return;
        }
        int n = 0;
        for (VariableLine variableLine : list) {
            float f = variableLine.width();
            boolean bl = false;
            for (int i = 0; i < n; ++i) {
                if (Float.compare(widthScratch[i], f) != 0) continue;
                bl = true;
                break;
            }
            if (bl) continue;
            if (n == widthScratch.length) {
                widthScratch = Arrays.copyOf(widthScratch, n * 2);
            }
            Render3D.widthScratch[n++] = f;
        }
        RenderSystem.setShader((class_10156)class_10142.field_53876);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        for (int i = 0; i < n; ++i) {
            float f = widthScratch[i];
            RenderSystem.lineWidth((float)f);
            class_287 class_2872 = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_29344, class_290.field_1576);
            for (VariableLine variableLine : list) {
                if (Float.compare(variableLine.width(), f) != 0) continue;
                Vertex vertex = variableLine.start();
                Vertex vertex2 = variableLine.end();
                class_2872.method_22918(vertex.matrix, vertex.x, vertex.y, vertex.z).method_39415(vertex.color);
                class_2872.method_22918(vertex2.matrix, vertex2.x, vertex2.y, vertex2.z).method_39415(vertex2.color);
            }
            class_286.method_43433((class_9801)class_2872.method_60800());
        }
        RenderSystem.lineWidth((float)1.0f);
        GL11.glDisable((int)2848);
    }

    public static void drawDepth(List<VertexCollection> list, List<VertexCollection> list2, boolean bl) {
        if (list.isEmpty() && list2.isEmpty()) {
            return;
        }
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask((boolean)false);
        try {
            Render3D.draw(list, list2, bl);
        }
        finally {
            RenderSystem.depthMask((boolean)true);
        }
    }

    public static void renderBillboardTexture(class_243 class_2432, float f, class_2960 class_29602, int n, boolean bl) {
        Render3D.renderBillboardTexture(class_2432, f, class_29602, n, bl, false);
    }

    public static void renderBillboardTexture(class_243 class_2432, float f, class_2960 class_29602, int n, boolean bl, boolean bl2) {
        if (!rendering) {
            return;
        }
        class_4184 class_41842 = Render3D.aiG.field_1773.method_19418();
        class_4587 class_45872 = billboardScratch;
        class_45872.method_34426();
        class_243 class_2433 = class_41842.method_19326();
        class_45872.method_22904(class_2432.field_1352 - class_2433.field_1352, class_2432.field_1351 - class_2433.field_1351, class_2432.field_1350 - class_2433.field_1350);
        class_45872.method_22907(class_7833.field_40716.rotationDegrees(-class_41842.method_19330()));
        class_45872.method_22907(class_7833.field_40714.rotationDegrees(class_41842.method_19329()));
        class_4587.class_4665 class_46652 = class_45872.method_23760().method_56822();
        int n2 = n >>> 24 & 0xFF;
        int n3 = n >>> 16 & 0xFF;
        int n4 = n >>> 8 & 0xFF;
        int n5 = n & 0xFF;
        TexturedQuad texturedQuad = new TexturedQuad(class_46652, class_29602, -f / 2.0f, -f / 2.0f, f, f, n3, n4, n5, n2);
        if (bl) {
            if (bl2) {
                TEXTURED_QUADS_DEPTH_ADDITIVE.add(texturedQuad);
            } else {
                TEXTURED_QUADS_DEPTH.add(texturedQuad);
            }
        } else if (bl2) {
            TEXTURED_QUADS_ADDITIVE.add(texturedQuad);
        } else {
            TEXTURED_QUADS.add(texturedQuad);
        }
    }

    public static void renderOrientedTexture(class_243 class_2432, float f, class_2960 class_29602, int n, class_2350 class_23502, boolean bl) {
        Render3D.renderOrientedTexture(class_2432, f, class_29602, n, class_23502, bl, false);
    }

    public static void renderOrientedTexture(class_243 class_2432, float f, class_2960 class_29602, int n, class_2350 class_23502, boolean bl, boolean bl2) {
        if (!rendering) {
            return;
        }
        class_4184 class_41842 = Render3D.aiG.field_1773.method_19418();
        class_4587 class_45872 = billboardScratch;
        class_45872.method_34426();
        class_243 class_2433 = class_41842.method_19326();
        class_45872.method_22904(class_2432.field_1352 - class_2433.field_1352, class_2432.field_1351 - class_2433.field_1351, class_2432.field_1350 - class_2433.field_1350);
        Render3D.applyFaceRotation(class_45872, class_23502);
        class_4587.class_4665 class_46652 = class_45872.method_23760().method_56822();
        int n2 = n >>> 24 & 0xFF;
        int n3 = n >>> 16 & 0xFF;
        int n4 = n >>> 8 & 0xFF;
        int n5 = n & 0xFF;
        TexturedQuad texturedQuad = new TexturedQuad(class_46652, class_29602, -f / 2.0f, -f / 2.0f, f, f, n3, n4, n5, n2);
        if (bl) {
            if (bl2) {
                TEXTURED_QUADS_DEPTH_ADDITIVE.add(texturedQuad);
            } else {
                TEXTURED_QUADS_DEPTH.add(texturedQuad);
            }
        } else if (bl2) {
            TEXTURED_QUADS_ADDITIVE.add(texturedQuad);
        } else {
            TEXTURED_QUADS.add(texturedQuad);
        }
    }

    public static void drawTexture(class_4587.class_4665 class_46652, class_2960 class_29602, float f, float f2, float f3, float f4, int n, boolean bl) {
        Render3D.drawTexture(class_46652, class_29602, f, f2, f3, f4, n, bl, false);
    }

    public static void drawTexture(class_4587.class_4665 class_46652, class_2960 class_29602, float f, float f2, float f3, float f4, int n, boolean bl, boolean bl2) {
        if (!rendering || class_46652 == null || class_29602 == null) {
            return;
        }
        int n2 = n >>> 24 & 0xFF;
        int n3 = n >>> 16 & 0xFF;
        int n4 = n >>> 8 & 0xFF;
        int n5 = n & 0xFF;
        TexturedQuad texturedQuad = new TexturedQuad(class_46652, class_29602, f, f2, f3, f4, n3, n4, n5, n2);
        if (bl) {
            if (bl2) {
                TEXTURED_QUADS_DEPTH_ADDITIVE.add(texturedQuad);
            } else {
                TEXTURED_QUADS_DEPTH.add(texturedQuad);
            }
        } else if (bl2) {
            TEXTURED_QUADS_ADDITIVE.add(texturedQuad);
        } else {
            TEXTURED_QUADS.add(texturedQuad);
        }
    }

    private static void applyFaceRotation(class_4587 class_45872, class_2350 class_23502) {
        switch (class_23502) {
            case field_11036: {
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(-90.0f));
                break;
            }
            case field_11033: {
                class_45872.method_22907(class_7833.field_40714.rotationDegrees(90.0f));
                break;
            }
            case field_11043: {
                class_45872.method_22907(class_7833.field_40716.rotationDegrees(180.0f));
                break;
            }
            case field_11035: {
                break;
            }
            case field_11034: {
                class_45872.method_22907(class_7833.field_40716.rotationDegrees(-90.0f));
                break;
            }
            case field_11039: {
                class_45872.method_22907(class_7833.field_40716.rotationDegrees(90.0f));
            }
        }
    }

    public static void drawTextures() {
        Render3D.drawTexturedQuads(TEXTURED_QUADS, false, false, false);
        Render3D.drawTexturedQuads(TEXTURED_QUADS_ADDITIVE, false, true, false);
        Render3D.drawTexturedQuads(TEXTURED_QUADS_DEPTH, true, false, false);
        Render3D.drawTexturedQuads(TEXTURED_QUADS_DEPTH_ADDITIVE, true, true, false);
    }

    public static void drawDepthTexturesPreFog() {
    }

    private static void drawTexturedQuads(List<TexturedQuad> list, boolean bl, boolean bl2, boolean bl3) {
        if (list.isEmpty()) {
            return;
        }
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        if (bl2) {
            RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE);
        } else {
            RenderSystem.blendFunc((GlStateManager.class_4535)GlStateManager.class_4535.SRC_ALPHA, (GlStateManager.class_4534)GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
        }
        if (bl) {
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask((boolean)bl3);
        } else {
            RenderSystem.disableDepthTest();
        }
        textureBatches.clear();
        for (TexturedQuad quad : list) {
            textureBatches.computeIfAbsent(quad.texture(), ignored -> new ArrayList<>()).add(quad);
        }
        for (Map.Entry<class_2960, List<TexturedQuad>> entry : textureBatches.entrySet()) {
            List<TexturedQuad> batch = entry.getValue();
            if (batch.isEmpty()) continue;
            RenderSystem.setShaderTexture(0, entry.getKey());
            RenderSystem.setShader((class_10156)class_10142.field_53880);
            class_287 buffer = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
            for (TexturedQuad texturedQuad : batch) {
                Render3D.emitTexturedQuad(buffer, texturedQuad);
            }
            class_286.method_43433((class_9801)buffer.method_60800());
        }
        if (bl) {
            RenderSystem.depthMask((boolean)true);
        } else {
            RenderSystem.enableDepthTest();
        }
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        list.clear();
    }

    private static void emitTexturedQuad(class_287 class_2872, TexturedQuad texturedQuad) {
        Matrix4f matrix4f = texturedQuad.entry().method_23761();
        float f = texturedQuad.x();
        float f2 = texturedQuad.y();
        float f3 = texturedQuad.width();
        float f4 = texturedQuad.height();
        int n = texturedQuad.red();
        int n2 = texturedQuad.green();
        int n3 = texturedQuad.blue();
        int n4 = texturedQuad.alpha();
        class_2872.method_22918(matrix4f, f, f2 + f4, 0.0f).method_22913(0.0f, 1.0f).method_1336(n, n2, n3, n4);
        class_2872.method_22918(matrix4f, f + f3, f2 + f4, 0.0f).method_22913(1.0f, 1.0f).method_1336(n, n2, n3, n4);
        class_2872.method_22918(matrix4f, f + f3, f2, 0.0f).method_22913(1.0f, 0.0f).method_1336(n, n2, n3, n4);
        class_2872.method_22918(matrix4f, f, f2, 0.0f).method_22913(0.0f, 0.0f).method_1336(n, n2, n3, n4);
    }

    private static boolean isVisible(class_238 class_2383) {
        return ((IWorldRenderer)Render3D.aiG.field_1769).getFrustum().method_23093(class_2383);
    }

    private static class_243 cameraTransform(class_243 class_2432, Matrix4f matrix4f) {
        class_243 class_2433 = Render3D.aiG.field_1773.method_19418().method_19326();
        Vector4f vector4f = new Vector4f((float)(class_2432.field_1352 - class_2433.field_1352), (float)(class_2432.field_1351 - class_2433.field_1351), (float)(class_2432.field_1350 - class_2433.field_1350), 1.0f);
        vector4f.mul((Matrix4fc)matrix4f);
        return new class_243((double)vector4f.x(), (double)vector4f.y(), (double)vector4f.z());
    }

    private static class_238 cameraTransform(class_238 class_2383, Matrix4f matrix4f) {
        class_243 class_2432 = Render3D.cameraTransform(new class_243(class_2383.field_1323, class_2383.field_1322, class_2383.field_1321), matrix4f);
        class_243 class_2433 = Render3D.cameraTransform(new class_243(class_2383.field_1320, class_2383.field_1325, class_2383.field_1324), matrix4f);
        return new class_238(class_2432, class_2433);
    }

    @Generated
    private Render3D() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static final class VertexCollection {
        private final Vertex[] vertices;

        VertexCollection(Vertex... vertices) {
            this.vertices = vertices;
        }

        public void vertex(class_287 class_2872) {
            for (Vertex vertex : this.vertices) {
                class_2872.method_22918(vertex.matrix, vertex.x, vertex.y, vertex.z).method_39415(vertex.color);
            }
        }
    }

    static final class Vertex {
        final Matrix4f matrix;
        final float x;
        final float y;
        final float z;
        final int color;

        Vertex(Matrix4f matrix4f, float f, float f2, float f3, int n) {
            this.matrix = matrix4f;
            this.x = f;
            this.y = f2;
            this.z = f3;
            this.color = n;
        }

        @Override
        public final String toString() {
            return "Vertex[" + "matrix=" + this.matrix + ", " + "x=" + this.x + ", " + "y=" + this.y + ", " + "z=" + this.z + ", " + "color=" + this.color + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.matrix, this.x, this.y, this.z, this.color);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof Vertex other)) return false;
            return java.util.Objects.deepEquals(this.matrix, other.matrix)
                && java.util.Objects.deepEquals(this.x, other.x)
                && java.util.Objects.deepEquals(this.y, other.y)
                && java.util.Objects.deepEquals(this.z, other.z)
                && java.util.Objects.deepEquals(this.color, other.color);
        }

        public Matrix4f matrix() {
            return this.matrix;
        }

        public float x() {
            return this.x;
        }

        public float y() {
            return this.y;
        }

        public float z() {
            return this.z;
        }

        public int color() {
            return this.color;
        }
    }

    record VariableLine(Vertex start, Vertex end, float width) {
    }

    record ShapeCache(List<class_238> boxes, List<ShapeLine> lines) {
    }

    record ShapeLine(class_243 start, class_243 end) {
    }

    record TexturedQuad(class_4587.class_4665 entry, class_2960 texture, float x, float y, float width, float height, int red, int green, int blue, int alpha) {
    }
}
