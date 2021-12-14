package expression;

import java.math.BigDecimal;

public class ShiftLeft extends BinaryOperatorExpression {
    public ShiftLeft(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public int getPriorityRight() {
        return this.getPriority() - 1;
    }

    @Override
    protected int apply(int left, int right) {
        return left << right;
    }

    @Override
    protected BigDecimal apply(BigDecimal left, BigDecimal right) {
        return new BigDecimal(left.intValue() << right.intValue());
    }

    @Override
    protected String getOperatorString() {
        return "<<";
    }
}
