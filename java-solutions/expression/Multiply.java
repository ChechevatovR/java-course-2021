package expression;

import java.math.BigDecimal;

public class Multiply extends BinaryOperatorExpression {
    public Multiply(final PrioritizedExpression left, final PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 2000;
    }

    @Override
    protected int apply(final int left, final int right) {
        return left * right;
    }

    @Override
    protected BigDecimal apply(final BigDecimal left, final BigDecimal right) {
        return left.multiply(right);
    }

    @Override
    protected String getOperatorString() {
        return "*";
    }
}