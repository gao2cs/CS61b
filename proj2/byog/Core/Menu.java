package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu implements Serializable {
    private int width;
    private int height;
    private long seed;
    private Random rand;
    private boolean gameState;
    private boolean menuState;

    public MapGeneratorUI mapUIgenerator = null;
    TERenderer ter;

    public Menu(int width, int height) {
        this.width = width;
        this.height = height;
        ter = new TERenderer();
        this.gameState = false;
        this.menuState = true;
    }

    public void displayMenu() {
        initializeMenu();
        this.menuState = true;
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
        StdDraw.setPenColor(StdDraw.ORANGE);
        StdDraw.text(xCenter, 0.75 * this.height, "CS61B: THE GAME");
        StdDraw.enableDoubleBuffering();

        font = new Font("Monaco",Font.PLAIN, 20);
        StdDraw.setFont(font);
        StdDraw.text(xCenter, 0.5 * this.height, "New Game (N)");
        StdDraw.text(xCenter, 0.5 * this.height - 1.5, "Load Game (L)");
        StdDraw.text(xCenter, 0.5 * this.height - 3.0, "Quit (Q)");
    }

    private void drawFrame(String s) {
        this.menuState = true;
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        int xCenter = this.width / 2;
        int yCenter = this.height / 2;

        Font font = new Font("Monaco", Font.PLAIN, 40);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.ORANGE);
        StdDraw.text(xCenter, 0.75 * this.height, "CS61B: THE GAME");

        font = new Font("Monaco",Font.PLAIN, 20);
        StdDraw.setFont(font);
        StdDraw.text(xCenter, 0.5 * this.height, "New Game (N)");
        StdDraw.text(xCenter, 0.5 * this.height - 1.5, "Load Game (L)");
        StdDraw.text(xCenter, 0.5 * this.height - 3.0, "Quit (Q)");
        StdDraw.text(xCenter, 0.5 * this.height - 6.0, s);
        StdDraw.show();
    }

    private void handleNewGame() {
        boolean isSpressed = false;
        StringBuilder stringBuilder = new StringBuilder();
        while (!isSpressed) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (key == 's') {
                    isSpressed = true;
                    drawFrame("");
                    this.seed = Long.parseLong(stringBuilder.toString());
                    this.gameState = true;
                    this.menuState = false;
                    mapUIgenerator = new MapGeneratorUI(this.width , this.height, this.seed);
                    mapUIgenerator.generateMap(100);
                    ter.initialize(this.width, this.height);
                    ter.renderFrame(mapUIgenerator.getMap());
                } else {
                    stringBuilder.append(key);
                    drawFrame(stringBuilder.toString());
                }
            }
        }
    }

    public void startGame() {
        displayMenu();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                switch (key) {
                    case 'n':
                        drawFrame("Enter Seed");
                        handleNewGame();
                        break;
                    case ':':
                        while (true) {
                            if (StdDraw.hasNextKeyTyped()) {
                                if (Character.toLowerCase(StdDraw.nextKeyTyped()) == 'q') {
                                    if (mapUIgenerator == null || this.menuState) {
                                        System.exit(0);
                                    } else {
                                        this.saveGame();
                                        this.gameState = false;
                                        displayMenu();
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    case 'l':
                        if (mapUIgenerator == null || !mapUIgenerator.saveState) {
                            System.exit(0);
                        }
                        if (!gameState) {
                            this.menuState = false;
                            this.gameState = true;
                            this.readGame();
                            mapUIgenerator.recoverTileEnginePen();
                            ter.renderFrame(mapUIgenerator.getMap());
                        }
                        break;
                    case 'a':
                    case 'w':
                    case 's':
                    case 'd':
                        if (this.gameState) {
                            mapUIgenerator.handlePlayer(key);
                            ter.renderFrame(mapUIgenerator.getMap());
                        }
                        break;
                } // After key pressing, the program will run the next line and loop
                if (!menuState) {
                    mapUIgenerator.enableHUD(this.gameState);
                }
            }
        }
    }

    public void newGame(String playString) {
        StringBuilder numberBuilder = new StringBuilder();
        int i;
        for (i = 1; i < playString.length(); i += 1) {
            if (playString.charAt(i) != 's') {
                numberBuilder.append(playString.charAt(i));
            } else {
                break;
            }
        }
        this.seed = Long.parseLong(numberBuilder.toString());
        this.gameState = true;
        this.menuState = false;
        mapUIgenerator = new MapGeneratorUI(this.width , this.height, this.seed);
        mapUIgenerator.generateMap(100);
        //ter.initialize(this.width, this.height);
        play(playString.substring(i + 1));
        //ter.renderFrame(mapUIgenerator.getMap());
    }

    public void loadGame(String playString) {
        mapUIgenerator = new MapGeneratorUI(this.width , this.height, this.seed);
        readGame();
        this.gameState = true;
        this.menuState = false;
        //ter.initialize(this.width, this.height);
        play(playString.substring(1));
        //ter.renderFrame(mapUIgenerator.getMap());
    }

    private void play(String playString) {
        for (int i = 0; i < playString.length(); i += 1) {
            char key = playString.charAt(i);
            switch (key) {
                case 'a':
                    if (mapUIgenerator.isValidMove(mapUIgenerator.player.x - 1, mapUIgenerator.player.y)) {
                        mapUIgenerator.updatePlayer(mapUIgenerator.player.x - 1, mapUIgenerator.player.y);
                    }
                    break;
                case 'w':
                    if (mapUIgenerator.isValidMove(mapUIgenerator.player.x, mapUIgenerator.player.y + 1)) {
                        mapUIgenerator.updatePlayer(mapUIgenerator.player.x, mapUIgenerator.player.y + 1);
                    }
                    break;
                case 'd':
                    if (mapUIgenerator.isValidMove(mapUIgenerator.player.x + 1, mapUIgenerator.player.y)) {
                        mapUIgenerator.updatePlayer(mapUIgenerator.player.x + 1, mapUIgenerator.player.y);
                    }
                    break;
                case 's':
                    if (mapUIgenerator.isValidMove(mapUIgenerator.player.x, mapUIgenerator.player.y - 1)) {
                        mapUIgenerator.updatePlayer(mapUIgenerator.player.x, mapUIgenerator.player.y - 1);
                    }
                    break;
                case ':':
                    if (i + 1 < playString.length() && playString.charAt(i + 1) == 'q') {
                        saveGame();
                        return;
                    }
                    break;
            }
        }
    }

    private void saveGame() {
        mapUIgenerator.saveMap();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savefile.txt"))) {
            out.writeObject(mapUIgenerator.world);
            out.writeObject(mapUIgenerator.player);
            out.writeLong(this.seed); // Save the seed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("savefile.txt"))) {
            mapUIgenerator.world = (TETile[][]) in.readObject();
            mapUIgenerator.player = (Position) in.readObject();
            this.seed = in.readLong();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}