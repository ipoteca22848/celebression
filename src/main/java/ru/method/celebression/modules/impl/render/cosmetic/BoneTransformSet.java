package ru.method.celebression.modules.impl.render.cosmetic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.class_3532;

public final class BoneTransformSet {
    private final Map<String, float[]> bl = new HashMap<String, float[]>();
    private final Map<String, float[]> bm = new HashMap<String, float[]>();
    private final Map<String, float[]> bn = new HashMap<String, float[]>();

    public void a(String string, float f, float f2, float f3) {
        this.bl.put(string, new float[]{f, f2, f3});
    }

    public void b(String string, float f, float f2, float f3) {
        this.bm.put(string, new float[]{f, f2, f3});
    }

    public void c(String string, float f, float f2, float f3) {
        this.bn.put(string, new float[]{f, f2, f3});
    }

    public float s(String string) {
        return BoneTransformSet.a(this.bl, string, 0);
    }

    public float t(String string) {
        return BoneTransformSet.a(this.bl, string, 1);
    }

    public float u(String string) {
        return BoneTransformSet.a(this.bl, string, 2);
    }

    public boolean v(String string) {
        return this.bm.containsKey(string);
    }

    public float w(String string) {
        return BoneTransformSet.a(this.bm, string, 0);
    }

    public float x(String string) {
        return BoneTransformSet.a(this.bm, string, 1);
    }

    public float y(String string) {
        return BoneTransformSet.a(this.bm, string, 2);
    }

    public boolean z(String string) {
        return this.bn.containsKey(string);
    }

    public float aa(String string) {
        float[] fArray = this.bn.get(string);
        return fArray == null ? 1.0f : fArray[0];
    }

    public float ab(String string) {
        float[] fArray = this.bn.get(string);
        return fArray == null ? 1.0f : fArray[1];
    }

    public float ac(String string) {
        float[] fArray = this.bn.get(string);
        return fArray == null ? 1.0f : fArray[2];
    }

    public void a(BoneTransformSet boneTransformSet) {
        this.bl.clear();
        this.bm.clear();
        this.bn.clear();
        if (boneTransformSet == null) {
            return;
        }
        BoneTransformSet.a(boneTransformSet.bl, this.bl);
        BoneTransformSet.a(boneTransformSet.bm, this.bm);
        BoneTransformSet.a(boneTransformSet.bn, this.bn);
    }

    public static BoneTransformSet a(BoneTransformSet boneTransformSet, BoneTransformSet boneTransformSet2, float f) {
        BoneTransformSet boneTransformSet3 = new BoneTransformSet();
        if (boneTransformSet == null && boneTransformSet2 == null) {
            return boneTransformSet3;
        }
        if (boneTransformSet == null) {
            boneTransformSet3.a(boneTransformSet2);
            return boneTransformSet3;
        }
        if (boneTransformSet2 == null) {
            boneTransformSet3.a(boneTransformSet);
            return boneTransformSet3;
        }
        float f2 = class_3532.method_15363((float)f, (float)0.0f, (float)1.0f);
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(boneTransformSet.bl.keySet());
        hashSet.addAll(boneTransformSet2.bl.keySet());
        hashSet.addAll(boneTransformSet.bm.keySet());
        hashSet.addAll(boneTransformSet2.bm.keySet());
        hashSet.addAll(boneTransformSet.bn.keySet());
        hashSet.addAll(boneTransformSet2.bn.keySet());
        for (String string : hashSet) {
            if (boneTransformSet.bl.containsKey(string) || boneTransformSet2.bl.containsKey(string)) {
                boneTransformSet3.a(string, class_3532.method_16439((float)f2, (float)boneTransformSet.s(string), (float)boneTransformSet2.s(string)), class_3532.method_16439((float)f2, (float)boneTransformSet.t(string), (float)boneTransformSet2.t(string)), class_3532.method_16439((float)f2, (float)boneTransformSet.u(string), (float)boneTransformSet2.u(string)));
            }
            if (boneTransformSet.bm.containsKey(string) || boneTransformSet2.bm.containsKey(string)) {
                boneTransformSet3.b(string, class_3532.method_16439((float)f2, (float)boneTransformSet.w(string), (float)boneTransformSet2.w(string)), class_3532.method_16439((float)f2, (float)boneTransformSet.x(string), (float)boneTransformSet2.x(string)), class_3532.method_16439((float)f2, (float)boneTransformSet.y(string), (float)boneTransformSet2.y(string)));
            }
            if (!boneTransformSet.bn.containsKey(string) && !boneTransformSet2.bn.containsKey(string)) continue;
            boneTransformSet3.c(string, class_3532.method_16439((float)f2, (float)boneTransformSet.aa(string), (float)boneTransformSet2.aa(string)), class_3532.method_16439((float)f2, (float)boneTransformSet.ab(string), (float)boneTransformSet2.ab(string)), class_3532.method_16439((float)f2, (float)boneTransformSet.ac(string), (float)boneTransformSet2.ac(string)));
        }
        return boneTransformSet3;
    }

    private static void a(Map<String, float[]> map, Map<String, float[]> map2) {
        for (Map.Entry<String, float[]> entry : map.entrySet()) {
            map2.put(entry.getKey(), (float[])entry.getValue().clone());
        }
    }

    private static float a(Map<String, float[]> map, String string, int n) {
        float[] fArray = map.get(string);
        return fArray == null || n < 0 || n >= fArray.length ? 0.0f : fArray[n];
    }
}

