package game;

public interface Position {
    Cell getCurPlayerCell();

    Cell getCell(int row, int column);

    int getTurnsDone();

    boolean isValid(Move move);

    String toHumanReadableString();

    int getCurPlayerInd();

    int getM();

    int getN();

    int getK();
}
