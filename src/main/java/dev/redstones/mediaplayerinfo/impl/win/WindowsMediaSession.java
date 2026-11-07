package dev.redstones.mediaplayerinfo.impl.win;

import dev.redstones.mediaplayerinfo.IMediaSession;
import dev.redstones.mediaplayerinfo.MediaInfo;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\tJ\u0010\u0010\u000b\u001a\u00020\nH\u0096 \u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\r\u001a\u00020\nH\u0096 \u00a2\u0006\u0004\b\r\u0010\fJ\u0010\u0010\u000e\u001a\u00020\nH\u0096 \u00a2\u0006\u0004\b\u000e\u0010\fJ\u0010\u0010\u000f\u001a\u00020\nH\u0096 \u00a2\u0006\u0004\b\u000f\u0010\fJ\u0010\u0010\u0010\u001a\u00020\nH\u0096 \u00a2\u0006\u0004\b\u0010\u0010\fJ\u0010\u0010\u0011\u001a\u00020\nH\u0096 \u00a2\u0006\u0004\b\u0011\u0010\fR\u0014\u0010\u0012\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u00028\u0017X\u0097\u0004\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u00048\u0017X\u0097\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b"}, d2={"Ldev/redstones/mediaplayerinfo/impl/win/WindowsMediaSession;", "Ldev/redstones/mediaplayerinfo/IMediaSession;", "Ldev/redstones/mediaplayerinfo/MediaInfo;", "p0", "", "p1", "", "p2", "<init>", "(Ldev/redstones/mediaplayerinfo/MediaInfo;Ljava/lang/String;I)V", "", "next", "()V", "pause", "play", "playPause", "previous", "stop", "index", "I", "media", "Ldev/redstones/mediaplayerinfo/MediaInfo;", "getMedia", "()Ldev/redstones/mediaplayerinfo/MediaInfo;", "owner", "Ljava/lang/String;", "getOwner", "()Ljava/lang/String;"})
public final class WindowsMediaSession
implements IMediaSession {
    @NotNull
    private final MediaInfo media;
    @NotNull
    private final String owner;
    private final int index;

    public WindowsMediaSession(@NotNull MediaInfo mediaInfo, @NotNull String string, int n) {
        Intrinsics.checkNotNullParameter((Object)mediaInfo, (String)"");
        Intrinsics.checkNotNullParameter((Object)string, (String)"");
        this.media = mediaInfo;
        this.owner = string;
        this.index = n;
    }

    @Override
    @NotNull
    @JvmName(name="getMedia")
    public MediaInfo getMedia() {
        return this.media;
    }

    @Override
    @NotNull
    @JvmName(name="getOwner")
    public String getOwner() {
        return this.owner;
    }

    @Override
    public native void play();

    @Override
    public native void pause();

    @Override
    public native void playPause();

    @Override
    public native void stop();

    @Override
    public native void next();

    @Override
    public native void previous();
}

