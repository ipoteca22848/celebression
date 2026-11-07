package ru.method.celebression.punchy.client.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.joml.Vector3f;
import ru.method.celebression.punchy.client.model.BedrockBone;

public class BedrockGeoModel {
    private final Map<String, BedrockBone> bones = new LinkedHashMap<String, BedrockBone>();

    public void addBone(BedrockBone bedrockBone) {
        if (bedrockBone != null) {
            this.bones.put(bedrockBone.getName(), bedrockBone);
        }
    }

    public Map<String, BedrockBone> getBones() {
        return Collections.unmodifiableMap(this.bones);
    }

    public Vector3f getLocalPivot(String string) {
        BedrockBone bedrockBone = this.bones.get(string);
        return bedrockBone != null ? bedrockBone.getLocalPivot() : null;
    }
}

