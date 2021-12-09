package networking;

class InvalidMessageType extends Exception{}

enum MessageDataType {

    // Blocks
    REQUEST_BLOCK(64), // Do you have this block?
    //Add delay so that a negligent node can't request someone's block for "HAS_BLOCK"
     // The data after this contains the block
    BLOCK_PROOF(65), // Prove to me that you have this block
     // Submit Hash as proof

    // Transactions
    SUBMIT_TRANSACTION(66),
    
    // Search
    PING_PONG(0),
    FIND_NODE(1),

    // Metadata
    EPOCH_NUM(2),
    BLOCK_NUM(3),
    REQUEST_DIFFICULTY(4),
    REQUEST_MERKLE(5);

    private final byte id;
    MessageDataType(int id){
        this.id = (byte) id;
    }
    public byte serialize(){
        return this.id;
    }
    public static MessageDataType deserialize(byte inputData) throws InvalidMessageType {
        return switch (inputData) {
            case 0 -> PING_PONG;
            case 1 -> FIND_NODE;
            case 2 -> EPOCH_NUM;
            case 3 -> BLOCK_NUM;
            case 4 -> REQUEST_DIFFICULTY;
            case 5 -> REQUEST_MERKLE;
            case 64 -> REQUEST_BLOCK;
            case 65 -> BLOCK_PROOF;
            case 66 -> SUBMIT_TRANSACTION;
            default -> throw new InvalidMessageType();
        };
    }
}

enum MessageSendType {
    REQUEST(0),
    SUBMIT(1);
    private final byte id;
    MessageSendType(int id){
        this.id = (byte) id;
    }
    public byte serialize(){
        return this.id;
    }
    public static MessageSendType deserialize(byte inputData) throws InvalidMessageType {
        return switch (inputData) {
            case 0 -> REQUEST;
            case 1 -> SUBMIT;
            default -> throw new InvalidMessageType();
        };
    }
}