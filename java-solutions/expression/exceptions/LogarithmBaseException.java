package expression.exceptions;

public class LogarithmBaseException extends FunctionDomainException {
    public LogarithmBaseException() {
        super();
    }

    @Override
    public String getMessageAsResult() {
        return "invalid logarithm base";
    }
}
