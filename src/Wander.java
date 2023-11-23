public class Wander extends Action{

    enum Direction{up, down, left , right}

    Direction dir;
    int damage;

    public Wander(Direction dir, int damage) {
        this.dir = dir;
        this.damage = damage;
    }

    @Override
    BattleState act(BattleState state) {


        BattleState copy = state.copy();

        boolean targeted = false;

        if(copy.player.x == copy.boss.x){
            if(copy.player.y > copy.boss.y){
                dir = Direction.down;
                targeted = true;
            }
            if(copy.player.y < copy.boss.y){
                dir = Direction.up;
                targeted = true;
            }
        }
        if(copy.player.y == copy.boss.y){
            if(copy.player.x > copy.boss.x){
                dir = Direction.right;
                targeted = true;
            }
            if(copy.player.x < copy.boss.x){
                dir = Direction.left;
                targeted = true;
            }
        }

        if(!targeted) {
            long n = copy.r.nextLong();
            copy.generations++;
            if (n < 0) n = -n;
            switch ((int) (n % 4)) {
                case 0:
                    dir = Direction.up;
                    break;
                case 1:
                    dir = Direction.left;
                    break;
                case 2:
                    dir = Direction.right;
                    break;
                case 3:
                    dir = Direction.down;
                    break;
            }
        }

        Boss newboss = copy.boss.copy();
        switch (dir){
            case up:
                newboss.y--;
                if(newboss.y < 0)newboss.y = 0;
                break;
            case right:
                newboss.x++;
                if(newboss.x >= copy.world.width)newboss.x = copy.world.width-1;
                break;
            case left:
                newboss.x--;
                if(newboss.x < 0)newboss.x = 0;
                break;
            case down:
                newboss.y++;
                if(newboss.y >= copy.world.height)newboss.y = copy.world.height-1;
                break;

        }

        if(newboss.x == copy.player.x && newboss.y == copy.player.y){
            copy.player = copy.player.damage(damage);
        }

        copy.boss = newboss;

        return copy;
    }
}
