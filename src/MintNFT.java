public class MintNFT extends Transaction{
    final static int COST = 7;
    String NFT;
    String user;
    byte[] signature;

    public MintNFT(String NFT, String user, byte[] signature) {
        this.NFT = NFT;
        this.user = user;
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

        //confirm the user has enough god coins
        if(history.getGodCoins(user) < COST) {
            System.out.println("Insufficient coins.");
            return false;
        }

        //confirm the NFT has not already been minted
        if(history.getMinter(NFT) != null) {
            System.out.println("Already branded. ");
            return false;
        }







        return true;
    }
}
