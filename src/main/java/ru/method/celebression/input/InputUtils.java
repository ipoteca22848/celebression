package ru.method.celebression.input;

import lombok.Generated;
import org.lwjgl.glfw.GLFW;

public final class InputUtils {
    public static boolean b(int n, int n2, float f, float f2, float f3, float f4) {
        return (float)n >= f && (float)n <= f + f3 && (float)n2 >= f2 && (float)n2 <= f2 + f4;
    }

    public static void rb() {
        long l = GLFW.glfwGetCurrentContext();
        if (l != 0L) {
            GLFW.glfwSetCursor((long)l, (long)GLFW.glfwCreateStandardCursor((int)221188));
        }
    }

    public static void rc() {
        long l = GLFW.glfwGetCurrentContext();
        if (l != 0L) {
            GLFW.glfwSetCursor((long)l, (long)GLFW.glfwCreateStandardCursor((int)221185));
        }
    }

    @Generated
    private InputUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

