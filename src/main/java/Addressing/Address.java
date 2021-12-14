package Addressing;

public class Address {
    byte[] address;

    public Address(PublicKey pubkey){
        this.address = pubkey.encoded();
    }
    
    // @Override
    // public String toString(){
    //     return 
    // }
}
