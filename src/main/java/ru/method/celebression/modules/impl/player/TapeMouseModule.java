package ru.method.celebression.modules.impl.player;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_239;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;
import ru.method.celebression.api.setting.SliderSetting;

public class TapeMouseModule
extends Module {
    private final EnumSetting<ClickState> xb = new EnumSetting<ClickState>("modules.settings.tape_mouse.button", ClickState.xe);
    private final SliderSetting xc = new SliderSetting("modules.settings.tape_mouse.delay", 50.0f, 30.0f, 500.0f, 1.0f);
    private long xd;

    public TapeMouseModule() {
        super("TapeMouse", ModuleCategory.qm);
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        if (TapeMouseModule.fullNullCheck() || TapeMouseModule.aiG.field_1755 != null || TapeMouseModule.aiG.field_1761 == null) {
            return;
        }
        long l = System.currentTimeMillis();
        if ((float)(l - this.xd) < ((Float)this.xc.jT()).floatValue()) {
            return;
        }
        this.xd = l;
        if (this.xb.jT() == ClickState.xe) {
            this.hP();
        } else {
            this.hQ();
        }
    }

    private void hP() {
        class_3966 class_39662;
        class_239 class_2392 = TapeMouseModule.aiG.field_1765;
        if (class_2392 instanceof class_3966 && (class_39662 = (class_3966)class_2392).method_17782() != TapeMouseModule.aiG.field_1724) {
            TapeMouseModule.aiG.field_1761.method_2918((class_1657)TapeMouseModule.aiG.field_1724, class_39662.method_17782());
            TapeMouseModule.aiG.field_1724.method_6104(class_1268.field_5808);
            return;
        }
        if (class_2392 instanceof class_3965) {
            class_3965 blockHit = (class_3965)class_2392;
            TapeMouseModule.aiG.field_1761.method_2910(blockHit.method_17777(), blockHit.method_17780());
            TapeMouseModule.aiG.field_1724.method_6104(class_1268.field_5808);
            return;
        }
        TapeMouseModule.aiG.field_1724.method_6104(class_1268.field_5808);
    }

    private void hQ() {
        class_239 class_2392 = TapeMouseModule.aiG.field_1765;
        if (class_2392 instanceof class_3966) {
            class_3966 class_39662 = (class_3966)class_2392;
            TapeMouseModule.aiG.field_1761.method_2905((class_1657)TapeMouseModule.aiG.field_1724, class_39662.method_17782(), class_1268.field_5808);
        } else if (class_2392 instanceof class_3965) {
            class_3965 class_39652 = (class_3965)class_2392;
            TapeMouseModule.aiG.field_1761.method_2896(TapeMouseModule.aiG.field_1724, class_1268.field_5808, class_39652);
        } else {
            TapeMouseModule.aiG.field_1761.method_2919((class_1657)TapeMouseModule.aiG.field_1724, class_1268.field_5808);
        }
        TapeMouseModule.aiG.field_1724.method_6104(class_1268.field_5808);
    }

    static enum ClickState
    implements NamedOption  {
        xe("modules.settings.tape_mouse.button.left"),
        xf("modules.settings.tape_mouse.button.right");
        private final String xg;

private ClickState(String string2) {
            this.xg = string2;
        }

        @Override
        public String getName() {
            return this.xg;
        }

}
}
