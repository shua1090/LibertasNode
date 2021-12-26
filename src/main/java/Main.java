import Wallet.*;
import com.debuggor.schnorrkel.sign.*;
import com.debuggor.schnorrkel.utils.HexUtils;

import java.nio.charset.StandardCharsets;


class Main {
    public static void main(String[] args) throws Exception {
        Signer s = new Signer();
        byte[] dat = "nice".getBytes(StandardCharsets.UTF_8);
        byte[] signedMessage = s.sign(dat);

        Signer s2 = new Signer();
        byte[] signedMessage2 = s2.sign(signedMessage);

        System.out.println("nice:");
        System.out.println(Signer.verifySignature(signedMessage, signedMessage2, KeyPair.fromPublicKey(s2.keypair.getPublicKey().toPublicKey())));

//
//        KeyPair keyPair = KeyPair.fromSecretSeed(HexUtils.hexToBytes("579d7aa286b37b800b95fe41adabbf0c2a577caf2854baeca98f8fb242ff43ae"), ExpansionMode.Ed25519);
//
//        byte[] message = "test message".getBytes();
//        SigningContext ctx = SigningContext.createSigningContext("good".getBytes());
//        SigningTranscript t = ctx.bytes(message);
//        Signature signature = keyPair.sign(t);
//        byte[] sign = signature.to_bytes();
//        System.out.println(HexUtils.bytesToHex(sign));
//
//
//        SigningContext ctx2 = SigningContext.createSigningContext("good".getBytes());
//        SigningTranscript t2 = ctx2.bytes(message);
//        KeyPair fromPublicKey = KeyPair.fromPublicKey(keyPair.getPublicKey().toPublicKey());
//        boolean verify = fromPublicKey.verify(t2, sign);
//        System.out.println(verify);
    }
}

class Signer{
    KeyPair keypair;
//    public PublicKey pubKey;
//    public PrivateKey priKey;

    public Signer(){
        this.keypair = KeyPair.generateKeyPair();
    }

    public byte[] sign(byte[] input){
        try {
            SigningContext ctx = SigningContext.createSigningContext("good".getBytes(StandardCharsets.UTF_8));
            SigningTranscript t = ctx.bytes(input);
            Signature signature = keypair.sign(t);
            return signature.to_bytes();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }

    public static boolean verifySignature(byte[] origMessage, byte[] signedmessage, KeyPair kp){
        try {
            return kp.verify(SigningContext.createSigningContext("good".getBytes(StandardCharsets.UTF_8)).bytes(origMessage), signedmessage);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
            return false;
        }
    }
}
