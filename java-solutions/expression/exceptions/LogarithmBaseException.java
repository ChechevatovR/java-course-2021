package expression.exceptions;

public class LogarithmBaseException extends ExpressionEvaluationException {
    public LogarithmBaseException() {
        super();
    }

    @Override
    public String getMessageAsResult() {
        return "invalid logarithm base";
    }
}
