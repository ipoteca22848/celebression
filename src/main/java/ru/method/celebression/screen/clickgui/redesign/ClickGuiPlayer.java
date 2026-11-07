package ru.method.celebression.screen.clickgui.redesign;

import java.awt.Color;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.method.celebression.Celebression;
import ru.method.celebression.animation.Animation;
import ru.method.celebression.hud.DynamicIslandHud;
import ru.method.celebression.animation.Easing;
import ru.method.celebression.media.MediaSessionRegistry;
import ru.method.celebression.media.MediaSessionTracker;
import ru.method.celebression.utils.mediaplayer.SystemVolumeController;
import ru.method.celebression.utils.render.ColorRGBA;
import ru.method.celebression.utils.render.GuiIcons;
import ru.method.celebression.utils.render.Render2D;
import ru.method.celebression.utils.render.fonts.Fonts;
import ru.method.celebression.utils.render.fonts.Instance;

public final class ClickGuiPlayer {
    private static final Color TEXT = new Color(238, 242, 239);
    private static final Color TEXT3 = new Color(195, 204, 199);
    private static final Color MUTED = new Color(139, 150, 143);
    private static final Color MUTED3 = new Color(93, 104, 98);
    private static final float ART = 40.0f;
    private static final float MEDIA_BASELINE = 1.0898438f;
    private final MediaSessionTracker media = MediaSessionRegistry.dy();
    private final Animation[] ctrlHover = new Animation[]{new Animation(140L, 1.0, false, Easing.aiZ), new Animation(140L, 1.0, false, Easing.aiZ), new Animation(140L, 1.0, false, Easing.aiZ)};
    private float x;
    private float y;
    private float width;
    private float volume = 0.72f;
    private float displayedVolume = 0.72f;
    private boolean volumeInit;
    private boolean volumeDragging;
    private long lastVolumePollMs;
    private long lastVolumeSetMs;
    private float preMuteVolume = 0.5f;
    private float displayedProgress;
    private boolean progressInit;
    private long lastProgressMs;
    private long lastVolumeSmoothMs;

    public void init() {
        this.media.dc();
        SystemVolumeController.ensureInit();
        SystemVolumeController.refresh();
        Float f = SystemVolumeController.currentVolume();
        if (f != null) {
            this.volume = f.floatValue();
        }
    }

    public void shutdown() {
        this.volumeDragging = false;
    }

    public void setBounds(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.width = f3;
    }

    public float height() {
        return 62.0f;
    }

