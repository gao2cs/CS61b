package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Draw a horizontal pattern of TETile inside the world.
     *
     * @param x is the starting width position
     * @param y is the starting height position
     * @param width is the length of the width
     * @param world is a 2D array of TETile objects
     * @param t is a single TETile object
     * @param r is a Random object
     */
    private static void addHorizontalLine(int x, int y, int width, TETile[][] world, TETile t, Random r) {
        for (int i = x; i < x + width; i += 1) {
            world[i][y] = TETile.colorVariant(t, 32, 32, 32, r);
        }
    }

    /**
     * Draw a Hexagon composed of TETiles inside the world.
     *
     * @param x is the starting width position
     * @param y is the starting height position
     * @param s is the size of the Hexagon
     * @param world is a 2D array of TETile objects
     * @param t is a single TETile object
     * @param r is a Random object
     */
    private static void addHexagon(int x, int y, int s, TETile[][] world, TETile t, Random r) {
        int depth = y;
        for (int i = 0; i < s; i += 1) {
            int width = 2 * i + s;
            addHorizontalLine(x - i, depth, width, world, t, r);
            depth -= 1;
        }
        for (int i = s - 1; i >= 0; i -= 1) {
            int width = 2 * i + s;
            addHorizontalLine(x - i, depth, width, world, t, r);
            depth -= 1;
        }
    }

    /**
     * Get a TETile randomly.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.GRASS;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            case 3: return Tileset.SAND;
            case 4: return Tileset.MOUNTAIN;
            default: return Tileset.NOTHING;
        }
    }
    /**
     * Draw vertical pattern of Hexagons of a specific size from bottom to top.
     *
     * @param x is the starting width position
     * @param y is the starting height position
     * @param s is the size of the Hexagon
     * @param world is a 2D array of TETile objects
     * @param t is a single TETile object
     * @param r is a Random object
     * @param numOfHexagons is the number of hexagon of size s to be drawn
     */
    private static void addHexagonVerticalPattern(int x, int y, int s, TETile[][] world, TETile t, Random r, int numOfHexagons) {
        int offset = 0;
        for (int i = 0; i < numOfHexagons; i += 1) {
            t = randomTile();
            addHexagon(x, y + offset, s, world, t, r);
            offset += 2 * s;
        }

    }
    /**
     * Draw a tesselation of hexagons consisting of 19 total.
     *
     * @param x is the starting width position
     * @param y is the starting height position
     * @param s is the size of the Hexagon
     * @param world is a 2D array of TETile objects
     * @param t is a single TETile object
     * @param r is a Random object
     */
    private static void addHexagonPattern(int x, int y, int s, TETile[][] world, TETile t, Random r) {
        addHexagonVerticalPattern(x, y, s, world, t, r, 3);
        x += 2 * s - 1;
        y -= s;
        addHexagonVerticalPattern(x, y, s, world, t, r, 4);
        x += 2 * s - 1;
        y -= s;
        addHexagonVerticalPattern(x, y, s, world, t, r, 5);
        x += 2 * s - 1;
        y += s;
        addHexagonVerticalPattern(x, y, s, world, t, r, 4);
        x += 2 * s - 1;
        y += s;
        addHexagonVerticalPattern(x, y, s, world, t, r, 3);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        addHexagonPattern(10, 20, 4, world, randomTile(), RANDOM);

        ter.renderFrame(world);
    }
}
