package expression;

import java.math.BigDecimal;

public class UnaryMinus extends UnaryOperatorExpression {
    public UnaryMinus(final PrioritizedExpression operand) {
        super(operand);
    }

    @Override
    public int getPriority() {
        return 1_000_000_000;
    }

    @Override
    protected int apply(final int operand) {
        return -operand;
    }

    @Override
    protected BigDecimal apply(final BigDecimal operand) {
        return operand.negate();
    }

    @Override
    protected String getOperatorString() {
        return "-";
    }
}
