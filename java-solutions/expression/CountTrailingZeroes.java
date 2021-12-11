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
    protected UnaryOperator getOperator() {
        return a -> new BigDecimal(Integer.numberOfTrailingZeros(a.intValue()));
    }

    @Override
    protected String getOperatorString() {
        return "t0";
    }
}
