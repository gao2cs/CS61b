package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class MapGeneratorUI extends MapGenerator {

    public MapGeneratorUI(int width, int height, long seed) {
        super(width, height, seed);
        worldSaved = new TETile[this.width][this.height];
        for (int x = 0; x < this.width; x += 1) {
            for (int y = 0; y < this.height; y += 1) {
                worldSaved[x][y] = Tileset.NOTHING;
            }
        }
        saveState = false;
    }

    protected Position player;
    protected TETile[][] worldSaved;
    protected boolean saveState;

    protected void saveMap() {
        this.worldSaved = this.world;
        this.saveState = true;
    }

    protected void loadMap() {
        this.world = this.worldSaved;
    }

    @Override
    protected void addPlayer() {
        while (true) {
            int x = random.nextInt(this.width);
            int y = random.nextInt(this.height);

            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.PLAYER;
                player = new Position(x, y);
                break;
            }
        }
    }

    protected boolean isValidMove(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height || world[x][y] != Tileset.FLOOR) {
            return false;
        }
        return true;
    }

    protected void updatePlayer(int x, int y) {
        this.world[this.player.x][this.player.y] = Tileset.FLOOR;
        this.player.x = x;
        this.player.y = y;
        this.world[this.player.x][this.player.y] = Tileset.PLAYER;
    }

    protected void handlePlayer(char key) {
        switch (key) {
            case 'a':
                if (isValidMove(player.x - 1, player.y)) {
                    updatePlayer(player.x - 1, player.y);
                }
                break;
            case 'w':
                if (isValidMove(player.x, player.y + 1)) {
                    updatePlayer(player.x, player.y + 1);
                }
                break;
            case 'd':
                if (isValidMove(player.x + 1, player.y)) {
                    updatePlayer(player.x + 1, player.y);
                }
                break;
            case 's':
                if (isValidMove(player.x, player.y - 1)) {
                    updatePlayer(player.x, player.y - 1);
                }
                break;
        }
    }

    public void recoverTileEnginePen() {
        Font font = new Font("Monaco", Font.BOLD, 16 - 2);
        StdDraw.setFont(font);
    }

    public void enableHUD(boolean gameState) {
        while (gameState) {
            String string;

            int mouseX = (int) StdDraw.mouseX();
            int mouseY = (int) StdDraw.mouseY();

            if (mouseX < 0 || mouseX >= this.width || mouseY < 0 || mouseY >= this.height) {
                continue;
            }

            string = world[mouseX][mouseY].description();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(1, this.height - 2, 10, 1);
            Font font = new Font("Monaco", Font.PLAIN, 20);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.ORANGE);
            StdDraw.textLeft(1, this.height - 2, string);

            recoverTileEnginePen();
            StdDraw.show();

            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
    }
}
