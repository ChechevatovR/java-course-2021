package expression.exceptions;

public class DivisionByZeroException extends ExpressionEvaluationException {

    @Override
    public String getMessageAsResult() {
        return "division by zero";
    }
}
