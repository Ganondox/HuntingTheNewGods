import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class ModeController {

    enum Mode{Hunt, Shop, Menu, Museum, Talk, Quit, Donate}


    Mode cur = Mode.Menu;
    PseudoClient game;

    int storyProgress = 0;

    public ModeController(PseudoClient game) {
        this.game = game;
    }

    boolean play(){

        Scanner scanner = new Scanner(System.in);
        String in;

        boolean quit = false;
        while(!quit){
            switch (cur){
                case Menu:
                    System.out.println("Choose Action");
                    System.out.println("H - Hunt");
                    System.out.println("S - Shop");
                    System.out.println("M - Museum");
                    System.out.println("T - Talk");
                    System.out.println("D - Donate");
                    System.out.println("Q - Quit");
                    in = scanner.nextLine();
                    if(in.equals("H")) {
                        cur = Mode.Hunt;
                    }
                    if(in.equals("S")) {
                        cur = Mode.Shop;
                    }
                    if(in.equals("M")) {
                        cur = Mode.Museum;
                    }
                    if(in.equals("T")) {
                        cur = Mode.Talk;
                    }
                    if(in.equals("D")) {
                        cur = Mode.Donate;
                    }
                    if(in.equals("Q")) {
                       cur = Mode.Quit;
                    }
                    break;
                case Hunt:
                    Battle battle = game.server.joinHunt();
                    if(battle != null){
                        BattleController bc = new BattleController(battle);
                        if(bc.attemptHunt()){
                            System.out.println("New God slain.");
                            if(game.completeHunt(bc.history)){
                                System.out.println("Congratulations, you are lord of the hunt!");
                            } else {
                                System.out.println("The hunt had moved one, better luck next time.");
                            }
                        } else {
                            System.out.println("The New God is victorious, try again.");
                        }

                    } else {
                        System.out.println("Attempt to join hunt failed, try again later");

                    }
                    cur = Mode.Menu;
                    break;
                case Shop:
                    new ShopController().enter(game);
                    cur = Mode.Menu;
                    break;
                case Museum:
                    System.out.println("Whose art do you want to see?");
                    in = scanner.nextLine();
                    List<String> NFTS = game.server.history.getNFTs(in);
                    for(String nft:NFTS){
                        System.out.println(nft);
                    }
                    cur = Mode.Menu;

                    break;
                case Donate:
                    System.out.println("Who do you want to donate to?");
                    in = scanner.nextLine();
                    String other = in;
                    System.out.println("How much do you want to donate?");
                    in = scanner.nextLine();
                    double amount = 0;
                    try {
                         amount = Double.parseDouble(in);
                    } catch (Exception e){

                    }
                    System.out.println("Is there any message you want to send with this donation?");
                    in = scanner.nextLine();
                    String memo = in;
                    if(game.server.getGodCoins(game.user) >= amount){
                        try {
                            if(game.submitTransaction(new Donate(game.user, other, amount, memo,game.privateKey.sign(amount + memo + other)))){
                                System.out.println("Donation successful");
                            }

                        } catch (Exception e){

                        }
                    }
                    cur = Mode.Menu;
                    break;
                case Talk:
                    int slays = game.server.history.getSlays(game.user);
                    switch(storyProgress){
                        case 0:
                            System.out.println("It's hunting time!");
                            if(slays > 0) storyProgress++;
                            break;
                        case 1:
                            System.out.println("Did you know there was a time being used to use non-magic coins? How lame. ");
                            if(slays > 1) storyProgress++;
                            break;
                        case 2:
                            System.out.println("Legend has it the Old Gods were once mortal like us, but not like us, since we have no hope of becoming Gods. We can only slay them. ");
                            if(slays > 2) storyProgress++;
                            break;
                        case 3:
                            System.out.println("It only because necessary to hunt the New Gods after the Old Gods took over. ");
                            if(slays > 3) storyProgress++;
                            break;
                        case 4:
                            System.out.println("Good luck, " + game.user + ", our hero. ");
                            break;

                    }
                    cur = Mode.Menu;
                    break;

                case Quit:
                    quit = true;
                    break;
            }
        }
        return false;
    }



}
