package Transactions;

import java.math.BigDecimal;
import Addressing.*;

public class UTXO {
    long enum_num;
    byte block_num;
    Address recipient;
    BigDecimal amount;
    
    // return hash of the transaction
    byte[] getHash(){
        return null;
    }
}
