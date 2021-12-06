public enum {

    // Blocks
    REQUEST_BLOCK, // Do you have this block? 
    //Add delay so that a negligent node can't request someone's block for "HAS_BLOCK"
    
    REQUEST_BLOCKS, // Request multiple blocks
    RELAY_BLOCK, // The data after this contains the block
    HAS_BLOCK, // Prove to me that you have this block
    BLOCK_PROOF, // Submit Hash as proof

    // Transactions
    SUBMIT_TRANSACTION,
    
    // Search
    PING, PONG,
    FIND_NODE;

    // Metadata
    REQUEST_EPOCH_NUM,
    REQUEST_BLOCK_NUM,
    REQUEST_DIFFICULTY,

    REQUEST_MERKLE,

    METADATA,

}

enum MessageTypes {
    REQUEST,
    SUBMIT;
}