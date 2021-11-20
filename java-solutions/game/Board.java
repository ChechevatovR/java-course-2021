package game;

import java.io.IOException;
import java.io.OutputStreamWriter;

public interface Board {
    Position getPosition();

    MoveResult applyMove(Move move);

    Cell getCurrentPlayerCell();

    int getCurPlayerIndex();

    void log(OutputStreamWriter logger) throws IOException;
}
