import ru.method.celebression.service.SessionDeactivation;

public class SessionDeactivationSmoke {
    public static void main(String[] args) {
        var state = new SessionDeactivation();
        int[] calls = {0, 0};
        boolean changed = state.deactivate(() -> {
            if (state.isActive()) throw new AssertionError("Settings must be saved before disabling");
            calls[0]++;
        }, () -> {
            if (!state.isActive()) throw new AssertionError("Callbacks must see inactive client during cleanup");
            calls[1]++;
        });
        if (!changed || !state.isActive()) throw new AssertionError("Deactivation was not latched");
        if (state.deactivate(() -> calls[0]++, () -> calls[1]++)) throw new AssertionError("Duplicate deactivation");
        if (calls[0] != 1 || calls[1] != 1) throw new AssertionError("State was saved or cleaned twice");
        var failure = new SessionDeactivation();
        try {
            failure.deactivate(() -> {}, () -> { throw new IllegalStateException("cleanup failure"); });
            throw new AssertionError("Expected cleanup failure");
        } catch (IllegalStateException expected) {
            if (!failure.isActive()) throw new AssertionError("Cleanup failure re-enabled client");
        }
        System.out.println("Session deactivation: ordering, one-shot behavior and failure state passed");
    }
}
