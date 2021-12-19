package expression.exceptions;

import expression.PrioritizedExpression;

public class CheckedAbs extends CheckedUnaryOperatorExpression {
    public CheckedAbs(PrioritizedExpression operand) {
        super(operand);
    }

    @Override
    protected String getOperatorString() {
        return "abs";
    }

    @Override
    public int getPriority() {
        return 1_000_000_000;
    }

    @Override
    public ExpressionEvaluationException check(int operand) {
        if (operand == Integer.MIN_VALUE) {
            return new OverflowException();
        }
        return null;
    }

    @Override
    public int apply(int operand) {
        if (operand < 0) {
            return -operand;
        } else {
            return operand;
        }
    }
}
