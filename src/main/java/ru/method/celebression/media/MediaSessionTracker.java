package ru.method.celebression.media;

import dev.redstones.mediaplayerinfo.IMediaSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import ru.method.celebression.Celebression;
import ru.method.celebression.utils.mediaplayer.SystemMediaController;
import ru.method.celebression.utils.mediaplayer.WinRtInterop;
import ru.method.celebression.utils.mediaplayer.WindowsMediaKeys;

public final class MediaSessionTracker {
    private static final boolean lq = !Boolean.getBoolean("celebression.mediaPollingDisabled");
    private static final boolean lr = Boolean.getBoolean("celebression.mediaDebug");
    private static final long ls = Long.getLong("celebression.mediaPollIntervalMs", 450L);
    private static final long lt = Long.getLong("celebression.mediaFastPollIntervalMs", 120L);
    private static final long lu = Long.getLong("celebression.mediaFastPollWindowMs", 4000L);
    private static final long lv = Long.getLong("celebression.mediaOptimisticGuardMs", 900L);
    private static final long lw = Long.getLong("celebression.mediaWatchdogTimeoutMs", 5000L);
    private static final int lx = Integer.getInteger("celebression.mediaMaxStuckRecoveries", 3);
    private static final SystemMediaController.MediaSnapshot ly = new SystemMediaController.MediaSnapshot("", "", "", false, 0L, 0L, null, null);
    private final Map<Integer, class_1043> lz = new ConcurrentHashMap<Integer, class_1043>();
    private final List<Integer> lA = Collections.synchronizedList(new ArrayList());
    private final AtomicBoolean lB = new AtomicBoolean(false);
    private final AtomicReference<SystemMediaController.MediaSnapshot> lC = new AtomicReference();
    private volatile long lD;
    private volatile int lE;
    private ExecutorService lF;
    private volatile boolean lG;
    private volatile SystemMediaController.MediaSnapshot lH;
    private volatile IMediaSession lI;
    private volatile String lJ = "";
    private volatile String lK = "";
    private class_1043 lL;
    private int lM;
    private long lN;
    private volatile long lO;
    private volatile long lP;
    private volatile long lQ;
    private long lR;
    private long lS;
    private long lT;

    public void dc() {
        if (!lq) {
            this.lG = true;
            return;
        }
        if (this.lF == null || this.lF.isShutdown()) {
            this.lF = Executors.newSingleThreadExecutor(runnable -> {
                Thread thread = new Thread(() -> {
                    boolean bl = false;
                    try {
                        bl = WinRtInterop.ensureInitialized();
                        runnable.run();
                    }
                    finally {
                        if (bl) {
                            WinRtInterop.uninitializeCurrentThread();
                        }
                    }
                }, "DynamicIsland-Media");
                thread.setDaemon(true);
                return thread;
            });
        }
    }

    private void dg() {
        ExecutorService executorService = this.lF;
        this.lF = null;
        this.lB.set(false);
        this.lC.set(null);
        this.lD = 0L;
        if (executorService != null) {
            executorService.shutdownNow();
        }
        ++this.lE;
        Celebression.LOGGER.warn("[MediaPlayerInfo] Poll stuck > {} ms \u2014 restarted media thread (recovery #{})", (Object)lw, (Object)this.lE);
        if (this.lE >= lx) {
            this.lG = true;
            Celebression.LOGGER.warn("[MediaPlayerInfo] Disabling media polling after {} stuck recoveries", (Object)this.lE);
        }
    }

    public void shutdown() {
        this.lC.set(null);
        this.lB.set(false);
        this.lD = 0L;
        this.lE = 0;
        if (this.lF != null) {
            this.lF.shutdownNow();
            this.lF = null;
        }
        this.dt();
        this.du();
        this.lH = null;
        this.lI = null;
        this.lQ = 0L;
        this.lR = 0L;
        this.lS = 0L;
        this.lT = 0L;
    }

