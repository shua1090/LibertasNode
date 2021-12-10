enum BlockType{
    ORTHODOX_BLOCK,
    TREE_BLOCK_PARENT,
    TREE_BLOCK_CHILD;
}

interface Block {
    byte[] serializeBlock();
    static Block deserializeBlock(byte[] serializedBlock);
    getBlockType();
}

class OBlock implements Block {
    

    public byte[] serializeBlock(){

    }

    public OBlock deserializeBlock(byte[] serializedBlock){

    }
}