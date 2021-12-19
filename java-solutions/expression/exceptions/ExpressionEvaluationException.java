package expression.exceptions;

public abstract class ExpressionEvaluationException extends RuntimeException {
    public ExpressionEvaluationException() {
        super();
    }

    public ExpressionEvaluationException(String message) {
        super(message);
    }

    abstract public String getMessageAsResult();
}
