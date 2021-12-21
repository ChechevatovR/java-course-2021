package game;

import util.StringPadder;

import java.util.Map;

public class MNKPosition implements Position {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "O"
    );

    private final int mPadWidth;

    private final Cell[][] field;
    private final int m;
    private final int n;
    private final int k;

    private final int curPlayerInd;
    private final Cell curPlayerCell;
    private final int turnsDone;

    @Override
    public int getCurPlayerInd() {
        return curPlayerInd;
    }

    public MNKPosition(final Cell[][] field, final int k, final int curPlayerInd, final Cell curPlayerCell, final int turnsDone) {
        this.field = field;
        m = field[0].length;
        n = field.length;
        this.k = k;
        this.curPlayerInd = curPlayerInd;
        this.curPlayerCell = curPlayerCell;
        this.turnsDone = turnsDone;
        mPadWidth = (int) (Math.log10(m) + 1);
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getK() {
        return k;
    }

    @Override
    public Cell getCurPlayerCell() {
        return curPlayerCell;
    }

    @Override
    public Cell getCell(final int x, final int y) {
        // Throws ArrayIndexOutOfBounds on illegal x, y
        // Understandable and must be expected
        return field[y][x];
    }

    @Override
    public int getTurnsDone() {
        return turnsDone;
    }

    public boolean isValid(final Move move) {
        return move != null
                && 0 <= move.getY() && move.getY() < n
                && 0 <= move.getX() && move.getX() < m
                && field[move.getY()][move.getX()] == Cell.E
                && curPlayerCell == move.getVal();
    }

    @Override
    public String toHumanReadableString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int i = 1; i <= m; i++) {
            sb.append(StringPadder.pad(Integer.toString(i), " ", mPadWidth)).append(" ");
        }
        sb.append(System.lineSeparator());
        for (int y = 0; y < n; y++) {
            final String yS = Integer.toString(y + 1);
            sb.append(" ".repeat(y - yS.length() + 1));
            sb.append(yS).append(" ");
            for (int x = 0; x < m; x++) {
                sb.append(StringPadder.pad(CELL_TO_STRING.get(field[y][x]), " ", mPadWidth)).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
