package expression;

import java.math.BigDecimal;

public class CountLeadingZeroes extends UnaryOperatorExpression {
    public CountLeadingZeroes(PrioritizedExpression operand) {
        super(operand);
    }

    @Override
    public int getPriority() {
        return 1_000_000_000;
    }

    @Override
    protected int apply(int operand) {
        return Integer.numberOfLeadingZeros(operand);
    }

    @Override
    protected BigDecimal apply(BigDecimal operand) {
        return new BigDecimal(Integer.numberOfLeadingZeros(operand.intValue()));
    }

    @Override
    protected String getOperatorString() {
        return "l0";
    }
}
