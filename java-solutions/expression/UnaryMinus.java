package expression;

import java.math.BigDecimal;

public class UnaryMinus extends UnaryOperatorExpression {
    public UnaryMinus(PrioritizedExpression operand) {
        super(operand);
    }

    @Override
    public int getPriority() {
        return 1_000_000_000;
    }

    @Override
    protected int apply(int operand) {
        return -operand;
    }

    @Override
    protected BigDecimal apply(BigDecimal operand) {
        return operand.negate();
    }

    @Override
    protected String getOperatorString() {
        return "-";
    }
}
