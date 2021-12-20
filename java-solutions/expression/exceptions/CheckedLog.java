package expression.exceptions;

import expression.PrioritizedExpression;

public class CheckedLog extends CheckedBinaryOperatorExpression {
    public CheckedLog(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperatorString() {
        return "//";
    }

    @Override
    public int getPriority() {
        return 3000;
    }

    @Override
    public int getPriorityRight() {
        return this.getPriority() - 1;
    }

    @Override
    public ExpressionEvaluationException check(int left, int right) {
        if (left <= 0) {
            return new FunctionDomainException();
        } if (right <= 0 || right == 1) {
            return new LogarithmBaseException();
        }
        return null;
    }

    @Override
    protected int apply(int left, int right) {
        int res = 0;
        while (left >= right) {
            left /= right;
            res++;
        }
        return res;
    }
}
