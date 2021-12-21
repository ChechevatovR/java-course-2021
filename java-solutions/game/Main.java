package game;

import util.Scanner;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Main {
    public static void main(final String[] args) {
        try (
                final FileOutputStream fileOutputStream = new FileOutputStream("log.txt");
                final OutputStreamWriter logger = new OutputStreamWriter(fileOutputStream)
        ) {
            final int m = 3;
            final int n = 3;
            final int k = 7;
            final int result = new TurnByTurnGame(
                    new MNKBoard(m, n, k),
                    new HumanPlayer(new Scanner(System.in)),
                    new RandomPlayer()
            ).play(logger);
            System.out.println("The game has ended, and the result is:");
            if (result > 0) {
                System.out.println("Player №" + result + " won");
            } else if (result < 0) {
                System.out.println("Player №" + -result + " lost. Everyone else won");
            } else {
                System.out.println("Draw");
            }
        } catch (final FileNotFoundException e) {
            System.err.println("Log file not found " + e.getMessage());
        } catch (final IOException e) {
            System.err.println("IOException when creating logger " + e.getMessage());
        }
    }
}
