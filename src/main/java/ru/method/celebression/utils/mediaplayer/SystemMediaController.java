package ru.method.celebression.utils.mediaplayer;

import dev.redstones.mediaplayerinfo.IMediaSession;
import dev.redstones.mediaplayerinfo.MediaInfo;
import dev.redstones.mediaplayerinfo.MediaPlayerInfo;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.fabricmc.loader.api.FabricLoader;
import ru.method.celebression.Celebression;
import ru.method.celebression.utils.mediaplayer.WinRtInterop;
import ru.method.celebression.utils.mediaplayer.WindowsMediaKeys;
import ru.method.celebression.utils.mediaplayer.WindowsWindowTitleScanner;

public final class SystemMediaController {
    private static final String LOG_PREFIX = "[MediaPlayerInfo]";
    private static final long WINDOW_SCAN_GRACE_MS = 5000L;
    private static final int PLAYING_BONUS = 100000;
    private static final Path PROBE_LOCK = FabricLoader.getInstance().getConfigDir().resolve("celebression-media.lock");
    private static volatile boolean mediaDisabled;
    private static volatile boolean probeDone;

    private SystemMediaController() {
    }

    public static boolean isDisabled() {
        return mediaDisabled;
    }

    public static List<IMediaSession> fetchSessions() {
        boolean bl;
        if (mediaDisabled) {
            return List.of();
        }
        if (!WinRtInterop.ensureInitialized()) {
            Celebression.LOGGER.debug("{} WinRT/COM initialization unavailable", (Object)"[MediaPlayerInfo]");
            return List.of();
        }
        boolean bl2 = bl = !probeDone;
        if (bl) {
            try {
                Files.writeString(PROBE_LOCK, (CharSequence)"probing", new OpenOption[0]);
            }
            catch (Throwable throwable) {
            }
        }
        try {
            List<IMediaSession> list = MediaPlayerInfo.Instance.getMediaSessions();
            SystemMediaController.clearProbe(bl);
            return list == null ? List.of() : list;
        }
        catch (Throwable throwable) {
            SystemMediaController.clearProbe(bl);
            Celebression.LOGGER.debug("{} Failed to fetch media sessions", (Object)"[MediaPlayerInfo]", (Object)throwable);
            return List.of();
        }
    }

    private static void clearProbe(boolean bl) {
        if (!bl) {
            return;
        }
        probeDone = true;
        try {
            Files.deleteIfExists(PROBE_LOCK);
        }
        catch (Throwable throwable) {
        }
    }

    public static IMediaSession selectBestSession(List<IMediaSession> list, IMediaSession iMediaSession, String string, MediaSnapshot mediaSnapshot, long l) {
        IMediaSession iMediaSession2 = null;
        MediaSnapshot mediaSnapshot2 = null;
        int n = Integer.MIN_VALUE;
        long l2 = System.currentTimeMillis();
        for (IMediaSession iMediaSession3 : list) {
            MediaSnapshot mediaSnapshot3;
            if (iMediaSession3 == null || (mediaSnapshot3 = SystemMediaController.captureSnapshot(iMediaSession3)) == null) continue;
            int n2 = SystemMediaController.scoreSnapshot(mediaSnapshot3, iMediaSession, string, iMediaSession3, mediaSnapshot, l, l2);
            if (mediaSnapshot2 != null && n2 <= n) continue;
            mediaSnapshot2 = mediaSnapshot3;
            iMediaSession2 = iMediaSession3;
            n = n2;
        }
        if (iMediaSession2 != null) {
            return iMediaSession2;
        }
        return iMediaSession;
    }

    public static MediaSnapshot captureSnapshot(IMediaSession iMediaSession) {
        try {
            MediaInfo mediaInfo = iMediaSession.getMedia();
            if (mediaInfo == null) {
                return null;
            }
            String string = SystemMediaController.safeText(mediaInfo.getTitle());
            String string2 = SystemMediaController.safeText(mediaInfo.getArtist());
            boolean bl = mediaInfo.getPlaying();
            long l = SystemMediaController.normalizeMediaTime(mediaInfo.getDuration());
            long l2 = SystemMediaController.normalizeMediaTime(mediaInfo.getPosition());
            if (!bl && string.isEmpty() && string2.isEmpty() && l <= 0L) {
                return null;
            }
            byte[] artwork = mediaInfo.getArtworkPng();
            artwork = artwork != null && artwork.length > 0 ? Arrays.copyOf(artwork, artwork.length) : null;
            return new MediaSnapshot(SystemMediaController.safeText(iMediaSession.getOwner()), string, string2, bl, l, l2, artwork, iMediaSession);
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.debug("{} Session '{}' getMedia() failed", (Object)"[MediaPlayerInfo]", (Object)iMediaSession.getOwner(), (Object)throwable);
            return null;
        }
    }

    public static MediaSnapshot pollSnapshot(IMediaSession iMediaSession, String string, MediaSnapshot mediaSnapshot, long l) {
        MediaSnapshot mediaSnapshot2;
        Object object;
        List<IMediaSession> list = SystemMediaController.fetchSessions();
        if (!list.isEmpty() && (object = SystemMediaController.selectBestSession(list, iMediaSession, string, mediaSnapshot, l)) != null && (mediaSnapshot2 = SystemMediaController.captureSnapshot((IMediaSession)object)) != null) {
            return mediaSnapshot2;
        }
        if (l > 0L && System.currentTimeMillis() - l < 5000L) {
            return null;
        }
        object = WindowsWindowTitleScanner.scanBestCandidate();
        if (object == null) {
            return null;
        }
        return new MediaSnapshot(((WindowsWindowTitleScanner.WindowMediaCandidate)object).owner(), ((WindowsWindowTitleScanner.WindowMediaCandidate)object).title(), ((WindowsWindowTitleScanner.WindowMediaCandidate)object).artist(), true, 0L, 0L, null, null);
    }

