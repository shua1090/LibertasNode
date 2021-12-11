package Transactions;

import java.util.ArrayList;

interface TransactionInterface{
    byte[] serializeTransaction();
    byte[] getHash();
}

//enum TransactionFailureTypes{
//    OUPUT_GREATER_THAN_INPUT,
//    INVALID_SIGNATURE,
//    UTXO_FROM_DIFFERENT_ADDRESSES,
//    VALID_TRANSACTION;
//}

public class Transaction implements TransactionInterface{
    ArrayList<UTXO> inputs = new ArrayList<>();
    ArrayList<UTXO> outputs = new ArrayList<>();
    ArrayList<byte[]> signatures;
    long current_time;

    // Inputs
    void addInput(UTXO additionalInput){
        this.inputs.add(additionalInput);
    }
    void removeInput(int index){
        this.inputs.remove(index);
    }
    UTXO[] getInputs(){return this.inputs.toArray(new UTXO[0]);}

    // Outputs
    void addOutput(UTXO additionalOutput){
        this.outputs.add(additionalOutput);
    }
    void removeOutput(int index){
        this.outputs.remove(index);
    }
    UTXO[] getOutputs(){return this.outputs.toArray(new UTXO[0]);}

    @Override
    public byte[] serializeTransaction() {
        return new byte[0];
    }

    public static Transaction deserializeTransaction(byte[] data){
        return null;
    }

    // Hash of the transaction, not including signatures
    @Override
    public byte[] getHash(){
        return null;
    }
}
