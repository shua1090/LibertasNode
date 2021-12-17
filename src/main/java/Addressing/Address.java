package Addressing;

import java.security.PublicKey;
import org.bouncycastle.util.encoders.Hex;

public class Address {
    byte[] address;

    public Address(PublicKey pubkey){
        this.address = pubkey.getEncoded();
    }

    public byte[] serializeAddress(){
        return this.address;
    }

    public PublicKey deserializeAddress(){
        return KeyFactory.getInstance("ECDSA", "BC").generatePublic(new X509EncodedKeySpec(this.address));
    }
    
    @Override
    public String toString(){
        return Hex.toString(this.address);
    }
}
