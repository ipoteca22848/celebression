import java.nio.file.*;
import ru.method.celebression.service.AppearancePreferences;
import ru.method.celebression.service.IntroTransition;
import ru.method.celebression.server.ServerAds;

public class MenuBehaviorSmoke {
    public static void main(String[] args) throws Exception {
        Path directory = Files.createTempDirectory("celebression-appearance-test");
        try {
            Path file = directory.resolve("appearance.properties");
            var preferences = AppearancePreferences.load(file);
            if (!preferences.isCustom()) throw new AssertionError("Custom appearance should be the initial default");
            preferences.setCustom(false);
            if (AppearancePreferences.load(file).isCustom()) throw new AssertionError("Vanilla choice was not persisted");
            preferences.setCustom(true);
            if (!AppearancePreferences.load(file).isCustom()) throw new AssertionError("Custom choice was not persisted");
            if (IntroTransition.progress(100, 100, 1000) != 0f) throw new AssertionError("Intro must start transparent");
            if (Math.abs(IntroTransition.progress(100, 600, 1000) - .5f) > .0001f) throw new AssertionError("Wrong midpoint");
            if (IntroTransition.progress(100, 1100, 1000) != 1f) throw new AssertionError("Intro did not finish");
            if (IntroTransition.progress(100, 50, 1000) != 0f) throw new AssertionError("Intro became negative");
            float previous = 0f;
            for (long now = 100; now <= 1200; now += 7) {
                float value = IntroTransition.progress(100, now, 1000);
                if (value < previous || value > 1f) throw new AssertionError("Intro is not monotonic and bounded");
                previous = value;
            }
            if (!ServerAds.isPromotion("§aCuteWorld", "mc.example.org")) throw new AssertionError("Branded promotion not removed");
            if (!ServerAds.isPromotion("Server", "MC.ARTYGRIEF.SU:25565")) throw new AssertionError("Promoted address not removed");
            if (!ServerAds.isPromotion("Кьютворлд", "example.org")) throw new AssertionError("Russian promotion not removed");
            if (ServerAds.isPromotion("My private server", "play.example.org:25565")) throw new AssertionError("Personal server removed");
            if (ServerAds.isPromotion("Cute friends", "cute.example.org")) throw new AssertionError("Unrelated server removed");
            if (ServerAds.isPromotion(null, null)) throw new AssertionError("Empty server treated as promotion");
            System.out.println("Menus: appearance persistence, smooth intro and promotion filtering passed");
        } finally {
            try (var paths = Files.walk(directory)) {
                for (Path path : paths.sorted(java.util.Comparator.reverseOrder()).toList()) Files.delete(path);
            }
        }
    }
}
