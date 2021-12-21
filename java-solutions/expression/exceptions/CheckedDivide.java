package expression.exceptions;

import expression.*;

public class CheckedDivide extends CheckedBinaryOperatorExpression {
    public CheckedDivide(final PrioritizedExpression left, final PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperatorString() {
        return "/";
    }

    @Override
    public int getPriority() {
        return -2000;
    }

    @Override
    public int getPriorityRight() {
        return getPriority() + 1;
    }

    @Override
    public ExpressionEvaluationException check(final int left, final int right) {
        if (right == 0) {
            return new DivisionByZeroException();
        } else if (right == -1 && left == Integer.MIN_VALUE) {
            return new OverflowException();
        }
        return null;
    }

    @Override
    protected int apply(final int left, final int right) {
        return left / right;
    }
}
