package expression.exceptions;

public abstract class ExpressionParsingException extends RuntimeException {
    // Must it be RuntimeException?
    public ExpressionParsingException() {
        super();
    }

    public ExpressionParsingException(final String message) {
        super(message);
    }
}
