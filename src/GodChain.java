import java.security.PublicKey;
import java.util.LinkedList;
import java.util.List;

public class GodChain {



    List<GodBlock> chain = new LinkedList<>();


    TransactionHistory quick_history = new TransactionHistory(new LinkedList<>());
    int quick_hash = 0;
    int quick_difficulty = 0;


    boolean verify(){

        int hash = 0;
        int difficulty = 0;
        TransactionHistory history = new TransactionHistory();
        for(GodBlock block: chain){
            if(block.verify(hash, history) && block.difficulty == difficulty){
                hash += block.hashCode();
                for(Transaction t: block.transactions){
                    history.add(t);
                }
                history.add(new ClaimBounty(block.sealer));
                difficulty++;
            } else return false;
        }

        return true;

    }

    //returns true if it is the dominant chain, meaning it is valid and longer than the other chain
    boolean compare(GodChain other){
        if(chain.size() > other.chain.size()){
            return verify();
        } return false;
    }

    boolean addBlock(GodBlock block){
        if(block.verify(quick_hash, quick_history)){
            chain.add(block);
            quick_hash += block.hashCode();
            quick_difficulty++;
            for(Transaction transaction: block.transactions){
                quick_history.history.add(transaction);
            }
            quick_history.add(new ClaimBounty(block.sealer));
            return  true;
        } else return false;



    }

    double getGodCoins(String player){
       return quick_history.getGodCoins(player);
    }

    PublicKey getKey(String user){
      return  quick_history.getKey(user);
    }

}
