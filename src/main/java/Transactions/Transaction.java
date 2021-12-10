
interface Transaction {
    byte[] serializeTransaction();
    static Transaction deserializeTransaction(byte[] data);
}

enum TransactionFailureTypes{
    OUPUT_GREATER_THAN_INPUT,
    INVALID_SIGNATURE,
    UTXO_FROM_DIFFERENT_ADDRESSES,
    VALID_TRANSACTION;
}

public class Transaction implements Transaction {
    UTXO[] inputs;
    UTXO[] outputs;
    byte[] inputSignatures;
    byte[] outputSignatures;
    long current_time;
    
    byte[] serializeTransaction(){
        return null;
    }

    static Transaction deserializeTransaction(byte[] data){
        return null;
    };

    void signInput(int index, byte[] signature){
        inputSignatures[index] = signature;
    }

    byte[] signOutput(int index, byte[] signature){
        outputSignatures[index] = signature;
    }

    byte[] getInputPlusOutputHash(int outputIndex){
        // Concatenate Inputs and Input Signatures with one output, and return the hash
    }

    // Hash of the transaction, including signatures 
    byte[] getHash(){
        return null;
    }

}