    public MediaSessionInfo dh() {
        long l;
        if (lq && (this.lF == null || this.lF.isShutdown())) {
            this.dc();
        }
        if (this.lG) {
            return MediaSessionInfo.lW;
        }
        if (this.lB.get() && this.lD > 0L && System.currentTimeMillis() - this.lD > lw) {
            this.dg();
            return MediaSessionInfo.lW;
        }
        ExecutorService executorService = this.lF;
        if (executorService == null || executorService.isShutdown()) {
            return MediaSessionInfo.lW;
        }
        boolean bl = false;
        boolean bl2 = false;
        long l2 = System.currentTimeMillis();
        SystemMediaController.MediaSnapshot mediaSnapshot = this.lC.getAndSet(null);
        if (mediaSnapshot != null) {
            if (mediaSnapshot == ly) {
                if (this.lH != null && l2 - this.lQ >= 6000L) {
                    MediaSessionTracker.bf("clearing media state after stale NO_MEDIA");
                    this.ds();
                    bl2 = true;
                }
            } else {
                this.lQ = l2;
                MediaSessionTracker.bf("applying media snapshot owner='" + mediaSnapshot.owner() + "' title='" + mediaSnapshot.title() + "'");
                bl = this.a(mediaSnapshot);
            }
        }
        long l3 = l = l2 < this.lO ? lt : ls;
        if (l2 - this.lN < l) {
            return new MediaSessionInfo(bl, bl2);
        }
        if (this.lB.get()) {
            return new MediaSessionInfo(bl, bl2);
        }
        this.lN = l2;
        if (!this.lB.compareAndSet(false, true)) {
            return new MediaSessionInfo(bl, bl2);
        }
        this.lD = l2;
        try {
            executorService.submit(() -> {
                try {
                    SystemMediaController.MediaSnapshot snapshot = SystemMediaController.pollSnapshot(this.lI, this.lH == null ? null : this.lH.owner(), this.lH, this.lQ);
                    this.lC.set(snapshot == null ? ly : snapshot);
                }
                catch (Throwable throwable) {
                    Celebression.LOGGER.debug("[MediaPlayerInfo] Poll failed", throwable);
                    this.lC.set(ly);
                }
                finally {
                    this.lB.set(false);
                    this.lD = 0L;
                    this.lE = 0;
                }
            });
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            this.lB.set(false);
            this.lD = 0L;
        }
        return new MediaSessionInfo(bl, bl2);
    }

    public MediaSessionInfo di() {
        if (this.lG) {
            return MediaSessionInfo.lW;
        }
        boolean bl = false;
        boolean bl2 = false;
        long l = System.currentTimeMillis();
        SystemMediaController.MediaSnapshot mediaSnapshot = this.lC.getAndSet(null);
        if (mediaSnapshot == null) {
            return MediaSessionInfo.lW;
        }
        if (mediaSnapshot == ly) {
            if (this.lH != null && l - this.lQ >= 6000L) {
                MediaSessionTracker.bf("clearing media state after stale NO_MEDIA");
                this.ds();
                bl2 = true;
            }
        } else {
            this.lQ = l;
            MediaSessionTracker.bf("applying media snapshot owner='" + mediaSnapshot.owner() + "' title='" + mediaSnapshot.title() + "'");
            bl = this.a(mediaSnapshot);
        }
        return new MediaSessionInfo(bl, bl2);
    }

    public boolean cr() {
        if (this.lH == null) {
            return false;
        }
        return this.lH.playing() || !MediaSessionTracker.safeText(this.lH.title()).isEmpty() || !MediaSessionTracker.safeText(this.lH.artist()).isEmpty();
    }

    public boolean isPlaying() {
        return this.lH != null && this.lH.playing();
    }

    public String getTitle() {
        return this.lJ;
    }

    public String getArtist() {
        return this.lK;
    }

    public String dj() {
        return this.lH == null ? "" : MediaSessionTracker.safeText(this.lH.title());
    }

    public String dk() {
        return this.lH == null ? "" : MediaSessionTracker.safeText(this.lH.artist());
    }

    public long dl() {
        return this.lH == null ? 0L : this.lH.duration();
    }

