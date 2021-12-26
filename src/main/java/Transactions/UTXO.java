package Transactions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import Addressing.*;
import Utils.Hash;

public class UTXO {
    long enum_num;
    byte block_num;
    Address recipient;
    BigDecimal amount;
    
    public byte[] serializedUTXO(){
        byte[] enum_num_serialized = Utils.ByteUtils.longToBytes(enum_num);
        byte[] block_num_serialized = new byte[]{block_num};
        byte[] address_serialized = recipient.serializeAddress();
//       TODO: byte[] amount_serialized = amount.;
        try (
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            baos.write(enum_num_serialized);
            baos.write(block_num_serialized);
            baos.write(address_serialized);
//            baos.write(amount_serialized);
            return baos.toByteArray();
        } catch (IOException ioe){
            ioe.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

//    public UTXO deserializedUTXO(byte[] inputData){
//
//        long enum_num
//    }

//    public byte[] getHash(){
//        return Hash.hash(this.serializedUTXO());
//    }

}