    public void tick() {
        if (!this.isDynamicIslandUpdating()) {
            this.media.dh();
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
    }

    private float rowACenterY() {
        return this.y + 20.0f;
    }

    private float[] ctrlCentersX() {
        float f = this.x + this.width / 2.0f;
        return new float[]{f - 33.0f, f, f + 33.0f};
    }

    private float volBarX() {
        return this.x + this.width - 80.0f;
    }

    private float volIconCx() {
        return this.volBarX() - 9.0f - 7.0f;
    }

    public void render(class_332 class_3322, float f, float f2, Color color, float f3, boolean bl) {
        float f4;
        class_1043 class_10432;
        class_4587 class_45872 = class_3322.method_51448();
        float f5 = this.rowACenterY();
        Color color2 = bl ? new Color(26, 31, 38) : TEXT;
        Color color3 = bl ? new Color(68, 75, 86) : TEXT3;
        Color color4 = bl ? new Color(122, 130, 142) : MUTED;
        Color color5 = bl ? new Color(18, 22, 28) : Color.WHITE;
        int n = bl ? 30 : 255;
        float f6 = this.y;
        class_1043 class_10433 = class_10432 = this.media.cr() ? this.media.dq() : null;
        if (class_10432 != null) {
            Render2D.drawTexture(class_45872, this.x, f6, 40.0f, 40.0f, 8.0f, (class_1044)class_10432, ClickGuiPlayer.a(Color.WHITE, f3));
        } else {
            Render2D.drawRoundedRect(class_45872, this.x, f6, 40.0f, 40.0f, 8.0f, ClickGuiPlayer.a(new Color(color.getRed(), color.getGreen(), color.getBlue(), 90), f3));
        }
        Render2D.drawBorder(class_45872, this.x, f6, 40.0f, 40.0f, 8.0f, 0.8f, new ColorRGBA(ClickGuiPlayer.a(new Color(n, n, n, 26), f3)));
        float f7 = this.x + 40.0f + 13.0f;
        Instance instance = Fonts.UNBOUNDED.getFont(12.0f);
        Instance instance2 = Fonts.PLEXMONO.getFont(10.5f);
        String string = this.media.cr() ? ClickGuiPlayer.safe(this.media.getTitle(), "Unknown track") : "\u041d\u0435\u0442 \u043c\u0435\u0434\u0438\u0430";
        String string2 = this.media.cr() ? ClickGuiPlayer.safe(this.media.getArtist(), "") : "\u043f\u043b\u0435\u0435\u0440 \u043f\u0440\u043e\u0441\u0442\u0430\u0438\u0432\u0430\u0435\u0442";
        float f8 = this.width * 0.22f;
        float f9 = instance.getHeight();
        boolean bl2 = string2 != null && !string2.isBlank();
        float f10 = instance2.getHeight();
        float f11 = f9 + (bl2 ? 3.0f + f10 : 0.0f);
        float f12 = f5 - f11 / 2.0f;
        Render2D.drawFont(class_45872, instance, ClickGuiPlayer.clip(instance, string, f8), f7, f12, ClickGuiPlayer.a(color5, f3));
        if (bl2) {
            Render2D.drawFont(class_45872, instance2, ClickGuiPlayer.clip(instance2, string2, f8), f7, f12 + f9 + 3.0f, ClickGuiPlayer.a(color4, f3));
        }
        float[] fArray = this.ctrlCentersX();
        boolean bl3 = this.media.isPlaying();
        String[] stringArray = new String[]{"A", bl3 ? "D" : "C", "B"};
        float[] fArray2 = new float[]{16.0f, 21.0f, 16.0f};
        for (int i = 0; i < 3; ++i) {
            boolean bl4 = ClickGuiPlayer.dist(f, f2, fArray[i], f5) < 13.0f;
            this.ctrlHover[i].i(bl4);
            f4 = class_3532.method_15363((float)this.ctrlHover[i].rk(), (float)0.0f, (float)1.0f);
            Color color6 = i == 1 ? ClickGuiPlayer.lerp(color2, color, f4) : ClickGuiPlayer.lerp(color3, color2, f4);
            this.drawMediaIcon(class_45872, stringArray[i], fArray[i], f5, ClickGuiPlayer.a(color6, f3), fArray2[i]);
        }
        float f13 = this.volBarX();
        float f14 = f5 - 1.5f;
        f4 = this.volIconCx();
        this.volumeInit = this.smoothVolumeStep();
        boolean bl5 = this.volume < 0.01f;
        GuiIcons.draw(class_45872, bl5 ? GuiIcons.MUTE : GuiIcons.SOUND, f4, f5, 14.0f, ClickGuiPlayer.a(color3, f3));
        Render2D.drawRoundedRect(class_45872, f13, f14, 80.0f, 3.0f, 1.5f, ClickGuiPlayer.a(new Color(n, n, n, 41), f3));
        Render2D.drawRoundedRect(class_45872, f13, f14, 80.0f * this.displayedVolume, 3.0f, 1.5f, ClickGuiPlayer.a(color, f3));
        float f15 = f13 + 80.0f * this.displayedVolume;
        float f16 = f14 + 1.5f;
        Render2D.drawCircle(class_45872, f15, f16, 4.5f, 56, ClickGuiPlayer.a(color2, f3));
        float f17 = this.y + 40.0f + 8.0f;
        long l = Math.max(1L, this.media.cr() ? this.media.cw() : 1L);
        long l2 = this.media.cr() ? this.media.cx() : 0L;
        float f18 = this.media.cr() ? this.smoothProgress(Math.min(1.0f, (float)l2 / (float)l), bl3, l) : 0.0f;
        String string3 = ClickGuiPlayer.fmt(l2);
        String string4 = ClickGuiPlayer.fmt(l);
        Instance instance3 = Fonts.PLEXMONO.getFont(10.5f);
        float f19 = instance3.getWidth(string3);
        float f20 = instance3.getWidth(string4);
        float f21 = f17;
        Color color7 = ClickGuiPlayer.a(color3, f3);
        int n2 = Math.round(this.x);
        int n3 = Math.round(this.x + this.width - f20);
        int n4 = Math.round(f21);
        Render2D.drawFont(class_45872, instance3, string3, (float)n2, (float)n4, color7);
        Render2D.drawFont(class_45872, instance3, string3, (float)n2 + 0.5f, (float)n4, color7);
        Render2D.drawFont(class_45872, instance3, string4, (float)n3, (float)n4, color7);
        Render2D.drawFont(class_45872, instance3, string4, (float)n3 + 0.5f, (float)n4, color7);
        float f22 = this.x + f19 + 10.0f;
        float f23 = this.width - f19 - f20 - 20.0f;
        float f24 = f21 + instance3.getHeight() / 2.0f - 1.0f;
        Render2D.drawRoundedRect(class_45872, f22, f24, f23, 2.0f, 1.0f, ClickGuiPlayer.a(new Color(n, n, n, 41), f3));
        if (f18 > 0.001f) {
            Render2D.drawRoundedRect(class_45872, f22, f24, f23 * f18, 2.0f, 1.0f, ClickGuiPlayer.a(color2, f3));
            float f25 = f22 + f23 * f18;
            Render2D.drawCircle(class_45872, f25, f24 + 1.0f, 4.5f, 56, ClickGuiPlayer.a(color2, f3));
        }
    }

    public boolean mouseClicked(double d, double d2, int n) {
        if (n != 0) {
            return false;
        }
        float f = this.rowACenterY();
        float f2 = this.volIconCx();
        if (ClickGuiPlayer.dist((float)d, (float)d2, f2, f) < 9.0f) {
            this.toggleMute();
            return true;
        }
        float f3 = this.volBarX();
        if (d >= (double)(f3 - 6.0f) && d <= (double)(f3 + 86.0f) && Math.abs(d2 - (double)(f - 1.5f) - 1.5) < 8.0) {
            this.volumeDragging = true;
            this.applyVolume(((float)d - f3) / 80.0f);
            return true;
        }
        if (!this.media.cr()) {
            return false;
        }
        float[] fArray = this.ctrlCentersX();
        for (int i = 0; i < 3; ++i) {
            if (!(ClickGuiPlayer.dist((float)d, (float)d2, fArray[i], f) < 13.0f)) continue;
            if (i == 0) {
                this.media.dn();
            } else if (i == 1) {
                this.media.togglePlayback();
            } else {
                this.media.dp();
            }
            return true;
        }
        return false;
    }

    public void mouseReleased(double d, double d2, int n) {
        if (n == 0) {
            this.volumeDragging = false;
        }
    }

    public void mouseDragged(double d, double d2) {
        if (this.volumeDragging) {
            this.applyVolume(((float)d - this.volBarX()) / 80.0f);
        }
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

    private boolean smoothVolumeStep() {
        long l = System.currentTimeMillis();
        if (!this.volumeInit) {
            this.displayedVolume = this.volume;
            this.lastVolumeSmoothMs = l;
            return true;
        }
        float f = Math.min(0.1f, Math.max(0.0f, (float)(l - this.lastVolumeSmoothMs) / 1000.0f));
        this.lastVolumeSmoothMs = l;
        this.displayedVolume += (this.volume - this.displayedVolume) * (1.0f - (float)Math.exp(-15.0f * f));
        if (Math.abs(this.volume - this.displayedVolume) < 0.001f) {
            this.displayedVolume = this.volume;
        }
        return true;
    }

    private float smoothProgress(float f, boolean bl, long l) {
        long l2 = System.currentTimeMillis();
        if (!this.progressInit) {
            this.displayedProgress = f;
            this.progressInit = true;
            this.lastProgressMs = l2;
            return f;
        }
        float f2 = Math.min(0.25f, Math.max(0.0f, (float)(l2 - this.lastProgressMs) / 1000.0f));
        this.lastProgressMs = l2;
        if (Math.abs(f - this.displayedProgress) > 0.04f) {
            this.displayedProgress = f;
            return f;
        }
        if (bl && l > 0L) {
            this.displayedProgress += f2 * 1000.0f / (float)l;
        }
        this.displayedProgress += (f - this.displayedProgress) * (1.0f - (float)Math.exp(-0.8f * f2));
        this.displayedProgress = class_3532.method_15363((float)this.displayedProgress, (float)0.0f, (float)1.0f);
        return this.displayedProgress;
    }

    private void drawMediaIcon(class_4587 class_45872, String string, float f, float f2, Color color, float f3) {
        float f4;
        float f5;
        float f6;
        float f7 = f3;
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
        Render2D.drawFont(class_45872, Fonts.MEDIA_PLAYER.getFont(f7), string, f11, f12, color);
    }

    private boolean isDynamicIslandUpdating() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null || celebression.getHudManager() == null) {
            return false;
        }
        DynamicIslandHud dynamicIslandHud = celebression.getHudManager().a(DynamicIslandHud.class);
        return dynamicIslandHud != null && dynamicIslandHud.isToggled();
    }

