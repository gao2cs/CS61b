package byog.Core;

public class Room implements Comparable<Room> {
    protected Position p;
    protected int width, height;
    protected boolean hasWalls = false;

    @Override
    public int compareTo(Room other) {
        return this.p.x - other.p.x;
    }

    public Room(Position p, int width, int height) {
        this.p = p;
        this.width = width;
        this.height = height;
    }

    private boolean horizontalOverlaps(Room other) {
        return other.p.x <= this.p.x + this.width - 1 && other.p.x + other.width - 1 >= this.p.x;
    }

    private boolean verticalOverlaps(Room other) {
        return other.p.y <= this.p.y + this.height - 1 && other.p.y + other.height - 1 >= this.p.y;
    }

    public boolean overlaps(Room other) {
        return this.horizontalOverlaps(other) && this.verticalOverlaps(other);
    }
}
