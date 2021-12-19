package expression.exceptions;

import expression.PrioritizedExpression;

public class CheckedShiftRight extends CheckedBinaryOperatorExpression {
    public CheckedShiftRight(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperatorString() {
        return ">>>";
    }

    @Override
    public int getPriority() {
        return 500;
    }

    @Override
    public int getPriorityRight() {
        return this.getPriority() - 1;
    }

    @Override
    public ExpressionEvaluationException check(int left, int right) {
//        Why would you not do that?
//        if (right < 0 || right >= 32) {
//            return new OverflowException();
//        }
        return null;
    }

    @Override
    public int apply(int left, int right) {
        return left >>> right;
    }
}
