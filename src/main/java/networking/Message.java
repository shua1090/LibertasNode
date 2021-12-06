import java.nio.ByteBuffer;

public class Message {
    public final MessageSendType sendType;
    public final MessageDataType dataType;
    final long MessageLength;
    public final byte[] data;

    private Message(){};

    public Message(MessageSendType sendType, MessageDataType dataType, byte[] data){
        this.sendType = sendType;
        this.dataType = dataType;
        this.MessageLength = data.length;
        this.data = data;
    }

    public long deserializeMessageLength(byte[] headerData){
        byte[] messLen = new byte[8];
        for (int i = 2; i < headerData.length; i++){
            messLen[i-2] = headerData[i];
        }
        return ByteUtils.bytesToLong(messLen);
    }

    public Message deserializeData(byte[] data){
        Message m = new Message();
        m.sendType = MessageSendType.deserialize(data[0]);
        m.dataType = MessageDataType.deserialize(data[1]);
        byte[] messagelen = new byte[8];
        for (int i = 0; i < messagelen.length; i++){
            messagelen[i] = data[i+2];
        }
        m.MessageLength = ByteUtils.bytesToLong(messagelen);
        for (int i = 0; i < m.MessageLength; i++){
            m.data[i] = data[10+i];
        }
        
        return m;
    }

    public byte[] serializeData(){
        byte[] serializedData = new byte[10+MessageLength]
        serializedData[0] = sendType.serialize();
        serializedData[1] = dataType.serialize();

        byte[] temp = ByteUtils.longToBytes(this.MessageLength);
        for (int i = 0; i < temp.length; i++){
            serializedData[2+i] = temp[i];
        }

        for (int i = 0; i < MessageLength; i++){
            serializedData[10+i] = data[i];
        }

        return serializedData;
    }

    class ByteUtils {
        private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);    
    
        public static byte[] longToBytes(long x) {
            buffer.putLong(0, x);
            return buffer.array();
        }
    
        public static long bytesToLong(byte[] bytes) {
            buffer.put(bytes, 0, bytes.length);
            buffer.flip();//need flip 
            return buffer.getLong();
        }
    }

}
