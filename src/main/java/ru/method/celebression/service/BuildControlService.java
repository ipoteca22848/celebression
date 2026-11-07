package ru.method.celebression.service;

import ru.method.celebression.api.setting.Setting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import net.fabricmc.loader.api.FabricLoader;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.BooleanParser;
import ru.method.celebression.render.FrameTimeTracker;
import ru.method.celebression.service.NativeProtectionStatus;

public final class BuildControlService {
    private static final String nq = "LV_BUILD_CONTROL_ENABLED";
    private static final String nr = "LV_BUILD_CONTROL_ENDPOINT";
    private static final String ns = "LV_BUILD_ID";
    private static final String nt = "lv.buildId";
    private static final String nu = "http://91.92.33.75:8091/build/status";
    private static final Duration nv = Duration.ofSeconds(3L);

    private BuildControlService() {
    }

    public static void ex() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            return;
        }
        if (!BuildControlService.isEnabled()) {
            return;
        }
        String string = BuildControlService.ey();
        if (string.isBlank()) {
            return;
        }
        try {
            URI uRI = BuildControlService.bx(string);
            HttpClient httpClient = HttpClient.newBuilder().connectTimeout(nv).version(HttpClient.Version.HTTP_1_1).build();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(uRI).timeout(nv).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (httpResponse.statusCode() != 200 || httpResponse.body() == null || httpResponse.body().isBlank()) {
                return;
            }
            if (!NativeProtectionStatus.ad(httpResponse.body(), httpResponse.headers().firstValue("X-Signature").orElse(null))) {
                Celebression.LOGGER.warn("[BuildControl] Unsigned/invalid response; allowing launch");
                return;
            }
            JsonObject jsonObject = JsonParser.parseString((String)httpResponse.body()).getAsJsonObject();
            boolean bl = jsonObject.has("disabled") && jsonObject.get("disabled").getAsBoolean();
            Celebression.LOGGER.info("[BuildControl] build={} disabled={}", (Object)BuildControlService.ez(), (Object)bl);
            if (!bl) {
                return;
            }
            String string2 = jsonObject.has("reason") ? jsonObject.get("reason").getAsString() : "";
            String string3 = string2 == null || string2.isBlank() ? "Celebression build is disabled remotely." : "Celebression build is disabled remotely: " + string2;
            throw new BuildControlResult(string3);
        }
        catch (BuildControlResult buildControlResult) {
            throw buildControlResult;
        }
        catch (Exception exception) {
            Celebression.LOGGER.warn("[BuildControl] Startup check failed; allowing launch", (Throwable)exception);
            return;
        }
    }

    private static boolean isEnabled() {
        String string = FrameTimeTracker.cQ(System.getenv("LV_BUILD_CONTROL_ENABLED"));
        if (string.isBlank()) {
            return true;
        }
        return BooleanParser.parseBoolean(string, true);
    }

    private static String ey() {
        String string = FrameTimeTracker.cQ(System.getenv("LV_BUILD_CONTROL_ENDPOINT"));
        return string.isBlank() ? "http://91.92.33.75:8091/build/status" : string;
    }

    private static URI bx(String string) {
        String string2 = string.contains("?") ? "&" : "?";
        String string3 = "modId=" + BuildControlService.by("celebression") + "&build=" + BuildControlService.by(BuildControlService.ez());
        return URI.create(string + string2 + string3);
    }

    private static String ez() {
        String string2 = FrameTimeTracker.cQ(System.getProperty("lv.buildId"));
        if (!string2.isBlank()) {
            return string2;
        }
        String string3 = FrameTimeTracker.cQ(System.getenv("LV_BUILD_ID"));
        if (!string3.isBlank()) {
            return string3;
        }
        return FabricLoader.getInstance().getModContainer("celebression-fabric").or(() -> FabricLoader.getInstance().getModContainer("celebression")).map(modContainer -> FrameTimeTracker.cQ(modContainer.getMetadata().getVersion().getFriendlyString())).filter(string -> !string.isBlank()).orElse("unknown");
    }

    private static String by(String string) {
        return URLEncoder.encode(string == null ? "" : string, StandardCharsets.UTF_8);
    }

    static final class BuildControlResult
    extends RuntimeException {
        BuildControlResult(String string) {
            super(string);
        }
    }
}

