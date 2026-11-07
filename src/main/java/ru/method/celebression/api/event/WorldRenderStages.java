package ru.method.celebression.api.event;

import lombok.Generated;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_9779;
import org.joml.Matrix4f;
import ru.method.celebression.api.event.CancellableEvent;

public class WorldRenderStages
extends CancellableEvent {

    public static class CameraStage
    extends CancellableEvent {
        private final class_4184 q;
        private final Matrix4f r;
        private final class_9779 s;

        @Generated
        public CameraStage(class_4184 class_41842, Matrix4f matrix4f, class_9779 class_97792) {
            this.q = class_41842;
            this.r = matrix4f;
            this.s = class_97792;
        }

        @Generated
        public class_4184 m() {
            return this.q;
        }

        @Generated
        public Matrix4f n() {
            return this.r;
        }

        @Generated
        public class_9779 k() {
            return this.s;
        }
    }

    public static class MatrixStackStage
    extends CancellableEvent {
        private final class_9779 n;
        private final class_4587 o;

        @Generated
        public MatrixStackStage(class_9779 class_97792, class_4587 class_45872) {
            this.n = class_97792;
            this.o = class_45872;
        }

        @Generated
        public class_9779 k() {
            return this.n;
        }

        @Generated
        public class_4587 l() {
            return this.o;
        }
    }
}

