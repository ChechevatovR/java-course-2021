package game;

public class Move {
    private final int x;
    private final int y;
    private final Cell val;

    public Move(int x, int y, Cell val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell getVal() {
        return val;
    }

    @Override
    public String toString() {
        return String.format("Move(%d, %d, %s)", x + 1, y+ 1, val);
    }
}
