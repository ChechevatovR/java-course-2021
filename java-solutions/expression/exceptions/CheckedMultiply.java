package expression.exceptions;

import expression.*;

public class CheckedMultiply extends CheckedBinaryOperatorExpression {
    public CheckedMultiply(final PrioritizedExpression left, final PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperatorString() {
        return "*";
    }

    @Override
    public int getPriority() {
        return 2000;
    }

    @Override
    protected ExpressionEvaluationException check(final int left, final int right) {
        return checkStatic(left, right);
    }

    protected static ExpressionEvaluationException checkStatic(final int left, final int right) {
        if (right == -1) {
            return left == Integer.MIN_VALUE ? new OverflowException() : null;
        } else if (left == -1) {
            return right == Integer.MIN_VALUE ? new OverflowException() : null;
        } else if (right == 0 || left == 0 || right == 1 || left == 1) {
            return null;
        }

        final int lo = Math.min(Integer.MAX_VALUE / left, Integer.MIN_VALUE / left);
        final int hi = Math.max(Integer.MAX_VALUE / left, Integer.MIN_VALUE / left);

        return lo <= right && right <= hi ? null : new OverflowException();
    }

    @Override
    protected int apply(final int left, final int right) {
        return left * right;
    }
}
