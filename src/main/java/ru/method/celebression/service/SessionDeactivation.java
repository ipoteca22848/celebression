package ru.method.celebression.service;

/** One-way session state: take a snapshot once, then disable until the JVM exits. */
public final class SessionDeactivation {
    private volatile boolean active;
    public boolean isActive() { return active; }
    public synchronized boolean deactivate(Runnable snapshot, Runnable cleanup) {
        if (active) return false;
        snapshot.run();
        active = true;
        cleanup.run();
        return true;
    }
}
