import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.List;

public class MakeUser extends Transaction {

    String username;
    PublicKey key;
    byte[] signature;


    MakeUser(String u, PublicKey k, byte[] s){
        username = u;
        key = k;
        signature = s;
    }


    @Override
    boolean verify(TransactionHistory history) {

        //check the name hasn't already been claimed
        for(Transaction t: history.history){
            if(t instanceof MakeUser){
                MakeUser mu = (MakeUser)t;
                if(mu.username.endsWith(username)) return false;
            }
        }

        //check that the signature is valid
        try {
            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedMessageBytes = decryptCipher.doFinal(signature);
            String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
            return decryptedMessage.equals(username);
        } catch (Exception e){
            return false;
        }

    }

    static PrivateKey make(PseudoServer chain, String username){

        PublicKey publicKey = null;
        PrivateKey privateKey = null;
        byte[] sig = null;


        try {
            //generate pair
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            publicKey = pair.getPublic();
            privateKey = pair.getPrivate();
            sig = null;
            //sign username
            Cipher signCipher = Cipher.getInstance("RSA");
            signCipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] usernameBytes = username.getBytes(StandardCharsets.UTF_8);
            sig = signCipher.doFinal(usernameBytes);

        } catch (Exception e){

        }
        //submit transaction
        Transaction t = new MakeUser(username, publicKey, sig);
        if(chain.submitTransaction(t)){
            return privateKey;
        } return null;

    }
}
