import java.util.Random;

public class BattleState {

    Random r;
    int generations;
    int seed;
    Boss boss;
    Player player;
    World world;
    String feedback;

    public BattleState(Random r, int uses, int seed, Boss boss, Player player, World world, String feedback) {
        this.r = r;
        this.generations = uses;
        this.seed = seed;
        this.boss = boss;
        this.player = player;
        this.world = world;
        this.feedback = feedback;
    }

    BattleState next(Action a){
        BattleState state = copy();
        state.feedback = "";
        if(player.health <= 0) return state;
        state =  a.act(state);
       return boss.act(state).act(state);
    }

    boolean isWin(){
        return boss.health <= 0;
    }

    BattleState copy(){
        Random random = new Random(seed);
        for(int i = 0; i< generations; i++ ){
           random.nextLong();
        }
        return new BattleState(random, generations, seed,  boss, player, world, feedback);

    }

}
