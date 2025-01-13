package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);

        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        this. rand = new Random(seed);

    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder stringbuilder = new StringBuilder();
        for (int i = 0; i < n; i += 1) {
            char ch = (char) ('a' + this.rand.nextInt(26));
            stringbuilder.append(ch);
        }
        return stringbuilder.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        int xCoord = this.width / 2;
        int yCoord = this.height / 2;

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);

        if (!this.gameOver) {
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.textLeft(1, this.height - 1, "Round: " + this.round);
            StdDraw.text(xCoord, this.height - 1, this.playerTurn? "Type!" : "Watch!");
            StdDraw.textRight(this.width - 1, this.height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, this.height - 2, this.width - 1, this.height - 2);
        }

        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(xCoord, yCoord, s);
        StdDraw.show();

    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        this.playerTurn = false;
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);

        int xCoord = this.width / 2;
        int yCoord = this.height / 2;

        for (int i = 0; i < letters.length(); i += 1) {
            StdDraw.pause(500);
            drawFrame(Character.toString(letters.charAt(i)));
            StdDraw.pause(1000);
        }
        this.playerTurn = true;
        drawFrame(" ");
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                stringBuilder.append(StdDraw.nextKeyTyped());
                drawFrame(stringBuilder.toString());
            }
            StdDraw.pause(500);
        }
        return stringBuilder.toString();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        this.gameOver = false;
        this.round = 1;

        //TODO: Establish Game loop
        while(!this.gameOver) {
            this.playerTurn = false;
            String message = "Round: ";
            drawFrame(message + this.round);
            StdDraw.pause(500);

            String letters = generateRandomString(this.round);
            flashSequence(letters);

            String userTyped = solicitNCharsInput(this.round);
            if (userTyped.equals(letters)) {
                this.round += 1;
                continue;
            }
            this.gameOver = true;
            drawFrame("Game Over! You made it to round: " + this.round);
        }
    }
}
