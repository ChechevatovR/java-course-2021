package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random = new Random();
    private final int yBound;
    private final int xBound;

    public RandomPlayer(int yBound, int xBound) {
        this.yBound = yBound;
        this.xBound = xBound;
    }

    @Override
    public Move makeMove(Position position) {
        while (true) {
            final Move move = new Move(
                    random.nextInt(this.xBound),
                    random.nextInt(this.yBound),
                    position.getCurPlayerCell()
            );
            if (position.isValid(move)) {
                return move;
            }
        }
    }

    @Override
    public boolean askForDraw(Position position) {
        return random.nextBoolean();
    }
}
