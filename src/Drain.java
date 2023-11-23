public class Drain extends Action {

    public Drain(int damage) {
        this.damage = damage;
    }

    int damage;



    @Override
    BattleState act(BattleState state) {
        BattleState next = state.copy();
        next.player = next.player.damage(damage);
        return next;
    }
}
