public class Hash {

    public static byte[] hash(byte[] data){
        return MessageDigest.getInstance("Sha3-256").digest(data);
    }
    
}
