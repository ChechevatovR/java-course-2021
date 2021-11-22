package game;

import util.Vector2;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MNKBoard implements Board {
    private interface NextCellProvider {
        void next(Vector2<Integer> v);
    }

    private final Cell[][] field;
    private final int m;
    private final int n;
    private final int k;
    private final int playersAmount;

    private int curPlayerIndex = 0;
    private Cell curPlayerCell = Cell.X;
    private int turnsDone = 0;

    public MNKBoard(int m, int n, int k, int playersAmount) {
        // m is for width
        // n is for height
        field = new Cell[n][m];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        this.m = m;
        this.n = n;
        this.k = k;
        this.playersAmount = playersAmount;
    }

    @Override
    public Cell getCurrentPlayerCell() {
        return this.curPlayerCell;
    }

    @Override
    public int getCurPlayerIndex() {
        return curPlayerIndex;
    }

    @Override
    public Position getPosition() {
        return new MNKPosition(this.field, this.k, this.curPlayerCell, this.turnsDone);
    }

    @Override
    public MoveResult applyMove(Move move) {
        if (!isValid(move)) {
            return MoveResult.INVALID;
        }
        field[move.getY()][move.getX()] = move.getVal();
        this.curPlayerCell = this.curPlayerCell == Cell.X ? Cell.O : Cell.X;
        this.curPlayerIndex = (this.curPlayerIndex + 1) % this.playersAmount;
        this.turnsDone++;
        return this.checkBoard(move);
    }

    private int checkLine(Vector2<Integer> v, NextCellProvider next) {
        Cell req = this.field[v.y][v.x];
        next.next(v);
        int res = 0;
        while (0 <= v.y && v.y < this.n && 0 <= v.x && v.x < this.m && this.field[v.y][v.x] == req && res < this.k) {
            res++;
            next.next(v);
        }
        return res;
    }

    private MoveResult checkBoard(Move lastMove) {
        Vector2<Integer> pos = new Vector2<>(lastMove.getX(), lastMove.getY());
        int res = Collections.max(List.of(
                1 + this.checkLine(pos.copy(), v -> v.x++) + this.checkLine(pos.copy(), v -> v.x--),
                1 + this.checkLine(pos.copy(), v -> v.y++) + this.checkLine(pos.copy(), v -> v.y--),
                1 + this.checkLine(pos.copy(), v -> {v.x++; v.y++;}) + this.checkLine(pos.copy(), v -> {v.x--; v.y--;}),
                1 + this.checkLine(pos.copy(), v -> {v.x++; v.y--;}) + this.checkLine(pos.copy(), v -> {v.x--; v.y++;})
        ));
        if (res >= this.k) {
            return MoveResult.WIN;
        } else if (this.turnsDone == m * n) {
            return MoveResult.DRAW;
        }
        return MoveResult.NOT_FINISHED;
    }

    public boolean isValid(Move move) {
        return move != null
                && 0 <= move.getY() && move.getY() < this.n
                && 0 <= move.getX() && move.getX() < this.m
                && field[move.getY()][move.getX()] == Cell.E
                && this.curPlayerCell == move.getVal();
    }

    @Override
    public void log(OutputStreamWriter logger) throws IOException {
        logger.append(this.getPosition().toHumanReadableString());
        logger.append(System.lineSeparator());
        logger.append(String.valueOf(this.turnsDone)).append(" turns done").append(System.lineSeparator());
    }

}
