package game;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.function.Predicate;

import scanner.Scanner;
import util.IsLineSeparator;

public class HumanPlayer implements Player {
    private final Scanner in;
    private final static Predicate<Character> isNotLineSeparator = new IsLineSeparator().negate();

    public HumanPlayer(Scanner in) {
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
    }

    private static void printYourMoveIsInvalid(String format) {
        System.out.println("Your input is invalid.");
        System.out.println("Please enter with the required format (" + format + "):");
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println("Current position is: ");
        System.out.println(position.toHumanReadableString());
        System.out.println(
                "Player " + (position.getCurPlayerInd() + 1)
                + ", it is now your turn with " + position.getCurPlayerCell() + ": "
        );
        return this.getMove(position);
    }

    @Override
    public boolean askForDraw(Position position) {
        System.out.println("Current position is: ");
        System.out.println(position.toHumanReadableString());
        System.out.println("Your opponent suggested a draw. Do you agree (Y/N)? ");
        do {
            String answer;
            try {
                if (in.hasNextToken(isNotLineSeparator)) {
                    answer = in.nextToken(isNotLineSeparator);
                    switch (answer) {
                        case "Y":
                            return true;
                        case "N":
                            return false;
                        default:
                            printYourMoveIsInvalid("\"Y\"/\"N\"");
                    }
                } else {
                    return true;
                }
            } catch (IOException e) {
                System.out.println("There are IOException problems between you and me, ");
                System.out.println("so I was not able to read your input. ");
                System.out.println(e.getMessage() + ", to be exact. ");
                System.out.println("Please, try again: ");
            }
        } while (true);
    }

    private Move getMove(Position position) {
        do {
            String answer;
            try {
                if (in.hasNextToken(isNotLineSeparator)) {
                    answer = in.nextToken(isNotLineSeparator);
                } else {
                    answer = "surrender";
                }
            } catch (IOException e) {
                System.out.println("There are IOException problems between you and me, ");
                System.out.println("so I was not able to read your input. ");
                System.out.println(e.getMessage() + ", to be exact. ");
                System.out.println("Please, try again: ");
                continue;
            }
            switch (answer) {
                case "surrender":
                    return new Move(-1, -1, Cell.E);
                case "draw":
                    return new Move(-1, -1, Cell.E, true);
                default:
                    // Intentionally empty
            }
            Scanner answerParser = new Scanner(answer);
            Move move;
            try {
                if (answerParser.hasNextInt()) {
                    int y = answerParser.nextInt() - 1;
                    int x = answerParser.nextInt() - 1;
                    move = new Move(x, y, position.getCurPlayerCell());
                    if (answerParser.hasNextInt()) {
                        printYourMoveIsInvalid(">2< positive integer numbers separated by a whitespace");
                        continue;
                    }
                } else {
                    printYourMoveIsInvalid("2 positive >integer numbers< >separated by a whitespace<");
                    continue;
                }
            } catch (InputMismatchException e) {
                printYourMoveIsInvalid("2 positive >integer numbers< separated by a whitespace");
                continue;
            } catch (IOException e) {
                throw new AssertionError("Got an IO Exception while trying to read from RAM", e);
            }
            if (position.isValid(move)) {
                return move;
            } else {
                printYourMoveIsInvalid("2 >positive< integer numbers separated by" +
                        " a whitespace >corresponding to an empty cell<");
            }
        } while (true);
    }
}
