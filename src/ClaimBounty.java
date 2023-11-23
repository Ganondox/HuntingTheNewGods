import java.util.List;

public class ClaimBounty extends Transaction {

    String claimant;
    final static int BOUNTY = 10;


    public ClaimBounty(String claimant) {
        this.claimant = claimant;
    }

    @Override
    boolean verify(TransactionHistory history) {
        return false; //cannot be added to a block through ordinary means
    }
}
