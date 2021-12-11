package expression;

import java.math.BigDecimal;

public class UnaryMinus extends UnaryOperatorExpression {
    public UnaryMinus(PrioritizedExpression operand) {
        super(operand);
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE / 2;
    }

    @Override
    protected UnaryOperator getOperator() {
        return BigDecimal::negate;
    }

    @Override
    protected String getOperatorString() {
        return "-";
    }
}
