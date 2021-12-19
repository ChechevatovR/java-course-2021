package expression.exceptions;

import expression.*;

public class CheckedMultiply extends CheckedBinaryOperatorExpression {
    public CheckedMultiply(PrioritizedExpression left, PrioritizedExpression right) {
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
    protected ExpressionEvaluationException check(int left, int right) {
        return checkStatic(left, right);
    }

    protected static ExpressionEvaluationException checkStatic(int left, int right) {
        if (right == -1) {
            return left == Integer.MIN_VALUE ? new OverflowException() : null;
        } else if (left == -1) {
            return right == Integer.MIN_VALUE ? new OverflowException() : null;
        } else if (right == 0 || left == 0 || right == 1 || left == 1) {
            return null;
        }

        int lo = Math.min(Integer.MAX_VALUE / left, Integer.MIN_VALUE / left);
        int hi = Math.max(Integer.MAX_VALUE / left, Integer.MIN_VALUE / left);

        return lo <= right && right <= hi ? null : new OverflowException();
    }

    @Override
    public int apply(int left, int right) {
        return left * right;
    }
}
