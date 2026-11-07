package unkw.visualtest.client.hud;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_332;

final class SmoothGuiRenderer {
    private static final Map<class_2960, class_1921> LAYERS = new HashMap<class_2960, class_1921>();

    private SmoothGuiRenderer() {
    }

    static class_1921 getLayer(class_2960 class_29602) {
        return LAYERS.computeIfAbsent(class_29602, SmoothGuiRenderer::createLayer);
    }

    static void draw(class_332 class_3322, class_2960 class_29602, int n, int n2, int n3, int n4, int n5, int n6) {
        SmoothGuiRenderer.drawRegion(class_3322, class_29602, n, n2, n3, n4, 0, 0, n5, n6, n5, n6);
    }

    static void drawNineSlice(class_332 class_3322, class_2960 class_29602, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        SmoothGuiRenderer.drawNineSlice(class_3322, class_29602, n, n2, n3, n4, n5, n6, n7, n7);
    }

    static void drawNineSlice(class_332 class_3322, class_2960 class_29602, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9 = Math.min(n8, n3 / 2);
        int n10 = Math.min(n8, n3 / 2);
        int n11 = Math.min(n8, n4 / 2);
        int n12 = Math.min(n8, n4 / 2);
        int n13 = n3 - n9 - n10;
        int n14 = n4 - n11 - n12;
        int n15 = n5 - n7 * 2;
        int n16 = n6 - n7 * 2;
        SmoothGuiRenderer.drawRegion(class_3322, class_29602, n, n2, n9, n11, 0, 0, n7, n7, n5, n6);
        SmoothGuiRenderer.drawRegion(class_3322, class_29602, n + n9, n2, n13, n11, n7, 0, n15, n7, n5, n6);
        SmoothGuiRenderer.drawRegion(class_3322, class_29602, n + n3 - n10, n2, n10, n11, n5 - n7, 0, n7, n7, n5, n6);
        SmoothGuiRenderer.drawRegion(class_3322, class_29602, n, n2 + n11, n9, n14, 0, n7, n7, n16, n5, n6);
        SmoothGuiRenderer.drawRegion(class_3322, class_29602, n + n9, n2 + n11, n13, n14, n7, n7, n15, n16, n5, n6);
        SmoothGuiRenderer.drawRegion(class_3322, class_29602, n + n3 - n10, n2 + n11, n10, n14, n5 - n7, n7, n7, n16, n5, n6);
        SmoothGuiRenderer.drawRegion(class_3322, class_29602, n, n2 + n4 - n12, n9, n12, 0, n6 - n7, n7, n7, n5, n6);
        SmoothGuiRenderer.drawRegion(class_3322, class_29602, n + n9, n2 + n4 - n12, n13, n12, n7, n6 - n7, n15, n7, n5, n6);
        SmoothGuiRenderer.drawRegion(class_3322, class_29602, n + n3 - n10, n2 + n4 - n12, n10, n12, n5 - n7, n6 - n7, n7, n7, n5, n6);
    }

    static void drawRegion(class_332 class_3322, class_2960 class_29602, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        if (n3 <= 0 || n4 <= 0 || n7 <= 0 || n8 <= 0) {
            return;
        }
        class_3322.method_25302(SmoothGuiRenderer::getLayer, class_29602, n, n2, (float)n5, (float)n6, n3, n4, n7, n8, n9, n10);
    }

    private static class_1921 createLayer(class_2960 class_29602) {
        return class_1921.method_62277(class_29602);
    }
}
