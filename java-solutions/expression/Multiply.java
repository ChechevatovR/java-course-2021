package expression;

import java.math.BigDecimal;

public class Multiply extends BinaryOperatorExpression {
    public Multiply(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 20;
    }


    @Override
    protected int apply(int left, int right) {
        return left * right;
    }

    @Override
    protected BigDecimal apply(BigDecimal left, BigDecimal right) {
        return left.multiply(right);
    }

    @Override
    protected String getOperatorString() {
        return "*";
    }
}