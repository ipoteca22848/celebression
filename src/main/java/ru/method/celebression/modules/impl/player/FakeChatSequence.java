package ru.method.celebression.modules.impl.player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_310;
import net.minecraft.class_5250;
import net.minecraft.class_5251;
import net.minecraft.class_640;
import ru.method.celebression.api.mixins.accessors.IClientPlayNetworkHandler;
import ru.method.celebression.command.CrasherChatOutput;

public final class FakeChatSequence {
    private static final int yg = 16729882;
    private static final int yh = 0x8C8C8C;
    private static final int yi = 0xFF6600;
    private static final int yj = 6094821;
    private static final int yk = 0xFFFFE6;
    private static final int yl = 0xD8D8D8;
    private static final int ym = 0xFFFFFF;
    private static final String yn = "\u043f\u0438\u0441\u0435";
    private static final String yo = "\u0411\u0430\u0440\u043e\u043d";
    private static final String[] yp = new String[]{"\u0447\u043e \u0437\u0430 \u043b\u0430\u0433\u0438", "-\u0441\u0435\u0440\u0432", "-\u0441\u0435\u0440\u0432 \u0441\u0440\u043e\u0447\u043d\u043e", "\u0441\u0435\u0440\u0432 \u043b\u0435\u0433 \u043a\u0442\u043e \u0435\u0449\u0451 \u0432\u0438\u0434\u0438\u0442", "\u043d\u0430 \u0441\u043f\u0430\u0432\u043d\u0435 \u043b\u0430\u0432\u0430 \u0447\u043e \u0442\u0430\u043a\u043e\u0435", "\u043a\u0442\u043e \u043b\u0430\u0432\u0443 \u043d\u0430 \u0441\u043f\u0430\u0432\u043d \u0437\u0430\u043b\u0438\u043b", "\u0443 \u043c\u0435\u043d\u044f \u0431\u043b\u043e\u043a\u0438 \u043f\u0440\u043e\u043f\u0430\u043b\u0438 \u043f\u043e\u0441\u0440\u0435\u0434\u0438 \u0441\u043f\u0430\u0432\u043d\u0430", "\u0430\u0434\u043c\u0438\u043d\u044b \u0433\u043b\u044f\u043d\u044c\u0442\u0435 \u0441\u043f\u0430\u0432\u043d", "\u043d\u0443 \u0438 \u0433\u043e\u0432\u043d\u043e", "\u043b\u0430\u0433\u0430\u0435\u0442 \u043f\u0438\u0437\u0434\u0435\u0446", "\u044f \u0432 \u0431\u043b\u043e\u043a\u0430\u0445 \u0437\u0430\u0441\u0442\u0440\u044f\u043b", "\u0447\u0451 \u0437\u0430 \u043a\u0430\u043c\u0435\u043d\u044c \u0432\u0435\u0437\u0434\u0435", "\u0440\u0435\u0441\u0442\u0430\u0440\u0442 \u0434\u0435\u043b\u0430\u0439\u0442\u0435", "\u0443 \u0432\u0441\u0435\u0445 \u0442\u0430\u043a \u0438\u043b\u0438 \u0443 \u043c\u0435\u043d\u044f \u043e\u0434\u043d\u043e\u0433\u043e", "\u0441\u043f\u0430\u0432\u043d \u0441\u043d\u0435\u0441\u043b\u0438 \u0447\u0442\u043e \u043b\u0438"};
    private static final int yq = 100;
    private static final int yr = 140;
    private static final int ys = 12;
    private static final int yt = 36;
    private final Random yu = new Random();
    private final Deque<Message> yv = new ArrayDeque<Message>();
    private int yw;

    public void clear() {
        this.yv.clear();
        this.yw = 0;
    }

    public void im() {
        List<String> list = FakeChatSequence.in();
        if (list.isEmpty()) {
            return;
        }
        this.clear();
        int n = 3 + this.yu.nextInt(2);
        ArrayList<String> arrayList = new ArrayList<String>(List.of(yp));
        Collections.shuffle(arrayList, this.yu);
        Collections.shuffle(list, this.yu);
        int n2 = 100 + this.yu.nextInt(41);
        for (int i = 0; i < n; ++i) {
            String string = list.get(i % list.size());
            String string2 = (String)arrayList.get(i % arrayList.size());
            this.yv.add(new Message(string, string2, n2));
            n2 = 12 + this.yu.nextInt(25);
        }
        this.yw = this.yv.peek() == null ? 0 : this.yv.peek().ip();
    }

    public void tick() {
        Message message;
        if (this.yv.isEmpty()) {
            return;
        }
        if (--this.yw > 0) {
            return;
        }
        Message message2 = this.yv.poll();
        if (message2 != null) {
            CrasherChatOutput.e((class_2561)FakeChatSequence.q(message2.nick(), message2.io()));
        }
        this.yw = (message = this.yv.peek()) == null ? 0 : message.ip();
    }

    private static List<String> in() {
        class_310 class_3102 = class_310.method_1551();
        ArrayList<String> arrayList = new ArrayList<String>();
        if (class_3102.method_1562() == null || class_3102.field_1724 == null) {
            return arrayList;
        }
        for (class_640 class_6402 : ((IClientPlayNetworkHandler)class_3102.method_1562()).celebression$getPlayerListEntries().values()) {
            String string;
            if (class_6402 == null || class_6402.method_2966() == null || (string = class_6402.method_2966().getName()) == null || string.isBlank() || string.equals(class_3102.field_1724.method_7334().getName())) continue;
            arrayList.add(string);
        }
        return arrayList;
    }

    private static class_5250 q(String string, String string2) {
        class_5250 class_52502 = class_2561.method_43470((String)"\u24bc ").method_10862(FakeChatSequence.aa(16729882));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)"<").method_10862(FakeChatSequence.aa(0x8C8C8C)));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)"\u043f\u0438\u0441\u0435").method_10862(FakeChatSequence.aa(0xFF6600)));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)"> ").method_10862(FakeChatSequence.aa(0x8C8C8C)));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)"[").method_10862(FakeChatSequence.aa(0xFFFFFF)));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)"\u0411\u0430\u0440\u043e\u043d").method_10862(FakeChatSequence.aa(6094821)));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)"] ").method_10862(FakeChatSequence.aa(0xFFFFFF)));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)string).method_10862(FakeChatSequence.aa(0xFFFFFF)));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)" \u21e8 ").method_10862(FakeChatSequence.aa(0xFFFFE6)));
        class_52502.method_10852((class_2561)class_2561.method_43470((String)string2).method_10862(FakeChatSequence.aa(0xD8D8D8).method_10978(Boolean.valueOf(true))));
        return class_52502;
    }

    private static class_2583 aa(int n) {
        return class_2583.field_24360.method_27703(class_5251.method_27717((int)n));
    }

    static final class Message {
        private final String yx;
        private final String yy;
        private final int yz;

        Message(String string, String string2, int n) {
            this.yx = string;
            this.yy = string2;
            this.yz = n;
        }

        @Override
        public final String toString() {
            return "Message[" + "nick=" + this.yx + ", " + "phrase=" + this.yy + ", " + "delay=" + this.yz + "]";
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(this.yx, this.yy, this.yz);
        }

        @Override
        public final boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof Message other)) return false;
            return java.util.Objects.deepEquals(this.yx, other.yx)
                && java.util.Objects.deepEquals(this.yy, other.yy)
                && java.util.Objects.deepEquals(this.yz, other.yz);
        }

        public String nick() {
            return this.yx;
        }

        public String io() {
            return this.yy;
        }

        public int ip() {
            return this.yz;
        }
    }
}

