package ru.method.celebression.media;

import java.awt.Color;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.hud.DynamicIslandHud;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.render.LinkGuiBrandRenderer;
import ru.method.celebression.util.MathUtils;
import ru.method.celebression.media.MediaSessionRegistry;
import ru.method.celebression.media.MediaSessionTracker;
import ru.method.celebression.ui.ThemeTransition;
import ru.method.celebression.utils.mediaplayer.SystemVolumeController;
import ru.method.celebression.utils.render.GuiIcons;
import ru.method.celebression.utils.render.LinkGuiRenderContext;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;

public class MediaPlayerWidget {
    private static final float Yo = 40.0f;
    private static final float Yp = 24.0f;
    private static final float Yq = 10.2f;
    private static final float Yr = 8.4f;
    private static final float Ys = 13.8f;
    private static final float Yt = 9.2f;
    private static final float Yu = 52.0f;
    private static final float Yv = 3.0f;
    private static final float Yw = 26.4f;
    private static final float Yx = 1.0898438f;
    private final MediaSessionTracker Yy = MediaSessionRegistry.dy();
    private final Animation[] Yz = new Animation[]{new Animation(240L, 1.0, false, Easing.aiZ), new Animation(240L, 1.0, false, Easing.aiZ), new Animation(240L, 1.0, false, Easing.aiZ)};
    private final Animation YA = new Animation(220L, 1.0, false, Easing.aiZ);
    private final Animation YB = new Animation(220L, 1.0, false, Easing.aiZ);
    private final Animation YC = new Animation(260L, 1.0, false, Easing.aiZ);
    private final Animation YD = new Animation(220L, 1.0, false, Easing.aiZ);
    private float x;
    private float y;
    private float width;
    private float height = 76.0f;
    private float volume = 0.72f;
    private boolean volumeDragging;
    private long lastVolumePollMs;
    private long lastVolumeSetMs;
    private float displayedVolume = 0.72f;
    private boolean YE;
    private long YF;
    private float preMuteVolume = 0.5f;
    private float displayedProgress;
    private boolean YG;
    private long YH;
    private String YI;
    private float YJ;
    private String YK;
    private String YL;
    private float YM;
    private String YN;
    private long YO = -1L;
    private String YP = "0:00";
    private long YQ = -1L;
    private String YR = "0:00";

    public void init() {
        this.Yy.dc();
        SystemVolumeController.ensureInit();
        SystemVolumeController.refresh();
        Float f = SystemVolumeController.currentVolume();
        if (f != null) {
            this.volume = f.floatValue();
        }
        this.YB.ae(this.volume >= 0.01f);
        if (this.Yy.isPlaying()) {
            this.YC.ro();
        } else {
            this.YC.ae(false);
        }
    }

    public void shutdown() {
        this.volumeDragging = false;
    }

