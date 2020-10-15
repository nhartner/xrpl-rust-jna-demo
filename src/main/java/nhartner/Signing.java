package nhartner;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.nio.file.Path;
import java.nio.file.Paths;


public class Signing {
    public interface CSigning extends Library {
        Path signingLibPath = Paths.get(Signing.class.getClassLoader().getResource(getNativeLibraryName()).getPath());
        CSigning INSTANCE = Native.load(signingLibPath.toString(), CSigning.class);
        Pointer ed25519_new(String value);
        String ed25519_sign(Pointer algo, String message);
        boolean ed25519_verify(Pointer algo, String message, String signature);

        void ed25519_free_signature(String signature);
        void ed25519_free(Pointer algo);
    }

    public static void main(String[] args) {
        Pointer ed25519 = CSigning.INSTANCE.ed25519_new("0102030405060708090a0b0c0d0e0f10");
        String signature = CSigning.INSTANCE.ed25519_sign(ed25519, "test message");
        System.out.println(signature);
        boolean verify = CSigning.INSTANCE.ed25519_verify(ed25519, "test message", signature);
        System.out.println(verify);

        CSigning.INSTANCE.ed25519_free_signature(signature);
        CSigning.INSTANCE.ed25519_free(ed25519);
    }

    private static String getNativeLibraryName() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("mac")) {
            return "libsigning_lib_rs.dylib";
        }
        if (OS.contains("nux")) {
            return "libsigning_lib_rs.so";
        }
        else throw new UnsupportedOperationException(OS + " is not a supported OS");
    }
}