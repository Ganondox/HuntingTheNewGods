import java.security.PrivateKey;
import java.util.Scanner;

public class Main {

    public static void main(String args[]){

        System.out.println("Welcome to the Hunting Grounds, brave hunter. We needs hunters like you to slay the New Gods so the cycle of rebirth may continue, and our transactions may be processed. You see, it takes the death of a New God to release the magic that runs our society. Whenever the current New God is slain, magic will be released, and the hunter who slew it is annouced as the lord of the hunt, and will be paid in the divine God Coins for their services. Over time, the gods will grow more powerful, but our council of elders have agreed to decrease their power since it has been a long time since we've seen a hunter. Blessings to you hunter, may you save our society through your greed, and may be the Old Gods finally perish. ");

        System.out.println("Brave Hunter, what is your name? Remember that names have power.");

        PseudoServer server = new PseudoServer();
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();
        PrivateKey key = null;
        while(key == null){

            key = MakeUser.make(server, in);
        }

        PrivateUser privateUser = new PrivateUser(key);

        ModeController mode = new ModeController( new PseudoClient(in, server, privateUser) );
        mode.play();


        //System.out.println("\033[9mHI\033[0m there");
    }

}
