package expression.exceptions;

public class FunctionDomainException extends ExpressionEvaluationException {
    public FunctionDomainException() {
        super();
    }

    @Override
    public String getMessageAsResult() {
        return "function undefined";
    }
}
