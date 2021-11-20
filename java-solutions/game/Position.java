package game;

import java.io.IOException;
import java.io.OutputStreamWriter;

public interface Position {
    Cell getCurPlayerCell();

    Cell getCell(int row, int column);

    int getTurnsDone();

    boolean isValid(Move move);

    String toString();
}
