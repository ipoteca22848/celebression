package ru.method.celebression.ui;


import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Color;
import java.io.InputStream;
import java.nio.ByteBuffer;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_1060;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import org.jetbrains.skija.Canvas;
import org.jetbrains.skija.Data;
import org.jetbrains.skija.Image;
import org.jetbrains.skija.Rect;
import org.jetbrains.skija.Surface;
import org.jetbrains.skija.skottie.Animation;
import org.jetbrains.skija.skottie.RenderFlag;
import org.lwjgl.opengl.GL11;
import ru.method.celebression.Celebression;
import ru.method.celebression.utils.render.Render2D;

public final class MainMenuLottiePlayer
implements AutoCloseable {
    private static final class_2960 ajC = Celebression.id("mainmenu/welcome.json");
    private static final class_2960 ajD = Celebression.id("mainmenu/welcome_lottie");
    private static final float ajE = 0.62f;
    private static final float ajF = 0.5f;
    private static final float ajG = 2.0f;
    private Animation ajH;
    private Surface ajI;
    private class_1043 ajJ;
    private int ajK;
    private int ajL;
    private boolean ajM;

    public MainMenuLottiePlayer() {
    }

    public boolean a(class_310 class_3102) {
        if (this.ajH != null || this.ajM) {
            if (this.ajH == null) return false;
            return true;
        }
        if (class_3102 == null) {
            this.ajM = true;
            return false;
        }
        try (InputStream inputStream = class_3102.method_1478().open(ajC);){
            byte[] byArray = inputStream.readAllBytes();
            Data data = Data.makeFromBytes((byte[])byArray);
            this.ajH = Animation.makeFromData((Data)data);
            data.close();
            if (this.ajH == null) {
                this.ajM = true;
                boolean bl2 = false;
                return bl2;
            }
            boolean bl = true;
            return bl;
        }
        catch (Throwable throwable3) {
            Celebression.LOGGER.warn("[MainMenuLottiePlayer] Skija unavailable, skipping Lottie animation: {}", (Object)throwable3.getMessage());
            this.ajM = true;
            return false;
        }
    }

    public float rC() {
        return this.ajH != null ? this.ajH.getDuration() : 0.0f;
    }

    public void render(class_332 class_3322, int n, int n2, float f) {
        if (this.ajH == null || class_3322 == null) {
            return;
        }
        float f2 = Math.max(1.0f, this.ajH.getWidth());
        float f3 = Math.max(1.0f, this.ajH.getHeight());
        float f4 = Math.min((float)n * 0.62f, f2 * 2.0f);
        float f5 = f4 / f2;
        float f6 = f3 * f5;
        float f7 = (float)n * 0.5f;
        float f8 = (float)n2 * 0.5f;
        float f9 = f7 - f4 / 2.0f;
        float f10 = f8 - f6 / 2.0f;
        int n3 = Math.max(1, Math.round(f4 * 2.0f));
        int n4 = Math.max(1, Math.round(f6 * 2.0f));
        this.w(n3, n4);
        if (this.ajI == null || this.ajJ == null) {
            return;
        }
        f = Math.max(0.0f, Math.min(1.0f, f));
        Canvas canvas = this.ajI.getCanvas();
        canvas.clear(0);
        this.ajH.seek(f);
        this.ajH.render(canvas, new Rect(0.0f, 0.0f, (float)n3, (float)n4), new RenderFlag[0]);
        Image image = this.ajI.makeImageSnapshot();
        this.a(image);
        image.close();
        Render2D.drawTexture(class_3322.method_51448(), f9, f10, f4, f6, 0.0f, (class_1044)this.ajJ, Color.WHITE);
    }

    @Override
    public void close() {
        if (this.ajJ != null) {
            this.ajJ.close();
            this.ajJ = null;
        }
        if (this.ajI != null) {
            this.ajI.close();
            this.ajI = null;
        }
        if (this.ajH != null) {
            this.ajH.close();
            this.ajH = null;
        }
    }

    private void w(int n, int n2) {
        class_1060 class_10602;
        if (n == this.ajK && n2 == this.ajL && this.ajI != null && this.ajJ != null) {
            return;
        }
        this.rD();
        this.ajK = n;
        this.ajL = n2;
        this.ajI = Surface.makeRasterN32Premul((int)n, (int)n2);
        class_1011 class_10112 = new class_1011(class_1011.class_1012.field_4997, n, n2, false);
        this.ajJ = new class_1043(class_10112);
        this.ajJ.method_4527(true, false);
        class_310 class_3102 = class_310.method_1551();
        class_1060 class_10603 = class_10602 = class_3102 != null ? class_3102.method_1531() : null;
        if (class_10602 != null) {
            class_10602.method_4616(ajD, (class_1044)this.ajJ);
        }
    }

    private void a(Image image) {
        if (image == null || this.ajJ == null) {
            return;
        }
        ByteBuffer byteBuffer = image.peekPixels();
        if (byteBuffer == null) {
            return;
        }
        byteBuffer.rewind();
        GlStateManager._bindTexture((int)this.ajJ.method_4624());
        GlStateManager._pixelStore((int)3314, (int)0);
        GlStateManager._pixelStore((int)3316, (int)0);
        GlStateManager._pixelStore((int)3315, (int)0);
        GlStateManager._pixelStore((int)3317, (int)4);
        GL11.glTexSubImage2D((int)3553, (int)0, (int)0, (int)0, (int)this.ajK, (int)this.ajL, (int)32993, (int)5121, (ByteBuffer)byteBuffer);
    }

    private void rD() {
        if (this.ajJ != null) {
            this.ajJ.close();
            this.ajJ = null;
        }
        if (this.ajI != null) {
            this.ajI.close();
            this.ajI = null;
        }
    }
}

