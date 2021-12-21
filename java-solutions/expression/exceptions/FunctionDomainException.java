package expression.exceptions;

public class FunctionDomainException extends ExpressionEvaluationException {
    @Override
    public String getMessageAsResult() {
        return "function undefined";
    }
}
