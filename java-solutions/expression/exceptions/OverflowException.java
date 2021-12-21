package expression.exceptions;

public class OverflowException extends ExpressionEvaluationException {
    @Override
    public String getMessageAsResult() {
        return "overflow";
    }
}
