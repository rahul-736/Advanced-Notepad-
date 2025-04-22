import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encryption {
    private static final String KEY = "1234567890123456"; // 16-byte key for AES

    public static String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes()));
    }

    public static String decrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
    }
}
