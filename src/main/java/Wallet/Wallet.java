package Wallet;

import Utils.EncoderUtils;
import Utils.Hash;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.bouncycastle.util.encoders.Hex;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Wallet {
    
    PublicKey public_address;
    PrivateKey privateKey;

    /**
     * A Private constructor
     * @param ad The PublicKey address of this Wallet
     * @param privateKey The PrivateKey of this Wallet
     */
    private Wallet(PublicKey ad, PrivateKey privateKey){
        this.public_address = ad;
        this.privateKey = privateKey;
    }

    /**
     * A factory that creates a new wallet with a valid, random ECDSA public and private Key
     * @return A new Wallet with Keys chosen from Elliptic Curves
     */
    public static Wallet createNewWallet(){
        KeyPair kp = Wallet.GenerateKeys();
        PublicKey pubkey = kp.getPublic();
        PrivateKey prikey = kp.getPrivate();
        return new Wallet(pubkey, prikey);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        Wallet w1 = Wallet.createNewWallet();
        Wallet w2 = Wallet.createNewWallet();
        byte[] plaintextMessage = "test".getBytes(StandardCharsets.UTF_8);
        byte[] signedMessage = w1.sign(plaintextMessage);
        byte[] doublySignedMessage = w2.sign(signedMessage);
        System.out.println(signedMessage.length);
        System.out.println(Wallet.verifySignature(doublySignedMessage, signedMessage, w2.public_address));
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "public_address=" + public_address +
                ", privateKey=" + privateKey +
                '}';
    }


    // TODO: Consider SSL?

    /**
     * A static method that simply loads the keys in a file into a Wallet and returns it
     * @param path The path of the Wallet file that is to be loaded
     * @return A Wallet loaded from the given path
     */
    public static Wallet loadWallet(String path){
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path))) {
            int pubKeyLength = Utils.ByteUtils.convertByteArrayToInt(bis.readNBytes(4));
            int priKeyLength = Utils.ByteUtils.convertByteArrayToInt(bis.readNBytes(4));
            byte[] pubKeyBytes = bis.readNBytes(pubKeyLength);
            byte[] priKeyBytes = bis.readNBytes(priKeyLength);
            PublicKey pubKey = KeyFactory.getInstance("SHA3-512withECDSA", "BC").generatePublic(new X509EncodedKeySpec(pubKeyBytes));
            PrivateKey priKey = KeyFactory.getInstance("SHA3-512withECDSA", "BC").generatePrivate(new PKCS8EncodedKeySpec(priKeyBytes));
            return new Wallet(pubKey, priKey);
        } catch (Exception e) {
            //TODO: handle exception

            return null;
        }
    }

    /**
     * A method that saves the Wallet to a file with the PublicKey and PrivateKey written as raw bytes.
     * This is insecure, but there are no plans to add encryption yet: that is the user's job.
     * @param path The path to save the Wallet to
     */
    public void saveWallet(String path){
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path))) {
            byte[] pubKey = this.public_address.getEncoded();
            byte[] priKey = this.privateKey.getEncoded();
            byte[] pubKeyLength = Utils.ByteUtils.intToBytes( pubKey.length );
            byte[] priKeyLength = Utils.ByteUtils.intToBytes(priKey.length);
            bos.write(pubKeyLength);
            bos.write(priKeyLength);
            bos.write(pubKey);
            bos.write(priKey);
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A method that simply generates a private and its corresponding public ECDSA keys
     * @return The Generated KeyPair
     */
    private static KeyPair GenerateKeys() {
        try {
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("B-571");
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", "BC");
            System.out.println(g.getAlgorithm());
            g.initialize(ecSpec, new SecureRandom());
            return g.generateKeyPair();
        } catch (GeneralSecurityException gse) {
            gse.printStackTrace();
            throw new RuntimeException("Error generating Key, see above^");
        }
    }
    
    /**
     * Signs the message
     *
     * @param message The message to sign
     * @return A byte array representing the signed message
     */
    public byte[] sign(byte[] message) {
        try {
            java.security.Signature ecdsaSign = java.security.Signature.getInstance("SHA3-512withECDSA", "BC");
            ecdsaSign.initSign(this.privateKey);
            ecdsaSign.update(message);
            return ecdsaSign.sign();
        } catch (NoSuchAlgorithmException | SignatureException | NoSuchProviderException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("Error signing the message, see above ^");
        }
    }

    /**
     * @param signature    The signature to be verified
     * @param dataToVerify The data that the signature should be equal to
     * @param pubkey       The Public Key to use to verify
     * @return a boolean that represents if the signature and the data are the same (The signature is valid)
     */
    public static boolean verifySignature(byte[] signature, byte[] dataToVerify, PublicKey pubkey) {
        try {
            java.security.Signature ecdsaSign = java.security.Signature.getInstance("SHA3-512withECDSA", "BC");
            ecdsaSign.initVerify(pubkey);
            ecdsaSign.update(dataToVerify);
            return ecdsaSign.verify(signature);
        } catch (NoSuchAlgorithmException | SignatureException | NoSuchProviderException | InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }
    }

}