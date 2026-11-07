package ru.method.celebression.api.render.providers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3300;
import ru.method.celebression.Celebression;

public final class ResourceProvider {
    private static final Gson GSON = new Gson();

    private static class_3300 resourceManager() {
        return class_310.method_1551().method_1478();
    }

    public static class_2960 getShaderIdentifier(String string) {
        return Celebression.id("core/" + string);
    }

    public static JsonObject toJson(class_2960 class_29602) {
        return JsonParser.parseString((String)ResourceProvider.toString(class_29602)).getAsJsonObject();
    }

    public static <T> T fromJsonToInstance(class_2960 class_29602, Class<T> clazz) {
        return (T)GSON.fromJson(ResourceProvider.toString(class_29602), clazz);
    }

    public static String toString(class_2960 class_29602) {
        return ResourceProvider.toString(class_29602, "\n");
    }

    public static String toString(class_2960 class_29602, String string) {
        try (InputStream inputStream = ResourceProvider.resourceManager().open(class_29602);){
            String string2;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));){
                string2 = bufferedReader.lines().collect(Collectors.joining(string));
            }
            return string2;
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }
}

