package ru.method.celebression.utils.mediaplayer;

import java.awt.AWTException;
import java.awt.Robot;
import ru.method.celebression.Celebression;

public final class WindowsMediaKeys {
    private static final int VK_MEDIA_PREVIOUS = 177;
    private static final int VK_MEDIA_NEXT = 176;
    private static final int VK_MEDIA_PLAY_PAUSE = 179;

    private WindowsMediaKeys() {
    }

    public static boolean send(MediaAction mediaAction) {
        if (!WindowsMediaKeys.isWindows()) {
            return false;
        }
        int n = switch (mediaAction.ordinal()) {
            default -> throw new IllegalStateException("Unexpected enum value");
            case 0 -> 177;
            case 2 -> 176;
            case 1 -> 179;
        };
        try {
            Robot robot = new Robot();
            robot.keyPress(n);
            robot.keyRelease(n);
            return true;
        }
        catch (AWTException aWTException) {
            Celebression.LOGGER.debug("[MediaKeys] Failed to send {}", (Object)mediaAction, (Object)aWTException);
            return false;
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    public static enum MediaAction {
        PREVIOUS,
        PLAY_PAUSE,
        NEXT;

    }
}

