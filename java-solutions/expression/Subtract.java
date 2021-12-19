package expression;

import java.math.BigDecimal;

public class Subtract extends BinaryOperatorExpression {
    public Subtract(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return -1000;
    }

    @Override
    protected int apply(int left, int right) {
        return left - right;
    }

    @Override
    protected BigDecimal apply(BigDecimal left, BigDecimal right) {
        return left.subtract(right);
    }

    @Override
    protected String getOperatorString() {
        return "-";
    }
}