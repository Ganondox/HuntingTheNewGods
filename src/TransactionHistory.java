import java.security.PublicKey;
import java.util.*;

public class TransactionHistory {

    List<Transaction> history;

    public TransactionHistory() {
        history = new LinkedList<>();
    }

    void add(Transaction t){
        history.add(t);
    }

    public TransactionHistory(List<Transaction> history) {
        this.history = history;
    }

    double getGodCoins(String user){
        double coins = 0;
        for(Transaction transaction: history){
            if(transaction instanceof ClaimBounty){
                ClaimBounty claim = (ClaimBounty)transaction;
                if(claim.claimant.equals(user)){
                    coins += ClaimBounty.BOUNTY;
                }
            }
            if(transaction instanceof MintNFT){
                MintNFT mint = (MintNFT) transaction;
                if(mint.user.equals(user)){
                    coins -= MintNFT.COST;
                }
            }
            if(transaction instanceof Donate){
                Donate donation = (Donate) transaction;
                if(donation.doner.equals(user)){
                    coins -= donation.amount;
                }
                if(donation.donee.equals(user)){
                    coins += donation.amount;
                }
            }
        }
        return coins;
    }

    PublicKey getKey(String user) {
            for (Transaction transaction : history) {
                if (transaction instanceof MakeUser) {
                    MakeUser mu = (MakeUser) transaction;
                    if (mu.username.equals(user)) return mu.key;
                }
            }
        return null;
    }

    int getTransactionCount(String user) {
        int instances = 0;
        for(Transaction transaction: history){
            if(transaction instanceof MakeUser){
                MakeUser registration = (MakeUser)transaction;
                if(registration.username.equals(user)){
                    instances++;
                }
            }
            if(transaction instanceof ClaimBounty){
                ClaimBounty claim = (ClaimBounty)transaction;
                if(claim.claimant.equals(user)){
                    instances++;
                }
            }
            if(transaction instanceof MintNFT){
                MintNFT mint = (MintNFT) transaction;
                if(mint.user.equals(user)){
                    instances++;
                }
            }
            if(transaction instanceof Donate){
                Donate donation = (Donate) transaction;
                if(donation.doner.equals(user)){
                    instances++;
                }
            }
            if(transaction instanceof SellNFT){
                SellNFT sell = (SellNFT) transaction;
                if(sell.user.equals(user)){
                    instances++;
                }
            }
            if(transaction instanceof BuyNFT){
                BuyNFT buy = (BuyNFT) transaction;
                if(buy.user.equals(user)){
                    instances++;
                }
            }
        }
        return instances++;
    }

    String getMinter(String NFT){
        for(Transaction transaction: history) {
            if (transaction instanceof MintNFT) {
                MintNFT mint = (MintNFT) transaction;
                if (mint.NFT.equals(NFT)) {
                    return mint.user;
                }
            }
        }
        return null;
    }

    String getOwer(String NFT){

        String minter = null;
        String owner = null;
        for(Transaction transaction: history) {
            //find minter
            if(minter == null) {
                if (transaction instanceof MintNFT) {
                    MintNFT mint = (MintNFT) transaction;
                    if (mint.NFT.equals(NFT)) {
                       minter = mint.user;
                       owner = mint.user;
                    }
                }
            } else {
                if (transaction instanceof BuyNFT){
                    BuyNFT buy = (BuyNFT) transaction;
                    if(buy.NFT.equals(NFT)){
                        owner = buy.user;
                    }
                }
            }
        }
        return owner;
    }

    boolean forSale(String NFT){
        String minter = null;
        boolean selling = false;
        for(Transaction transaction: history) {
            //find minter
            if(minter == null) {
                if (transaction instanceof MintNFT) {
                    MintNFT mint = (MintNFT) transaction;
                    if (mint.NFT.equals(NFT)) {
                        minter = mint.user;
                    }
                }
            } else {
                if (transaction instanceof SellNFT){
                    SellNFT sell = (SellNFT) transaction;
                    if(sell.NFT.equals(NFT)){
                        selling = true;
                    }
                }
                if (transaction instanceof BuyNFT){
                    BuyNFT buy = (BuyNFT) transaction;
                    if(buy.NFT.equals(NFT)){
                        selling = false;
                    }
                }
            }
        }
        return selling;
    }

    double getPrice(String NFT){
        String minter = null;
        double price = 0;
        for(Transaction transaction: history) {
            //find minter
            if(minter == null) {
                if (transaction instanceof MintNFT) {
                    MintNFT mint = (MintNFT) transaction;
                    if (mint.NFT.equals(NFT)) {
                        minter = mint.user;
                    }
                }
            } else {
                if (transaction instanceof SellNFT){
                    SellNFT sell = (SellNFT) transaction;
                    if(sell.NFT.equals(NFT)){
                        price = sell.price;
                    }
                }
            }
        }
        return price;
    }

    List<String> getNFTs(String User){
        List<String> NFTs = new LinkedList<>();
        for(Transaction transaction: history) {
            if (transaction instanceof MintNFT) {
                MintNFT mint = (MintNFT) transaction;
                if (mint.user.equals(User)) {
                    NFTs.add(mint.NFT);
                }
            }
        }
        return NFTs;
    }

    int getSlays(String user){
        int instances = 0;
        for(Transaction transaction: history) {
            if (transaction instanceof ClaimBounty) {
                ClaimBounty claim = (ClaimBounty) transaction;
                if (claim.claimant.equals(user)) {
                    instances++;
                }
            }
        }
        return instances;
    }

    List<String> getMarket(){
        Set<String> Nfts = new HashSet<>();
        List<String> market = new LinkedList<>();
        for(Transaction transaction: history) {
            if (transaction instanceof MintNFT) {
                MintNFT mint = (MintNFT) transaction;
                if (!Nfts.contains(mint.NFT)) {
                    Nfts.add(mint.NFT);
                    if(forSale(mint.NFT)) market.add(mint.NFT);
                }
            }
        }
        return market;
    }

}
