package expression.exceptions;

import expression.*;

public class CheckedNegate extends CheckedUnaryOperatorExpression {
    public CheckedNegate(final PrioritizedExpression operand) {
        super(operand);
    }

    @Override
    protected String getOperatorString() {
        return "-";
    }

    @Override
    public int getPriority() {
        return 1_000_000_000;
    }

    @Override
    public ExpressionEvaluationException check(final int operand) {
        return checkStatic(operand);
    }

    public static ExpressionEvaluationException checkStatic(final int operand) {
        if (operand == Integer.MIN_VALUE) {
            return new OverflowException();
        }
        return null;
    }

    @Override
    protected int apply(final int operand) {
        return -operand;
    }
}
