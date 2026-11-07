package ru.method.celebression.server;

import java.util.ArrayList;
import net.minecraft.class_641;
import net.minecraft.class_642;
import ru.method.celebression.server.ServerListSnapshot;

public class ServerListReorderer {
    public static void a(class_641 class_6412) {
        if (class_6412 == null) {
            return;
        }
        int n = ServerListSnapshot.ss();
        if (n == 0) {
            return;
        }
        boolean bl = false;
        for (int i = 0; i < Math.min(n, class_6412.method_2984()); ++i) {
            class_642 class_6422 = class_6412.method_2982(i);
            class_642 class_6423 = ServerListSnapshot.sr().get(i);
            if (class_6422.field_3761.equals(class_6423.field_3761)) continue;
            bl = true;
            break;
        }
        if (!bl) {
            return;
        }
        ArrayList<class_642> arrayList = new ArrayList<class_642>();
        for (int i = 0; i < class_6412.method_2984(); ++i) {
            arrayList.add(class_6412.method_2982(i));
        }
        ArrayList<class_642> arrayList2 = new ArrayList<class_642>();
        for (int i = 0; i < n; ++i) {
            class_642 class_6424 = ServerListSnapshot.sr().get(i);
            arrayList2.add(new class_642(class_6424.field_3752, class_6424.field_3761, class_642.class_8678.field_45611));
        }
        for (class_642 class_6424 : arrayList) {
            boolean bl2 = false;
            for (class_642 class_6425 : ServerListSnapshot.sr()) {
                if (!class_6424.field_3761.equals(class_6425.field_3761)) continue;
                bl2 = true;
                break;
            }
            if (bl2) continue;
            arrayList2.add(class_6424);
        }
        while (class_6412.method_2984() > 0) {
            class_6412.method_2983(class_6412.method_2982(0));
        }
        for (class_642 class_6424 : arrayList2) {
            class_6412.method_2988(class_6424, false);
        }
    }

    public static boolean a(class_641 class_6412, int n) {
        return n >= 0 && n < Math.min(ServerListSnapshot.ss(), class_6412.method_2984());
    }
}

