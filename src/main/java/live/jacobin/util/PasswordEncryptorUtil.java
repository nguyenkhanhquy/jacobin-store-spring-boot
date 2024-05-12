package live.jacobin.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordEncryptorUtil {

    public static String toSHA1(String str) {
        // Làm cho mật khẩu phức tạp
        String salt = System.getenv("SALT");
        String result = null;

        // Mã hóa SHA-1
        str = str + salt;
        try {
            byte[] dataBytes = str.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            result = Base64.encodeBase64String(md.digest(dataBytes));
        } catch (Exception ignored) {
            
        }
        return result;
    }

}
