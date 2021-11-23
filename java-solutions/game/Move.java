package game;

public class Move {
    private final int x;
    private final int y;
    private final Cell val;
    private final boolean isDrawRequest;

    public Move(int x, int y, Cell val, boolean isDrawRequest) {
        this.x = x;
        this.y = y;
        this.val = val;
        this.isDrawRequest = isDrawRequest;
    }

    public Move(int x, int y, Cell val) {
        this(x, y, val, false);
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

    public boolean isDrawRequest() {
        return isDrawRequest;
    }

    @Override
    public String toString() {
        return String.format("Move(%d, %d, %s)", x + 1, y+ 1, val);
    }
}
