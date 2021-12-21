package expression;

import java.math.BigDecimal;

public class Add extends BinaryOperatorExpression {
    public Add(final PrioritizedExpression left, final PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    @Override
    protected int apply(final int left, final int right) {
        return left + right;
    }

    @Override
    protected BigDecimal apply(final BigDecimal left, final BigDecimal right) {
        return left.add(right);
    }

    @Override
    protected String getOperatorString() {
        return "+";
    }
}