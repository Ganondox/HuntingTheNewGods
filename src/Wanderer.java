public class Wanderer extends Boss{


        public Wanderer(int health, String weakness, int x, int y) {
            super.health = health;
            super.weakness = weakness;
            super.x = x;
            super.y = y;

        }

        @Override
        Action act(BattleState state) {

            return new Wander(Wander.Direction.up, 1);
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

            for(int i = 0; i < 10; i++) {
                switch ((int)(n%5)) {
                    case 0:
                        next.weakness += "a";
                        break;
                    case 1:
                        next.weakness += "e";
                        break;
                    case 2:
                        next.weakness += "i";
                        break;
                    case 3:
                        next.weakness += "o";
                        break;
                    case 4:
                        next.weakness += "u";
                        break;

                }
                n /= 5;
            }


            return next;
        }

        @Override
        Boss copy() {
            return new Wanderer(health, weakness, x, y);
        }
    }



