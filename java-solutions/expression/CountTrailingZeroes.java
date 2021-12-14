package expression;

import java.math.BigDecimal;

public class CountTrailingZeroes extends UnaryOperatorExpression {
    public CountTrailingZeroes(PrioritizedExpression operand) {
        super(operand);
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE / 2;
    }

    @Override
    protected int apply(int operand) {
        return Integer.numberOfTrailingZeros(operand);
    }

    @Override
    protected BigDecimal apply(BigDecimal operand) {
        return new BigDecimal(Integer.numberOfTrailingZeros(operand.intValue()));
    }

    @Override
    protected String getOperatorString() {
        return "t0";
    }
}
