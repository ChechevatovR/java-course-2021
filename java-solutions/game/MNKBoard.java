package game;

import util.MutableVector2;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MNKBoard implements Board {
    private interface NextCellProvider {
        void next(MutableVector2<Integer> v);
    }

    private final Cell[][] field;
    private final int m;
    private final int n;
    private final int k;

    private int curPlayerIndex;
    private Cell curPlayerCell = Cell.X;
    private int turnsDone;

    public MNKBoard(final int m, final int n, final int k) {
        if (n <= 0 || m <= 0 || k <= 0) {
            throw new IllegalArgumentException("All MNKBoard constructor arguments must be positive integers");
        }
        field = new Cell[n][m];
        for (final Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        this.m = m;
        this.n = n;
        this.k = k;
    }

    @Override
    public Cell getCurrentPlayerCell() {
        return curPlayerCell;
    }

    @Override
    public int getCurPlayerIndex() {
        return curPlayerIndex;
    }

    @Override
    public Position getPosition() {
        return new MNKPosition(field, k, curPlayerIndex, curPlayerCell, turnsDone);
    }

    @Override
    public MoveResult applyMove(final Move move) {
        if (move.isDrawRequest()) {
            return MoveResult.DRAW_REQUEST;
        }
        if (!isValid(move)) {
            return MoveResult.INVALID;
        }
        field[move.getY()][move.getX()] = move.getVal();
        curPlayerCell = curPlayerCell == Cell.X ? Cell.O : Cell.X;
        curPlayerIndex = 1 - curPlayerIndex;
        turnsDone++;
        return checkBoard(move);
    }

    private int checkLine(final MutableVector2<Integer> v, final NextCellProvider next) {
        final Cell req = field[v.y][v.x];
        next.next(v);
        int res = 0;
        while (0 <= v.y && v.y < n && 0 <= v.x && v.x < m && field[v.y][v.x] == req && res < k) {
            res++;
            next.next(v);
        }
        return res;
    }

    private MoveResult checkBoard(final Move lastMove) {
        final MutableVector2<Integer> pos = new MutableVector2<>(lastMove.getX(), lastMove.getY());
        final int res = Collections.max(List.of(
                1 + checkLine(pos.copy(), v -> v.x++) + checkLine(pos.copy(), v -> v.x--),
                1 + checkLine(pos.copy(), v -> v.y++) + checkLine(pos.copy(), v -> v.y--),
//                1 + this.checkLine(pos.copy(), v -> {v.x++; v.y++;}) + this.checkLine(pos.copy(), v -> {v.x--; v.y--;}),
                1 + checkLine(pos.copy(), v -> {v.x++; v.y--;}) + checkLine(pos.copy(), v -> {v.x--; v.y++;})
        ));
        if (res >= k) {
            return MoveResult.WIN;
        } else if (turnsDone == m * n) {
            return MoveResult.DRAW;
        }
        return MoveResult.NOT_FINISHED;
    }

    public boolean isValid(final Move move) {
        return move != null
                && 0 <= move.getY() && move.getY() < n
                && 0 <= move.getX() && move.getX() < m
                && field[move.getY()][move.getX()] == Cell.E
                && curPlayerCell == move.getVal();
    }

    @Override
    public void log(final OutputStreamWriter logger) throws IOException {
        logger.append(getPosition().toHumanReadableString());
        logger.append(System.lineSeparator());
        logger.append(String.valueOf(turnsDone)).append(" turns done").append(System.lineSeparator());
    }

}
