package Wallet;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


class Wallet {
    
    PublicKey public_address;
    PrivateKey privateKey;

    private Wallet(PublicKey ad, PrivateKey privateKey){
        this.public_address = ad;
        this.privateKey = privateKey;
    }

    public static Wallet createNewWallet(){
        KeyPair kp = Wallet.GenerateKeys();
        PublicKey pubkey = kp.getPublic();
        PrivateKey prikey = kp.getPrivate();
        return new Wallet(pubkey, prikey);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        Wallet w = Wallet.createNewWallet();
        w.saveWallet("nice.wallet");
        System.out.println(toHashedHexString(w.privateKey.getEncoded()));
        System.out.println(toHashedHexString(Wallet.loadWallet("nice.wallet").privateKey.getEncoded()));
    }

    public static String toHashedHexString(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("Sha3-256");
        return Base64.getEncoder().encodeToString(md.digest(data));
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "public_address=" + public_address +
                ", privateKey=" + privateKey +
                '}';
    }


    // TODO: Consider SSL?
    // TODO: Try this? SHA3-512withECDSA
    public static Wallet loadWallet(String path){
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path))) {
            int pubKeyLength = ByteUtils.convertByteArrayToInt(bis.readNBytes(4));
            int priKeyLength = ByteUtils.convertByteArrayToInt(bis.readNBytes(4));
            byte[] pubKeyBytes = bis.readNBytes(pubKeyLength);
            byte[] priKeyBytes = bis.readNBytes(priKeyLength);
            PublicKey pubKey = KeyFactory.getInstance("ECDSA", "BC").generatePublic(new X509EncodedKeySpec(pubKeyBytes));
            PrivateKey priKey = KeyFactory.getInstance("ECDSA", "BC").generatePrivate(new PKCS8EncodedKeySpec(priKeyBytes));
            return new Wallet(pubKey, priKey);
        } catch (Exception e) {
            //TODO: handle exception

            return null;
        }
    }

    public void saveWallet(String path){
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path))) {
            byte[] pubKey = this.public_address.getEncoded();
            byte[] priKey = this.privateKey.getEncoded();
            byte[] pubKeyLength = ByteUtils.intToBytes( pubKey.length );
            byte[] priKeyLength = ByteUtils.intToBytes(priKey.length);
            bos.write(pubKeyLength);
            bos.write(priKeyLength);
            bos.write(pubKey);
            bos.write(priKey);
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static KeyPair GenerateKeys() {
        try {
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("B-571");
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", "BC");
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
     * @param prikey  The EC private key to use in signing
     * @return A byte array representing the signed message
     */
    private static byte[] sign(byte[] message, PrivateKey prikey) {
        try {
            java.security.Signature ecdsaSign = java.security.Signature.getInstance("SHA256withECDSA", "BC");
            ecdsaSign.initSign(prikey);
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
    private static boolean verifySignature(byte[] signature, byte[] dataToVerify, PublicKey pubkey) {
        try {
            java.security.Signature ecdsaSign = java.security.Signature.getInstance("SHA512withECDSA", "BC");
            ecdsaSign.initVerify(pubkey);
            ecdsaSign.update(dataToVerify);
            return ecdsaSign.verify(signature);
        } catch (NoSuchAlgorithmException | SignatureException | NoSuchProviderException | InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }
    }

}