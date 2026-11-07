package dev.redstones.mediaplayerinfo;

import dev.redstones.mediaplayerinfo.MediaInfo;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u000f\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0006\u0010\u0004J\u000f\u0010\u0007\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0007\u0010\u0004J\u000f\u0010\b\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\b\u0010\u0004J\u000f\u0010\t\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\t\u0010\u0004R\u0014\u0010\r\u001a\u00020\n8'X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0011\u001a\u00020\u000e8'X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010"}, d2={"Ldev/redstones/mediaplayerinfo/IMediaSession;", "", "", "next", "()V", "pause", "play", "playPause", "previous", "stop", "Ldev/redstones/mediaplayerinfo/MediaInfo;", "getMedia", "()Ldev/redstones/mediaplayerinfo/MediaInfo;", "media", "", "getOwner", "()Ljava/lang/String;", "owner"})
public interface IMediaSession {
    @NotNull
    @JvmName(name="getOwner")
    public String getOwner();

    @NotNull
    @JvmName(name="getMedia")
    public MediaInfo getMedia();

    public void play();

    public void pause();

    public void playPause();

    public void stop();

    public void next();

    public void previous();
}