    public static boolean performAction(IMediaSession iMediaSession, WindowsMediaKeys.MediaAction mediaAction) {
        if (iMediaSession != null) {
            try {
                switch (mediaAction) {
                    case PREVIOUS: {
                        iMediaSession.previous();
                        break;
                    }
                    case PLAY_PAUSE: {
                        iMediaSession.playPause();
                        break;
                    }
                    case NEXT: {
                        iMediaSession.next();
                    }
                }
                return true;
            }
            catch (Throwable throwable) {
                Celebression.LOGGER.debug("{} Session action {} failed, trying media keys", (Object)"[MediaPlayerInfo]", (Object)mediaAction, (Object)throwable);
            }
        }
        return WindowsMediaKeys.send(mediaAction);
    }

    public static IMediaSession resolveActionSession(IMediaSession iMediaSession) {
        if (iMediaSession != null) {
            return iMediaSession;
        }
        List<IMediaSession> list = SystemMediaController.fetchSessions();
        return SystemMediaController.selectBestSession(list, null, null, null, 0L);
    }

    private static int scoreSnapshot(MediaSnapshot mediaSnapshot, IMediaSession iMediaSession, String string, IMediaSession iMediaSession2, MediaSnapshot mediaSnapshot2, long l, long l2) {
        boolean bl;
        int n = SystemMediaController.scoreOwner(mediaSnapshot.owner());
        if (mediaSnapshot.playing()) {
            n += 100000;
        }
        if (iMediaSession != null && iMediaSession2 == iMediaSession) {
            n += 40;
        }
        if (string != null && Objects.equals(string, mediaSnapshot.owner())) {
            n += 30;
        }
        boolean bl2 = bl = !mediaSnapshot.title().isEmpty() || !mediaSnapshot.artist().isEmpty();
        if (!bl) {
            n -= 1000;
        }
        if (!mediaSnapshot.title().isEmpty()) {
            n += 20;
        }
        if (!mediaSnapshot.artist().isEmpty()) {
            n += 10;
        }
        if (mediaSnapshot.duration() > 0L) {
            n += 5;
        }
        if (mediaSnapshot.position() > 0L) {
            n += 2;
        }
        if (!mediaSnapshot.playing() && mediaSnapshot.duration() > 0L && mediaSnapshot.position() >= mediaSnapshot.duration()) {
            n -= 30;
        }
        if (mediaSnapshot.playing() && mediaSnapshot.duration() > 0L && mediaSnapshot.position() >= mediaSnapshot.duration()) {
            n -= 10;
        }
        if (mediaSnapshot2 != null && Objects.equals(mediaSnapshot2.owner(), mediaSnapshot.owner()) && Objects.equals(mediaSnapshot2.title(), mediaSnapshot.title()) && Objects.equals(mediaSnapshot2.artist(), mediaSnapshot.artist()) && mediaSnapshot.duration() > 0L && mediaSnapshot.position() >= mediaSnapshot.duration() && l2 - l > 1200L) {
            n -= 80;
        }
        return n;
    }

    private static int scoreOwner(String string) {
        if (string == null || string.isBlank()) {
            return 0;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.contains("yandex")) {
            return 3000;
        }
        if (string2.contains("spotify") || string2.contains("music")) {
            return 1200;
        }
        if (string2.contains("youtube")) {
            return 120;
        }
        return 0;
    }

    private static String safeText(String string) {
        return string == null ? "" : string;
    }

    private static long normalizeMediaTime(long l) {
        if (l <= 0L) {
            return 0L;
        }
        if (l < 100000L) {
            return l * 1000L;
        }
        return l;
    }

    static {
        try {
            if (Files.exists(PROBE_LOCK, new LinkOption[0])) {
                mediaDisabled = true;
                Celebression.LOGGER.warn("{} \u043f\u0440\u043e\u0448\u043b\u044b\u0439 \u0437\u0430\u043f\u0443\u0441\u043a \u0443\u043f\u0430\u043b \u0432 \u043d\u0430\u0442\u0438\u0432\u0435 MediaPlayerInfo.dll \u2014 \u0438\u043d\u0442\u0435\u0433\u0440\u0430\u0446\u0438\u044f \u0441\u0438\u0441\u0442\u0435\u043c\u043d\u043e\u0433\u043e \u043f\u043b\u0435\u0435\u0440\u0430 \u043e\u0442\u043a\u043b\u044e\u0447\u0435\u043d\u0430 (\u0443\u0434\u0430\u043b\u0438 {} \u0447\u0442\u043e\u0431\u044b \u0432\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u0441\u043d\u043e\u0432\u0430)", (Object)"[MediaPlayerInfo]", (Object)PROBE_LOCK);
            }
        }
        catch (Throwable throwable) {
        }
    }

    public record MediaSnapshot(String owner, String title, String artist, boolean playing, long duration, long position, byte[] artwork, IMediaSession session) {
    }
}
