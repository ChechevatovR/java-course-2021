package expression.exceptions;

import expression.PrioritizedExpression;

public class CheckedLog extends CheckedBinaryOperatorExpression {
    public CheckedLog(final PrioritizedExpression left, final PrioritizedExpression right) {
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
        return getPriority() - 1;
    }

    @Override
    public ExpressionEvaluationException check(final int left, final int right) {
        if (left <= 0 || right <= 1) {
            return new FunctionDomainException();
        }
        return null;
    }

    @Override
    protected int apply(int left, final int right) {
        int res = 0;
        while (left >= right) {
            left /= right;
            res++;
        }
        return res;
    }
}
