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

    private final Cell curPlayerCell;
    private final int turnsDone;

    public MNKPosition(Cell[][] field, int k, Cell curPlayerCell, int turnsDone) {
        this.field = field.clone();
        this.m = field[0].length;
        this.n = field.length;
        this.k = k;
        this.curPlayerCell = curPlayerCell;
        this.turnsDone = turnsDone;
        this.mPadWidth = (int) (Math.log10(this.m) + 1);
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
        return this.curPlayerCell;
    }

    @Override
    public Cell getCell(int x, int y) {
        // Throws ArrayIndexOutOfBounds on illegal x, y
        // Understandable and must be expected
        return this.field[y][x];
    }

    @Override
    public int getTurnsDone() {
        return this.turnsDone;
    }

    public boolean isValid(Move move) {
        return move != null
                && 0 <= move.getY() && move.getY() < this.n
                && 0 <= move.getX() && move.getX() < this.m
                && field[move.getY()][move.getX()] == Cell.E
                && this.curPlayerCell == move.getVal();
    }

    @Override
    public String toHumanReadableString() {
        StringBuilder sb = new StringBuilder(" ");
        for (int i = 1; i <= this.m; i++) {
            sb.append(StringPadder.pad(Integer.toString(i), " ", mPadWidth)).append(" ");
        }
        sb.append(System.lineSeparator());
        for (int y = 0; y < this.n; y++) {
            String yS = Integer.toString(y + 1);
            sb.append(" ".repeat(y - yS.length() + 1));
            sb.append(yS).append(" ");
            for (int x = 0; x < this.m; x++) {
                sb.append(StringPadder.pad(CELL_TO_STRING.get(this.field[y][x]), " ", mPadWidth)).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
