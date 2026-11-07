package dev.redstones.mediaplayerinfo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import javax.imageio.ImageIO;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ByteArraySerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serializable
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 62\u00020\u0001:\u000256BO\b\u0011\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0002\u0010\u0010B5\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u0011J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\bH\u00c6\u0003J\t\u0010%\u001a\u00020\nH\u00c6\u0003J\t\u0010&\u001a\u00020\nH\u00c6\u0003J\t\u0010'\u001a\u00020\rH\u00c6\u0003JE\u0010(\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010)\u001a\u00020\r2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010+\u001a\u00020\u0003H\u0016J\b\u0010,\u001a\u00020\u0005H\u0016J&\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u00002\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u00c1\u0001\u00a2\u0006\u0002\b4R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u00158FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0013\u00a8\u00067"}, d2={"Ldev/redstones/mediaplayerinfo/MediaInfo;", "", "seen1", "", "title", "", "artist", "artworkPng", "", "position", "", "duration", "playing", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;[BJJZLkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;[BJJZ)V", "getArtist", "()Ljava/lang/String;", "artwork", "Ljava/awt/image/BufferedImage;", "getArtwork", "()Ljava/awt/image/BufferedImage;", "artwork$delegate", "Lkotlin/Lazy;", "getArtworkPng", "()[B", "getDuration", "()J", "getPlaying", "()Z", "getPosition", "getTitle", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "write$Self$MediaPlayerInfo", "$serializer", "Companion", "MediaPlayerInfo"})
public final class MediaInfo {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String title;
    @NotNull
    private final String artist;
    @NotNull
    private final byte[] artworkPng;
    private final long position;
    private final long duration;
    private final boolean playing;
    @NotNull
    private final Lazy artwork$delegate;

