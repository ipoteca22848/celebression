package ru.method.celebression.punchy.client.animation.data;

import ru.method.celebression.animation.Animation;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import ru.method.celebression.punchy.client.animation.data.Keyframe;

public class BoneTrack {
    private final String boneName;
    private final List<Keyframe> keyframes = new ArrayList<Keyframe>();
    private final TreeMap<Float, Keyframe> timeIndex = new TreeMap();

    public BoneTrack(String string) {
        this.boneName = string;
    }

    public void addKeyframe(Keyframe keyframe) {
        this.keyframes.add(keyframe);
        this.timeIndex.put(Float.valueOf(keyframe.timeStamp()), keyframe);
    }

    public String getBoneName() {
        return this.boneName;
    }

    public List<Keyframe> getKeyframes() {
        return this.keyframes;
    }

    public TreeMap<Float, Keyframe> getTimeIndex() {
        return this.timeIndex;
    }
}

