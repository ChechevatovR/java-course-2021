package expression.exceptions;

public class InvalidInputException extends ExpressionParsingException {
    public InvalidInputException(String expected, String got, String context) {
        super("Expected: " + expected + "; Got: " + got + System.lineSeparator() + "At " + context);
    }
}
