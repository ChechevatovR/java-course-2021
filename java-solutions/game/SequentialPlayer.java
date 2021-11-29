package game;

public class SequentialPlayer implements Player {
    private boolean drawAgreement = false;

    @Override
    public Move makeMove(Position position) {
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
    public boolean askForDraw(Position position) {
        boolean res = drawAgreement;
        drawAgreement = !drawAgreement;
        return res;
    }
}
