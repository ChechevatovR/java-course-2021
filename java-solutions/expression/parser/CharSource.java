package expression.parser;

import expression.exceptions.ExpressionParsingException;

public interface CharSource {
    char next();

    boolean hasNext();

    char revert();

    ExpressionParsingException error(String expected, String got);
}