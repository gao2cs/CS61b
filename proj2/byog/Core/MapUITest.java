package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.algs4.SparseVector;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.util.Random;

public class MapUITest {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    public static class Menu {
        private int width;
        private int height;
        private long seed;
        private Random rand;

        private MapGeneratorUI mapgenerator;
        TERenderer ter;

        public Menu(int width, int height) {
            this.width = width;
            this.height = height;
            ter = new TERenderer();
        }

        public void displayMenu() {
            initializeMenu();
            StdDraw.show();
        }

        private void setRandomSeed(long seed) {
            rand = new Random(seed);
        }

        private void initializeMenu() {
            StdDraw.setCanvasSize(this.width * 16, this.height * 16);
            StdDraw.setXscale(0, this.width);
            StdDraw.setYscale(0, this.height);

            StdDraw.clear(Color.BLACK);
            StdDraw.enableDoubleBuffering();

            int xCenter = this.width / 2;
            int yCenter = this.height / 2;

            Font font = new Font("Monaco", Font.PLAIN, 40);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(xCenter, 0.75 * this.height, "CS61B: THE GAME");
            StdDraw.enableDoubleBuffering();

            font = new Font("Monaco",Font.PLAIN, 20);
            StdDraw.setFont(font);
            StdDraw.text(xCenter, 0.5 * this.height, "(N)New Game");
            StdDraw.text(xCenter, 0.5 * this.height - 1.5, "(L)Load");
            StdDraw.text(xCenter, 0.5 * this.height - 3.0, "(Q)Quit");
        }

        private void drawFrame(String s) {
            StdDraw.clear(Color.BLACK);
            StdDraw.enableDoubleBuffering();

            int xCenter = this.width / 2;
            int yCenter = this.height / 2;

            Font font = new Font("Monaco", Font.PLAIN, 40);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(xCenter, 0.75 * this.height, "CS61B: THE GAME");

            font = new Font("Monaco",Font.PLAIN, 20);
            StdDraw.setFont(font);
            StdDraw.text(xCenter, 0.5 * this.height, "(N)New Game");
            StdDraw.text(xCenter, 0.5 * this.height - 1.5, "(L)Load");
            StdDraw.text(xCenter, 0.5 * this.height - 3.0, "(Q)Quit");
            StdDraw.text(xCenter, 0.5 * this.height - 6.0, s);
            StdDraw.show();
        }

        private void handleNewGame() {
            boolean isSpressed = false;
            StringBuilder stringBuilder = new StringBuilder();
            while (!isSpressed) {
                if (StdDraw.hasNextKeyTyped()) {
                    char key = StdDraw.nextKeyTyped();
                    if (key == 'S') {
                        isSpressed = true;
                        drawFrame("");
                        this.seed = Long.parseLong(stringBuilder.toString());
                        mapgenerator = new MapGeneratorUI(this.width , this.height, this.seed);
                        mapgenerator.generateMap(100);
                        ter.initialize(this.width, this.height);
                        ter.renderFrame(mapgenerator.getMap());
                        System.out.println(TETile.toString(mapgenerator.getMap()));
                    } else {
                        stringBuilder.append(key);
                        drawFrame(stringBuilder.toString());
                        StdDraw.pause(20);
                    }
                }
            }
        }

        public void startMenu() {
            displayMenu();
            boolean inMenu = true;
            while (inMenu) {
                if (StdDraw.hasNextKeyTyped()) {
                    char key = StdDraw.nextKeyTyped();
                    switch (key) {
                        case 'N':
                            drawFrame("Enter Seed");
                            handleNewGame();
                            inMenu = false;
                            break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
      Menu menu = new Menu(WIDTH, HEIGHT);
      menu.startMenu();

    }
}
