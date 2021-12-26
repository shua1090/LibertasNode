package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static byte[] hash(byte[] data) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("Sha3-256").digest(data);
    }
    
}
