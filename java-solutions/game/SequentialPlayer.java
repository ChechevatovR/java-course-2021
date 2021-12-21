package game;

public class SequentialPlayer implements Player {
    private boolean drawAgreement;

    @Override
    public Move makeMove(final Position position) {
        for (int y = 0; y < position.getN(); y++) {
            for (int x = 0; x < position.getM(); x++) {
                final Move move = new Move(x, y, position.getCurPlayerCell());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }

    @Override
    public boolean askForDraw(final Position position) {
        final boolean res = drawAgreement;
        drawAgreement = !drawAgreement;
        return res;
    }
}
