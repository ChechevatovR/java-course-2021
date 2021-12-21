package expression;

import java.math.BigDecimal;

public class CountTrailingZeroes extends UnaryOperatorExpression {
    public CountTrailingZeroes(final PrioritizedExpression operand) {
        super(operand);
    }

    @Override
    public int getPriority() {
        return 1_000_000_000;
    }

    @Override
    protected int apply(final int operand) {
        return Integer.numberOfTrailingZeros(operand);
    }

    @Override
    protected BigDecimal apply(final BigDecimal operand) {
        return new BigDecimal(Integer.numberOfTrailingZeros(operand.intValue()));
    }

    @Override
    protected String getOperatorString() {
        return "t0";
    }
}
