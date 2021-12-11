package expression;

import java.math.BigDecimal;

public class CountLeadingZeroes extends UnaryOperatorExpression {
    public CountLeadingZeroes(PrioritizedExpression operand) {
        super(operand);
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE / 2;
    }

    @Override
    protected UnaryOperator getOperator() {
        return a -> new BigDecimal(Integer.numberOfLeadingZeros(a.intValue()));
    }

    @Override
    protected String getOperatorString() {
        return "l0";
    }
}
