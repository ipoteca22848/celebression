package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.AttackEntityEvent;
import ru.method.celebression.api.setting.BooleanSetting;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;

public class HitSoundModule
extends Module {
    private final EnumSetting<HitSound> uG = new EnumSetting<HitSound>("settings.hitsound.sound", HitSound.uI);
    private final BooleanSetting uH = new BooleanSetting("settings.hitsound.only_crit", false);

    public HitSoundModule() {
        super("HitSound", ModuleCategory.qm);
    }

    @EventHandler
    private void onAttack(AttackEntityEvent attackEntityEvent) {
        class_1309 class_13092;
        class_1657 class_16572;
        if (HitSoundModule.fullNullCheck() || attackEntityEvent.a() != HitSoundModule.aiG.field_1724) {
            return;
        }
        if (aiG.method_1483() == null) {
            return;
        }
        class_1297 class_12972 = attackEntityEvent.b();
        if (class_12972 instanceof class_1657 ? (class_16572 = (class_1657)class_12972).method_31481() : (class_12972 = attackEntityEvent.b()) instanceof class_1309 && (!(class_13092 = (class_1309)class_12972).method_5805() || class_13092.method_6032() <= 0.0f)) {
            return;
        }
        if (((Boolean)this.uH.jT()).booleanValue() && !this.a(attackEntityEvent)) {
            return;
        }
        class_3414 soundEvent = ((HitSound)this.uG.jT()).ho();
        if (soundEvent == null) {
            return;
        }
        aiG.method_1483().method_4873((class_1113)class_1109.method_4757(soundEvent, 1.0f, 1.0f));
    }

    private boolean a(AttackEntityEvent attackEntityEvent) {
        class_746 class_7462 = HitSoundModule.aiG.field_1724;
        if (class_7462 == null || !(attackEntityEvent.b() instanceof class_1309)) {
            return false;
        }
        return class_7462.field_6017 > 0.0f && !class_7462.method_24828() && !class_7462.method_6101() && !class_7462.method_5799() && !class_7462.method_6059(class_1294.field_5919) && !class_7462.method_5765() && !class_7462.method_5624() && class_7462.method_7261(0.5f) > 0.9f;
    }

    public static enum HitSound
    implements NamedOption  {
        uI("settings.hitsound.sound.bell", "bell"),
        uJ("settings.hitsound.sound.crime", "crime"),
        uK("settings.hitsound.sound.nya", "nya"),
        uL("settings.hitsound.sound.uwu", "uwu");
        private final String uM;
        private final class_3414 uN;

private HitSound(String string2, String string3) {
            this.uM = string2;
            this.uN = HitSound.bA(string3);
        }

        @Override
        public String getName() {
            return this.uM;
        }

        private static class_3414 bA(String string) {
            class_2960 class_29602 = Celebression.id(string);
            return class_7923.field_41172.method_10250(class_29602) ? (class_3414)class_7923.field_41172.method_63535(class_29602) : class_3414.method_47908((class_2960)class_29602);
        }

        public class_3414 ho() {
            return this.uN;
        }

}
}
