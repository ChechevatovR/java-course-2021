package game;

public class SequentialPlayer implements Player {
    private final int yBound;
    private final int xBound;

    public SequentialPlayer(int yBound, int xBound) {
        this.yBound = yBound;
        this.xBound = xBound;
    }

    @Override
    public Move makeMove(Position position) {
        for (int y = 0; y < yBound; y++) {
            for (int x = 0; x < xBound; x++) {
                final Move move = new Move(x, y, position.getCurPlayerCell());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }
}