    public void b(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public void tick() {
        if (!this.isDynamicIslandUpdating()) {
            this.Yy.dh();
        }
        long l = System.currentTimeMillis();
        if (!this.volumeDragging && l - this.lastVolumePollMs > 250L && l - this.lastVolumeSetMs > 600L) {
            SystemVolumeController.refresh();
            Float f = SystemVolumeController.currentVolume();
            if (f != null) {
                this.volume = f.floatValue();
            }
            this.lastVolumePollMs = l;
        }
        this.YC.i(this.Yy.isPlaying());
    }

    private float as(float f) {
        return this.y + (f - 40.0f) / 2.0f + 2.0f;
    }

    private TrackInfo at(float f) {
        float f2 = this.x + this.width / 2.0f;
        float f3 = this.as(f) + 20.0f + 5.0f;
        float[] fArray = new float[]{f2 - 29.0f, f2, f2 + 29.0f};
        return new TrackInfo(fArray, f3);
    }

    public void a(class_332 class_3322, int n, int n2) {
        LinkGuiBrandRenderer.j(class_3322.method_51448(), this.x, this.y, this.width, this.height);
        float f = this.height - 24.0f;
        float f2 = this.x + 14.0f;
        float f3 = this.as(f);
        float f4 = f3 + 20.0f;
        TrackInfo trackInfo = this.at(f);
        if (!this.Yy.cr()) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(9.2f), "No media playing", f2 + 40.0f + 10.0f, f4 - 4.0f, ThemeTransition.d(110));
            this.a(class_3322, trackInfo, n, n2, false);
            this.YG = false;
            this.displayedProgress = 0.0f;
            this.a(class_3322, 0.0f, "0:00", "0:00", 0.55f);
            this.a(class_3322, n, n2, f4);
            return;
        }
        class_1043 class_10432 = this.Yy.dq();
        if (class_10432 != null) {
            int n3 = Math.max(0, Math.min(255, Math.round(255.0f * LinkGuiRenderContext.getFadeMultiplier())));
            Render2D.drawTexture(class_3322.method_51448(), f2, f3, 40.0f, 40.0f, 7.0f, (class_1044)class_10432, new Color(255, 255, 255, n3));
        } else {
            Render2D.drawRoundedRect(class_3322.method_51448(), f2, f3, 40.0f, 40.0f, 7.0f, new Color(ThemeTransition.accent().getRed(), ThemeTransition.accent().getGreen(), ThemeTransition.accent().getBlue(), 120));
        }
        String string = this.Yy.getTitle();
        if (string == null || string.isBlank()) {
            string = "Unknown";
        }
        String string2 = this.Yy.getArtist();
        float f5 = Math.round(f2 + 40.0f + 10.0f);
        float f6 = this.width * 0.22f;
        float f7 = Fonts.ROUNDBOLD.getHeight(10.2f);
        float f8 = Fonts.MEDIUM.getHeight(8.4f);
        boolean bl = string2 != null && !string2.isBlank();
        float f9 = f7 + (bl ? 2.0f + f8 : 0.0f);
        float f10 = Math.round(f4 - f9 / 2.0f);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(10.2f), this.c(string, f6), f5, f10, ThemeTransition.d(255));
        if (bl) {
            Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIUM.getFont(8.4f), this.d(string2, f6), f5, f10 + f7 + 2.0f, ThemeTransition.d(145));
        }
        long l = Math.max(1L, this.Yy.cw());
        long l2 = this.Yy.cx();
        float f11 = this.smoothProgress(Math.min(1.0f, (float)l2 / (float)l), this.Yy.isPlaying(), l);
        this.a(class_3322, f11, this.t(l2), this.u(l), 1.0f);
        this.a(class_3322, trackInfo, n, n2, true);
        this.a(class_3322, n, n2, f4);
    }

    private void a(class_332 class_3322, TrackInfo trackInfo, int n, int n2, boolean bl) {
        if (!bl) {
            return;
        }
        float f = this.YC.rk();
        String[] stringArray = new String[]{"A", f >= 0.5f ? "D" : "C", "B"};
        for (int i = 0; i < 3; ++i) {
            float f2 = trackInfo.YS[i] - 13.2f;
            float f3 = trackInfo.YT - 13.2f;
            boolean bl2 = MathUtils.c(f2, f3, 26.4f, 26.4f, n, n2);
            this.Yz[i].i(bl2);
            float f4 = this.Yz[i].rk();
            if (f4 > 0.04f) {
                LinkGuiBrandRenderer.h(class_3322.method_51448(), f2, f3, 26.4f, 26.4f, 6.0f, f4);
            }
            int n3 = (int)(210.0f + 45.0f * f4);
            float f5 = i == 1 ? 0.75f + 0.25f * Math.abs(2.0f * f - 1.0f) : 1.0f;
            this.a(class_3322, stringArray[i], trackInfo.YS[i], trackInfo.YT, ThemeTransition.d(n3), f5);
        }
    }

    private void a(class_332 class_3322, String string, float f, float f2, Color color, float f3) {
        float f4;
        float f5;
        float f6;
        float f7 = 13.8f * f3;
        float f8 = switch (string) {
            case "A", "B" -> {
                f6 = -0.078125f;
                f5 = 1.078125f;
                f4 = -0.078125f;
                yield 1.078125f;
            }
            case "C", "D" -> {
                f6 = 0.046875f;
                f5 = 0.953125f;
                f4 = -0.078125f;
                yield 1.078125f;
            }
            default -> {
                f6 = -0.07714844f;
                f5 = 1.0791016f;
                f4 = 0.023925781f;
                yield 0.9770508f;
            }
        };
        float f9 = (f6 + f5) * 0.5f;
        float f10 = (f4 + f8) * 0.5f;
        float f11 = f - (f9 - f6) * f7;
        float f12 = f2 - (1.0898438f - f10) * f7;
        if ("C".equals(string)) {
            f11 += f7 * 0.12f;
        }
        Render2D.drawFont(class_3322.method_51448(), Fonts.MEDIA_PLAYER.getFont(f7), string, f11, f12, color);
    }

    private void a(class_332 class_3322, int n, int n2, float f) {
        float f2 = this.x + this.width - 92.0f;
        float f3 = f2 + 14.0f;
        float f4 = f - 4.6f - 1.0f;
        float f5 = f4 + 5.0f;
        boolean bl = MathUtils.c(f3 - 6.0f, f5 - 8.0f, 64.0f, 16.0f, n, n2);
        this.YA.i(bl || this.volumeDragging);
        if (this.volumeDragging) {
            this.applyVolume(((float)n - f3) / 52.0f);
        }
        this.YB.i(this.volume >= 0.01f);
        float f6 = this.YB.rk();
        float f7 = f5 + 1.5f;
        float f8 = 12.0f;
        float f9 = f2 + f8 / 2.0f;
        float f10 = f7;
        boolean bl2 = MathUtils.c(f9 - f8 / 2.0f - 2.0f, f10 - f8 / 2.0f - 2.0f, f8 + 4.0f, f8 + 4.0f, n, n2);
        this.YD.i(bl2);
        Color color = ThemeTransition.d(Math.round(215.0f + 30.0f * this.YD.rk()));
        if (f6 < 0.996f) {
            GuiIcons.draw(class_3322.method_51448(), GuiIcons.MUTE, f9, f10, f8, MediaPlayerWidget.d(color, 1.0f - f6));
        }
        if (f6 > 0.004f) {
            GuiIcons.draw(class_3322.method_51448(), GuiIcons.SOUND, f9, f10, f8, MediaPlayerWidget.d(color, f6));
        }
        LinkGuiBrandRenderer.i(class_3322.method_51448(), f3, f5, 52.0f, 3.0f, this.oU(), this.YA.rk());
    }

    private void applyVolume(float f) {
        this.volume = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        SystemVolumeController.setVolume(this.volume);
        this.lastVolumeSetMs = System.currentTimeMillis();
    }

    private void toggleMute() {
        if (this.volume > 0.001f) {
            this.preMuteVolume = this.volume;
            this.applyVolume(0.0f);
        } else {
            this.applyVolume(this.preMuteVolume > 0.01f ? this.preMuteVolume : 0.5f);
        }
    }

    private float oU() {
        long l = System.currentTimeMillis();
        if (!this.YE) {
            this.displayedVolume = this.volume;
            this.YE = true;
            this.YF = l;
            return this.displayedVolume;
        }
        float f = Math.min(0.1f, Math.max(0.0f, (float)(l - this.YF) / 1000.0f));
        this.YF = l;
        float f2 = this.volumeDragging ? 32.0f : 13.0f;
        this.displayedVolume += (this.volume - this.displayedVolume) * (1.0f - (float)Math.exp(-f2 * f));
        if (Math.abs(this.volume - this.displayedVolume) < 0.001f) {
            this.displayedVolume = this.volume;
        }
        return this.displayedVolume;
    }

    private void a(class_332 class_3322, float f, String string, String string2, float f2) {
        float f3 = this.x + 14.0f;
        float f4 = this.y + this.height - 9.0f;
        float f5 = this.width - 28.0f;
        float f6 = 8.4f;
        float f7 = f4 - 13.0f;
        int n = Math.round(170.0f * f2);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(f6), string, f3, f7, ThemeTransition.d(n));
        float f8 = Fonts.ROUNDBOLD.getWidth(string2, f6);
        Render2D.drawFont(class_3322.method_51448(), Fonts.ROUNDBOLD.getFont(f6), string2, f3 + f5 - f8, f7, ThemeTransition.d(n));
        LinkGuiBrandRenderer.g(class_3322.method_51448(), this.x + 12.0f, this.y + this.height - 24.0f, this.width - 24.0f);
        LinkGuiBrandRenderer.i(class_3322.method_51448(), f3, f4, f5, 3.1f, f, 0.0f);
    }

    public boolean mouseClicked(double d, double d2, int n) {
        if (!MathUtils.c(this.x, this.y, this.width, this.height, (float)d, (float)d2)) {
            return false;
        }
        if (n != 0) {
            return true;
        }
        float f = this.height - 24.0f;
        float f2 = this.as(f) + 20.0f;
        TrackInfo trackInfo = this.at(f);
        float f3 = this.x + this.width - 92.0f;
        float f4 = f2 - 4.6f - 1.0f + 5.0f + 1.5f;
        if (MathUtils.c(f3 - 2.0f, f4 - 8.0f, 16.0f, 16.0f, (float)d, (float)d2)) {
            this.toggleMute();
            return true;
        }
        float f5 = f3 + 14.0f;
        float f6 = f2 - 1.0f;
        if (MathUtils.c(f5 - 6.0f, f6 - 8.0f, 64.0f, 16.0f, (float)d, (float)d2)) {
            this.volumeDragging = true;
            this.applyVolume(((float)d - f5) / 52.0f);
            return true;
        }
        if (!this.Yy.cr()) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            float f7 = trackInfo.YS[i] - 13.2f;
            float f8 = trackInfo.YT - 13.2f;
            if (!MathUtils.c(f7, f8, 26.4f, 26.4f, (float)d, (float)d2)) continue;
            if (i == 0) {
                this.Yy.dn();
            } else if (i == 1) {
                this.Yy.togglePlayback();
            } else {
                this.Yy.dp();
            }
            return true;
        }
        return true;
    }

    public void mouseReleased(double d, double d2, int n) {
        if (n == 0) {
            this.volumeDragging = false;
        }
    }

    public void mouseDragged(double d, double d2) {
        if (!this.volumeDragging) {
            return;
        }
        float f = this.x + this.width - 92.0f + 14.0f;
        this.applyVolume(((float)d - f) / 52.0f);
    }

    private boolean isDynamicIslandUpdating() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getHudManager() == null) {
            return false;
        }
        DynamicIslandHud dynamicIslandHud = celebression.getHudManager().a(DynamicIslandHud.class);
        return dynamicIslandHud != null && dynamicIslandHud.isToggled();
    }

    private float smoothProgress(float f, boolean bl, long l) {
        long l2 = System.currentTimeMillis();
        if (!this.YG) {
            this.displayedProgress = f;
            this.YG = true;
            this.YH = l2;
            return this.displayedProgress;
        }
        float f2 = Math.min(0.25f, Math.max(0.0f, (float)(l2 - this.YH) / 1000.0f));
        this.YH = l2;
        if (Math.abs(f - this.displayedProgress) > 0.04f) {
            this.displayedProgress = f;
            return this.displayedProgress;
        }
        if (bl && l > 0L) {
            this.displayedProgress += f2 * 1000.0f / (float)l;
        }
        this.displayedProgress += (f - this.displayedProgress) * (1.0f - (float)Math.exp(-0.8f * f2));
        this.displayedProgress = Math.max(0.0f, Math.min(1.0f, this.displayedProgress));
        return this.displayedProgress;
    }

    private static Color d(Color color, float f) {
        int n = Math.max(0, Math.min(255, Math.round((float)color.getAlpha() * f)));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
    }

    private static String a(long l) {
        long l2 = l / 1000L;
        long l3 = l2 / 60L;
        long l4 = l2 % 60L;
        return l3 + ":" + (l4 < 10L ? "0" : "") + l4;
    }

    private String c(String string, float f) {
        if (f == this.YJ && string.equals(this.YI)) {
            return this.YK;
        }
        this.YI = string;
        this.YJ = f;
        this.YK = MediaPlayerWidget.e(string, f, 10.2f);
        return this.YK;
    }

    private String d(String string, float f) {
        if (f == this.YM && string.equals(this.YL)) {
            return this.YN;
        }
        this.YL = string;
        this.YM = f;
        this.YN = MediaPlayerWidget.b(string, f, 8.4f);
        return this.YN;
    }

    private String t(long l) {
        long l2 = l / 1000L;
        if (l2 != this.YO) {
            this.YO = l2;
            this.YP = MediaPlayerWidget.a(l);
        }
        return this.YP;
    }

    private String u(long l) {
        long l2 = l / 1000L;
        if (l2 != this.YQ) {
            this.YQ = l2;
            this.YR = MediaPlayerWidget.a(l);
        }
        return this.YR;
    }

    private static String b(String string, float f, float f2) {
        if (Fonts.MEDIUM.getWidth(string, f2) <= f) {
            return string;
        }
        while (string.length() > 3 && Fonts.MEDIUM.getWidth(string + "...", f2) > f) {
            string = string.substring(0, string.length() - 1);
        }
        return string + "...";
    }

    private static String e(String string, float f, float f2) {
        if (Fonts.ROUNDBOLD.getWidth(string, f2) <= f) {
            return string;
        }
        while (string.length() > 3 && Fonts.ROUNDBOLD.getWidth(string + "...", f2) > f) {
            string = string.substring(0, string.length() - 1);
        }
        return string + "...";
    }

    static final class TrackInfo {
        final float[] YS;
        final float YT;

        TrackInfo(float[] fArray, float f) {
            this.YS = fArray;
            this.YT = f;
        }

        @Override
        public final String toString() {
            return "TrackInfo[" + "btnCenterX=" + this.YS + ", " + "btnCenterY=" + this.YT + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.YS, this.YT);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof TrackInfo other)) return false;
            return java.util.Objects.deepEquals(this.YS, other.YS)
                && java.util.Objects.deepEquals(this.YT, other.YT);
        }

        public float[] oV() {
            return this.YS;
        }

        public float oW() {
            return this.YT;
        }
    }
}
