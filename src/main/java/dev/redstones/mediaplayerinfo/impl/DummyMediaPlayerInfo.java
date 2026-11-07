package dev.redstones.mediaplayerinfo.impl;

import dev.redstones.mediaplayerinfo.IMediaSession;
import dev.redstones.mediaplayerinfo.MediaPlayerInfo;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0017\u00a2\u0006\u0004\b\u0006\u0010\u0007"}, d2={"Ldev/redstones/mediaplayerinfo/impl/DummyMediaPlayerInfo;", "Ldev/redstones/mediaplayerinfo/MediaPlayerInfo;", "<init>", "()V", "", "Ldev/redstones/mediaplayerinfo/IMediaSession;", "getMediaSessions", "()Ljava/util/List;"})
public final class DummyMediaPlayerInfo
implements MediaPlayerInfo {
    @NotNull
    public static final DummyMediaPlayerInfo INSTANCE = new DummyMediaPlayerInfo();

    private DummyMediaPlayerInfo() {
    }

    @Override
    @NotNull
    public List<IMediaSession> getMediaSessions() {
        return CollectionsKt.emptyList();
    }
}

