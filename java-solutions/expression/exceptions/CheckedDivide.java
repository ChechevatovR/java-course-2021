package expression.exceptions;

import expression.*;

public class CheckedDivide extends CheckedBinaryOperatorExpression {
    public CheckedDivide(PrioritizedExpression left, PrioritizedExpression right) {
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
        return this.getPriority() + 1;
    }

    @Override
    public ExpressionEvaluationException check(int left, int right) {
        if (right == 0) {
            return new DivisionByZeroException();
        } else if (right == -1 && left == Integer.MIN_VALUE) {
            return new OverflowException();
        }
        return null;
    }

    @Override
    protected int apply(int left, int right) {
        return left / right;
    }
}
