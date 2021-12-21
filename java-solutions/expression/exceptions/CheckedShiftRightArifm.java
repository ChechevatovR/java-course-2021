package expression.exceptions;

import expression.PrioritizedExpression;

public class CheckedShiftRightArifm extends CheckedBinaryOperatorExpression {
    public CheckedShiftRightArifm(final PrioritizedExpression left, final PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperatorString() {
        return ">>";
    }

    @Override
    public int getPriority() {
        return 500;
    }

    @Override
    public int getPriorityRight() {
        return getPriority() - 1;
    }

    @Override
    public ExpressionEvaluationException check(final int left, final int right) {
//        Why would you not do that?
//        if (right < 0 || right >= 32) {
//            return new OverflowException();
//        }
        return null;
    }

    @Override
    protected int apply(final int left, final int right) {
        return left >> right;
    }
}
