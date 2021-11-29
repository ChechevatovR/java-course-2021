package game;

import scanner.Scanner;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("log.txt");
                OutputStreamWriter logger = new OutputStreamWriter(fileOutputStream);
        ) {
            int m = 3;
            int n = 3;
            int k = 7;
            final int result = new TurnByTurnGame(
                    new MNKBoard(m, n, k),
                    new HumanPlayer(new Scanner(System.in), 1),
                    new RandomPlayer ()
//                    new RandomPlayer(11, 11)
            ).play(logger);
            System.out.println("The game has ended, and the result is:");
            if (result > 0) {
                System.out.println("Player №" + result + " won");
            } else if (result < 0) {
                System.out.println("Player №" + -result + " lost. Everyone else won");
            } else {
                System.out.println("Draw");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Log file not found " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException when creating logger " + e.getMessage());
        }
    }
}
