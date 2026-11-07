import java.nio.file.*;
import ru.method.celebression.service.DataDirectory;

public class DataDirectorySmoke {
    public static void main(String[] args) throws Exception {
        Path game = Files.createTempDirectory("celebression-data-test");
        try {
            Path old = game.resolve("celebression/configs");
            Files.createDirectories(old);
            Files.writeString(old.resolve("default.celebression"), "previous settings");
            Files.createDirectories(game.resolve("config/punchy"));
            Files.writeString(game.resolve("config/punchy/punchy_config.json"), "animation settings");
            Path target = DataDirectory.prepare(game);
            if (!target.equals(game.resolve(".cache/.ce"))) throw new AssertionError("Unexpected data location");
            if (!Files.readString(target.resolve("configs/default.celebression")).equals("previous settings")) throw new AssertionError("Config migration failed");
            if (!Files.readString(target.resolve("config/punchy/punchy_config.json")).equals("animation settings")) throw new AssertionError("Animation config migration failed");
            Files.writeString(target.resolve("configs/default.celebression"), "new settings");
            DataDirectory.prepare(game);
            if (!Files.readString(target.resolve("configs/default.celebression")).equals("new settings")) throw new AssertionError("Existing target was overwritten");
            if (!Files.readString(old.resolve("default.celebression")).equals("previous settings")) throw new AssertionError("Legacy backup was changed");
            System.out.println("DataDirectory: migration, repeatability and existing settings preserved");
        } finally {
            try (var paths = Files.walk(game)) {
                for (Path path : paths.sorted(java.util.Comparator.reverseOrder()).toList()) Files.delete(path);
            }
        }
    }
}
