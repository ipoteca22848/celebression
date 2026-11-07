package ru.method.celebression.utils.render.fonts;

import lombok.Generated;
import ru.method.celebression.api.render.msdf.MsdfFont;
import ru.method.celebression.utils.render.fonts.Font;

public final class Fonts {
    public static Font BOLD;
    public static Font MEDIUM;
    public static Font REGULAR;
    public static Font SEMIBOLD;
    public static Font MEDIA_PLAYER;
    public static Font ICONSNEW;
    public static Font ICONS2;
    public static Font ICONSLINK;
    public static Font ROUNDBOLD;
    public static Font FORT;
    public static Font MODICONS;
    public static Font ICONSFA;
    public static Font UNBOUNDED;
    public static Font UNBOUNDED_L;
    public static Font GOLOS;
    public static Font GOLOS_SEMI;
    public static Font PLEXMONO;

    @Generated
    private Fonts() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        UNBOUNDED = new Font(MsdfFont.builder().atlas("unbounded").data("unbounded").build());
        UNBOUNDED_L = new Font(MsdfFont.builder().atlas("unbounded_light").data("unbounded_light").build());
        GOLOS = new Font(MsdfFont.builder().atlas("golos").data("golos").build());
        GOLOS_SEMI = new Font(MsdfFont.builder().atlas("golos_semi").data("golos_semi").build());
        PLEXMONO = new Font(MsdfFont.builder().atlas("plexmono").data("plexmono").build());
        BOLD = new Font(MsdfFont.builder().atlas("sf_bold").data("sf_bold").build());
        MEDIUM = new Font(MsdfFont.builder().atlas("sf_medium").data("sf_medium").build());
        REGULAR = new Font(MsdfFont.builder().atlas("sf_regular").data("sf_regular").build());
        SEMIBOLD = new Font(MsdfFont.builder().atlas("sf_semibold").data("sf_semibold").build());
        ROUNDBOLD = new Font(MsdfFont.builder().atlas("round").data("round").build());
        MEDIA_PLAYER = new Font(MsdfFont.builder().atlas("media_player").data("media_player").build());
        ICONSNEW = new Font(MsdfFont.builder().atlas("iconsnew").data("iconsnew").build());
        ICONS2 = new Font(MsdfFont.builder().atlas("icons2").data("icons2").build());
        ICONSLINK = new Font(MsdfFont.builder().atlas("iconslink").data("iconslink").build());
        FORT = new Font(MsdfFont.builder().atlas("fort").data("fort").build());
        MODICONS = new Font(MsdfFont.builder().atlas("modicons").data("modicons").build());
        ICONSFA = new Font(MsdfFont.builder().atlas("iconsfa").data("iconsfa").build());
    }
}

