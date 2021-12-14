package expression;

import java.math.BigDecimal;

public class Add extends BinaryOperatorExpression {
    public Add(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    protected int apply(int left, int right) {
        return left + right;
    }

    @Override
    protected BigDecimal apply(BigDecimal left, BigDecimal right) {
        return left.add(right);
    }

    @Override
    protected String getOperatorString() {
        return "+";
    }
}