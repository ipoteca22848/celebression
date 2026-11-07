package dev.redstones.mediaplayerinfo.impl.win;

import dev.redstones.mediaplayerinfo.IMediaSession;
import dev.redstones.mediaplayerinfo.MediaPlayerInfo;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0097 \u00a2\u0006\u0004\b\u0006\u0010\u0007"}, d2={"Ldev/redstones/mediaplayerinfo/impl/win/WindowsMediaPlayerInfo;", "Ldev/redstones/mediaplayerinfo/MediaPlayerInfo;", "<init>", "()V", "", "Ldev/redstones/mediaplayerinfo/IMediaSession;", "getMediaSessions", "()Ljava/util/List;"})
public final class WindowsMediaPlayerInfo
implements MediaPlayerInfo {
    @NotNull
    public static final WindowsMediaPlayerInfo INSTANCE = new WindowsMediaPlayerInfo();

    private WindowsMediaPlayerInfo() {
    }

    @Override
    @NotNull
    public native List<IMediaSession> getMediaSessions();

    static {
        try {
            File file = Files.createTempDirectory("mediaplayerinfo-", new FileAttribute[0]).resolve("MediaPlayerInfo.dll").toFile();
            Intrinsics.checkNotNull((Object)file);
            try (InputStream inputStream = INSTANCE.getClass().getResourceAsStream("/mediaplayerinfo/natives/win/MediaPlayerInfo.dll")) {
                Intrinsics.checkNotNull((Object)inputStream);
                byte[] bytes = inputStream.readAllBytes();
                FilesKt.writeBytes(file, bytes);
            }
            System.load(file.getCanonicalPath());
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}