    public long cw() {
        if (this.lH != null && this.lH.duration() > 0L) {
            this.lR = this.lH.duration();
        }
        return this.lR;
    }

    public long cx() {
        if (this.lH != null && this.lH.playing() && this.lT > 0L) {
            long l = this.lS + Math.max(0L, System.currentTimeMillis() - this.lT);
            return this.lR > 0L ? Math.min(l, this.lR) : l;
        }
        return this.lS;
    }

    public IMediaSession dm() {
        return this.lI;
    }

    public boolean dn() {
        return this.a(WindowsMediaKeys.MediaAction.PREVIOUS, this.isPlaying(), this.dj());
    }

    public boolean togglePlayback() {
        boolean bl = this.isPlaying();
        if (bl) {
            this.lS = this.cx();
            this.lT = 0L;
        } else {
            this.lT = System.currentTimeMillis();
        }
        this.dr();
        return this.a(WindowsMediaKeys.MediaAction.PLAY_PAUSE, bl, null);
    }

    public boolean dp() {
        return this.a(WindowsMediaKeys.MediaAction.NEXT, this.isPlaying(), this.dj());
    }

    public class_1043 dq() {
        return this.lL;
    }

    private boolean a(WindowsMediaKeys.MediaAction mediaAction, boolean bl, String string) {
        if (!lq) {
            return false;
        }
        this.dc();
        ExecutorService executorService = this.lF;
        if (executorService == null || executorService.isShutdown()) {
            return false;
        }
        this.lO = System.currentTimeMillis() + lu;
        this.lN = 0L;
        try {
            executorService.submit(() -> this.b(mediaAction, bl, string));
            return true;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            return false;
        }
    }

