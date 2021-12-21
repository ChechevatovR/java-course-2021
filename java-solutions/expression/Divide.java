package expression;

import java.math.BigDecimal;

public class Divide extends BinaryOperatorExpression {
    public Divide(final PrioritizedExpression left, final PrioritizedExpression right) {
        super(left, right);
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
    protected int apply(final int left, final int right) {
        return left / right;
    }

    @Override
    protected BigDecimal apply(final BigDecimal left, final BigDecimal right) {
        return left.divide(right);
    }

    @Override
    protected String getOperatorString() {
        return "/";
    }
}