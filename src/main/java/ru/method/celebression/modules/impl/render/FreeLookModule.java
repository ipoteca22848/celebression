package ru.method.celebression.modules.impl.render;

import ru.method.celebression.api.setting.Setting;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import net.minecraft.class_3532;
import net.minecraft.class_5498;
import net.minecraft.class_746;
import org.lwjgl.glfw.GLFW;
import ru.method.celebression.api.setting.BindSetting;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.setting.EnumSetting;
import ru.method.celebression.input.KeyBind;
import ru.method.celebression.module.Module;
import ru.method.celebression.module.ModuleCategory;
import ru.method.celebression.api.setting.NamedOption;

public class FreeLookModule
extends Module {
    private final BindSetting AQ = new BindSetting("settings.freelook.bind", new KeyBind(294, false));
    private final EnumSetting<FreeLookState> AR = new EnumSetting<FreeLookState>("settings.freelook.mode", FreeLookState.Be);
    private boolean active;
    private float AS;
    private float AT;
    private class_5498 AU = class_5498.field_26664;
    private boolean AV;
    private float AW;
    private float AX;
    private float AY;
    private float AZ;
    private float Ba;
    private float Bb;
    private float Bc;
    private float Bd;

    public FreeLookModule() {
        super("Freelook", ModuleCategory.ql);
        this.getSettings().add(this.AQ);
        this.getSettings().add(this.AR);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.iM();
    }

    @Override
    public void onDisable() {
        this.iQ();
        super.onDisable();
    }

    @EventHandler
    private void onTick(ClientTickEvent clientTickEvent) {
        this.iM();
    }

    public void iM() {
        if (!this.isToggled() || FreeLookModule.fullNullCheck() || FreeLookModule.aiG.field_1755 != null) {
            this.iQ();
            return;
        }
        if (this.AR.jT() == FreeLookState.Be && !this.iR()) {
            this.iQ();
        }
        if (this.active && FreeLookModule.aiG.field_1690.method_31044() != class_5498.field_26665) {
            FreeLookModule.aiG.field_1690.method_31043(class_5498.field_26665);
        }
    }

    public void j(int n, int n2) {
        this.a(n, false, n2);
    }

    public void k(int n, int n2) {
        this.a(n, true, n2);
    }

    private void a(int n, boolean bl, int n2) {
        if (!this.isToggled()) {
            return;
        }
        if (FreeLookModule.fullNullCheck() || FreeLookModule.aiG.field_1755 != null) {
            this.iQ();
            return;
        }
        KeyBind keyBind = (KeyBind)this.AQ.jT();
        if (keyBind == null || keyBind.isEmpty() || keyBind.jW() != bl || keyBind.c() != n) {
            return;
        }
        FreeLookState freeLookState = (FreeLookState)this.AR.jT();
        if (freeLookState == FreeLookState.Be) {
            if (n2 == 1) {
                this.iP();
            } else if (n2 == 0) {
                this.iQ();
            }
            return;
        }
        if (n2 == 1) {
            if (this.active) {
                this.iQ();
            } else {
                this.iP();
            }
        }
    }

    public boolean isActive() {
        return this.isToggled() && this.active && !FreeLookModule.fullNullCheck();
    }

    public boolean c(class_1297 class_12972) {
        return class_12972 == FreeLookModule.aiG.field_1724 && this.isActive();
    }

    public float iN() {
        return this.AS;
    }

    public float iO() {
        return this.AT;
    }

    public void a(class_746 class_7462) {
        if (!this.isActive() || class_7462 == null) {
            return;
        }
        this.AV = true;
        this.AW = class_7462.method_36454();
        this.AX = class_7462.method_36455();
        this.AY = class_7462.field_5982;
        this.AZ = class_7462.field_6004;
        this.Ba = class_7462.field_6283;
        this.Bb = class_7462.field_6220;
        this.Bc = class_7462.field_6241;
        this.Bd = class_7462.field_6259;
    }

    public void b(class_746 class_7462) {
        if (!this.AV || class_7462 == null) {
            return;
        }
        this.AV = false;
        if (this.active) {
            float f = class_3532.method_15393((float)(class_7462.method_36454() - this.AW));
            float f2 = class_7462.method_36455() - this.AX;
            this.AS = class_3532.method_15393((float)(this.AS + f));
            this.AT = class_3532.method_15363((float)(this.AT + f2), (float)-90.0f, (float)90.0f);
        }
        class_7462.method_36456(this.AW);
        class_7462.method_36457(this.AX);
        class_7462.field_5982 = this.AY;
        class_7462.field_6004 = this.AZ;
        class_7462.field_6283 = this.Ba;
        class_7462.field_6220 = this.Bb;
        class_7462.field_6241 = this.Bc;
        class_7462.field_6259 = this.Bd;
    }

    public boolean l(int n, int n2) {
        if (!this.isToggled() || FreeLookModule.fullNullCheck() || FreeLookModule.aiG.field_1755 != null) {
            return false;
        }
        KeyBind keyBind = (KeyBind)this.AQ.jT();
        if (keyBind == null || keyBind.isEmpty() || keyBind.jW()) {
            return false;
        }
        return keyBind.af(n) && FreeLookModule.aiG.field_1690.field_1824.method_1417(n, n2);
    }

    private void iP() {
        if (this.active || FreeLookModule.fullNullCheck()) {
            return;
        }
        this.active = true;
        this.AU = FreeLookModule.aiG.field_1690.method_31044();
        this.AS = FreeLookModule.aiG.field_1724.method_36454();
        this.AT = FreeLookModule.aiG.field_1724.method_36455();
        FreeLookModule.aiG.field_1690.method_31043(class_5498.field_26665);
    }

    private void iQ() {
        if (!this.active) {
            return;
        }
        this.active = false;
        this.AV = false;
        FreeLookModule.aiG.field_1690.method_31043(this.AU);
        this.AU = class_5498.field_26664;
    }

    private boolean iR() {
        KeyBind keyBind = (KeyBind)this.AQ.jT();
        if (keyBind == null || keyBind.isEmpty()) {
            return false;
        }
        long l = aiG.method_22683().method_4490();
        if (keyBind.jW()) {
            return GLFW.glfwGetMouseButton((long)l, (int)keyBind.c()) == 1;
        }
        return GLFW.glfwGetKey((long)l, (int)keyBind.c()) == 1;
    }

    static enum FreeLookState
    implements NamedOption  {
        Be("settings.freelook.mode.hold"),
        Bf("settings.freelook.mode.toggle");
        private final String Bg;

private FreeLookState(String string2) {
            this.Bg = string2;
        }

        @Override
        public String getName() {
            return this.Bg;
        }

}
}

