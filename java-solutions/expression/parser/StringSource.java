package expression.parser;

import expression.exceptions.ExpressionParsingException;
import expression.exceptions.InvalidInputException;

public class StringSource implements CharSource {
    private final String string;
    private int pos;

    public StringSource(String string) {
        this.string = string;
        this.pos = 0;
    }

    @Override
    public char next() {
        return this.string.charAt(this.pos++);
    }

    @Override
    public char revert() {
        return this.string.charAt(--this.pos - 1);
    }

    @Override
    public boolean hasNext() {
        return this.pos < this.string.length();
    }

    @Override
    public ExpressionParsingException error(String expected, String got) {
        return new InvalidInputException(
                expected,
                got,
                "Position " + this.pos + ": "
                    + this.string.substring(Math.max(0, this.pos - 5), Math.min(this.string.length(), this.pos + 5))
        );
    }
}