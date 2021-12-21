package expression;

import java.math.BigDecimal;

public class ShiftLeft extends BinaryOperatorExpression {
    public ShiftLeft(final PrioritizedExpression left, final PrioritizedExpression right) {
        super(left, right);
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
    protected int apply(final int left, final int right) {
        return left << right;
    }

    @Override
    protected BigDecimal apply(final BigDecimal left, final BigDecimal right) {
        return new BigDecimal(left.intValue() << right.intValue());
    }

    @Override
    protected String getOperatorString() {
        return "<<";
    }
}
