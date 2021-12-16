package Addressing;

import java.security.PublicKey;

public class Address {
    byte[] address;

    public Address(PublicKey pubkey){
        this.address = pubkey.getEncoded();
    }
    
    // @Override
    // public String toString(){
    //     return 
    // }
}
