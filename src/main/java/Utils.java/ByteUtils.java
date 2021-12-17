public class ByteUtils {
    public static byte[] intToBytes( final int i ) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i); 
        return bb.array();
    }
    public static int convertByteArrayToInt(byte[] intBytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
        return byteBuffer.getInt();
    }

    public static byte[] longToBytes(final long i) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(i);
        return bb.array();
    }

    public static int convertByteArrayToLong(byte[] intBytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
        return byteBuffer.getLong():
    }
}
