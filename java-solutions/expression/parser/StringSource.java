package expression.parser;

import expression.exceptions.ExpressionParsingException;
import expression.exceptions.InvalidInputException;

public class StringSource implements CharSource {
    private final String string;
    private int pos;

    public StringSource(final String string) {
        this.string = string;
        pos = 0;
    }

    @Override
    public char next() {
        return string.charAt(pos++);
    }

    @Override
    public char revert() {
        return string.charAt(--pos - 1);
    }

    @Override
    public boolean hasNext() {
        return pos < string.length();
    }

    @Override
    public ExpressionParsingException error(final String expected, final String got) {
        // :NOTE: Position
        return new InvalidInputException(
                expected,
                got,
                "Position " + pos + ": "
                    + string.substring(Math.max(0, pos - 5), Math.min(string.length(), pos + 5))
        );
    }
}