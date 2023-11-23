import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class PrivateUser {

    public PrivateUser(PrivateKey key) {
        this.key = key;
        transactions = 1; //for registration

    }

    PrivateKey key;
    int transactions;

    byte[] sign(String message) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        message += transactions;
        Cipher signCipher = Cipher.getInstance("RSA");
        signCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        return signCipher.doFinal(messageBytes);
    }
}
