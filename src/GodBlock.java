import java.util.LinkedList;
import java.util.List;

public class GodBlock {


    List<Transaction> transactions;
    List<Action> seal;
    String sealer;
    int difficulty;

    public GodBlock(List<Transaction> transactions, List<Action> seal, String sealer, int difficulty) {
        this.transactions = transactions;
        this.seal = seal;
        this.sealer = sealer;
        this.difficulty = difficulty;
    }

    boolean verify(int seed, TransactionHistory history){

        //copy history since it's gonna change
        TransactionHistory newHistory = new TransactionHistory();
        for(Transaction t: history.history){
            newHistory.add(t);
        }

        //verify each transaction
        for(Transaction t : transactions){
            if(t.verify(newHistory)){
                newHistory.add(t);
                seed += t.hashCode();
            } else return false;
        }
        //verify seal
        return new Battle(seed, difficulty).run(seal);



    }

}
