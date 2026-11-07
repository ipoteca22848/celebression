import dev.redstones.mediaplayerinfo.MediaInfo;
import kotlinx.serialization.MissingFieldException;
import kotlinx.serialization.json.Json;

public class MediaInfoSmoke {
    public static void main(String[] args) {
        var serializer = MediaInfo.Companion.serializer();
        var expected = new MediaInfo("Track", "Artist", new byte[]{1, 2, 3}, 1234, 5678, true);
        String json = Json.Default.encodeToString(serializer, expected);
        MediaInfo actual = Json.Default.decodeFromString(serializer, json);
        if (!expected.equals(actual)) throw new AssertionError("MediaInfo JSON round-trip lost data");
        try {
            Json.Default.decodeFromString(serializer, "{\"title\":\"missing fields\"}");
            throw new AssertionError("Missing required fields were accepted");
        } catch (MissingFieldException expectedFailure) {
            if (expectedFailure.getMissingFields().size() != 5) throw new AssertionError("Incorrect required-field descriptor");
        }
        System.out.println("MediaInfo: JSON round-trip and required-field rejection passed");
    }
}
