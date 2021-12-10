public class UTXO {
    long enum_num;
    byte block_num;
    Address recipient;
    BigDecimal amount;
    
    // return hash of the transaction
    byte[] getHash(){
        return null;
    }

    void addSignature(byte[] signature){
        this.signature = signature;
    }
}
