package byog.Core;

import byog.TileEngine.TERenderer;

public class MapVisualTest {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        MapGenerator map = new MapGenerator(WIDTH, HEIGHT, 2873123);
        map.generateMap(100);

        ter.renderFrame(map.getMap());
    }
}
