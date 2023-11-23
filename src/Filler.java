import java.util.List;

public class Filler extends Transaction{


    @Override
    boolean verify(TransactionHistory history) {
        return true;
    }
}
