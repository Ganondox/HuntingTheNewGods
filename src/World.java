public class World {

    int width;
    int height;

    int[][] arena;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.arena = new int[width][height];
    }
}
