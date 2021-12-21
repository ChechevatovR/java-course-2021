package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random = new Random();

    @Override
    public Move makeMove(final Position position) {
        while (true) {
            final Move move = new Move(
                    random.nextInt(position.getM()),
                    random.nextInt(position.getN()),
                    position.getCurPlayerCell()
            );
            if (position.isValid(move)) {
                return move;
            }
        }
    }

    @Override
    public boolean askForDraw(final Position position) {
        return random.nextBoolean();
    }
}
