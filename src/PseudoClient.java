import java.util.List;

public class PseudoClient {

    public PseudoClient(String user, PseudoServer server, PrivateUser pu) {
        this.user = user;
        this.server = server;
        privateKey = pu;
    }

    String user;
    PseudoServer server;
    PrivateUser privateKey;

    boolean submitTransaction(Transaction transaction){
        if(server.submitTransaction(transaction)){
            privateKey.transactions++;
            return true;
        }
        return false;
    }

    boolean completeHunt(List<Action> seal){
        if(server.completeHunt(seal, user)){
            privateKey.transactions++;
            return true;
        }
        return false;
    }

}
