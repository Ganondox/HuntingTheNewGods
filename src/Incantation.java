public class Incantation extends Action{

    String incantation;
    int power;

    public Incantation(String incantation, int power) {
        this.incantation = incantation;
        this.power = power;
    }

    @Override
    BattleState act(BattleState state) {
        BattleState next = state.copy();
       if(next.boss.weakness.equals(incantation)){
           next.boss = next.boss.hurt(power, next);
           next.boss = next.boss.changeWeakness(next);
           next.feedback = "The incantation strikes at the heart of the New God!";
       } else {
           next.feedback = "The New God is not pleased with your heresy. ";

       }
       return next;
    }
}
