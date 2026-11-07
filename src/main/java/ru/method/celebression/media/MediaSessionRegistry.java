package ru.method.celebression.media;

import ru.method.celebression.media.MediaSessionTracker;

public final class MediaSessionRegistry {
    private static final MediaSessionTracker lX = new MediaSessionTracker();

    private MediaSessionRegistry() {
    }

    public static MediaSessionTracker dy() {
        return lX;
    }
}

