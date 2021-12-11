package expression;

import java.math.BigDecimal;

public class CountLeadingZeroes extends UnaryOperatorExpression {
    public CountLeadingZeroes(PrioritizedExpression operand) {
        super(operand, a -> new BigDecimal(Integer.numberOfLeadingZeros(a.intValue())), "l0");
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE / 2;
    }
}
