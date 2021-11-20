package util;

public class StringPadder {
    final private String padding;
    final private int length;

    public StringPadder(String padding, int length) {
        this.padding = padding;
        this.length = length;
    }

    public String pad(String s) {
        return this.padding.repeat(Math.max(0, this.length - s.length())) + s;
    }

    public String pad(Object o) {
        return this.pad(o.toString());
    }
}
