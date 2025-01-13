package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public  class MapGenerator {
    private int width, height;
    private TETile[][] world;
    private ArrayList<Room> rooms = new ArrayList<>();

    private long seed;
    private Random random;

    public MapGenerator(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.seed = seed;
        this.random = new Random(seed);
        world = new TETile[width][height];
        for (int x = 0; x < this.width; x += 1) {
            for (int y = 0; y < this.height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private Position sampleRoom(Room room) {
        return new Position(room.p.x + 1 +  random.nextInt(room.width - 2),
                room.p.y + 1 + random.nextInt(room.height - 2));
    }

    private boolean isValidRoom(Room room) {
        return room.p.x >= 0 && room.p.y >= 0 && room.p.x + room.width < this.width &&
                room.p.y + room.height < this.height;
    }

    private void addRoom(Room room) {
        if (!isValidRoom(room)) {
            return;
        }
        for (int x = room.p.x; x < room.p.x + room.width; x += 1) {
            for (int y = room.p.y; y < room.p.y + room.height; y += 1) {
                if (x == room.p.x || y == room.p.y || x == room.p.x + room.width - 1 || y == room.p.y + room.height - 1) {
                    world[x][y] = TETile.colorVariant(Tileset.WALL, 32, 32, 32, random);
                } else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
        rooms.add(room);
    }

    private boolean isOverlapping(Room other) {
        for (Room room: rooms) {
            if (room.overlaps(other)) {
                return true;
            }
        }
        return false;
    }

    private void generateRooms(int N) {
        for (int i = 0; i < N; i += 1) {
            int randomWidth = 4 + random.nextInt(9 - 4 + 1);
            int randomHeight = 4 + random.nextInt(9 - 4 + 1);
            Position p = new Position(random.nextInt(this.width - 1), random.nextInt( this.height - 1));
            Room randomRoom = new Room(p, randomWidth, randomHeight);
            if (!isValidRoom(randomRoom)) {
                continue;
            }

            if (!isOverlapping(randomRoom)) {
                addRoom(randomRoom);
                rooms.add(randomRoom);
            }
        }
    }

    private void drawHorizontalHallway(Position p1, Position p2) {
        int xStart = Math.min(p1.x, p2.x);
        int xEnd = Math.max(p1.x, p2.x);
        assert p1.y == p2.y;
        for (int x = xStart; x <= xEnd; x += 1) {
            world[x][p1.y] = Tileset.FLOOR;
        }
    }

    private void drawVerticalHallway(Position p1, Position p2) {
        int yStart = Math.min(p1.y, p2.y);
        int yEnd = Math.max(p1.y, p2.y);
        assert p1.x == p2.x;
        for (int y = yStart; y <= yEnd; y += 1) {
            world[p1.x][y] = Tileset.FLOOR;
        }
    }

    private void drawLShapedHallway(Position p1, Position p2) {
        if (random.nextBoolean()) {
            drawHorizontalHallway(p1, new Position(p2.x, p1.y));
            drawVerticalHallway(new Position(p2.x, p1.y), p2);
        } else {
            drawVerticalHallway(p1, new Position(p1.x, p2.y));
            drawHorizontalHallway(new Position(p1.x, p2.y), p2);
        }
    }

    private void connectRooms() {
        Collections.sort(rooms);
        for (int i = 0; i < rooms.size() - 1; i += 1) {
            Room room1 = rooms.get(i);
            Room room2 = rooms.get(i + 1);

            Position p1 = sampleRoom(room1);
            Position p2 = sampleRoom(room2);

            if (p1.y == p2.y) {
                drawHorizontalHallway(p1, p2);
            } else if (p1.x == p2.x) {
                drawVerticalHallway(p1, p2);
            } else {
                drawLShapedHallway(p1, p2);
            }
        }
    }

    private void buildWalls() {
        int[][] neighbors = {
                {-1,  1}, {0,  1}, {1,  1},
                {-1,  0},          {1,  0},
                {-1, -1}, {0, -1}, {1, -1}
        };
        for (int x = 1; x < this.width - 1; x += 1) {
            for (int y = 1; y < this.height - 1; y += 1) {
                if (world[x][y] == Tileset.FLOOR) {
                    for (int[] neighbor : neighbors) {
                        if (world[x + neighbor[0]][y + neighbor[1]] == Tileset.NOTHING) {
                            world[x + neighbor[0]][y + neighbor[1]] = TETile.colorVariant(Tileset.WALL, 32, 32, 32, random);
                        }
                    }
                }
            }
        }
    }

    private void addPlayer() {
        while (true) {
            int x = random.nextInt(this.width);
            int y = random.nextInt(this.height);

            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.PLAYER;
                break;
            }
        }
    }

    private void addLockedDoor() {
        while (true) {
            int x = random.nextInt(this.width);
            int y = random.nextInt(this.height);

            if (world[x][y].equals(Tileset.WALL)) {
                world[x][y] = Tileset.LOCKED_DOOR;
                break;
            }
        }
    }

    public void generateMap(int upperbound) {
        int N = 10 + random.nextInt(upperbound - 10 + 1);
        generateRooms(N);
        connectRooms();
        buildWalls();
        addPlayer();
        addLockedDoor();
    }

    public TETile[][] getMap() {
        return world;
    }
}
