package expression.exceptions;

public abstract class ExpressionParsingException extends RuntimeException {
    // Must it be RuntimeException?
    public ExpressionParsingException() {
        super();
    }

    public ExpressionParsingException(String message) {
        super(message);
    }
}
