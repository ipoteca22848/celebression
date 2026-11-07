import java.awt.Font;
import java.nio.file.*;
import java.util.regex.*;

public class UiFontSmoke {
    public static void main(String[] args) throws Exception {
        String source = Files.readString(Path.of(args[0]));
        Matcher prefix = Pattern.compile("fontsDir\\s*=\\s*\"([^\"]+)\"").matcher(source);
        if (!prefix.find()) throw new AssertionError("Missing font resource directory");
        Matcher names = Pattern.compile("initializationFont\\([^,]+,\\s*\"([^\"]+)\"\\)").matcher(source);
        int count = 0;
        while (names.find()) {
            String path = prefix.group(1) + names.group(1);
            try (var stream = UiFontSmoke.class.getResourceAsStream(path)) {
                if (stream == null) throw new AssertionError("Font not packaged: " + path);
                Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
                if (font.getNumGlyphs() == 0) throw new AssertionError("Empty font: " + path);
                count++;
            }
        }
        if (count < 1) throw new AssertionError("No font resources checked");
        System.out.println("UI font decoding passed for " + count + " packaged fonts");
    }
}
