import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BattleController {

    Battle battle;
    List<Action> history;

    public BattleController(Battle battle) {
        this.battle = battle;
        history = new LinkedList<>();
    }

    boolean attemptHunt(){
        BattleState cur = battle.start.copy();
        while(cur.player.health > 0 && cur.boss.health > 0){

            //display game state to player
            String line = "";
            for(int j = 0; j < cur.world.width; j++){
                 line += "_";
            }
            System.out.println(line);
            for(int i = 0; i < cur.world.height; i++){
                line = "|";
                for(int j = 0; j < cur.world.width; j++){
                    if(cur.player.x == j && cur.player.y == i){
                        if(cur.boss.x == j && cur.boss.y == i){
                            line += "X";
                        } else {
                            line += "P";
                        }
                    } else {
                        if(cur.boss.x == j && cur.boss.y == i){
                            line += "B";
                        } else {
                            line += ".";
                        }
                    }
                }
                line += "|";
                System.out.println(line);

            }
            line = "";
            for(int j = 0; j < cur.world.width; j++){
                line += "-";
            }
            System.out.println(line);
            System.out.println("This god is weak to the sacred incantation \033[9m" + cur.boss.weakness + "\033[0m "  );



            //select action
            System.out.println("W - up");
            System.out.println("A - left");
            System.out.println("S - down");
            System.out.println("D - right");
            System.out.println("I - Incantation");
            Scanner scanner = new Scanner(System.in);
            Action action;
            String in = scanner.nextLine();
            if(in.equals("W")){
                action = new Move(Move.Direction.up);
            } else
            if(in.equals("A")){
                action = new Move(Move.Direction.left);
            } else
            if(in.equals("S")){
                action = new Move(Move.Direction.down);
            } else
            if(in.equals("D")){
                action = new Move(Move.Direction.right);
            } else
            if(in.equals("I")) {
                System.out.println("Type in incanation");
                in = scanner.nextLine();
                action = new Incantation(in, 1);
            } else {
                action = new Stall();
            }

            //give feedback from action
            history.add(action);
            cur = cur.next(action);
            System.out.println(cur.feedback);
        }

        return cur.isWin();

    }

}
