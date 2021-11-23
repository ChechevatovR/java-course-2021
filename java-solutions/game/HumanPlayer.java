package game;

import java.io.IOException;
import java.util.InputMismatchException;
import scanner.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;

    public HumanPlayer(Scanner in, int index) {
        this.in = in;
        System.out.println("You are going to play an \"M, N, K Game\", also known as K-in-line on an MxN board.");
        System.out.println("The rules are simple as they are in Tic-Tac-Toe, but with the differences mentioned above.");
        System.out.println("Once in a while you will be asked for a turn.");
        System.out.println("We will provide you information about board state, as well as figure you are playing for.");
        System.out.println("And we expect your answer to be 2 positive integer numbers, representing row and column");
        System.out.println("respectively; separated by whitespace. With no commas, letters, and anything non-digit.");
        System.out.println("To surrender, simply enter \"surrender\" (without quotes). This will result in your loss");
        System.out.println("To ask other player for a draw, enter \"draw\". If opponent agrees, game will end in draw");
        System.out.println("But keep in mind, that two draw requests in row are not allowed");
        System.out.println("and will result in your loss");
        System.out.println("Enjoy your game!");
        System.out.println();
        System.out.println("You are player №" + index);
        System.out.println();
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println("Current position is: ");
        System.out.println(position.toHumanReadableString());
        System.out.println("It is now your turn with " + position.getCurPlayerCell() + ": ");
        return this.getMove(position);
    }

    @Override
    public boolean askForDraw(Position position) {
        System.out.println("Current position is: ");
        System.out.println(position.toHumanReadableString());
        System.out.println("Your opponent suggested a draw. Do you agree (Y/N)? ");
        while (true) {
            char c;
            try {
                c = in.nextToken(ch -> Character.isLetter(ch)).charAt(0);
            } catch (IOException e) {
                System.out.println("There are IOException problems between you and me, ");
                System.out.println("so I was not able to read your input. ");
                System.out.println(e.getMessage() + ", to be exact. ");
                System.out.println("Please, try again: ");
                continue;
            }
            switch (c) {
                case 'Y':
                    return true;
                case 'N':
                    return false;
                default:
                    System.out.println("The response you entered is invalid.");
                    System.out.println("Please enter something valid (\"Y\"/\"N\"): ");
            }
        }
    }

    private Move getMove(Position position) {
        Move move = null;
        do {
            try {
                if (in.hasNextInt()) {
                    int y = in.nextInt() - 1;
                    int x = in.nextInt() - 1;
                    move = new Move(x, y, position.getCurPlayerCell());
                } else {
                    String command = in.nextWord();
                    if (command.equals("surrender")) {
                        // Returning an invalid turn, which will result in loss
                        return new Move(-1, -1, Cell.E, false);
                    } else if (command.equals("draw")) {
                        return new Move(-1, -1, Cell.E, true);
                    }
                }
            } catch (InputMismatchException ignored) {

            } catch (IOException e) {
                System.out.println("There are IOException problems between you and me, ");
                System.out.println("so I was not able to read your input. ");
                System.out.println(e.getMessage() + ", to be exact. ");
                System.out.println("Please, try again: ");
                continue;
            }
            if (position.isValid(move)) {
                return move;
            }
            System.out.println("The move you entered is invalid.");
            System.out.println("Please enter something valid: ");
        } while (true);
    }
}
