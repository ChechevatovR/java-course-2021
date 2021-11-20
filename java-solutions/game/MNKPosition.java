package game;

import util.StringPadder;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class MNKPosition implements Position {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "O"
    );

    private static StringPadder padder;

    private final Cell[][] field;
    private final int m;
    private final int n;
    private final int k;

    private final Cell curPlayerCell;
    private final int turnsDone;

    public MNKPosition(Cell[][] field, int k, int playersAmount, Cell curPlayerCell, int turnsDone) {
        this.field = field.clone();
        this.m = field.length;
        this.n = field[0].length;
        this.k = k;
        this.curPlayerCell = curPlayerCell;
        this.turnsDone = turnsDone;
        padder = new StringPadder(" ", (int) Math.floor(Math.log10(Math.max(this.m, this.n))) + 1);
    }

    @Override
    public Cell getCurPlayerCell() {
        return this.curPlayerCell;
    }

    @Override
    public Cell getCell(int row, int column) {
        return this.field[row][column];
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
    public String toString() {
        StringBuilder sb = new StringBuilder(padder.pad("")).append(" ");
        for (int i = 1; i <= this.m; i++) {
            sb.append(padder.pad(i)).append(' ');
        }
        sb.append(System.lineSeparator());
        for (int y = 0; y < this.n; y++) {
            sb.append(padder.pad(y + 1)).append(' ');
            for (int x = 0; x < this.m; x++) {
                sb.append(padder.pad(CELL_TO_STRING.get(this.field[y][x]))).append(' ');
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
