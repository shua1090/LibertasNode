import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;

class Wallet{
    PublicKey public_address;
    PrivateKey privateKey;


    private Wallet(PublicKey ad, PriKey privateKey){
        this.public_address = ad;
        this.privateKey = privateKey;
    }

    public static Wallet createNewWallet(){
        KeyPair kp = Wallet.GenerateKeys();
        PublicKey pubkey = kp.getPublic();
        PrivateKey prikey = kp.getPrivate();
        Wallet w = new Wallet(pubkey, prikey);
    }

    public static Wallet loadWallet(String path){
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(path)))) {
            
            return null;
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void saveWallet(String path){
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(path)))) {
            byte[] pubKey = this.public_address.getEncoded();
            byte[] priKey = this.privateKey.getEncoded();
            byte[] pubKeyLength = ByteUtils.intToBytes( pubKey.length );
            byte[] priKeyLength = ByteUtils.intToBytes(priKey.length);
            bos.write(pubKeyLength);
            bos.write(priKeyLength);
            bos.write(pubKey);
            bos.write(priKey);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ByteUtils{
        public static byte[] intToBytes( final int i ) {
            ByteBuffer bb = ByteBuffer.allocate(4); 
            bb.putInt(i); 
            return bb.array();
        }
        public static int convertByteArrayToInt(byte[] intBytes){
            ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
            return byteBuffer.getInt();
        }
    }

    private static KeyPair GenerateKeys() {
        try {
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("B-571");
            KeyPairGenerator g = KeyPairGenerator.getInstance("SHA512withECDSA", "BC");
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
            java.security.Signature ecdsaSign = java.security.Signature.getInstance("SHA512withECDSA", "BC");
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