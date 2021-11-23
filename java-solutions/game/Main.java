package game;

import scanner.Scanner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Main {
    public static void main(String[] args) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("log.txt");
                OutputStreamWriter logger = new OutputStreamWriter(fileOutputStream);
        ) {
            int m = 10;
            int n = 10;
            int k = 6;
            final int result = new TurnByTurnGame(
                    new MNKBoard(m, n, k),
                    new HumanPlayer(new Scanner(System.in), 1),
                    new HumanPlayer(new Scanner(System.in), 2)
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
