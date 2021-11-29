package game;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class TurnByTurnGame {
    private final Board board;
    private final Player[] players = new Player[2];

    public TurnByTurnGame(Board board, Player player1, Player player2) {
        Objects.requireNonNull(board);
        this.board = board;
        this.players[0] = player1;
        this.players[1] = player2;
    }

    public int play(OutputStreamWriter logWriter) {
        boolean drawIsNotAllowed = false;
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
            if (result != MoveResult.DRAW_REQUEST) {
                drawIsNotAllowed = false;
            }
            switch (result) {
                case DRAW_REQUEST:
                    if (drawIsNotAllowed) {
                        return -(curPlayerIndex + 1);
                    }
                    drawIsNotAllowed = true;
                    if (players[1 - curPlayerIndex].askForDraw(board.getPosition())) {
                        return 0;
                    }
                    break;
                case WIN:
                    return curPlayerIndex + 1;
                case DRAW:
                    return 0;
                case LOSS:
                case INVALID:
                    return -(curPlayerIndex + 1);
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
            logger.append("===================================================").append(System.lineSeparator());
        } catch (IOException e) {
            System.err.println("No logs for this turn due to IOException " + e.getMessage());
        }
    }
}
