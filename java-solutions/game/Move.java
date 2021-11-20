package game;

public class Move {
    private final int y;
    private final int x;
    private final Cell val;

    public Move(int x, int y, Cell val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Cell getVal() {
        return val;
    }

    @Override
    public String toString() {
        return String.format("Move(%s, %d, %d)", val, y + 1, x + 1);
    }
}
