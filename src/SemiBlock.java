import java.util.List;

public class SemiBlock {

    List<Transaction> transactions;
    int difficulty;
    int seed;


    public SemiBlock(List<Transaction> transactions, int difficulty, int seed) {
        this.transactions = transactions;
        this.difficulty = difficulty;
        this.seed = seed;
    }

    Battle getBattle(){
        return new Battle(seed, difficulty);
    }


    GodBlock seal(List<Action> seal,String user){
       if(new Battle(seed,difficulty).run(seal)){

           return new GodBlock(transactions, seal, user, difficulty);

       } else return null;
    }


}
