package ru.method.celebression.manager;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_7923;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;

public class SoundManager
implements MinecraftClientAccess {
    private static final long pI = 650L;
    private final class_3414 pJ;
    private final class_3414 pK;
    private final class_3414 pL;
    private final class_3414 pM;
    private final Map<SoundEntry, class_3414> pN = new HashMap<SoundEntry, class_3414>();
    private long pO = -1L;
    private Module pP;

    public SoundManager() {
        this.pJ = this.aE("enable");
        this.pK = this.aE("disable");
        this.pL = this.aE("critical");
        this.pM = this.aE("clickgui_open");
        for (SoundEntry soundEntry : SoundEntry.values()) {
            this.pN.put(soundEntry, this.aE(soundEntry.fI()));
        }
    }

    private class_3414 aE(String string) {
        class_2960 class_29602 = Celebression.id(string);
        return class_7923.field_41172.method_10250(class_29602) ? (class_3414)class_7923.field_41172.method_63535(class_29602) : class_3414.method_47908((class_2960)class_29602);
    }

    private void a(class_3414 class_34142) {
        if (class_34142 == null || aiG.method_1483() == null) {
            return;
        }
        aiG.method_1483().method_4873((class_1113)class_1109.method_4758((class_3414)class_34142, (float)1.0f));
    }

    public void fF() {
        this.a(this.pJ);
    }

    public void fG() {
        this.a(this.pK);
    }

    public void a(Module module) {
        this.pO = System.currentTimeMillis();
        this.pP = module;
        this.a(this.pL);
    }

    public void fH() {
        this.a(this.pM);
    }

    public void a(SoundEntry soundEntry) {
        if (soundEntry == null) {
            return;
        }
        this.a(this.pN.get((Object)soundEntry));
    }

    public class_3414 b(SoundEntry soundEntry) {
        return this.pN.get((Object)soundEntry);
    }

    public float b(Module module) {
        if (module == null) {
            return 0.0f;
        }
        if (this.pP != module) {
            return 0.0f;
        }
        if (this.pO < 0L) {
            return 0.0f;
        }
        long l = System.currentTimeMillis() - this.pO;
        if (l >= 650L) {
            this.pO = -1L;
            this.pP = null;
            return 0.0f;
        }
        float f = (float)l / 650.0f;
        float f2 = 1.0f - f;
        return (float)(Math.sin((double)f * Math.PI * 6.0) * (double)f2 * 4.0);
    }

    public static enum SoundEntry {
        pQ("hellodragons"),
        pR("krithealth_lechisy"),
        pS("molodec_ubil"),
        pT("unichtozjen"),
        pU("zapen"),
        pV("vcheli"),
        pW("respawn"),
        pX("next_raz_povezyo"),
        pY("snes_totema"),
        pZ("multikill"),
        qa("neostonovim"),
        qb("dabkill"),
        qc("dota_double_kill"),
        qd("dota_triple_kill"),
        qe("dota_mega_kill"),
        qf("dota_monster_kill"),
        qg("dota_rampage");
        private final String qh;

private SoundEntry(String string2) {
            this.qh = string2;
        }

        public String fI() {
            return this.qh;
        }

}
}

