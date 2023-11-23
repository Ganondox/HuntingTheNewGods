public class Donate extends Transaction {

    String doner;
    String donee;
    double amount;
    String memo;
    byte[] signature;

    public Donate(String doner, String donee, double amount, String memo, byte[] signature) {
        this.doner = doner;
        this.donee = donee;
        this.amount = amount;
        this.memo = memo;
        this.signature = signature;
    }

    @Override
    boolean verify(TransactionHistory history) {
        //confirm the user is who they say they are
        try {
            if(!new PublicUser(history.getKey(doner)).verify(amount + memo +  donee, signature, history.getTransactionCount(doner))){
                System.out.println("Message Mismatch");
                System.out.println(history.getTransactionCount(doner));
                return false;
            }
        } catch (Exception e) {
            System.out.println("Verification failed.");
            return false;
        }
        //confirm the user has enough god coins
        if(history.getGodCoins(doner) < amount) {
            System.out.println("Insufficient coins.");
            return false;
        }
        return true;
    }
}
