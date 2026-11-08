package ru.method.celebression.server;

/** Hides a session nickname accidentally saved as a server label, keeping the server. */
public final class ServerLabel {
    private ServerLabel() {}
    public static String resolve(String name, String address, String nickname) {
        String clean = name == null ? "" : name.trim();
        if (clean.isEmpty() || (nickname != null && clean.equalsIgnoreCase(nickname.trim()))) {
            return address == null || address.isBlank() ? "Сервер" : address.trim();
        }
        return clean;
    }
}