    private void b(WindowsMediaKeys.MediaAction mediaAction, boolean bl, String string) {
        long[] lArray;
        Object object;
        try {
            object = SystemMediaController.resolveActionSession(this.lI);
            SystemMediaController.performAction((IMediaSession)object, mediaAction);
        }
        catch (Throwable throwable) {
            Celebression.LOGGER.debug("[MediaPlayerInfo] Media action {} failed", (Object)mediaAction, (Object)throwable);
        }
        if (mediaAction == WindowsMediaKeys.MediaAction.PLAY_PAUSE) {
            long[] lArray2 = new long[6];
            lArray2[0] = 0L;
            lArray2[1] = 80L;
            lArray2[2] = 180L;
            lArray2[3] = 350L;
            lArray2[4] = 650L;
            lArray = lArray2;
            lArray2[5] = 1100L;
        } else {
            long[] lArray3 = new long[7];
            lArray3[0] = 0L;
            lArray3[1] = 120L;
            lArray3[2] = 280L;
            lArray3[3] = 500L;
            lArray3[4] = 900L;
            lArray3[5] = 1400L;
            lArray = lArray3;
            lArray3[6] = 2200L;
        }
        long previousDelay = 0L;
        for (long delay : lArray) {
            long l = Math.max(0L, delay - previousDelay);
            previousDelay = delay;
            if (l > 0L) {
                try {
                    Thread.sleep(l);
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            try {
                SystemMediaController.MediaSnapshot mediaSnapshot = SystemMediaController.pollSnapshot(this.lI, this.lH == null ? null : this.lH.owner(), this.lH, this.lQ);
                if (mediaSnapshot == null) continue;
                this.lC.set(mediaSnapshot);
                if (!(mediaAction == WindowsMediaKeys.MediaAction.PLAY_PAUSE ? mediaSnapshot.playing() != bl : string != null && !string.isBlank() && !Objects.equals(MediaSessionTracker.safeText(mediaSnapshot.title()), string))) continue;
                return;
            }
            catch (Throwable throwable) {
                Celebression.LOGGER.debug("[MediaPlayerInfo] Post-action poll failed", throwable);
            }
        }
    }

    private void dr() {
        SystemMediaController.MediaSnapshot mediaSnapshot = this.lH;
        if (mediaSnapshot == null) {
            return;
        }
        byte[] byArray = mediaSnapshot.artwork();
        this.lH = new SystemMediaController.MediaSnapshot(mediaSnapshot.owner(), mediaSnapshot.title(), mediaSnapshot.artist(), !mediaSnapshot.playing(), mediaSnapshot.duration(), this.lS, byArray == null ? null : Arrays.copyOf(byArray, byArray.length), mediaSnapshot.session());
        this.lP = System.currentTimeMillis();
    }

    private boolean a(SystemMediaController.MediaSnapshot mediaSnapshot) {
        if (mediaSnapshot == null) {
            this.ds();
            return false;
        }
        boolean bl = this.c(mediaSnapshot = this.b(mediaSnapshot));
        if (bl) {
            this.lR = 0L;
            this.lS = 0L;
        }
        this.lH = mediaSnapshot;
        this.lI = mediaSnapshot.session();
        this.lJ = MediaSessionTracker.bd(mediaSnapshot.title());
        this.lK = MediaSessionTracker.be(mediaSnapshot.artist());
        long l = Math.max(0L, mediaSnapshot.duration());
        long l2 = Math.max(0L, mediaSnapshot.position());
        if (bl) {
            this.lR = l;
            if (l > 0L && l2 > l) {
                l2 = l;
            }
            this.lS = l2;
            this.lT = System.currentTimeMillis();
        } else {
            if (l > 0L) {
                this.lR = l;
            } else if (mediaSnapshot.duration() == 0L) {
                this.lR = 0L;
            }
            if (this.lR > 0L && l2 > this.lR) {
                l2 = this.lR;
            }
            this.lS = l2;
            this.lT = System.currentTimeMillis();
        }
        this.a(mediaSnapshot.artwork());
        return bl;
    }

    private void ds() {
        this.lH = null;
        this.lI = null;
        this.lJ = "";
        this.lK = "";
        this.dt();
        this.lQ = 0L;
        this.lR = 0L;
        this.lS = 0L;
        this.lT = 0L;
    }

    private void a(byte[] byArray) {
        int n;
        int n2 = n = byArray == null ? 0 : Arrays.hashCode(byArray);
        if (n != 0 && n == this.lM && this.lL != null) {
            this.o(n);
            return;
        }
        if (byArray == null || byArray.length == 0) {
            this.dt();
            return;
        }
        class_1043 class_10432 = this.lz.get(n);
        if (class_10432 != null) {
            this.a(n, class_10432);
            this.o(n);
            return;
        }
        class_1043 class_10433 = this.b(byArray);
        if (class_10433 == null) {
            this.dt();
            return;
        }
        this.b(n, class_10433);
        this.a(n, class_10433);
    }

    private void dt() {
        if (this.lL != null) {
            boolean bl;
            boolean bl2 = bl = this.lz.getOrDefault(this.lM, null) == this.lL;
            if (!bl) {
                this.lL.close();
            }
        }
        this.lL = null;
        this.lM = 0;
    }

    private class_1043 b(byte[] byArray) {
        class_1043 class_10432;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
        try {
            class_1011 class_10112 = class_1011.method_4309((InputStream)byteArrayInputStream);
            class_10432 = new class_1043(class_10112);
        }
        catch (Throwable throwable) {
            try {
                try {
                    byteArrayInputStream.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException iOException) {
                return null;
            }
        }
        try {
            byteArrayInputStream.close();
        }
        catch (IOException ignored) {
        }
        return class_10432;
    }

    private void a(int n, class_1043 class_10432) {
        if (this.lL != null && this.lL != class_10432) {
            boolean bl;
            boolean bl2 = bl = this.lz.getOrDefault(this.lM, null) == this.lL;
            if (!bl) {
                this.lL.close();
            }
        }
        this.lL = class_10432;
        this.lM = n;
    }

        private void b(int n, class_1043 class_10432) {
        this.lz.put(n, class_10432);
        List<Integer> list = this.lA;
        synchronized (list) {
            this.lA.remove((Object)n);
            this.lA.add(n);
            while (this.lA.size() > 6) {
                Integer n2 = this.lA.remove(0);
                class_1043 class_10433 = this.lz.remove(n2);
                if (class_10433 == null || class_10433 == this.lL) continue;
                class_10433.close();
            }
        }
    }

        private void o(int n) {
        List<Integer> list = this.lA;
        synchronized (list) {
            this.lA.remove((Object)n);
            this.lA.add(n);
        }
    }

        private void du() {
        for (Map.Entry<Integer, class_1043> entry : this.lz.entrySet()) {
            class_1043 class_10432 = entry.getValue();
            if (class_10432 == null || class_10432 == this.lL) continue;
            class_10432.close();
        }
        this.lz.clear();
        List<Integer> list = this.lA;
        synchronized (list) {
            this.lA.clear();
        }
    }

    private static String safeText(String string) {
        return string == null ? "" : string;
    }

    private static String bd(String string) {
        String string2 = MediaSessionTracker.safeText(string);
        return string2.isEmpty() ? "Unknown track" : string2;
    }

    private static String be(String string) {
        return MediaSessionTracker.safeText(string);
    }

    private static void bf(String string) {
        if (lr) {
            Celebression.LOGGER.info("[MediaPlayerInfo] {}", (Object)string);
        }
    }

    private SystemMediaController.MediaSnapshot b(SystemMediaController.MediaSnapshot mediaSnapshot) {
        if (mediaSnapshot == null || this.lH == null || this.lP <= 0L) {
            return mediaSnapshot;
        }
        long l = System.currentTimeMillis() - this.lP;
        if (l > lv) {
            this.lP = 0L;
            return mediaSnapshot;
        }
        if (!MediaSessionTracker.a(mediaSnapshot, this.lH)) {
            this.lP = 0L;
            return mediaSnapshot;
        }
        if (mediaSnapshot.playing() == this.lH.playing()) {
            this.lP = 0L;
            return mediaSnapshot;
        }
        byte[] byArray = mediaSnapshot.artwork();
        return new SystemMediaController.MediaSnapshot(mediaSnapshot.owner(), mediaSnapshot.title(), mediaSnapshot.artist(), this.lH.playing(), mediaSnapshot.duration(), mediaSnapshot.position(), byArray == null ? null : Arrays.copyOf(byArray, byArray.length), mediaSnapshot.session());
    }

    private static boolean a(SystemMediaController.MediaSnapshot mediaSnapshot, SystemMediaController.MediaSnapshot mediaSnapshot2) {
        if (mediaSnapshot == null || mediaSnapshot2 == null) {
            return false;
        }
        return Objects.equals(mediaSnapshot.owner(), mediaSnapshot2.owner()) && Objects.equals(mediaSnapshot.title(), mediaSnapshot2.title()) && Objects.equals(mediaSnapshot.artist(), mediaSnapshot2.artist());
    }

    private boolean c(SystemMediaController.MediaSnapshot mediaSnapshot) {
        if (mediaSnapshot == null || this.lH == null) {
            return true;
        }
        if (!Objects.equals(mediaSnapshot.owner(), this.lH.owner())) {
            return true;
        }
        if (!Objects.equals(mediaSnapshot.title(), this.lH.title())) {
            return true;
        }
        if (!Objects.equals(mediaSnapshot.artist(), this.lH.artist())) {
            return true;
        }
        return mediaSnapshot.playing() != this.lH.playing();
    }

    public static final class MediaSessionInfo {
        private final boolean lU;
        private final boolean lV;
        public static final MediaSessionInfo lW = new MediaSessionInfo(false, false);

        public MediaSessionInfo(boolean bl, boolean bl2) {
            this.lU = bl;
            this.lV = bl2;
        }

        @Override
        public final String toString() {
            return "MediaSessionInfo[" + "mediaChanged=" + this.lU + ", " + "cleared=" + this.lV + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.lU, this.lV);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof MediaSessionInfo other)) return false;
            return java.util.Objects.deepEquals(this.lU, other.lU)
                && java.util.Objects.deepEquals(this.lV, other.lV);
        }

        public boolean dw() {
            return this.lU;
        }

        public boolean dx() {
            return this.lV;
        }
    }
}
