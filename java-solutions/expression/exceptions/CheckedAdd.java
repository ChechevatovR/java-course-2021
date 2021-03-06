package expression.exceptions;

import expression.*;

public class CheckedAdd extends CheckedBinaryOperatorExpression {
    public CheckedAdd(final PrioritizedExpression left, final PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperatorString() {
        return "+";
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    @Override
    public ExpressionEvaluationException check(final int left, final int right) {
        if (right > 0 && Integer.MAX_VALUE - right < left) {
            return new OverflowException();
        } else if (right < 0 && Integer.MIN_VALUE - right > left) {
            return new OverflowException();
        }
        return null;
    }

    @Override
    protected int apply(final int left, final int right) {
        return left + right;
    }
}
