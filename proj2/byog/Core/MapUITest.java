package byog.Core;

public class MapUITest {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    public static void main(String[] args) {
        Menu menu = new Menu(WIDTH, HEIGHT);
        menu.startGameWithString(Long.parseLong("123456789"));
    }
}
