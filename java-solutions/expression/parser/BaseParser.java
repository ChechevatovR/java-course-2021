package expression.parser;

public class BaseParser {
    protected static final char EOI = 0;
    protected CharSource source;
    protected char ch;

    public BaseParser() {}

    public BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    private static String formatChar(final char c) {
        return c != EOI ? "" + c : "[end of input]";
    }

    protected void revert() {
        ch = source.revert();
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean testBetween(final char left, final char right) {
        return left <= ch && ch <= right;
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : EOI;
        return result;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        } else {
            return false;
        }
    }

    protected void expect(final char expected) {
       if (!take(expected)) {
           throw source.error(formatChar(expected), formatChar(ch));
       }
    }

    protected void expect(final String expected) {
        for (final char exp : expected.toCharArray()) {
            expect(exp);
        }
    }

    protected void expectBetween(final char left, final char right) {
        if (!testBetween(left, right)) {
            throw source.error(
                    "Value between " + formatChar(left) + " and " + formatChar(right),
                    formatChar(ch)
            );
        }
        take();
    }
}