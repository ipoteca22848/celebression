package ru.method.celebression.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ru.method.celebression.module.Module;
import ru.method.celebression.ui.ModuleSettingsPanel;

public final class ClickRippleManager {
    private static final float WU = 10.0f;
    private static final float WV = 8.0f;
    private static final List<ModuleSettingsPanel> WW = new ArrayList<ModuleSettingsPanel>();
    private static float[] WX;
    private static float[] WY;
    private static float WZ;
    private static float Xa;
    private static float Xb;
    private static float Xc;
    private static final float Xd = 48.0f;

    private ClickRippleManager() {
    }

    public static List<ModuleSettingsPanel> nW() {
        return WW;
    }

    public static void clear() {
        for (ModuleSettingsPanel moduleSettingsPanel : WW) {
            moduleSettingsPanel.resetInputState();
        }
        WW.clear();
    }

    public static void nX() {
        for (ModuleSettingsPanel moduleSettingsPanel : WW) {
            moduleSettingsPanel.resetInputState();
        }
    }

    public static void a(float[] fArray, float[] fArray2) {
        WX = fArray;
        WY = fArray2;
    }

    public static void r(float f, float f2) {
        WZ = f;
        Xa = f2;
    }

    public static void s(float f, float f2) {
        Xb = f;
        Xc = f2;
    }

    public static float t(float f, float f2) {
        float f3 = Xc - 48.0f;
        float f4 = Xb - (f2 - 48.0f);
        if (f3 < f4) {
            return Math.min(Xb + 10.0f, f3);
        }
        return Math.max(f4, Math.min(f, f3));
    }

    public static float u(float f, float f2) {
        float f3 = Xa - 48.0f;
        float f4 = WZ + 10.0f;
        if (f3 < f4) {
            f3 = f4;
        }
        return Math.max(f4, Math.min(f, f3));
    }

    public static float ao(float f) {
        return Math.max(24.0f, Xa - WZ - f - 20.0f);
    }

    public static void nY() {
        for (ModuleSettingsPanel moduleSettingsPanel : WW) {
            if (moduleSettingsPanel.isClosing()) continue;
            moduleSettingsPanel.requestClose();
            return;
        }
    }

    public static void nZ() {
        if (WW.isEmpty()) {
            return;
        }
        int n = 0;
        while (n++ < 8) {
            ClickRippleManager.oe();
            ModuleSettingsPanel moduleSettingsPanel = null;
            for (ModuleSettingsPanel moduleSettingsPanel2 : WW) {
                if (moduleSettingsPanel2.isClosing() || moduleSettingsPanel2.om() || !(moduleSettingsPanel2.oh() + moduleSettingsPanel2.og() > ClickRippleManager.ob() + 60.0f) || moduleSettingsPanel != null && moduleSettingsPanel2.on() >= moduleSettingsPanel.on()) continue;
                moduleSettingsPanel = moduleSettingsPanel2;
            }
            if (moduleSettingsPanel == null) break;
            moduleSettingsPanel.requestClose();
        }
    }

    private static float oa() {
        return Math.max(WZ + 10.0f, 130.0f);
    }

    private static float ob() {
        return Xa - 10.0f;
    }

    private static float[] oc() {
        float f = 258.0f;
        return new float[]{960.0f - f / 2.0f, 960.0f + f / 2.0f + 16.0f};
    }

    public static void c(Module module, float f, float f2) {
        for (ModuleSettingsPanel moduleSettingsPanel : WW) {
            if (moduleSettingsPanel.getModule() != module || moduleSettingsPanel.isClosing()) continue;
            return;
        }
        ModuleSettingsPanel moduleSettingsPanel = new ModuleSettingsPanel(module, ClickRippleManager.oc()[0], ClickRippleManager.oa());
        WW.add(moduleSettingsPanel);
        ClickRippleManager.oe();
        int n = 0;
        while (n++ < 16 && !ClickRippleManager.od()) {
            ModuleSettingsPanel moduleSettingsPanel2 = null;
            for (ModuleSettingsPanel moduleSettingsPanel3 : WW) {
                if (moduleSettingsPanel3 == moduleSettingsPanel || moduleSettingsPanel3.isClosing() || moduleSettingsPanel3.om() || moduleSettingsPanel2 != null && moduleSettingsPanel3.on() >= moduleSettingsPanel2.on()) continue;
                moduleSettingsPanel2 = moduleSettingsPanel3;
            }
            if (moduleSettingsPanel2 == null) break;
            moduleSettingsPanel2.requestClose();
            ClickRippleManager.oe();
        }
        moduleSettingsPanel.oi();
    }

