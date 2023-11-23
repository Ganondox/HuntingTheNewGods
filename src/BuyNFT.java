public class BuyNFT extends Transaction{


    String user;
    String NFT;
    byte[] signature;

    public BuyNFT(String user, String NFT, byte[] signature) {
        this.user = user;
        this.NFT = NFT;
        this.signature = signature;
    }

    @Override
    boolean verify(TransactionHistory history) {
        //confirm the user is who they say they are
        try {
            if(!new PublicUser(history.getKey(user)).verify(NFT, signature, history.getTransactionCount(user))){
                System.out.println("Message Mismatch");
                System.out.println(history.getTransactionCount(user));
                return false;
            }
        } catch (Exception e) {
            System.out.println("Verification failed.");
            return false;
        }

        //confirm the NFT is for sale
        if(!history.forSale(NFT)) {
            System.out.println("Not for sale. ");
            return false;
        }

        //confirm the user has enough god coins
        if(history.getGodCoins(user) < history.getPrice(NFT)) {
            System.out.println("Insufficient coins.");
            return false;
        }



        return true;
    }
}