    public MediaInfo(@NotNull String string, @NotNull String string2, @NotNull byte[] byArray, long l, long l2, boolean bl) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"");
        Intrinsics.checkNotNullParameter((Object)string2, (String)"");
        Intrinsics.checkNotNullParameter((Object)byArray, (String)"");
        this.title = string;
        this.artist = string2;
        this.artworkPng = byArray;
        this.position = l;
        this.duration = l2;
        this.playing = bl;
        this.artwork$delegate = LazyKt.lazy(this::readArtwork);
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getArtist() {
        return this.artist;
    }

    @NotNull
    public final byte[] getArtworkPng() {
        return this.artworkPng;
    }

    public final long getPosition() {
        return this.position;
    }

    public final long getDuration() {
        return this.duration;
    }

    public final boolean getPlaying() {
        return this.playing;
    }

    @Nullable
    public final BufferedImage getArtwork() {
        Lazy lazy = this.artwork$delegate;
        return (BufferedImage)lazy.getValue();
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return true;
        }
        Object object2 = object;
        if (!Intrinsics.areEqual(this.getClass(), object2 != null ? object2.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull((Object)object);
        MediaInfo other = (MediaInfo)object;
        if (!Intrinsics.areEqual((Object)this.title, (Object)other.title)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.artist, (Object)other.artist)) {
            return false;
        }
        if (!Arrays.equals(this.artworkPng, other.artworkPng)) {
            return false;
        }
        if (this.position != other.position) {
            return false;
        }
        if (this.duration != other.duration) {
            return false;
        }
        return this.playing == other.playing;
    }

    public int hashCode() {
        int n = this.title.hashCode();
        n = 31 * n + this.artist.hashCode();
        n = 31 * n + Arrays.hashCode(this.artworkPng);
        n = 31 * n + Long.hashCode(this.position);
        n = 31 * n + Long.hashCode(this.duration);
        n = 31 * n + Boolean.hashCode(this.playing);
        return n;
    }

    @NotNull
    public String toString() {
        return "MediaInfo(title='" + this.title + "', artist='" + this.artist + "', position=" + this.position + ", duration=" + this.duration + ", playing=" + this.playing + ")";
    }

    @NotNull
    public final String component1() {
        return this.title;
    }

    @NotNull
    public final String component2() {
        return this.artist;
    }

    @NotNull
    public final byte[] component3() {
        return this.artworkPng;
    }

    public final long component4() {
        return this.position;
    }

    public final long component5() {
        return this.duration;
    }

    public final boolean component6() {
        return this.playing;
    }

    @NotNull
    public final MediaInfo copy(@NotNull String string, @NotNull String string2, @NotNull byte[] byArray, long l, long l2, boolean bl) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"");
        Intrinsics.checkNotNullParameter((Object)string2, (String)"");
        Intrinsics.checkNotNullParameter((Object)byArray, (String)"");
        return new MediaInfo(string, string2, byArray, l, l2, bl);
    }

    public static MediaInfo copy$default(MediaInfo mediaInfo, String string, String string2, byte[] byArray, long l, long l2, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            string = mediaInfo.title;
        }
        if ((n & 2) != 0) {
            string2 = mediaInfo.artist;
        }
        if ((n & 4) != 0) {
            byArray = mediaInfo.artworkPng;
        }
        if ((n & 8) != 0) {
            l = mediaInfo.position;
        }
        if ((n & 0x10) != 0) {
            l2 = mediaInfo.duration;
        }
        if ((n & 0x20) != 0) {
            bl = mediaInfo.playing;
        }
        return mediaInfo.copy(string, string2, byArray, l, l2, bl);
    }

    @JvmStatic
    public static final void write$Self$MediaPlayerInfo(MediaInfo mediaInfo, CompositeEncoder compositeEncoder, SerialDescriptor serialDescriptor) {
        compositeEncoder.encodeStringElement(serialDescriptor, 0, mediaInfo.title);
        compositeEncoder.encodeStringElement(serialDescriptor, 1, mediaInfo.artist);
        compositeEncoder.encodeSerializableElement(serialDescriptor, 2, (SerializationStrategy)ByteArraySerializer.INSTANCE, (Object)mediaInfo.artworkPng);
        compositeEncoder.encodeLongElement(serialDescriptor, 3, mediaInfo.position);
        compositeEncoder.encodeLongElement(serialDescriptor, 4, mediaInfo.duration);
        compositeEncoder.encodeBooleanElement(serialDescriptor, 5, mediaInfo.playing);
    }

    @Deprecated(message="This synthesized declaration should not be used directly", replaceWith=@ReplaceWith(expression="", imports={}), level=DeprecationLevel.HIDDEN)
    public MediaInfo(int n, String string, String string2, byte[] byArray, long l, long l2, boolean bl, SerializationConstructorMarker serializationConstructorMarker) {
        if (63 != (0x3F & n)) {
            PluginExceptionsKt.throwMissingFieldException((int)n, (int)63, Companion.serializer().getDescriptor());
        }
        this.title = string;
        this.artist = string2;
        this.artworkPng = byArray;
        this.position = l;
        this.duration = l2;
        this.playing = bl;
        this.artwork$delegate = LazyKt.lazy(this::readArtwork);
    }

    @Nullable
    private BufferedImage readArtwork() {
        try {
            return ImageIO.read(new ByteArrayInputStream(this.artworkPng));
        }
        catch (Exception ignored) {
            return null;
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2={"Ldev/redstones/mediaplayerinfo/MediaInfo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Ldev/redstones/mediaplayerinfo/MediaInfo;", "MediaPlayerInfo"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final KSerializer<MediaInfo> serializer() {
            try {
                Class<?> serializerClass = Class.forName("dev.redstones.mediaplayerinfo.MediaInfo$$serializer");
                return (KSerializer<MediaInfo>)serializerClass.getField("INSTANCE").get(null);
            }
            catch (ReflectiveOperationException exception) {
                throw new IllegalStateException("MediaInfo serializer is unavailable", exception);
            }
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