    private static boolean od() {
        for (ModuleSettingsPanel moduleSettingsPanel : WW) {
            if (moduleSettingsPanel.isClosing() || moduleSettingsPanel.om() || !(moduleSettingsPanel.oh() + moduleSettingsPanel.og() > ClickRippleManager.ob())) continue;
            return false;
        }
        return true;
    }

    public static void oe() {
        int n;
        float[] fArray = ClickRippleManager.oc();
        float f = 258.0f;
        List[] listArray = new List[fArray.length];
        for (int i = 0; i < fArray.length; ++i) {
            listArray[i] = new ArrayList();
            float[][] object2 = new float[][]{WX, WY};
            n = object2.length;
            for (int j = 0; j < n; ++j) {
                float[] fArray2 = object2[j];
                if (fArray2 == null || !(fArray[i] < fArray2[0] + fArray2[2]) || !(fArray[i] + f > fArray2[0])) continue;
                listArray[i].add(new float[]{fArray2[1], fArray2[1] + fArray2[3]});
            }
        }
        for (ModuleSettingsPanel moduleSettingsPanel3 : WW) {
            if (moduleSettingsPanel3.isClosing() || !moduleSettingsPanel3.om()) continue;
            for (n = 0; n < fArray.length; ++n) {
                if (!(fArray[n] < moduleSettingsPanel3.getX() + f) || !(fArray[n] + f > moduleSettingsPanel3.getX())) continue;
                listArray[n].add(new float[]{moduleSettingsPanel3.getY(), moduleSettingsPanel3.getY() + moduleSettingsPanel3.og()});
            }
        }
        ArrayList<ModuleSettingsPanel> arrayList = new ArrayList<ModuleSettingsPanel>(WW);
        arrayList.sort((moduleSettingsPanel, moduleSettingsPanel2) -> Long.compare(moduleSettingsPanel2.on(), moduleSettingsPanel.on()));
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            ModuleSettingsPanel moduleSettingsPanel4 = (ModuleSettingsPanel)iterator.next();
            if (moduleSettingsPanel4.isClosing() || moduleSettingsPanel4.om()) continue;
            float f2 = moduleSettingsPanel4.og();
            int n2 = -1;
            float f3 = ClickRippleManager.oa();
            int n3 = 0;
            float f4 = Float.MAX_VALUE;
            for (int n4 : ClickRippleManager.q(moduleSettingsPanel4.ol(), fArray.length)) {
                float f5 = ClickRippleManager.b(listArray[n4], f2);
                if (f5 + f2 <= ClickRippleManager.ob()) {
                    n2 = n4;
                    f3 = f5;
                    listArray[n4].add(new float[]{f5, f5 + f2});
                    break;
                }
                if (!(f5 < f4)) continue;
                f4 = f5;
                n3 = n4;
            }
            if (n2 < 0) {
                n2 = n3;
                f3 = f4;
                listArray[n3].add(new float[]{f4, f4 + f2});
            }
            moduleSettingsPanel4.au(n2);
            moduleSettingsPanel4.w(fArray[n2], f3);
        }
    }

    private static int[] q(int n, int n2) {
        int n3 = Math.max(0, Math.min(n2 - 1, n));
        int[] nArray = new int[n2];
        nArray[0] = n3;
        int n4 = 1;
        for (int i = 0; i < n2; ++i) {
            if (i == n3) continue;
            nArray[n4++] = i;
        }
        return nArray;
    }

    private static float b(List<float[]> list, float f) {
        list.sort((fArray, fArray2) -> Float.compare(fArray[0], fArray2[0]));
        float f2 = ClickRippleManager.oa();
        for (float[] fArray3 : list) {
            if (f2 + f <= fArray3[0]) {
                return f2;
            }
            if (!(fArray3[1] + 8.0f > f2)) continue;
            f2 = fArray3[1] + 8.0f;
        }
        return f2;
    }

    static {
        WZ = 0.0f;
        Xa = 720.0f;
        Xb = 0.0f;
        Xc = 1280.0f;
    }
}

