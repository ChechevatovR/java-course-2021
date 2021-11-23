package game;

public class CheatingPlayer implements Player {
    private final int yBound;
    private final int xBound;

    public CheatingPlayer(int yBound, int xBound) {
        this.yBound = yBound;
        this.xBound = xBound;
    }

    @Override
    public Move makeMove(Position position) {
        final MNKBoard board = (MNKBoard) position;
        Move first = null;
        for (int y = 0; y < yBound; y++) {
            for (int x = 0; x < xBound; x++) {
                final Move move = new Move(x, y, position.getCurPlayerCell());
                if (position.isValid(move)) {
                    if (first == null) {
                        first = move;
                    } else {
                        board.applyMove(move);
                    }
                }
            }
        }
        return first;
    }

    @Override
    public boolean askForDraw(Position position) {
        return false;
    }
}
