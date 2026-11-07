package ru.method.celebression.manager;

import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2596;
import net.minecraft.class_2663;
import net.minecraft.class_2828;
import net.minecraft.class_2848;
import net.minecraft.class_2868;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.event.ClientTickEvent;
import ru.method.celebression.api.accessor.MinecraftClientAccess;
import ru.method.celebression.module.Module;
import ru.method.celebression.api.event.PacketEvent;
import ru.method.celebression.api.event.TotemPopEvent;

public class ServerManager
implements MinecraftClientAccess {
    private int pw;
    private float px;
    private float py;
    private float pz;
    private double pA;
    private double pB;
    private double pC;
    private boolean pD;
    private boolean pE;
    private boolean pF;
    private boolean pG;

    public ServerManager() {
        Celebression.getInstance().getEventHandler().subscribe((Object)this);
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (Module.fullNullCheck()) {
            return;
        }
        double d = ServerManager.aiG.field_1724.field_6036 - ServerManager.aiG.field_1724.method_23318();
        if (ServerManager.aiG.field_1724.method_24828()) {
            this.pz = 0.0f;
        } else if (d > 0.0) {
            this.pz += (float)d;
        }
    }

    @EventHandler
    public void onPacketSend(PacketEvent.Send send) {
        class_2828 class_28282;
        if (Module.fullNullCheck()) {
            return;
        }
        class_2596<?> class_25962 = send.i();
        if (class_25962 instanceof class_2828) {
            class_28282 = (class_2828)class_25962;
            if (class_28282.method_36171()) {
                this.pA = class_28282.method_12269(ServerManager.aiG.field_1724.method_23317());
                this.pB = class_28282.method_12268(ServerManager.aiG.field_1724.method_23318());
                this.pC = class_28282.method_12274(ServerManager.aiG.field_1724.method_23321());
            }
            if (class_28282.method_36172()) {
                this.px = class_28282.method_12271(ServerManager.aiG.field_1724.method_36454());
                this.py = class_28282.method_12270(ServerManager.aiG.field_1724.method_36455());
            }
            this.pD = class_28282.method_12273();
            this.pG = class_28282.method_61225();
        }
        if ((class_25962 = send.i()) instanceof class_2868) {
            class_2868 clientStatusPacket = (class_2868)class_25962;
            this.pw = clientStatusPacket.method_12442();
        }
        if ((class_25962 = send.i()) instanceof class_2848) {
            class_2848 clientCommandPacket = (class_2848)class_25962;
            switch (clientCommandPacket.method_12365()) {
                case field_12981: {
                    this.pE = true;
                    break;
                }
                case field_12985: {
                    this.pE = false;
                    break;
                }
                case field_12979: {
                    this.pF = true;
                    break;
                }
                case field_12984: {
                    this.pF = false;
                }
            }
        }
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive receive) {
        class_2663 class_26632;
        if (Module.fullNullCheck()) {
            return;
        }
        class_2596<?> packet = receive.i();
        if (packet instanceof class_2663 && (class_26632 = (class_2663)packet).method_11470() == 35) {
            class_1297 class_12972 = class_26632.method_11469((class_1937)ServerManager.aiG.field_1687);
            if (!(class_12972 instanceof class_1657)) {
                return;
            }
            class_1657 player = (class_1657)class_12972;
            Celebression.getInstance().getEventHandler().post((Object)new TotemPopEvent(player));
        }
    }

    @Generated
    public int fu() {
        return this.pw;
    }

    @Generated
    public float fv() {
        return this.px;
    }

    @Generated
    public float fw() {
        return this.py;
    }

    @Generated
    public float fx() {
        return this.pz;
    }

    @Generated
    public double fy() {
        return this.pA;
    }

    @Generated
    public double fz() {
        return this.pB;
    }

    @Generated
    public double fA() {
        return this.pC;
    }

    @Generated
    public boolean fB() {
        return this.pD;
    }

    @Generated
    public boolean fC() {
        return this.pE;
    }

    @Generated
    public boolean fD() {
        return this.pF;
    }

    @Generated
    public boolean fE() {
        return this.pG;
    }
}
