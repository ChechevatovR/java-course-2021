package expression.exceptions;

public class InvalidInputException extends ExpressionParsingException {
    public InvalidInputException(final String expected, final String got, final String context) {
        super("Expected: " + expected + "; Got: " + got + System.lineSeparator() + "At " + context);
    }
}
