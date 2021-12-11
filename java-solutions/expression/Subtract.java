package expression;

import java.math.BigDecimal;

public class Subtract extends BinaryOperatorExpression {
    public Subtract(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return -10;
    }


    @Override
    protected BinaryOperator getOperator() {
        return BigDecimal::subtract;
    }

    @Override
    protected String getOperatorString() {
        return "-";
    }
}