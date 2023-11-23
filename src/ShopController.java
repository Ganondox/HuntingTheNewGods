import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

public class ShopController {

    enum Mode{main, spend, mint, buy, sell, exit}

    void enter(PseudoClient game){

        Mode cur = Mode.main;
        boolean quit = false;

        Scanner scanner = new Scanner(System.in);
        String in;

        String NFT;

        while (!quit){
            double coins = game.server.getGodCoins(game.user);
            switch (cur){
                case main:
                    System.out.println("Buying or selling?");
                    System.out.println("B - Buying");
                    System.out.println("S - Selling");
                    System.out.println("L - Leaving");
                    in = scanner.nextLine();
                    if(in.equals("B")){
                        cur = Mode.spend;
                    }
                    if(in.equals("S")){
                        cur = Mode.sell;
                    }
                    if(in.equals("L")){
                        cur = Mode.exit;
                    }
                    break;
                case spend:
                    coins = game.server.getGodCoins(game.user);
                    System.out.println("You have " + coins + " God coins." );
                    System.out.println("Do you wish to mint or buy?");
                    System.out.println("M - Mint");
                    System.out.println("B - Buy");
                    System.out.println("N - Neither");
                    in = scanner.nextLine();
                    if(in.equals("M")){
                        cur = Mode.mint;
                    }
                    if(in.equals("B")){
                        cur = Mode.buy;
                    }
                    if(in.equals("N")){
                        cur = Mode.main;
                    }

                    break;


                case mint:
                    coins = game.server.getGodCoins(game.user);
                    if(coins >= MintNFT.COST) {
                        System.out.println("Do you wish to brand an art? This costs " + MintNFT.COST + " God coins. ");
                        System.out.println("Y - Yes");
                        System.out.println("N - No");
                        in = scanner.nextLine();
                        if (in.equals("Y")) {
                            //mint NFT
                            System.out.println("What art would you like to brand?");
                            in = scanner.nextLine();
                            try {
                                if (game.submitTransaction(new MintNFT(in, game.user, game.privateKey.sign(in)))) {
                                    System.out.println(in + " has been successfully branded!");
                                } else {
                                    System.out.println(in + " is unavailable for branding. ");
                                }
                            } catch (NoSuchAlgorithmException e) {
                                System.out.println("1");
                            } catch (NoSuchPaddingException e) {
                                System.out.println("2");
                            } catch (IllegalBlockSizeException e) {
                                System.out.println("3");
                            } catch (BadPaddingException e) {
                                System.out.println("4");
                            } catch (InvalidKeyException e){
                                System.out.println("5");
                            }
                        }

                    } else {
                        System.out.println("You are too poor to purchase anything here.");
                    }
                    cur = Mode.spend;
                    break;

                case buy:
                   List<String> market = game.server.history.getMarket();
                   for(String nft: market){
                       System.out.println(game.server.history.getOwer(nft) + "'s " + nft + " by " + game.server.history.getMinter(nft) + " - " + game.server.history.getPrice(nft));
                   }
                   System.out.println("Which NFT would you like to buy?");
                    in = scanner.nextLine();
                     NFT = in;
                    try {
                        if(game.submitTransaction(new BuyNFT(game.user, NFT, game.privateKey.sign(NFT)))){
                            System.out.println("Buying " + NFT + " for " + game.server.history.getPrice(NFT) + " god coins!");
                        } else {
                            System.out.println("Can't buy that!");
                        }
                    } catch (Exception e){

                    }
                    cur = Mode.spend;
                    break;
                case sell:
                    System.out.println("What NFT do you wish to sell?");
                    in = scanner.nextLine();
                     NFT = in;
                    System.out.println("How much do you want to sell it for?");
                    in = scanner.nextLine();
                    double amount = 0;
                    try {
                        amount = Double.parseDouble(in);
                    } catch (Exception e){

                    }
                    try {
                        if(game.submitTransaction(new SellNFT(game.user, NFT, amount, game.privateKey.sign(NFT+amount)))){
                            System.out.println("Selling " + NFT + " for " + amount + " god coins!");
                        } else {
                            System.out.println("Can't sell that!");
                        }
                    } catch (Exception e){

                    }

                    cur = Mode.main;
                    break;

                case exit:
                    quit = true;
                    break;

            }
        }


    }

}
