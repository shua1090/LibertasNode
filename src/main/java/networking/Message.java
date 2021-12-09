package networking;

import com.offbynull.kademlia.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Message {
    private MessageSendType sendType = null;
    private MessageDataType dataType = null;
    private int MessageLength = 0;
    private byte[] data = new byte[0];

    private Message(){}

    public Message(MessageSendType sendType, MessageDataType dataType, byte[] data){
        this.sendType = sendType;
        this.dataType = dataType;
        this.MessageLength = data.length;
        this.data = data;
    }

    public long deserializeMessageLength(byte[] headerData){
        byte[] messLen = new byte[4];
        for (int i = 2; i < headerData.length; i++){
            messLen[i-2] = headerData[i];
        }
        return ByteUtils.bytesToLong(messLen);
    }

    public static Message deserializeData(byte[] data) throws InvalidMessageType {
        Message m = new Message();
        m.sendType = MessageSendType.deserialize(data[0]);
        m.dataType = MessageDataType.deserialize(data[1]);
        byte[] messagelen = new byte[4];
        for (int i = 0; i < messagelen.length; i++){
            messagelen[i] = data[i+2];
        }
        m.MessageLength = ByteUtils.bytesToLong(messagelen);
        for (int i = 0; i < m.MessageLength; i++){
            m.data[i] = data[6+i];
        }
        
        return m;
    }

    public byte[] serializeData(){
        byte[] serializedData = new byte[6+MessageLength];
        serializedData[0] = sendType.serialize();
        serializedData[1] = dataType.serialize();

        byte[] temp = ByteUtils.longToBytes(this.MessageLength);
        for (int i = 0; i < temp.length; i++){
            serializedData[2+i] = temp[i];
        }

        for (int i = 0; i < MessageLength; i++){
            serializedData[6+i] = data[i];
        }

        return serializedData;
    }

    public static void main(String[] args) {
        System.out.println("yes");
        Message m = new Message(MessageSendType.REQUEST, MessageDataType.EPOCH_NUM, new byte[0]);
        System.out.println(m);
        System.out.println(Arrays.toString(m.serializeData()));
        try {
            System.out.println(Message.deserializeData(m.serializeData()));
        } catch (InvalidMessageType e) {
            e.printStackTrace();
        }
//        Message m = new Message()
    }

    static class ByteUtils {
        private static final ByteBuffer buffer = ByteBuffer.allocate(4);
    
        public static byte[] longToBytes(int x) {
            buffer.putInt(0, x);
            return buffer.array();
        }
    
        public static int bytesToLong(byte[] bytes) {
            buffer.put(bytes, 0, bytes.length);
            buffer.flip();//need flip 
            return buffer.getInt();
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "sendType=" + sendType +
                ", dataType=" + dataType +
                ", MessageLength=" + MessageLength +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
