package dev.redstones.mediaplayerinfo;

import dev.redstones.mediaplayerinfo.MediaPlayerInfo;
import dev.redstones.mediaplayerinfo.impl.DummyMediaPlayerInfo;
import dev.redstones.mediaplayerinfo.impl.linux.LinuxMediaPlayerInfo;
import dev.redstones.mediaplayerinfo.impl.win.WindowsMediaPlayerInfo;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u001a\u0010\u0001\u001a\u00020\u00008\u0007X\u0087\u0004\u00a2\u0006\f\n\u0004\b\u0001\u0010\u0002\u001a\u0004\b\u0003\u0010\u0004"}, d2={"Ldev/redstones/mediaplayerinfo/MediaPlayerInfo;", "systemMediaPlayerInfo", "Ldev/redstones/mediaplayerinfo/MediaPlayerInfo;", "getSystemMediaPlayerInfo", "()Ldev/redstones/mediaplayerinfo/MediaPlayerInfo;"})
public final class MediaPlayerInfoKt {
    @NotNull
    private static final MediaPlayerInfo systemMediaPlayerInfo;

    @NotNull
    @JvmName(name="getSystemMediaPlayerInfo")
    public static final MediaPlayerInfo getSystemMediaPlayerInfo() {
        return systemMediaPlayerInfo;
    }

    static {
        MediaPlayerInfo mediaPlayerInfo;
        String string = System.getProperty("os.name");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"");
        String string2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"");
        if (string2.startsWith("windows")) {
            mediaPlayerInfo = WindowsMediaPlayerInfo.INSTANCE;
        } else {
            String string3 = System.getProperty("os.name");
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"");
            String string4 = string3.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"");
            mediaPlayerInfo = Intrinsics.areEqual((Object)string4, (Object)"linux") ? (MediaPlayerInfo)LinuxMediaPlayerInfo.INSTANCE : (MediaPlayerInfo)DummyMediaPlayerInfo.INSTANCE;
        }
        systemMediaPlayerInfo = mediaPlayerInfo;
    }
}
