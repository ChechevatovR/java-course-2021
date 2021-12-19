package expression.exceptions;

public class UnderflowException extends ExpressionEvaluationException {
    public UnderflowException() {
        super();
    }

    @Override
    public String getMessageAsResult() {
        return "underflow";
    }
}
