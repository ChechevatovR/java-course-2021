package game;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class TurnByTurnGame {
    private final Board board;
    private final Player[] players;

    public TurnByTurnGame(Board board, Player... players) {
        this.board = board;
        this.players = players;
    }

    public int play(OutputStreamWriter logWriter) {
        while (true) {
            int curPlayerIndex = board.getCurPlayerIndex();
            MoveResult result;
            Move move = null;
            try {
                move = players[curPlayerIndex].makeMove(board.getPosition());
                result = board.applyMove(move);
            } catch (Exception e) {
                result = MoveResult.LOSS;
            }
            if (logWriter != null) {
                this.log(logWriter, curPlayerIndex, move, result);
            }
            curPlayerIndex++;
            switch (result) {
                case WIN:
                    return curPlayerIndex;
                case DRAW:
                    return 0;
                case LOSS:
                case INVALID:
                    return -curPlayerIndex;
            }
        }
    }

    private void log(OutputStreamWriter logger, int curPlayerIndex, Move move, MoveResult result) {
        try {
            logger.append("Player №").append(String.valueOf(curPlayerIndex));
            logger.append(" has done turn ").append(String.valueOf(move));
            logger.append(" which resulted in ").append(result.name()).append(System.lineSeparator());
            logger.append("Board after the turn: ").append(System.lineSeparator());
            this.board.log(logger);
            logger.append("===================================================");
            logger.append(System.lineSeparator());
        } catch (IOException e) {
            System.err.println("No logs for this turn due to IOException " + e.getMessage());
        }
    }
}
