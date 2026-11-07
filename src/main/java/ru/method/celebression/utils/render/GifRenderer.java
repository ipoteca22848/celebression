package ru.method.celebression.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_10142;
import net.minecraft.class_10156;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_9801;
import org.joml.Matrix4f;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.utils.render.GifTexture;

public final class GifRenderer
implements MinecraftClientAccess {
    private GifRenderer() {
    }

    public static void drawGif(class_4587 class_45872, GifTexture gifTexture, float f, float f2, float f3, float f4, float f5) {
        if (gifTexture == null || !gifTexture.isLoaded() || f5 <= 0.01f || class_45872 == null || f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        class_2960 class_29602 = gifTexture.getCurrentFrameTexture();
        if (class_29602 == null || aiG == null) {
            return;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShaderTexture((int)0, (int)aiG.method_1531().method_4619(class_29602).method_4624());
        RenderSystem.setShader((class_10156)class_10142.field_53879);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)f5);
        Matrix4f matrix4f = class_45872.method_23760().method_23761();
        class_287 class_2872 = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1585);
        class_2872.method_22918(matrix4f, f, f2 + f4, 0.0f).method_22913(0.0f, 1.0f);
        class_2872.method_22918(matrix4f, f + f3, f2 + f4, 0.0f).method_22913(1.0f, 1.0f);
        class_2872.method_22918(matrix4f, f + f3, f2, 0.0f).method_22913(1.0f, 0.0f);
        class_2872.method_22918(matrix4f, f, f2, 0.0f).method_22913(0.0f, 0.0f);
        class_286.method_43433((class_9801)class_2872.method_60800());
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (int)0);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        gifTexture.update();
    }
}

