package Blockchain;

enum BlockType{
    ORTHODOX_BLOCK,
    TREE_BLOCK_PARENT,
    TREE_BLOCK_CHILD;
}

interface Block {
    abstract byte[] serializeBlock();
    abstract BlockType getBlockType();

}

//// Finish
//class OBlock extends Block {
//
//}