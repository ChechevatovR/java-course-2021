package expression.parser;

public class BaseParser {
    protected final static char EOF = 0;
    protected CharSource source;
    protected char ch;

    public BaseParser() {}

    public BaseParser(CharSource source) {
        this.source = source;
        this.take();
    }

    protected boolean test(char expected) {
        return this.ch == expected;
    }

    protected boolean testBetween(char left, char right) {
        return left <= this.ch && this.ch <= right;
    }

    protected char take() {
        final char result = this.ch;
        this.ch = this.source.hasNext() ? this.source.next() : EOF;
        return result;
    }

    protected boolean take(final char expected) {
        if (this.test(expected)) {
            this.take();
            return true;
        } else {
            return false;
        }
    }

    protected void expect(final char expected) {
       if (!this.take(expected)) {
           throw this.source.error("Expected " + expected + ", found: " + (this.ch != EOF ? this.ch : "EOF"));
       }
    }

    protected void expect(final String expected) {
        for (char exp : expected.toCharArray()) {
            this.expect(exp);
        }
    }

    protected void expectBetween(char left, char right) {
        if (!this.testBetween(left, right)) {
            throw this.source.error(
                    "Expected value between " + left + " and " + right
                    + ", but found: " + (this.ch != EOF ? this.ch : "EOF")
            );
        }
        this.take();
    }
}