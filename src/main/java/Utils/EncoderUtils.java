package Utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncoderUtils {
    
    public static byte[] encodeToB64(byte[] data){
        return Base64.getEncoder().encode(data);
    }

    public static String encodeToString(byte[] data){
        return new String(data, StandardCharsets.UTF_8);
    }
    
}
