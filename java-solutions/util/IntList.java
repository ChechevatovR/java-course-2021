package util;

import java.util.Arrays;

public class IntList {
    private int[] c;
    public int length;

    public IntList() {
        c = new int[0];
        length = 0;
    }

    public IntList(final int val) {
        c = new int[1];
        c[0] = val;
        length = 1;
    }

    public void add(final int val) {
        if (length == c.length) {
            c = Arrays.copyOf(c, length * 2);
        }
        c[length++] = val;
    }

    public int get(final int pos) {
        return c[pos];
    }

    public void shrink() {
        c = Arrays.copyOf(c, length);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(c[i]).append(" ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
