package expression.exceptions;

public abstract class ExpressionEvaluationException extends RuntimeException {
    public ExpressionEvaluationException() {
        super();
    }

    public ExpressionEvaluationException(final String message) {
        super(message);
    }

    public abstract String getMessageAsResult();
}