    private static float dist(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return (float)Math.sqrt(f5 * f5 + f6 * f6);
    }

    private static Color a(Color color, float f) {
        int n = Math.max(0, Math.min(255, Math.round((float)color.getAlpha() * f)));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
    }

    private static Color lerp(Color color, Color color2, float f) {
        f = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        return new Color(Math.round((float)color.getRed() + (float)(color2.getRed() - color.getRed()) * f), Math.round((float)color.getGreen() + (float)(color2.getGreen() - color.getGreen()) * f), Math.round((float)color.getBlue() + (float)(color2.getBlue() - color.getBlue()) * f), Math.round((float)color.getAlpha() + (float)(color2.getAlpha() - color.getAlpha()) * f));
    }

    private static String safe(String string, String string2) {
        return string == null || string.isBlank() ? string2 : string;
    }

    private static String clip(Instance instance, String string, float f) {
        if (string == null) {
            return "";
        }
        if (instance.getWidth(string) <= f) {
            return string;
        }
        while (string.length() > 1 && instance.getWidth(string + "...") > f) {
            string = string.substring(0, string.length() - 1);
        }
        return string + "...";
    }

    private static String fmt(long l) {
        long l2 = Math.max(0L, l) / 1000L;
        return l2 / 60L + ":" + (l2 % 60L < 10L ? "0" : "") + l2 % 60L;
    }
}
