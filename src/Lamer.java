public class Lamer extends Boss {

    public Lamer(int health, String weakness) {
        super.health = health;
        super.weakness = weakness;
    }

    @Override
    Action act(BattleState state) {
        return new Drain(1);
    }

    @Override
    Boss hurt(int attack, BattleState state) {
        Boss next = copy();
        next.health -= attack;
        return next;
    }

    @Override
    Boss changeWeakness(BattleState state) {

        Boss next = copy();

        Long n = state.r.nextLong();
        if(n < 0) n = -n;
        state.generations++;

        next.weakness = "";

        int m = 3;

        switch ((int)(n%m)){
            case 0:
                next.weakness += "Abra ";
                break;
            case 1:
                next.weakness += "Ala ";
                break;
            case 2:
                next.weakness += "Hocus ";
                break;
            default:
                System.out.println((int)(n%m));
        }

        n/=m;

        m = 3;

        switch ((int)(n%m)){
            case 0:
                next.weakness += "cadabra";
                break;
            case 1:
                next.weakness += "kazam";
                break;
            case 2:
                next.weakness += "pocus";
                break;
            default:
                System.out.println((int)(n%m));
        }


        return next;
    }

    @Override
    Boss copy() {
        return new Lamer(health, weakness);
    }
}
