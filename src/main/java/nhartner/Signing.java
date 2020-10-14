package nhartner;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.nio.file.Path;
import java.nio.file.Paths;


public class Signing {
    public interface CSigning extends Library {
        Path signingLibPath = Paths.get(Signing.class.getClassLoader().getResource("libsigning_lib_rs.dylib").getPath());
        CSigning INSTANCE = Native.load(signingLibPath.toString(), CSigning.class);
        Pointer ed25519_new(String value);
        String ed25519_sign(Pointer algo, String message);
        boolean ed25519_verify(Pointer algo, String message, String signature);
    }

    public static void main(String[] args) {
        Pointer ed25519 = CSigning.INSTANCE.ed25519_new("0102030405060708090a0b0c0d0e0f10");
        String signature = CSigning.INSTANCE.ed25519_sign(ed25519, "test message");
        System.out.println(signature);
        boolean verify = CSigning.INSTANCE.ed25519_verify(ed25519, "test message", signature);
        System.out.println(verify);
    }
}