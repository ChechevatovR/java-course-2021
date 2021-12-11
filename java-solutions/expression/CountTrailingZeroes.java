package expression;

import java.math.BigDecimal;

public class CountTrailingZeroes extends UnaryOperatorExpression {
    public CountTrailingZeroes(PrioritizedExpression operand) {
        super(operand, a -> new BigDecimal(Integer.numberOfTrailingZeros(a.intValue())), "t0");
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE / 2;
    }
}
