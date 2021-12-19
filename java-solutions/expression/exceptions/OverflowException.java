package expression.exceptions;

public class OverflowException extends ExpressionEvaluationException {
    public OverflowException() {
        super();
    }

    @Override
    public String getMessageAsResult() {
        return "overflow";
    }
}
