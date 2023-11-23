public class Stall extends Action{

    public Stall() {
    }

    @Override
    BattleState act(BattleState state) {
        return state.copy();
    }
}
