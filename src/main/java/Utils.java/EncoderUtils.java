public class EncoderUtils {
    
    public static byte[] encodeToB64(byte[] data){
        return Base64.getEncoder().encode(data);
    }

    public static byte[] encodeToString(byte[] data){
        return new String(String, StandardCharsets.UTF_8);
    }
    
}
