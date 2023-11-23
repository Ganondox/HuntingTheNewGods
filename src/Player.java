public class Player {


    int health;
    int x;
    int y;

    public Player(int health, int x, int y) {
        this.health = health;
        this.x = x;
        this.y = y;
    }

    Player damage(int damage){
        Player after = this.copy();
        after.health -= damage;
        return after;
    }

    Player copy(){
        return new Player(health, x, y);
    }

}
