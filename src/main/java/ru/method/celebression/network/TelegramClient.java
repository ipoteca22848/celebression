package ru.method.celebression.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

public class TelegramClient {
    public static final long akz = 0x3200000L;
    private static final byte[] akA = "\r\n".getBytes(StandardCharsets.UTF_8);
    private static final URI akB = URI.create("https://api.telegram.org/");
    private final HttpClient akC;
    private final URI akD;

    public TelegramClient(HttpClient httpClient) {
        this(httpClient, akB);
    }

    public TelegramClient(HttpClient httpClient, URI uRI) {
        this.akC = Objects.requireNonNull(httpClient, "client");
        this.akD = Objects.requireNonNull(uRI, "apiBase");
    }

    public void close() {
        try {
            this.akC.shutdownNow();
        }
        catch (Throwable throwable) {
        }
    }

    public TelegramResponse a(String string, String string2, Path path, String string3, Duration duration) {
        String string4 = TelegramClient.cQ(string);
        String string5 = TelegramClient.cQ(string2);
        if (string4.isEmpty()) {
            return TelegramResponse.df("empty_bot_token");
        }
        if (string5.isEmpty()) {
            return TelegramResponse.df("empty_chat_id");
        }
        if (path == null || !Files.isRegularFile(path, new LinkOption[0])) {
            return TelegramResponse.df("document_not_found");
        }
        try {
            long l = Files.size(path);
            if (l > 0x3200000L) {
                return TelegramResponse.df("file_too_large");
            }
            String string6 = "----LVBoundary" + UUID.randomUUID().toString().replace("-", "");
            String string7 = TelegramClient.bn(path.getFileName().toString());
            String string8 = Files.probeContentType(path);
            if (string8 == null || string8.isBlank()) {
                string8 = "text/plain";
            }
            byte[] byArray = TelegramClient.a(string6, string5, TelegramClient.cQ(string3), string7, string8, Files.readAllBytes(path));
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(this.af(string4, "sendDocument")).timeout(duration == null ? Duration.ofSeconds(10L) : duration).header("Content-Type", "multipart/form-data; boundary=" + string6).POST(HttpRequest.BodyPublishers.ofByteArray(byArray)).build();
            HttpResponse<String> httpResponse = this.akC.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return this.a(httpResponse);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            return TelegramResponse.df("interrupted");
        }
        catch (Exception exception) {
            return TelegramResponse.df(exception.getClass().getSimpleName() + ":" + exception.getMessage());
        }
    }

    private TelegramResponse a(HttpResponse<String> httpResponse) {
        if (httpResponse == null) {
            return TelegramResponse.df("no_response");
        }
        String string = httpResponse.body();
        int n = httpResponse.statusCode();
        if (n != 200) {
            return new TelegramResponse(false, n, string, "http_" + n);
        }
        if (!this.de(string)) {
            return new TelegramResponse(false, n, string, "telegram_not_ok");
        }
        return new TelegramResponse(true, n, string, "");
    }

    private boolean de(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        try {
            JsonObject jsonObject = JsonParser.parseString((String)string).getAsJsonObject();
            return jsonObject.has("ok") && jsonObject.get("ok").getAsBoolean();
        }
        catch (Exception exception) {
            return false;
        }
    }

    private URI af(String string, String string2) {
        String string3 = string2 == null ? "" : string2.trim();
        return this.akD.resolve("bot" + string + "/" + string3);
    }

    private static String cQ(String string) {
        return string == null ? "" : string.trim();
    }

    private static String bn(String string) {
        if (string == null || string.isBlank()) {
            return "crash-report.txt";
        }
        return string.replace("\"", "_").replace("\r", "_").replace("\n", "_");
    }

    static byte[] a(String string, String string2, String string3, String string4, String string5, byte[] byArray) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TelegramClient.a(byteArrayOutputStream, string, "chat_id", string2);
        if (!string3.isBlank()) {
            TelegramClient.a(byteArrayOutputStream, string, "caption", string3);
        }
        byteArrayOutputStream.writeBytes(("--" + string).getBytes(StandardCharsets.UTF_8));
        byteArrayOutputStream.writeBytes(akA);
        byteArrayOutputStream.writeBytes(("Content-Disposition: form-data; name=\"document\"; filename=\"" + string4 + "\"").getBytes(StandardCharsets.UTF_8));
        byteArrayOutputStream.writeBytes(akA);
        byteArrayOutputStream.writeBytes(("Content-Type: " + string5).getBytes(StandardCharsets.UTF_8));
        byteArrayOutputStream.writeBytes(akA);
        byteArrayOutputStream.writeBytes(akA);
        byteArrayOutputStream.writeBytes(byArray);
        byteArrayOutputStream.writeBytes(akA);
        byteArrayOutputStream.writeBytes(("--" + string + "--").getBytes(StandardCharsets.UTF_8));
        byteArrayOutputStream.writeBytes(akA);
        return byteArrayOutputStream.toByteArray();
    }

    private static void a(ByteArrayOutputStream byteArrayOutputStream, String string, String string2, String string3) {
        byteArrayOutputStream.writeBytes(("--" + string).getBytes(StandardCharsets.UTF_8));
        byteArrayOutputStream.writeBytes(akA);
        byteArrayOutputStream.writeBytes(("Content-Disposition: form-data; name=\"" + string2 + "\"").getBytes(StandardCharsets.UTF_8));
        byteArrayOutputStream.writeBytes(akA);
        byteArrayOutputStream.writeBytes(akA);
        byteArrayOutputStream.writeBytes(string3.getBytes(StandardCharsets.UTF_8));
        byteArrayOutputStream.writeBytes(akA);
    }

    public static final class TelegramResponse {
        private final boolean akE;
        private final int akF;
        private final String akG;
        private final String akH;

        public TelegramResponse(boolean bl, int n, String string, String string2) {
            this.akE = bl;
            this.akF = n;
            this.akG = string;
            this.akH = string2;
        }

        public static TelegramResponse df(String string) {
            return new TelegramResponse(false, -1, "", string == null ? "unknown" : string);
        }

        @Override
        public final String toString() {
            return "TelegramResponse[" + "success=" + this.akE + ", " + "statusCode=" + this.akF + ", " + "body=" + this.akG + ", " + "error=" + this.akH + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.akE, this.akF, this.akG, this.akH);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof TelegramResponse other)) return false;
            return java.util.Objects.deepEquals(this.akE, other.akE)
                && java.util.Objects.deepEquals(this.akF, other.akF)
                && java.util.Objects.deepEquals(this.akG, other.akG)
                && java.util.Objects.deepEquals(this.akH, other.akH);
        }

        public boolean rY() {
            return this.akE;
        }

        public int rZ() {
            return this.akF;
        }

        public String sa() {
            return this.akG;
        }

        public String sb() {
            return this.akH;
        }
    }
}
