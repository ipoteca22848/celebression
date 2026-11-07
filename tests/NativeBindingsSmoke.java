import ru.method.celebression.service.NativeProtectionStatus;
public class NativeBindingsSmoke {
    public static void main(String[] args) {
        boolean enabled = NativeProtectionStatus.enabled();
        System.out.println("Native verifier loaded; enabled=" + enabled);
        if (NativeProtectionStatus.ad("{}", "invalid")) throw new AssertionError("Invalid signature accepted");
        if (NativeProtectionStatus.ad("{}", java.util.Base64.getEncoder().encodeToString(new byte[64]))) throw new AssertionError("Forged signature accepted");
        if (NativeProtectionStatus.ad("{}", null)) throw new AssertionError("Missing signature accepted");
        System.out.println("Malformed, forged and missing signatures rejected");
    }
}
