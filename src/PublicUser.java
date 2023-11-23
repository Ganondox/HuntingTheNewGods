import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

public class PublicUser {

    PublicKey key;

    public PublicUser(PublicKey key) {
        this.key = key;
    }

    boolean verify(String message, byte[] signature, int transactions) throws Exception{
        message += transactions; //stop people from duplicating transactions
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedMessageBytes = decryptCipher.doFinal(signature);
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        return decryptedMessage.equals(message);
    }

}
