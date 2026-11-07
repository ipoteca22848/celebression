package ru.method.celebression.media;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class LyricsService {
    private final List<LyricsLine> lk = Collections.synchronizedList(new ArrayList());
    private ExecutorService ll;
    private volatile boolean open;
    private volatile boolean lm;
    private volatile boolean loaded;
    private volatile boolean unavailable;
    private volatile String ln;

    public void dc() {
        if (this.ll == null || this.ll.isShutdown()) {
            this.ll = Executors.newSingleThreadExecutor(runnable -> {
                Thread thread = new Thread(runnable, "DynamicIsland-Lyrics");
                thread.setDaemon(true);
                return thread;
            });
        }
    }

    public void shutdown() {
        if (this.ll != null) {
            this.ll.shutdownNow();
            this.ll = null;
        }
        this.reset();
    }

    public boolean isOpen() {
        return this.open;
    }

    public boolean dd() {
        return this.lm;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public boolean isUnavailable() {
        return this.unavailable;
    }

    public List<LyricsLine> de() {
        return this.lk;
    }

    public void reset() {
        this.open = false;
        this.loaded = false;
        this.lm = false;
        this.unavailable = false;
        this.ln = null;
        this.lk.clear();
    }

    public void a(String string, String string2, long l) {
        if (this.unavailable) {
            return;
        }
        boolean bl = this.open = !this.open;
        if (this.open && !this.loaded && !this.lm) {
            this.b(string, string2, l);
        }
    }

    public void b(String string, String string2, long l) {
        if (this.unavailable) {
            return;
        }
        ExecutorService executorService = this.ll;
        if (executorService == null || executorService.isShutdown()) {
            return;
        }
        String string3 = LyricsService.safeText(string);
        String string4 = LyricsService.safeText(string2);
        String string5 = string3 + "|" + string4 + "|" + l;
        if (this.ln != null && this.ln.equals(string5) && (this.loaded || this.lm)) {
            return;
        }
        this.ln = string5;
        this.lk.clear();
        this.loaded = false;
        this.lm = true;
        this.unavailable = false;
        String string6 = string5;
        executorService.submit(() -> {
            try {
                List<LyricsLine> list = this.c(string3, string4, l);
                if (!string6.equals(this.ln)) {
                    return;
                }
                List<LyricsLine> list2 = this.lk;
                synchronized (list2) {
                    this.lk.clear();
                    if (list != null) {
                        this.lk.addAll(list);
                    }
                }
                boolean bl = list != null && !list.isEmpty();
                this.loaded = true;
                this.lm = false;
                if (!bl) {
                    this.unavailable = true;
                    this.open = false;
                } else {
                    this.unavailable = false;
                }
            }
            catch (Throwable throwable) {
                if (!string6.equals(this.ln)) {
                    return;
                }
                this.loaded = true;
                this.lm = false;
            }
        });
    }

    public int b(long l) {
        if (this.lk.isEmpty()) {
            return -1;
        }
        int n = 0;
        int n2 = this.lk.size() - 1;
        int n3 = -1;
        while (n <= n2) {
            int n4 = n + n2 >>> 1;
            long l2 = this.lk.get(n4).df();
            if (l2 <= l) {
                n3 = n4;
                n = n4 + 1;
                continue;
            }
            n2 = n4 - 1;
        }
        return n3;
    }

    private List<LyricsLine> c(String string, String string2, long l) {
        int n = this.c(l);
        String string3 = this.b(string, string2, n);
        String string4 = this.bc(string3);
        if ((string4 == null || string4.isEmpty()) && n > 0) {
            String string5 = this.c(string, string2, n);
            string4 = this.bc(string5);
        }
        if (string4 == null || string4.isEmpty()) {
            return Collections.emptyList();
        }
        return this.a(string4, l);
    }

    private List<LyricsLine> a(String string, long l) {
        try {
            JsonElement jsonElement = JsonParser.parseString((String)string);
            if (jsonElement.isJsonObject()) {
                List<LyricsLine> list = this.a(jsonElement.getAsJsonObject(), l);
                if (!list.isEmpty()) {
                    return list;
                }
            } else if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                for (JsonElement jsonElement2 : jsonArray) {
                    List<LyricsLine> list;
                    if (!jsonElement2.isJsonObject() || (list = this.a(jsonElement2.getAsJsonObject(), l)).isEmpty()) continue;
                    return list;
                }
            }
        }
        catch (Throwable throwable) {
        }
        return Collections.emptyList();
    }

    private List<LyricsLine> a(JsonObject jsonObject, long l) {
        String string = this.c(jsonObject, "syncedLyrics");
        if (string != null && !string.isEmpty()) {
            return this.az(string);
        }
        String string2 = this.c(jsonObject, "plainLyrics");
        if (string2 != null && !string2.isEmpty()) {
            return this.b(string2, l);
        }
        return Collections.emptyList();
    }

    private String c(JsonObject jsonObject, String string) {
        JsonElement jsonElement = jsonObject.get(string);
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        if (jsonElement.isJsonPrimitive()) {
            String string2 = jsonElement.getAsString();
            if (string2 == null) {
                return null;
            }
            return string2.replace("\\r", "").replace("\\n", "\n");
        }
        return null;
    }

    private List<LyricsLine> b(String string, long l) {
        Object object;
        if (string == null) {
            return Collections.emptyList();
        }
        String[] stringArray = string.replace("\r", "\n").split("\n");
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string2 : stringArray) {
            if (string2 == null || ((String)(object = string2.trim())).isEmpty()) continue;
            arrayList.add((String)object);
        }
        if (arrayList.isEmpty()) {
            return Collections.emptyList();
        }
        long l2 = l > 0L ? l : 180000L;
        long l3 = Math.max(1500L, l2 / (long)Math.max(1, arrayList.size() + 1));
        ArrayList<LyricsLine> lines = new ArrayList<>();
        long l4 = 0L;
        for (String string3 : arrayList) {
            lines.add(new LyricsLine(l4, string3));
            l4 += l3;
        }
        return lines;
    }

    private List<LyricsLine> az(String string) {
        if (string == null) {
            return Collections.emptyList();
        }
        String[] stringArray = string.replace("\r", "\n").split("\n");
        ArrayList<LyricsLine> arrayList = new ArrayList<LyricsLine>();
        for (String string2 : stringArray) {
            if (string2 == null || string2.isEmpty()) continue;
            int n = string2.indexOf(91);
            int n2 = string2.indexOf(93);
            if (n != 0 || n2 <= 1) continue;
            String string3 = string2.substring(n + 1, n2);
            String string4 = string2.substring(Math.min(string2.length(), n2 + 1)).trim();
            long l = this.ba(string3);
            if (l < 0L) continue;
            arrayList.add(new LyricsLine(l, string4));
        }
        arrayList.sort((lyricsLine, lyricsLine2) -> Long.compare(lyricsLine.df(), lyricsLine2.df()));
        return arrayList;
    }

    private long ba(String string) {
        try {
            String[] stringArray = string.split(":");
            if (stringArray.length < 2) {
                return -1L;
            }
            int n = Integer.parseInt(stringArray[0]);
            float f = Float.parseFloat(stringArray[1]);
            return (long)((float)((long)n * 60000L) + f * 1000.0f);
        }
        catch (Exception exception) {
            return -1L;
        }
    }

    private String b(String string, String string2, int n) {
        String string3 = "https://lrclib.net/api/get";
        int n2 = Math.max(1, n);
        return string3 + "?track_name=" + this.bb(string) + "&artist_name=" + this.bb(string2) + "&album_name=&duration=" + n2;
    }

    private String c(String string, String string2, int n) {
        String string3 = "https://lrclib.net/api/search";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?track_name=").append(this.bb(string)).append("&artist_name=").append(this.bb(string2)).append("&album_name=");
        if (n > 0) {
            stringBuilder.append("&duration=").append(n);
        }
        return string3 + String.valueOf(stringBuilder);
    }

    private int c(long l) {
        if (l <= 0L) {
            return -1;
        }
        if (l < 100000L) {
            return (int)l;
        }
        return (int)Math.max(1L, l / 1000L);
    }

    private String bb(String string) {
        try {
            return URLEncoder.encode(string == null ? "" : string, StandardCharsets.UTF_8.name());
        }
        catch (Exception exception) {
            return "";
        }
    }

    private String bc(String string) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(string);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            int responseCode = httpURLConnection.getResponseCode();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    responseCode >= 200 && responseCode < 300
                            ? httpURLConnection.getInputStream()
                            : httpURLConnection.getErrorStream(),
                    StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        }
        catch (Exception exception) {
            return null;
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    private static String safeText(String string) {
        return string == null ? "" : string;
    }

    public static final class LyricsLine {
        private final long lo;
        private final String lp;

        public LyricsLine(long l, String string) {
            this.lo = l;
            this.lp = string;
        }

        @Override
        public final String toString() {
            return "LyricsLine[" + "timeMs=" + this.lo + ", " + "text=" + this.lp + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.lo, this.lp);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof LyricsLine other)) return false;
            return java.util.Objects.deepEquals(this.lo, other.lo)
                && java.util.Objects.deepEquals(this.lp, other.lp);
        }

        public long df() {
            return this.lo;
        }

        public String text() {
            return this.lp;
        }
    }
}
