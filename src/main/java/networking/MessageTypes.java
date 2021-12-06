
class InvalidMessageType extends Exception{}

public enum MessageDataType {

    // Blocks
    REQUEST_BLOCK(128), // Do you have this block? 
    //Add delay so that a negligent node can't request someone's block for "HAS_BLOCK"
     // The data after this contains the block
    BLOCK_PROOF(129), // Prove to me that you have this block
     // Submit Hash as proof

    // Transactions
    SUBMIT_TRANSACTION(255),
    
    // Search
    PING_PONG(0),
    FIND_NODE(1),

    // Metadata
    EPOCH_NUM(2),
    BLOCK_NUM(3),
    REQUEST_DIFFICULTY(4),
    REQUEST_MERKLE(5),

    private byte id;
    MessageDataType(byte id){
        this.id = id;
    }
    public byte serialize(){
        return this.id;
    }
    public static MessageDataType deserialize(byte inputData) {
        switch (inputData) {
            case 0: return PING_PONG;
            case 1: return FIND_NODE;
            case 2:
                return EPOCH_NUM;
            case 3:
                return BLOCK_NUM;
            case 4:
                return REQUEST_DIFFICULTY;
            case 5:
                return REQUEST_MERKLE;
            case 128:
                return REQUEST_BLOCK;
            case 129:
                return BLOCK_PROOF;
            case 255:
                return SUBMIT_TRANSACTION;
            default:
                throw InvalidMessageType();
                return null;
        }
    }
}

enum MessageSendType {
    REQUEST(0),
    SUBMIT(1);
    private byte id;
    MessageSendType(byte id){
        this.id = id;
    }
    public byte serialize(){
        return this.id;
    }
    public static MessageSendType deserialize(byte inputData) {
        switch (inputData) {
            case 0: return REQUEST;
            case 1: return SUBMIT;
            default:
                throw InvalidMessageType();
                return null;
        }
    }
}