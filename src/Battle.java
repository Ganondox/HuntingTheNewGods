import java.util.List;
import java.util.Random;

public class Battle {

    int seed;
    BattleState start;
    BattleState cur;

    Battle(int seed, int difficulty){
       //create initial state
        Random r = new Random(seed);
        Boss boss = new Wanderer(difficulty + 1, "Oogie boogie", 3, 3);
        Player player = new Player(10, 1, 1);
        World world = new World(5,5);
        start = new BattleState(r, 0, seed, boss, player, world,"");




        this.seed = seed;
    }

    boolean run(List<Action> actions){
        cur = start.copy();
        cur.r = new Random(seed);
        for(Action action: actions){
            cur = cur.next(action);
        }
        return cur.isWin();
    }

}
