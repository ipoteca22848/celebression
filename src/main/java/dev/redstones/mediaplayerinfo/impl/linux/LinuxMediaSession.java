package dev.redstones.mediaplayerinfo.impl.linux;

import dev.redstones.mediaplayerinfo.IMediaSession;
import dev.redstones.mediaplayerinfo.MediaInfo;
import dev.redstones.mediaplayerinfo.impl.linux.LinuxMediaPlayerInfo;
import dev.redstones.mediaplayerinfo.impl.linux.dbus.Player;
import java.net.URL;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.freedesktop.dbus.DBusMap;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\t\u001a\u00020\bH\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\rJ\u000f\u0010\u000f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\rJ\u000f\u0010\u0010\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0010\u0010\rJ\u000f\u0010\u0011\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0011\u0010\rJ\u000f\u0010\u0012\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0012\u0010\rR\u0014\u0010\u0013\u001a\u00020\u00028\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\b8\u0017X\u0097\u0004\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\nR\u001a\u0010\u0018\u001a\u00020\u00048\u0017X\u0097\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b"}, d2={"Ldev/redstones/mediaplayerinfo/impl/linux/LinuxMediaSession;", "Ldev/redstones/mediaplayerinfo/IMediaSession;", "Ldev/redstones/mediaplayerinfo/impl/linux/dbus/Player;", "p0", "", "p1", "<init>", "(Ldev/redstones/mediaplayerinfo/impl/linux/dbus/Player;Ljava/lang/String;)V", "Ldev/redstones/mediaplayerinfo/MediaInfo;", "generateMediaInfo", "()Ldev/redstones/mediaplayerinfo/MediaInfo;", "", "next", "()V", "pause", "play", "playPause", "previous", "stop", "dbus", "Ldev/redstones/mediaplayerinfo/impl/linux/dbus/Player;", "media", "Ldev/redstones/mediaplayerinfo/MediaInfo;", "getMedia", "owner", "Ljava/lang/String;", "getOwner", "()Ljava/lang/String;"})
public final class LinuxMediaSession
implements IMediaSession {
    @NotNull
    private final Player dbus;
    @NotNull
    private final String owner;
    @NotNull
    private final MediaInfo media;

    public LinuxMediaSession(@NotNull Player player, @NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"");
        Intrinsics.checkNotNullParameter((Object)string, (String)"");
        this.dbus = player;
        this.owner = string;
        this.media = this.generateMediaInfo();
    }

    @Override
    @NotNull
    @JvmName(name="getOwner")
    public String getOwner() {
        return this.owner;
    }

    @Override
    @NotNull
    @JvmName(name="getMedia")
    public MediaInfo getMedia() {
        return this.media;
    }

    @Override
    public void play() {
        this.dbus.Play();
    }

    @Override
    public void pause() {
        this.dbus.Pause();
    }

    @Override
    public void playPause() {
        this.dbus.PlayPause();
    }

    @Override
    public void stop() {
        this.dbus.Stop();
    }

    @Override
    public void next() {
        this.dbus.Next();
    }

    @Override
    public void previous() {
        this.dbus.Previous();
    }

    private final MediaInfo generateMediaInfo() {
        String string;
        DBusMap dBusMap = (DBusMap)LinuxMediaPlayerInfo.INSTANCE.getProperty$MediaPlayerInfo(this.getOwner(), "Metadata");
        boolean bl = Intrinsics.areEqual(LinuxMediaPlayerInfo.INSTANCE.getProperty$MediaPlayerInfo(this.getOwner(), "PlaybackStatus"), (Object)"Playing");
        long l = (long)((Number)LinuxMediaPlayerInfo.INSTANCE.getProperty$MediaPlayerInfo(this.getOwner(), "Position")).doubleValue() / (long)1000000;
        Object object = dBusMap.get((Object)"mpris:length");
        Intrinsics.checkNotNull((Object)object);
        long l2 = Long.parseLong(object.toString()) / (long)1000000;
        Object object2 = dBusMap.get((Object)"xesam:title");
        Intrinsics.checkNotNull((Object)object2);
        String string2 = (String)object2;
        Object object3 = dBusMap.get((Object)"xesam:artist");
        boolean bl2 = false;
        if (object3 instanceof String) {
            string = (String)object3;
        } else {
            Intrinsics.checkNotNull((Object)object3);
            string = String.join(", ", (List<String>)object3);
        }
        String string3 = string;
        Object object4 = dBusMap.get((Object)"mpris:artUrl");
        object3 = new byte[]{};
        if (object4 instanceof String) {
            try {
                object3 = TextStreamsKt.readBytes(new URL((String)object4));
            }
            catch (Exception ignored) {
            }
        }
        return new MediaInfo(string2, string3, (byte[])object3, l, l2, bl);
    }
}
