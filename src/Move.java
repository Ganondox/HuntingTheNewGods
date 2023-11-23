public class Move extends Action{



        enum Direction{up, down, left , right}

        Direction dir;


        public Move(Direction dir) {
            this.dir = dir;
        }

        @Override
        BattleState act(BattleState state) {

            BattleState copy = state.copy();

            Player newplayer = copy.player.copy();
            switch (dir){
                case up:
                    newplayer.y--;
                    if(newplayer.y < 0)newplayer.y = 0;
                    break;
                case right:
                    newplayer.x++;
                    if(newplayer.x >= copy.world.width)newplayer.x = copy.world.width-1;
                    break;
                case left:
                    newplayer.x--;
                    if(newplayer.x < 0)newplayer.x = 0;
                    break;
                case down:
                    newplayer.y++;
                    if(newplayer.y >= copy.world.height)newplayer.y = copy.world.height-1;
                    break;

            }

            copy.player = newplayer;

            return copy;
        }
    }



