import java.util.LinkedList;
import java.util.List;

public class PseudoServer {


    final int BLOCKSIZE = 10;
    final int BUFFERSIZE = 10;



    TransactionHistory history; //all submittted
    GodChain chain; //signed and blocked
    SemiBlock next; //blocked but not signed
    List<Transaction> pending; //not blocked



   PseudoServer(){
       history = new TransactionHistory();
       chain = new GodChain();
       next = null;
       pending = new LinkedList<>();
   }




    boolean submitTransaction(Transaction t){
        if(pending.size() < BUFFERSIZE) {

            if (t.verify(history)) {
                history.add(t);
                pending.add(t);
                return true;
            }

        }
        return false;
    }

    Battle joinHunt(){
        if(next == null){
            //complete semiblock
            while(pending.size() < BLOCKSIZE){
                pending.add(new Filler());
                history.add(new Filler());
            }
            //calculate seed
            int seed = chain.quick_hash;
            for(Transaction t: pending){
                seed += t.hashCode();
            }
            next = new SemiBlock(pending,chain.quick_difficulty, seed);
            pending = new LinkedList<>();
        }
        return next.getBattle();
    }

    boolean completeHunt(List<Action> seal, String user){
        if(next== null) return false;
        GodBlock block = next.seal(seal, user);
        if(block == null) return false;
        if(chain.addBlock(block)){
            next = null;
            history.add(new ClaimBounty(user));
            return true;
        }
        return false;

    }

    double getGodCoins(String player){

       return history.getGodCoins(player);

    }

}
