package ru.method.celebression.animation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_3532;

public final class AnimationKeyframe {
    private static final float[] aN = new float[]{0.0f, 0.0f, 0.0f};
    private final List<TimedVector> aO;

    private AnimationKeyframe(List<TimedVector> list) {
        this.aO = list;
    }

    static AnimationKeyframe g(JsonObject jsonObject) {
        if (jsonObject == null) {
            return AnimationKeyframe.by();
        }
        ArrayList<TimedVector> arrayList = new ArrayList<TimedVector>();
        for (Map.Entry entry : jsonObject.entrySet()) {
            float f = AnimationKeyframe.parseTime((String)entry.getKey());
            VectorSample vectorSample = AnimationKeyframe.a((JsonElement)entry.getValue());
            if (vectorSample == null) continue;
            arrayList.add(new TimedVector(f, vectorSample.bz(), vectorSample.A()));
        }
        arrayList.sort(Comparator.comparingDouble(timedVector -> timedVector.aP));
        return new AnimationKeyframe(arrayList);
    }

    static AnimationKeyframe by() {
        return new AnimationKeyframe(List.of());
    }

    boolean isEmpty() {
        return this.aO.isEmpty();
    }

    float[] a(float f, float f2, boolean bl) {
        if (this.aO.isEmpty()) {
            return aN;
        }
        if (this.aO.size() == 1) {
            return (float[])this.aO.getFirst().aQ.clone();
        }
        float f3 = bl ? AnimationKeyframe.b(f, f2) : class_3532.method_15363((float)f, (float)0.0f, (float)f2);
        int n = this.d(f3);
        int n2 = Math.max(0, n - 1);
        TimedVector timedVector = this.aO.get(n2);
        TimedVector timedVector2 = this.aO.get(n);
        if (timedVector2.aP <= timedVector.aP) {
            return (float[])timedVector.aQ.clone();
        }
        float f4 = (f3 - timedVector.aP) / (timedVector2.aP - timedVector.aP);
        f4 = AnimationKeyframe.a(f4, timedVector2.A());
        if (AnimationKeyframe.ae(timedVector2.A()) && this.aO.size() >= 4) {
            int n3 = Math.max(0, n2 - 1);
            int n4 = Math.min(this.aO.size() - 1, n + 1);
            return new float[]{AnimationKeyframe.a(this.aO.get((int)n3).aQ[0], timedVector.aQ[0], timedVector2.aQ[0], this.aO.get((int)n4).aQ[0], f4), AnimationKeyframe.a(this.aO.get((int)n3).aQ[1], timedVector.aQ[1], timedVector2.aQ[1], this.aO.get((int)n4).aQ[1], f4), AnimationKeyframe.a(this.aO.get((int)n3).aQ[2], timedVector.aQ[2], timedVector2.aQ[2], this.aO.get((int)n4).aQ[2], f4)};
        }
        return new float[]{AnimationKeyframe.lerp(timedVector.aQ[0], timedVector2.aQ[0], f4), AnimationKeyframe.lerp(timedVector.aQ[1], timedVector2.aQ[1], f4), AnimationKeyframe.lerp(timedVector.aQ[2], timedVector2.aQ[2], f4)};
    }

    private int d(float f) {
        for (int i = 0; i < this.aO.size(); ++i) {
            if (!(f <= this.aO.get((int)i).aP)) continue;
            return i;
        }
        return this.aO.size() - 1;
    }

    private static float b(float f, float f2) {
        if (f2 <= 0.0f) {
            return f;
        }
        float f3 = f % f2;
        return f3 < 0.0f ? f3 + f2 : f3;
    }

    private static float parseTime(String string) {
        try {
            return Float.parseFloat(string);
        }
        catch (NumberFormatException numberFormatException) {
            return 0.0f;
        }
    }

    private static VectorSample a(JsonElement jsonElement) {
        float[] fArray;
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        if (jsonElement.isJsonArray()) {
            float[] fArray2 = AnimationKeyframe.a(jsonElement.getAsJsonArray());
            return fArray2 == null ? null : new VectorSample(fArray2, "linear");
        }
        if (!jsonElement.isJsonObject()) {
            return null;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        float[] fArray3 = fArray = jsonObject.has("vector") && jsonObject.get("vector").isJsonArray() ? AnimationKeyframe.a(jsonObject.getAsJsonArray("vector")) : null;
        if (fArray == null) {
            return null;
        }
        String string = jsonObject.has("lerp_mode") ? jsonObject.get("lerp_mode").getAsString() : "linear";
        return new VectorSample(fArray, string);
    }

    private static float[] a(JsonArray jsonArray) {
        if (jsonArray.size() < 3) {
            return null;
        }
        return new float[]{jsonArray.get(0).getAsFloat(), jsonArray.get(1).getAsFloat(), jsonArray.get(2).getAsFloat()};
    }

    private static boolean ae(String string) {
        return string != null && string.toLowerCase(Locale.ROOT).contains("catmull");
    }

    private static float a(float f, String string) {
        if (string == null) {
            return f;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.contains("easeinout")) {
            return f * f * (3.0f - 2.0f * f);
        }
        return f;
    }

    private static float a(float f, float f2, float f3, float f4, float f5) {
        float f6 = f5 * f5;
        float f7 = f6 * f5;
        return 0.5f * (2.0f * f2 + (-f + f3) * f5 + (2.0f * f - 5.0f * f2 + 4.0f * f3 - f4) * f6 + (-f + 3.0f * f2 - 3.0f * f3 + f4) * f7);
    }

    private static float lerp(float f, float f2, float f3) {
        return f + (f2 - f) * f3;
    }

    static final class VectorSample {
        private final float[] aS;
        private final String aT;

        VectorSample(float[] fArray, String string) {
            this.aS = fArray;
            this.aT = string;
        }

        @Override
        public final String toString() {
            return "VectorSample[" + "vector=" + this.aS + ", " + "lerpMode=" + this.aT + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.aS, this.aT);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof VectorSample other)) return false;
            return java.util.Objects.deepEquals(this.aS, other.aS)
                && java.util.Objects.deepEquals(this.aT, other.aT);
        }

        public float[] bz() {
            return this.aS;
        }

        public String A() {
            return this.aT;
        }
    }

    static final class TimedVector {
        final float aP;
        final float[] aQ;
        private final String aR;

        TimedVector(float f, float[] fArray, String string) {
            this.aP = f;
            this.aQ = fArray;
            this.aR = string;
        }

        @Override
        public final String toString() {
            return "TimedVector[" + "time=" + this.aP + ", " + "vector=" + this.aQ + ", " + "lerpMode=" + this.aR + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.aP, this.aQ, this.aR);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof TimedVector other)) return false;
            return java.util.Objects.deepEquals(this.aP, other.aP)
                && java.util.Objects.deepEquals(this.aQ, other.aQ)
                && java.util.Objects.deepEquals(this.aR, other.aR);
        }

        public float time() {
            return this.aP;
        }

        public float[] bz() {
            return this.aQ;
        }

        public String A() {
            return this.aR;
        }
    }
}

