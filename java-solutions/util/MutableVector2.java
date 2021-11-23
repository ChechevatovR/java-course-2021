package util;

public class MutableVector2<T> {
    public T x;
    public T y;

    public MutableVector2(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public MutableVector2<T> copy() {
        return new MutableVector2<T>(x, y);
    }
}
