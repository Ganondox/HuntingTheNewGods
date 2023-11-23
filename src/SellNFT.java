public class SellNFT extends Transaction {

    String user;
    String NFT;
    double price;
    byte[] signature;

    public SellNFT(String user, String NFT, double price, byte[] signature) {
        this.user = user;
        this.NFT = NFT;
        this.price = price;
        this.signature = signature;
    }

    @Override
    boolean verify(TransactionHistory history) {
        //confirm the user is who they say they are
        try {
            if(!new PublicUser(history.getKey(user)).verify(NFT+price, signature, history.getTransactionCount(user))){
                System.out.println("Message Mismatch");
                System.out.println(history.getTransactionCount(user));
                return false;
            }
        } catch (Exception e) {
            System.out.println("Verification failed.");
            return false;
        }

        //confirm the NFT is owned by the seller
        if(history.getMinter(NFT) == null || !history.getOwer(NFT).equals(user)) {
            System.out.println("Not yours to sell. ");
            return false;
        }

        return true;

    }
}
