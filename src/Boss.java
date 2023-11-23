public abstract class Boss {

    int x;
    int y;

    int health;
    String weakness;

   abstract Action act(BattleState state);

   abstract Boss hurt(int attack, BattleState state);

   abstract Boss changeWeakness(BattleState state);

   abstract Boss copy();
}
