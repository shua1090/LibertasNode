package Addressing;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.encoders.Hex;

public class Address {
    byte[] address;

    public Address(PublicKey pubkey){
        this.address = pubkey.getEncoded();
    }


    /**
     * Returns a byte array that represents this Address. There is no 'serialization' occurring technically,
     * as the Address implementation uses a byte array internally anyways.
     * @return A byte array that corresponds to the PublicKey associated with this address
     */
    public byte[] serializeAddress(){
        return this.address;
    }

    /**
     * A method that converts an Address to a PublicKey and returns it
     * @return The Public Key for this address, best used in signature verification
     * @throws NoSuchAlgorithmException An Exception that should not occur due to proper specification within the method
     * @throws NoSuchProviderException An Exception that may occur if BouncyCastle is not added as another JCE provider
     * @throws InvalidKeySpecException An Exception that may occur if the KeyEncoding is invalid (unlikely if properly implemented)
     */
    public PublicKey deserializeAddress() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        return KeyFactory.getInstance("ECDSA", "BC").generatePublic(new X509EncodedKeySpec(this.address));
    }
    
    @Override
    public String toString(){
        return Hex.toHexString(this.address);
    }
}
